package org.payio.wallet.mvp.otc_web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.payio.customview.ToastUtil;
import org.payio.progresshub.KProgressHUD;
import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.database.WalletManager;
import org.payio.wallet.model.data.Wallet;
import org.payio.wallet.model.event.LogoutEvent;
import org.payio.wallet.model.event.OTCChangedEvent;
import org.payio.wallet.model.event.OrderChangedEvent;
import org.payio.wallet.model.event.ViewPagerChangeEvent;
import org.payio.wallet.mvp.appeal.AppealActivity;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.params.User;
import org.payio.wallet.utils.AppUtil;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.Log;
import org.payio.wallet.utils.QRCode;
import org.payio.wallet.utils.SharedPreference;
import org.payio.wallet.utils.UriPictureHelper;
import org.payio.wallet.utils.luban.CompressionPredicate;
import org.payio.wallet.utils.luban.Luban;
import org.payio.wallet.utils.luban.OnCompressListener;
import org.payio.wallet.widget.SaveQRCodeDialog;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static org.payio.wallet.params.TransParam.APPEAL_REQUEST_CODE;
import static org.payio.wallet.params.TransParam.GET_QRCODE_IMAGE_REQUESTCODE;

public class OTCWebActivity extends Activity implements View.OnLongClickListener {
    private static final String TAG = "OTC_WEBVIEW";
    private Activity mContext;

    private WebView mWebView;
    private WebSettings mWebSettings;
    private SaveQRCodeDialog mQrCodeDialog;
    private KProgressHUD progressHUD;
    private AlertDialog.Builder mDialog;
    private String codeType;
    private int inputBottomHeight;

    private Handler mHandler = new Handler();
    private String errorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otcweb);
        LinearLayout mRootView = findViewById(R.id.otc_rootview);
        mWebView = findViewById(R.id.otc_webview);
        mContext = this;

        keepKeyboardNotOver(mRootView);

        String pageUrl = getIntent().getStringExtra(TransParam.OTC_PAGE_URL);

        if (TextUtils.isEmpty(pageUrl)) return;
        setWebView(pageUrl);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView(String decodedURL) {

        mWebSettings = mWebView.getSettings();
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSaveFormData(false);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setBlockNetworkImage(false); // 解决图片不显示

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        try {
            decodedURL = URLDecoder.decode(decodedURL, "UTF-8");
            Log.i(TAG, "WebViewURL == " + decodedURL);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mWebView.loadUrl(decodedURL);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));//直接拨打设置为ACTION_CALL,要做权限申请
                    startActivity(intent);
                    return true;
                }

                if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        new AlertDialog.Builder(mContext)
                                .setMessage(mContext.getString(R.string.otc_web_alipay_not_installed))
                                .setPositiveButton(mContext.getString(R.string.otc_web_install_alipay), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        mContext.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton(mContext.getString(R.string.cancel), null).show();
                    }
                    return true;
                }

                webView.loadUrl(url);
                //在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorCode == WebViewClient.ERROR_CONNECT || errorCode == WebViewClient.ERROR_TIMEOUT || errorCode == WebViewClient.ERROR_HOST_LOOKUP || errorCode == WebViewClient.ERROR_UNKNOWN) {
                    errorInfo = "网络异常";
                } else if (errorCode == WebViewClient.ERROR_BAD_URL) {
                    errorInfo = "无效URL";
                } else if (errorCode == WebViewClient.ERROR_FILE_NOT_FOUND || errorCode == WebViewClient.ERROR_FILE) {
                    errorInfo = "文件找不到";
                } else {
                    errorInfo = "请求出错";
                }

                Log.e(TAG, "WebViewError == " + errorInfo + "  description :: " + description);

                mWebView.loadUrl("file:///android_asset/error/error.html");
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                Log.i(TAG, "onPageStarted : " + s);
                showLoading(getString(R.string.loading));
                if (s.contains(Constants.LOGIN_HOST)) {
                    dismissLoading();
                    OTCWebActivity.this.finish();
                    EventBusUtil.post(LogoutEvent.getInstance());
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "onPageFinished : " + url);
                String cookie = CookieManager.getInstance().getCookie(url);
                SharedPreference.get(OTCWebActivity.this).putStringValue(User.USER_COOKIE, cookie);
                super.onPageFinished(view, url);
                dismissLoading();
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                Log.e("onPageCommitVisible");
                dismissLoading();
            }
        });

        //js和android交互,这里添加一个js的接口,以供在JavaScript里调用Android里面的方法
        mWebView.addJavascriptInterface(this, "Android");

        mWebView.setOnLongClickListener(this);
    }

    private void keepKeyboardNotOver(final View root) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                final int rootInvisibleHeight = root.getHeight() - rect.bottom + AppUtil.getStatusBarHeight(mContext);

                final int inputMarginBottom = root.getHeight() - AppUtil.dip2px(mContext, inputBottomHeight);

                final int realInputMarginBottom = (int) (mWebView.getContentHeight() * mWebView.getScale() - AppUtil.dip2px(mContext, inputBottomHeight));

                if (rootInvisibleHeight > inputMarginBottom && rootInvisibleHeight > 0) {
                    int scrollHeight = rootInvisibleHeight - inputMarginBottom;
                    mWebView.scrollTo(0, scrollHeight);
                    if (rootInvisibleHeight > realInputMarginBottom) {
                        int bottom = rootInvisibleHeight - realInputMarginBottom;
                        root.scrollTo(0, bottom);
                    }
                } else {
                    inputBottomHeight = 0;
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    private AlertDialog.Builder setDialog(String message, final String confrimFunction) {
        if (mDialog == null) {
            mDialog = new AlertDialog.Builder(mContext)
                    .setTitle(mContext.getString(R.string.tips))
                    .setNegativeButton(mContext.getString(R.string.cancel), null);
        }
        mDialog.setPositiveButton(mContext.getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mWebView.loadUrl("javascript:" + confrimFunction);
            }
        });

        mDialog.setMessage(message);

        return mDialog;
    }

    @JavascriptInterface
    public void updateWalletPassword(String ethPassword) {
        Log.e("JavaScriptInterface funcion == updateWalletPassword");
        Wallet wallet = WalletManager.getInstance().queryWalletByAddress(CommonUtil.getWalletAddress());
        if (wallet != null) {
            wallet.setWalletPassword(CommonUtil.MD5(ethPassword));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder mCancelTradeDialog = new AlertDialog.Builder(mContext)
                        .setTitle(mContext.getString(R.string.otc_web_password_updated_title))
                        .setMessage(mContext.getString(R.string.otc_web_password_updated_content))
                        .setCancelable(false)
                        .setNegativeButton(mContext.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OTCWebActivity.this.finish();
                                EventBusUtil.post(LogoutEvent.getInstance());
                            }
                        });
                mCancelTradeDialog.show();
            }
        });
    }

    @JavascriptInterface
    public void showKeyboard(final int height) {
        this.inputBottomHeight = height;
    }

    @JavascriptInterface
    public void showDialog(final String message, final String confrimFunction) {
        Log.e("JavaScriptInterface funcion == showDialog message == " + message + " function == " + confrimFunction);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDialog(message, confrimFunction).show();
            }
        });
    }

    @JavascriptInterface
    public void cancelTrade(final String message, final String confrimFunction) {
        Log.e("JavaScriptInterface funcion == showDialog message == " + message + " function == " + confrimFunction);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder mCancelTradeDialog = new AlertDialog.Builder(mContext)
                        .setTitle(mContext.getString(R.string.tips))
                        .setMessage(message)
                        .setNegativeButton(mContext.getString(R.string.think_it_again), null)
                        .setPositiveButton(mContext.getString(R.string.confirm_to_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mWebView.loadUrl("javascript:" + confrimFunction);
                            }
                        });
                mCancelTradeDialog.show();
            }
        });
    }

    @JavascriptInterface
    public void submitAppeal(String orderId, String paymentType) {
        Log.e("JavaScriptInterface funcion == submitAppeal");
        Intent intent = new Intent(mContext, AppealActivity.class);
        intent.putExtra(TransParam.APPEAL_ORDER_ID, orderId);
        intent.putExtra(TransParam.APPEAL_PAYMENT_TYPE, paymentType);
        mContext.startActivityForResult(intent, APPEAL_REQUEST_CODE);
    }

    public void showLoading(String msg) {
        if (progressHUD == null) {
            progressHUD = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setSize(100, 100)
                    .setCancellable(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            OTCWebActivity.this.finish();
                        }
                    });
        }

        if (!TextUtils.isEmpty(msg)) {
            progressHUD.setLabelSize(13);
            progressHUD.setLabel(msg);
        }
        progressHUD.show();
    }

    @JavascriptInterface
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressHUD == null) {
                    progressHUD = KProgressHUD.create(mContext)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setSize(100, 100)
                            .setLabelSize(15)
                            .setCancellable(false);
                }
                progressHUD.show();
            }
        });
    }

    @JavascriptInterface
    public void dismissLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressHUD != null && progressHUD.isShowing()) {
                    progressHUD.dismiss();
                }
            }
        });
    }

    @JavascriptInterface
    public void showToast(String message) {
        Log.e("JavaScriptInterface funcion == showToast message == " + message);
        ToastUtil.showInCenter(OTCWebActivity.this, message, Toast.LENGTH_SHORT);
    }

    @JavascriptInterface
    public String errorPageMessage() {
        Log.e("JavaScriptInterface funcion == errorPageMessage");
        return errorInfo;
    }

    @JavascriptInterface
    public void paymentBackRegister() {
        Log.e("JavaScriptInterface funcion == paymentBackRegister");
        this.finish();
    }

    @JavascriptInterface
    public void paymentBackHomeRefresh() {
        Log.e("JavaScriptInterface funcion == paymentBackHomeRefresh");
        EventBusUtil.post(new ViewPagerChangeEvent(0));
        EventBusUtil.post(OTCChangedEvent.getInstance());
        this.finish();
    }

    @JavascriptInterface
    public void paymentBackHome() {
        Log.e("JavaScriptInterface funcion == paymentBackHome");
        this.finish();
    }

    @JavascriptInterface
    public void paymentBackOrder() {
        Log.e("JavaScriptInterface funcion == paymentBackOrder");
        this.finish();
    }

    @JavascriptInterface
    public void paymentBackOrderRefresh() {
        Log.e("JavaScriptInterface funcion == paymentBackOrderRefresh");
        EventBusUtil.post(new ViewPagerChangeEvent(1));
        EventBusUtil.post(OrderChangedEvent.getInstance());
        this.finish();
    }

    @JavascriptInterface
    public void paymentBackTransaction() {
        Log.e("JavaScriptInterface funcion == paymentBackTransaction");
        this.finish();
    }

    @JavascriptInterface
    public void getQRCodeImage(String codeType) {
        this.codeType = codeType;
        Log.e("JavaScriptInterface funcion == getQRCodeImage");
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (AndPermission.hasPermissions(mContext, Permission.READ_EXTERNAL_STORAGE)) {
                            Intent picIntent = new Intent(Intent.ACTION_PICK);
                            picIntent.setType("image/*");
                            startActivityForResult(picIntent, GET_QRCODE_IMAGE_REQUESTCODE);
                        } else {
                            SmartToast.warning(mContext.getString(R.string.no_storage_permission));
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        SmartToast.warning(mContext.getString(R.string.no_storage_permission));
                    }
                })
                .start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            mWebView.loadUrl("javascript:goup()");
            return true;
            /*if (mWebView.canGoBack()) {
                if (mWebView.getUrl().contains("/trade/paymentResultPage")) {
                    //付款成功页面
                    OTCWebActivity.this.finish();
                }
                mWebView.goBack();
                mWebView.loadUrl("javascript:goup()");
                return true;
            }*/
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_QRCODE_IMAGE_REQUESTCODE) {
            if (data != null) {
                uploadQRCode(data);
            }
        } else if (requestCode == APPEAL_REQUEST_CODE) {
            mWebView.reload();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: 2019/1/10 样式修改后发生变化
//        mWebView.clearCache(true);
//        mWebView.clearHistory();
    }

    /**
     * 上传图片中的二维码
     *
     * @param data
     */
    private void uploadQRCode(Intent data) {
        // 得到图片的全路径
        String path = UriPictureHelper.getPath(mContext, data.getData());
        if (!TextUtils.isEmpty(path)) {
            Log.i(TAG, path);
            Luban.with(this)
                    .load(path)
                    .ignoreBy(1536)//1.5M
                    .setTargetDir(getFilesDir().getAbsolutePath())
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            showLoading();
                        }

                        @Override
                        public void onSuccess(final File file) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            final String qrContent = QRCode.decodeQRCode(bitmap);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    dismissLoading();
                                    String content = qrContent;

                                    if (TextUtils.isEmpty(content)) {
                                        SmartToast.error(mContext.getString(R.string.otc_web_qrcode_error));
                                        return;
                                    } else {
                                        Log.e("二维码图片内容 == " + content);
                                        content = content.toLowerCase();

                                        if (content.startsWith("wxp") && codeType.equals("wechat")) {
                                            mWebView.loadUrl("javascript:uploadCode('" + content + "')");
                                        } else if (content.startsWith("https://qr.alipay.com") && codeType.equals("alipay")) {
                                            mWebView.loadUrl("javascript:uploadCode('" + content + "')");
                                        } else {
                                            SmartToast.error(mContext.getString(R.string.otc_web_qrcode_error));
                                        }
                                    }
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            SmartToast.error(mContext.getString(R.string.otc_web_get_qrcode_failed));
                        }
                    }).launch();
        } else {
            SmartToast.error(mContext.getString(R.string.otc_web_get_qrcode_failed));
        }
    }

    /**
     * 长按图片保存信息
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        WebView.HitTestResult result = mWebView.getHitTestResult();
        if (null == result) return false;
        int type = result.getType();
        switch (type) {
            case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                mWebView.evaluateJavascript("orderCode()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                        Log.e("QRCodeContent == " + value);

                        final String fixValue;

                        if (TextUtils.isEmpty(value) || TextUtils.equals("null", value)) {
                            Log.e("value == null image is not qrcode");
                            return;
                        } else {
                            fixValue = value.replace("\"", "").trim();
                        }

                        final Bitmap bitmap = QRCode.createQRCode(fixValue);

                        if (bitmap == null) {
                            Log.e("bitmap == null");
                            return;
                        }

                        if (mQrCodeDialog == null) {
                            mQrCodeDialog = new SaveQRCodeDialog(OTCWebActivity.this);
                        }
                        mQrCodeDialog.setOnCreateQRListener(new SaveQRCodeDialog.OnSaveQRListener() {
                            @Override
                            public void onSaveQR() {
                                saveQRCode(fixValue, bitmap);
                            }
                        });
                        mQrCodeDialog.show();
                    }
                });

                return true;
        }
        return false;
    }

    private void saveQRCode(final String content, final Bitmap bitmap) {
        mWebView.evaluateJavascript("orderAmount()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.e("onReceiveValue value=" + value);
                value = value.replace("\"", "").trim();
                if (TextUtils.isEmpty(value)) {
                    SmartToast.error(mContext.getString(R.string.otc_web_get_amount_failed));
                    return;
                }

                View qrcodeView;
                if (content.startsWith("wxp")) {
                    qrcodeView = getLayoutInflater().inflate(R.layout.qrcode_background_wechat, null, false);
                } else {
                    qrcodeView = getLayoutInflater().inflate(R.layout.qrcode_background_alipay, null, false);
                }

                //计算视图大小
                DisplayMetrics displayMetrics = CommonUtil.getWindowDisplayMetrics(mContext);
                //创建视图
                CommonUtil.layoutView(qrcodeView, displayMetrics.widthPixels, displayMetrics.heightPixels);
                ((ImageView) qrcodeView.findViewById(R.id.qrcode_img)).setImageBitmap(bitmap);
                ((TextView) qrcodeView.findViewById(R.id.qrcode_amount)).setText(value);

                //将视图生成图片
                Bitmap image = CommonUtil.loadBitmapFromView(qrcodeView);

                if (image != null) {
                    String fileName = CommonUtil.createImgName();
                    Log.e("IMAGE_NAME == " + fileName);
                    File file = CommonUtil.saveBitmapFile(image, fileName);
                    if (file != null) {
                        CommonUtil.notifyImgChanged(mContext, file);
                        SmartToast.success(getString(R.string.otc_web_qrcode_save_success));
                    }
                }
            }
        });

    }
}
