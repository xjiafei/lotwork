package com.winterframework.firefrog.config.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.config.entity.ConfigEntity;
import com.winterframework.firefrog.common.config.entity.MailConfigEnitiy;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.config.service.IMailConfigService;
import com.winterframework.firefrog.common.email.IMailSender;
import com.winterframework.firefrog.config.web.controller.dto.ArgueTipsDtoUser;
import com.winterframework.firefrog.config.web.controller.dto.ChargeDto;
import com.winterframework.firefrog.config.web.controller.dto.Config;
import com.winterframework.firefrog.config.web.controller.dto.DefaultConfig;
import com.winterframework.firefrog.config.web.controller.dto.EmailTemplateDto;
import com.winterframework.firefrog.config.web.controller.dto.ExamineTipsDtoUser;
import com.winterframework.firefrog.config.web.controller.dto.RegisterLoginConfigDto;
import com.winterframework.firefrog.config.web.controller.dto.TransfertoUser;
import com.winterframework.firefrog.config.web.controller.dto.WithDrawCheck;
import com.winterframework.firefrog.config.web.controller.dto.WithdralDtoUser;
import com.winterframework.firefrog.fund.web.controller.ConfigUtils;
import com.winterframework.firefrog.fund.web.dto.ConfigSaveRequestDTO;
import com.winterframework.firefrog.fund.web.dto.ConfigValueRequestDTO;
import com.winterframework.firefrog.fund.web.dto.ConfigValueResponse;
import com.winterframework.firefrog.notice.web.dto.EmailConfigDto;
import com.winterframework.firefrog.notice.web.dto.EmailContentDto;
import com.winterframework.firefrog.notice.web.dto.GetEmailConfigDto;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: ConfigController 
* @Description: 配置文件处理控制器 
* @author david
* @date 2013-7-4 下午5:52:42 
*  
*/
@Controller("configController")
@RequestMapping(value = "/common")
public class ConfigController {

	private static final Logger log = LoggerFactory.getLogger(ConfigController.class);
	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	@Resource(name = "configUtils")
	private ConfigUtils configUtils;
	@Resource(name = "MailconfigServiceImpl")
	private IMailConfigService MailconfigService;
	@Resource(name = "templateMailSender")
	private IMailSender mailSender;

	/** 
	* @Title: saveConfig 
	* @Description: 保存配置信息 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/saveConfig")
	@ResponseBody
	public Response<ConfigSaveRequestDTO> saveConfig(
			@RequestBody @ValidRequestBody Request<ConfigSaveRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		ConfigEntity config = new ConfigEntity();
		config.setModule(request.getBody().getParam().getModule());
		config.setKey(request.getBody().getParam().getFunction());
		config.setValue(JsonMapper.nonAlwaysMapper().toJson(request.getBody().getParam().getVal()));
		try {
			configService.saveConfig(config);
			response.setResult(request.getBody().getParam());
		} catch (Exception e) {
			log.error("saveConfig error.", e);
			throw e;
		}

		return response;
	}

	@RequestMapping(value = "/saveConfig/charge")
	@ResponseBody
	public Response<DefaultConfig<ChargeDto[]>> saveConfigWithDrawcharge(
			@RequestBody @ValidRequestBody Request<DefaultConfig<ChargeDto[]>> request) throws Exception {
		return saveConfigDefault(request);
	}

	@RequestMapping(value = "/getConfigValueByKey/charge")
	@ResponseBody
	public Response<ConfigSaveRequestDTO<Long>> getConfigValueByKeyWithdrawcharge(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		return getDefaultValueByKey(request, Long.class);
	}

	@RequestMapping(value = "/saveConfig/withdraw")
	@ResponseBody
	public Response<DefaultConfig<WithdralDtoUser>> saveConfigWithDraw(
			@RequestBody @ValidRequestBody Request<DefaultConfig<WithdralDtoUser>> request) throws Exception {
		return saveConfigDefault(request);
	}
	
	@RequestMapping(value = "/saveConfig/arguetips")
	@ResponseBody
	public Response<DefaultConfig<ArgueTipsDtoUser>> saveConfigArguetips(
			@RequestBody @ValidRequestBody Request<DefaultConfig<ArgueTipsDtoUser>> request) throws Exception {
		return saveConfigDefault(request);
	}
	
	@RequestMapping(value = "/saveConfig/examinetips")
	@ResponseBody
	public Response<DefaultConfig<ExamineTipsDtoUser>> saveConfigExaminetips(
			@RequestBody @ValidRequestBody Request<DefaultConfig<ExamineTipsDtoUser>> request) throws Exception {
		return saveConfigDefault(request);
	}
	
	@RequestMapping(value = "/getConfigValueByKey/withdraw")
	@ResponseBody
	public Response<ConfigSaveRequestDTO<WithdralDtoUser>> getConfigValueByKeyWithdraw(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		return getDefaultValueByKey(request, WithdralDtoUser.class);
	}
	
	
	@RequestMapping(value = "/getConfigValueByKey/arguetips")
	@ResponseBody
	public Response<ConfigSaveRequestDTO<ArgueTipsDtoUser>> getConfigValueByKeyArguetips(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		log.info("start run search arguetips");
		return getDefaultValueByKey(request, ArgueTipsDtoUser.class);
	}
	
	@RequestMapping(value = "/getConfigValueByKey/examinetips")
	@ResponseBody
	public Response<ConfigSaveRequestDTO<ExamineTipsDtoUser>> getConfigValueByKeyExaminetips(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		log.info("start run search examinetips");
		return getDefaultValueByKey(request, ExamineTipsDtoUser.class);
	}	

	@RequestMapping(value = "/saveConfig/transfer")
	@ResponseBody
	public Response<DefaultConfig<TransfertoUser>> saveConfigWithDrawT(
			@RequestBody @ValidRequestBody Request<DefaultConfig<TransfertoUser>> request) throws Exception {
		return saveConfigDefault(request);
	}

	@RequestMapping(value = "/getConfigValueByKey/transfer")
	@ResponseBody
	public Response<ConfigSaveRequestDTO<TransfertoUser>> getConfigValueByKeyT(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		return getDefaultValueByKey(request, TransfertoUser.class);
	}

	@RequestMapping(value = "/saveConfig/withdrawCheck")
	@ResponseBody
	public Response<DefaultConfig<WithDrawCheck>> saveConfigWithDrawCheck(
			@RequestBody @ValidRequestBody Request<DefaultConfig<WithDrawCheck>> request) throws Exception {
		return saveConfigDefault(request);
	}

	@RequestMapping(value = "/getConfigValueByKey/withdrawCheck")
	@ResponseBody
	public Response<ConfigSaveRequestDTO<WithDrawCheck>> getConfigValueByKeyWithdrawCheck(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		return getDefaultValueByKey(request, WithDrawCheck.class);
	}

	@RequestMapping(value = {  "/saveConfig/bet","/saveConfig/chargeCoute", "/saveConfig/chargeCountDown" })
	@ResponseBody
	public Response<DefaultConfig<Long>> saveConfigWithDrawCount(
			@RequestBody @ValidRequestBody Request<DefaultConfig<Long>> request) throws Exception {
		return saveConfigDefault(request);
	}

	@RequestMapping(value = {  "/getConfigValueByKey/bet","/getConfigValueByKey/chargeCoute", "/getConfigValueByKey/chargeCountDown"})
	@ResponseBody
	public Response<ConfigSaveRequestDTO<Long>> getConfigValueByKeyWithdrawCount(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		return getDefaultValueByKey(request, Long.class);
	}

	@RequestMapping(value = "/saveConfig/regLogin")
	@ResponseBody
	public Response<DefaultConfig<RegisterLoginConfigDto>> saveConfigRegLogin(
			@RequestBody @ValidRequestBody Request<DefaultConfig<RegisterLoginConfigDto>> request) throws Exception {
		return saveConfigDefault(request);
	}

	@RequestMapping(value = "/saveConfig/emailTemplate")
	@ResponseBody
	public Response<DefaultConfig<EmailTemplateDto>> saveConfigEmailTemplate(
			@RequestBody @ValidRequestBody Request<DefaultConfig<EmailTemplateDto>> request) throws Exception {
		return saveConfigDefault(request);
	}
	
	@RequestMapping(value = "/getConfigValueByKey/emailTemplate")
	@ResponseBody
	public Response<ConfigSaveRequestDTO<EmailTemplateDto>> getConfigValueByEmailTemplate(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		return getDefaultValueByKey(request, EmailTemplateDto.class);
	}

	@RequestMapping(value = "/getConfigValueByKey/regLogin")
	@ResponseBody
	public Response<ConfigSaveRequestDTO<RegisterLoginConfigDto>> getConfigValueByKeyRegLogin(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		return getDefaultValueByKey(request, RegisterLoginConfigDto.class);
	}

	@RequestMapping(value = "/saveConfig/ipSwitch")
	@ResponseBody
	public Response<DefaultConfig<Long>> saveConfigIPSwitch(
			@RequestBody @ValidRequestBody Request<DefaultConfig<Long>> request) throws Exception {
		return saveConfigDefault(request);
	}

	@RequestMapping(value = "/getConfigValueByKey/ipSwitch")
	@ResponseBody
	public Response<ConfigSaveRequestDTO<Long>> getConfigValueByKeyIPSwitch(
			@RequestBody @ValidRequestBody Request<Config> request) throws Exception {
		return getDefaultValueByKey(request, Long.class);
	}
	
	@RequestMapping(value = "/getMail")
	@ResponseBody
	public  Response<List<EmailConfigDto>> getMail(@RequestBody Request<EmailConfigDto> request) throws Exception {
		Response<List<EmailConfigDto>> response = new Response<List<EmailConfigDto>>(request);
		List<EmailConfigDto> emailList = new ArrayList<EmailConfigDto>();
		try{
			EmailConfigDto dao = new EmailConfigDto();
			List<MailConfigEnitiy> mailConfig = MailconfigService.GetAllMailConfig();
					for(MailConfigEnitiy mail : mailConfig){
						GetEmailConfigDto mailDto = new GetEmailConfigDto();
						dao = mailDto.getEmail(mail);
						emailList.add(dao);
					}
					response.setResult(emailList);
		}catch(Exception e){
			System.out.println(e);
		}
		return response;
	}
	
	@RequestMapping(value = "/mailtest")
	@ResponseBody
	public Response<Boolean> mailtest(@RequestBody Request<EmailContentDto> request) throws Exception {
		log.debug("EmailTest Begin");
		Response<Boolean> respStatus = new Response<Boolean>(request);
		Boolean sendresault=false;
		try {
			EmailContentDto emailDto = request.getBody().getParam();	
			sendresault=mailSender.testsendMail(emailDto);	
			log.info("EMailTest API Success!!   sendresault = "+sendresault );				
		} catch (Exception e) {			
			log.error("EMailTest API  Error", e);			
			throw e;
		}
		finally {			
			log.info("EMAILT TESET Fin_start");
			respStatus.setResult(sendresault);	
			log.info("EMAILT TESET Fin_end_status:"+sendresault);
		    return respStatus;
		}
		
	}
	
	
	@RequestMapping(value = "/getEmailByID")
	@ResponseBody
	public  Response<EmailConfigDto> getEmailByID(@RequestBody Request<EmailConfigDto> request) throws Exception {
		Response<EmailConfigDto> response = new Response<EmailConfigDto>(request);
		try{
			String account = request.getBody().getParam().getAccount();
			List<MailConfigEnitiy> mailConfid = MailconfigService.GetEmailByID(account);
			EmailConfigDto dto = new EmailConfigDto();
			for(int i=0;i<mailConfid.size();i++)
			{
				dto.setSendmethod(mailConfid.get(i).GetSendmethod());
				dto.setSmtpserver(mailConfid.get(i).GetSmtpServer());
				dto.setPort(mailConfid.get(i).GetPort());
				dto.setSender(mailConfid.get(i).GetSender());
				dto.setAccount(mailConfid.get(i).GetAccount());
				dto.setPassword(mailConfid.get(i).GetPassword());			
			}
			response.setResult(dto);
		}catch(Exception e){
			System.out.println(e);
		}
		return response;
	}//
	
	@RequestMapping(value = "/insertEMail")
	@ResponseBody
	public Response<EmailConfigDto> insertMail(@RequestBody Request<EmailConfigDto> request) throws Exception {
		Response<EmailConfigDto> response = new Response<EmailConfigDto>(request);
		try{
			EmailConfigDto emailConfigDto = request.getBody().getParam();
			MailConfigEnitiy mailConfigEnitiy = new MailConfigEnitiy();
			mailConfigEnitiy.SetSendmethod(emailConfigDto.getSendmethod());
			mailConfigEnitiy.SetSmtpServer(emailConfigDto.getSmtpserver());
			mailConfigEnitiy.SetPort(emailConfigDto.getPort());
			mailConfigEnitiy.SetSender(emailConfigDto.getSender());
			mailConfigEnitiy.SetAccount(emailConfigDto.getAccount());
			mailConfigEnitiy.SetPassword(emailConfigDto.getPassword());
			List<MailConfigEnitiy> mailConfid = MailconfigService.GetEmailByID(emailConfigDto.getAccount());
			if(mailConfid.size()<1){
				MailconfigService.InsertEmail(mailConfigEnitiy);
				response.getBody().setResult(emailConfigDto);				
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return response;
	}
	
	@RequestMapping(value = "/updateEmailConfigById")
	@ResponseBody
	public Response<EmailConfigDto> updateMail(@RequestBody Request<EmailConfigDto> request) throws Exception {
		Response<EmailConfigDto> response = new Response<EmailConfigDto>(request);
		try{
			EmailConfigDto emailConfigDto = request.getBody().getParam();
			MailConfigEnitiy mailConfigEnitiy = new MailConfigEnitiy();
			mailConfigEnitiy.SetSendmethod(emailConfigDto.getSendmethod());
			mailConfigEnitiy.SetSmtpServer(emailConfigDto.getSmtpserver());
			mailConfigEnitiy.SetPort(emailConfigDto.getPort());
			mailConfigEnitiy.SetSender(emailConfigDto.getSender());
			mailConfigEnitiy.SetAccount(emailConfigDto.getAccount());
			mailConfigEnitiy.SetPassword(emailConfigDto.getPassword());
			MailconfigService.UpdateEmailByID(mailConfigEnitiy);
			response.getBody().setResult(emailConfigDto);
		}catch(Exception e){
			System.out.println(e);
	
		}
		return response;
	}
	
	@RequestMapping(value = "/deleteEmailById")
	@ResponseBody
	public Response<EmailConfigDto> deleteMail(@RequestBody Request<EmailConfigDto> request) throws Exception {
		Response<EmailConfigDto> response = new Response<EmailConfigDto>(request);
		try{
			String deletaccount = request.getBody().getParam().getAccount();			
			MailconfigService.DeleteEmailById(deletaccount);
			response.getBody().setResult(request.getBody().getParam());	
		}catch(Exception e){
			System.out.println(e);
		}
		return response;
	}
	

	/** 
	* @Title: getConfigValueByKey 
	* @Description: 通过key获取配置的value值 
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getConfigValueByKey")
	@ResponseBody
	public Response<ConfigValueResponse> getConfigValueByKey(
			@RequestBody @ValidRequestBody Request<ConfigValueRequestDTO> request) throws Exception {
		Response<ConfigValueResponse> response = new Response<ConfigValueResponse>(request);
		try {
			String str = configService.getConfigValueByKey(request.getBody().getParam().getModule(), request.getBody()
					.getParam().getFunction());
			ConfigValueResponse configValueResponse = new ConfigValueResponse();
			configValueResponse.setVal(str);
			response.setResult(configValueResponse);
		} catch (Exception e) {
			log.error("getConfigValueByKey error.", e);
			throw e;
		}
		return response;
	}

	private <T> Response<ConfigSaveRequestDTO<T>> getDefaultValueByKey(Request<Config> request, Class<T> cls)
			throws Exception {
		Response<ConfigSaveRequestDTO<T>> response = new Response<ConfigSaveRequestDTO<T>>(request);
		try {
			String str = configService.getConfigValueByKey(request.getBody().getParam().getModule(), request.getBody()
					.getParam().getFunction());
			ConfigSaveRequestDTO<T> configValueResponse = new ConfigSaveRequestDTO<T>();
			configValueResponse.setVal(JsonMapper.nonEmptyMapper().fromJson(str, cls));
			response.setResult(configValueResponse);
		} catch (Exception e) {
			log.error("getConfigValueByKey error.", e);
			throw e;
		}
		return response;
	}

	private <T> Response<DefaultConfig<T>> saveConfigDefault(Request<DefaultConfig<T>> request) throws Exception {
		Response<DefaultConfig<T>> response = new Response<DefaultConfig<T>>(request);
		ConfigEntity config = new ConfigEntity();
		config.setModule(request.getBody().getParam().getModule());
		config.setKey(request.getBody().getParam().getFunction());
		config.setValue(JsonMapper.nonEmptyMapper().toJson(request.getBody().getParam().getVal()));
		response.setResult(request.getBody().getParam());
		configUtils.saveConfig(config);
		return response;
	}

}
