package org.payio.wallet.mvp.main_4_user;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import org.payio.wallet.base.view.IView;

public interface UserView extends IView<FragmentActivity> {
    void showLoading(String msg);

    void dismiss();

    void finish();

    void setUserId();

    void setUserInfo(String userName,String headImage);

    void clearUserData();

    void updateUserNameError(String error);

    void updateUserNameSuccess(String userName);

    void updateHeadImageError(String error);

    void updateHeadImageSuccess(String headImage);
}
