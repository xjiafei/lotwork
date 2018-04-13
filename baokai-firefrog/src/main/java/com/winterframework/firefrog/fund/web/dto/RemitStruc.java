package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.firefrog.common.convert.BeanConverter.Alias;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class RemitStruc implements Serializable {

	private static final long serialVersionUID = -5459066642957459710L;

	private String sn;
	private Long id;

	private String rcvAccount;

	private Long typeId;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date applyTime;

	@Alias(field = "depositAmt")
	private Long withdrawAmt;

	private UserBankStruc userBankStruc;

	private String memo;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date mcRemitTime;
	private Long realWithdrawAmt;

	private String applyAccount;

	private String apprAccount;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date apprTime;
	private Long status;
	private String attach;
	@Alias(field = "isVip")
	private Long vipLvl;

	

	

	public Long getRealWithdrawAmt() {
		return realWithdrawAmt;
	}

	public void setRealWithdrawAmt(Long realWithdrawAmt) {
		this.realWithdrawAmt = realWithdrawAmt;
	}

	public Long getVipLvl() {
		return vipLvl;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getRcvAccount() {
		return rcvAccount;
	}

	public void setRcvAccount(String rcvAccount) {
		this.rcvAccount = rcvAccount;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Long getWithdrawAmt() {
		return withdrawAmt;
	}

	public void setWithdrawAmt(Long withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}

	public UserBankStruc getUserBankStruc() {
		return userBankStruc;
	}

	public void setUserBankStruc(UserBankStruc userBankStruc) {
		this.userBankStruc = userBankStruc;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getMcRemitTime() {
		return mcRemitTime;
	}

	public void setMcRemitTime(Date mcRemitTime) {
		this.mcRemitTime = mcRemitTime;
	}

	public String getApplyAccount() {
		return applyAccount;
	}

	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}

	public String getApprAccount() {
		return apprAccount;
	}

	public void setApprAccount(String apprAccount) {
		this.apprAccount = apprAccount;
	}

	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}

}
