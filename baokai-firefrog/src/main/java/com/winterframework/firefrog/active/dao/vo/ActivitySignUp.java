package com.winterframework.firefrog.active.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 活動報名表
 * @author David.Wu
 *
 */
public class ActivitySignUp extends BaseEntity{

	/**
	 *  ID NUMBER NOT NULL 
	 *  , ACCOUNT VARCHAR2(24 BYTE) NOT NULL 
		, MONTH NUMBER NOT NULL 
		, SOURCE VARCHAR2(24 BYTE) 
		, GMT_CREATE TIMESTAMP(6) DEFAULT sysdate NOT NULL 
		, START_TIME TIMESTAMP(3) 
		, END_TIME TIMESTAMP(3) 
		, ACTIVITY_ID NUMBER 
	 */
	private static final long serialVersionUID = -748775236402594906L;
	
	// alias	
		public static final String TABLE_ALIAS = "ActivitySignUp";
		public static final String ALIAS_ID = "報名ID";
		public static final String ALIAS_ActivityID = "活動ID";
		public static final String ALIAS_Accunt = "報名帳號";
		public static final String ALIAS_Month = "活動月份";		
		public static final String ALIAS_Source = "報名來源媒介";
		public static final String ALIAS_Notice = "報名成功是否通知";
		public static final String ALIAS_Status = "報名狀態";
		public static final String ALIAS_GmtCreate = "報名時間";
		public static final String ALIAS_StartTime = "活動開始時間";
		public static final String ALIAS_EndTime = "活動結束時間";
		
					
		private Long id;
		private Long activityId;
		private String accunt;	
		private Long month;
		private String source;
		private Long notice;
		private Long status;
		private Date gmtCreate;
		private Date startTime;
		private Date endTime;
		
		public final Long getId() {
			return id;
		}
		public final void setId(Long id) {
			this.id = id;
		}
		public final String getAccunt() {
			return accunt;
		}
		public final void setAccunt(String accunt) {
			this.accunt = accunt;
		}
		public final Long getStatus() {
			return status;
		}
		public final void setStatus(Long status) {
			this.status = status;
		}
		public final String getSource() {
			return source;
		}
		public final void setSource(String source) {
			this.source = source;
		}
		public final Date getStartTime() {
			return startTime;
		}
		public final void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		public final Date getEndTime() {
			return endTime;
		}
		public final void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		public final Long getActivityId() {
			return activityId;
		}
		public final void setActivityId(Long activityId) {
			this.activityId = activityId;
		}
		public Date getGmtCreate() {
			return gmtCreate;
		}
		public void setGmtCreate(Date gmtCreate) {
			this.gmtCreate = gmtCreate;
		}
		public Long getMonth() {
			return month;
		}
		public void setMonth(Long month) {
			this.month = month;
		}
		public Long getNotice() {
			return notice;
		}
		public void setNotice(Long notice) {
			this.notice = notice;
		}
}
