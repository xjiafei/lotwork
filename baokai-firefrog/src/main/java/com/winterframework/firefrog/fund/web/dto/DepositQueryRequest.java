package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class DepositQueryRequest implements Serializable {

	private static final long serialVersionUID = -5348544106769081318L;

	private Long[] status;
	//单号
	private String sn;
	//类型 
	private Long typeId;
	//接受账号
	private String rcvAccount;
	//是否VIP
	private Long isVip;
	//创建开始时间
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date gmtCreatedStart;
	//创建结束时间
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date gmtCreatedEnd;
	//建单开始时间
	private String applyAccount;
	//建单结束时间
	private String apprAccount;
	//审核时间从
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date apprDateStart;
	//审核时间到
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date apprDateEnd;
	private Long isBatch;
	
	public Long getIsBatch() {
		return isBatch;
	}

	public void setIsBatch(Long isBatch) {
		this.isBatch = isBatch;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getRcvAccount() {
		return rcvAccount;
	}

	public void setRcvAccount(String rcvAccount) {
		this.rcvAccount = rcvAccount;
	}

	public Long getIsVip() {
		return isVip;
	}

	public void setIsVip(Long isVip) {
		this.isVip = isVip;
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

	public Long[] getStatus() {
		return status;
	}

	public void setStatus(Long[] status) {
		this.status = status;
	}

	public Date getGmtCreatedStart() {
		return gmtCreatedStart;
	}

	public void setGmtCreatedStart(Date gmtCreatedStart) {
		this.gmtCreatedStart = gmtCreatedStart;
	}

	public Date getGmtCreatedEnd() {
		return gmtCreatedEnd;
	}

	public void setGmtCreatedEnd(Date gmtCreatedEnd) {
		this.gmtCreatedEnd = gmtCreatedEnd;
	}

	public Date getApprDateStart() {
		return apprDateStart;
	}

	public void setApprDateStart(Date apprDateStart) {
		this.apprDateStart = apprDateStart;
	}

	public Date getApprDateEnd() {
		return apprDateEnd;
	}

	public void setApprDateEnd(Date apprDateEnd) {
		this.apprDateEnd = apprDateEnd;
	}

	

}
