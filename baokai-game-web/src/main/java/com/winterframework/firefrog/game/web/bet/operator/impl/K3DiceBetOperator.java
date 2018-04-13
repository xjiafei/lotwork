package com.winterframework.firefrog.game.web.bet.operator.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.winterframework.firefrog.game.web.bet.entity.LastNumber;
import com.winterframework.firefrog.game.web.bet.entity.LotteryConfig;
import com.winterframework.firefrog.game.web.bet.entity.LotteryGameGroup;
import com.winterframework.firefrog.game.web.dto.BetDetailStrucDTO;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryByBetRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryResponse;
import com.winterframework.firefrog.game.web.dto.BetMethodMultipleStruc;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameLimit;
import com.winterframework.firefrog.game.web.dto.GameOrderDiceRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameSeriesRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesResponse;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardListStruc;
import com.winterframework.firefrog.game.web.dto.LotteryListStruc;
import com.winterframework.firefrog.game.web.dto.OrdersStrucDTO;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdRequest;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdResponse;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigResponse;
import com.winterframework.firefrog.game.web.dto.UserInfoRequest;
import com.winterframework.firefrog.game.web.dto.UserInfoResponse;
import com.winterframework.firefrog.game.web.dto.VedioStruc;
import com.winterframework.firefrog.game.web.dto.WinOrderResponse;
import com.winterframework.firefrog.game.web.dto.WinUserListResponse;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.session.interceptor.vo.UserStrucResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.modules.web.util.RequestContext;

/**
 * @Title 时时彩操作类
 * 
 * @author bob
 *
 */
public class K3DiceBetOperator extends AbstractBetOperator {
	private Logger logger = LoggerFactory.getLogger(SSCBetOperator.class);

	@PropertyConfig("chip.chips")
	private String chipsStr;

	@PropertyConfig("chip.chips.chipsSelected")
	private String chipsSelectedStr;
	@PropertyConfig("chip.chips.vip")
	private String chipsVipStr;

	@PropertyConfig("chip.chips.vip.chipsSelected")
	private String chipsVipSelectedStr;

	@PropertyConfig("ballLists")
	private String ballListsStr;

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

	private String getGameTips(Integer betMethodCode, Integer gameSetCode, Integer gameGroupCode, String title)
			throws Exception {
		Response<QueryDescByBetMethodByUserIdResponse> response = new Response<QueryDescByBetMethodByUserIdResponse>();
		QueryDescByBetMethodByUserIdRequest request = new QueryDescByBetMethodByUserIdRequest();
		request.setBetMethodCode(betMethodCode);
		request.setGameSetCode(gameSetCode);
		request.setGameGroupCode(gameGroupCode);
		request.setUserid(RequestContext.getCurrUser().getId());
		request.setLotteryid(this.getLotteryId());
		response = betHttpClient.QueryDescByBetMethodByUserId(request);
		if (null != response.getBody().getResult()) {//bug 2923 需求小数点后是0的去掉0
			Double bonus = 0.0;
			if (null != response.getBody().getResult().getActualBonus()) {
				bonus = Double.valueOf(response.getBody().getResult().getActualBonus());
				bonus = bonus / 10000;
			}
			try {
				if (Math.round(bonus) == bonus) {

					return response.getBody().getResult().getGamePromptDes()
							.replaceAll("bonus", String.valueOf(Math.round(bonus)));
				} else {
					return response.getBody().getResult().getGamePromptDes().replaceAll("bonus", String.valueOf(bonus));
				}
			} catch (Exception e) {
				logger.info("queryNumberCharts end" + e);
			}

		}
		return title;

	}

	@Override
	public String getLotteryConfig(Model model, String select2000) throws Exception {
		logger.info("get lottery config begin...");
		String result;
		try {
			Integer userLvl = -1;
			try {
				UserStrucResponse usr = (UserStrucResponse) RequestContext.getCurrUser();
				userLvl = usr.getUserLvl();
			} catch (Exception e) {
				logger.info("get userLvl error...", e);

			}
			Long userId = RequestContext.getCurrUser().getId();
			String userName = RequestContext.getCurrUser().getUserName();
			LotteryConfig config = new LotteryConfig();
			config = convertJsonToObject(ballListsStr, LotteryConfig.class);
			BetMethodValidListQueryRequest betMethodValidListQueryRequest = new BetMethodValidListQueryRequest();
			betMethodValidListQueryRequest.setLotteryid(lotteryId);

			UserInfoRequest userInfoReq=new UserInfoRequest();
			userInfoReq.setUserId(userId);
			Response<UserInfoResponse> userInfoRes = betHttpClient.getUserInfo(userInfoReq);
			// 从接口获得数据
			logger.info("get lottery config  betMethodValidListQuery begin...");
			Response<BetMethodValidListQueryResponse> betMethodValidListQueryResponse = betHttpClient
					.getValidBetMethodList(betMethodValidListQueryRequest);
			String[] gameTypeCodes = betMethodValidListQueryResponse.getBody().getResult().getValidMethods();

			//		List<String> temps=new ArrayList<String>();
			//		
			//
			//
			//			for (String gameTypeCode : gameTypeCodes) {
			//
			//				String[] codes = StringUtils.split(gameTypeCode, ",");
			//				//解析得玩法群、玩法组、玩法code
			//				Integer gameGroupCode = Integer.valueOf(codes[0]);
			//				Integer gameSetCode = Integer.valueOf(codes[1]);
			//				Integer betMethodCode = Integer.valueOf(codes[2]);
			//				String bytetypeCode = gameGroupCode + "_" + gameSetCode + "_" + betMethodCode;
			//				String name = bnConvertor.getBetMethodFullNameByValue(gameGroupCode, gameSetCode, betMethodCode);
			//				String title = bnConvertor.getMMCBetMethodFullTitleByValue(gameGroupCode, gameSetCode, betMethodCode);
			//				String names[] = name.split("\\.");
			//				String titles[] = title.split("\\_");
			//
			//				String temp="update  game_bettype_status set group_Code_Name='" + names[0] + "',set_code_name='"
			//						+ names[1] + "',method_code_name='" + names[2] + "',group_code_title='" + titles[0]
			//						+ "',set_code_title='" + titles[1] + "',method_code_title='" + titles[2] + "' where lotteryid="
			//						+ lotteryId + " and bet_type_code='" + bytetypeCode + "';";
			//				temps.add(temp);
			//			}
			//			
			//			String temp2="-- lotteery id="+lotteryId+"   size="+gameTypeCodes.length;
			//			temps.add(temp2);
			//		
			//			 try {
			//		            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			//		            FileWriter writer = new FileWriter("D:/file.sql",true);
			//		            for(String s:temps){
			//		            writer.write(s);
			//		            writer.write('\n');
			//		            }
			//		            writer.close();
			//		        } catch (IOException e) {
			//		            e.printStackTrace();
			//		        }

			logger.info("get lottery config  betMethodValidListQuery end...");
			config.setUserLvl(userLvl);
			config.setUserId(userId);
			config.setUserName(userName);
			config.setAwardRetStatus(userInfoRes.getBody().getResult().getAwardRetStatus());
			
			config.setChips(convertToInteger(isVip(lotteryId)?chipsVipStr:chipsStr));
			config.setChipsSelected(convertToInteger(isVip(lotteryId)?chipsVipSelectedStr:chipsSelectedStr));

			List<LotteryGameGroup> gameMethods = new ArrayList<LotteryGameGroup>();

			for (String gameTypeCode : gameTypeCodes) {

				String[] codes = StringUtils.split(gameTypeCode, ",");
				LotteryGameGroup lotteryGameGroup = new LotteryGameGroup();

				//解析得玩法群、玩法组、玩法code
				Integer gameGroupCode = Integer.valueOf(codes[0]);
				Integer gameSetCode = Integer.valueOf(codes[1]);
				Integer betMethodCode = Integer.valueOf(codes[2]);
				LotteryGameGroup gameGroup = new LotteryGameGroup();
				gameGroup.setCode(gameGroupCode);
				gameGroup.setName(GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 0));
				gameGroup.setTitle(GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 0));
				/*LotteryGameSet gameSet = new LotteryGameSet();
				gameSet.setCode(gameSetCode);
				gameSet.setName(GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 1));
				gameSet.setTitle(GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 1));
				gameSet.setParent(gameGroup.getName());
				LotteryBetMethod betMethod = new LotteryBetMethod();
				betMethod.setCode(betMethodCode);
				betMethod.setName(GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 2));
				betMethod.setTitle(GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 2));
				betMethod.setParent(gameSet.getName());
				betMethod.setMode(gameGroup.getName());*/
				lotteryGameGroup.setGameType(gameGroup.getName());
				lotteryGameGroup.setGameTypeCn(gameGroup.getTitle());
				lotteryGameGroup.setGameTips(this.getGameTips(betMethodCode, gameSetCode, gameGroupCode,
						gameGroup.getTitle()));
				gameMethods.add(lotteryGameGroup);
			}

			config.setGameMethods(gameMethods);
			List<LotteryGameUserAwardListStruc> userAwardListStrucList=getUserArwardGroupsByLotteryIdAndUserId();
			config.setAwardGroups(userAwardListStrucList);
			
			if(null!=userAwardListStrucList){
				for(LotteryGameUserAwardListStruc userAwardListStruc:userAwardListStrucList){
					if(1==userAwardListStruc.getBetType().intValue()){
						config.setAwardGroupRetStatus(userAwardListStruc.getAwardGroupRetStatus());
					}
				}
			} 
			config.setGameType(lotteryCode);
			config.setLotteryId(lotteryId);

			/*boolean flag = false;*/

			/*for (String code : gameTypeCodes) {
				if (defaultMethod.equals(code)) {
					flag = true;
					List<LotteryGameGroup> temp = bnConvertor.getGameTypes(new String[] { code });
					config.setDefaultMethod(temp.get(0).getName() + "." + temp.get(0).getChilds().get(0).getName()
							+ "." + temp.get(0).getChilds().get(0).getChilds().get(0).getName());
				}
			}
			//当指定默认玩法被停售时默认玩法动态加载
			if (flag == false) {
				if (!config.getGameMethods().isEmpty()) {
					config.setDefaultMethod(config.getGameMethods().get(0).getName() + "."
							+ config.getGameMethods().get(0).getChilds().get(0).getName() + "."
							+ config.getGameMethods().get(0).getChilds().get(0).getChilds().get(0).getName());
				}
			}*/

			QuerySeriesConfigRequest request = new QuerySeriesConfigRequest();
			request.setLotteryid(lotteryId);
			request.setUserId(userId);

			Response<QuerySeriesConfigResponse> response = betHttpClient.getQuerySeriesConfig(request);
			//视频源处理
			List<String> slist = new ArrayList<String>();
			List<VedioStruc> vss = response.getBody().getResult().getVedioStrucs();
			if (vss != null && !vss.isEmpty()) {
				for (VedioStruc vs : vss) {
					slist.add(vs.getUrl());
				}

			}
			config.setSourceList(slist);
			config.setBackOutRadio(response.getBody().getResult().getBackoutRatio());
			config.setBackOutStartFee(response.getBody().getResult().getBackoutStartFee());
			config.setUserNickName(response.getBody().getResult().getUserNickName());
			config.setHeadImg(response.getBody().getResult().getHeadImg());
			config.setGameTypeCn(lotteryName);
			String temp = "/gameBet/" + lotteryCode + "/";
			config.setDynamicConfigUrl(temp + "dynamicConfig");//动态配置接口地址
			config.setUploadPath(temp + "betFile");
			config.setGetHandingChargeUrl(temp + "handlingCharge?amount=");
			config.setGetUserOrdersUrl(temp + "getUserOrders");
			config.setGetUserPlansUrl(temp + "getUserPlans");
			config.setQueryUserBalUrl("/gameBet/" + "queryUserBal");
			config.setSumbitUrl(temp + "submit");
			config.setTrendChartUrl(temp + "trendChart?type=");
			config.setGetBetAwardUrl(temp + "getBetAward");
			config.setGetHotColdUrl(temp + "frequency");
			config.setGetLotteryLogoPath(getProperty("logoPath") + "logo-" + lotteryCode + getProperty("LogoSuffix"));
			config.setQueryGameUserAwardGroupByLotteryIdUrl(temp + "queryGameUserAwardGroupByLotteryId");
			config.setSaveProxyBetGameAwardGroupUrl(temp + "saveBetAward");
			config.setIndexInit(temp + "indexInit");
			config.setLastNumberUrl(temp + "lastNumber");
			config.setHelpLink(getHelpLink());
			config.setWinningListUrl(temp + "getWinningList");
			config.setPlayerBetUrl(temp + "getPlayerBetList");
			config.setUploadUserInfo("/gameBet/"+"saveUserHeadImg");

			GameSeriesRequest requestData = new GameSeriesRequest();
			requestData.setLotteryId(lotteryId);
			config.setIsLotteryStopSale(false);//彩种是否停售
			Response<GameSeriesResponse> responseSeries = betHttpClient.querySeries(requestData);
			if (!responseSeries.getBody().getResult().getList().isEmpty()) {
				LotteryListStruc lottery = responseSeries.getBody().getResult().getList().get(0);
				if (lottery.getStatus() == 0) {
					config.setIsLotteryStopSale(true);
				}
			}

			JsonMapper jm = new JsonMapper();
			String str = jm.toJson(config);
			result = "";

			result = getMessage("configK3diceJs", new String[] { str, lotterySeries });

			logger.info("get lottery config begin end...");
			return result;
		} catch (Exception e) {
			logger.info("get lottery config begin error...", e);
			result = getMessage("errorConfigJs");//当数据出问题时，保证js页面不会崩溃
			return result;
		}
		//		model.addAttribute("result", result);

	}
	private boolean isVip(Long lotteryId){
		return lotteryId.longValue()==99603L;
	}
	private Integer[] convertToInteger(String arg) {
		String[] args = arg.split(",");
		Integer[] in = new Integer[args.length];
		int i = 0;
		for (String str : args) {
			in[i] = Integer.valueOf(str);
			i++;
		}
		return in;
	}

	/**
	 * 查询投注限制
	 * 
	 * @return
	 * @throws Exception
	 */
	protected List<Map<String, GameLimit>> getGameLimits() throws Exception {

		BetLimitQueryByBetRequest betLimitQueryRequest = new BetLimitQueryByBetRequest();
		betLimitQueryRequest.setLotteryid(lotteryId);

		Response<BetLimitQueryResponse> betLimitQueryResponse = betHttpClient.getBetLimit(betLimitQueryRequest);

		BetMethodValidListQueryRequest betMethodValidListQueryRequest = new BetMethodValidListQueryRequest();
		betMethodValidListQueryRequest.setLotteryid(lotteryId);
		Response<BetMethodValidListQueryResponse> betMethodValidListQueryResponse = betHttpClient
				.getValidBetMethodList(betMethodValidListQueryRequest);

		List<Map<String, GameLimit>> gameLimits = new ArrayList<Map<String, GameLimit>>();
		Map<String, GameLimit> map = new HashMap<String, GameLimit>();

		//限制逻辑修改  由于前台js需要限制数据，改成以bet_type_status表为主 没有的显示为无限制

		for (String gameTypeCode : betMethodValidListQueryResponse.getBody().getResult().getValidMethods()) {

			String[] codes = StringUtils.split(gameTypeCode, ",");

			//解析得玩法群、玩法组、玩法code
			Integer gameGroupCode = Integer.valueOf(codes[0]);
			Integer gameSetCode = Integer.valueOf(codes[1]);
			Integer betMethodCode = Integer.valueOf(codes[2]);

			GameLimit gl = new GameLimit();
			if (!betLimitQueryResponse.getBody().getResult().getBetLimitList().isEmpty()) {
				gl.setMaxmultiple(-1L);
				for (BetMethodMultipleStruc bs : betLimitQueryResponse.getBody().getResult().getBetLimitList()) {
					if (gameGroupCode.intValue() == bs.getGameGroupCode()
							&& gameSetCode.intValue() == bs.getGameSetCode()
							&& betMethodCode.intValue() == bs.getBetMethodCode()) {
						gl.setMaxmultiple(bs.getMultiple().longValue());
						gl.setMaxmultiples(bs.getMultiples());
						gl.setK3hezhiMultiple(bs.getK3hezhiMultiple());
					}
				}
			} else {
				gl.setMaxmultiple(-1L);//null值为无限制
			}
			gl.setUsergroupmoney(null);// 此数据已不在这里获取了，现在随意给个null值  此行不能注销，前台js需要
			map.put(GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 0), gl);

		}

		//		for (BetMethodMultipleStruc bs : betLimitQueryResponse.getBody().getResult().getBetLimitList()) {
		//			GameLimit gl = new GameLimit();
		//			if (bs.getMultiple().equals(null)) {
		//				gl.setMaxmultiple(-1L);//null值为无限制
		//			} else {
		//				gl.setMaxmultiple(Long.valueOf(bs.getMultiple()));
		//			}
		//			gl.setUsergroupmoney(null);// 此数据已不在这里获取了，现在随意给个null值  此行不能注销，前台js需要
		//			map.put(bnConvertor.getBetMethodFullNameByValue(bs.getGameGroupCode(), bs.getGameSetCode(),
		//					bs.getBetMethodCode()), gl);
		//		}

		gameLimits.add(map);

		return gameLimits;
	}

	protected GameOrderRequestDTO convertJsonToGameOrderRequestDTO(String data) throws Exception {
		GameOrderRequestDTO dto = new GameOrderRequestDTO();
		GameOrderDiceRequestDTO diceDto = new GameOrderDiceRequestDTO();
		diceDto = convertJsonToObject(data, GameOrderDiceRequestDTO.class);
		List<BetDetailStrucDTO> balls = diceDto.getBalls();
		for (BetDetailStrucDTO betDetailDto : balls) {
			String gameType = betDetailDto.getType();
			String[] gameTypes = gameType.split("\\.");
			betDetailDto.setMultiple(diceDto.getMultiple());
			betDetailDto.setType(gameTypes[0] + "." + gameTypes[0] + "." + gameTypes[0]);
			betDetailDto.setMoneyunit(0.1);
		}
		dto.setAmount(String.valueOf(diceDto.getAmount()));
		dto.setBalls(diceDto.getBalls());

		dto.setGameType(diceDto.getGameType());
		dto.setIsTrace(diceDto.getIsTrace());
		dto.setOrders(diceDto.getOrders());
		dto.setTraceStopValue(-1);
		dto.setTraceWinStop(0);
		return dto;
	}

	protected int getItemAmountValue(BetDetailStrucDTO bd) {
		return ((Double) (bd.getAmount() * getLongProperty("moneyMultiple") * 1.0)).intValue();
	}

	protected Long getTotalAmountValue(BetDetailStrucDTO bd) {
		return ((long) ((Double) (bd.getAmount() * getLongProperty("moneyMultiple") * 1.0)).intValue() * bd
				.getMultiple());
	}

	protected int getTotalBet(BetDetailStrucDTO bd) {
		return 1;
	}

	protected void setWinOrderList(LastNumber lm) throws Exception {
		List<OrdersStrucDTO> list = super.getUserOrders();
		List<WinOrderResponse> winlists = new ArrayList<WinOrderResponse>();
		if (list != null) {
			for (OrdersStrucDTO dto : list) {
				if (dto.getWebIssueCode().equals(lm.getLastnumber())) {
					WinOrderResponse win = new WinOrderResponse();
					win.setProjectId(dto.getOrderCode());
					if (dto.getStatus() == 2) {
						win.setWinMoney(dto.getTotwin());
					}
					winlists.add(win);
				}
			}
			lm.setWinlists(winlists);
		}
	}
	
	public WinUserListResponse getWinningList(String orderIds) throws Exception {
		WinUserListResponse resp = new WinUserListResponse();
		resp.setIsSuccess(1);
		resp.setMsg("success");
		if(orderIds !=null){
			Map  mapOrderIds =convertJsonToObject(orderIds, Map.class);
			//获取当前奖期信息
			GameIssueQueryResponse gameIssue = getCurrentGameIssue();
			Map<String,String> map = new HashMap<String,String>();
			map.put("issueCode", gameIssue.getIssueCode()+"");
			map.put("lotteryId", lotteryId+"");
			map.put("orderIds", String.valueOf(mapOrderIds.get("orderIds")));
			Response<LinkedHashMap> gameWinningListResponse =null;
			try{
				Long userId = RequestContext.getCurrUser().getId();
				String userName = RequestContext.getCurrUser().getUserName();
				gameWinningListResponse = betHttpClient.getWinningList(map,userId,userName);
			}catch(Exception e){
				resp.setIsSuccess(0);
				resp.setMsg("queryWinningList error");
				logger.error("queryWinningList error", e);
			}
			if(gameWinningListResponse!=null && gameWinningListResponse.getBody()!=null){
				resp.setData(gameWinningListResponse.getBody().getResult());
			}
		}
			
		return resp;	
	}
	
	public WinUserListResponse getPlayerBetList() throws Exception {
		WinUserListResponse resp = new WinUserListResponse();
		resp.setIsSuccess(1);
		resp.setMsg("success");
		//获取当前奖期信息
		GameIssueQueryResponse gameIssue = getCurrentGameIssue();
		Map<String,Long> map = new HashMap<String,Long>();
		map.put("issueCode", gameIssue.getIssueCode());
		map.put("lotteryId", lotteryId);
		Response<GameOrderQueryResponse> playerBetListResponse = null;
		try{
			Long userId = RequestContext.getCurrUser().getId();
			String userName = RequestContext.getCurrUser().getUserName();
			playerBetListResponse = betHttpClient.getPlayerBetList(map, userId, userName);
		}catch(Exception e){
			resp.setIsSuccess(0);
			resp.setMsg("getPlayerBetList error");
			logger.error("getPlayerBetList error", e);
		}
		if(playerBetListResponse!=null && playerBetListResponse.getBody()!=null){
			resp.setData(playerBetListResponse.getBody().getResult().getOrdersStruc());
		}
		return resp;
	}
}
