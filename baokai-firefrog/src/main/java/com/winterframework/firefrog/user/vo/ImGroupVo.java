package com.winterframework.firefrog.user.vo;

import java.io.Serializable;
import java.util.Date;

public class ImGroupVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String groupKey;

	private Date createDate;

	public void setId(Long id){
		 this.id= id;
	}

	public Long getId(){
		return this.id;
	}

	public void setGroupKey(String groupKey){
		 this.groupKey= groupKey;
	}

	public String getGroupKey(){
		return this.groupKey;
	}

	public void setCreateDate(Date createDate){
		 this.createDate= createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

}
