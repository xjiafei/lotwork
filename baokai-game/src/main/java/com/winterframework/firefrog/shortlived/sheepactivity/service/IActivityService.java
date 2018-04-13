package com.winterframework.firefrog.shortlived.sheepactivity.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.DailyActivityVo;
import com.winterframework.firefrog.game.web.dto.GetLuckyRequest;
import com.winterframework.firefrog.game.web.dto.LotteryRecordStruc;
import com.winterframework.firefrog.game.web.dto.LotteryResultStruc;
import com.winterframework.firefrog.game.web.dto.QueryLotteryRecordRequest;
import com.winterframework.firefrog.game.web.dto.QueryLotteryResultRequest;
import com.winterframework.firefrog.game.web.dto.QueryRedEnvelopeRequest;
import com.winterframework.firefrog.game.web.dto.RedEnvelopeStruc;
import com.winterframework.firefrog.shortlived.sheepactivity.service.util.Award;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName IActivityService 
* @Description 活动 
* @author  hugh
* @date 2014年12月2日 下午3:33:54 
*  
*/
public interface IActivityService {

	
	/**获取日常投注领奖数据结构
	 * @param startTime
	 * @param endTime
	 * @param userId
	 * @return
	 */
	public List<DailyActivityVo> getDailyActivityStrucs(String startTime,String endTime,Long userId);
	
	/**获取当天用户的可抽奖次数
	 * @param userId
	 * @param activityStartTime
	 * @return
	 */
	Long getUserLuckyTime(Long userId, Date activityStartTime,
			Date activityEndTime);

	Page<RedEnvelopeStruc> queryRedEnvelope(
			PageRequest<QueryRedEnvelopeRequest> pr);

	Page<LotteryRecordStruc> queryLotteryRecord(
			PageRequest<QueryLotteryRecordRequest> pr);

	Page<LotteryResultStruc> queryLotteryResult(
			PageRequest<QueryLotteryResultRequest> pr);
	
	 /** 
	* @Title: getBetAward 
	* @Description: 开奖
	* @param userId
	* @return
	* @throws Exception
	*/
	Award getBetAward(GetLuckyRequest param) throws Exception;
}
