package com.example.yanyining.zhihudaily;

import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yanyining.zhihudaily.fragment.FirstFragment;

public class MainActivity extends AppCompatActivity {
    Button menu;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    final int FIRST_TYPE = 0;
    final int OTHER_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment("https://news-at.zhihu.com/api/4/stories/latest", FIRST_TYPE);
        //menu = (Button) findViewById(R.id.drawer_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemTextColor(null);
        View view = navigationView.getHeaderView(0);
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.first_news_item);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment("https://news-at.zhihu.com/api/4/stories/latest", FIRST_TYPE);
                drawerLayout.closeDrawers();
                linearLayout.setBackgroundColor(0xFFE0E0E0);
                navigationView.getMenu().setGroupCheckable(R.id.group, false, true);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                linearLayout.setBackgroundColor(0xFFFCFCFC);
                navigationView.getMenu().setGroupCheckable(R.id.group, true, true);
                switch (item.getItemId()) {
                    case R.id.news_2 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/2", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_3 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/3", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_4 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/4", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_5 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/5", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_6 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/6", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_7 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/7", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_8 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/8", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_9 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/9", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_10 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/10", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_11 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/11", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_12 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/12", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    case R.id.news_13 :
                        replaceFragment("https://news-at.zhihu.com/api/4/theme/13", OTHER_TYPE);
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        break;
                    default:
                }
                return false;
            }
        });
    }

    private void replaceFragment (String URL, int type) {
        FirstFragment fragment = new FirstFragment();
        fragment.initData(URL, type);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
