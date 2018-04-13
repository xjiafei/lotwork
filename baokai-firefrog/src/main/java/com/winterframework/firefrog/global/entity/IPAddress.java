package com.winterframework.firefrog.global.entity;

import java.util.Date;

import com.winterframework.firefrog.acl.entity.AclUser;

public class IPAddress {

	private Long id;

	private String ip;

	private String area;

	private Date effectDate;

	private Date expireDate;

	private AclUser operator;

	private Type type;

	public enum Type {
		blackList, whiteList
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}


	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public AclUser getOperator() {
		return operator;
	}

	public void setOperator(AclUser operator) {
		this.operator = operator;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

}
