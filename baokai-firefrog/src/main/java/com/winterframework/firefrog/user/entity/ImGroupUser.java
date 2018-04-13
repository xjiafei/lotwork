package com.winterframework.firefrog.user.entity;

import com.winterframework.orm.dal.ibatis3.BaseEntity;
import java.util.Date;

public class ImGroupUser extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long groupId;

	private Long userId;

	private Long unreadCount;

	private Date historyStartTime;

	private Date createDate;

	private Date lastUpdateDate;

	private Long isActive;

	private Long targetId;

	private Long targetGroupId;

	private Long targetUserId;

	private Long targetUnreadCount;

	private Date targetHistoryStartTimeStart;

	private Date targetHistoryStartTimeEnd;

	private Date targetCreateDateStart;

	private Date targetCreateDateEnd;

	private Date targetLastUpdateDateStart;

	private Date targetLastUpdateDateEnd;

	private Long targetIsActive;

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

	public void setTargetId(Long targetId){
		 this.targetId= targetId;
	}

	public Long getTargetId(){
		return this.targetId;
	}

	public void setTargetGroupId(Long targetGroupId){
		 this.targetGroupId= targetGroupId;
	}

	public Long getTargetGroupId(){
		return this.targetGroupId;
	}

	public void setTargetUserId(Long targetUserId){
		 this.targetUserId= targetUserId;
	}

	public Long getTargetUserId(){
		return this.targetUserId;
	}

	public void setTargetUnreadCount(Long targetUnreadCount){
		 this.targetUnreadCount= targetUnreadCount;
	}

	public Long getTargetUnreadCount(){
		return this.targetUnreadCount;
	}

	public void setTargetHistoryStartTimeStart(Date targetHistoryStartTimeStart){
		 this.targetHistoryStartTimeStart= targetHistoryStartTimeStart;
	}

	public Date getTargetHistoryStartTimeStart(){
		return this.targetHistoryStartTimeStart;
	}

	public void setTargetHistoryStartTimeEnd(Date targetHistoryStartTimeEnd){
		 this.targetHistoryStartTimeEnd= targetHistoryStartTimeEnd;
	}

	public Date getTargetHistoryStartTimeEnd(){
		return this.targetHistoryStartTimeEnd;
	}

	public void setTargetCreateDateStart(Date targetCreateDateStart){
		 this.targetCreateDateStart= targetCreateDateStart;
	}

	public Date getTargetCreateDateStart(){
		return this.targetCreateDateStart;
	}

	public void setTargetCreateDateEnd(Date targetCreateDateEnd){
		 this.targetCreateDateEnd= targetCreateDateEnd;
	}

	public Date getTargetCreateDateEnd(){
		return this.targetCreateDateEnd;
	}

	public void setTargetLastUpdateDateStart(Date targetLastUpdateDateStart){
		 this.targetLastUpdateDateStart= targetLastUpdateDateStart;
	}

	public Date getTargetLastUpdateDateStart(){
		return this.targetLastUpdateDateStart;
	}

	public void setTargetLastUpdateDateEnd(Date targetLastUpdateDateEnd){
		 this.targetLastUpdateDateEnd= targetLastUpdateDateEnd;
	}

	public Date getTargetLastUpdateDateEnd(){
		return this.targetLastUpdateDateEnd;
	}

	public void setTargetIsActive(Long targetIsActive){
		 this.targetIsActive= targetIsActive;
	}

	public Long getTargetIsActive(){
		return this.targetIsActive;
	}

}
