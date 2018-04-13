package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: QuerySeriesConfigRequest 
* @Description: 查询运营参数请求
* @author Richard
* @date 2013-9-17 下午5:16:31 
*
 */
public class QuerySeriesConfigRequest implements Serializable{

	private static final long serialVersionUID = 2365289585154650551L;
	
	@NotNull
	private Long lotteryid;
	
	private Long userId;
	
	public QuerySeriesConfigRequest(){
		
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
