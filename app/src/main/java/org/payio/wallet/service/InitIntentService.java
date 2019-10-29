package org.payio.wallet.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.toast.SmartToast;

import org.payio.wallet.OTCApplication;
import org.payio.wallet.database.DaoManager;

import cn.jpush.android.api.JPushInterface;

public class InitIntentService extends IntentService {

    private static final String ACTION_INIT = "org.payio.wallet.service.action.INIT";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public InitIntentService() {
        super("InitIntentService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitIntentService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && ACTION_INIT.equals(intent.getAction())) {
            DaoManager.getInstance().init(this);
            SmartShow.init(OTCApplication.APPLICATION);
            SmartToast.setting().dismissOnLeave(true);
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
            JPushInterface.setLatestNotificationNumber(this, 1);
        }
    }
}
