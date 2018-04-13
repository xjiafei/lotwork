package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateDeSerializer;
//平台订单号 ， mow订单号 ，订单状态，用户名，所属总代，
//申请提现时间起始值 ，申请提现时间结束值 ，订单发给mow时间起始值 ，订单发给mow时间结束值，银行实际支付时间起始值 ，
//银行实际支付时间结束值，mow返回结果时间起始值，mow返回结果时间结束值，申请提现卡，申请提现户名

public class QueryFundWithdrawOrderRequest implements Serializable {

	private static final long serialVersionUID = -2770775973067523193L;
	//平台订单号
	private String sn;
	private Long userId;
	//mow订单号
	private String mcSn;
	//申请渠道
	private Long withdrawMode;
	//申请提现时间结束值
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date fromDate;
	//申请提现时间结束值
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date toDate;
	//发送mow时间结束值
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date fromNoticeMowDate;
	//发送mow时间结束值
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date toNoticeMowDate;
	//mow返回时间结束值
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date fromMowDate;
	//mow返回时间结束值
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date toMowDate;
	//DP時間
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date fromOperatingDate;
	//DP時間
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date toOperatingDate;
	//用户名
	private String applyAccount;
	//用户名
	private String topAcc;
	//订单状态
	private Long[] statuses;
	//申请提现卡账号
	private String applyCardAcct;
	//申请提现卡号
	private String applyCardNo;
	//一申管理员
	private String apprAccount;
	//复审管理员
	private String appr2Acct;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date yishenStartBegin;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date yishenStartEnd;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date yishenEndBegin;
	@JsonDeserialize(using = FirefrogDateDeSerializer.class) 
	private Date yishenEndEnd;
	private Integer[] riskType;
	private Long isVip;
	private Long withdrawAmtFrom;
	private Long withdrawAmtTo;
	private Long currFirst;
	private String currApprer;
	private Long realWithDrawAmtFrom;
	private Long realWithDrawAmtTo;
	private String isReport;
	
	//MOWAPI 查狀態用
	private String companyId;             //企業ID
	private String mownecumOrderNum;      //MOW  DP訂單號
	private String companyOrderNum;      //平台訂單

	//帳號回收紀錄時間
	private Date recycleDate;
	
	//紀錄是哪個php action 呼叫
	private String fromAction;
	
	
	public Long getWithdrawMode() {
		return withdrawMode;
	}
	public void setWithdrawMode(Long withdrawMode) {
		this.withdrawMode = withdrawMode;
	}
	public String getFromAction() {
		return fromAction;
	}
	public void setFromAction(String fromAction) {
		this.fromAction = fromAction;
	}
	public String getIsReport() {
		return isReport;
	}
	public String getApplyCardAcctStruc(){
		String tt="";
		if(this.getApplyCardAcct()!=null){
			//"bankAccount":"李四","bankNumber":"6222002201101326425"
			tt+="\"bankAccount\":\""+getApplyCardAcct()+"\"";
		} 
		if(this.getApplyCardNo()!=null){
			tt+=",\"bankNumber\":\""+getApplyCardNo()+"\"";
		}
		return tt;
	}
	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	public Long getCurrFirst() {
		return currFirst;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCurrApprer() {
		return currApprer;
	}

	public Long getRealWithDrawAmtFrom() {
		return realWithDrawAmtFrom;
	}

	public void setRealWithDrawAmtFrom(Long realWithDrawAmtFrom) {
		this.realWithDrawAmtFrom = realWithDrawAmtFrom;
	}

	public Long getRealWithDrawAmtTo() {
		return realWithDrawAmtTo;
	}

	public void setRealWithDrawAmtTo(Long realWithDrawAmtTo) {
		this.realWithDrawAmtTo = realWithDrawAmtTo;
	}

	public void setCurrApprer(String currApprer) {
		this.currApprer = currApprer;
	}

	public void setCurrFirst(Long currFirst) {
		this.currFirst = currFirst;
	}
	public String getMcSn() {
		return mcSn;
	}
	public void setMcSn(String mcSn) {
		this.mcSn = mcSn;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
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
	public Date getFromNoticeMowDate() {
		return fromNoticeMowDate;
	}
	public void setFromNoticeMowDate(Date fromNoticeMowDate) {
		this.fromNoticeMowDate = fromNoticeMowDate;
	}
	public Date getToNoticeMowDate() {
		return toNoticeMowDate;
	}
	public void setToNoticeMowDate(Date toNoticeMowDate) {
		this.toNoticeMowDate = toNoticeMowDate;
	}
	public Date getFromMowDate() {
		return fromMowDate;
	}
	public void setFromMowDate(Date fromMowDate) {
		this.fromMowDate = fromMowDate;
	}
	public Date getToMowDate() {
		return toMowDate;
	}
	public void setToMowDate(Date toMowDate) {
		this.toMowDate = toMowDate;
	}
	public String getApplyAccount() {
		return applyAccount;
	}
	public void setApplyAccount(String applyAccount) {
		this.applyAccount = applyAccount;
	}
	
	
	public String getTopAcc() {
		return topAcc;
	}

	public void setTopAcc(String topAcc) {
		this.topAcc = topAcc;
	}

	public Long[] getStatuses() {
		return statuses;
	}
	public void setStatuses(Long[] statuses) {
		this.statuses = statuses;
	}
	public String getApplyCardAcct() {
		return applyCardAcct;
	}
	public void setApplyCardAcct(String applyCardAcct) {
		this.applyCardAcct = applyCardAcct;
	}
	public String getApplyCardNo() {
		return applyCardNo;
	}
	public void setApplyCardNo(String applyCardNo) {
		this.applyCardNo = applyCardNo;
	}
	public String getApprAccount() {
		return apprAccount;
	}
	public void setApprAccount(String apprAccount) {
		this.apprAccount = apprAccount;
	}
	public String getAppr2Acct() {
		return appr2Acct;
	}
	public void setAppr2Acct(String appr2Acct) {
		this.appr2Acct = appr2Acct;
	}
	
	public Date getYishenStartBegin() {
		return yishenStartBegin;
	}
	public void setYishenStartBegin(Date yishenStartBegin) {
		this.yishenStartBegin = yishenStartBegin;
	}
	public Date getYishenStartEnd() {
		return yishenStartEnd;
	}
	public void setYishenStartEnd(Date yishenStartEnd) {
		this.yishenStartEnd = yishenStartEnd;
	}
	public Date getYishenEndBegin() {
		return yishenEndBegin;
	}
	public void setYishenEndBegin(Date yishenEndBegin) {
		this.yishenEndBegin = yishenEndBegin;
	}
	public Date getYishenEndEnd() {
		return yishenEndEnd;
	}
	public void setYishenEndEnd(Date yishenEndEnd) {
		this.yishenEndEnd = yishenEndEnd;
	}
	public Integer[] getRiskType() {
		return riskType;
	}
	public void setRiskType(Integer[] riskType) {
		this.riskType = riskType;
	}
	public Long getIsVip() {
		return isVip;
	}
	public void setIsVip(Long isVip) {
		this.isVip = isVip;
	}
	public Long getWithdrawAmtFrom() {
		return withdrawAmtFrom;
	}
	public void setWithdrawAmtFrom(Long withdrawAmtFrom) {
		this.withdrawAmtFrom = withdrawAmtFrom;
	}
	public Long getWithdrawAmtTo() {
		return withdrawAmtTo;
	}
	public void setWithdrawAmtTo(Long withdrawAmtTo) {
		this.withdrawAmtTo = withdrawAmtTo;
	}
	public Date getRecycleDate() {
		return recycleDate;
	}
	public void setRecycleDate(Date recycleDate) {
		this.recycleDate = recycleDate;
	}
	
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	
	public String getMownecumOrderNum() {
		return mownecumOrderNum;
	} 
	public void setMownecumOrderNum(String mownecumOrderNum) {
		this.mownecumOrderNum = mownecumOrderNum;
	}
	
	
	
	public String getCompanyOrderNum() {
		return companyOrderNum;
	}	
	public void setCompanyOrderNum(String companyOrderNum) {
		this.companyOrderNum = companyOrderNum;
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
	

	
}
