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
import java.util.HashMap;
import com.qszxin.asterism.R;
import com.qszxin.asterism.api.SmackTool;
import java.util.Map;
/**
 * Created by wubo on 2016/3/2.
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

        regHandler = new RegHandler();  //注册Handler

        btn_reg =(Button)findViewById(R.id.id_s_regbtn);
        text_username = (EditText)findViewById(R.id.id_reg_username);//用户名
        text_password= (EditText)findViewById(R.id.id_reg_password);//用户密码
        text_email= (EditText)findViewById(R.id.id_reg_email); //用户邮箱信息

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
                            Map<String, String> attr = new HashMap<String,String>();//用户注册的信息
                            attr.put("email", text_email.getText().toString());//用户注册的邮箱信息

                            if (st.register(text_username.getText().toString(), text_password.getText().toString(),attr)) {
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
                RegisterActivity.this.finish();  //撤销注册页面
                startActivity(loginIntent);
            }

        });

    }

    private class RegHandler extends Handler {
        /**
         * 账户注册成功。
         */
        protected final int MSG_REG_SUCCESS  = 1;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_REG_SUCCESS:
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();

                    //跳转到主界面
                    Intent mainIntent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("username",text_username.getText().toString());
                    //把附加的数据放到意图当中
                    mainIntent.putExtras(bundle);
                    mainIntent.setClass(getApplication(),MainActivity.class);
                    RegisterActivity.this.finish();  //撤销登陆页面
                    startActivity(mainIntent);
                    break;
                default:
                    break;
            }
        }
    }
}
