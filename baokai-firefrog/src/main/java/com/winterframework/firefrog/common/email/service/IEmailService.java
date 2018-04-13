package com.winterframework.firefrog.common.email.service;

import com.winterframework.firefrog.common.email.EmailInfo;

public interface IEmailService {

	public void sendEmail(EmailInfo email) throws Exception;
}
