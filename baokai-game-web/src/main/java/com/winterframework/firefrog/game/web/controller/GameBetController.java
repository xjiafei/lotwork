package com.winterframework.firefrog.game.web.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.web.bet.operator.IBetOperation;
import com.winterframework.firefrog.game.web.bet.operator.impl.AbstractBetOperator;
import com.winterframework.firefrog.game.web.bet.util.BetHttpJsonClient;
import com.winterframework.firefrog.game.web.controller.view.ViewLock;
import com.winterframework.firefrog.game.web.dto.AjaxResponse;
import com.winterframework.firefrog.game.web.dto.BetContentStruc;
import com.winterframework.firefrog.game.web.dto.BetDetailStrucDTO;
import com.winterframework.firefrog.game.web.dto.BetDownloadStruc;
import com.winterframework.firefrog.game.web.dto.BlockadeResponseDTO;
import com.winterframework.firefrog.game.web.dto.BlockadeStrutResponseDTO;
import com.winterframework.firefrog.game.web.dto.DTOConvertor4Web;
import com.winterframework.firefrog.game.web.dto.DynamicConfig;
import com.winterframework.firefrog.game.web.dto.GameBetJsonDateStruc;
import com.winterframework.firefrog.game.web.dto.GameBetJsonResultStruc;
import com.winterframework.firefrog.game.web.dto.GameBetTplData;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponseDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderDownloadStruc;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderWinsDetailDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanParm;
import com.winterframework.firefrog.game.web.dto.GetAwardResultDTO;
import com.winterframework.firefrog.game.web.dto.HistoryBallsResultDTO;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardRequest;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardResponse;
import com.winterframework.firefrog.game.web.dto.LockPointRequestDTO;
import com.winterframework.firefrog.game.web.dto.NumberFrequencyCell;
import com.winterframework.firefrog.game.web.dto.OrdersStrucDTO;
import com.winterframework.firefrog.game.web.dto.PlansStrucForUI;
import com.winterframework.firefrog.game.web.dto.PointsRequestDTO;
import com.winterframework.firefrog.game.web.dto.PointsStrucRequestDTO;
import com.winterframework.firefrog.game.web.dto.SlipsStrucDTO;
import com.winterframework.firefrog.game.web.dto.StraightOdds;
import com.winterframework.firefrog.game.web.dto.UserInfoRequest;
import com.winterframework.firefrog.game.web.dto.UserInfoResponse;
import com.winterframework.firefrog.game.web.dto.WinUserListResponse;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.session.interceptor.vo.UserStrucResponse;
import com.winterframework.firefrog.user.entity.IndexLottery;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/**
 * @Title 投注控制器
 * @Description 
 * 	
 * 	1. 加载投注页面对象及显示投注页面
 * 	2. 加载页面渲染所需配置或数据
 * 	3. 单式上传文件接收及解析
 * 	4. 显示撤单费用
 * 	5. 投注提交
 * 
 * 	此控制器只定义通用方法，如彩系彩种需要一些特殊的方法，可重载此控制器。
 * 
 * @author bob
 *
 */
@Controller("GameBetController")
@RequestMapping(value = "/gameBet")
public class GameBetController {
	private Logger logger = LoggerFactory.getLogger(GameBetController.class);

	@Resource(name = "gameBetOperators")
	private List<IBetOperation> gameBetOperators;

	@Resource(name = "betHttpClient")
	protected BetHttpJsonClient betHttpClient;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.queryOrderDetail")
	private String queryOrderDetail;

	@PropertyConfig(value = "url.game.queryGameIssue")
	private String queryGameIssue;
	
	/**forward to gameApi URL:歷史開獎資料*/
	@PropertyConfig(value = "url.game.historyLotteryAward")
	private String historyLotteryAward;
	
	@Resource(name = "RedisClient")
	private RedisClient redis;
	
	/**
	 * 投注页面入口 
	 * @param lotteryCode 彩種代碼
	 * @param select2000 是否為超級12生肖；1 = 是
	 * @param activityType
	 * @param model
	 * @param request
	 * @param isPass 是否跳過移動裝置的邏輯判斷
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}")
	public String betView(@PathVariable String lotteryCode,String select2000,String activityType ,Model model,HttpServletRequest request,@RequestParam(value = "isPass", required = false) Boolean isPass) throws Exception {
		logger.info("enter lottery bet view...");
		model.addAttribute("select2000", select2000);
		String path = getBetOperator(lotteryCode).show(model);
		//From WapInterceptor(gameWeb)
		String isMoblie = (String) request.getAttribute("IsMobile");
		//判斷是否是手機還是web
		if(isPass == null){
			if(isMoblie != null && isMoblie == "yes"){
				//目前只有cqssc、xjssc、tjssc、hljssc、shssl、slmmc 開放其他時時彩  需要修改
				if(lotteryCode.equals("cqssc")||lotteryCode.equals("xjssc")||
						lotteryCode.equals("tjssc")||lotteryCode.equals("hljssc")||
						lotteryCode.equals("shssl")||lotteryCode.equals("slmmc")){
					// for view/bet/layouts/betMain.jsp 判斷用
					model.addAttribute("checkMobile", true);
					path = "/wap" + path;
				}
				//12生肖WAP版
				if((lotteryCode.equals("slmmc")&& "1".equals(activityType))){
					model.addAttribute("checkMobile", true);
					model.addAttribute("shierShengXiao", true);
					path = "/wap/bet/shierShengXiao/index";
				}
			}
		}
		return path;
	}
	
	/**
	 * 加载玩法群、玩法组、投注方式列表,以及彩种基本配置信息
	 * @param lotteryCode 彩种代码
	 * @param select2000 是否為超級2000；1 = 是
	 * @param model 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/{lotteryCode}/config", produces = "text/javascript; charset=UTF-8")
	public String config(@PathVariable String lotteryCode,String select2000, Model model) throws Exception {
		logger.info("get lottery bet config...");
		return getBetOperator(lotteryCode).getLotteryConfig(model, select2000);
	}

	/**
	 * 查询玩法描述和默认冷热球及用户投注方式奖金
	 * @param lotteryCode
	 * @param type
	 * @param extent
	 * @param line
	 * @param lenth
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/{lotteryCode}/getBetAward")
	public GetAwardResultDTO getBetAward(@PathVariable String lotteryCode, @RequestParam("type") String type,
			@RequestParam("extent") String extent, @RequestParam("line") Integer line,
			@RequestParam("lenth") Integer lenth) throws Exception {
		return getBetOperator(lotteryCode).getBetAward(type, extent, line, lenth);
	}

	@ResponseBody
	@RequestMapping(value = "/{lotteryCode}/frequency")
	public List<List<NumberFrequencyCell>> frequency(@PathVariable String lotteryCode,
			@RequestParam("gameMode") String gameMode, @RequestParam("extent") String extent,
			@RequestParam("frequencyType") String frequencyType, @RequestParam("line") Integer line,
			@RequestParam("lenth") Integer lenth) throws Exception {
		return getBetOperator(lotteryCode).frequency(gameMode, extent, frequencyType, line, lenth);
	}

	/** 
	* @Title: queryGameUserAwardGroupByLotteryId 
	* @Description: 查询用户可配置的投注奖金组列表
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/{lotteryCode}/queryGameUserAwardGroupByLotteryId")
	@ResponseBody
	public Object queryGameUserAwardGroupByLotteryId(@PathVariable String lotteryCode) throws Exception {
		return getBetOperator(lotteryCode).queryGameUserAwardGroupByLotteryId();
	}

	/**
	 * 
	* @Title: saveProxyBetGameAwardGroup 
	* @Description: 保存代理投注奖金组
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}/saveBetAward")
	@ResponseBody
	public Object saveProxyBetGameAwardGroup(@PathVariable String lotteryCode, @RequestParam Long awardGroupId)
			throws Exception {
		return getBetOperator(lotteryCode).saveProxyBetGameAwardGroup(awardGroupId);
	}

	/** 
	* @Title: indexInit 
	* @Description: 获取首页配置信息 
	* @param gametype
	* @return// 动态配置大部分参数将在cookie中获取，部分参数在测试中暂时写死数据
	* @throws Exception
	*/
	@RequestMapping(value = "/{lotteryCode}/indexInit")
	@ResponseBody
	public Object indexInit(@PathVariable String lotteryCode) throws Exception {
		return getBetOperator(lotteryCode).indexInit();
	}

	/** 
	* @Title: lastNumber 
	* @Description: 获取最新开奖号码
	* @param lotteryCode
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/{lotteryCode}/lastNumber")
	@ResponseBody
	public Object lastNumber(@PathVariable String lotteryCode) throws Exception {
		return getBetOperator(lotteryCode).lastNumber();
	}

	/**
	 * @Title: dynamicConfig
	 * @Description: 获取彩种动态配置信息
	 * @param lotteryCode
	 * @return 动态配置大部分参数将在cookie中获取，部分参数在测试中暂时写死数据
	 * @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}/dynamicConfig")
	@ResponseBody
	public DynamicConfig dynamicConfig(@PathVariable String lotteryCode) throws Exception {
		return getBetOperator(lotteryCode).getDynamicConfig();
	}
	
	/**
	 * @Title: straightOdds
	 * @Description: 获取六合彩賠率配置信息
	 * @param lotteryCode
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}/straightOdds")
	@ResponseBody
	public StraightOdds straightOdds(@PathVariable String lotteryCode) throws Exception {
		return getBetOperator(lotteryCode).getStraightOdds();
	}

	@RequestMapping(value = "/queryUserBal")
	@ResponseBody
	public Object queryUserBal() throws Exception {
		logger.debug("queryUserBal start");

		Long userId = RequestContext.getCurrUser().getId();
		Response<Long> response = betHttpClient.getUserBal(userId);

		return response.getBody().getResult().longValue();
	}

	/** 
	* @Title: getOrdersByUserId 
	* @Description:首页获取用户最新的五条投注记录
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/getOrdersByUserId")
	@ResponseBody
	public Object getOrdersByUserId(@RequestParam("userId") Long userId) throws Exception {
		logger.info("getOrdersByUserId start");

		GameOrderQueryRequest gameOrderRequest = new GameOrderQueryRequest();
		Pager pager = new Pager();
		pager.setStartNo(0);
		pager.setEndNo(5);

		Response<GameOrderQueryResponse> orderResponse = betHttpClient.getGameOrders(gameOrderRequest, userId, pager);
		logger.info("queryOrders end");

		if (orderResponse.getBody().getResult() != null) {
			return DTOConvertor4Web.orderStrucs2OrderStrucsDTO(orderResponse.getBody().getResult().getOrdersStruc());
		}
		return null;
	}

	@RequestMapping(value = "/{lotteryCode}/getUserOrders")
	@ResponseBody
	public List<OrdersStrucDTO> getUserOrders(@PathVariable String lotteryCode) throws Exception {
		return getBetOperator(lotteryCode).getUserOrders();
	}
	
	@RequestMapping(value = "/{lotteryCode}/getUserOrders/{activityType}" )
	@ResponseBody
	public List<OrdersStrucDTO> getShierShengXiaoUserOrders(@PathVariable String lotteryCode ,String activityType) throws Exception {
		logger.debug("activityType"+activityType);
		return getBetOperator(lotteryCode).getShierShengXiaoUserOrders(activityType);
	}

	@RequestMapping(value = "/{lotteryCode}/getUserPlans")
	@ResponseBody
	public List<PlansStrucForUI> getUserPlans(@PathVariable String lotteryCode) throws Exception {
		return getBetOperator(lotteryCode).getUserPlans();
	}

	/** 
	* @Title: betFile 
	* @Description: 单式文件上传处理文件内容，将其显示到页面文本框中
	* @param request
	* @param model
	* @throws Exception
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{lotteryCode}/betFile")
	@ResponseBody
	public Object betFile(@PathVariable String lotteryCode, @RequestParam("file") MultipartFile file) throws Exception {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.TEXT_HTML);
		return new ResponseEntity((Object) getBetOperator(lotteryCode).betFile(file), responseHeaders, HttpStatus.OK);
	}

	/**
	 * @Title: handlingCharge
	 * @Description: 计算撤单扣费金额
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}/handlingCharge")
	@ResponseBody
	public GameBetJsonResultStruc handlingCharge(@PathVariable String lotteryCode, @RequestParam("amount") String amount)
			throws Exception {
		return getBetOperator(lotteryCode).getCancelOrderCharge(amount);
	}

	/**
	 *	查询号码走势
	 * 
	 * @param lotteryCode
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}/trendChart")
	@ResponseBody
	public HistoryBallsResultDTO getTrendChart(@PathVariable String lotteryCode, @RequestParam("type") String type)
			throws Exception {

		return getBetOperator(lotteryCode).getTrendChart(type);
	}

	/**
	 * 保存用户投注信息  注意前台传递的数据结构，将其转换为接口请求reques。
	 * @param lotteryCode 彩種編碼
	 * @param data 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}/submit")
	@ResponseBody
	public GameBetJsonResultStruc betSubmit(@PathVariable String lotteryCode, @RequestBody String data,
			HttpServletRequest request) throws Exception {

		try {
			//此处本地开发环境运行会抛出一个异常，属于正常现象，已经try catch处理，原因是我们没有登录功能，服务器上此处无bug

			GameBetJsonDateStruc jds = new GameBetJsonDateStruc();
			GameBetJsonResultStruc hr = new GameBetJsonResultStruc();
			GameBetTplData tplDate = new GameBetTplData();
			UserStrucResponse usr = (UserStrucResponse) RequestContext.getCurrUser();
			long userFreeze = usr.getFreezeMethod();
			if (userFreeze == 2L || userFreeze == 3L || userFreeze == 1L) {
				tplDate.setMsg("用户账户已冻结不能投注");
				jds.setTplData(tplDate);
				hr.setIsSuccess(0);
				hr.setType("subFailed");
				hr.setMsg("用户账户已冻结不能投注");
				hr.setData(jds);
				return hr;
			}
			
			if(lotteryCode.equals("lhc")){
				UserInfoRequest userInfoReq = new UserInfoRequest();
				userInfoReq.setUserId(usr.getId());
				Response<UserInfoResponse> userInfoRes = betHttpClient
						.getUserInfo(userInfoReq);
				if(1!=userInfoRes.getBody().getResult().getLhcStatus()){
					tplDate.setMsg("没有六合彩投注权限，请联系上一级或客服人员");
					jds.setTplData(tplDate);
					hr.setIsSuccess(0);
					hr.setType("lhcStatusCheck");
					hr.setMsg("没有六合彩投注权限，请联系上一级或客服人员");
					hr.setData(jds);
					return hr;
				}
			}

		} catch (Exception e) {
			logger.error("获取用户信息异常", e);
		}

		if (lotteryCode.equals("slmmc")||lotteryCode.equals("sl115")) {
			return getBetOperator(lotteryCode).saveMMC(data, request);
		} else {
			logger.info("JSON betData : " + data);
			return getBetOperator(lotteryCode).save(data, request);
		}
	}

	/**
	 * 根据彩种code获取彩种操作对象
	 * 
	 * @param lotteryCode
	 * @return
	 * @throws Exception
	 */
	private IBetOperation getBetOperator(String lotteryCode) {
		logger.info("get lottery bet operrator  begin...");
		for (IBetOperation operator : gameBetOperators) {
			if (operator.getLotteryCode().equals(lotteryCode))
				return operator;
		}
		logger.info("get lottery bet operrator  end...");
		return null;
	}

	/** 
	* @Title: showBlockadeInfo 
	* @Description: 显示封锁变价详细信息
	* @param lotteryCode
	* @param data
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/{lotteryCode}/submit/showBlockadeInfo")
	public ModelAndView showBlockadeInfo(@PathVariable String lotteryCode, HttpServletRequest request) throws Exception {
		AbstractBetOperator operator = (AbstractBetOperator) getBetOperator(lotteryCode);
		ModelAndView mav = new ModelAndView("bet/fc3d/showBlockadeInfo");
		GameOrderRequestDTO dto = new GameOrderRequestDTO();
		try {
			String data = request.getParameter("data");
			ObjectMapper jsonMapper = new ObjectMapper();
			if (data == null || data.equals("")) {
				return null;
			}
			dto = jsonMapper.readValue(data, GameOrderRequestDTO.class);
			List<BetDetailStrucDTO> sturcDTOList = dto.getBalls();
			Map<Long, String> mps = new TreeMap<Long, String>();

			List<GamePlanParm> parms = dto.getOrders();

			for (GamePlanParm gpp : parms) {
				mps.put(gpp.getIssueCode(), gpp.getNumber());
			}
			;

			Map<String, ViewLock> issues = new TreeMap<String, ViewLock>();
			if (sturcDTOList != null && !sturcDTOList.isEmpty()) {

				Collections.sort(sturcDTOList, new Comparator<BetDetailStrucDTO>() {
					@Override
					public int compare(BetDetailStrucDTO o1, BetDetailStrucDTO o2) {
						return (o1.getIssueCode()).compareTo(o2.getIssueCode());
					}
				});

				for (BetDetailStrucDTO betDetail : sturcDTOList) {
					ViewLock lock = issues.get(betDetail.getIssueCode() + "");
					if (lock == null) {
						lock = new ViewLock();
						if(lotteryCode.equals("lhc")){
							StringBuilder sb = new StringBuilder(mps.get(betDetail.getIssueCode()));
							sb.insert(2, "/");
							issues.put(sb.toString(), lock);
						}else{
							issues.put(mps.get(betDetail.getIssueCode()), lock);
						}
						
					}
					;
					String betType = betDetail.getType();
					String type[] = betType.split("\\.");
					Integer gameGroupCode = GameAwardNameUtil.getCode(operator.getLotteryId(), type[0], type[1],
							type[2], 0);
					Integer gameSetCode = GameAwardNameUtil.getCode(operator.getLotteryId(), type[0], type[1], type[2],
							1);
					Integer betMethodCode = GameAwardNameUtil.getCode(operator.getLotteryId(), type[0], type[1],
							type[2], 2);
					String groupName = GameAwardNameUtil.getTitle(operator.getLotteryId(), gameGroupCode, gameSetCode,
							betMethodCode, 0);
					String setName = GameAwardNameUtil.getTitle(operator.getLotteryId(), gameGroupCode, gameSetCode,
							betMethodCode, 1);
					String methodName = GameAwardNameUtil.getTitle(operator.getLotteryId(), gameGroupCode, gameSetCode,
							betMethodCode, 2);
					String betName = groupName + "_" + setName + methodName;
					LockPointRequestDTO lockDTO = betDetail.getLockPoint();
					if (lockDTO != null) {
						//初始化封锁信息
						List<BlockadeResponseDTO> tempList = lockDTO.getBeforeBlockadeList();
						if (isSSQ(lotteryCode)) {
							//如果是双色球的话，则特殊处理
							if (tempList != null && !tempList.isEmpty()) {
								BlockadeStrutResponseDTO addDTO = new BlockadeStrutResponseDTO();
								addDTO.setBetType(betName);
								addDTO.setBeishu(lockDTO.getBeishu());
								addDTO.setBlockadeDetail(betDetail.getBall());
								addDTO.setRealBeishu(betDetail.getMultiple().longValue());
								lock.getBlockadeList().add(addDTO);
							}
						} else {
							if (tempList != null && !tempList.isEmpty()) {
								for (BlockadeResponseDTO blockadeResponseDTO : tempList) {
									BlockadeStrutResponseDTO addDTO = new BlockadeStrutResponseDTO(blockadeResponseDTO,
											betName);
									addDTO.setBeishu(lockDTO.getBeishu());
									if(lotteryCode.equals("lhc")){
										addDTO.setBeishu(lockDTO.getBeishu()/10000);
									}
									addDTO.setRealBeishu(betDetail.getMultiple().longValue());
									lock.getBlockadeList().add(addDTO);
								}
							}
						}
						//初始化变价信息
						List<PointsRequestDTO> pointList = lockDTO.getPointsList();
						boolean needChangePrice = false;
						if (pointList != null && !pointList.isEmpty()) {
							for (PointsRequestDTO tempPointsDTO : pointList) {
								PointsStrucRequestDTO addDTO = new PointsStrucRequestDTO(tempPointsDTO,
										betDetail.getBall(), betName);
								if (tempPointsDTO.getCurrentPhase() > 0) {
									needChangePrice = true;
								}
								lock.getPointsList().add(addDTO);

							}
							if (!needChangePrice) {
								lock.getPointsList().clear();
							}
						}
					}
					//根据玩法和投注号码对变价数据排序
					Collections.sort(lock.getPointsList(), new Comparator<PointsStrucRequestDTO>() {
						@Override
						public int compare(PointsStrucRequestDTO o1, PointsStrucRequestDTO o2) {
							return (o1.getBetType() + o1.getBetDetail()).compareTo(o2.getBetType() + o2.getBetDetail());
						}
					});
					if (lock.getBlockadeList().size() > 1)
						lock.setBlockadeList(mergeBloacadeData(lock.getBlockadeList()));
				}
			}

			mav.addObject("issues", issues);
			if (lotteryCode.equals("ssq") || lotteryCode.equals("lhc"))
				mav.addObject("noBJ", "ssq");
			//mav.addObject("blockadeList", mergeBloacadeData(blockadeList));
			//mav.addObject("pointsList", pointsList);
		} catch (Exception e) {
			logger.error("saveGameOrder json param paser error!", e);
		}
		return mav;
	}
	
	
	
	
	/** 
	 * 显示六合彩封锁变价详细信息
	 * @param lotteryCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}/submit/showBlockadeInfoLhc")
	public ModelAndView showBlockadeInfoLhc(@PathVariable String lotteryCode, HttpServletRequest request) throws Exception {
		AbstractBetOperator operator = (AbstractBetOperator) getBetOperator(lotteryCode);
		ModelAndView mav = new ModelAndView("bet/lhc/showBlockadeInfo");
		GameOrderRequestDTO dto = new GameOrderRequestDTO();
		try {
			String data = request.getParameter("data");
			ObjectMapper jsonMapper = new ObjectMapper();
			if (data == null || data.equals("")) {
				return null;
			}
			dto = jsonMapper.readValue(data, GameOrderRequestDTO.class);
			List<BetDetailStrucDTO> sturcDTOList = dto.getBalls();
			Map<Long, String> mps = new TreeMap<Long, String>();

			List<GamePlanParm> parms = dto.getOrders();

			for (GamePlanParm gpp : parms) {
				mps.put(gpp.getIssueCode(), gpp.getNumber());
			}

			Map<String, ViewLock> issues = new TreeMap<String, ViewLock>();
			if (sturcDTOList != null && !sturcDTOList.isEmpty()) {

				Collections.sort(sturcDTOList, new Comparator<BetDetailStrucDTO>() {
					@Override
					public int compare(BetDetailStrucDTO o1, BetDetailStrucDTO o2) {
						return (o1.getIssueCode()).compareTo(o2.getIssueCode());
					}
				});
				Long idx = 0l;
				for (BetDetailStrucDTO betDetail : sturcDTOList) {
					idx++;
					ViewLock lock = issues.get(betDetail.getIssueCode() + "");
					if (lock == null) {
						lock = new ViewLock();
						if(lotteryCode.equals("lhc")){
							if(issues.size() ==0){
								StringBuilder sb = new StringBuilder(mps.get(betDetail.getIssueCode()));
								sb.insert(2, "/");
								issues.put(idx.toString(), lock);
								mav.addObject("issueCode", sb.toString());
							}
						}else{
							issues.put(mps.get(betDetail.getIssueCode()), lock);
						}
						
					}
					;
					String betType = betDetail.getType();
					String type[] = betType.split("\\.");
					Integer gameGroupCode = GameAwardNameUtil.getCode(operator.getLotteryId(), type[0], type[1],
							type[2], 0);
					Integer gameSetCode = GameAwardNameUtil.getCode(operator.getLotteryId(), type[0], type[1], type[2],
							1);
					Integer betMethodCode = GameAwardNameUtil.getCode(operator.getLotteryId(), type[0], type[1],
							type[2], 2);
					String groupName = GameAwardNameUtil.getTitle(operator.getLotteryId(), gameGroupCode, gameSetCode,
							betMethodCode, 0);
					String setName = GameAwardNameUtil.getTitle(operator.getLotteryId(), gameGroupCode, gameSetCode,
							betMethodCode, 1);
					String methodName = GameAwardNameUtil.getTitle(operator.getLotteryId(), gameGroupCode, gameSetCode,
							betMethodCode, 2);
					String betName = groupName + "_" + setName + methodName;
					LockPointRequestDTO lockDTO = betDetail.getLockPoint();
					if (lockDTO != null) {
						//初始化封锁信息
						List<BlockadeResponseDTO> tempList = lockDTO.getBeforeBlockadeList();
						if(tempList.size() != 0){
							StringBuilder sb = new StringBuilder(mps.get(betDetail.getIssueCode()));
							sb.insert(2, "/");
							issues.put(idx.toString(), lock);
						}
						if (isSSQ(lotteryCode)) {
							//如果是双色球的话，则特殊处理
							if (tempList != null && !tempList.isEmpty()) {
								BlockadeStrutResponseDTO addDTO = new BlockadeStrutResponseDTO();
								addDTO.setBetType(betName);
								addDTO.setBeishu(lockDTO.getBeishu());
								addDTO.setBlockadeDetail(betDetail.getBall());
								addDTO.setRealBeishu(betDetail.getMultiple().longValue());
								lock.getBlockadeList().add(addDTO);
							}
						} else {
							if (tempList != null && !tempList.isEmpty()) {
								for (BlockadeResponseDTO blockadeResponseDTO : tempList) {
									BlockadeStrutResponseDTO addDTO = new BlockadeStrutResponseDTO(blockadeResponseDTO,
											betName);
									addDTO.setAfterAmount(blockadeResponseDTO.getAfterAmount());
									addDTO.setBeforeAmount(blockadeResponseDTO.getBeforeAmount());
									lock.getBlockadeList().add(addDTO);
								}
							}
						}
						//初始化变价信息
						List<PointsRequestDTO> pointList = lockDTO.getPointsList();
						boolean needChangePrice = false;
						if (pointList != null && !pointList.isEmpty()) {
							for (PointsRequestDTO tempPointsDTO : pointList) {
								PointsStrucRequestDTO addDTO = new PointsStrucRequestDTO(tempPointsDTO,
										betDetail.getBall(), betName);
								if (tempPointsDTO.getCurrentPhase() > 0) {
									needChangePrice = true;
								}
								lock.getPointsList().add(addDTO);

							}
							if (!needChangePrice) {
								lock.getPointsList().clear();
							}
						}
					}
					//根据玩法和投注号码对变价数据排序
					Collections.sort(lock.getPointsList(), new Comparator<PointsStrucRequestDTO>() {
						@Override
						public int compare(PointsStrucRequestDTO o1, PointsStrucRequestDTO o2) {
							return (o1.getBetType() + o1.getBetDetail()).compareTo(o2.getBetType() + o2.getBetDetail());
						}
					});
					if (lock.getBlockadeList().size() > 1)
						lock.setBlockadeList(mergeBloacadeData(lock.getBlockadeList()));
				}
			}

			mav.addObject("issues", issues);
			
			if (lotteryCode.equals("ssq") || lotteryCode.equals("lhc"))
				mav.addObject("noBJ", "ssq");
			//mav.addObject("blockadeList", mergeBloacadeData(blockadeList));
			//mav.addObject("pointsList", pointsList);
		} catch (Exception e) {
			logger.error("saveGameOrder json param paser error!", e);
		}
		return mav;
	}

	private boolean isSSQ(String lotteryCode) {
		return "ssq".equals(lotteryCode);
	}

	/** 
	* @Title: mergeBloacadeData 
	* @Description: 合并封锁数据
	* @return
	*/
	private static List<BlockadeStrutResponseDTO> mergeBloacadeData(List<BlockadeStrutResponseDTO> blockadeList) {
		List<BlockadeStrutResponseDTO> resultList = new ArrayList<BlockadeStrutResponseDTO>();
		if (blockadeList == null || blockadeList.isEmpty()) {
			return resultList;
		}
		for (BlockadeStrutResponseDTO temp : blockadeList) {
			//判读列表中是否存在投注类型，投注倍数，实际倍数是否一样的对象
			if (resultList.contains(temp)) {
				int aimIndex = resultList.indexOf(temp);
				BlockadeStrutResponseDTO aimDTO = resultList.get(aimIndex);
				//将对象的投注内容合并
				aimDTO.setBlockadeDetail(aimDTO.getBlockadeDetail() + "[" + temp.getBlockadeDetail() + "]");
			} else {
				temp.setBlockadeDetail("[" + temp.getBlockadeDetail() + "]");
				resultList.add(temp);
			}
		}
		return resultList;
	}

	@RequestMapping("/downLoadOrder")
	public ModelAndView downLoadOrder(@RequestParam("orderCode") String orderCode) throws Exception {
		logger.info("queryOrderDetail start");
		Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();

		Response<GameIssueQueryResponse> responseGameIssue = new Response<GameIssueQueryResponse>();
		ModelAndView mav = new ModelAndView("userCenter/orderPrint");

		Long userId = RequestContext.getCurrUser().getId();
		GameOrderDetailQueryRequest request = new GameOrderDetailQueryRequest();
		request.setOrderCode(orderCode);
		try {
			response = httpClient.invokeHttp(serverPath + queryOrderDetail, request, userId, null,
					GameOrderDetailQueryResponse.class);
			if (response.getBody() != null && response.getBody().getResult() != null
					&& response.getBody().getResult().getOrdersStruc() != null) {
				if (response.getBody().getResult().getOrdersStruc().getUserid().longValue() != userId) {
					logger.error("当前用户没有权限查询订单： " + orderCode);
					return null;
				}
			}
			logger.info("queryOrderDetail end");

			GameIssueQueryRequest gameIssueQueryRequest = new GameIssueQueryRequest();
			if (response.getBody() != null && response.getBody().getResult() != null
					&& response.getBody().getResult().getOrdersStruc() != null) {
				gameIssueQueryRequest.setLotteryId(response.getBody().getResult().getOrdersStruc().getLotteryid());
				gameIssueQueryRequest.setIssueCode(response.getBody().getResult().getOrdersStruc().getIssueCode());
			}
			try {

				logger.info("queryGameIssue start");
				responseGameIssue = httpClient.invokeHttp(serverPath + queryGameIssue, gameIssueQueryRequest,
						GameIssueQueryResponse.class);
				logger.info("queryGameIssue end");
			} catch (Exception e) {
				logger.error("查询订单报错 ", e);
			}

			if (response.getBody() != null && response.getBody().getResult() != null) {
				GameOrderDetailQueryResponseDTO dto = DTOConvertor4Web
						.gameOrderDetailQueryResponse2GameOrderDetailQueryResponseDTO(response.getBody().getResult());
				GameOrderDownloadStruc gameOrderDownloadStruc = new GameOrderDownloadStruc();
				gameOrderDownloadStruc.setLotteryId(gameIssueQueryRequest.getLotteryId());
				gameOrderDownloadStruc.setLotteryName(dto.getOrdersStruc().getLotteryName());
				gameOrderDownloadStruc.setFormatSaleTime(dto.getOrdersStruc().getFormatSaleTime());
				gameOrderDownloadStruc.setOrderCode(orderCode);
				gameOrderDownloadStruc.setTotamount(dto.getOrdersStruc().getTotamount());
				gameOrderDownloadStruc.setWebIssueCode(responseGameIssue.getBody().getResult().getWebIssueCode());
				List<BetDownloadStruc> list = new ArrayList<BetDownloadStruc>();
				Map<String, List<BetContentStruc>> map = new HashMap<String, List<BetContentStruc>>();
				for (SlipsStrucDTO slip : dto.getSlipsStruc()) {
					String planName = slip.getGamePlayName();
					BetContentStruc betContentStruc = new BetContentStruc();
					betContentStruc.setNumber(slip.getBetDetail());
					//六合彩倍數用賠率取代
					if(gameIssueQueryRequest.getLotteryId()==99701){
						if(slip.getSingleWin()!=null){
						betContentStruc.setSingleWin(String.valueOf(new Double(slip.getSingleWin())/10000));							
						}
					}else{
						betContentStruc.setTimes(slip.getMultiple());						
					}
					
					betContentStruc.setSubamout(slip.getTotamount());
					if (map.containsKey(planName)) {
						map.get(planName).add(betContentStruc);
					} else {
						List<BetContentStruc> listContent = new ArrayList<BetContentStruc>();
						listContent.add(betContentStruc);
						map.put(planName, listContent);
					}
				}
				for (Entry<String, List<BetContentStruc>> entry : map.entrySet()) {
					BetDownloadStruc betDownloadStruc = new BetDownloadStruc();
					betDownloadStruc.setGameType(entry.getKey());
					betDownloadStruc.setBetContent(entry.getValue());
					list.add(betDownloadStruc);
				}
				gameOrderDownloadStruc.setBetList(list);
				mav.addObject("struc", gameOrderDownloadStruc);

			}
		} catch (Exception e) {
			logger.error("查询订单报错 ", e);
			return null;
		}
		return mav;
	}
	
	@RequestMapping(value = "queryWinsOrder")
	@ResponseBody
	public Object queryWinsOrder(@RequestParam(value="lotteryid",required=false) Long lotteryid)
			throws Exception {
		AjaxResponse res = new AjaxResponse();
		
		DecimalFormat decimalFormat=new DecimalFormat(",###");
		
		String lotteryIndexJson =  redis.get("firefrog_index_lastdata_"+lotteryid);
		IndexLottery indexLottery = (IndexLottery)DataConverterUtil.convertJson2Object(lotteryIndexJson, IndexLottery.class);
		Map<String,String> wins = indexLottery.getWins();
		List<GameOrderWinsDetailDTO> winList = new ArrayList<GameOrderWinsDetailDTO>();
		
		if(null != wins){
			for(Entry<String, String> winEntry:wins.entrySet()){
				GameOrderWinsDetailDTO dto = new GameOrderWinsDetailDTO();
				String issueAccount = winEntry.getKey();
				String[] account = issueAccount.split("-");
				String winAmount = winEntry.getValue();
				
				if(StringUtils.isNotEmpty(winAmount)){
					try {
						Long amount = Long.valueOf(winAmount)/10000;
						winAmount = decimalFormat.format(amount);
						dto.setAmountValue(amount);
					} catch (Exception e) {
						logger.error("中獎金額錯誤 ", e);
					}
				}
				String accountName= account[0];
				accountName = accountName.substring(0,1)+"***"+accountName.substring(accountName.length()-1)+" ";
				if(account.length>1){
					dto.setUserName(accountName);
					dto.setCurrentIssue(account[1]);				
				}else{
					dto.setUserName(accountName);
					dto.setCurrentIssue("");				
				}
				
				dto.setAmount(winAmount);
				winList.add(dto);
			}
		}
		Collections.sort(winList, new Comparator<GameOrderWinsDetailDTO>() {
			@Override
			public int compare(GameOrderWinsDetailDTO o1,
					GameOrderWinsDetailDTO o2) {
				if(o2.getCurrentIssue().compareTo(o1.getCurrentIssue())==0){
					return o2.getAmountValue().compareTo(o1.getAmountValue());
				}else{
					return o2.getCurrentIssue().compareTo(o1.getCurrentIssue());
				}
			}
		});
		res.setData(winList);
		res.setStatus(1l);
		return res;
	}

	/**
	 * @Title: getWinningList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}/getWinningList")
	@ResponseBody
	public WinUserListResponse getWinningList(@PathVariable String lotteryCode,@RequestBody String orderIds)
			throws Exception {
		return getBetOperator(lotteryCode).getWinningList(orderIds);
}
	
	
	/**
	 * @Title: handlingCharge
	 * @Description: 计算撤单扣费金额
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{lotteryCode}/getPlayerBetList")
	@ResponseBody
	public WinUserListResponse getPlayerBetList(@PathVariable String lotteryCode)
			throws Exception {
		return getBetOperator(lotteryCode).getPlayerBetList();
	}
	
	/** 
	* @Title: getOrdersByUserId 
	* @Description:首页获取用户最新的五条投注记录
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/saveUserHeadImg")
	@ResponseBody
	public Object saveUserHeadImg(@RequestParam("nickname") String nickName,@RequestParam("headImg") String headImg) throws Exception {
		Long userId = RequestContext.getCurrUser().getId();
		String account = RequestContext.getCurrUser().getUserName();

		Map<String,String> request = new HashMap<String,String>();
		request.put("nickName", nickName);
		request.put("headImg", headImg);
		WinUserListResponse result = new WinUserListResponse();
		Response<Long> resp = betHttpClient.saveUserHeadImg(request, userId, account);
		result.setIsSuccess(1);
		if(resp.getBody().getResult() == 0){
			result.setIsSuccess(0);
			result.setMsg(nickName+"已经存在，请重新设置！");
		}else if(resp.getBody().getResult() ==2){
			result.setIsSuccess(0);
			result.setMsg(nickName+"未知错误！");
		}
		return result;
	}
	
	/** 
	* @Title: getOrderDetail 
	* @Description: 12生肖投注詳情
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/orderDetail")
	@ResponseBody
	public GameOrderDetailQueryResponseDTO getOrderDetail(@RequestBody Request<GameOrderDetailQueryRequest> request) throws Exception{
		
		logger.debug("getOrderDetail~~~~~~~!!!!");
		logger.debug("order_id : " + request.getBody().getParam().getOrderId());
		logger.debug("order_code : " + request.getBody().getParam().getOrderCode());
		try {		
			Response<GameOrderDetailQueryResponse> detailRes = httpClient.invokeHttp(serverPath + queryOrderDetail, request.getBody().getParam(),GameOrderDetailQueryResponse.class);
			if (detailRes.getBody() != null && detailRes.getBody().getResult() != null) {
				GameOrderDetailQueryResponseDTO result = DTOConvertor4Web.gameOrderDetailQueryResponse2GameOrderDetailQueryResponseDTO(detailRes.getBody().getResult());				
				return result;
			}
		} catch (Exception e) {
			logger.error("getOrderDetail error:", e);

		}
		
		return null ;
	}
	
	/**
	 * 查詢彩種最近開獎資料。
	 * @param lotteryCode 彩種代碼
	 * @param model
	 * @param historynum 待回傳歷史開獎資料筆數
	 */
	@RequestMapping(value = "/{lotteryCode}/historyLotteryAward")
	@ResponseBody
	public Response<HistoryLotteryAwardResponse> historyLotteryAward(@PathVariable String lotteryCode, @RequestParam("historynum") String historynum) {
		try {
			AbstractBetOperator operator = (AbstractBetOperator) getBetOperator(lotteryCode);
			HistoryLotteryAwardRequest request = new HistoryLotteryAwardRequest();
			request.setLotteryId(operator.getLotteryId());
			request.setHistorynum(Long.valueOf(historynum));
			Response<HistoryLotteryAwardResponse> resp = httpClient.invokeHttp(serverPath + historyLotteryAward, request, HistoryLotteryAwardResponse.class);
			return resp;
		} catch(Exception e) {
			logger.error("{} historyLotteryAward error:{}", lotteryCode, e.getMessage(), e);
			return null;
		}
	}
}
