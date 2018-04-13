package com.winterframework.firefrog.fund.service.impl.th;

import java.io.Serializable;

public class ThPayQuery implements Serializable{

	private String inputCharset;
	private String merchantCode;
	private String merchantOrder;
	private String sign;
	
	
	public String getInputCharset() {
		return inputCharset;
	}
	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String createParam(String thpayKey){
		return "input_charset="+inputCharset+"&merchant_code="+merchantCode+"&merchant_order="+merchantOrder+"&key=" +thpayKey;
	}
}
