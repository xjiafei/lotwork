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
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.adbox.dao.IAdDeviceDao;
import com.winterframework.adbox.dao.base.BaseDaoImpl;
import com.winterframework.adbox.dao.base.Context;
import com.winterframework.adbox.entity.AdDevice;



@Repository("adDeviceDaoImpl")
public class AdDeviceDaoImpl<E extends AdDevice> extends BaseDaoImpl<AdDevice> implements IAdDeviceDao{

	@Override
	public List<AdDevice> getAdDeviceList(Context ctx, Long userId,
			String code, String sort, int startNo, int endNo) throws Exception {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("code", code);
		map.put("startNo", startNo);
		map.put("endNo", endNo);
		map.put("sort", sort);
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getAdDeviceList"), map);
	}

}
