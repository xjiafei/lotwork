/**   
* @Title: AclGroupTreeStruc.java 
* @Package com.winterframework.firefrog.acl.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-12 上午10:41:39 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.dto;

import java.util.List;

/** 
* @ClassName: AclGroupTreeStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-12 上午10:41:39 
*  
*/
public class AclGroupTreeStruc {
	private AclGroupStruc group;
	private List<AclGroupStruc> subList;
	public AclGroupStruc getGroup() {
		return group;
	}
	public void setGroup(AclGroupStruc group) {
		this.group = group;
	}
	public List<AclGroupStruc> getSubList() {
		return subList;
	}
	public void setSubList(List<AclGroupStruc> subList) {
		this.subList = subList;
	}
}
