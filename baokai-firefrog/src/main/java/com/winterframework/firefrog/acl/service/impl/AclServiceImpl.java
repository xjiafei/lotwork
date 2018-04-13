/**   
* @Title: AclServiceImpl.java 
* @Package com.winterframework.firefrog.acl.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 下午3:49:56 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.acl.dao.IAclDao;
import com.winterframework.firefrog.acl.entity.Acl;
import com.winterframework.firefrog.acl.service.IAclService;

@Service("aclServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AclServiceImpl implements IAclService {

	@Resource(name = "aclDaoImpl")
	private IAclDao aclDao;

	/**
	* Title: queryAclByGroup
	* Description:
	* @param groupId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.IAclService#queryAclByGroup(java.lang.Long) 
	*/
	@Override
	public List<Acl> queryAclByGroup(Long groupId) throws Exception {
		List<Acl> acls = aclDao.queryAclByGroup(groupId);
		return acls;//handerAclTreeList(acls);
	}

	private List<Acl> handerAclTreeList(List<Acl> aclList) {
		List<Acl> parents = new ArrayList<Acl>();
		List<Acl> others = new ArrayList<Acl>();
		for (Acl acl : aclList) {
			if (acl.getParentAcl() == null) {
				acl.setSubAcls(new ArrayList<Acl>());
				parents.add(acl);
			} else {
				others.add(acl);
			}
		}
		buildTree(parents, others);
		return parents;
	}

	private void buildTree(List<Acl> parents, List<Acl> others) {
		List<Acl> record = new ArrayList<Acl>();
		for (Iterator<Acl> it = parents.iterator(); it.hasNext();) {
			Acl vi = it.next();
			if (vi.getId() != null) {
				for (Iterator<Acl> otherIt = others.iterator(); otherIt.hasNext();) {
					Acl inVi = otherIt.next();
					if (vi.getId().equals(inVi.getParentAcl().getId())) {
						if (null == vi.getSubAcls()) {
							vi.setSubAcls(new ArrayList<Acl>());
						}
						vi.getSubAcls().add(inVi);
						record.add(inVi);
						otherIt.remove();
					}
				}
			}
		}
		if (others.size() == 0) {
			return;
		} else {
			buildTree(record, others);
		}
	}

	@Override
	public List<Acl> queryAclByUser(Long userId) throws Exception {
		return aclDao.queryAclByUser(userId);
	}
}
