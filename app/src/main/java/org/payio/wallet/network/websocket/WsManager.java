package org.payio.wallet.network.websocket;

import android.os.Handler;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.payio.wallet.Constants;
import org.payio.wallet.utils.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WsManager implements WebsocketProxy {
    private static WsManager mInstance;
    private final String TAG = "WsManager == ";

    private static final int FRAME_QUEUE_SIZE = 5;
    public static final int CHECK_WEBSOCKET = 4000;
    public static final int CLIENT_CLOSE_WEBSOCKET = 4001;
    public static final int CLIENT_INIT_WEBSOCKET = 4002;
    private static final int CONNECT_TIMEOUT = 5000;
    private int reconnectCount = 0;//重连次数
    private long minInterval = 3000;//重连最小时间间隔
    private long maxInterval = 60000;//重连最大时间间隔

    private WsStatus mStatus;
    private WebSocket mWebSocket;
    private EchoWebSocketListener mWebSocketListener;
    private WebSocketFactory factory;
    private Runnable mReconnectTask = new Runnable() {
        @Override
        public void run() {
            recreateWebsocket();
        }
    };
    private Handler mHandler = new Handler();
    private String userId;

    private WsManager() {}

    public static WsManager getInstance() {
        if (mInstance == null) {
            synchronized (WsManager.class) {
                if (mInstance == null) {
                    mInstance = new WsManager();
                }
            }
        }
        return mInstance;
    }

    public void init(String userId) {
        this.userId = userId;

        if (mStatus != null && mStatus.equals(WsStatus.CONNECT_SUCCESS)) return;

        createWebsocket();
    }

    private void createWebsocket() {

        if (factory == null) {
            factory = new WebSocketFactory();
        }

        if (mWebSocketListener == null) {
            mWebSocketListener = new EchoWebSocketListener(this);
        }

        try {
            mWebSocket = factory.createSocket(Constants.WS_HOST, CONNECT_TIMEOUT);

            mWebSocket.addHeader("userid", userId);

            mWebSocket.addListener(mWebSocketListener);

            mWebSocket.setPingInterval(10000);

            mWebSocket.setFrameQueueSize(FRAME_QUEUE_SIZE);//设置帧队列最大值为5

            mWebSocket.setMissingCloseFrameAllowed(false);//设置不允许服务端关闭连接却未发送关闭帧

            mWebSocket.connectAsynchronously();

            setStatus(WsStatus.CONNECTING);

            Log.i(TAG + "WebSocket created");
        } catch (IOException e) {
            e.printStackTrace();
            mWebSocket = null;
        }
    }

    private void recreateWebsocket() {
        if (mWebSocket == null) {
            createWebsocket();
        }
    }

    public void reconnect() {
        if (mWebSocket == null || (!mWebSocket.isOpen() && getStatus() != WsStatus.CONNECTING)) {//当前连接断开了且不是正在重连状态

            reconnectCount++;
            setStatus(WsStatus.CONNECTING);

            long reconnectTime = minInterval;
            if (reconnectCount > 3) {
                long temp = minInterval * (reconnectCount - 2);
                reconnectTime = temp > maxInterval ? maxInterval : temp;
            }

            Log.i("准备开始第" + reconnectCount + "次重连,重连间隔 : " + reconnectTime + " -- url:" + Constants.WS_HOST);
            mHandler.postDelayed(mReconnectTask, reconnectTime);
        }
    }

    public void checkWebsocket() {
        if (mWebSocket == null) {
            createWebsocket();
        } else {
            if (!mWebSocket.isOpen()) {
                reconnect();
            }
        }
    }

    private void cancelReconnect() {
        reconnectCount = 0;
        mHandler.removeCallbacks(mReconnectTask);
    }

    public void sendMessage(String message) {
        if (mStatus != null && mStatus.equals(WsStatus.CONNECT_SUCCESS)) {
            mWebSocket.sendText(message);
        }
    }

    public void disconnect(int closeCode, String message) {
        if (mWebSocket != null)
            mWebSocket.disconnect(closeCode, message);
    }

    public void disconnect(int closeCode) {
        if (mWebSocket != null)
            mWebSocket.disconnect(closeCode);
    }

    private void setStatus(WsStatus status) {
        this.mStatus = status;
    }

    public WsStatus getStatus() {
        return mStatus;
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
        int clientCloseCode = clientCloseFrame.getCloseCode();
        Log.i(TAG + "onDisconnected :: ClientCloseCode == " + clientCloseCode);

        mWebSocket = null;

        setStatus(WsStatus.CONNECT_FAIL);

        if (clientCloseCode != CLIENT_CLOSE_WEBSOCKET) {
            Log.i(TAG + "Reconnect");
            reconnect();
        }
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) {
        if (listener != null) {
            listener.onTextMessageReceived(text);
        }
    }

    @Override
    public void onConnectError(WebSocket websocket, WebSocketException exception) {
        exception.printStackTrace();
        Log.i(TAG + "onConnectError");
        mWebSocket = null;
        setStatus(WsStatus.CONNECT_FAIL);
        reconnect();//连接错误的时候调用重连方法
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
        Log.i(TAG + "onConnected");
        setStatus(WsStatus.CONNECT_SUCCESS);
        cancelReconnect();
    }

    public interface OnTextMessageReceivedListener {
        void onTextMessageReceived(String message);
    }

    private OnTextMessageReceivedListener listener;

    public void setOnTextMessageReceivedListener(OnTextMessageReceivedListener listener) {
        this.listener = listener;
    }
}
