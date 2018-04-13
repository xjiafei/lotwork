package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameRiskIssue;

/** 
* @ClassName GameRiskIssue 
* @Description 审核奖期  for 审核系统
* @author  hugh
* @date 2014年4月9日 下午1:43:41 
*  
*/
public class GameRiskIssueStruc implements Serializable {

	private static final long serialVersionUID = -2619259061602950899L;

	private Long lotteryid;
	private String lotteryName;
	private Long issueCode;
	private String webIssueCode;
	private String saleStartTime;
	private String saleEndTime;
	private String saleDate;
	private String openDrawTime;
	private String factionDrawTime;

	private Long riskWarnOrderNumber;//风险订单数
	private Long riskDealedWarnOrderNumber;//已处理风险订单数据
	private Integer riskStatus;//0无需审核 1已完成 2待处理
	private String operator;

	public GameRiskIssueStruc() {
	}

	public GameRiskIssueStruc(GameRiskIssue gameRiskIssue) {
		super();
		this.lotteryid = gameRiskIssue.getLotteryid();
		this.issueCode = gameRiskIssue.getIssueCode();
		this.webIssueCode = gameRiskIssue.getWebIssueCode();
		this.saleStartTime = DateUtils.format(gameRiskIssue.getSaleStartTime(), DateUtils.DATETIME_FORMAT_PATTERN);
		this.saleEndTime = DateUtils.format(gameRiskIssue.getSaleEndTime(), DateUtils.DATETIME_FORMAT_PATTERN);
		this.saleDate = DateUtils.format(gameRiskIssue.getSaleStartTime(), DateUtils.DATE_FORMAT_PATTERN);
		this.openDrawTime = DateUtils.format(gameRiskIssue.getOpenDrawTime(), DateUtils.DATETIME_FORMAT_PATTERN);
		this.factionDrawTime = DateUtils.format(gameRiskIssue.getFactionDrawTime(), DateUtils.DATETIME_FORMAT_PATTERN);
		this.riskWarnOrderNumber = gameRiskIssue.getRiskWarnOrderNumber();
		this.riskDealedWarnOrderNumber = gameRiskIssue.getRiskDealedWarnOrderNumber();
		this.riskStatus = gameRiskIssue.getRiskStatus();
		this.operator = gameRiskIssue.getOperator();
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

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public String getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(String saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public String getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(String saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getOpenDrawTime() {
		return openDrawTime;
	}

	public void setOpenDrawTime(String openDrawTime) {
		this.openDrawTime = openDrawTime;
	}

	public String getFactionDrawTime() {
		return factionDrawTime;
	}

	public void setFactionDrawTime(String factionDrawTime) {
		this.factionDrawTime = factionDrawTime;
	}

	public Long getRiskWarnOrderNumber() {
		return riskWarnOrderNumber;
	}

	public void setRiskWarnOrderNumber(Long riskWarnOrderNumber) {
		this.riskWarnOrderNumber = riskWarnOrderNumber;
	}

	public Long getRiskDealedWarnOrderNumber() {
		return riskDealedWarnOrderNumber;
	}

	public void setRiskDealedWarnOrderNumber(Long riskDealedWarnOrderNumber) {
		this.riskDealedWarnOrderNumber = riskDealedWarnOrderNumber;
	}

	public Integer getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(Integer riskStatus) {
		this.riskStatus = riskStatus;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	

}
