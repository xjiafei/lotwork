package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 奖金组Dao接口类 
 * @author Richard
 * @date 2013-12-27 下午3:49:24 
 */
public interface IGameAwardDao extends BaseDao<GameAward> {

	/**
	 * 查询正式表的GameAward
	 * @param groupId
	 * @param lotteryId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	List<GameAward> queryGameAwardByGroupId(Long groupId, Long lotteryId, Integer status) throws Exception;

	/**
	 * 根据奖金组ID获取奖金组
	 * @param awardGroupId
	 * @return
	 */
	List<GameAward> getGameAwardByGroupId(Long awardGroupId);

	/**
	 * 根据用户id，彩种id，以及玩法获取对应的期望奖金
	 * @param lotteryId
	 * @param betTypeCode
	 * @param userId
	 * @return
	 */
	Long getActualBonus(Long lotteryId, String betTypeCode, Long userId);
	Long getActualBonusDown(Long lotteryId, String betTypeCode, Long userId);
	Long getActualBonusByAwardGroupId(Long lotteryId, String betTypeCode, Integer sysGroupId);
	/**
	 * 根据彩种，玩法信息，和系统奖金组id获取系统奖金信息
	 * @param lotteryId
	 * @param betTypeCode
	 * @param AwardGroupId
	 * @return
	 */
	GameAward getGameAwardByBetTypeAndLotteryId(Long lotteryId, String betTypeCode, Long AwardGroupId);

	/**
	 * 获取多奖金组信息列表
	 * @param lotteryid
	 * @param betTypeCode
	 * @param userId
	 * @return
	 */
	List<GameAward> getGameAwardListByBetCodeParent(Long lotteryid, String betTypeCode, Long userId);
	
	public List<GameAward> queryUserGameAwardByGroupId(Long awardGroupId, Long lotteryId, Integer status)
			throws Exception;
	
	public Long queryUserAwardGroupIdBySysAwardGroupAndUser(Long sysAwardGroupId,Long userId) throws Exception;
	
	List<GameAward> getValidByLotteryIdAndGroupIdAndBetTypeCodeParent(Long lotteryId,Long awardGroupId,String betTypeCode);
	
	/**
	 * 依據 lotteryId 取回六合彩賠率物件。
	 * @param lotteryId 彩種ID
	 * @return
	 */
	public List<GameLhcOdds> getByLotteryId(Long lotteryId);
	
	public String getAwardBetTypeForOne(Long lotteryId);
}
