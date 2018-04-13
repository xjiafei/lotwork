package com.winterframework.firefrog.advert.web.dto;

import com.winterframework.modules.validate.FFLength;


/** 
* @ClassName: AdSpaceStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-5 下午3:48:47 
*  
*/
public class AdSpaceStruc {
	private Long id;
	private Long width;
	private Long height;
	private Long paramId;
	@FFLength(min = 1, max = 20)
	private String name;
	private Long urlTarget;
	private String dftImg;
	private Long isUsed;
	private String dftImgs;
	private Long isDftUsed;
	private Integer allProcess;
	private Integer inProcess;
	private Integer noProcess;
	private Long pageId;
	private String paramName;
	public AdSpaceStruc() {
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

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
