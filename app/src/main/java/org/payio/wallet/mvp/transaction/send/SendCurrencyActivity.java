package org.payio.wallet.mvp.transaction.send;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.payio.customview.spinner.MaterialSpinner;
import org.payio.progresshub.KProgressHUD;
import org.payio.wallet.R;
import org.payio.wallet.base.view.BaseActivity;
import org.payio.wallet.database.CryptocurrencyManger;
import org.payio.wallet.database.WalletManager;
import org.payio.wallet.dialog.MessageAlertDialog;
import org.payio.wallet.dialog.OnlinePayDialog;
import org.payio.wallet.dialog.PayDialog;
import org.payio.wallet.model.OnlineQRCode;
import org.payio.wallet.model.RaidenQRCode;
import org.payio.wallet.model.data.Cryptocurrency;
import org.payio.wallet.model.data.Wallet;
import org.payio.wallet.mvp.login.LoginActivity;
import org.payio.wallet.mvp.qrcode.QrcodeScanActivity;
import org.payio.wallet.network.io.WalletInfoResponse;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.params.User;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;
import org.payio.wallet.utils.SharedPreference;
import org.payio.wallet.utils.UrlQrcodeParser;

import java.util.ArrayList;
import java.util.List;

public class SendCurrencyActivity extends BaseActivity<SendPresenter> implements View.OnClickListener, SendView {

    private Activity mActivity;

    private String amount;
    private String token;
    private String sysFlowNo;
    private String proxy;
    private String address;

    private String outFlowNo;
    private String agentCode;

    private EditText mAmount;
    private EditText mAddress;
    private KProgressHUD progressHUD;
    private long currencyId;
    private PayDialog mPayDialog;
    private OnlinePayDialog mOnlinePayDialog;
    private MessageAlertDialog messageAlertDialog;
    private Cryptocurrency mCryptocurrency;
    private List<WalletInfoResponse.CryptoInfo> mCryptoInfos;
    private List<String> cryptos;
    private int selectedCrypto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_currency);
        mActivity = this;

        RelativeLayout mTitleBack = findViewById(R.id.send_titlebar_back);
        RelativeLayout mTitleScan = findViewById(R.id.send_titlebar_scan);
        mAddress = findViewById(R.id.send_et_address);
        mAmount = findViewById(R.id.send_et_amount);
        TextView mConfirm = findViewById(R.id.send_coin_confirm);

        mTitleBack.setOnClickListener(this);
        mTitleScan.setOnClickListener(this);
        mConfirm.setOnClickListener(this);

        currencyId = getIntent().getLongExtra(TransParam.CRYPTOCURRENCY_ID, 0);

        if (currencyId != 0) {
            MvpPre.getWalletInfo();
        }

        handleUriData();
    }

    @Override
    protected SendPresenter bindPresenter() {
        return new SendPresenter(this);
    }

    private void handleUriData() {
        boolean hasLogin = SharedPreference.get(this).getBoolean(User.USER_IS_LOGIN, false);
        if (hasLogin) {
            Intent intent = getIntent();
            Uri uri = intent.getData();
            if (uri != null) {//由第三方APP跳转过来携带的信息
                agentCode = uri.getQueryParameter("agentCode");
                outFlowNo = uri.getQueryParameter("outFlowNo");

                //1.先根据外部订单号,查询订单及币种相关信息
                if (!TextUtils.isEmpty(agentCode) && !TextUtils.isEmpty(outFlowNo)) {
                    MvpPre.getWalletInfo(mActivity, agentCode, outFlowNo);
                } else {
                    SmartToast.error(getString(R.string.send_schema_error));
                }
            }
        } else {
            //未登录
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setSendInfo(String result) {
        Log.e("QRCODE_RESULT == " + result);
        if (!TextUtils.isEmpty(result)) {
            RaidenQRCode raidenQRCode = (RaidenQRCode) GsonUtil.jsonToBean(result, RaidenQRCode.class);
            OnlineQRCode onlineQRCode = (OnlineQRCode) GsonUtil.jsonToBean(result, OnlineQRCode.class);
            UrlQrcodeParser.Parameter parameter = UrlQrcodeParser.parseString(result);

            if (raidenQRCode != null && !TextUtils.isEmpty(raidenQRCode.getSysFlowNo())) {
                token = raidenQRCode.getToken();
                sysFlowNo = raidenQRCode.getSysFlowNo();
                amount = raidenQRCode.getAmount();
                proxy = raidenQRCode.getProxy();
                if (!TextUtils.isEmpty(proxy)) {
                    mAddress.setText(proxy.toLowerCase());
                    mAddress.setEnabled(false);
                }
                if (!TextUtils.isEmpty(amount)) {
                    mAmount.setText(amount);
                    mAmount.setEnabled(false);
                }
            } else if (onlineQRCode != null && !TextUtils.isEmpty(onlineQRCode.getAgentCode()) && !TextUtils.isEmpty(onlineQRCode.getOutFlowNo())) {
                agentCode = onlineQRCode.getAgentCode();
                outFlowNo = onlineQRCode.getOutFlowNo();
                //1.先根据外部订单号,查询订单及币种相关信息
                if (!TextUtils.isEmpty(agentCode) && !TextUtils.isEmpty(outFlowNo)) {
                    MvpPre.getWalletInfo(mActivity, agentCode, outFlowNo);
                } else {
                    SmartToast.warning(getString(R.string.send_schema_error));
                }
            } else if (parameter != null && !TextUtils.isEmpty(parameter.getAgentCode()) && !TextUtils.isEmpty(parameter.getOutFlowNo())) {
                agentCode = parameter.getAgentCode();
                outFlowNo = parameter.getOutFlowNo();
                //1.先根据外部订单号,查询订单及币种相关信息
                if (!TextUtils.isEmpty(agentCode) && !TextUtils.isEmpty(outFlowNo)) {
                    MvpPre.getWalletInfo(mActivity, agentCode, outFlowNo);
                } else {
                    SmartToast.warning(getString(R.string.send_schema_error));
                }
            } else {
                SmartToast.warning(getString(R.string.send_address_error));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_titlebar_back) {
            this.finish();
        } else if (v.getId() == R.id.send_coin_confirm) {

            amount = mAmount.getText().toString().trim();
            address = mAddress.getText().toString().trim();

            if (!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(address)) {
                String walletAddress = CommonUtil.getWalletAddress();

                if (currencyId != 0) {
                    //默认进入方式
                    mCryptocurrency = CryptocurrencyManger.getInstance().queryCryptocurrencyByContract(token);
                }

                if (TextUtils.isEmpty(walletAddress)) {
                    SmartToast.error(getString(R.string.send_wallet_not_created));
                    return;
                }

                if (mCryptocurrency == null) {
                    SmartToast.error(getString(R.string.send_currency_error));
                    return;
                }

                showPayDialog();
            }
        } else if (v.getId() == R.id.send_titlebar_scan) {
            AndPermission.with(this)
                    .runtime()
                    .permission(Permission.CAMERA)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            if (AndPermission.hasPermissions(SendCurrencyActivity.this, Permission.CAMERA)) {
                                Intent intent = new Intent(SendCurrencyActivity.this, QrcodeScanActivity.class);
                                startActivityForResult(intent, TransParam.QRCODE_REQUEST_CODE);
                            } else {
                                SmartToast.warning(getString(R.string.no_camera_permission));
                            }
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            SmartToast.warning(getString(R.string.no_camera_permission));
                        }
                    })
                    .start();
        }
    }

    private void showPayDialog() {
        mPayDialog = new PayDialog(this);
        mPayDialog.setAddress(address);
        mPayDialog.setAmount(amount);
        mPayDialog.setUnit(mCryptocurrency.getUnit());

        Window window = mPayDialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        mPayDialog.setOnConfirmClickListener(new PayDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(String password) {
                Wallet wallet = WalletManager.getInstance().queryWalletByAddress(CommonUtil.getWalletAddress());
                if (wallet == null) return;
                if (CommonUtil.MD5(password).equals(wallet.getWalletPassword())) {
                    mPayDialog.dismiss();
                    showLoading();
                    MvpPre.pay(SendCurrencyActivity.this, wallet.getWalletAddress(), amount, sysFlowNo, token);
                } else {
                    mPayDialog.showTips(SendCurrencyActivity.this.getString(R.string.wallet_pay_wrong_password));
                }
            }
        });

        mPayDialog.show();
    }

    private void showOnlinePayDialog() {
        mOnlinePayDialog = new OnlinePayDialog(this);

        mOnlinePayDialog.setOrderAmount(String.valueOf(mCryptoInfos.get(0).getOrderAmount()));

        if (cryptos == null) {
            cryptos = new ArrayList<>();
        } else {
            cryptos.clear();
        }

        for (WalletInfoResponse.CryptoInfo cryptoInfo : mCryptoInfos) {
            cryptos.add(cryptoInfo.getName());
        }

        //默认选择第一种币
        mOnlinePayDialog.setCryptoList(cryptos);
        mOnlinePayDialog.setOrderQuantity(String.valueOf(SendCurrencyActivity.this.mCryptoInfos.get(0).getTokenAmount()));
        mOnlinePayDialog.setUnit(SendCurrencyActivity.this.mCryptoInfos.get(0).getName());
        mOnlinePayDialog.setBalance(String.valueOf(SendCurrencyActivity.this.mCryptoInfos.get(0).getNeedAmount()));
        //判断余额
        if (SendCurrencyActivity.this.mCryptoInfos.get(0).getTokenAmount() > SendCurrencyActivity.this.mCryptoInfos.get(0).getNeedAmount()) {
            mOnlinePayDialog.showTips(SendCurrencyActivity.this.getString(R.string.wallet_pay_balance_not_enough));
        }

        //用户选择币种
        mOnlinePayDialog.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                selectedCrypto = position;
                mOnlinePayDialog.setOrderQuantity(String.valueOf(SendCurrencyActivity.this.mCryptoInfos.get(position).getTokenAmount()));
                mOnlinePayDialog.setUnit(SendCurrencyActivity.this.mCryptoInfos.get(position).getName());
                mOnlinePayDialog.setBalance(String.valueOf(SendCurrencyActivity.this.mCryptoInfos.get(position).getNeedAmount()));
                if (SendCurrencyActivity.this.mCryptoInfos.get(position).getTokenAmount() > SendCurrencyActivity.this.mCryptoInfos.get(position).getNeedAmount()) {
                    mOnlinePayDialog.showTips(SendCurrencyActivity.this.getString(R.string.wallet_pay_balance_not_enough));
                }
            }
        });

        //确认支付
        mOnlinePayDialog.setOnConfirmClickListener(new OnlinePayDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(String password) {
                Wallet wallet = WalletManager.getInstance().queryWalletByAddress(CommonUtil.getWalletAddress());
                if (wallet == null) return;

                //判断余额
                if (SendCurrencyActivity.this.mCryptoInfos.get(selectedCrypto).getTokenAmount() > SendCurrencyActivity.this.mCryptoInfos.get(selectedCrypto).getNeedAmount()) {
                    mOnlinePayDialog.showTips(SendCurrencyActivity.this.getString(R.string.wallet_pay_balance_not_enough));
                    return;
                }

                if (CommonUtil.MD5(password).equals(wallet.getWalletPassword())) {
                    mOnlinePayDialog.dismiss();
                    showLoading();
                    MvpPre.onlinePay(SendCurrencyActivity.this,
                            agentCode,
                            outFlowNo,
                            SendCurrencyActivity.this.mCryptoInfos.get(selectedCrypto).getTokenAddress(),
                            String.valueOf(SendCurrencyActivity.this.mCryptoInfos.get(selectedCrypto).getTokenAmount()),
                            SendCurrencyActivity.this.mCryptoInfos.get(selectedCrypto).getName());
                } else {
                    mOnlinePayDialog.showTips(SendCurrencyActivity.this.getString(R.string.wallet_pay_wrong_password));
                }
            }
        });

        mOnlinePayDialog.setOnCancelClickListener(new OnlinePayDialog.OnCancelClickListener() {
            @Override
            public void onCancelClick() {
                SendCurrencyActivity.this.finish();
            }
        });

        Window window = mOnlinePayDialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        mOnlinePayDialog.show();
    }

    @Override
    public void paySuccess(String message, final int resultCode) {
        if (messageAlertDialog == null) {
            messageAlertDialog = new MessageAlertDialog(this);
        }
        messageAlertDialog.setContent(message);
        messageAlertDialog.setOnConfirmClickListener(new MessageAlertDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick() {
                SendCurrencyActivity.this.setResult(resultCode);
                SendCurrencyActivity.this.finish();
            }
        });
        if (!messageAlertDialog.isShowing()) {
            messageAlertDialog.show();
        }
    }

    @Override
    public void showLoading() {
        if (progressHUD == null) {
            progressHUD = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setSize(110, 110)
                    .setLabelSize(15)
                    .setCancellable(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            MvpPre.cancelPay();
                        }
                    });
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
    public void setCryptoInfos(List<WalletInfoResponse.CryptoInfo> mCryptoInfos, boolean onlinePay) {
        this.mCryptoInfos = mCryptoInfos;

        //2.显示支付弹窗
        if (onlinePay) {
            showOnlinePayDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TransParam.QRCODE_REQUEST_CODE && resultCode == TransParam.QRCODE_RESULT_CODE) {
            if (data != null) {
                String result = data.getStringExtra(TransParam.QRCODE_RESULT);
                setSendInfo(result);
            }
        }
    }
}
