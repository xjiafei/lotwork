package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameUserAward;
 
public interface IGameUserAwardService {
	int save(GameUserAward gameUserAward) throws Exception; 
	int remove(Long id) throws Exception;
	/**
	 * 查询用户奖金
	 * @param lotteryId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<GameUserAward> getByLotteryIdAndUserId(Long lotteryId,Long userId) throws Exception;
	List<GameUserAward> getByLotteryIdAndUserIdAndAwardGroupId(Long lotteryId,Long userId,Long userAwardGroupId) throws Exception;
	/**
	 * 查询用户奖金(正常状态)
	 * @param lotteryId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<GameUserAward> getNormalByLotteryIdAndUserId(Long lotteryId,Long userId,Long userAwardGroupId) throws Exception;
	/**
	 * 查询（根据用户彩种玩法）
	 * @param lotteryId
	 * @param userId
	 * @param betTypeCode
	 * @return
	 * @throws Exception
	 */
	GameUserAward getByLotteryIdAndUserIdAndBetTypeCode(Long lotteryId,Long userId,String betTypeCode) throws Exception;
	
	/**
	 * 更新状态（根据玩法类型）
	 * @param lotteryId
	 * @param userId
	 * @param sysAwardGroupId	系统奖金组ID
	 * @param awardBetType
	 * @param status
	 * @throws Exception
	 */
	int updateUserAwardStatusByAwardBetType(Long lotteryId,Long userId,int awardBetType,int status) throws Exception;
	
	/**
	 * 若 GAME_AWARD_USER 內包含指定彩種的某玩法時，刪除該用戶該彩種該玩法的資料。
	 * @param lotteryId 彩種ID
	 * @param userId 用戶ID
	 * @param awardBetType 系統內部玩法編碼(對照 enum GameAwardBetType)
	 * @return 刪除資料筆數
	 * @throws Exception
	 */
	int deleteByAwardBetType(Long lotteryId, Long userId, int awardBetType)throws Exception;
	/**
	 * 批量修改
	 * @param userAwardList
	 * @return	修改记录数
	 * @throws Exception
	 */
	int batchModifyGameUserAward(List<GameUserAward> userAwardList) throws Exception;
	
	/**
	 * 修改(按照用户彩种和玩法）
	 * @param userAwardList
	 * @return
	 * @throws Exception
	 */
	int saveByUserIdLotteryIdBetTypeCode(GameUserAward userAward) throws Exception;
	
}
