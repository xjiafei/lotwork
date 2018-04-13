package com.winterframework.firefrog.user.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.user.dao.vo.Judgement;
import com.winterframework.firefrog.user.service.IJudgementService;
import com.winterframework.firefrog.user.web.dto.JudgementRequestDTO;
import com.winterframework.firefrog.user.web.dto.JudgementResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("judgementController")
@RequestMapping(value = "/tools")
public class JudgementController {

	//	private Logger log = LoggerFactory.getLogger(JudgementController.class);
	@Resource(name = "judgementServiceImpl")
	private IJudgementService judgementService;

	/** 
	* @Title: initAction 
	* @Description: 生成工具类信息
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/initAction")
	@ResponseBody
	public Object initAction(@RequestBody @ValidRequestBody Request<JudgementRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		Judgement judgement = new Judgement();
		judgement.setAccount(request.getBody().getParam().getAccount());
		judgement.setAction(Long.valueOf(request.getBody().getParam().getAction()));
		judgement.setEffectTime(DataConverterUtil.convertLong2Date(request.getBody().getParam().getEffectTime()));

		judgementService.initAction(judgement);

		return response;
	}

	/** 
	* @Title: updateAction 
	* @Description:更新工具类信息
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/updateAction")
	@ResponseBody
	public Object updateAction(@RequestBody @ValidRequestBody Request<JudgementRequestDTO> request) throws Exception {
		@SuppressWarnings("rawtypes")
		Response response = new Response(request);
		Judgement judgement = new Judgement();
		judgement.setAccount(request.getBody().getParam().getAccount());
		judgement.setAction(Long.valueOf(request.getBody().getParam().getAction()));
		judgement.setEffectTime(DataConverterUtil.convertLong2Date(request.getBody().getParam().getEffectTime()));
		judgementService.updateAction(judgement);
		return response;
	}

	/** 
	* @Title: getAction 
	* @Description: 获取工具类信息
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getAction")
	@ResponseBody
	public Response<JudgementResponse> getAction(@RequestBody @ValidRequestBody Request<JudgementRequestDTO> request)
			throws Exception {
		Response<JudgementResponse> response = new Response<JudgementResponse>(request);
		Judgement judgement = new Judgement();
		judgement.setAccount(request.getBody().getParam().getAccount());
		judgement.setAction(Long.valueOf(request.getBody().getParam().getAction()));
		Judgement jd = judgementService.getAction(judgement);
		JudgementResponse jr = new JudgementResponse();
		jr.setAccount(jd.getAccount());
		jr.setAction(jd.getAction().intValue());
		jr.setEffectTime(DataConverterUtil.convertDate2Long(jd.getEffectTime()));
		response.setResult(jr);
		return response;
	}
}
