/**   
* @Title: AdQueryRequest.java 
* @Package com.winterframework.firefrog.advert.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-5 下午3:34:12 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto;

import java.util.List;

/** 
* @ClassName: AdQueryRequest 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-5 下午3:34:12 
*  
*/
public class AdQueryRequest {

	private String name;

	private Long type;

	private Long status;

	private Long spaceId;

	private Long rcAll;
	private Long rcGuest;
	private Long rcTopAgent;
	private Long rcOtAgent;
	private Long rcVip;
	private Long rcNonVip;
	private Long rcCustomer;
	private Long orderBySpaces;
	private Long orderById;
	
	private int pageNo;
	private int pageSize;
	
	private List<Long> allStatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Long spaceId) {
		this.spaceId = spaceId;
	}

	public Long getRcAll() {
		return rcAll;
	}

	public void setRcAll(Long rcAll) {
		this.rcAll = rcAll;
	}

	public Long getRcGuest() {
		return rcGuest;
	}

	public void setRcGuest(Long rcGuest) {
		this.rcGuest = rcGuest;
	}

	public Long getRcTopAgent() {
		return rcTopAgent;
	}

	public void setRcTopAgent(Long rcTopAgent) {
		this.rcTopAgent = rcTopAgent;
	}

	public Long getRcOtAgent() {
		return rcOtAgent;
	}

	public void setRcOtAgent(Long rcOtAgent) {
		this.rcOtAgent = rcOtAgent;
	}

	public Long getRcVip() {
		return rcVip;
	}

	public void setRcVip(Long rcVip) {
		this.rcVip = rcVip;
	}

	public Long getRcNonVip() {
		return rcNonVip;
	}

	public void setRcNonVip(Long rcNonVip) {
		this.rcNonVip = rcNonVip;
	}

	public Long getRcCustomer() {
		return rcCustomer;
	}

	public void setRcCustomer(Long rcCustomer) {
		this.rcCustomer = rcCustomer;
	}

	public Long getOrderBySpaces() {
		return orderBySpaces;
	}

	public void setOrderBySpaces(Long orderBySpaces) {
		this.orderBySpaces = orderBySpaces;
	}

	public Long getOrderById() {
		return orderById;
	}

	public void setOrderById(Long orderById) {
		this.orderById = orderById;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Long> getAllStatus() {
		return allStatus;
	}

	public void setAllStatus(List<Long> allStatus) {
		this.allStatus = allStatus;
	}
}
