package com.qszxin.asterism.api;

/**
 * Created by 倾水折心 on 2016/3/2.
 *  封装smack 4.1协议，实现连接openfire服务器，登陆服务器，注册用户
 */

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.sasl.provided.SASLPlainMechanism;
import org.jivesoftware.smackx.iqregister.AccountManager;
import android.util.Log;

import java.util.Map;

public class SmackTool {
    //private static int SERVER_PORT = 5222;
    //private static String SERVER_HOST = "59.71.78.227";
    //private static String SERVER_NAME = "192.168.1.104";
    private static XMPPTCPConnection connection =null;

    public static XMPPTCPConnection getConnection(){
        if(connection != null)
            return connection;
        else return null;
    }
    private boolean startConnection(){

        SASLPlainMechanism plain = new SASLPlainMechanism(); //设置验证方式
        SASLAuthentication.registerSASLMechanism(plain);

        /** 创建connection链接 */
        try {
             connection = new XMPPTCPConnection("xampp:admin@192.168.191.1","wbgood");//连接服务器
            /** 建立连接 */
            try {
                connection.connect();
                Log.v("连接", "成功");
                return true;
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            // TODO(clchen): Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 注册
     * @param usrname 登录帐号
     * @param password 登录密码
     * @param attr 其他账户信息
     * @return boolean
     */
    public boolean register(String usrname,String password,Map<String,String> attr){
        if(startConnection()){
            AccountManager accountManager = AccountManager.getInstance(connection); //账户管理
            try {
                if(accountManager.supportsAccountCreation()){
                    accountManager.createAccount(usrname,password,attr);  //创建账户
                }
                return true;

            } catch (SmackException.NoResponseException e) {
                e.printStackTrace();
            } catch (XMPPException.XMPPErrorException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 登录
     *
     * @param a 登录帐号
     * @param p 登录密码
     * @return boolean
     */
    public boolean login(String a, String p) {
        if (startConnection()) {
            try {
                /** 登录 */
                connection.login(a, p);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }else
            return false;
    }
}
