package com.winterframework.firefrog.help.web.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import com.winterframework.firefrog.help.entity.GeneralHelp;
import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.entity.HelpFeedback;
import com.winterframework.firefrog.help.entity.HelpKnowledgeLink;
import com.winterframework.firefrog.help.entity.LotteryHelp;

public class DTOConverter {

	private static final BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();

	public static final Map<Long, HelpFeedback.Reason> feedbackMap = new HashMap<Long, HelpFeedback.Reason>();

	static {
		feedbackMap.put(0L, HelpFeedback.Reason.few);
		feedbackMap.put(1L, HelpFeedback.Reason.many);
		feedbackMap.put(2L, HelpFeedback.Reason.complex);
		feedbackMap.put(3L, HelpFeedback.Reason.other);
	}

	public static Help transHelpStruc2Help(HelpStruc helpStruc, Help oriHelp) throws Exception {
		Help help = null;
		if (oriHelp == null) {
			help = helpStruc.getLotteryContentStruc() == null ? new GeneralHelp() : new LotteryHelp();
		} else {
			help = oriHelp instanceof GeneralHelp ? new GeneralHelp() : new LotteryHelp();			
		}
		//beanUtils.copyProperties(help, helpStruc);
		BeanUtils.copyProperties(help, helpStruc);
		//重新设置属性
		help.setCateId(helpStruc.getCateId() == null ? null : helpStruc.getCateId());
		help.setCateId2(helpStruc.getCateId2() == null ? null : helpStruc.getCateId2());
		help.setBrowsenum(helpStruc.getBrowsenum() == null ? null : helpStruc.getBrowsenum());
		help.setSolvednum(helpStruc.getSolvednum() == null ? null : helpStruc.getSolvednum());
		help.setUnsolvednum(helpStruc.getUnsolvednum() == null ? null : helpStruc.getUnsolvednum());
		if (oriHelp != null && oriHelp instanceof LotteryHelp) {
			help.setIsRecommend(oriHelp.getIsRecommend());
		} 
		if (helpStruc.getIsRec() != null) {
			help.setIsRecommend(helpStruc.getIsRec().intValue() == 1 ? true : false);
		}
		
		if (help instanceof LotteryHelp && helpStruc.getLotteryContentStruc() != null) {
			List<HelpKnowledgeLink> links = ((LotteryHelp) help).getLinkList();
			HelpKnowledgeLink link = null;
			for (LotteryContentStruc content : helpStruc.getLotteryContentStruc()) {
				link = new HelpKnowledgeLink();
				link.setKid(content.getId());
				link.setContent(content.getContent());
				link.setHelpId(helpStruc.getId());
				links.add(link);
			}
		}
		return help;
	}

	public static HelpStruc transHelp2HelpStruc(Help help) throws Exception {
		HelpStruc helpStruc = new HelpStruc();
		//beanUtils.copyProperties(helpStruc, help);
		BeanUtils.copyProperties(helpStruc, help);
		helpStruc.setIsRec(help.getIsRecommend().booleanValue() ? 1 : 0);
		helpStruc.setType(help instanceof LotteryHelp ? 1 : 0);
		if (help instanceof LotteryHelp) {
			List<LotteryContentStruc> lotteryContentStrucList = new ArrayList<LotteryContentStruc>();
			LotteryContentStruc lotteryContentStruc = null;
			if(((LotteryHelp) help).getLinkList()!=null)
			for (HelpKnowledgeLink link : ((LotteryHelp) help).getLinkList()) {
				lotteryContentStruc = new LotteryContentStruc();
				lotteryContentStruc.setId(link.getKid());
				lotteryContentStruc.setContent(link.getContent());
				lotteryContentStrucList.add(lotteryContentStruc);
			}
			helpStruc.setLotteryContentStruc(lotteryContentStrucList);
		}
		return helpStruc;
	}

	public static HelpFeedback transFeedReq2HelpFeedback(HelpFeedBackRequest feedbackReq, Long uId) throws Exception {
		HelpFeedback feedback = new HelpFeedback();
		feedback.setHelpId(feedbackReq.getHelpId());
		feedback.setReasonContent(feedbackReq.getReason());
		feedback.setIsSolved(feedbackReq.getIsSolved().longValue() == 1L ? true : false);
		feedback.setReasonId(feedbackMap.get(feedbackReq.getReasonId()));
		feedback.setUid(uId);
		return feedback;
	}
}
