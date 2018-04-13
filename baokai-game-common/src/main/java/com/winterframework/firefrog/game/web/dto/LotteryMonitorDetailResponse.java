package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: LotteryMonitorDetailResponse 
* @Description: 5.5.32	彩种奖期监控详情(OMI032) 响应
* @author Richard
* @date 2013-10-12 上午10:34:05 
*
 */
public class LotteryMonitorDetailResponse implements Serializable {

	private static final long serialVersionUID = -3503866277605620206L;

	private Long lotteryId;
	private String lotteryName;
	private Long issueCode;
	private String numberRecord;
	private String warnTypeStr;
	private String warnParas;
	private String suggestTypeStr;
	private String	otherTypeStr;
	private String webIssueCode;
	private Long status;
	private Long isCanCanel = 0L;//0可以 1不可以
	private Long isAfterSaleEndTime = 0L;//0不是 1是
	private List<RiskOrderStruc> riskOrderList;	//待审核
	private List<RiskOrderStruc> riskOrderAuditedList;//已审核，或者审核未通过	
	private List<SpiteOrderStruc> spiteOrderList;
	
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	public String getWarnTypeStr() {
		return warnTypeStr;
	}
	public void setWarnTypeStr(String warnTypeStr) {
		this.warnTypeStr = warnTypeStr;
	}
	public String getWarnParas() {
		return warnParas;
	}
	public void setWarnParas(String warnParas) {
		this.warnParas = warnParas;
	}
	public String getSuggestTypeStr() {
		return suggestTypeStr;
	}
	public void setSuggestTypeStr(String suggestTypeStr) {
		this.suggestTypeStr = suggestTypeStr;
	}
	public String getOtherTypeStr() {
		return otherTypeStr;
	}
	public void setOtherTypeStr(String otherTypeStr) {
		this.otherTypeStr = otherTypeStr;
	}
	public List<RiskOrderStruc> getRiskOrderList() {
		return riskOrderList;
	}
	public void setRiskOrderList(List<RiskOrderStruc> riskOrderList) {
		this.riskOrderList = riskOrderList;
	}
	public List<SpiteOrderStruc> getSpiteOrderList() {
		return spiteOrderList;
	}
	public void setSpiteOrderList(List<SpiteOrderStruc> spiteOrderList) {
		this.spiteOrderList = spiteOrderList;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public List<RiskOrderStruc> getRiskOrderAuditedList() {
		return riskOrderAuditedList;
	}
	public void setRiskOrderAuditedList(List<RiskOrderStruc> riskOrderAuditedList) {
		this.riskOrderAuditedList = riskOrderAuditedList;
	}
	public Long getIsCanCanel() {
		return isCanCanel;
	}
	public void setIsCanCanel(Long isCanCanel) {
		this.isCanCanel = isCanCanel;
	}
	public Long getIsAfterSaleEndTime() {
		return isAfterSaleEndTime;
	}
	public void setIsAfterSaleEndTime(Long isAfterSaleEndTime) {
		this.isAfterSaleEndTime = isAfterSaleEndTime;
	}
}
