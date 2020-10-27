package com.ddang.demo.mail.vo;


import java.io.Serializable;


public class PropertiesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** header包含的字段 */
    public static final String HEADER_COL = "lang,ticket,token";

    public static String host;

    public static String password;

    public static int port;

    public static int timeout;

    public static int maxIdle;

    public static long maxWaitMillis;

    public static String noCheckUrl;

    public static int expire;

    public static int maxSession;

    public static long minIdle;

    public static long maxActive;

    public static String quartzEnable;

    public static String jobGroupName;

    public static String triggerGroupName;

    public static String appName;

    public static String mailFrom;

    public static String mailFtlPath;

    public static String mailFilePath;

    public static String activemqUrl;

    public static String activemqUser;

    public static String activemqPassword;

    public static String erpAppKey;

    public static String rsaPublicKey;

    public static String rsaPrivateKey;

    public static String mailRex;

    public static String mailHost;

    public static String mailUsername;

    public static String mailPassword;

    public static int mailPort;

    public static String mailProtocol;

    public static String mailDebug;

    public static String mailTimeout;

    public static String mailAuth;

    public static String mailEnable;

    public static String mailTrust;

    public static String mailFactoryPort;

    public static String mailFallback;
    
    public static String erpAddress;
    
    public static String erpCapitalAddress;
}