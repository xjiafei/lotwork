package com.winterframework.firefrog.game.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.fund.dao.vo.GameBettypeStatus;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameBettypeStatusDao extends BaseDao<GameBettypeStatus> {
	public List<String> getSuper2000BetTypeCode();
	public List<String> getSuper2000FOURLvlBtc();
	public Long getSuper2000TotalBets(Long userId, List<String> betTypeCodes,ActivityConfig actCfg,List<Integer> lotsList);
	public Long getTotalBets(Long userId,ActivityConfig actCfg,List<Integer> lotsList);
	public Long getTotalBetsByInterval(Long userId, Date startTime, Date endTime, List<Integer> lotsList);
}
