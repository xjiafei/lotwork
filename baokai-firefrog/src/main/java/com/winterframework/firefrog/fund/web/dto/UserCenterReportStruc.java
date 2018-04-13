package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

/** 
* @ClassName: UserCenterReportStruc 
* @Description: 用户中心盈亏报表结构
* @author david 
* @date 2013-9-17 下午1:33:32 
*  
*/
public class UserCenterReportStruc implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	/*用户名*/
	private String account;
	/*用户id*/
	private Long userId;
	/*用户组：-1: user 0:top agent 1-10:common agent*/
	private Integer userLvl;
	/*下级销售总额*/
	private Long totalSubuserSaleroom;
	/*返点总额*/
	private Long totalSubuserPoint;
	/*实际销售总额*/
	private Long ActualTotalSubuserSaleroom;
	/*中奖总金额*/
	private Long totalSubuserWins;
	/*盈亏值*/
	private Long totalProfit;
	
	private Long hasNextUser;

	public UserCenterReportStruc() {

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

	public Integer getUserLvl() {
		return userLvl;
	}

	public void setUserLvl(Integer userLvl) {
		this.userLvl = userLvl;
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

	public Long getActualTotalSubuserSaleroom() {
		return ActualTotalSubuserSaleroom;
	}

	public void setActualTotalSubuserSaleroom(Long actualTotalSubuserSaleroom) {
		ActualTotalSubuserSaleroom = actualTotalSubuserSaleroom;
	}

	public Long getTotalSubuserWins() {
		return totalSubuserWins;
	}

	public void setTotalSubuserWins(Long totalSubuserWins) {
		this.totalSubuserWins = totalSubuserWins;
	}

	public Long getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Long totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Long getHasNextUser() {
		return hasNextUser;
	}

	public void setHasNextUser(Long hasNextUser) {
		this.hasNextUser = hasNextUser;
	}
}
