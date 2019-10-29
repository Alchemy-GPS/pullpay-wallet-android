package org.payio.wallet.mvp.appeal;

import android.app.Activity;

import org.payio.wallet.base.view.IView;
import org.payio.wallet.network.io.RaidenHistoryResponse;

import java.util.List;

public interface AppealView extends IView<Activity> {

    void showLoading(String msg);

    void dismiss();

    void uploadSuccess();
}
