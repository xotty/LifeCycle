/**
 * 演示Service 的生命周期
 * 一、启动Service
 * 1）startService
 * 2）bindService
 * 二、停止Service
 * 1）stopService
 * 2）unBindService
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:Intent DEMO
 * <br/>Date:June，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.servicelifecycle;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xottys.lifecycle.R;


public class ServiceActivity extends Activity {
    final private String TAG = "ServiceLifecycle";
    private MyBindService myBindService;
    public TextView tv;
    boolean isServiceStop;
    private ServiceConnection conn = new ServiceConnection() {
        //每个宿主与service第一次Bind成功时会回调该方法，同一宿主第二次及以后bind同一Sercice时不会再回调本方法，除非unBindService后再次Bind
        @Override
        public void onServiceConnected(ComponentName name
                , IBinder service) {
            isServiceStop=false;
            //获取绑定Service传递过来的IBinder对象，通过这个IBinder对象，实现宿主和Service的交互。
            MyBindService.MyBinder myBinder = (MyBindService.MyBinder) service;

            //通过传递过来的IBinder对象中的方法来获取Service的对象以便调用其公共方法
            myBindService = myBinder.getMyBindService();

            tv.append("MainActivity-->onServiceConnected\n");
            Log.i(TAG, "MainActivity-->onServiceConnected");
        }

        //在与服务的连接意外中断时（例如当服务崩溃或被终止时）调用该方法。当客户端取消绑定时，系统“绝对不会”调用该方法。
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceStop=true;
            Log.i(TAG, "*******MainActivity onServiceDisconnected Thread：" + Thread.currentThread().getName());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Log.i(TAG, "*******MainActivity --> onCreate, Thread: " + Thread.currentThread().getName());
        final Button bt1 = (Button) findViewById(R.id.bt1);
        final Button bt2 = (Button) findViewById(R.id.bt2);
        final Button bt3 = (Button) findViewById(R.id.bt3);
        final Button bt4 = (Button) findViewById(R.id.bt4);

        bt1.setBackgroundColor(0xbd292f34);
        bt1.setTextColor(0xFFFFFFFF);
        bt2.setBackgroundColor(0xbd292f34);
        bt2.setTextColor(0xFFFFFFFF);
        bt3.setBackgroundColor(0xbd292f34);
        bt3.setTextColor(0xFFFFFFFF);
        tv = (TextView) findViewById(R.id.tv);
        MyStartService.register_activity(ServiceActivity.this);
        MyBindService.register_activity(ServiceActivity.this);

        //Service启动和消息传递
        bt1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, MyStartService.class);
                if (bt1.getText().equals("Start\n Service")) {
                    Log.i(TAG, "*******MainActivity执行startService");
                    bt1.setText("Stop\n Service");
                    intent.putExtra("city", "上海");
                    intent.putExtra("GDP", 2.67f);
                    //启动Service，该方法不能获取Service返回数据
                    startService(intent);
                    tv.setText("MainActivity--->Start Service\n");
                } else {
                    Log.i(TAG, "*******MainActivity执行stoptService");
                    tv.append("MainActivity--->Stop Service\n");
                    bt1.setText("Start\n Service");
                    //停止Service
                    stopService(intent);

                }
            }
        });

        //Service绑定和消息传递演
        bt2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                final Intent intent = new Intent(ServiceActivity.this, MyBindService.class);

                if (bt2.getText().equals("Bind\n Service")) {
                    bt2.setText("Unbind\n Service");
                    tv.setText("MainActivity-->Bind Service\n");

                    //向Service 传递数据的一种方法，只能第一次bindService时使用
                    intent.putExtra("city", "广州(Bind Intent)");
                    intent.putExtra("GDP", 2.00f);

                    //绑定指定Serivce
                    //flags则是指定绑定时是否自动创建Service:0代表不自动创建、BIND_AUTO_CREATE则代表自动创建。
                    bindService(intent, conn, Service.BIND_AUTO_CREATE);
                    Log.i(TAG, "*******MainActivity 执行 bindService");
                } else {
                    bt2.setText("Bind\n Service");
                    tv.append("MainActivity-->unBindService\n");
                    // 解除绑定Serivce
                    unbindService(conn);
                    Log.i(TAG, "*******MainActivity 执行 unbindService");
                }
            }
        });


        //MyBindService被Start方式启动后将一直存在，直到所有宿主Unbind然后又被显式Stop，
        //在这之前任何宿主可以再次Bind，此时MyBindService的onRebind或onBind会被调用（取决于onUnbind的返回值）
        //startService和bindService先后顺序无论怎样，效果是一样的，
        bt3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, MyBindService.class);

                if (bt3.getText().equals("Start\nBindService")) {
                    tv.setText("MainActivity-->Start BindService\n");
                    Log.i(TAG, "*******MainActivity执行startService---MyBindService");
                    bt3.setText("Stop\nBindService");

                    intent.putExtra("city", "南京(Start Intent)");
                    intent.putExtra("GDP", 1.87f);

                    //启动Service，该方法不能获取Service返回数据
                    startService(intent);
                } else {
                    tv.append("MainActivity-->Stop BindService\n");
                    Log.i(TAG, "*******MainActivity执行stoptService---MyBindService");
                    bt3.setText("Start\nBindService");
                    //停止Service
                    stopService(intent);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "*******MainActivity --> onDestroy, Thread: " + Thread.currentThread().getName());
    }
}
