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

public class GameWinsReport extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966769353979115985L;
	//alias
	public static final String TABLE_ALIAS = "单期盈亏报表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_LOTTERY_TYPE_CODE = "彩票类型";
	public static final String ALIAS_LOTTERY_SERIES_CODE = "彩系编码";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群编码";
	public static final String ALIAS_GAME_SET_CODE = "玩法组编码";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式编码";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_WEB_ISSUE_CODE = "WEB显示奖期";
	public static final String ALIAS_TOTAL_SALES = "销售总金额";
	public static final String ALIAS_TOTAL_POINTS = "返点总金额";
	public static final String ALIAS_TOTAL_WINS = "返奖总金额";
	public static final String ALIAS_TOTAL_PROFIT = "盈亏值";
	public static final String ALIAS_TOTAL_CANCEL = "撤单手续费";
	public static final String ALIAS_REPORT_DATE = "日期";

	//date formats

	//columns START
	private Long lotteryid;
	private String lotteryName;
	private Long lotteryTypeCode;
	private Long lotterySeriesCode;
	private Long gameGroupCode;
	private Long gameSetCode;
	private Long betMethodCode;
	private String gameGroupName;
	private String gameSetName;
	private String betMethodName;
	private Long issueCode;
	private String webIssueCode;
	private Long totalSales;
	private Long totalPoints;
	private Long totalWins;
	private Long totalProfit;
	private Long totalCancel;
	private Date reportDate;
	private Date createTime;
	private Date updateTime;
	//columns END

	public GameWinsReport() {
	}

	public GameWinsReport(Long id) {
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	public Long getLotteryid() {
		return this.lotteryid;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public void setLotteryTypeCode(Long value) {
		this.lotteryTypeCode = value;
	}

	public Long getLotteryTypeCode() {
		return this.lotteryTypeCode;
	}

	public void setLotterySeriesCode(Long value) {
		this.lotterySeriesCode = value;
	}

	public Long getLotterySeriesCode() {
		return this.lotterySeriesCode;
	}

	public void setGameGroupCode(Long value) {
		this.gameGroupCode = value;
	}

	public Long getGameGroupCode() {
		return this.gameGroupCode;
	}

	public void setGameSetCode(Long value) {
		this.gameSetCode = value;
	}

	public Long getGameSetCode() {
		return this.gameSetCode;
	}

	public void setBetMethodCode(Long value) {
		this.betMethodCode = value;
	}

	public Long getBetMethodCode() {
		return this.betMethodCode;
	}

	public String getGameGroupName() {
		return gameGroupName;
	}

	public void setGameGroupName(String gameGroupName) {
		this.gameGroupName = gameGroupName;
	}

	public String getGameSetName() {
		return gameSetName;
	}

	public void setGameSetName(String gameSetName) {
		this.gameSetName = gameSetName;
	}

	public String getBetMethodName() {
		return betMethodName;
	}

	public void setBetMethodName(String betMethodName) {
		this.betMethodName = betMethodName;
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

	public void setTotalSales(Long value) {
		this.totalSales = value;
	}

	public Long getTotalSales() {
		return this.totalSales;
	}

	public void setTotalPoints(Long value) {
		this.totalPoints = value;
	}

	public Long getTotalPoints() {
		return this.totalPoints;
	}

	public void setTotalWins(Long value) {
		this.totalWins = value;
	}

	public Long getTotalWins() {
		return this.totalWins;
	}

	public void setTotalProfit(Long value) {
		this.totalProfit = value;
	}

	public Long getTotalProfit() {
		return this.totalProfit;
	}

	public void setTotalCancel(Long value) {
		this.totalCancel = value;
	}

	public Long getTotalCancel() {
		return this.totalCancel;
	}

	public void setReportDate(Date value) {
		this.reportDate = value;
	}

	public Date getReportDate() {
		return this.reportDate;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Lotteryid", getLotteryid())
				.append("LotteryTypeCode", getLotteryTypeCode()).append("LotterySeriesCode", getLotterySeriesCode())
				.append("GameGroupCode", getGameGroupCode()).append("GameSetCode", getGameSetCode())
				.append("BetMethodCode", getBetMethodCode()).append("IssueCode", getIssueCode())
				.append("WebIssueCode", getWebIssueCode()).append("TotalSales", getTotalSales())
				.append("TotalPoints", getTotalPoints()).append("TotalWins", getTotalWins())
				.append("TotalProfit", getTotalProfit()).append("TotalCancel", getTotalCancel())
				.append("ReportDate", getReportDate()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLotteryid()).append(getLotteryTypeCode())
				.append(getLotterySeriesCode()).append(getGameGroupCode()).append(getGameSetCode())
				.append(getBetMethodCode()).append(getIssueCode()).append(getWebIssueCode()).append(getTotalSales())
				.append(getTotalPoints()).append(getTotalWins()).append(getTotalProfit()).append(getTotalCancel())
				.append(getReportDate()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameWinsReport == false)
			return false;
		if (this == obj)
			return true;
		GameWinsReport other = (GameWinsReport) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLotteryid(), other.getLotteryid())

		.append(getLotteryTypeCode(), other.getLotteryTypeCode())

		.append(getLotterySeriesCode(), other.getLotterySeriesCode())

		.append(getGameGroupCode(), other.getGameGroupCode())

		.append(getGameSetCode(), other.getGameSetCode())

		.append(getBetMethodCode(), other.getBetMethodCode())

		.append(getIssueCode(), other.getIssueCode())

		.append(getWebIssueCode(), other.getWebIssueCode())

		.append(getTotalSales(), other.getTotalSales())

		.append(getTotalPoints(), other.getTotalPoints())

		.append(getTotalWins(), other.getTotalWins())

		.append(getTotalProfit(), other.getTotalProfit())

		.append(getTotalCancel(), other.getTotalCancel())

		.append(getReportDate(), other.getReportDate())

		.isEquals();
	}
}
