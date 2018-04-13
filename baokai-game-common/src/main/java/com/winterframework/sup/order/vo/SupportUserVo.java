package com.winterframework.sup.order.vo;

import java.util.Date;

public class SupportUserVo extends BaseSupVo {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String account;

	private String mail;

	private String lvl;

	private Date createDate;
	
	private Date updateDate;

	private Long platformId;
	
	private Boolean isRegister;
	
	private Long platformUserId;

	public void setId(Long id){
		 this.id= id;
	}

	public Long getId(){
		return this.id;
	}

	public void setAccount(String account){
		 this.account= account;
	}

	public String getAccount(){
		return this.account;
	}

	public void setMail(String mail){
		 this.mail= mail;
	}

	public String getMail(){
		return this.mail;
	}

	public void setLvl(String lvl){
		 this.lvl= lvl;
	}

	public String getLvl(){
		return this.lvl;
	}

	public void setCreateDate(Date createDate){
		 this.createDate= createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setPlatformId(Long platformId){
		 this.platformId= platformId;
	}

	public Long getPlatformId(){
		return this.platformId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}

	public Long getPlatformUserId() {
		return platformUserId;
	}

	public void setPlatformUserId(Long platformUserId) {
		this.platformUserId = platformUserId;
	}
}
