package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

public class UserBankStruc {
	private Long id;

	private Long bankId;

	private String bankAccount;

	private String bankNumber;
	private String province;
	private String city;
	private String branchName;
	private Long mcBankId;
	private String nickName;
	private Long bindcardType;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date bindDate;
	
	private Boolean isBlackList;


	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getMcBankId() {
		return mcBankId;
	}

	public void setMcBankId(Long mcBankId) {
		this.mcBankId = mcBankId;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsBlackList() {
		return isBlackList;
	}

	public void setIsBlackList(Boolean isBlackList) {
		this.isBlackList = isBlackList;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getBindcardType() {
		return bindcardType;
	}

	public void setBindcardType(Long bindcardType) {
		this.bindcardType = bindcardType;
	}
	
}
