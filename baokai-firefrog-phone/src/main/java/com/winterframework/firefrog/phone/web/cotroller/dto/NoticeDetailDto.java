package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

public class NoticeDetailDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3003504303292374721L;
	private Long id;//	公告id
	private String sendtime;//	公布时间
	private String subject;//	标题
	private String content;//	内容
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
