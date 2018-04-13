package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class WinsSumReportQueryWebResponse implements Serializable {

	private static final long serialVersionUID = 6768506916578433159L;

	private String lotteryName;
	/** 总期数 */
	private Integer totalIssueCount;
	/** 销售额总计 */
	private Long totalSalesSum;
	/** 返点总额总计 */
	private Long totalPointsSum;
	/** 撤单手续费总计 */
	private Long totalCancelSum;
	/** 返奖总额总计 */
	private Long totalWinsSum;
	/** 盈亏值总计 */
	private Long totalProfitSum;
	
	private String startTime;
	private String endTime;

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public Integer getTotalIssueCount() {
		return totalIssueCount;
	}

	public void setTotalIssueCount(Integer totalIssueCount) {
		this.totalIssueCount = totalIssueCount;
	}

	public Long getTotalSalesSum() {
		return totalSalesSum;
	}

	public void setTotalSalesSum(Long totalSalesSum) {
		this.totalSalesSum = totalSalesSum;
	}

	public Long getTotalPointsSum() {
		return totalPointsSum;
	}

	public void setTotalPointsSum(Long totalPointsSum) {
		this.totalPointsSum = totalPointsSum;
	}

	public Long getTotalCancelSum() {
		return totalCancelSum;
	}

	public void setTotalCancelSum(Long totalCancelSum) {
		this.totalCancelSum = totalCancelSum;
	}

	public Long getTotalWinsSum() {
		return totalWinsSum;
	}

	public void setTotalWinsSum(Long totalWinsSum) {
		this.totalWinsSum = totalWinsSum;
	}

	public Long getTotalProfitSum() {
		return totalProfitSum;
	}

	public void setTotalProfitSum(Long totalProfitSum) {
		this.totalProfitSum = totalProfitSum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
