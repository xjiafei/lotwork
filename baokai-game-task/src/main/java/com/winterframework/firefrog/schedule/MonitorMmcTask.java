package com.winterframework.firefrog.schedule;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.dao.entity.GameMmcTask;
import com.winterframework.firefrog.game.dao.entity.GameMmcTask.Status;
import com.winterframework.firefrog.game.dao.entity.GameMmcTask.Type;
import com.winterframework.firefrog.game.mmctask.IGameMmcTaskHandler;
import com.winterframework.firefrog.game.service.IGameMmcTaskService;

@Service("monitorMmcService")
public class MonitorMmcTask {

	private Logger log = LoggerFactory.getLogger(MonitorMmcTask.class);

	@Resource(name = "gameMmcTaskServiceImpl")
	private IGameMmcTaskService gameMmcTaskService;

	@Resource(name = "mmcTaskHandlerMap")
	private Map<String, IGameMmcTaskHandler> handlers;

	public void execute() throws Exception {
		log.debug("---begin MonitorMmcTask----");
		List<GameMmcTask> taskList = gameMmcTaskService.queryBeforeNowTaskList(Status.UNDO);
		log.info("---begin MonitorMmcTask list:"+taskList.size()+"---");
		for (GameMmcTask task : taskList) {
			gameMmcTaskService.updateTaskStatus(task.getId(), Status.UNDO, Status.DOING);
			executeTask(task);
			gameMmcTaskService.updateTaskStatus(task.getId(), Status.DOING, Status.FINISH);
		}
		log.debug("---end MonitorMmcTask----");
	}

	private void executeTask(GameMmcTask task) {
		try {
			Type type = Type.mapping(task.getType());
			IGameMmcTaskHandler handler = getTaskHandler(type);
			handler.execute(task);
		} catch (Exception e) {
			log.error("GameMmcTask execute error", e);
			gameMmcTaskService.updateTaskStatus(task.getId(), Status.DOING, Status.FAIL);
		}
	}

	private IGameMmcTaskHandler getTaskHandler(Type type) throws Exception {
		IGameMmcTaskHandler handler = handlers.get(type.value().toString());
		if (handler == null) {
			throw new Exception("No this type taskHandler:" + type.name());
		}
		return handler;
	}
}
