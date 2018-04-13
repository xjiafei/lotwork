package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
/**
 * 
* @ClassName: AuditGameAwardGroupRequest 
* @Description: 奖金组审核请求
* @author Richard
* @date 2013-8-16 上午11:06:32 
*
 */
public class AuditGameAwardGroupRequest implements Serializable {

	private static final long serialVersionUID = -2666134862587238818L;

	@NotNull
	private Long lotteryId;
	@NotNull
	private String awardId;
	@NotNull
	private Integer checkType;
	@NotNull
	private Integer checkResult;
	
	public AuditGameAwardGroupRequest() {
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}
	
	
}
