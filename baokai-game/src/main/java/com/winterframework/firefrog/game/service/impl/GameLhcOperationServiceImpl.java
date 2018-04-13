package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameHelpDao;
import com.winterframework.firefrog.game.dao.IGameNumberConfigDao;
import com.winterframework.firefrog.game.dao.vo.GameHelpAndBonus;
import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.service.IGameLhcOperationService;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.web.dto.LhcGameTips;
import com.winterframework.firefrog.game.web.dto.LhcGameZodiac;

@Service("gameLhcOperationServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameLhcOperationServiceImpl implements IGameLhcOperationService {

	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao;
	
	@Resource(name = "gameNumberConfigDaoImpl")
	private IGameNumberConfigDao gameNumberConfigDao;
	
	@Resource(name = "gameHelpDaoImpl")
	private IGameHelpDao gameHelpDao;


	@Override
	public List<GameLhcOdds> getAllOdds() {
		return gameAwardDao.getByLotteryId(99701l);
	}
	
	@Override
	public List<LhcGameZodiac> getAllNumber() {
		List<LhcGameZodiac> gameZodiacList = new ArrayList<LhcGameZodiac>();
		List<GameNumberConfig> numberConfigs = gameNumberConfigDao.getByEffDate();
		
		for(GameNumberConfig v : numberConfigs){
			LhcGameZodiac gameZodiac = new LhcGameZodiac();
			gameZodiac.setZodiacNameCn(v.getNumType());
			gameZodiac.setZodiacName(LHCUtil.Zodiac.getEnName(v.getNumType()));
			gameZodiac.setSpecialFlag(v.getSpecialFlag());
			gameZodiac.setNumber(v.getGameNumber());
			gameZodiacList.add(gameZodiac);
		}
		
		return gameZodiacList;
	}

	@Override
	public List<LhcGameTips> getAllGameTips() {
		List<LhcGameTips> gameTipsList = new ArrayList<LhcGameTips>();
		List<GameHelpAndBonus> gameHelpAndBonus = gameHelpDao.getTipsAndGameName(99701);
		
		Map<String, List<GameHelpAndBonus>> map = new HashMap<String, List<GameHelpAndBonus>>();
		for(GameHelpAndBonus gameHelpAndBonu : gameHelpAndBonus){
			String groupCodeName = gameHelpAndBonu.getGroupCodeName();
			if(map.containsKey(groupCodeName)){
				map.get(groupCodeName).add(gameHelpAndBonu);
			} else {
				List<GameHelpAndBonus> bonus = new ArrayList<GameHelpAndBonus>();
				bonus.add(gameHelpAndBonu);
				map.put(groupCodeName, bonus);
			}
		}
		
		for(String key : map.keySet()){
			List<GameHelpAndBonus> tempGameHelpAndBonus = map.get(key);
			LhcGameTips gameTips = new LhcGameTips();
			List<LhcGameTips> childs = new ArrayList<LhcGameTips>();
			
			for(GameHelpAndBonus gameHelpAndBonu : tempGameHelpAndBonus){
				gameTips.setName(gameHelpAndBonu.getGroupCodeName());
				gameTips.setRule(gameHelpAndBonu.getGamePromptDes());
				gameTips.setExample(gameHelpAndBonu.getGameExamplesDes());
				
				LhcGameTips tip = new LhcGameTips();
				LhcGameTips subtip = new LhcGameTips();		
				List<LhcGameTips> subChilds = new ArrayList<LhcGameTips>();
				tip.setName(gameHelpAndBonu.getSetCodeName());      		
				//細項
				subtip.setName(gameHelpAndBonu.getMethodCodeName());      
				subtip.setRule(gameHelpAndBonu.getGamePromptDes());      
				subtip.setExample(gameHelpAndBonu.getGameExamplesDes()); 
				subChilds.add(subtip);
				tip.setChilds(subChilds);

				childs.add(tip);
			}
			gameTips.setChilds(childs);
			gameTipsList.add(gameTips);
		}
		
		return gameTipsList;
	}
}


