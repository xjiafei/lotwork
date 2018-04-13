/**   
* @Title: SingleIssueIncomeAndExpenseEntity.java 
* @Package com.winterframework.firefrog.game.entity 
* @Description: winter-game-common.SingleIssueIncomeAndExpenseEntity.java 
* @author Denny  
* @date 2014-2-21 下午4:02:00 
* @version V1.0   
*/
package com.winterframework.firefrog.game.entity;

import java.io.Serializable;

/** 
* @ClassName: SingleIssueIncomeAndExpenseEntity 
* @Description: 单期收支相关数据实体Bean 
* @author Denny 
* @date 2014-2-21 下午4:02:00 
*  
*/
public class SingleIssueIncomeAndExpenseEntity implements Serializable {

	private static final long serialVersionUID = 3834379819014993417L;

	/** 彩种名称 */
	private String lotteryName;
	/** web奖期期号 */
	private String webIssueCode;
	/** 销售时间段 */
	private String saleTimePeriod;
	/** 实际投注：该奖期截止目前的实际售出金额 */
	private Long actualSoldAmount;
	/** 撤销投注： */
	private Long canceledAmount;
	/** 撤单手续费：该奖期在开奖前用户主动撤单产生的撤单手续费 */
	private Long cancellationsFee;
	/** 待派奖金：该奖期截止目前处理中的奖金金额 */
	private Long toDistributeBonuses;
	/** 实派奖金：该奖期截止目前已派至用户且未进行扣除的奖金 */
	private Long distributedBonuses;
	/** 实派返点：该奖期截止目前已派送且未被扣除的返点 */
	private Long distributedRetPoint;
	/** 营收：计算公式，投注金额+手续费-实派（奖金+返点） */
	private Long revenue;

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public String getSaleTimePeriod() {
		return saleTimePeriod;
	}

	public void setSaleTimePeriod(String saleTimePeriod) {
		this.saleTimePeriod = saleTimePeriod;
	}

	public Long getActualSoldAmount() {
		return actualSoldAmount;
	}

	public void setActualSoldAmount(Long actualSoldAmount) {
		this.actualSoldAmount = actualSoldAmount;
	}

	public Long getCanceledAmount() {
		return canceledAmount;
	}

	public void setCanceledAmount(Long canceledAmount) {
		this.canceledAmount = canceledAmount;
	}

	public Long getCancellationsFee() {
		return cancellationsFee;
	}

	public void setCancellationsFee(Long cancellationsFee) {
		this.cancellationsFee = cancellationsFee;
	}

	public Long getToDistributeBonuses() {
		return toDistributeBonuses;
	}

	public void setToDistributeBonuses(Long toDistributeBonuses) {
		this.toDistributeBonuses = toDistributeBonuses;
	}

	public Long getDistributedBonuses() {
		return distributedBonuses;
	}

	public void setDistributedBonuses(Long distributedBonuses) {
		this.distributedBonuses = distributedBonuses;
	}

	public Long getDistributedRetPoint() {
		return distributedRetPoint;
	}

	public void setDistributedRetPoint(Long distributedRetPoint) {
		this.distributedRetPoint = distributedRetPoint;
	}

	public Long getRevenue() {
		return this.actualSoldAmount + this.cancellationsFee - (this.distributedBonuses + this.distributedRetPoint);
	}

	public void setRevenue(Long revenue) {
		this.revenue = revenue;
	}

}
