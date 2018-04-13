package com.winterframework.firefrog.advert.web.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.winterframework.firefrog.advert.entity.AdPage;
import com.winterframework.firefrog.advert.entity.AdParam;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.firefrog.advert.entity.Advert;

public class AdDTOConverter {

	/** 
	 * 将广告位对象装换位广告位结构体对象
	* @Title: transAdSpaceToStruc 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param adSpace
	* @return
	*/
	public static AdSpaceStruc transAdSpaceToStruc(AdSpace adSpace) {
		AdSpaceStruc adSpaceStruc = new AdSpaceStruc();
		BeanUtils.copyProperties(adSpace, adSpaceStruc);
		if (adSpace.getAdParam() != null) {
			adSpaceStruc.setParamId(adSpace.getAdParam().getId());
			adSpaceStruc.setParamName(adSpace.getAdParam().getName());
		}
		if (adSpace.getAdPage() != null) {
			adSpaceStruc.setPageId(adSpace.getAdPage().getId());
		}
		return adSpaceStruc;
	}

	public static AdspaceRelationStruc transRalationToStruc(Advert advert, Long spaceId) {
		AdspaceRelationStruc struc = new AdspaceRelationStruc();
		//copy基本属性
		struc.setIsShown(advert.getIsShown());
		struc.setOrders(advert.getReOrders());
		//copy广告中的属性
		struc.setAdName(advert.getName());
		struc.setAdImgUrl(advert.getImgUrl());
		struc.setAdGmtEffectBegin(advert.getGmtEffectBegin());
		struc.setAdGmtEffectEnd(advert.getGmtEffectEnd());
		struc.setAdTargetUrl(advert.getTargetUrl());
		struc.setAdStatus((long) advert.getStatuss().getIndex());
		struc.setAdvertId(advert.getId());
		struc.setRcAll(advert.getRcAll());
		struc.setRcGuest(advert.getRcGuest());
		struc.setRcTopAgent(advert.getRcTopAgent());
		struc.setRcOtAgent(advert.getRcOtAgent());
		struc.setRcVip(advert.getRcVip());
		struc.setRcNonVip(advert.getRcNonVip());
		struc.setRcCustomer(advert.getRcCustomer());
		//copy广告位中的属性
		struc.setAdSpaceId(spaceId);
		return struc;
	}

	public static AdParamStru transAdParamToStruc(AdParam adSpace) {
		AdParamStru stru = new AdParamStru();
		BeanUtils.copyProperties(adSpace, stru);
		return stru;
	}

	public static AdSpace transAdStructToAdSpace(AdSpaceStruc response) {
		AdSpace adSpace = new AdSpace();
		BeanUtils.copyProperties(response, adSpace);
		if (response.getParamId() != null) {
			adSpace.setAdParam(new AdParam(response.getParamId()));
		}
		adSpace.setAdPage(new AdPage(response.getPageId()));
		return adSpace;
	}

	public static Advert transAdRelationStructToAdRelation(AdUnbingStruc adspaceRelationStruc) {
		Advert advert = new Advert();
		BeanUtils.copyProperties(adspaceRelationStruc, advert);
		advert.setReOrders(adspaceRelationStruc.getOrders());
		if (adspaceRelationStruc.getAdvertId() != null) {
			advert.setId(adspaceRelationStruc.getAdvertId());
		}
		return advert;
	}

	public static List<AdvertStruc> transAdvertToAdvertStruc(List<Advert> advertList) {
		List<AdvertStruc> list = new ArrayList<AdvertStruc>();
		for (Advert advert : advertList) {
			AdvertStruc advertStruc = new AdvertStruc();
			advertStruc.setId(advert.getId());
			advertStruc.setTitle(advert.getName());
			advertStruc.setSrc(advert.getImgUrl());
			advertStruc.setLink(advert.getTargetUrl());
			list.add(advertStruc);
		}
		return list;
	}
}
