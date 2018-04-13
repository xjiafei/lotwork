package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

public class GameHelpEntity implements Serializable {

	private static final long serialVersionUID = -8156333600108975014L;

	private Lottery lottery;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private String gamePromptDes;
	private String gameExamplesDes;
	private Date creteatTime;
	private Date updateTime;
	
	public GameHelpEntity(){
		
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
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

	public Date getCreteatTime() {
		return creteatTime;
	}

	public void setCreteatTime(Date creteatTime) {
		this.creteatTime = creteatTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
