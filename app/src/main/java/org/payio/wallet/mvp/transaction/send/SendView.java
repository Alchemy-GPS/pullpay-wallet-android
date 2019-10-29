package org.payio.wallet.mvp.transaction.send;

import android.app.Activity;

import org.payio.wallet.base.view.IView;
import org.payio.wallet.network.io.WalletInfoResponse;

import java.util.List;

public interface SendView extends IView<Activity> {
    void showLoading();

    void dismiss();

    void paySuccess(String message, int resultCode);

    void setCryptoInfos(List<WalletInfoResponse.CryptoInfo> mCryptoInfos,boolean onlinePay);
}
