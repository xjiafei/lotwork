package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameBettypeAssist;

public interface IGameBettypeAssistDao {

	/** 
	* @Title: getBettypeAssistListByTypeIds 
	* @Description: 根据投注类型id获取对应的辅助玩法列表
	* @param betTypeStatusIds
	* @return
	*/
	List<GameBettypeAssist> getBettypeAssistListByTypeIds(List<Long> betTypeStatusIds);

}
