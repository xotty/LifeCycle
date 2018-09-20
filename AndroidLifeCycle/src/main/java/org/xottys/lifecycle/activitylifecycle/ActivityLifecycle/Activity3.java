/**
 * 第三个用于演示Activity生命周期的Activity
 * 没有xml布局文件，全部使用自定义控件
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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity3 extends Activity
{    private final String TAG = "ActivityLC";
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//用代码自定义一个LinearLayout并设置相关参数
		final LinearLayout myLayout = new LinearLayout(Activity3.this); // 线性布局方式
		myLayout.setOrientation(LinearLayout.VERTICAL); //
		myLayout.setBackgroundColor(0xffd6d7d7);
		LinearLayout.LayoutParams LP_MM = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

		myLayout.setLayoutParams(LP_MM);

		//用代码自定义一个TextView
		TextView tv = new TextView(Activity3.this);
		tv.setText("Activity3");
		tv.setTextColor(Color.BLUE);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setTextSize(30);


		//用代码自定义一个Button
		final Button myButton = new Button(Activity3.this);
		myButton.setText("退出");
		myButton.setBackgroundColor(Color.GRAY);
		myButton.setTextColor(Color.WHITE);
		myButton.setTextSize(24);
		//为这个自定义的Button准备和设置Layout参数
		LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		LP_WW.gravity = Gravity.CENTER;
		LP_WW.setMargins(0, 400, 0, 0);
		myButton.setLayoutParams(LP_WW);

		//向MyButton添加点击响应
		myButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//缺省resultCode是RESULT_CANCEL
				setResult(RESULT_OK);

				finish();
			}
		});

		//将自定义TextView添加到自定义myLayout中
		myLayout.addView(tv);
		//将自定义Button添加到自定义myLayout中
		myLayout.addView(myButton);
		//显示自定义的布局
		setContentView(myLayout);

		Log.d(TAG, "-------Activity3 onCreate------");
	}

	//Activity被Stop后再启动时回调
	@Override
	public void onRestart() {
		super.onRestart();
		Log.d(TAG, "-------Activity3 onRestart------\n");
	}

	//Activity启动或再启动时回调
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "-------Activity3 onStart------");
	}

	//onStart()完成后调用
	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		Log.d(TAG, "-------Activity3 onPostCreate------");
	}

	//Activity恢复可见时回调
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "-------Activity3 onResume------");
	}

	//onResume()完成后调用
	@Override
	public void onPostResume() {
		super.onPostResume();
		Log.d(TAG, "-------Activity3 onPostResume------");
	}
	//Activity失去焦点时回调
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "-------Activity3 onPause------");

	}

	//Activity完全不可见时回调
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "-------Activity3 onStop------");
	}

	//Activity销毁时回调
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "-------Activity3 onDestroy------");
	}
}
