package com.winterframework.firefrog.advert.web.dto;

import java.util.Date;

public class AdspaceRelationStruc {
	private Long id;

	/** 
	*  广告名称
	*/
	private String adName;
	/** 
	*  图片地址
	*/
	private String adImgUrl;
	/** 
	*  开始时间 
	*/
	private Date adGmtEffectBegin;
	/** 
	* 结束时间 
	*/
	private Date adGmtEffectEnd;
	/** 
	* 打开方式
	*/
	private String adTargetUrl;
	/** 
	*  广告状态 
	*/
	private Long adStatus;
	/** 
	*  广告id 
	*/
	private Long advertId;
	/** 
	*  广告位id 
	*/
	private Long adSpaceId;
	/** 
	*  顺序号 
	*/
	private Long orders;

	/** 
	*  是否暂停绑定
	*/
	private Long isShown;

	private Long rcAll;//全部站内用户
	private Long rcGuest;//有课	
	private Long rcTopAgent;//总代
	private Long rcOtAgent;//其他代理
	private Long rcVip;//vip
	private Long rcNonVip;//非vip
	private Long rcCustomer;//普通客户
	public AdspaceRelationStruc() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAdImgUrl() {
		return adImgUrl;
	}

	public void setAdImgUrl(String adImgUrl) {
		this.adImgUrl = adImgUrl;
	}

	public Date getAdGmtEffectBegin() {
		return adGmtEffectBegin;
	}

	public void setAdGmtEffectBegin(Date adGmtEffectBegin) {
		this.adGmtEffectBegin = adGmtEffectBegin;
	}

	public Date getAdGmtEffectEnd() {
		return adGmtEffectEnd;
	}

	public void setAdGmtEffectEnd(Date adGmtEffectEnd) {
		this.adGmtEffectEnd = adGmtEffectEnd;
	}

	public String getAdTargetUrl() {
		return adTargetUrl;
	}

	public void setAdTargetUrl(String adTargetUrl) {
		this.adTargetUrl = adTargetUrl;
	}

	public Long getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(Long adStatus) {
		this.adStatus = adStatus;
	}

	public Long getAdvertId() {
		return advertId;
	}

	public void setAdvertId(Long advertId) {
		this.advertId = advertId;
	}

	public Long getAdSpaceId() {
		return adSpaceId;
	}

	public void setAdSpaceId(Long adSpaceId) {
		this.adSpaceId = adSpaceId;
	}

	public Long getOrders() {
		return orders;
	}

	public void setOrders(Long orders) {
		this.orders = orders;
	}

	public Long getIsShown() {
		return isShown;
	}

	public void setIsShown(Long isShown) {
		this.isShown = isShown;
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
}
