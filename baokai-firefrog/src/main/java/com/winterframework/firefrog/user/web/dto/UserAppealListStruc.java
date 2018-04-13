package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

/**
 * 用户申述查询返回申诉详情
 * 
 * @author david
 * 
 */
public class UserAppealListStruc implements Serializable {

	private static final long serialVersionUID = 5924326617496255471L;

	private int id;
	private String account;
	private Integer appealType;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date createDate;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date passDate;
	private Long passed;
	private String operater;
	private Long vipLvl;
	private String memo;

	
	
	/**
	 * @return the vipLvl
	 */
	public Long getVipLvl() {
		return vipLvl;
	}

	/**
	 * @param vipLvl the vipLvl to set
	 */
	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

	public int getId() {
		return id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	public void setPassed(Long passed) {
		this.passed = passed;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getAppealType() {
		return appealType;
	}

	public void setAppealType(Integer appealType) {
		this.appealType = appealType;
	}

	

	public long getPassed() {
		return passed;
	}

	public void setPassed(long passed) {
		this.passed = passed;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
