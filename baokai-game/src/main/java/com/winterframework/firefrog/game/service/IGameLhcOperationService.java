package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;
import com.winterframework.firefrog.game.web.dto.LhcGameTips;
import com.winterframework.firefrog.game.web.dto.LhcGameZodiac;

public interface IGameLhcOperationService {

	public List<GameLhcOdds> getAllOdds() throws Exception;
	
	
	public List<LhcGameZodiac> getAllNumber() throws Exception;
	
	public List<LhcGameTips> getAllGameTips() throws Exception;
}
