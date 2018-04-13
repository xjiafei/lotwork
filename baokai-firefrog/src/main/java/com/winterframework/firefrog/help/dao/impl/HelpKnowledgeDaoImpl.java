package com.winterframework.firefrog.help.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.help.dao.IHelpKnowledgeDao;
import com.winterframework.firefrog.help.dao.vo.HelpKnowledgeVO;
import com.winterframework.firefrog.help.dao.vo.VOConverter;
import com.winterframework.firefrog.help.entity.HelpKnowledge;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("helpKnowledgeDaoImpl")
public class HelpKnowledgeDaoImpl extends BaseIbatis3Dao<HelpKnowledgeVO> implements IHelpKnowledgeDao {

	@Override
	public HelpKnowledge add(HelpKnowledge helpKnowledge) throws Exception {
		HelpKnowledgeVO vo = VOConverter.helpKnowledgeEntity2VO(helpKnowledge);
		this.insert(vo);
		return VOConverter.vO2HelpKnowledge(vo);
	}

	@Override
	public int update(HelpKnowledge helpKnowledge) throws Exception {
		HelpKnowledgeVO vo = VOConverter.helpKnowledgeEntity2VO(helpKnowledge);
		return this.update(vo);
	}

	@Override
	public List<HelpKnowledge> queryAll() throws Exception {
		List<HelpKnowledgeVO> voList = this.getAll();
		List<HelpKnowledge> resultList = new ArrayList<HelpKnowledge>();
		for (HelpKnowledgeVO vo : voList) {
			HelpKnowledge helpKnowledge = VOConverter.vO2HelpKnowledge(vo);
			resultList.add(helpKnowledge);
		}
		return resultList;
	}

	@Override
	public HelpKnowledge getHelpKnowledgeById(Long id) throws Exception {
		HelpKnowledgeVO vo = this.getById(id);
		return VOConverter.vO2HelpKnowledge(vo);
	}

	@Override
	public Long getNo() throws Exception {
		return this.sqlSessionTemplate.selectOne("getHelpKnowledgeNo");
	}
}
