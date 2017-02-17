package com.example.yanyining.zhihudaily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.yanyining.zhihudaily.json.Aticle;
import com.example.yanyining.zhihudaily.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ArticleActivity extends AppCompatActivity {
    private TextView test;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        webView = (WebView) findViewById(R.id.web_view);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        ArrayList<String> urls = intent.getStringArrayListExtra("urls");
        String url = "https://news-at.zhihu.com/api/4/story/" + urls.get(position);

        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Aticle aticle = gson.fromJson(response.body().string(), Aticle.class);
                String css = "<link rel=\"stylesheet\" href=\"http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3\" type=\"text/css\">";
                String html = "<html><head>" + css + "</head><body>" + aticle.getBody() + "</body></html>";
                html = html.replace("<div class=\"img-place-holder\">", "");
                final String finalHtml = html;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadDataWithBaseURL("x-data://base", finalHtml, "text/html", "UTF-8", null);
                    }
                });

            }
        });
    }
}
