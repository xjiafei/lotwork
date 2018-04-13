package com.winterframework.firefrog.fund.service.impl.sp;


public class SPDeposit {
	//簽名
	private String SIGN;
	//商户号
	private String MERCNUM;
	//交易数据
	private String TRANDATA;
	private String transDomain;
	public String getSIGN() {
		return SIGN;
	}
	public void setSIGN(String sIGN) {
		SIGN = sIGN;
	}
	public String getMERCNUM() {
		return MERCNUM;
	}
	public void setMERCNUM(String mERCNUM) {
		MERCNUM = mERCNUM;
	}
	public String getTRANDATA() {
		return TRANDATA;
	}
	public void setTRANDATA(String tRANDATA) {
		TRANDATA = tRANDATA;
	}
	public String getTransDomain() {
		return transDomain;
	}
	public void setTransDomain(String transDomain) {
		this.transDomain = transDomain;
	}
	
	
	
	
	

}
