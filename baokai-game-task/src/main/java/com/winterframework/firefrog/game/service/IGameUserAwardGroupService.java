package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.enums.GameAwardBetType;

public interface IGameUserAwardGroupService {
	/**
	 * 获取用户奖金组（根据彩种和用户）
	 * @param lottertId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<GameUserAwardGroup> getUserAwardGroupByLotteryIdAndUserId(Long lotteryId,Long userId) throws Exception;
	Long getRetPointByUserIdAndLotteryIdAndBetTypeCode(Long userId,Long lotteryId,String betTypeCode) throws Exception;
	GameUserAwardGroup getBetedByUserIdAndLotteryId(Long userId,Long lotteryId) throws Exception;
	/**
	 * 获取奖金组玩法类型
	 * @param betType	玩法类型
	 * @return
	 * @throws Exception
	 */
	GameAwardBetType getGameAwardBetType(GameBetType betType) throws Exception;
	/**
	 * 取得六合彩用戶於不同玩法時的返點。
	 * @param userId 用戶ID
	 * @param lotteryId 彩種ID
	 * @param betTypeCode 投注方式代碼
	 * @param odds 賠率(必須先*10000)
	 * @return
	 * @throws Exception
	 */
	Long getRetPointByUserIdAndLotteryIdAndBetTypeCodeAndMultiple(Long userId,Long lotteryId,String betTypeCode,Long odds) throws Exception;

}
