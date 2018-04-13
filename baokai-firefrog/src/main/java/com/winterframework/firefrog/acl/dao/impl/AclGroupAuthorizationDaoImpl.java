/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.acl.dao.impl;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.acl.dao.IAclGroupAuthorizationDao;
import com.winterframework.firefrog.acl.dao.vo.AclGroupAuthorizationVO;
import com.winterframework.firefrog.acl.dao.vo.VOConverter;
import com.winterframework.firefrog.acl.entity.AclGroupAuthorization;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("aclGroupAuthorizationDaoImpl")
public class AclGroupAuthorizationDaoImpl extends BaseIbatis3Dao<AclGroupAuthorizationVO> implements
		IAclGroupAuthorizationDao {

	@Override
	public void bindAuthForGroup(List<AclGroupAuthorization> auths) throws Exception {
		for (AclGroupAuthorization auth : auths) {
			this.insert(VOConverter.aclGroupAuthorization2VO(auth));
		}
	}

	@Override
	public void deleteAuthForGroup(Long groupId) throws Exception {
		this.sqlSessionTemplate.delete("deleteByGroup", groupId);
	}
	
	@Override
	public void deleteAuthForGroups(List<Long> ids) throws Exception {
		this.sqlSessionTemplate.delete("deleteByGroups", ids);
	}
}
