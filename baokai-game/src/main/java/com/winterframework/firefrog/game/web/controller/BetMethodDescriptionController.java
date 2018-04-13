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
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.service.IBetMethodDescriptionService;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionCheckRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionCheckResponse;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionJoinBonus;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionModifyRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionModifyResponse;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionQueryResponse;
import com.winterframework.firefrog.game.web.dto.BetMethodHelpStruc;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GetAllBettypeStatusResponse;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdRequest;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdResponse;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodRequest;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodResponse;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: BetMethodDescriptionController 
* @Description: 玩法描述Controller 
* @author Denny 
* @date 2013-8-20 下午2:07:10 
*  
*/
@Controller("betMethodDescriptionController")
@RequestMapping("/game")
public class BetMethodDescriptionController {
	private Logger log = LoggerFactory.getLogger(BetMethodDescriptionController.class);
	
	@Resource(name = "betMethodDescriptionServiceImpl")
	private IBetMethodDescriptionService betMethodDescriptionServiceImpl;
	
	/** 
	* @Title: queryBetMethodDescription 
	* @Description: 4.34 查询玩法描述
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryBetMethodDescription")
	@ResponseBody
	public Response<BetMethodDescriptionQueryResponse> queryBetMethodDescription(
			@RequestBody @ValidRequestBody Request<BetMethodDescriptionQueryRequest> request) throws Exception {

		log.info("开始查询玩法描述......");
		Response<BetMethodDescriptionQueryResponse> response = new Response<BetMethodDescriptionQueryResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();

		List<BetMethodDescription> betMethodDescriptionList = new ArrayList<BetMethodDescription>();
		BetMethodDescriptionQueryResponse result = new BetMethodDescriptionQueryResponse();
		List<BetMethodHelpStruc> betMethodHelpStrucList = new ArrayList<BetMethodHelpStruc>();
		String lotteryHelpDes;
		String lotteryHelpDesBak;
		Integer checkStatus;
		try {
			betMethodDescriptionList = betMethodDescriptionServiceImpl.queryBetMethodDescription(lotteryid);
			lotteryHelpDes = betMethodDescriptionServiceImpl.queryLotteryHelpDes(lotteryid);
			lotteryHelpDesBak = betMethodDescriptionServiceImpl.queryLotteryHeloDesBak(lotteryid);
			checkStatus = betMethodDescriptionServiceImpl.queryLotteryHelpCheckStatus(lotteryid);
			BetMethodHelpStruc bmms = new BetMethodHelpStruc();
			for (BetMethodDescription bl : betMethodDescriptionList) {
				bmms = DTOConvert.betMethodDescription2BetMethodHelpStruc(bl);
				betMethodHelpStrucList.add(bmms);
			}
			result.setBetMethodHelpStruc(betMethodHelpStrucList);
			result.setLotteryHelpDes(lotteryHelpDes);
			result.setLotteryHelpDes_bak(lotteryHelpDesBak);
            result.setCheckStatus(checkStatus);
			response.setResult(result);
		} catch (Exception e) {
			log.error("查询玩法描述异常 ", e);
			throw e;
		}

		log.info("查询玩法描述完成。");
		return response;
	}
	
	/** 
	* @Title: modifyBetMethodDescription 
	* @Description: 4.35 修改玩法描述
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/modifyBetMethodDescription")
	@ResponseBody
	public Response<BetMethodDescriptionModifyResponse> modifyBetMethodDescription(
			@RequestBody @ValidRequestBody Request<BetMethodDescriptionModifyRequest> request) throws Exception {

		log.info("开始修改玩法描述......");
		Response<BetMethodDescriptionModifyResponse> response = new Response<BetMethodDescriptionModifyResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();
		List<BetMethodHelpStruc> modifyList = request.getBody().getParam().getBetMethodHelpStruc();
		String lotteryHelpDes = request.getBody().getParam().getLotteryHelpDes();
		
		List<BetMethodDescription> BetMethodDescriptionList = new ArrayList<BetMethodDescription>();
		BetMethodDescription bl = new BetMethodDescription();
		try {
			for (BetMethodHelpStruc bmms : modifyList) {
				bl = DTOConvert.betMethodHelpStruc2BetMethodDescription(bmms, lotteryid);
				BetMethodDescriptionList.add(bl);
			}
			betMethodDescriptionServiceImpl.modifyBetMethodDescription(BetMethodDescriptionList, lotteryHelpDes, lotteryid);
		} catch (Exception e) {
			log.error("修改玩法描述异常 ", e);
			throw e;
		}

		log.info("修改玩法描述完成。");
		return response;
	}
	
	/** 
	* @Title: checkBetMethodDescription 
	* @Description: 4.36 审核玩法描述
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/checkBetMethodDescription")
	@ResponseBody
	public Response<BetMethodDescriptionCheckResponse> checkBetMethodDescription(
			@RequestBody @ValidRequestBody Request<BetMethodDescriptionCheckRequest> request) throws Exception {

		log.info("开始查询玩法描述......");
		Response<BetMethodDescriptionCheckResponse> response = new Response<BetMethodDescriptionCheckResponse>(request);
		Long lotteryid = request.getBody().getParam().getLotteryid();
		Long auditType = request.getBody().getParam().getAuditType();

		try {
			betMethodDescriptionServiceImpl.checkBetMethodDescription(lotteryid, auditType);
		} catch (Exception e) {
			log.error("查询玩法描述异常 ", e);
			throw e;
		}

		log.info("查询玩法描述完成。");
		return response;
	}

    /**
     * 发布
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/publishBetMethodDescription")
    @ResponseBody
    public Response<BetMethodDescriptionCheckResponse> publishBetMethodDescription(
            @RequestBody @ValidRequestBody Request<BetMethodDescriptionCheckRequest> request) throws Exception {

        log.info("开始查询玩法描述......");
        Response<BetMethodDescriptionCheckResponse> response = new Response<BetMethodDescriptionCheckResponse>(request);
        Long lotteryid = request.getBody().getParam().getLotteryid();
        Long auditType = request.getBody().getParam().getAuditType();

        try {
            betMethodDescriptionServiceImpl.publishBetMethodDescription(lotteryid, auditType);
        } catch (Exception e) {
            log.error("查询玩法描述异常 ", e);
            throw e;
        }

        log.info("查询玩法描述完成。");
        return response;
    }
	/** 
	* @Title: queryDescByBetMethod 
	* @Description: 按投注方式查询玩法描述
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryDescByBetMethod")
	@ResponseBody
	public Response<QueryDescByBetMethodResponse> queryDescByBetMethod(
			@RequestBody @ValidRequestBody Request<QueryDescByBetMethodRequest> request) throws Exception {

		log.info("开始查询玩法提示描述及投注示例......");
		Response<QueryDescByBetMethodResponse> response = new Response<QueryDescByBetMethodResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();
		int gameGroupCode = request.getBody().getParam().getGameGroupCode();
		int gameSetCode = request.getBody().getParam().getGameSetCode();
		int betMethodCode = request.getBody().getParam().getBetMethodCode(); 

		QueryDescByBetMethodResponse result = new QueryDescByBetMethodResponse();
		BetMethodDescription bmd = new BetMethodDescription();
		try {
			bmd = betMethodDescriptionServiceImpl.queryDescByBetMethod(lotteryid, gameGroupCode, gameSetCode, betMethodCode);
			if (bmd != null) {
				result.setGameExamplesDes(bmd.getGameExamplesDes());
				result.setGamePromptDes(bmd.getGamePromptDes());
			}
			response.setResult(result);
		} catch (Exception e) {
			log.error("查询玩法提示描述及投注示例异常 ", e);
			throw e;
		}

		log.info("查询玩法提示描述及投注示例完成。");
		return response;
	}
	
	
	/** 
	* @Title: queryDescByBetMethod 
	* @Description: 按投注方式查询玩法描述
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/queryDescByBetMethodByUserId")
	@ResponseBody
	public Response<QueryDescByBetMethodByUserIdResponse> queryDescByBetMethodByUserId(
			@RequestBody @ValidRequestBody Request<QueryDescByBetMethodByUserIdRequest> request) throws Exception {

		log.info("开始查询玩法提示描述及投注示例by用户id......");
		Response<QueryDescByBetMethodByUserIdResponse> response = new Response<QueryDescByBetMethodByUserIdResponse>(request);
		long lotteryid = request.getBody().getParam().getLotteryid();
		int gameGroupCode = request.getBody().getParam().getGameGroupCode();
		int gameSetCode = request.getBody().getParam().getGameSetCode();
		int betMethodCode = request.getBody().getParam().getBetMethodCode();
		long userid = request.getBody().getParam().getUserid();

		QueryDescByBetMethodByUserIdResponse result = new QueryDescByBetMethodByUserIdResponse();
		BetMethodDescriptionJoinBonus bmd = new BetMethodDescriptionJoinBonus();
		try {
			bmd = betMethodDescriptionServiceImpl.queryDescByBetMethod(lotteryid, userid,gameGroupCode, gameSetCode, betMethodCode);
			if (bmd != null) {
				result.setGameExamplesDes(bmd.getGameExamplesDes());
				result.setGamePromptDes(bmd.getGamePromptDes());
				result.setActualBonus(bmd.getActualBonus());
				result.setActualBonusDown(bmd.getActualBonusDown());
				result.setTheoryBonus(bmd.getTheoryBonus());
				result.setRetPoint(bmd.getRetPoint());
				result.setMoreBouns(bmd.getMoreBouns());
			}
			
			response.setResult(result);
		} catch (Exception e) {
			log.error("开始查询玩法提示描述及投注示例by用户id异常 ", e);
			throw e;
		}

		log.info("开始查询玩法提示描述及投注示例by用户id完成");
		return response;
	}
	
	
	/** 
	* @Title: queryBetMethodDescription 
	* @Description:查询所有的玩法
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getAllBettypeStatus")
	@ResponseBody
	public Response<GetAllBettypeStatusResponse> getAllBettypeStatus() throws Exception {

		log.info("开始查询所有玩法......");
		Response<GetAllBettypeStatusResponse> response = new Response<GetAllBettypeStatusResponse>();
		GetAllBettypeStatusResponse result=new GetAllBettypeStatusResponse();
		List<GameBettypeStatus> gbs=new ArrayList<GameBettypeStatus>();
		try {
			gbs=betMethodDescriptionServiceImpl.getAllBettypeStatus();
			result.setGameBettypeStatuss(gbs);
			response.setResult(result);
		} catch (Exception e) {
			log.error("查询所有玩法异常 ", e);
			throw e;
		}

		log.info("查询所有玩法完成。");
		return response;
	}
}