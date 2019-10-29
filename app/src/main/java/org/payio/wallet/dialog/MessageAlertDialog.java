package org.payio.wallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.payio.wallet.R;

public class MessageAlertDialog extends Dialog {
    private Context mContext;
    private TextView mConfirm;
    private TextView mContent;
    private ImageView mIcon;
    private View mainView;
    private OnConfirmClickListener confirmListener;

    public MessageAlertDialog(@NonNull Context context) {
        this(context, R.style.GlobalDialog);
    }

    public MessageAlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    private void initView() {
        mainView = LayoutInflater.from(mContext).inflate(R.layout.dialog_message_alert, null);
        mContent = mainView.findViewById(R.id.message_alert_content);
        mConfirm = mainView.findViewById(R.id.message_alert_confrim);
        mIcon = mainView.findViewById(R.id.message_alert_icon);

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener != null) {
                    MessageAlertDialog.this.dismiss();
                    confirmListener.onConfirmClick();
                } else {
                    MessageAlertDialog.this.dismiss();
                }
            }
        });

        this.setContentView(mainView);
    }

    public void setConfirm(String confirm) {
        mConfirm.setText(confirm);
    }

    public void setContent(String content) {
        this.mContent.setText(content);
    }

    public void setOnConfirmClickListener(OnConfirmClickListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public interface OnConfirmClickListener {
        void onConfirmClick();
    }

}
