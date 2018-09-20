package org.xottys.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;


import org.xottys.lifecycle.activitylifecycle.ActivityLifecycle.Activity1;
import org.xottys.lifecycle.activitylifecycle.ActivityTaskMode.StandardActivity;
import org.xottys.lifecycle.fragmentlifecycle.FragmentBackStackActivity;
import org.xottys.lifecycle.fragmentlifecycle.FragmentShowHideActivity;
import org.xottys.lifecycle.fragmentlifecycle.FragmentSimpleActivity;
import org.xottys.lifecycle.fragmentlifecycle.FragmentTransactionActivity;
import org.xottys.lifecycle.fragmentlifecycle.FragmentViewPagerActivity;
import org.xottys.lifecycle.servicelifecycle.ServiceActivity;

import java.lang.ref.WeakReference;

public class MainActivity extends Activity {
    private final String TAG = "UserInterface";
    private Button bt1, bt2, bt3, bt4, bt5, bt6;
    private TextView tv;
    private Intent intent;
    StringBuilder txvString=new StringBuilder();
    MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app=MyApplication.getMyApplication();
        txvString.append(app.appStatus);
        txvString.append("\n");
        app.mHandler = new MainHandler(this);
        Log.d(TAG, "MainActivity-->onCreate");
        app.appStatus="MainActivity-->onCreate";
        app.mHandler.sendEmptyMessage(0);

        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);

        bt1.setBackgroundColor(0xbd292f34);
        bt1.setTextColor(0xFFFFFFFF);
        bt2.setBackgroundColor(0xbd292f34);
        bt2.setTextColor(0xFFFFFFFF);
        bt3.setBackgroundColor(0xbd292f34);
        bt3.setTextColor(0xFFFFFFFF);
        bt4.setBackgroundColor(0xbd292f34);
        bt4.setTextColor(0xFFFFFFFF);

        tv = (TextView) findViewById(R.id.tv);

        bt1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               intent = new Intent(MainActivity.this
                        , Activity1.class);
                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(MainActivity.this
                        , StandardActivity.class);
                       startActivity(intent);
            }

        });

        bt3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View btn) {
                // 创建PopupMenu对象，关联相关View
                final PopupMenu popup = new PopupMenu(MainActivity.this, btn);
                // 将R.menu.popup_menu菜单资源加载到popup菜单中
                getMenuInflater().inflate(R.menu.fragment_menu, popup.getMenu());
                // 为popup菜单的菜单项单击事件绑定事件监听器
                popup.setOnMenuItemClickListener(
                        new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.simple:
                                        intent=new Intent(MainActivity.this
                                                , FragmentSimpleActivity.class);
                                        break;
                                    case R.id.showhide:
                                        intent=new Intent(MainActivity.this
                                                , FragmentShowHideActivity.class);
                                        break;
                                    case R.id.transaction:
                                        intent=new Intent(MainActivity.this
                                                , FragmentTransactionActivity.class);
                                        break;
                                    case R.id.backstack:
                                        intent=new Intent(MainActivity.this
                                                , FragmentBackStackActivity.class);
                                        break;
                                    case R.id.viewpager:
                                        intent=new Intent(MainActivity.this
                                                , FragmentViewPagerActivity.class);
//                                    default:
//                                        // 显示用户单击的菜单项
//                                        position = item.getOrder();
//                                        Log.e(TAG, "onMenuItemClick: " + position);
//                                        invalidateOptionsMenu();
//                                        showMessage("您单击了【" + item.getTitle() + "】菜单项");
                                        break;
                                }
                                startActivity(intent);
                                return true;
                            }
                        });
                popup.show();
            }
        });


        bt4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this
                        , ServiceActivity.class);
                startActivity(intent);
            }
        });
    }
    // 低内存的时候执行
    @Override
    public void onLowMemory() {

        Log.d(TAG, "MainActivity-->onLowMemory");
        txvString.append(app.appStatus);
        txvString.append("\n");
        app.appStatus="MainActivity-->onLowMemory";
        app.mHandler.sendEmptyMessage(0);
        super.onLowMemory();
    }

    //低内存的时候执行，用来代替onLowMemory()
    @Override
    public void onTrimMemory(int level) {

        Log.d(TAG, "MainActivity-->onTrimMemory:"+level);
        txvString.append(app.appStatus);
        txvString.append("\n");
        app.appStatus="MainActivity-->onTrimMemory,level="+level;
        app.mHandler.sendEmptyMessage(0);
        super.onTrimMemory(level);
    }

    //配置变化（如语言、屏幕方向切换）时执行，仅在AndroidManifest中配置了属性
    //"android:configChanges="orientation"后才会调用本方法，否则会直接调用onCreate()
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "MainActivity-->onConfigurationChanged");
        app.appStatus="MainActivity-->onConfigurationChanged";
        app.mHandler.sendEmptyMessage(0);
        super.onConfigurationChanged(newConfig);
    }


    //使用内部静态类Handler以避免内存泄漏
    private  static  class MainHandler extends Handler {
        //持有弱引用HandlerDemo Activity,GC回收时会被回收掉.
        WeakReference<MainActivity> mActivty;

        private MainHandler(MainActivity activity) {
            mActivty = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            MainActivity mainActivity =  mActivty.get();
            super.handleMessage(msg);
            mainActivity.txvString.append(mainActivity.app.appStatus);
            mainActivity.txvString.append("\n");
            mainActivity.tv.setText(mainActivity.txvString);

        }
    }
}
