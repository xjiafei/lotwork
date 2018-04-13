package com.winterframework.firefrog.acl.entity;

import java.util.Date;

public class AclOperateLog {

	private Long id;

	private AclUser user;

	private String ip;

	private String url;

	private String action;

	private Type detail;

	private Date createTime;

	public enum Type {
		detail, successful, failed, noAccess
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AclUser getUser() {
		return user;
	}

	public void setUser(AclUser user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Type getDetail() {
		return detail;
	}

	public void setDetail(Type detail) {
		this.detail = detail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
