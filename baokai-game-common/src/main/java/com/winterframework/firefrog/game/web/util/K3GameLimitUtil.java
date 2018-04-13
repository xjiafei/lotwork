package com.winterframework.firefrog.game.web.util;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.entity.BetLimit;
import com.winterframework.firefrog.game.entity.BetLimitAssist;

/**
 * 骰寶類投注限制特殊處理
 * @author Ami.Tsai
 *
 */
public class K3GameLimitUtil {
	
	//骰寶和值投注限制
	public static Map<Long,String[]> jlsbhezhiLimit = new HashMap<Long, String[]>();
	
	private static final String heizhi="34_28_71";
	
	static{
		jlsbhezhiLimit.put(52l, new String[]{"17","4"});
		jlsbhezhiLimit.put(53l, new String[]{"16","5"});
		jlsbhezhiLimit.put(54l, new String[]{"15","6"});
		jlsbhezhiLimit.put(55l, new String[]{"14","7"});
		jlsbhezhiLimit.put(56l, new String[]{"13","8"});
		jlsbhezhiLimit.put(57l, new String[]{"12","9"});
		jlsbhezhiLimit.put(58l, new String[]{"11","10"});
	}
	
	public static Map<String,Integer> processorSpecialGameLimit(BetLimit bl){
		
		Map<String,Integer> gameLimitMap = new HashMap<String, Integer>();
		Long lotteryId = bl.getLotteryid();		
		if((lotteryId==99601 || lotteryId==99602||lotteryId==99603)){
			String betTypeCode = bl.getGameGroupCode()+"_"+bl.getGameSetCode()+"_"+bl.getBetMethodCode();
			//和值的處理方式
			if(heizhi.equals(betTypeCode)){
				for (BetLimitAssist betLimitAssist : bl.getBetLimitAssist()) {
					if(jlsbhezhiLimit.get(betLimitAssist.getAssitCode())!=null){
						for(String limitNumber:jlsbhezhiLimit.get(betLimitAssist.getAssitCode())){
							gameLimitMap.put(limitNumber, betLimitAssist.getMaxMultiple().intValue());
						}
					}
				}
			}
		}
		
		return gameLimitMap;
	}
	
	
}
