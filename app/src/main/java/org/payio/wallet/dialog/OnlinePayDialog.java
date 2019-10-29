package org.payio.wallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import org.payio.customview.spinner.MaterialSpinner;
import org.payio.wallet.R;

import java.util.List;

public class OnlinePayDialog extends Dialog implements TextWatcher {

    private Context mContext;
    private TextView mConfirm;
    private TextView mCancel;


    private EditText mPassword;
    private TextView mPasswordTip;

    private View mainView;
    private OnConfirmClickListener confirmListener;
    private OnCancelClickListener cancelListener;
    private MaterialSpinner mSpinner;
    private TextView mOrderAmount;
    private TextView mQuantity;
    private TextView mQuantityUnit;
    private TextView mWalletBalance;
    private TextView mWalletBalanceUnit;

    public OnlinePayDialog(@NonNull Context context) {
        this(context, R.style.GlobalDialog);
    }

    public OnlinePayDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    private void initView() {
        mainView = LayoutInflater.from(mContext).inflate(R.layout.dialog_pay_online, null);
        mConfirm = mainView.findViewById(R.id.dialog_pay_confirm);
        mCancel = mainView.findViewById(R.id.dialog_pay_cancel);

        mSpinner = mainView.findViewById(R.id.pay_crypto_spinner);

        mOrderAmount = mainView.findViewById(R.id.pay_order_amount);

        mQuantity = mainView.findViewById(R.id.pay_order_quantity);
        mQuantityUnit = mainView.findViewById(R.id.pay_order_quantity_unit);

        mWalletBalance = mainView.findViewById(R.id.pay_wallet_balance);
        mWalletBalanceUnit = mainView.findViewById(R.id.pay_wallet_balance_unit);

        mPassword = mainView.findViewById(R.id.wallet_pay_password);
        mPasswordTip = mainView.findViewById(R.id.wallet_pay_password_tip);

        mPassword.addTextChangedListener(this);

//        mEdittext.setSelectAllOnFocus(true);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString().trim();
                if (confirmListener != null
                        && !TextUtils.isEmpty(password)) {
                    confirmListener.onConfirmClick(password);
                } else {
                    showTips(mContext.getString(R.string.wallet_pay_wrong_password));
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlinePayDialog.this.dismiss();
                if (cancelListener != null) {
                    cancelListener.onCancelClick();
                }
            }
        });

        this.setContentView(mainView);
    }

    public String getPassword() {
        return mPassword.getText().toString().trim();
    }

    public void showTips(String tips) {
        mPasswordTip.setVisibility(View.VISIBLE);
        mPasswordTip.setText(tips);
        mPasswordTip.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.text_shake));
    }

    public void setCryptoList(List<String> cryptos) {
        if (mSpinner != null) {
            mSpinner.setItems(cryptos);
        }
    }

    public void setOrderAmount(String orderAmount) {
        if (mOrderAmount != null) {
            mOrderAmount.setText(orderAmount);
        }
    }

    public void setOrderQuantity(String quantity) {
        if (mQuantity != null) {
            mQuantity.setText(quantity);
        }
    }

    public void setBalance(String balance) {
        if (mWalletBalance != null) {
            mWalletBalance.setText(balance);
        }
    }

    public void setUnit(String unit) {
        if (mQuantityUnit != null) {
            mQuantityUnit.setText(unit);
        }
        if (mWalletBalanceUnit != null) {
            mWalletBalanceUnit.setText(unit);
        }
    }

    public void setConfirm(String confirm) {
        mConfirm.setText(confirm);
    }

    public void setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener onItemSelectedListener) {
        if (mSpinner != null) {
            mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    public void setOnConfirmClickListener(OnConfirmClickListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public void setOnCancelClickListener(OnCancelClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            mConfirm.setBackground(mContext.getResources().getDrawable(R.drawable.button_grey_background));
            mPasswordTip.setVisibility(View.INVISIBLE);
        } else {
            mConfirm.setBackground(mContext.getResources().getDrawable(R.drawable.button_blue_background));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnConfirmClickListener {
        void onConfirmClick(String password);
    }

    public interface OnCancelClickListener {
        void onCancelClick();
    }
}
