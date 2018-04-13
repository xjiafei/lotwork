package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

public class LotteryWinLianxiaoLhcCaculator extends LotteryWinMultLhcCaculator {

	@Override
	protected Map<String, Integer> getAssistCodeWinNumberMap(Date orderTime, List<String> betDetails, String resultCode, 
			String betTypeCode, List<GameAward> gameAwards){
		//當年度生肖設定檔
		List<GameNumberConfig> gameNumberConfigs = findThisYearNumberConfig(orderTime);
		//當年度主肖
		String mainYearXaio = getMainYearXaio();
		
		//將bet依照含主肖 與 沒含主肖  做分類
		List<String> includeMainXaios = new ArrayList<String>();
		List<String> unincludeMainXaios = new ArrayList<String>();
		for(String betDetail : betDetails){
			if(betDetail.indexOf(mainYearXaio) > -1){
				includeMainXaios.add(betDetail);
			} else {
				unincludeMainXaios.add(betDetail);
			}
		}
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(GameAward gameAward : gameAwards){
			String realBetTypeCode = gameAward.getBetTypeCode();
			String methodType = realBetTypeCode.replaceAll(betTypeCode, "").replaceAll("_", "");
			String lhcCode = gameAward.getLhcCode();//六合彩奖金识别
			if(lhcCode.startsWith("ON")){
				//主肖
				Integer winNum = lhcCodeCaculators.get(lhcCode).caculator(includeMainXaios, resultCode, gameNumberConfigs);
				map.put(methodType, winNum);
			} else {
				//副肖
				Integer winNum = lhcCodeCaculators.get(lhcCode).caculator(unincludeMainXaios, resultCode, gameNumberConfigs);
				map.put(methodType, winNum);
			}
		}
		return map;
	}
}
