package org.payio.wallet.mvp.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.payio.wallet.R;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.Log;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class QrcodeScanActivity extends Activity implements QRCodeView.Delegate, View.OnClickListener {
    private ZXingView mScanView;
    private boolean isFlashlightOpen;
    private LinearLayout mFlashlightLayout;
    private ImageView mFlashlightIcon;
    private TextView mFlashlightTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);
        mScanView = findViewById(R.id.qr_code_view);
        mFlashlightLayout = findViewById(R.id.qrcode_scan_flashlight_layout);
        mFlashlightIcon = findViewById(R.id.qrcode_scan_flashlight_icon);
        mFlashlightTip = findViewById(R.id.qrcode_scan_flashlight_tip);

        RelativeLayout mTitleBack = findViewById(R.id.qrcode_scan_back);
        mTitleBack.setOnClickListener(this);
        mFlashlightLayout.setOnClickListener(this);

        mScanView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mScanView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别

        mScanView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
    }

    @Override
    protected void onStop() {
        mScanView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        mScanView.showScanRect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScanView.onDestroy(); // 销毁二维码扫描控件
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        mScanView.closeFlashlight();
        Intent intent = new Intent();
        intent.putExtra(TransParam.QRCODE_RESULT, result);
        setResult(TransParam.QRCODE_RESULT_CODE, intent);
        this.finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        if (isDark) {
            mFlashlightLayout.setVisibility(View.VISIBLE);
        } else {
            if (isFlashlightOpen) {
                mFlashlightLayout.setVisibility(View.VISIBLE);
                return;
            }
            mFlashlightLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.i("error");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.qrcode_scan_back) {
            this.finish();
        } else if (v.getId() == R.id.qrcode_scan_flashlight_layout) {
            turnFlashLight();
        }
    }

    private void turnFlashLight() {
        if (isFlashlightOpen) {
            mScanView.closeFlashlight();
            isFlashlightOpen = false;
            mFlashlightTip.setText(getString(R.string.qrcode_scan_open_flashlight));
            mFlashlightIcon.setImageResource(R.mipmap.flashlight_off);
        } else {
            mScanView.openFlashlight();
            isFlashlightOpen = true;
            mFlashlightTip.setText(getString(R.string.qrcode_scan_close_flashlight));
            mFlashlightIcon.setImageResource(R.mipmap.flashlight_on);
        }
    }
}
