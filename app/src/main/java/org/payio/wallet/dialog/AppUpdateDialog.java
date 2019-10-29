package org.payio.wallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.payio.wallet.R;
import org.payio.wallet.network.io.UpdateAppResponse;

import java.util.List;

public class AppUpdateDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private TextView mUpdateInfo;
    private TextView mCancel;
    private TextView mConfirm;
    private OnUpdatekClickListener listener;

    public AppUpdateDialog(@NonNull Context context) {
        this(context, R.style.GlobalDialog);
    }

    public AppUpdateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private void init() {
        View layoutView = LayoutInflater.from(mContext).inflate(R.layout.dialog_app_update, null);
        mUpdateInfo = layoutView.findViewById(R.id.dialog_update_info);
        mCancel = layoutView.findViewById(R.id.dialog_updte_cancel);
        mConfirm = layoutView.findViewById(R.id.dialog_updte_confirm);
        mConfirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
        this.setContentView(layoutView);
    }

    public void setUpdateInfo(UpdateAppResponse response) {
        if (response != null) {
            String updateInfo = response.getUpdateInfo();

            if (!TextUtils.isEmpty(updateInfo)) {
                updateInfo = updateInfo.replace("\\n", "\n");
                mUpdateInfo.setText(updateInfo);
            } else {
                mUpdateInfo.setText("");
            }

            if (response.getForceUpdate().equals("true")) {
                mCancel.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_updte_cancel) {
            this.dismiss();
        } else if (v.getId() == R.id.dialog_updte_confirm) {
            if (this.listener != null) {
                AndPermission.with(mContext)
                        .runtime()
                        .permission(Permission.Group.STORAGE)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                if (!AndPermission.hasPermissions(mContext, Permission.WRITE_EXTERNAL_STORAGE)) {
                                    SmartToast.warning(mContext.getString(R.string.need_storage_permission_to_update));
                                } else {
                                    listener.onUpdatekClick();
                                    AppUpdateDialog.this.dismiss();
                                }
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                SmartToast.warning(mContext.getString(R.string.need_storage_permission_to_update));
                            }
                        }).start();
            }
        }
    }

    @Override
    public void show() {
        super.show();
    }

    public interface OnUpdatekClickListener {
        void onUpdatekClick();
    }

    public void setUpdatekListener(OnUpdatekClickListener listener) {
        this.listener = listener;
    }
}
