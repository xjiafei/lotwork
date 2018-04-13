/**   
* @Title: AclGroupIdRequest.java 
* @Package com.winterframework.firefrog.acl.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 下午5:52:55 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.dto;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: AclGroupIdRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-10 下午5:52:55 
*  
*/
public class AclGroupIdRequest {

	@NotNull
	private Long aclGId;

	public Long getAclGId() {
		return aclGId;
	}

	public void setAclGId(Long aclGId) {
		this.aclGId = aclGId;
	}
}
