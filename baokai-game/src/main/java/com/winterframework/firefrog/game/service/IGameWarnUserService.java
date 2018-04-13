package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;

 
/**
 * 风险用户服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月16日
 */
public interface IGameWarnUserService {
	 
	GameWarnUser getByLotteryIssueUserId(GameContext ctx,Long userId,Long lotteryId,Long issueCode) throws Exception;
	  
	/**
	 * 获取警告用户最近一期数据
	 * @param ctx
	 * @param lotteryId
	 * @param issueCode
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	GameWarnUser getLastWarnUser(GameContext ctx,Long userId,Long lotteryId,Long issueCode) throws Exception; 
}
