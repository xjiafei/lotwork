package com.winterframework.firefrog.game.service.wincaculate.number.lhc;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.service.wincaculate.config.AbstractLotteryWinSlipNumCaculator;
import com.winterframework.firefrog.game.util.LhcRedisUtil;

public abstract class LotteryWinLhcCaculator extends AbstractLotteryWinSlipNumCaculator {
	
	@Resource(name = "gameAwardDaoImpl")
	protected IGameAwardDao gameAwardDaoImpl;
	
	
	@Resource(name="lhcRedisUtil")
	private LhcRedisUtil lhcRedisUtil;
	
	/**當年度主肖*/
	protected static String mainYearXaio;
	
	public static String getMainYearXaio() {
		return mainYearXaio;
	}

	public static void setMainYearXaio(String mainYearXaio) {
		LotteryWinLhcCaculator.mainYearXaio = mainYearXaio;
	}
	
	/**
	 * 取得當年度生肖設定檔
	 * @return
	 */
	protected synchronized List<GameNumberConfig> findThisYearNumberConfig(Date orderTime){
		List<GameNumberConfig> gameNumberConfigs = lhcRedisUtil.findThisYearNumberConfig(orderTime);
		//取得主肖
		for(GameNumberConfig config : gameNumberConfigs){
			if("Y".equalsIgnoreCase(config.getSpecialFlag())){//Y 是主肖
				setMainYearXaio(config.getNumType());
				break;
			}
		}
		return gameNumberConfigs;
	}
}
