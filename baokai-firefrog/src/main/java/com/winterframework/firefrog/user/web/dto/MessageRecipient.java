package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.winterframework.modules.validate.FFLength;

public class MessageRecipient implements Serializable {

	private static final long serialVersionUID = -373139940046532904L;

	@Min(1)
	private long receiveId;

	@NotNull
	@NotEmpty
	@FFLength(max = 16, min = 6)
	private String receiveAccount;

	public long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(long receiveId) {
		this.receiveId = receiveId;
	}

	public String getReceiveAccount() {
		return receiveAccount;
	}

	public void setReceiveAccount(String receiveAccount) {
		this.receiveAccount = receiveAccount;
	}

}
