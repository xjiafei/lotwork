package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName GameRiskReportRequest 
* @Description 报表查询类 
* @author  hugh
* @date 2014年4月28日 下午3:38:58 
*  
*/
public class GameRiskReportRequest implements Serializable {

	private static final long serialVersionUID = 5265999102631020672L;

	/** 彩种 */
	private Long lotteryId;

	/** 奖期 */
	private Long issueCode;

	/** 开始时间 */
	private Long showStartTime;

	/** 结束时间 */
	private Long showEndTime;
	
	/** 结束时间 */
	private Integer dateType;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
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

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

}
