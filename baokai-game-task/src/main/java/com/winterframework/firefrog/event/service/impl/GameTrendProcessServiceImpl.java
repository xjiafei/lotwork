package com.winterframework.firefrog.event.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.event.service.IGameTrendProcessService;
import com.winterframework.firefrog.game.dao.vo.GameTrendTask;
import com.winterframework.firefrog.game.service.IGameTrendTaskService;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartService;

@Service("gameTrendProcessServiceImpl")
public class GameTrendProcessServiceImpl implements IGameTrendProcessService{
	private static final Logger log= LoggerFactory.getLogger(GameTrendProcessServiceImpl.class);
	
	@Resource(name="gameTrendTaskServiceImpl")
	private IGameTrendTaskService gameTrendTaskService;	
	@Resource(name="gameTrendChartServiceImpl")
	private IGameTrendChartService gameTrendChartService; 
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void doProcess(String taskIdStr) throws Exception {
		GameContext ctx=new GameContext();
		Long trendTaskId=Long.valueOf(taskIdStr);
		GameTrendTask trendTask=gameTrendTaskService.getById(ctx,trendTaskId);
		if(null==trendTask){
			log.error("Trend task not exists. task id:"+trendTaskId);
			return;
		}
		trendTask.setExecTime(DateUtils.currentDate());
		try{
			gameTrendChartService.generateCurrentIssueTrendData(trendTask.getLotteryId(), trendTask.getIssueCode(), trendTask.getUserId());
			trendTask.setStatus(GameTrendTask.Status.SUCCESS.getValue());
		}catch(Exception e){
			trendTask.setStatus(GameTrendTask.Status.FAILED.getValue());
			trendTask.setRemark(getStackTrace(e,300));
		}	
		gameTrendTaskService.save(ctx, trendTask);
	}
	
	public String getStackTrace(Throwable t,int len) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        t.printStackTrace(printWriter);
        String err=result.toString();
        if(err.length()>len){
        	err=err.substring(0, len);
        }
        return err;
      }
}
