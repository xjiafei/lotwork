package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.Set;

public class LotteryWinSpecialChooseNumLhcCaculator extends LotteryWinSingleLhcCaculator {

	@Override
	protected Integer checkWinMethod(Date orderTime, String betDetail, String specialCode, Set<String> zhengmas, Long singleWin) {
		
		if(betDetail.equals(specialCode)){
			return singleWin.intValue();
		}else{
			return 0;
		}
	}

}
