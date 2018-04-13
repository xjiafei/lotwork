/**   
* @Title: AclTreeStruc.java 
* @Package com.winterframework.firefrog.acl.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-17 上午10:08:20 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.dto;

import java.io.Serializable;

/** 
* @ClassName: AclTreeStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-17 上午10:08:20 
*  
*/
public class AclTreeStruc implements Serializable{
	private static final long serialVersionUID = 2334233423L;

	private Long id;
	private Long pid;
	private String text;
	private String value;
	private Long type;
	private Long isChecked;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Long isChecked) {
		this.isChecked = isChecked;
	}
	
}
