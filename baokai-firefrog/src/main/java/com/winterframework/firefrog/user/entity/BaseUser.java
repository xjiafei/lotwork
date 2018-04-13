package com.winterframework.firefrog.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface BaseUser {
	public Long getId() ;
	public void setId(Long id)  ;
	public String getAccount() ;
	public void setAccount(String account) ;
	@JsonIgnore 
	public boolean isFrontUser();

}
