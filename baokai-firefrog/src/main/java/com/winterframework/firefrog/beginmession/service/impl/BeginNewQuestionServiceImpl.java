package com.winterframework.firefrog.beginmession.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.dao.BeginNewQuestionDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.YesOrNo;
import com.winterframework.firefrog.beginmession.service.BeginNewQuestionService;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewQuestionRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service
@Transactional
public class BeginNewQuestionServiceImpl implements BeginNewQuestionService{

	private final int  pageRowSize=20;
	
	@Autowired
	private BeginNewQuestionDao beginNewQuestionDao;
	
	@Override
	public void saveQuestion(List<BeginNewQuestion> vo) throws Exception {
		//儲存時將flag=Y的刪除
		beginNewQuestionDao.deleteFlagData();
		for(BeginNewQuestion question:vo){
			if(question.getId()!=null){
				beginNewQuestionDao.update(question);
			}else{
				beginNewQuestionDao.insert(question);
			}
		}
	}

	@Override
	public Page<BeginNewQuestion> getAllByPage(
			PageRequest<BeginNewQuestionRequest> pageRequest) throws Exception {
		//查詢時將flag壓掉
		beginNewQuestionDao.updateDeleteFlag(null, YesOrNo.NO);
		Page<BeginNewQuestion> page= beginNewQuestionDao.selectByPage(pageRequest);
		int totalCount = page.getTotalCount();
		int startNo = totalCount-(page.getPageNo()-1)*pageRowSize;
		if(page.getResult()!=null){
			List<BeginNewQuestion> lists = Lists.newArrayList();
			for(BeginNewQuestion que:page.getResult()){
				que.setIndex(startNo--);
				lists.add(que);
			}
			page.setResult(lists);
		}
		
		return page;
	}

	@Override
	public void updateDeleteFlag(Long deleteId) {
		//畫面點擊刪除時,將flag壓成y
		beginNewQuestionDao.updateDeleteFlag(deleteId, YesOrNo.YES);
	}

}
