package com.winterframework.firefrog.game.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.winterframework.firefrog.game.web.bet.convertor.IBetNameConvertor;
import com.winterframework.firefrog.game.web.bet.util.BetHttpJsonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.entity.GameLryl;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BetChartStrucForBetUI;
import com.winterframework.firefrog.game.web.dto.BetChartStrucKl8;
import com.winterframework.firefrog.game.web.dto.GameColdAndHotNumbersQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameLrylQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameMissingValueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameTips;
import com.winterframework.firefrog.game.web.dto.GameTipsByBet;
import com.winterframework.firefrog.game.web.dto.GetAwardDTO;
import com.winterframework.firefrog.game.web.dto.GetAwardResultDTO;
import com.winterframework.firefrog.game.web.dto.HistoryBallsDTO;
import com.winterframework.firefrog.game.web.dto.HistoryBallsResultDTO;
import com.winterframework.firefrog.game.web.dto.NumberFrequencyCell;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionRequest;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionResponse;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdRequest;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdResponse;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.game.web.util.SSCBetNameUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: GameDrawWebController 
* @Description: 开奖统计相关前端Controller 
* @author Denny 
* @date 2013-9-26 下午2:02:29 
*  
*/
@Controller("gameDrawWebController")
@RequestMapping(value = "/gameTrend")
public class GameDrawWebController {

	private Logger logger = LoggerFactory.getLogger(GameDrawWebController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "web.server.url")
	private String webServerPath;

	@PropertyConfig(value = "url.game.queryAssistAction")
	private String queryAssistActionUrl;

	@PropertyConfig(value = "url.game.queryKl8AssistAction")
	private String queryKl8AssistActionUrl;

	@PropertyConfig(value = "url.game.queryColdAndHotNumbers")
	private String queryColdAndHotNumbersUrl;

	@PropertyConfig(value = "url.game.queryMissingValue")
	private String queryMissingValueUrl;

	@PropertyConfig(value = "url.game.queryDescByBetMethodByUserId")
	private String queryBetMethodDescUrl;

	@PropertyConfig(value = "url.gameBet.createNumberChartsDom")
	private String createNumberChartsDomUrl;
	@Resource(name = "betHttpClient")
	private BetHttpJsonClient betHttpClient;

	@PropertyConfig(value = "url.connect.chart")
	private String chartServerPath;

	/**
	 * 
	* @Title: createNumberChartsDom 
	* @Description: 生成号码走势图页面DOM
	* @param request
	* @return
	* @throws Exception
	 */
	@Deprecated
	@RequestMapping(value = "/createNumberChartsDom")
	public String createNumberChartsDom(@RequestParam("betMethodType") String betMethodType,
			@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		logger.info("createNumberChartsDom start");

		Response<QueryAssistActionResponse> response = new Response<QueryAssistActionResponse>();
		betMethodType = betMethodType.replaceAll("\"", "");
		QueryAssistActionRequest request = makeQueryAssistActionRequest(betMethodType, lotteryid);

		try {
			response = httpClient.invokeHttp(serverPath + queryAssistActionUrl, request,
					QueryAssistActionResponse.class);
			logger.info("createNumberChartsDom end");
		} catch (Exception e) {
			logger.error("createNumberChartsDom is error.", e);
			throw e;
		}
		List<BaseChartStruc> list = response.getBody().getResult().getList();
		List<BetChartStrucForBetUI> betCharts = new ArrayList<BetChartStrucForBetUI>();
		BetChartStrucForBetUI betChart = null;
		for (BaseChartStruc s : list) {
			betChart = new BetChartStrucForBetUI();
			betChart.setWebIssueCode(s.getWebIssueCode());
			betChart.setNumberRecord(s.getNumberRecord());

			//			if (s.getZxChartStrucList() != null) {
			//				List<ZxChartStruc> zxChartStrucList = s.getZxChartStrucList();
			//				List<String> ll = new ArrayList<String>();
			//				for (ZxChartStruc zxChartStruc : zxChartStrucList) {
			//					ll.add(zxChartStruc.getChartContent());
			//				}
			//
			//				betChart.setZxChartStrucList(makeChartStrucList(ll));
			//			}
			//			if (s.getZuxuanbaseChartStrucList() != null) {
			//				List<ZuxuanbaseChartStruc> zuxuanbaseChartStrucList = s.getZuxuanbaseChartStrucList();
			//				List<String> ll = new ArrayList<String>();
			//				for (ZuxuanbaseChartStruc zuxuanbaseChartStruc : zuxuanbaseChartStrucList) {
			//					ll.add(zuxuanbaseChartStruc.getChartContent());
			//				}
			//
			//				betChart.setZuxuanbaseChartStrucList(makeChartStrucList(ll));
			//			}
			//			if (s.getHzbaseChartStrucList() != null) {
			//				List<HzbaseChartStruc> hzbaseChartStrucList = s.getHzbaseChartStrucList();
			//				List<String> ll = new ArrayList<String>();
			//				for (HzbaseChartStruc hzbaseChartStruc : hzbaseChartStrucList) {
			//					ll.add(hzbaseChartStruc.getChartContent());
			//				}
			//
			//				betChart.setHzbaseChartStrucList(makeChartStrucList(ll));
			//			}

			betCharts.add(betChart);
		}

		model.addAttribute("betCharts", betCharts);
		model.addAttribute("request", request);

		return "/bet/chart/" + betMethodType;

	}

	/**
	 * 
	* @Title: createKl8Html 
	* @Description: 生成号码走势图页面DOM
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createNumberChartsDomKl8")
	public String createNumberChartsDomKl8(@RequestParam("betMethodType") String betMethodType,
			@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		logger.info("createNumberChartsDomKl8 start");
		betMethodType = betMethodType.replaceAll("\"", "");
		QueryAssistActionRequest request = makeQueryAssistActionRequest(betMethodType, lotteryid);
		request.setCount(30);
		List<BetChartStrucKl8> betCharts = new ArrayList<BetChartStrucKl8>();
		try {
			betCharts = (List<BetChartStrucKl8>) httpClient
					.invokeHttp(serverPath + queryKl8AssistActionUrl, request,
							new TypeReference<Response<List<BetChartStrucKl8>>>() {
							}).getBody().getResult();
			logger.info("createNumberChartsDomKl8 end");
		} catch (Exception e) {
			logger.error("createNumberChartsDomKl8 is error.", e);
			throw e;
		}

		model.addAttribute("betCharts", betCharts);
		model.addAttribute("request", request);

		return "/bet/bjkl8/chart/" + betMethodType;
	}

	/**
	 * 
	* @Title: createK3Html 
	* @Description: 生成号码走势图页面DOM
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/createNumberChartsDomK3")
	public String createNumberChartsDomK3(@RequestParam("betMethodType") String betMethodType,
			@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		logger.info("createNumberChartsDomK3 start");
		//betMethodType = betMethodType.replaceAll("\"", "");
		QueryAssistActionRequest request = new QueryAssistActionRequest();
		request.setLotteryid(lotteryid);
		request.setGameGroupCode(34);
		request.setCount(30);
		QueryAssistActionResponse actionResponse = null;
		try {
			actionResponse = (QueryAssistActionResponse) httpClient
					.invokeHttp(chartServerPath + queryAssistActionUrl, request,
							new TypeReference<Response<QueryAssistActionResponse>>() {
							}).getBody().getResult();
			logger.info("createNumberChartsDomK3 end");
		} catch (Exception e) {
			logger.error("createNumberChartsDomK3 is error.", e);
			throw e;
		}
		List<Object> list = new ArrayList<Object>();
		if (actionResponse != null && actionResponse.getData() != null) {
			list = actionResponse.getData();
			for (Object object : list) {
				List<Object> dataList = (List) object;
				String[] number = String.valueOf(dataList.get(1)).split("");
				List<Long> trendListhaomao = (List<Long>) dataList.get(2);
				List<Object> copytrendListhaomao = new ArrayList<Object>();
				for (int i = 0; i < trendListhaomao.size(); i++) {
					List<Long> eachList = new ArrayList<Long>();
					long val = Long.parseLong(String.valueOf(trendListhaomao.get(i)));
					eachList.add(val);
					Long numberval = 0l;
					for (String nm : number) {
						if (nm != null && !nm.equals("")) {
							if (Long.valueOf(nm).equals(Long.valueOf(i + 1))) {
								numberval++;
							}
						}
					}
					eachList.add(numberval);
					copytrendListhaomao.add(eachList);
				}
				dataList.set(2, copytrendListhaomao);
			}

		}
		model.addAttribute("betCharts", list);
		model.addAttribute("request", request);
		return "/bet/k3/chart/k3.chart";
	}

	@RequestMapping(value = "/getKl8ChartResult")
	@ResponseBody
	public Object getKl8ChartResult(@RequestParam("count") Integer count, @RequestParam("type") String type, Model model)
			throws Exception {
		logger.info("getKl8ChartResult start");
		type = type.replaceAll("\"", "");
		QueryAssistActionRequest request = makeQueryAssistActionRequest(type, 99201l);
		request.setCount(count);
		List<BetChartStrucKl8> betCharts = new ArrayList<BetChartStrucKl8>();
		try {
			betCharts = (List<BetChartStrucKl8>) httpClient
					.invokeHttp(serverPath + queryKl8AssistActionUrl, request,
							new TypeReference<Response<List<BetChartStrucKl8>>>() {
							}).getBody().getResult();
			logger.info("getKl8ChartResult end");
		} catch (Exception e) {
			logger.error("getKl8ChartResult is error.", e);
			throw e;
		}
		Long shang = 0l, zhong = 0l, xia = 0l, ji = 0l, he = 0l, ou = 0l, da = 0l, xiao = 0l, dan = 0l, shuang = 0l, dadan = 0l, dashuang = 0l, xiaodan = 0l, xiaoshuang = 0l;
		for (BetChartStrucKl8 betChart : betCharts) {
			if (betChart.isDa()) {
				da++;
			}
			if (betChart.isDan()) {
				dan++;
			}
			if (betChart.isHe()) {
				he++;
			}
			if (betChart.isJi()) {
				ji++;
			}
			if (betChart.isOu()) {
				ou++;
			}
			if (betChart.isShang()) {
				shang++;
			}
			if (betChart.isShuang()) {
				shuang++;
			}
			if (betChart.isXia()) {
				xia++;
			}
			if (betChart.isXiao()) {
				xiao++;
			}
			if (betChart.isZhong()) {
				zhong++;
			}
			if (betChart.isDa() && betChart.isDan()) {
				dadan++;
			}
			if (betChart.isDa() && betChart.isShuang()) {
				dashuang++;
			}
			if (betChart.isXiao() && betChart.isDan()) {
				xiaodan++;
			}
			if (betChart.isXiao() && betChart.isShuang()) {
				xiaoshuang++;
			}
		}

		Object[][][] chartResult = new Object[5][][];
		//上中下
		chartResult[0] = makeShangxia(shang, zhong, xia);
		//奇和偶
		chartResult[1] = makeJiou(ji, he, ou);
		//单双
		chartResult[2] = makeDanshuang(dan, shuang);
		//大小
		chartResult[3] = makeDaxiao(da, xiao);
		//和值
		chartResult[4] = makeHezhi(dadan, dashuang, xiaodan, xiaoshuang);

		return chartResult;
	}

	private Object[][] makeHezhi(Long dadan, Long dashuang, Long xiaodan, Long xiaoshuang) {
		Object[][] hezhi = new Object[4][4];
		hezhi[0][0] = "大单";
		hezhi[0][1] = "dadan";
		hezhi[0][2] = dadan;
		hezhi[0][3] = "red";
		hezhi[1][0] = "大双";
		hezhi[1][1] = "dashuang";
		hezhi[1][2] = dashuang;
		hezhi[1][3] = "Purple";
		hezhi[2][0] = "小单";
		hezhi[2][1] = "xiaodan";
		hezhi[2][2] = xiaodan;
		hezhi[2][3] = "yellow";
		hezhi[3][0] = "小双";
		hezhi[3][1] = "xiaoshuang";
		hezhi[3][2] = xiaoshuang;
		hezhi[3][3] = "blue";
		return hezhi;
	}

	private Object[][] makeDaxiao(Long da, Long xiao) {
		Object[][] daxiao = new Object[2][4];
		daxiao[0][0] = "大";
		daxiao[0][1] = "da";
		daxiao[0][2] = da;
		daxiao[0][3] = "red";
		daxiao[1][0] = "小";
		daxiao[1][1] = "xiao";
		daxiao[1][2] = xiao;
		daxiao[1][3] = "blue";
		return daxiao;
	}

	private Object[][] makeDanshuang(Long dan, Long shuang) {
		Object[][] danshuang = new Object[2][4];
		danshuang[0][0] = "单";
		danshuang[0][1] = "dan";
		danshuang[0][2] = dan;
		danshuang[0][3] = "red";
		danshuang[1][0] = "双";
		danshuang[1][1] = "shuang";
		danshuang[1][2] = shuang;
		danshuang[1][3] = "blue";
		return danshuang;
	}

	private Object[][] makeShangxia(Long shang, Long zhong, Long xia) {
		Object[][] shangxia = new Object[3][4];
		shangxia[0][0] = "上";
		shangxia[0][1] = "shang";
		shangxia[0][2] = shang;
		shangxia[0][3] = "red";
		shangxia[1][0] = "中";
		shangxia[1][1] = "zhong";
		shangxia[1][2] = zhong;
		shangxia[1][3] = "green";
		shangxia[2][0] = "下";
		shangxia[2][1] = "xia";
		shangxia[2][2] = xia;
		shangxia[2][3] = "blue";
		return shangxia;
	}

	private static Object[][] makeJiou(Long ji, Long he, Long ou) {
		Object[][] jiou = new Object[3][4];
		jiou[0][0] = "奇";
		jiou[0][1] = "ji";
		jiou[0][2] = ji;
		jiou[0][3] = "red";
		jiou[1][0] = "和";
		jiou[1][1] = "he";
		jiou[1][2] = he;
		jiou[1][3] = "green";
		jiou[2][0] = "偶";
		jiou[2][1] = "ou";
		jiou[2][2] = ou;
		jiou[2][3] = "blue";
		return jiou;
	}

	private static List<List<List<Integer>>> makeChartStrucList(List<String> l) {
		List<List<List<Integer>>> chartStrucList = new ArrayList<List<List<Integer>>>();
		for (String s : l) {
			String[] ss = s.split(",");
			List<List<Integer>> ll = new ArrayList<List<Integer>>();
			for (String a : ss) {
				String[] aa = a.split("\\|");
				List<Integer> intList = new ArrayList<Integer>();
				for (String aaa : aa) {
					intList.add(Integer.parseInt(aaa));
				}
				ll.add(intList);
			}
			chartStrucList.add(ll);
		}
		return chartStrucList;
	}

	/** 
	* @Title: makeQueryAssistActionRequest 
	* @Description: 生成查询Request 
	* @param @param betMethodType
	*/
	private QueryAssistActionRequest makeQueryAssistActionRequest(String betMethodType, Long lotteryid) {
		QueryAssistActionRequest queryAssistActionRequest = new QueryAssistActionRequest();
		queryAssistActionRequest.setLotteryid(lotteryid);

		String[] type = betMethodType.split("\\.");
		Integer gameGroupCode = SSCBetNameUtil.getGameGroupCode(type[0]);
		Integer gameSetCode = SSCBetNameUtil.getGameSetCode(type[1]);
		Integer betMethodCode = SSCBetNameUtil.getBetMethodCode(type[2]);

		queryAssistActionRequest.setGameGroupCode(gameGroupCode);
		queryAssistActionRequest.setGameSetCode(gameSetCode);
		queryAssistActionRequest.setBetMethodCode(betMethodCode);

		return queryAssistActionRequest;
	}

	/** 
	* @Title: makeQueryAssistActionRequest 
	* @Description: 生成查询Request  
	* @param @param betMethodType
	*/
	private QueryDescByBetMethodByUserIdRequest makeQueryAssistActionRequest(String betMethodType, Long lotteryid,
			Long userid) {
		QueryDescByBetMethodByUserIdRequest queryAssistActionRequest = new QueryDescByBetMethodByUserIdRequest();
		queryAssistActionRequest.setLotteryid(lotteryid);
		queryAssistActionRequest.setUserid(userid);

		String[] type = betMethodType.split("\\.");
		Integer gameGroupCode = SSCBetNameUtil.getGameGroupCode(type[0]);
		Integer gameSetCode = SSCBetNameUtil.getGameSetCode(type[1]);
		Integer betMethodCode = SSCBetNameUtil.getBetMethodCode(type[2]);

		queryAssistActionRequest.setGameGroupCode(gameGroupCode);
		queryAssistActionRequest.setGameSetCode(gameSetCode);
		queryAssistActionRequest.setBetMethodCode(betMethodCode);

		return queryAssistActionRequest;
	}

	/**
	 * 
	* @Title: historyballs 
	* @Description: 查询号码走势
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/historyballs")
	public @ResponseBody
	HistoryBallsResultDTO historyballs(@RequestParam("type") String type, @RequestParam("extent") String extent,
			@RequestParam("line") Integer line, @RequestParam("lenth") Integer lenth) throws Exception {
		logger.info("queryNumberCharts start");

		HistoryBallsResultDTO result = new HistoryBallsResultDTO();
		HistoryBallsDTO historyBallsDTO = new HistoryBallsDTO();
		String historyBallsHtml = "";

		try {
			historyBallsHtml = getHtmlContent(type, 99101L);

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
	 * 
	* @Title: getBetAward 
	* @Description: 查询玩法描述和默认冷热球及用户投注方式奖金
	* @param request
	* @return
	* @throws Exception
	 */
	@Deprecated
	@RequestMapping(value = "/getBetAward")
	public @ResponseBody
	GetAwardResultDTO getBetAward(@RequestParam("lotteryid") Long lotteryid, @RequestParam("userId") Long userId,
			@RequestParam("type") String type, @RequestParam("extent") String extent,
			@RequestParam("line") Integer line, @RequestParam("lenth") Integer lenth) throws Exception {

		logger.info("getBetAward start");
		//		userId=RequestContext.getCurrUser().getId();

		Response<QueryDescByBetMethodByUserIdResponse> response = new Response<QueryDescByBetMethodByUserIdResponse>();
		QueryDescByBetMethodByUserIdRequest request = makeQueryAssistActionRequest(type, lotteryid, userId);

		GetAwardResultDTO result = new GetAwardResultDTO();
		GetAwardDTO getAwardDTO = new GetAwardDTO();
		GameTipsByBet gameTips = new GameTipsByBet();
		List<List<NumberFrequencyCell>> frequency = new ArrayList<List<NumberFrequencyCell>>();

		try {
			logger.info("getBetAward 1");
			response = httpClient.invokeHttp(serverPath + queryBetMethodDescUrl, request,
					QueryDescByBetMethodByUserIdResponse.class);
			frequency = getDefaultFrequency(lotteryid, type, extent);

			logger.info("queryNumberCharts end");
		} catch (Exception e) {
			logger.error("queryNumberCharts is error.", e);
		//	throw e;
		}

		if (null != response.getBody().getResult()) {
			Double bonus = 0.0;
			if (null != response.getBody().getResult().getActualBonus()) {
				bonus = Double.valueOf(response.getBody().getResult().getActualBonus());
				bonus = bonus / 10000;
			}
			gameTips.setExample(response.getBody().getResult().getGameExamplesDes()
					.replaceAll("bonus", String.valueOf(bonus)));
			gameTips.setTips(response.getBody().getResult().getGamePromptDes()
					.replaceAll("bonus", String.valueOf(bonus)));
			gameTips.setActualBonus(bonus);
		} else {
			gameTips.setExample(type + "选号实例");
			gameTips.setTips(type + "玩法提示");
			gameTips.setActualBonus(0L);
		}

		getAwardDTO.setGameTips(gameTips);
		getAwardDTO.setFrequency(frequency);

		result.setData(getAwardDTO);
		result.setIsSuccess(1);
		result.setMsg("success");
		result.setType("userError");
		logger.info("getBetAward end");
		return result;
	}

	/**
	 * 
	* @Title: frequency 
	* @Description: 查询冷热遗漏号码
	* @param request
	* @return
	* @throws Exception
	 */
	@Deprecated
	@RequestMapping(value = "/frequency")
	public @ResponseBody
	List<List<NumberFrequencyCell>> frequency(@RequestParam("lotteryid") Long lotteryid,
			@RequestParam("gameMode") String gameMode, @RequestParam("extent") String extent,
			@RequestParam("frequencyType") String frequencyType, @RequestParam("line") Integer line,
			@RequestParam("lenth") Integer lenth) throws Exception {

		logger.info("query frequency start");
		Response<GameLrylQueryResponse> response = new Response<GameLrylQueryResponse>();

		if ("lost".equals(frequencyType)) {

			// 遗漏
			GameMissingValueQueryRequest request = makeGameMissingValueQueryRequest(gameMode, lotteryid, extent);
			try {
				response = httpClient.invokeHttp(serverPath + queryMissingValueUrl, request,
						GameLrylQueryResponse.class);
				logger.info("query lost Numbers end");
			} catch (Exception e) {
				logger.error("query lost Numbers error.", e);
				throw e;
			}

		} else if ("fre".equals(frequencyType)) {
			// 冷热
			GameColdAndHotNumbersQueryRequest request = makeGameColdAndHotNumbersQueryRequest(gameMode, lotteryid,
					extent);
			try {
				response = httpClient.invokeHttp(serverPath + queryColdAndHotNumbersUrl, request,
						GameLrylQueryResponse.class);
				logger.info("query lost Numbers end");
			} catch (Exception e) {
				logger.error("query lost Numbers error.", e);
				throw e;
			}
		}

		List<List<NumberFrequencyCell>> result = new ArrayList<List<NumberFrequencyCell>>();
		List<GameLryl> list = new ArrayList<GameLryl>();

		if (response != null) {
			list = response.getBody().getResult().getGameLrylNumbers();
			List<NumberFrequencyCell> l = new ArrayList<NumberFrequencyCell>();
			NumberFrequencyCell cell = new NumberFrequencyCell();

			if (gameMode.endsWith("hezhi") || gameMode.endsWith("kuadu")) {
				for (GameLryl gl : list) {
					cell = new NumberFrequencyCell();
					cell.setCurrentNum(gl.getLottNumber());
					cell.setPinlv(gl.getRetValue());
					l.add(cell);
				}
				result.add(l);

			} else if (extent.endsWith("currentFre")) {
				for (int i = 0; i < list.size(); i++) {
					GameLryl gl = list.get(i);
					if (i % 10 == 0) {
						l = new ArrayList<NumberFrequencyCell>();
						result.add(l);
					}
					cell = new NumberFrequencyCell();
					cell.setCurrentNum(i % 10);
					cell.setPinlv(gl.getRetValue());
					l.add(cell);
				}

			} else {

				for (int i = 5; i > 0; i--) {
					l = new ArrayList<NumberFrequencyCell>();

					for (int j = 0; j < list.size(); j++) {
						GameLryl gl = list.get(j);
						if (gl.getBitNumber() == i) {
							cell = new NumberFrequencyCell();
							cell.setCurrentNum(gl.getLottNumber());
							cell.setPinlv(gl.getRetValue());
							l.add(cell);
						}
					}
					if (l.size() > 0) {
						result.add(l);
					}
				}

				//				for (int i = list.size() - 1; i >= 0; i--) {
				//					GameLryl gl = list.get(i);
				//					if (i % 10 == 9) {
				//						l = new ArrayList<NumberFrequencyCell>();
				//						result.add(l);
				//					}
				//					cell = new NumberFrequencyCell();
				//					cell.setCurrentNum(i % 10);
				//					cell.setPinlv(gl.getRetValue());
				//					l.add(cell);
				//				}
			}
		}

		return result;
	}

	/** 
	* @throws Exception 
	 * @Title: getDefaultFrequency 
	* @Description: 获取默认的冷热遗漏号(CQSSC五星直选当前遗漏)
	*/
	private List<List<NumberFrequencyCell>> getDefaultFrequency(Long lotteryid, String type, String extent)
			throws Exception {
		List<List<NumberFrequencyCell>> frequency = new ArrayList<List<NumberFrequencyCell>>();

		Response<GameLrylQueryResponse> response = new Response<GameLrylQueryResponse>();
		GameMissingValueQueryRequest request = makeGameMissingValueQueryRequest(type, lotteryid, extent);
		try {
			response = httpClient.invokeHttp(serverPath + queryMissingValueUrl, request, GameLrylQueryResponse.class);
			logger.info("query lost Numbers end");
		} catch (Exception e) {
			logger.error("query lost Numbers error.", e);
			throw e;
		}

		List<GameLryl> list = new ArrayList<GameLryl>();

		if (response != null) {
			list = response.getBody().getResult().getGameLrylNumbers();

			List<NumberFrequencyCell> l = new ArrayList<NumberFrequencyCell>();
			NumberFrequencyCell cell = new NumberFrequencyCell();
			//			for (int i = list.size() - 1; i >= 0; i--) {
			//				GameLryl gl = list.get(i);
			//				if (i % 10 == 9) {
			//					l = new ArrayList<NumberFrequencyCell>();
			//					frequency.add(l);
			//				}
			//				cell = new NumberFrequencyCell();
			//				cell.setCurrentNum(i % 10);
			//				cell.setPinlv(gl.getRetValue());
			//				l.add(cell);
			//			}

			for (int i = 0; i < list.size(); i++) {
				GameLryl gl = list.get(i);
				if (i % 10 == 0) {
					l = new ArrayList<NumberFrequencyCell>();
					frequency.add(l);
				}
				cell = new NumberFrequencyCell();
				cell.setCurrentNum(i % 10);
				cell.setPinlv(gl.getRetValue());
				l.add(cell);
			}

		}

		return frequency;
	}

	/** 
	* @Title: makeGameColdAndHotNumbersQueryRequest 
	* @Description: 生成查询Request  
	*/
	private GameColdAndHotNumbersQueryRequest makeGameColdAndHotNumbersQueryRequest(String gameMode, Long lotteryid,
			String extent) {

		GameColdAndHotNumbersQueryRequest request = new GameColdAndHotNumbersQueryRequest();

		String[] type = gameMode.split("\\.");
		Integer gameGroupCode = SSCBetNameUtil.getGameGroupCode(type[0]);
		Integer gameSetCode = SSCBetNameUtil.getGameSetCode(type[1]);
		Integer betMethodCode = SSCBetNameUtil.getBetMethodCode(type[2]);

		request.setLotteryId(lotteryid);
		request.setGameGroupCode(gameGroupCode);
		request.setGameSetCode(gameSetCode);
		request.setBetMethodCode(betMethodCode);
		request.setCountIssue(Integer.parseInt(extent));

		return request;
	}

	/** 
	* @Title: makeGameMissingValueQueryRequest 
	* @Description: 生成查询Request 
	* @param gameMode
	* @param lotteryid
	* @param extent
	* @return Request<GameMissingValueQueryRequest>    返回类型 
	* @throws 
	*/
	private GameMissingValueQueryRequest makeGameMissingValueQueryRequest(String gameMode, Long lotteryid, String extent) {

		GameMissingValueQueryRequest request = new GameMissingValueQueryRequest();

		String[] type = gameMode.split("\\.");
		Integer gameGroupCode = SSCBetNameUtil.getGameGroupCode(type[0]);
		Integer gameSetCode = SSCBetNameUtil.getGameSetCode(type[1]);
		Integer betMethodCode = SSCBetNameUtil.getBetMethodCode(type[2]);

		request.setLotteryId(lotteryid);
		request.setGameGroupCode(gameGroupCode);
		request.setGameSetCode(gameSetCode);
		request.setBetMethodCode(betMethodCode);
		if ("maxFre".equals(extent)) {
			// 最大遗漏
			request.setType(2);
		} else if ("currentFre".equals(extent)) {
			// 当前遗漏
			request.setType(1);
		}

		return request;
	}

	/**
	 * @throws IOException 
	 * @throws MalformedURLException  
	* @Title: getHtmlContent 
	* @Description: 生成页面HTML代码 
	* @param betMethodType
	* @param lotteryid    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private String getHtmlContent(String betMethodType, Long lotteryid) throws IOException {
		URL url = new URL(webServerPath + createNumberChartsDomUrl + "?betMethodType=" + betMethodType + "&lotteryid="
				+ lotteryid);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestProperty("contentType", "utf-8");
		InputStream inStream = conn.getInputStream();
		String data = readInputStream(inStream);
		String html = new String(data);

		return html;

		//		HttpClient httpclient = new DefaultHttpClient();
		//		try {
		//			HttpGet httpget = new HttpGet(url.toString());
		//
		//			System.out.println("executing request " + httpget.getURI());
		//
		//			// Create a response handler
		//			ResponseHandler<String> responseHandler = new BasicResponseHandler();
		//			String responseBody = httpclient.execute(httpget, responseHandler);
		//			System.out.println("----------------------------------------");
		//			System.out.println(responseBody);
		//			System.out.println("----------------------------------------");
		//
		//		} finally {
		//			// When HttpClient instance is no longer needed,
		//			// shut down the connection manager to ensure
		//			// immediate deallocation of all system resources
		//			httpclient.getConnectionManager().shutdown();
		//		}
		//
		//		return "";

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

	/**
	 *
	 * @Title: createNumberChartsDomSSQ
	 * @Description: 生成号码走势图页面DOM
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createNumberChartsDomSSQ")
	public String createNumberChartsDomSSQ(@RequestParam("betMethodType") String betMethodType,
			@RequestParam("lotteryid") Long lotteryid, Model model) throws Exception {
		//生成Request
		QueryAssistActionRequest queryAssistActionRequest = new QueryAssistActionRequest();
		queryAssistActionRequest.setLotteryid(lotteryid);

		String[] types = betMethodType.split("\\.");
		Integer gameGroupCode = GameAwardNameUtil.getCode(lotteryid, types[0], types[1], types[2], 0);
		Integer gameSetCode = GameAwardNameUtil.getCode(lotteryid, types[0], types[1], types[2], 1);
		Integer betMethodCode = GameAwardNameUtil.getCode(lotteryid, types[0], types[1], types[2], 2);

		queryAssistActionRequest.setGameGroupCode(gameGroupCode);
		queryAssistActionRequest.setGameSetCode(gameSetCode);
		queryAssistActionRequest.setBetMethodCode(betMethodCode);

		//连接服务查询
		Response<QueryAssistActionResponse> response = betHttpClient.getQueryAssistAction(queryAssistActionRequest);
		
		List<BaseChartStruc> baseChartStrucs = response.getBody().getResult().getList();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (BaseChartStruc baseChartStruc : baseChartStrucs) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("balls", baseChartStruc.getNumberRecord().split(","));
			map.put("webIssueCode", baseChartStruc.getWebIssueCode());
			result.add(map);
		}
		model.addAttribute("result", result);
		return "/bet/ssq/chart/ssq";
	}
}
