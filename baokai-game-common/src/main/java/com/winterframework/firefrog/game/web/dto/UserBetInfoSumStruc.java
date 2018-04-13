/**   
* @Title: UserBetInfoSumStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-12-3 上午9:43:10 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: UserBetInfoSumStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-12-3 上午9:43:10 
*  
*/
public class UserBetInfoSumStruc implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 234355657657l;

	private Long betAmount;
	
	private Long retPoint;
	
	private Long winAmount;

	public Long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Long betAmount) {
		this.betAmount = betAmount;
	}

	public Long getRetPoint() {
		return retPoint;
	}

	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}

	public Long getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Long winAmount) {
		this.winAmount = winAmount;
	}
}
