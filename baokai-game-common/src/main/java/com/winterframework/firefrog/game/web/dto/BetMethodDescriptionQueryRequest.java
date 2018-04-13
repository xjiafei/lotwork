package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BetMethodDescriptionQueryRequest 
* @Description: 玩法描述查询请求参数封装对象 
* @author Denny 
* @date 2013-8-21 下午4:48:27 
*  
*/
public class BetMethodDescriptionQueryRequest implements Serializable {

	private static final long serialVersionUID = 2895306915378613862L;

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
