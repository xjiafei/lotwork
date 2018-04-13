/**   
* @Title: UserNoticeTask.java 
* @Package com.winterframework.firefrog.notice.entity 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-29 上午9:51:11 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.entity;

/** 
* @ClassName: UserNoticeTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-29 上午9:51:11 
*  
*/
public class UserNoticeTask {

	private String module;
	private String task;
	private Long activated;
	private Long innerMsgActivated;
	private Long innerMsgUsed;
	private Long emailActivated;
	private Long emailUsed;
	private Long noteActivated;
	private Long noteUsed;
	private Long emsActivated;
	private Long emsUsed;
	private Long userId;
	private Long id;
	private Long smsActivated;
	public String getModule() {
		return module;
	}

	public Long getSmsActivated() {
		return smsActivated;
	}

	public void setSmsActivated(Long smsActivated) {
		this.smsActivated = smsActivated;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Long getActivated() {
		return activated;
	}

	public void setActivated(Long activated) {
		this.activated = activated;
	}

	public Long getInnerMsgActivated() {
		return innerMsgActivated;
	}

	public void setInnerMsgActivated(Long innerMsgActivated) {
		this.innerMsgActivated = innerMsgActivated;
	}

	public Long getInnerMsgUsed() {
		return innerMsgUsed;
	}

	public void setInnerMsgUsed(Long innerMsgUsed) {
		this.innerMsgUsed = innerMsgUsed;
	}

	public Long getEmailActivated() {
		return emailActivated;
	}

	public void setEmailActivated(Long emailActivated) {
		this.emailActivated = emailActivated;
	}

	public Long getEmailUsed() {
		return emailUsed;
	}

	public void setEmailUsed(Long emailUsed) {
		this.emailUsed = emailUsed;
	}

	public Long getNoteActivated() {
		return noteActivated;
	}

	public void setNoteActivated(Long noteActivated) {
		this.noteActivated = noteActivated;
	}

	public Long getNoteUsed() {
		return noteUsed;
	}

	public void setNoteUsed(Long noteUsed) {
		this.noteUsed = noteUsed;
	}

	public Long getEmsActivated() {
		return emsActivated;
	}

	public void setEmsActivated(Long emsActivated) {
		this.emsActivated = emsActivated;
	}

	public Long getEmsUsed() {
		return emsUsed;
	}

	public void setEmsUsed(Long emsUsed) {
		this.emsUsed = emsUsed;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
