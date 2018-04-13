package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;


/** 
* @ClassName UserAgentIncomeVO 
* @Description 总代盈亏  VIEW_USER_AGENT_INCOME
* @author  hugh
* @date 2014年9月23日 下午3:37:35 
*  
*/
public class UserAgentIncomeVO  extends BaseEntity{

	private static final long serialVersionUID = 8966894042830402494L;
	private String account;
	private Long userLvl;
	private String userChain;
	private Long isFreeze;
	private Long bet;
	private Long ret;
	private Long trueBet;
	private Long win;
	private Long activityGifts;
	private Long result;
	private Long userId;
	private Long moneyMode;
	private String betTypeCode;
	private Long lotteryId;
	private Date createTime;
	
	/*下级销售总额*/
	private Long totalSubuserSaleroom;
	/*返点总额*/
	private Long totalSubuserPoint;
	/*中奖总金额*/
	private Long totalSubuserWins;
	
	public Long getActivityGifts() {
		return activityGifts;
	}
	public void setActivityGifts(Long activityGifts) {
		this.activityGifts = activityGifts;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMoneyMode() {
		return moneyMode;
	}
	public void setMoneyMode(Long moneyMode) {
		this.moneyMode = moneyMode;
	}
	public String getBetTypeCode() {
		return betTypeCode;
	}
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getTotalSubuserSaleroom() {
		return totalSubuserSaleroom;
	}
	public void setTotalSubuserSaleroom(Long totalSubuserSaleroom) {
		this.totalSubuserSaleroom = totalSubuserSaleroom;
	}
	public Long getTotalSubuserPoint() {
		return totalSubuserPoint;
	}
	public void setTotalSubuserPoint(Long totalSubuserPoint) {
		this.totalSubuserPoint = totalSubuserPoint;
	}
	public Long getTotalSubuserWins() {
		return totalSubuserWins;
	}
	public void setTotalSubuserWins(Long totalSubuserWins) {
		this.totalSubuserWins = totalSubuserWins;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getUserLvl() {
		return userLvl;
	}
	public void setUserLvl(Long userLvl) {
		this.userLvl = userLvl;
	}
	public String getUserChain() {
		return userChain;
	}
	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}
	public Long getIsFreeze() {
		return isFreeze;
	}
	public void setIsFreeze(Long isFreeze) {
		this.isFreeze = isFreeze;
	}
	public Long getBet() {
		return bet;
	}
	public void setBet(Long bet) {
		this.bet = bet;
	}
	public Long getRet() {
		return ret;
	}
	public void setRet(Long ret) {
		this.ret = ret;
	}
	public Long getTrueBet() {
		return trueBet;
	}
	public void setTrueBet(Long trueBet) {
		this.trueBet = trueBet;
	}
	public Long getWin() {
		return win;
	}
	public void setWin(Long win) {
		this.win = win;
	}
	public Long getResult() {
		return result;
	}
	public void setResult(Long result) {
		this.result = result;
	}
	
	
}
