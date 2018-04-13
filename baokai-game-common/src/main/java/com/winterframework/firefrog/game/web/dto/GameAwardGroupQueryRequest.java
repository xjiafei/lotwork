package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: GameAwardGroupQueryReqest 
* @Description: 奖金组查询请求类 
* @author Richard
* @date 2013-8-16 上午9:42:19 
*
 */
public class GameAwardGroupQueryRequest implements Serializable {

	private static final long serialVersionUID = -3926306106255504272L;

	@NotNull
	private Long lotteryId;
	private Integer status;
	private Long gameAwardGroupId;
	
	public GameAwardGroupQueryRequest() {
		
	}

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

	public Long getGameAwardGroupId() {
		return gameAwardGroupId;
	}

	public void setGameAwardGroupId(Long gameAwardGroupId) {
		this.gameAwardGroupId = gameAwardGroupId;
	}
	
	
}
