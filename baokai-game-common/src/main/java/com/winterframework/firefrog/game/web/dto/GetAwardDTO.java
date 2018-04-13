package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;


/** 
* @ClassName: GetAwardDTO 
* @Description:  遗漏值DTO
* @author Denny 
* @date 2013-9-27 下午5:10:25 
*  
*/
public class GetAwardDTO implements Serializable {

	private static final long serialVersionUID = 5219894413526577424L;
	// html
	private GameTipsByBet gameTips;
	
	private List<List<NumberFrequencyCell>> frequency;
	
	public GameTipsByBet getGameTips() {
		return gameTips;
	}

	public void setGameTips(GameTipsByBet gameTips) {
		this.gameTips = gameTips;
	}

	public List<List<NumberFrequencyCell>> getFrequency() {
		return frequency;
	}

	public void setFrequency(List<List<NumberFrequencyCell>> frequency) {
		this.frequency = frequency;
	}
}
