package com.winterframework.firefrog.fund.dao.vo;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: UserCenterReportVO 
* @Description: 用户中心盈亏报表自定义接收数据库查询数据vo
* @author david 
* @date 2013-9-17 下午1:33:32 
*  
*/
public class UserCenterReportVo implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	/*用户名*/
	private String account;
	/*用户id*/
	private Long userId;
	/*用户组：-1: user 0:top agent 1-10:common agent*/
	private Long userLvl;
	
	private Long isFreeze;
	
	private Long moneyMode;
	
	private String betTypeCode;
	
	private Date createTime;
	
	private Long lotteryId; 
	
	private String userChain;
	/*下级销售总额*/
	private Long totalSubuserSaleroom;
	/*返点总额*/
	private Long totalSubuserPoint;
	/*中奖总金额*/
	private Long totalSubuserWins;

	public UserCenterReportVo() {

	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Long userLvl) {
		this.userLvl = userLvl;
	}
	
	public Long getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(Long isFreeze) {
		this.isFreeze = isFreeze;
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

	public String getUserChain() {
		return userChain;
	}

	public void setUserChain(String userChain) {
		this.userChain = userChain;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

}
