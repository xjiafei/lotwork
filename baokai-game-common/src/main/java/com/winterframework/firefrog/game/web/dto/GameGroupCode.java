package com.winterframework.firefrog.game.web.dto;

import java.util.List;

import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

public class GameGroupCode {

	//彩种ID
	private Long lotteryId;
	//玩法群
	private Integer gameGroupCode;
	//玩法群名称
	private String gameGroupCodeName;
	//玩法群对应的颜色状态(0停售中、1在售中)
	private Integer gameGroupColorStatus = 0;
	//玩法组集合
	private List<GameSetCode> setCodes;
	
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
	public String getGameGroupCodeName() {
//		gameGroupCodeName = GameAwardNameUtil.groupName(gameGroupCode);
		return gameGroupCodeName;
	}
	public void setGameGroupCodeName(String gameGroupCodeName) {
		this.gameGroupCodeName = gameGroupCodeName;
	}
	public Integer getGameGroupColorStatus() {
		return gameGroupColorStatus;
	}
	public void setGameGroupColorStatus(Integer gameGroupColorStatus) {
		this.gameGroupColorStatus = gameGroupColorStatus;
	}
	public List<GameSetCode> getSetCodes() {
		return setCodes;
	}
	public void setSetCodes(List<GameSetCode> setCodes) {
		this.setCodes = setCodes;
	}
	
}
