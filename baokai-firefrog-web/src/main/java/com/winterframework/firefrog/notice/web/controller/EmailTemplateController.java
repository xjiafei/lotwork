/**   
* @Title: EmailTemplateController.java 
* @Package com.winterframework.firefrog.notice.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-24 上午10:38:58 
* @version V1.0   
*/
package com.winterframework.firefrog.notice.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.global.web.dto.DefaultConfig;
import com.winterframework.firefrog.notice.web.dto.EmailConfigDto;
import com.winterframework.firefrog.notice.web.dto.EmailContentDto;
import com.winterframework.firefrog.notice.web.dto.EmailDto;
import com.winterframework.firefrog.notice.web.dto.EmailTemplateDto;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: EmailTemplateController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-10-24 上午10:38:58 
*  
*/
@Controller("emailTemplateController")
@RequestMapping(value = "/noticeAdmin")
public class EmailTemplateController {

	@PropertyConfig(value = "url.connect")
	private String urlPath;

	@PropertyConfig(value = "url.notice.saveEmailTemplate")
	private String saveEmailTemplateUrl;
	
	@PropertyConfig(value = "url.notice.getEmailTemplate")
	private String getEmailTemplateUrl;
	
	@PropertyConfig(value = "url.notice.testEmail")
	private String testEmailUrl;	
	
	@PropertyConfig(value = "url.notice.getEmail")
	private String getEmail;
	
	@PropertyConfig(value = "url.notice.getEmailByID")
	private String getEmailByID;
	
	@PropertyConfig(value = "url.notice.updateEmailConfigById")
	private String updateEmailConfigById;
	
	@PropertyConfig(value = "url.notice.insertEmail")
	private String insertEmail;
	
	@PropertyConfig(value = "url.notice.deleteEmailById")
	private String deleteEmailById;
	
	@PropertyConfig(value = "url.notice.mailtest")
	private String mailtest;


	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	private static final Logger logger = LoggerFactory.getLogger(EmailTemplateController.class);

	@RequestMapping(value = "/saveEmailTemplate")
	public String saveEmailTemplate(@ModelAttribute("config") EmailTemplateDto config, Errors errors, Model model)
			throws Exception {
		DefaultConfig<EmailTemplateDto> reqData = new DefaultConfig<EmailTemplateDto>();
		reqData.setVal(config);
		reqData.setModule("notice");
		reqData.setFunction("email");
		try {
			httpClient.invokeHttpWithoutResultType(urlPath + saveEmailTemplateUrl, reqData);
		} catch (Exception e) {
			logger.error("saveEmailTemplate error", e);
			throw e;
		}
		return toSaveEmailTemplate(model);
	}

	@RequestMapping(value = "/toSaveEmailTemplate")
	public String toSaveEmailTemplate(Model model) throws Exception {
		try{
			Response<List<EmailConfigDto>> resp = httpClient.invokeHttp(urlPath +getEmail, 
					new TypeReference<Response<List<EmailConfigDto>>>() {
			});	
			List<EmailConfigDto> mail = resp.getBody().getResult();			
			model.addAttribute("Mail", mail);
		}catch(Exception e){
			throw e;
		}
		model.addAttribute("cate2Name", "邮件配置");
		return "/notice/emailTemplateConfig";
	}

	@RequestMapping(value = "/toTestEmailTemplate")
	public String toTestEmailTemplate(Model model) throws Exception {
		try{
			Response<List<EmailConfigDto>> resp = httpClient.invokeHttp(urlPath +getEmail, 
					new TypeReference<Response<List<EmailConfigDto>>>() {
			});
			List<EmailConfigDto> mail = resp.getBody().getResult();
			model.addAttribute("Mail", mail);
		}catch(Exception e){
			throw e;
		}
		model.addAttribute("cate2Name", "邮件测试");
		return "/notice/testEmail";
	}
	
	@RequestMapping(value = "/getEmailByID")
	public String getEmailByID(HttpServletRequest req , Model model) throws Exception {
		try{
			EmailConfigDto emailDto = new EmailConfigDto();
			emailDto.setAccount(req.getParameter("account").trim());			
			Response<EmailConfigDto> resp = httpClient.invokeHttp(urlPath +getEmailByID,emailDto, 
						new TypeReference<Response<EmailConfigDto>>() {
			});
			EmailConfigDto getMailById = resp.getBody().getResult();
			model.addAttribute("updateEmail", getMailById);
			logger.info("GetEmailByid process successful!!");
		}catch(Exception e){
			logger.error("GetEmailByid error", e);
		}
		return toSaveEmailTemplate(model);
	}
	
	
	@RequestMapping(value = "/updateEmailConfigById")
	public String updateEmailConfigById(HttpServletRequest req , Model model) throws Exception {
		try{
			EmailConfigDto emailDto = new EmailConfigDto();
			emailDto.setSendmethod(req.getParameter("upsendmethod"));
			emailDto.setSmtpserver(req.getParameter("upsmtpserver"));
			emailDto.setPort(req.getParameter("upport"));
			emailDto.setSender(req.getParameter("upsender").trim());
			emailDto.setAccount(req.getParameter("upaccount").trim());
			emailDto.setPassword(req.getParameter("uppassword"));
			Response<EmailConfigDto> resp = httpClient.invokeHttp(urlPath +updateEmailConfigById,emailDto, 
			new TypeReference<Response<EmailConfigDto>>() {
			});			
			EmailConfigDto updateMail = resp.getBody().getResult();
			model.addAttribute("updateEmail", updateMail);			
			model.addAttribute("updatestatus", null==updateMail);	
			logger.info("Update EmailSetting process successful!!");
		}catch(Exception e){
			logger.error("Update EmailSetting error", e);
		}
		return toSaveEmailTemplate(model);
	}
	
	
	@RequestMapping(value = "/insertEmail")
	public String insertEmail(HttpServletRequest req , Model model) throws Exception {
		try{
			EmailConfigDto emailDto = new EmailConfigDto();	
			emailDto.setSendmethod(req.getParameter("upsendmethod"));
			emailDto.setSmtpserver(req.getParameter("upsmtpserver"));
			emailDto.setPort(req.getParameter("upport"));
			emailDto.setSender(req.getParameter("upsender").trim());
			emailDto.setAccount(req.getParameter("upaccount").trim());
			emailDto.setPassword(req.getParameter("uppassword"));
			Response<EmailConfigDto> resp = httpClient.invokeHttp(urlPath +insertEmail,emailDto, 
			new TypeReference<Response<EmailConfigDto>>() {
			});			
			EmailConfigDto insertMail = resp.getBody().getResult();
			if(null==insertMail){
				emailDto.setSendmethod(null);	
				model.addAttribute("updateEmail", emailDto);
			}			
			model.addAttribute("insertstatus", null==insertMail);	
			logger.info("Insert EmailSetting process successful!!");
		}catch(Exception e){
			logger.error("Insert EmailSetting error", e);
		}
		return toSaveEmailTemplate(model);
	}
	
	
	
	@RequestMapping(value = "/deleteEmailById")
	public String deleteEmailById(HttpServletRequest req , Model model) throws Exception {
		try{
			EmailConfigDto emailDto = new EmailConfigDto();
			emailDto.setAccount(req.getParameter("account").trim());			
			Response<EmailConfigDto> resp = httpClient.invokeHttp(urlPath +deleteEmailById,emailDto, 
					new TypeReference<Response<EmailConfigDto>>() {
			});
			EmailConfigDto deleteEMail = resp.getBody().getResult();
			model.addAttribute("deletestatus", null==deleteEMail);
			logger.info("Delete EmailSetting process successful!!");
		}catch(Exception e){
			logger.error("Delete EmailSetting error", e);
		}
		return toSaveEmailTemplate(model);
	}
	
	
	@RequestMapping(value = "/mailtest")
	public String testEmail( Model model, HttpServletRequest req) throws Exception {
		try {
			EmailContentDto email = new EmailContentDto(); 
			email.setSendmethod(req.getParameter("sendmethod"));
			email.setSmtpserver(req.getParameter("smtpserver"));
			email.setPort(req.getParameter("port"));
			email.setSender(req.getParameter("sender"));
			email.setAccount(req.getParameter("account"));
			email.setPassword(req.getParameter("password"));
			email.setRcvEmail(req.getParameter("rcvEmail"));
			email.setTitle(req.getParameter("title"));
			email.setContent(req.getParameter("content"));
			Response<Boolean> resp = httpClient.invokeHttp(urlPath + mailtest, email,new TypeReference<Response<Boolean>>() {
			}  );
			model.addAttribute("mailtest",resp.getBody().getResult());
			logger.info("EmailTest process successful!!");
			logger.info("EmailTest process status:"+resp.getBody().getResult());
		} catch (Exception e) {
			logger.error("EmailTest error", e);
		}
		return toTestEmailTemplate(model);
	}

	@RequestMapping(value = "testEmailTemplate")
	public String testEmailTemplate(@ModelAttribute("email") EmailDto email, Model model) throws Exception {
		try {
			httpClient.invokeHttpWithoutResultType(urlPath + testEmailUrl, email);
		} catch (Exception e) {
			logger.error("testEmailTemplate error", e);
		}
		return toTestEmailTemplate(model);
	}
	

}
