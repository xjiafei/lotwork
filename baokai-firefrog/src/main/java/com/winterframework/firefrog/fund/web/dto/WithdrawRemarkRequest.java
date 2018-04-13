/**   
* @Title: WithdrawRemarkRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-24 下午2:52:13 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

/** 
* @ClassName: WithdrawRemarkRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-24 下午2:52:13 
*  
*/
public class WithdrawRemarkRequest implements Serializable {

	private static final long serialVersionUID = 779759955780576600L;

	private Long typeId;

	private String memo;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
