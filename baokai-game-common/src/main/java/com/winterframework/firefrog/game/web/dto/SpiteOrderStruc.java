package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.winterframework.firefrog.common.util.DataConverterUtil;

/**
 * 
* @ClassName: SpiteOrderStruc 
* @Description:5.2.32	SPITE_ORDER_STRUC（恶意方案基本结构）CSI032
* @author Richard
* @date 2013-10-12 上午10:13:18 
*
 */
public class SpiteOrderStruc implements Serializable {

	private static final long serialVersionUID = 8175366582672391435L;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private Long	lotteryid;
	private String lotteryName;
	private String		orderCode;
	private Long		issueCode;
	private String 	webIssueCode;
	private Long		totamount;
	private Long		userId;
	private String		account;
	private Long 		saleTime;
	private String isoSaleTime;
	private String		status;
	private Integer		userStatus;
	private Long orderId;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
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
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
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
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	/**
	 * 
	* @Title: getStatus 
	* @Description: 0 待审核 1 已审核 2 未通过审核
	* @return
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 
	* @Title: setStatus 
	* @Description: 0 待审核 1 已审核 2 未通过审核
	* @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 
	* @Title: getUserStatus 
	* @Description: 1 未冻结 2 已冻结
	* @return
	 */
	public Integer getUserStatus() {
		return userStatus;
	}
	/**
	 * 
	* @Title: setUserStatus 
	* @Description: 1 未冻结 2 已冻结
	* @param userStatus
	 */
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Long getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
	}

	public String getIsoSaleTime() {
		isoSaleTime = dateFormat.format(DataConverterUtil.convertLong2Date(saleTime));
		return isoSaleTime;
	}
	public void setIsoSaleTime(String isoSaleTime) {
		this.isoSaleTime = isoSaleTime;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
