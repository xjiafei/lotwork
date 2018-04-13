package com.winterframework.modules.catalina.session;

public class SessionJavaHead implements java.io.Serializable{
	private String id;
	private String authType; // Transient only
	private Long creationTime;
	private Long lastAccessedTime;
	private int maxInactiveInterval;
	private boolean isNew;
	private boolean isValid;
	private long thisAccessedTime;
	private String principal; // Transient only
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public Long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}
	public Long getLastAccessedTime() {
		return lastAccessedTime;
	}
	public void setLastAccessedTime(Long lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public long getThisAccessedTime() {
		return thisAccessedTime;
	}
	public void setThisAccessedTime(long thisAccessedTime) {
		this.thisAccessedTime = thisAccessedTime;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
