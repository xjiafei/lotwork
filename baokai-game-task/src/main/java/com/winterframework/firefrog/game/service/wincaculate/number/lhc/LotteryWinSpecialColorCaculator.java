package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import com.winterframework.firefrog.game.entity.LotteryWinLhcMap;

/**
 * 色波
 * @author Ami.Tsai
 *
 */
public class LotteryWinSpecialColorCaculator extends LotteryWinSingleLhcCaculator {
	
	@Override
	protected Integer checkWinMethod(Date orderTime, String betDetail, String specialCode, Set<String> zhengmas, Long singleWin) {
		String[] wimNums = LotteryWinLhcMap.NUM_MAP.get(betDetail);
		Boolean isWin = Arrays.asList(wimNums).contains(specialCode);
		if(isWin){
			return singleWin.intValue();
		}else{
			return 0;
		}
		
	}
}
