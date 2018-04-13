package com.winterframework.firefrog.game.service.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.redis.RedisClient2;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameNumberConfigDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.util.LhcRedisUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Component
public class LhcConfigRedisInit {
	
	@Resource(name = "gameNumberConfigDaoImpl")
	protected IGameNumberConfigDao gameNumberConfigDaoImpl;
	
	@Resource(name = "gameAwardDaoImpl")
	protected IGameAwardDao gameAwardDaoImpl;
	
	@Resource(name="RedisClient2")
	private RedisClient2 redisClient2;

	@PropertyConfig(value="lottery.LHC")
	private String lotteryId;
	
	private static ObjectMapper objMapper = new ObjectMapper();
	
	/**將生肖設定檔 放入 redis cache, 目的是為了讓web 或 其他地方有用到的  不需要再回去api取資料*/
	@PostConstruct
	private void init() throws Exception{
		boolean isLock = redisClient2.acquireLock(LhcRedisUtil.lhcConfigLockKey, 10000);
		try{
			if(isLock){
				//設定生肖設定檔
				List<List<GameNumberConfig>> list = new ArrayList<List<GameNumberConfig>>();
				
				Date now = DateUtils.currentDate();
				List<GameNumberConfig> thisYearDatas = gameNumberConfigDaoImpl.getByYearLaterDate(now);
				
				if(CollectionUtils.isNotEmpty(thisYearDatas)){
					Date startTime = thisYearDatas.get(0).getStartTime();
					Date endTime = thisYearDatas.get(0).getEndTime();
					
					Date lastYear = DateUtils.addDays(startTime, -1);
					List<GameNumberConfig> lastYearDatas = gameNumberConfigDaoImpl.getByYearLaterDate(lastYear);
					
					Date nextYear = DateUtils.addDays(endTime, 1);
					List<GameNumberConfig> nextYearDatas = gameNumberConfigDaoImpl.getByYearLaterDate(nextYear);
					
					list.add(lastYearDatas);
					list.add(thisYearDatas);
					list.add(nextYearDatas);
					String nowYearData = objMapper.writeValueAsString(list);
					redisClient2.set(LhcRedisUtil.gameNumberConfigKey, nowYearData);
				} else {
					throw new Exception("無法取得六合彩生肖設定檔");
				}
				//設定獎金組明細
				List<GameAward> gameAwards = gameAwardDaoImpl.getGameAwardByLotteryIdFilterFourCode(Long.valueOf(lotteryId));
				
				if(CollectionUtils.isNotEmpty(gameAwards)){
					Map<Long, List<GameAward>> tmpGameAwards = new HashMap<Long, List<GameAward>>();
					for(GameAward gameAward : gameAwards){
						if(tmpGameAwards.containsKey(gameAward.getAwardGroupId())){
							tmpGameAwards.get(gameAward.getAwardGroupId()).add(gameAward);
						} else {
							List<GameAward> tmpDatas = new ArrayList<GameAward>();
							tmpDatas.add(gameAward);
							tmpGameAwards.put(gameAward.getAwardGroupId(), tmpDatas);
						}
					}
					String gameAwardsStr = objMapper.writeValueAsString(tmpGameAwards);
					redisClient2.set(LhcRedisUtil.lhcGameAwardKey, gameAwardsStr);
				} else {
					throw new Exception("無法取得六合彩獎金明細設定檔");
				}
				
				
			} else {
				
			}
		} finally {
			if(isLock){
				redisClient2.releaseLock(LhcRedisUtil.lhcConfigLockKey);
			}
		}
	}
}
