/**   
* @Title: Acl.java 
* @Package com.winterframework.firefrog.acl.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 上午11:09:42 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.entity;

import java.util.List;

/** 
* @ClassName: Acl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-10 上午11:09:42 
*  
*/
public class Acl {

	private Long id;

	private String name;
	private Long type;
	private Long orders;
	private String label;

	private Acl parentAcl;
	
	private List<Acl> subAcls;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getOrders() {
		return orders;
	}

	public void setOrders(Long orders) {
		this.orders = orders;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Acl getParentAcl() {
		return parentAcl;
	}

	public void setParentAcl(Acl parentAcl) {
		this.parentAcl = parentAcl;
	}

	public List<Acl> getSubAcls() {
		return subAcls;
	}

	public void setSubAcls(List<Acl> subAcls) {
		this.subAcls = subAcls;
	}
}
