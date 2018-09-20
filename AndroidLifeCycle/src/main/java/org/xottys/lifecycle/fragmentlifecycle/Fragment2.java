/**
 * 第二个Fragment，大部分应用程序都应该至少为每个fragment实现这三个方法
 * 1）onCreate()
 * 2）onCreateView()
 * 3）onPause()
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Sept，2018
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.fragmentlifecycle;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
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
import org.xottys.lifecycle.R;


public class Fragment2 extends Fragment
{ private static final String TAG = "FragmentLifecycle";

	@Override
	public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {

		super.onInflate(context, attrs, savedInstanceState);
		showText("--Fragment2-->onInflate--");
		Log.d(TAG, "--Fragment2-->onInflate--");
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showText("--Fragment2-->onCreate--");
		Log.d(TAG, "--Fragment2-->onCreate--");
	}

	@Override
	public View onCreateView(LayoutInflater inflater
		, ViewGroup container, Bundle data)
	{
		View view =inflater.inflate(R.layout.fragment_fragment2, container, false);
		TextView tv =view.findViewById(R.id.tv);
		tv.setText("Fragment 2");
		tv.setTextColor(Color.BLACK);
		tv.setTextSize(20);
		tv.setGravity(Gravity.CENTER);
		tv.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));

		showText("--Fragment2-->onCreateView--");
        Log.d(TAG, "--Fragment2-->onCreateView--");
		return view;


//		return inflater.inflate(R.layout.fragment_fragment2, container, false);

	}


	@Override
	public void onPause() {
		super.onPause();
		showText("--Fragment2-->onPause--");
		Log.d(TAG, "--Fragment2-->onPause--");
	}


	//当宿主 Activity 的 onCreate() 方法返回后，该方法被回调,此时可以执行与Activity相关的UI操作
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		showText("--Fragment2-->onActivityCreated--");
		Log.d(TAG, "--Fragment2-->onActivityCreated--");
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		showText("--Fragment2-->onViewStateRestored--");
		Log.d(TAG, "--Fragment2-->onViewStateRestored--");
		super.onViewStateRestored(savedInstanceState);
	}


	//当Fragment启动时,该方法被回调
	@Override
	public void onStart() {
		super.onStart();
		showText("--Fragment2-->onStart--");
		Log.d(TAG, "--Fragment2-->onStart--");
	}

	//当Fragment恢复时,该方法被回调
	@Override
	public void onResume() {
		super.onResume();
		showText("--Fragment2-->onResume--");
		Log.d(TAG, "--Fragment2-->onResume--");
	}

	//当Fragment停止时,该方法被回调
	@Override
	public void onStop() {
		super.onStop();
		showText("--Fragment2-->onStop--");
		Log.d(TAG, "--Fragment2-->onStop--");
	}

	//当与Fragment绑定的UI视图被移除时，该方法被回调
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		showText("--Fragment2-->onDestroyView--");
		Log.d(TAG, "--Fragment2-->onDestroyView--");
	}

	//当Fragment销毁时,该方法被回调
	@Override
	public void onDestroy() {
		super.onDestroy();
		showText("--Fragment2-->onDestroy--");
		Log.d(TAG, "--Fragment2-->onDestroy--");
	}

	//当Fragment与Activity解除绑定时,该方法被回调
	@Override
	public void onDetach() {
		super.onDetach();
		showText("--Fragment2-->onDetach--");
		Log.d(TAG, "--Fragment2-->onDetach--");
	}


	//当 Fragment 调用 hide() 、 show() 时回调
	@Override
	public void onHiddenChanged(boolean hidden) {
		showText("--Fragment2-->onHiddenChanged--");
		Log.d(TAG, "--Fragment2-->onHiddenChanged--");
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
		showText("--Fragment2-->setUserVisibleHint--");
		Log.d(TAG, "--Fragment2-->setUserVisibleHint--");
		super.setUserVisibleHint(isVisibleToUser);
	}


	private void showText(String info) {
		if (getActivity()!=null) {
			if (getActivity().getClass() == FragmentSimpleActivity.class) {
				((FragmentSimpleActivity) getActivity()).setTvText(info);
			} else if (getActivity().getClass() == FragmentTransactionActivity.class) {
				((FragmentTransactionActivity) getActivity()).setTvText(info);
			} else if (getActivity().getClass() == FragmentBackStackActivity.class) {
				((FragmentBackStackActivity) getActivity()).setTvText(info);
			} else if (getActivity().getClass() == FragmentShowHideActivity.class) {
				((FragmentShowHideActivity) getActivity()).setTvText(info);
			} else if (getActivity().getClass() == FragmentViewPagerActivity.class) {
				((FragmentViewPagerActivity) getActivity()).setTvText(info);
			}
		}
	}
}
