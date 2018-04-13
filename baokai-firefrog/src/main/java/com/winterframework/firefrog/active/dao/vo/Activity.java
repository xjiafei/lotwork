package com.winterframework.firefrog.active.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 活動資料主表
 * @author David.Wu
 *
 */
public class Activity extends BaseEntity{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -8575781373135565510L;
	// alias	
	public static final String TABLE_ALIAS = "Activity";
	public static final String ALIAS_ID = "活動ID";
	public static final String ALIAS_ActivityName = "活動名稱";
	public static final String ALIAS_StartTime = "活動開始時間";
	public static final String ALIAS_EndTime = "活動結束時間";
	public static final String ALIAS_Status = "活動狀態";
	public static final String ALIAS_ActivityType = "自定義類別";
	public static final String ALIAS_GmtCreate = "活動建立時間";
	public static final String ALIAS_GmtModify = "活動修改時間";
	public static final String ALIAS_ActivityCode = "活動代碼";
	public static final String ALIAS_COMMON = "活動共用設定";
	
	private Long id;
	private String activityName;
	private Date startTime;
	private Date endTime;
	private int status;
	private String activityType;
	private Date gmtCreate;
	private Date gmtModify;
	private String activityCode;
	private String common;
	
	public final String getActivityName() {
		return activityName;
	}
	public final void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public final Date getEndTime() {
		return endTime;
	}
	public final void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public final int getStatus() {
		return status;
	}
	public final void setStatus(int status) {
		this.status = status;
	}
	public final String getActivityType() {
		return activityType;
	}
	public final void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public final String getActivityCode() {
		return activityCode;
	}
	public final void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public static final long getSerialversionuid() {
		return serialVersionUID;
	}
	public final Date getStartTime() {
		return startTime;
	}
	public final void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
}
