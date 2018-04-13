/**   
* @Title: Ad.java 
* @Package com.winterframework.firefrog.advert.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-4 下午3:54:59 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.advert.entity.AdSpace;

/** 
* @ClassName: Ad 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-4 下午3:54:59 
*  
*/
public class AdvertSearchDto {
	private Long id;
	private Long spaces;
	private String name;
	private String imgUrl;
	private Date gmtEffectBegin;
	private Date gmtEffectEnd;
	private String targetUrl;
	private Long status;
	private String memo;
	private String approver;
	private String operator;
	private Date gmtAppr;
	private String receivers;
	private Long orders;
	private Long rcAll;
	private Long rcGuest;
	private Long rcTopAgent;
	private Long rcOtAgent;
	private Long rcVip;
	private Long rcNonVip;
	private Long rcCustomer;
	
	private Long spaceId;

	private Long orderBySpacesAsc;
	private Long orderByIdAsc;
	private Long orderBySpacesDesc;
	private Long orderByIdDesc;
	private Long unBegin;
	private Long begin;
	private Long end;
	private Date currentDate;

	private List<AdSpace> adspaces;
	
	private List<Long> allStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSpaces() {
		return spaces;
	}

	public void setSpaces(Long spaces) {
		this.spaces = spaces;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Date getGmtEffectBegin() {
		return gmtEffectBegin;
	}

	public void setGmtEffectBegin(Date gmtEffectBegin) {
		this.gmtEffectBegin = gmtEffectBegin;
	}

	public Date getGmtEffectEnd() {
		return gmtEffectEnd;
	}

	public void setGmtEffectEnd(Date gmtEffectEnd) {
		this.gmtEffectEnd = gmtEffectEnd;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getGmtAppr() {
		return gmtAppr;
	}

	public void setGmtAppr(Date gmtAppr) {
		this.gmtAppr = gmtAppr;
	}

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public Long getOrders() {
		return orders;
	}

	public void setOrders(Long orders) {
		this.orders = orders;
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

	public Long getUnBegin() {
		return unBegin;
	}

	public void setUnBegin(Long unBegin) {
		this.unBegin = unBegin;
	}

	public Long getBegin() {
		return begin;
	}

	public void setBegin(Long begin) {
		this.begin = begin;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public Long getOrderBySpacesAsc() {
		return orderBySpacesAsc;
	}

	public void setOrderBySpacesAsc(Long orderBySpacesAsc) {
		this.orderBySpacesAsc = orderBySpacesAsc;
	}

	public Long getOrderByIdAsc() {
		return orderByIdAsc;
	}

	public void setOrderByIdAsc(Long orderByIdAsc) {
		this.orderByIdAsc = orderByIdAsc;
	}

	public Long getOrderBySpacesDesc() {
		return orderBySpacesDesc;
	}

	public void setOrderBySpacesDesc(Long orderBySpacesDesc) {
		this.orderBySpacesDesc = orderBySpacesDesc;
	}

	public Long getOrderByIdDesc() {
		return orderByIdDesc;
	}

	public void setOrderByIdDesc(Long orderByIdDesc) {
		this.orderByIdDesc = orderByIdDesc;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public List<AdSpace> getAdspaces() {
		return adspaces;
	}

	public void setAdspaces(List<AdSpace> adspaces) {
		this.adspaces = adspaces;
	}

	public Long getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Long spaceId) {
		this.spaceId = spaceId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<Long> getAllStatus() {
		return allStatus;
	}

	public void setAllStatus(List<Long> allStatus) {
		this.allStatus = allStatus;
	}
}
