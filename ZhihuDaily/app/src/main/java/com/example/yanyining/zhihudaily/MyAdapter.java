package com.example.yanyining.zhihudaily;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by YanYiNing on 2017/2/15.
 */

public class MyAdapter extends PagerAdapter {

    private List<View> viewList;

    public MyAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
