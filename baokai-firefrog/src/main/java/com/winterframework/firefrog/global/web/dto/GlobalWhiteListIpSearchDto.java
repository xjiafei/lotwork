package com.winterframework.firefrog.global.web.dto;

import java.io.Serializable;

public class GlobalWhiteListIpSearchDto implements Serializable {
	
	private static final long serialVersionUID = 775138852915428464L;
	private Long id;
	private String ipAddr;
	private String accunt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getAccunt() {
		return accunt;
	}
	public void setAccunt(String accunt) {
		this.accunt = accunt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
