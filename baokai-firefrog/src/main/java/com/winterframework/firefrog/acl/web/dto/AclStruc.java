/**   
* @Title: AclStruc.java 
* @Package com.winterframework.firefrog.acl.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 下午5:45:34 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: AclStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-10 下午5:45:34 
*  
*/
public class AclStruc implements Serializable{
	private static final long serialVersionUID = -22323232321111L;

	private Long id;
	
	private Long pid;
	
	private String name;
	
	private String label;
	
	private Long types;
	
	private Long orders;
	
	private List<AclStruc> subAcls;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getTypes() {
		return types;
	}

	public void setTypes(Long types) {
		this.types = types;
	}

	public Long getOrders() {
		return orders;
	}

	public void setOrders(Long orders) {
		this.orders = orders;
	}

	public List<AclStruc> getSubAcls() {
		return subAcls;
	}

	public void setSubAcls(List<AclStruc> subAcls) {
		this.subAcls = subAcls;
	}
}
