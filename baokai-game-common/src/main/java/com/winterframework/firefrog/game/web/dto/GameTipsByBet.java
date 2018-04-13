package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: GameTips 
* @Description: 投注首页玩法描述
* @author Denny 
* @date 2013-9-29 下午7:51:30 
*  
*/
public class GameTipsByBet implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3836750049781374202L;
	private String example;
	private String tips;
	private double actualBonus;
	private double theoryBonus;	//玩法理论奖金
	private double retPoint;	//玩法返点

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public double getActualBonus() {
		return actualBonus;
	}

	public void setActualBonus(double actualBonus) {
		this.actualBonus = actualBonus;
	}

	public double getTheoryBonus() {
		return theoryBonus;
	}

	public void setTheoryBonus(double theoryBonus) {
		this.theoryBonus = theoryBonus;
	}

	public double getRetPoint() {
		return retPoint;
	}

	public void setRetPoint(double retPoint) {
		this.retPoint = retPoint;
	}
	
	
}
