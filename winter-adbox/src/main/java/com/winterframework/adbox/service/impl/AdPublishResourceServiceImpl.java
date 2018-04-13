package com.winterframework.adbox.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.adbox.dao.IAdPublishResourceDao;
import com.winterframework.adbox.dao.base.BaseServiceImpl;

import com.winterframework.adbox.entity.AdPublishDevice;


import com.winterframework.adbox.service.IAdPublishResourceService;



@Service("adPublishResourceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class AdPublishResourceServiceImpl  extends BaseServiceImpl<IAdPublishResourceDao,AdPublishDevice> implements IAdPublishResourceService {
	@Resource(name="adPublishResourceDaoImpl")
	private IAdPublishResourceDao dao;
	@Override
	protected IAdPublishResourceDao getEntityDao() {
		return dao;
	}
	@Override
	public AdPublishDevice getLastAdPublishDevice(Long deviceId, Long version)
			throws Exception {
		return dao.getLastAdPublishDevice(deviceId, version);
	}
	
}
