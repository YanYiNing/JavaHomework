package com.example.yanyining.fragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by YanYiNing on 2016/12/7.
 */

public class MyAdapter extends FragmentPagerAdapter{
    String [] mTitles;
    List<Fragment> mFragment;

    public void setmTitles(String[] mTitles) {
        this.mTitles = mTitles;
    }

    public void setmFragment(List<Fragment> mFragment) {
        this.mFragment = mFragment;
    }

    public MyAdapter (FragmentManager fm){
        super (fm);
    }
    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
