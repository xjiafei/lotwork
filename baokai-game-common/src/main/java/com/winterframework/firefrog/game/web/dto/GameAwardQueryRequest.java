package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: GameAwardQueryRequest 
* @Description: 奖金组奖金查询
* @author Richard
* @date 2013-8-16 上午10:13:52 
*
 */
public class GameAwardQueryRequest implements Serializable {

	private static final long serialVersionUID = -4914178551928266359L;

	@NotNull
	private Long lotteryId;
	@NotNull
	private Long awardGroupId;
	private Integer status;
	
	public GameAwardQueryRequest(){
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
