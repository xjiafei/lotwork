package com.winterframework.firefrog.game.web.bet.operator;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Title 投注操作支持类
 * @Description
 * 	提供投注页面显示及提交过程中所需的业务不相关的支持方法
 * 	1. 从JSON数据到对象的转换
 * 	2. 读取定义的配置项：配置项分基本配置项和扩展配置项，基本配置项为全彩种适用配置，扩展配置项为彩种自定义配置。当扩展配置项和基本配置项相同时，优先使用扩展配置项
 * 	3. 读取定义的文本信息：文本信息分基本文本信息和扩展文本信息，基本文本信息为全彩种适用文本信息，扩展文本信息为彩种自定义文本信息。当扩展文本信息和基本文本信息相同时，优先使用扩展文本信息
 * 	4. 格式化金额 * 倍数
 * 
 * @author bob
 *
 */
public abstract class BetSupport {

	//基本配置项
	private Map<String, String> properties;

	//基本文本信息
	private Map<String, String> messages;

	//扩展配置项，由各彩种添加
	private Map<String, String> extendedProperties;

	//扩展文本信息，由各彩种添加
	private Map<String, String> extendedMessages;

	private ObjectMapper jsonMapper = new ObjectMapper();

	/**
	 * 从JSON数据到对象的转换
	 * 
	 * @param json
	 * @param objClass
	 * @return
	 * @throws Exception
	 */
	protected <T> T convertJsonToObject(String json, Class<T> objClass) throws Exception {
		return jsonMapper.readValue(json, objClass);
	}

	/**
	 * 所有金额统以乘以10000
	 * 
	 * @param amount
	 * @return
	 */
	protected long formatMultipleMoney(Double amount) {
		return ((Double) (amount * getLongProperty("moneyMultiple"))).longValue();
	}

	protected long formatMultipleMoney(String amount) {
		return formatMultipleMoney(Double.valueOf(amount.replace(",", "")));
	}

	protected long formatMultipleMoney(Integer amount) {
		return formatMultipleMoney((double) amount);
	}

	/**
	 * 获取配置项
	 * 
	 * @param propName
	 * @return
	 */
	protected String getProperty(String propName) {
		if (extendedProperties != null && extendedProperties.containsKey(propName)) {
			return extendedProperties.get(propName);
		}
		return properties == null ? null : properties.get(propName);
	}

	/**
	 * 获取整型配置项
	 * 
	 * @param propName
	 * @return
	 */
	protected int getIntProperty(String propName) {
		try {
			return Integer.parseInt(getProperty(propName));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 获取长整型配置项
	 * 
	 * @param propName
	 * @return
	 */
	protected long getLongProperty(String propName) {
		try {
			return Long.parseLong(getProperty(propName));
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 获取文本信息
	 * 
	 * @param key
	 * @return
	 */
	protected String getMessage(String key) {
		if (extendedMessages != null && extendedMessages.containsKey(key)) {
			return extendedMessages.get(key);
		}
		return messages == null ? null : messages.get(key);
	}

	/**
	 * 获取文本信息，并替换关键词
	 * 
	 * 根据传入的params数组，来替换对应下标的关键词${n}。
	 * 例如：
	 * 数组:{"重", "时", "彩"}
	 * 文本:${0}庆${1}时${2}
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	protected String getMessage(String key, String[] params) {
		String msg = getMessage(key);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				msg = StringUtils.replace(msg, "{" + i + "}", params[i]);
			}
		}
		return msg;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}

	public void setExtendedProperties(Map<String, String> extendedProperties) {
		this.extendedProperties = extendedProperties;
	}

	public void setExtendedMessages(Map<String, String> extendedMessages) {
		this.extendedMessages = extendedMessages;
	}

}
