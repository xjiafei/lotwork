package com.winterframework.firefrog.beginmession.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.beginmession.entity.BeginMissionData;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.YesOrNo;
import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.beginmession.web.dto.BeginMissionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginMissionResponse;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping("/begin/mission")
public class BeginMissionController {

	private static final Logger log = LoggerFactory.getLogger(BeginMissionController.class);
	
	@Autowired
	private BeginMissionService beginMissionService;
	
	
	@RequestMapping(value="/gotoNewMission")
	@ResponseBody
	public Response<BeginMissionResponse> gotoNewMission(@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)throws Exception{
		Response<BeginMissionResponse> response = new Response<BeginMissionResponse>(request);
		log.info("into gotoNewMission ");
		try {
			if (null != request.getBody()) {
				log.info("into gotoNewMission userId="+request.getBody().getParam().getUserId());
				final Long userId = request.getBody().getParam().getUserId();
				BeginMissionData data = beginMissionService.checkNewMission(userId);
				response.setResult(convertData(data));
				log.debug("is bind card="+data.getBindCardStatus());
			}
		} catch (Exception e) {
			log.error("gotoNewMission error:", e);
			throw e;
		}
		log.debug("----------------------bindCardStatus="+response.getBody().getResult().getBindCardStatus());
		
		return response;
	}
	
	@RequestMapping(value="/gotoDaillyMission")
	@ResponseBody
	public Response<BeginMissionResponse> getDailyMissionData(@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)throws Exception{
		Response<BeginMissionResponse> response = new Response<BeginMissionResponse>(request);
		log.info("into gotoDaillyMission ");
		try {
			if (null != request.getBody()) {
				log.info("into gotoDaillyMission userId="+request.getBody().getParam().getUserId());
				final Long userId = request.getBody().getParam().getUserId();
				BeginMissionData data = beginMissionService.getDailyMissionData(userId);
				response.setResult(convertData(data));
			}
		} catch (Exception e) {
			log.error("getDailyMissionData error:", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value="/gotoDaillyQuestion")
	@ResponseBody
	public Response<BeginMissionResponse> gotoDaillyQuestion(@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)throws Exception{
		Response<BeginMissionResponse> response = new Response<BeginMissionResponse>(request);
		log.info("into gotoDaillyQuestion ");
		try {
			if (null != request.getBody()) {
				log.info("into gotoDaillyQuestion userId="+request.getBody().getParam().getUserId());
				final Long userId = request.getBody().getParam().getUserId();
				BeginMissionData data = beginMissionService.getDayQuestion(userId);
				response.setResult(convertData(data));
			}
		} catch (Exception e) {
			log.error("getDailyMissionData error:", e);
			throw e;
		}
		return response;
	}
	
	
	@RequestMapping(value="/dailyAnswerAward")
	@ResponseBody
	public Response<BeginMissionResponse> dailyAnswerAward(@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)throws Exception{
		Response<BeginMissionResponse> response = new Response<BeginMissionResponse>(request);
		log.info("into dailyAnswerAward ");
		try {
			if (null != request.getBody()) {
				log.info("into dailyAnswerAward userId="+request.getBody().getParam().getUserId());
				final Long userId = request.getBody().getParam().getUserId();
				BeginMissionData data = beginMissionService.dailyAnswerAward(userId);
				response.setResult(convertData(data));
			}
		} catch (Exception e) {
			log.error("dailyAnswerAward error:", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value="/gotoEggLottery")
	@ResponseBody
	public Response<BeginMissionResponse> gotoEggLottery(@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)throws Exception{
		Response<BeginMissionResponse> response = new Response<BeginMissionResponse>(request);
		log.info("into gotoEggLottery ");
		try {
			if (null != request.getBody()) {
				log.info("into gotoEggLottery userId="+request.getBody().getParam().getUserId());
				final Long userId = request.getBody().getParam().getUserId();
				BeginMissionData data = beginMissionService.gotoEggLottery(userId);
				response.setResult(convertData(data));
			}
		} catch (Exception e) {
			log.error("gotoEggLottery error:", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value="/eggLotteryAward")
	@ResponseBody
	public Response<BeginMissionResponse> eggLotteryAward(@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)throws Exception{
		Response<BeginMissionResponse> response = new Response<BeginMissionResponse>(request);
		log.info("into eggLotteryAward ");
		try {
			if (null != request.getBody()) {
				log.info("into eggLotteryAward userId="+request.getBody().getParam().getUserId());
				final Long userId = request.getBody().getParam().getUserId();
				final Long lotteryType = request.getBody().getParam().getLotteryType();
				BeginMissionData data = beginMissionService.eggLotteryAward(userId,lotteryType);
				response.setResult(convertData(data));
			}
		} catch (Exception e) {
			log.error("gotoEggLottery error:", e);
			throw e;
		}
		return response;
	}
	
	@RequestMapping(value="/getUserBeginNewCharge")
	@ResponseBody
	public Response<BeginMissionResponse> getUserBeginNewCharge(@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)throws Exception{
		Response<BeginMissionResponse> response = new Response<BeginMissionResponse>(request);
		log.info("into eggLotteryAward ");
		try {
			if (null != request.getBody()) {
				log.info("into eggLotteryAward userId="+request.getBody().getParam().getUserId());
				final Long userId = request.getBody().getParam().getUserId();
				BeginMissionData data = beginMissionService.getUserBeginNewCharge(userId);
				response.setResult(convertData(data));
			}
		} catch (Exception e) {
			log.error("getUserBeginNewCharge error:", e);
			throw e;
		}
		return response;
	}
	
	
	
	private BeginMissionResponse convertData(BeginMissionData data){
		BeginMissionResponse res = new BeginMissionResponse();
		res.setMissionDay(data.getMissionDay());
		res.setBindCardDay(data.getBindCardDay());
		res.setNewMission(data.getNewMission());
		res.setDayBests(data.getDayBests());
		res.setTolBets(data.getTolBets());
		res.setTolBetDay(data.getTolBetDay());
		res.setBetDayList(data.getBetDayList());
		res.setQuestionData(data.getQuestionData());
		res.setDayques(data.getDayques());
		res.setAnsMoney(data.getAnsMoney());
		res.setAnswersDays(data.getAnswersDays());
		res.setLotteryMap(data.getLotteryMap());
		res.setLotteryAwardAmt(data.getLotteryAwardAmt());
		res.setCharges(data.getCharges());
		
		res.setIsNewMission(booleanToYN(data.getIsNewMission()));		
		res.setBindCardStatus(data.getBindCardStatus());
		res.setIsFirstChagre(booleanToYN(data.getIsFirstChagre()));
		res.setIsFirstWithdraw(booleanToYN(data.getIsFirstWithdraw()));
		res.setDayAnsFinish(booleanToYN(data.getDayAnsFinish()));		
		res.setErrorAwardFlag(data.getErrorAwardFlag());
		return res;
	}
	
	private String booleanToYN(Boolean flag){
		if(flag!=null && flag==true){
			return YesOrNo.YES.getValue();
		}else{
			return YesOrNo.NO.getValue();
		}
	}
	
}
