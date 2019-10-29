package org.payio.wallet.mvp.main_3_wallet;

import android.support.v4.app.FragmentActivity;

import org.payio.wallet.base.view.IView;
import org.payio.wallet.model.data.Cryptocurrency;

import java.util.List;

public interface WalletView extends IView<FragmentActivity> {
    void setCoinInfo(List<Cryptocurrency> coinTypeList);

    void stopRefresh();

    void setWalletAddress(String walletAddress);

    void setWalletBalance(String walletBalance);
}
