package com.winterframework.firefrog.help.service.impl;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.entity.HelpFeedback;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("HelpGeneralServiceImpl")
public class HelpGeneralServiceImpl extends HelpServiceImpl {
	@Override
	public void createHelp(Help help) throws Exception {
		helpDao.saveHelp(help);
	}

	@Override
	public void modifyHelp(Help help) throws Exception {
		helpDao.updateHelp(help);
	}

	@Override
	public void deleteHelp(Long id) throws Exception {
		helpDao.deleteHelp(id);
	}

	@Override
	public Help queryHelpDetail(Long id) throws Exception {
		return helpDao.selectById(id);
	}

	@Override
	public void createFeedback(HelpFeedback feedback) throws Exception {
		feedbackDao.saveFeedback(feedback);
		Help help = queryHelpDetail(feedback.getHelpId());
		if (feedback.getIsSolved().booleanValue()) {
			help.setSolvednum(help.getSolvednum() == null ? 1 : help.getSolvednum() + 1);
		} else {
			help.setUnsolvednum(help.getUnsolvednum() == null ? 1 : help.getUnsolvednum() + 1);
		}
		helpDao.updateFeedBackCount(help);
	}

	@Override
	public Page<Help> queryHelp(PageRequest<HelpQueryRequest> pageRequest) throws Exception {
		return helpDao.selectHelp(pageRequest);
	}
}
