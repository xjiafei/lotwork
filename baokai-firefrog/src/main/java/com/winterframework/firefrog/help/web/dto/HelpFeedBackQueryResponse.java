/**   
* @Title: HelpFeedBackQueryResponse.java 
* @Package com.winterframework.firefrog.help.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-8 下午4:45:38 
* @version V1.0   
*/
package com.winterframework.firefrog.help.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: HelpFeedBackQueryResponse 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-8 下午4:45:38 
*  
*/
public class HelpFeedBackQueryResponse implements Serializable {
	
	private static final long serialVersionUID = -231143354656l;
	private Long reasonId;

	private Long count;

	private List<String> reason;

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<String> getReason() {
		return reason;
	}

	public void setReason(List<String> reason) {
		this.reason = reason;
	}

}
