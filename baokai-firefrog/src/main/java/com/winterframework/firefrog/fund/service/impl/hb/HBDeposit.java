package com.winterframework.firefrog.fund.service.impl.hb;


public class HBDeposit {
	//簽名
	private String sign;
	//加密資料
	private String data;
	
	private String transDomain;
	//HB地址
	private String hbUrl;
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getTransDomain() {
		return transDomain;
	}
	public void setTransDomain(String transDomain) {
		this.transDomain = transDomain;
	}
	public String getHbUrl() {
		return hbUrl;
	}
	public void setHbUrl(String hbUrl) {
		this.hbUrl = hbUrl;
	}
	
	
	
	

}
