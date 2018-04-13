package com.winterframework.firefrog.acl.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.winterframework.firefrog.user.entity.BaseUser;

public class AclUser implements BaseUser{

	private Long id;

	private String account;
 

	private String passwd;

	private String bindPasswd;
	private String bindCard;

	private String dep;

	private String cellphone;

	private String telephone;

	private String email;
	private Long lastIp;
	
	private Status userStatus;
	
	private String createder;
	private String modifieder;
	private Date gmtCreated;
	private Date gmtModified;
	private List<String> acls;
	
	public String getBindCard() {
		return bindCard;
	}

	public void setBindCard(String bindCard) {
		this.bindCard = bindCard;
	}

	public enum Status {
		normal, locked, deleting
	}

	public Long getId() {
		return id;
	}

	public Long getLastIp() {
		return lastIp;
	}

	public void setLastIp(Long lastIp) {
		this.lastIp = lastIp;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
 
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getBindPasswd() {
		return bindPasswd;
	}

	public void setBindPasswd(String bindPasswd) {
		this.bindPasswd = bindPasswd;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Status userStatus) {
		this.userStatus = userStatus;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCreateder() {
		return createder;
	}

	public void setCreateder(String createder) {
		this.createder = createder;
	}

	public String getModifieder() {
		return modifieder;
	}

	public void setModifieder(String modifieder) {
		this.modifieder = modifieder;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public List<String> getAcls() {
		return acls;
	}

	public void setAcls(List<String> acls) {
		this.acls = acls;
	}

	@Override
	@JsonIgnore 
	public boolean isFrontUser() {
		// TODO Auto-generated method stub
		return false;
	}
}
