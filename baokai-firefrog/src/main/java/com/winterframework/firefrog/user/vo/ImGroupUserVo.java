package com.winterframework.firefrog.user.vo;

import java.io.Serializable;
import java.util.Date;

public class ImGroupUserVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long groupId;

	private Long userId;

	private Long unreadCount;

	private Date historyStartTime;

	private Date createDate;

	private Date lastUpdateDate;

	private Long isActive;

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

	public void setUnreadCount(Long unreadCount){
		 this.unreadCount= unreadCount;
	}

	public Long getUnreadCount(){
		return this.unreadCount;
	}

	public void setHistoryStartTime(Date historyStartTime){
		 this.historyStartTime= historyStartTime;
	}

	public Date getHistoryStartTime(){
		return this.historyStartTime;
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

	public void setIsActive(Long isActive){
		 this.isActive= isActive;
	}

	public Long getIsActive(){
		return this.isActive;
	}

}
