package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameAward;

/**
 * 連碼 (三中二 56_42_14, 二中特 56_42_16)
 * CONTINUECODE322
CONTINUECODE323
CONTINUECODE22Z
CONTINUECODE22T
 * @author user
 *
 */
public class LotteryWinLianmaLhcCaculator extends LotteryWinMultLhcCaculator {

	@Override
	protected Map<String, Integer> getAssistCodeWinNumberMap(Date orderTime, List<String> betDetails, String resultCode,
			String betTypeCode, List<GameAward> gameAwards) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(GameAward gameAward : gameAwards){
			String realBetTypeCode = gameAward.getBetTypeCode();
			String methodType = realBetTypeCode.replaceAll(betTypeCode, "").replaceAll("_", "");
			Integer winNum = lhcCodeCaculators.get(gameAward.getLhcCode()).caculator(betDetails, resultCode, findThisYearNumberConfig(orderTime));
			map.put(methodType, winNum);
		}
		return map;
	}

}
