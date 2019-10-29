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
import android.widget.Toast;

import org.payio.customview.spinner.MaterialSpinner;
import org.payio.wallet.R;

import java.util.List;

public class PayDialog extends Dialog implements TextWatcher {

    private Context mContext;
    private TextView mConfirm;
    private TextView mCancel;


    private EditText mPassword;
    private TextView mPasswordTip;

    private View mainView;
    private TextView mAmount;
    private TextView mUnit;
    private TextView mToAddress;

    private OnConfirmClickListener confirmListener;

    public PayDialog(@NonNull Context context) {
        this(context, R.style.GlobalDialog);
    }

    public PayDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    private void initView() {
        mainView = LayoutInflater.from(mContext).inflate(R.layout.dialog_pay, null);
        mConfirm = mainView.findViewById(R.id.dialog_wallet_pay_confirm);
        mCancel = mainView.findViewById(R.id.dialog_wallet_pay_cancel);

        mAmount = mainView.findViewById(R.id.wallet_pay_amount);
        mUnit = mainView.findViewById(R.id.wallet_pay_unit);
        mToAddress = mainView.findViewById(R.id.wallet_pay_to_address);

        mPassword = mainView.findViewById(R.id.wallet_pay_password);
        mPasswordTip = mainView.findViewById(R.id.wallet_pay_password_tip);

        mPassword.addTextChangedListener(this);


//        mEdittext.setSelectAllOnFocus(true);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mAmount.getText().toString().trim();
                String unit = mUnit.getText().toString().trim();
                String toAddress = mToAddress.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (confirmListener != null
                        && !TextUtils.isEmpty(amount)
                        && !TextUtils.isEmpty(unit)
                        && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(toAddress)) {
                    confirmListener.onConfirmClick(password);
                } else {
                    showTips(mContext.getString(R.string.wallet_pay_param_error));
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayDialog.this.dismiss();
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

    public void setAmount(String amount) {
        if (mAmount != null) {
            mAmount.setText(amount);
        }
    }

    public void setAddress(String address) {
        if (mToAddress != null) {
            mToAddress.setText(address);
        }
    }

    public void setUnit(String unit) {
        if (mUnit != null) {
            mUnit.setText(unit);
        }
    }

    public void setConfirm(String confirm) {
        mConfirm.setText(confirm);
    }

    public void setOnConfirmClickListener(OnConfirmClickListener confirmListener) {
        this.confirmListener = confirmListener;
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
}
