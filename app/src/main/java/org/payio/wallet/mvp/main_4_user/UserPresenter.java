package org.payio.wallet.mvp.main_4_user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.payio.wallet.R;
import org.payio.wallet.base.presenter.BasePresenter;
import org.payio.wallet.database.WalletManager;
import org.payio.wallet.model.event.WebsocketEvent;
import org.payio.wallet.mvp.login.LoginActivity;
import org.payio.wallet.network.Basesubscriber;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.LogoutRequest;
import org.payio.wallet.network.io.LogoutResponse;
import org.payio.wallet.network.io.UpdateNameRequest;
import org.payio.wallet.network.io.UpdateNameResponse;
import org.payio.wallet.network.io.UploadHeadImageResponse;
import org.payio.wallet.network.io.UserInfoResponse;
import org.payio.wallet.network.upload.FileUploadObserver;
import org.payio.wallet.network.websocket.WsManager;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.params.User;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;
import org.payio.wallet.utils.SharedPreference;

import java.io.File;

import okhttp3.ResponseBody;


public class UserPresenter extends BasePresenter<UserView> {
    private Context mContext;

    public UserPresenter(UserView view) {
        super(view);
    }

    public void getUserInfo(final Context mContext) {
        this.mContext = mContext;

        RetrofitUtil.getInstance().getUserInfo(new Basesubscriber<UserInfoResponse>() {
            @Override
            public void onResponse(UserInfoResponse response) {
                Log.i(GsonUtil.objectToJson(response));
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    CommonUtil.setUserName(response.getData().getUserName());

                    if (MvpRef != null) {
                        MvpRef.get().setUserId();
                        MvpRef.get().setUserInfo(response.getData().getUserName(), response.getData().getHeadImage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public void logout(final Context mContext) {
        this.mContext = mContext;

        RetrofitUtil.getInstance().logout(new LogoutRequest(CommonUtil.getUserId()), new Basesubscriber<LogoutResponse>() {
            @Override
            public void onResponse(LogoutResponse response) {
                Log.i(GsonUtil.objectToJson(response));
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {

                    SmartToast.show(mContext.getString(R.string.user_logout_success));
                    if (MvpRef != null) {
                        MvpRef.get().clearUserData();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (MvpRef != null) {
                    MvpRef.get().dismiss();
                }
            }
        });
    }

    public void updateName(final Context mContext, final String userName) {
        this.mContext = mContext;
        RetrofitUtil.getInstance().updateName(new UpdateNameRequest(userName), new Basesubscriber<UpdateNameResponse>() {
            @Override
            public void onResponse(UpdateNameResponse response) {
                Log.e(GsonUtil.objectToJson(response));
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    CommonUtil.setUserName(userName);

                    if (MvpRef != null) {
                        MvpRef.get().setUserId();
                        MvpRef.get().updateUserNameSuccess(userName);
                    }
                } else {
                    if (MvpRef != null) {
                        MvpRef.get().updateUserNameError(response.getReturnMsg());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (MvpRef != null) {
                    MvpRef.get().updateUserNameError(mContext.getString(R.string.user_edit_name_net_error));
                }
            }
        });
    }

    public void uploadHeadImage(final Context mContext, File image) {
        RetrofitUtil.getInstance().uploadHeadImage(image, new FileUploadObserver<ResponseBody>() {
            @Override
            public void onUpLoadSuccess(String response) {
                Log.e("response == " + response);
                if (!TextUtils.isEmpty(response)) {
                    UploadHeadImageResponse headImageResponse = (UploadHeadImageResponse) GsonUtil.jsonToBean(response, UploadHeadImageResponse.class);
                    if (headImageResponse != null) {
                        if (headImageResponse.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                            if (MvpRef != null) {
                                MvpRef.get().updateHeadImageSuccess(headImageResponse.getData());
                            }
                        } else {
                            dismiss();
                            SmartToast.warning(headImageResponse.getReturnMsg());
                        }
                        return;
                    }
                }
                dismiss();
                SmartToast.error(mContext.getString(R.string.user_head_image_update_fail));
            }

            @Override
            public void onUpLoadFail(Throwable e) {
                e.printStackTrace();
                dismiss();
                SmartToast.error(mContext.getString(R.string.user_head_image_update_fail));
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

    private void clearUserData(Context mContext) {
        WalletManager.getInstance().deleteAll();

        //清楚webview的cookie
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(mContext);
        CookieManager.getInstance().removeAllCookie();
        cookieSyncManager.sync();

        SharedPreference.get(mContext).putStringValue(User.USER_COOKIE, "");

        //登录状态改为false
        SharedPreference.get(mContext).putBooleanValue(User.USER_IS_LOGIN, false);

        CommonUtil.setUserName("");
        CommonUtil.setUserId("");
        CommonUtil.setOrderStatus("");

        EventBusUtil.post(new WebsocketEvent(false, WsManager.CLIENT_CLOSE_WEBSOCKET));

        if (MvpRef != null) {
            MvpRef.get().dismiss();
        }

        //跳转到登录界面
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
        /*
        if (MvpRef != null) {
            MvpRef.get().finish();
        }
        */
    }
}
