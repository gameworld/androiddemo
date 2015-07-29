package com.example.practice.demo.Net;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;
import android.util.Log;

import java.util.concurrent.Future;

/**
 * Created by fighter on 15-7-29.
 */
public class SeaClient extends  Thread {

    private static String wsChannelUrl="http://1.xmsweixin.sinaapp.com/chnl";
    private String wsAddr="";


    public SeaClient()
    {

    }

    public void run()
    {
        try
        {
            AsyncHttpClient client=new AsyncHttpClient();
            Future<Response> f=client.prepareGet(wsChannelUrl).execute();
            wsAddr=f.get().getResponseBody();
            Log.d("Demo","web socket address:"+wsAddr);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        AsyncHttpClient c = new AsyncHttpClient();


        try {

            WebSocket websocket = c.prepareGet(wsAddr)
                    .execute(
                            new WebSocketUpgradeHandler.Builder()
                                    .addWebSocketListener(new WebSocketTextListener() {

                                        @Override
                                        public void onMessage(String message) {
                                            System.out.println(message);
                                            Log.d("Demo", "recv message"+message);
                                        }


                                        @Override
                                        public void onOpen(WebSocket websocket) {
                                            Log.d("Demo","web socket connect succ");
                                            websocket.sendMessage("echo你好啊");
                                        }

                                        @Override
                                        public void onClose(WebSocket websocket) {
                                            Log.d("Demo", "web socket Closed");
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
