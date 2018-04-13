/**   
* @Title: EmailDto.java 
* @Package com.winterframework.firefrog.notice.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-24 下午2:55:13 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.web.dto;

/** 
* @ClassName: EmailDto 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-24 下午2:55:13 
*  
*/
public class EmailDto {

	private String sendEmail;

	private String rcvEmail;

	private String title;

	private String content;

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getRcvEmail() {
		return rcvEmail;
	}

	public void setRcvEmail(String rcvEmail) {
		this.rcvEmail = rcvEmail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
