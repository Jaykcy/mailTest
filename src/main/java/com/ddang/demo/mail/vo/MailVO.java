package com.ddang.demo.mail.vo;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;


public class MailVO implements Serializable {

    private static final long serialVersionUID = -5417934524673214046L;

    private String[] to;

    private String[] cc;

    /** 发送方地址 */
    private String from;

    /**
     * 主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String htmlContent;

    /**
     * 邮件模板
     */
    private String templateName;

    /** 模板路径地址 */
    private String templatePath;

    private Map<String, Object> paramMap;

    private String[] files;

    private String mailToken;

    public MailVO() {
        mailToken = String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String TemplatePath) {
        if (StringUtils.isNotBlank(TemplatePath)) {
            int index = StringUtils.lastIndexOf(TemplatePath, "/")+1;
            String str = StringUtils.substring(TemplatePath, index, TemplatePath.length());
            this.templateName = str;
        }
        else {
            this.templateName = "";
        }
    }

    public Map<String, ?> getParamMap() {
        return paramMap;
    }

    public String getMailToken() {
        return mailToken;
    }

    public void setMailToken(String mailToken) {
        this.mailToken = mailToken;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public void addFile(String fileName, InputStream input)
            throws IOException {
        String filePath = getFilePath(fileName);
        File file = new File(filePath);
        FileUtils.copyInputStreamToFile(input, file);
        setFile(fileName);
    }

    public void addFile(File file)
            throws IOException {
        String fileName = file.getName();
        if (file.exists() && file.isFile()) {
            String filePath = getFilePath(fileName);
            File destFile = new File(filePath);
            FileUtils.copyFile(file, destFile);
            setFile(fileName);
        }
    }

    public void addFile(String file)
            throws IOException {
        addFile(new File(file));
    }

    private void setFile(String fileName) {
        this.files = (String[])ArrayUtils.add(this.files, 0, fileName);
    }

    public String getFilePath(String fileName) {
        String filePath = PropertiesVO.mailFilePath + File.separator + this.mailToken;
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Make directory: " + filePath);
            file.mkdirs();
        }
        return filePath + File.separator + fileName;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
}