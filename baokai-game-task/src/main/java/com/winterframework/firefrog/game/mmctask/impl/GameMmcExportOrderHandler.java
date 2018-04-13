package com.winterframework.firefrog.game.mmctask.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.entity.GameMmcTask;
import com.winterframework.firefrog.game.mmctask.IGameMmcTaskHandler;
import com.winterframework.firefrog.game.service.IExportFileService;

@Service("gameMmcExportOrderHandler")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public class GameMmcExportOrderHandler implements IGameMmcTaskHandler {

	private Logger log = LoggerFactory.getLogger(GameMmcExportOrderHandler.class);

	@Resource(name = "exportFileServiceImpl")
	private IExportFileService exportFileService;

	@Override
	public void execute(GameMmcTask task) throws Exception {
		log.debug("---begin GameMmcExportOrderHandler----");
		Date curDate = task.getIssueEndTime();
		Date startTime = task.getIssueStartTime();
		Date endTime = task.getIssueEndTime();
		Long lotteryId = task.getLotteryid();
		log.info("SSSSSSS issue:"+task.getIssue() + "curDate:" + curDate+ "  startTime:" + startTime + " endTime:" + endTime+ "SSSSSSS");
		exportFileService.exportGameSlipMmc(startTime, endTime, curDate, "1",lotteryId);
		exportFileService.exportGameSlipMmc(startTime, endTime, curDate, "2",lotteryId);
		exportFileService.exportRngMmc(startTime, endTime, curDate, lotteryId);
		log.debug("---end GameMmcExportOrderHandler----");
	}
}
