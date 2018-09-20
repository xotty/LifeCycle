/**
 * 第一个Fragment，其生命周期全过程将会被完整显示出来
 *
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Sept，2018
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.fragmentlifecycle;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xottys.lifecycle.MyApplication;

public class Fragment1 extends Fragment {
    private static final String TAG = "FragmentLifecycle";

    //当 Fragment 调用 hide() 、 show() 时回调
    @Override
    public void onHiddenChanged(boolean hidden) {
        showText("--Fragment1-->onHiddenChanged--");
        Log.d(TAG, "--Fragment1-->onHiddenChanged--");
        super.onHiddenChanged(hidden);
    }

    //当 Fragment 与 ViewPager 结合使用时，切换 Pager 时回调
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if ( !MyApplication.isFirstSetUserVisibleHint) {
            MyApplication.isFirstSetUserVisibleHint=true;
            if (getActivity()!=null)
            ((FragmentViewPagerActivity) getActivity()).atv.setText("");
        }
        showText("--Fragment1-->setUserVisibleHint--");
        Log.d(TAG, "--Fragment1-->setUserVisibleHint--");
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {

        super.onInflate(context, attrs, savedInstanceState);
        showText("--Fragment1-->onInflate--");
        Log.d(TAG, "--Fragment1-->onInflate--");
    }

    //Activity与Fragment绑定
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        showText("--Fragment1-->onAttach--");
        Log.d(TAG, "--Fragment1-->onAttach--");
    }

    //Fragment被创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showText("--Fragment1-->onCreate--");
        Log.d(TAG, "--Fragment1-->onCreate--");
    }

    //Fragment视图被创建,绑定UI视图时,该方法被回调
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle data) {
        showText("--Fragment1-->onCreateView--");
        Log.d(TAG, "--Fragment1-->onCreateView--");

        //这里定义Fragment要显示的内容
        TextView tv = new TextView(getActivity());
        tv.setText("Fragment 1");
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);
        tv.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        return tv;
    }

    //该方法在onCreateView()之后会被立即执行,此时可以对 View 对象进行赋值
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        showText("--Fragment-->onViewCreated--");
        Log.d(TAG, "--Fragment1-->onViewCreated--");

        super.onViewCreated(view, savedInstanceState);

    }

    //当宿主 Activity 的 onCreate() 方法返回后，该方法被回调,此时可以执行与Activity相关的UI操作
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showText("--Fragment1-->onActivityCreated--");
        Log.d(TAG, "--Fragment1-->onActivityCreated--");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        showText("--Fragment1-->onViewStateRestored--");
        Log.d(TAG, "--Fragment1-->onViewStateRestored--");
        super.onViewStateRestored(savedInstanceState);
    }


    //当Fragment启动时,该方法被回调
    @Override
    public void onStart() {
        super.onStart();
        showText("--Fragment1-->onStart--");
        Log.d(TAG, "--Fragment1-->onStart--");
    }

    //当Fragment恢复时,该方法被回调
    @Override
    public void onResume() {
        super.onResume();
        showText("--Fragment1-->onResume--");
        Log.d(TAG, "--Fragment1-->onResume--");
    }

    //当Fragment暂停时,该方法被回调
    @Override
    public void onPause() {
        super.onPause();
        showText("--Fragment1-->onPause--");
        Log.d(TAG, "--Fragment1-->onPause--");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "--Fragment1-->onSaveInstanceState--");
        showText("--Fragment1-->onSaveInstanceState--");
        //将需要保存的数据放入Bundle中
    }

    //当Fragment停止时,该方法被回调
    @Override
    public void onStop() {
        super.onStop();
        showText("--Fragment1-->onStop--");
        Log.d(TAG, "--Fragment1-->onStop--");
    }

    //当与Fragment绑定的UI视图被移除时，该方法被回调
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showText("--Fragment1-->onDestroyView--");
        Log.d(TAG, "--Fragment1-->onDestroyView--");
    }

    //当Fragment销毁时,该方法被回调
    @Override
    public void onDestroy() {
        super.onDestroy();
        showText("--Fragment1-->onDestroy--");
        Log.d(TAG, "--Fragment1-->onDestroy--");
    }

    //当Fragment与Activity解除绑定时,该方法被回调
    @Override
    public void onDetach() {
        super.onDetach();
        showText("--Fragment1-->onDetach--");
        Log.d(TAG, "--Fragment1-->onDetach--");
    }

    private void showText(String info) {
        if (getActivity()!=null) {
            if (getActivity().getClass().isAssignableFrom(FragmentSimpleActivity.class)) {
                ((FragmentSimpleActivity) getActivity()).setTvText(info);
            } else if (getActivity().getClass().isAssignableFrom(FragmentTransactionActivity.class)) {
                ((FragmentTransactionActivity) getActivity()).setTvText(info);
            } else if (getActivity().getClass().isAssignableFrom(FragmentBackStackActivity.class)) {
                ((FragmentBackStackActivity) getActivity()).setTvText(info);
            } else if (getActivity().getClass().isAssignableFrom(FragmentShowHideActivity.class)) {
                ((FragmentShowHideActivity) getActivity()).setTvText(info);
            } else if (getActivity().getClass().isAssignableFrom(FragmentViewPagerActivity.class)) {
                ((FragmentViewPagerActivity) getActivity()).setTvText(info);
            }
        }
    }
}
