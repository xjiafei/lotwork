/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.acl.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.acl.dao.IAclDao;
import com.winterframework.firefrog.acl.dao.vo.AclVO;
import com.winterframework.firefrog.acl.dao.vo.VOConverter;
import com.winterframework.firefrog.acl.entity.Acl;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Repository("aclDaoImpl")
public class AclDaoImpl extends BaseIbatis3Dao<AclVO> implements IAclDao {
	@Override
	public List<Acl> queryAclByGroup(Long groupId) throws Exception {
		List<AclVO> voList = null;
		if (groupId != null) {
			voList = this.sqlSessionTemplate.selectList("queryAclsByGroup", groupId);
		} else {
			voList = this.getAll();
		}
		List<Acl> aclList = new ArrayList<Acl>();
		for (AclVO vo : voList) {
			aclList.add(VOConverter.vO2Acl(vo));
		}
		return aclList;
	}

	@Override
	public List<Acl> queryAclByUser(Long userId) throws Exception {
		List<AclVO> voList = null;
		if (userId != null) {
			voList = this.sqlSessionTemplate.selectList("queryAclsByUser", userId);
		} else {
			voList = this.getAll();
		}
		List<Acl> aclList = new ArrayList<Acl>();
		if (voList != null) {
			for (AclVO vo : voList) {
				aclList.add(VOConverter.vO2Acl(vo));
			}
		}
		return aclList;
	}
}
