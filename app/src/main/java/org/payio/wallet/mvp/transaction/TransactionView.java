package org.payio.wallet.mvp.transaction;

import android.app.Activity;

import org.payio.wallet.base.view.IView;
import org.payio.wallet.network.io.CryptoHistoryResponse;

import java.util.List;

public interface TransactionView extends IView<Activity> {
    void setTransactionHistory(List<CryptoHistoryResponse.Data.CryptoHistory> mList);

    void addTransactionHistory(List<CryptoHistoryResponse.Data.CryptoHistory> mList);

    void stopLoading(boolean haveMore);

    void stopRefresh();

    void setEnabledAmount(String enabledAmount);

    void setFreezedAmount(String freezedAmount);

    void showProgress();

    void dismissProgress();
}
