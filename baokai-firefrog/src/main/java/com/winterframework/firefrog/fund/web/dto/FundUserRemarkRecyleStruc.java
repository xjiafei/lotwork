/**   
* @Title: FundUserRemarkRecyleStruc.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-11 下午3:29:43 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.winterframework.modules.web.jsonresult.FirefrogDateSerializer;

/** 
* @ClassName: FundUserRemarkRecyleStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-11 下午3:29:43 
*  
*/
public class FundUserRemarkRecyleStruc {

	private Long id;
	
	private String remark;
	
	@JsonSerialize(using = FirefrogDateSerializer.class) 
	private Date createTime;
	
	private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
