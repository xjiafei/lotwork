package com.winterframework.adbox.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.adbox.dao.IAdResourceDao;
import com.winterframework.adbox.dao.base.BaseServiceImpl;
import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.entity.AdResource;
import com.winterframework.adbox.service.IAdResourceService;


@Service("adResourceServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class AdResourceServiceImpl  extends BaseServiceImpl<IAdResourceDao,AdResource> implements IAdResourceService {
	@Resource(name="adResourceDaoImpl")
	private IAdResourceDao dao;
	@Override
	protected IAdResourceDao getEntityDao() {
		return dao;
	}
	@Override
	public List<AdResource> getAdResourceByPath(Context ctx,String path,Long userId) throws Exception {
		AdResource adResource = new AdResource();
		adResource.setFilePath(path);
		adResource.setUserId(userId);
		return this.selectListObjByAttribute(ctx, adResource);
	}
	
	
}
