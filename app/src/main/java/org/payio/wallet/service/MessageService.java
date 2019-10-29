package org.payio.wallet.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.android.process.aidl.IProcessService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.payio.wallet.Constants;
import org.payio.wallet.R;
import org.payio.wallet.model.event.PayMessageEvent;
import org.payio.wallet.model.event.WebsocketEvent;
import org.payio.wallet.mvp.main.MainActivity;
import org.payio.wallet.mvp.otc_web.OTCWebActivity;
import org.payio.wallet.network.io.PayServerMessage;
import org.payio.wallet.network.io.WebsocketServerMessage;
import org.payio.wallet.network.websocket.WsManager;
import org.payio.wallet.network.websocket.WsStatus;
import org.payio.wallet.params.TransParam;
import org.payio.wallet.receiver.NotificationReceiver;
import org.payio.wallet.utils.CommonUtil;
import org.payio.wallet.utils.EventBusUtil;
import org.payio.wallet.utils.GsonUtil;
import org.payio.wallet.utils.Log;

public class MessageService extends Service implements WsManager.OnTextMessageReceivedListener {
    String TAG = "MessageService";

    private LocalBinder mLocalBinder;

    private LocalServiceConnection mLocalServiceConn;
    private Handler mHandler = new Handler();
    private static Notification.Builder builder;
    private static NotificationManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBinder = new LocalBinder();
        if (mLocalServiceConn == null) {
            mLocalServiceConn = new LocalServiceConnection();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG + " onStartCommand");

        /*
        EventBusUtil.register(this);

        WsManager.getInstance().setOnTextMessageReceivedListener(this);

        WsStatus mStatus = WsManager.getInstance().getStatus();

        if (mStatus == null || mStatus.equals(WsStatus.CONNECT_FAIL)) {
            if (!TextUtils.isEmpty(CommonUtil.getUserId())) {
                WsManager.getInstance().init(CommonUtil.getUserId());
            }
        }
        //  绑定远程服务
        bindService(new Intent(this, ProtectService.class), mLocalServiceConn, Context.BIND_IMPORTANT);
        */
        return START_STICKY;
    }

    @Override
    public void onTextMessageReceived(String content) {
        Log.i("onTextMessage == " + content);

        WebsocketServerMessage serverMessage = (WebsocketServerMessage) GsonUtil.jsonToBean(content, WebsocketServerMessage.class);
        PayServerMessage payServerMessage = (PayServerMessage) GsonUtil.jsonToBean(content, PayServerMessage.class);

        if (serverMessage != null && serverMessage.getData() != null && !TextUtils.isEmpty(serverMessage.getMessageType())) {
            WebsocketServerMessage.Message message = serverMessage.getData();

            String orderStatus = message.getOrderNo().concat(message.getOrderStatus());

            if (!orderStatus.equals(CommonUtil.getOrderStatus())) {
                CommonUtil.setOrderStatus(orderStatus);

                showNotifictionIcon(this, message.getMessageTitle(), message.getOrderStatusMsg(), message.getUrl());
            }
        } else if (payServerMessage != null && payServerMessage.getReturnCode().equals(TransParam.SUCCESS_CODE)) {
            EventBusUtil.post(PayMessageEvent.getInstance());
        }
    }

    public static void showNotifictionIcon(Context context, String title, String content, String extra) {
        if (builder == null) {
            builder = new Notification.Builder(context);
        }
        if (manager == null) {
            manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        }
//        Intent intent = new Intent(context, OTCWebActivity.class);//将要跳转的界面
//        intent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + "raidenOtcWallet" + extra + "&type=2");
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        broadcastIntent.putExtra(TransParam.OTC_PAGE_URL, Constants.APP_HOST + "raidenOtcWallet" + extra + "&type=2");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(context.getPackageName());
            NotificationChannel channel = new NotificationChannel(context.getPackageName(), "PayIO", NotificationManager.IMPORTANCE_DEFAULT);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        builder.setAutoCancel(true);//点击后消失
        builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知栏消息标题的头像
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        builder.setContentTitle(title);
        builder.setContentText(content);
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        builder.setContentIntent(pendingIntent);

        if (manager != null) {

            int id = CommonUtil.getTodayCurrentTime();

            manager.notify(id, builder.build());
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND) //在ui线程执行
    public void onWebsocketEvent(WebsocketEvent event) {
        if (event != null) {
            if (event.connect) {
                if (TextUtils.isEmpty(CommonUtil.getUserId())) {
                    Log.e("userId 为空");
                    return;
                }

                if (event.code == WsManager.CHECK_WEBSOCKET) {
                    WsManager.getInstance().checkWebsocket();
                } else if (event.code == WsManager.CLIENT_INIT_WEBSOCKET) {
                    WsManager.getInstance().init(CommonUtil.getUserId());
                }

            } else {
                WsManager.getInstance().disconnect(event.code);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(mLocalServiceConn);
        EventBusUtil.unregister(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    /**
     * 通过AIDL实现进程间通信
     */
    class LocalBinder extends IProcessService.Stub {
        @Override
        public String getServiceName() throws RemoteException {
            return "MessageService";
        }
    }

    /**
     * 连接远程服务
     */
    class LocalServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                // 与远程服务通信
                IProcessService process = IProcessService.Stub.asInterface(service);
                Log.i("连接" + process.getServiceName() + "服务成功");
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e("连接 ProtectService 失败");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // RemoteException连接过程出现的异常，才会回调,unbind不会回调
            // 监测，远程服务已经死掉，则重启远程服务
            Log.i("ProtectService 远程服务挂掉了,远程服务被杀死");

            // 启动远程服务
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                startService(new Intent(MessageService.this, ProtectService.class));
            }

            // 绑定远程服务
            bindService(new Intent(MessageService.this, ProtectService.class), mLocalServiceConn, Context.BIND_IMPORTANT);
        }
    }
}
