package com.winterframework.firefrog.fund.service.impl.jinyang;

import java.io.Serializable;

public class JinYangPayQueryDetailResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5957143367941129533L;
	private String r1_mchtid;
	private String r2_systemorderno;
	private String r3_orderno;
	private String r4_amount;
	private String r5_orderstate;
	private String r6_version;
	private String sign;
	public String getR1_mchtid() {
		return r1_mchtid;
	}
	public void setR1_mchtid(String r1_mchtid) {
		this.r1_mchtid = r1_mchtid;
	}
	public String getR2_systemorderno() {
		return r2_systemorderno;
	}
	public void setR2_systemorderno(String r2_systemorderno) {
		this.r2_systemorderno = r2_systemorderno;
	}
	public String getR3_orderno() {
		return r3_orderno;
	}
	public void setR3_orderno(String r3_orderno) {
		this.r3_orderno = r3_orderno;
	}
	
	public String getR4_amount() {
		return r4_amount;
	}
	public void setR4_amount(String r4_amount) {
		this.r4_amount = r4_amount;
	}
	
	public String getR5_orderstate() {
		return r5_orderstate;
	}
	public void setR5_orderstate(String r5_orderstate) {
		this.r5_orderstate = r5_orderstate;
	}
	public String getR6_version() {
		return r6_version;
	}
	public void setR6_version(String r6_version) {
		this.r6_version = r6_version;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}


}
