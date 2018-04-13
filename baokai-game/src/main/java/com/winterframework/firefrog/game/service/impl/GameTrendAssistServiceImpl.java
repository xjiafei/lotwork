package com.winterframework.firefrog.game.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.firefrog.game.service.IGameTrendService;
import com.winterframework.firefrog.game.service.assist.bet.LotteryMethodTrendTypeKeyGenerator;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.service.jbyl.impl.util.TrendJbylQueryParam;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: GameTrendAssistServiceImpl 
* @Description: 走势图数据Service实现类--走势辅助
* @author Denny 
* @date 2014-3-27 下午1:47:16 
*  
*/
@Service("gameTrendAssistServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameTrendAssistServiceImpl implements IGameTrendService {

	@Resource(name = "gameTrendJbylDaoImpl")
	private IGameTrendJbylDao gameTrendJbylDaoImpl;

	@PropertyConfig(value = "key.seperator")
	private String keySeperator;

	public void setKeySeperator(String keySeperator) {
		this.keySeperator = keySeperator;
	}

	private Map<LotteryPlayMethodKeyGenerator, List<TrendType>> trendTypeMap;

	public void setTrendTypeMap(Map<LotteryPlayMethodKeyGenerator, List<TrendType>> trendTypeMap) {
		this.trendTypeMap = trendTypeMap;
	}
	
	private Map<LotteryMethodTrendTypeKeyGenerator, TrendJbylQueryParam> queryParamMap;
	
	public void setQueryParamMap(Map<LotteryMethodTrendTypeKeyGenerator, TrendJbylQueryParam> queryParamMap) {
		this.queryParamMap = queryParamMap;
	}
	
	
	/**
	* Title: getTrendByBetMethod
	* Description:查询彩种统计数据
	* @param lotteryId
	* @param gameGroupCode
	* @param gameSetCode
	* @param betMethodCode
	* @param num
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameTrendService#getTrendByBetMethod(long, int, int, int, int) 
	*/
	@Override
	public List<GameTrendJbyl> getTrendByBetMethod(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, int num) {

		return gameTrendJbylDaoImpl.getTrendByBetMethod(lotteryId, gameGroupCode, gameSetCode, betMethodCode, num);
	}

	@Override
	public Map<TrendType, List<String>> queryOmissionValue(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, Integer num) {
		
		Map<TrendType, List<String>> webValueMap = new HashMap<TrendType, List<String>>();
		
		List<TrendType> trendTypes = matchedTrendTypes(lotteryId, gameGroupCode, gameSetCode, betMethodCode,
				keySeperator);
		
		List<String> webValues = null;
		Integer setCode = null;
		for (TrendType trendType : trendTypes) {
			
			TrendJbylQueryParam queryParam = matchedQueryParam(lotteryId, gameGroupCode, gameSetCode, betMethodCode,trendType.getValue(),
					keySeperator);
			if (queryParam != null) {
				setCode = queryParam.getGameSetCode();
			} else {
				setCode = null;
			}
			
			webValues = new ArrayList<String>();
			List<GameTrendJbyl> gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbyl(lotteryId, gameGroupCode, setCode, betMethodCode, trendType.getValue(), num);
			for (GameTrendJbyl g : gameTrendJbylList) {
				webValues.add(g.getWebValue());
			}
			
			webValueMap.put(trendType, webValues);
		}
		
		return webValueMap;
	}

	/** 
	* @Title: matchedKeyGen 
	* @Description: 匹配的遗漏类型
	*/
	private List<TrendType> matchedTrendTypes(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode,
			String keySeperator2) {
		
		LotteryPlayMethodKeyGenerator lotteryKeyGenerator = new LotteryPlayMethodKeyGenerator(lotteryId, gameGroupCode, gameSetCode, betMethodCode,
				keySeperator);
		LotteryPlayMethodKeyGenerator cloneKeyGenerator = (LotteryPlayMethodKeyGenerator) lotteryKeyGenerator.clone();

		List<TrendType> matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}

		cloneKeyGenerator.setLotteryType(null);
		matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}

		cloneKeyGenerator.setMethodCode(null);
		matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}
		
		cloneKeyGenerator.setSetCode(null);
		matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}		
		
		cloneKeyGenerator.setGroupCode(null);
		matchedTrendTypes = trendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}

		return null;
	}

	/** 
	* @Title: matchedQueryParam 
	* @Description: 匹配的查询参数 
	*/
	private TrendJbylQueryParam matchedQueryParam(long lotteryId, int gameGroupCode, int gameSetCode,
			int betMethodCode, int trendType, String keySeperator2) {
		LotteryMethodTrendTypeKeyGenerator lotteryKeyGenerator = new LotteryMethodTrendTypeKeyGenerator(lotteryId, gameGroupCode, gameSetCode, betMethodCode,trendType,
				keySeperator);
		LotteryMethodTrendTypeKeyGenerator cloneKeyGenerator = (LotteryMethodTrendTypeKeyGenerator) lotteryKeyGenerator.clone();

		TrendJbylQueryParam matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}

		cloneKeyGenerator.setLotteryType(null);
		matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}
		
		cloneKeyGenerator.setTrendType(null);
		matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}
		
		cloneKeyGenerator.setMethodCode(null);
		matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}
		
		cloneKeyGenerator.setSetCode(null);
		matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}		
		
		cloneKeyGenerator.setGroupCode(null);
		matchedQueryParam = queryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}

		return null;
	}
	
}
