package com.winterframework.firefrog.advert.web.dto;

public class AdNoticeView {
	
	private Long id;
	
	private String title;
	
	private String type;
	
	private String showPages;
	
	private String showGroups;
	
	private String gmtEffectBegin;
	
	private String gmtEffectEnd;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShowPages() {
		return showPages;
	}

	public void setShowPages(String showPages) {
		this.showPages = showPages;
	}

	public String getShowGroups() {
		return showGroups;
	}

	public void setShowGroups(String showGroups) {
		this.showGroups = showGroups;
	}

	public String getGmtEffectBegin() {
		return gmtEffectBegin;
	}

	public void setGmtEffectBegin(String gmtEffectBegin) {
		this.gmtEffectBegin = gmtEffectBegin;
	}

	public String getGmtEffectEnd() {
		return gmtEffectEnd;
	}

	public void setGmtEffectEnd(String gmtEffectEnd) {
		this.gmtEffectEnd = gmtEffectEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
