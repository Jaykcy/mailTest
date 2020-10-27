package com.ddang.demo.activemq.consumer;


import com.alibaba.fastjson.JSONObject;
import com.ddang.demo.mail.vo.MailVO;
import com.ddang.demo.mail.vo.PropertiesVO;
import com.ddang.demo.util.COMMON;
import com.ddang.demo.util.FreeMakerUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.mail.Address;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;


@Service("consumerService")
public class ConsumerService {

    @Resource(name = "javaMailSender")
    private JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @JmsListener(destination = "myTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveTopic(MailVO message)
            throws JMSException {
        logger.debug("mail.queue start!");
        logger.debug(" message: " + JSONObject.toJSONString(message));
        logger.debug("from: " + PropertiesVO.mailFrom);
        if (message != null) {
            doSendEmail(message, false);
        }
        logger.debug("mail.queue end!");
    }

    private void doSendEmail(MailVO mailVo, boolean isReSend) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("827970894@qq.com");
            // 接收方
            if (ArrayUtils.isNotEmpty(mailVo.getTo())) {
                helper.setTo(mailVo.getTo());
            }
            // 抄送
            if (ArrayUtils.isNotEmpty(mailVo.getCc())) {
                helper.setCc(mailVo.getCc());
            }
            // 主题
            helper.setSubject(mailVo.getSubject());
            String html = "";
            // 邮件模版渲染内容
            if (StringUtils.isNotEmpty(mailVo.getTemplateName())) {
                if (mailVo.getParamMap() != null) {
                    html = FreeMakerUtil.geFreeMarkerTemplateContent(mailVo.getTemplateName(),
                            mailVo.getTemplatePath(), mailVo.getParamMap());
                }
                else {
                    html = FreeMakerUtil.geFreeMarkerTemplateContent(mailVo.getTemplateName(),
                            mailVo.getTemplatePath(), new HashMap<String, String>());
                }
                helper.setText(html, true);
            }
            else {
                // 文本内容
                helper.setText(mailVo.getHtmlContent(), true);
            }
            // 附件
            if (mailVo.getFiles() != null) {
                for (String file : mailVo.getFiles()) {
                    helper.addAttachment(file, new File(mailVo.getFilePath(file)));
                }
            }
            mailSender.send(mimeMessage);
        }
        catch (MailSendException e) {
            Set<String> tmpInvalidMails = getInvalidAddress(e);
            // 非无效收件人导致的异常，暂不处理
            if (tmpInvalidMails.isEmpty()) {
                logger.error("ConsumerService doSendEmail is error MailSendException ", e);
                return;
            }
            else {
                // 打印失败的邮箱地址
                Iterator<String> it = tmpInvalidMails.iterator();
                while (it.hasNext()) {
                    String mailStr = it.next();
                    logger.error(mailStr + " is error and send fail.please check");
                }
                if (!isReSend) {
                    // 排除失败的邮箱，重新发送邮件
                    String[] mailToArray = getRightMailAddress(mailVo.getTo(), tmpInvalidMails);
                    String[] mailCcArray = getRightMailAddress(mailVo.getCc(), tmpInvalidMails);
                    mailVo.setTo(mailToArray);
                    mailVo.setCc(mailCcArray);
                    doSendEmail(mailVo, true);
                }
            }
        }
        catch (Exception e) {
            logger.error("ConsumerService doSendEmail is error Exception", e);
        }
    }

    /**
     * 获取错误的邮箱地址
     *
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

    private String[] getRightMailAddress(String[] mailArr, Set<String> tmpInvalidMails) {
        List<String> newMailAdressList = new ArrayList<String>();
        for (int i = 0; i < mailArr.length; i++ ) {
            // 如果邮箱地址在失败的地址列表里面，则排除掉
            if (!isExist(tmpInvalidMails, mailArr[i])) {
                newMailAdressList.add(mailArr[i]);
            }
        }
        return COMMON.list2Array(newMailAdressList);
    }

    private boolean isExist(Set<String> tmpInvalidMails, String mailAddress) {
        Iterator<String> it = tmpInvalidMails.iterator();
        while (it.hasNext()) {
            String mailStr = it.next();
            if (StringUtils.equals(mailAddress.trim(), mailStr.trim())) {
                return true;
            }
        }
        return false;
    }
}