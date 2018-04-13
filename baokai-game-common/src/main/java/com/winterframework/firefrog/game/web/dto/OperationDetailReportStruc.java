package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: OperationDetailReportStruc 
* @Description: 运营盈亏报表明细基本结构 
* @author Denny 
* @date 2013-10-23 上午9:41:54 
*  
*/
public class OperationDetailReportStruc implements Serializable {

	private static final long serialVersionUID = -5726415664690850836L;
	private Long lotteryId;
	private String gameGroupName;
	private Long gameGroupCode;
	private String gameSetName;
	private Long gameSetCode;
	private String betMethodName;
	private Long betMethodCode;
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

	
	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getGameGroupName() {
		return gameGroupName;
	}

	public void setGameGroupName(String gameGroupName) {
		this.gameGroupName = gameGroupName;
	}

	public Long getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Long gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public String getGameSetName() {
		return gameSetName;
	}

	public void setGameSetName(String gameSetName) {
		this.gameSetName = gameSetName;
	}

	public Long getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Long gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public String getBetMethodName() {
		return betMethodName;
	}

	public void setBetMethodName(String betMethodName) {
		this.betMethodName = betMethodName;
	}

	public Long getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Long betMethodCode) {
		this.betMethodCode = betMethodCode;
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
