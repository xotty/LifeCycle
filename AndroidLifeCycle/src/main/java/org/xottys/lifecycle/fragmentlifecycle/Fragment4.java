/**
 * 第四个Fragment，演示Viewpager+Fragment中会用到
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Sept，2018
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.fragmentlifecycle;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xottys.lifecycle.MyApplication;

public class Fragment4 extends Fragment
{
	private static final String TAG = "FragmentLifecycle";

	@Override
	public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {

		super.onInflate(context, attrs, savedInstanceState);
		showText("--Fragment4-->onInflate--");
		Log.d(TAG, "--Fragment4-->onInflate--");
	}

	//Activity与Fragment绑定
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		showText("--Fragment4-->onAttach--");
		Log.d(TAG, "--Fragment4-->onAttach--");
	}
	@Override
	public View onCreateView(LayoutInflater inflater
		, ViewGroup container, Bundle data)
	{
		TextView tv = new TextView(getActivity());
		tv.setText("Fragment 4");
		tv.setTextColor(Color.BLACK);
		tv.setTextSize(20);
		tv.setGravity(Gravity.CENTER);
		tv.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));

		showText("--Fragment4-->onCreateView--");
		Log.d(TAG, "--Fragment4-->onCreateView--");
		return tv;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showText("--Fragment4-->onCreate--");
		Log.d(TAG, "--Fragment4-->onCreate--");
	}


	@Override
	public void onPause() {
		super.onPause();
		showText("--Fragment4-->onPause--");
		Log.d(TAG, "--Fragment4-->onPause--");
	}


	//当宿主 Activity 的 onCreate() 方法返回后，该方法被回调,此时可以执行与Activity相关的UI操作
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		showText("--Fragment4-->onActivityCreated--");
		Log.d(TAG, "--Fragment4-->onActivityCreated--");
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		showText("--Fragment4-->onViewStateRestored--");
		Log.d(TAG, "--Fragment4-->onViewStateRestored--");
		super.onViewStateRestored(savedInstanceState);
	}


	//当Fragment启动时,该方法被回调
	@Override
	public void onStart() {
		super.onStart();
		showText("--Fragment4-->onStart--");
		Log.d(TAG, "--Fragment4-->onStart--");
	}

	//当Fragment恢复时,该方法被回调
	@Override
	public void onResume() {
		super.onResume();
		showText("--Fragment4-->onResume--");
		Log.d(TAG, "--Fragment4-->onResume--");
	}

	//当Fragment停止时,该方法被回调
	@Override
	public void onStop() {
		super.onStop();
		showText("--Fragment4-->onStop--");
		Log.d(TAG, "--Fragment4-->onStop--");
	}

	//当与Fragment绑定的UI视图被移除时，该方法被回调
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		showText("--Fragment4-->onDestroyView--");
		Log.d(TAG, "--Fragment4-->onDestroyView--");
	}

	//当Fragment销毁时,该方法被回调
	@Override
	public void onDestroy() {
		super.onDestroy();
		showText("--Fragment4-->onDestroy--");
		Log.d(TAG, "--Fragment4-->onDestroy--");
	}

	//当Fragment与Activity解除绑定时,该方法被回调
	@Override
	public void onDetach() {
		super.onDetach();
		showText("--Fragment4-->onDetach--");
		Log.d(TAG, "--Fragment4-->onDetach--");
	}

	//当 Fragment 调用 hide() 、 show() 时回调
	@Override
	public void onHiddenChanged(boolean hidden) {
		showText("--Fragment4-->onHiddenChanged--");
		Log.d(TAG, "--Fragment4-->onHiddenChanged--");
		super.onHiddenChanged(hidden);
	}

	//当 Fragment 与 ViewPager 结合使用时，切换 Pager 时回调
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if ( !MyApplication.isFirstSetUserVisibleHint) {
			if (getActivity() != null) {
				((FragmentViewPagerActivity) getActivity()).atv.setText("");
				Log.i(TAG, "setUserVisibleHint: 4");
				MyApplication.isFirstSetUserVisibleHint = true;
			}
		}
				
		showText("--Fragment4-->setUserVisibleHint--");
		Log.d(TAG, "--Fragment4-->setUserVisibleHint--");
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
