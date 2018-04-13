package com.winterframework.firefrog.user.service;

import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeByUserResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeChangeResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeCheckResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeModifyResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotOccupancyResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotOccupancySaveResponse;

@Transactional(rollbackFor = Exception.class)
public interface IUserSlotExhangeService {
	
	public UserSlotExchangeChangeResponse change(String activityType);
	
	public UserSlotOccupancyResponse occupancy(String activityType, String cellularPhone, String exchangeNumber, Long exchangePrize);
	
	public UserSlotOccupancySaveResponse occupancySave(Long userId, String cellularPhone, String activityType);
	
	public UserSlotExchangeModifyResponse modifyExchange(Long type, String exchangeNumber);
	
	public UserSlotExchangeCheckResponse exchangeCheck(Long userId);
	
	public UserSlotExchangeResponse exchange(Long userId, String exchangeNumber, String clientIp) throws FundChangedException, Exception;
	
	public UserSlotExchangeByUserResponse getSlotExchangeByUser(Long userId);
	
}