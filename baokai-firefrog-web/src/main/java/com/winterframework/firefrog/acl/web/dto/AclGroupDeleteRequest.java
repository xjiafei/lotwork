/**   
* @Title: AclGroupDeleteRequest.java 
* @Package com.winterframework.firefrog.acl.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-11 下午3:49:02 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: AclGroupDeleteRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-11 下午3:49:02 
*  
*/
public class AclGroupDeleteRequest {

	@NotNull
	public List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}
