package com.winterframework.firefrog.help.web.controller;

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
import com.winterframework.firefrog.help.entity.HelpKnowledge;
import com.winterframework.firefrog.help.service.IHelpKnowledgeService;
import com.winterframework.firefrog.help.web.dto.LotteryCateStruc;
import com.winterframework.firefrog.help.web.dto.LotteryKnowledgeCategoryAddRequest;
import com.winterframework.firefrog.help.web.dto.LotteryKnowledgeCategoryAddResponse;
import com.winterframework.firefrog.help.web.dto.LotteryKnowledgeCategoryDeleteRequest;
import com.winterframework.firefrog.help.web.dto.LotteryKnowledgeCategoryEditRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("helpKnowledgeController")
@RequestMapping("/help")
public class HelpKnowledgeController {

	private static final Logger log = LoggerFactory.getLogger(HelpKnowledgeController.class);

	@Resource(name = "helpKnowledgeServiceImpl")
	private IHelpKnowledgeService helpKnowledgeServiceImpl;

	/**
	 * 查询所有彩种知识目录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryLotteryKnowledgeCategory")
	@ResponseBody
	public Response<List<LotteryCateStruc>> queryLotteryKnowledgeCategory(@RequestBody Request request)
			throws Exception {
		log.info("queryLotteryKnowledgeCategory start");
		Response<List<LotteryCateStruc>> resp = new Response<List<LotteryCateStruc>>(request);
		try {
			List<HelpKnowledge> helpKnowledges = helpKnowledgeServiceImpl.queryAllHelpKnowledge();
			List<LotteryCateStruc> lotteryCateStrucs = new ArrayList<LotteryCateStruc>();
			for (HelpKnowledge helpKnowledge : helpKnowledges) {
				LotteryCateStruc lotteryCateStruc = new LotteryCateStruc();
				lotteryCateStruc.setId(helpKnowledge.getId());
				lotteryCateStruc.setName(helpKnowledge.getName());
				lotteryCateStruc.setNo(helpKnowledge.getNo());
				lotteryCateStrucs.add(lotteryCateStruc);
			}
			resp.setResult(lotteryCateStrucs);
		} catch (Exception e) {
			log.error("queryLotteryKnowledgeCategory error", e);
			throw e;
		}
		log.info("queryLotteryKnowledgeCategory end");
		return resp;
	}

	/**
	 * 创建彩种知识目录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/createLotteryKnowledgeCategory")
	@ResponseBody
	public Response<LotteryKnowledgeCategoryAddResponse> createLotteryKnowledgeCategory(
			@RequestBody @ValidRequestBody Request<LotteryKnowledgeCategoryAddRequest> request) throws Exception {
		log.info("createLotteryKnowledgeCategory start");
		Response<LotteryKnowledgeCategoryAddResponse> resp = new Response<LotteryKnowledgeCategoryAddResponse>(request);
		try {
			LotteryKnowledgeCategoryAddRequest lotteryKnowledgeCategoryAddRequest = request.getBody().getParam();
			HelpKnowledge addEntity = new HelpKnowledge();
			addEntity.setName(lotteryKnowledgeCategoryAddRequest.getName());
			HelpKnowledge helpKnowledge = helpKnowledgeServiceImpl.addHelpKnowledge(addEntity);
			LotteryKnowledgeCategoryAddResponse lotteryKnowledgeCategoryAddResponse = new LotteryKnowledgeCategoryAddResponse();
			lotteryKnowledgeCategoryAddResponse.setId(helpKnowledge.getId());
			lotteryKnowledgeCategoryAddResponse.setName(helpKnowledge.getName());
			lotteryKnowledgeCategoryAddResponse.setNo(helpKnowledge.getNo());
			resp.setResult(lotteryKnowledgeCategoryAddResponse);
		} catch (Exception e) {
			log.error("createLotteryKnowledgeCategory error", e);
			throw e;
		}
		log.info("createLotteryKnowledgeCategory end");
		return resp;
	}

	/**
	 * 修改彩种知识目录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editLotteryKnowledgeCategory")
	@ResponseBody
	public Response editLotteryKnowledgeCategory(
			@RequestBody @ValidRequestBody Request<LotteryKnowledgeCategoryEditRequest> request) throws Exception {
		log.info("editLotteryKnowledgeCategory start");
		Response resp = new Response(request);
		try {
			LotteryKnowledgeCategoryEditRequest editRequest = request.getBody().getParam();
			HelpKnowledge updateEntity = new HelpKnowledge();
			updateEntity.setId(editRequest.getId());
			updateEntity.setName(editRequest.getName());
			updateEntity.setNo(editRequest.getNo());
			helpKnowledgeServiceImpl.updateHelpKnowledge(updateEntity);
		} catch (Exception e) {
			log.error("editLotteryKnowledgeCategory error", e);
			throw e;
		}
		log.info("editLotteryKnowledgeCategory end");
		return resp;
	}

	/**
	 * 删除彩种知识目录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteLotteryKnowledgeCategory")
	@ResponseBody
	public Response deleteLotteryKnowledgeCategory(
			@RequestBody @ValidRequestBody Request<LotteryKnowledgeCategoryDeleteRequest> request) throws Exception {
		log.info("deleteLotteryKnowledgeCategory start");
		Response resp = new Response(request);
		try {
			LotteryKnowledgeCategoryDeleteRequest deleteRequest = request.getBody().getParam();
			helpKnowledgeServiceImpl.deleteHelpKnowledge(deleteRequest.getId());
		} catch (Exception e) {
			log.error("deleteLotteryKnowledgeCategory error", e);
			throw e;
		}
		log.info("deleteLotteryKnowledgeCategory end");
		return resp;
	}
}
