package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class MessageSendRequest implements Serializable {

	private static final long serialVersionUID = 4623213459696632629L;

	private MessageRecipient[] receivesList;

	@NotNull
	@NotEmpty
	@FFLength(max = 16, min = 4)
	private String sendAccount;	               

	@NotNull
	@NotEmpty
	@FFLength(max = 30, min = 1)
	private String title;

	@NotNull
	@NotEmpty
	@FFLength(max = 300, min = 1)
	private String content;

	public MessageRecipient[] getReceivesList() {
		return receivesList;
	}

	public void setReceivesList(MessageRecipient[] receivesList) {
		this.receivesList = receivesList;
	}

	public String getSendAccount() {
		return sendAccount;
	}

	public void setSendAccount(String sendAccount) {
		this.sendAccount = sendAccount;
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
