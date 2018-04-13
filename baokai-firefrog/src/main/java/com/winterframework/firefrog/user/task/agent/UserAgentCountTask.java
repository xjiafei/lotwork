package com.winterframework.firefrog.user.task.agent;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.winterframework.firefrog.user.service.IUserAgentCountService;

/** 
* @ClassName UserAgentCountTask 
* @Description 统计用户代理情况 
* @author  hugh
* @date 2014年6月18日 上午11:51:10 
*  
*/
@Component
public class UserAgentCountTask {

	private static Logger logger = LoggerFactory.getLogger(UserAgentCountTask.class);
	
	@Resource(name = "userAgentCountServiceImpl")
	private IUserAgentCountService userAgentCountServiceImpl;
	
	//@Scheduled(cron = "0 1 * * * ?")  
    public void userAgentCountOneHourBefore() {  
		logger.info("----------统计用户代理数据开始：");
		try {
			userAgentCountServiceImpl.userAgentCountOneHourBefore();
			logger.info("----------统计用户代理数据结束");
		} catch (Exception e) {
			logger.error("----------统计用户代理数据失败",e);
		}
    } 
	
	
}
