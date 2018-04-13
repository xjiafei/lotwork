package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWinsReport;

public interface IGameWinsReportService {

	int save(GameWinsReport report)  throws Exception;
	/**
	 * 
	* @Title: createGameWinReport 
	* @Description: 生成 Game_Wins_Report信息
	* @param lotteryId
	* @param issueCode
	 */
	public void createGameWinReport(Long lotteryId, Long issueCode) throws Exception;
	
	/**
	 * 获取不存在盈亏表记录的issue_code
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	public List<Long> getGameIssueCodeListNoWinsReport(Long lotteryId) throws Exception;
}
