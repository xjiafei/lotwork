/**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.firefrog.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.sample.dao.TaskDao;
import com.winterframework.firefrog.sample.entity.Task;
import com.winterframework.orm.dal.ibatis3.BaseManager;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class TaskManager extends BaseManager<TaskDao, Task> {

	@Override
	@Autowired
	public void setEntityDao(TaskDao taskDao) {
		this.entityDao = taskDao;
	}

}
