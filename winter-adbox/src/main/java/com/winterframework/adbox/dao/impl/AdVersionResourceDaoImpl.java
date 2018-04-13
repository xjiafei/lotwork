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



import org.springframework.stereotype.Repository;


import com.winterframework.adbox.dao.IAdVersionResourceDao;
import com.winterframework.adbox.dao.base.BaseDaoImpl;


import com.winterframework.adbox.entity.AdVersionResource;



@Repository("adVersionResourceDaoImpl")
public class AdVersionResourceDaoImpl<E extends AdVersionResource> extends BaseDaoImpl<AdVersionResource> implements IAdVersionResourceDao{
	@Override
	public Long getLastVersion(Long userId) throws Exception {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getLastVersion"),userId);
	}
	
}
