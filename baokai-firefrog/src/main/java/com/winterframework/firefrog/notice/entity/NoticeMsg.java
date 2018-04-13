package com.winterframework.firefrog.notice.entity;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.user.entity.User;

public class NoticeMsg {

	private Long id;
	private AclUser operator;
	private List<User> receivers;
	private List<UserGroup> recGroup;
	private String title;
	private String content;
	private Date gmtCreated;
	private Date gmtSended;
	private Date gmtExpired;
	private Status status;
	private AclUser deleter;
	private SendType sendType;
	private Long effectHours;
	private String messagePush;

	public String getMessagePush() {
		return messagePush;
	}

	public void setMessagePush(String messagePush) {
		this.messagePush = messagePush;
	}

	public enum Status {
		NEW("new", 1), SENT("sent", 2), REVOCATION("revocation", 3), DELETE("delete", 4);
		private String name;
		private int index;

		private Status(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static Status getEnum(int index) {
			for (Status c : Status.values()) {
				if (c.getIndex() == index) {
					return c;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	public enum SendType {
		GROUP("group", 1), USER("user", 2);
		private String name;
		private int index;

		private SendType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static SendType getEnum(int index) {
			for (SendType c : SendType.values()) {
				if (c.getIndex() == index) {
					return c;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtSended() {
		return gmtSended;
	}

	public void setGmtSended(Date gmtSended) {
		this.gmtSended = gmtSended;
	}

	public Date getGmtExpired() {
		return gmtExpired;
	}

	public void setGmtExpired(Date gmtExpired) {
		this.gmtExpired = gmtExpired;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public SendType getSendType() {
		return sendType;
	}

	public void setSendType(SendType sendType) {
		this.sendType = sendType;
	}

	public Long getEffectHours() {
		return effectHours;
	}

	public void setEffectHours(Long effectHours) {
		this.effectHours = effectHours;
	}

	public AclUser getOperator() {
		return operator;
	}

	public void setOperator(AclUser operator) {
		this.operator = operator;
	}

	public AclUser getDeleter() {
		return deleter;
	}

	public void setDeleter(AclUser deleter) {
		this.deleter = deleter;
	}

	public List<User> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<User> receivers) {
		this.receivers = receivers;
	}

	public List<UserGroup> getRecGroup() {
		return recGroup;
	}

	public void setRecGroup(List<UserGroup> recGroup) {
		this.recGroup = recGroup;
	}

}
