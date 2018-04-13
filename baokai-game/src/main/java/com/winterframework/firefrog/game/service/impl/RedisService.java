package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.user.entity.IndexLottery;
import com.winterframework.modules.web.util.JsonMapper;

@Service("RedisService")
public class RedisService {  
	private static final Logger log = LoggerFactory.getLogger(RedisService.class);

	@Resource(name = "RedisClient")
	private RedisClient redisClient;  

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesImpl;
	
	
	private static final String INDEX_LOTTERY_KEY = "firefrog_index_lastdata_";

	/**
	 * 设置首页缓存信息（按彩种）
	 * @param indexLotteryNew	新缓存信息
	 * @return
	 */
	public boolean setHome(Long lotteryId,IndexLottery indexLottery){
		if(indexLottery==null || lotteryId==null) {
			log.error("设置首页缓存出错", "参数为空或者彩种ID为空");
			return false;
		}
		boolean flag=true;
		try{ 
			String redisKey = INDEX_LOTTERY_KEY +lotteryId; 		
			redisClient.set(redisKey, JsonMapper.nonDefaultMapper().toJson(indexLottery));
		}catch(Exception e){
			flag=false;
			log.error("设置首页缓存出错", e);
		}
		return flag;
	}   
	/**
	 * 获取首页缓存信息
	 * @param lotteryId
	 * @return
	 */
	public IndexLottery getHome(Long lotteryId){
		String redisKey = INDEX_LOTTERY_KEY +lotteryId;
		String indexLotteryJson = redisClient.get(redisKey);
		IndexLottery indexLottery=JsonMapper.nonDefaultMapper().fromJson(indexLotteryJson, IndexLottery.class);
		if(indexLottery==null){
			indexLottery=new IndexLottery();
			GameSeries series = gameSeriesImpl.getByLotteyId (lotteryId);
			if (series != null){
				indexLottery.setLottery(series.getLotteryName());
			}
			indexLottery.setLotteryId(lotteryId);
		}
		return indexLottery;
	}
}
