package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: OperationReportStruc 
* @Description: 运营盈亏报表基本结构 
* @author Denny 
* @date 2013-10-16 上午9:58:42 
*  
*/
public class OperationReportStruc implements Serializable {

	private static final long serialVersionUID = -8276986772684147475L;

	private Long lotteryid;
	private String lotteryName;
	private Long issueCode;
	private String webIssueCode;
	private Long reportDate;
	/** 返点总额 */
	private Long totalPoints;
	/** 销售总额 */
	private Long totalSales;
	/** 撤单手续费 */
	private Long totalCancel;
	/** 返奖总额 */
	private Long totalWins;
	/** 盈亏值 */
	private Long totalProfit;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
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

	public Long getReportDate() {
		return reportDate;
	}

	public void setReportDate(Long reportDate) {
		this.reportDate = reportDate;
	}

	public Long getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Long totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Long getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}

	public Long getTotalCancel() {
		return totalCancel;
	}

	public void setTotalCancel(Long totalCancel) {
		this.totalCancel = totalCancel;
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

}
