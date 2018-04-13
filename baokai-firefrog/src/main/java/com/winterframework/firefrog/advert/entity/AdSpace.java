/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.advert.entity;

import java.util.List;



/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class AdSpace{
	//columns START
	private Long id;
	private AdParam adParam;
	private Long width;
	private Long height;
	private String name;
	private Long urlTarget;
	private String dftImg;
	private Long isUsed;
	private AdPage adPage;
	private String dftImgs;
	private Long isDftUsed;
	private Integer allProcess;
	private Integer inProcess;
	private Integer noProcess;
	//columns END
	
	List<Advert> advertList;

	public AdSpace() {
	}

	public AdSpace(Long id) {
		this.setId(id);
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AdParam getAdParam() {
		return adParam;
	}

	public void setAdParam(AdParam adParam) {
		this.adParam = adParam;
	}

	public Integer getAllProcess() {
		return allProcess;
	}

	public void setAllProcess(Integer allProcess) {
		this.allProcess = (allProcess == null ? 0 : allProcess);
	}

	public Integer getInProcess() {
		return inProcess;
	}

	public void setInProcess(Integer inProcess) {
		this.inProcess = (inProcess == null ? 0 : inProcess);
	}

	public Integer getNoProcess() {
		return noProcess;
	}

	public void setNoProcess(Integer noProcess) {
		this.noProcess = (noProcess == null ? 0 : noProcess);
	}

	public AdPage getAdPage() {
		return adPage;
	}

	public void setAdPage(AdPage adPage) {
		this.adPage = adPage;
	}

	public List<Advert> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<Advert> advertList) {
		this.advertList = advertList;
	}
}
