package com.winterframework.firefrog.help.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.entity.HelpKnowledgeLink;
import com.winterframework.firefrog.help.entity.LotteryHelp;

@Service("HelpLotteryServiceImpl")
public class HelpLotteryServiceImpl extends HelpServiceImpl {
	@Override
	public void createHelp(Help help) throws Exception {
		Long helpId = helpDao.saveHelp(help);
		helpKnowledgeLinkDao.saveLink(((LotteryHelp) help).getLinkList(), helpId);
	}

	@Override
	public void modifyHelp(Help help) throws Exception {
		helpDao.updateHelp(help);
		for (HelpKnowledgeLink link : ((LotteryHelp) help).getLinkList()) {
			helpKnowledgeLinkDao.updateLink(link);
		}
	}

	@Override
	public void deleteHelp(Long id) throws Exception {
		Help help = queryHelpDetail(id);
		LotteryHelp lotteryHelp = (LotteryHelp) help;
		List<HelpKnowledgeLink> list = lotteryHelp.getLinkList();
		if (list != null && (!list.isEmpty())) {
			for (HelpKnowledgeLink link : lotteryHelp.getLinkList()) {
				helpKnowledgeLinkDao.deleteLink(link.getId());
			}
		}
		helpDao.deleteHelp(id);
	}

	@Override
	public Help queryHelpDetail(Long id) throws Exception {
		Help help = helpDao.selectById(id);
		List<HelpKnowledgeLink> list = helpKnowledgeLinkDao.selectByHelpId(id);
		((LotteryHelp) help).setLinkList(list);
		return help;
	}
}
