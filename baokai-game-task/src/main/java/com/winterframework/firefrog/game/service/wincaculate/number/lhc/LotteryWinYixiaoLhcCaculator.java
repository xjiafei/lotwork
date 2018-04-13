package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

/**
 * 正特碼 一肖
 * @author user
 *
 */
public class LotteryWinYixiaoLhcCaculator extends LotteryWinSingleLhcCaculator{

	@Override
	protected Integer checkWinMethod(Date orderTime,String betDetail, String specialNum,Set<String> zhengmas, Long singleWin) {
		//一肖 正特碼 中獎號碼合併計算
		zhengmas.add(specialNum);
		Set<String> xiaos = new HashSet<String>();//暫存開獎的所有生肖
		List<GameNumberConfig> numberConfigs = findThisYearNumberConfig(orderTime);
		for(String result: zhengmas){
			for(GameNumberConfig gameNumberConfig : numberConfigs){
				if(gameNumberConfig.getGameNumber().indexOf(result) > -1){
					xiaos.add(gameNumberConfig.getNumType());
				}
			}
		}
		
		boolean isWin = Boolean.TRUE;
		if(xiaos.contains(betDetail)){
					
		} else {
			isWin = Boolean.FALSE;
		}
		if(isWin){
			return singleWin.intValue();
		}

		return 0;
	}

}
