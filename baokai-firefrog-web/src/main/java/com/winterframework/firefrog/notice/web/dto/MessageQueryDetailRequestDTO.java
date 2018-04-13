/**   
* @Title: MessageQueryDetailRequestDTO.java 
* @Package com.winterframework.firefrog.user.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Denny  
* @date 2013-5-6 下午4:11:38 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.web.dto;

/** 
* @ClassName: MessageQueryDetailRequestDTO 
* @Description: 查询站内信详情的请求(rootId和userId) 
* @author Denny 
* @date 2013-5-6 下午4:11:38 
*  
*/
public class MessageQueryDetailRequestDTO {

	private long rootId;

	//	private long userId;

	//	public long getUserId() {
	//		return userId;
	//	}
	//
	//	public void setUserId(long userId) {
	//		this.userId = userId;
	//	}

	public long getRootId() {
		return rootId;
	}

	public void setRootId(long rootId) {
		this.rootId = rootId;
	}

}
