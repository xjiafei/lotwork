package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: UserAwardListByBetStruc 
* @Description: 用户投注奖金组列表基本结构 
* @author Denny 
* @date 2013-9-17 下午5:36:04 
*  
*/
public class UserAwardListByLoginStruc implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8530654692804192524L;
	/**
	 * 
	 */
	/** 彩种ID */
	private Long lotteryId;
	/** 奖金组ID */
	private Long awardGroupId;

	public Long getAwardGroupId() {
		return awardGroupId;
	}

	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

}
