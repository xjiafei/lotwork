package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: HistoryBallsDTO 
* @Description: 选号历史遗漏值DTO
* @author Denny 
* @date 2013-9-27 下午5:10:25 
*  
*/
public class HistoryBallsDTO implements Serializable {

	private static final long serialVersionUID = 6047273067779698938L;

	// html
	private String historyBalls;
	private GameTips gameTips;
	private List<List<NumberFrequencyCell>> frequency;

	public String getHistoryBalls() {
		return historyBalls;
	}

	public void setHistoryBalls(String historyBalls) {
		this.historyBalls = historyBalls;
	}

	public GameTips getGameTips() {
		return gameTips;
	}

	public void setGameTips(GameTips gameTips) {
		this.gameTips = gameTips;
	}

	public List<List<NumberFrequencyCell>> getFrequency() {
		return frequency;
	}

	public void setFrequency(List<List<NumberFrequencyCell>> frequency) {
		this.frequency = frequency;
	}

}
