package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GameTrendIssue extends BaseEntity {
  
	private static final long serialVersionUID = 4596114633752785136L;
	//alias
	public static final String TABLE_ALIAS = "走势奖期表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_WEB_ISSUE_CODE = "显示奖期";
	public static final String ALIAS_NUMBER_RECORD = "开奖号码";
	public static final String ALIAS_MAX_OMIT = "历史最大遗漏值";
	public static final String ALIAS_WEB_MAX_OMIT = "历史最大遗漏显示值";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	//date formats

	//columns START
	private Long lotteryid;
	private Long issueCode;
	private String webIssueCode; 
	private String numberRecord;
	private String maxOmit;
	private String webMaxOmit;
	private Date createTime;
	private Date updateTime; 	
	//columns END
 
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	public String getMaxOmit() {
		return maxOmit;
	}
	public void setMaxOmit(String maxOmit) {
		this.maxOmit = maxOmit;
	}
	
	public String getWebMaxOmit() {
		return webMaxOmit;
	}
	public void setWebMaxOmit(String webMaxOmit) {
		this.webMaxOmit = webMaxOmit;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
