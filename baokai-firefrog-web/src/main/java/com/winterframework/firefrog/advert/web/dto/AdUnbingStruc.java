package com.winterframework.firefrog.advert.web.dto;


public class AdUnbingStruc {
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

	public AdUnbingStruc(long l) {
		this.adSpaceId=l;
	}

	public AdUnbingStruc() {
		// TODO Auto-generated constructor stub
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
}
