/**   
* @Title: IAclGroupDao.java 
* @Package com.winterframework.firefrog.acl.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-11 上午10:22:02 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.dao;

import java.util.List;

import com.winterframework.firefrog.acl.entity.AclGroup;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IAclGroupDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-11 上午10:22:02 
*  
*/
public interface IAclGroupDao {
	
	public AclGroup queryAclGroupByUser(Long userId) throws Exception;

	public List<AclGroup> querySubGroups(Long groupId) throws Exception;
	
	public void deleteGroups(List<Long> ids) throws Exception;
	
	public void update(AclGroup aclGroup) throws Exception;
	
	public AclGroup insert(AclGroup aclGroup) throws Exception;
	
	public Page<AclGroup> queryAclGroup(PageRequest<Long> pageRequest) throws Exception;
	
	public List<AclGroup> queryFirstSubGroups(Long groupId) throws Exception;
	
	public AclGroup queryById(Long id) throws Exception;
	
	public Boolean checkByName(AclGroup aclGroup) throws Exception;
}
