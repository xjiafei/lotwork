/**   
* @Title: IAclGroupService.java 
* @Package com.winterframework.firefrog.acl.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-11 下午2:09:58 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.service;

import java.util.List;

import com.winterframework.firefrog.acl.entity.AclGroup;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IAclGroupService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-11 下午2:09:58 
*  
*/
public interface IAclGroupService {
	public List<AclGroup> querySubGroupsByUser(Long userId) throws Exception;

	public void deleteGroup(List<Long> ids) throws Exception;
	
	public void update(AclGroup aclGroup) throws Exception;
	
	public AclGroup insert(AclGroup aclGroup) throws Exception;
	
	public Page<AclGroup> queryAclGroup(PageRequest<Long> pageRequest) throws Exception;
	
	public void copyAclGroup(AclGroup aclGroup) throws Exception;
	
	public List<AclGroup> queryFirstSubGroups(Long groupId) throws Exception;
	
	public Boolean checkDelete(Long groupId) throws Exception;
	
	public AclGroup queryAclGroupByUser(Long userId) throws Exception;
	
	public Boolean checkByName(AclGroup aclGroup) throws Exception;
}
