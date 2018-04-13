package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.phone.web.begin.entity.QuestionData;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewCharge;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewDaybet;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewDayques;
import com.winterframework.firefrog.phone.web.begin.vo.BeginNewTolbet;
import com.winterframework.firefrog.phone.web.cotroller.dto.BeginMissionRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.BeginMissionResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.InitMissionResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller
@RequestMapping("/iapi/begin/mission")
public class BeginMissionController extends BaseController {

	private static final Logger log = LoggerFactory
			.getLogger(BeginMissionController.class);

	@PropertyConfig(value = "url.begin.gotoNewMission")
	private String gotoNewMissionUrl;

	@PropertyConfig(value = "url.begin.gotoDaillyMission")
	private String gotoDaillyMission;

	@PropertyConfig(value = "url.begin.gotoDaillyQuestion")
	private String gotoDaillyQuestion;

	@PropertyConfig(value = "url.begin.gotoEggLottery")
	private String gotoEggLottery;

	@PropertyConfig(value = "url.begin.dailyAnswerAward")
	private String dailyAnswerAward;
	
	@PropertyConfig(value = "url.begin.eggLotteryAward")
	private String eggLotteryAward;
	
	@PropertyConfig(value = "url.begin.missioninfo")
	private String missioninfo;
	
	@RequestMapping(value = "/initMission")
	@ResponseBody
	public Response<InitMissionResponse> initMission(
	    		@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)
			throws Exception {
		Response<InitMissionResponse> response = new Response<InitMissionResponse>(request);
		InitMissionResponse initRes = new InitMissionResponse();
		log.info("initMission ");
		//預設失敗
		initRes.setIsSuccess(0L);
		initRes.setMessage("FAIL");
		
		String token = request.getHead().getSessionId();
		try {
		    String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			// test code
//			UserToken ut = new UserToken();
//			ut.setUserName("testnewbie6");
//			ut.setUserId(1302283L);
			// gotoNewMisssion
			BeginMissionRequest bmReq = new BeginMissionRequest();
			bmReq.setUserId(ut.getUserId());
			Response<BeginMissionResponse> newMisisonRes = httpClient.invokeHttp(
					firefrogUrl + gotoNewMissionUrl,bmReq,
					new TypeReference<Response<BeginMissionResponse>>() {
					});
			if("Y".equals(newMisisonRes.getBody().getResult().getIsNewMission())){
				Response<BeginMissionResponse> dailyMissionRes = httpClient.invokeHttp(
						firefrogUrl + gotoDaillyMission,bmReq,
						new TypeReference<Response<BeginMissionResponse>>() {
						});
	
				Response<BeginMissionResponse> dailyQuesRes = httpClient.invokeHttp(
						firefrogUrl + gotoDaillyQuestion,bmReq,
						new TypeReference<Response<BeginMissionResponse>>() {
						});
	
				Response<BeginMissionResponse> eggRes = httpClient.invokeHttp(
						firefrogUrl + gotoEggLottery, bmReq,
						new TypeReference<Response<BeginMissionResponse>>() {
						});
	
				Map<String, Object> obj = aggregatedData(newMisisonRes.getBody()
						.getResult(), dailyMissionRes.getBody().getResult(),
						dailyQuesRes.getBody().getResult(), eggRes.getBody().getResult());
	
				initRes.setIsSuccess(1L);
				initRes.setMessage("SUCCESS");
				initRes.setData(obj);
			}
		} catch (Exception e) {
			log.error("gotoNewMission error:", e);
		}
		response.setResult(initRes);
		return response;
	}

	@RequestMapping(value = "/dailyAnswerAward")
	@ResponseBody
	public Response<InitMissionResponse> dailyAnswerAward(
			@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)
			throws Exception {
		Response<InitMissionResponse> response = new Response<InitMissionResponse>(request);
		InitMissionResponse res = new InitMissionResponse();
		Map<String, Object> data = new HashMap<String, Object>();

		Response<BeginMissionResponse> newMisisonRes = new Response<BeginMissionResponse>(request);
		log.info("dailyAnswerAward");
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			// test code
//			UserToken ut = new UserToken();
//			ut.setUserName("ami888");
//			ut.setUserId(1339224L);
			// gotoNewMisssion
			BeginMissionRequest bmReq = new BeginMissionRequest();
			bmReq.setUserId(ut.getUserId());
			newMisisonRes = httpClient.invokeHttp(firefrogUrl
					+ dailyAnswerAward, bmReq,
					new TypeReference<Response<BeginMissionResponse>>() {
					});

			Long answersDays = newMisisonRes.getBody().getResult()
					.getAnswersDays() != null ? newMisisonRes.getBody()
					.getResult().getAnswersDays() : 0L;
					
			Long money = new BigDecimal(newMisisonRes.getBody().getResult()
					.getAnsMoney().toString())
					.setScale(2, RoundingMode.HALF_UP).longValue();
			
			if ("Y".equals(newMisisonRes.getBody().getResult().getIsNewMission())) {
				if ("N".equals(newMisisonRes.getBody().getResult().getErrorAwardFlag())) {
					res.setIsSuccess(1L);
					res.setMessage("成功");
					data.put("money", money);
				} else {
					res.setIsSuccess(0L);
					res.setMessage("今日已答題完畢");
					data.put("money", 0L);
				}
			} else {
				res.setIsSuccess(0L);
				res.setMessage("您已失去参与活动资格");
				data.put("money", 0L);
			}
			data.put("answersDays", answersDays);
		} catch (Exception e) {
			log.error("dailyAnswerAward error:", e);
			throw e;
		}
		res.setData(data);
		response.setResult(res);
		return response;
	}
	
	@RequestMapping(value="/eggLotteryAward")
	@ResponseBody
	public Response<InitMissionResponse> eggLotteryAward(
			@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)
			throws Exception{
		Response<InitMissionResponse> response = new Response<InitMissionResponse>(request);
		InitMissionResponse res = new InitMissionResponse();
		Map<String, Object> data = new HashMap<String, Object>();

		Response<BeginMissionResponse> newMisisonRes = new Response<BeginMissionResponse>(request);
		log.info("eggLotteryAward");
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			// test code
//			UserToken ut = new UserToken();
//			ut.setUserName("ami888");
//			ut.setUserId(1339224L);
			// gotoNewMisssion
			BeginMissionRequest bmReq = new BeginMissionRequest();
			bmReq.setUserId(ut.getUserId());
			bmReq.setLotteryType(request.getBody().getParam().getLotteryType());
			newMisisonRes = httpClient.invokeHttp(firefrogUrl + eggLotteryAward, bmReq,
					new TypeReference<Response<BeginMissionResponse>>(){});
			Long money = newMisisonRes.getBody().getResult().getLotteryAwardAmt()!=null?new BigDecimal(newMisisonRes.getBody().getResult()
					.getLotteryAwardAmt().toString())
					.setScale(2, RoundingMode.HALF_UP).longValue():0L;
			if ("Y".equals(newMisisonRes.getBody().getResult().getIsNewMission())) {
				if ("N".equals(newMisisonRes.getBody().getResult().getErrorAwardFlag())) {
					res.setIsSuccess(1L);
					res.setMessage("成功");
					data.put("money", money);
				} else {
					res.setIsSuccess(0L);
					res.setMessage("砸蛋已完成");
					data.put("money", 0L);
				}
			}else{
				res.setIsSuccess(0L);
				res.setMessage("您已失去参与活动资格");
				data.put("money", money);
			}
		} catch (Exception e) {
			log.error("eggLotteryAward error:", e);
			throw e;
		}
		res.setData(data);
		response.setResult(res);
		return response;
	}
	
	@RequestMapping(value="/missioninfoAction")
	@ResponseBody
	public Response<InitMissionResponse> missioninfoAction(
			@RequestBody @ValidRequestBody Request<BeginMissionRequest> request)
			throws Exception{
		Response<InitMissionResponse> response = new Response<InitMissionResponse>(request);
		InitMissionResponse res = new InitMissionResponse();
		res.setIsSuccess(0L);
		Map<String, Object> data = new HashMap<String, Object>();
		Response<BeginMissionResponse> newMisisonRes = new Response<BeginMissionResponse>(request);
		log.info("missioninfoAction");
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
//			// test code
//			UserToken ut = new UserToken();
//			ut.setUserName("testnewbie");
//			ut.setUserId(1339319L);
			// gotoNewMisssion
			BeginMissionRequest bmReq = new BeginMissionRequest();
			bmReq.setUserId(ut.getUserId());
			bmReq.setLotteryType(request.getBody().getParam().getLotteryType());
			newMisisonRes = httpClient.invokeHttp(firefrogUrl + missioninfo, bmReq,
					new TypeReference<Response<BeginMissionResponse>>(){});
			List<List<Long>> missionInfo = new ArrayList<List<Long>>();
			List<BeginNewCharge> charges = newMisisonRes.getBody().getResult().getCharges();
			if(charges.size() > 0){
				for(BeginNewCharge charge:charges){
					List<Long> chargeAry = new ArrayList<Long>();
					chargeAry.add(charge.getChargeAmt());
					chargeAry.add(charge.getChargePreium());
					chargeAry.add(charge.getChargePer());
					chargeAry.add(charge.getChargeFactor());
					chargeAry.add(charge.getBettingDate());
					missionInfo.add(chargeAry);
				}
				data.put("help", missionInfo);
				res.setIsSuccess(1L);
				res.setData(data);
			}
		} catch (Exception e) {
			log.error("eggLotteryAward error:", e);
		}
		
		response.setResult(res);
		return response;
	}
	
	private Map<String, Object> aggregatedData(
			BeginMissionResponse newMisisonRes,
			BeginMissionResponse dailyMissionRes,
			BeginMissionResponse dailyQuesRes, BeginMissionResponse eggRes)
			throws Exception {
		// 有新手資格才往下做
		Map<String, Object> data = new HashMap<String, Object>();
		if ("Y".equals(newMisisonRes.getIsNewMission())) {

			Map<String, Object> countDown = new HashMap<String, Object>();
			Map<String, Object> mission = new HashMap<String, Object>();
			Map<String, Object> daily = new HashMap<String, Object>();
			Map<String, Object> question = new HashMap<String, Object>();
			Map<String, Object> egg = new HashMap<String, Object>();
			if (null != newMisisonRes) {
				countDown.put("isbinded",newMisisonRes.getBindCardStatus());
				countDown.put("bankDays", newMisisonRes.getBindCardDay());
				countDown.put("gameDays", newMisisonRes.getMissionDay());
				mission.put("tipsBank", newMisisonRes.getNewMission()
						.getBindCardText());
				mission.put("tipsCard", newMisisonRes.getNewMission()
						.getChargeText());
				mission.put("tipsWithdraw", newMisisonRes.getNewMission()
						.getWithdrawText());
				mission.put("isFinish1",newMisisonRes.getBindCardStatus());
				mission.put("isFinish2",
						"Y".equals(newMisisonRes.getIsFirstChagre()) ? 1L : 0L);
				mission.put("isFinish3", "Y".equals(newMisisonRes
						.getIsFirstWithdraw()) ? 1L : 0L);
				daily.put("isbinded",newMisisonRes.getBindCardStatus());
			}

			if (null != dailyMissionRes) {
				List<Map<String, Object>> achievedList = new ArrayList<Map<String, Object>>();

				daily.put(
						"countDays",
						dailyMissionRes.getTolBetDay() != null ? dailyMissionRes
								.getTolBetDay() : 0L);
				for (int i = 0; i < dailyMissionRes.getTolBets().size(); i++) {
					BeginNewTolbet tolBet = dailyMissionRes.getTolBets().get(i);
					daily.put(convertLevel(i), tolBet.getIsSuccess() ? 1L : 0L);
					daily.put("countDay" + (i + 1), tolBet.getTolbetDate());
					daily.put("isAmount" + (i + 1), tolBet.getIsAmount());					
					daily.put("countMoney" + (i + 1), tolBet.getAmount());
					daily.put("lottery" + (i + 1), tolBet.getLottery());
					daily.put("isLottery" + (i + 1), tolBet.getIsLottery());
					daily.put("lotteryType" + (i + 1), tolBet.getLotteryType());					
				}
				daily.put("dateList", dailyMissionRes.getBetDayList());

				for (int i = 0; i < dailyMissionRes.getDayBests().size(); i++) {
					BeginNewDaybet dayBet = dailyMissionRes.getDayBests()
							.get(i);
					Map<String, Object> achieve = new HashMap<String, Object>();
					achieve.put("standard", dayBet.getDaybetAmount());
					achieve.put("times", "N".equals(dayBet.getIsLottery()) ? 0L
							: dayBet.getLottery());
					achieve.put("reward", "N".equals(dayBet.getIsAmount()) ? 0L
							: dayBet.getAmount());
					achieve.put(
							"type",
							dayBet.getLotteryType() != null ? dayBet
									.getLotteryType() : 0L);
					achieve.put("achieve", dayBet.getIsSuccess() ? 1L : 0L);
					achievedList.add(achieve);
				}
				daily.put("achievedList", achievedList);
			}

			if (null != dailyQuesRes) {
				List<Map<String, Object>> answerList = new ArrayList<Map<String, Object>>();

				question.put("isFinished",
						"Y".equals(dailyQuesRes.getDayAnsFinish()) ? 1L : 0L);
				question.put(
						"getMoney",
						dailyQuesRes.getAnsMoney() != null ? new BigDecimal(
								dailyQuesRes.getAnsMoney().toString())
								.setScale(2, RoundingMode.HALF_UP).longValue()
								: 0);
				question.put(
						"answersDays",
						dailyQuesRes.getAnswersDays() != null ? dailyQuesRes
								.getAnswersDays() : 0L);
				for (int i = 0; i < dailyQuesRes.getDayques().size(); i++) {
					BeginNewDayques dayQues = dailyQuesRes.getDayques().get(i);
					question.put("answersDay" + (i + 1),
							dayQues.getAnswerDate());
					question.put("answersMoney" + (i + 1), dayQues.getLottery());
					question.put("prizeType" + (i + 1),
							dayQues.getLotteryCss());
				}

				for (int i = 0; i < dailyQuesRes.getQuestionData().size(); i++) {
					QuestionData quesData = dailyQuesRes.getQuestionData().get(
							i);
					Map<String, Object> answer = new HashMap<String, Object>();
					answer.put("title", quesData.getTitle());
					answer.put("answer", quesData.getAnswer());
					answer.put("correct", quesData.getCorrect());
					answerList.add(answer);
				}
				//問題列表
				question.put("answerList", answerList);
			}

			if (eggRes != null) {
				egg.put("copper",
						eggRes.getLotteryMap().get("coppor") != null ? eggRes
								.getLotteryMap().get("coppor") : 0);
				egg.put("silver",
						eggRes.getLotteryMap().get("silver") != null ? eggRes
								.getLotteryMap().get("silver") : 0);
				egg.put("golden",
						eggRes.getLotteryMap().get("golden") != null ? eggRes
								.getLotteryMap().get("golden") : 0);
			}
			data.put("countdown", countDown);
			data.put("mission", mission);
			data.put("daily", daily);
			data.put("question", question);
			data.put("egg", egg);
		}
		return data;
	}

	private String convertLevel(int index) throws Exception {
		switch (index) {
		case 0:
			return "levelfirst";
		case 1:
			return "levelsecond";
		case 2:
			return "levelthird";
		default:
			throw new Exception("convertLevel index error");
		}
	}
}
