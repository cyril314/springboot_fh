package com.fit.common.utils;

import java.util.List;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * Spring Mail发送邮件工具类
 */
@Slf4j
@Component
public class SpringMailUtil {

    @Autowired
    private MailSender mailSender;
    @Autowired
    private JavaMailSenderImpl sender;

    /**
     * @param host    邮件服务器
     * @param PORT    端口
     * @param EMAIL   本邮箱账号
     * @param PAW     本邮箱密码
     * @param toEMAIL 对方箱账号
     * @param TITLE   标题
     * @param CONTENT 内容
     * @param TYPE    1：文本格式;2：HTML格式
     */
    public static void sendEmail(String host, String PORT, String EMAIL, String PAW, String toEMAIL, String TITLE, String CONTENT, String TYPE) {
        try {
            // 创建使用参数
            Properties javaMailProperties = new Properties();
            javaMailProperties.put("mail.smtp.auth", true);
            javaMailProperties.put("mail.smtp.port", PORT);
            javaMailProperties.put("mail.smtp.timeout", 60000);
            javaMailProperties.put("mail.smtp.ssl.enable", true);
            // 创建邮件对象
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setHost(host);
            javaMailSender.setUsername(EMAIL);
            javaMailSender.setPassword(PAW);
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8"); // 解决乱码问题
            helper.setTo(toEMAIL);
            helper.setSubject(TITLE);
            helper.setFrom(EMAIL);
            helper.setText(CONTENT, "1".equals(TYPE) ? false : true);
            javaMailSender.send(mail); // 发送
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发
     *
     * @param e_title    主题
     * @param body       内容
     * @param recipients 收件人
     */
    public boolean send(String e_title, String body, List<String> recipients) {
        if (recipients.size() > 0) {
            try {
                String[] tos = recipients.toArray(new String[0]);
                return sendHtml(null, e_title, body, false, tos);
            } catch (MailException e) {
                log.error("群发送邮件失败", e);
            }
        }
        return false;
    }

    /**
     * 发送邮件
     *
     * @param e_title   主题
     * @param body      内容
     * @param e_address 收件人
     */
    public boolean send(String e_title, String body, String... e_address) {
        return sendHtml(null, e_title, body, false, e_address);
    }

    /**
     * 发送邮件
     *
     * @param e_from    发件人
     * @param e_title   主题
     * @param body      内容
     * @param e_address 收件人
     */
    public boolean sendHtml(String e_from, String e_title, String body, boolean html, String... e_address) {
        try {
            MimeMessage mail = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8"); // 解决乱码问题
            helper.setTo(e_address);
            helper.setSubject(e_title);
            if (e_from != null) {
                helper.setFrom(e_from);
            }
            // 邮件内容，第二个参数指定发送的是HTML格式
            helper.setText(body, html);
            sender.send(mail); // 发送
            return true;
        } catch (Exception e) {
            log.error("发送邮件失败", e);
        }
        return false;
    }
}
