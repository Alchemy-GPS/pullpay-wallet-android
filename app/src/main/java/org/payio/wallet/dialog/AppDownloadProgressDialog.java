package org.payio.wallet.dialog;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.payio.wallet.R;
import org.payio.wallet.model.event.DownloadEvent;
import org.payio.wallet.utils.AppUtil;
import org.payio.wallet.utils.EventBusUtil;

/**
 * Created by dawnton on 2017/5/3.
 */

public class AppDownloadProgressDialog extends Dialog {
    private Context mContext;
    private View view;
    private ProgressBar downloadProgressBar;
    private TextView downloadPercent;

    public AppDownloadProgressDialog(@NonNull Context context) {
        this(context, R.style.GlobalDialog);
    }

    public AppDownloadProgressDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        EventBusUtil.register(this);
        init();
    }

    private void init() {
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_app_download_progress, null);
        downloadProgressBar = view.findViewById(R.id.download_progress);
        downloadPercent = view.findViewById(R.id.download_percent);

        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
        this.setContentView(view);
    }

    private void setDownloadProgress(int progress) {
        downloadProgressBar.setProgress(progress);
        downloadPercent.setText(progress + "%");
    }

    @Override
    public void show() {
        super.show();
        //设置宽度全屏
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().unregister(this);
    }

    private void onDownloadComplete() {
        this.dismiss();
        AppDownloadFinishDialog dialog = new AppDownloadFinishDialog(mContext);
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressUpdate(DownloadEvent event) {
        int progress = event.getProgress();
        int status = event.getStatus();
        setDownloadProgress(progress);
        if (status == DownloadManager.STATUS_SUCCESSFUL) {
            onDownloadComplete();
        } else if (status == DownloadManager.STATUS_PAUSED || status == DownloadManager.STATUS_FAILED) {
            if (AppUtil.isWifi(mContext)) {
                SmartToast.show(mContext.getString(R.string.check_network));
            } else {
                SmartToast.warning(mContext.getString(R.string.network_no_wifi));
            }
        }
    }
}
