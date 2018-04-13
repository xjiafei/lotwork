package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: BetMethodValidListQueryRequest 
* @Description: 彩种有效玩法列表查询请求 
* @author david 
* @date 2014-3-21 下午1:26:07 
*  
*/
public class BetMethodValidListQueryRequest implements Serializable {

	private static final long serialVersionUID = -4709516554522930287L;

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
