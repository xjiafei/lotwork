/**   
* @Title: ExceptionCoinRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-17 上午11:22:43 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import com.winterframework.firefrog.common.convert.BeanConverter.Alias;

/** 
* @ClassName: ExceptionCoinRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-17 上午11:22:43 
*  
*/
public class ExceptionCoinRequest implements Serializable {

	private static final long serialVersionUID = 2255473639268352519L;

	@Alias(field = "id")
	private Long exceptId;

	private Long userId;

	@Alias(field = "attachment")
	private String attachMent;

	@Alias(field = "apprMemo")
	private String applyMemo;

	public Long getExceptId() {
		return exceptId;
	}

	public void setExceptId(Long exceptId) {
		this.exceptId = exceptId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAttachMent() {
		return attachMent;
	}

	public void setAttachMent(String attachMent) {
		this.attachMent = attachMent;
	}

	public String getApplyMemo() {
		return applyMemo;
	}

	public void setApplyMemo(String applyMemo) {
		this.applyMemo = applyMemo;
	}

}
