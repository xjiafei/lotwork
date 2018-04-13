package com.winterframework.firefrog.game.event;

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
}