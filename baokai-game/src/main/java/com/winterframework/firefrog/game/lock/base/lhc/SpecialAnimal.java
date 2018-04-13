package com.winterframework.firefrog.game.lock.base.lhc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.lock.base.SingleMethod;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.util.LHCUtil.BetTypeCodeMapping;

/**
 * <pre>
 * 六合彩：
 * 特碼_特肖(每注號碼單選)
 * 正特碼_連肖(中)(每注號碼多選)
 * 正特碼_連肖(不中)(每注號碼多選)
 * </pre>
 * @author Pogi.Lin
 */
public class SpecialAnimal extends SingleMethod {

	/**
	 * Key:注單明細投注內容對應的號球；value:同投注內容出現幾次
	 */
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> _lockMap = new HashMap<String, Integer>();
		
		BetTypeCodeMapping _betTypeCodeMapping = BetTypeCodeMapping.findMappingBetType(methodId);
		if(_betTypeCodeMapping == null) {
			throw new IllegalArgumentException("找不到投注方式编码(" + methodId + ")对应的内部资料。");
		}
		Integer _sizeCombinations = _betTypeCodeMapping.getMinBalls().intValue();
		
		List<GameNumberConfig> _gncList = lhcRedisUtil.findThisYearNumberConfig(new Date());
		List<String> _zodiacList = LHCUtil.combinationsAllBet(betContent, _sizeCombinations);
		
		// 不中要改抓所有未選中的號碼
		if(LHCUtil.isUncontinueNotin(methodId)) {
			List<String> _nums = new ArrayList<String>();
			// 做出一個 5 * 10 陣列，[0,0] 不使用(因為沒有 0 的號碼)
			for (int j = 0; j < 5; j++) {
				for (int k = 0; k < 10; k++) {
					if(j == 0 && k == 0){
						continue;
					}
					_nums.add("" + j + k);
				}
			}
			
			for(String _zodiacBetContent : _zodiacList) {
				String[] _zodiacs = _zodiacBetContent.split(",");
				List<String> _uncontinueNotins = new ArrayList<String>();
				for(String _zodiac : _zodiacs) {
					for(GameNumberConfig _gnc : _gncList) {
						if(_zodiac.equals(_gnc.getNumType())) {
							_uncontinueNotins.addAll(Arrays.asList(_gnc.getGameNumber().split(",")));
						}
					}
				}
				
				for(String _num : _nums) {
					if(!_uncontinueNotins.contains(_num)) {
						if(_lockMap.containsKey(_num)) {
							_lockMap.put(_num, _lockMap.get(_num) + 1);
						} else {
							_lockMap.put(_num, 1);
						}
					}
				}
			}
		} else {
			for(String _zodiacBetContent : _zodiacList) {
				String[] _zodiacs = _zodiacBetContent.split(",");
				for(String _zodiac : _zodiacs) {
					for(GameNumberConfig _gnc : _gncList) {
						if(_zodiac.equals(_gnc.getNumType())) {
							String[] _gameNumber = _gnc.getGameNumber().split(",");
							for(String _key : _gameNumber) {
								if(_lockMap.containsKey(_key)) {
									_lockMap.put(_key, _lockMap.get(_key) + 1);
								} else {
									_lockMap.put(_key, 1);
								}
							}
						}
					}
				}
			}
		}
		
		return _lockMap;
	}
}