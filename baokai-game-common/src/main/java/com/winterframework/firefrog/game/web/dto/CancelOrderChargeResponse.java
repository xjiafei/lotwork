package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: CancelOrderChargeResponse 
* @Description: 用户撤单手续费响应 
* @author david 
* @date 2013-12-20 上午10:32:25 
*  
*/
public class CancelOrderChargeResponse implements Serializable{

	private static final long serialVersionUID = 2365289585154650551L;
	
	@NotNull
	private Double handlingCharge;
	
	public CancelOrderChargeResponse(){
		
	}

	public Double getHandlingCharge() {
		return handlingCharge;
	}

	public void setHandlingCharge(Double handlingCharge) {
		this.handlingCharge = handlingCharge;
	}



}
