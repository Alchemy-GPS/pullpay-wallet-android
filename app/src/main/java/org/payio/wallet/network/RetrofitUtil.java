package org.payio.wallet.network;


import org.payio.wallet.Constants;
import org.payio.wallet.OTCApplication;
import org.payio.wallet.network.cookie.ClearableCookieJar;
import org.payio.wallet.network.cookie.PersistentCookieJar;
import org.payio.wallet.network.cookie.cache.SetCookieCache;
import org.payio.wallet.network.cookie.persistence.SharedPrefsCookiePersistor;
import org.payio.wallet.network.io.CryptoHistoryResponse;
import org.payio.wallet.network.io.LoginRequest;
import org.payio.wallet.network.io.LoginResponse;
import org.payio.wallet.network.io.LogoutRequest;
import org.payio.wallet.network.io.LogoutResponse;
import org.payio.wallet.network.io.OTCOrderRequest;
import org.payio.wallet.network.io.OTCOrderResponse;
import org.payio.wallet.network.io.OTCTradeRequest;
import org.payio.wallet.network.io.OTCTradeResponse;
import org.payio.wallet.network.io.OnlineRaidenPayRequest;
import org.payio.wallet.network.io.OnlineRaidenPayResponse;
import org.payio.wallet.network.io.RaidenHistoryResponse;
import org.payio.wallet.network.io.RaidenPayRequest;
import org.payio.wallet.network.io.RaidenPayResponse;
import org.payio.wallet.network.io.RegisterRequest;
import org.payio.wallet.network.io.RegisterResponse;
import org.payio.wallet.network.io.UpdateAppRequest;
import org.payio.wallet.network.io.UpdateAppResponse;
import org.payio.wallet.network.io.UpdateNameRequest;
import org.payio.wallet.network.io.UpdateNameResponse;
import org.payio.wallet.network.io.UploadAppealResponse;
import org.payio.wallet.network.io.UserInfoResponse;
import org.payio.wallet.network.io.WalletInfoRequest;
import org.payio.wallet.network.io.WalletInfoResponse;
import org.payio.wallet.network.upload.FileUploadObserver;
import org.payio.wallet.network.upload.UploadFileRequestBody;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author nanchen
 * @fileName RetrofitRxDemoo
 * @packageName com.nanchen.retrofitrxdemoo.util
 * @date 2016/12/12  10:38
 */

public class RetrofitUtil {
    private static final long DEFAULT_TIMEOUT = 30L;
    private static RetrofitUtil mInstance;
    private Retrofit mRetrofit;
    private ApiService mApiService;
    private static ClearableCookieJar cookieJar;

    /**
     * 私有构造方法
     */
    private RetrofitUtil() {
        OkHttpClient client = getClient();

        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.APP_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mApiService = mRetrofit.create(ApiService.class);
    }

    public static OkHttpClient getClient() {

        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(OTCApplication.APPLICATION), true);

        return new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpInterceptor())
                .build();
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }

    public static void recreate() {
        mInstance = null;
        synchronized (RetrofitUtil.class) {
            mInstance = new RetrofitUtil();
        }
    }

    private RequestBody modelToRequestBody(Object object) {
        String json = GsonUtil.objectToJson(object);

        Log.e("REQUEST_JSON == " + json);

        return RequestBody.create(MediaType.parse("application/json"), json);
    }

    /**
     * 查询更新接口
     *
     * @param subscriber
     */
    public void checkAppUpdate(UpdateAppRequest request, Observer<UpdateAppResponse> subscriber) {
        mApiService.checkAppUpdate(modelToRequestBody(request))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 雷电网络通道支付
     *
     * @param subscriber
     */
    public void raidenPay(RaidenPayRequest request, Basesubscriber<RaidenPayResponse> subscriber) {
        mApiService.raidenPay(request.getToken(), request.getProxy(), request.getUserAddr(), request.getAmount(), request.getSysFlowNo())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 雷电网络通道支付
     *
     * @param subscriber
     */
    public void raidenPay(OnlineRaidenPayRequest request, Basesubscriber<OnlineRaidenPayResponse> subscriber) {
        mApiService.raidenPay(request.getOutFlowNo(),
                request.getAgentCode(),
                request.getTokenAmount(),
                request.getCustomerAddr(),
                request.getCustomerId(),
                request.getTokenAddress(),
                request.getTokenName())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取雷电历史交易记录
     *
     * @param subscriber
     */
    public void getRaidenHistory(String token, String walletAddress, Basesubscriber<RaidenHistoryResponse> subscriber) {
        mApiService.getRaidenHistory(token, walletAddress)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取钱包所有历史交易记录
     *
     * @param subscriber
     */
    public void getCryptoHistory(String token, String walletAddress,String lastOrderTime, Basesubscriber<CryptoHistoryResponse> subscriber) {
        mApiService.getCryptoHistory(token, walletAddress,lastOrderTime)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * OTC订单列表
     *
     * @param subscriber
     */
    public void getOTCOrderList(OTCOrderRequest request, Basesubscriber<OTCOrderResponse> subscriber) {
        mApiService.getOTCOrderList(modelToRequestBody(request))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 注册
     *
     * @param subscriber
     */
    public void register(RegisterRequest request, Basesubscriber<RegisterResponse> subscriber) {
        mApiService.register(modelToRequestBody(request))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 登录
     *
     * @param subscriber
     */
    public void login(LoginRequest request, Basesubscriber<LoginResponse> subscriber) {
        mApiService.login(modelToRequestBody(request))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退出登录
     *
     * @param subscriber
     */
    public void logout(LogoutRequest request, Basesubscriber<LogoutResponse> subscriber) {
        mApiService.logout(modelToRequestBody(request))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取OTC交易信息
     *
     * @param subscriber
     */
    public void getOTCTradeList(OTCTradeRequest request, Basesubscriber<OTCTradeResponse> subscriber) {
        mApiService.getOTCTradeList(modelToRequestBody(request))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改用户昵称
     *
     * @param subscriber
     */
    public void updateName(UpdateNameRequest request, Basesubscriber<UpdateNameResponse> subscriber) {
        mApiService.updateName(modelToRequestBody(request))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取用户信息
     *
     * @param subscriber
     */
    public void getUserInfo(Basesubscriber<UserInfoResponse> subscriber) {
        mApiService.getUserInfo(RequestBody.create(MediaType.parse("application/json"), ""))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 雷电网络通道获取余额接口
     *
     * @param subscriber
     */
    public void getWalletBalnce(WalletInfoRequest request, Basesubscriber<WalletInfoResponse> subscriber) {
        mApiService.getWalletBalnce(request.userAddr, request.outFlowNo, request.agentCode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 雷电网络通道获取余额接口
     *
     * @param subscriber
     */
    public void getWalletBalnce(String walletAddress, Basesubscriber<WalletInfoResponse> subscriber) {
        mApiService.getWalletBalnce(walletAddress)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 上传申诉图片和订单号
     *
     * @param subscriber
     */
    public void uploadAppealImage(File file, String orderId, FileUploadObserver<ResponseBody> subscriber) {
        RequestBody orderIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), orderId);

        UploadFileRequestBody mFileRequestBody = new UploadFileRequestBody(file, subscriber);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), mFileRequestBody);

        mApiService.uploadAppealImage(orderIdBody, filePart)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 上传头像
     *
     * @param subscriber
     */
    public void uploadHeadImage(File file, FileUploadObserver<ResponseBody> subscriber) {

        UploadFileRequestBody mFileRequestBody = new UploadFileRequestBody(file, subscriber);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), mFileRequestBody);

        mApiService.uploadHeadImage(filePart)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
