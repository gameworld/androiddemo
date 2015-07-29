package com.example.practice.demo.Net;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;
import android.util.Log;

/**
 * Created by fighter on 15-7-29.
 */
public class SeaClient extends  Thread {



    public SeaClient()
    {

    }

    public void run()
    {
        AsyncHttpClient c = new AsyncHttpClient();

        try {

            WebSocket websocket = c.prepareGet("ws://192.168.1.107:8889")
                    .execute(
                            new WebSocketUpgradeHandler.Builder()
                                    .addWebSocketListener(new WebSocketTextListener() {

                                        @Override
                                        public void onMessage(String message) {
                                            System.out.println(message);
                                        }


                                        @Override
                                        public void onOpen(WebSocket websocket) {
                                            websocket.sendMessage("echo你好啊");
                                        }

                                        @Override
                                        public void onClose(WebSocket websocket) {
                                            Log.d("Demo","web socket Closed");
                                        }

                                        @Override
                                        public void onError(Throwable t) {
                                            t.printStackTrace();

                                        }
                                    }).build()).get();


        }catch(Exception e)
        {

            Log.i("Demo"," Exception "+e.getMessage());
        }
    }


}
