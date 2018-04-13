package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginNewQuestionDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.YesOrNo;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewQuestionRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository
public class BeginNewQuestionDaoImpl extends BaseIbatis3Dao<BeginNewQuestion> implements
		BeginNewQuestionDao {

	@Override
	public List<BeginNewQuestion> findByCondition(BeginNewQuestion vo)
			throws Exception {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"), vo);
	}

	@Override
	public Page<BeginNewQuestion> selectByPage(
			PageRequest<BeginNewQuestionRequest> pageRequest) throws Exception {
		pageRequest.setSortColumns("ID DESC");
		return this.getAllByPage(pageRequest);
	}

	@Override
	public void deleteFlagData() {
		this.sqlSessionTemplate.update(this.getQueryPath("deleteFlagData"));
	}

	@Override
	public void updateDeleteFlag(Long deleteId,YesOrNo type) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("deleteId", deleteId);
		map.put("deleteFlag", type.getValue());
		this.sqlSessionTemplate.update(this.getQueryPath("updateDeleteFlag"),map);
	}

}
