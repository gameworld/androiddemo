package com.example.practice.demo.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NetService extends IntentService {

    public NetService()
    {
        super("Net Service");
    }

    protected void onHandleIntent(Intent intent) {
        Log.d("Demo","intent");
    }

    @Override
    public void onDestroy() {
        Log.i("Demo", "service destory");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.i("Demo", "service started");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Demo","bind");
        // TODO: Return the communication channel to the service.
        return  null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Demo","start command");
        return START_STICKY;
    }
}
