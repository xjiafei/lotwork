package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.active.dao.vo.ActivityConfig;

public interface IGameBettypeStatusService {
	public List<String> getSuper2000BettypecodeByLotId();
	
	public long getSuper2000TotalBets(Long userId, List<String> betTypeCodesDate,ActivityConfig actCfg,List<Integer> lotsList);
	public long getTotalBets(Long userId,ActivityConfig actCfg,List<Integer> lotsList);
	public Long getTotalBetsByInterval(Long userId, Date startTime, Date endTime, List<Integer> lotsList);
}
