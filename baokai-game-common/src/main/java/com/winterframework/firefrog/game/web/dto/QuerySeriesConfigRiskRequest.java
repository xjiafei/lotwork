package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: QuerySeriesConfigRequest 
* @Description: 查询运营参数（审核）请求
* @author Alan
* @date 2013-9-17 下午5:16:31 
*
 */
public class QuerySeriesConfigRiskRequest implements Serializable{

	private static final long serialVersionUID = 2365289585154650551L;
	
	@NotNull
	private Long lotteryid;
	
	public QuerySeriesConfigRiskRequest(){
		
	}

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}

}
