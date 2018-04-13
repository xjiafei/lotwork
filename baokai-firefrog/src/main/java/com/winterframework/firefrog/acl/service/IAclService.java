/**   
* @Title: IAclService.java 
* @Package com.winterframework.firefrog.acl.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 下午3:41:22 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.service;

import java.util.List;

import com.winterframework.firefrog.acl.entity.Acl;

/** 
* @ClassName: IAclService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-10 下午3:41:22 
*  
*/
public interface IAclService {
	public List<Acl> queryAclByGroup(Long groupId) throws Exception;
	
	public List<Acl> queryAclByUser(Long userId) throws Exception;
}
