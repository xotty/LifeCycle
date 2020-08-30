/**
 * 这是Fragment演示中所有宿主Activity的父类，有完整的Activity生命周期
 * 0) onAttachFragment
 * 1) onCreate
 * 2) onRestart
 * 3) onStart
 * 4) onRestoreInstanceState..在activity销毁重建的时候才会调用，回复UI状态数据
 * 5) onPostCreate
 * 6) onResume
 * 7) onPostResume
 * 8) onSaveInstanceState.....系统直接销毁Activity时会调用，finish时不会调用，保存UI状态数据或自定义数据
 * 9) onAttachedToWindow
 * 10)onCreateOptionsMenu
 * 11)onPause
 * 12)onStop
 * 13)onDestroy
 * 还有一个onBackStackChanged回调用于监听Fragment回退栈的变化
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.fragmentlifecycle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.xottys.lifecycle.R;

public abstract class FragmentBaseActivity extends AppCompatActivity {
    private static final String TAG = "FragmentLifecycle";
    protected TextView atv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initData();
        initView();
        initViewListener();
        processData();

        atv = (TextView) findViewById(R.id.atv);
        atv.setMovementMethod(ScrollingMovementMethod.getInstance());
        //为回退栈绑定事件监听器

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                
                setTvText("--Activity-->onBackStackChanged---");
                Log.i(TAG, "--Activity-->onBackStackChanged---");
            }
        });

        setTvText("这里显示的生命周期可能不完整!");
        setTvText("完整的生命周期请查看Log信息");
        setTvText("--Activity-->onCreate--");
        Log.d(TAG, "--Activity-->onCreate--");

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (atv!=null) {
            setTvText("--Activity-->onAttachFragment--");
        }
        Log.d(TAG, "--Activity-->onAttachFragment--");
    }

    //Activity被Stop后再启动时回调
    @Override
    public void onRestart() {
        super.onRestart();
        setTvText("--Activity-->onReStart--");
        Log.d(TAG, "--Activity-->onRestart--");
    }

    //Activity启动或再启动时回调
    @Override
    public void onStart() {
        super.onStart();
        setTvText("--Activity-->onStart--");
        Log.d(TAG, "--Activity-->onStart--");
    }

    //onStart()完成后调用
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setTvText("--Activity-->onPostCreate--");
        Log.d(TAG, "--Activity-->onPostCreate--");
    }

    //Activity恢复可见时回调
    @Override
    public void onResume() {
        super.onResume();
        setTvText("--Activity-->onResume--");
        Log.d(TAG, "--Activity-->onResume--");
    }

    //onResume()完成后调用
    @Override
    public void onPostResume() {
        super.onPostResume();
        setTvText("--Activity-->onPostResume--");
        Log.d(TAG, "--Activity-->onPostResume--");
    }

    //View在没有绘制出来时（onDraw前）调用的，但只会调用一次
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "--Activity-->onAttachedToWindow--");
        setTvText("--Activity-->onAttachedToWindow--");
    }


    //Activity失去焦点时回调
    @Override
    public void onPause() {
        super.onPause();
        setTvText("--Activity-->onPause--");
        Log.d(TAG, "--Activity-->onPause--");

    }

    //Activity完全不可见时回调
    @Override
    public void onStop() {
        super.onStop();
        setTvText("--Activity-->onStop--");
        Log.d(TAG, "--Activity-->onStop--");
    }

    //Activity销毁时回调
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "--Activity-->onDestroy--");
        setTvText("--Activity-->onDestroy--");
    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        Log.d(TAG, "--Activity-->onRestoreInstanceState---");
        setTvText("--Activity-->onRestoreInstanceState--");
    }


    //Activity销毁前调用，用于保存当前状态数据，以便再次进入时，在onCreate中能恢复
    //有ID且获得焦点的控件才会被保存
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "--Activity-->onSaveInstanceState--");
        setTvText("--Activity-->onSaveInstanceState--");
        //将需要保存的数据放入Bundle中
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTvText("--Activity-->onCreateOptionsMenu--");
        Log.i(TAG, "--Activity-->onCreateOptionsMenu--" );

        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    // 选项菜单的菜单项被单击后调用
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        setTvText("--Activity-->onOptionsItemSelected--");
        Log.i(TAG, "--Activity-->onOptionsItemSelected--");
        //判断单击的是哪个菜单项，并有针对性地作出响应
        switch (menuItem.getItemId()) {
            case R.id.clearscreen:
                atv.setText("");
        }
        return false;
    }

    //Fragment调用它来在屏幕上显示Fragment生命周期过程，这也是一种二者之间的通讯方式
    public void setTvText(String str) {
        if (atv!=null)
//            if (atv.getText().toString().contains("setUserVisibleHint")) {
//                if (!isfirst)
//                    isfirst = true;
//                else
//                    isfirst=false;
//            }
//            else
//                isfirst=false;
//
//            if (isfirst)
//                 atv.setText("");

           atv.append(str + "\n");
    }


    /**
     * 设置页面布局ID
     *
     * @return
     */
    protected abstract int getLayoutID();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化页面控件
     */
    protected abstract void initView();

    /**
     * 初始化控件监听器
     */
    protected abstract void initViewListener();

    protected abstract void processData();
}