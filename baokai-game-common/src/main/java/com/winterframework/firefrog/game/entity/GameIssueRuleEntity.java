package com.winterframework.firefrog.game.entity;

import java.util.Date;
import java.util.List;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameIssueRuleEntity {
	public static long AUDIT_LIMIT_DAY=4; //审核时规则生效时间必须在几天之后的凌晨
	public static long STATUS_UNAUDIT=4;
	public static long STATUS_INUSEING=1;
	public static long STATUS_DELETED=2;
	public static long STATUS_STOP=3;
	private Long id;
	private Lottery lottery;
	private String issueRulesName;
	private Date ruleStartTime;
	private Date ruleEndTime;
	private String openAwardPeriod;
	private RuleType ruleType;
	private Long status;
	private Date createTime;
	private Date updateTime;
	private Long ruleid;
	private Date stopStartTime;
	private Date stopEndTime;
	
	private Long seriesIssueCode;

	private List<GameIssueTemplateEntity> gameIssueTemplateEntitys;

	public enum RuleType {
		COMMEN(1), SPECIAL(2), STOP(3);
		private int value = 0;

		RuleType(int action) {
			value = action;
		}

		public int getValue() {
			return value;
		}
	}

	public enum DayVale {
		MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6), SUNDAY(7);
		private int value = 0;

		DayVale(int action) {
			value = action;
		}

		public int getValue() {
			return value;
		}
	}

	public GameIssueRuleEntity() {
	}

	public GameIssueRuleEntity(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public void setIssueRulesName(String value) {
		this.issueRulesName = value;
	}

	public String getIssueRulesName() {
		return this.issueRulesName;
	}

	public void setOpenAwardPeriod(String value) {
		this.openAwardPeriod = value;
	}

	public String getOpenAwardPeriod() {
		return this.openAwardPeriod;
	}

	public void setStatus(Long value) {
		this.status = value;
	}

	public Long getStatus() {
		return this.status;
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

	public Long getRuleid() {
		return ruleid;
	}

	public void setRuleid(Long ruleid) {
		this.ruleid = ruleid;
	}

	public RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}

	public List<GameIssueTemplateEntity> getGameIssueTemplateEntitys() {
		return gameIssueTemplateEntitys;
	}

	public void setGameIssueTemplateEntitys(List<GameIssueTemplateEntity> gameIssueTemplateEntitys) {
		this.gameIssueTemplateEntitys = gameIssueTemplateEntitys;
	}

	public Date getStopStartTime() {
		return stopStartTime;
	}

	public void setStopStartTime(Date stopStartTime) {
		this.stopStartTime = stopStartTime;
	}

	public Date getStopEndTime() {
		return stopEndTime;
	}

	public void setStopEndTime(Date stopEndTime) {
		this.stopEndTime = stopEndTime;
	}

	public Long getSeriesIssueCode() {
		return seriesIssueCode;
	}

	public void setSeriesIssueCode(Long seriesIssueCode) {
		this.seriesIssueCode = seriesIssueCode;
	}
	
	
}
