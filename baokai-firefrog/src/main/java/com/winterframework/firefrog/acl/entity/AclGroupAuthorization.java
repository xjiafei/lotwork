/**   
* @Title: AclGroupAuthorization.java 
* @Package com.winterframework.firefrog.acl.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 上午11:10:30 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.entity;

/** 
* @ClassName: AclGroupAuthorization 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-10 上午11:10:30 
*  
*/
public class AclGroupAuthorization {

	private Long id;
	
	private Acl acl;
	
	private AclGroup aclGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Acl getAcl() {
		return acl;
	}

	public void setAcl(Acl acl) {
		this.acl = acl;
	}

	public AclGroup getAclGroup() {
		return aclGroup;
	}

	public void setAclGroup(AclGroup aclGroup) {
		this.aclGroup = aclGroup;
	}
}
