package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName GameRiskIssue 
* @Description 审核奖期  for 审核系统
* @author  hugh
* @date 2014年4月9日 下午1:43:41 
*  
*/
public class GameRiskIssue extends BaseEntity {

	private static final long serialVersionUID = -2619259061602950899L;

	//date formats

	//columns START
	private Long lotteryid;
	private Long issueCode;
	private String webIssueCode;
	private Date saleStartTime;
	private Date saleEndTime;
	private Date openDrawTime;
	private Date factionDrawTime;
	private Long status;
	private Date createTime;
	private Date updateTime;
	private Long periodStatus;
	private Integer pauseStatus;
	private Integer eventStatus;
	private Long sequence;
	private Integer planFinishStatus;//计划订单是否完成 0 未完成 1 已完成
	private Integer lastIssueStop; //上期暂停计划 0 无 1 存在上期未完成的计划
	
	private Long riskWarnOrderNumber;//风险订单数
	private Long riskDealedWarnOrderNumber;//已处理风险订单数据
	private Integer riskStatus;//0无需审核 1已完成 2待处理
	private String operator;
	
	//columns END

	public GameRiskIssue() {
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

	public Date getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(Date saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public Date getOpenDrawTime() {
		return openDrawTime;
	}

	public void setOpenDrawTime(Date openDrawTime) {
		this.openDrawTime = openDrawTime;
	}

	public Date getFactionDrawTime() {
		return factionDrawTime;
	}

	public void setFactionDrawTime(Date factionDrawTime) {
		this.factionDrawTime = factionDrawTime;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getPeriodStatus() {
		return periodStatus;
	}

	public void setPeriodStatus(Long periodStatus) {
		this.periodStatus = periodStatus;
	}

	public Date getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(Date saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public Integer getPauseStatus() {
		return pauseStatus;
	}

	public void setPauseStatus(Integer pauseStatus) {
		this.pauseStatus = pauseStatus;
	}

	public Integer getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(Integer eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Integer getPlanFinishStatus() {
		return planFinishStatus;
	}

	public void setPlanFinishStatus(Integer planFinishStatus) {
		this.planFinishStatus = planFinishStatus;
	}

	public Integer getLastIssueStop() {
		return lastIssueStop;
	}

	public void setLastIssueStop(Integer lastIssueStop) {
		this.lastIssueStop = lastIssueStop;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
