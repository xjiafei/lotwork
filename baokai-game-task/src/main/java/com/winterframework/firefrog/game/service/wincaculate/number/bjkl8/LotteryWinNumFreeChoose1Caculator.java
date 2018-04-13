package com.winterframework.firefrog.game.service.wincaculate.number.bjkl8;

import java.util.Map;

/**
 * 处理北京快乐8的如下彩种：任选型-普通玩法-任选7
*/
public class LotteryWinNumFreeChoose1Caculator extends LotteryWinNumFreeChooseBJKL8Caculator {

	public void setWinToBetTypeMap(Map<Integer, String> winToBetTypeMap) {
		this.winToBetTypeMap = winToBetTypeMap;
	}

	public Map<Integer, String> getWinToBetTypeMap() {
		return this.winToBetTypeMap;
	}

	@Override
	public Integer getCombinationsNum() {
		return this.combinationsNum;
	}

	@Override
	public void setCombinationsNum(Integer combinationsNum) {
		this.combinationsNum = combinationsNum;
	}
}
