/**
 * 仅用Bind方法启动的Service,仅当与另一个应用组件绑定时，该Service才会运行。 多个组件可以同时绑定到该服务，
 * 但全部宿主(如Activity)解除绑定后，该服务即会被销毁；
 * 1)onCreate
 * 2)onBind
 * 3)onServiceConnected
 *   onServiceConnected
 *   .......
 * 4)onUnbind(全部绑定解除时才调用，全部宿主销毁会自动解除绑定)
 * 5)onDestroy
 *
 * 启动绑定
 * onCreate() —> onStartCommand() —>  onBind() -> onServiceConnected -> onUnbind -> onServiceConnected -> onRebind
 * 绑定启动
 * onCreate() —> onBind() -> onServiceConnected -> onStartCommand() -> onUnbind ->  onServiceConnected -> onRebind
 *
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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyBindService extends Service {
    final private String TAG = "ServiceLifecycle";

    private static ServiceActivity sActivity;

    static public void register_activity(Context context) {
        sActivity = (ServiceActivity)context;
    }

    //把Binder类的对象返回给客户端
    //Service第一次被绑定时会回调该方法且只会被回调一次，多次调用bindService()方法并不会导致多次回调该方法
    @Override
    public IBinder onBind(Intent intent) {
        sActivity.tv.append("MyBindtService-->onBind\n");
        Log.i(TAG, "*******MyBindService-->onBind, Thread: " + Thread.currentThread().getName());

        //生成所返回的对象
        MyBinder myBinder = new MyBinder();
        return myBinder;
    }

    //Service被创建时回调该方法，即第一次bindSrvice时才会回调该方法，整个生命周期中只会被回调一次
    @Override
    public void onCreate() {
        super.onCreate();
        sActivity.tv.append("MyBindService-->onCreate\n");
        Log.i(TAG, "*******MyBindService --> onCreate, Thread: " + Thread.currentThread().getName());
    }

    //最后一个与Service连接的宿主与Service的断开时会调该方法，通常通过宿主Activity退出或显式调用unbindService()方法来启动
    @Override
    public boolean onUnbind(Intent intent) {
        sActivity.tv.append("MyBindService-->onUnbind\n");
        Log.i(TAG, "*******MyBindService-->onUnbind, from:" + intent.getStringExtra("from"));
        return true;   //true：满足条件时onRebind()被调用  false：直接进入onServiceConnected
    }

    //如果服务一直在后台（通常是用StartService启动后再Bind），将已经Bind的Service全部unBind后再次调用bindService()时，会回调本方法
    @Override
    public void onRebind(Intent intent) {
        sActivity.tv.append("MyBindService-->onRebind\n");
        Log.i(TAG, "*******MyBindService-->onRebind, from:" + intent.getStringExtra("from"));

    }

    //BindService中该方法可选，如果用Start方式启动则有用，否则可以省略
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sActivity.tv.append("MyBindService-->onStartCommand\n");
        Log.i(TAG, "*******MyBindService-->onStartCommand, startId: " + startId + ", Thread: " + Thread.currentThread().getName());

        return START_NOT_STICKY;
    }

    //Service被关闭之前回调该方法。
    @Override
    public void onDestroy() {
        super.onDestroy();
        sActivity.tv.append("MyBindService-->onDestroy\n");
        Log.i(TAG, "*******MyBindService --> onDestroy, Thread: " + Thread.currentThread().getName());

    }

    //用于返回给客户端即Activity使用，提供数据交换的接口
    //Service 要完成的任务通常放在这里
    public class MyBinder extends Binder {

        //返回当前对象MyBindService,这样我们就可在客户端调用Service的公共方法了
        public MyBindService getMyBindService() {
            return MyBindService.this;
        }

    }

}
