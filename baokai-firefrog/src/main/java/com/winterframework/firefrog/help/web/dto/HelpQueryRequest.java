/**   
* @Title: HelpQueryRequest.java 
* @Package com.winterframework.firefrog.help.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-9-11 上午11:09:26 
* @version V1.0   
*/
package com.winterframework.firefrog.help.web.dto;

/** 
* @ClassName: HelpQueryRequest 
* @Description: 帮助查询Request
* @author floy 
* @date 2013-9-11 上午11:09:26 
*  
*/
public class HelpQueryRequest {

	private String keywords;
	private String like_match;
	private String title;
	
	private Long type;
	
	private Long cateId;
	
	private Long cateId2;
	
	private Long isRec;
	
	private String orderBy;
	
	public String getLike_match() {
		return like_match;
	}

	public void setLike_match(String like_match) {
		this.like_match = like_match;
	}

	/** 
	* 是否搜索高级选项0：不是，1：是 
	*/ 
	private Integer isSearchHotlottery;
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public Long getCateId2() {
		return cateId2;
	}

	public void setCateId2(Long cateId2) {
		this.cateId2 = cateId2;
	}

	public Long getIsRec() {
		return isRec;
	}

	public void setIsRec(Long isRec) {
		this.isRec = isRec;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getIsSearchHotlottery() {
		return isSearchHotlottery;
	}

	public void setIsSearchHotlottery(Integer isSearchHotlottery) {
		this.isSearchHotlottery = isSearchHotlottery;
	}
}
