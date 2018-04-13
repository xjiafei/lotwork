package com.winterframework.firefrog.index.web.dto;

import java.io.Serializable;

public class OrdersStrucDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 订单编号 */
	private String orderCode;
	/** 彩种 */
	private Long lotteryid;
	/** 彩种名称*/
	private String lotteryName;
	/** 期号 */
	private Long issueCode;
	/** web期号 */
	private String webIssueCode;
	/** 订单总金额 */
	private Long totamount;
	/** 订单奖金（计算中奖注单得到的奖金数） */
	private Long totwin;
	/** 状态 */
	private Integer status;
	/** 状态名称 */
	private String statusName;
	/** 订单父类型 */
	private Integer parentType;
	/** 父类型ID */
	private Long parentid;
	/** 投注时间 */
	private Long saleTime;
	/** 投注时间 */
	private String formatSaleTime;
	/** 开奖号码 */
	private String numberRecord;
	/** 订单ID */
	private Long orderId;
	/** 用户名 */
	private String account;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
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

	public Long getTotwin() {
		return totwin;
	}

	public void setTotwin(Long totwin) {
		this.totwin = totwin;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getParentType() {
		return parentType;
	}

	public void setParentType(Integer parentType) {
		this.parentType = parentType;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public Long getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Long saleTime) {
		this.saleTime = saleTime;
	}

	public String getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getFormatSaleTime() {
		return formatSaleTime;
	}

	public void setFormatSaleTime(String formatSaleTime) {
		this.formatSaleTime = formatSaleTime;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
}
