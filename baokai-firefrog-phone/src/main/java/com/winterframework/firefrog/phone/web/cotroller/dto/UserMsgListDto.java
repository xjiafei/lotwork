package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class UserMsgListDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2269923968925943891L;
	private String entry;//	信息id
	private Long iskeep;//	状态
	private String subject;//	标题
	private String title;//	类型
	private String sendtime;//	时间
	private Integer isRead;
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public Long getIskeep() {
		return iskeep;
	}
	public void setIskeep(Long iskeep) {
		this.iskeep = iskeep;
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
