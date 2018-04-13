package com.winterframework.firefrog.shortlived.sheepactivity.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivitySheepOperateLog 
* @Description 羊年活动操作日志
* @author  hugh
* @date 2015年1月12日 下午5:36:12 
*  
*/
public class ActivitySheepOperateLog  extends BaseEntity{
 
	private static final long serialVersionUID = -1L;
		
	private String userName; //被操作用户名			USER_NAME
	private String operateName; //操作人			OPERATE_NAME
	private Long activityId; //被操作活动id		ACTIVITY_ID
	private Long activityConfigId; //被操作活动id		ACTIVITY_CONFIG_ID
	private String activityName; //被操作活动名			ACTIVITY_NAME
	private String activityChildName; //被操作子活动名		ACTIVITY_CHILD_NAME
	
	private Long operateType;//状态   操作类型 OPERATE_TYPE
	private String operateContent;//状态  操作类容		OPERATE_CONTENT	 	
	private Long num; //被操作数字		NUM
	//protected Date gmtCreated; GMT_CREATED
	
	private Long  startTimel;
	
	private Long endTimel;
	
	private Date beginTime; //只用于条件查询
	private Date endTime; //只用于条件查询
	
	

	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getStartTimel() {
		return startTimel;
	}
	public void setStartTimel(Long startTimel) {
		this.startTimel = startTimel;
	}
	public Long getEndTimel() {
		return endTimel;
	}
	public void setEndTimel(Long endTimel) {
		this.endTimel = endTimel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityChildName() {
		return activityChildName;
	}
	public void setActivityChildName(String activityChildName) {
		this.activityChildName = activityChildName;
	}

	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public Long getActivityConfigId() {
		return activityConfigId;
	}
	public void setActivityConfigId(Long activityConfigId) {
		this.activityConfigId = activityConfigId;
	}
	public Long getOperateType() {
		return operateType;
	}
	public void setOperateType(Long operateType) {
		this.operateType = operateType;
	}
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}	
	
}
