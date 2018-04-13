package com.winterframework.firefrog.schedule.gameOrder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.service.IActivityService;
import com.winterframework.firefrog.game.service.IGameOrderService;


/**
 * @ClassName: GameOrderWinNoticeTask
 * @Description: 中奖订单通知用户
 * @author hugh
 * @date 2013-11-21 下午8:07:29
 * 
 */
public class ActivitySheepHongBaoTask {

	private Logger log = LoggerFactory.getLogger(ActivitySheepHongBaoTask.class);

	@Resource(name = "ativityServiceImpl")
	private IActivityService ativityServiceImpl;


	/**
	 * @Title: execute
	 * @Description: 任务调度生成器
	 * @throws Exception
	 */
	public void execute() throws Exception {
		log.error("----begin ActivitySheepHongBaoTask----:");
		ativityServiceImpl.excute();
		log.error("----end ActivitySheepHongBaoTask----");
	}


}
