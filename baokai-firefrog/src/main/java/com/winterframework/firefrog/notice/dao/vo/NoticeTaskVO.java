/**   
* @Title: NoticeTaskVO.java 
* @Package com.winterframework.firefrog.notice.dao.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-25 下午5:51:39 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.dao.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName: NoticeTaskVO 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-25 下午5:51:39 
*  
*/
public class NoticeTaskVO extends BaseEntity {
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = -67654678788L;
	//alias
	public static final String TABLE_ALIAS = "NoticeTask";
	public static final String ALIAS_MODULE = "模块 信息变动，资金变动";
	public static final String ALIAS_TASK = "任务";
	public static final String ALIAS_ACTIVATED = "1)启用 0)禁用 通知任务";
	public static final String ALIAS_INNER_MSG_ACTIVATED = "1)启用 0)禁用 站内信";
	public static final String ALIAS_INNER_MSG_USED = "1)可用 0)不可用 站内信";
	public static final String ALIAS_EMAIL_ACTIVATED = "1)启用 0)禁用 邮箱";
	public static final String ALIAS_EMAIL_USED = "1)可用 0)不可用 邮箱";
	public static final String ALIAS_NOTE_ACTIVATED = "1)启用 0)禁用 桌面通知";
	public static final String ALIAS_NOTE_USED = "1)可用 0)不可用 桌面通知";
	public static final String ALIAS_EMS_ACTIVATED = "1)启用 0)禁用 短信";
	public static final String ALIAS_EMS_USED = "1)可用 0)不可用 短信";
	public static final String ALIAS_INNER_MSG_TEMP = "站内信模板";
	public static final String ALIAS_EMAIL_TEMP = "email模板";
	public static final String ALIAS_NOTE_TEMP = "桌面通知模板";
	public static final String ALIAS_EMS_TEMP = "短信模板";
	public static final String ALIAS_INNER_MSG_TITLE = "站内信标题";
	public static final String ALIAS_EMAIL_TITLE = "email标题";
	public static final String ALIAS_SET_BY_USER = "1)用户可设置  2）用户不可设置";

	//date formats

	//columns START
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
	private String innerMsgTemp;
	private String emailTemp;
	private String noteTemp;
	private String emsTemp;
	private String innerMsgTitle;
	private String emailTitle;
	private Long setByUser;

	//columns END

	public NoticeTaskVO() {
	}

	public NoticeTaskVO(Long id) {
		this.id = id;
	}

	public void setModule(String value) {
		this.module = value;
	}

	public String getModule() {
		return this.module;
	}

	public void setTask(String value) {
		this.task = value;
	}

	public String getTask() {
		return this.task;
	}

	public void setActivated(Long value) {
		this.activated = value;
	}

	public Long getActivated() {
		return this.activated;
	}

	public void setInnerMsgActivated(Long value) {
		this.innerMsgActivated = value;
	}

	public Long getInnerMsgActivated() {
		return this.innerMsgActivated;
	}

	public void setInnerMsgUsed(Long value) {
		this.innerMsgUsed = value;
	}

	public Long getInnerMsgUsed() {
		return this.innerMsgUsed;
	}

	public void setEmailActivated(Long value) {
		this.emailActivated = value;
	}

	public Long getEmailActivated() {
		return this.emailActivated;
	}

	public void setEmailUsed(Long value) {
		this.emailUsed = value;
	}

	public Long getEmailUsed() {
		return this.emailUsed;
	}

	public void setNoteActivated(Long value) {
		this.noteActivated = value;
	}

	public Long getNoteActivated() {
		return this.noteActivated;
	}

	public void setNoteUsed(Long value) {
		this.noteUsed = value;
	}

	public Long getNoteUsed() {
		return this.noteUsed;
	}

	public void setEmsActivated(Long value) {
		this.emsActivated = value;
	}

	public Long getEmsActivated() {
		return this.emsActivated;
	}

	public void setEmsUsed(Long value) {
		this.emsUsed = value;
	}

	public Long getEmsUsed() {
		return this.emsUsed;
	}

	public void setInnerMsgTemp(String value) {
		this.innerMsgTemp = value;
	}

	public String getInnerMsgTemp() {
		return this.innerMsgTemp;
	}

	public void setEmailTemp(String value) {
		this.emailTemp = value;
	}

	public String getEmailTemp() {
		return this.emailTemp;
	}

	public void setNoteTemp(String value) {
		this.noteTemp = value;
	}

	public String getNoteTemp() {
		return this.noteTemp;
	}

	public void setEmsTemp(String value) {
		this.emsTemp = value;
	}

	public String getEmsTemp() {
		return this.emsTemp;
	}

	public void setInnerMsgTitle(String value) {
		this.innerMsgTitle = value;
	}

	public String getInnerMsgTitle() {
		return this.innerMsgTitle;
	}

	public void setEmailTitle(String value) {
		this.emailTitle = value;
	}

	public String getEmailTitle() {
		return this.emailTitle;
	}

	public void setSetByUser(Long value) {
		this.setByUser = value;
	}

	public Long getSetByUser() {
		return this.setByUser;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Module", getModule()).append("Task", getTask())
				.append("Activated", getActivated()).append("InnerMsgActivated", getInnerMsgActivated())
				.append("InnerMsgUsed", getInnerMsgUsed()).append("EmailActivated", getEmailActivated())
				.append("EmailUsed", getEmailUsed()).append("NoteActivated", getNoteActivated())
				.append("NoteUsed", getNoteUsed()).append("EmsActivated", getEmsActivated())
				.append("EmsUsed", getEmsUsed()).append("InnerMsgTemp", getInnerMsgTemp())
				.append("EmailTemp", getEmailTemp()).append("NoteTemp", getNoteTemp()).append("EmsTemp", getEmsTemp())
				.append("InnerMsgTitle", getInnerMsgTitle()).append("EmailTitle", getEmailTitle())
				.append("SetByUser", getSetByUser()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getModule()).append(getTask()).append(getActivated())
				.append(getInnerMsgActivated()).append(getInnerMsgUsed()).append(getEmailActivated())
				.append(getEmailUsed()).append(getNoteActivated()).append(getNoteUsed()).append(getEmsActivated())
				.append(getEmsUsed()).append(getInnerMsgTemp()).append(getEmailTemp()).append(getNoteTemp())
				.append(getEmsTemp()).append(getInnerMsgTitle()).append(getEmailTitle()).append(getSetByUser())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NoticeTaskVO == false)
			return false;
		if (this == obj)
			return true;
		NoticeTaskVO other = (NoticeTaskVO) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getModule(), other.getModule())

		.append(getTask(), other.getTask())

		.append(getActivated(), other.getActivated())

		.append(getInnerMsgActivated(), other.getInnerMsgActivated())

		.append(getInnerMsgUsed(), other.getInnerMsgUsed())

		.append(getEmailActivated(), other.getEmailActivated())

		.append(getEmailUsed(), other.getEmailUsed())

		.append(getNoteActivated(), other.getNoteActivated())

		.append(getNoteUsed(), other.getNoteUsed())

		.append(getEmsActivated(), other.getEmsActivated())

		.append(getEmsUsed(), other.getEmsUsed())

		.append(getInnerMsgTemp(), other.getInnerMsgTemp())

		.append(getEmailTemp(), other.getEmailTemp())

		.append(getNoteTemp(), other.getNoteTemp())

		.append(getEmsTemp(), other.getEmsTemp())

		.append(getInnerMsgTitle(), other.getInnerMsgTitle())

		.append(getEmailTitle(), other.getEmailTitle())

		.append(getSetByUser(), other.getSetByUser())

		.isEquals();
	}
}
