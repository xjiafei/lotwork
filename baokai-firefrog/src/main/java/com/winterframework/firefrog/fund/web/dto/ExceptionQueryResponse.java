/**   
* @Title: ExceptionQueryResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-16 下午4:42:38 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

/** 
* @ClassName: ExceptionQueryResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-16 下午4:42:38 
*  
*/
public class ExceptionQueryResponse implements Serializable {

	private static final long serialVersionUID = 7950147138676489537L;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date mcExactTime;

	private Long bankId;

	private String cardAcct;

	private String cardNumber;

	private String bankName;

	private String bankAddr;
	private String recBankEmail;
	private String exceptionOrderNo;
	
	
	private Long id;
	private Long userId;
	private String rcvCardNumber;
	private String rcvAccName;
	private String rcvEmail;
	private Long rcvBank;
	private Long realChargeAmt;
	private Date mcNoticeTime;
	private Long status;
	private String memo;
	private Long mcFee;
	private String sn;
	private String mcChannel;
	private String mcArea;
	private String mcSn;
	private Long mcBankFee;
	private String attachment;
	private String apprMemo;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date apprTime;
	private String apprAccount;
	private Long refundAmt;
	private String applyMemo;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date applyTime;
	private String applyAccount;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date mcSecondNoticeTime;
	private String userChain;
	private String currApprer;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date apprBeginTime;
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date appr2BeginTime;
	private Date operatingTime;
	private String baseInfo;
	
	public String getCurrApprer() {
		return currApprer;
	}

	public Date getApprBeginTime() {
		return apprBeginTime;
	}

	public void setApprBeginTime(Date apprBeginTime) {
		this.apprBeginTime = apprBeginTime;
	}

	public Date getAppr2BeginTime() {
		return appr2BeginTime;
	}

	public void setAppr2BeginTime(Date appr2BeginTime) {
		this.appr2BeginTime = appr2BeginTime;
	}

	public void setCurrApprer(String currApprer) {
		this.currApprer = currApprer;
	}

	public Long getId() {
		return id;
	}

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRcvCardNumber() {
		return rcvCardNumber;
	}

	public void setRcvCardNumber(String rcvCardNumber) {
		this.rcvCardNumber = rcvCardNumber;
	}

	public String getRcvAccName() {
		return rcvAccName;
	}

	public void setRcvAccName(String rcvAccName) {
		this.rcvAccName = rcvAccName;
	}

	public Long getRcvBank() {
		return rcvBank;
	}

	public void setRcvBank(Long rcvBank) {
		this.rcvBank = rcvBank;
	}

	public String getRcvEmail() {
		return rcvEmail;
	}

	public void setRcvEmail(String rcvEmail) {
		this.rcvEmail = rcvEmail;
	}

	public Long getRealChargeAmt() {
		return realChargeAmt;
	}

	public void setRealChargeAmt(Long realChargeAmt) {
		this.realChargeAmt = realChargeAmt;
	}

	public Date getMcNoticeTime() {
		return mcNoticeTime;
	}

	public void setMcNoticeTime(Date mcNoticeTime) {
		this.mcNoticeTime = mcNoticeTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getMcFee() {
		return mcFee;
	}

	public void setMcFee(Long mcFee) {
		this.mcFee = mcFee;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getMcChannel() {
		return mcChannel;
	}

	public void setMcChannel(String mcChannel) {
		this.mcChannel = mcChannel;
	}

	public String getMcArea() {
		return mcArea;
	}

	public void setMcArea(String mcArea) {
		this.mcArea = mcArea;
	}

	public String getMcSn() {
		return mcSn;
	}

	public void setMcSn(String mcSn) {
		this.mcSn = mcSn;
	}

	public Long getMcBankFee() {
		return mcBankFee;
	}

	public void setMcBankFee(Long mcBankFee) {
		this.mcBankFee = mcBankFee;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getApprMemo() {
		return apprMemo;
	}

	public void setApprMemo(String apprMemo) {
		this.apprMemo = apprMemo;
	}

	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}

	public String getApprAccount() {
		return apprAccount;
	}

	public void setApprAccount(String apprAccount) {
		this.apprAccount = apprAccount;
	}

	public Long getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(Long refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getApplyMemo() {
		return applyMemo;
	}

	public void setApplyMemo(String applyMemo) {
		this.applyMemo = applyMemo;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}

	public Date getMcSecondNoticeTime() {
		return mcSecondNoticeTime;
	}

	public void setMcSecondNoticeTime(Date mcSecondNoticeTime) {
		this.mcSecondNoticeTime = mcSecondNoticeTime;
	}

	public Date getMcExactTime() {
		return mcExactTime;
	}

	public void setMcExactTime(Date mcExactTime) {
		this.mcExactTime = mcExactTime;
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

	public String getRecBankEmail() {
		return recBankEmail;
	}

	public void setRecBankEmail(String recBankEmail) {
		this.recBankEmail = recBankEmail;
	}

	public String getExceptionOrderNo() {
		return exceptionOrderNo;
	}

	public void setExceptionOrderNo(String exceptionOrderNo) {
		this.exceptionOrderNo = exceptionOrderNo;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}
	
}
