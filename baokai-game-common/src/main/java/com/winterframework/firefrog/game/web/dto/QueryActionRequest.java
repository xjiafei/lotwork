package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
/**
 * 
* @ClassName: QueryActionRequest 
* @Description: 投注方式查询 
* @author Richard
* @date 2013-8-21 下午2:25:54 
*
 */
public class QueryActionRequest implements Serializable {

	private static final long serialVersionUID = -357385298583165445L;

	@NotNull
	private Long lotteryId;
	
	public QueryActionRequest(){
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	
}
