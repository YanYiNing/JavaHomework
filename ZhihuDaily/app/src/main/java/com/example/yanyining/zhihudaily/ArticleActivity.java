package com.example.yanyining.zhihudaily;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yanyining.zhihudaily.json.Article;
import com.example.yanyining.zhihudaily.json.Extra;
import com.example.yanyining.zhihudaily.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ArticleActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title;
    private WebView webView;
    private TextView comment;
    private TextView popularity;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    /*List<String> htmls = new ArrayList<String>();
    List<Article> articleList = new ArrayList<Article>();
    List<View> viewList;*/

    /**
     *直接用WebView来加载，放弃用ViewPager
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        /*viewPager = (ViewPager) findViewById(R.id.article_viewpager);
        viewList = new ArrayList<View>();*/
        imageView = (ImageView) findViewById(R.id.article_image);
        title = (TextView) findViewById(R.id.article_title);
        webView = (WebView) findViewById(R.id.web_view);
        comment = (TextView) findViewById(R.id.comment);
        popularity = (TextView) findViewById(R.id.popularity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.article_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String string = intent.getStringExtra("url");
        ArrayList<String> urls = intent.getStringArrayListExtra("urls");

        String url = "https://news-at.zhihu.com/api/4/story/" + string;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                final Article article = gson.fromJson(response.body().string(), Article.class);
                String css = "<link rel=\"stylesheet\" href=\"http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3\" type=\"text/css\">";
                String html = "<html><head>" + css + "</head><body>" + article.getBody() + "</body></html>";
                html = html.replace("<div class=\"img-place-holder\">", "");
                final String imageUrl = article.getImage();
                final String finalHtml = html;
                final String finalTitle = article.getTitle();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadDataWithBaseURL("x-data://base", finalHtml, "text/html", "UTF-8", null);
                        if (imageUrl != null){
                            Glide.with(ArticleActivity.this).load(imageUrl).into(imageView);
                        } else {
                            frameLayout.setVisibility(View.GONE);
                        }
                        title.setText(finalTitle);
                    }
                });
            }
        });

        url = "https://news-at.zhihu.com/api/4/story-extra/" + string;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                final Extra extra = gson.fromJson(response.body().string(), Extra.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        comment.setText(String.valueOf(extra.getComments()));
                        popularity.setText(String.valueOf(extra.getPopularity()));
                    }
                });
            }
        });
        //initData(urls);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void initData(ArrayList<String> urls) {

            for (String url : urls) {
                url = "https://news-at.zhihu.com/api/4/story/" + url;
                HttpUtil.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        Article article = gson.fromJson(response.body().string(), Article.class);
                        String css = "<link rel=\"stylesheet\" href=\"http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3\" type=\"text/css\">";
                        String html = "<html><head>" + css + "</head><body>" + article.getBody() + "</body></html>";
                        html = html.replace("<div class=\"img-place-holder\">", "");
                        htmls.add(html);
                        articleList.add(article);
                        final String finalHtml = html;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                View view = LayoutInflater.from(ArticleActivity.this).inflate(R.layout.article_fragment, viewPager, false);
                                WebView webView = (WebView) view.findViewById(R.id.web_view);
                                webView.loadDataWithBaseURL("x-data://base", finalHtml, "text/html", "UTF-8", null);
                                viewList.add(view);
                                MyAdapter adapter = new MyAdapter(viewList);
                                viewPager.setAdapter(adapter);
                            }
                        });

                    }
                });
            }

        }*/
    private void initUI() {

        /*for (String html : htmls) {
            test.setText(html);
        }*/

    }
}
