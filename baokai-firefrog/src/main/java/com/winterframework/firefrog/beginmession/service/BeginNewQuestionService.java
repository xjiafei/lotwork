package com.winterframework.firefrog.beginmession.service;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewQuestionRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

public interface BeginNewQuestionService {

	/**
	 * insert begine mession questition data
	 * @param vo
	 * @throws Exception
	 */
	public void saveQuestion(List<BeginNewQuestion> vo) throws Exception;
	
	public Page<BeginNewQuestion> getAllByPage(PageRequest<BeginNewQuestionRequest> pageRequest) throws Exception;
	
	void updateDeleteFlag(Long deleteId);
}
