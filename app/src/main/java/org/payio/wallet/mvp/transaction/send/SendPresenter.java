package org.payio.wallet.mvp.transaction.send;

import android.content.Context;

import com.coder.zzq.smartshow.toast.SmartToast;

import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.base.presenter.BasePresenter;
import org.payio.wallet.database.CryptocurrencyManger;
import org.payio.wallet.dialog.PayDialog;
import org.payio.wallet.model.event.WalletChangedEvent;
import org.payio.wallet.network.Basesubscriber;
import org.payio.wallet.network.RetrofitUtil;
import org.payio.wallet.network.io.OnlineRaidenPayRequest;
import org.payio.wallet.network.io.OnlineRaidenPayResponse;
import org.payio.wallet.network.io.RaidenPayRequest;
import org.payio.wallet.network.io.RaidenPayResponse;
import org.payio.wallet.network.io.WalletInfoRequest;
import org.payio.wallet.network.io.WalletInfoResponse;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;

import java.util.List;

public class SendPresenter extends BasePresenter<SendView> {

    private PayDialog mPayDialog;
    private Basesubscriber<RaidenPayResponse> mRaidenPayResponse;
    private Basesubscriber<OnlineRaidenPayResponse> mOnlinePayResponse;

    public SendPresenter(SendView view) {
        super(view);
    }

    public void pay(final Context mContext, String userAddress, String amount, String sysFlowNo, String contractAddress) {
        final RaidenPayRequest request = new RaidenPayRequest();
        request.setToken(contractAddress);
        request.setProxy(Constants.TUNNEL_PROXY_ADDRESS);
        request.setUserAddr(userAddress);
        request.setAmount(amount);
        request.setSysFlowNo(sysFlowNo);

        MvpRef.get().showLoading();

        mRaidenPayResponse = new Basesubscriber<RaidenPayResponse>() {
            @Override
            public void onResponse(RaidenPayResponse response) {
                Log.i(GsonUtil.objectToJson(response));
                MvpRef.get().dismiss();
                if (response != null && response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    EventBusUtil.post(WalletChangedEvent.getInstance());
                    MvpRef.get().paySuccess(mContext.getString(R.string.wallet_pay_success), 10000);
                } else {
                    MvpRef.get().paySuccess(mContext.getString(R.string.wallet_pay_failed), 9999);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                MvpRef.get().dismiss();
                MvpRef.get().paySuccess(mContext.getString(R.string.wallet_pay_failed), 9999);
            }
        };

        RetrofitUtil.getInstance().raidenPay(request, mRaidenPayResponse);
    }

    public void cancelPay() {
        if (mRaidenPayResponse != null) {
            mRaidenPayResponse.cancel();
        }

        if (mOnlinePayResponse != null) {
            mOnlinePayResponse.cancel();
        }
    }

    public void onlinePay(final Context mContext,
                          String agentCode,
                          String outFlowNo,
                          String tokenAddress,
                          String tokenAmount,
                          String tokenName) {

        OnlineRaidenPayRequest request = new OnlineRaidenPayRequest(CommonUtil.getWalletAddress(), CommonUtil.getUserId());

        request.setAgentCode(agentCode);
        request.setOutFlowNo(outFlowNo);
        request.setTokenAddress(tokenAddress);
        request.setTokenAmount(tokenAmount);
        request.setTokenName(tokenName);

        MvpRef.get().showLoading();

        mOnlinePayResponse = new Basesubscriber<OnlineRaidenPayResponse>() {
            @Override
            public void onResponse(OnlineRaidenPayResponse response) {
                Log.i(GsonUtil.objectToJson(response));
                MvpRef.get().dismiss();
                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    EventBusUtil.post(WalletChangedEvent.getInstance());
                    MvpRef.get().paySuccess(mContext.getString(R.string.wallet_pay_success), 10000);
                } else {
                    MvpRef.get().paySuccess(response.getReturnMsg(), 9999);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (MvpRef != null) {
                    MvpRef.get().dismiss();
                }
                MvpRef.get().paySuccess(mContext.getString(R.string.wallet_pay_failed), 9999);
            }
        };

        RetrofitUtil.getInstance().raidenPay(request, mOnlinePayResponse);
    }

    public void getWalletInfo(final Context mContext, String agentCode, String outFlowNo) {

        WalletInfoRequest request = new WalletInfoRequest(CommonUtil.getWalletAddress());
        request.setAgentCode(agentCode);
        request.setOutFlowNo(outFlowNo);

        MvpRef.get().showLoading();

        RetrofitUtil.getInstance().getWalletBalnce(request, new Basesubscriber<WalletInfoResponse>() {
            @Override
            public void onResponse(WalletInfoResponse response) {
                Log.e(GsonUtil.objectToJson(response));

                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    List<WalletInfoResponse.CryptoInfo> mCryptoInfos = response.getData();
                    CryptocurrencyManger.getInstance().updateCryptocurrencies(response.getData(), CommonUtil.getWalletAddress());

                    if (MvpRef != null) {
                        MvpRef.get().dismiss();
                        MvpRef.get().setCryptoInfos(mCryptoInfos, true);
                    }
                } else {
                    if (MvpRef != null) {
                        MvpRef.get().dismiss();
                    }
                    SmartToast.warning(mContext.getString(R.string.send_schema_error));
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (MvpRef != null) {
                    MvpRef.get().dismiss();
                }
                SmartToast.warning(mContext.getString(R.string.send_schema_error));
            }
        });
    }

    public void getWalletInfo() {

        RetrofitUtil.getInstance().getWalletBalnce(CommonUtil.getWalletAddress(), new Basesubscriber<WalletInfoResponse>() {
            @Override
            public void onResponse(WalletInfoResponse response) {
                Log.e(GsonUtil.objectToJson(response));

                if (response.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
                    List<WalletInfoResponse.CryptoInfo> mCryptoInfos = response.getData();
                    CryptocurrencyManger.getInstance().updateCryptocurrencies(response.getData(), CommonUtil.getWalletAddress());

                    if (MvpRef != null) {
                        MvpRef.get().setCryptoInfos(mCryptoInfos, false);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
