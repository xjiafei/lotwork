package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GameReportIssue  extends BaseEntity{
	private static final long serialVersionUID = 628653886678731866L;
	//alias
	public static final String TABLE_ALIAS = "游戏报表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_ISSUE_CODE = "奖期";
	public static final String ALIAS_WEB_ISSUE_CODE = "WEB显示奖期";
	public static final String ALIAS_TOTAL_SALES = "销售总金额";
	public static final String ALIAS_TOTAL_POINTS = "返点实际总金额";
	public static final String ALIAS_TOTAL_WINS = "返奖总金额";
	public static final String ALIAS_TOTAL_PROFIT = "盈亏值";
	public static final String ALIAS_TOTAL_CANCEL = "撤单手续费";	
	public static final String ALIAS_TOTAL_CANCEL_ORDER = "撤单总金额";
	public static final String ALIAS_TOTAL_ACTUAL_AWARD = "实际派奖奖金";
	public static final String ALIAS_REPORT_DATE = "创建时间";
	public static final String ALIAS_UPDATE_TIME ="更新时间";

	//date formats

	//columns START
	private Long lotteryId;
	private Long issueCode;
	private String webIssueCode;	
	private Long totalSales;
	private Long totalPoints;
	private Long totalWins;
	private Long totalProfit;
	private Long totalCancel;
	private Long totalCancelOrder;
	private Long totalActualAward;
	private Date reportDate;
	private Date updateTime;
	//columns END
	private String lotteryName;
	private Date saleTimeBegin;
	private Date saleTimeEnd;
	
	
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public Date getSaleTimeBegin() {
		return saleTimeBegin;
	}
	public void setSaleTimeBegin(Date saleTimeBegin) {
		this.saleTimeBegin = saleTimeBegin;
	}
	public Date getSaleTimeEnd() {
		return saleTimeEnd;
	}
	public void setSaleTimeEnd(Date saleTimeEnd) {
		this.saleTimeEnd = saleTimeEnd;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public Long getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}
	public Long getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Long totalPoints) {
		this.totalPoints = totalPoints;
	}
	public Long getTotalWins() {
		return totalWins;
	}
	public void setTotalWins(Long totalWins) {
		this.totalWins = totalWins;
	}
	public Long getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(Long totalProfit) {
		this.totalProfit = totalProfit;
	}
	public Long getTotalCancel() {
		return totalCancel;
	}
	public void setTotalCancel(Long totalCancel) {
		this.totalCancel = totalCancel;
	}
	public Long getTotalCancelOrder() {
		return totalCancelOrder;
	}
	public void setTotalCancelOrder(Long totalCancelOrder) {
		this.totalCancelOrder = totalCancelOrder;
	}
	public Long getTotalActualAward() {
		return totalActualAward;
	}
	public void setTotalActualAward(Long totalActualAward) {
		this.totalActualAward = totalActualAward;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
