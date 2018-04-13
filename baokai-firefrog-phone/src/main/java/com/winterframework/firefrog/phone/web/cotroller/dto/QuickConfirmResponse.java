package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class QuickConfirmResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7575020512112013754L;

	private String revAccName;
	private String rcvBankName;
	private String rcvAccNum;

	private String rcvEmail;

	private Long expireTime;

	private String chargeMemo;
	private Long bankId;
	private Long mode;
	private String url;
	
	private String merchantCode;
	private String serviceType;
	private String returnUrl;
	private String notifyUrl;
	private String interfaceVersion;
	private String inputCharset;
	private String signType;
	private String orderNo;
	private String orderTime;
	private String orderAmount;
	private String bankCode;
	private String productName;
	private String sign; 
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRevAccName() {
		return revAccName;
	}

	public void setRevAccName(String revAccName) {
		this.revAccName = revAccName;
	}

	public String getRcvBankName() {
		return rcvBankName;
	}

	public void setRcvBankName(String rcvBankName) {
		this.rcvBankName = rcvBankName;
	}

	public String getRcvAccNum() {
		return rcvAccNum;
	}

	public void setRcvAccNum(String rcvAccNum) {
		this.rcvAccNum = rcvAccNum;
	}

	public String getRcvEmail() {
		return rcvEmail;
	}

	public void setRcvEmail(String rcvEmail) {
		this.rcvEmail = rcvEmail;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public String getChargeMemo() {
		return chargeMemo;
	}

	public void setChargeMemo(String chargeMemo) {
		this.chargeMemo = chargeMemo;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getMode() {
		return mode;
	}

	public void setMode(Long mode) {
		this.mode = mode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
