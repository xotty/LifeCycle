/**
 * 本例演示了动态Fragment的生命周期(Fragment在8~9之间活动在前台)：
 * 1）onAttach->onAttachFragment(Activity)
 * 2）onCreate
 * 3）onCreateView
 * 4）onViewCreated
 * 5）onActivityCreated
 * 6）onViewStateRestored
 * 7）onStart
 * 8）onResume
 * 9) onPause
 * 10)onStop
 * 11)onDestroyView
 * 12)onDestroy
 * 13)onDetach
 *
 * replace操作 = add操作+remove操作
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Sept，2018
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

public class FragmentTransactionActivity extends FragmentBaseActivity {

    Button addFragment, replaceFragment, removeFragment;
    TextView atv;
    Fragment fragment1,fragment2,fragment3;

    @Override
    protected int getLayoutID(){
        return R.layout.activity_fragmenttransaction;
    }

    @Override
    protected  void initData(){

    }

    @Override
    protected  void initView() {
        addFragment = (Button) findViewById(R.id.bt1);
        replaceFragment = (Button) findViewById(R.id.bt2);
        removeFragment = (Button) findViewById(R.id.bt3);
        atv = (TextView) findViewById(R.id.atv);
        atv.setMovementMethod(ScrollingMovementMethod.getInstance());

        //定义三个不同的Fragment
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
    }

    @Override
    protected  void initViewListener(){
        //添加Fragment到布局文件的容器中，同一容器中可以添加多个Fragment，后面的会覆盖前面的
        //同一个Fragment的同一实例不能在系统中同时出现，不同实例可以同时出现
        addFragment.setOnClickListener(new OnClickListener() {
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
        replaceFragment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                atv.setText("Replace Fragment生命周期\n\n");
                setTvText("这里显示的生命周期可能不完整!");
                setTvText("完整的生命周期请查看Log信息");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container1, fragment3)
                        .commit();
            }
        });

        //移除容器中的的Fragment
        removeFragment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                atv.setText("Remove Fragment生命周期\n\n");
                setTvText("这里显示的生命周期可能不完整!");
                setTvText("完整的生命周期请查看Log信息");
                Fragment2 fragment = new Fragment2();
                getSupportFragmentManager().beginTransaction()
                        .remove(fragment2)
                        .commit();
            }
        });

    }

    @Override
    protected  void processData(){

    }

}