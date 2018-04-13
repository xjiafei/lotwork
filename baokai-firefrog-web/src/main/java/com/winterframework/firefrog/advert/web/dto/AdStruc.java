/**   
* @Title: AdStruc.java 
* @Package com.winterframework.firefrog.advert.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-5 下午3:23:22 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/** 
* @ClassName: AdStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-5 下午3:23:22 
*  
*/
public class AdStruc {

	private Long id;

	private Long spaces;
	
	@NotNull
	@Length(min = 1, max = 20)
	private String name;

	private List<Long> spacesIds;

	@NotNull
	private String imgUrl;
	
	@NotNull
	private Long gmtEffectBegin;

	@NotNull
	private Long gmtEffectEnd;

	private Long status;

	private String memo;

	private String approver;
	
	private String operator;

	private Long gmtAppr;

	private Long rcAll;
	private Long rcGuest;
	private Long rcTopAgent;
	private Long rcOtAgent;
	private Long rcVip;
	private Long rcNonVip;
	private Long rcCustomer;
	
	private String targetUrl;
	
	private Long width;
	private Long height;

	public String getEffectBeginStr() {
		String effectBeginStr = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (this.getGmtEffectBegin() != null) {
			Date date = new Date(this.getGmtEffectBegin());
			effectBeginStr = sf.format(date);
		}
		return effectBeginStr;
	}

	public String getEffectEndStr() {
		String effectEndStr = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (this.getGmtEffectEnd() != null) {
			Date date = new Date(this.getGmtEffectEnd());
			effectEndStr = sf.format(date);
		}
		return effectEndStr;
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

	public Long getGmtEffectBegin() {
		return gmtEffectBegin;
	}

	public void setGmtEffectBegin(Long gmtEffectBegin) {
		this.gmtEffectBegin = gmtEffectBegin;
	}

	public Long getGmtEffectEnd() {
		return gmtEffectEnd;
	}

	public void setGmtEffectEnd(Long gmtEffectEnd) {
		this.gmtEffectEnd = gmtEffectEnd;
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

	public Long getGmtAppr() {
		return gmtAppr;
	}

	public void setGmtAppr(Long gmtAppr) {
		this.gmtAppr = gmtAppr;
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

	public List<Long> getSpacesIds() {
		return spacesIds;
	}

	public void setSpacesIds(List<Long> spacesIds) {
		this.spacesIds = spacesIds;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}
}
