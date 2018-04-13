package com.winterframework.firefrog.help.dao.vo;

import com.winterframework.firefrog.help.entity.*;
import com.winterframework.firefrog.help.web.dto.DTOConverter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeanUtils;

public class VOConverter {

	private static final BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();

	public static HelpHotkeywordsVO helpHotkeywordEntity2VO(HelpHotkeywords helpHotkeywords) {
		HelpHotkeywordsVO vo = new HelpHotkeywordsVO();
		vo.setId(helpHotkeywords.getId());
		vo.setNo(helpHotkeywords.getNo());
		vo.setKeyword(helpHotkeywords.getKeyword());
		return vo;
	}

	public static HelpHotkeywords vO2HelpHotkeywordsEntity(HelpHotkeywordsVO vo) {
		HelpHotkeywords entity = new HelpHotkeywords();
		entity.setId(vo.getId());
		entity.setKeyword(vo.getKeyword());
		entity.setNo(vo.getNo());
		return entity;
	}

	public static HelpKnowledgeVO helpKnowledgeEntity2VO(HelpKnowledge helpKnowledge) {

		HelpKnowledgeVO vo = new HelpKnowledgeVO();
		vo.setId(helpKnowledge.getId());
		vo.setName(helpKnowledge.getName());
		vo.setNo(helpKnowledge.getNo());
		return vo;
	}

	public static HelpVO transHelp2VO(Help help) throws Exception {
		HelpVO vo = new HelpVO();
		beanUtils.copyProperties(vo, help);
		vo.setCateId(help.getCateId() == null ? null : help.getCateId());
		vo.setCateId2(help.getCateId2() == null ? null : help.getCateId2());
		vo.setBrowsenum(help.getBrowsenum() == null ? 0 : help.getBrowsenum());
		vo.setSolvednum(help.getSolvednum() == null ? 0 : help.getSolvednum());
		vo.setUnsolvednum(help.getUnsolvednum() == null ? 0 : help.getUnsolvednum());
		if (help.getIsRecommend() != null) {
			vo.setIsRec(help.getIsRecommend().booleanValue() ? 1L : 0L);
		}else{
			vo.setIsRec(0L);
		}
		vo.setType(help instanceof LotteryHelp ? 1L : 0L);
		return vo;
	}

	public static Help transVO2Help(HelpVO vo) throws Exception {
		Help help = (vo.getType()!=null && vo.getType() == 1L) ? new LotteryHelp() : new GeneralHelp();
		beanUtils.copyProperties(help, vo);
		help.setIsRecommend(vo.getIsRec().longValue() == 1L ? true : false);
		return help;
	}

	public static HelpKnowledge vO2HelpKnowledge(HelpKnowledgeVO vo) throws Exception {
		HelpKnowledge helpKnowledge = new HelpKnowledge();
		if (vo != null) {
			helpKnowledge.setId(vo.getId());
			helpKnowledge.setName(vo.getName());
			helpKnowledge.setNo(vo.getNo());
		}
		return helpKnowledge;
	}

	public static HelpCategoryVO HelpCategory2VO(HelpCategory helpCategory) throws Exception {
		HelpCategoryVO vo = new HelpCategoryVO();
		BeanUtils.copyProperties(helpCategory, vo);
		if (helpCategory.getParent() != null) {
			vo.setPid(helpCategory.getParent().getId());
		}
		return vo;
	}

	public static HelpCategory vO2HelpCategory(HelpCategoryVO vo) throws Exception {
		HelpCategory helpCategory = new HelpCategory();
		BeanUtils.copyProperties(vo, helpCategory);
		if (vo.getPid() != null) {
			HelpCategory parent = new HelpCategory();
			parent.setId(vo.getPid());
			helpCategory.setParent(parent);
		}
		return helpCategory;
	}

	public static HelpFeedbackVO transHelpFeedback2VO(HelpFeedback feedback) throws Exception {
		HelpFeedbackVO vo = new HelpFeedbackVO();
		beanUtils.copyProperties(vo, feedback);
		vo.setIsSolved(feedback.getIsSolved().booleanValue() ? 1L : 0L);
		vo.setReasonId((long) feedback.getReasonId().ordinal());
		return vo;
	}

	public static HelpFeedback vO2HelpFeedback(HelpFeedbackVO vo) throws Exception {
		HelpFeedback helpFeedback = new HelpFeedback();
		helpFeedback.setHelpId(vo.getHelpId());
		helpFeedback.setId(vo.getId());
		helpFeedback.setReasonContent(vo.getReasonContent());
		helpFeedback.setUid(vo.getUid());
		helpFeedback.setIsSolved(vo.getIsSolved() == 1L ? true : false);
		helpFeedback.setReasonId(DTOConverter.feedbackMap.get(vo.getReasonId()));
		return helpFeedback;
	}

}
