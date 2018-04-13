package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: SpiteStrucForUI 
* @Description: 
* @author Alan
* @date 2013-10-23 下午4:24:37 
*  
*/
public class SpiteStrucForUI {

	private String		orderCode;
	private String 	lotteryName;
	private String 	webIssueCode;
	private Long		userId;
	private String		account;
	private String 		saleTime;
	private Long		totamount;
	private String		status;
	private Integer		userStatus;
	private Long		orderId;
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public Long getTotamount() {
		return totamount;
	}
	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
