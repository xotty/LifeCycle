/**
 * 第二个用于演示Activity生命周期的Activity
 * 没有xml布局文件，直接显示一个TextView控件
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.activitylifecycle.ActivityLifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Activity2 extends Activity
{
	private final String TAG = "ActivityLC";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("对话框风格的Activity2");
		setContentView(tv);
		Log.d(TAG, "-------Activity2 onCreate------");
	}

	//Activity被Stop后再启动时回调
	@Override
	public void onRestart() {
		super.onRestart();
		Log.d(TAG, "-------Activity2 onRestart------\n");
	}

	//Activity启动或再启动时回调
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "-------Activity2 onStart------");
	}

	//onStart()完成后调用
	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		Log.d(TAG, "-------Activity2 onPostCreate------");
	}

	//Activity恢复可见时回调
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "-------Activity2 onResume------");
	}

	//onResume()完成后调用
	@Override
	public void onPostResume() {
		super.onPostResume();
		Log.d(TAG, "-------Activity2 onPostResume------");
	}
	//Activity失去焦点时回调
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "-------Activity2 onPause------");

	}

	//Activity完全不可见时回调
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "-------Activity2 onStop------");
	}

	//Activity销毁时回调
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "-------Activity2 onDestroy------");
	}
}
