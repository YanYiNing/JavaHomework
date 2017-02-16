package com.example.yanyining.zhihudaily.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yanyining.zhihudaily.News;
import com.example.yanyining.zhihudaily.R;
import com.example.yanyining.zhihudaily.json.FirstData;
import com.example.yanyining.zhihudaily.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by YanYiNing on 2017/2/15.
 */

public class FirstFragment extends Fragment {
    private ViewPager viewPager;
    private Button itemButton;
    private LinearLayout firstLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.view_paper);
        /*recyclerView = (RecyclerView) view.findViewById(R.id.first_recycler_view);*/
        itemButton = (Button) view.findViewById(R.id.drawer_button);
        firstLayout = (LinearLayout) view.findViewById(R.id.first_news_layout);
        /*LinearLayoutManager layoutManger = new LinearLayoutManager(getContext());*/
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initData() {
        final String request = "https://news-at.zhihu.com/api/4/stories/latest";
        HttpUtil.sendOkHttpRequest(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final FirstData data = gson.fromJson(string, FirstData.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textview = new TextView(getContext());
                        textview.setText("今日热闻");
                        textview.setPadding(40,40,40,40);
                        firstLayout.addView(textview);
                        for (FirstData.Story story : data.stories) {
                            String title = story.title;
                            String imgUrl = story.images.get(0);
                            View view = LayoutInflater.from(getContext()).inflate(R.layout.item, firstLayout, false);
                            ImageView newsImage = (ImageView) view.findViewById(R.id.item_image);
                            TextView newsTitle = (TextView) view.findViewById(R.id.item_title);
                            newsTitle.setText(title);
                            Glide.with(getContext()).load(imgUrl).into(newsImage);
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getActivity(), "You clicked this", Toast.LENGTH_SHORT).show();
                                }
                            });
                            firstLayout.addView(view);
                        }
                    }
                });
            }
        });
    }
}
