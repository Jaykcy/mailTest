package com.ddang.demo.util;

public enum ErrorCodeEnum {
    SUCCESS(1000, "success", "成功"),
	FAIL(1008, "fail", "失败"),
	USER_NOT_LOGIN(1001, "notLogin", "您没有登录，请先登录"),
	USER_ACCOUNT_IS_NULL(1002, "accountNotNull", "账号不能为空"),
	USER_PASSWORD_IS_NULL(1003, "passwordNotNull", "密码不能为空"),
	USER_NO_PERMISSION(1004, "noPermission", "您没有权限操作"),
	USER_DISABLED(1005, "userDisabled", "用户已被禁用"),
	USER_ERROR(1006, "userError", "用户名或者密码错误"),
	USER_TIMEOUT(1007, "userTimeout", "会话已经超时，请重新登录"),
	MAIL_TO_IS_NULL(1009, "mailToIsNull", "收件人邮箱地址为空"),
	MAIL_SUBJECT_IS_NULL(1010, "mailSubjectIsNull", "邮箱主题为空"),
	MAIL_FROM_IS_NULL(1011, "mailFromIsNull", "发件人邮箱地址为空"),
	;

    /** 错误码 */
    private int code;
    
	/** 提示信息的Key */
	private String msg;

	/** 错误信息描述 */
	private String desc;

	private ErrorCodeEnum(int code, String msg, String desc) {
		this.code = code;
		this.msg = msg;
		this.desc = desc;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}