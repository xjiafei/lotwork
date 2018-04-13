package com.winterframework.firefrog.game.lock.base.lhc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.SingleMethod;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.util.LHCUtil.BetTypeCodeMapping;

/**
 * <pre>
 * 六合彩：
 * 正碼_平碼_直選六碼(每注號碼單選)
 * 特碼_直選_直選一碼(每注號碼單選)
 * 正特碼_一肖_一肖(每注生肖單選)
 * 正特碼_不中_不中(每注號碼多選)
 * 連碼_連碼(每注號碼多選)
 * </pre>
 * @author Ami.Tsai
 */
public class SpecialZX extends SingleMethod {
	/**
	 * Key:注單明細投注內容對應的號球或生肖；value:同投注內容出現幾次
	 */
	@Override
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> _lockMap = new HashMap<String, Integer>();
		
		if(betContent.contains(",")) {
			BetTypeCodeMapping _betTypeCodeMapping = BetTypeCodeMapping.findMappingBetType(methodId);
			if(_betTypeCodeMapping == null) {
				throw new IllegalArgumentException("找不到投注方式编码(" + methodId + ")对应的内部资料。");
			}
			Integer _sizeCombinations = _betTypeCodeMapping.getMinBalls().intValue();
			List<String> _betContentList = LHCUtil.combinationsAllBet(betContent, _sizeCombinations);
			
			if(LHCUtil.isNotin(methodId)) {
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
				
				for(String _betContentNums : _betContentList) {
					List<String> _betNums = Arrays.asList(_betContentNums.split(","));
					for(String _num : _nums) {
						if(!_betNums.contains(_num)) {
							if(_lockMap.containsKey(_num)) {
								_lockMap.put(_num, _lockMap.get(_num)+1);
							} else {
								_lockMap.put(_num, 1);
							}
						}
					}
				}
			} else {
				for(String _betContentNums : _betContentList) {
					List<String> _betNums = Arrays.asList(_betContentNums.split(","));
					for(String _betNum : _betNums) {
						if(_lockMap.containsKey(_betNum)) {
							_lockMap.put(_betNum, _lockMap.get(_betNum)+1);
						} else {
							_lockMap.put(_betNum, 1);
						}
					}
				}
			}
		} else {
			if(_lockMap.containsKey(betContent)) {
				_lockMap.put(betContent, _lockMap.get(betContent) + 1);
			} else {
				_lockMap.put(betContent, 1);
			}
		}
		return _lockMap;
	}
}
