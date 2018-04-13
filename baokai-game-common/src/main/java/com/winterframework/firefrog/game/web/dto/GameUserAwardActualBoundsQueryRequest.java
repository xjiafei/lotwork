package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameUserAwardActualBoundsQueryRequest 
* @Description: 查询用户奖金组实际奖金
* @author Alan
* @date 2013-10-10 上午10:45:02 
*  
*/
public class GameUserAwardActualBoundsQueryRequest {

	//彩种id
	private Integer lotteryid;
	//玩法群
	private Long gameGroupCode;
	//玩法组
	private Long gameSetCode;
	//玩法
	private Long betMethodCode;

	public Integer getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Integer lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Long gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Long getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Long gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Long getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Long betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

}
