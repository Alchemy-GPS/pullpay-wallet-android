package org.payio.wallet;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.toast.SmartToast;

import org.payio.wallet.database.DaoManager;
import org.payio.wallet.service.InitIntentService;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.Log;

import cn.jpush.android.api.JPushInterface;

public class OTCApplication extends MultiDexApplication {
    public static OTCApplication APPLICATION;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION = this;

        InitIntentService.start(this);

//        Log.e("OTCApplication start time == "+ CommonUtil.formatDateTimeMillis(System.currentTimeMillis()));

//        DaoManager.getInstance().init(this);
//        SmartShow.init(this);
//        SmartToast.setting().dismissOnLeave(true);
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        JPushInterface.setLatestNotificationNumber(this, 1);

//        Log.e("OTCApplication end time == "+ CommonUtil.formatDateTimeMillis(System.currentTimeMillis()));

    }
}
