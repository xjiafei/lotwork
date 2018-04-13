/**   
* @Title: NoticeStruc.java 
* @Package com.winterframework.firefrog.notice.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-18 上午10:46:28 
* @version V1.0   
*/
package com.winterframework.firefrog.json.api.web.controller;


/** 
* @ClassName: NoticeStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-18 上午10:46:28 
*  
*/
public class NoticeStruc {
	private Long userId;
	private String noticeTime;
	private String url;
	private String text;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
