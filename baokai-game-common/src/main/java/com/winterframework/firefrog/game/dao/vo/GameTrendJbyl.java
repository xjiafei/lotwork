package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GameTrendJbyl extends BaseEntity {

	private static final long serialVersionUID = -4019047388386570639L;
	//alias
	public static final String TABLE_ALIAS = "基本走势遗漏走势表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_WEB_ISSUE_CODE = "显示奖期";
	public static final String ALIAS_TREND_TYPE = "走势类型 1 位数遗漏 2 分布遗漏 3 跨度遗漏 4 组选遗漏 5 和值遗漏";
	public static final String ALIAS_VALUE = "图表数据";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_GAME_SET_CODE = "玩法组";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群";

	//date formats

	//columns START
	private Long lotteryid;
	private Long issueCode;
	private String webIssueCode;
	private String trendType;
	private String value;
	private String webValue;
	private Date createTime;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Integer gameGroupCode;
	private String numberRecord;
	private Long userId;
	
	//columns END

	public GameTrendJbyl() {
	}

	public GameTrendJbyl(Long id) {
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

	public String getWebValue() {
		return webValue;
	}

	public void setWebValue(String webValue) {
		this.webValue = webValue;
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

	public void setTrendType(String value) {
		this.trendType = value;
	}

	public String getTrendType() {
		return this.trendType;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setGameSetCode(Integer value) {
		this.gameSetCode = value;
	}

	public Integer getGameSetCode() {
		return this.gameSetCode;
	}

	public void setBetMethodCode(Integer value) {
		this.betMethodCode = value;
	}

	public Integer getBetMethodCode() {
		return this.betMethodCode;
	}

	public void setGameGroupCode(Integer value) {
		this.gameGroupCode = value;
	}

	public Integer getGameGroupCode() {
		return this.gameGroupCode;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
