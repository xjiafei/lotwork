package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.entity.GameIssueEntity;

public interface IGameSimulateService {

	/**
	 * 开奖模拟器
	 * @param issueList
	 * @throws Exception
	 */
	public void simulaterOpenArward(List<GameIssueEntity> issueList) throws Exception;

	/**
	 * 立即开奖模拟器
	 * @param lotteryId
	 * @param curTime
	 */
	public void simulaterImmediateOpenAwardByLotteryId(Long lotteryId, Date curTime);

}
