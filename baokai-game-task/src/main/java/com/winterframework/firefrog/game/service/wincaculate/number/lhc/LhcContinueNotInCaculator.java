package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

/**
 * 55_41_9X 連肖(不中) 二, 三, 四, 五連肖
 * @author user
 *
 */
public class LhcContinueNotInCaculator implements ILotteryWinLhcCodeCaculator{

	@Override
	public Integer caculator(List<String> betDetails, String resultCode, List<GameNumberConfig> gameNumberConfigs) {
		Integer winBet = 0;
		Set<String> xiaos = new HashSet<String>();//暫存開獎的所有生肖
		
		String[] results = resultCode.split(",");
		for(int i = 0 ; i < results.length ; i++){
			String result = results[i];
			for(GameNumberConfig gameNumberConfig : gameNumberConfigs){
				if(gameNumberConfig.getGameNumber().indexOf(result) > -1){
					xiaos.add(gameNumberConfig.getNumType());
				}
			}
		}
		
		for(String detail : betDetails){
			boolean isWin = Boolean.TRUE;
			String[] betXiaos = detail.split(",");//投注生肖
			for(int i = 0 ; i < betXiaos.length ; i++){
				String betXiao = betXiaos[i];
				if(xiaos.contains(betXiao)){
					isWin = Boolean.FALSE;
					break;
				} else {
					
				}
			}
			if(isWin){
				winBet = winBet + 1;
			}
		}
		return winBet;
	}
}
