package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameWarnOrderQueryRequest 
* @Description: 风险记录查询request
* @author Alan
* @date 2013-10-15 下午1:54:41 
*  
*/
public class GameWarnOrderQueryRequest {

	/**彩种ID*/
	private Long lotteryid;
	/**投注时间选择方式*/
	private Long selectTimeMode;
	/**开始提交时间*/
	private Long startCreateTime;
	/**结束提交时间*/
	private Long endCreateTime;
	/**用户名或方案编号*/
	private String paramCode;
	/**状态*/
	private Integer status;
	/**奖金开始范围*/
	private Long startWinsCount;
	/**奖金开始范围*/
	private Long endWinsCount;
	/**是否包含下级*/
	private Integer containSub;
	/**单期奖金*/
	private Long totalWins;
	/**单期中投比*/
	private Long winsRatios;
	/**单注最大奖金*/
	private Long maxslipWins;
	/**单注最大中投比*/
	private Long slipWinsRatio;
	/**连续中奖期数*/
	private Long continuousWinsIssue;
	
	private Long issueAward;
	private Long issueWinsRatio;
	private Long betAward;
	private Long betWinsRatio;
	private Long winsTime;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Long getSelectTimeMode() {
		return selectTimeMode;
	}
	public void setSelectTimeMode(Long selectTimeMode) {
		this.selectTimeMode = selectTimeMode;
	}
	public Long getStartCreateTime() {
		return startCreateTime;
	}
	public void setStartCreateTime(Long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}
	public Long getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(Long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getStartWinsCount() {
		return startWinsCount;
	}
	public void setStartWinsCount(Long startWinsCount) {
		this.startWinsCount = startWinsCount;
	}
	public Long getEndWinsCount() {
		return endWinsCount;
	}
	public void setEndWinsCount(Long endWinsCount) {
		this.endWinsCount = endWinsCount;
	}
	public Integer getContainSub() {
		return containSub;
	}
	public void setContainSub(Integer containSub) {
		this.containSub = containSub;
	}
	public Long getTotalWins() {
		return totalWins;
	}
	public void setTotalWins(Long totalWins) {
		this.totalWins = totalWins;
	}
	public Long getWinsRatios() {
		return winsRatios;
	}
	public void setWinsRatios(Long winsRatios) {
		this.winsRatios = winsRatios;
	}
	public Long getMaxslipWins() {
		return maxslipWins;
	}
	public void setMaxslipWins(Long maxslipWins) {
		this.maxslipWins = maxslipWins;
	}
	public Long getSlipWinsRatio() {
		return slipWinsRatio;
	}
	public void setSlipWinsRatio(Long slipWinsRatio) {
		this.slipWinsRatio = slipWinsRatio;
	}
	public Long getContinuousWinsIssue() {
		return continuousWinsIssue;
	}
	public void setContinuousWinsIssue(Long continuousWinsIssue) {
		this.continuousWinsIssue = continuousWinsIssue;
	}
	public Long getIssueAward() {
		return issueAward;
	}
	public void setIssueAward(Long issueAward) {
		this.issueAward = issueAward;
	}
	public Long getIssueWinsRatio() {
		return issueWinsRatio;
	}
	public void setIssueWinsRatio(Long issueWinsRatio) {
		this.issueWinsRatio = issueWinsRatio;
	}
	public Long getBetAward() {
		return betAward;
	}
	public void setBetAward(Long betAward) {
		this.betAward = betAward;
	}
	public Long getBetWinsRatio() {
		return betWinsRatio;
	}
	public void setBetWinsRatio(Long betWinsRatio) {
		this.betWinsRatio = betWinsRatio;
	}
	public Long getWinsTime() {
		return winsTime;
	}
	public void setWinsTime(Long winsTime) {
		this.winsTime = winsTime;
	}

}
