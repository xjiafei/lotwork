package com.winterframework.firefrog.game.web.bet.operator.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.modules.web.jsonresult.Response;

/**
 * @Title 时时彩操作类
 * 
 * @author bob
 *
 */
public class SSCBetOperator extends AbstractBetOperator {
	private Logger logger = LoggerFactory.getLogger(SSCBetOperator.class);

	@Override
	protected String formatLastBalls(GameIssueQueryResponse gameIssue) {

		// 为了在第一期时正常显示页面，初始化上期开奖号码
		String formatedLastBalls = "1,2,3,4,5";

		//将开奖号码用逗号分隔
		if (gameIssue.getNumberRecord() != null) {//测试阶段 初始化数据时这里的获取可能为null
			char[] repeatChars = gameIssue.getNumberRecord().toCharArray();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < repeatChars.length; i++) {
				sb.append(repeatChars[i]);
				if (i != repeatChars.length - 1) {
					sb.append(",");
				}
			}
			formatedLastBalls = sb.toString();//获取上期开奖号码
		}

		return formatedLastBalls;
	}

	@Override
	protected GameIssueQueryResponse getCurrentGameIssue() throws Exception {
		logger.info("getCurrentGameIssue start...");

		GameIssueQueryRequest gameIssueQueryRequest = new GameIssueQueryRequest();
		gameIssueQueryRequest.setLotteryId(lotteryId);

		Response<GameIssueQueryResponse> gameIssueQueryResponse = betHttpClient
				.getCurrentGameIssue(gameIssueQueryRequest);

		//当前期不存在时做模拟当前奖期的数据
		if (gameIssueQueryResponse == null || gameIssueQueryResponse.getBody() == null
				|| gameIssueQueryResponse.getBody().getResult() == null) {
			GameIssueQueryResponse gi = new GameIssueQueryResponse();
			gi.setIssueCode(20130301101001L);
			gi.setLastWebIssueCode("20130228-120");
			gi.setLotteryId(lotteryId);
			gi.setNumberRecord("12345");
			gi.setSaleEndTime(new Date().getTime() + 10000000);
			gi.setSaleStartTime(new Date().getTime() - 1000000);
			gi.setStatus(1);
			gi.setWebIssueCode("20130301-120");
			return gi;
		}
		logger.info("getCurrentGameIssue end..,");

		return gameIssueQueryResponse.getBody().getResult();

	}
}
