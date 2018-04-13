package com.winterframework.firefrog.user.web.dto;

/**
 * 
 *    
 * 类功能说明: 多条件查询请求DTO
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 *  
 *
 */
public class QueryUserByCriteriaRequestDTO extends QueryUserListRequestDTO {

	private static final long serialVersionUID = -8581076995578926597L;

	private long userId;

	/**登录日期*/
	private Long fromLoginDate;
	private Long toLoginDate;
	private Long vipLvl;
	private String orderBy;
	private String userChain;
	private Long includeTeamBal;
	private String device;
	

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public QueryUserByCriteriaRequestDTO() {

	}

	public Long getVipLvl() {
		return vipLvl;
	}

	public Long getIncludeTeamBal() {
		return includeTeamBal;
	}

	public void setIncludeTeamBal(Long includeTeamBal) {
		this.includeTeamBal = includeTeamBal;
	}

	public void setVipLvl(Long vipLvl) {
		this.vipLvl = vipLvl;
	}

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
