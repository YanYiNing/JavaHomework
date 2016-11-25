package com.example.yanyining.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    private Button reg;
    private EditText count;
    private EditText pwd;
    private TextView state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        reg= (Button) findViewById(R.id.SignIn);
        count= (EditText) findViewById(R.id.UserName);
        pwd= (EditText) findViewById(R.id.Password);
        state= (TextView) findViewById(R.id.State);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=count.getText().toString().trim();
                String pass=pwd.getText().toString().trim();
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);

                User user=new User();
                user.setUsername(name);
                user.setPassword(pass);

                int result=SqliteDB.getInstance(getApplicationContext()).saveUser(user);
                if (result==1){
                    state.setText("注册成功！");
                    startActivity(intent);
                }else  if (result==-1)
                {
                    state.setText("用户名已经存在！");
                }
                else
                {
                    state.setText("！");
                }

            }
        });
    }
}
