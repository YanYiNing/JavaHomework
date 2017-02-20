package com.example.yanyining.tset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMgs();
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        /**
         * 设置send按钮的监听事件，添加新的Msg，清空输入栏，并刷新recyclerView。
         */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                Msg msg = new Msg(content, Msg.TYPE_SENT);
                msgList.add(msg);

                sendRequestWtihHttpURLConnection(content);
                adapter.notifyItemInserted(msgList.size() - 1);
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
                inputText.setText("");

            }
        });
    }

    private void sendRequestWtihHttpURLConnection(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                JSONObject json = new JSONObject();
                String string = null;
                /**
                 * 新建新的json数据，添加上key，info（查询内容），和userid。
                 */
                try {
                    json.put("key", "1d6fb93416e040f580cfea11033984c2");
                    json.put("info", str);
                    json.put("userid", "123456");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /**
                 *
                 */
                try {
                    URL url = new URL("http://www.tuling123.com/openapi/api");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setRequestProperty("Accept", "application/json; charset=UTF-8");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.write(json.toString().getBytes());
                    out.flush();
                    out.close();
                    if (connection.getResponseCode() == 200) {
                        InputStream in = connection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null){
                            response.append(line);
                        }
                        parseText(response.toString());
                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try{
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     *解析收到的json数据，回到ui线程刷新显示
     */
    private void parseText(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String string = null;
                try {
                    JSONObject jb = new JSONObject(str);
                    string = (jb.getString("text"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Msg msg = new Msg(string, Msg.TYPE_RECEIVED);
                msgList.add(msg);
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
                inputText.setText("");
            }
        });
    }
    private void initMgs() {
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        Msg msg2 = new Msg("Hello.Who is that?", Msg.TYPE_SENT);
        Msg msg3 = new Msg("This is Tom. Nice to talking to you.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        msgList.add(msg2);
        msgList.add(msg3);
    }
}
