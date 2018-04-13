/**   
* @Title: GameTrendChartSpecialRequirementServiceImpl.java 
* @Package com.winterframework.firefrog.game.service.impl 
* @Description: winter-game-task.GameTrendChartSpecialRequirementServiceImpl.java 
* @author Denny  
* @date 2014-7-30 下午2:00:23 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.service.IGameTrendChartSpecialRequirementService;
import com.winterframework.firefrog.user.entity.IndexLottery;

/** 
* @ClassName: GameTrendChartSpecialRequirementServiceImpl 
* @Description: 走势图特殊需求Service实现类 
* @author Denny 
* @date 2014-7-30 下午2:00:23 
*  
*/
@Service("gameTrendChartSpecialRequirementServiceImpl")
public class GameTrendChartSpecialRequirementServiceImpl implements IGameTrendChartSpecialRequirementService {

	private static final Logger log = LoggerFactory.getLogger(GameTrendChartSpecialRequirementServiceImpl.class);
	
	@Resource(name = "RedisService")
	private RedisService redisService;
	 
	@Override
	public void updateMaxOmitValue(Long lotteryId, String omityTrend) throws Exception {
		log.debug("end-create trend:"+omityTrend);
		IndexLottery indexLottery =redisService.getHome(lotteryId); 
		indexLottery.setOmityTrend(omityTrend);
		redisService.setHome(lotteryId,indexLottery); 		
	}

}
