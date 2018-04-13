 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.active.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.active.dao.ActiveUserMigrateDao;
import com.winterframework.firefrog.active.entity.ActiveUserMigrate;
import com.winterframework.orm.dal.ibatis3.BaseManager;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ActiveUserMigrateManager extends BaseManager<ActiveUserMigrateDao,ActiveUserMigrate>{

@Override
@Autowired
public void setEntityDao(ActiveUserMigrateDao activeUserMigrateDao ) {
	this.entityDao=activeUserMigrateDao;
}

}
