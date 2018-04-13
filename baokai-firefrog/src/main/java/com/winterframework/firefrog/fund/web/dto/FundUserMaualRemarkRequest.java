/**   
* @Title: FundUserMaualRemarkSaveRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-11 下午4:29:10 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

/** 
* @ClassName: FundUserMaualRemarkSaveRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-11 下午4:29:10 
*  
*/
public class FundUserMaualRemarkRequest {
	
	private static final long serialVersionUID = 3872628105895612548L;

	private String remark;
	private Long userId;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
