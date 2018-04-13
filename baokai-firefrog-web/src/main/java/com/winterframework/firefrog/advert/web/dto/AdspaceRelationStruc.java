package com.winterframework.firefrog.advert.web.dto;

import java.util.Date;

import com.winterframework.firefrog.common.util.DateUtils;


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

	public String getAdGmtEffectBegin() {
		return DateUtils.format(adGmtEffectBegin, "yyyy-MM-dd hh:mm:ss");
	}

	public void setAdGmtEffectBegin(Date adGmtEffectBegin) {
		this.adGmtEffectBegin = adGmtEffectBegin;
	}

	public String getAdGmtEffectEnd() {
		return DateUtils.format(adGmtEffectEnd, "yyyy-MM-dd hh:mm:ss");
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

	public String getAdStatus() {
		//1)已提交审核  2）审核通过 3）审核未通过 4 ) 待审核 5）已删除
		String aimStr ="";
		switch (Integer.valueOf(adStatus.toString())) {
		case 1:
			aimStr = "审核中";
			break;
		case 2:
			aimStr = "已通过";
			break;
		case 3:
			aimStr = "未通过";
			break;
		case 4:
			aimStr = "待审核";
			break;
		case 5:
			aimStr = "已删除";
			break;
		default:
		}
		return aimStr;
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
	
	public String getShowGroup()
 {
		String aimStr = "";
		if (this.rcAll != null && this.rcAll == 1L) {
			aimStr += "全部站内用户；";
		}

		if (this.rcGuest != null && this.rcGuest == 1L) {
			aimStr += "游客；";
		}
		if (this.rcTopAgent != null && this.rcTopAgent == 1L) {
			aimStr += "总代；";
		}
		if (this.rcOtAgent != null && this.rcOtAgent == 1L) {
			aimStr += "其他代理；";
		}
		if (this.rcVip != null && this.rcVip == 1L) {
			aimStr += "vip；";
		}
		if (this.rcNonVip != null && this.rcNonVip == 1L) {
			aimStr += "非vip";
		}
		if (this.rcCustomer != null && this.rcCustomer == 1L) {
			aimStr += "普通客户";
		}
		return aimStr;
	}
	
	public String getShowStatus() {
		Date currentDate = new Date();
		if (currentDate.before(this.adGmtEffectBegin)) {
			return "未开始";
		}
		if (currentDate.after(this.adGmtEffectBegin) || currentDate.before(this.adGmtEffectEnd)) {
			return "进行中";
		}
		if (currentDate.after(this.adGmtEffectEnd)) {
			return "已结束";
		}
		return "";
	}
	
}
