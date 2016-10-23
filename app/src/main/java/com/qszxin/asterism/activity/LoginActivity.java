package com.qszxin.asterism.activity;

/**
 * Created by wubo on 2016/3/2.
 */
import android.app.Activity;
import android.os.Bundle;
import com.qszxin.asterism.R;
import com.qszxin.asterism.api.SmackTool;


import android.os.Message;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class LoginActivity extends Activity {
    private LoginHandler handler ;
    private Button btn_login;
    private EditText text_username;
    private EditText text_password;
    private TextView btn_reg;
    private Intent regIntent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handler = new LoginHandler();

        btn_login =(Button)findViewById(R.id.id_loginbtn);
        text_username = (EditText)findViewById(R.id.id_userNameText);
        text_password= (EditText)findViewById(R.id.id_passwdText);
        btn_reg = (TextView)findViewById(R.id.id_regbtn);

        final SmackTool st = new SmackTool();

        //登陆按钮监听
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        /** 建立连接 */
                        try {
                            if (st.login(text_username.getText().toString(), text_password.getText().toString())) {
                                handler.sendEmptyMessage(handler.MSG_LOGIN_SUCCESS); //登陆成功，传递消息
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //return true;
                    }
                }.start();
            }
        });

        //注册按钮监听
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** 打开注册页面 */
                if(regIntent == null)
                    regIntent = new Intent();
                regIntent.setClass(getApplicationContext(), RegisterActivity.class);
                LoginActivity.this.finish();
                startActivity(regIntent);
            }

        });

    }
    private class LoginHandler extends Handler {
        /**
         * 网络登陆成功。
         */
        protected final int MSG_LOGIN_SUCCESS  = 2;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_LOGIN_SUCCESS:
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    //跳转到主界面
                    Intent mainIntent = new Intent();
                    mainIntent.setClass(getApplication(),MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username",text_username.getText().toString());
                    //把附加的数据放到意图当中
                    mainIntent.putExtras(bundle);
                    LoginActivity.this.finish();  //撤销登陆页面
                    startActivity(mainIntent);
                    break;
                default:
                    break;
            }
        }
    }
}