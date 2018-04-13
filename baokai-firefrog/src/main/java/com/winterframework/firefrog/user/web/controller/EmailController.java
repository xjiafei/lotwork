package com.winterframework.firefrog.user.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.email.EmailInfo;
import com.winterframework.firefrog.common.email.service.IEmailService;
import com.winterframework.firefrog.user.web.dto.SendMailStrucRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("emailController")
@RequestMapping(value = "/user/email/")
public class EmailController {

	@Resource(name = "emailServiceImpl")
	private IEmailService emailService;

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

	/**
	* @Title: sendEmail 
	* @Description: 发邮件
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/sendEmail")
	public @ResponseBody
	Response<Object> sendEmail(@RequestBody @ValidRequestHeader Request<SendMailStrucRequest> request) throws Exception {
		Response<Object> response = new Response<Object>(request);
		SendMailStrucRequest struc = request.getBody().getParam();
		EmailInfo email = new EmailInfo();
		email.setAddress(struc.getEmail());
		email.setContent(struc.getContent());
		email.setTitle(struc.getTitle());
		try {
			emailService.sendEmail(email);
		} catch (Exception e) {
			logger.error("sendEmail error.", e);
			throw e;
		}
		return response;
	}

}
