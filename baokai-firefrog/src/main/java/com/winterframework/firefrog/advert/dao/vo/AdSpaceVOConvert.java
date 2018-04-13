package com.winterframework.firefrog.advert.dao.vo;

import org.springframework.beans.BeanUtils;

import com.winterframework.firefrog.advert.entity.AdPage;
import com.winterframework.firefrog.advert.entity.AdParam;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.firefrog.advert.entity.Advert;

public class AdSpaceVOConvert {

	public static AdPage convertAdPage(AdPageVO vo) {
		return null;
	}

	public static AdParam convertAdParam(AdParamVO vo) {
		AdParam adparam = new AdParam();
		BeanUtils.copyProperties(vo, adparam);
		return adparam;
	}

	public static AdSpaceVO convertAdSpaceTOVo(AdSpace adSpace) {
		AdSpaceVO vo = new AdSpaceVO();
		BeanUtils.copyProperties(adSpace, vo);
		if (adSpace.getAdParam() != null) {
			vo.setAdParamId(adSpace.getAdParam().getId());
		}
		if(adSpace.getAdPage()!=null){
			vo.setPageId(adSpace.getAdPage().getId());
		}
		return vo;
	}

	public static AdSpace convertAdSpace(AdSpaceVO vo) {
		AdSpace adSpace = new AdSpace();
		BeanUtils.copyProperties(vo, adSpace);
		adSpace.setAdPage(new AdPage(vo.getPageId()));
		if (vo.getAdParamId() != null) {
			AdParam adParam = new AdParam();
			adParam.setId(vo.getAdParamId());
			adSpace.setAdParam(adParam);
		}
		return adSpace;
	}

	public static Advert convertAdSpaceRelation(AdspaceRelationAdVO relationAdVO) {
		AdSpace relation = new AdSpace(relationAdVO.getAdSpaceId());
		relation.setId(relationAdVO.getId());
		
		Advert advert = new Advert();
		advert.setName(relationAdVO.getAdName());
		advert.setImgUrl(relationAdVO.getAdImgUrl());
		advert.setGmtEffectBegin(relationAdVO.getAdGmtEffectBegin());
		advert.setGmtEffectEnd(relationAdVO.getAdGmtEffectEnd());
		advert.setTargetUrl(relationAdVO.getAdTargetUrl());
		advert.setStatuss(Advert.Status.getEnum(relationAdVO.getAdStatus().intValue()));
		advert.setId(relationAdVO.getAdvertId());
		advert.setRcAll(relationAdVO.getRcAll());
		advert.setRcGuest(relationAdVO.getRcGuest());
		advert.setRcTopAgent(relationAdVO.getRcTopAgent());
		advert.setRcOtAgent(relationAdVO.getRcOtAgent());
		advert.setRcVip(relationAdVO.getRcVip());
		advert.setRcNonVip(relationAdVO.getRcNonVip());
		advert.setRcCustomer(relationAdVO.getRcCustomer());
		advert.setReOrders(relationAdVO.getOrders());
		advert.setIsShown(relationAdVO.getIsShown());
		return advert;
	}
}
