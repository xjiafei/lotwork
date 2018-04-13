package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: HistoryBallsRequest 
* @Description: 选号历史遗漏值查询Request
* @author Denny 
* @date 2013-9-27 下午5:20:41 
*  
*/
public class HistoryBallsRequest implements Serializable{

	private static final long serialVersionUID = -8036775426904032978L;

	private String betMethodType;
	private Long lotteryid;

	public String getBetMethodType() {
		return betMethodType;
	}

	public void setBetMethodType(String betMethodType) {
		this.betMethodType = betMethodType;
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

}
