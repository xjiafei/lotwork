package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: LotteryGameUserAwardGroupQueryRequest 
* @Description: 用户投注彩种奖金组列表获取 
* @author david 
* @date 2014-5-31 下午6:31:45 
*  
*/
public class LotteryGameUserAwardGroupQueryRequest implements Serializable {

	private static final long serialVersionUID = 7835688061078360021L;

	private Long userId;
	private Long lotteryId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

}
