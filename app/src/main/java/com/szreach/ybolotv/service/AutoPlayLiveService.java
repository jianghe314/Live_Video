package com.szreach.ybolotv.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.szreach.ybolotv.activities.AutoLivePlayActivity;
import com.szreach.ybolotv.activities.MainActivity;
import com.szreach.ybolotv.beans.LiveBean;
import com.szreach.ybolotv.utils.Constant;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Adams.Tsui on 2017/10/25 0025.
 */

public class AutoPlayLiveService extends Service {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        String websocketPort = "8088";
        String websocketUrl = "ws://" + getServerHost() + ":" + websocketPort + "/liveandroidsocket";
        AsyncHttpClient.getDefaultInstance().websocket(websocketUrl, websocketPort, new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, WebSocket webSocket) {
                if (ex != null) {
                    return;
                }

                webSocket.send(UUID.randomUUID().toString());
                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String retStr) {
//                        Log.e("YBolo发送信息:", retStr);
                        Intent actIntent = null;
                        if("CLOSE".equals(retStr)) {
                            actIntent = new Intent(getBaseContext(), MainActivity.class);
                        } else {
                            LiveBean live = null;
                            actIntent = new Intent(getBaseContext(), AutoLivePlayActivity.class);
                            try {
                                live = mapper.readValue(retStr, new TypeReference<LiveBean>() {});
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(live != null) {
//                                Log.e(":~~~Live Info:", live.getRtmpAddress());
                                actIntent.putExtra("livePath", live.getRtmpAddress());
                            }
                        }
                        actIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(actIntent);
                    }
                });

                webSocket.setDataCallback(new DataCallback() {
                    @Override
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {

                    }
                });
            }
        });
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
