/**   
* @Title: AclGroup.java 
* @Package com.winterframework.firefrog.acl.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-10 上午11:10:01 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.entity;

import java.util.Date;
import java.util.List;

/** 
* @ClassName: AclGroup 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-10 上午11:10:01 
*  
*/
public class AclGroup {
	private Long id;
	private Long inUse;
	private String name;
	private String fullName;
	private AclGroup parentGroup;
	private Long lvl;
	
	private List<AclGroup> subGroups;
	
	private String creatorer;
	private String modifierer;

	private Date gmtCreated;
	
	private Date gmtModified;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInUse() {
		return inUse;
	}

	public void setInUse(Long inUse) {
		this.inUse = inUse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public AclGroup getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(AclGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	public Long getLvl() {
		return lvl;
	}

	public void setLvl(Long lvl) {
		this.lvl = lvl;
	}

	public List<AclGroup> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(List<AclGroup> subGroups) {
		this.subGroups = subGroups;
	}

	public String getCreatorer() {
		return creatorer;
	}

	public void setCreatorer(String creatorer) {
		this.creatorer = creatorer;
	}

	public String getModifierer() {
		return modifierer;
	}

	public void setModifierer(String modifierer) {
		this.modifierer = modifierer;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
