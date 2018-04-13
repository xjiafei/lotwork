/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.acl.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.acl.dao.IAclGroupDao;
import com.winterframework.firefrog.acl.dao.vo.AclGroupVO;
import com.winterframework.firefrog.acl.dao.vo.VOConverter;
import com.winterframework.firefrog.acl.entity.AclGroup;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Repository("aclGroupDaoImpl")
public class AclGroupDaoImpl extends BaseIbatis3Dao<AclGroupVO> implements IAclGroupDao {
	/**
	* Title: queryAclGroupByUser
	* Description:
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.dao.IAclGroupDao#queryAclGroupByUser(java.lang.Long) 
	*/
	@Override
	public AclGroup queryAclGroupByUser(Long userId) throws Exception {
		AclGroupVO vo = this.sqlSessionTemplate.selectOne("queryAclGroupByUser", userId);
		return VOConverter.vO2AclGroup(vo);
	}

	@Override
	public void deleteGroups(List<Long> ids) throws Exception {
		this.sqlSessionTemplate.delete("deleteByIds", ids);
	}

	/**
	* Title: querySubGroups
	* Description:
	* @param groupId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.dao.IAclGroupDao#querySubGroups(java.lang.Long) 
	*/
	@Override
	public List<AclGroup> querySubGroups(Long groupId) throws Exception {
		List<AclGroupVO> voList = this.sqlSessionTemplate.selectList("queryAllSubGroup", groupId);
		List<AclGroup> aclGroupList = new ArrayList<AclGroup>();
		for (AclGroupVO vo : voList) {
			if (vo.getId().longValue() == groupId.longValue()) {
				continue;
			}
			if (vo.getPid().longValue() == groupId.longValue()) {
				vo.setPid(null);
			}
			aclGroupList.add(VOConverter.vO2AclGroup(vo));
		}
		return aclGroupList;
	}

	/**
	* Title: update
	* Description:
	* @param aclGroup
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.dao.IAclGroupDao#update(com.winterframework.firefrog.acl.entity.AclGroup) 
	*/
	@Override
	public void update(AclGroup aclGroup) throws Exception {
		aclGroup.setGmtCreated(null);
		aclGroup.setGmtModified(null);
		AclGroupVO vo = VOConverter.aclGroup2VO(aclGroup);
		vo.setGmtModified(new Date());
		this.update(vo);
	}

	/**
	* Title: insert
	* Description:
	* @param aclGroup
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.dao.IAclGroupDao#insert(com.winterframework.firefrog.acl.entity.AclGroup) 
	*/
	@Override
	public AclGroup insert(AclGroup aclGroup) throws Exception {
		aclGroup.setGmtCreated(null);
		aclGroup.setGmtModified(null);
		AclGroupVO vo = VOConverter.aclGroup2VO(aclGroup);
		vo.setGmtCreated(new Date());
		this.insert(vo);
		return VOConverter.vO2AclGroup(vo);
	}

	/**
	* Title: queryAclGroup
	* Description:
	* @param pageRequest
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.dao.IAclGroupDao#queryAclGroup(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public Page<AclGroup> queryAclGroup(PageRequest<Long> pageRequest) throws Exception {
		Long userId = pageRequest.getSearchDo();
		AclGroupVO userGroupvo = this.sqlSessionTemplate.selectOne("queryAclGroupByUser", userId);
		int totalCount = userGroupvo != null ? sqlSessionTemplate.selectList("queryAllSubGroup", userGroupvo.getId())
				.size() : 0;
		Page<AclGroup> page = new Page<AclGroup>(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount);

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.put("groupId", userGroupvo != null ? userGroupvo.getId() : -2);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<AclGroupVO> voList = sqlSessionTemplate.selectList("queryAllSubGroup", filters, rowBounds);
		List<AclGroup> result = new ArrayList<AclGroup>();
		for (AclGroupVO vo : voList) {
			result.add(VOConverter.vO2AclGroup(vo));
		}
		page.setResult(result);
		return page;
	}

	/**
	* Title: queryFirstSubGroups
	* Description:
	* @param groupId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.dao.IAclGroupDao#queryFirstSubGroups(java.lang.Long) 
	*/
	@Override
	public List<AclGroup> queryFirstSubGroups(Long groupId) throws Exception {
		List<AclGroupVO> voList = this.sqlSessionTemplate.selectList("queryFirstSubGroups", groupId);
		List<AclGroup> result = new ArrayList<AclGroup>();
		for (AclGroupVO vo : voList) {
			result.add(VOConverter.vO2AclGroup(vo));
		}
		return result;
	}

	/**
	* Title: queryById
	* Description:
	* @param id
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.dao.IAclGroupDao#queryById(java.lang.Long) 
	*/
	@Override
	public AclGroup queryById(Long id) throws Exception {
		AclGroupVO vo = this.getById(id);
		return VOConverter.vO2AclGroup(vo);
	}

	/**
	* Title: checkByName
	* Description:
	* @param aclGroup
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.dao.IAclGroupDao#checkByName(com.winterframework.firefrog.acl.entity.AclGroup) 
	*/
	@Override
	public Boolean checkByName(AclGroup aclGroup) throws Exception {
		AclGroupVO vo = VOConverter.aclGroup2VO(aclGroup);
		List<AclGroupVO> voList = this.sqlSessionTemplate.selectList("queryByName",vo);
		return voList.isEmpty();
	}
}
