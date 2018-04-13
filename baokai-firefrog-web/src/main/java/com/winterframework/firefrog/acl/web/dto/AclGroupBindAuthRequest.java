/**   
* @Title: AclGroupBindAuthRequest.java 
* @Package com.winterframework.firefrog.acl.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-11 上午8:54:41 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: AclGroupBindAuthRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-11 上午8:54:41 
*  
*/
public class AclGroupBindAuthRequest {

	@NotNull
	private Long gid;
	
	@NotNull
	private List<Long> aclIds;

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public List<Long> getAclIds() {
		return aclIds;
	}

	public void setAclIds(List<Long> aclIds) {
		this.aclIds = aclIds;
	}
	
}
