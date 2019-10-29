package org.payio.wallet.mvp.transaction.receive;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.payio.wallet.R;
import org.payio.wallet.model.event.PayMessageEvent;
import org.payio.wallet.model.event.WalletChangedEvent;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.AppUtil;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.QRCode;


public class ReceiveCurrencyActivity extends Activity implements View.OnClickListener {
    private String address;
    private Bitmap QRBitmap;
    private ImageView mQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_currency);

        EventBusUtil.register(this);

        RelativeLayout mTitleBack = findViewById(R.id.receive_titlebar_back);
        TextView mReceiveAddress = findViewById(R.id.receive_wallet_address);
        mQrcode = findViewById(R.id.receive_address_qrcode);

        address = getIntent().getStringExtra(TransParam.WALLET_ADDRESS);

        mTitleBack.setOnClickListener(this);
        mQrcode.setOnClickListener(this);

        if (!TextUtils.isEmpty(address)) {
            mReceiveAddress.setText(address);
            try {
                QRBitmap = QRCode.createQRCode(address);
                mQrcode.setImageBitmap(QRBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.receive_titlebar_back) {
            this.finish();
        } else if (v.getId() == R.id.receive_address_qrcode) {
            if (!TextUtils.isEmpty(address)) {
                AppUtil.copyAddress(this, address);
                SmartToast.show(getString(R.string.copy_success));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onPayMessageReceived(PayMessageEvent event) {
        if (event != null && event.isChanged()) {
            EventBusUtil.post(WalletChangedEvent.getInstance());
            SmartToast.success(getString(R.string.wallet_pay_success));
            this.finish();
        }
    }
}
