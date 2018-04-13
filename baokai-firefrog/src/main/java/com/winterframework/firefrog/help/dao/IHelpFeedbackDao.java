package com.winterframework.firefrog.help.dao;

import java.util.List;

import com.winterframework.firefrog.help.entity.HelpFeedback;

public interface IHelpFeedbackDao {

	public void saveFeedback(HelpFeedback feedback) throws Exception;
	
	public List<HelpFeedback> queryUnsolvedFeedback(Long helpId) throws Exception;

}
