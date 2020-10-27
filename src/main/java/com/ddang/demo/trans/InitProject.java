package com.ddang.demo.trans;

import com.ddang.demo.mail.service.MailService;
import com.ddang.demo.mail.vo.MailVO;
import com.ddang.demo.util.FeedBack;
import com.ddang.demo.util.FreeMakerUtil;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.mail.Address;
import javax.mail.SendFailedException;
import java.util.*;

@Logger
@Configuration
@Component
public class InitProject implements ApplicationRunner {

    // 静态初使化当前类
    private static InitProject initProject;
    @PostConstruct
    public void init() {
        initProject = this;
    }
    // 在方法上加上注解@PostConstruct，这样方法就会在Bean初始化之后被Spring容器执行（注：Bean初始化包括，实例化Bean，并装配Bean的属性（依赖注入））。

    private EmailUtil emailUtil;
    /*@Autowired
    private Application application;*/
    @Autowired
    private PropertiesVo propertiesVo;

    @Autowired
    private MailService mailService;

    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean

    @Value("${spring.mail.username}")
    private String from; //读取配置文件中的参数

    @Value("Jaykcy@163.com")
    private String sendTo;
    private  int i = 0;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("初始化项目......");
        //initProject.timeOut();
        sendMail();
        System.out.println("邮件发送完成......");
    }

    public Boolean sendMail() throws Exception{
        Boolean status=false;
        SimpleMailMessage message = new SimpleMailMessage();
        MailVO mail = new MailVO();
        // 抄送
        //mail.setCc(new String[]{"827970894@qq.com"});
        // 接收
        mail.setTo(new String[]{"Jaykcy@163.com"});
        // 来自
        mail.setFrom("827970894@qq.com");
        // 主题
        mail.setSubject("Mail reminder of order delivery success（订单出库成功邮件提醒）");//项目立项完成邮件提醒
        // 内容
        //mail.setHtmlContent(content);
        mail.setTemplatePath("/template/freemaker/mail/sendMail.ftl");
        mail.setTemplateName(mail.getTemplatePath());
        Map paramMap=new HashMap();
        String contentZHS="您申请的IPhone12出库申请已经审批通过，出库编号为:Test"+1234+",请悉知!";
        String contentUS="The iPhone 12 delivery application you applied for has been approved, and the delivery number is: "+1234+"), Please be informed";
        paramMap.put("contentZHS",contentZHS);
        paramMap.put("contentUS",contentUS);
        paramMap.put("disclaimer", FreeMakerUtil.getStringByFile("/template/freemaker/mail/disclaimer.html"));
        mail.setParamMap(paramMap);
        FeedBack feedBackStatus=initProject.mailService.sendEmail(mail);
        if(feedBackStatus.getStatus()==200){
            status=true;
        }
        return status;


    }


//////////////////////////////////////////////////////////////////////////////////
    public void timeOut(){
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run(){
                try {
                    i++;
                    System.out.println("第："+ i +"次发送邮件");
                    initProject.sendMail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },60000,60000);
    }
    /**
     * 将收件人地址进行处理（可添加检验）
     */
    public String[] emailAddress(ArrayList<String> list){
        String[] strings = new String[list.size()];
        return  list.toArray(strings);

    }

    /**
     * 移除错误的邮件地址
     * @param e
     * @return
     */
    private Set<String> getInvalidAddress(MailSendException e) {
        Set<String> mails = new HashSet<>();
        for (Exception exception : e.getFailedMessages().values()) {
            if (exception instanceof SendFailedException) {
                for (Address address : ((SendFailedException)exception).getInvalidAddresses()) {
                    mails.add(address.toString());
                }
            }
        }
        return mails;
    }


}
