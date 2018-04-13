/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.sample.dao;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.sample.entity.Task;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("taskDao")
public class TaskDao extends BaseIbatis3Dao<Task> {

}
