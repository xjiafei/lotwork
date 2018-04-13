 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.adbox.dao.impl;



/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */



import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.winterframework.adbox.dao.IAdPublishResourceDao;
import com.winterframework.adbox.dao.base.BaseDaoImpl;


import com.winterframework.adbox.entity.AdPublishDevice;



@Repository("adPublishResourceDaoImpl")
public class AdPublishResourceDaoImpl<E extends AdPublishDevice> extends BaseDaoImpl<AdPublishDevice> implements IAdPublishResourceDao{

	@Override
	public AdPublishDevice getLastAdPublishDevice(Long deviceId, Long version)
			throws Exception {
		Map map = new HashMap();
		map.put("deviceId", deviceId);
		map.put("version", version);
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getLastAdPublishDevice"), map);
	}

}
