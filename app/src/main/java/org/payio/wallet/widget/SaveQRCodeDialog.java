package org.payio.wallet.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.payio.wallet.R;

import java.util.List;

public class SaveQRCodeDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private TextView mSaveQrcode;
    private TextView mCancel;

    private OnSaveQRListener mOnSaveQRListener;

    public SaveQRCodeDialog(@NonNull Context context) {
        this(context, R.style.GlobalDialog);
    }

    public SaveQRCodeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private void init() {
        View layoutView = LayoutInflater.from(mContext).inflate(R.layout.dialog_save_qrcode, null);

        mSaveQrcode = layoutView.findViewById(R.id.dialog_save_qrcode);
        mCancel = layoutView.findViewById(R.id.dialog_save_qrcode_cancel);

        mSaveQrcode.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        this.setContentView(layoutView);
        Window window = getWindow();
        //设置弹出窗口大小
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //设置显示位置
        window.setGravity(Gravity.BOTTOM);
        //设置动画效果
        //window.setWindowAnimations(R.style.AnimBottom);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_save_qrcode) {

            AndPermission.with(mContext)
                    .runtime()
                    .permission(Permission.WRITE_EXTERNAL_STORAGE)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            if (AndPermission.hasPermissions(mContext, Permission.WRITE_EXTERNAL_STORAGE)) {
                                if (mOnSaveQRListener != null) {
                                    mOnSaveQRListener.onSaveQR();
                                }
                                SaveQRCodeDialog.this.dismiss();
                            } else {
                                SmartToast.warning(mContext.getString(R.string.no_storage_permission));
                                SaveQRCodeDialog.this.dismiss();
                            }
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            SmartToast.warning(mContext.getString(org.payio.wallet.R.string.no_storage_permission));
                            SaveQRCodeDialog.this.dismiss();
                        }
                    })
                    .start();
        } else if (v.getId() == R.id.dialog_save_qrcode_cancel) {
            this.dismiss();
        }
    }

    @Override
    public void show() {
        super.show();
    }

    public void setOnCreateQRListener(OnSaveQRListener mOnSaveQRListener) {
        this.mOnSaveQRListener = mOnSaveQRListener;
    }

    public interface OnSaveQRListener {
        void onSaveQR();
    }
}
