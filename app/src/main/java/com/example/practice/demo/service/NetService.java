package com.example.practice.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NetService extends Service {
    public NetService() {
    }

    @Override
    public void onDestroy() {
        Log.i("Demo", "stop service");
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.i("Demo", "start service");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Demo","bind");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
