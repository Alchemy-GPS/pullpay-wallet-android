package org.payio.wallet.mvp.main;

import android.support.v4.app.FragmentActivity;

import org.payio.wallet.base.view.IView;
import org.payio.wallet.network.io.UpdateAppResponse;

public interface MainView extends IView<FragmentActivity> {
    void showUpdate(UpdateAppResponse response);

    void showDownloadProgress();
}
