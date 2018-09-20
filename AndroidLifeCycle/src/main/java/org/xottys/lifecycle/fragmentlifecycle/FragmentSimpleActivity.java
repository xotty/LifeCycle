/**
 * 本例演示了静态Fragment的生命周期(Fragment在8~11之间活动在前台)：
 * 0）onInflate
 * 1）onAttach->onAttachFragment(Activity)
 * 2）onCreate
 * 3）onCreateView
 * 4）onViewCreated->onCreate(Activity)
 * 5）onActivityCreated
 * 6）onViewStateRestored
 * 7）onStart->onStart(Activity)->onPostCreate(Activity)->onResume(Activity)
 * 8）onResume->onPostResume(Activity)->onAttachedToWindow(Activity)->onCreateOptionsMenu(Activity)
 * 9) onCreateOptionsMenu
 * 10)onPrepareOptionsMenu
 * 11)onPause->onPause(Activity)->onSaveInstanceState(Activity)
 * 12)onSaveInstanceState
 * 13)onStop->onStop(Activity)
 * 14)onDestroyView
 * 15)onDestroy
 * 16)onDetach->onDestroy(Activity)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Sept，2018
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.fragmentlifecycle;

import org.xottys.lifecycle.R;

public class FragmentSimpleActivity extends FragmentBaseActivity {

    @Override
    protected int getLayoutID(){
      return R.layout.activity_fragmentsimple;
  }

    @Override
    protected  void initData(){

    }

    @Override
    protected  void initView(){

    }

    @Override
    protected  void initViewListener(){

    }

    @Override
    protected  void processData(){

    }
}