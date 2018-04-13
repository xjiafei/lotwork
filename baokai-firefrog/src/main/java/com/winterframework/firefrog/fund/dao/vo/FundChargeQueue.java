/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.firefrog.fund.enums.FundChargeQueueEnum.QueueStatus;
import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class FundChargeQueue extends BaseEntity {

	//alias
		public static final String TABLE_ALIAS = "FundChargeQueue";
		public static final String ALIAS_SN = "流水号";
		public static final String ALIAS_STATUS = "状态1:待處理 2:處理中 3：已完成";
		public static final String ALIAS_TIME = "接收次數";
		public static final String ALIAS_CREATED_TIME = "生成時間";
		public static final String ALIAS_UPDATE_TIME = "更新時間";
		public static final String ALIAS_NOTE = "訂單資訊";
		public static final String ALIAS_USER_SN = "使用者訂單複合參數";
		public static final String ALIAS_CHARGE_CONFIRM_TIME = "訂單查詢次數";
		

		//date formats

		//columns START
		private String sn;
		private Long status;
		private Long time;
		private Date createdTime;
		private Date updateTime;
		private String note;
		private String userSn;
		private Long chargeConfirmTime;
		
		
		public String getSn() {
			return sn;
		}

		public void setSn(String sn) {
			this.sn = sn;
		}

		public Long getStatus() {
			return status;
		}

		public void setStatus(Long status) {
			this.status = status;
		}

		public Long getTime() {
			return time;
		}

		public void setTime(Long time) {
			this.time = time;
		}

		public Date getCreatedTime() {
			return createdTime;
		}

		public void setCreatedTime(Date createdTime) {
			this.createdTime = createdTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}
		public String getUserSn() {
			return userSn;
		}

		public void setUserSn(String userSn) {
			this.userSn = userSn;
		}
		
		

		public Long getChargeConfirmTime() {
			return chargeConfirmTime;
		}

		public void setChargeConfirmTime(Long chargeConfirmTime) {
			this.chargeConfirmTime = chargeConfirmTime;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this).append("Id", getId()).append("sn", getSn())
					.append("status", getStatus()).append("time", getTime())
					.append("createdTime", getCreatedTime()).append("updateTime", getUpdateTime())
					.append("note", getNote()).append("userSn", getUserSn()).append("chargeConfirmTime", getUserSn()).toString();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(getId()).append(getSn()).append(getStatus()).append(getTime())
					.append(getCreatedTime()).append(getUpdateTime()).append(getNote()).append(getUserSn()).append(getChargeConfirmTime()).toHashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof FundChargeQueue == false) {
				return false;
			}
			if (this == obj) {
				return true;
			}
			FundChargeQueue other = (FundChargeQueue) obj;
			return new EqualsBuilder().append(getId(), other.getId())

			.append(getSn(), other.getSn())

			.append(getStatus(), other.getStatus())

			.append(getTime(), other.getTime())

			.append(getCreatedTime(), other.getCreatedTime())

			.append(getUpdateTime(), other.getUpdateTime())

			.append(getNote(), other.getNote())
			
			.append(getUserSn(), other.getUserSn())
			
			.append(getChargeConfirmTime(), other.getChargeConfirmTime())

			.isEquals();
		}
	
}
