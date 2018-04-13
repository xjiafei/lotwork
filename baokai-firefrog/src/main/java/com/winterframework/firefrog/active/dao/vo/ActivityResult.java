package com.winterframework.firefrog.active.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 活動(中獎)結果
 * @author David.Wu
 *
 */
public class ActivityResult extends BaseEntity{

	/**
	 * ACTIVITY_RESULT
	 * ID NUMBER NOT NULL 
	 * , ACTIVITY_ID NUMBER NOT NULL 
	 * , USERID VARCHAR2(20 BYTE) 
	 * , STATUS NUMBER 
	 * , TYPE VARCHAR2(20 BYTE) 
	 * , RESULT VARCHAR2(255 BYTE) NOT NULL 
	 * , CREATEUSER NUMBER 
	 * , CREATETIME TIMESTAMP(6) DEFAULT sysdate 
	 * , MODIFYUSER NUMBER 
	 * , MODIFYTIME TIMESTAMP(6) DEFAULT sysdate 
	 */
	private static final long serialVersionUID = 1065635331474617773L;
	// alias	
	public static final String TABLE_ALIAS = "ActivityResult";
	public static final String ALIAS_ID = "報名ID";
	public static final String ALIAS_ActivityID = "活動ID";
	public static final String ALIAS_UserID = "用戶ID";
	public static final String ALIAS_Status = "報名狀態";
	public static final String ALIAS_Type = "自定義類別";
	public static final String ALIAS_Result = "中獎結果";
	public static final String ALIAS_CreateUser = "建立人員";
	public static final String ALIAS_CreateTime = "建立時間";
	public static final String ALIAS_ModifyUser = "修改人員";
	public static final String ALIAS_ModifyTime = "修改時間";
	
						
	private Long id;
	private Long activityId;	
	private Long userId;
	private Long status;		
	private String type;	
	private String result;	
	private Long createUser;
	private Date createTime;
	private Long modifyUser;
	private Date modifyTime;
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final Long getActivityId() {
		return activityId;
	}
	public final void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public final Long getUserId() {
		return userId;
	}
	public final void setUserId(Long userId) {
		this.userId = userId;
	}
	public final Long getStatus() {
		return status;
	}
	public final void setStatus(Long status) {
		this.status = status;
	}
	public final String getType() {
		return type;
	}
	public final void setType(String type) {
		this.type = type;
	}
	public final String getResult() {
		return result;
	}
	public final void setResult(String result) {
		this.result = result;
	}
	public final Long getCreateUser() {
		return createUser;
	}
	public final void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public final Date getCreateTime() {
		return createTime;
	}
	public final void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public final Long getModifyUser() {
		return modifyUser;
	}
	public final void setModifyUser(Long modifyUser) {
		this.modifyUser = modifyUser;
	}
	public final Date getModifyTime() {
		return modifyTime;
	}
	public final void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
