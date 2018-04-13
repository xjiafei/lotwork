/**   
* @Title: FundUserRemarkImportRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-11 下午3:07:00 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.List;

/** 
* @ClassName: FundUserRemarkImportRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-11 下午3:07:00 
*  
*/
public class FundUserRemarkImportRequest {

	private static final long serialVersionUID = -7582439450123244484L;
	private List<Long> userIds;

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}
}
