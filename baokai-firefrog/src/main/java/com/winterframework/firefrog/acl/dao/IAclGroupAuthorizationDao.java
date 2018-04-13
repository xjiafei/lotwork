/**   
* @Title: IAclGroupAuthorizationDao.java 
* @Package com.winterframework.firefrog.acl.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 上午11:36:37 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.dao;

import java.util.List;

import com.winterframework.firefrog.acl.entity.AclGroupAuthorization;

/** 
* @ClassName: IAclGroupAuthorizationDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-10 上午11:36:37 
*  
*/
public interface IAclGroupAuthorizationDao {

	public void bindAuthForGroup(List<AclGroupAuthorization> auths) throws Exception;

	public void deleteAuthForGroup(Long groupId) throws Exception;
	
	public void deleteAuthForGroups(List<Long> ids) throws Exception;

}
