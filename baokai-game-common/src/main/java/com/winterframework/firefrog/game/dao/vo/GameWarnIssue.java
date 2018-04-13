 package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GameWarnIssue extends BaseEntity {
	
	//alias
	public static final String TABLE_ALIAS = "奖期警告表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_WEB_ISSUE_CODE = "WEB显示奖期";
	public static final String ALIAS_ISSUE_WARN_ID = "奖期警告ID";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_READ_FLAG = "标志位 0 未读 1 已读";
	public static final String ALIAS_WARN_PARAS = "警告参数预留 如提前开奖时间";
	public static final String ALIAS_STATUS = "处理状态 1 待处理 2 已处理";
	public static final String ALIAS_STATUS_ROUT = "处理过程";
	public static final String ALIAS_NOTICE_STATUS = "通知状态  0未阅  1已阅";
	//date formats
	
	//columns START
	private Long lotteryid;
	private Long issueCode;
	private String webIssueCode;
	private String issueWarnId;
	private Date createTime;
	private Date updateTime;
	private Long readFlag;
	private String warnParas;
	private Long status;
	private String statusRout;
	private Long noticeStatus;
	//columns END

	public GameWarnIssue(){
	}

	public GameWarnIssue(
		Long id
	){
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}
	
	public Long getLotteryid() {
		return this.lotteryid;
	}
	public void setIssueCode(Long value) {
		this.issueCode = value;
	}
	
	public Long getIssueCode() {
		return this.issueCode;
	}
	public void setWebIssueCode(String value) {
		this.webIssueCode = value;
	}
	
	public String getWebIssueCode() {
		return this.webIssueCode;
	}
	public void setIssueWarnId(String value) {
		this.issueWarnId = value;
	}
	
	public String getIssueWarnId() {
		return this.issueWarnId;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setReadFlag(Long value) {
		this.readFlag = value;
	}
	
	public Long getReadFlag() {
		return this.readFlag;
	}
	public void setWarnParas(String value) {
		this.warnParas = value;
	}
	
	public String getWarnParas() {
		return this.warnParas;
	}
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Lotteryid",getLotteryid())		
		.append("IssueCode",getIssueCode())		
		.append("WebIssueCode",getWebIssueCode())		
		.append("IssueWarnId",getIssueWarnId())		
		.append("CreateTime",getCreateTime())		
		.append("UpdateTime",getUpdateTime())		
		.append("ReadFlag",getReadFlag())		
		.append("WarnParas",getWarnParas())		
		.append("Status",getStatus())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getLotteryid())
		.append(getIssueCode())
		.append(getWebIssueCode())
		.append(getIssueWarnId())
		.append(getCreateTime())
		.append(getUpdateTime())
		.append(getReadFlag())
		.append(getWarnParas())
		.append(getStatus())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof GameWarnIssue == false) return false;
		if(this == obj) return true;
		GameWarnIssue other = (GameWarnIssue)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getLotteryid(),other.getLotteryid())

		.append(getIssueCode(),other.getIssueCode())

		.append(getWebIssueCode(),other.getWebIssueCode())

		.append(getIssueWarnId(),other.getIssueWarnId())

		.append(getCreateTime(),other.getCreateTime())

		.append(getUpdateTime(),other.getUpdateTime())

		.append(getReadFlag(),other.getReadFlag())

		.append(getWarnParas(),other.getWarnParas())

		.append(getStatus(),other.getStatus())

			.isEquals();
	}

	public String getStatusRout() {
		return statusRout;
	}

	public void setStatusRout(String statusRout) {
		this.statusRout = statusRout;
	}

	public Long getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(Long noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
}

