package com.winterframework.firefrog.fund.task;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * @ClassName UserAgentCountTask
 * @Description 如果半个小时内还有订单，则取消订单，发送mowcumu取消订单
 * @author hugh
 * @date 2014年6月18日 上午11:51:10
 * 
 */
//@Component
public class CancelMowOrderTask {

	private static Logger logger = LoggerFactory
			.getLogger(CancelMowOrderTask.class);

	@Resource(name = "fundChargeServiceImpl")
	private IFundChargeService fundChargeServiceImpl;
	@PropertyConfig(value = "scheduleTask")
	private String scheduleTask;

	//@Scheduled(cron = "0/5 * * * * ?")
	public void userAgentCountOneHourBefore() {
		if (StringUtils.contains("true", scheduleTask)) {
			logger.trace("----------如果半个小时内还有订单，则取消订单，发送mowcumu取消订单开始：");
			try {
				fundChargeServiceImpl.cancelMowOrde();
				logger.debug("----------如果半个小时内还有订单，则取消订单，发送mowcumu取消订单结束");
			} catch (Exception e) {
				logger.error("----------如果半个小时内还有订单，则取消订单，发送mowcumu取消订单失败", e);
			}
		}
	}

}
