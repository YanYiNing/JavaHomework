package com.example.yanyining.zhihudaily.fragment;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Matrix;
        import android.icu.util.IndianCalendar;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.view.ViewPager;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.util.AttributeSet;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.view.animation.Transformation;
        import android.view.animation.TranslateAnimation;
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
        import com.example.yanyining.zhihudaily.SplashActivity;
        import com.example.yanyining.zhihudaily.json.FirstData;
        import com.example.yanyining.zhihudaily.json.OtherData;
        import com.example.yanyining.zhihudaily.util.HttpUtil;
        import com.google.gson.Gson;

        import java.io.IOException;
        import java.lang.reflect.Array;
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
    private String title = "首页";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);

        viewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        /*recyclerView = (RecyclerView) view.findViewById(R.id.first_recycler_view);*/
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
        /*scrollView = (ScrollView) view.findViewById(R.id.scroll_view);
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
                            loadMore();
                        }
                        break;
                    }
                }
                return false;
            }
        });*/
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        /*LinearLayoutManager layoutManger = new LinearLayoutManager(getContext());*/
        initData(URL, title, type);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void initData(String URL, String title, final int type) {
        this.URL = URL;
        this.type = type;
        this.title = title;
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

    private void firstUI (String string) {
        titleText.setText(title);
        Gson gson = new Gson();
        final FirstData data = gson.fromJson(string, FirstData.class);
        TextView textview = new TextView(getContext());
        textview.setText("今日热闻");
        textview.setPadding(40,40,40,40);
        firstLayout.addView(textview);
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
            view.setOnClickListener(new MyClickListener(i, urls));
            firstLayout.addView(view);
        }

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
            view.setOnClickListener(new MyClickListener(i, topUrls));
        }
        MyAdapter adapter = new MyAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.setInterval(5000);
        viewPager.startAutoScroll();
        indicator.setViewPager(viewPager);
    }

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
            view.setOnClickListener(new MyClickListener(i, urls));
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

    private void loadMore() {
    }

    class MyClickListener implements View.OnClickListener {
        int position;
        private ArrayList<String> urls;

        public MyClickListener(int position, ArrayList<String> urls) {
            this.position = position;
            this.urls = urls;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            intent.putExtra("url", urls.get(position));
            startActivity(intent);
        }
    }

}
