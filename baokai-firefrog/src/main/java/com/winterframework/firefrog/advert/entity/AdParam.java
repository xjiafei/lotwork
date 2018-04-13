package com.winterframework.firefrog.advert.entity;




public class AdParam {

	private Long id;
	//columns START
	private String pageMemo;
	private String name;
	private String position;
	private Long width;
	private Long height;

	//columns END

	public AdParam() {
	}
	public AdParam(Long paramId) {
		this.id=paramId;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setPosition(String value) {
		this.position = value;
	}

	public String getPosition() {
		return this.position;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPageMemo() {
		return pageMemo;
	}

	public void setPageMemo(String pageMemo) {
		this.pageMemo = pageMemo;
	}
}
