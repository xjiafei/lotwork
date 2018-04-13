package com.winterframework.firefrog.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.redis.RedisQueue;

@Service
//@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class GameTrendTaskServer {
	private static final Logger log = LoggerFactory.getLogger(GameTrendTaskServer.class); 
	
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(60);
	public static final String THREND_TASK_PRE="trend_task_pre";
	public static final String THREND_TASK_LOCK="trend_task_lock";
	public static final String QUEUE_NAME=THREND_TASK_PRE+"userIdList";
	
	@Resource(name="redisQueue")
	private RedisQueue redisQueue;
	
	public void startup(){
		log.info("顺利彩 走势图  服务 启动......");
		new GameTrendThread(threadPool,QUEUE_NAME).start();	
	}
	
	private class GameTrendThread extends Thread{
		ExecutorService threadPool;
		String queueName;
		public GameTrendThread(ExecutorService threadPool,String queueName){
			this.threadPool=threadPool;
			this.queueName=queueName;
		}
		@SuppressWarnings("static-access")
		@Override
		public void run() {
			try {  
				while(true){
					long time1=System.currentTimeMillis(); 
					try {
						Thread.currentThread().sleep(2);
					} catch(InterruptedException ex){
						log.error("Thread be interrupted.",ex);
					}
					//处理正常的消息队列
					
					execute(threadPool,queueName);
					long time2=System.currentTimeMillis();
					log.debug("notyServer cost time:"+(time2-time1));
				}
			} catch (Exception e) {
				log.error("顺利彩 走势图  服务 异常:"+queueName,e);
			} 
		} 
	}
	private void execute(ExecutorService threadPool,String queueName) { 
		String keyUserId=redisQueue.get(queueName); 
	
		//List<String> values=redisQueue.get(queueName,queueName + "_offline",queueName+"_offline_1");  
		if(null==keyUserId){
			//log.info("notification in the queue all has been pushed.");
			return;
		}
	
		threadPool.execute(new GameTrendProcessor(keyUserId));
		
	}
}