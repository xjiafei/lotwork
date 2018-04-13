package com.winterframework.firefrog.acl.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AclUserStruc implements Serializable {

	private static final long serialVersionUID = -4673759050139599572L;

	private Long id;

	private String passwd;

	private String account;

	private String bindPasswd;
	private String bindCard;

	private String dep;

	private String cellphone;

	private String telephone;

	private String email;

	private Long status;

	private Long groupId;
	
	private String groupName;
	private String createder;
	private String modifieder;
	private Date gmtCreated;
	private Date gmtModified;
	private List<String> acls;
	private Long lastLoginDate;	
	private String lastArea;
	private Long lastIp;
	private Long cLoginTimes;
	

	



	public String getBindCard() {
		return bindCard;
	}

	public void setBindCard(String bindCard) {
		this.bindCard = bindCard;
	}

	public Long getLastIp() {
		return lastIp;
	}

	public void setLastIp(Long lastIp) {
		this.lastIp = lastIp;
	}

	public Long getcLoginTimes() {
		return cLoginTimes;
	}

	public void setcLoginTimes(Long cLoginTimes) {
		this.cLoginTimes = cLoginTimes;
	}

	public Long getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Long lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastArea() {
		return lastArea;
	}

	public void setLastArea(String lastArea) {
		this.lastArea = lastArea;
	}

	public List<String> getAcls() {
		return acls;
	}

	public void setAcls(List<String> acls) {
		this.acls = acls;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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


}
