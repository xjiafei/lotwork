package com.winterframework.firefrog.acl.web.interceptor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.winterframework.modules.web.util.IUser;

public class AclUserStruc implements Serializable,IUser {

	private static final long serialVersionUID = -4673759050139599572L;
	private List<String> acls;
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

	public String getBindCard() {
		return bindCard;
	}

	public void setBindCard(String bindCard) {
		this.bindCard = bindCard;
	}

	/** 
	* 搜索类型：0：用户名搜索，1：邮箱搜索，2：电话:cellphone，3：座机 telephone
	*/ 
	private Integer searchType;
	/** 
	* 搜索值
	*/ 
	private String searchValue;

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

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
		if (searchValue != null && !searchValue.equals("")) {
			switch (this.getSearchType()) {
			case 1:
				this.setEmail(searchValue);
				break;
			case 2:
				this.setCellphone(searchValue);
				break;
			case 3:
				this.setTelephone(searchValue);
				break;
			case 0:
			default:
				this.setAccount(searchValue);
				break;
			}
		}
	}

	public List<String> getAcls() {
		return acls;
	}

	public void setAcls(List<String> acls) {
		this.acls = acls;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return this.account;
	}

	@Override
	public boolean IsBlocked() {
		// TODO Auto-generated method stub
		return this.IsBlocked();
	}

	
	
}
