package org.payio.wallet.mvp.main_1_otc.content;

import android.support.v4.app.FragmentActivity;

import org.payio.wallet.base.view.IView;
import org.payio.wallet.network.io.OTCTradeResponse;

import java.util.List;

public interface OTCContentView extends IView<FragmentActivity> {
    void setOTCTrade(List<OTCTradeResponse.TradeInfo> mDetails);

    void addOTCTrade(List<OTCTradeResponse.TradeInfo> mDetails);

    void stopLoading(boolean haveMore);

    void stopRefresh();
}
