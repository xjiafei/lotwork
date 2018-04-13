package com.winterframework.firefrog.game.dao.vo;

import java.io.Serializable;

/**
 * @ClassName: UserCenterReportVO
 * @Description: 用户中心盈亏报表自定义接收数据库查询数据vo
 * @author david
 * @date 2013-9-17 下午1:33:32
 * 
 */
public class UserCenterReportVo implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	/* 用户名 */
	private String account;
	/* 用户id */
	private Long userId;
	/* 用户组：-1: user 0:top agent 1-10:common agent */
	private Integer userLvl;

	private String userChain;
	/* 下级销售总额 */
	private Long totalSubuserSaleroom;
	/* 返点总额 */
	private Long totalSubuserPoint;
	/* 中奖总金额 */
	private Long totalSubuserWins;
	/* 活动礼金 */
	private Long activityGifts;

	private Long trueBet;

	private Long result;

	private Long hasNextUser;

	public Long getActivityGifts() {
		return activityGifts;
	}

	public void setActivityGifts(Long activityGifts) {
		this.activityGifts = activityGifts;
	}

	public Long getTrueBet() {
		return trueBet;
	}

	public void setTrueBet(Long trueBet) {
		this.trueBet = trueBet;
	}

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

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

	public Long getHasNextUser() {
		return hasNextUser;
	}

	public void setHasNextUser(Long hasNextUser) {
		this.hasNextUser = hasNextUser;
	}

}
