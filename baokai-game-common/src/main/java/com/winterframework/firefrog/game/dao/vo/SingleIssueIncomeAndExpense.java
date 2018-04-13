/**   
* @Title: SingleIssueIncomeAndExpense.java 
* @Package com.winterframework.firefrog.game.dao.vo 
* @Description: winter-game-common.SingleIssueIncomeAndExpense.java 
* @author Denny  
* @date 2014-2-21 下午4:36:00 
* @version V1.0   
*/
package com.winterframework.firefrog.game.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName: SingleIssueIncomeAndExpense 
* @Description: 单期收支数据vo 
* @author Denny 
* @date 2014-2-21 下午4:36:00 
*  
*/
public class SingleIssueIncomeAndExpense extends BaseEntity {

	private static final long serialVersionUID = 2708312915014574915L;
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

}
