package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

public interface ILotteryWinLhcCodeCaculator {

	Integer caculator(List<String> betDetails, String resultCode, List<GameNumberConfig> gameNumberConfigs);
}
