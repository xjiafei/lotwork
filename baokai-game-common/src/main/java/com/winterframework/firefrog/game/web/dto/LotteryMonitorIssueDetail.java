package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.RiskOrders;
import com.winterframework.firefrog.game.dao.vo.SpiteOrders;
import com.winterframework.modules.page.Page;

public class LotteryMonitorIssueDetail implements Serializable {

	private static final long serialVersionUID = -4737424515318593127L;
	
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
//	private List<RiskOrders> riskOrderList;	
	private List<SpiteOrders> spiteOrderList;
	private Page<RiskOrders> page;
	
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
//	public List<RiskOrders> getRiskOrderList() {
//		return riskOrderList;
//	}
//	public void setRiskOrderList(List<RiskOrders> riskOrderList) {
//		this.riskOrderList = riskOrderList;
//	}
	public List<SpiteOrders> getSpiteOrderList() {
		return spiteOrderList;
	}
	public void setSpiteOrderList(List<SpiteOrders> spiteOrderList) {
		this.spiteOrderList = spiteOrderList;
	}
	public String getWebIssueCode() {
		return webIssueCode;
	}
	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}
	public Page<RiskOrders> getPage() {
		return page;
	}
	public void setPage(Page<RiskOrders> page) {
		this.page = page;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
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
