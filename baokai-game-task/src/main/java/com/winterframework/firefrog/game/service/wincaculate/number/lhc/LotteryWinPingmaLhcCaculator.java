package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.Set;

/**
 * 平碼
 * @author user
 *
 */
public class LotteryWinPingmaLhcCaculator extends LotteryWinSingleLhcCaculator{

	@Override
	protected Integer checkWinMethod(Date orderTime, String betDetail, String specialNum, Set<String> zhengmas, Long singleWin) {
		return zhengmas.contains(betDetail) ? singleWin.intValue() : 0;
	}

}
