package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserMsgDetailResponse implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -756807039679765263L;
	private String entry;//	信息id
	private Integer iskeep;//	状态
	private String content;//	内容
	private String subject;//	标题
	private String title;//	类型
	private String sendtime;//	时间
	private Integer unread;
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public Integer getUnread() {
		return unread;
	}
	public void setUnread(Integer unread) {
		this.unread = unread;
	}
	public Integer getIskeep() {
		return iskeep;
	}
	public void setIskeep(Integer iskeep) {
		this.iskeep = iskeep;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

}
