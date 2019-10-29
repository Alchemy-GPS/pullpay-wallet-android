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

import org.payio.wallet.R;

public class EditUsernameDialog extends Dialog implements TextWatcher {

    private Context mContext;
    private TextView mConfirm;
    private TextView mCancel;


    private EditText mUserName;
    private TextView mUserNameTip;

    private View mainView;
    private OnConfirmClickListener confirmListener;

    public EditUsernameDialog(@NonNull Context context) {
        this(context, R.style.GlobalDialog);
    }

    public EditUsernameDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    private void initView() {
        mainView = LayoutInflater.from(mContext).inflate(R.layout.dialog_edit_user_name, null);

        mConfirm = mainView.findViewById(R.id.edit_user_name_confirm);
        mCancel = mainView.findViewById(R.id.edit_user_name_cancel);

        mUserName = mainView.findViewById(R.id.input_user_name);
        mUserNameTip = mainView.findViewById(R.id.input_user_name_tip);

        mUserName.addTextChangedListener(this);

//        mEdittext.setSelectAllOnFocus(true);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUserName.getText().toString().trim();
                if (confirmListener != null && !TextUtils.isEmpty(username)) {
                    confirmListener.onEditUserNameConfirmClick(EditUsernameDialog.this.getUserName());
                } else {
                    showTips(mContext.getString(R.string.user_input_name));
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUsernameDialog.this.dismiss();
            }
        });

        this.setContentView(mainView);
    }

    public String getUserName() {
        return mUserName.getText().toString().trim();
    }

    public void showTips(String error) {
        mUserNameTip.setVisibility(View.VISIBLE);
        mUserNameTip.setText(error);
        mUserNameTip.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.text_shake));
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
            mUserNameTip.setVisibility(View.INVISIBLE);
        } else {
            mConfirm.setBackground(mContext.getResources().getDrawable(R.drawable.button_blue_background));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnConfirmClickListener {
        void onEditUserNameConfirmClick(String userName);
    }
}
