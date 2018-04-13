package com.winterframework.firefrog.help.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.help.dao.IHelpKnowledgeDao;
import com.winterframework.firefrog.help.dao.IHelpKnowledgeLinkDao;
import com.winterframework.firefrog.help.entity.HelpKnowledge;
import com.winterframework.firefrog.help.service.IHelpKnowledgeService;

@Service("helpKnowledgeServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class HelpKnowledgeServiceImpl implements IHelpKnowledgeService {

	@Resource(name = "helpKnowledgeDaoImpl")
	private IHelpKnowledgeDao helpKnowledgeDaoImpl;

	@Resource(name = "helpKnowledgeLinkDaoImpl")
	private IHelpKnowledgeLinkDao helpKnowledgeLinkDaoImpl;

	@Override
	public List<HelpKnowledge> queryAllHelpKnowledge() throws Exception {
		return helpKnowledgeDaoImpl.queryAll();
	}

	@Override
	public int updateHelpKnowledge(HelpKnowledge helpKnowledge) throws Exception {
		return helpKnowledgeDaoImpl.update(helpKnowledge);
	}

	@Override
	public void deleteHelpKnowledge(Long id) throws Exception {
		helpKnowledgeLinkDaoImpl.deleteLinkByKnowledgeId(id);
		helpKnowledgeDaoImpl.delete(id);
	}

	@Override
	public HelpKnowledge addHelpKnowledge(HelpKnowledge helpKnowledge) throws Exception {
		helpKnowledge.setNo(helpKnowledgeDaoImpl.getNo());
		return helpKnowledgeDaoImpl.add(helpKnowledge);
	}
}
