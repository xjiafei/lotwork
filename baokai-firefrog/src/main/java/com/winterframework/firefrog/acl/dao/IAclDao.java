/**   
* @Title: IAclDao.java 
* @Package com.winterframework.firefrog.acl.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 下午2:11:44 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.dao;

import java.util.List;

import com.winterframework.firefrog.acl.entity.Acl;

/** 
* @ClassName: IAclDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-10 下午2:11:44 
*  
*/
public interface IAclDao {
	public List<Acl> queryAclByGroup(Long groupId) throws Exception;
	
	public List<Acl> queryAclByUser(Long userId) throws Exception;
}
