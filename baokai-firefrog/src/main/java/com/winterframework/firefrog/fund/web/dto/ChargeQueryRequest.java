/**   
* @Title: ChargeQueryRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-4 上午11:07:53 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/** 
* @ClassName: ChargeQueryRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-4 上午11:07:53 
*  交易流水号 ，订单状态，用户名，所属总代，申请时间起始值 ，申请时间结束值，收款卡 ，交易时间起始值，交易时间结束值
*  ，付款卡，付款户名，加游戏币时间起始值，加游戏币时间结束值
*/
public class ChargeQueryRequest implements Serializable {

	private static final long serialVersionUID = 8146093340931096310L;

	private Long userId;
	//用户名
	private String account;
	//所属总代
	private String topVip;
	private String topAcc;
	//交易流水号
	private String sn;
	//申请时间起始值 
	private Date fromDate;
	//申请时间结束值
	private Date toDate;
	//交易时间起始值 
	private Date fromDealDate;
	//交易时间结束值
	private Date toDealDate;
	//加游戏币时间起始值 
	private Date fromAddCoinDate;
	//加游戏币时间结束值 
	private Date toAddCoinDate;
	//DP操作時間 
	private Date fromOperatingDate;
	//DP操作時間
	private Date toOperatingDate;
	//付款卡
	private String payAcct;
	//收款卡
	private String rcvAcct;
	private String applyCardAcct;
	private String userAct;
	//付款户名
	private String payNo;
	private String isReport;
	private Long platVersion;
	//订单状态
	private Long[] status;
	private Long[] depositeMode;
	
	private Long payBankId;
	//帳號回收紀錄時間
	private Date recycleDate;
	
	private Long[] notAppealStatus;
	private Long chargeMode;

	public Long getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(Long chargeMode) {
		this.chargeMode = chargeMode;
	}

	public String getApplyCardAcct() {
		return applyCardAcct;
	}

	public Long getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(Long payBankId) {
		this.payBankId = payBankId;
	}

	

	public void setApplyCardAcct(String applyCardAcct) {
		this.applyCardAcct = applyCardAcct;
	}


	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	

	public Long[] getDepositeMode() {
		return depositeMode;
	}

	public void setDepositeMode(Long[] depositeMode) {
		this.depositeMode = depositeMode;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUserAct() {
		return userAct;
	}

	public void setUserAct(String userAct) {
		this.userAct = userAct;
	}

	public Long[] getStatus() {
		return status;
	}

	public void setStatus(Long[] status) {
		this.status = status;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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

	public String getTopVip() {
		return topAcc==null?topVip:topAcc;
	}

	public void setTopVip(String topVip) {
		this.topVip = topVip;
	}

	public Date getFromDealDate() {
		return fromDealDate;
	}

	public void setFromDealDate(Date fromDealDate) {
		this.fromDealDate = fromDealDate;
	}

	public Date getToDealDate() {
		return toDealDate;
	}

	public void setToDealDate(Date toDealDate) {
		this.toDealDate = toDealDate;
	}

	public Date getFromAddCoinDate() {
		return fromAddCoinDate;
	}

	public void setFromAddCoinDate(Date fromAddCoinDate) {
		this.fromAddCoinDate = fromAddCoinDate;
	}

	public Date getToAddCoinDate() {
		return toAddCoinDate;
	}

	public void setToAddCoinDate(Date toAddCoinDate) {
		this.toAddCoinDate = toAddCoinDate;
	}

	public String getPayAcct() {
		return payAcct;
	}

	public void setPayAcct(String payAcct) {
		this.payAcct = payAcct;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRcvAcct() {
		return rcvAcct;
	}

	public void setRcvAcct(String rcvAcct) {
		this.rcvAcct = rcvAcct;
	}

	public String getTopAcc() {
		return topAcc==null?topVip:topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public Long getPlatVersion() {
		return platVersion;
	}

	public void setPlatVersion(Long platVersion) {
		this.platVersion = platVersion;
	}

	public Date getRecycleDate() {
		return recycleDate;
	}

	public void setRecycleDate(Date recycleDate) {
		this.recycleDate = recycleDate;
	}

	public Date getFromOperatingDate() {
		return fromOperatingDate;
	}

	public void setFromOperatingDate(Date fromOperatingDate) {
		this.fromOperatingDate = fromOperatingDate;
	}

	public Date getToOperatingDate() {
		return toOperatingDate;
	}

	public void setToOperatingDate(Date toOperatingDate) {
		this.toOperatingDate = toOperatingDate;
	}

	public Long[] getNotAppealStatus() {
		return notAppealStatus;
	}

	public void setNotAppealStatus(Long[] notAppealStatus) {
		this.notAppealStatus = notAppealStatus;
	}

}
