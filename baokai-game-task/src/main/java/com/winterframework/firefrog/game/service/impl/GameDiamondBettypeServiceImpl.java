package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.dao.IGameDiamondBettypeDao;
import com.winterframework.firefrog.game.dao.vo.GameDiamondBettype;
import com.winterframework.firefrog.game.service.IGameDiamondBettypeService;

@Service("gameDiamondBettypeServiceImpl")
public class GameDiamondBettypeServiceImpl implements IGameDiamondBettypeService{

	@Resource(name = "gameDiamondBettypeDaoImpl")
	private IGameDiamondBettypeDao gameDiamondBettypeDaoImpl;
	
	@Override
	public List<GameDiamondBettype> getDiamondBettypeByGroupCode(Long diamondLv) {
		return gameDiamondBettypeDaoImpl.getDiamondBettypeByGroupCode(diamondLv);
	}

}
