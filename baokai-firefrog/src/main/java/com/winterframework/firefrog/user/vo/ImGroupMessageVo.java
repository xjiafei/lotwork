package com.winterframework.firefrog.user.vo;

import java.io.Serializable;
import java.util.Date;

public class ImGroupMessageVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long groupId;

	private Long userId;

	private String account;

	private String content;

	private String stompContent;

	private Date createDate;

	public void setId(Long id){
		 this.id= id;
	}

	public Long getId(){
		return this.id;
	}

	public void setGroupId(Long groupId){
		 this.groupId= groupId;
	}

	public Long getGroupId(){
		return this.groupId;
	}

	public void setUserId(Long userId){
		 this.userId= userId;
	}

	public Long getUserId(){
		return this.userId;
	}

	public void setAccount(String account){
		 this.account= account;
	}

	public String getAccount(){
		return this.account;
	}

	public void setContent(String content){
		 this.content= content;
	}

	public String getContent(){
		return this.content;
	}

	public void setStompContent(String stompContent){
		 this.stompContent= stompContent;
	}

	public String getStompContent(){
		return this.stompContent;
	}

	public void setCreateDate(Date createDate){
		 this.createDate= createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

}
