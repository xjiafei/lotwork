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
* @ClassName: GameTrendJbylServiceImpl 
* @Description: 走势图数据Service实现类--基本走势&综合走势
* @author Denny 
* @date 2014-3-27 下午1:47:16 
*  
*/
@Service("gameTrendJbylServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameTrendJbylServiceImpl implements IGameTrendService {

	@Resource(name = "gameTrendJbylDaoImpl")
	private IGameTrendJbylDao gameTrendJbylDaoImpl;

	@PropertyConfig(value = "key.seperator")
	private String keySeperator;

	public void setKeySeperator(String keySeperator) {
		this.keySeperator = keySeperator;
	}

	private Map<LotteryPlayMethodKeyGenerator, List<TrendType>> jbylTrendTypeMap;

	public void setJbylTrendTypeMap(Map<LotteryPlayMethodKeyGenerator, List<TrendType>> trendTypeMap) {
		this.jbylTrendTypeMap = trendTypeMap;
	}
	
	private Map<LotteryMethodTrendTypeKeyGenerator, TrendJbylQueryParam> jbylQueryParamMap;
	
	public void setJbylQueryParamMap(Map<LotteryMethodTrendTypeKeyGenerator, TrendJbylQueryParam> queryParamMap) {
		this.jbylQueryParamMap = queryParamMap;
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
			List<GameTrendJbyl> gameTrendJbylList = gameTrendJbylDaoImpl.getTrendJbyl(lotteryId,gameGroupCode, setCode, betMethodCode,trendType.getValue(), num);
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
	private List<TrendType> matchedTrendTypes(Long lotteryId, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode,
			String keySeperator2) {
		
		LotteryPlayMethodKeyGenerator lotteryKeyGenerator = new LotteryPlayMethodKeyGenerator(lotteryId, gameGroupCode, gameSetCode, betMethodCode,
				keySeperator);
		LotteryPlayMethodKeyGenerator cloneKeyGenerator = (LotteryPlayMethodKeyGenerator) lotteryKeyGenerator.clone();

		List<TrendType> matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}

		cloneKeyGenerator.setLotteryType(null);
		matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}

		cloneKeyGenerator.setMethodCode(null);
		matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}
		
		cloneKeyGenerator.setSetCode(null);
		matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}		
		
		cloneKeyGenerator.setGroupCode(null);
		matchedTrendTypes = jbylTrendTypeMap.get(cloneKeyGenerator);
		if (matchedTrendTypes != null) {
			return matchedTrendTypes;
		}

		return null;
	}

	/** 
	* @Title: matchedQueryParam 
	* @Description: 匹配的查询参数 
	*/
	private TrendJbylQueryParam matchedQueryParam(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, int trendType, String keySeperator2) {
		LotteryMethodTrendTypeKeyGenerator lotteryKeyGenerator = new LotteryMethodTrendTypeKeyGenerator(lotteryId, gameGroupCode, gameSetCode, betMethodCode,trendType,
				keySeperator);
		LotteryMethodTrendTypeKeyGenerator cloneKeyGenerator = (LotteryMethodTrendTypeKeyGenerator) lotteryKeyGenerator.clone();

		TrendJbylQueryParam matchedQueryParam = jbylQueryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}

		cloneKeyGenerator.setLotteryType(null);
		matchedQueryParam = jbylQueryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}
		
		cloneKeyGenerator.setTrendType(null);
		matchedQueryParam = jbylQueryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}
		
		cloneKeyGenerator.setMethodCode(null);
		matchedQueryParam = jbylQueryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}
		
		cloneKeyGenerator.setSetCode(null);
		matchedQueryParam = jbylQueryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}		
		
		cloneKeyGenerator.setGroupCode(null);
		matchedQueryParam = jbylQueryParamMap.get(cloneKeyGenerator);
		if (matchedQueryParam != null) {
			return matchedQueryParam;
		}

		return null;
	}
	
}
