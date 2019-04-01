package com.syc.oa.util;


import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    public static boolean sendMail(String email, String title, String content) {
        Properties props = new Properties();
        //配置邮件服务器地址
        props.setProperty("mail.smtp.host","localhost");
        //连接服务器时是否需要验证
        props.setProperty("mail.smtp.auto","true");
        //设置发送邮件的协议,需在连接时传输用户名和密码
        props.setProperty("mail.smtp.protocol","smtp");
        //创建一个邮件会话
        Session session = Session.getInstance(props);
        //邮件消息
        MimeMessage msg = new MimeMessage(session);
        try {
            //发件人地址
            String fromAddress="xfpc@sun.com";
            msg.setFrom(new InternetAddress(fromAddress));
            //设置邮件主题
            msg.setSubject(title);
            //设置邮件内容
            msg.setContent(content,"text/html;charset=UTF-8");
            //邮件传输对象
            Transport transport = session.getTransport();
            //连接服务器，并设置用户名和密码
            String username = "xfpc@sun.com";
            String password = "123";
            transport.connect("localhost",25,username,password);
            //发送信息,并设置收件人地址
            transport.sendMessage(msg,new Address[]{new InternetAddress(email)});
            //释放连接
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
