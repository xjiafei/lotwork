package com.winterframework.firefrog.game.web.controller;

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
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.game.entity.BetLimit;
import com.winterframework.firefrog.game.entity.BetLimitAssist;
import com.winterframework.firefrog.game.service.IBetLimitService;
import com.winterframework.firefrog.game.service.IGameMultipleService;
import com.winterframework.firefrog.game.web.dto.BetLimitAssistStruc;
import com.winterframework.firefrog.game.web.dto.BetLimitCheckRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitCheckResponse;
import com.winterframework.firefrog.game.web.dto.BetLimitModifyRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitModifyResponse;
import com.winterframework.firefrog.game.web.dto.BetLimitPublishRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitPublishResponse;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryByBetRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryResponse;
import com.winterframework.firefrog.game.web.dto.BetMethodMultipleStruc;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.SBLimitRequest;
import com.winterframework.firefrog.game.web.dto.SBLimitResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: BetLimitController 
* @Description: 投注限制Controller 
* @author Denny 
* @date 2013-8-20 下午2:07:10 
*  
*/
@Controller("betLimitController")
@RequestMapping("/game")
public class BetLimitController {

	private Logger log = LoggerFactory.getLogger(BetLimitController.class);

	@Resource(name = "betLimitServiceImpl")
	private IBetLimitService betLimitServiceImpl;

	@Resource(name = "gameMultipleServiceImpl")
	private IGameMultipleService gameMultipleService;

	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	
	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	
	/** 
	* @Title: queryBetLimit 
	* @Description: 4.30 查询投注限制
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/querySBLimit")
	@ResponseBody
	public Response<SBLimitResponse> quertSBLimit(@RequestBody @ValidRequestBody Request<SBLimitRequest> request) throws Exception {
		log.info("query querySBLimit ");
		Response<SBLimitResponse> response = new Response<SBLimitResponse>(request);
		
		String winray = configService.getConfigValueByKey("game", "sbwinray");
		SBLimitResponse re = jmapper.fromJson(winray, SBLimitResponse.class);
		if (re == null){
			re = new SBLimitResponse();
			re.setSlipCount(10L);
			re.setThreshold("90");
		}

		response.setResult(re);
		return response;
	}
	
	
	/** 
	* @Title: queryBetLimit 
	* @Description: 4.30 查询投注限制
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/setSBLimit")
	@ResponseBody
	public Response<SBLimitResponse> setSBLimit(
			@RequestBody @ValidRequestBody Request<SBLimitRequest> request) throws Exception {
		log.info("query querySBLimit ");
		Response<SBLimitResponse> response = new Response<SBLimitResponse>(request);
		String arg = request.getBody().getParam().getThreshold();
		Long count = request.getBody().getParam().getSlipCount();
		SBLimitRequest limit = request.getBody().getParam();
		if ( Long.valueOf(arg) < 0){
			limit.setThreshold("90");
		}
		if (count < 0){
			limit.setSlipCount(10L);
		}
		String sblimit = jmapper.toJson(limit);
		configService.saveConfig("game", "sbwinray", sblimit);
		SBLimitResponse re = new SBLimitResponse();
		re.setThreshold(limit.getThreshold());
		re.setSlipCount(limit.getSlipCount());
		response.setResult(re);
		return response;
	}
	/** 
	* @Title: queryBetLimit 
	* @Description: 4.30 查询投注限制
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryBetLimit")
	@ResponseBody
	public Response<BetLimitQueryResponse> queryBetLimit(
			@RequestBody @ValidRequestBody Request<BetLimitQueryRequest> request) throws Exception {

		log.debug("开始查询投注限制......");
		Response<BetLimitQueryResponse> response = new Response<BetLimitQueryResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();

		List<BetLimit> betLimitList;
		BetLimitQueryResponse result = new BetLimitQueryResponse();
		List<BetMethodMultipleStruc> betMethodMultipleStrucList = new ArrayList<BetMethodMultipleStruc>();

		try {
			betLimitList = betLimitServiceImpl.queryBetLimit(lotteryid);
			BetMethodMultipleStruc bmms;
			for (BetLimit bl : betLimitList) {
				List<BetLimitAssist> list = gameMultipleService.getGameMultipleAssistParentId(bl.getId());
				List<BetLimitAssistStruc> listStruc = new ArrayList<BetLimitAssistStruc>();
				if (list != null && !list.isEmpty()) {
					for (BetLimitAssist betLimitAssist : list) {
						BetLimitAssistStruc struc = new BetLimitAssistStruc();
						struc.setId(betLimitAssist.getId());
						struc.setParentId(bl.getId());
						struc.setMaxMultiple(betLimitAssist.getMaxMultiple_bak() == null ? betLimitAssist
								.getMaxMultiple() : betLimitAssist.getMaxMultiple_bak());
						struc.setMaxMultiple_bak(betLimitAssist.getMaxMultiple_bak() == null ? null : betLimitAssist
								.getMaxMultiple());
						struc.setRemark(betLimitAssist.getRemark());
						listStruc.add(struc);
					}
				}
				bmms = DTOConvert.betLimit2BetMethodMultipleStruc(bl);
				bmms.setAssistList(listStruc);
				betMethodMultipleStrucList.add(bmms);
			}
			result.setBetLimitList(betMethodMultipleStrucList);
			result.setStatus(betLimitServiceImpl.betLimitStatus(lotteryid));
			response.setResult(result);
		} catch (Exception e) {
			log.error("查询投注限制异常 ", e);
			throw e;
		}

		log.debug("查询投注限制完成。");
		return response;
	}

	/** 
	* @Title: queryBetLimit 
	* @Description: 投注首页查询投注限制
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryBetLimitByBet")
	@ResponseBody
	public Response<BetLimitQueryResponse> queryBetLimitByBet(
			@RequestBody @ValidRequestBody Request<BetLimitQueryByBetRequest> request) throws Exception {

		log.debug("投注页面开始查询投注限制......");
		Response<BetLimitQueryResponse> response = new Response<BetLimitQueryResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();

		List<BetLimit> betLimitList;
		BetLimitQueryResponse result = new BetLimitQueryResponse();
		List<BetMethodMultipleStruc> betMethodMultipleStrucList = new ArrayList<BetMethodMultipleStruc>();

		try {
			betLimitList = betLimitServiceImpl.queryBetLimitByBet(lotteryid);
			BetMethodMultipleStruc bmms;
			for (BetLimit bl : betLimitList) {
				bmms = DTOConvert.betLimit2BetMethodMultipleStrucForBetPage(bl);
				betMethodMultipleStrucList.add(bmms);
			}
			result.setBetLimitList(betMethodMultipleStrucList);
			result.setStatus(betLimitServiceImpl.betLimitStatus(lotteryid));
			response.setResult(result);
		} catch (Exception e) {
			log.error("投注页面查询投注限制异常 ", e);
			throw e;
		}

		log.debug("投注页面查询投注限制完成。");
		return response;
	}

	/** 
	* @Title: modifyBetLimit 
	* @Description: 4.31 修改投注限制
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/modifyBetLimit")
	@ResponseBody
	public Response<BetLimitModifyResponse> modifyBetLimit(
			@RequestBody @ValidRequestBody Request<BetLimitModifyRequest> request) throws Exception {

		log.debug("开始修改投注限制......");
		Response<BetLimitModifyResponse> response = new Response<BetLimitModifyResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();
		List<BetMethodMultipleStruc> modifyList = request.getBody().getParam().getBetLimitList();
		List<BetLimit> betLimitList = new ArrayList<BetLimit>();
		BetLimit bl;
		boolean isFirst = true;
		String spicalMultiple="";
		try {
			for (BetMethodMultipleStruc bmms : modifyList) {
				bl = DTOConvert.betMethodMultipleStruc2BetLimit(bmms, lotteryid);
				if(bl.getGameGroupCode() == 18 && bl.getGameSetCode() == 13 && bl.getBetMethodCode() == 65 &&bl.getMultipleIndex().equals("6")){
					spicalMultiple += bl.getMaxMultiple();
					bl.setSpecialLimit(spicalMultiple);
					
				}else if(bl.getGameGroupCode() == 18 && bl.getGameSetCode() == 13 && bl.getBetMethodCode() == 65 && !bl.getMultipleIndex().equals("6")){
					spicalMultiple += bl.getMaxMultiple() +",";
					continue;
				}
				betLimitList.add(bl);
			}
			betLimitServiceImpl.modifyBetLimit(betLimitList, lotteryid);
			List<BetLimitAssistStruc> assistList = request.getBody().getParam().getBetAssist();
			if(assistList != null && !assistList.isEmpty()){
				List<BetLimitAssist> list = new ArrayList<BetLimitAssist>();
				for(BetLimitAssistStruc betLimitAssistStruc:assistList){
					BetLimitAssist betLimitAssist = new BetLimitAssist();
					betLimitAssist.setId(betLimitAssistStruc.getId());
					betLimitAssist.setMaxMultiple_bak(betLimitAssistStruc.getMaxMultiple_bak());
					list.add(betLimitAssist);
				}
				betLimitServiceImpl.modifyBetLimitAssist(list);
			}
			
		} catch (Exception e) {
			log.error("修改投注限制异常 ", e);
			throw e;
		}

		log.debug("修改投注限制完成。");
		return response;
	}

	/** 
	* @Title: checkBetLimit 
	* @Description: 4.32 审核投注限制
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/checkBetLimit")
	@ResponseBody
	public Response<BetLimitCheckResponse> checkBetLimit(
			@RequestBody @ValidRequestBody Request<BetLimitCheckRequest> request) throws Exception {

		log.debug("开始审核投注限制......");
		Response<BetLimitCheckResponse> response = new Response<BetLimitCheckResponse>(request);
		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long auditType = request.getBody().getParam().getAuditType();

		try {
			betLimitServiceImpl.checkBetLimit(lotteryid, auditType);
		} catch (Exception e) {
			log.error("审核投注限制异常 ", e);
			throw e;
		}

		log.debug("审核投注限制完成。");
		return response;
	}

	/** 
	* @Title: publishBetLimit 
	* @Description: 4.33 发布投注限制
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/publishBetLimit")
	@ResponseBody
	public Response<BetLimitPublishResponse> publishBetLimit(
			@RequestBody @ValidRequestBody Request<BetLimitPublishRequest> request) throws Exception {

		log.debug("开始发布投注限制......");
		Response<BetLimitPublishResponse> response = new Response<BetLimitPublishResponse>(request);
		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long publishType = request.getBody().getParam().getPublishType();

		try {
			betLimitServiceImpl.publishBetLimit(lotteryid, publishType);

		} catch (Exception e) {
			log.error("发布投注限制异常 ", e);
			throw e;
		}

		log.debug("发布投注限制完成。");
		return response;
	}

	/** 
	* @Title: queryMaxGameIssue 
	* @Description: queryMaxGameIssue
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryMaxGameIssue")
	@ResponseBody
	public Response<Long> queryMaxGameIssue(@RequestBody Request<Long> request) throws Exception {

		log.debug("queryMaxGameIssue......");
		Response<Long> response = new Response<Long>(request);

		try {
			Long maxIssue = betLimitServiceImpl.getMaxGameIssue(request.getBody().getParam());
			response.setResult(maxIssue);

		} catch (Exception e) {
			log.error("queryMaxGameIssue error ", e);
			throw e;
		}

		log.debug("queryMaxGameIssue end");
		return response;
	}

}
