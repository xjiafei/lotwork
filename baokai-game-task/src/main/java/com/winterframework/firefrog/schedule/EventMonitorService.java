package com.winterframework.firefrog.schedule;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("eventMonitorService")
public class EventMonitorService implements Serializable {
	private static final long serialVersionUID = 7026615035981953235L;

	private static final Logger LOG = LoggerFactory.getLogger(EventMonitorService.class);

	/**
	 * 定时监控方法
	 */
	public void startMonitorEvent() {
		LOG.error("开始执行定时任务--监控Event超时");
		//自己的业务处理
	}
}