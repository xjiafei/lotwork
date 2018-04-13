package com.winterframework.firefrog.help.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.help.entity.GeneralHelp;
import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.entity.LotteryHelp;
import com.winterframework.firefrog.help.service.IHelpService;

@Service("helpServiceFactory")
public class HelpServiceFactory implements ApplicationContextAware {

	private ApplicationContext ctx;

	private static final String GENERAL_HELP = "HelpGeneralServiceImpl";

	private static final String LOTTERY_HELP = "HelpLotteryServiceImpl";

	public IHelpService getHelpService(Help help) {
		if (help instanceof GeneralHelp) {
			return (IHelpService) ctx.getBean(GENERAL_HELP);
		} else if (help instanceof LotteryHelp) {
			return (IHelpService) ctx.getBean(LOTTERY_HELP);
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

}
