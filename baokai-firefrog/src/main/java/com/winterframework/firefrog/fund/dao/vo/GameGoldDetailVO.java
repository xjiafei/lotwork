package com.winterframework.firefrog.fund.dao.vo;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName GameGoldDetail 
* @Description 游戏币明细 对应 VIEW_GAME_GOLD_DETAIL
* @author  hugh
* @date 2014年9月22日 下午3:32:34 
*  
*/
public class GameGoldDetailVO  extends BaseEntity{

	private static final long serialVersionUID = 8966894042830402494L;
	private String account;
	private Long isFreeze;
	private Long bal;
	private Long frozenAmt;
	private Long useMoney;
	private Long sumBal;
	private Long sumFrozen;
	private Long sumUseMoney;

	private Long endUseMoney;
	private Long endSumUseMoney;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getIsFreeze() {
		return isFreeze;
	}
	public void setIsFreeze(Long isFreeze) {
		this.isFreeze = isFreeze;
	}
	public Long getBal() {
		return bal;
	}
	public void setBal(Long bal) {
		this.bal = bal;
	}
	public Long getFrozenAmt() {
		return frozenAmt;
	}
	public void setFrozenAmt(Long frozenAmt) {
		this.frozenAmt = frozenAmt;
	}
	public Long getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(Long useMoney) {
		this.useMoney = useMoney;
	}
	public Long getSumBal() {
		return sumBal;
	}
	public void setSumBal(Long sumBal) {
		this.sumBal = sumBal;
	}
	public Long getSumFrozen() {
		return sumFrozen;
	}
	public void setSumFrozen(Long sumFrozen) {
		this.sumFrozen = sumFrozen;
	}
	public Long getSumUseMoney() {
		return sumUseMoney;
	}
	public void setSumUseMoney(Long sumUseMoney) {
		this.sumUseMoney = sumUseMoney;
	}
	public Long getEndUseMoney() {
		return endUseMoney;
	}
	public void setEndUseMoney(Long endUseMoney) {
		this.endUseMoney = endUseMoney;
	}
	public Long getEndSumUseMoney() {
		return endSumUseMoney;
	}
	public void setEndSumUseMoney(Long endSumUseMoney) {
		this.endSumUseMoney = endSumUseMoney;
	}
	
	

	
	
}
