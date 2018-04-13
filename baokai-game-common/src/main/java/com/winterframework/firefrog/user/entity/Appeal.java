package com.winterframework.firefrog.user.entity;

import java.util.Date;

public class Appeal {

	public static long PASS_STATUS_PASSED = 1;
	public static long PASS_STATUS_UNPASSED = 2;
	public static long PASS_STATUS_UNAUDITED = 3;
	public static long APPEALTYPE_QA = 1;
	public static long APPEALTYPE_EMAIL = 2;

	public enum AppealType {
		QA("qa", 1), Email("email", 2);
		private AppealType(String key, long value) {
			this.key = key;
			this.value = value;
		};

		private String key;
		private long value;

		public long getValue() {
			return value;
		};
	}

	private long id;

	private String account;

	private AppealType type;

	private String idCopy;

	private CreditCardInfo creditCard;

	private String registerArea;

	private String loginArea;

	private String receiveEmail;

	/**
	 * 1 pass 2 unpass 3 unaudit
	 */
	private long passed;

	private Long operater;

	private String operaterAccount;

	private String notice;

	private Date appealDate;

	private Date passDate;

	private Long activedDays;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdCopy() {
		return idCopy;
	}

	public void setIdCopy(String idCopy) {
		this.idCopy = idCopy;
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

	public String getReceiveEmail() {
		return receiveEmail;
	}

	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}

	public long getPassed() {
		return passed;
	}

	public void setPassed(long passed) {
		this.passed = passed;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Date getAppealDate() {
		return appealDate;
	}

	public void setAppealDate(Date appealDate) {
		this.appealDate = appealDate;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getOperater() {
		return operater;
	}

	public void setOperater(Long operater) {
		this.operater = operater;
	}

	public String getOperaterAccount() {
		return operaterAccount;
	}

	public void setOperaterAccount(String operaterAccount) {
		this.operaterAccount = operaterAccount;
	}

	public CreditCardInfo getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCardInfo creditCard) {
		this.creditCard = creditCard;
	}

	public AppealType getType() {
		return type;
	}

	public void setType(AppealType type) {
		this.type = type;
	}

}
