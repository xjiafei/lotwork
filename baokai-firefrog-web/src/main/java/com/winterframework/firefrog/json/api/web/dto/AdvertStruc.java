/**   
* @Title: Advert.java 
* @Package com.winterframework.firefrog.json.api.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-24 上午10:06:36 
* @version V1.0   
*/
package com.winterframework.firefrog.json.api.web.dto;

/** 
* @ClassName: Advert 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-24 上午10:06:36 
*  
*/
public class AdvertStruc {
	private Long id;
	private String src;
	private String link;
	private String title;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
