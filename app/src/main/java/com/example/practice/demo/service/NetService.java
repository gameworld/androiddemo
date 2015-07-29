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
 * 尚未完全实现
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
        this.readbuffer=ByteBuffer.allocate(4096);
        this.writebuffer=ByteBuffer.allocate(4096);
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


                selector=Selector.open();
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
                                if(chnl.read(this.readbuffer)==0) {
                                    this.callback.OnClose();
                                }
                                else
                                {
                                    readbuffer.flip();
                                    byte [] data=new byte[readbuffer.limit()];
                                    readbuffer.get(data);
                                    this.callback.OnData(data);
                                }
                            }

                            if(key.isWritable())
                            {
                                chnl.write(this.writebuffer);
                                this.writebuffer.compact();
                                if(this.writebuffer.remaining()>0)
                                {
                                    key.interestOps(SelectionKey.OP_WRITE|SelectionKey.OP_READ);
                                }
                                else
                                {
                                    key.interestOps(SelectionKey.OP_READ);
                                }
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
    public void Send(byte [] data)
    {
        ByteBuffer buffer=ByteBuffer.wrap(data);
        try {
            this.sock.getChannel().write(buffer);
            buffer.compact();
            this.writebuffer.put(buffer);
        }
        catch(Exception e)
        {
                Log.d("Demo",e.getMessage());
        }
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
    private  Selector selector;

}
