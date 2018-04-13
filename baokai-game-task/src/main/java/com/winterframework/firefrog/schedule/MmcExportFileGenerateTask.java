package com.winterframework.firefrog.schedule;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.service.IGameMmcTaskService;

public class MmcExportFileGenerateTask {

	private Logger log = LoggerFactory.getLogger(MmcExportFileGenerateTask.class);
	
	@Resource(name="gameMmcTaskServiceImpl")
	private IGameMmcTaskService gameMmcTaskService;

	public void execute() throws Exception {
		log.debug("---begin MmcExportFileGenerateSchedule----");
		final String pattern = DateUtils.DATETIME_WITHOUT_SECOND_FORMAT_PATTERN;
		//补上今天剩下的Task
		Date currentTime=DateUtils.parse(DateUtils.format(new Date(),pattern),pattern);
		gameMmcTaskService.generateExportEvent(Lottery.SLMMC, currentTime);
		gameMmcTaskService.generateExportEvent(Lottery.SL115, currentTime);
		gameMmcTaskService.generateExportEvent(Lottery.SLMMC2000, currentTime);
		//建立明后二天的
		Date nextDay = DateUtils.addDays(new Date(), 1);
		generateDaysExportEvent(Lottery.SLMMC,nextDay,2);
		generateDaysExportEvent(Lottery.SL115,nextDay,2);
		generateDaysExportEvent(Lottery.SLMMC2000,nextDay,2);
		log.debug("---end MmcExportFileGenerateSchedule----");
	}

	private void generateDaysExportEvent(Lottery lotteryId,Date date,int days){
		Date currentDateStartTime = DateUtils.getStartTimeOfDate(date);
		for(int addDays=0;addDays<days;addDays++){
			Date startTime = DateUtils.addDays(currentDateStartTime, addDays);
			gameMmcTaskService.generateExportEvent(lotteryId,startTime);
		}
	}
	
	public enum Lottery{
		SLMMC(99112L,1440,1),
		SL115(99306L,1440,1),
		SLMMC2000(99113L,1440,1);
		public final Long lotteryId;
		public final int events;
		public final int perMinutes;
		Lottery(Long lotteryId,int events,int perMinutes){
			this.lotteryId = lotteryId;
			this.events = events;
			this.perMinutes = perMinutes;
		}
	}
}
