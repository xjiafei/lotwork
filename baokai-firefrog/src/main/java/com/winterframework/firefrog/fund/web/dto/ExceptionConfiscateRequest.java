/**   
* @Title: ExceptionConfiscateRequest.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-17 上午11:18:45 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import com.winterframework.firefrog.common.convert.BeanConverter.Alias;

/** 
* @ClassName: ExceptionConfiscateRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-17 上午11:18:45 
*  
*/
public class ExceptionConfiscateRequest implements Serializable {

	private static final long serialVersionUID = -6463368105688387160L;

	@Alias(field = "id")
	private Long exceptId;

	@Alias(field = "apprMemo")
	private String applyMemo;

	public Long getExceptId() {
		return exceptId;
	}

	public void setExceptId(Long exceptId) {
		this.exceptId = exceptId;
	}

	public String getApplyMemo() {
		return applyMemo;
	}

	public void setApplyMemo(String applyMemo) {
		this.applyMemo = applyMemo;
	}

}
