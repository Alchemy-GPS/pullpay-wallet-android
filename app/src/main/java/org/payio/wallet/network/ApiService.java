package org.payio.wallet.network;


import org.payio.wallet.network.io.CryptoHistoryResponse;
import org.payio.wallet.network.io.LoginResponse;
import org.payio.wallet.network.io.LogoutResponse;
import org.payio.wallet.network.io.OTCOrderResponse;
import org.payio.wallet.network.io.OTCTradeResponse;
import org.payio.wallet.network.io.OnlineRaidenPayResponse;
import org.payio.wallet.network.io.RaidenHistoryResponse;
import org.payio.wallet.network.io.RaidenPayResponse;
import org.payio.wallet.network.io.RegisterResponse;
import org.payio.wallet.network.io.UpdateAppResponse;
import org.payio.wallet.network.io.UpdateNameResponse;
import org.payio.wallet.network.io.UploadAppealResponse;
import org.payio.wallet.network.io.UserInfoResponse;
import org.payio.wallet.network.io.WalletInfoResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo
 * @date 2016/12/09  17:04
 */

public interface ApiService {

    @GET("raidenOtcWallet/newWallet/getBalance")
    Observable<WalletInfoResponse> getWalletBalnce(
            @Query("userAddr") String walletAddress,
            @Query("outFlowNo") String outFlowNo,
            @Query("agentCode") String agentCode);

    @GET("raidenOtcWallet/newWallet/getBalance")
    Observable<WalletInfoResponse> getWalletBalnce(
            @Query("userAddr") String walletAddress);

    @GET("raidenOtcWallet/customer/channelPayment")
    Observable<RaidenPayResponse> raidenPay(
            @Query("token") String token,
            @Query("proxy") String proxy,
            @Query("userAddr") String userAddr,
            @Query("amount") String amount,
            @Query("sysFlowNo") String sysFlowNo);

    @GET("raidenOtcWallet/newWallet/onLinelPayment")
    Observable<OnlineRaidenPayResponse> raidenPay(
            @Query("outFlowNo") String outFlowNo,
            @Query("agentCode") String agentCode,
            @Query("tokenAmount") String tokenAmount,
            @Query("customerAddr") String customerAddr,
            @Query("customerId") String customerId,
            @Query("tokenAddress") String tokenAddress,
            @Query("tokenName") String tokenName);

    @GET("raidenOtcWallet/customer/getOrderList")
    Observable<RaidenHistoryResponse> getRaidenHistory(
            @Query("token") String token,
            @Query("userAddr") String userAddr);

    @GET("raidenOtcWallet/wallet/getOrderList")
    Observable<CryptoHistoryResponse> getCryptoHistory(
            @Query("token") String token,
            @Query("userAddr") String userAddr,
            @Query("time") String time);

    @POST("raidenOtcWallet/user/login")
    Observable<LoginResponse> login(@Body RequestBody requestBody);

    @POST("raidenOtcWallet/user/save/register")
    Observable<RegisterResponse> register(@Body RequestBody requestBody);

    @POST("raidenOtcWallet/user/delete")
    Observable<LogoutResponse> logout(@Body RequestBody requestBody);

    @POST("raidenOtcWallet/trade/orderList/list")
    Observable<OTCOrderResponse> getOTCOrderList(@Body RequestBody requestBody);

    @POST("raidenOtcWallet/trade/page/list")
    Observable<OTCTradeResponse> getOTCTradeList(@Body RequestBody requestBody);

    @POST("raidenOtcWallet/user/info")
    Observable<UserInfoResponse> getUserInfo(@Body RequestBody requestBody);

    @POST("raidenOtcWallet/user/update/name")
    Observable<UpdateNameResponse> updateName(@Body RequestBody requestBody);

    @POST("raidenOtcWallet/trade/appeal/save")
    @Multipart
    Observable<ResponseBody> uploadAppealImage(@Part("orderId") RequestBody orderId, @Part MultipartBody.Part file);

    @POST("raidenOtcWallet/user/update/headImage")
    @Multipart
    Observable<ResponseBody> uploadHeadImage(@Part MultipartBody.Part file);

    @POST("raidenOtcWallet/version/get")
    Observable<UpdateAppResponse> checkAppUpdate(@Body RequestBody requestBody);
}
