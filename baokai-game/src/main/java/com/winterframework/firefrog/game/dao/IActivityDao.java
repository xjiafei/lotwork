package com.winterframework.firefrog.game.dao;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.Activity;
import com.winterframework.firefrog.game.dao.vo.DailyActivityVo;
import com.winterframework.firefrog.game.web.dto.LotteryRecordStruc;
import com.winterframework.firefrog.game.web.dto.LotteryResultStruc;
import com.winterframework.firefrog.game.web.dto.QueryLotteryRecordRequest;
import com.winterframework.firefrog.game.web.dto.QueryLotteryResultRequest;
import com.winterframework.firefrog.game.web.dto.QueryRedEnvelopeRequest;
import com.winterframework.firefrog.game.web.dto.RedEnvelopeStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IActivityDao 
* @Description 活动 
* @author  hugh
* @date 2015年1月14日 下午3:28:20 
*  
*/
public interface IActivityDao  extends BaseDao<Activity>{
	
	/**
	 * 获取日常投注奖励所需基本数据结构
	 * @param map
	 * @return
	 */
	public List<DailyActivityVo> getDailyActivityStrucs(Map<String,Object> map);

	/**获取用户在活动期内的有效投注天数
	 * @param map
	 * @return
	 */
	public Long getBetDays(Map<String, Object> map);

	/**获取用户已抽奖次数
	 * @param map
	 * @return
	 */
	Long getRewardNumber(Map<String, Object> map);

	public Page<RedEnvelopeStruc> queryRedEnvelop(
			PageRequest<QueryRedEnvelopeRequest> pr);

	public Page<LotteryRecordStruc> queryLotteryRecord(
			PageRequest<QueryLotteryRecordRequest> pr);

	public Page<LotteryResultStruc> queryLotteryResult(
			PageRequest<QueryLotteryResultRequest> pr);

}
