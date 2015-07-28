package com.example.practice.demo.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;
import com.example.practice.demo.activity.MainActivity;
import android.support.v4.app.NotificationCompat;


import com.example.practice.demo.R;

public class NetService extends Service {


    public NetService()
    {
        super();
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
        Log.i("Demo", "start command");
        return START_STICKY;
    }



    // Send result to activity using ResultReceiver

}
