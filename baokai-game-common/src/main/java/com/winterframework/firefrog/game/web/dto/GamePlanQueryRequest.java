package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.Date;

public class GamePlanQueryRequest implements Serializable {
	
	private static final long serialVersionUID = 6585515520729714688L;
	
	/** 彩种 */
	private Long lotteryId;
	/** 状态 0，全部；1，已中奖；2，未中奖；3，进行中*/
	private Integer status;
	/** 期号 */
	private String webIssueCode;
	/** 追号起止时间 */
	private Long startTime;
	/** 追号截止时间 */
	private Long endTime;
	/** 查询追号编号 */
	private String planCode;
	/** 查詢回收記錄**/
	private Date recycleDate; 

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWebIssueCode() {
		return webIssueCode;
	}

	public void setWebIssueCode(String webIssueCode) {
		this.webIssueCode = webIssueCode;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Date getRecycleDate() {
		return recycleDate;
	}

	public void setRecycleDate(Date recycleDate) {
		this.recycleDate = recycleDate;
	}

}
