package com.ddang.demo.mail.service.impl;


import com.ddang.demo.activemq.producer.ProducerService;
import com.ddang.demo.mail.service.MailService;
import com.ddang.demo.mail.vo.MailVO;
import com.ddang.demo.mail.vo.PropertiesVO;
import com.ddang.demo.util.COMMON;
import com.ddang.demo.util.ErrorCodeEnum;
import com.ddang.demo.util.FeedBack;
import com.ddang.demo.util.FeedBack.Status;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProducerService ProducerService;

    @Override
    public FeedBack sendEmail(MailVO mailVo) {
        /*// 过滤掉收件邮箱地址格式错误的邮箱
        if(!COMMON.isEmpty(mailVo.getTo())){
            List<String> mailAddressList=checkMailTo(mailVo.getTo());
            String[] mailArray=COMMON.list2Array(mailAddressList);
            mailVo.setTo(mailArray);
        }
        // 过滤掉抄送邮箱地址格式错误的邮箱
        if(!COMMON.isEmpty(mailVo.getCc())){
            List<String> mailCcList = checkMailTo(mailVo.getCc());
            String[] mailCc = COMMON.list2Array(mailCcList);
            mailVo.setCc(mailCc);
        }
        // 校验收件人信息是否为空
        if (ArrayUtils.isEmpty(mailVo.getTo())) {
            return FeedBack.getInstance(Status.FAIL, ErrorCodeEnum.MAIL_TO_IS_NULL);
        }
        // 校验是否有邮件主题
        if (StringUtils.isBlank(mailVo.getSubject())) {
            return FeedBack.getInstance(Status.FAIL, ErrorCodeEnum.MAIL_SUBJECT_IS_NULL);
        }
        // 校验是否有发件人信息
        if (StringUtils.isBlank(mailVo.getFrom())) {
            return FeedBack.getInstance(Status.FAIL, ErrorCodeEnum.MAIL_FROM_IS_NULL);
        }*/
        try {
            ProducerService.sendMessage(mailVo);
            return FeedBack.getInstance(Status.SUCCESS, ErrorCodeEnum.SUCCESS);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return FeedBack.getInstance(Status.FAIL, ErrorCodeEnum.FAIL);
        }
    }

    /** 检查收件邮箱地址是否正确，不正确的排除掉 */
    private List<String> checkMailTo(String[] mailToArr) {
        List<String> mailAddrssList = new ArrayList<String>();
        if (ArrayUtils.isNotEmpty(mailToArr)) {
            for (int i = 0; i < mailToArr.length; i++ ) {
                if (checkMailAddress(mailToArr[i])) {
                    mailAddrssList.add(mailToArr[i].trim());
                }
            }
        }
        return mailAddrssList;
    }

    /** 检查收件邮箱地址 */
    private boolean checkMailAddress(String mailAddress) {
        // 使用正则表达式判断
        boolean matches = mailAddress.matches(PropertiesVO.mailRex);
        // 输出判断结果正确为true 错误为false
        if (!matches) {
            logger.error("mailAddress:" + mailAddress + "is error,please check mail address");
        }
        return matches;
    }
}