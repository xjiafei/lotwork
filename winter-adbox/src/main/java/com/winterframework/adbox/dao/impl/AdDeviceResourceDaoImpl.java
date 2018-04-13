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

import com.winterframework.adbox.dao.IAdDeviceResourceDao;
import com.winterframework.adbox.dao.base.BaseDaoImpl;

import com.winterframework.adbox.entity.AdDeviceResource;



@Repository("adDeviceResourceDaoImpl")
public class AdDeviceResourceDaoImpl<E extends AdDeviceResource> extends BaseDaoImpl<AdDeviceResource> implements IAdDeviceResourceDao{

}
