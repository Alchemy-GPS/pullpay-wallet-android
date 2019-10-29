package org.payio.wallet.mvp.appeal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.payio.progresshub.KProgressHUD;
import org.payio.wallet.R;
import org.payio.wallet.base.view.BaseActivity;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.AppUtil;
import org.payio.wallet.utils.UriPictureHelper;

import java.io.File;
import java.util.List;

import static org.payio.wallet.params.TransParam.APPEAL_RESULT_CODE;
import static org.payio.wallet.params.TransParam.GET_QRCODE_IMAGE_REQUESTCODE;

public class AppealActivity extends BaseActivity<AppealPresenter> implements AppealView, View.OnClickListener {
    private KProgressHUD progressHUD;
    private Activity mContext;
    private ImageView mImageDisplay;
    private TextView mTips;
    private RelativeLayout mImagePickerLayout;
    private String path;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal);
        mContext = this;
        RelativeLayout mTitleBack = findViewById(R.id.appeal_title_back);
        TextView mUpload = findViewById(R.id.appeal_upload);
        mImagePickerLayout = findViewById(R.id.appeal_image_picker_layout);
        mTips = findViewById(R.id.appeal_upload_tips);
        mImageDisplay = findViewById(R.id.appeal_image_display);

        mTitleBack.setOnClickListener(this);
        mUpload.setOnClickListener(this);
        mImagePickerLayout.setOnClickListener(this);
        mImageDisplay.setOnClickListener(this);

        dispayPaymentType();
    }

    @Override
    protected AppealPresenter bindPresenter() {
        return new AppealPresenter(this);
    }

    private void dispayPaymentType() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra(TransParam.APPEAL_ORDER_ID);
        String paymentType = intent.getStringExtra(TransParam.APPEAL_PAYMENT_TYPE);

        String uploadTips;
        if (TextUtils.equals(paymentType, "wechat")) {
            uploadTips = String.format(mContext.getResources().getString(R.string.appeal_upload_tips), mContext.getResources().getString(R.string.wechat));
        } else if (TextUtils.equals(paymentType, "alipay")) {
            uploadTips = String.format(mContext.getResources().getString(R.string.appeal_upload_tips), mContext.getResources().getString(R.string.alipay));
        } else if (TextUtils.equals(paymentType, "card")) {
            uploadTips = String.format(mContext.getResources().getString(R.string.appeal_upload_tips), mContext.getResources().getString(R.string.bankcard));
        } else {
            uploadTips = String.format(mContext.getResources().getString(R.string.appeal_upload_tips), "");
        }
        mTips.setText(uploadTips);
    }

    @Override
    public void showLoading(String msg) {
        if (progressHUD == null) {
            progressHUD = KProgressHUD.create(getSelfActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setSize(110, 110)
                    .setCancellable(false);
        }

        if (!TextUtils.isEmpty(msg)) {
            progressHUD.setLabelSize(15);
            progressHUD.setLabel(msg);
        }
        progressHUD.show();
    }

    @Override
    public void dismiss() {
        if (progressHUD != null && progressHUD.isShowing()) {
            progressHUD.dismiss();
        }
    }

    @Override
    public void uploadSuccess() {
        dismiss();
        AlertDialog.Builder mCancelTradeDialog = new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.appeal_upload_success))
                .setMessage(mContext.getString(R.string.appeal_upload_success_message))
                .setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.confirm_user_known), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(APPEAL_RESULT_CODE);
                        AppealActivity.this.finish();
                    }
                });
        mCancelTradeDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.appeal_title_back) {
            this.finish();
        } else if (v.getId() == R.id.appeal_image_picker_layout) {
            selectImage();
        } else if (v.getId() == R.id.appeal_image_display) {
            selectImage();
        } else if (v.getId() == R.id.appeal_upload) {
            if (TextUtils.isEmpty(orderId)) {
                SmartToast.error(getString(R.string.appeal_upload_order_error));
                return;
            }
            if (TextUtils.isEmpty(path)) {
                SmartToast.error(getString(R.string.appeal_upload_image_error));
                return;
            }
            showLoading(getString(R.string.appeal_upload_ing));

            File image = new File(path);
            MvpPre.uploadImage(this, image, orderId);
        }
    }

    private void selectImage() {
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

    private void displayImage(Intent data) {
        path = UriPictureHelper.getPath(mContext, data.getData());
        if (!TextUtils.isEmpty(path)) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);

            ViewGroup.LayoutParams imageViewParams = mImageDisplay.getLayoutParams();
            //还原回原始高度和宽度
            imageViewParams.width = AppUtil.dip2px(mContext, 300);
            imageViewParams.height = AppUtil.dip2px(mContext, 300);

            if (bitmap.getHeight() >= bitmap.getWidth()) {
                imageViewParams.width = (bitmap.getWidth() * imageViewParams.height) / bitmap.getHeight();
            } else {
                imageViewParams.height = (bitmap.getHeight() * imageViewParams.width) / bitmap.getWidth();
            }

            mImageDisplay.setLayoutParams(imageViewParams);

            mImageDisplay.setImageBitmap(bitmap);
            mImageDisplay.setVisibility(View.VISIBLE);
            mImagePickerLayout.setVisibility(View.GONE);

            RelativeLayout.LayoutParams tipParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tipParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tipParams.addRule(RelativeLayout.BELOW, R.id.appeal_image_display);
            tipParams.topMargin = AppUtil.dip2px(mContext, 15);
            mTips.setLayoutParams(tipParams);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == GET_QRCODE_IMAGE_REQUESTCODE) {
                displayImage(data);
            }
        }
    }
}
