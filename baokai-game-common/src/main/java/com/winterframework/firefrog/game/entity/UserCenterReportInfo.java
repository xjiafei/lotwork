package com.winterframework.firefrog.game.entity;

import java.io.Serializable;

import com.winterframework.firefrog.user.entity.User;

/**
 * @ClassName: UserCenterReportInfo
 * @Description: 用户中心盈亏报表结构
 * @author david
 * @date 2013-9-17 下午1:33:32
 * 
 */
public class UserCenterReportInfo implements Serializable {

	private static final long serialVersionUID = 6933950873653289409L;

	private User user;
	/* 下级销售总额 */
	private Long totalSubuserSaleroom;
	/* 返点总额 */
	private Long totalSubuserPoint;
	/* 实际销售总额 */
	private Long ActualTotalSubuserSaleroom;
	/* 中奖总金额 */
	private Long totalSubuserWins;
	/* 活动礼金 */
	private Long activityGifts;
	/* 盈亏值 */
	private Long totalProfit;
	/* 是否存在下級 */
	private Long hasNextUser;

	public Long getActivityGifts() {
		return activityGifts;
	}

	public void setActivityGifts(Long activityGifts) {
		this.activityGifts = activityGifts;
	}

	public UserCenterReportInfo() {

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
