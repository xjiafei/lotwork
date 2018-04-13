/**   
* @Title: AdminHelpConfigController.java 
* @Package com.winterframework.firefrog.help.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-9-11 上午11:08:06 
* @version V1.0   
*/
package com.winterframework.firefrog.help.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpStruc;
import com.winterframework.firefrog.help.web.dto.KeywordRequest;
import com.winterframework.firefrog.help.web.dto.KeywordStruc;
import com.winterframework.firefrog.help.web.dto.LotteryCateStruc;
import com.winterframework.firefrog.help.web.dto.LotteryKnowledgeCategoryAddRequest;
import com.winterframework.firefrog.help.web.dto.LotteryKnowledgeCategoryAddResponse;
import com.winterframework.firefrog.help.web.dto.LotteryKnowledgeCategoryDeleteRequest;
import com.winterframework.firefrog.help.web.dto.LotteryKnowledgeCategoryEditRequest;
import com.winterframework.firefrog.help.web.dto.OperateStatusResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("adminHelpConfigController")
@RequestMapping(value = "/helpAdmin")
public class AdminHelpConfigController {

	private static final Logger logger = LoggerFactory.getLogger(AdminHelpConfigController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.help.queryHotWord")
	private String queryHotWordUrl;

	@PropertyConfig(value = "url.help.saveHotWord")
	private String saveHotWordUrl;

	@PropertyConfig(value = "url.help.queryLotteryKnowledgeCategory")
	private String queryLotteryKnowledgeCategoryUrl;

	@PropertyConfig(value = "url.help.createLotteryKnowledgeCategory")
	private String createLotteryKnowledgeCategoryUrl;

	@PropertyConfig(value = "url.help.deleteLotteryKnowledgeCategory")
	private String deleteLotteryKnowledgeCategoryUrl;

	@PropertyConfig(value = "url.help.editLotteryKnowledgeCategory")
	private String editLotteryKnowledgeCategoryUrl;

	@PropertyConfig(value = "url.help.queryHelp")
	private String queryHelpUrl;

	@PropertyConfig(value = "url.help.modifyHelp")
	private String modifyHelpUrl;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	private String getUrl(String path) {
		return serverPath + path;
	}

	@RequestMapping(value = "/queryHelpConfig")
	public String queryAdminHelpConfigPage(@RequestParam("cate2Name") String name, Model model) throws Exception {
		try {
			HelpQueryRequest lotteryReq = new HelpQueryRequest();
			lotteryReq.setIsRec(1L);
			lotteryReq.setType(1L);
			lotteryReq.setCateId(-1l);
			lotteryReq.setCateId2(-1l);
			List<HelpStruc> helps = (List<HelpStruc>) httpClient
					.invokeHttp(getUrl(queryHelpUrl), lotteryReq, new TypeReference<Response<List<HelpStruc>>>() {
					}).getBody().getResult();
			List<KeywordStruc> keywords = (List<KeywordStruc>) httpClient
					.invokeHttp(getUrl(queryHotWordUrl), new TypeReference<Response<List<KeywordStruc>>>() {
					}).getBody().getResult();
			List<LotteryCateStruc> lotteryCateStrucs = (List<LotteryCateStruc>) httpClient
					.invokeHttp(getUrl(queryLotteryKnowledgeCategoryUrl),
							new TypeReference<Response<List<LotteryCateStruc>>>() {
							}).getBody().getResult();
			model.addAttribute("keywords", keywords);
			model.addAttribute("helps", helps);
			model.addAttribute("lotteryCateStrucs", lotteryCateStrucs);
			model.addAttribute("cate2Name", "帮助配置");
		} catch (Exception e) {
			logger.error("query admin help config page error");
			throw e;
		}
		return "/help/helpConfig";
	}

	@RequestMapping(value = "/modifyKeyword")
	@ResponseBody
	public Object modifyKeyword(@ModelAttribute("keyword") @Valid KeywordRequest keyword, BindingResult result)
			throws Exception {
		logger.info("modify keyword start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			if (result.hasErrors()) {
				resp.setStatus(0l);
				resp.setMessage("修改数据错误！");
				return resp;
			}
			httpClient.invokeHttpWithoutResultType(getUrl(saveHotWordUrl), keyword);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("modify keyword error", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		return resp;
	}

	@RequestMapping(value = "/addLotteryKnowledgeCate")
	@ResponseBody
	public Object addLotteryKnowledgeCate(
			@ModelAttribute("lotteryKnowledgeCate") @Valid LotteryKnowledgeCategoryAddRequest lotteryKnowledgeCate,
			BindingResult result) throws Exception {
		logger.info("add LotteryKnowledgeCate start");
		try {
			if (result.hasErrors()) {
				OperateStatusResponse resp = new OperateStatusResponse();
				resp.setStatus(0l);
				resp.setMessage("添加数据错误！");
				return resp;
			}
			LotteryKnowledgeCategoryAddResponse lotteryKnowledge = (LotteryKnowledgeCategoryAddResponse) httpClient
					.invokeHttp(getUrl(createLotteryKnowledgeCategoryUrl), lotteryKnowledgeCate,
							LotteryKnowledgeCategoryAddResponse.class).getBody().getResult();
			return lotteryKnowledge;
		} catch (Exception e) {
			logger.error("add LotteryKnowledgeCate error", e);
			OperateStatusResponse resp = new OperateStatusResponse();
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
	}

	@RequestMapping(value = "/editLotteryKnowledgeCate")
	@ResponseBody
	public Object editLotteryKnowledgeCate(
			@ModelAttribute("lotteryKnowledgeCate") @Valid LotteryKnowledgeCategoryEditRequest lotteryKnowledgeCate,
			BindingResult result) throws Exception {
		logger.info("modify LotteryKnowledgeCate start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			if (result.hasErrors()) {
				resp.setStatus(0l);
				resp.setMessage("修改数据错误！");
				return resp;
			}
			httpClient.invokeHttpWithoutResultType(getUrl(editLotteryKnowledgeCategoryUrl), lotteryKnowledgeCate);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("modify LotteryKnowledgeCate error", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		return resp;
	}

	@RequestMapping(value = "/deleteLotteryKnowledgeCate")
	@ResponseBody
	public Object deleteLotteryKnowledgeCate(
			@ModelAttribute("lotteryKnowledgeCate") @Valid LotteryKnowledgeCategoryDeleteRequest lotteryKnowledgeCate,
			BindingResult result) throws Exception {
		logger.info("delete LotteryKnowledgeCate start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			if (result.hasErrors()) {
				resp.setStatus(0l);
				resp.setMessage("删除数据错误！");
				return resp;
			}
			httpClient.invokeHttpWithoutResultType(getUrl(deleteLotteryKnowledgeCategoryUrl), lotteryKnowledgeCate);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("delete LotteryKnowledgeCate error", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		return resp;
	}

	@RequestMapping(value = "/changeHotLotteryNo")
	@ResponseBody
	public Object changeHotLotteryNo(@ModelAttribute("hotLottery") HelpStruc hotLottery) throws Exception {
		logger.info("change hotLottery no start");
		OperateStatusResponse resp = new OperateStatusResponse();
		try {
			hotLottery.setIsRec(1);
			httpClient.invokeHttpWithoutResultType(getUrl(modifyHelpUrl), hotLottery);
			resp.setStatus(1l);
		} catch (Exception e) {
			logger.error("change hotLottery no error", e);
			resp.setStatus(0l);
			resp.setMessage(e.getMessage());
			return resp;
		}
		return resp;
	}
}
