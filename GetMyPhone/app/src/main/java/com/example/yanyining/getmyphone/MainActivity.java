package com.example.yanyining.getmyphone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetNumber.getNumber(this);

        lv = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter(GetNumber.lists, this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + GetNumber.lists.get(position).getNumber()));
                startActivity(dialIntent);
            }
        });


    }
}
