package com.winterframework.firefrog.advert.dao.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.advert.entity.AdNotice;
import com.winterframework.firefrog.advert.entity.AdTopic;
import com.winterframework.firefrog.advert.entity.AdTopicCate;
import com.winterframework.firefrog.advert.entity.Advert;
import com.winterframework.firefrog.common.util.DataConverterUtil;

public class VOConverter {
	public static AdVO adEntity2Vo(Advert ad) {
		Date begin = ad.getGmtEffectBegin();
		Date end = ad.getGmtEffectEnd();
		Date approver = ad.getGmtAppr();
		AdVO adVo = new AdVO();
		ad.setGmtEffectBegin(null);
		ad.setGmtEffectEnd(null);
		ad.setGmtAppr(null);
		BeanUtils.copyProperties(ad, adVo);
		adVo.setGmtEffectBegin(begin);
		adVo.setGmtEffectEnd(end);
		adVo.setGmtAppr(approver);
		if (ad.getStatuss() != null) {
			adVo.setStatus((long) ad.getStatuss().getIndex());
		}
		return adVo;
	}

	public static Advert adVo2Entity(AdVO adVo) {
		Advert ad = new Advert();
		Date begin = adVo.getGmtEffectBegin();
		Date end = adVo.getGmtEffectEnd();
		Date approver = ad.getGmtAppr();
		adVo.setGmtEffectBegin(null);
		adVo.setGmtEffectEnd(null);
		ad.setGmtAppr(null);
		BeanUtils.copyProperties(adVo, ad);
		ad.setStatuss(Advert.Status.getEnum(adVo.getStatus().intValue()));
		ad.setGmtEffectBegin(begin);
		ad.setGmtEffectEnd(end);
		adVo.setGmtAppr(approver);
		return ad;
	}

	public static AdNoticeVO transAdNotice2VO(AdNotice notice) {
		AdNoticeVO vo = new AdNoticeVO();
		vo.setContent(notice.getContent());
		vo.setGmtEffectBegin(notice.getGmtEffectBegin());
		vo.setGmtEffectEnd(notice.getGmtEffectEnd());
		vo.setId(notice.getId());
		if (notice.getOperator() != null) {
			vo.setOperator(notice.getOperator().getAccount());
		}
		vo.setShowPages(notice.getShowPages());
		if (notice.getStatus() != null) {
			vo.setStatus((long) notice.getStatus().getIndex());
		}
		vo.setTitle(notice.getTitle());
		if (notice.getType() != null) {
			vo.setType((long) notice.getType().getIndex());
		}
		vo.setRcAll(notice.getRcAll());
		vo.setRcCustomer(notice.getRcCustomer());
		vo.setRcNonVip(notice.getRcNonVip());
		vo.setRcOtAgent(notice.getRcOtAgent());
		vo.setRcTopAgent(notice.getRcTopAgent());
		vo.setRcVip(notice.getRcVip());
		vo.setMemo(notice.getMemo());
		vo.setNoticeLevel(notice.getNoticelevel());
		if (vo.getRcAll() != null && vo.getRcCustomer() != null && vo.getRcNonVip() != null
				&& vo.getRcOtAgent() != null && vo.getRcTopAgent() != null && vo.getRcVip() != null
				&& vo.getRcAll() == 0L && vo.getRcCustomer() == 0L && vo.getRcNonVip() == 0L && vo.getRcOtAgent() == 0L
				&& vo.getRcTopAgent() == 0L && vo.getRcVip() == 0L) {
			vo.setRcAll(null);
			vo.setRcCustomer(null);
			vo.setRcNonVip(null);
			vo.setRcOtAgent(null);
			vo.setRcTopAgent(null);
			vo.setRcVip(null);
		}
		if (notice.getApprover() != null) {
			vo.setApprover(notice.getApprover().getAccount());
		}
		return vo;
	}

	public static AdNotice transVO2AdNotice(AdNoticeVO vo) {
		AdNotice notice = new AdNotice();
		notice.setContent(vo.getContent());
		notice.setGmtEffectBegin(vo.getGmtEffectBegin());
		notice.setGmtEffectEnd(vo.getGmtEffectEnd());
		notice.setId(vo.getId());
		AclUser user = new AclUser();
		user.setAccount(vo.getOperator());
		notice.setOperator(user);
		notice.setShowPages(vo.getShowPages());
		notice.setStatus(AdNotice.Status.getEnum(vo.getStatus().intValue()));
		notice.setTitle(vo.getTitle());
		notice.setType(AdNotice.Type.getEnum(vo.getType().intValue()));
		notice.setRcAll(vo.getRcAll());
		notice.setRcCustomer(vo.getRcCustomer());
		notice.setRcNonVip(vo.getRcNonVip());
		notice.setRcOtAgent(vo.getRcOtAgent());
		notice.setRcTopAgent(vo.getRcTopAgent());
		notice.setRcVip(vo.getRcVip());
		notice.setMemo(vo.getMemo());
		AclUser approver = new AclUser();
		approver.setAccount(vo.getApprover());
		notice.setApprover(approver);
		notice.setGmtCreatede(vo.getGmtCreated());
		notice.setNoticelevel(vo.getNoticeLevel());
		return notice;
	}

	public static AdTopicVO transAdTopic2VO(AdTopic topic) {
		AdTopicVO vo = new AdTopicVO();
		vo.setCateId(topic.getCate().getId());
		vo.setId(topic.getId());
		vo.setOperator(topic.getOperator().getAccount());
		vo.setTitle(topic.getTitle());
		vo.setUrls(DataConverterUtil.convertObject2Json(topic.getUrls()));
		return vo;
	}

	public static AdTopic transVO2AdTopic(AdTopicVO vo) {
		AdTopic topic = new AdTopic();
		topic.setId(vo.getId());
		topic.setTitle(vo.getTitle());
		if (vo.getUrls() != null) {
			topic.setUrls((List<String>) DataConverterUtil.convertJson2Object(vo.getUrls(), ArrayList.class));
		}
		AdTopicCate cate = new AdTopicCate();
		cate.setId(vo.getCateId());
		AclUser operator = new AclUser();
		operator.setAccount(vo.getOperator());
		topic.setOperator(operator);
		topic.setCate(cate);
		topic.setGmtCreated(vo.getGmtCreated());
		return topic;
	}
}
