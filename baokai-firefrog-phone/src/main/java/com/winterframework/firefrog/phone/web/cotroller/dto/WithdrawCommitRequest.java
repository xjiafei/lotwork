package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class WithdrawCommitRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5534563318220897650L;
	private Double money;//	提现金额
//	private String cardid;//	银行id
//	private Integer bankId;//	银行编号
	private String secpwd;//	资金密码
	private Integer questionId;//	questionId
	private String questionpwd;//	安全問題密碼
	private Long ipAddr;
	private Long bindId; 
	
	public Long getBindId() {
		return bindId;
	}
	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}
	public Long getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(Long ipAddr) {
		this.ipAddr = ipAddr;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getQuestionpwd() {
		return questionpwd;
	}
	public void setQuestionpwd(String questionpwd) {
		this.questionpwd = questionpwd;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	/*public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}*/
	public String getSecpwd() {
		return secpwd;
	}
	public void setSecpwd(String secpwd) {
		this.secpwd = secpwd;
	}

}
