package com.winterframework.firefrog.beginmession.dao;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.YesOrNo;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewQuestionRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginNewQuestionDao extends BaseDao<BeginNewQuestion>{

	public List<BeginNewQuestion> findByCondition(BeginNewQuestion vo) throws Exception;
	
	public Page<BeginNewQuestion> selectByPage(PageRequest<BeginNewQuestionRequest> pageRequest) throws Exception;
	
	public void deleteFlagData();
	
	public void updateDeleteFlag(Long deleteId,YesOrNo type);
}
