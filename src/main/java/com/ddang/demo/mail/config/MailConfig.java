package com.ddang.demo.mail.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class MailConfig {

    @Bean(name = "javaMailSender")
    public JavaMailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.qq.com");
        sender.setUsername("827970894@qq.com");
        sender.setPort(587);
        sender.setProtocol("smtp");
        sender.setPassword("jbheuwdirkhzbbjf");
        Properties pro = new Properties();
        pro.setProperty("mail.debug", "true");
        pro.setProperty("mail.smtp.timeout", "30000");
        pro.setProperty("mail.smtp.auth", "true");
        pro.setProperty("mail.smtp.starttls.enable", "true");
        pro.setProperty("mail.smtp.ssl.trust", "smtp.qq.com");
        pro.setProperty("mail.smtp.socketFactory.port", "587");
        pro.setProperty("mail.smtp.socketFactory.fallback", "false");
        sender.setJavaMailProperties(pro);
        return sender;
    }
}