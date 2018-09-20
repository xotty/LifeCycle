/**
 * 本例演示了除Fragment add/pop回退栈时生命周期：
 * 一、addToBackStack
 * 1）onAttach()->onAttachFragment(Activity)
 * 2）onCreate()
 * 3）onCreateView()
 * 4）onViewCreated
 * 5）onActivityCreated
 * 6）onViewStateRestored
 * 5）onStart()
 * 6）onResume()->onBackStackChanged(Activity)
 * 二、popBackStack()
 * 1）onPause()
 * 2) onStop()
 * 3) onDestroyView()
 * 4) onDestroy()
 * 5) onDetach()->onBackStackChanged(Activity)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.fragmentlifecycle;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.xottys.lifecycle.R;

public class FragmentBackStackActivity extends FragmentBaseActivity {

    Button addStackBT,popStackBT,popStackAllBT;
    TextView atv;
    Fragment fragment1,fragment2,fragment3;

    @Override
    protected int getLayoutID(){
        return R.layout.activity_fragmenttransaction;
    }

    @Override
    protected  void initData(){
        //定义三个不同的Fragment
       fragment1 = new Fragment1();
       fragment2 = new Fragment2();
       fragment3 = new Fragment3();

    }

    @Override
    protected  void initView(){
        atv=(TextView) findViewById(R.id.atv);
        addStackBT = (Button) findViewById(R.id.bt1);
        addStackBT.setText("Add To\nBackStack");
        popStackBT = (Button) findViewById(R.id.bt2);
        popStackBT.setText("Pop Top\nBackStack");
        popStackAllBT = (Button) findViewById(R.id.bt3);
        popStackAllBT.setText("Pop All\nBackStack");

    }

    @Override
    protected  void initViewListener(){
        //将Fragment操作放入回退栈，以便能随时取消该操作
        addStackBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                atv.setText("AddStack Fragment生命周期\n\n");
                if (!fragment1.isAdded()) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container1, fragment1)
                            .addToBackStack("aa") // 将上述操作添加到Back栈
                            .commit();
                }
                if (!fragment2.isAdded()) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container2, fragment2)
                            .addToBackStack("bb")// 将替换前的Fragment添加到Back栈
                            .commit();
                }

                if (!fragment3.isAdded()) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container2, fragment3)
                            .addToBackStack("cc") // 将上述操作添加到Back栈
                            .commit();
                }
            }
        });

        //弹出特定的Fragment回退栈，取消上一步的相应操作
        popStackBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                atv.setText("PopStack Fragment生命周期\n\n");
                //弹出栈顶的Fragment操作
                getSupportFragmentManager().popBackStack();
                /*可替代语句
                getSupportFragmentManager().popBackStack(null, 0);
                getSupportFragmentManager().popBackStack("bb", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/

            }
        });

        //弹出全部Fragment回退栈
        popStackAllBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                atv.setText("PopStack All Fragment生命周期\n\n");
                //弹出栈中全部Fragment操作
                getSupportFragmentManager().popBackStack(null, 1);
                /*可替代语句
                getSupportFragmentManager().popBackStack("aa", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
            }
        });
    }

    @Override
    protected  void processData(){

    }
}