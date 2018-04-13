package com.winterframework.firefrog.game.service.openaward;

import com.winterframework.firefrog.common.util.ProcessResult;

public interface IOpenAwardService {
	
	/** 
	* @Title: openAward 
	* @Description: 单个订单开奖
	* @param orderId
	* @return
	* @throws Exception
	*/
	ProcessResult openAward(Long orderId) throws Exception;
	
	
}
