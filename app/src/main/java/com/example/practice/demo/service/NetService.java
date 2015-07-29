package com.example.practice.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.net.SocketFactory;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class NetService extends Service {


    private NetThread nthread=null;

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
        nthread=new NetThread("10.2.12.190",9000);
        nthread.start();
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

}

/**
 * 网络收发线程
 */

class NetThread extends Thread
{

    public interface NetCallBack
    {
        public void OnData(byte [] data);
        public void OnClose();
        public void OnConnect();
        public void OnError();

    }

    public NetThread(String ip,int port)
    {
        this.ip=ip;
        this.port=port;
    }

    @Override
    public void run()
    {
        Log.i("Demo","thread started");
        try
        {
            sock= SocketFactory.getDefault().createSocket(ip,port);
            if(sock.isConnected())
            {
                Log.i("Demo", "socket connected");
                this.callback.OnConnect();


                Selector selector=Selector.open();
                sock.getChannel().register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                sock.getChannel().configureBlocking(false);

                while(true) {

                    int ret = selector.select();
                    if (ret > 0) {

                        Set<SelectionKey> set=selector.selectedKeys();

                        Iterator<SelectionKey> keyIterator = set.iterator();
                        while(keyIterator.hasNext()) {
                            SelectionKey key=keyIterator.next();

                            SocketChannel chnl=(SocketChannel)key.channel();
                            if(key.isReadable())
                            {
                                chnl.read(this.readbuffer);
                            }

                            if(key.isWritable())
                            {
                                chnl.write(this.writebuffer);
                            }

                            keyIterator.remove();
                        }
                    } else {
                        continue;
                    }

                }

            }
            else
            {
                this.callback.OnError();
                Log.i("Demo", "socket connect fail");
            }
        }
        catch (Exception e)
        {
            Log.i("Demo","exception :"+e.getMessage());
            this.callback.OnError();
        }
    }

/**
 */
    public void Send(ByteBuffer buffer)
    {
       // this.writebuffer.put(buffer);
        //this.sock.getChannel().register();

    }


    public void SetCallBack(NetCallBack callback)
    {
        this.callback=callback;
    }

    private Socket sock=null;


    private  ByteBuffer writebuffer;
    private  ByteBuffer readbuffer;

    private String ip;
    private int port;

    private NetCallBack callback;

}
