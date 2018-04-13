package com.winterframework.firefrog.fund.exception;

public class FundPTNoUserException extends RuntimeException{
	
	private static final long serialVersionUID = 1;

	public final static Long CODE = 2015L;

	public final static String MSG = "目标帐号未注册PT";
	
	private String account;

	public FundPTNoUserException(String account) {
		super(MSG);
		this.setAccount(account);
	}

	public FundPTNoUserException(Throwable cause) {
		super(String.valueOf(CODE), cause);
	}

	public Long getStatus() {
		return CODE;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
