package com.winterframework.firefrog.user.vo;

import java.io.Serializable;
import java.util.Date;

public class ImUserVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long userId;

	private Long imStatus;

	private Long canReply;

	private String pictureUrl;

	private Date createDate;

	private Date lastUpdateDate;

	public void setId(Long id){
		 this.id= id;
	}

	public Long getId(){
		return this.id;
	}

	public void setUserId(Long userId){
		 this.userId= userId;
	}

	public Long getUserId(){
		return this.userId;
	}

	public void setImStatus(Long imStatus){
		 this.imStatus= imStatus;
	}

	public Long getImStatus(){
		return this.imStatus;
	}

	public void setCanReply(Long canReply){
		 this.canReply= canReply;
	}

	public Long getCanReply(){
		return this.canReply;
	}

	public void setPictureUrl(String pictureUrl){
		 this.pictureUrl= pictureUrl;
	}

	public String getPictureUrl(){
		return this.pictureUrl;
	}

	public void setCreateDate(Date createDate){
		 this.createDate= createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate){
		 this.lastUpdateDate= lastUpdateDate;
	}

	public Date getLastUpdateDate(){
		return this.lastUpdateDate;
	}

}
