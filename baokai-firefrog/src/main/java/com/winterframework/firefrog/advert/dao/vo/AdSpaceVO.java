/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.advert.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AdSpaceVO extends BaseEntity {

	private static final long serialVersionUID = -5743308719162593121L;
	//alias
	public static final String TABLE_ALIAS = "AdSpace";
	public static final String ALIAS_AD_PARAM_ID = "广告位参数id";
	public static final String ALIAS_WIDTH = "宽度";
	public static final String ALIAS_HEIGHT = "高度";
	public static final String ALIAS_NAME = "位置名称";
	public static final String ALIAS_URL_TARGET = "url打开参数  1）新窗口 2）老窗口";
	public static final String ALIAS_DFT_IMG = "当前占位图";
	public static final String ALIAS_IS_USED = "1)启用  0）不可用";
	public static final String ALIAS_PAGE_ID = "页面id";
	public static final String ALIAS_DFT_IMGS = "所有占位图";
	public static final String ALIAS_IS_DFT_USED = "1开启  0未开启";

	//date formats

	//columns START
	private Long adParamId;
	private Long width;
	private Long height;
	private String name;
	private Long urlTarget;
	private String dftImg;
	private Long isUsed;
	private Long pageId;
	private String dftImgs;
	private Long isDftUsed;
	
	private Integer allProcess;
	private Integer inProcess;
	private Integer noProcess;
	
	//columns END

	public AdSpaceVO() {
	}

	public AdSpaceVO(Long id) {
		this.id = id;
	}

	public void setAdParamId(Long value) {
		this.adParamId = value;
	}

	public Long getAdParamId() {
		return this.adParamId;
	}

	public void setWidth(Long value) {
		this.width = value;
	}

	public Long getWidth() {
		return this.width;
	}

	public void setHeight(Long value) {
		this.height = value;
	}

	public Long getHeight() {
		return this.height;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setUrlTarget(Long value) {
		this.urlTarget = value;
	}

	public Long getUrlTarget() {
		return this.urlTarget;
	}

	public void setDftImg(String value) {
		this.dftImg = value;
	}

	public String getDftImg() {
		return this.dftImg;
	}

	public void setIsUsed(Long value) {
		this.isUsed = value;
	}

	public Long getIsUsed() {
		return this.isUsed;
	}

	public void setPageId(Long value) {
		this.pageId = value;
	}

	public Long getPageId() {
		return this.pageId;
	}

	public void setDftImgs(String value) {
		this.dftImgs = value;
	}

	public String getDftImgs() {
		return this.dftImgs;
	}

	public void setIsDftUsed(Long value) {
		this.isDftUsed = value;
	}

	public Long getIsDftUsed() {
		return this.isDftUsed;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("AdParamId", getAdParamId())
				.append("Width", getWidth()).append("Height", getHeight()).append("Name", getName())
				.append("UrlTarget", getUrlTarget()).append("DftImg", getDftImg()).append("IsUsed", getIsUsed())
				.append("PageId", getPageId()).append("DftImgs", getDftImgs()).append("IsDftUsed", getIsDftUsed())
				.toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getAdParamId()).append(getWidth()).append(getHeight())
				.append(getName()).append(getUrlTarget()).append(getDftImg()).append(getIsUsed()).append(getPageId())
				.append(getDftImgs()).append(getIsDftUsed()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AdSpaceVO == false)
			return false;
		if (this == obj)
			return true;
		AdSpaceVO other = (AdSpaceVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getAdParamId(), other.getAdParamId())

		.append(getWidth(), other.getWidth())

		.append(getHeight(), other.getHeight())

		.append(getName(), other.getName())

		.append(getUrlTarget(), other.getUrlTarget())

		.append(getDftImg(), other.getDftImg())

		.append(getIsUsed(), other.getIsUsed())

		.append(getPageId(), other.getPageId())

		.append(getDftImgs(), other.getDftImgs())

		.append(getIsDftUsed(), other.getIsDftUsed())

		.isEquals();
	}

	public Integer getAllProcess() {
		return allProcess;
	}

	public void setAllProcess(Integer allProcess) {
		this.allProcess = allProcess;
	}

	public Integer getInProcess() {
		return inProcess;
	}

	public void setInProcess(Integer inProcess) {
		this.inProcess = inProcess;
	}

	public Integer getNoProcess() {
		return noProcess;
	}

	public void setNoProcess(Integer noProcess) {
		this.noProcess = noProcess;
	}
}
