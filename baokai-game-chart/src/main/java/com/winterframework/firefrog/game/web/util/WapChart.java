package com.winterframework.firefrog.game.web.util;

import java.io.Serializable;

public class WapChart implements Serializable{

	private String webIssueCode;
	private String numberRecord;
	private Long kuadu;
	private Long totValue;
	private boolean super2000;
	
	
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	public Long getKuadu() {
		return kuadu;
	}
	public void setKuadu(Long kuadu) {
		this.kuadu = kuadu;
	}
	public Long getTotValue() {
		return totValue;
	}
	public void setTotValue(Long totValue) {
		this.totValue = totValue;
	}
	public boolean isSuper2000() {
		return super2000;
	}
	public void setSuper2000(boolean super2000) {
		this.super2000 = super2000;
	}
	
}
