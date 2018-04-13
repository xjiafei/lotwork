package com.winterframework.firefrog.fund.service.impl.th;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class ThPayQueryResponseDetail implements Serializable{

	@XmlElement(name = "is_success")
	private String isSuccess;
	@XmlElement(name = "merchant_code")
	private String merchantCode;
	@XmlElement(name = "merchant_order")
	private String merchantOrder;
	@XmlElement(name = "remit_order")
	private String remitOrder;
	@XmlElement(name = "remit_amount")
	private String remitAmount;
	@XmlElement(name = "remit_time")
	private String remitTime;
	@XmlElement(name = "remit_status")
	private String remitStatus;
	@XmlElement(name = "remit_status_desc")
	private String remitStatusDesc;
	@XmlElement(name = "account")
	private String account;
	@XmlElement(name = "card_no")
	private String cardNo;
	@XmlElement(name = "bank_name")
	private String bankName;
	@XmlElement(name = "sign")
	private String sign;
	@XmlElement(name = "error_msg")
	private String errorMsg;
	
	

	public String getIsSuccess() {
		return isSuccess;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public String getRemitOrder() {
		return remitOrder;
	}
	public String getRemitAmount() {
		return remitAmount;
	}
	public String getRemitTime() {
		return remitTime;
	}
	public String getRemitStatus() {
		return remitStatus;
	}
	public String getRemitStatusDesc() {
		return remitStatusDesc;
	}
	public String getAccount() {
		return account;
	}
	public String getCardNo() {
		return cardNo;
	}
	public String getBankName() {
		return bankName;
	}
	public String getSign() {
		return sign;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	
	
}
