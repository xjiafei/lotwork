/**   
* @Title: AclTreeResponse.java 
* @Package com.winterframework.firefrog.acl.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-17 上午10:15:13 
* @version V1.0   
*/
package com.winterframework.firefrog.acl.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: AclTreeResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-17 上午10:15:13 
*  
*/
public class AclTreeResponse implements Serializable {

	private static final long serialVersionUID = 113224232L;
	private Long isSuccess;

	private String type;

	private String msg;

	private List<AclTreeStruc> data;

	private List<AclTreeStruc> pdata;

	public Long getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Long isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<AclTreeStruc> getData() {
		return data;
	}

	public void setData(List<AclTreeStruc> data) {
		this.data = data;
	}

	public List<AclTreeStruc> getPdata() {
		return pdata;
	}

	public void setPdata(List<AclTreeStruc> pdata) {
		this.pdata = pdata;
	}

}
