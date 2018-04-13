package com.winterframework.firefrog.fund.service.impl.jinyang;

import java.io.Serializable;

public class JinYangPayDetailResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5591716684008157564L;
	private String r1_mchtid;
	private String r2_systemorderno;
	private String r3_orderno;
	private String r4_amount;
	private String r5_version;
	private String r6_qrcode;
	private String r7_paytype;
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
	public String getR5_version() {
		return r5_version;
	}
	public void setR5_version(String r5_version) {
		this.r5_version = r5_version;
	}
	public String getR6_qrcode() {
		return r6_qrcode;
	}
	public void setR6_qrcode(String r6_qrcode) {
		this.r6_qrcode = r6_qrcode;
	}
	public String getR7_paytype() {
		return r7_paytype;
	}
	public void setR7_paytype(String r7_paytype) {
		this.r7_paytype = r7_paytype;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}


}
