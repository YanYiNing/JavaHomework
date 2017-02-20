package com.example.yanyining.zhihudaily.fragment;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.example.yanyining.zhihudaily.ArticleActivity;
        import com.example.yanyining.zhihudaily.MyAdapter;
        import com.example.yanyining.zhihudaily.R;
        import com.example.yanyining.zhihudaily.json.Before;
        import com.example.yanyining.zhihudaily.json.FirstData;
        import com.example.yanyining.zhihudaily.json.OtherData;
        import com.example.yanyining.zhihudaily.util.HttpUtil;
        import com.google.gson.Gson;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
        import me.relex.circleindicator.CircleIndicator;
        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.Response;

/**
 * Created by YanYiNing on 2017/2/15.
 */

public class FirstFragment extends Fragment {

    private AutoScrollViewPager viewPager;
    private CircleIndicator indicator;
    private Button menu;
    private ImageView otherImage;
    private LinearLayout firstLayout;
    private SwipeRefreshLayout swipeRefresh;
    private ScrollView scrollView;
    private String URL;
    private TextView titleText;
    final int FIRST_TYPE = 0;
    final int OTHER_TYPE = 1;
    private int type = 0;
    private String date;
    private String title = "首页";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);

        viewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);

        menu = (Button) view.findViewById(R.id.drawer_button);
        titleText = (TextView) view.findViewById(R.id.first_title);
        firstLayout = (LinearLayout) view.findViewById(R.id.first_news_layout);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.first_swipe_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstLayout.removeAllViews();
                initData(URL, title, type);
                swipeRefresh.setRefreshing(false);
            }
        });
        scrollView = (ScrollView) view.findViewById(R.id.scroll_view);
        if (type == FIRST_TYPE){
            scrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()){
                        case MotionEvent.ACTION_MOVE:{
                            break;
                        }
                        case MotionEvent.ACTION_DOWN:{
                            break;
                        }
                        case MotionEvent.ACTION_UP:{
                            if(scrollView.getChildAt(0).getMeasuredHeight() <= scrollView.getScrollY() + scrollView.getHeight()){
                                String beforeUrl = "http://news-at.zhihu.com/api/4/news/before/" + date;
                                HttpUtil.sendOkHttpRequest(beforeUrl, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        final String string = response.body().string();
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadMore(string);
                                            }
                                        });
                                    }
                                });
                            }
                            break;
                        }
                    }
                    return false;
                }
            });
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        return view;
    }

    /**
     *由于mainActivity中用replaceFragment方法，所以控件的初始化放在initData中，通过判断传入数据类型来用不同的JSON解析
     */
    public void initData(String URL, String title, final int type) {
        this.URL = URL;
        this.type = type;
        this.title = title;
        //取得HttpUtil的返回数据，根据不同的类型用不同的解析
        HttpUtil.sendOkHttpRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (type == FIRST_TYPE) {
                            firstUI(string);
                        } else if (type == OTHER_TYPE) {
                            otherUI(string);
                        }
                    }
                });
            }
        });
    }

    /**
     * 初始化列表数据，添加到views中， 然后设置循环根据i让layout动态添加view。
     * 原先计划是把所有的urls传入ArticleActivity中，用ViewPager显示全部然后跳转到指定的i
     * 这个方法太蠢了...耗流量、占内存，后来取消了，但是代码没有修改，保留了用循环设置监听
     */
    private void firstUI (String string) {
        titleText.setText(title);
        Gson gson = new Gson();
        final FirstData data = gson.fromJson(string, FirstData.class);
        //设置小标题
        TextView textview = new TextView(getContext());
        textview.setText("今日热闻");
        textview.setPadding(40,40,40,40);
        firstLayout.addView(textview);
        date = data.getDate();

        //首先是列表
        List<View> views = new ArrayList<View>();
        ArrayList<String> urls = new ArrayList<String>();

        for (FirstData.StoriesBean story : data.getStories()) {
            String title = story.getTitle();
            String imgUrl = story.getImages().get(0);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item, firstLayout, false);
            ImageView newsImage = (ImageView) view.findViewById(R.id.story_image);
            TextView newsTitle = (TextView) view.findViewById(R.id.story_title_text);
            newsTitle.setText(title);
            Glide.with(getContext()).load(imgUrl).into(newsImage);
            views.add(view);
            String url = String.valueOf(story.getId());
            urls.add(url);
        }
        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            view.setOnClickListener(new MyClickListener(i, urls, 1));
            firstLayout.addView(view);
        }

        //然后是标题轮播图，这里采用了两个开源项目
        List<View> viewList = new ArrayList<View>();
        ArrayList<String> topUrls = new ArrayList<String>();

        for (FirstData.TopStoriesBean topStory : data.getTop_stories()) {
            String title = topStory.getTitle();
            String imgUrl = topStory.getImage();
            View view = LayoutInflater.from(getContext()).inflate(R.layout.viewpaper_fragment, viewPager, false);
            ImageView newsImage = (ImageView) view.findViewById(R.id.top_story_image);
            TextView newsTitle = (TextView) view.findViewById(R.id.top_story_title);
            newsTitle.setText(title);
            Glide.with(getContext()).load(imgUrl).centerCrop().into(newsImage);
            String url = String.valueOf(topStory.getId());
            topUrls.add(url);
            viewList.add(view);
        }
        for (int i = 0; i < viewList.size(); i++) {
            View view = viewList.get(i);
            view.setOnClickListener(new MyClickListener(i, topUrls, 0));
        }

        MyAdapter adapter = new MyAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.setInterval(5000);
        viewPager.startAutoScroll();
        indicator.setViewPager(viewPager);
    }

    /**
     *同firstUI的加载，不同的是标题只有一张图，给图片设置了动画。
     */
    private void otherUI(String string) {
        titleText.setText(title);
        ArrayList<String> urls = new ArrayList<String>();
        Gson gson = new Gson();
        final OtherData data = gson.fromJson(string, OtherData.class);
        List<View> views = new ArrayList<View>();
        for (OtherData.StoriesBean story : data.getStories()) {
            String title = story.getTitle();
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item, firstLayout, false);
            ImageView newsImage = (ImageView) view.findViewById(R.id.story_image);
            TextView newsTitle = (TextView) view.findViewById(R.id.story_title_text);
            newsTitle.setText(title);
            if (story.getImages().size() != 0){
                String imgUrl = story.getImages().get(0);
                Glide.with(getContext()).load(imgUrl).into(newsImage);
            } else {
                newsImage.setVisibility(View.GONE);
            }
            String url = String.valueOf(story.getId());
            urls.add(url);
            views.add(view);
        }
        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            view.setOnClickListener(new MyClickListener(i, urls, 1));
            firstLayout.addView(view);
        }
        List<View> viewList = new ArrayList<View>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.viewpaper_fragment, viewPager, false);
        otherImage = (ImageView) view.findViewById(R.id.top_story_image);
        TextView newsTitle = (TextView) view.findViewById(R.id.top_story_title);
        newsTitle.setText(data.getDescription());
        newsTitle.setTextSize(18);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.splash);
        Glide.with(getContext()).load(data.getBackground()).centerCrop().animate(animation).into(otherImage);

        viewList.add(view);
        MyAdapter adapter = new MyAdapter(viewList);
        viewPager.setAdapter(adapter);
    }

    /**
     *只是JSON的不同
     */
    private void loadMore(String string) {
        Gson gson = new Gson();
        Before data = gson.fromJson(string, Before.class);
        date = data.getDate();
        TextView textview = new TextView(getContext());
        textview.setText(String.valueOf(date));
        textview.setPadding(40,40,40,40);
        firstLayout.addView(textview);
        List<View> views = new ArrayList<View>();
        ArrayList<String> urls = new ArrayList<String>();

        for (Before.StoriesBean story : data.getStories()) {
            String title = story.getTitle();
            String imgUrl = story.getImages().get(0);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item, firstLayout, false);
            ImageView newsImage = (ImageView) view.findViewById(R.id.story_image);
            TextView newsTitle = (TextView) view.findViewById(R.id.story_title_text);
            newsTitle.setText(title);
            Glide.with(getContext()).load(imgUrl).into(newsImage);
            views.add(view);
            String url = String.valueOf(story.getId());
            urls.add(url);
        }
        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            view.setOnClickListener(new MyClickListener(i, urls, 1));
            firstLayout.addView(view);
        }
    }

    /**
     * 愚蠢的自定义监听，见证着卡顿的ViewPager的死亡。
     */
    class MyClickListener implements View.OnClickListener {
        int position;
        private ArrayList<String> urls;
        int type;

        public MyClickListener(int position, ArrayList<String> urls, int type) {
            this.position = position;
            this.urls = urls;
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            if (type == 1) {
                TextView newsTitle = (TextView) v.findViewById(R.id.story_title_text);
                newsTitle.setTextColor(0xFFC0C0C0);
            }
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            intent.putExtra("url", urls.get(position));
            startActivity(intent);
        }
    }

}
