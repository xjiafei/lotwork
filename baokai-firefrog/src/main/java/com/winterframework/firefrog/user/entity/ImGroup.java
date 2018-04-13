package com.winterframework.firefrog.user.entity;

import com.winterframework.orm.dal.ibatis3.BaseEntity;
import java.util.Date;

public class ImGroup extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String groupKey;

	private Date createDate;

	private Long targetId;

	private String targetGroupKey;

	private Date targetCreateDateStart;

	private Date targetCreateDateEnd;

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

	public void setTargetId(Long targetId){
		 this.targetId= targetId;
	}

	public Long getTargetId(){
		return this.targetId;
	}

	public void setTargetGroupKey(String targetGroupKey){
		 this.targetGroupKey= targetGroupKey;
	}

	public String getTargetGroupKey(){
		return this.targetGroupKey;
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

}
