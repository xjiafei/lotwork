package com.winterframework.firefrog.game.web.dto;

import java.util.List;

public class GameSetCode {

	//玩法组
	private Integer gameSetCode;
	//玩法组名称
	private String gameSetCodeName;
	//玩法组对应的颜色状态(0停售中、1在售中)
	private Integer gameSetColorStatus = 0;
	//投注方式集合
	private List<BetLimitMethodCode> methodCodes;
	//玩法描述集合
	private List<HelpCode> helpCodes;
	//销售状态集合
	private List<SellingStatusCode> sellingCodes;
	
	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public String getGameSetCodeName() {
//		gameSetCodeName  = GameAwardNameUtil.setCodeName(gameSetCode);
		return gameSetCodeName;
	}

	public void setGameSetCodeName(String gameSetCodeName) {
		this.gameSetCodeName = gameSetCodeName;
	}

	public Integer getGameSetColorStatus() {
		return gameSetColorStatus;
	}

	public void setGameSetColorStatus(Integer gameSetColorStatus) {
		this.gameSetColorStatus = gameSetColorStatus;
	}

	public List<BetLimitMethodCode> getMethodCodes() {
		return methodCodes;
	}

	public void setMethodCodes(List<BetLimitMethodCode> methodCodes) {
		this.methodCodes = methodCodes;
	}

	public List<HelpCode> getHelpCodes() {
		return helpCodes;
	}

	public void setHelpCodes(List<HelpCode> helpCodes) {
		this.helpCodes = helpCodes;
	}

	public List<SellingStatusCode> getSellingCodes() {
		return sellingCodes;
	}

	public void setSellingCodes(List<SellingStatusCode> sellingCodes) {
		this.sellingCodes = sellingCodes;
	}

}
