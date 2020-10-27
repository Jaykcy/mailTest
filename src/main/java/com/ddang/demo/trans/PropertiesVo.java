package com.ddang.demo.trans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Data


@Component
@PropertySource(value = "application.properties")
public class PropertiesVo  {

    private String[] to;//收件人

    private String[] cc;//抄送

    private String subject;//主题

    private String form;//发送人

    private String content;//内容

    private String templatePath;//模板地址

    private Map<String, Object> paramMap;//模板参数


}
