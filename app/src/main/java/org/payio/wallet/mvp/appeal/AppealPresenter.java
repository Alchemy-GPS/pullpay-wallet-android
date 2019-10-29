package org.payio.wallet.mvp.appeal;

import android.content.Context;
import android.text.TextUtils;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.payio.wallet.R;
import org.payio.wallet.base.presenter.BasePresenter;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.UploadAppealResponse;
import org.payio.wallet.network.upload.FileUploadObserver;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;

import java.io.File;

import okhttp3.ResponseBody;

public class AppealPresenter extends BasePresenter<AppealView> {

    public AppealPresenter(AppealView view) {
        super(view);
    }


    public void uploadImage(final Context mContext, File image, String orderId) {
        RetrofitUtil.getInstance().uploadAppealImage(image, orderId, new FileUploadObserver<ResponseBody>() {

            @Override
            public void onUpLoadSuccess(String response) {
                Log.e("response == " + response);
                if (!TextUtils.isEmpty(response)) {
                    UploadAppealResponse appealResponse = (UploadAppealResponse) GsonUtil.jsonToBean(response, UploadAppealResponse.class);
                    if (appealResponse != null) {
                        if (appealResponse.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                            if (MvpRef != null) {
                                MvpRef.get().uploadSuccess();
                            }
                        } else {
                            dismiss();
                            SmartToast.error(appealResponse.getReturnMsg());
                        }
                        return;
                    }
                }

                dismiss();
                SmartToast.error(mContext.getString(R.string.appeal_upload_fail));
            }

            @Override
            public void onUpLoadFail(Throwable e) {
                e.printStackTrace();
                dismiss();
                SmartToast.error(mContext.getString(R.string.appeal_upload_fail));
            }

            @Override
            public void onProgress(int progress) {
                Log.e("progress == " + progress);
            }
        });
    }

    private void dismiss() {
        if (MvpRef != null) {
            MvpRef.get().dismiss();
        }
    }
}
