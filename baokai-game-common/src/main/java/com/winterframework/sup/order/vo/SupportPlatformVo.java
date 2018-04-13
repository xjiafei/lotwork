package com.winterframework.sup.order.vo;

public class SupportPlatformVo extends BaseSupVo{
	
	private static final long serialVersionUID = 3248577064865360485L;

	private Long id;

	private String name;
	
	private String code;
	
	private String loginUrl;

	private Long isActive;

	public void setId(Long id){
		 this.id= id;
	}

	public Long getId(){
		return this.id;
	}

	public void setName(String name){
		 this.name= name;
	}

	public String getName(){
		return this.name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setIsActive(Long isActive){
		 this.isActive= isActive;
	}

	public Long getIsActive(){
		return this.isActive;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

}
