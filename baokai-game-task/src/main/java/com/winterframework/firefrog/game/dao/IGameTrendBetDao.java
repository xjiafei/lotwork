package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameTrendBet;
import com.winterframework.orm.dal.ibatis3.BaseDao;

 
/**
 * 开奖号码走势图投注页信息DAO接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月28日
 */
public interface IGameTrendBetDao extends BaseDao<GameTrendBet> {
   
	/**
	 * 获取最近的记录（根据彩种、玩法）
	 * @param ctx
	 * @param lotteryId
	 * @param groupCode
	 * @param setCode
	 * @param methodCode
	 * @return
	 * @throws Exception
	 */
	GameTrendBet getByLotteryIdAndBetTypeAndType(Long lotteryId,Integer groupCode,Integer setCode,Integer methodCode,Integer type) throws Exception;
	List<GameTrendBet> getByLotteryId(Long lotteryId) throws Exception;
}
