package org.payio.wallet.mvp.main;

import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.payio.wallet.OTCApplication;
import org.payio.wallet.R;
import org.payio.wallet.base.presenter.BasePresenter;
import org.payio.wallet.network.Basesubscriber;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.UpdateAppRequest;
import org.payio.wallet.network.io.UpdateAppResponse;
import org.payio.wallet.network.web3j.Web3JService;
import org.payio.wallet.observer.DownloadChangeObserver;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.params.User;
import org.payio.wallet.receiver.DownloadCompleteReceiver;
import org.payio.wallet.utils.AppUtil;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.FileUtils;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;
import org.payio.wallet.utils.SharedPreference;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;

public class MainPresenter extends BasePresenter<MainView> {

    private DownloadChangeObserver observer;

    public MainPresenter(MainView view) {
        super(view);
    }

    public void setPushALias(Context mContext) {
        JPushInterface.setAlias(mContext, 9999, CommonUtil.getUserId());
    }

    public void checkAppUpdate(final Context mContext, final boolean isCheck) {

        UpdateAppRequest request = new UpdateAppRequest(AppUtil.getAppVersionName(mContext), AppUtil.getPackageName(mContext));

        RetrofitUtil.getInstance().checkAppUpdate(request, new Basesubscriber<UpdateAppResponse>() {

            @Override
            public void onResponse(UpdateAppResponse updateAppResponse) {
                Log.i("UpdateAppResponse== " + GsonUtil.objectToJson(updateAppResponse));
                if (updateAppResponse != null && updateAppResponse.getReturnCode().equals(TransParam.SUCCESS_CODE)) {

                    if (!DownloadCompleteReceiver.mIsInit) {
                        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                        OTCApplication.APPLICATION.registerReceiver(new DownloadCompleteReceiver(), filter);
                    }

                    if (updateAppResponse.getNewVersion().equals("true")) {

                        SharedPreference.get(mContext).putStringValue(User.APK_MD5_VALUE, updateAppResponse.getApkMd5());

                        long downloadId = SharedPreference.get(mContext).getLong(User.DOWNLOAD_ID, 0);

                        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

                        observer = new DownloadChangeObserver(null, mContext);

                        observer.setDownloadId(downloadId);

                        mContext.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, observer);

                        switch (observer.queryDownloadStatus()) {

                            case DownloadManager.STATUS_FAILED://下载失败
                                Log.i("下载失败");
                                downloadManager.remove(downloadId);
                                if (MvpRef != null) {
                                    MvpRef.get().showUpdate(updateAppResponse);
                                }
                                break;
                            case DownloadManager.STATUS_PAUSED://下载暂停
                                Log.i("下载暂停");
                                //删除原来下载的apk
                                downloadManager.remove(downloadId);
                                //下载应用 退出App
                                MvpRef.get().showUpdate(updateAppResponse);
                                break;
                            case DownloadManager.STATUS_PENDING://正在请求链接地址
                                Log.i("正在请求");
                                MvpRef.get().showDownloadProgress();
                                break;
                            case DownloadManager.STATUS_RUNNING://正在下载中
                                Log.i("正在下载");
                                MvpRef.get().showDownloadProgress();
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL://下载成功
                                Log.i("下载成功");
                                break;
                            default:
                                Log.i("未下载");
                                if (MvpRef != null) {
                                    MvpRef.get().showUpdate(updateAppResponse);
                                }
                                break;
                        }
                    } else {
                        if (isCheck) {
                            SmartToast.warning(mContext.getString(R.string.app_is_latest_version));
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 使用DownloadManager下载新版本的apk
     *
     * @param url apk的下载链接
     */
    public void download(String url, Context mContext) {
        File apk = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), User.DOWNLOAD_APK_NAME);
        if (apk.exists()) {
            boolean deleted = apk.delete();
        }

        Log.i("downloadURL === " + url);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(User.DOWNLOAD_APK_NAME);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
        request.setVisibleInDownloadsUi(true);
        request.setAllowedOverRoaming(false);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, User.DOWNLOAD_APK_NAME);

        DownloadManager mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        if (mDownloadManager != null) {
            long downloadId = mDownloadManager.enqueue(request);
            if (observer != null) {
                observer.setDownloadId(downloadId);
            }
            SharedPreference.get(mContext).putLongValue("DOWNLOAD_ID", downloadId);
        }
    }

    public void importWallet(final Context mContext) {

        String password = "123456";

        File mWalletFile = FileUtils.createFileInFiles(mContext, "buyer.json");

        FileUtils.copyFilesFromAssets(mContext, "buyer.json", mWalletFile.getAbsolutePath());

        Web3JService.loadWallet(password, mWalletFile.getAbsolutePath(), new org.payio.wallet.network.web3j.BaseSubscriber<Credentials>() {
            @Override
            public void onSuccess(Credentials credentials) {

                ECKeyPair ecKeyPair = credentials.getEcKeyPair();

                try {
                    String fileName = WalletUtils.generateWalletFile("456789", ecKeyPair, mContext.getFilesDir(), false);


                    Log.e("filename == " + fileName);


                } catch (CipherException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
