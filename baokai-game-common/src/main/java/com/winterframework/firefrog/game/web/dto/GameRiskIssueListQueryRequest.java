package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName GameRiskIssueListQueryRequest 
* @Description 审核系统 审核奖期查询 
* @author  hugh
* @date 2014年4月9日 下午3:16:29 
*  
*/
public class GameRiskIssueListQueryRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	/** 彩种 */
	private Long lotteryId;

	/** 状态  全部0 已完成1 未处理2*/
	private Integer status = 0;

	/** 开始时间 */
	private Long showStartTime;

	/** 结束时间 */
	private Long showEndTime;

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

	public Long getShowStartTime() {
		return showStartTime;
	}

	public void setShowStartTime(Long showStartTime) {
		this.showStartTime = showStartTime;
	}

	public Long getShowEndTime() {
		return showEndTime;
	}

	public void setShowEndTime(Long showEndTime) {
		this.showEndTime = showEndTime;
	}

}
