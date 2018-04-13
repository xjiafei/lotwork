package com.winterframework.firefrog.fund.service.impl.yinbang;

public class YINBANGPayConfirmRequest {
	private String sign;
	private String merId;
	private String version;
	private String payOrderId;
	private String orderId;
	private String signType;
	private String encParam;
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPayOrderId() {
		return payOrderId;
	}
	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getEncParam() {
		return encParam;
	}
	public void setEncParam(String encParam) {
		this.encParam = encParam;
	}
	
}
