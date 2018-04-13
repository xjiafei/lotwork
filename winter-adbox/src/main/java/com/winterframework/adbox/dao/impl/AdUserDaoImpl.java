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
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.adbox.dao.IAdUserDao;
import com.winterframework.adbox.dao.base.BaseDaoImpl;
import com.winterframework.adbox.entity.AdUser;


@Repository("adUserDaoImpl")
public class AdUserDaoImpl<E extends AdUser> extends BaseDaoImpl<AdUser> implements IAdUserDao{

	@Override
	public List<AdUser> getUserList(String userName) throws Exception {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getUserList"), userName);
	}
}
