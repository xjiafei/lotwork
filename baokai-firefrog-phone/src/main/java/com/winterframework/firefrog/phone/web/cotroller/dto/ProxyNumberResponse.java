package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class ProxyNumberResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6165218537226002086L;
	private Integer membernum;//	会员个数
	private Integer proxynum;//	一级代理个数
	public Integer getMembernum() {
		return membernum;
	}
	public void setMembernum(Integer membernum) {
		this.membernum = membernum;
	}
	public Integer getProxynum() {
		return proxynum;
	}
	public void setProxynum(Integer proxynum) {
		this.proxynum = proxynum;
	}

}
