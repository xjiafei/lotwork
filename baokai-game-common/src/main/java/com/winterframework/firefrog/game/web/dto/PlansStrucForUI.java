package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: PlansStrucForUI 
* @Description: 
* @author Denny 
* @date 2013-10-15 下午5:05:23 
*  
*/
public class PlansStrucForUI implements Serializable {

	private static final long serialVersionUID = -5506966805071902465L;

	/** 追号编号 */
	private String planCode;
	/** 彩种 */
	private Long lotteryid;
	private String lotteryName;
	/** 起始期号 */
	private Long startIssueCode;
	/** 起始Web期号 */
	private String startWebIssueCode;
	/** 已追期号 */
	private Integer finishIssue;
	/** 总期数 */
	private Integer totalIssue;
	/** 已下注金额 */
	private Long usedAmount;
	/** 追号总金额 */
	private Long totamount;
	/**总奖金(计算中奖方案得到的奖金数)*/
	private Long totalWin;
	/** 停止方式：0:不停止 1:按累计盈利停止 2:中奖即停 */
	private Integer stopMode;
	/** 停止数量：累计中奖停止金额 */
	private Long stopParms;
	/** 状态 */
	private Integer status;
	/** 追号时间 */
	private String saleTime;
	/** 追号ID */
	private Long planid;
	
	private Boolean canStop;//两个条件：1 状态<2，2：当停止方式不为不停止时，没有待审核的订单
	
	private String statusName;
	
	public Boolean getCanStop() {
		return canStop;
	}

	public void setCanStop(Boolean canStop) {
		this.canStop = canStop;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

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

	public Long getStartIssueCode() {
		return startIssueCode;
	}

	public void setStartIssueCode(Long startIssueCode) {
		this.startIssueCode = startIssueCode;
	}

	public String getStartWebIssueCode() {
		return startWebIssueCode;
	}

	public void setStartWebIssueCode(String startWebIssueCode) {
		this.startWebIssueCode = startWebIssueCode;
	}

	public Integer getFinishIssue() {
		return finishIssue;
	}

	public void setFinishIssue(Integer finishIssue) {
		this.finishIssue = finishIssue;
	}

	public Integer getTotalIssue() {
		return totalIssue;
	}

	public void setTotalIssue(Integer totalIssue) {
		this.totalIssue = totalIssue;
	}

	public Long getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(Long usedAmount) {
		this.usedAmount = usedAmount;
	}

	public Long getTotamount() {
		return totamount;
	}

	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}

	public Long getTotalWin() {
		return totalWin;
	}

	public void setTotalWin(Long totalWin) {
		this.totalWin = totalWin;
	}

	public Integer getStopMode() {
		return stopMode;
	}

	public void setStopMode(Integer stopMode) {
		this.stopMode = stopMode;
	}

	public Long getStopParms() {
		return stopParms;
	}

	public void setStopParms(Long stopParms) {
		this.stopParms = stopParms;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public Long getPlanid() {
		return planid;
	}

	public void setPlanid(Long planid) {
		this.planid = planid;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
