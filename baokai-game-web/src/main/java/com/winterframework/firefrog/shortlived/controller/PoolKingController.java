package com.winterframework.firefrog.shortlived.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingRound;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScore;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreRequest;
import com.winterframework.firefrog.shortlived.poolking.dto.PoolKingScoreResponse;
import com.winterframework.modules.spring.exetend.ExtendedPropertyPlaceholderConfigurer;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.modules.web.util.RequestContext;

@Controller("poolKingController")
@RequestMapping(value = "/poolking")
public class PoolKingController {

	private Logger logger = LoggerFactory.getLogger(PoolKingController.class);

	private JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	@Autowired
	private ExtendedPropertyPlaceholderConfigurer propertyConfigurer;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.shortlived.poolking.queryPoolKingScore")
	private String queryPoolKingScoreUrl;

	@PropertyConfig(value = "url.shortlived.poolking.queryPoolKingUserScore")
	private String queryPoolKingUserScoreUrl;

	@PropertyConfig(value = "shortlived.poolking.startDate")
	private String poolKingStartDate;

	@PropertyConfig(value = "shortlived.poolking.endDate")
	private String poolKingEndDate;

	@RequestMapping(value = "/main")
	public ModelAndView index() throws Exception {
		logger.info("poolKing mainPage");
		List<PoolKingRound> rounds = getRounds();
		PoolKingRound currentRound = getCurrentRound(rounds);
		logger.info("currentRound:" + jmapper.toJson(currentRound));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(poolKingStartDate);
		Date endDate = sdf.parse(poolKingEndDate);
		List<PoolKingScore> scores = queryScores(startDate, endDate);
		PoolKingScore userScore = null;
		userScore = queryUserScore(startDate, endDate);
		Date lastUpdateDate = getLastUpdateDate();
		ModelAndView view = new ModelAndView("/shortLived/poolKing/poolKing");
		view.addObject("rounds", rounds);
		view.addObject("scores", scores);
		view.addObject("userScore", userScore);
		view.addObject("lastUpdateDate", lastUpdateDate);
		return view;
	}

	@RequestMapping(value = "/history")
	public ModelAndView history() throws Exception {
		logger.info("poolKing history");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(poolKingStartDate);
		
		ModelAndView view = new ModelAndView(
				"/shortLived/poolKing/poolKingHist");
		List<PoolKingRound> rounds = getRounds();
		for (PoolKingRound round : rounds) {
			List<PoolKingScore> scores = queryScores(startDate,
					round.getEndDate());
			round.setScores(scores);
		}
		view.addObject("rounds", rounds);
		return view;
	}

	private List<PoolKingRound> getRounds() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PoolKingRound> rounds = new ArrayList<PoolKingRound>();
		boolean hasNextRound = true;
		int roundNum = 1;
		while (hasNextRound) {
			String roundJson = (String) propertyConfigurer
					.getProperty("shortlived.poolking.round" + roundNum);
			if (roundJson != null) {
				PoolKingRound round = jmapper.fromJson(roundJson,
						PoolKingRound.class);
				String start = (String) propertyConfigurer
						.getProperty("shortlived.poolking.round" + roundNum+".startDate");
				String end = (String) propertyConfigurer
						.getProperty("shortlived.poolking.round" + roundNum+".endDate");
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

	private PoolKingRound getCurrentRound(List<PoolKingRound> rounds) {
		Date now = new Date();
		PoolKingRound currentRound = new PoolKingRound();
		currentRound.setStartDate(new Date());
		currentRound.setEndDate(new Date());
		for (PoolKingRound round : rounds) {
			if (now.after(round.getStartDate())
					&& now.before(round.getEndDate())) {
				currentRound = round;
				break;
			}
		}
		return currentRound;
	}

	private List<PoolKingScore> queryScores(Date startDate, Date endDate) {
		List<PoolKingScore> scores = new ArrayList<PoolKingScore>();
		PoolKingScoreRequest request = new PoolKingScoreRequest();
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		try {
			Response<PoolKingScoreResponse> response = httpClient.invokeHttp(
					serverPath + queryPoolKingScoreUrl, request,
					PoolKingScoreResponse.class);
			scores = response.getBody().getResult().getScores();
			for (PoolKingScore score : scores) {
				score.setUserName(maskString(score.getUserName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scores;
	}

	private PoolKingScore queryUserScore(Date startDate, Date endDate) {
		PoolKingScore score = null;
		PoolKingScoreRequest request = new PoolKingScoreRequest();
		Long userId = RequestContext.getCurrUser().getId();
		String userName = RequestContext.getCurrUser().getUserName();
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		try {
			Response<PoolKingScoreResponse> response = httpClient.invokeHttp(
					serverPath + queryPoolKingUserScoreUrl, request, userId,
					userName, PoolKingScoreResponse.class);
			score = response.getBody().getResult().getUserScore();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (userName != null && score == null) {
			score = new PoolKingScore();
			score.setRowNo(999);
			score.setTotalAmount(0L);
			score.setUserId(userId);
			score.setUserName(userName);
		}
		return score;
	}

	private String maskString(String str) {
		int masklen = str.length() / 2;
		int front = 1;
		if (str.length() >= 7) {
			masklen = str.length() - 4;
			front = 2;
		}
		StringBuffer strMask = new StringBuffer();
		strMask.append(str.substring(0, front));
		for (int i = 0; i < 3; i++) {
			strMask.append("*");
		}
		strMask.append(str.substring(front + masklen));
		return strMask.toString();
	}

	private Date getLastUpdateDate() {
		Date now = new Date();
		int hour = (DateUtils.getHours(now) / 2) * 2;
		Date lastUpdateDate = DateUtils.addHours(
				DateUtils.getStartTimeOfDate(now), hour);
		return lastUpdateDate;
	}

}
