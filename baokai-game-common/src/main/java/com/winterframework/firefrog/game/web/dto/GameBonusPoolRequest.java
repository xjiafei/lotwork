/**   
* @Title: GameBonusPoolRequest.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-7-29 下午4:09:22 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameBonusPoolRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-7-29 下午4:09:22 
*  
*/
public class GameBonusPoolRequest {

	private Long lotteryid;
	private Long actualBonus;
	private Long actualBonusProcess;
	private String changeReason;
	private Long minimumBonus;
	private Long minimumBonusProcess;
	private Long distribute1;
	private Long distribute2;
	private Double distribute1process;
	private Double distribute2process;
	private Long status;
	private Long id;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getActualBonus() {
		return actualBonus;
	}

	public void setActualBonus(Long actualBonus) {
		this.actualBonus = actualBonus;
	}

	public Long getActualBonusProcess() {
		return actualBonusProcess;
	}

	public void setActualBonusProcess(Long actualBonusProcess) {
		this.actualBonusProcess = actualBonusProcess;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public Long getMinimumBonus() {
		return minimumBonus;
	}

	public void setMinimumBonus(Long minimumBonus) {
		this.minimumBonus = minimumBonus;
	}

	public Long getMinimumBonusProcess() {
		return minimumBonusProcess;
	}

	public void setMinimumBonusProcess(Long minimumBonusProcess) {
		this.minimumBonusProcess = minimumBonusProcess;
	}

	public Long getDistribute1() {
		return distribute1;
	}

	public void setDistribute1(Long distribute1) {
		this.distribute1 = distribute1;
	}

	public Long getDistribute2() {
		return distribute2;
	}

	public void setDistribute2(Long distribute2) {
		this.distribute2 = distribute2;
	}

	public Double getDistribute1process() {
		return distribute1process;
	}

	public void setDistribute1process(Double distribute1process) {
		this.distribute1process = distribute1process;
	}

	public Double getDistribute2process() {
		return distribute2process;
	}

	public void setDistribute2process(Double distribute2process) {
		this.distribute2process = distribute2process;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
