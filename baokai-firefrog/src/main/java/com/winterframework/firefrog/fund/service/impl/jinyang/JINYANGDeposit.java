package com.winterframework.firefrog.fund.service.impl.jinyang;

import com.winterframework.modules.security.MD5Encrypt;

public class JINYANGDeposit {
	private String merId;
	private String payType;
	private String paymoney;
	private String orderno;
	private String asynURL;
	private String version;
	private String signType;
	private String isShow;
	private String sign;
	private String chargeUrl;
	private String transDomain;
	
	
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getAsynURL() {
		return asynURL;
	}
	public void setAsynURL(String asynURL) {
		this.asynURL = asynURL;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getChargeUrl() {
		return chargeUrl;
	}
	public void setChargeUrl(String chargeUrl) {
		this.chargeUrl = chargeUrl;
	}
	public String getTransDomain() {
		return transDomain;
	}
	public void setTransDomain(String transDomain) {
		this.transDomain = transDomain;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public static void main(String[] s){
		float d=5220000*1.0f/10000;
		System.out.println(d); 
		
		System.out.println(MD5Encrypt.encrypt("p1_mchtid=27345&p2_signtype=1&p3_orderno=FDADAL5LS51UQNKVOIOEAWFLk&p4_version=v2.8a75f5e449015d821a6f18338e8b06c3a"));
		
	}

}
