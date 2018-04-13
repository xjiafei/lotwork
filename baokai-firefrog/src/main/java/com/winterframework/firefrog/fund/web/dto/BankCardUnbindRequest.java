/**   
* @Title: BankCardUnbindRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: 银行卡删除绑定请求参数DTO 
* @author Denny  
* @date 2013-7-2 上午9:25:58 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BankCardUnbindRequest 
* @Description: 银行卡删除绑定请求参数DTO
* @author Denny 
* @date 2013-7-2 上午9:25:58 
*  
*/
public class BankCardUnbindRequest {

	/** 用户id */
	@NotNull
	private Long userId;
	/** MC银行id */
	@NotNull
	private Long mcBankId;
	/** 银行id */
	@NotNull
	private Long bankId;
	@NotNull
	private Long bindId;
	
	private Long bindcardType;
	
	private String nickName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMcBankId() {
		return mcBankId;
	}

	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getBindId() {
		return bindId;
	}

	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}

	public Long getBindcardType() {
		return bindcardType;
	}

	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
