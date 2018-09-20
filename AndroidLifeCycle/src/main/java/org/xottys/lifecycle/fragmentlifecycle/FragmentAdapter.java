package org.xottys.lifecycle.fragmentlifecycle;

import android.support.v4.app.Fragment;
import java.util.List;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/*可选的替代Adapter
import android.support.v4.app.FragmentStatePagerAdapter;*/

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Fragment> fragments, List<String> titles) {
        mFragments = fragments;
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {

         Fragment ff=(mFragments == null ? null : mFragments.get(position));
        return ff;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
