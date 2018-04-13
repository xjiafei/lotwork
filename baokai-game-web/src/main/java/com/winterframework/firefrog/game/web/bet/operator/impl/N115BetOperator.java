package com.winterframework.firefrog.game.web.bet.operator.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.web.dto.GameBetJsonDateStruc;
import com.winterframework.firefrog.game.web.dto.GameBetJsonResultStruc;
import com.winterframework.firefrog.game.web.dto.GameBetTplData;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: N115BetOperator 
* @Description: 11选5操作类
* @author david 
* @date 2014-3-25 下午3:30:56 
*  
*/
public class N115BetOperator extends AbstractBetOperator {

	private Logger logger = LoggerFactory.getLogger(SSCBetOperator.class);
	@Override
	protected String formatLastBalls(GameIssueQueryResponse gameIssue) {

		// 为了在第一期时正常显示页面，初始化上期开奖号码
		String formatedLastBalls = "01,02,03,04,05";

		//将开奖号码用逗号分隔
		if (gameIssue.getNumberRecord() != null) {//测试阶段 初始化数据时这里的获取可能为null
			return gameIssue.getNumberRecord();
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
			gi.setNumberRecord("01,02,03,04,05");
			gi.setSaleEndTime(new Date().getTime() + 10000000);
			gi.setSaleStartTime(new Date().getTime() - 1000000);
			gi.setStatus(1);
			gi.setWebIssueCode("20130301-120");
			return gi;
		}
		logger.info("getCurrentGameIssue end..,");

		return gameIssueQueryResponse.getBody().getResult();

	}
	
	public String formatOrderNumber(String numberRecord) {
		return numberRecord;
	}
}
