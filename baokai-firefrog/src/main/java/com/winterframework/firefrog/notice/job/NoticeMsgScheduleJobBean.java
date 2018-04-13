/**   
* @Title: NoticeMsgScheduleJobBean.java 
* @Package com.winterframework.firefrog.notice.job 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-31 上午11:04:53 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.channel.service.IChannelService;
import com.winterframework.firefrog.channel.web.dto.ChannelDto;
import com.winterframework.firefrog.notice.service.INoticeMsgService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: NoticeMsgScheduleJobBean 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-31 上午11:04:53 
*  
*/
//@Component
public class NoticeMsgScheduleJobBean {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Resource(name = "noticeMsgServiceImpl")
	private INoticeMsgService msgService;
	@PropertyConfig(value = "scheduleTask")
	private String scheduleTask;
	@Resource(name = "channelServiceImpl")
	private IChannelService channelService;
	
	
	@Scheduled(cron = "1 * *  * * ? ")
	public void execute() throws Exception {
		if (StringUtils.contains("true", scheduleTask))
			msgService.pushScheduleMsgToMq(rabbitTemplate);
	}
	
	@Scheduled(cron = "* 2 0  * * ? ")
	public void executeChannel() throws Exception {
		try{
			channelService.getReset();
			channelService.setDayReset();
		}catch(Exception e){
			throw e;
		}
	}
}
