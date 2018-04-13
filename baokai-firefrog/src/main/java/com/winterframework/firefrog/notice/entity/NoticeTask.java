/**   
* @Title: NoticeTask.java 
* @Package NoticeTaskVO 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-28 上午9:37:02 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.entity;

/** 
* @ClassName: NoticeTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-28 上午9:37:02 
*  
*/
public class NoticeTask {

	private String module;
	private String task;
	private Boolean activated;
	private Boolean innerMsgActivated;
	private Boolean innerMsgUsed;
	private Boolean emailActivated;
	private Boolean emailUsed;
	private Boolean noteActivated;
	private Boolean noteUsed;
	private Boolean emsActivated;
	private Boolean emsUsed;
	private String innerMsgTemp;
	private String emailTemp;
	private String noteTemp;
	private String emsTemp;
	private String innerMsgTitle;
	private String emailTitle;
	private Boolean setByUser;
	
	private Long id;

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

	public String getEmsTemp() {
		return emsTemp;
	}

	public void setEmsTemp(String emsTemp) {
		this.emsTemp = emsTemp;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Boolean getInnerMsgActivated() {
		return innerMsgActivated;
	}

	public void setInnerMsgActivated(Boolean innerMsgActivated) {
		this.innerMsgActivated = innerMsgActivated;
	}

	public Boolean getInnerMsgUsed() {
		return innerMsgUsed;
	}

	public void setInnerMsgUsed(Boolean innerMsgUsed) {
		this.innerMsgUsed = innerMsgUsed;
	}

	public Boolean getEmailActivated() {
		return emailActivated;
	}

	public void setEmailActivated(Boolean emailActivated) {
		this.emailActivated = emailActivated;
	}

	public Boolean getEmailUsed() {
		return emailUsed;
	}

	public void setEmailUsed(Boolean emailUsed) {
		this.emailUsed = emailUsed;
	}

	public Boolean getNoteActivated() {
		return noteActivated;
	}

	public void setNoteActivated(Boolean noteActivated) {
		this.noteActivated = noteActivated;
	}

	public Boolean getNoteUsed() {
		return noteUsed;
	}

	public void setNoteUsed(Boolean noteUsed) {
		this.noteUsed = noteUsed;
	}

	public Boolean getEmsActivated() {
		return emsActivated;
	}

	public void setEmsActivated(Boolean emsActivated) {
		this.emsActivated = emsActivated;
	}

	public Boolean getEmsUsed() {
		return emsUsed;
	}

	public void setEmsUsed(Boolean emsUsed) {
		this.emsUsed = emsUsed;
	}

	public Boolean getSetByUser() {
		return setByUser;
	}

	public void setSetByUser(Boolean setByUser) {
		this.setByUser = setByUser;
	}
}
