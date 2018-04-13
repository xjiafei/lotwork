package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BetLimitQueryRequest 
* @Description: 投注限制查询请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:35:27 
*  
*/
public class BetLimitQueryByBetRequest implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6141343312207549554L;
	/**
	 * 
	 */
	/** 彩种 */
	@NotNull
	private Long lotteryid;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

}
