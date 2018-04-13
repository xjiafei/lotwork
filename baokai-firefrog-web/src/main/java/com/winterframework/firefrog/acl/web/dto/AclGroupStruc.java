/**   
* @Title: AclGroupStruc.java 
* @Package com.winterframework.firefrog.acl.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-11 下午2:46:08 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** 
* @ClassName: AclGroupStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-11 下午2:46:08 
*  
*/
public class AclGroupStruc implements Serializable {
	private static final long serialVersionUID = -8749326343741L;

	private Long id;
	private Long pid;

	@Size(min = 2, max = 20, message = "名称长度应为{min}-{max}个字符")
	private String name;

	private Long lvl;
	private String creatorer;
	private Date gmtCreated;
	private String modifierer;
	private Date gmtModified;

	private Long inUser;

	private List<AclGroupStruc> subAclGroups = new ArrayList<AclGroupStruc>();

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

	public String getCreatorer() {
		return creatorer;
	}

	public void setCreatorer(String creatorer) {
		this.creatorer = creatorer;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getModifierer() {
		return modifierer;
	}

	public void setModifierer(String modifierer) {
		this.modifierer = modifierer;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getInUser() {
		return inUser;
	}

	public void setInUser(Long inUser) {
		this.inUser = inUser;
	}

	public List<AclGroupStruc> getSubAclGroups() {
		return subAclGroups;
	}

	public void setSubAclGroups(List<AclGroupStruc> subAclGroups) {
		this.subAclGroups = subAclGroups;
	}

	public Long getLvl() {
		return lvl;
	}

	public void setLvl(Long lvl) {
		this.lvl = lvl;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	public String getBox() {
		return toBox(this, 0,false);
	}

	public String getSunBox() {
		return toBox(this, -1,false);
	}

	private String toBox(AclGroupStruc struc, int deep,boolean selected) {
		String str = "";
		if (deep > -1) {
			//第一次不加入
			str += "<option value=\"" + struc.getId() + "\"" ;
			str+=selected?" selected=\"selected\" ":"";
			str+=">";
			for (int i = 0; i < deep; i++) {
				str += "&nbsp;&nbsp;";
			}
		
		 str += "|--" + struc.getName() + "</option>";
		}
		if (struc.getSubAclGroups() != null) {
			int d = deep + 1;
			for (AclGroupStruc s : struc.getSubAclGroups()) {
				str += toBox(s, d,false);
			}
		}
		return str;

	}

}
