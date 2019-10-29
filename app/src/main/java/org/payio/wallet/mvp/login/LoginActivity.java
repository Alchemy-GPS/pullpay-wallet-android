package org.payio.wallet.mvp.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.payio.customview.ToastUtil;
import org.payio.progresshub.KProgressHUD;
import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.database.CryptocurrencyManger;
import org.payio.wallet.database.WalletManager;
import org.payio.wallet.model.data.Wallet;
import org.payio.wallet.model.event.WebsocketEvent;
import org.payio.wallet.mvp.main.MainActivity;
import org.payio.wallet.network.Basesubscriber;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.LoginRequest;
import org.payio.wallet.network.io.LoginResponse;
import org.payio.wallet.network.io.WalletInfoResponse;
import org.payio.wallet.network.websocket.WsManager;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.params.User;
import org.payio.wallet.service.MessageService;
import org.payio.wallet.service.ProtectService;
import org.payio.wallet.utils.AppUtil;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;
import org.payio.wallet.utils.SharedPreference;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "LOGIN_WEBVIEW";
    private static final String LOGIN_URL = Constants.APP_HOST + Constants.LOGIN_HOST;

    private WebView mWebView;
    private WebSettings mWebSettings;
    private KProgressHUD progressHUD;
    private Activity mContext;
    private AlertDialog.Builder mDialog;
    private String errorInfo;
    private EditText mUserAccount;
    private EditText mPassword;
    private TextView mLoginButton;
    private TextView mRegister;
    private RelativeLayout mDeleteAccount;
    private RelativeLayout mDeletePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        mWebView = findViewById(R.id.login_webview);

        mUserAccount = findViewById(R.id.login_input_user_account);
        mPassword = findViewById(R.id.login_input_user_password);
        mDeleteAccount = findViewById(R.id.login_account_delete_layout);
        mDeletePassword = findViewById(R.id.login_pwd_delete_layout);

        mLoginButton = findViewById(R.id.login_button);
        mRegister = findViewById(R.id.login_register);

        mRegister.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mDeleteAccount.setOnClickListener(this);
        mDeletePassword.setOnClickListener(this);
        mUserAccount.setOnFocusChangeListener(this);
        mPassword.setOnFocusChangeListener(this);
        mUserAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mDeleteAccount.setVisibility(View.GONE);
                } else {
                    mDeleteAccount.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mDeletePassword.setVisibility(View.GONE);
                } else {
                    mDeletePassword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initWebView(LOGIN_URL);
    }

    private void initWebView(String decodedURL) {
        mWebSettings = mWebView.getSettings();
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSaveFormData(false);
        mWebSettings.setDomStorageEnabled(true);
        try {
            decodedURL = URLDecoder.decode(decodedURL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mWebView.loadUrl(decodedURL);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorCode == WebViewClient.ERROR_CONNECT || errorCode == WebViewClient.ERROR_TIMEOUT || errorCode == WebViewClient.ERROR_HOST_LOOKUP || errorCode == WebViewClient.ERROR_UNKNOWN) {
                    errorInfo = "网络异常";
                } else if (errorCode == WebViewClient.ERROR_BAD_URL) {
                    errorInfo = "无效url";
                } else if (errorCode == WebViewClient.ERROR_FILE_NOT_FOUND || errorCode == WebViewClient.ERROR_FILE) {
                    errorInfo = "文件找不到";
                } else {
                    errorInfo = "请求出错";
                }
                Log.i(TAG, "WebViewError" + errorInfo);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "onPageFinished : " + url);
                super.onPageFinished(view, url);
            }
        });
//        js和android交互,这里添加一个js的接口,以供在JavaScript里调用Android里面的方法
//        mWebView.addJavascriptInterface(this, "Android");
    }

    public void showLoading(String msg) {
        if (progressHUD == null) {
            progressHUD = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setSize(110, 110)
                    .setLabelSize(15)
                    .setCancellable(false);
        }
        if (!TextUtils.isEmpty(msg)) {
            progressHUD.setLabel(msg);
        }
        progressHUD.show();
    }

    public void dismiss() {
        if (progressHUD != null && progressHUD.isShowing()) {
            progressHUD.dismiss();
        }
    }

    public void loginSuccess(final String ethAddress, String ethPassword, String name, String userId) {

        Log.e("JavaScriptInterface funcion == loginSuccess ethAddress == " + ethAddress);
        Log.e("JavaScriptInterface funcion == loginSuccess name == " + name);
        Log.e("JavaScriptInterface funcion == loginSuccess userId == " + userId);

        CommonUtil.setUserName(name);
        CommonUtil.setUserId(userId);

        CommonUtil.setWalletAddress(ethAddress);
        CommonUtil.setWalletPassword(ethPassword);

        List<Wallet> wallets = WalletManager.getInstance().queryAllWallet();
        if (wallets != null && wallets.size() != 0) {
            WalletManager.getInstance().deleteAll();
        }

        Wallet wallet = new Wallet();
        wallet.setWalletPath("");
        wallet.setWalletAddress(ethAddress);
        wallet.setWalletPassword(ethPassword);
        wallet.setWalletName("WALLET");
        wallet.setCreateTime("" + System.currentTimeMillis());
        WalletManager.getInstance().insertWallet(wallet);


        RetrofitUtil.getInstance().getWalletBalnce(ethAddress, new Basesubscriber<WalletInfoResponse>() {
            @Override
            public void onResponse(WalletInfoResponse response) {

                Log.i(GsonUtil.objectToJson(response));

                if (response != null && response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    CryptocurrencyManger.getInstance().updateCryptocurrencies(response.getData(), ethAddress);
                }

                /*
                if (!AppUtil.isServiceRunning(mContext, "org.payio.wallet.service.MessageService")) {
                    Intent intent = new Intent(mContext, MessageService.class);
                    mContext.startService(intent);
                }

                if (!AppUtil.isServiceRunning(mContext, "org.payio.wallet.service.ProtectService")) {
                    Intent intent = new Intent(mContext, ProtectService.class);
                    mContext.startService(intent);
                }
                */

                EventBusUtil.post(new WebsocketEvent(true, WsManager.CLIENT_INIT_WEBSOCKET));

                dismiss();
                SharedPreference.get(LoginActivity.this).putBooleanValue(User.USER_IS_LOGIN, true);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                dismiss();
                SmartToast.error(mContext.getString(R.string.login_error));
            }
        });
    }

    private void login(String account, String password) {
        showLoading(getString(R.string.login_logining));
        LoginRequest request = new LoginRequest(account, password);
        RetrofitUtil.getInstance().login(request, new Basesubscriber<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    //登录成功
                    LoginResponse.UserInfo userInfo = response.getInfo();
                    if (userInfo == null) {
                        dismiss();
                        SmartToast.warning(response.getReturnMsg());
                        return;
                    }
                    loginSuccess(userInfo.getEthAddress(), userInfo.getEthPassword(), userInfo.getName(), userInfo.getUserId());
                } else {
                    //登录失败
                    dismiss();
                    SmartToast.error(response.getReturnMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                dismiss();
                SmartToast.error(mContext.getString(R.string.login_error));
            }
        });
    }

    private void checkInputInfo(String account, String password) {

        if (TextUtils.isEmpty(account)) {
            SmartToast.error(getString(R.string.login_account_empty_tips));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            SmartToast.error(getString(R.string.login_password_empty_tips));
            return;
        }

        if (!CommonUtil.isPhone(account)) {
            SmartToast.error(getString(R.string.login_phone_format_error));
            return;
        }

        login(account, password);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button) {
            String account = mUserAccount.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            checkInputInfo(account, password);
        } else if (v.getId() == R.id.login_register) {
            Intent intent = new Intent(mContext, RegisterActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.login_account_delete_layout) {
            mUserAccount.setText("");
        } else if (v.getId() == R.id.login_pwd_delete_layout) {
            mPassword.setText("");
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.login_input_user_account) {
            if (hasFocus && !TextUtils.isEmpty(mUserAccount.getText())) {
                mDeleteAccount.setVisibility(View.VISIBLE);
            } else {
                mDeleteAccount.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.login_input_user_password) {
            if (hasFocus && !TextUtils.isEmpty(mPassword.getText())) {
                mDeletePassword.setVisibility(View.VISIBLE);
            } else {
                mDeletePassword.setVisibility(View.GONE);
            }
        }
    }
}
