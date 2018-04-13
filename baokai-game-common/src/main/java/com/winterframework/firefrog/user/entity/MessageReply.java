package com.winterframework.firefrog.user.entity;

/** 
 * @ClassName: MessageReply 
 * @Description: 用户消息回复 
 * @author Denny 
 * @date 2013-4-18 下午4:27:28 
 *  
 */
public class MessageReply extends Message {

	private Message root;

	private Message parent;

	public Message getRoot() {
		return root;
	}

	public void setRoot(Message root) {
		this.root = root;
	}

	public Message getParent() {
		return parent;
	}

	public void setParent(Message parent) {
		this.parent = parent;
	}
}
