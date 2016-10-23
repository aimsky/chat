package com.qszxin.asterism.api;

/**
 * Created by wubo on 2016/3/2.
 *  封装smack 4.1协议，实现连接openfire服务器，登陆服务器，注册用户
 */

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.sasl.provided.SASLPlainMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

public class SmackTool {
    //private static int SERVER_PORT = 5222;
    private static String SERVER_HOST = "10.0.2.2";

    private static AbstractXMPPConnection connection = null;
    private static XMPPTCPConnectionConfiguration connConfig =
            XMPPTCPConnectionConfiguration.builder()
                    .setHost(SERVER_HOST)  // Name of your Host
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setPort(5222)          // Your Port for accepting c2s connection
                    .setDebuggerEnabled(true)
                    .setServiceName(SERVER_HOST)
                    .build();
    public static AbstractXMPPConnection getConnection(){
        if(connection != null)
            return connection;
        else{
            if(startConnection()){
                return connection;
            }else{
                return null;
            }
        }
    }
    private static boolean startConnection(){

        SASLPlainMechanism plain = new SASLPlainMechanism(); //设置验证方式
        SASLAuthentication.registerSASLMechanism(plain);

        /** 创建connection链接 */
        try {
             connection = new XMPPTCPConnection(connConfig);//连接服务器
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
     * @param attr 其他账户信息(邮箱等等)
     * @return boolean
     */
    public boolean register(String usrname,String password,Map<String,String> attr){
        AbstractXMPPConnection connection = getConnection();
        AccountManager accountManager = AccountManager.getInstance(connection); //账户管理
        accountManager.sensitiveOperationOverInsecureConnection(true);
        try {

            if(accountManager.supportsAccountCreation()){
                accountManager.createAccount(usrname,password,attr);  //创建账户
            }
            connection.login(usrname,password);
            return true;

        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
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
        AbstractXMPPConnection connection = getConnection();
        try {
            /** 登录 */
            connection.login(a, p);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
