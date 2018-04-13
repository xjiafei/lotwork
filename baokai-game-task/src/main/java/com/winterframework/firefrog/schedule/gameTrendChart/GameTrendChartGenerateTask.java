package com.winterframework.firefrog.schedule.gameTrendChart;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartService;

/** 
* @ClassName: GameTrendChartGenerateSchedule 
* @Description: 走势图数据更新任务 
* @author Denny 
* @date 2013-10-25 下午3:38:32 
*  
*/
public class GameTrendChartGenerateTask {

	private Logger log = LoggerFactory.getLogger(GameTrendChartGenerateTask.class);

	@Resource(name = "gameTrendChartServiceImpl")
	private IGameTrendChartService gameTrendChartService;
	@Deprecated
	public void execute() throws Exception {
		log.error("---begin GameTrendChartGenerateSchedule generateTrendChartData----");
		gameTrendChartService.generateTrendChartData();
		log.error("---end GameTrendChartGenerateSchedule generateTrendChartData----");
	}

}
