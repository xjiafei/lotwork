package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameMutipleQueryRequest 
* @Description: 投注限制查询请求
* @author Alan
* @date 2013-8-26 下午1:59:04 
*  
*/
public class GameMultipleQueryRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long lotteryId;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	
	public GameMultipleQueryRequest(){
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

}
