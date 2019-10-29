package org.payio.wallet.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import org.payio.wallet.Constants;
import org.payio.wallet.model.event.PayMessageEvent;
import org.payio.wallet.mvp.main.MainActivity;
import org.payio.wallet.mvp.otc_web.OTCWebActivity;
import org.payio.wallet.network.io.PayServerMessage;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.utils.AppUtil;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;

import cn.jpush.android.api.JPushInterface;

import static org.payio.wallet.params.TransParam.LAUNCH_RXTRA_BUNDLE;
import static org.payio.wallet.params.TransParam.OTC_PAGE_URL;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                String message_type = (String) GsonUtil.getJsonValue(extra, "message_type");

                Log.e("接收到自定义消息: " + message);

                if (!TextUtils.isEmpty(message_type) && !TextUtils.isEmpty(message)) {
                    if (TextUtils.equals(message_type, "EXCHANGE_RESULT")) {
                        processExchangeMessage(context, message);
                    }
                }
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                String content = bundle.getString(JPushInterface.EXTRA_EXTRA);
                String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
                String title = bundle.getString(JPushInterface.EXTRA_TITLE);

                Log.e("用户收到了通知 : ALERT == " + alert + " TITLE == " + title + " 全部内容 == " + content);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d("用户点击打开了通知");
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                String message_type = (String) GsonUtil.getJsonValue(extra, "message_type");
                String message_url = (String) GsonUtil.getJsonValue(extra, "message_url");
                if (!TextUtils.isEmpty(message_type) && !TextUtils.isEmpty(message_url)) {
                    if (TextUtils.equals(message_type, "ORDER_DETAIL")) {
                        processAlert(context, message_url);
                    }
                }
            }
        }
    }

    private void processAlert(Context context, String message_url) {
        if (AppUtil.isAppRunning(context, AppUtil.getPackageName(context))) {
            Intent mainIntent = new Intent(context, MainActivity.class);
            //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
            //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
            //如果Task栈不存在MainActivity实例，则在栈顶创建
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent detailIntent = new Intent(context, OTCWebActivity.class);
            detailIntent.putExtra(OTC_PAGE_URL, Constants.APP_HOST + "raidenOtcWallet" + message_url);
            Intent[] intents = {mainIntent, detailIntent};
            context.startActivities(intents);
        } else {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(AppUtil.getPackageName(context));
            if (launchIntent != null) {
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Bundle bundle = new Bundle();
                bundle.putString(OTC_PAGE_URL, Constants.APP_HOST + "raidenOtcWallet" + message_url);
                launchIntent.putExtra(LAUNCH_RXTRA_BUNDLE, bundle);
                context.startActivity(launchIntent);
            }
        }
    }

    private void processExchangeMessage(Context context, String message) {
        PayServerMessage payServerMessage = (PayServerMessage) GsonUtil.jsonToBean(message, PayServerMessage.class);
        if (payServerMessage != null && payServerMessage.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
            EventBusUtil.post(PayMessageEvent.getInstance());
        }
    }
}
