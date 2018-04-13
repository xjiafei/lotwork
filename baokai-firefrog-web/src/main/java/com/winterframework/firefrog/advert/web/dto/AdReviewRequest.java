/**   
* @Title: AdReviewRequest.java 
* @Package com.winterframework.firefrog.advert.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-6 下午3:51:32 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

/** 
* @ClassName: AdReviewRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 下午3:51:32 
*  
*/
public class AdReviewRequest {

	@NotNull
	private List<Long> ids;
	private String memo;
	@NotNull
	private Long status;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
