package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: PublishGameAwardGroupRequest 
* @Description: 发布奖金组 
* @author Richard
* @date 2013-8-16 上午11:26:07 
*
 */
public class PublishGameAwardGroupRequest implements Serializable {

	private static final long serialVersionUID = -3035398036131187792L;

	@NotNull
	private Long lotteryId;
	@NotNull
	private String awardId;
	@NotNull
	private Integer publishResult;
	
	public PublishGameAwardGroupRequest(){
		
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

	public Integer getPublishResult() {
		return publishResult;
	}

	public void setPublishResult(Integer publishResult) {
		this.publishResult = publishResult;
	}
	
}
