package com.winterframework.firefrog.help.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.help.dao.IHelpKnowledgeLinkDao;
import com.winterframework.firefrog.help.dao.vo.HelpKnowledgeLinkVO;
import com.winterframework.firefrog.help.entity.HelpKnowledgeLink;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("helpKnowledgeLinkDaoImpl")
public class HelpKnowledgeLinkDaoImpl extends BaseIbatis3Dao<HelpKnowledgeLinkVO> implements IHelpKnowledgeLinkDao {

	private final static BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();

	@Override
	public void saveLink(HelpKnowledgeLink link) throws Exception {
		HelpKnowledgeLinkVO vo = new HelpKnowledgeLinkVO();
		beanUtils.copyProperties(vo, link);
		this.insert(vo);
	}

	@Override
	public void updateLink(HelpKnowledgeLink link) throws Exception {
		HelpKnowledgeLinkVO vo = new HelpKnowledgeLinkVO();
		beanUtils.copyProperties(vo, link);
		sqlSessionTemplate.update("updateByHelpId", vo);
	}

	@Override
	public void deleteLink(Long id) throws Exception {
		this.delete(id);
	}

	@Override
	public List<HelpKnowledgeLink> selectByHelpId(Long id) throws Exception {
		HelpKnowledgeLinkVO entity = new HelpKnowledgeLinkVO();
		entity.setHelpId(id);
		List<HelpKnowledgeLinkVO> voList = this.sqlSessionTemplate.selectList("getByHelpId", id);
		if (voList == null || voList.isEmpty()) {
			return null;
		}
		HelpKnowledgeLink helpKnowledgeLink = null;
		List<HelpKnowledgeLink> list = new ArrayList<HelpKnowledgeLink>();
		for (HelpKnowledgeLinkVO vo : voList) {
			helpKnowledgeLink = new HelpKnowledgeLink();
			beanUtils.copyProperties(helpKnowledgeLink, vo);
			list.add(helpKnowledgeLink);
		}
		return list;
	}

	@Override
	public void saveLink(List<HelpKnowledgeLink> links, Long helpId) throws Exception {
		List<HelpKnowledgeLinkVO> list = new ArrayList<HelpKnowledgeLinkVO>();
		HelpKnowledgeLinkVO vo = null;
		for (HelpKnowledgeLink link : links) {
			vo = new HelpKnowledgeLinkVO();
			beanUtils.copyProperties(vo, link);
			vo.setHelpId(helpId);
			list.add(vo);
		}
		this.insert(list);
	}

	@Override
	public void deleteLinkByKnowledgeId(Long kid) throws Exception {
		this.sqlSessionTemplate.delete(this.getQueryPath("deleteByKnowledge"), kid);
	}
}
