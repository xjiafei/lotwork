package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: GameMutipleEntity 
* @Description: GameMutipleEntity 
* @author Alan
* @date 2013-8-26 下午1:31:20 
*  
*/
public class GameMultipleEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Lottery lottery;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private Integer maxMultiple;
	private Date creteatTime;
	private Date updateTime;
	private Integer maxCountIssue;
	private String betTypeCode;
	private String specialMultiple;
	
	public GameMultipleEntity(){
		
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

	public Integer getMaxMultiple() {
		return maxMultiple;
	}

	public void setMaxMultiple(Integer maxMultiple) {
		this.maxMultiple = maxMultiple;
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

	public Integer getMaxCountIssue() {
		return maxCountIssue;
	}

	public void setMaxCountIssue(Integer maxCountIssue) {
		this.maxCountIssue = maxCountIssue;
	}

	public String getBetTypeCode() {
//		return betTypeCode;
		return gameGroupCode+"_"+gameSetCode+"_"+betMethodCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialMultiple() {
		return specialMultiple;
	}

	public void setSpecialMultiple(String specialMultiple) {
		this.specialMultiple = specialMultiple;
	}
	
}
