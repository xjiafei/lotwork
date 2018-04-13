package com.winterframework.adbox.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winterframework.adbox.dao.IAdDeviceResourceDao;
import com.winterframework.adbox.dao.base.BaseServiceImpl;
import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.entity.AdDeviceResource;
import com.winterframework.adbox.service.IAdDeviceResourceService;


@Service("adDeviceResourceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class AdDeviceResourceServiceImpl  extends BaseServiceImpl<IAdDeviceResourceDao,AdDeviceResource> implements IAdDeviceResourceService {
	@Resource(name="adDeviceResourceDaoImpl")
	private IAdDeviceResourceDao dao;
	@Override
	protected IAdDeviceResourceDao getEntityDao() {
		return dao;
	}
	@Override
	public List<AdDeviceResource> getAdDeviceResourceByDeviceId(Long deviceId,Integer status)
			throws Exception {
		AdDeviceResource entity = new AdDeviceResource();
		entity.setDeviceId(deviceId);
		entity.setStatus(status);
		Context ctx = new Context();
		ctx.set("userId", -1);
		return this.selectListObjByAttribute(ctx, entity);
	}
}
