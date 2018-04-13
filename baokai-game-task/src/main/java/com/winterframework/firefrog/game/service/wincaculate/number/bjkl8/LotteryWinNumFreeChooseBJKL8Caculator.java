package com.winterframework.firefrog.game.service.wincaculate.number.bjkl8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;

/**
 * 处理北京快乐8的如下彩种：任选型-普通玩法-任选7
*/
public abstract class LotteryWinNumFreeChooseBJKL8Caculator extends AbstractLotteryWinSlipNumCaculator {

	/** 
	* 中奖方式已经与玩法类型对应的Map
	*/
	protected Map<Integer, String> winToBetTypeMap;

	/** 
	* 选取几个数做组合
	*/
	protected Integer combinationsNum;

	@Override
	public IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception {
		//注单的中奖结果Map
		Map<String, Integer> winResultMap = new HashMap<String, Integer>();
		//可能中奖方式的key值（7中七7，7中6，7中5    中的7，6，5集合）
		Set<Integer> winKeyList = getWinToBetTypeMap().keySet();
		List<String> betNumbers = new ArrayList<String>(Arrays.asList(betDetail.split(",")));
		List<String> resultNumbers = new ArrayList<String>(Arrays.asList(resultCode.split(",")));
		//获取投注数字的组合列表
		List<String> combinResult = CaculateUtil.getCombinations(betNumbers, getCombinationsNum());
		for (String string : combinResult) {
			List<String> temBetNumer = new ArrayList<String>(Arrays.asList(string.split(",")));
			//重新赋值
			resultNumbers = new ArrayList<String>(Arrays.asList(resultCode.split(",")));
			//开奖号码和中奖号码去交集
			resultNumbers.retainAll(temBetNumer);
			if (winKeyList.contains(resultNumbers.size())) {
				String betTypeStr = getWinToBetTypeMap().get(resultNumbers.size());
				if (winResultMap.containsKey(betTypeStr)) {
					winResultMap.put(betTypeStr, winResultMap.get(betTypeStr) + 1);
				} else {
					winResultMap.put(betTypeStr, 1);
				}
			}
		}
		return generateWinResultMultBean(winResultMap, context);
	}

	public abstract void setWinToBetTypeMap(Map<Integer, String> winToBetTypeMap);

	public abstract Map<Integer, String> getWinToBetTypeMap();

	public abstract Integer getCombinationsNum();

	public abstract void setCombinationsNum(Integer combinationsNum);
}
