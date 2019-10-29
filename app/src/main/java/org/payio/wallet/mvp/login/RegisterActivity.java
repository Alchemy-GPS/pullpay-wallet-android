package org.payio.wallet.mvp.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.payio.progresshub.KProgressHUD;
import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.database.WalletManager;
import org.payio.wallet.mvp.otc_web.OTCWebActivity;
import org.payio.wallet.network.Basesubscriber;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.RegisterRequest;
import org.payio.wallet.network.io.RegisterResponse;
import org.payio.wallet.network.web3j.BaseSubscriber;
import org.payio.wallet.network.web3j.Web3JService;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.FileUtils;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;
import org.web3j.crypto.Credentials;

import java.io.File;

public class RegisterActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText mPhoneNumber;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private RelativeLayout mDeleteAccount;
    private RelativeLayout mDeletePassword;
    private RelativeLayout mDeleteConfirmPassword;
    private KProgressHUD progressHUD;
    private Activity mContext;
    private AlertDialog.Builder mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        RelativeLayout mTitleBack = findViewById(R.id.register_title_back);

        mPhoneNumber = findViewById(R.id.register_input_user_account);
        mPassword = findViewById(R.id.register_input_password);
        mConfirmPassword = findViewById(R.id.register_input_confirm_password);

        mDeleteAccount = findViewById(R.id.register_account_delete_layout);
        mDeletePassword = findViewById(R.id.register_pwd_delete_layout);
        mDeleteConfirmPassword = findViewById(R.id.register_confirm_pwd_delete_layout);

        TextView mLoginButton = findViewById(R.id.register_button);
        TextView mAgreement = findViewById(R.id.register_payio_agreement);
        mTitleBack.setOnClickListener(this);
        mAgreement.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mDeleteAccount.setOnClickListener(this);
        mDeletePassword.setOnClickListener(this);
        mDeleteConfirmPassword.setOnClickListener(this);

        mPhoneNumber.setOnFocusChangeListener(this);
        mPassword.setOnFocusChangeListener(this);
        mConfirmPassword.setOnFocusChangeListener(this);

        mPhoneNumber.addTextChangedListener(new TextWatcher() {
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

        mConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mDeleteConfirmPassword.setVisibility(View.GONE);
                } else {
                    mDeleteConfirmPassword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_title_back) {
            this.finish();
        } else if (v.getId() == R.id.register_button) {
            String phone = mPhoneNumber.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String confirmPassword = mConfirmPassword.getText().toString().trim();
            checkInputInfo(phone, password, confirmPassword);
        } else if (v.getId() == R.id.register_payio_agreement) {
            Intent intent = new Intent(this, OTCWebActivity.class);
            intent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + "raidenOtcWallet/user/protocol");
            startActivity(intent);
        } else if (v.getId() == R.id.register_account_delete_layout) {
            mPhoneNumber.setText("");
        } else if (v.getId() == R.id.register_pwd_delete_layout) {
            mPassword.setText("");
        } else if (v.getId() == R.id.register_confirm_pwd_delete_layout) {
            mConfirmPassword.setText("");
        }
    }

    private void checkInputInfo(String phone, String password, String confirmPassword) {

        if (TextUtils.isEmpty(phone)) {
            SmartToast.error(getString(R.string.register_account_empty_tips));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            SmartToast.error(getString(R.string.register_password_empty_tips));
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            SmartToast.error(getString(R.string.register_password_empty_tips));
            return;
        }

        if (!CommonUtil.isPhone(phone)) {
            SmartToast.error(getString(R.string.register_phone_format_error));
            return;
        }

        if (!TextUtils.equals(password, confirmPassword)) {
            SmartToast.error(getString(R.string.register_password_not_match));
            return;
        }

        if (!CommonUtil.isRightPassword(password) || !CommonUtil.isRightPassword(confirmPassword)) {
            SmartToast.error(getString(R.string.register_password_format_error));
            return;
        }

        register(phone, password);
    }

    private void register(final String phone, final String password) {
        showLoading(getString(R.string.register_registering));
        Web3JService.createWallet(password, new BaseSubscriber<String>() {
            @Override
            public void onSuccess(final String path) {
                //注册失败,重新注册
                Web3JService.loadWallet(password, path, new BaseSubscriber<Credentials>() {
                    @Override
                    public void onSuccess(Credentials credentials) {
                        String address = credentials.getAddress();
                        String ethPrivate = credentials.getEcKeyPair().getPrivateKey().toString(16);
                        String keystore = FileUtils.getFileContent(new File(path));

                        register(phone, password, address, CommonUtil.MD5(password));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dismiss();
                        SmartToast.error(getString(R.string.register_error));
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                dismiss();
                SmartToast.error(getString(R.string.register_error));
            }
        });
    }

    private void register(String phone, String password, String ethAddress, String ethPassword) {
        RegisterRequest request = new RegisterRequest(phone, password, ethAddress, ethPassword);
        RetrofitUtil.getInstance().register(request, new Basesubscriber<RegisterResponse>() {
            @Override
            public void onResponse(RegisterResponse response) {
                Log.i(GsonUtil.objectToJson(response));
                dismiss();
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    if (mDialog == null) {
                        mDialog = new AlertDialog.Builder(mContext)
                                .setTitle(getString(R.string.register_success));
                    }
                    mDialog.setNegativeButton(getString(R.string.register_go_to_login), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RegisterActivity.this.finish();
                        }
                    });
                    mDialog.setMessage(getString(R.string.register_same_password));
                    mDialog.setCancelable(false);
                    mDialog.show();

                } else {
                    SmartToast.error(response.getReturnMsg());
                    WalletManager.getInstance().deleteAll();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                WalletManager.getInstance().deleteAll();
                dismiss();
                SmartToast.error(getString(R.string.register_error));
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.register_input_user_account) {
            if (hasFocus && !TextUtils.isEmpty(mPhoneNumber.getText())) {
                mDeleteAccount.setVisibility(View.VISIBLE);
            } else {
                mDeleteAccount.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.register_input_password) {
            if (hasFocus && !TextUtils.isEmpty(mPassword.getText())) {
                mDeletePassword.setVisibility(View.VISIBLE);
            } else {
                mDeletePassword.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.register_input_confirm_password) {
            if (hasFocus && !TextUtils.isEmpty(mConfirmPassword.getText())) {
                mDeleteConfirmPassword.setVisibility(View.VISIBLE);
            } else {
                mDeleteConfirmPassword.setVisibility(View.GONE);
            }
        }
    }
}
