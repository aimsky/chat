package com.qszxin.asterism.api;

/**
 * Created by 倾水折心 on 2016/3/2.
 */
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaIdFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.sasl.provided.SASLPlainMechanism;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.iqregister.packet.Registration;

import java.util.HashMap;
import android.util.Log;

import java.util.Map;

/**
 * Created by 倾水折心 on 2016/2/19.
 * 连接openfire服务器
 */
public class SmackTool {
    private static int SERVER_PORT = 5222;
    private static String SERVER_HOST = "59.71.78.227";
    private static String SERVER_NAME = "192.168.1.104";
    private static XMPPTCPConnection connection =null;

//    public SmackTool(Context mcontext){
//        this.context = mcontext;
//    }
    public static XMPPTCPConnection getConnection(){
        if(connection != null)
            return connection;
        else return null;
    }
    private boolean startConnection(){

        SASLPlainMechanism plain = new SASLPlainMechanism();
        SASLAuthentication.registerSASLMechanism(plain);


        /** 创建connection链接 */
        try {
             connection = new XMPPTCPConnection("xampp:admin@192.168.191.1","wbgood");

            //connection = new XMPPTCPConnection("admin","wbgood","con");

            /** 建立连接 */
            try {

                connection.connect();
                //connection.loginAnonymously();
                //saslAuthentication.authenticateAnonymously();
                Log.v("连接", "成功");
                return true;
                //register(context);
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            // TODO(clchen): Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    public boolean register(String usrname,String email,String password){
        if(startConnection()){
            Map<String, String> attr = new HashMap<String,String>();//用户注册的信息
            //attr.put("name",usrname);
            //attr.put("password",password);
            attr.put("email", email);
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
//            //Registration reg = new Registration(attr);
//            //设置类型
//            reg.setType(IQ.Type.set); //注册
//            //发送到哪
//            reg.setTo(connection.getServiceName());
//
//            //创建包过滤器
//            //PacketFilter filter = new PacketFilter(new PacketIDFilter(reg.getPacketID()), new PacketTypeFilter(IQ.class));
//            AndFilter filter = new AndFilter(new StanzaIdFilter(reg.getStanzaId()), new StanzaTypeFilter(IQ.class));
//            //创建包收集器
//            PacketCollector collector = connection.createPacketCollector(filter);
//            //发送包
//            try {
//                connection.sendStanza(reg);
//            } catch (SmackException.NotConnectedException e) {
//                e.printStackTrace();
//            }
//            //获取返回信息
//            IQ result = (IQ) collector.nextResult(SmackConfiguration.getDefaultPacketReplyTimeout());
//            // 取消收集
//            collector.cancel();
//            //通过返回信息判断
//            if (result == null) {
//                //Toast.makeText(context,"服务器异常" ,Toast.LENGTH_SHORT).show();
//                Log.v("注册结果", "服务器异常");
//            } else if (result.getType() == IQ.Type.error) {
//                if (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
//                    //Toast.makeText(context,"注册失败，用户已存在" , Toast.LENGTH_SHORT).show();
//                    Log.v("注册结果", "用户已存在");
//                } else {
//                    //Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
//                    Log.v("注册结果", result.getError().toString());
//                }
//            } else if (result.getType() == IQ.Type.result) {
//                //Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
//                Log.v("注册结果", "成功");
//                return true;
//            }
        }
        return false;
    }
    /**
     * 登录
     *
     * @param a 登录帐号
     * @param p 登录密码
     * @return
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
