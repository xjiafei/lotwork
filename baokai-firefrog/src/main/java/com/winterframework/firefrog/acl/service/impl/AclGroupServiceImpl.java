/**   
* @Title: AclGroupServiceImpl.java 
* @Package com.winterframework.firefrog.acl.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-11 下午2:20:03 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.acl.dao.AclUserDao;
import com.winterframework.firefrog.acl.dao.IAclDao;
import com.winterframework.firefrog.acl.dao.IAclGroupAuthorizationDao;
import com.winterframework.firefrog.acl.dao.IAclGroupDao;
import com.winterframework.firefrog.acl.entity.Acl;
import com.winterframework.firefrog.acl.entity.AclGroup;
import com.winterframework.firefrog.acl.entity.AclGroupAuthorization;
import com.winterframework.firefrog.acl.service.IAclGroupService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("aclGroupServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AclGroupServiceImpl implements IAclGroupService {

	@Resource(name = "aclGroupDaoImpl")
	private IAclGroupDao aclGroupDao;

	@Resource(name = "aclGroupAuthorizationDaoImpl")
	private IAclGroupAuthorizationDao aclGroupAuthorizationDao;

	@Resource(name = "aclDaoImpl")
	private IAclDao aclDao;

	@Resource(name = "aclUserDaoImpl")
	private AclUserDao aclUserDao;

	/**
	* Title: querySubGroupsByUser
	* Description:
	* @param userId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupService#querySubGroupsByUser(java.lang.Long) 
	*/
	@Override
	public List<AclGroup> querySubGroupsByUser(Long userId) throws Exception {
		AclGroup group = aclGroupDao.queryAclGroupByUser(userId);
		List<AclGroup> result = new ArrayList<AclGroup>();
		if (group != null) {
			result = aclGroupDao.querySubGroups(group.getId());
		}
		return result;
	}

	public AclGroup queryAclGroupByUser(Long userId) throws Exception {
		return aclGroupDao.queryAclGroupByUser(userId);
	}
	

	/**
	* Title: deleteGroup
	* Description:
	* @param ids
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupService#deleteGroup(java.util.List) 
	*/
	@Override
	public void deleteGroup(List<Long> ids) throws Exception {
		for (Long groupId : ids) {
			List<Long> deleteIds = new ArrayList<Long>();
			deleteIds.add(groupId);
			List<AclGroup> subGroups = aclGroupDao.querySubGroups(groupId);
			for (AclGroup aclGroup : subGroups) {
				deleteIds.add(aclGroup.getId());
			}
			aclGroupAuthorizationDao.deleteAuthForGroups(deleteIds);
			aclGroupDao.deleteGroups(deleteIds);
		}
	}

	/**
	* Title: update
	* Description:
	* @param aclGroup
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupService#update(com.winterframework.firefrog.acl.entity.AclGroup) 
	*/
	@Override
	public void update(AclGroup aclGroup) throws Exception {
		if (aclGroup.getParentGroup() == null || aclGroup.getParentGroup().getId() == 0) {
			aclGroup.setLvl(1l);
		} else {
			AclGroup pAclGroup = aclGroupDao.queryById(aclGroup.getParentGroup().getId());
			aclGroup.setLvl(pAclGroup.getLvl() + 1);
		}
		aclGroupDao.update(aclGroup);
	}

	/**
	* Title: insert
	* Description:
	* @param aclGroup
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupService#insert(com.winterframework.firefrog.acl.entity.AclGroup) 
	*/
	@Override
	public AclGroup insert(AclGroup aclGroup) throws Exception {
		if (aclGroup.getParentGroup() == null || aclGroup.getParentGroup().getId() == 0) {
			aclGroup.setLvl(1l);
		} else {
			AclGroup pAclGroup = aclGroupDao.queryById(aclGroup.getParentGroup().getId());
			aclGroup.setLvl(pAclGroup.getLvl() + 1);
		}
		return aclGroupDao.insert(aclGroup);
	}

	/**
	* Title: queryAclGroup
	* Description:
	* @param pageRequest
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupService#queryAclGroup(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public Page<AclGroup> queryAclGroup(PageRequest<Long> pageRequest) throws Exception {
		Page<AclGroup> page = aclGroupDao.queryAclGroup(pageRequest);
		/*List<AclGroup> pageList = page.getResult();
		for (AclGroup aclGroup : pageList) {
			List<AclGroup> subList = aclGroupDao.queryFirstSubGroups(aclGroup.getId());
			aclGroup.setSubGroups(subList);
		}*/
		return page;
	}

	/**
	* Title: copyAclGroup
	* Description:
	* @param aclGroup
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupService#copyAclGroup(com.winterframework.firefrog.acl.entity.AclGroup) 
	*/
	@Override
	public void copyAclGroup(AclGroup aclGroup) throws Exception {
		AclGroup sourceGroup = aclGroupDao.queryById(aclGroup.getId());
		sourceGroup.setName(aclGroup.getName());
		sourceGroup.setInUse(aclGroup.getInUse());
		sourceGroup.setCreatorer(aclGroup.getCreatorer());
		sourceGroup.setGmtModified(null);
		sourceGroup.setModifierer(null);
		doCopyOne(sourceGroup);

	}

	private void doCopyOne(AclGroup aclGroup) throws Exception {
		AclGroup targetGroup = aclGroupDao.insert(aclGroup);
		List<Acl> aclList = aclDao.queryAclByGroup(aclGroup.getId());
		List<AclGroupAuthorization> auths = new ArrayList<AclGroupAuthorization>();
		for (Acl acl : aclList) {
			AclGroupAuthorization auth = new AclGroupAuthorization();
			auth.setAcl(acl);
			auth.setAclGroup(targetGroup);
			auths.add(auth);
		}
		aclGroupAuthorizationDao.bindAuthForGroup(auths);
	}

	/**
	* Title: queryFirstSubGroups
	* Description:
	* @param groupId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupService#queryFirstSubGroups(java.lang.Long) 
	*/
	@Override
	public List<AclGroup> queryFirstSubGroups(Long groupId) throws Exception {
		return aclGroupDao.queryFirstSubGroups(groupId);
	}

	/**
	* Title: checkDelete
	* Description:
	* @param groupId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupService#checkDelete(java.lang.Long) 
	*/
	@Override
	public Boolean checkDelete(Long groupId) throws Exception {
		List<Long> checkList = new ArrayList<Long>();
		AclGroup aclGroup = aclGroupDao.queryById(groupId);
		checkList.add(aclGroup.getId());
		List<AclGroup> subList = aclGroupDao.querySubGroups(groupId);
		for (AclGroup group : subList) {
			checkList.add(group.getId());
		}
		return aclUserDao.checkUserByGroupIds(checkList);
	}

	/**
	* Title: checkByName
	* Description:
	* @param aclGroup
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupService#checkByName(com.winterframework.firefrog.acl.entity.AclGroup) 
	*/
	@Override
	public Boolean checkByName(AclGroup aclGroup) throws Exception {
		return aclGroupDao.checkByName(aclGroup);
	}
}
