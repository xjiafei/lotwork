/**   
* @Title: AclGroupAuthorizationServiceImpl.java 
* @Package com.winterframework.firefrog.acl.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 下午3:23:15 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.acl.dao.IAclDao;
import com.winterframework.firefrog.acl.dao.IAclGroupAuthorizationDao;
import com.winterframework.firefrog.acl.dao.IAclGroupDao;
import com.winterframework.firefrog.acl.entity.Acl;
import com.winterframework.firefrog.acl.entity.AclGroup;
import com.winterframework.firefrog.acl.entity.AclGroupAuthorization;
import com.winterframework.firefrog.acl.service.IAclGroupAuthorizationService;

@Service("aclGroupAuthorizationServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AclGroupAuthorizationServiceImpl implements IAclGroupAuthorizationService {

	@Resource(name = "aclGroupAuthorizationDaoImpl")
	private IAclGroupAuthorizationDao aclGroupAuthorizationDao;

	@Resource(name = "aclGroupDaoImpl")
	private IAclGroupDao aclGroupDao;

	@Resource(name = "aclDaoImpl")
	private IAclDao aclDao;

	/**
	* Title: bindAuthForGroup
	* Description:
	* @param auths
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclGroupAuthorizationService#bindAuthForGroup(java.util.List) 
	*/
	@Override
	public void bindAuthForGroup(List<AclGroupAuthorization> auths) throws Exception {
		AclGroupAuthorization auth = auths.get(0);
		if (auth != null) {
			aclGroupAuthorizationDao.deleteAuthForGroup(auth.getAclGroup().getId());
			aclGroupAuthorizationDao.bindAuthForGroup(auths);
			List<AclGroup> subGroups = aclGroupDao.querySubGroups(auth.getAclGroup().getId());

			for (AclGroup aclGroup : subGroups) {
				List<AclGroupAuthorization> subAclsAuth = new ArrayList<AclGroupAuthorization>();
				List<Acl> acls = aclDao.queryAclByGroup(aclGroup.getId());
				for (Acl acl : acls) {
					for (AclGroupAuthorization au : auths) {
						if(acl.getId().longValue() == au.getAcl().getId().longValue()){
							AclGroupAuthorization aclGroupAuth = new AclGroupAuthorization();
							aclGroupAuth.setAcl(acl);
							aclGroupAuth.setAclGroup(aclGroup);
							subAclsAuth.add(aclGroupAuth);
						}

					}
				}
				aclGroupAuthorizationDao.deleteAuthForGroup(aclGroup.getId());
				aclGroupAuthorizationDao.bindAuthForGroup(subAclsAuth);
			}
		}
	}

}
