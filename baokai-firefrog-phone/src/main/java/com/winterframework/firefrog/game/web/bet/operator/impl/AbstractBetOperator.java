package com.winterframework.firefrog.game.web.bet.operator.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.httpjsonclient.impl.HttpJsonClientImpl;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.validate.business.IValidateExecutor;
import com.winterframework.firefrog.common.validate.business.IValidateExecutorContext;
import com.winterframework.firefrog.common.validate.business.IValidatorFactory;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.exception.GameBetAmountErrorException;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.game.web.bet.operator.BetSupport;
import com.winterframework.firefrog.game.web.bet.operator.IBetOperation;
import com.winterframework.firefrog.game.web.bet.util.BetHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.BetDetailStruc;
import com.winterframework.firefrog.game.web.dto.BetDetailStrucDTO;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GameBetBalls;
import com.winterframework.firefrog.game.web.dto.GameBetJsonDateStruc;
import com.winterframework.firefrog.game.web.dto.GameBetJsonResultStruc;
import com.winterframework.firefrog.game.web.dto.GameBetTplData;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderDetailQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderQueryResponse;
import com.winterframework.firefrog.game.web.dto.GameOrderRequest;
import com.winterframework.firefrog.game.web.dto.GameOrderRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderResponse;
import com.winterframework.firefrog.game.web.dto.LockPointRequestDTO;
import com.winterframework.firefrog.game.web.dto.MMCOrderDetail;
import com.winterframework.firefrog.game.web.dto.OrdersStruc;
import com.winterframework.firefrog.game.web.dto.PointsRequestDTO;
import com.winterframework.firefrog.game.web.dto.SlipsStruc;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;
import com.winterframework.firefrog.phone.util.IPUtil;
import com.winterframework.firefrog.phone.web.validate.business.BetValidateContext;
import com.winterframework.firefrog.phone.web.validate.business.LotteryPlayMethodKeyGenerator;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResponseHeader;

/**
 * @Title 全彩种投注操作类
 * 
 * @author bob
 *
 */
public abstract class AbstractBetOperator extends BetSupport implements IBetOperation {

	private Logger logger = LoggerFactory.getLogger(AbstractBetOperator.class);

//	@Resource(name = "httpJsonClientImpl")
//	protected IHttpJsonClient httpClient;
//	@Resource(name = "betHttpClient")
//	public BetHttpJsonClient betHttpClient;

//	@Resource(name = "gameBetNumberValidatorFactory")
//	private IValidatorFactory<GameSlip> validatorFactory;
	
//	//服务器状态码
//	@Resource(name = "serverMessages")
//	protected Map<Long, GameBetJsonResultStruc> serverMessages;
	
	@PropertyConfig(value = "url.connect")
	protected String firefrogUrl;
	
	@PropertyConfig(value = "url.game")
	protected String gameUrl;
	
	@PropertyConfig(value = "url.game.buy")
	protected String saveGameOrder;
	
	@PropertyConfig(value = "url.front.userBal")
	private String getUserBal;
	
	@PropertyConfig(value = "url.front.gamedetail")
	private String queryOrderDetail;
	
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

//	private List<String> videoSourceList;

	private String helpLink;//玩法说明链接

	@PropertyConfig("platVersion")
	private String platVersion;

	@Resource(name = "RedisClient")
	private RedisClient redis;

	@PropertyConfig("multiplemaxtimes")
	private String multiplemaxtimes;

	@PropertyConfig("multipleToCnyFactor")
	private String multipleToCnyFactor;

	public Long getLotteryId() {
		return lotteryId;
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

	protected GameOrderRequestDTO convertJsonToGameOrderRequestDTO(String data) throws Exception {
		return convertJsonToObject(data, GameOrderRequestDTO.class);
	}

	

//	private GameBetJsonResultStruc errorReturn(Long status) {
//		if (serverMessages.get(status) != null) {
//			return serverMessages.get(status);
//		} else {
//			return serverMessages.get(99999L);
//		}
//	}

	@Override
	public GameBetJsonResultStruc saveMMC(String data, HttpServletRequest request ,Long userId, String account) throws Exception {

		logger.info("saveGameOrder start");
		logger.info("-------------------------------------------------");
		logger.info("data : " + data);
		
		//springmvc 支持直接解析json，但是参数或类型错误不会打印出来，直接给报一个400 bad request，因此在此用代码转换json串  //  金额*10000  自解析json处理异常抛出
		GameOrderRequestDTO dto = new GameOrderRequestDTO();
		try {
			dto = convertJsonToObject(data, GameOrderRequestDTO.class);
		} catch (Exception e) {
			logger.error("saveGameOrder json param paser error!", e);
			//return serverMessages.get(99999L);// 参数修改成投注格式错误
			throw new Exception();
		}

//		//TODO 添加秒秒投注key值对比 防止页面多开
//		String serverBetKeyValue = redis.get("mmcBet" + RequestContext.getCurrUser().getId());
//		logger.info("serverBetKeyValue : " + serverBetKeyValue);
//		logger.info("serverBetKeyValue : " + serverBetKeyValue);
//		for (Cookie co : request.getCookies()) {
//			logger.info("co.getName() : " + co.getName());
//			if (co.getName().equals("mmcBetCookie")) {
//				logger.info("co.getValue() : " + co.getValue());
//				if (!serverBetKeyValue.equals(co.getValue())) {
//					GameBetJsonResultStruc struc = new GameBetJsonResultStruc();
//					struc.setIsSuccess(0);
//					struc.setType("normal");
//					struc.setMsg("秒秒彩投注失败!请在最新开的秒秒页面投注或者刷新当前页面重新投注!");
//					return struc;
//				}
//			}
//		}

		Long status = 0L;
		Response<GameOrderResponse> result = orderBet(dto, request,userId,account);
		status = result.getHead().getStatus();
		//status = 111111L;
		logger.info("status : " + status);
		
		//构建秒秒彩返回json
		if (status == 0) {
			Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();
			GameOrderDetailQueryRequest queryRequest = new GameOrderDetailQueryRequest();
			queryRequest.setOrderId(result.getBody().getResult().getOrderId());
			try {
				
//				response = betHttpClient.getOrderDetai(queryRequest);
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
			datas.setWinMoney(oc.getTotwin());
			if (oc.getStatus() == 2) {
				datas.setWinNum(1L);
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
				}else if (ss.getMoneyMode() == 3) { //1元2角
					md.setModes("分");
				} else {
					md.setModes("角");
				}
				md.setNum(ss.getTotbets());
				md.setMultiple(ss.getMultiple() + "");
				md.setProjectid(oc.getOrderCode());
				md.setTotalPrice(ss.getTotamount() + "");
				if (ss.getStatus() == 2) {
					md.setWinNum(3L);
					md.setWinMoney(ss.getAward());
				} else {
					md.setWinNum(0L);
					md.setWinMoney(0L);
				}
				md.setWriteTime(DateUtils.format(DataConverterUtil.convertLong2Date(oc.getSaleTime()),
						DateUtils.DATETIME_FORMAT_PATTERN));
				list.add(md);
			}
			datas.setList(list);
			struc.setData(datas);
			return struc;

		}

		if (status == 204005) {//秒秒彩开奖失败
			Response<GameOrderDetailQueryResponse> response = new Response<GameOrderDetailQueryResponse>();
			GameOrderDetailQueryRequest queryRequest = new GameOrderDetailQueryRequest();
			queryRequest.setOrderId(result.getBody().getResult().getOrderId());
			logger.info("--------------------------------------status == 204005");
			try {
//				response = betHttpClient.getOrderDetai(queryRequest);
				logger.info("queryOrderDetail end");
			} catch (Exception e) {
				logger.error("queryOrderDetail is error.", e);
				throw e;
			}

			GameBetJsonResultStruc struc = new GameBetJsonResultStruc();
			struc.setIsSuccess(0);
			struc.setType("normal");
			struc.setMsg("秒秒彩开奖失败！投注订单已做撤销处理，请重新投注！");
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
//		if (status == 205000) {
//			GameBetJsonResultStruc struc = handingBetStatus(205000L);
//			//处理封锁变价的数据结构
//			LockFacadeUtils.facadeGameOrderStruc(struc, result.getBody().getResult().getGameOrderDTO(), dto, account,
//					getBetNameConvertor(), lotteryId);
//			return struc;
//		}

		//保存选号球，假如存在暂停玩法，删掉暂停选号球返回
		List<GameBetBalls> balls = new ArrayList<GameBetBalls>();
		List<BetDetailStrucDTO> betBalls = dto.getBalls();
		GameBetJsonDateStruc jds = new GameBetJsonDateStruc();
		GameBetJsonResultStruc hr = new GameBetJsonResultStruc();
		GameBetTplData tplDate = new GameBetTplData();
		String strStatus = status + "";
		logger.info("strStatus : " + strStatus);
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
			//return handingBetStatus(status);
			throw new Exception();
		}
	}

	private String formatOrderNumber(String numberRecord) {
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

	

	/** 
	* @Title: orderBet 
	* @Description:普通投注
	* @param dto
	* @param request
	* @return
	* @throws Exception
	*/
	private Response<GameOrderResponse> orderBet(GameOrderRequestDTO dto, HttpServletRequest request,Long userId, String account) throws Exception {

		GameOrderRequest gr = new GameOrderRequest();
		
		logger.info("--------------orderBet----------------");
		lotteryId = 99112L;
		logger.info("lotteryId : " + lotteryId);
		gr.setLotteryid(lotteryId);
		try {
			gr.setUserIp(IPConverter.ipToLong(IPUtil.getClientIpAddr(request)));
			logger.info("UserIp : " + gr.getUserIp());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("dto.getAmount() : " + dto.getAmount());
		gr.setPackageAmount(formatMultipleMoney(dto.getAmount()));
		logger.info("PackageAmount : " + gr.getPackageAmount());
		gr.setSaleTime(new Date().getTime());
		//gr.setServerIp(IPConverter.ipToLong(getProperty("serverIP")));
		gr.setServerIp(123456789L);
		//gr.setChannelId(Integer.valueOf(getProperty("channelId")));
		gr.setChannelId(1);
		gr.setChannelVersion(platVersion);
		logger.info("IsFirstSubmit : " + dto.getIsFirstSubmit());
		gr.setIsFirstSubmit(dto.getIsFirstSubmit());
		if (dto.getOrders() != null && !dto.getOrders().isEmpty()) {
			logger.info("IssueCode : " + dto.getOrders().get(0).getIssueCode());
			gr.setIssueCode(dto.getOrders().get(0).getIssueCode());
		}

		List<BetDetailStruc> bdss = new ArrayList<BetDetailStruc>();

		Long totalDetailMoney = 0L;
		List<BetDetailStrucDTO> ary = dto.getBalls();
		logger.info("ary size : " + ary.size());
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
			bds.setMoneyMode(bd.getMoneyunit() == 0.1 ? 2 : (bd.getMoneyunit() == 0.01?3:1));//页面传过来的值时0.1 为角模式，对应接口数据1元2角
			logger.info("MoneyMode : " + bds.getMoneyMode());
			bds.setMultiple(bd.getMultiple());
			logger.info("Multiple : " + bds.getMultiple());
			bds.setItemAmount(getItemAmountValue(bd));//元角模式单注金额
			logger.info("ItemAmount : " + bds.getItemAmount());
			bds.setTotbets(getTotalBet(bd));//注数
			logger.info("TotalBet : " + bds.getTotbets());
			bds.setTotamount(getTotalAmountValue(bd));
			logger.info("Totamount : " + bds.getTotamount());
			totalDetailMoney += bds.getTotamount();
			logger.info("totalDetailMoney : " + totalDetailMoney);
			//变价列表
			LockPointRequestDTO lockPointDTO = bd.getLockPoint();
			if (lockPointDTO != null) {
				List<PointsRequestDTO> points = lockPointDTO.getPointsList();
				if (points != null && !points.isEmpty()) {
					bds.setPointsList(points);
				}
			}

			bdss.add(bds);
		}
		gr.setBetDetailStruc(bdss);
		long status = 0l;
		Response<GameOrderResponse> gameOrderResponse = new Response<GameOrderResponse>();
		logger.info("----------totalDetailMoney : " + totalDetailMoney.longValue());
		logger.info("----------getPackageAmount : " + gr.getPackageAmount());
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
					.convertGameOrderRequest2GameOrder(gr, userId);
			status = validate(gameOrder);
			if (status > -1l) {
				RequestHeader requestH = new RequestHeader();
				ResponseHeader rh = ResponseHeader.createReponseHeader(requestH);
				rh.setStatus(status);
				gameOrderResponse.setHead(rh);
				return gameOrderResponse;
			}
		}

		BetHttpJsonClient betHttpClient = new BetHttpJsonClient();
		logger.info("before betHttpClient.saveGameOrder ~~~~~!");
		//gameOrderResponse = httpClient.invokeHttp("http://127.0.0.1:8084" + "/game/saveGameOrder", gr, 1292830L, "captainray", GameOrderResponse.class);
		gameOrderResponse = betHttpClient.saveGameOrder(gr, userId, account);
//		IHttpJsonClient httpClient = new HttpJsonClientImpl();
//		gameOrderResponse = httpClient.invokeHttp("http://127.0.0.1:8084" + "/game/saveGameOrder", gr, userId, account, GameOrderResponse.class);
		logger.info("after betHttpClient.saveGameOrder ~~~~~!");
		return gameOrderResponse;
	}

	protected int getItemAmountValue(BetDetailStrucDTO bd) {
		return ((Double) (2 * 10000 * bd.getMoneyunit())).intValue();
	}

	protected Long getTotalAmountValue(BetDetailStrucDTO bd) {
		return ((long) ((Double) (2 * 10000 * bd.getMoneyunit())).intValue() * bd.getNum() * bd
				.getMultiple());
	}

	protected int getTotalBet(BetDetailStrucDTO bd) {
		return bd.getNum();
	}

	
	/**
	 * 根据配置的玩法判断是否为单式文件模式
	 * 
	 * @param betMethodType
	 * @return
	 */
	protected int isFileMode(String betMethodType) {
		String fileModeMethodTypes = "danshi,hunhezuxuan";
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
		logger.info("NumberRecord : " + gameOrder.getNumberRecord());
		logger.info("OrderCod : " +gameOrder.getOrderCode());
		logger.info("CalculateWinTime : " +gameOrder.getCalculateWinTime());
		logger.info("CancelFee : " +gameOrder.getCancelFee());
		logger.info("CancelModes : " +gameOrder.getCancelModes());
		logger.info("EndCanCancelTime : " +gameOrder.getEndCanCancelTime());
		logger.info("FileMode().name() : " +gameOrder.getFileMode().name());
		logger.info("FundStatus : " +gameOrder.getFundStatus());
		logger.info("GamePlanDetailId() : " +gameOrder.getGamePlanDetailId());
		try {
			for (GameSlip slip : gameOrder.getSlipList()) {
				logger.info("=======================getSlipList0");
				IKeyGenerator keyGen = new LotteryPlayMethodKeyGenerator(99112L, slip.getGameBetType()
						.getGameGroupCode(), slip.getGameBetType().getGameSetCode(), slip.getGameBetType()
						.getBetMethodCode(), "_$_");
				logger.info("=======================getSlipList1");
				IValidateExecutorContext context = new BetValidateContext(gameOrder, slip);
				logger.info("=======================getSlipList2");
				
//				IValidateExecutor<GameSlip> validatorExecutor = validatorFactory.getValidateExecutor("slmmc",
//						keyGen);
//				logger.info("=======================getSlipList3");
//				validatorExecutor.execute(slip, context);
//				logger.info("=======================getSlipList4");
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

//	protected GameBetJsonResultStruc handingBetStatus(Long status) {
//		return errorReturn(status);
//	}

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

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public void setDefaultMethod(String defaultMethod) {
		this.defaultMethod = defaultMethod;
	}

	public void setLotterySeries(String lotterySeries) {
		this.lotterySeries = lotterySeries;
	}

//	public void setVideoSourceList(List<String> videoSourceList) {
//		this.videoSourceList = videoSourceList;
//	}

	public void setHelpLink(String helpLink) {
		this.helpLink = helpLink;
	}

	public String getHelpLink() {
		return helpLink;
	}
}
