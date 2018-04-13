package com.winterframework.firefrog.game.service;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.game.dao.entity.GameMmcTask;
import com.winterframework.firefrog.game.dao.entity.GameMmcTask.Status;
import com.winterframework.firefrog.schedule.MmcExportFileGenerateTask.Lottery;

public interface IGameMmcTaskService {

	public int insertTask(GameMmcTask task);
	
	public List<GameMmcTask> queryBeforeNowTaskList(Status status);
	
	public int updateTaskStatus(Long id,Status old,Status newStatus);
	
	public void generateExportEvent(Lottery lottery,Date startTime);
}
