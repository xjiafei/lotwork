package com.winterframework.firefrog.game.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameMmcTaskDao;
import com.winterframework.firefrog.game.dao.entity.GameMmcTask;
import com.winterframework.firefrog.game.dao.entity.GameMmcTask.Status;
import com.winterframework.firefrog.game.dao.entity.GameMmcTask.Type;
import com.winterframework.firefrog.game.service.IGameMmcTaskService;
import com.winterframework.firefrog.schedule.MmcExportFileGenerateTask.Lottery;

@Service("gameMmcTaskServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameMmcTaskServiceImpl implements IGameMmcTaskService{

	private Logger log = LoggerFactory.getLogger(GameMmcTaskServiceImpl.class);
	
	@Resource(name="gameMmcTaskDaoImpl")
	private IGameMmcTaskDao gameMmcTaskDao;
	
	@Override
	public int insertTask(GameMmcTask task) {
		return gameMmcTaskDao.insertUpdate(task);
	}

	@Override
	public List<GameMmcTask> queryBeforeNowTaskList(Status status) {
		List<GameMmcTask> list = null;
		GameMmcTask request = new GameMmcTask();
		request.setTargetStatus(status);
		request.setTargetEndTimeEnd(new Date());
		list = gameMmcTaskDao.queryBeforeNowTaskList(request);
		if (list == null) {
			list = new ArrayList<GameMmcTask>();
		}
		return list;
	}

	@Override
	public int updateTaskStatus(Long id, Status oldStatus,
			Status newStatus) {
		log.info("updateMmcTask id:" + id + ",from:"
				+ oldStatus.name() + " to:" + newStatus.name());
		GameMmcTask task = new GameMmcTask();
		task.setTargetId(id);
		task.setTargetStatus(oldStatus);
		task.setStatus(newStatus);
		task.setUpdateDate(new Date());
		return gameMmcTaskDao.update(task);
	}
	

	@Override
	public void generateExportEvent(Lottery lottery,Date startTime){
		log.info("generateExportEvent lottery:"+lottery.name()+",perMinute:"+lottery.perMinutes+",events:"+lottery.events);
		String lotteryLowId = lottery.lotteryId.toString().substring(2,5);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"+lotteryLowId+"HHmm");
		Date currentDateStartTime = DateUtils.getStartTimeOfDate(startTime);
		long todayOldEvents = DateUtils.calcMinutesBetween(currentDateStartTime, startTime);
		long events = lottery.events - todayOldEvents;
		for(int i=0;i<events;i++){
			Date time = DateUtils.addMinutes(startTime, i);
			Long issueCode = Long.parseLong(sdf.format(time));
			addExportFileTask(lottery, time, issueCode);
		}
		log.info("generateExportEvent startIssueCode:"+sdf.format(startTime));
		
	}
	
	private void addExportFileTask(Lottery lottery,Date startTime,Long issue){
		try {
			Date endTime = DateUtils.addMinutes(startTime, lottery.perMinutes);
			Date now = new Date();
			GameMmcTask task = new GameMmcTask();
			task.setIssue(issue);
			task.setIssueStartTime(startTime);
			task.setIssueEndTime(endTime);
			task.setLotteryid(lottery.lotteryId);
			task.setStatus(Status.UNDO);
			task.setType(Type.EXPORT_ORDER);
			task.setCreateDate(now);
			task.setUpdateDate(now);
			gameMmcTaskDao.insertUpdate(task);
		} catch (Exception e) {
			log.error("create mmcExportOrdeEvent:"+issue,e);
		}
	}

}
