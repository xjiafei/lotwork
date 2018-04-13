/**   
* @Title: NoticeTaskStruc.java 
* @Package com.winterframework.firefrog.notice.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 下午1:23:55 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.web.dto;

/** 
* @ClassName: NoticeTaskStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 下午1:23:55 
*  
*/
public class NoticeTaskStruc {

	private Long id;
	
	private String module;
	
	private String task;
	
	private Long actived;
	
	private Long innerMsgActived;
	
	private Long innerMsgUsed;
	
	private Long emailActived;
	
	private Long emailUsed;
	
	private Long noteActived;
	
	private Long noteUsed;
	
	private Long smsActived;
	
	private Long smsUsed;
	
	private String innerMsgTemp;
	
	private String emailTemp;
	
	private String noteTemp;
	
	private String smsTemp;
	
	private Long setByUser;
	
	private String innerMsgTitle;
	
	private String emailTitle;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModule() {
		return module;
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

	public Long getActived() {
		return actived;
	}

	public void setActived(Long actived) {
		this.actived = actived;
	}

	public Long getInnerMsgActived() {
		return innerMsgActived;
	}

	public void setInnerMsgActived(Long innerMsgActived) {
		this.innerMsgActived = innerMsgActived;
	}

	public Long getInnerMsgUsed() {
		return innerMsgUsed;
	}

	public void setInnerMsgUsed(Long innerMsgUsed) {
		this.innerMsgUsed = innerMsgUsed;
	}

	public Long getEmailActived() {
		return emailActived;
	}

	public void setEmailActived(Long emailActived) {
		this.emailActived = emailActived;
	}

	public Long getEmailUsed() {
		return emailUsed;
	}

	public void setEmailUsed(Long emailUsed) {
		this.emailUsed = emailUsed;
	}

	public Long getNoteActived() {
		return noteActived;
	}

	public void setNoteActived(Long noteActived) {
		this.noteActived = noteActived;
	}

	public Long getNoteUsed() {
		return noteUsed;
	}

	public void setNoteUsed(Long noteUsed) {
		this.noteUsed = noteUsed;
	}

	public Long getSmsActived() {
		return smsActived;
	}

	public void setSmsActived(Long smsActived) {
		this.smsActived = smsActived;
	}

	public Long getSmsUsed() {
		return smsUsed;
	}

	public void setSmsUsed(Long smsUsed) {
		this.smsUsed = smsUsed;
	}

	public String getInnerMsgTemp() {
		return innerMsgTemp;
	}

	public void setInnerMsgTemp(String innerMsgTemp) {
		this.innerMsgTemp = innerMsgTemp;
	}

	public String getEmailTemp() {
		return emailTemp;
	}

	public void setEmailTemp(String emailTemp) {
		this.emailTemp = emailTemp;
	}

	public String getNoteTemp() {
		return noteTemp;
	}

	public void setNoteTemp(String noteTemp) {
		this.noteTemp = noteTemp;
	}

	public String getSmsTemp() {
		return smsTemp;
	}

	public void setSmsTemp(String smsTemp) {
		this.smsTemp = smsTemp;
	}

	public Long getSetByUser() {
		return setByUser;
	}

	public void setSetByUser(Long setByUser) {
		this.setByUser = setByUser;
	}

	public String getInnerMsgTitle() {
		return innerMsgTitle;
	}

	public void setInnerMsgTitle(String innerMsgTitle) {
		this.innerMsgTitle = innerMsgTitle;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}
}
