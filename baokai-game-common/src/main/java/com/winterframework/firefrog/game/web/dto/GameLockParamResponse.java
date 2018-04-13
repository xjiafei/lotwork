/**   
* @Title: GameLockParamResponse.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-8 下午3:34:40 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameLockParamResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-5-8 下午3:34:40 
*  
*/
public class GameLockParamResponse implements Serializable {
	private static final long serialVersionUID = -9787862322L;

	private Long id;

	private Long gameId;

	private Long status;

	private Long minVal;

	private String startTime;

	private String endTime;

	private Long minValProcess;

	private String startTimeProcess;

	private String endTimeProcess;
	
	private String modifier;
	
	private String operator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getMinVal() {
		return minVal;
	}

	public void setMinVal(Long minVal) {
		this.minVal = minVal;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getMinValProcess() {
		return minValProcess;
	}

	public void setMinValProcess(Long minValProcess) {
		this.minValProcess = minValProcess;
	}

	public String getStartTimeProcess() {
		return startTimeProcess;
	}

	public void setStartTimeProcess(String startTimeProcess) {
		this.startTimeProcess = startTimeProcess;
	}

	public String getEndTimeProcess() {
		return endTimeProcess;
	}

	public void setEndTimeProcess(String endTimeProcess) {
		this.endTimeProcess = endTimeProcess;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
