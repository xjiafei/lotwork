package com.winterframework.firefrog.shortlived.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.shortlived.monkeyActivity.dto.LeaderboardRequest;
import com.winterframework.firefrog.shortlived.monkeyActivity.dto.MonkeyActivityRound;
import com.winterframework.firefrog.shortlived.monkeyActivity.dto.MonkeyHistoryRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScore;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreResponse;
import com.winterframework.modules.spring.exetend.ExtendedPropertyPlaceholderConfigurer;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.modules.web.util.RequestContext;

@Controller("monkeyActivityController")
@RequestMapping(value = "/monkeyActivity")
public class MonkeyActivityController {
	
	private Logger logger = LoggerFactory.getLogger(PoolKingController.class);
	
	private JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@PropertyConfig(value = "url.shortlived.monkeyActivity.queryMonkeyActivityScore")
	private String queryMonkeyActivityScore;

	@PropertyConfig(value = "url.shortlived.monkeyActivity.queryScoresHistory")
	private String queryScoresHistory;
	
	@PropertyConfig(value = "shortlived.monkeyActivity.startDate")
	private String monkeyActivityStartDate;

	@PropertyConfig(value = "shortlived.monkeyActivity.endDate")
	private String monkeyActivityEndDate;
	
	@PropertyConfig(value = "shortlived.monkeyActivity.round3.startDate")
	private String round3startDate;

	@RequestMapping(value = "/main")
	public ModelAndView index() throws Exception {
		logger.info("monkeyActivity mainPage");
		List<MonkeyActivityRound> rounds = getRounds();
		MonkeyActivityRound currentRound = getCurrentRound(rounds);
		logger.info("currentRound:" + jmapper.toJson(currentRound));
		Date lastUpdateDate = getLastUpdateDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDate = sdf.parse(monkeyActivityEndDate);
		if(lastUpdateDate.getTime() > endDate.getTime()){
			lastUpdateDate = endDate;
		}
		Date round3start = sdf.parse(round3startDate);
		List<PoolKingScore> scores = null;
		if(round3start.getTime() < new Date().getTime()){
			scores = queryScores3(DateUtils.getStartTimeOfDate(lastUpdateDate), DateUtils.getEndTimeOfDate(lastUpdateDate));
		}else{
			scores = queryScores(DateUtils.getStartTimeOfDate(lastUpdateDate), DateUtils.getEndTimeOfDate(lastUpdateDate));
		}
		Map<String, Object> userScore = null;
		userScore = queryUserScore(scores);
		ModelAndView view = new ModelAndView("/shortLived/monkeyActivity/monkeyActivity");
		view.addObject("rounds", rounds);
		view.addObject("scores", userScore.get("scores"));
		view.addObject("userScore", userScore.get("userScore"));
		view.addObject("userRowNo", userScore.get("rowNo"));
		view.addObject("nowDate", new Date());
		view.addObject("leaderboardDate", lastUpdateDate);
		view.addObject("lastUpdateDate", lastUpdateDate);
		view.addObject("checkDate", DateUtils.getStartTimeOfDate(lastUpdateDate));
		return view;
	}
	
	@RequestMapping(value = "/history")
	public ModelAndView history() throws Exception {
		logger.info("monkeyActivity history");
		Long userId = RequestContext.getCurrUser().getId();
		MonkeyHistoryRequest request = new MonkeyHistoryRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(monkeyActivityStartDate);
		Date endDate = sdf.parse(monkeyActivityEndDate);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		request.setUserId(userId);
		ModelAndView view = new ModelAndView("/shortLived/monkeyActivity/monkeyActivityHist");
		Response<List<LeaderboardRequest>> scores = httpClient.invokeHttp(serverPath+queryScoresHistory,request,new TypeReference<Response<List<LeaderboardRequest>>>(){});
		Map<String, Object> res = queryUserLuckyMoney(scores.getBody().getResult());
		view.addObject("scores", res.get("scores"));
		view.addObject("totLuckyMoney", res.get("totLuckyMoney"));
		view.addObject("totKaiyun", res.get("totKaiyun"));
		return view;
	}

	@RequestMapping(value = "/rank_pre")
	public ModelAndView rank_pre() throws Exception {
		logger.info("monkeyActivity mainPage");
		List<MonkeyActivityRound> rounds = getRounds();
		MonkeyActivityRound currentRound = getCurrentRound(rounds);
		logger.info("currentRound:" + jmapper.toJson(currentRound));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date lastUpdateDate = getLastUpdateDate();
		Date queryDate = new Date();
		if(DateUtils.getDate(new Date()) == 2){
			queryDate = DateUtils.getEndTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -2));
		}else{
			queryDate = lastUpdateDate;
		}
		Date endDate = sdf.parse(monkeyActivityEndDate);
		if(queryDate.getTime() > endDate.getTime()){
			queryDate = endDate;
			lastUpdateDate = endDate;
		}
		Date round3start = sdf.parse(round3startDate);
		List<PoolKingScore> scores	= null;
		List<PoolKingScore> scores1	= null;
		if(round3start.getTime() < new Date().getTime()){
			scores = queryScores3(DateUtils.addDays(DateUtils.getStartTimeOfDate(queryDate),-1), DateUtils.addDays(DateUtils.getEndTimeOfDate(queryDate),-1));
			scores1 = queryScores3(DateUtils.getStartTimeOfDate(lastUpdateDate), DateUtils.getEndTimeOfDate(lastUpdateDate));
		}else{
			scores = queryScores(DateUtils.addDays(DateUtils.getStartTimeOfDate(queryDate),-1), DateUtils.addDays(DateUtils.getEndTimeOfDate(queryDate),-1));
			scores1 = queryScores(DateUtils.getStartTimeOfDate(lastUpdateDate), DateUtils.getEndTimeOfDate(lastUpdateDate));
		}
		//自己當天的紀錄
		Map<String, Object> userScore1 = null;
		userScore1 = queryUserScore(scores1);
		//前一天的排名
		Map<String, Object> userScore = null;
		userScore = queryUserScore(scores);
		ModelAndView view = new ModelAndView("/shortLived/monkeyActivity/monkeyActivity");
		view.addObject("rounds", rounds);
		view.addObject("scores", userScore.get("scores"));
		view.addObject("userScore", userScore1.get("userScore"));
		view.addObject("userRowNo", userScore1.get("rowNo"));
		view.addObject("nowDate", new Date());
		view.addObject("leaderboardDate", DateUtils.addDays(queryDate,-1));
		view.addObject("lastUpdateDate", lastUpdateDate);
		view.addObject("checkDate", DateUtils.getStartTimeOfDate(lastUpdateDate));
		return view;
	}
	
	private List<MonkeyActivityRound> getRounds() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<MonkeyActivityRound> rounds = new ArrayList<MonkeyActivityRound>();
		boolean hasNextRound = true;
		int roundNum = 1;
		while (hasNextRound) {
			String roundJson = (String) propertyConfigurer
					.getProperty("shortlived.monkeyActivity.round" + roundNum);
			if (roundJson != null) {
				MonkeyActivityRound round = jmapper.fromJson(roundJson,
						MonkeyActivityRound.class);
				String start = (String) propertyConfigurer
						.getProperty("shortlived.monkeyActivity.round" + roundNum+".startDate");
				String end = (String) propertyConfigurer
						.getProperty("shortlived.monkeyActivity.round" + roundNum+".endDate");
				round.setStartDate(sdf.parse(start));
				round.setEndDate(sdf.parse(end));
				rounds.add(round);
				roundNum++;
			} else {
				break;
			}
		}
		return rounds;
	}

	private MonkeyActivityRound getCurrentRound(List<MonkeyActivityRound> rounds) {
		Date now = new Date();
		MonkeyActivityRound currentRound = new MonkeyActivityRound();
		currentRound.setStartDate(new Date());
		currentRound.setEndDate(new Date());
		for (MonkeyActivityRound round : rounds) {
			if (now.after(round.getStartDate())
					&& now.before(round.getEndDate())) {
				currentRound = round;
				break;
			}
		}
		return currentRound;
	}

	private List<PoolKingScore> queryScores(Date startDate, Date endDate) {
		int lv1 = 0;
		int lv2 = 0;
		int lv3 = 0;
		List<PoolKingScore> scores = new ArrayList<PoolKingScore>();
		PoolKingScoreRequest request = new PoolKingScoreRequest();
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		try {
			Response<PoolKingScoreResponse> response = httpClient.invokeHttp(
					serverPath + queryMonkeyActivityScore, request,
					PoolKingScoreResponse.class);
			scores = response.getBody().getResult().getScores();
			for (PoolKingScore score : scores) {
				if(score.getTotalAmount() >= 30000000000l){
					if(lv1 == 2 && lv2 == 3 && lv3 == 15 ){score.setRowNo(11);}
					if(lv1 == 2 && lv2 == 3 && lv3 >= 8 && lv3 <= 14 ){score.setRowNo(10);lv3++;}
					if(lv1 == 2 && lv2 == 3 && lv3 >= 3 && lv3 <= 7 ){score.setRowNo(9);lv3++;}
					if(lv1 == 2 && lv2 == 3 && lv3 == 2){score.setRowNo(8);lv3++;}
					if(lv1 == 2 && lv2 == 3 && lv3 == 1){score.setRowNo(7);lv3++;}
					if(lv1 == 2 && lv2 == 3 && lv3 == 0){score.setRowNo(6);lv3++;}
					if(lv1 == 2 && lv2 == 2){score.setRowNo(5);lv2++;}
					if(lv1 == 2 && lv2 == 1){score.setRowNo(4);lv2++;}
					if(lv1 == 2 && lv2 == 0){score.setRowNo(3);lv2++;}
					if(lv1 == 1){score.setRowNo(2);lv1++;}
					if(lv1 == 0){score.setRowNo(1);lv1++;}
				}if(score.getTotalAmount() >= 15000000000l && score.getTotalAmount() < 30000000000l){
					if(lv2 == 3 && lv3 == 15 ){score.setRowNo(11);}
					if(lv2 == 3 && lv3 >= 8 && lv3 <= 14 ){score.setRowNo(10);lv3++;}
					if(lv2 == 3 && lv3 >= 3 && lv3 <= 7 ){score.setRowNo(9);lv3++;}
					if(lv2 == 3 && lv3 == 2){score.setRowNo(8);lv3++;}
					if(lv2 == 3 && lv3 == 1){score.setRowNo(7);lv3++;}
					if(lv2 == 3 && lv3 == 0){score.setRowNo(6);lv3++;}
					if(lv2 == 2){score.setRowNo(5);lv2++;}
					if(lv2 == 1){score.setRowNo(4);lv2++;}
					if(lv2 == 0){score.setRowNo(3);lv2++;}
				}if(score.getTotalAmount() >= 2000000000l && score.getTotalAmount() < 15000000000l){
					if(lv3 == 15 ){score.setRowNo(11);}
					if(lv3 >= 8 && lv3 <= 14 ){score.setRowNo(10);lv3++;}
					if(lv3 >= 3 && lv3 <= 7 ){score.setRowNo(9);lv3++;}
					if(lv3 == 2){score.setRowNo(8);lv3++;}
					if(lv3 == 1){score.setRowNo(7);lv3++;}
					if(lv3 == 0){score.setRowNo(6);lv3++;}
				}if(score.getTotalAmount() < 2000000000l){score.setRowNo(11);}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scores;
	}

	private List<PoolKingScore> queryScores3(Date startDate, Date endDate) {
		int lv1 = 0;
		int lv2 = 0;
		int lv3 = 0;
		List<PoolKingScore> scores = new ArrayList<PoolKingScore>();
		PoolKingScoreRequest request = new PoolKingScoreRequest();
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		try {
			Response<PoolKingScoreResponse> response = httpClient.invokeHttp(
					serverPath + queryMonkeyActivityScore, request,
					PoolKingScoreResponse.class);
			scores = response.getBody().getResult().getScores();
			for (PoolKingScore score : scores) {
				if(score.getTotalAmount() >= 15000000000l){
					if(lv1 == 2 && lv2 == 3 && lv3 == 15 ){score.setRowNo(11);}
					if(lv1 == 2 && lv2 == 3 && lv3 >= 8 && lv3 <= 14 ){score.setRowNo(10);lv3++;}
					if(lv1 == 2 && lv2 == 3 && lv3 >= 3 && lv3 <= 7 ){score.setRowNo(9);lv3++;}
					if(lv1 == 2 && lv2 == 3 && lv3 == 2){score.setRowNo(8);lv3++;}
					if(lv1 == 2 && lv2 == 3 && lv3 == 1){score.setRowNo(7);lv3++;}
					if(lv1 == 2 && lv2 == 3 && lv3 == 0){score.setRowNo(6);lv3++;}
					if(lv1 == 2 && lv2 == 2){score.setRowNo(5);lv2++;}
					if(lv1 == 2 && lv2 == 1){score.setRowNo(4);lv2++;}
					if(lv1 == 2 && lv2 == 0){score.setRowNo(3);lv2++;}
					if(lv1 == 1){score.setRowNo(2);lv1++;}
					if(lv1 == 0){score.setRowNo(1);lv1++;}
				}if(score.getTotalAmount() >= 5000000000l && score.getTotalAmount() < 15000000000l){
					if(lv2 == 3 && lv3 == 15 ){score.setRowNo(11);}
					if(lv2 == 3 && lv3 >= 8 && lv3 <= 14 ){score.setRowNo(10);lv3++;}
					if(lv2 == 3 && lv3 >= 3 && lv3 <= 7 ){score.setRowNo(9);lv3++;}
					if(lv2 == 3 && lv3 == 2){score.setRowNo(8);lv3++;}
					if(lv2 == 3 && lv3 == 1){score.setRowNo(7);lv3++;}
					if(lv2 == 3 && lv3 == 0){score.setRowNo(6);lv3++;}
					if(lv2 == 2){score.setRowNo(5);lv2++;}
					if(lv2 == 1){score.setRowNo(4);lv2++;}
					if(lv2 == 0){score.setRowNo(3);lv2++;}
				}if(score.getTotalAmount() >= 1000000000l && score.getTotalAmount() < 5000000000l){
					if(lv3 == 15 ){score.setRowNo(11);}
					if(lv3 >= 8 && lv3 <= 14 ){score.setRowNo(10);lv3++;}
					if(lv3 >= 3 && lv3 <= 7 ){score.setRowNo(9);lv3++;}
					if(lv3 == 2){score.setRowNo(8);lv3++;}
					if(lv3 == 1){score.setRowNo(7);lv3++;}
					if(lv3 == 0){score.setRowNo(6);lv3++;}
				}if(score.getTotalAmount() < 1000000000l){score.setRowNo(12);}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scores;
	}
//	private PoolKingScore queryUserScore(Date startDate, Date endDate) {
//		PoolKingScore score = null;
//		PoolKingScoreRequest request = new PoolKingScoreRequest();
//		Long userId = RequestContext.getCurrUser().getId();
//		String userName = RequestContext.getCurrUser().getUserName();
//		request.setStartDate(startDate);
//		request.setEndDate(endDate);
//		try {
//			Response<PoolKingScoreResponse> response = httpClient.invokeHttp(
//					serverPath + queryPoolKingUserScoreUrl, request, userId,
//					userName, PoolKingScoreResponse.class);
//			score = response.getBody().getResult().getUserScore();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (userName != null && score == null) {
//			score = new PoolKingScore();
//			score.setRowNo(999);
//			score.setTotalAmount(0L);
//			score.setUserId(userId);
//			score.setUserName(userName);
//		}
//		return score;
//	}

	private Map<String, Object> queryUserScore(List<PoolKingScore> scores) {
		Map<String, Object> filters = new HashMap<String, Object>();
		int count = 0;
		int rowNo = 0;
		PoolKingScore userScore = new PoolKingScore();
		String userName = RequestContext.getCurrUser().getUserName();
			for(PoolKingScore score : scores){
				if(score.getUserName().equals(userName)){
					userScore.setRowNo(score.getRowNo()); 
					userScore.setTotalAmount(score.getTotalAmount());
					userScore.setUserName(score.getUserName());
					rowNo = count+1;
				}
				count++;
				score.setUserName(maskString(score.getUserName()));
			}
		filters.put("scores", scores);
		filters.put("userScore", userScore);
		filters.put("rowNo", rowNo);
		return filters;
	}
	
	private String maskString(String str) {
		StringBuffer strMask = new StringBuffer();
		strMask.append(str.charAt(0));
		strMask.append("***");
		strMask.append(str.charAt(str.length()-1));
		return strMask.toString();
	}

	private Date getLastUpdateDate() {
		Date date = new Date();
		if(DateUtils.getDate(new Date()) == 7){
			date = DateUtils.getEndTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -1));
		}if(DateUtils.getDate(new Date()) == 1){
			date = DateUtils.getEndTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -2));
		}if(DateUtils.getDate(new Date()) >= 2 && DateUtils.getDate(new Date()) <= 6){
			date = getLastDate(DateUtils.currentDate());
		}
		return date;
	}
	
	private  Date getLastDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		return cal.getTime();
	}
	
	private Map<String, Object> queryUserLuckyMoney(List<LeaderboardRequest> scores) throws Exception {
		Map<String, Object> filters = new HashMap<String, Object>();
		Long totLuckyMoney = 0l;
		Long totKaiyun = 0l;
		List<LeaderboardRequest> response = new ArrayList<LeaderboardRequest>();
		List<MonkeyActivityRound> rounds = getRounds();
		for(int i = 2 ; i>=0 ; i--){
			for(LeaderboardRequest score : scores){
				if(rounds.get(i).getStartDate().getTime() <= score.getCreateTime().getTime() && rounds.get(i).getEndDate().getTime() >= score.getCreateTime().getTime()){
					LeaderboardRequest res = new LeaderboardRequest();
					res = score;
					res.setLuckyMoney(rounds.get(i).getRoundItems().get((int)(score.getLv()-1)).getLuckyMoney());
					totLuckyMoney += rounds.get(i).getRoundItems().get((int)(score.getLv()-1)).getLuckyMoney();
					res.setKaiyun(rounds.get(i).getRoundItems().get((int)(score.getLv()-1)).getKaiyun());
					totKaiyun += rounds.get(i).getRoundItems().get((int)(score.getLv()-1)).getKaiyun();
					response.add(res);
				}
			}
		}
		filters.put("scores", response);
		filters.put("totLuckyMoney", totLuckyMoney);
		filters.put("totKaiyun", totKaiyun);
		return filters;
	}
}
