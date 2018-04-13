package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

public class ActionResponse implements Serializable{

	private static final long serialVersionUID = 7565663294657611576L;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private String gamePromptDes;
	private String gameExamplesDes;
	
	public ActionResponse(){
		
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

	public String getGamePromptDes() {
		return gamePromptDes;
	}

	public void setGamePromptDes(String gamePromptDes) {
		this.gamePromptDes = gamePromptDes;
	}

	public String getGameExamplesDes() {
		return gameExamplesDes;
	}

	public void setGameExamplesDes(String gameExamplesDes) {
		this.gameExamplesDes = gameExamplesDes;
	}
	
	
}
