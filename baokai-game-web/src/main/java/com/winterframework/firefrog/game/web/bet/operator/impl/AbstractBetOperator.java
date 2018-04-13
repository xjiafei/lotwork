package com.winterframework.firefrog.game.web.bet.operator.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.common.util.MultMd5;
import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.validate.business.IValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.common.validate.business.IValidatorFactory;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameBonusJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameLhcOdds;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.dto.GameFavorites;
import com.winterframework.firefrog.game.entity.ChannelType;
import com.winterframework.firefrog.game.entity.GameLryl;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.enums.GameLHCBetType;
import com.winterframework.firefrog.game.exception.GameBetAmountErrorException;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.game.util.LhcRedisUtil;
import com.winterframework.firefrog.game.util.SuperPairUtil;
import com.winterframework.firefrog.game.web.bet.convertor.IBetNameConvertor;
import com.winterframework.firefrog.game.web.bet.convertor.impl.BetNameConvertor;
import com.winterframework.firefrog.game.web.bet.entity.LastNumber;
import com.winterframework.firefrog.game.web.bet.entity.LotteryBetMethod;
import com.winterframework.firefrog.game.web.bet.entity.LotteryConfig;
import com.winterframework.firefrog.game.web.bet.entity.LotteryGameGroup;
import com.winterframework.firefrog.game.web.bet.entity.LotteryGameSet;
import com.winterframework.firefrog.game.web.bet.operator.BetSupport;
import com.winterframework.firefrog.game.web.bet.operator.IBetOperation;
import com.winterframework.firefrog.game.web.bet.util.BetHttpJsonClient;
import com.winterframework.firefrog.game.web.bet.util.LockFacadeUtils;
import com.winterframework.firefrog.game.web.chart.entity.Chart;
import com.winterframework.firefrog.game.web.controller.GameBetQueryController;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BetDetailStruc;
import com.winterframework.firefrog.game.web.dto.BetDetailStrucDTO;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryByBetRequest;
import com.winterframework.firefrog.game.web.dto.BetLimitQueryResponse;
import com.winterframework.firefrog.game.web.dto.BetMethodMultipleStruc;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryRequest;
import com.winterframework.firefrog.game.web.dto.BetMethodValidListQueryResponse;
import com.winterframework.firefrog.game.web.dto.CancelOrderChargeRequest;
import com.winterframework.firefrog.game.web.dto.CancelOrderChargeResponse;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.DynamicConfig;
import com.winterframework.firefrog.game.web.dto.GameBetBalls;
import com.winterframework.firefrog.game.web.dto.GameBetBitDate;
import com.winterframework.firefrog.game.web.dto.GameBetJsonDateStruc;
import com.winterframework.firefrog.game.web.dto.GameBetJsonResultStruc;
import com.winterframework.firefrog.game.web.dto.GameBetTplData;
import com.winterframework.firefrog.game.web.dto.GameColdAndHotNumbersQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameConfigAndAwardData;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameLhcOddsResponse;
import com.winterframework.firefrog.game.web.dto.GameLhcTipsResponse;
import com.winterframework.firefrog.game.web.dto.GameLhcZodiacResponse;
import com.winterframework.firefrog.game.web.dto.GameLimit;
import com.winterframework.firefrog.game.web.dto.GameLrylQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameMissingValueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameNumbers;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderResponeOverMutipleDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderResponseDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanBetOriginDataStruc;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanQueryResponse;
import com.winterframework.firefrog.game.web.dto.GamePlanRequest;
import com.winterframework.firefrog.game.web.dto.GamePlanResponse;
import com.winterframework.firefrog.game.web.dto.GameSeriesRequest;
import com.winterframework.firefrog.game.web.dto.GameSeriesResponse;
import com.winterframework.firefrog.game.web.dto.GameTips;
import com.winterframework.firefrog.game.web.dto.GameTipsByBet;
import com.winterframework.firefrog.game.web.dto.GameUserBetAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.GameUserBetAwardGroupResponse;
import com.winterframework.firefrog.game.web.dto.GetAwardDTO;
import com.winterframework.firefrog.game.web.dto.GetAwardResultDTO;
import com.winterframework.firefrog.game.web.dto.HistoryBallsDTO;
import com.winterframework.firefrog.game.web.dto.HistoryBallsResultDTO;
import com.winterframework.firefrog.game.web.dto.IssueStruc;
import com.winterframework.firefrog.game.web.dto.LhcGameOdds;
import com.winterframework.firefrog.game.web.dto.LhcGameOddsDetail;
import com.winterframework.firefrog.game.web.dto.LhcGameTips;
import com.winterframework.firefrog.game.web.dto.LhcGameZodiac;
import com.winterframework.firefrog.game.web.dto.LockPointRequestDTO;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryRequest;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardGroupQueryResponse;
import com.winterframework.firefrog.game.web.dto.LotteryGameUserAwardListStruc;
import com.winterframework.firefrog.game.web.dto.LotteryListStruc;
import com.winterframework.firefrog.game.web.dto.MMCOrderDetail;
import com.winterframework.firefrog.game.web.dto.NumberFrequencyCell;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.OrdersStrucDTO;
import com.winterframework.firefrog.game.web.dto.PlansStrucForUI;
import com.winterframework.firefrog.game.web.dto.PointsRequestDTO;
import com.winterframework.firefrog.game.web.dto.PreviewIssueStruc;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionRequest;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionResponse;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdRequest;
import com.winterframework.firefrog.game.web.dto.QueryDescByBetMethodByUserIdResponse;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigRequest;
import com.winterframework.firefrog.game.web.dto.QuerySeriesConfigResponse;
import com.winterframework.firefrog.game.web.dto.SaveProxyBetGameAwardGroupRequest;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.game.web.dto.StraightOdds;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.TraceGameIssueListQueryResponse;
import com.winterframework.firefrog.game.web.dto.UserAwardListByBetStruc;
import com.winterframework.firefrog.game.web.dto.UserInfoRequest;
import com.winterframework.firefrog.game.web.dto.UserInfoResponse;
import com.winterframework.firefrog.game.web.dto.VedioStruc;
import com.winterframework.firefrog.game.web.dto.WinUserListResponse;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.game.web.util.IPUtil;
import com.winterframework.firefrog.game.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.game.web.validate.business.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.session.interceptor.vo.UserStrucResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResponseHeader;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.modules.web.util.RequestContext;

/**
 * @Title 全彩种投注操作类
 * 
 * @author bob
 *
 */
public abstract class AbstractBetOperator extends BetSupport implements IBetOperation {

	private Logger logger = LoggerFactory.getLogger(AbstractBetOperator.class);

	@Resource(name = "betHttpClient")
	protected BetHttpJsonClient betHttpClient;

	@Resource(name = "gameBetNumberValidatorFactory")
	private IValidatorFactory<GameSlip> validatorFactory;
	
	@Resource(name="lhcRedisUtil")
	private LhcRedisUtil lhcRedisUtil;

	//服务器状态码
	@Resource(name = "serverMessages")
	protected Map<Long, GameBetJsonResultStruc> serverMessages;
	
	@Resource(name = "groupCodeList2000")
	protected List<String> groupCodeList2000;
	
	//当前彩种代码，如cqssc
	protected String lotteryCode;

	//当前彩种ID，如99101
	protected Long lotteryId;

	//彩种名称
	protected String lotteryName;

	//默认玩法
	protected String defaultMethod;

	//彩种所属菜系对应的应为简称 比如SSC，N115 用于js调用
	protected String lotterySeries;

	//jsp页面文件路径，如/bet/ssc/bet
	protected String view;

	//投注玩法群、玩法组、玩法同JS定义之间的转换类
	protected IBetNameConvertor bnConvertor;

	//走势辅助图渲染器
	private Map<String, Map<String, Chart>> trendCharts;

	private List<String> videoSourceList;

	private String helpLink;//玩法说明链接

	@PropertyConfig("platVersion")
	private String platVersion;

	@Resource(name = "RedisClient")
	private RedisClient redis;

	@PropertyConfig("multiplemaxtimes")
	private String multiplemaxtimes;

	@PropertyConfig("multipleToCnyFactor")
	private String multipleToCnyFactor;

	private JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	
	public Long getLotteryId() {
		return lotteryId;
	}

	protected void setWinOrderList(LastNumber lm) throws Exception {

	}

	@Override
	public LastNumber lastNumber() throws Exception {
		LastNumber lm = new LastNumber();
		lm.setUsername(RequestContext.getCurrUser().getUserName());
		GameIssueQueryResponse response = getCurrentGameIssue();
		if (lotteryId == 99701) {
			StringBuilder sb = new StringBuilder(response.getLastWebIssueCode());
			sb.insert(2, "/");
			lm.setLastnumber(sb.toString());
		} else {
			lm.setLastnumber(response.getLastWebIssueCode());
		}
		lm.setLastballs(formatLastBalls(response));
		if (!StringUtils.isEmpty(lm.getLastballs()) && lotteryId == 99201) {
			String[] temp = lm.getLastballs().split(",");
			Integer[] numbers = new Integer[temp.length];
			for (int i = 0; i < temp.length; i++) {
				numbers[i] = Integer.valueOf(temp[i]);
			}

			lm.setBallInfo(DTOConvertor4Web.getBetKl8Tip(Arrays.asList(numbers)));

		}
		lm.setIsstop(0);
		//获取当前奖期信息
		GameIssueQueryResponse gameIssue = getCurrentGameIssue();
		lm.setNowstoptime(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleEndTime()),
				getProperty("jsViewDataFormat")));//当前期投注结束时间
		lm.setNowtime(DateUtils.format(new Date(), getProperty("jsViewDataFormat")));//当前服务器时间
		lm.setNumber(gameIssue.getWebIssueCode());//当前web期号
		lm.setIssueCode(gameIssue.getIssueCode());//当前期号
		lm.setResulttime(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleStartTime()),
				getProperty("jsViewDataFormat")));//当前期预计开始时间
		if((lotteryId == 99602 || lotteryId == 99603) && gameIssue.getLastIssueCode()!=null){
			Map<String,Long> request = new HashMap<String,Long>();
			request.put("lotteryId", lotteryId);
			request.put("issueCode", gameIssue.getLastIssueCode());
			String uname = RequestContext.getCurrUser().getUserName();
			lm.setPrizeBet(betHttpClient.getPrizeBetLis(request, RequestContext.getCurrUser().getId(), uname).getBody().getResult());
			lm.setResulttime(DateUtils.format(DateUtils.addSeconds(DataConverterUtil.convertLong2Date(gameIssue.getSaleStartTime()), 15),
					getProperty("jsViewDataFormat")));//当前期预计开始时间
			lm.setNowstoptime(DateUtils.format(DateUtils.addSeconds(DataConverterUtil.convertLong2Date(gameIssue.getSaleEndTime()), 15),
					getProperty("jsViewDataFormat")));//当前期投注结束时间
		}
		return lm;
	}

	@Override
	public String show(Model model) throws Exception {

		Long userId = RequestContext.getCurrUser().getId();
		int userLvl = -1;
		try {
			//此处本地开发环境运行会抛出一个异常，属于正常现象，已经try catch处理，原因是我们没有登录功能，服务器上此处无bug
			UserStrucResponse usr = (UserStrucResponse) RequestContext.getCurrUser();
			userLvl = usr.getUserLvl();
		} catch (Exception e) {
			logger.error("获取用户信息异常", e);
		}

		List<OrdersStrucDTO> osds = getUserOrders();

		List<String[]> numbers = new ArrayList<String[]>();

		if (userId != null) {
			model.addAttribute("orders", osds);
			model.addAttribute("plans", getUserPlans());
		}
		model.addAttribute("lotteryCode", lotteryCode);
		model.addAttribute("lotteryName", lotteryName);
		model.addAttribute("userLvl", userLvl);

	if (lotteryId == 99112||lotteryId==99306) {
			String value = MultMd5.dmd(new Date().getTime() + "");

				redis.set("mmcBet" + userId+lotteryId, value, 60* 60 *24);
			model.addAttribute("mmcBetKey", value);

			if (osds != null && !osds.isEmpty()) {
				for (OrdersStrucDTO osd : osds) {
					if (osd.getStatus() != 4) { //开奖不成功的开奖号码不显示
						if (!StringUtils.isEmpty(osd.getNumberRecord())) {
							String[] num = osd.getNumberRecord().split(",");
							//秒秒彩鑽石等級
							if(lotteryId == 99112){
								StringBuilder str = new StringBuilder();
								int lv = 0;
								for(int i = 1 ;i < num.length ; i++){
									if(num[0].equals(num[i])){
										str.append(num[i]+" diamondnum,");
										lv++;
									}else{
										str.append(num[i]+",");
									}
									str.append(num[i]+",");
								}
								if(lv == 0){
									str.append(num[0]+",");
								}else{
									str.append(num[0]+" diamondnum,");
								}
								str.append(num[0]+",");
								str.append(lv);
								num = str.toString().split(",");
							}
							numbers.add(num);
						}
					}
				}
			}
			model.addAttribute("nums", numbers);
		}
		if (lotteryId == 99601||lotteryId == 99602||lotteryId == 99603) {
			model.addAttribute("results", getGameIssueNumberRecord());
		}
		return view;
	}

	public List<OrdersStrucDTO> getGameIssueNumberRecord() throws Exception {
		Response<List<IssueStruc>> response = new Response<List<IssueStruc>>();
		List<IssueStruc> GameIssues = new ArrayList<IssueStruc>();
		List<OrdersStrucDTO> osds = new ArrayList<OrdersStrucDTO>();
		try {
			response = betHttpClient.getGameIssueNumberRecord(lotteryId);
			if (null != response.getBody().getResult()) {
				GameIssues = response.getBody().getResult();
				int i = 0;
				for (IssueStruc gameIssue : GameIssues) {
					OrdersStrucDTO dto = new OrdersStrucDTO();
					dto.setWebIssueCode(gameIssue.getWebIssueCode().split("-")[1]);
					dto.setNumberRecordList(DTOConvertor4Web.explode(gameIssue.getNumberRecord()));
					int hezhi = 0;
					for (String num : dto.getNumberRecordList()) {
						hezhi += Integer.parseInt(num);
					}
					List<Object> showList = new ArrayList<Object>();
					showList.add(hezhi);
					showList.add(hezhi < 11 ? "小" : "大");
					showList.add(hezhi % 2 == 0 ? "双" : "单");
					dto.setShowList(showList);
					osds.add(dto);
					i++;
					if(lotteryId == 99602 || lotteryId == 99603){
						if (i > 30) {
							break;
						}
					}else{
						if (i > 10) {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("getGameIssueNumberRecord is error.", e);
			throw e;
		}
		return osds;
	}

	@Override
	public Object queryGameUserAwardGroupByLotteryId() throws Exception {

		logger.info("queryGameUserAwardGroupByLotteryId start");
		GameUserBetAwardGroupRequest request = new GameUserBetAwardGroupRequest();
		Response<GameUserBetAwardGroupResponse> response = new Response<GameUserBetAwardGroupResponse>();

		Long userId = RequestContext.getCurrUser().getId();
		request.setLotteryid(lotteryId);
		request.setUserid(userId);

		List<UserAwardListByBetStruc> userAwardList = new ArrayList<UserAwardListByBetStruc>();
		try {
			response = betHttpClient.queryGameUserAwardGroupByLotteryId(request);
			if (null != response.getBody().getResult()) {
				userAwardList = response.getBody().getResult().getUserAwardListByBetStruc();
				for(UserAwardListByBetStruc strutc:userAwardList){
					//最高獎金組增加推薦字樣
					if(strutc.getIsMaxAward()!=null && strutc.getIsMaxAward()){
						String str = "<span style='color:red'> 荐 </sapn> ";
						strutc.setAwardName(strutc.getAwardName()+str);
					}
				}
			}
			logger.info("queryGameUserAwardGroupByLotteryId end");
		} catch (Exception e) {
			logger.error("queryGameUserAwardGroupByLotteryId is error.", e);
			throw e;
		}

		return userAwardList;
	}

	/** 
	* @Title: getUserArwardGroupsByLotteryIdAndUserId 
	* @Description: 获取当前用户在当前彩种的奖金组信息
	* @return
	* @throws Exception
	*/
	public List<LotteryGameUserAwardListStruc> getUserArwardGroupsByLotteryIdAndUserId() throws Exception {

		logger.info("getUserArwardGroupsByLotteryIdAndUserId start");
		LotteryGameUserAwardGroupQueryRequest request = new LotteryGameUserAwardGroupQueryRequest();
		Response<LotteryGameUserAwardGroupQueryResponse> response = new Response<LotteryGameUserAwardGroupQueryResponse>();

		Long userId = RequestContext.getCurrUser().getId();
		request.setLotteryId(lotteryId);
		request.setUserId(userId);

		List<LotteryGameUserAwardListStruc> userAwardList = new ArrayList<LotteryGameUserAwardListStruc>();
		try {
			response = betHttpClient.getUserArwardGroupsByLotteryIdAndUserId(request);
			if (null != response.getBody().getResult()) {
				userAwardList = response.getBody().getResult().getUserAwardListStruc();
			}
			logger.info("getUserArwardGroupsByLotteryIdAndUserId end");
		} catch (Exception e) {
			logger.error("getUserArwardGroupsByLotteryIdAndUserId is error.", e);
			throw e;
		}

		return userAwardList;
	}

	@Override
	public Object saveProxyBetGameAwardGroup(Long awardGroupId) throws Exception {
		AjaxResponse resp = new AjaxResponse();
		Long userId = RequestContext.getCurrUser().getId();
		SaveProxyBetGameAwardGroupRequest retJson = new SaveProxyBetGameAwardGroupRequest();
		retJson.setAwardGroupId(awardGroupId);
		retJson.setLotteryid(lotteryId);
		retJson.setUserid(userId);

		logger.info("modify ret start");
		try {
			Response response = betHttpClient.saveProxyBetGameAwardGroup(retJson);
			if (response.getHead().getStatus() == 2) {
				logger.error("aready config");
				resp.setStatus(2l);
				resp.setMessage("aready config");
			} else if (response.getHead().getStatus() == 3) {
				logger.error("modify ret error");
				resp.setStatus(3l);
				resp.setMessage("modify ret error");
			} else {
				resp.setStatus(1l);
				resp.setMessage("success");
			}
		} catch (Exception e) {
			logger.error("modify ret error", e);
			resp.setStatus(2l);
			resp.setMessage("modify ret error");
		}

		logger.info("modify ret end");

		return resp;
	};

	@Override
	public Object indexInit() throws Exception {
		String uname = RequestContext.getCurrUser().getUserName();
		//获取当前奖期信息
		GameIssueQueryRequest gameIssueQueryRequest = new GameIssueQueryRequest();
		gameIssueQueryRequest.setLotteryId(lotteryId);
		GameIssueQueryResponse gameIssue = getCurrentGameIssue();

		GameConfigAndAwardData gd = new GameConfigAndAwardData();

		//将开奖号码用逗号分隔
		char[] repeatChars = gameIssue.getNumberRecord().toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < repeatChars.length; i++) {
			sb.append(repeatChars[i]);
			if (i != repeatChars.length - 1) {
				sb.append(",");
			}
		}
		gd.setLastballs(sb.toString());//获取上期开奖号码
		gd.setLastnumber(gameIssue.getLastWebIssueCode());//获取上期奖期
		gd.setNowstoptime(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleEndTime()),
				DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));//当前期投注结束时间
		gd.setNowtime(DateUtils.format(new Date(), DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));//当前服务器时间
		gd.setNumber(gameIssue.getWebIssueCode());//当前web期号
		gd.setIssueCode(gameIssue.getIssueCode());//当前期号
		gd.setResulttime(DateUtils.format(DataConverterUtil.convertLong2Date(gameIssue.getSaleStartTime()),
				DateUtils.DATETIME_JSVIEW_FORMAT_PATTERN));//当前期预计开始时间
		gd.setUsername(uname);//当前用户名称
		return gd;
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

			logger.info("get lottery config  betMethodValidListQuery end...");
			config.setUserLvl(userLvl);
			config.setUserId(userId);
			config.setUserName(userName);
			config.setLhcStatus(userInfoRes.getBody().getResult().getLhcStatus());
			config.setAwardRetStatus(userInfoRes.getBody().getResult()
					.getAwardRetStatus());
			Set<LotteryGameGroup> gameGroups = new HashSet<LotteryGameGroup>();

			Set<LotteryGameSet> gameSets = new HashSet<LotteryGameSet>();

			Set<LotteryBetMethod> betMethods = new HashSet<LotteryBetMethod>();
			QuerySeriesConfigRequest request = new QuerySeriesConfigRequest();
			request.setLotteryid(lotteryId);
			request.setUserId(userId);

			Response<QuerySeriesConfigResponse> response = betHttpClient.getQuerySeriesConfig(request);
			config.setIsSupport2000(response.getBody().getResult().getIsSupport2000()==null?
					false:response.getBody().getResult().getIsSupport2000().intValue()==1?
							true:false);
			// 2000獎金組 提示 活動 重慶  天津 新疆 黑龍江
			String redisKey = "info2000" + userId;
			String redisValue = redis.get(redisKey);
			boolean firsttime = false;
			if (redisValue == null && (lotteryId == 99101L || lotteryId == 99104L || lotteryId == 99103L || lotteryId == 99105L )){
				redis.set(redisKey, "1");
				config.setIsfirstimeuse2000(true);
				firsttime = true;
			}else{
				config.setIsfirstimeuse2000(false);
			}
			
			// 鑽石活動 提示 活動 秒秒彩
			String redisKey_d = "infoDiamodn" + userId;
			String redisValue_d = redis.get(redisKey_d);
			if (redisValue_d == null && lotteryId == 99112L){
				redis.set(redisKey_d, "1");
				config.setIsfirstimeusediamond2000(true);
			}else{
				config.setIsfirstimeusediamond2000(false);
			}
			
			List<String> gameTypeList = new ArrayList<String>();

			for (String gameTypeCode : gameTypeCodes) {

				String[] codes = StringUtils.split(gameTypeCode, ",");

				//解析得玩法群、玩法组、玩法code
				Integer gameGroupCode = Integer.valueOf(codes[0]);
				Integer gameSetCode = Integer.valueOf(codes[1]);
				Integer betMethodCode = Integer.valueOf(codes[2]);
				LotteryGameGroup gameGroup = new LotteryGameGroup();
				gameGroup.setCode(gameGroupCode);
				gameGroup.setName(GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 0));
				gameGroup.setTitle(GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 0));
				if(groupCodeList2000.contains(gameGroupCode+"")){
					gameGroup.setIsNew(1);
				}else{
					gameGroup.setIsNew(0);
				}
				
				LotteryGameSet gameSet = new LotteryGameSet();
				gameSet.setCode(gameSetCode);
				gameSet.setName(GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 1));
				gameSet.setTitle(GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 1));
				gameSet.setParent(gameGroup.getName());
				LotteryBetMethod betMethod = new LotteryBetMethod();
				betMethod.setCode(betMethodCode);
				betMethod.setName(GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 2));
				betMethod.setTitle(GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameSetCode, betMethodCode, 2));
				betMethod.setParent(gameSet.getName());
				betMethod.setMode(gameGroup.getName());
				if(config.getIsSupport2000()==false && gameGroup.getIsNew()==1){
					continue;
				}
				
				if(lotteryId == 99112 && config.getUserLvl() != 0){
					//玩法是否支援鑽石加獎
					if(gameGroupCode == 10 || gameGroupCode == 12 || gameGroupCode == 15 || groupCodeList2000.contains(gameGroupCode+"")){
						gameGroup.setIsdiamond(false);
					}else{
						gameGroup.setIsdiamond(true);
					}
				}
				gameTypeList.add(gameTypeCode);
				gameGroups.add(gameGroup);
				gameSets.add(gameSet);
				betMethods.add(betMethod);
			}

			bnConvertor = new BetNameConvertor(new ArrayList<LotteryGameGroup>(gameGroups),
					new ArrayList<LotteryGameSet>(gameSets), new ArrayList<LotteryBetMethod>(betMethods));
			gameTypeCodes = new String[gameTypeList.size()];
			gameTypeList.toArray(gameTypeCodes);

			config.setGameMethods(bnConvertor.getGameTypes(gameTypeCodes));
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

			boolean flag = false;

			for (String code : gameTypeCodes) {
				if (defaultMethod.equals(code)) {
					flag = true;
					List<LotteryGameGroup> temp = bnConvertor.getGameTypes(new String[] { code });
					config.setDefaultMethod(temp.get(0).getName() + "." + temp.get(0).getChilds().get(0).getName()
							+ "." + temp.get(0).getChilds().get(0).getChilds().get(0).getName());
				}
			}
			if ( lotteryId == 99701) {
				flag = true;
				config.setDefaultMethod(config.getGameMethods().get(0)
						.getName()
						+ "."
						+ config.getGameMethods().get(0).getChilds().get(0)
								.getName());
			}
			// 当指定默认玩法被停售时默认玩法动态加载
			if (flag == false) {
				if (!config.getGameMethods().isEmpty()) {
					config.setDefaultMethod(config.getGameMethods().get(0).getName() + "."
							+ config.getGameMethods().get(0).getChilds().get(0).getName() + "."
							+ config.getGameMethods().get(0).getChilds().get(0).getChilds().get(0).getName());
				}
			}
			
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
			config.setHelpLink(helpLink);
			config.setQueryStraightOddsUrl(temp + "straightOdds");// 动态配置接口地址
			config.setHistoryLotteryAwardUrl(temp + "historyLotteryAward");// 历史开奖接口
			Cookie cookie = RequestContext.get().cookie("firstimeuse2000");
			if ((select2000 != null && select2000.equals("1")) || firsttime == true){	
				config.setDefaultMethod("housan_2000.zhixuan.fushi");
			}	
			if (lotteryId == 99401) {
				config.setPoolBouns(getPoolBouns());//双色球获取奖池金额
			}

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
			if (lotteryId == 99701) {
				// fixH.S

				//趣味賠率
				config.setGameOdds(getLhcGameOdds());
				config.setGameZodiac(getlhcGameZodiac());
				config.setGameTips(getLhcGameTips());
			}

			JsonMapper jm = new JsonMapper();
			String str = jm.toJson(config);
			result = "";
			if (lotteryId == 99401) {// 双色球单独处理
				result = getMessage("ssqConfigJs", new String[] { str,
						lotterySeries });
			} else if (lotteryId == 99701) {
				result = getMessage("lhcConfigJs", new String[] { str,
						lotterySeries });
			} else {
				result = getMessage("configJs", new String[] { str, lotterySeries });
			}
			logger.info("get lottery config begin end...");
			return result;
		} catch (Exception e) {
			logger.error("get lottery config begin error...", e);
			result = getMessage("errorConfigJs");//当数据出问题时，保证js页面不会崩溃
			return result;
		}
	}

	private Long getPoolBouns() throws Exception {
		GameIssueQueryResponse gameIssue = getCurrentGameIssue();
		GameBonusJsonBean bean = convertJsonToObject(gameIssue.getAwardStruc(), GameBonusJsonBean.class);
		return bean.getGameBonusPoolJson().getActualBonus();
	}
	
	@Override
	public DynamicConfig getDynamicConfig() throws Exception {
		logger.info("get dynamicConfig  start");

		String uname = RequestContext.getCurrUser().getUserName();

		GameConfigAndAwardData gd = new GameConfigAndAwardData();
		logger.info("get dynamicConfig  gamelimit start");
		//获取当前投注方式的最大倍数限制以及最大追号期数
		gd.setGamelimit(getGameLimits());//获取各玩法的倍数限制信息
		logger.info("get dynamicConfig  gamelimit end");

		//可追号的最大期数
		gd.setTracemaxtimes(betHttpClient.getMaxGameIssue(lotteryId).getBody().getResult().intValue());
          
		if(lotteryId==99111||lotteryId==99114) {
			gd.setTracemaxtimes(40);
		}
		//获取可追号期数列表
		gd.setGamenumbers(getGameNumbers(gd.getTracemaxtimes()));

		//是否当前奖期销售暂停 0正常 1 暂停
		gd.setIsstop(0);
		gd.setMultiplemaxtimes(Integer.valueOf(multiplemaxtimes));
		gd.setMultipleToCnyFactor(Integer.valueOf(multipleToCnyFactor));

		//获取当前奖期信息
		GameIssueQueryResponse gameIssue = getCurrentGameIssue();

		//将开奖号码用逗号分隔
		gd.setLastballs(formatLastBalls(gameIssue));

		if (!StringUtils.isEmpty(gd.getLastballs()) && lotteryId == 99201) {
			String[] temp = gd.getLastballs().split(",");
			Integer[] numbers = new Integer[temp.length];
			for (int i = 0; i < temp.length; i++) {
				numbers[i] = Integer.valueOf(temp[i]);
			}

			gd.setBallInfo(DTOConvertor4Web.getBetKl8Tip(Arrays.asList(numbers)));
		}
		
		// 获取上期奖期
		if (lotteryId == 99701) {
			if(gameIssue.getLastWebIssueCode()!=null){
				StringBuilder sb = new StringBuilder(gameIssue.getLastWebIssueCode());
				sb.insert(2, "/");
				gd.setLastnumber(sb.toString());
			}else{
				gd.setLastnumber("/");
			}
		} else {
			gd.setLastnumber(gameIssue.getLastWebIssueCode());
		}
		gd.setNowstoptime(DateUtils.format(
				DataConverterUtil.convertLong2Date(gameIssue.getSaleEndTime()),
				getProperty("jsViewDataFormat")));// 当前期投注结束时间
		gd.setNowtime(DateUtils.format(new Date(),
				getProperty("jsViewDataFormat")));// 当前服务器时间
		if (lotteryId == 99701) {
			StringBuilder sb = new StringBuilder(gameIssue.getWebIssueCode());
			sb.insert(2, "/");
			gd.setNumber(sb.toString());// 当前web期号
		} else {
			gd.setNumber(gameIssue.getWebIssueCode());// 当前web期号
		}

		gd.setIssueCode(gameIssue.getIssueCode());// 当前期号
		gd.setResulttime(DateUtils.format(DataConverterUtil
				.convertLong2Date(gameIssue.getSaleStartTime()),
				getProperty("jsViewDataFormat")));// 当前期预计开始时间
		gd.setUsername(uname);// 当前用户名称

		gd.setLotteryday(DateUtils.format(
				DataConverterUtil.convertLong2Date(gameIssue.getOpenDrawTime()),
				getProperty("jsViewDataTimeFormat")));

		if((lotteryId == 99602 || lotteryId == 99603) && gameIssue.getLastIssueCode()!=null){
			gd.setResulttime(DateUtils.format(DateUtils.addSeconds(DataConverterUtil.convertLong2Date(gameIssue.getSaleStartTime()), 15),
					getProperty("jsViewDataFormat")));//当前期预计开始时间
			gd.setNowstoptime(DateUtils.format(DateUtils.addSeconds(DataConverterUtil.convertLong2Date(gameIssue.getSaleEndTime()), 15),
					getProperty("jsViewDataFormat")));//当前期投注结束时间
		}

		DynamicConfig dc = new DynamicConfig();
		dc.setData(gd);
		dc.setIsSuccess(1);
		dc.setMsg("success");
		logger.info("get dynamicConfig  end");
		return dc;
	}
	private static boolean isZuxuan(String type){
		if(StringUtils.contains(type, "zuxuan.hezhi") || StringUtils.contains(type, "zuxuan.baodan") || StringUtils.contains(type, "zuxuan.hunhezuxuan")){
			if(StringUtils.contains(type, "qiansan") || StringUtils.contains(type, "zhongsan") || StringUtils.contains(type, "housan"))
			return true;
		}
		return false;
	}

	@Override
	public StraightOdds getStraightOdds() throws Exception {
		List<LotteryGameUserAwardListStruc> userAwardListStrucList = getUserArwardGroupsByLotteryIdAndUserId();
		String oddstr = "";
		int defOdd = 40; // 改static
		if (userAwardListStrucList != null) {
			for (LotteryGameUserAwardListStruc struc : userAwardListStrucList) {
				for (Long i = struc.getDirectRet() / 100; i >= 0; i -= 2) {
					oddstr += defOdd + ",";
					defOdd++;
				}
			}
		}
		StraightOdds so = new StraightOdds();
		so.setIsSuccess(1);
		so.setMsg("success");
		String[] odds = oddstr.split(",");
		so.setOddsList(odds);
		return so;
	}

	/**
	 * 
	* @Title: getBetAward 
	* @Description: 查询玩法描述和默认冷热球及用户投注方式奖金
	* @param request
	* @return
	* @throws Exception
	 */
	@Override
	public GetAwardResultDTO getBetAward(@RequestParam("type") String type, @RequestParam("extent") String extent,
			@RequestParam("line") Integer line, @RequestParam("lenth") Integer lenth) throws Exception {

		logger.info("getBetAward start");

		Response<QueryDescByBetMethodByUserIdResponse> response = new Response<QueryDescByBetMethodByUserIdResponse>();
		QueryDescByBetMethodByUserIdRequest request = makeQueryAssistActionRequest(type);

		GetAwardResultDTO result = new GetAwardResultDTO();
		GetAwardDTO getAwardDTO = new GetAwardDTO();
		GameTipsByBet gameTips = new GameTipsByBet();
		List<List<NumberFrequencyCell>> frequency = new ArrayList<List<NumberFrequencyCell>>();

		try {
			response = betHttpClient.QueryDescByBetMethodByUserId(request);
			frequency = getDefaultFrequency(type, extent);

			logger.info("queryNumberCharts end");
		} catch (Exception e) {
			logger.error("queryNumberCharts is error.", e);
			throw e;
		}

		if (null != response.getBody().getResult()) {//bug 2923 需求小数点后是0的去掉0
			result.setMoreBouns(response.getBody().getResult().getMoreBouns());
			Double bonus = 0.0;
			Double bonusDown = 0.0;
			Double theoryBonus = 0.0;
			Double retPoint = 0.0;
			if (null != response.getBody().getResult().getActualBonus()) {
				bonus = Double.valueOf(response.getBody().getResult().getActualBonus());
				bonus = bonus / 10000;
			}
			if (null != response.getBody().getResult().getActualBonusDown()) {
				bonusDown = Double.valueOf(response.getBody().getResult().getActualBonusDown());
				bonusDown = bonusDown / 10000;
			}
			if (null != response.getBody().getResult().getTheoryBonus()) {
				theoryBonus = Double.valueOf(response.getBody().getResult().getTheoryBonus());
				theoryBonus = theoryBonus / 10000;
			}
			if (null != response.getBody().getResult().getRetPoint()) {
				retPoint = Double.valueOf(response.getBody().getResult().getRetPoint());
				retPoint = retPoint / 100;
			}
			try {
				gameTips.setExample(response.getBody().getResult().getGameExamplesDes());
				gameTips.setTips(response.getBody().getResult().getGamePromptDes());
				if (Math.round(bonusDown) == bonusDown) {
					gameTips.setExample(gameTips.getExample().replaceAll("bonus2", String.valueOf(Math.round(bonusDown))));
					gameTips.setTips(gameTips.getTips().replaceAll("bonus2", String.valueOf(Math.round(bonusDown))));
				} else {
					gameTips.setExample(gameTips.getExample().replaceAll("bonus2", String.valueOf(bonusDown)));
					gameTips.setTips(gameTips.getTips().replaceAll("bonus2", String.valueOf(bonusDown)));
				}
				if (Math.round(bonus) == bonus) {
					gameTips.setExample(gameTips.getExample().replaceAll("bonus", String.valueOf(Math.round(bonus))));
					gameTips.setTips(gameTips.getTips().replaceAll("bonus", String.valueOf(Math.round(bonus))));
				
				} else {
					gameTips.setExample(gameTips.getExample().replaceAll("bonus", String.valueOf(bonus)));
					gameTips.setTips(gameTips.getTips().replaceAll("bonus", String.valueOf(bonus)));
				}
			} catch (Exception e) {
				logger.error("queryNumberCharts end", e);
			}
			gameTips.setActualBonus(bonus);
			gameTips.setTheoryBonus(theoryBonus); 
			if(!isZuxuan(type) && !SuperPairUtil.isSuperPairByGroupCodeName(type)){ 
				gameTips.setRetPoint(retPoint);
			}else{
				gameTips.setActualBonus(0L);
				gameTips.setTheoryBonus(0L); 
				gameTips.setRetPoint(0);
			}
			
		} else {
			gameTips.setExample(type + "选号实例");
			gameTips.setTips(type + "玩法提示");
			gameTips.setActualBonus(0L);
			gameTips.setTheoryBonus(0L);
			gameTips.setRetPoint(0L);
		}

		if (lotteryId == 99401) {//双色球
			String str[] = gameTips.getExample().split(",");
			
			// +6 2000   獎金組 造成問題 往後退6個位置
			String temp = getMessage("ssqAwardExample", new String[] { str[0 + 6], str[1+ 6], str[2+ 6], str[3+ 6], str[3+ 6], str[4+ 6],
					str[4+ 6], str[5+ 6], str[5+ 6], str[5+ 6] });
			gameTips.setExample(temp);
		}
		
		getAwardDTO.setGameTips(gameTips);
		getAwardDTO.setFrequency(frequency);

		result.setData(getAwardDTO);
		result.setIsSuccess(1);
		result.setMsg("success");
		result.setType("userError");

		return result;
	}

	/** 
	* @Title: makeQueryAssistActionRequest 
	* @Description: 生成查询Request  
	* @param @param betMethodType
	*/
	private QueryDescByBetMethodByUserIdRequest makeQueryAssistActionRequest(String betMethodType) {
		QueryDescByBetMethodByUserIdRequest queryAssistActionRequest = new QueryDescByBetMethodByUserIdRequest();
		queryAssistActionRequest.setLotteryid(lotteryId);
		queryAssistActionRequest.setUserid(RequestContext.getCurrUser().getId());

		String[] type = betMethodType.split("\\.");
		Integer gameGroupCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 0);
		Integer gameSetCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 1);
		Integer betMethodCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 2);

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
	private QueryDescByBetMethodByUserIdRequest makeQuery(Integer gameGroupCode,Integer gameSetCode,Integer betMethodCode) {
		QueryDescByBetMethodByUserIdRequest queryAssistActionRequest = new QueryDescByBetMethodByUserIdRequest();
		queryAssistActionRequest.setLotteryid(lotteryId);
		queryAssistActionRequest.setUserid(RequestContext.getCurrUser().getId());
		queryAssistActionRequest.setGameGroupCode(gameGroupCode);
		queryAssistActionRequest.setGameSetCode(gameSetCode);
		queryAssistActionRequest.setBetMethodCode(betMethodCode);
		return queryAssistActionRequest;
	}

	/**
 	* @throws Exception 
	 * @Title: getDefaultFrequency 
	* @Description: 获取默认的冷热遗漏号(CQSSC五星直选当前遗漏)
	*/
	private List<List<NumberFrequencyCell>> getDefaultFrequency(String type, String extent) throws Exception {
		List<List<NumberFrequencyCell>> frequency = new ArrayList<List<NumberFrequencyCell>>();

		Response<GameLrylQueryResponse> response = new Response<GameLrylQueryResponse>();
		GameMissingValueQueryRequest request = makeGameMissingValueQueryRequest(type, extent);
		try {
			logger.info("query lost Numbers end");
		} catch (Exception e) {
			logger.error("query lost Numbers error.", e);
			throw e;
		}
		return frequency;
	}

	protected void packData(String gameMode, List<List<NumberFrequencyCell>> frequency, List<GameLryl> list) {
		List<NumberFrequencyCell> l = null;
		NumberFrequencyCell cell = null;
		int seq = -1;
		for (int i = 0; i < list.size(); i++) {
			GameLryl gl = list.get(i);
			if (seq != gl.getBitNumber()) {
				seq = gl.getBitNumber();
				l = new ArrayList<NumberFrequencyCell>();
				frequency.add(l);
			}
			cell = new NumberFrequencyCell();
			cell.setCurrentNum(gl.getLottNumber());
			cell.setPinlv(gl.getRetValue());
			l.add(cell);
		}
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
	public GameMissingValueQueryRequest makeGameMissingValueQueryRequest(String gameMode, String extent) {

		GameMissingValueQueryRequest request = new GameMissingValueQueryRequest();

		String[] type = gameMode.split("\\.");

		Integer gameGroupCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 0);
		Integer gameSetCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 1);
		Integer betMethodCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 2);

		request.setLotteryId(lotteryId);
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
	 * 
	* @Title: frequency 
	* @Description: 查询冷热遗漏号码
	* @param request
	* @return
	* @throws Exception
	 */
	@Override
	public List<List<NumberFrequencyCell>> frequency(@RequestParam("gameMode") String gameMode,
			@RequestParam("extent") String extent, @RequestParam("frequencyType") String frequencyType,
			@RequestParam("line") Integer line, @RequestParam("lenth") Integer lenth) throws Exception {

		logger.info("query frequency start");
		Response<GameLrylQueryResponse> response = new Response<GameLrylQueryResponse>();

		if ("lost".equals(frequencyType)) {

			// 遗漏
			GameMissingValueQueryRequest request = makeGameMissingValueQueryRequest(gameMode, extent);
			//超级2000的遗漏转成普通玩法的遗漏查询begin
			int groupCode = request.getGameGroupCode()==51?33: request.getGameGroupCode()==50?16:request.getGameGroupCode()==49?
					15:request.getGameGroupCode()==48?14:request.getGameGroupCode()==47?13:request.getGameGroupCode()==46?12:
						request.getGameGroupCode()==45?11:request.getGameGroupCode()==44?10:request.getGameGroupCode();
			request.setGameGroupCode(groupCode);
			//超级2000的遗漏转成普通玩法的遗漏查询end
			try {
				response = betHttpClient.queryMissingValue(request);
				logger.info("query lost Numbers end");
			} catch (Exception e) {
				logger.error("query lost Numbers error.", e);
				throw e;
			}

		} else if ("fre".equals(frequencyType)) {
			// 冷热
			GameColdAndHotNumbersQueryRequest request = makeGameColdAndHotNumbersQueryRequest(gameMode, extent);
			int groupCode = request.getGameGroupCode()==51?33: request.getGameGroupCode()==50?16:request.getGameGroupCode()==49?
					15:request.getGameGroupCode()==48?14:request.getGameGroupCode()==47?13:request.getGameGroupCode()==46?12:
						request.getGameGroupCode()==45?11:request.getGameGroupCode()==44?10:request.getGameGroupCode();
			request.setGameGroupCode(groupCode);
			try {
				response = betHttpClient.queryColdAndHotNumbers(request);
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
				packData(gameMode, result, list);
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
			}
		}
		
		return result;
	}

	/** 
	* @Title: makeGameColdAndHotNumbersQueryRequest 
	* @Description: 生成查询Request  
	*/
	public GameColdAndHotNumbersQueryRequest makeGameColdAndHotNumbersQueryRequest(String gameMode, String extent) {

		GameColdAndHotNumbersQueryRequest request = new GameColdAndHotNumbersQueryRequest();

		String[] type = gameMode.split("\\.");
		Integer gameGroupCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 0);
		Integer gameSetCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 1);
		Integer betMethodCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 2);

		request.setLotteryId(lotteryId);
		request.setGameGroupCode(gameGroupCode);
		request.setGameSetCode(gameSetCode);
		request.setBetMethodCode(betMethodCode);
		request.setCountIssue(Integer.parseInt(extent));

		return request;
	}

	/**
	 * 取用户余额
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Long getUserBal() throws Exception {

		logger.info("queryUserBal start");

		Long userId = RequestContext.getCurrUser().getId();
		Response<Long> response = betHttpClient.getUserBal(userId);

		return response.getBody().getResult().longValue();
	}

	/**
	 * 取投注页面“我的方案”列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<OrdersStrucDTO> getUserOrders() throws Exception {

		logger.debug("getOrdersByUserId start");

		Long userId = RequestContext.getCurrUser().getId();

		GameOrderQueryRequest gameOrderRequest = new GameOrderQueryRequest();
		gameOrderRequest.setLotteryId(lotteryId);
		//只取最近7天的数据
		Date endTime = DateUtils.currentDate();
		Date startTime = DateUtils.addDays(endTime, -6);
		if (lotteryId == 99108 || lotteryId == 99109 || lotteryId == 99401) {//低频彩查50天
			startTime = DateUtils.addDays(endTime, -50);
		}
		gameOrderRequest.setStartTime(DataConverterUtil.convertDate2Long(startTime));
		gameOrderRequest.setEndTime(DataConverterUtil.convertDate2Long(endTime));
		Pager pager = new Pager();
		pager.setStartNo(0);
		if (lotteryId == 99106) {//宝开时时彩只返回4条数据
			pager.setEndNo(3);
		} else if (lotteryId == 99112) {
			pager.setEndNo(9);
		} else if (lotteryId == 99306) {
			pager.setEndNo(9);
		} else if (lotteryId == 99111) {
			pager.setEndNo(4);
		} else if (lotteryId == 99701) {
			pager.setEndNo(2);
		} else {
			pager.setEndNo(getIntProperty("ordersNum"));
		}

		Response<GameOrderQueryResponse> orderResponse = betHttpClient.getGameOrders(gameOrderRequest, userId, pager);
		logger.debug("queryOrders end");

		if (orderResponse.getBody().getResult() != null) {
			if (lotteryId == 99112||lotteryId==99306) {
				List<OrdersStrucDTO> sds = DTOConvertor4Web.orderStrucs2OrderStrucsDTO(orderResponse.getBody()
						.getResult().getOrdersStruc());
				if (!sds.isEmpty()) {
					for (OrdersStrucDTO sd : sds) {
						sd.setNumberRecord(formatOrderNumber(sd.getNumberRecord()));
						if (sd.getStatus() == 4) {
							sd.setStatusName("开奖失败(已撤销)");
						}
					}
				}
				return sds;
			} else {
				return DTOConvertor4Web
						.orderStrucs2OrderStrucsDTO(orderResponse.getBody().getResult().getOrdersStruc());
			}
		}
		return null;
	}

	/**
	 * 取投注页面“我的追号”列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PlansStrucForUI> getUserPlans() throws Exception {

		logger.info("queryPlans start");

		Long userId = RequestContext.getCurrUser().getId();

		GamePlanQueryRequest gamePlanRequest = new GamePlanQueryRequest();
		//只取最近7天的数据
		Date endTime = DateUtils.currentDate();
		Date startTime = DateUtils.addDays(endTime, -6);
		if (lotteryId == 99108 || lotteryId == 99109 || lotteryId == 99401) {//低频彩查50天
			startTime = DateUtils.addDays(endTime, -50);
		}
		gamePlanRequest.setStartTime(DataConverterUtil.convertDate2Long(startTime));
		gamePlanRequest.setEndTime(DataConverterUtil.convertDate2Long(endTime));
		gamePlanRequest.setLotteryId(lotteryId);
		Pager pager = new Pager();
		pager.setStartNo(0);
		if (lotteryId == 99106) {//宝开时时彩只返回4条数据
			pager.setEndNo(3);
		} else if (lotteryId == 99111) {
			pager.setEndNo(4);
		} else {
			pager.setEndNo(getIntProperty("plansNum"));
		}
		Response<GamePlanQueryResponse> gamePlanQueryResponse = betHttpClient.getGamePlans(gamePlanRequest, userId,
				pager);
		logger.info("queryPlans end");

		return DTOConvertor4Web.plansStrucs2PlansStrucForUIs(gamePlanQueryResponse.getBody().getResult()
				.getPlansStruc());
	}

	@Override
	public Object betFile(MultipartFile file) throws Exception {

		InputStream in = null;
		if (!file.isEmpty()) {
			try {
				String source = new String(file.getBytes(), getProperty("betFileCharset"));
				source = StringUtils.replaceEach(source, new String[] { "\r\n" }, new String[] { "\\n" });
				source = StringUtils.replaceEach(source, new String[] { "\n" }, new String[] { "\\n" });
				source = StringUtils.replaceEach(source, new String[] { "\'" }, new String[] { "&apos;" });
				source = StringUtils.trim(source);

				String str = "<script >(function(){var Games = window.parent.phoenix.Games,current = Games.getCurrentGame().getCurrentGameMethod(),data='"
						+ source + "'; current.getFile(data)})()</script>";
				return str;
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
					}
				}
			}
		}

		return "";
	}

	@Override
	public GameBetJsonResultStruc getCancelOrderCharge(String amount) throws Exception {

		logger.info("cal handlingCharge start");

		GameBetJsonDateStruc jds = new GameBetJsonDateStruc();

		CancelOrderChargeRequest request = new CancelOrderChargeRequest();
		request.setLotteryId(lotteryId);
		request.setTotalBetAmount(Double.valueOf(amount.replace(",", "")));

		Response<CancelOrderChargeResponse> config = betHttpClient.getCancelOrderCharge(request);
		jds.setHandingcharge(config.getBody().getResult().getHandlingCharge());
		logger.info("getHandlingCharge end");

		GameBetJsonResultStruc hr = new GameBetJsonResultStruc();
		hr.setIsSuccess(1);
		hr.setMsg("success");
		hr.setData(jds);

		return hr;
	}

	protected GameOrderRequestDTO convertJsonToGameOrderRequestDTO(String data) throws Exception {
		return convertJsonToObject(data, GameOrderRequestDTO.class);
	}

	@Override
	public GameBetJsonResultStruc save(String data, HttpServletRequest request) throws Exception {

		logger.info("saveGameOrder start");
		
		//springmvc 支持直接解析json，但是参数或类型错误不会打印出来，直接给报一个400 bad request，因此在此用代码转换json串  //  金额*10000  自解析json处理异常抛出
		GameOrderRequestDTO dto = new GameOrderRequestDTO();
		try {
			dto = convertJsonToGameOrderRequestDTO(data); 
		} catch (Exception e) {
			logger.error("saveGameOrder json param paser error!", e);
			return serverMessages.get(99999L);// 参数修改成投注格式错误
		}

		// 於 redis 內存入用戶目前投注的彩種(記錄 lotteryId, lotteryCode)
		String redisKey = DigestUtils.md5Hex("defindgamemenu"+ RequestContext.getCurrUser().getId().toString());
		String redisValue = redis.get(redisKey);
		if (redisValue != null){

			GameFavorites gameFavorite = jmapper.fromJson(redisValue, jmapper.createCollectionType(GameFavorites.class));
			gameFavorite.setGameLastId(lotteryId.toString());
			gameFavorite.setGameLastName(lotteryCode);
			redisValue = jmapper.toJson(gameFavorite);
			
			redis.set(redisKey, redisValue);
		}else{

			GameFavorites gameFavorite = new GameFavorites ();
			gameFavorite.setGameLastId(lotteryId.toString());
			gameFavorite.setGameLastName(lotteryCode);
			redisValue = jmapper.toJson(gameFavorite);
			redis.set(redisKey, redisValue);
		}
				
		//获取当前奖期信息并判断提交数据时奖期是否已经过期
		GameIssueQueryResponse gameIssue = getCurrentGameIssue();

		Long issueCode = dto.getOrders().get(0).getIssueCode();

		String webIssueCode = dto.getOrders().get(0).getNumber();
		//六合彩不追號
		if(lotteryCode.equals("lhc")){
			dto.setIsTrace(0);
		}
		if (dto.getIsTrace() == 0) {
			//不追号 判断投注是否为当前期
			if (issueCode.longValue() != gameIssue.getIssueCode().longValue()) {
				return betGameIssueError(gameIssue, webIssueCode);
			}
		} else {//追号 判断第一期是否>=当前期
			if (issueCode.longValue() < gameIssue.getIssueCode().longValue()) {
				return betGameIssueError(gameIssue, webIssueCode);
			}
		}

		Long status = 0L;
		String account = RequestContext.getCurrUser().getUserName();
		GameOrderResponseDTO gameOrderResponseDTO = null;
		if (dto.getIsTrace() == 0) {//不追号
			Response<GameOrderResponse> result = orderBet(dto, request);
			status = result.getHead().getStatus();
			if (result.getBody()!=null && result.getBody().getResult() != null) {
				gameOrderResponseDTO = result.getBody().getResult().getGameOrderDTO();
			}
			//处理封锁变价
			if (status == 205000) {
				GameBetJsonResultStruc struc = handingBetStatus(205000L);
				//处理封锁变价的数据结构
				LockFacadeUtils.facadeGameOrderStruc(struc, result.getBody().getResult().getGameOrderDTO(), dto,
						account, getBetNameConvertor(), lotteryId);
				return struc;
			}
			//处理倍数超限数据结构
			if (status == 202005) {
				GameBetJsonResultStruc struc = handingBetStatus(202005L);
				List<GameOrderResponeOverMutipleDTO> overMutipleDto = result.getBody().getResult().getOverMutipleDTO();
				if (!overMutipleDto.isEmpty()) {
					for (GameOrderResponeOverMutipleDTO omd : overMutipleDto) {
						String[] betType = omd.getBetTypeCode().split("\\_");
						Integer gameGroupCode = Integer.valueOf(betType[0]);
						Integer gameBetCode = Integer.valueOf(betType[1]);
						Integer gameMethodCode = Integer.valueOf(betType[2]);
						omd.setBetMethod("["
								+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 0)
								+ "_"
								+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 1)
								+ "_"
								+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 2)
								+ "]");
					}
					struc.getData().setOverMutipleData(overMutipleDto);
				} else {
					struc.setType("subFaild");
				}
				return struc;
			}
		} else {
			//追号
			Response<GamePlanResponse> result = planBet(dto, request, gameIssue);
			status = result.getHead().getStatus();
			if (result.getBody().getResult() != null) {
				gameOrderResponseDTO = result.getBody().getResult().getGameOrderResponseDTO();
			}
			//处理封锁变价
			if (status == 205000) {
				GameBetJsonResultStruc struc = handingBetStatus(205000L);
				//处理封锁变价的数据结构
				LockFacadeUtils.facadeGamePlanStruc(struc, result.getBody().getResult().getGameOrderResponseDTO(), dto,
						account, getBetNameConvertor(), lotteryId);
				return struc;
			}

			if (status == 202005) { //倍數限制
				GameBetJsonResultStruc struc = handingBetStatus(202005L);
				List<GameOrderResponeOverMutipleDTO> overMutipleDto = result.getBody().getResult().getOverMutipleDTO();
				if (!overMutipleDto.isEmpty()) {
					for (GameOrderResponeOverMutipleDTO omd : overMutipleDto) {
						String[] betType = omd.getBetTypeCode().split("\\_");
						Integer gameGroupCode = Integer.valueOf(betType[0]);
						Integer gameBetCode = Integer.valueOf(betType[1]);
						Integer gameMethodCode = Integer.valueOf(betType[2]);
						omd.setBetMethod("["
								+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 0)
								+ "_"
								+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 1)
								+ "_"
								+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 2)
								+ "]");
					}
					struc.getData().setOverMutipleData(overMutipleDto);
				} else {
					struc.setType("subFaild");
				}
				return struc;
			}
			
			if (status == 202009) { //金額上限
				return handingBetStatus(202009L);
			}
		}

		//保存选号球，假如存在暂停玩法，删掉暂停选号球返回
		List<GameBetBalls> balls = new ArrayList<GameBetBalls>();
		List<BetDetailStrucDTO> betBalls = dto.getBalls();
		GameBetJsonDateStruc jds = new GameBetJsonDateStruc();
		GameBetJsonResultStruc hr = new GameBetJsonResultStruc();
		GameBetTplData tplDate = new GameBetTplData();
		String strStatus = status + "";
		if (strStatus.length() == 12) {
			//投注方式暂停异常处理需要截取活的投注方式名称
			Integer gameGroupCode = Integer.valueOf(strStatus.substring(0, 2));
			Integer gameBetCode = Integer.valueOf(strStatus.substring(2, 4));
			Integer gameMethodCode = Integer.valueOf(strStatus.substring(4, 6));
			String betName = GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 0)
					+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 1)
					+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 2);
			String betEnName = GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 0)
					+ "." + GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 1) + "."
					+ GameAwardNameUtil.getName(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 2);
			tplDate.setMsg(getMessage("gameMethodPaused", new String[] { betName }));
			jds.setTplData(tplDate);
			hr.setIsSuccess(0);
			hr.setType("pauseBet");
			hr.setMsg(getMessage("gameMethodPaused", new String[] { betName }));
			for (BetDetailStrucDTO ball : betBalls) {
				if (ball.getType().equals(betEnName)) {
					GameBetBalls gb = new GameBetBalls();
					gb.setBall(ball.getBall());
					gb.setId(Long.valueOf(ball.getId()));
					gb.setType(ball.getType());
					balls.add(gb);
				}
			}
			tplDate.setBalls(balls);
			hr.setData(jds);
			return hr;
		} else {
			GameBetJsonResultStruc statusHr = handingBetStatus(status);
			if (statusHr != null && statusHr.getIsSuccess() == 1 && gameOrderResponseDTO != null) {
				statusHr.getData().setOrderId(gameOrderResponseDTO.getOrderId());
				statusHr.getData().setProjectId(gameOrderResponseDTO.getOrderCode());
				statusHr.getData().setWriteTime(gameOrderResponseDTO.getSaleTime());
				statusHr.getData().setTotalprice(gameOrderResponseDTO.getTotalAmount());
				statusHr.getData().setNumber(gameOrderResponseDTO.getWebIssueCode());
			}
			return statusHr;
		}
	}

	private GameBetJsonResultStruc errorReturn(Long status) {
		if (serverMessages.get(status) != null) {
			return serverMessages.get(status);
		} else {
			return serverMessages.get(99999L);
		}
	}

	@Override
	public GameBetJsonResultStruc saveMMC(String data, HttpServletRequest request) throws Exception {

		logger.info("saveGameOrder start");
		//springmvc 支持直接解析json，但是参数或类型错误不会打印出来，直接给报一个400 bad request，因此在此用代码转换json串  //  金额*10000  自解析json处理异常抛出
		GameOrderRequestDTO dto = new GameOrderRequestDTO();
		try {
			dto = convertJsonToObject(data, GameOrderRequestDTO.class);
		} catch (Exception e) {
			logger.error("saveGameOrder json param paser error!", e);
			return serverMessages.get(99999L);// 参数修改成投注格式错误
		}

		//TODO 添加秒秒投注key值对比 防止页面多开
		String serverBetKeyValue = redis.get("mmcBet" + RequestContext.getCurrUser().getId()+lotteryId);

		for (Cookie co : request.getCookies()) {
			if (co.getName().equals("mmcBetCookie"+lotteryId)) {
				if (!serverBetKeyValue.equals(co.getValue())) {
					GameBetJsonResultStruc struc = new GameBetJsonResultStruc();
					struc.setIsSuccess(0);
					struc.setType("normal");
					if(lotteryId == 99306l){
						struc.setMsg("秒开11选5投注失败!请在最新开的秒开11选5页面投注或者刷新当前页面重新投注!");
					}else{
						struc.setMsg("秒秒彩投注失败!请在最新开的秒秒页面投注或者刷新当前页面重新投注!");
					}
					return struc;
				}
			}
		}

		Long status = 0L;
		String account = RequestContext.getCurrUser().getUserName();
		Response<GameOrderResponse> result = orderBet(dto, request);
		status = result.getHead().getStatus();

		//构建秒秒彩返回json
		if (status == 0) {
			Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();
			GameOrderDetailQueryRequest queryRequest = new GameOrderDetailQueryRequest();
			queryRequest.setOrderId(result.getBody().getResult().getOrderId());
			try {
				response = betHttpClient.getOrderDetai(queryRequest);
				logger.info("queryOrderDetail end");
			} catch (Exception e) {
				logger.error("queryOrderDetail is error.", e);
				throw e;
			}

			GameBetJsonResultStruc struc = new GameBetJsonResultStruc();
			struc.setIsSuccess(1);
			struc.setType("success");
			struc.setMsg("恭喜您投注成功");
			GameBetJsonDateStruc datas = new GameBetJsonDateStruc();
			OrdersStruc oc = response.getBody().getResult().getOrdersStruc();
			List<SlipsStruc> slipStrucs = response.getBody().getResult().getSlipsStruc();
			datas.setProjectId(oc.getOrderCode());
			datas.setWriteTime(DateUtils.format(DataConverterUtil.convertLong2Date(oc.getSaleTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
			datas.setResult(formatOrderNumber(oc.getNumberRecord()));
			datas.setTotalprice(oc.getTotamount());
			datas.setDiamondLv(getDiamondLv(oc.getNumberRecord()));
			datas.setDiamondTimes(Double.valueOf(oc.getDiamondMultiple())/10);
			if (oc.getStatus() == 2) {
				datas.setWinNum(1L);
				datas.setWinMoney(oc.getTotwin()+oc.getTotDiamondWin());
			} else {
				datas.setWinNum(0L);
				datas.setWinMoney(0L);
			}

			List<MMCOrderDetail> list = new ArrayList<MMCOrderDetail>();
			for (SlipsStruc ss : slipStrucs) {
				MMCOrderDetail md = new MMCOrderDetail();
				md.setCode(ss.getBetDetail());
				if (ss.getStatus() == 2)//中奖
				{
					md.setIsWin(1);
				} else {
					md.setIsWin(0);
				}
				md.setMethodName(GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
						ss.getBetMethodCode(), 0)
						+ "_"
						+ GameAwardNameUtil.getTitle(lotteryId, ss.getGameGroupCode(), ss.getGameSetCode(),
								ss.getBetMethodCode(), 2));
				if (ss.getMoneyMode() == 1) { //1元2角
					md.setModes("元");
				}else if(ss.getMoneyMode() == 3){
					md.setModes("分");
				} else {
					md.setModes("角");
				}
				md.setNum(ss.getTotbets());
				md.setMultiple(ss.getMultiple() + "");
				md.setProjectid(oc.getOrderCode());
				md.setTotalPrice(ss.getTotamount() + "");
				
				String betTypeCode=ss.getGameGroupCode()+"_"+ss.getGameSetCode()+"_"+ss.getBetMethodCode();
				if(GameBetQueryController.isZuxuan(betTypeCode)){
					md.setGroupAward(0L);
					md.setGroupAwardDown(0L);
					md.setRetAward(0L);
					md.setRetPoint(0L);
				}else{
					md.setGroupAward(ss.getGroupAward());
					md.setGroupAwardDown(ss.getGroupAwardDown());
					md.setRetAward(formatLong(ss.getRetAward()));
					md.setRetPoint(ss.getRetPoint());
				}				
				md.setAwardMode(ss.getAwardMode());
				if (ss.getStatus() == 2) {
					md.setWinNum(3L);
					md.setWinMoney(ss.getAward());
					if(ss.getDiamondWin() > 0l){
						md.setDiamondWin(ss.getAward()+ss.getDiamondWin());
					}else{
						md.setDiamondWin(0l);
					}
				} else {
					md.setWinNum(0L);
					md.setWinMoney(0L);
					md.setDiamondWin(0l);
				}
				md.setWriteTime(DateUtils.format(DataConverterUtil.convertLong2Date(oc.getSaleTime()),
						DateUtils.DATETIME_FORMAT_PATTERN));
				md.setDiamondAmount(ss.getDiamondAmount());
				Boolean issuportdiamond = false;
				if(ss.getDiamondAmount() != 0l){
					issuportdiamond = true;
				}
				md.setIssuportdiamond(issuportdiamond);
				list.add(md);
			}
			datas.setList(list);
			struc.setData(datas);

			struc.setBalance(new BigDecimal(getUserBal())
					.divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP)
					.doubleValue());
			return struc;
		}

		if (status == 204005) {//秒秒彩开奖失败
			Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();
			GameOrderDetailQueryRequest queryRequest = new GameOrderDetailQueryRequest();
			queryRequest.setOrderId(result.getBody().getResult().getOrderId());
			try {
				response = betHttpClient.getOrderDetai(queryRequest);
				logger.info("queryOrderDetail end");
			} catch (Exception e) {
				logger.error("queryOrderDetail is error.", e);
				throw e;
			}

			GameBetJsonResultStruc struc = new GameBetJsonResultStruc();
			struc.setIsSuccess(0);
			struc.setType("normal");
			struc.setMsg("秒开时时彩开奖失败！投注订单已做撤销处理，请重新投注！");
			GameBetJsonDateStruc datas = new GameBetJsonDateStruc();
			OrdersStruc oc = response.getBody().getResult().getOrdersStruc();
			datas.setProjectId(oc.getOrderCode());
			datas.setWriteTime(DateUtils.format(DataConverterUtil.convertLong2Date(oc.getSaleTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
			datas.setResult(formatOrderNumber(oc.getNumberRecord()));
			datas.setTotalprice(oc.getTotamount());
			datas.setWinMoney(oc.getTotwin());
			if (oc.getStatus() == 2) {
				datas.setWinNum(1L);
			} else {
				datas.setWinNum(0L);
				datas.setWinMoney(0L);
			}
			struc.setData(datas);
			return struc;
		}

		//处理封锁变价
		if (status == 205000) {
			GameBetJsonResultStruc struc = handingBetStatus(205000L);
			//处理封锁变价的数据结构
			LockFacadeUtils.facadeGameOrderStruc(struc, result.getBody().getResult().getGameOrderDTO(), dto, account,
					getBetNameConvertor(), lotteryId);
			return struc;
		}

		//保存选号球，假如存在暂停玩法，删掉暂停选号球返回
		List<GameBetBalls> balls = new ArrayList<GameBetBalls>();
		List<BetDetailStrucDTO> betBalls = dto.getBalls();
		GameBetJsonDateStruc jds = new GameBetJsonDateStruc();
		GameBetJsonResultStruc hr = new GameBetJsonResultStruc();
		GameBetTplData tplDate = new GameBetTplData();
		String strStatus = status + "";
		if (strStatus.length() == 12) {
			//投注方式暂停异常处理需要截取活的投注方式名称
			Integer gameGroupCode = Integer.valueOf(strStatus.substring(0, 2));
			Integer gameBetCode = Integer.valueOf(strStatus.substring(2, 4));
			Integer gameMethodCode = Integer.valueOf(strStatus.substring(4, 6));
			String betName = GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 0)
					+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 1)
					+ GameAwardNameUtil.getTitle(lotteryId, gameGroupCode, gameBetCode, gameMethodCode, 2);
			String betEnName = GameAwardNameUtil.getFullName(lotteryId, gameGroupCode, gameBetCode, gameMethodCode);
			tplDate.setMsg(getMessage("gameMethodPaused", new String[] { betName }));
			jds.setTplData(tplDate);
			hr.setIsSuccess(0);
			hr.setType("pauseBet");
			hr.setMsg(getMessage("gameMethodPaused", new String[] { betName }));
			for (BetDetailStrucDTO ball : betBalls) {
				if (ball.getType().equals(betEnName)) {
					GameBetBalls gb = new GameBetBalls();
					gb.setBall(ball.getBall());
					gb.setId(Long.valueOf(ball.getId()));
					gb.setType(ball.getType());
					balls.add(gb);
				}
			}
			tplDate.setBalls(balls);
			hr.setData(jds);
			return hr;
		} else {
			return handingBetStatus(status);
		}
	}

	private Long getDiamondLv(String numberRecord){
		char diamond = numberRecord.charAt(0);
		Long count = 0l;
		for(int i = 1; i<numberRecord.length(); i++){
			if(diamond == numberRecord.charAt(i)){
				count++;
			}
		}
		return count;
	}
	
	public String formatOrderNumber(String numberRecord) {
		if (numberRecord != null) {
			String[] nums = numberRecord.split("");
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < nums.length; i++) {
				if (i != nums.length - 1) {
					sb.append(nums[i]).append(",");
				} else {
					sb.append(nums[i]);
				}
			}
			return sb.toString();
		}
		return null;
	}

	protected List<BaseChartStruc> getTrendChartResultList(String types) throws Exception {
		//生成Request
		QueryAssistActionRequest queryAssistActionRequest = new QueryAssistActionRequest();
		queryAssistActionRequest.setLotteryid(this.lotteryId);

		String[] type = types.split("\\.");
		Integer gameGroupCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 0);
		Integer gameSetCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 1);
		Integer betMethodCode = GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 2);

		queryAssistActionRequest.setGameGroupCode(gameGroupCode);
		queryAssistActionRequest.setGameSetCode(gameSetCode);
		queryAssistActionRequest.setBetMethodCode(betMethodCode);

		//连接服务查询
		Response<QueryAssistActionResponse> response = betHttpClient.getQueryAssistAction(queryAssistActionRequest);

		List<BaseChartStruc> list = response.getBody().getResult().getList();
		return list;
	}

	@Override
	public HistoryBallsResultDTO getTrendChart(String type) throws Exception {
		type = type.replace("_2000", "");
		Chart chart = trendCharts.get(lotteryCode).get(type);
		if (chart != null) {

			//生成Request
			QueryAssistActionRequest queryAssistActionRequest = new QueryAssistActionRequest();
			queryAssistActionRequest.setLotteryid(this.lotteryId);

			String[] types = type.split("\\.");
			Integer gameGroupCode = GameAwardNameUtil.getCode(lotteryId, types[0], types[1], types[2], 0);
			Integer gameSetCode = GameAwardNameUtil.getCode(lotteryId, types[0], types[1], types[2], 1);
			Integer betMethodCode = GameAwardNameUtil.getCode(lotteryId, types[0], types[1], types[2], 2);

			queryAssistActionRequest.setGameGroupCode(gameGroupCode);
			queryAssistActionRequest.setGameSetCode(gameSetCode);
			queryAssistActionRequest.setBetMethodCode(betMethodCode);
			int groupCode = queryAssistActionRequest.getGameGroupCode()==51?33: queryAssistActionRequest.getGameGroupCode()==50?16:queryAssistActionRequest.getGameGroupCode()==49?
					15:queryAssistActionRequest.getGameGroupCode()==48?14:queryAssistActionRequest.getGameGroupCode()==47?13:queryAssistActionRequest.getGameGroupCode()==46?12:
						queryAssistActionRequest.getGameGroupCode()==45?11:queryAssistActionRequest.getGameGroupCode()==44?10:queryAssistActionRequest.getGameGroupCode();
			queryAssistActionRequest.setGameGroupCode(groupCode);

			//连接服务查询
			Response<QueryAssistActionResponse> response = betHttpClient.getQueryAssistAction(queryAssistActionRequest);

			List<BaseChartStruc> baseChartStrucs = response.getBody().getResult().getList();

			HistoryBallsResultDTO result = new HistoryBallsResultDTO();
			HistoryBallsDTO historyBallsDTO = new HistoryBallsDTO();
			historyBallsDTO.setHistoryBalls(chart.format(baseChartStrucs));
			historyBallsDTO.setGameTips(new GameTips());
			historyBallsDTO.setFrequency(new ArrayList<List<NumberFrequencyCell>>());

			result.setData(historyBallsDTO);
			result.setIsSuccess(1);
			result.setMsg("success");
			result.setType("userError");

			return result;
		}

		return null;

	}

	/** 
	* @Title: orderBet 
	* @Description:普通投注
	* @param dto
	* @param request
	* @return
	* @throws Exception
	*/
	private Response<GameOrderResponse> orderBet(GameOrderRequestDTO dto, HttpServletRequest request) throws Exception {

		GameOrderRequest gr = new GameOrderRequest();
		gr.setLotteryid(lotteryId);
		gr.setAwardGroupId(dto.getAwardGroupId());
		try {
			gr.setUserIp(IPConverter.ipToLong(IPUtil.getClientIpAddr(request)));
		} catch (Exception e) {
			logger.error("取得用户IP时发生错误:" + e.getMessage(), e);
		}
		gr.setPackageAmount(formatMultipleMoney(dto.getAmount()));
		gr.setSaleTime(new Date().getTime());
		gr.setServerIp(IPConverter.ipToLong(getProperty("serverIP")));
		//判斷是WEB還是WAP
		gr.setChannelId(ChannelType.getChannelId(request.getHeader("USER-AGENT")));
		gr.setChannelVersion(platVersion);
		gr.setActivityType(dto.getActivityType());
		gr.setIsFirstSubmit(dto.getIsFirstSubmit());
		if (dto.getOrders() != null && !dto.getOrders().isEmpty()) {
			gr.setIssueCode(dto.getOrders().get(0).getIssueCode());
		}

		List<BetDetailStruc> bdss = new ArrayList<BetDetailStruc>();

		Long totalDetailMoney = 0L;

		for (BetDetailStrucDTO bd : dto.getBalls()) {
			BetDetailStruc bds = new BetDetailStruc();

			bds.setBetdetail(bd.getBall());
			bds.setFileMode(isFileMode(bd.getType()));
			String[] type = StringUtils.split(bd.getType(), '.');
			bds.setGameGroupCode(GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 0));
			bds.setGameSetCode(GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 1));
			if (GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 2) != null) {
				bds.setBetMethodCode(GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 2));
			}
			bds.setIssueCode(gr.getIssueCode());
			bds.setMoneyMode(bd.getMoneyunit() == 0.01 ?3:(bd.getMoneyunit() == 0.1 ? 2 : 1));//页面传过来的值时0.1 为角模式，对应接口数据1元2角
			bds.setMultiple(bd.getMultiple());
			bds.setItemAmount(getItemAmountValue(bd));//元角模式单注金额
			bds.setTotbets(getTotalBet(bd));//注数
			bds.setTotamount(getTotalAmountValue(bd));
			bds.setAwardMode(bd.getAwardMode());
			totalDetailMoney += bds.getTotamount();
			if(lotteryId == 99112){
				if(bd.getHasdiamondSubmit()){
					gr.setActivityType(2l);
					bds.setDiamondAmount(getDiamondAmountValue(bd));
					totalDetailMoney += bds.getDiamondAmount();
				}
			}
			//变价列表
			LockPointRequestDTO lockPointDTO = bd.getLockPoint();
			if (lockPointDTO != null) {
				List<PointsRequestDTO> points = lockPointDTO.getPointsList();
				if (points != null && !points.isEmpty()) {
					bds.setPointsList(points);
				}
			}
			
			if(lotteryId == 99701){
				bds.setOdds(bd.getOdds());
			}

			bdss.add(bds);
		}
		gr.setBetDetailStruc(bdss);
		long status = 0l;
		Response<GameOrderResponse> gameOrderResponse = new Response<GameOrderResponse>();
		if (totalDetailMoney.longValue() != gr.getPackageAmount()) {
			status = 202001l;//投注金额错误
			RequestHeader requestH = new RequestHeader();
			ResponseHeader rh = ResponseHeader.createReponseHeader(requestH);
			rh.setStatus(status);
			gameOrderResponse.setHead(rh);
			return gameOrderResponse;
		} else {
			//验证
			GameOrder gameOrder = DTOConvert
					.convertGameOrderRequest2GameOrder(gr, RequestContext.getCurrUser().getId());
			//設定獎金組ID
			gameOrder.setAwardGroupId(dto.getAwardGroupId());
			status = validate(gameOrder);
			if (status > -1l) {
				RequestHeader requestH = new RequestHeader();
				ResponseHeader rh = ResponseHeader.createReponseHeader(requestH);
				rh.setStatus(status);
				gameOrderResponse.setHead(rh);
				return gameOrderResponse;
			}
		}

		// 替换userid
		Long userId =RequestContext.getCurrUser().getId();
		String account = RequestContext.getCurrUser().getUserName();
		gameOrderResponse = betHttpClient.saveGameOrder(gr, userId, account);

		return gameOrderResponse;
	}

	protected int getItemAmountValue(BetDetailStrucDTO bd) {
		return ((Double) (2 * getLongProperty("moneyMultiple") * bd.getMoneyunit())).intValue();
	}

	protected Long getTotalAmountValue(BetDetailStrucDTO bd) {
		Long amountValue = 0l;
		if(lotteryId == 99701) {
			amountValue = new BigDecimal(bd.getAmount()).multiply(new BigDecimal(getLongProperty("moneyMultiple")))
					.multiply(new BigDecimal(bd.getMoneyunit())).multiply(new BigDecimal(bd.getNum())).longValue();
		} else {
			amountValue = new BigDecimal(2).multiply(new BigDecimal(getLongProperty("moneyMultiple")))
					.multiply(new BigDecimal(bd.getMoneyunit())).multiply(new BigDecimal(bd.getNum()))
					.multiply(new BigDecimal(bd.getMultiple())).longValue();
		}
		return amountValue;
	}
	
	protected Long getDiamondAmountValue(BetDetailStrucDTO bd) {
		return ((long) ((Double) (getLongProperty("moneyMultiple") * bd.getMoneyunit())).intValue() * bd.getNum() * bd
				.getMultiple());
	}

	protected int getTotalBet(BetDetailStrucDTO bd) {
		return bd.getNum();
	}

	/** 
	 * @Title: planBet 
	 * @Description: 追号投注 
	 * @param dto
	 * @param request
	 * @param gameIssue 
	 * @return
	 * @throws Exception
	 */
	private Response<GamePlanResponse> planBet(GameOrderRequestDTO dto, HttpServletRequest request,
			GameIssueQueryResponse gameIssue) throws Exception {

		GamePlanRequest gp = new GamePlanRequest();
		gp.setCurrentIssueCode(gameIssue.getIssueCode());
		gp.setLotteryid(lotteryId);
		gp.setPlanAmount(formatMultipleMoney(dto.getAmount()));
		gp.setSaleTime(new Date().getTime());
		gp.setServerip(IPConverter.ipToLong(getProperty("serverIP")));
		gp.setStartIssueCode(dto.getOrders().get(0).getIssueCode());
		gp.setIsFirstSubmit(dto.getIsFirstSubmit());
		//判斷是WEB還是WAP
		gp.setChannelId(ChannelType.getChannelId(request.getHeader("USER-AGENT")));
		gp.setChannelVersion(platVersion);
		gp.setActivityType(dto.getActivityType());
		if (dto.getTraceWinStop() == 1) {//中奖即停
			gp.setStopMode(2);
			gp.setOptionParms("");
		} else if (dto.getTraceWinStop() == 2) {//盈利停止
			gp.setStopMode(1);
			gp.setOptionParms("");//数据库中此字段的值暂时未用到
		} else {//默认为中奖不停止
			gp.setStopMode(0);
			gp.setOptionParms("");
		}
		gp.setStopParms(formatMultipleMoney(dto.getTraceStopValue()));//金额需要乘以10000保存到数据库
		gp.setTotalIssue((long) dto.getOrders().size());
		try {
			gp.setUserip(IPConverter.ipToLong(IPUtil.getClientIpAddr(request)));
		} catch (Exception e) {
		}
		Long totalDetailMoney = 0L;
		List<GamePlanBetOriginDataStruc> gamePlanBetOriginDataStruc = new ArrayList<GamePlanBetOriginDataStruc>();//投注追号原始投注数据结构，解决追号投注数据重复问题
		for (int i = 0; i < dto.getBalls().size(); i++) {
			if (dto.getBalls().get(i).getMultiple() == 0) {
				continue;
			}
			GamePlanBetOriginDataStruc ods = new GamePlanBetOriginDataStruc();
			ods.setBetdetail(dto.getBalls().get(i).getBall());
			String betMethodType = dto.getBalls().get(i).getType();
			String[] type = StringUtils.split(betMethodType, '.');
			ods.setFileMode(isFileMode(betMethodType));
			ods.setGameGroupCode(GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 0));
			ods.setGameSetCode(GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 1));
			ods.setBetMethodCode(GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 2));
			ods.setMoneyMode(dto.getBalls().get(i).getMoneyunit() == 0.01?3:(dto.getBalls().get(i).getMoneyunit() == 0.1 ? 2 : 1));//页面传过来的值时0.1 为角模式，对应接口数据1元2角
			ods.setMultiple(1);//追號第一注永遠為1
			BetDetailStrucDTO betDto = new BetDetailStrucDTO();
			betDto.setMoneyunit(dto.getBalls().get(i).getMoneyunit());
			betDto.setNum(dto.getBalls().get(i).getNum());
			betDto.setMultiple(ods.getMultiple());
			betDto.setAmount(dto.getBalls().get(i).getAmount());
			ods.setItemAmount(getItemAmountValue(betDto));//元角模式单注金额
			ods.setTotbets(getTotalBet(betDto));//注数
			ods.setTotamount(getTotalAmountValue(betDto));
			ods.setIssueCode(dto.getBalls().get(i).getIssueCode());
			ods.setAwardMode(dto.getBalls().get(i).getAwardMode());
			gamePlanBetOriginDataStruc.add(ods);
		}

		List<BetDetailStruc> bdss = new ArrayList<BetDetailStruc>();
		for (int i = 0; i < dto.getBalls().size(); i++) {
			for (int j = 0; j < dto.getOrders().size(); j++) {
				if (dto.getBalls().get(i).getMultiple() == 0
						|| (dto.getBalls().get(i).getLockPoint() != null && !dto.getOrders().get(j).getIssueCode()
								.equals(dto.getBalls().get(i).getIssueCode()))) {
					continue;//当传递过来的倍数为0时 丢掉这个投注内容，同时保证正常数据投注成功，对于攻击数据实现扣款，对于封锁数据我们这里做一个过滤去重
				}
				BetDetailStruc bds = new BetDetailStruc();
				bds.setBetdetail(dto.getBalls().get(i).getBall());
				String betMethodType = dto.getBalls().get(i).getType();
				String[] type = StringUtils.split(betMethodType, '.');
				bds.setFileMode(isFileMode(betMethodType));
				bds.setGameGroupCode(GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 0));
				bds.setGameSetCode(GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 1));
				bds.setBetMethodCode(GameAwardNameUtil.getCode(lotteryId, type[0], type[1], type[2], 2));
				bds.setIssueCode(dto.getOrders().get(j).getIssueCode());
				bds.setMoneyMode(dto.getBalls().get(i).getMoneyunit() == 0.01?3:(dto.getBalls().get(i).getMoneyunit() == 0.1 ? 2 : 1));//页面传过来的值时0.1 为角模式，对应接口数据1元2角
				bds.setMultiple(dto.getOrders().get(j).getMultiple() == 1 ? dto.getBalls().get(i).getMultiple() : dto
						.getOrders().get(j).getMultiple());
				BetDetailStrucDTO betDto = new BetDetailStrucDTO();
				betDto.setMoneyunit(dto.getBalls().get(i).getMoneyunit());
				betDto.setNum(dto.getBalls().get(i).getNum());
				betDto.setMultiple(bds.getMultiple());
				betDto.setAmount(dto.getBalls().get(i).getAmount());
				bds.setItemAmount(getItemAmountValue(betDto));//元角模式单注金额
				bds.setTotbets(getTotalBet(betDto));//注数
				bds.setTotamount(getTotalAmountValue(betDto));
				bds.setAwardMode(dto.getBalls().get(i).getAwardMode());
				totalDetailMoney += bds.getTotamount();

				//变价列表
				LockPointRequestDTO lockPointDTO = dto.getBalls().get(i).getLockPoint();
				if (lockPointDTO != null) {
					List<PointsRequestDTO> points = lockPointDTO.getPointsList();
					if (points != null && !points.isEmpty()) {
						bds.setPointsList(points);
					}
				}

				bdss.add(bds);
			}
		}

		gp.setBetDetailsStruc(bdss);
		gp.setBetOriginDataStruc(gamePlanBetOriginDataStruc);

		long status = 0l;
		Response<GamePlanResponse> gamePlanResponse = new Response<GamePlanResponse>();
		if (totalDetailMoney.longValue() != gp.getPlanAmount()) {
			status = 202001l;//投注金额错误
			gamePlanResponse.getHead().setStatus(status);
			return gamePlanResponse;
		} else {

			// 验证
			GameOrder gameOrder = DTOConvert.convertGamePlanRequest2GameOrder(gp, RequestContext.getCurrUser().getId());
			status = validate(gameOrder);
			if (status > -1l) {
				gamePlanResponse.getHead().setStatus(status);
				return gamePlanResponse;
			}
		}

		// 替换userid
		Long userId = RequestContext.getCurrUser().getId();
		String account = RequestContext.getCurrUser().getUserName();

		gamePlanResponse = betHttpClient.saveGamePlan(gp, userId, account);
		return gamePlanResponse;
	}

	/**
	 * 获取可追号的奖期列表
	 * 
	 * @param traceMaxTimes
	 * @return
	 * @throws Exception
	 */
	protected List<GameNumbers> getGameNumbers(int traceMaxTimes) throws Exception {

		TraceGameIssueListQueryRequest traceGameIssueListQueryRequest = new TraceGameIssueListQueryRequest();
		traceGameIssueListQueryRequest.setLotteryId(lotteryId);
		traceGameIssueListQueryRequest.setMaxCountIssue(traceMaxTimes);

		Response<TraceGameIssueListQueryResponse> traceGameIssueListQueryResponse = betHttpClient
				.getTraceGameIssues(traceGameIssueListQueryRequest);

		List<GameNumbers> gamenumbers = new ArrayList<GameNumbers>();
		for (PreviewIssueStruc is : traceGameIssueListQueryResponse.getBody().getResult().getIssueStrucs()) {
			GameNumbers gn = new GameNumbers();
			gn.setNumber(is.getWebIssueCode());
			gn.setIssueCode(is.getIssueCode());
			gn.setTime(DateUtils.format(DataConverterUtil.convertLong2Date(is.getSaleEndTime()),
					getProperty("jsViewDataFormat")));
			gamenumbers.add(gn);
		}

		return gamenumbers;
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
						if(bs.getMultiples()!=null&&bs.getMultiples().length>0){
							gl.setMaxmultiples(bs.getMultiples());
						}else{
							gl.setMaxmultiple(bs.getMultiple().longValue());
						}
						if(bs.getSpecialMultiple() != null
								&& bs.getGameGroupCode() == 18
								&& bs.getGameSetCode() == 13
								&& bs.getBetMethodCode() == 65){
							gl.setSpecialMultiple(bs.getSpecialMultiple().split(","));
						}
					}
				}
			} else {
				gl.setMaxmultiple(-1L);//null值为无限制
			}
			gl.setUsergroupmoney(null);// 此数据已不在这里获取了，现在随意给个null值  此行不能注销，前台js需要
			map.put(GameAwardNameUtil.getFullName(lotteryId, gameGroupCode, gameSetCode, betMethodCode), gl);
		}

		gameLimits.add(map);

		return gameLimits;
	}

	/**
	 * 获取当前奖期
	 * 
	 * @return
	 * @throws Exception
	 */
	protected GameIssueQueryResponse getCurrentGameIssue() throws Exception {

		GameIssueQueryRequest gameIssueQueryRequest = new GameIssueQueryRequest();
		gameIssueQueryRequest.setLotteryId(lotteryId);

		Response<GameIssueQueryResponse> gameIssueQueryResponse = betHttpClient
				.getCurrentGameIssue(gameIssueQueryRequest);

		return gameIssueQueryResponse.getBody().getResult();
	}

	/** 
	* @Title: betGameIssueError 
	* @Description:判断投注时选中的奖期不能小于当前期，否则给用户提示异常信息
	* @param gameIssue
	* @param webIssueCode
	* @return
	*/
	protected GameBetJsonResultStruc betGameIssueError(GameIssueQueryResponse gameIssue, String webIssueCode) {

		GameBetBitDate bitDate = new GameBetBitDate();
		bitDate.setCurrent(gameIssue.getWebIssueCode());
		bitDate.setExpiredDate(webIssueCode);

		GameBetTplData tplDate = new GameBetTplData();
		tplDate.setBitDate(bitDate);
		tplDate.setMsg(getMessage("gameIssueError", new String[] { webIssueCode, gameIssue.getWebIssueCode() }));

		GameBetJsonDateStruc jds = new GameBetJsonDateStruc();
		jds.setTplData(tplDate);

		GameBetJsonResultStruc hr = new GameBetJsonResultStruc();
		hr.setIsSuccess(0);
		hr.setType("betExpired");
		hr.setMsg(getMessage("gameIssueError", new String[] { webIssueCode, gameIssue.getWebIssueCode() }));
		hr.setData(jds);

		return hr;
	}

	/**
	 * 根据配置的玩法判断是否为单式文件模式
	 * 
	 * @param betMethodType
	 * @return
	 */
	protected int isFileMode(String betMethodType) {
		String fileModeMethodTypes = getProperty("fileModeMethodTypes");
		String[] methodTypes = StringUtils.split(fileModeMethodTypes, ',');
		for (String methodType : methodTypes) {
			if (betMethodType.contains(methodType)) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 投注格式、 注数、金额验证
	 * 
	 * @param gameOrder
	 * @return
	 * @throws Exception
	 */
	protected Long validate(GameOrder gameOrder) throws Exception { 
		long status = -1l;
		UserInfoRequest userInfoReq=new UserInfoRequest();
		userInfoReq.setUserId(RequestContext.getCurrUser().getId());
		Response<UserInfoResponse> userInfoRes = betHttpClient.getUserInfo(userInfoReq);
		Integer awardRetStatus=0;
		Integer superPairStatus=0;
		if(null!=userInfoRes && null!=userInfoRes.getBody().getResult()){
			awardRetStatus=userInfoRes.getBody().getResult().getAwardRetStatus();
			superPairStatus=userInfoRes.getBody().getResult().getSuperPairStatus();
			awardRetStatus=null==awardRetStatus?0:awardRetStatus;
			superPairStatus=null==superPairStatus?0:superPairStatus;
		}
		try {
			//若為六合彩 去game api拿validate 需要的設定檔
			fillLhcConfigs(gameOrder);
			
			for (GameSlip slip : gameOrder.getSlipList()) {
				IKeyGenerator keyGen = new LotteryPlayMethodKeyGenerator(lotteryId, slip.getGameBetType()
						.getGameGroupCode(), slip.getGameBetType().getGameSetCode(), slip.getGameBetType()
						.getBetMethodCode(), getProperty("keySeperator"));
				IValidateExecutorContext context = new BetValidateContext(gameOrder, slip);
				IValidateExecutor<GameSlip> validatorExecutor = validatorFactory.getValidateExecutor(lotteryCode,
						keyGen);
				validatorExecutor.execute(slip, context);
				
				//没有奖金模式权限的 如果投注内容含有奖金模式则抛异常
				if(0==awardRetStatus && null!=slip.getAwardMode() && 2==slip.getAwardMode()){
					throw new GameBetContentPatternErrorException();
				}
				if(SuperPairUtil.isSuperPair(slip.getGameBetType().getGameGroupCode())){
					if(0==superPairStatus){
						throw new GameBetContentPatternErrorException();
					}
					if(slip.getAwardMode()!=null&&slip.getAwardMode()==2){
						throw new GameBetContentPatternErrorException();
					}
				}
				if(lotteryId == 99112L){
					//判斷玩法是否支援鑽石加獎
					if(slip.getDiamondAmount() > 0l){
						Integer groupCode = slip.getGameBetType().getGameGroupCode();
						if(groupCode == 10 || groupCode == 12 || groupCode == 15 || groupCodeList2000.contains(groupCode+"")){
							throw new GameBetContentPatternErrorException();
						}
					}
				}
			}
		} catch (GameBetContentPatternErrorException e) {
			logger.error("投注格式错误：", e);
			status = e.getStatus();
		} catch (GameBetAmountErrorException e) {
			logger.error("投注金额错误：", e);
			status = e.getStatus();
		} catch (GameTotbetsErrorException e) {
			logger.error("投注注数错误：", e);
			status = e.getStatus();
		} catch (Exception e) {
			logger.error("下注操作失败：", e);
			throw e;
		}

		return status;
	}

	/**
	 * 補上六合彩validator所需要的設定檔
	 * @param gameOrder
	 * @throws Exception
	 */
	private void fillLhcConfigs(GameOrder gameOrder) throws Exception {
		if(lotteryId.equals(99701L)){
			Long awardGroupId = gameOrder.getAwardGroupId();
			List<GameAward> gameAwards = lhcRedisUtil.findGameAwardByAwardGroupId(awardGroupId);
			List<GameNumberConfig> gameNumberConfigs = lhcRedisUtil.findThisYearNumberConfig(new Date());
			for (GameSlip slip : gameOrder.getSlipList()) {
				slip.setGameAwards(gameAwards);
				slip.setGameNumberConfigs(gameNumberConfigs);
			}
		}
	}

	private Long formatLong(Long aaa) {
		if (aaa == null)
			return null;
		return NumberUtils.toLong(String.valueOf(aaa / 100) + "00");
	}

	private List<LhcGameOdds> getLhcGameOdds() {
//		LhcGameOdds odds = new LhcGameOdds();
		
		List<LhcGameOddsDetail> oddsChild = new ArrayList<LhcGameOddsDetail>();
		List<LhcGameOdds> oddsList = new ArrayList<LhcGameOdds>();
		try {
			Response<GameLhcOddsResponse> rs = betHttpClient.getGameOdds();
			List<GameLhcOdds> gameLhcOddsResponsesList = rs.getBody().getResult().getGameLhcOdds();
			Map<String, List<LhcGameOddsDetail>> map = new HashMap<String, List<LhcGameOddsDetail>>();
			for(GameLhcOdds v: gameLhcOddsResponsesList){
				LhcGameOddsDetail oddsDetail = new LhcGameOddsDetail();
				oddsDetail.setName(v.getMethodCodeName());
				oddsDetail.setOdds(new Double(v.getActualBonus())/10000);
				oddsDetail.setLhcCode(v.getLhcCode());
				if(v.getLhcCode()!=null && v.getLhcCode()!=""){
					if(v.getLhcCode().equals(GameLHCBetType.SAISHAI_ONYEAR.getType()) ||v.getLhcCode().equals(GameLHCBetType.SAPO_RED.getType())){
						oddsDetail.setSpecialFlag("Y");
					}else{
						oddsDetail.setSpecialFlag("N");
					}
				}

				oddsChild.add(oddsDetail);
				
				if(map.containsKey(v.getSetCodeName())){
					map.get(v.getSetCodeName()).add(oddsDetail);
				} else {
					List<LhcGameOddsDetail> tmpOddsChild = new ArrayList<LhcGameOddsDetail>();
					tmpOddsChild.add(oddsDetail);
					map.put(v.getSetCodeName(), tmpOddsChild);
				}
				
//				odds.setName(v.getSetCodeName());
			}
//			odds.setChilds(oddsChild);
//			oddsList.add(odds);
			
			for(String key : map.keySet()){
				LhcGameOdds odds = new LhcGameOdds();
				odds.setName(key);
				odds.setChilds(map.get(key));
				oddsList.add(odds);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return oddsList;
	}
	
	private List<LhcGameZodiac> getlhcGameZodiac() {
		List<LhcGameZodiac> gameZodiacList = new ArrayList<LhcGameZodiac>();
		try	{
			Response<GameLhcZodiacResponse> rs = betHttpClient.getGameZodiac();
			gameZodiacList = rs.getBody().getResult().getGameZodiac();
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return gameZodiacList;
	}
	
	private List<LhcGameTips> getLhcGameTips() {
		List<LhcGameTips> gameTipsList = new ArrayList<LhcGameTips>();
		try {
			Response<GameLhcTipsResponse> rs = betHttpClient.getGameTips();
			gameTipsList = rs.getBody().getResult().getGameTips();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		
		return gameTipsList;
	}
	
	/**
	 * 格式化上期开奖号码
	 * 不同彩种开奖号码格式不同，故不同彩种不同实现
	 * 
	 * @param gameIssue 奖期
	 * @return
	 */
	protected abstract String formatLastBalls(GameIssueQueryResponse gameIssue);

	/**
	 * 处理投注状态
	 * 
	 * @param status
	 * @return
	 */
	protected GameBetJsonResultStruc handingBetStatus(Long status) {
		return errorReturn(status);
	}
	
	/**
	 * 12生肖取投注页面“我的方案”列表
	 * 
	 * @return OrdersStrucDTO 20筆投注紀錄
	 * @throws Exception
	 */
	@Override
	public List<OrdersStrucDTO> getShierShengXiaoUserOrders(String activity) throws Exception {

		logger.debug("getShierShengXiaoUserOrders start");

		Long userId = RequestContext.getCurrUser().getId();

		GameOrderQueryRequest gameOrderRequest = new GameOrderQueryRequest();
		gameOrderRequest.setLotteryId(lotteryId);
		//只取最近7天的数据
		Date endTime = DateUtils.currentDate();
		Date startTime = DateUtils.addDays(endTime, -6);

		gameOrderRequest.setStartTime(DataConverterUtil.convertDate2Long(startTime));
		gameOrderRequest.setEndTime(DataConverterUtil.convertDate2Long(endTime));
		Pager pager = new Pager();
		pager.setStartNo(0);
		pager.setEndNo(19);

		Response<GameOrderQueryResponse> orderResponse = betHttpClient.getGameOrders(gameOrderRequest, userId, pager);
		logger.debug("getShierShengXiaoUserOrders end");

		if (orderResponse.getBody().getResult() != null) {
			List<OrdersStrucDTO> sds = DTOConvertor4Web.orderStrucs2OrderStrucsDTO(orderResponse.getBody()
					.getResult().getOrdersStruc());
			if (!sds.isEmpty()) {
				for (OrdersStrucDTO sd : sds) {
					sd.setNumberRecord(formatOrderNumber(sd.getNumberRecord()));
					if (sd.getStatus() == 4) {
						sd.setStatusName("开奖失败(已撤销)");
					}
				}
			}
			return sds;
		}
		return null;
	}

	@Override
	public String getLotteryCode() {
		return this.lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public void setView(String view) {
		this.view = view;
	}

	public void setBnConvertor(IBetNameConvertor bnConvertor) {
		this.bnConvertor = bnConvertor;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public void setDefaultMethod(String defaultMethod) {
		this.defaultMethod = defaultMethod;
	}

	public void setLotterySeries(String lotterySeries) {
		this.lotterySeries = lotterySeries;
	}

	public void setTrendCharts(Map<String, Map<String, Chart>> trendCharts) {
		this.trendCharts = trendCharts;
	}

	public IBetNameConvertor getBetNameConvertor() {
		return this.bnConvertor;
	}

	public void setVideoSourceList(List<String> videoSourceList) {
		this.videoSourceList = videoSourceList;
	}

	public void setHelpLink(String helpLink) {
		this.helpLink = helpLink;
	}

	public String getHelpLink() {
		return helpLink;
	}
	
	public WinUserListResponse getWinningList(String orderIds) throws Exception{
		return null;
	}
	
	public WinUserListResponse getPlayerBetList() throws Exception{
		return  null;
	}
}