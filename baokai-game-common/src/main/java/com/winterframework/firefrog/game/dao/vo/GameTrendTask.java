/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

 
/**
 * 走势图任务
 * @ClassName
 * @Description
 * @author ibm
 * 2015年10月15日
 */
public class GameTrendTask extends BaseEntity {  
	/**
	 * 
	 */
	private static final long serialVersionUID = -7604747785880367623L;
	//alias
	public static final String TABLE_ALIAS = "走势图任务表";
	public static final String ALIAS_LOTTERYID = "彩种ID";
	public static final String ALIAS_ISSUECODE = "奖期";
	public static final String ALIAS_DRAWNUMBER = "开奖号码";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_USERID = "用户ID";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_EXEC_TIME = "执行时间";

	//date formats

	//columns START
	private Long lotteryId;
	private Long issueCode;
	private String drawNumber;
	private Integer status;
	private Long userId;
	private String remark;
	private Date updateTime;
	private Date createTime;
	private Date execTime;
	//columns END
	public enum Status{
		//状态 1:未执行 2:执行成功 3:执行失败 4:已撤销
		INIT(1),SUCCESS(2),FAILED(3),CANCEL(4);
		private int _value;
		Status(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public String getDrawNumber() {
		return drawNumber;
	}
	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getExecTime() {
		return execTime;
	}
	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}
 
	
}
