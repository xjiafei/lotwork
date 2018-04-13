package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class QueryUserByCriteriaRequestDTO implements Serializable {

	private static final long serialVersionUID = 7570693801719004456L;

	private long userId;

	/**登录日期*/
	private Long fromLoginDate;
	private Long toLoginDate;
	private Long vipLvl;
	private String orderBy;
	private String userChain;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Long getFromLoginDate() {
		return fromLoginDate;
	}
	public void setFromLoginDate(Long fromLoginDate) {
		this.fromLoginDate = fromLoginDate;
	}
	public Long getToLoginDate() {
		return toLoginDate;
	}
	public void setToLoginDate(Long toLoginDate) {
		this.toLoginDate = toLoginDate;
	}
	public Long getVipLvl() {
		return vipLvl;
	}
	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getUserChain() {
		return userChain;
	}
	public void setUserChain(String userChain) {
		this.userChain = userChain;
	}
	
}
