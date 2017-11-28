package com.szreach.ybolotv.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.szreach.ybolotv.activities.AutoVodPlayActivity;
import com.szreach.ybolotv.activities.MainActivity;
import com.szreach.ybolotv.jsonMsg.ResultItem;
import com.szreach.ybolotv.utils.Constant;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adams.Tsui on 2017/10/25 0025.
 */

public class AutoPlayVodService extends Service {

    private static final ObjectMapper mapper;
    private Handler mHandler = new Handler();
    private WebSocket webSocketObj;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(AutoPlayVodService.class.getName(), "=====进入点播Service");

        new WebSockectThread().start();

    }

    /**
     * 连接Websocket服务端
     */
    private void connect() {
        String websocketPort = "8088";
        String websocketUrl = "ws://" + getServerHost() + ":" + websocketPort + "/liveandroidsocket?type=VIDEO";
        AsyncHttpClient.getDefaultInstance().websocket(websocketUrl, websocketPort, new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, WebSocket webSocket) {
                if (ex != null) {
                    Log.e(AutoPlayVodService.class.getName(), "=====点播Websocket连接出错");
                    return;
                }

                webSocketObj = webSocket;

                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String retStr) {
                        receiverWebSocket(retStr);
                    }
                });
            }
        });
    }

    /**
     * 处理服务端推送
     * @param retStr
     */
    private void receiverWebSocket(String retStr) {
        Log.e("YBolo发送点播信息:", retStr);
        Intent actIntent;
        if("CLOSE".equals(retStr)) {
            actIntent = new Intent(getBaseContext(), MainActivity.class);
        } else {
            actIntent = new Intent(getBaseContext(), AutoVodPlayActivity.class);
            try {
                Map<String, Object> result = mapper.readValue(retStr, new TypeReference<HashMap<String, Object>>() {});
                if(result != null && result.size() > 0) {
                    Object vodPath = result.get("httpAddrH00");
                    if(vodPath == null || (vodPath != null && vodPath.toString().length() == 0)) {
                        vodPath = result.get("httpAddrL00");
                    }
                    actIntent.putExtra("vodPath", vodPath.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        actIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(actIntent);
    }

    /**
     * 检测Websocket连接是否断开，如果断开，重连
     */
    private class WebSockectThread extends Thread {
        @Override
        public void run() {
            if(webSocketObj != null) {
                Log.e(AutoPlayVodService.class.getName(), "=======================点播websocket连接状态：：：" + webSocketObj.isOpen());
            }

            if(webSocketObj == null || (webSocketObj != null && !webSocketObj.isOpen())) {
                connect();
            }
            // 一分钟检测一次
            mHandler.postDelayed(this, 1000 * 30);
        }
    }

    private String getServerHost() {
        String dataServerAdress = Constant.DataServerAdress;
        String serverHost = "";
        if(dataServerAdress.indexOf("http://") >= 0) {
            serverHost = dataServerAdress.substring("http://".length(), dataServerAdress.length());
        } else if(dataServerAdress.indexOf("https://") >= 0) {
            serverHost = dataServerAdress.substring("https://".length(), dataServerAdress.length());
        }
        return serverHost;
    }

}
