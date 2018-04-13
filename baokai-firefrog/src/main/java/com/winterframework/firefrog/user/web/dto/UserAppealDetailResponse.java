package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

/**
 * 用户申述详情，审核界面信息
 * 
 * @author david
 * 
 */
public class UserAppealDetailResponse implements Serializable {

	private static final long serialVersionUID = 5924326617496255471L;

	private Long id;
	private String account;
	private Long appealType;
	private CardStruc cardStruc;
	private String idCopy;
	private String registerArea;
	private String loginArea;
	private String email;
	@JsonSerialize(using = FirefrogDateSerializer.class)  
	@JsonDeserialize(using = FirefrogDateDeSerializer.class)
	private Date passDate;
	private Long activedDays;
	private Long passed;
	private String memo;
	private Long vipLvl;
	
	
	
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

	public Long getPassed() {
		return passed;
	}

	public void setPassed(Long passed) {
		this.passed = passed;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAppealType() {
		return appealType;
	}

	public void setAppealType(Long appealType) {
		this.appealType = appealType;
	}

	public CardStruc getCardStruc() {
		return cardStruc;
	}

	public void setCardStruc(CardStruc cardStruc) {
		this.cardStruc = cardStruc;
	}

	public String getIdCopy() {
		return idCopy;
	}

	public void setIdCopy(String idCopy) {
		this.idCopy = idCopy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegisterArea() {
		return registerArea;
	}

	public void setRegisterArea(String registerArea) {
		this.registerArea = registerArea;
	}

	public String getLoginArea() {
		return loginArea;
	}

	public void setLoginArea(String loginArea) {
		this.loginArea = loginArea;
	}

	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	public Long getActivedDays() {
		return activedDays;
	}

	public void setActivedDays(Long activedDays) {
		this.activedDays = activedDays;
	}

}
