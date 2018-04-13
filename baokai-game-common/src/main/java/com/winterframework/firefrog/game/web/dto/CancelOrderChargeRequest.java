package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: CancelOrderChargeRequest 
* @Description:撤单手续费获取请求 
* @author david 
* @date 2013-12-20 上午10:30:53 
*  
*/
public class CancelOrderChargeRequest implements Serializable{

	private static final long serialVersionUID = 2365289585154650551L;
	
	@NotNull
	private Long lotteryId;
	
	//投注金额
	@NotNull
	private Double totalBetAmount;
	
	public CancelOrderChargeRequest(){
		
	}

	public Long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Double getTotalBetAmount() {
		return totalBetAmount;
	}

	public void setTotalBetAmount(Double totalBetAmount) {
		this.totalBetAmount = totalBetAmount;
	}
	
	


}
