package com.ddang.demo.util;


import net.sf.json.JSONArray;


/**
 * 内容摘要：操作后的返馈对象 返回状态、提示消息和其他需要返回的属性 完成日期：2014-12-3 编码作者：袁东
 */
public class FeedBack {

    /**
     * 操作状态，值为枚举Status中的值 比如：200为操作成功
     */
    private int status;

    /**
     * 提示消息， 可以直接给Status中的显示值
     */
    private String message;

    /**
     * 参数集合，存放其他需要返回的参数 可通过相应 addAttribute 方法来添加参数 或其他方法来得到参数、清空所有参数
     */
    private Attributes attributes;
    /**
     * 存放业务上错误JSON
     * */
    private JSONArray errorJSON;
    /**
     * 构造方法
     */
    public FeedBack(Status status, Attributes attributes) {
        this.attributes = attributes;
        if (this.attributes == null) {
            attributes = new Attributes();
        }
        this.status = status.getId();
        this.message = status.getDisplay();
    }

    /**
     * 构造方法
     */
    public FeedBack() {
        attributes = new Attributes();
    }

    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * 按状态得到返回对象 示例： FeedBack.getInstance(Status.SUCCESS);
     * 
     * @param status
     *            Status枚举类，详细请参考枚举说明
     * @return
     */
    public static FeedBack getInstance(Status status) {
        return new FeedBack(status, null);
    }

    /**
     * 按状态得到返回对象 示例： FeedBack.getInstance(Status.SUCCESS);
     * 
     * @param status
     *            Status枚举类，详细请参考枚举说明
     * @return
     */
    public static FeedBack getInstance(Status status, ErrorCodeEnum errorCodeEnum) {
        Attributes attr = new Attributes();
        attr.addAttribute("errorCode", errorCodeEnum.getCode());
        attr.addAttribute("errorMsg", errorCodeEnum.getMsg());
        attr.addAttribute("errorDesc", errorCodeEnum.getDesc());
        return new FeedBack(status, attr);
    }

    /**
     * 按状态得到返回对象 示例： Attributes attributes = Attributes.getInstance();
     * attributes.addAttribute(xxxx); FeedBack.getInstance(Status.SUCCESS, attributes);
     * 
     * @param status
     *            Status枚举类，详细请参考枚举说明
     * @param attributes
     *            返回的参数
     * @return
     */
    public static FeedBack getInstance(Status status, Attributes attributes) {
        return new FeedBack(status, attributes);
    }

    /**
     * 操作状态枚举
     * 
     * @author YuanDong
     */
    public enum Status {
        // 操作成功
        SUCCESS(200, "feedBack.status.success"),
        // 操作失败，业务类型的错误(如捕获到了业务处理异常名称重复DuplicationNameExceptionq)
        FAIL(300, "feedBack.status.fail"),
        // 操作失败，系统类型的错误，系统缺陷导致的错误(如空指针等)
        ERROR(500, "feedBack.status.error"),
    	//终止后台操作，适用于前台校验 后台返回错误码
    	RETURN(520,"Return operation");
    	
        private int id; // Id值

        private String display; // 显示值

        Status(int id, String display) {
            Status.this.id = id;
            Status.this.display = display;
        }

        /**
         * 得到Id值
         * 
         * @return
         */
        public int getId() {
            return id;
        }

        /**
         * 得到显示值
         * 
         * @return
         */
        public String getDisplay() {
            return display;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONArray getErrorJSON() {
        return errorJSON;
    }

    public void setErrorJSON(JSONArray errorJSON) {
        this.errorJSON = errorJSON;
    }
}