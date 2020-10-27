package com.ddang.demo.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 内容摘要：参数对象，
 * 如果定义的模式需要传递其他参数，可以加入此参数对象引用或继承
 * 完成日期：2015-2-13
 * 编码作者：袁东
 */
public class Attributes extends HashMap<String, Object>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9091213072100977813L;

	
	public static Attributes getInstance() {
	    return new Attributes();
	}
	
	public Attributes() {}

	public Attributes(String attrName, Object attrValue) {
		this.addAttribute(attrName, attrValue);
	}

	/**
	 * 添加返回参数
	 * @param attrName  参数名称
	 * @param attrValue 参数值
	 */
	public void addAttribute(String attrName, Object attrValue) {
		this.put(attrName, attrValue);
	}
	
	/**
	 * 添加返回参数，参数为实体，会自动转成Map<String,String>的格式
	 * @param bean 实体
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public void addAttribute(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		this.putAll(org.apache.commons.beanutils.BeanUtils.describe(bean));
	}
	
	/**
	 * 合并参数，相同名称的参数将被覆盖
	 * @param attrMap 需要合并的参数集合
	 */
	public void mergeAttributes(Map<String, Object> attrMap) {
		this.putAll(attrMap);
	}
	
	/**
	 * 添加所有参数
	 * @param attrMap
	 */
	public void addAllAttributes(Map<String, Object> attrMap) {
        if(attrMap != null) {
            this.clear();
            this.putAll(attrMap);
        }
	}

    /**
	 * 添加所有参数
	 * @param attributes
	 */
	public void addAllAttributes(Attributes attributes) {
        if(attributes != null) {
            this.clear();
            this.putAll(attributes);
        }
	}
	
	/**
	 * 按参数名称得到参数
	 * @param attrName 参数名称
	 * @return
	 */
	public Object getAttribute(String attrName) {
		return this.get(attrName);
	}
	
	/**
	 * 得到所有参数
	 * @return
	 */
	public Map<String, Object> getAttributes() {
		return this;
	}
	
	/**
	 * 清空所有参数
	 */
	public void clearAttributes() {
		this.clear();
	}

}
