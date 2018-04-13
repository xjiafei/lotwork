/**   
 * @Title: MessageTopic.java 
 * @Package com.winterframework.firefrog.user.entity 
 * @Description:  
 * @author Denny   
 * @date 2013-4-18 下午4:26:42 
 * @version V1.0   
 */
package com.winterframework.firefrog.user.entity;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: MessageTopic
 * @Description: 用户消息实体bean。此类为主消息，带有主题
 * @author Denny
 * @date 2013-4-18 下午4:26:42
 * 
 */
public class MessageTopic extends Message {
	public enum MessageType {
		SystemMsg(1), NormalMsg(2),DELETE(-1),MANYSEND(-2);

		private MessageType(int type) {
			this.type = type;
		}

		@SuppressWarnings("unused")
		private int type;

		public int getIntegerValue() {
			return type;
		}
	}

	private String title;

	private MessageType type;

	private Boolean isSenderRead;

	private Boolean isReceiverRead;

	private Date readTime;

	private String msgRoute;

	//-1，发送者删除，不可见；-2，未被回复的群发消息(生成1+n条记录，n条消息中均对发送者不可见)；
	//其它值，表示从此条消息开始可见
	private Long senderFrom;

	//-1，发送者删除，不可见；
	private Long receiverFrom;

	private User owner;

	private List<MessageReply> replys;

	private Date gmtModified;
	
	private Date effectHours;
	
	private Long noticeMsgId;
	
	private String messagePush;

	public String getMessagePush() {
		return messagePush;
	}

	public void setMessagePush(String messagePush) {
		this.messagePush = messagePush;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public Boolean isSenderRead() {
		return isSenderRead;
	}

	public void setSenderRead(Boolean isSenderRead) {
		this.isSenderRead = isSenderRead;
	}

	public Boolean isReceiverRead() {
		return isReceiverRead;
	}

	public void setReceiverRead(Boolean isReceiverRead) {
		this.isReceiverRead = isReceiverRead;
	}

	public String getMsgRoute() {
		return msgRoute;
	}

	public void setMsgRoute(String msgRoute) {
		this.msgRoute = msgRoute;
	}

	public Long getSenderFrom() {
		return senderFrom;
	}

	public void setSenderFrom(Long senderFrom) {
		this.senderFrom = senderFrom;
	}

	public Long getReceiverFrom() {
		return receiverFrom;
	}

	public void setReceiverFrom(Long receiverFrom) {
		this.receiverFrom = receiverFrom;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public List<MessageReply> getReplys() {
		return replys;
	}

	public void setReplys(List<MessageReply> replys) {
		this.replys = replys;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getEffectHours() {
		return effectHours;
	}

	public void setEffectHours(Date effectHours) {
		this.effectHours = effectHours;
	}

	public Long getNoticeMsgId() {
		return noticeMsgId;
	}

	public void setNoticeMsgId(Long noticeMsgId) {
		this.noticeMsgId = noticeMsgId;
	}
}
