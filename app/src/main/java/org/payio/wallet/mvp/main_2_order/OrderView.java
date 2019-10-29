package org.payio.wallet.mvp.main_2_order;

import android.support.v4.app.FragmentActivity;

import org.payio.wallet.base.view.IView;
import org.payio.wallet.network.io.OTCOrderResponse;

import java.util.List;

public interface OrderView extends IView<FragmentActivity> {
    void setOTCOrders(List<OTCOrderResponse.OrderDetail> mDetails);

    void addOTCOrders(List<OTCOrderResponse.OrderDetail> mDetails);

    void stopLoading(boolean haveMore);

    void stopRefresh();
}
