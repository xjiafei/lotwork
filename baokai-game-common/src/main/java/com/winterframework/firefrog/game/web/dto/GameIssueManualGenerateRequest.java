/**   
* @Title: GameIssueManualGenerateRequest.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-8-25 下午3:06:14 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameIssueManualGenerateRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-8-25 下午3:06:14 
*  
*/
public class GameIssueManualGenerateRequest implements Serializable{
	private static final long serialVersionUID = 12312453322l;
	/** 彩种 */
	private Long lotteryId;
	/** 开始时间 */
	private Long showStartTime;
	/** 结束时间 */
	private Long showEndTime;
	
	private String startIsssueCode;
	
	//1:作为开始期号  0：作为实际期号
	private Long startIsssueNumber;

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
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

	public String getStartIsssueCode() {
		return startIsssueCode;
	}

	public void setStartIsssueCode(String startIsssueCode) {
		this.startIsssueCode = startIsssueCode;
	}

	public Long getStartIsssueNumber() {
		return startIsssueNumber;
	}

	public void setStartIsssueNumber(Long startIsssueNumber) {
		this.startIsssueNumber = startIsssueNumber;
	}
}
