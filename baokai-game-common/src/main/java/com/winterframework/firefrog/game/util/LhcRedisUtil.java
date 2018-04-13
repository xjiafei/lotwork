package com.winterframework.firefrog.game.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.redis.RedisClient2;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;

@Component(value="lhcRedisUtil")
public class LhcRedisUtil {
	private Logger logger = LoggerFactory.getLogger(LhcRedisUtil.class);
	
	/**redis lock key*/
	public static final String lhcConfigLockKey = "LHC_CONFIG_LOCK_KEY";
	/**redis gameNumberConfig key*/
	public static final String gameNumberConfigKey = "GAME_NUMBER_CONFIG_KEY";
	/**redis lhcGameAwardKey key*/
	public static final String lhcGameAwardKey = "LHC_GAME_AWARD_KEY";

	@Resource(name="RedisClient2")
	private RedisClient2 redisClient2;
	
	private static ObjectMapper objMapper = new ObjectMapper();
	
	/**
	 * 取得當時所對應的生肖設定檔
	 * @param time 判斷生肖的時間
	 * @return
	 */
	public List<GameNumberConfig> findThisYearNumberConfig(Date time){
		String gameNumberConfigStr = redisClient2.get(gameNumberConfigKey);
		List<GameNumberConfig> thisYearConfigs = null;
		try {
			List<List<GameNumberConfig>> gameNumberConfigLists = objMapper.readValue(gameNumberConfigStr, 
					new TypeReference<List<List<GameNumberConfig>>>(){});
			for(List<GameNumberConfig> gameNumberConfigs : gameNumberConfigLists){
				boolean isBetween = Boolean.FALSE;
				
				if(null != gameNumberConfigs && gameNumberConfigs.size() > 0){
					//是否在區間內
					isBetween = time.compareTo(gameNumberConfigs.get(0).getStartTime()) >= 0 &&
								time.compareTo(gameNumberConfigs.get(0).getEndTime()) <= 0;
				}
				
				if(isBetween){
					thisYearConfigs = gameNumberConfigs;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return thisYearConfigs;
	}
	
	/**
	 * 依照獎金組ID, 取得獎金組明細
	 * @param awardGroupId
	 * @return
	 */
	public List<GameAward> findGameAwardByAwardGroupId(Long awardGroupId){
		String gameAwardStr = redisClient2.get(lhcGameAwardKey);
		List<GameAward> gameAwards = null;
		try {
			Map<Long, List<GameAward>> awardMap = objMapper.readValue(gameAwardStr, 
					new TypeReference<Map<Long, List<GameAward>>>(){});
			gameAwards = awardMap.get(awardGroupId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return gameAwards;
	}

	public void setRedisClient2(RedisClient2 redisClient2) {
		this.redisClient2 = redisClient2;
	}
}
