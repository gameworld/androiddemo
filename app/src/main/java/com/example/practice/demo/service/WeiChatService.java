package com.example.practice.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.practice.demo.Net.SeaClient;


/**
 连接sina sae 的websocket 服务，通过该服务实现
 微信消息的通信
 */

public class WeiChatService extends Service {

    private static String logTag="WeichatService";
    private SeaClient saeClient=null;

    public WeiChatService() {
    }


    @Override
    public void onCreate()
    {
        Log.i(logTag," WeichatService service started");
        saeClient=new SeaClient();
        saeClient.start();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
