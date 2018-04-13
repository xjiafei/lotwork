package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.winterframework.modules.validate.FFLength;

public class UserAppealRequestDTO implements Serializable {

	private static final long serialVersionUID = -3460491446596029685L;

	@NotNull
	@FFLength(max = 16, min = 3)
	private String account;

	@NotNull
	@Max(value = 2)
	@Min(value = 1)
	private int appealType;

	@NotNull
	private String idCopy;

	@NotNull
	private CardStruc cardStruc;

	@NotNull
	private String registerArea;

	@NotNull
	private String loginArea;

	@NotNull
	@Email
	private String email;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getAppealType() {
		return appealType;
	}

	public void setAppealType(int appealType) {
		this.appealType = appealType;
	}

	public String getIdCopy() {
		return idCopy;
	}

	public void setIdCopy(String idCopy) {
		this.idCopy = idCopy;
	}

	public CardStruc getCardStruc() {
		return cardStruc;
	}

	public void setCardStruc(CardStruc cardStruc) {
		this.cardStruc = cardStruc;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
