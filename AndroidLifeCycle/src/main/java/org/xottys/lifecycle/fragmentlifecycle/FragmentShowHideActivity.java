/**
 * 本例演示了Fragment show/hide操作时的生命周期：onHiddenChanged()
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.fragmentlifecycle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.xottys.lifecycle.R;

public class FragmentShowHideActivity extends FragmentBaseActivity {
    Button addBT,hideBT,showBT;
    TextView atv;
    Fragment fragment1,fragment2;

    @Override
    protected int getLayoutID(){
        return R.layout.activity_fragmenttransaction;
    }

    @Override
    protected  void initData(){

    }

    @Override
    protected  void initView(){
        addBT= (Button) findViewById(R.id.bt1);
        hideBT = (Button) findViewById(R.id.bt2);
        showBT = (Button) findViewById(R.id.bt3);
        hideBT.setText("Hide\nFragment");
        showBT.setText("Show\nFragment");
        atv = (TextView) findViewById(R.id.atv);
        atv.setMovementMethod(ScrollingMovementMethod.getInstance());

        //定义三个不同的Fragment
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();



    }

    @Override
    protected  void initViewListener(){
        //添加Fragment到布局文件的容器中，同一容器中可以添加多个Fragment，后面的会覆盖前面的
        //同一个Fragment的同一实例不能在系统中同时出现，不同实例可以同时出现
        addBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                if (!fragment1.isAdded()&&!fragment2.isAdded()){
                    atv.setText("Add Fragment生命周期\n\n");
                    setTvText("这里显示的生命周期可能不完整!");
                    setTvText("完整的生命周期请查看Log信息");
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container1, fragment1)
                            .add(R.id.container2, fragment2)
                            .commit();
                }
            }
        });

        //替换容器中的的Fragment
        hideBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                atv.setText("Hide Fragment生命周期\n\n");
                setTvText("这里显示的生命周期可能不完整!");
                setTvText("完整的生命周期请查看Log信息");
                getSupportFragmentManager().beginTransaction()
                        .hide(fragment1)
                        .hide(fragment2)
                        .commit();
            }
        });

        //移除容器中的的Fragment
        showBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                atv.setText("Show Fragment生命周期\n\n");
                setTvText("这里显示的生命周期可能不完整!");
                setTvText("完整的生命周期请查看Log信息");
                getSupportFragmentManager().beginTransaction()
                        .show(fragment1)
                        .show(fragment2)
                        .commit();
            }
        });

    }

    @Override
    protected  void processData(){

    }
}