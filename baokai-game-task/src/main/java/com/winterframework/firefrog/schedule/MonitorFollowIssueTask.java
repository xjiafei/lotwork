package com.winterframework.firefrog.schedule;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.service.IExportFileService;

@Service("monitorFollowIssueService")
public class MonitorFollowIssueTask { 
		private Logger log = LoggerFactory.getLogger(MonitorFollowIssueTask.class);

		@Resource(name = "exportFileServiceImpl")
		private IExportFileService exportFileService;
		@Resource(name = "gameSeriesDaoImpl")
		private IGameSeriesDao gameSeriesDao;
		

		public void execute() throws Exception {
			log.debug("---begin MonitorFollowIssueTask----");
			List<GameSeries> seriesList=gameSeriesDao.getAllValidLottery(1);
			if(seriesList!=null && seriesList.size()>0){
				Date curDate=DateUtils.currentDate();
				Date initDate=DateUtils.getHours(curDate)<23?curDate:DateUtils.addDays(curDate, 1); 
				Date startTime=DateUtils.parse(DateUtils.format(initDate,DateUtils.DATE_FORMAT_PATTERN)+" 00:00:00",DateUtils.DATETIME_FORMAT_PATTERN);
				Date endTime=DateUtils.parse(DateUtils.format(DateUtils.addDays(initDate,1),DateUtils.DATE_FORMAT_PATTERN)+" 23:59:59",DateUtils.DATETIME_FORMAT_PATTERN);
				Date endTimeSsq=DateUtils.parse(DateUtils.format(DateUtils.addDays(initDate,6),DateUtils.DATE_FORMAT_PATTERN)+" 23:59:59",DateUtils.DATETIME_FORMAT_PATTERN);
				for(GameSeries series:seriesList){  
					exportFileService.exportGameIssue(series.getLotteryid(), startTime, series.getLotteryid()==99401L?endTimeSsq:endTime,curDate,null); 
				}
			}
			log.debug("---end MonitorFollowIssueTask----");
		} 
}
