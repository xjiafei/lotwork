package com.winterframework.firefrog.advert.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.advert.entity.AdNotice;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.firefrog.advert.entity.AdTopic;
import com.winterframework.firefrog.advert.entity.AdTopicCate;
import com.winterframework.firefrog.advert.entity.Advert;

public class DTOConverter {

	public static Advert transAdStruc2Advert(AdStruc adStruc) {
		Advert ad = new Advert();
		if (adStruc.getSpacesIds() != null) {
			List<AdSpace> adspaces = new ArrayList<AdSpace>();
			for (Long spaceId : adStruc.getSpacesIds()) {
				AdSpace adSpaces = new AdSpace();
				adSpaces.setId(spaceId);
				adspaces.add(adSpaces);
			}
			ad.setAdspaces(adspaces);
			ad.setSpaces(Long.valueOf(adspaces.size()));
		}
		ad.setApprover(adStruc.getApprover());
		ad.setGmtEffectBegin(new Date(adStruc.getGmtEffectBegin()));
		ad.setGmtEffectEnd(new Date(adStruc.getGmtEffectEnd()));
		ad.setId(adStruc.getId());
		ad.setImgUrl(adStruc.getImgUrl());
		ad.setMemo(adStruc.getMemo());
		ad.setName(adStruc.getName());
		ad.setOrders(0l);
		ad.setRcAll(adStruc.getRcAll());
		ad.setRcCustomer(adStruc.getRcCustomer());
		ad.setRcGuest(adStruc.getRcGuest());
		ad.setRcNonVip(adStruc.getRcNonVip());
		ad.setRcOtAgent(adStruc.getRcOtAgent());
		ad.setRcTopAgent(adStruc.getRcTopAgent());
		ad.setRcVip(adStruc.getRcVip());
		if (adStruc.getStatus() != null) {
			ad.setStatuss(Advert.Status.getEnum(adStruc.getStatus().intValue()));
		}
		ad.setTargetUrl(adStruc.getTargetUrl());
		ad.setOperator(adStruc.getOperator());
		return ad;
	}

	public static AdStruc transAdvert2AdStruc(Advert ad) {
		AdStruc adStruc = new AdStruc();
		adStruc.setApprover(ad.getApprover());
		adStruc.setGmtAppr(ad.getGmtAppr() != null ? ad.getGmtAppr().getTime() : null);
		adStruc.setGmtEffectBegin(ad.getGmtEffectBegin() != null ? ad.getGmtEffectBegin().getTime() : null);
		adStruc.setGmtEffectEnd(ad.getGmtEffectEnd() != null ? ad.getGmtEffectEnd().getTime() : null);
		adStruc.setId(ad.getId());
		adStruc.setImgUrl(ad.getImgUrl());
		adStruc.setMemo(ad.getMemo());
		adStruc.setName(ad.getName());
		adStruc.setRcAll(ad.getRcAll());
		adStruc.setRcCustomer(ad.getRcCustomer());
		adStruc.setRcGuest(ad.getRcGuest());
		adStruc.setRcNonVip(ad.getRcNonVip());
		adStruc.setRcOtAgent(ad.getRcOtAgent());
		adStruc.setRcTopAgent(ad.getRcTopAgent());
		adStruc.setRcVip(ad.getRcVip());
		adStruc.setSpaces(ad.getSpaces());
		adStruc.setStatus((long) ad.getStatuss().getIndex());
		adStruc.setTargetUrl(ad.getTargetUrl());
		adStruc.setOperator(ad.getOperator());
		return adStruc;
	}

	public static AdvertSearchDto transAdQueryRequest2AdvertSearch(AdQueryRequest adReq) {
		AdvertSearchDto ad = new AdvertSearchDto();
		ad.setName(adReq.getName());
		if (adReq.getType() != null) {
			if (adReq.getType().intValue() == 1) {
				ad.setBegin(1l);
			} else if (adReq.getType().intValue() == 2) {
				ad.setUnBegin(1l);
			} else if (adReq.getType().intValue() == 3) {
				ad.setEnd(3l);
			}
		}
		ad.setStatus(adReq.getStatus());
		ad.setSpaceId(adReq.getSpaceId());
		ad.setRcAll(adReq.getRcAll());
		ad.setRcCustomer(adReq.getRcCustomer());
		ad.setRcGuest(adReq.getRcGuest());
		ad.setRcNonVip(adReq.getRcNonVip());
		ad.setRcOtAgent(adReq.getRcOtAgent());
		ad.setRcTopAgent(adReq.getRcTopAgent());
		ad.setRcVip(adReq.getRcVip());
		ad.setAllStatus(adReq.getAllStatus());
		if (adReq.getOrderById() != null) {
			if (adReq.getOrderById().intValue() == 0) {
				ad.setOrderByIdDesc(1l);
			} else if (adReq.getOrderById().intValue() == 1) {
				ad.setOrderByIdAsc(1l);
			}
		}
		if (adReq.getOrderBySpaces() != null) {
			if (adReq.getOrderBySpaces().intValue() == 0) {
				ad.setOrderBySpacesDesc(1l);
			} else if (adReq.getOrderBySpaces().intValue() == 1) {
				ad.setOrderBySpacesAsc(1l);
			}
		}
		return ad;
	}

	public static AdNoticeStruc transAdNotice2Dto(AdNotice adNotice) {
		AdNoticeStruc dto = new AdNoticeStruc();
		dto.setContent(adNotice.getContent());
		dto.setGmtEffectBegin(adNotice.getGmtEffectBegin());
		dto.setGmtEffectEnd(adNotice.getGmtEffectEnd());
		dto.setId(adNotice.getId());
		dto.setOperator(adNotice.getOperator().getAccount());
		dto.setShowPages(adNotice.getShowPages());
		dto.setStatus((long) adNotice.getStatus().getIndex());
		dto.setTitle(adNotice.getTitle());
		dto.setType((long) adNotice.getType().getIndex());
		dto.setRcAll(adNotice.getRcAll());
		dto.setRcCustomer(adNotice.getRcCustomer());
		dto.setRcNonVip(adNotice.getRcNonVip());
		dto.setRcOtAgent(adNotice.getRcOtAgent());
		dto.setRcTopAgent(adNotice.getRcTopAgent());
		dto.setRcVip(adNotice.getRcVip());
		dto.setGmtCreatede(adNotice.getGmtCreatede());
		dto.setNoticelevel(adNotice.getNoticelevel());
		if (adNotice.getApprover() != null) {
			dto.setApprover(adNotice.getApprover().getAccount());
		}

		dto.setMemo(adNotice.getMemo());
		return dto;
	}

	public static AdNotice transDto2AdNotice(AdNoticeStruc dto) {
		AdNotice notice = new AdNotice();
		notice.setContent(dto.getContent());
		notice.setGmtEffectBegin(dto.getGmtEffectBegin());
		notice.setGmtEffectEnd(dto.getGmtEffectEnd());
		notice.setId(dto.getId());
		AclUser user = new AclUser();
		user.setAccount(dto.getOperator());
		notice.setOperator(user);
		AclUser approver = new AclUser();
		approver.setAccount(dto.getApprover());
		notice.setApprover(approver);
		notice.setShowPages(dto.getShowPages());
		if (dto.getStatus() != null) {
			notice.setStatus(AdNotice.Status.getEnum(dto.getStatus().intValue()));
		}
		notice.setTitle(dto.getTitle());
		if (dto.getType() != null) {
			notice.setType(AdNotice.Type.getEnum(dto.getType().intValue()));
		}
		notice.setRcAll(0L);
		notice.setRcCustomer(0L);
		notice.setRcNonVip(0L);
		notice.setRcOtAgent(0L);
		notice.setRcTopAgent(0L);
		notice.setRcVip(0L);
		notice.setMemo(dto.getMemo());
		notice.setNoticelevel(dto.getNoticelevel());
		if (dto.getGroups() != null) {
			for (String str : dto.getGroups().split(",")) {
				switch (Integer.parseInt(str)) {
				case 1:
					notice.setRcAll(1L);
					break;
				case 2:
					notice.setRcTopAgent(1L);
					break;
				case 3:
					notice.setRcOtAgent(1L);
					break;
				case 4:
					notice.setRcCustomer(1L);
					break;
				case 5:
					notice.setRcVip(1L);
					break;
				case 6:
					notice.setRcNonVip(1L);
					break;
				}
			}
		}
		return notice;
	}

	public static AdTopic transDto2AdTopic(AdTopicStruc dto) {
		AdTopic topic = new AdTopic();
		topic.setId(dto.getId());
		topic.setTitle(dto.getTitle());
		topic.setUrls(dto.getUrls());
		AdTopicCate cate = new AdTopicCate();
		cate.setId(dto.getCateId());
		cate.setName(dto.getCateName());
		topic.setCate(cate);
		AclUser operator = new AclUser();
		operator.setAccount(dto.getOperator());
		topic.setOperator(operator);
		return topic;
	}

	public static AdTopicStruc transAdTopic2Dto(AdTopic topic) {
		AdTopicStruc dto = new AdTopicStruc();
		dto.setCateId(topic.getCate().getId());
		dto.setId(topic.getId());
		dto.setCateName(topic.getCate().getName());
		dto.setOperator(topic.getOperator().getAccount());
		dto.setTitle(topic.getTitle());
		dto.setUrls(topic.getUrls());
		dto.setGmtCreated(topic.getGmtCreated());
		return dto;
	}
}
