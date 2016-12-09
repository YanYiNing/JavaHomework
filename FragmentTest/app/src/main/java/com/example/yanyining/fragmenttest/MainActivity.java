package com.example.yanyining.fragmenttest;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyAdapter myAdapter ;
    TabLayout mTabLayout;
    ViewPager mViewPaper;
    String[] titles = new String[]{"第一个标题", "第二个标题", "第三个标题", "第四个标题", "第五个标题", "第六个标题", "第七个标题", "第八个标题", "第九个标题", "第十个标题"};
    List<Fragment> mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPaper = (ViewPager) findViewById(R.id.viewpager);
        mViewPaper.setOffscreenPageLimit(10);
        mFragment = new ArrayList<>();
        for(int i = 1; i <= 10; i++)
            mFragment.add(new FragmentOne());
        myAdapter = new MyAdapter(getSupportFragmentManager());
        myAdapter.setmTitles(titles);
        myAdapter.setmFragment(mFragment);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPaper.setAdapter(myAdapter);
        mTabLayout.setupWithViewPager(mViewPaper);
    }
}

