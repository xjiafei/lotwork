package com.winterframework.firefrog.advert.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.advert.dao.IAdSpaceRelationDao;
import com.winterframework.firefrog.advert.dao.vo.AdSpaceVOConvert;
import com.winterframework.firefrog.advert.dao.vo.AdspaceRelationAdVO;
import com.winterframework.firefrog.advert.dao.vo.AdspaceRelationVO;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.firefrog.advert.entity.Advert;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
*  
*  广告与广告位对应关系处理的dao
*/
@Repository("adspaceRelationDaoImpl")
public class AdSpaceRelationDaoImpl extends BaseIbatis3Dao<AdspaceRelationVO> implements IAdSpaceRelationDao {

	/**
	* Title: save
	* Description:
	* @param vo
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdSpaceRelationDao#save(com.winterframework.firefrog.advert.dao.vo.AdspaceRelationVO) 
	*/
	@Override
	public void save(AdspaceRelationVO vo) throws Exception {
		this.insert(vo);
	}

	/**
	* Title: deleteByAd
	* Description:
	* @param adId
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdSpaceRelationDao#deleteByAd(java.lang.Long) 
	*/
	@Override
	public void deleteByAd(Long adId) throws Exception {
		this.sqlSessionTemplate.delete("deleteByAd", adId);
	}
	/**
	* Title: getAdsByAdSpaceId
	* Description:
	* @param adSpaceId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdSpaceRelationDao#getAdsByAdSpaceId(java.lang.Long) 
	*/
	@Override
	public AdSpace getAdsByAdSpaceId(Long adSpaceId) throws Exception {
		List<AdspaceRelationAdVO> relationWithAdMap  = this.sqlSessionTemplate.selectList("com.winterframework.firefrog.advert.dao.vo.AdspaceRelationVO.getRelationWithAd", adSpaceId);
		List<Advert> relations = new ArrayList<Advert>();
		for (AdspaceRelationAdVO object : relationWithAdMap) {
			Advert advert = AdSpaceVOConvert.convertAdSpaceRelation(object);
			relations.add(advert);
		}
		AdSpace adSpace = new AdSpace(adSpaceId);
		adSpace.setAdvertList(relations);
		return adSpace;
	}
	
	/**
	* Title: getAdsByAdSpaceId
	* Description:
	* @param adSpaceId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.advert.dao.IAdSpaceRelationDao#getAdsByAdSpaceId(java.lang.Long) 
	*/
	@Override
	public AdSpace getEffectAdsByAdSpaceId(Long adSpaceId) throws Exception {
		List<AdspaceRelationAdVO> relationWithAdMap  = this.sqlSessionTemplate.selectList("com.winterframework.firefrog.advert.dao.vo.AdspaceRelationVO.getEffectAdByAdspaceId", adSpaceId);
		List<Advert> relations = new ArrayList<Advert>();
		for (AdspaceRelationAdVO object : relationWithAdMap) {
			Advert advert = AdSpaceVOConvert.convertAdSpaceRelation(object);
			relations.add(advert);
		}
		AdSpace adSpace = new AdSpace(adSpaceId);
		adSpace.setAdvertList(relations);
		return adSpace;
	}

	/**
	* Title: updateUnbingAdvert
	* Description:
	* @param relationList 
	* @see com.winterframework.firefrog.advert.dao.IAdSpaceRelationDao#updateUnbingAdvert(java.util.List) 
	*/
	@Override
	public void updateUnbingAdvert(AdSpace adSpace) {
		List<Advert> advertList = adSpace.getAdvertList();
		for (Advert advert : advertList) {
			AdspaceRelationVO vo = new AdspaceRelationVO();
			vo.setIsShown(advert.getIsShown());
			vo.setOrders(advert.getReOrders());
			vo.setAdId(advert.getId());
			vo.setSpaceId(adSpace.getId());
			this.sqlSessionTemplate.update("com.winterframework.firefrog.advert.dao.vo.AdspaceRelationVO.updateUnbingAdvert", vo);
		}
	}
}
