package com.qszxin.asterism.activity;

import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qszxin.asterism.R;
import com.qszxin.asterism.api.SmackTool;

/**
 * Created by 倾水折心 on 2016/3/2.
 */
public class RegisterActivity extends Activity {

    private EditText text_username;
    private EditText text_password;
    private EditText text_email;

    private TextView btn_exist;
    private Button btn_reg;

    private Intent loginIntent = null;

    private RegHandler regHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regHandler = new RegHandler();

        btn_reg =(Button)findViewById(R.id.id_s_regbtn);
        text_username = (EditText)findViewById(R.id.id_reg_username);
        text_password= (EditText)findViewById(R.id.id_reg_password);
        text_email= (EditText)findViewById(R.id.id_reg_email);

        btn_exist = (TextView)findViewById(R.id.id_existbtn);

        final SmackTool st = new SmackTool();

        //注册按钮监听
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        /** 建立连接 */
                        try {
                            if (st.register(text_username.getText().toString(),text_email.getText().toString(), text_password.getText().toString())) {
                                regHandler.sendEmptyMessage(regHandler.MSG_REG_SUCCESS); //注册成功，传递消息
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
        btn_exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** 打开注册页面 */
                if(loginIntent == null)
                    loginIntent = new Intent();
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                loginIntent.setClass(getApplicationContext(), LoginActivity.class);
                RegisterActivity.this.finish();
                startActivity(loginIntent);
                //return true;
            }

        });

    }

    private class RegHandler extends Handler {
        /**
         * 网络连接成功。
         */
        protected final int MSG_REG_SUCCESS  = 1;
        //protected final int MSG_LOGIN_SUCCESS  = 2;
        /**
         * 请求暂停轮播。
         */
        protected RegHandler(){
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REG_SUCCESS:
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    break;
//                case MSG_LOGIN_SUCCESS:
//                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
//                    break;
                default:
                    break;
            }
        }
    }
}
