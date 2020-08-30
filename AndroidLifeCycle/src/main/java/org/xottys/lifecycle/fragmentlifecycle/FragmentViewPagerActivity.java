/**
 * 本例演示了ViewPager中Fragment的生命周期：
 * 0）setUserVisibleHint
 * 1）onAttach()
 * 2）onCreate()
 * 3）onCreateView()
 * 4）onActivityCreated()
 * 5）onStart()
 * 6）onResume()
 * 7）onPause()
 * 8) onStop()
 * 9) onDestroyView()
 * 10)onDestroy()
 * 11)onDetach()
 * 特别事项：
 * 1）FragmentPagerAdapter会预加载Fragment，当ViewPager中的Fragment大于等于3个（viewPager.setOffscreenPageLimit()规定的参数;
 * 除去展示开头和结尾两个Fragment的情况，ViewPager会保留当前Fragment左右两侧以及自身3个Fragment的信息
 * 2）超过3个的Fragment只会调用onDestroyView()方法，而不会销毁该Fragment，即不会调用onDestroy和onDetach，直到其宿主Activity结束时才会调用
 * 3）FragmentStatePagerAdapter将会对limit外的进行Fragment回收。即对limit之外的Fragment会调用onDestroy()
 * 及onDetach()方法，销毁该Fragment。其它特性与FragmentPagerAdapter相同
 * 下面是FragmentPagerAdapter下，Fragment生命周期的具体演变过程：
 * --加载Fragment1时，Fragment1和Fragment2都走到onResume
 * --由Fragment1向右滑动到Fragment2时，Fragment3走到onResume
 * --由Fragment2向右滑到Fragment3时，Fragment1走到onDestroyView，Fragment4走到onResume
 * --由Fragment2向左滑到Fragment1时，Fragment3走到onDestroyView
 * --由Fragment3向右滑到Fragment4时，Fragment2走到onDestroyView
 * --由Fragment3向左滑到Fragment2时，Fragment1从onCreateView走到了onResume，Fragment4走到onDestroyView
 * --由Fragment4向左滑到Fragment3时，Fragment2从onCreateView走到了onResume
 *
 * * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.lifecycle.fragmentlifecycle;

import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import org.xottys.lifecycle.MyApplication;
import org.xottys.lifecycle.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentViewPagerActivity extends FragmentBaseActivity {
    private static final String TAG = "FragmentLifecycle";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;

    private List<Fragment> mFragments;
    private String[] mTitles = new String[]{"Fragment1", "Fragment2", "Fragment3","Fragment4"};

//    private boolean firstSetUserVisibleHint;
//    public boolean isFirstSetUserVisibleHint() {
//        return firstSetUserVisibleHint;
//    }
//    public void setFirstSetUserVisibleHint(boolean isFirstSetUserVisibleHint) {
//        this.firstSetUserVisibleHint = isFirstSetUserVisibleHint;
//    }

    @Override
    protected int getLayoutID(){
        return R.layout.activity_fragmentviewpager;
    }
    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        //定义三个不同的Fragment
        Fragment fragment1 = new Fragment1();
        mFragments.add(fragment1);
        Fragment fragment2 = new Fragment2();
        mFragments.add(fragment2);
        Fragment fragment3 = new Fragment3();
        mFragments.add(fragment3);
        Fragment fragment4 = new Fragment4();
        mFragments.add(fragment4);

        MyApplication.isFirstSetUserVisibleHint=false;
    }

    @Override
    protected void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mAdapter.setData(mFragments, Arrays.asList(mTitles));
    }

    @Override
    protected  void initViewListener(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
            // arg0是当前选中的页面的Position
                MyApplication.isFirstSetUserVisibleHint=false;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            // arg0 :当前页面，及你点击滑动的页面；arg1:当前页面偏移的百分比；arg2:当前页面偏移的像素位置
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            //arg0 ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没做。

            }
        });

    }

    @Override
    protected  void processData(){

    }
}