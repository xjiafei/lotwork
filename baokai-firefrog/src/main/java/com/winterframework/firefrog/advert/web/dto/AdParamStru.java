package com.winterframework.firefrog.advert.web.dto;



/** 
* @ClassName: AdParamStru 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-8 上午10:41:13 
*  
*/
public class AdParamStru {
	private Long id;
	private String pageMemo;
	private String name;
	private String position;
	private Long width;
	private Long height;

	public AdParamStru() {
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
