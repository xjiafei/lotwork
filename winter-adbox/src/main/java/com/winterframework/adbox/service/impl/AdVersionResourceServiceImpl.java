package com.winterframework.adbox.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.adbox.dao.IAdVersionResourceDao;
import com.winterframework.adbox.dao.base.BaseServiceImpl;

import com.winterframework.adbox.entity.AdVersionResource;

import com.winterframework.adbox.service.IAdVersionResourceService;


@Service("adVersionResourceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class AdVersionResourceServiceImpl  extends BaseServiceImpl<IAdVersionResourceDao,AdVersionResource> implements IAdVersionResourceService {
	@Resource(name="adVersionResourceDaoImpl")
	private IAdVersionResourceDao dao;
	@Override
	protected IAdVersionResourceDao getEntityDao() {
		return dao;
	}
	@Override
	public Long getLastVersion(Long userId) throws Exception {
		return dao.getLastVersion(userId);
	}
	@Override
	public AdVersionResource getAdVersionResourceByResId(String resourceId,Long userId)
			throws Exception {
		AdVersionResource adVersionResource = new AdVersionResource();
		adVersionResource.setResourceId(resourceId);
		adVersionResource.setUserId(userId);
		return this.selectOneObjByAttribute(null, adVersionResource);
	}
	@Override
	public AdVersionResource getByUserIdAndVersion(Long userId, Long version)
			throws Exception {
		AdVersionResource adVersionResource = new AdVersionResource();
		adVersionResource.setUserId(userId);
		adVersionResource.setVersion(version);
		return this.selectOneObjByAttribute(null, adVersionResource);
	}
	
	
}
