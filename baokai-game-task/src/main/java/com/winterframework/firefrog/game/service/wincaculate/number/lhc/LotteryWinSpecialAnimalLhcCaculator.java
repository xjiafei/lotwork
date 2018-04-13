package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

public class LotteryWinSpecialAnimalLhcCaculator extends LotteryWinSingleLhcCaculator {

	@Override
	protected Integer checkWinMethod(Date orderTime, String betDetail, String specialCode, Set<String> zhengmas, Long singleWin) {
		List<GameNumberConfig> numberConfigs = findThisYearNumberConfig(orderTime);
		for(GameNumberConfig config:numberConfigs){
			if(betDetail.equals(config.getNumType())){
				//特碼是否存在於生肖對應中
				if(config.getGameNumber().indexOf(specialCode)!=-1){
					return singleWin.intValue();
				}
			}
		}
		return 0;
	}
}
