/**   
* @Title: Ad.java 
* @Package com.winterframework.firefrog.advert.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-4 下午3:54:59 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.entity;

import java.util.Date;
import java.util.List;

/** 
* @ClassName: Ad 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-4 下午3:54:59 
*  
*/
public class Advert {
	private Long id;
	private Long spaces;
	private String name;
	private String imgUrl;
	private Date gmtEffectBegin;
	private Date gmtEffectEnd;
	private String targetUrl;
	private Status statuss;
	private String memo;
	private String approver;
	private String operator;
	private Date gmtAppr;
	private Long orders;
	private Long rcAll;
	private Long rcGuest;
	private Long rcTopAgent;
	private Long rcOtAgent;
	private Long rcVip;
	private Long rcNonVip;
	private Long rcCustomer;

	private List<AdSpace> adspaces;

	//对应关系中的顺序号以及是否绑定
	private Long reOrders;
	private Long isShown;
	
	public Advert(Long advertId) {
		this.id=advertId;
	}

	public Advert() {
	}
	
	public enum Status {
		NEW("audit", 1), AUDIT("pass", 2), PASS("refuse", 3), REFUSE("new", 4), DELETE("delete", 5);
		private String name;
		private int index;

		private Status(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static Status getEnum(int index) {
			for (Status c : Status.values()) {
				if (c.getIndex() == index) {
					return c;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

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

	public Status getStatuss() {
		return statuss;
	}

	public void setStatuss(Status statuss) {
		this.statuss = statuss;
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

	public List<AdSpace> getAdspaces() {
		return adspaces;
	}

	public void setAdspaces(List<AdSpace> adspaces) {
		this.adspaces = adspaces;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getReOrders() {
		return reOrders;
	}

	public void setReOrders(Long reOrders) {
		this.reOrders = reOrders;
	}

	public Long getIsShown() {
		return isShown;
	}

	public void setIsShown(Long isShown) {
		this.isShown = isShown;
	}
}
