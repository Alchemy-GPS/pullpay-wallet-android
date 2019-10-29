package org.payio.wallet.mvp.main_1_otc.content;

import android.content.Context;

import org.payio.wallet.base.presenter.BasePresenter;
import org.payio.wallet.network.Basesubscriber;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.OTCOrderRequest;
import org.payio.wallet.network.io.OTCOrderResponse;
import org.payio.wallet.network.io.OTCTradeRequest;
import org.payio.wallet.network.io.OTCTradeResponse;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;

import java.util.List;

public class OTCContentPresenter extends BasePresenter<OTCContentView> {
    public OTCContentPresenter(OTCContentView view) {
        super(view);
    }

    public void getOTCTrade(Context mContext,String type, String currencyType, int page) {
        OTCTradeRequest request = new OTCTradeRequest(type, currencyType, page);

        RetrofitUtil.getInstance().getOTCTradeList(request, new Basesubscriber<OTCTradeResponse>() {
            @Override
            public void onResponse(OTCTradeResponse response) {
                Log.e(GsonUtil.objectToJson(response));

                if (MvpRef == null) return;
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    List<OTCTradeResponse.TradeInfo> mTradeInfos = response.getData();

                    MvpRef.get().setOTCTrade(mTradeInfos);
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

    public void getMoreOTCTrade(Context mContext,String type, String currencyType, int page) {

        OTCTradeRequest request = new OTCTradeRequest(type, currencyType, page);

        RetrofitUtil.getInstance().getOTCTradeList(request, new Basesubscriber<OTCTradeResponse>() {
            @Override
            public void onResponse(OTCTradeResponse response) {
                Log.e(GsonUtil.objectToJson(response));

                if (MvpRef == null) return;

                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    List<OTCTradeResponse.TradeInfo> mTradeInfos = response.getData();

                    if (mTradeInfos != null && mTradeInfos.size() != 0) {
                        MvpRef.get().addOTCTrade(mTradeInfos);
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
