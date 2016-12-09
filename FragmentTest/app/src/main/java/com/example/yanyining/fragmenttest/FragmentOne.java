package com.example.yanyining.fragmenttest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by YanYiNing on 2016/12/7.
 */

public class FragmentOne extends android.support.v4.app.Fragment {
    View view;
    String[] titles =new String[]{"第一个标题", "第二个标题", "第三个标题", "第四个标题", "第五个标题", "第六个标题", "第七个标题", "第八个标题", "第九个标题", "第十个标题"};
    static int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(titles[i]);
        i++;
    }
}
