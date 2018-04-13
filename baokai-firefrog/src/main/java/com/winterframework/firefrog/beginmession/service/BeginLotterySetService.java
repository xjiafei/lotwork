package com.winterframework.firefrog.beginmession.service;

import com.winterframework.firefrog.beginmession.web.dto.BeginLotterySetRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginLotterySetResponse;

public interface BeginLotterySetService {

	public void insert(BeginLotterySetRequest dto) throws Exception;
	
	public BeginLotterySetResponse selectMaxVersion() throws Exception;
	
}
