package com.winterframework.firefrog.event;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.redis.RedisQueue;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameTrendTask;
import com.winterframework.firefrog.game.service.IGameTrendTaskService;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartService;
import com.winterframework.modules.utils.SpringContextHolder;

public class GameTrendProcessor implements Runnable{
	private static final Logger log= LoggerFactory.getLogger(GameTrendProcessor.class);
	private final RedisQueue redisQueue=SpringContextHolder.getBean("redisQueue");
	private final RedisClient redisClient=SpringContextHolder.getBean("RedisClient");
	private final IGameTrendTaskService gameTrendTaskService=SpringContextHolder.getBean("gameTrendTaskServiceImpl");
	private final IGameTrendChartService gameTrendChartService=SpringContextHolder.getBean("gameTrendChartServiceImpl");
	//private final IGameTrendProcessService gameTrendProcessService=SpringContextHolder.getBean("gameTrendProcessServiceImpl");
	
	private String keyUserId;
	public GameTrendProcessor(String keyUserId){
		this.keyUserId=keyUserId;
	}
	@Override
	public void run() {
		log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+Thread.currentThread().getName());
	    process(); 
	}
	
	private void process(){
		try{
				log.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+keyUserId+new Date());
			//synchronized(GameTrendSingleton.getInstance(keyUserId)){
				String taskIdStr=redisQueue.get(GameTrendTaskServer.THREND_TASK_PRE+keyUserId);
				//System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS"+new Date());
				while(null!=taskIdStr){
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
					log.debug("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS"+keyUserId+new Date());
					//Thread.sleep(20000);
					taskIdStr=redisQueue.get(GameTrendTaskServer.THREND_TASK_PRE+keyUserId);
					//System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"+new Date());
				}
				redisClient.del(GameTrendTaskServer.THREND_TASK_LOCK+keyUserId);
				log.debug("BBBBBBBBBBBBBBBBBBBBBBBBBBBB"+keyUserId+new Date());
		}catch(Exception e){
			log.error("get trend task error:",e);
		}
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
