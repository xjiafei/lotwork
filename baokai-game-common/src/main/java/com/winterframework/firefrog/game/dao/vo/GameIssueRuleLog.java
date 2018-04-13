 package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class GameIssueRuleLog extends BaseEntity {
	
	private static final long serialVersionUID = -6969861883692684139L;
	//alias
	public static final String TABLE_ALIAS = "奖期生成规则历史表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_RULES_NAME = "规则名称";
	public static final String ALIAS_RULE_START_TIME = "生效开始时间";
	public static final String ALIAS_RULE_END_TIME = "生效结束时间";
	public static final String ALIAS_STATUS = "规则状态 1:进行中 2:已删除 3:已停止";
	public static final String ALIAS_OPEN_AWARD_PERIOD = "开奖周期";
	public static final String ALIAS_RULE_TYPE = "规则类型 1 常规规则 2 特例规则 3 休市规则";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	
	//date formats
	
	//columns START
	private Long lotteryid;
	private String issueRulesName;
	private Date ruleStartTime;
	private Date ruleEndTime;
	private Long status;
	private String openAwardPeriod;
	private Long ruleType;
	private Date createTime;
	private Date updateTime;
	//columns END

	public GameIssueRuleLog(){
	}

	public GameIssueRuleLog(
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
	public void setIssueRulesName(String value) {
		this.issueRulesName = value;
	}
	
	public String getIssueRulesName() {
		return this.issueRulesName;
	}
	
	public void setStatus(Long value) {
		this.status = value;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setOpenAwardPeriod(String value) {
		this.openAwardPeriod = value;
	}
	
	public String getOpenAwardPeriod() {
		return this.openAwardPeriod;
	}
	public void setRuleType(Long value) {
		this.ruleType = value;
	}
	
	public Long getRuleType() {
		return this.ruleType;
	}

	public Date getRuleStartTime() {
		return ruleStartTime;
	}

	public void setRuleStartTime(Date ruleStartTime) {
		this.ruleStartTime = ruleStartTime;
	}

	public Date getRuleEndTime() {
		return ruleEndTime;
	}

	public void setRuleEndTime(Date ruleEndTime) {
		this.ruleEndTime = ruleEndTime;
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

