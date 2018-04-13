/**   
* @Title: ExceptionRefundRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-17 上午11:24:04 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.winterframework.firefrog.common.convert.BeanConverter.Alias;

/** 
* @ClassName: ExceptionRefundRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-17 上午11:24:04 
*  
*/
public class ExceptionRefundRequest implements Serializable {

	private static final long serialVersionUID = -6667017423993851297L;

	@Alias(field = "id")
	private Long exceptId;

	private Long bankId;

	private String cardAcct;

	private String cardNumber;

	private String bankName;

	private String bankAddr;
	@Alias(field = "attachment")
	private String attachMent;

	@Alias(field = "apprMemo")
	private String applyMemo;
	//3复审 4通过
	private Long status;
	private String currApprer;
	private Date currDate;
	private Long currStatus;
	private Long userId;

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getExceptId() {
		return exceptId;
	}
	

	public String getCurrApprer() {
		return currApprer;
	}


	public void setCurrApprer(String currApprer) {
		this.currApprer = currApprer;
	}


	public Date getCurrDate() {
		return currDate;
	}


	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}


	public void setExceptId(Long exceptId) {
		this.exceptId = exceptId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getCardAcct() {
		return cardAcct;
	}

	public void setCardAcct(String cardAcct) {
		this.cardAcct = cardAcct;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAddr() {
		return bankAddr;
	}

	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}


	public Long getCurrStatus() {
		return currStatus;
	}


	public void setCurrStatus(Long currStatus) {
		this.currStatus = currStatus;
	}

}
