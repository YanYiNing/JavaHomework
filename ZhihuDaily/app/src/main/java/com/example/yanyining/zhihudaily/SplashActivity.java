package com.example.yanyining.zhihudaily;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yanyining.zhihudaily.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 1000);
    }

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
