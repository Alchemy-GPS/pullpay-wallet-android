package org.payio.wallet.mvp.transaction;

import android.content.Context;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.payio.customview.ToastUtil;
import org.payio.wallet.base.presenter.BasePresenter;
import org.payio.wallet.network.Basesubscriber;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.CryptoHistoryResponse;
import org.payio.wallet.network.io.RaidenHistoryResponse;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;

import java.util.List;

public class TransactionPresenter extends BasePresenter<TransactionView> {

    public TransactionPresenter(TransactionView view) {
        super(view);
    }

    public void getTransaction(final Context mContext, String token, String address) {
        //先从库里加载
        RetrofitUtil.getInstance().getCryptoHistory(token, address, null, new Basesubscriber<CryptoHistoryResponse>() {
            @Override
            public void onResponse(CryptoHistoryResponse response) {
                Log.i(GsonUtil.objectToJson(response));
                //先入库
                if (MvpRef == null) return;
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {

                    MvpRef.get().setEnabledAmount(response.getData().getNeedAmount());
                    MvpRef.get().setFreezedAmount(response.getData().getFreezeAmount());

                    List<CryptoHistoryResponse.Data.CryptoHistory> mList = response.getData().getList();

                    if (mList != null && mList.size() != 0) {
                        MvpRef.get().setTransactionHistory(mList);
                    }
                } else {
                    SmartToast.warning(response.getReturnMsg());
                }
                MvpRef.get().stopRefresh();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (MvpRef != null) {
                    MvpRef.get().stopRefresh();
                }
            }
        });
    }

    public void getMoreTransaction(final Context mContext, String token, String address, String lastOrderTime) {
        RetrofitUtil.getInstance().getCryptoHistory(token, address, lastOrderTime, new Basesubscriber<CryptoHistoryResponse>() {
            @Override
            public void onResponse(CryptoHistoryResponse response) {
                Log.i(GsonUtil.objectToJson(response));
                //先入库
                if (MvpRef == null) return;
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {

                    List<CryptoHistoryResponse.Data.CryptoHistory> mList = response.getData().getList();

                    if (mList != null && mList.size() != 0) {
                        MvpRef.get().addTransactionHistory(mList);
                    } else {
                        MvpRef.get().stopLoading(false);
                    }
                }
                MvpRef.get().stopRefresh();
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
