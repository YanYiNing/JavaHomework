package com.example.yanyining.zhihudaily;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yanyining.zhihudaily.json.FirstData;
import com.example.yanyining.zhihudaily.json.OtherData;
import com.example.yanyining.zhihudaily.util.HttpUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    ImageView splashPicImg;
    ImageView logoPicImg;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_splash);

        splashPicImg = (ImageView) findViewById(R.id.splash__pic);
        logoPicImg = (ImageView) findViewById(R.id.logo_pic);
        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String splash = prefs.getString("splash_pic", null);
        if (splash != null) {
            Glide.with(this).load(splash).crossFade(500).into(splashPicImg);
        } else {
            loadSplashPic();
        }*/
        loadSplashPic();
        //initData();
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                //intent.putExtra("data", data);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        },2000);

    }

    /**
     * 请求到所有的JSON数据，转化成AllData对象，传给MainActivity
     */
   /* private void initData() {

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/stories/latest", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                FirstData firstData = gson.fromJson(string, FirstData.class);
                data.setFirstData(firstData);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/2", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews2(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/3", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews3(news);
                int i = 0;
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/4", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews4(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/5", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews5(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/6", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews6(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/7", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews7(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/8", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews8(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/9", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews9(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/10", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews10(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/11", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews11(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/12", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews12(news);
            }
        });

        HttpUtil.sendOkHttpRequest("https://news-at.zhihu.com/api/4/theme/13", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                OtherData news = gson.fromJson(string, OtherData.class);
                data.setNews13(news);
            }
        });
    }*/

    /**
     * 请求图片的URl地址，加载启动图像
     */
    private void loadSplashPic() {
        final String request = "https://news-at.zhihu.com/api/7/prefetch-launch-images/1080*1668";
        HttpUtil.sendOkHttpRequest(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String splash = response.body().string();
                String url = null;
                try {
                    JSONObject jsonObject = new JSONObject(splash);
                    JSONArray jsonArray = jsonObject.getJSONArray("creatives");
                    String string = jsonArray.getJSONObject(0).toString();
                    JSONObject jsonUrl = new JSONObject(string);
                    url = jsonUrl.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).edit();
                editor.putString("splash_pic", url);
                editor.apply();*/
                final String finalUrl = url;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int logoId = R.drawable.logo;
                        Glide.with(SplashActivity.this).load(finalUrl).into(splashPicImg);
                        Glide.with(SplashActivity.this).load(logoId).into(logoPicImg);
                    }
                });
            }
        });
    }
}
