package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: AuditLotteryMonitorOrdersRequest 
* @Description: 审核系统批量审核订单请求 
* @author david 
* @date 2014-2-26 上午11:23:53 
*  
*/
public class AuditLotteryMonitorOrdersRequest implements Serializable {

	private static final long serialVersionUID = -1700260853838435356L;
	
	@NotNull
	private String orderCodes;

	public String getOrderCodes() {
		return orderCodes;
	}

	public void setOrderCodes(String orderCodes) {
		this.orderCodes = orderCodes;
	}
	
}
