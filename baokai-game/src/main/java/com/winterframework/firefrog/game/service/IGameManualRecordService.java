package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.web.dto.GameManualRecordPageResponse;
import com.winterframework.firefrog.game.web.dto.GameManualRecordRequest;
import com.winterframework.firefrog.game.web.dto.GameManualRecordEncodingRequest;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName IGameManualRecordService 
* @Description 手工录号 
* @author  hugh
* @date 2014年9月15日 下午3:04:03 
*  
*/
public interface IGameManualRecordService {
	
	/** 
	* @Title: getGameManualRecordsByLottery 
	* @Description: 获取手工录号
	* @param lotteryId
	* @return
	*/
	GameManualRecordPageResponse getGameManualRecordsByLottery(PageRequest<GameManualRecordRequest> pr)  throws Exception;
	
	/** 
	* @Title: encodingGameManualRecordsByIssue 
	* @Description: 手工录号相关操作
	* @param issueCode
	*/
	void encodingGameManualRecordsByIssue(GameManualRecordEncodingRequest request)  throws Exception;
}
