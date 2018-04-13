package com.winterframework.firefrog.game.lock.base.lhc;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.entity.LotteryWinLhcMap;
import com.winterframework.firefrog.game.util.LHCUtil.BetTypeCodeMapping;

/**
 * <pre>
 * 六合彩：
 * 特碼_色波_色波(每注類別單選)
 * 特碼_色波_半波(每注類別單選)
 * 特碼_兩面_兩面(每注類別單選)
 * </pre>
 * @author Ami.Tsai
 */
public class SpecialColorAndFace extends SpecialFun {

	/**
	 * 依據投注內容取得真正的投注號碼。
	 */
	@Override
	protected String[] getBetNumber(String betContent) {
		if(BetTypeCodeMapping.lhc_54_37_83.name().contains(methodId)) {
			List<String> _result = new LinkedList<String>();
			
			if("大肖".equals(betContent) || "小肖".equals(betContent)) {
				List<GameNumberConfig> _gncList = lhcRedisUtil.findThisYearNumberConfig(new Date());
				for(String _zodiac : LotteryWinLhcMap.NUM_MAP.get(betContent)) {
					for(GameNumberConfig _gnc : _gncList) {
						if(_zodiac.equals(_gnc.getNumType())) {
							_result.addAll(Arrays.asList(_gnc.getGameNumber().split(",")));
						}
					}
				}
			} else {
				_result.addAll(Arrays.asList(LotteryWinLhcMap.NUM_MAP.get(betContent)));
				// 加入和值 49
				_result.add("49");
			}
			
			String[] _resultArray = new String[_result.size()];
			_result.toArray(_resultArray);
			return _resultArray;
		} else {
			return LotteryWinLhcMap.NUM_MAP.get(betContent);
		}
	}

}
