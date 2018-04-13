package com.winterframework.firefrog.help.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.help.dao.IHelpFeedbackDao;
import com.winterframework.firefrog.help.dao.vo.HelpFeedbackVO;
import com.winterframework.firefrog.help.dao.vo.VOConverter;
import com.winterframework.firefrog.help.entity.HelpFeedback;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("helpFeedbackDaoImpl")
public class HelpFeedbackDaoImpl extends BaseIbatis3Dao<HelpFeedbackVO> implements IHelpFeedbackDao {

	@Override
	public void saveFeedback(HelpFeedback feedback) throws Exception {
		HelpFeedbackVO vo = VOConverter.transHelpFeedback2VO(feedback);
		this.insert(vo);
	}

	/**
	* Title: queryUnsolvedFeedback
	* Description:
	* @param helpId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.help.dao.IHelpFeedbackDao#queryUnsolvedFeedback(java.lang.Long) 
	*/
	@Override
	public List<HelpFeedback> queryUnsolvedFeedback(Long helpId) throws Exception {
		HelpFeedbackVO helpFeedbackVO = new HelpFeedbackVO();
		helpFeedbackVO.setHelpId(helpId);
		helpFeedbackVO.setIsSolved(0L);
		List<HelpFeedbackVO> helpFeedbacks =  this.sqlSessionTemplate.selectList("getHelpFeedbacks", helpFeedbackVO);
		List<HelpFeedback> list = new ArrayList<HelpFeedback>();
		for(HelpFeedbackVO vo :helpFeedbacks){
			list.add(VOConverter.vO2HelpFeedback(vo));
		}
		return list;
	}
}
