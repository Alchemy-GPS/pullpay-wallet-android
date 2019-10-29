package org.payio.wallet.mvp.main_2_order;

import android.content.Context;

import org.payio.wallet.base.presenter.BasePresenter;
import org.payio.wallet.model.event.LogoutEvent;
import org.payio.wallet.network.Basesubscriber;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.OTCOrderRequest;
import org.payio.wallet.network.io.OTCOrderResponse;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;

import java.util.List;

import static org.payio.wallet.params.TransParam.SESSION_EXPIRED;

public class OrderPresenter extends BasePresenter<OrderView> {
    public OrderPresenter(OrderView view) {
        super(view);
    }

    public void getOTCOrder(Context mContext,String orderStatus, int page) {

        OTCOrderRequest request = new OTCOrderRequest(orderStatus, page, "");

        RetrofitUtil.getInstance().getOTCOrderList(request, new Basesubscriber<OTCOrderResponse>() {
            @Override
            public void onResponse(OTCOrderResponse response) {
                Log.e(GsonUtil.objectToJson(response));

                if (MvpRef == null) return;
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    List<OTCOrderResponse.OrderDetail> mDetails = response.getData();

                    MvpRef.get().setOTCOrders(mDetails);
                } else if (response.getReturnCode().equals(SESSION_EXPIRED)) {
                    EventBusUtil.post(LogoutEvent.getInstance());
                }
                MvpRef.get().stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (MvpRef == null) return;
                MvpRef.get().stopRefresh();
            }
        });
    }

    public void getMoreOTCOrder(Context mContext,String orderStatus, int page) {

        OTCOrderRequest request = new OTCOrderRequest(orderStatus, page, "");

        RetrofitUtil.getInstance().getOTCOrderList(request, new Basesubscriber<OTCOrderResponse>() {
            @Override
            public void onResponse(OTCOrderResponse response) {
                Log.e(GsonUtil.objectToJson(response));

                if (MvpRef == null) return;

                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    List<OTCOrderResponse.OrderDetail> mDetails = response.getData();
                    if (mDetails != null && mDetails.size() != 0) {
                        MvpRef.get().addOTCOrders(mDetails);
                    } else {
                        MvpRef.get().stopLoading(false);
                    }
                } else {
                    MvpRef.get().stopLoading(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (MvpRef == null) return;
                MvpRef.get().stopLoading(true);
            }
        });
    }
}
