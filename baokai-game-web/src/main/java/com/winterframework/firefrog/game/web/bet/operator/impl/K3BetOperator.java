package com.winterframework.firefrog.game.web.bet.operator.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameTips;
import com.winterframework.firefrog.game.web.dto.HistoryBallsDTO;
import com.winterframework.firefrog.game.web.dto.HistoryBallsResultDTO;
import com.winterframework.firefrog.game.web.dto.NumberFrequencyCell;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.firefrog.game.web.dto.BetDetailStrucDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderRequestDTO;


/**
 * @Title 时时彩操作类
 * 
 * @author bob
 *
 */
public class K3BetOperator extends AbstractBetOperator {
	private Logger logger = LoggerFactory.getLogger(SSCBetOperator.class);

	@PropertyConfig(value = "web.server.url")
	private String webServerPath;

	@Override
	protected String formatLastBalls(GameIssueQueryResponse gameIssue) {

		// 为了在第一期时正常显示页面，初始化上期开奖号码
		String formatedLastBalls = "1,2,3";

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
			gi.setIssueCode(130320012L);
			gi.setLastWebIssueCode("20130320-012");
			gi.setLotteryId(lotteryId);
			gi.setNumberRecord("123");
			gi.setSaleEndTime(new Date().getTime() + 10000000);
			gi.setSaleStartTime(new Date().getTime() - 1000000);
			gi.setStatus(1);
			gi.setWebIssueCode("20130320-011");
			return gi;
		}
		logger.info("getCurrentGameIssue end..,");

		return gameIssueQueryResponse.getBody().getResult();

	}

	@Override
	public HistoryBallsResultDTO getTrendChart(String type) throws Exception {
		logger.info("queryNumberCharts start");

		HistoryBallsResultDTO result = new HistoryBallsResultDTO();
		HistoryBallsDTO historyBallsDTO = new HistoryBallsDTO();
		String historyBallsHtml = "";

		try {
			historyBallsHtml = getHtmlContent(type, this.lotteryId);

			logger.info("queryNumberCharts end");
		} catch (Exception e) {
			logger.error("queryNumberCharts is error.", e);
			throw e;
		}
		historyBallsDTO.setHistoryBalls(historyBallsHtml);
		historyBallsDTO.setGameTips(new GameTips());
		historyBallsDTO.setFrequency(new ArrayList<List<NumberFrequencyCell>>());

		result.setData(historyBallsDTO);
		result.setIsSuccess(1);
		result.setMsg("success");
		result.setType("userError");

		return result;
	}

	/**
	* @throws IOException
	* @Title: getHtmlContent
	* @Description: 生成页面HTML代码 
	* @param betMethodType
	* @param lotteryid    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private String getHtmlContent(String betMethodType, Long lotteryid) throws IOException {
		URL url = new URL(webServerPath + "/gameTrend/createNumberChartsDomK3" + "?betMethodType=" + betMethodType
				+ "&lotteryid=" + lotteryid);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestProperty("contentType", "utf-8");
		InputStream inStream = conn.getInputStream();
		String data = readInputStream(inStream);
		String html = new String(data);
		return html;
	}

	/**
	* @Title: readInputStream 
	* @Description:  读取输入流
	* @param @param inStream
	 * @throws IOException 
	*/
	private String readInputStream(InputStream inStream) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		String str = buffer.toString();
		return str;
	}
	
	protected GameOrderRequestDTO convertJsonToGameOrderRequestDTO(String data) throws Exception {
		GameOrderRequestDTO dto = convertJsonToObject(data, GameOrderRequestDTO.class);
		List<BetDetailStrucDTO> balls = dto.getBalls();
		for (BetDetailStrucDTO betDetailDto : balls) {
			String gameType = betDetailDto.getType();
			String[] gameTypes = gameType.split("\\.");
			if ("hezhi".equals(gameTypes[0])) {
				betDetailDto.setType(gameTypes[0] + "." + gameTypes[0] + "." + gameTypes[0]);
			}
		}
		return dto;
	}
}
