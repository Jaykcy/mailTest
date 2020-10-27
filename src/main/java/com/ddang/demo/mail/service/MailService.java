package com.ddang.demo.mail.service;


import com.ddang.demo.util.FeedBack;
import com.ddang.demo.mail.vo.MailVO;

public interface MailService {

    FeedBack sendEmail(MailVO mailVo);
}