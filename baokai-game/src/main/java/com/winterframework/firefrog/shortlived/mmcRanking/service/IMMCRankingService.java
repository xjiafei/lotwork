package com.winterframework.firefrog.shortlived.mmcRanking.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.MMCRanking;
import com.winterframework.firefrog.game.web.dto.MmcRankingHistoryResponse;
import com.winterframework.firefrog.game.web.dto.MmcRankingResponse;

/** 
* @ClassName IActivityService 
* @Description 活动 
* @author  hugh
* @date 2014年12月2日 下午3:33:54 
*  
*/
public interface IMMCRankingService {

	
	public MmcRankingResponse queryTop(String account);
	
	public void isKingDay(Long orderId,String orderCode);
	
	public MmcRankingHistoryResponse queryHistory();
}
