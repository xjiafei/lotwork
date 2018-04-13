package com.winterframework.firefrog.help.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.help.dao.IHelpDao;
import com.winterframework.firefrog.help.dao.IHelpFeedbackDao;
import com.winterframework.firefrog.help.dao.IHelpKnowledgeLinkDao;
import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.entity.HelpFeedback;
import com.winterframework.firefrog.help.service.IHelpService;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: HelpServiceImpl 
* @Description: 帮助服务接口实现类 
* @author 你的名字 
* @date 2013-9-24 下午1:42:03 
*
 */
@Service("helpServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class HelpServiceImpl implements IHelpService {

	@Resource(name = "helpDaoImpl")
	protected IHelpDao helpDao;

	@Resource(name = "helpFeedbackDaoImpl")
	protected IHelpFeedbackDao feedbackDao;

	@Resource(name = "helpKnowledgeLinkDaoImpl")
	protected IHelpKnowledgeLinkDao helpKnowledgeLinkDao;

	@Resource(name = "helpServiceFactory")
	private HelpServiceFactory helpServiceFactory;

	@Override
	public void createHelp(Help help) throws Exception {
		helpServiceFactory.getHelpService(help).createHelp(help);
	}

	@Override
	public void modifyHelp(Help help) throws Exception {
		helpServiceFactory.getHelpService(help).modifyHelp(help);
	}

	@Override
	public void deleteHelp(Long id) throws Exception {
		Help help = queryHelpDetail(id);
		helpServiceFactory.getHelpService(help).deleteHelp(id);
	}

	@Override
	public Help queryHelpDetail(Long id) throws Exception {
		Help help = helpDao.selectById(id);
		return helpServiceFactory.getHelpService(help).queryHelpDetail(id);
	}

	@Override
	public void createFeedback(HelpFeedback feedback) throws Exception {
		feedbackDao.saveFeedback(feedback);
		Long helpId = feedback.getHelpId();
		//Help help = new Help(helpId);
		Help help=helpDao.selectById(helpId);
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

	/**
	* Title: queryUnsolvedFeedback
	* Description:
	* @param helpId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.help.service.IHelpService#queryUnsolvedFeedback(java.lang.Long) 
	*/
	@Override
	public List<HelpFeedback> queryUnsolvedFeedback(Long helpId) throws Exception {
		return feedbackDao.queryUnsolvedFeedback(helpId);
	}

	@Override
	public void updateBrowsenum(Help help) throws Exception {
		helpDao.updateBrowsenum(help);
	}
}
