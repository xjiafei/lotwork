package com.winterframework.firefrog.user.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.config.dao.IConfigDao;
import com.winterframework.firefrog.common.httpjsonclient.IHttpClient;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.enums.SysFundStatusEnum;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.exception.FundPTNoUserException;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.impl.pt.FundSysResult;
import com.winterframework.firefrog.fund.service.impl.pt.PtSysTransferRequest;
import com.winterframework.firefrog.fund.service.impl.pt.PtcheckFundRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.IUserSlotExchangeActivityDao;
import com.winterframework.firefrog.user.dao.IUserSlotExchangeDao;
import com.winterframework.firefrog.user.dao.IUserSlotExchangeLogDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.UserSlotExchange;
import com.winterframework.firefrog.user.dao.vo.UserSlotExchangeActivity;
import com.winterframework.firefrog.user.dao.vo.UserSlotExchangeLog;
import com.winterframework.firefrog.user.dao.vo.ViewUserSlotExchange;
import com.winterframework.firefrog.user.service.IUserSlotExhangeService;
import com.winterframework.firefrog.user.web.dto.ConfigLabarUrl;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeByUserResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeChangeResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeCheckResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeModifyResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotExchangeResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotOccupancyResponse;
import com.winterframework.firefrog.user.web.dto.UserSlotOccupancySaveResponse;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Service("userSlotExhangeServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class UserSlotExhangeServiceImpl implements IUserSlotExhangeService {

	private static Logger logger = LoggerFactory.getLogger(UserSlotExhangeServiceImpl.class);

	@Resource(name = "RedisClient")
	private RedisClient redisService;
	
	@Resource(name = "fundChangeServiceImpl")
	protected IFundAtomicOperationService fundChangeService;
	
	@Resource(name = "userSlotExchangeDaoImpl")
	private IUserSlotExchangeDao userSlotExchangeDao;

	@Resource(name = "userSlotExchangeLogDaoImpl")
	private IUserSlotExchangeLogDao userSlotExchangeLogDao;

	@Resource(name = "userSlotExchangeActivityDaoImpl")
	private IUserSlotExchangeActivityDao userSlotExchangeActivityDao;
	
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;
	
	@Resource(name = "configDaoImpl")
	private IConfigDao confifDao;
	
	@Resource(name = "SNUtil")
	protected ISNGenerator snUtil;
	
	@PropertyConfig(value = "pt.url")
	private String ptURL;
	
	@PropertyConfig(value = "pt.url.systransfer")
	private String ptsystransfer;
	
	@PropertyConfig(value = "pt.url.checktransfer")
	private String ptchecktranfer;
	
	@PropertyConfig(value = "user_sendNewsletters")
	private String sendNewsletters;
	
	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient httpJsonClient;
	
	@Resource(name = "HttpClientImpl")
	protected IHttpClient httpPtClient;
	
	@Override
	public UserSlotExchangeChangeResponse change(String activityType) {
		
		UserSlotExchangeChangeResponse response = new UserSlotExchangeChangeResponse();
		
		if(activityType == null){
			return response;
		}

		ConfigLabarUrl configLabarUrl = DataConverterUtil.convertJson2Object(confifDao.getConfigValueByKey("labar", "labarUrl"), ConfigLabarUrl.class);
		
		logger.info("configLabarUrl:"+configLabarUrl);
		logger.info("urlId:"+configLabarUrl.getUrlId());
		logger.info("url:"+configLabarUrl.getUrl());
		
		if(configLabarUrl != null){
			response.setUrl(configLabarUrl.getUrl());
		}
		
		ViewUserSlotExchange vo = new ViewUserSlotExchange();
		// 隨機取一筆拉霸獎金By exchangeAmount
		vo.setIsBinding(0L);
		vo.setIsAward(0L);
		vo.setSendStatus(0L);
		vo.setActivityId(changeActivityType(activityType));
		ViewUserSlotExchange model = userSlotExchangeDao.getRandomViewUserSlotExchange(vo);
		response.setExchangePrize(model.getExchangePrize());
		response.setExchangeNumber(model.getExchangeNumber());
		response.setStatus(startDateToEndate(model.getGmtActivityStart(),model.getGmtActivityEnd()));
		return response;
	}

	@Override
	public UserSlotOccupancyResponse occupancy(String activityType, String cellularPhone, String exchangeNumber, Long exchangePrize) {
		UserSlotOccupancyResponse response = new UserSlotOccupancyResponse();
		if(cellularPhone == null || exchangeNumber == null || exchangePrize == null || activityType == null){
			return response;
		}
		
		Long status = 0L;
		
		Long activityId = changeActivityType(activityType);
		
		Date newDate = new Date();
		
		ViewUserSlotExchange vo = new ViewUserSlotExchange();
		vo.setIsBinding(0L);
		vo.setGmtBindingStart(startDateByDay(newDate));
		vo.setGmtBindingEnd(endDateByDay(newDate));
		vo.setActivityId(activityId);
		Long count = userSlotExchangeDao.getViewUserSlotExchangeCount(vo);
		
		logger.info("=count:"+count);
		
		if(count >= 100){
			response.setStatus(7L); 
			return response;
		}
		
		UserSlotExchangeActivity userActivityVo = new UserSlotExchangeActivity();
		userActivityVo.setId(activityId);
		UserSlotExchangeActivity userActivityModel = userSlotExchangeActivityDao.getUserSlotExchangeActivity(userActivityVo);
		
		Long activityStatus = startDateToEndate(userActivityModel.getGmtActivityStart(), userActivityModel.getGmtActivityEnd());
		
		if(activityStatus.intValue() == 0){
			status = 6L;
		}else if (cellularPhone != null && isCellularPhone(cellularPhone)) {
			
			ViewUserSlotExchange view = new ViewUserSlotExchange();
			view.setCellularPhone(cellularPhone);
			view.setActivityId(activityId);
			ViewUserSlotExchange phoneModel = userSlotExchangeDao.getViewUserSlotExchange(view);
			
			view = new ViewUserSlotExchange();
			view.setExchangeNumber(exchangeNumber);
			view.setActivityId(activityId);
			ViewUserSlotExchange slotExchange = userSlotExchangeDao.getViewUserSlotExchange(view);
			
			if(phoneModel == null){
				//手機號碼不存在
				if(slotExchange != null){
					//兌換碼存在
					
					Integer isBinding = slotExchange.getIsBinding().intValue();
					Integer isAward = slotExchange.getIsAward().intValue();
					Long exchangeAmount = slotExchange.getExchangeAmount();
					
					if(isBinding == 0){
						//兌換碼未領獎
						occupancyNormal(exchangeNumber, cellularPhone);
						insertSlotExchangeLog(activityId, cellularPhone, exchangeNumber, exchangeAmount, UserSlotExchangeLog.STATUS_SUCCESS, "綁定獎金");
						status = 1L;
					}else if(isAward == 2){
						//兌換碼已兌獎
						response.setExchangeAmount(slotExchange.getExchangeAmount());
						response.setExchangeNumber(slotExchange.getExchangeNumber());
						response.setExchangePrize(slotExchange.getExchangePrize());
						response.setCellularPhone(cellularPhone);
						status = 4L;
					}
				}else{
					//兌換碼不存在
					status = 5L;
				}
			}else{
				
				Integer oldIsBinding = phoneModel.getIsBinding().intValue();
				
				if(oldIsBinding != 2){
					//手機號碼存在且並未註冊成功
					String oldExchangeNumber = phoneModel.getExchangeNumber();
					Long oldExchangeAmount = phoneModel.getExchangeAmount();
					
					if(slotExchange != null){
						//兌換碼存在
						Long exchangeAmount = slotExchange.getExchangeAmount();
						
						occupancyRestart(oldExchangeNumber);
						insertSlotExchangeLog(activityId, cellularPhone, oldExchangeNumber, oldExchangeAmount, UserSlotExchangeLog.STATUS_SUCCESS, "放棄獎金");
						occupancyNormal(exchangeNumber, cellularPhone);
						insertSlotExchangeLog(activityId, cellularPhone, exchangeNumber, exchangeAmount, UserSlotExchangeLog.STATUS_SUCCESS, "綁定獎金");
						status = 1L;
					}else{
						//兌換碼不存在
						status = 5L;
					}
				}else{
					//手機號碼已經註冊過
					status = 3L;
				}
			}
		} else {
			//手機格式錯誤
			status = 2L;
		}
		
		response.setStatus(status);
		
		return response;
	}
	
	private void occupancyNormal(String exchangeNumber, String cellularPhone){
		logger.info("正常領獎流程");
		UserSlotExchange vo = new UserSlotExchange();
		vo.setExchangeNumber(exchangeNumber);
		vo.setCellularPhone(cellularPhone);
		vo.setIsBinding(UserSlotExchange.IS_BINDING_ONGOING);
		vo.setGmtCreate(new Date());
		vo.setGmtBindingDate(new Date());
		userSlotExchangeDao.updateUserSlotExchange(vo);
	}
	
	private void occupancyRestart(String oldExchangeNumber){
		logger.info("覆重新領獎流程");
		UserSlotExchange vo = new UserSlotExchange();
		vo.setExchangeNumber(oldExchangeNumber);
		vo.setGmtCreate(new Date());
		vo.setGmtBindingDate(new Date());
		userSlotExchangeDao.updateUserSlotExchangeByRestart(vo);
	}
	
	@Override
	public UserSlotOccupancySaveResponse occupancySave(Long userId, String cellularPhone, String activityType) {
		UserSlotOccupancySaveResponse response = new UserSlotOccupancySaveResponse();

		logger.info("-userId:"+userId);
		logger.info("-cellularPhone:"+cellularPhone);
		logger.info("-activityType:"+activityType);
		
		if(userId == null || cellularPhone == null || activityType == null){
			return response;
		}
		
		Long activityId = changeActivityType(activityType);
		
		Long status = 0L;

		UserSlotExchangeActivity userActivityVo = new UserSlotExchangeActivity();
		userActivityVo.setId(activityId);
		UserSlotExchangeActivity userActivityModel = userSlotExchangeActivityDao.getUserSlotExchangeActivity(userActivityVo);
		
		Long activityStatus = startDateToEndate(userActivityModel.getGmtActivityStart(), userActivityModel.getGmtActivityEnd());
		
		if(activityStatus.intValue() == 0){
			status = 5L;
		}else if(cellularPhone != null && isCellularPhone(cellularPhone)) {
			ViewUserSlotExchange vo = new ViewUserSlotExchange();
			vo.setCellularPhone(cellularPhone);
			vo.setActivityId(activityId);
			ViewUserSlotExchange userSlotExchange = userSlotExchangeDao.getViewUserSlotExchange(vo);

			Integer isBinding = userSlotExchange.getIsBinding().intValue();
			String exchangeNumber = userSlotExchange.getExchangeNumber();
			Long exchangeAmount = userSlotExchange.getExchangeAmount();
			
			//驗證該手機號碼是否有綁定
			if (isBinding == 1) {
				
				Map<String, Object> map = new HashMap();
				String content = "您的验证码为："+exchangeNumber+",请到指定页面完成验证.【宝开】";
				String phoneXml = httpJsonClient.invokeHttpXml(sendNewsletters+"&mobile="+cellularPhone+"&content="+content,map);
				
//				String phoneXml = "AAA<returnstatus>Success</returnstatus>AAA";
				
				if(phoneXml.indexOf("<returnstatus>Success</returnstatus>") > 0){
					// 更新該筆獎金
					UserSlotExchange updateVo = new UserSlotExchange();
					updateVo.setUserId(userId);
					updateVo.setIsBinding(UserSlotExchange.IS_BINDING_SUCCESS);
					updateVo.setExchangeNumber(exchangeNumber);
					updateVo.setGmtUpdate(new Date());
					userSlotExchangeDao.updateUserSlotExchange(updateVo);
					
					insertSlotExchangeLog(activityId, cellularPhone, exchangeNumber, exchangeAmount, UserSlotExchangeLog.STATUS_SUCCESS, "領取獎金");
					status = 1L;
				}else{
					//系統發送手機簡訊失敗
					status = 4L;
				}
			} else {
				//手機號碼未綁定
				status = 3L;
			}
		} else {
			status = 2L;
		}

		response.setStatus(status);

		return response;
	}

	@Override
	public UserSlotExchangeModifyResponse modifyExchange(Long type, String exchangeNumber) {
		
		UserSlotExchangeModifyResponse response = new UserSlotExchangeModifyResponse();

		if(type == null || exchangeNumber == null){
			return response;
		}
		
		Long status = 0L;
		
		ViewUserSlotExchange viewVo = new ViewUserSlotExchange();
		viewVo.setExchangeNumber(exchangeNumber);
		ViewUserSlotExchange userSlotExchange = userSlotExchangeDao.getViewUserSlotExchange(viewVo);
		
		if(userSlotExchange != null){
			
			Long activityId = userSlotExchange.getActivityId();
			String cellularPhone = userSlotExchange.getCellularPhone();
			Long exchangeAmount = userSlotExchange.getExchangeAmount();
			
			
			UserSlotExchange userVo = new UserSlotExchange();
			userVo.setExchangeNumber(exchangeNumber);
			userVo.setType(type);
			userSlotExchangeDao.updateUserSlotExchange(userVo);
			
			String note = "";
			
			if(type.intValue() == 1){
				note = "選擇A方式獲取";
			}else if(type.intValue() == 2){
				note = "選擇B方式獲取";
			}
			
			insertSlotExchangeLog(activityId, cellularPhone, exchangeNumber, exchangeAmount, UserSlotExchangeLog.STATUS_SUCCESS, note);
			status = 1L;
		}
		
		response.setStatus(status);
		
		return response;
	}
	
	@Override
	public UserSlotExchangeCheckResponse exchangeCheck(Long userId) {
		UserSlotExchangeCheckResponse response = new UserSlotExchangeCheckResponse();
		
		Long status = 0L;
		Long type = 0L;
		
		ViewUserSlotExchange vo = new ViewUserSlotExchange();
		vo.setUserId(userId);
		ViewUserSlotExchange userSlotExchange = userSlotExchangeDao.getViewUserSlotExchange(vo);
		
		if(userSlotExchange != null){
			
			Long activityStatus = startDateToEndate(userSlotExchange.getGmtActivityStart(),userSlotExchange.getGmtActivityEnd());
			Integer isAward = userSlotExchange.getIsAward().intValue();
			type = userSlotExchange.getType();
			String exchangeNumber = userSlotExchange.getExchangeNumber();
			Long exchangeAmount = userSlotExchange.getExchangeAmount();
			
			if(activityStatus.intValue() == 0){
				//活動結束
				status = 2L;
			}else if(isAward != 0){
				//已兌換
				status = 1L;
			}
			
			response.setExchangeNumber(exchangeNumber);
			response.setExchangeAmount(exchangeAmount);
		}
		
		response.setStatus(status);
		response.setType(type);
		
		return response;
	}
	
	/**
	 * 修改該筆兌換碼USER_SLOT_EXCHANGE.IS_AWARD狀態為兌獎 新增USER_SLOT_EXCHANGE_LOG紀錄
	 * @throws Exception 
	 * @throws FundChangedException 
	 */
	@Override
	public UserSlotExchangeResponse exchange(Long userId, String exchangeNumber, String clientIp) throws FundChangedException, Exception {
		UserSlotExchangeResponse response = new UserSlotExchangeResponse();

		if(userId == null || exchangeNumber == null || clientIp == null){
			return response;
		}
		
		String date = dateChangeStr(new Date(), "-");
		String str = "slotExchange_ip_"+date+"_"+clientIp;
		
//		Integer q = 0;
		String vlaue = redisService.get(str);
		if(vlaue != null && !vlaue.equals("")){
//			q = Integer.parseInt(vlaue);
		}
		
//		logger.info("=q:"+q);
		
		//61.220.138.44
//		if(q > 5){
//			response.setStatus(5L);
//			return response;
//		}
		
		ViewUserSlotExchange viewVo = new ViewUserSlotExchange();
		viewVo.setExchangeNumber(exchangeNumber);
		ViewUserSlotExchange userSlotExchange = userSlotExchangeDao.getViewUserSlotExchange(viewVo);
		
		if(userSlotExchange == null){
			response.setStatus(0L);
//			q = q + 1;
//			redisService.set(str, q.toString(), 259200);
			return response;
		}
		
		Long activityStatus = startDateToEndate(userSlotExchange.getGmtActivityStart(), userSlotExchange.getGmtActivityEnd());
		
		if(activityStatus == 0){
			response.setStatus(4L);
			return response;
		}
		//驗證改獎項是否已兌換
		
		Long isAward = userSlotExchange.getIsAward();
		String cellularPhone = userSlotExchange.getCellularPhone();
		Long exchangeAmount = userSlotExchange.getExchangeAmount();
		
		UserCustomer user = userCustomerDao.getById(userId);
		
		UserSlotExchange vo;
		
		if(user != null && cellularPhone.equals(user.getCellphone())){
			if (isAward == 0 || isAward == 1) {

				//修改兌換中狀態
				vo = new UserSlotExchange();
				vo.setExchangeNumber(exchangeNumber);
				vo.setIsAward(UserSlotExchange.IS_AWARD_ONGOING);
				vo.setGmtCreate(new Date());
				userSlotExchangeDao.updateUserSlotExchange(vo);

				//轉帳Start
				Boolean transferStatus = systemTransferPt(userSlotExchange);
				//轉帳End

				logger.info("=transferStatus:"+transferStatus);
				if(transferStatus){
					//修改兌換成功狀態
					Date newDate = new Date();
					vo = new UserSlotExchange();
					vo.setExchangeNumber(exchangeNumber);
					vo.setIsAward(UserSlotExchange.IS_AWARD_SUCCESS);
					vo.setGmtCreate(newDate);
					vo.setGmtAwardDate(newDate);
					userSlotExchangeDao.updateUserSlotExchange(vo);
					
					//增加一筆拉霸機成功Log
					insertSlotExchangeLog(userSlotExchange.getActivityId(), cellularPhone, exchangeNumber, exchangeAmount, UserSlotExchangeLog.STATUS_SUCCESS, "收到註冊禮金");
				}else{
					//兌換失敗
					vo = new UserSlotExchange();
					vo.setExchangeNumber(exchangeNumber);
					vo.setIsAward(UserSlotExchange.IS_AWARD_FAIL);
					vo.setGmtCreate(new Date());
					userSlotExchangeDao.updateUserSlotExchange(vo);
					
					//增加一筆拉霸機Log
					insertSlotExchangeLog(userSlotExchange.getActivityId(), cellularPhone, exchangeNumber, exchangeAmount, UserSlotExchangeLog.STATUS_FAIL, "收到註冊禮金");
				}
				
				response.setStatus(1L);
			} else {
				response.setStatus(2L);
			}
		}else{
			//與註冊手機不一致
			response.setStatus(3L);
		}

		return response;
	}

	@Override
	public UserSlotExchangeByUserResponse getSlotExchangeByUser(Long userId) {
		ViewUserSlotExchange vo = new ViewUserSlotExchange();
		vo.setUserId(userId);
		vo.setActivityId(1L);
		ViewUserSlotExchange model = userSlotExchangeDao.getViewUserSlotExchange(vo);
		UserSlotExchangeByUserResponse response = model != null ? changeUserSlotExchangeByUserResponse(model) : new UserSlotExchangeByUserResponse();
		return response;
	}

	private Boolean systemTransferPt(ViewUserSlotExchange model) throws FundChangedException, Exception {

//		model.setUserId(1300498L);
		
		Boolean systemStatus = null;
		Long amount = 200000L;
		
		String sn = snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TFTP, model.getUserId());
		
		//加錢
		FundGameVo fundGameVo = new FundGameVo(FundModel.PM.PGXX.ITEMS.PGXX_3, model.getUserId(), amount, sn, true);
		fundGameVo.setSn(sn);
		fundGameVo.setNote("拉霸机活动礼金");
		fundChangeService.action(fundGameVo);
		
		// 當發生系統轉帳時 補上一筆 轉出 到Pt 的帳變 再轉出
		PtSysTransferRequest ptRequest = new PtSysTransferRequest();
		ptRequest.setAmount(amount);
		ptRequest.setDirect(0);
		ptRequest.setUserID(model.getUserId());
		UserFund fund = fundChangeService.getUserFund(model.getUserId());
		Long balance = fund.getBal();
		ptRequest.setBal(balance);
		ptRequest.setSn(sn);
		ptRequest.setManualSn(sn);
		ptRequest.setType(1);
		Response<FundSysResult> response = null;
		
		try {
			logger.info("pt checkfund error" + ptRequest.getSn());
//			response = httpPtClient.invokeHttp(ptURL + ptsystransfer, ptRequest, new TypeReference<Response<FundSysResult>>() {});
			response = httpPtClient.invokeHttp(ptURL+"/fund/systransfer", ptRequest, new TypeReference<Response<FundSysResult>>() {});
			systemStatus = true;
		} catch (java.net.SocketTimeoutException e) {
			logger.error("pt systransfer error");

			systemStatus = false;

			Thread.sleep(1000);
			PtcheckFundRequest ptCheck = new PtcheckFundRequest();
			ptCheck.setSn(sn);
			response = httpPtClient.invokeHttp(ptURL + ptchecktranfer, ptCheck, new TypeReference<Response<FundSysResult>>() {});
			if (response.getBody().getResult().getSucees() != 1) {
				logger.error("pt checkfund error");
				throw e;
			}
		}
		int status = response.getBody().getResult().getSucees();
		String rcvAccount = response.getBody().getResult().getFfAccount();
		if (status == SysFundStatusEnum.FAIL_NO_USER.getValue()) {
			logger.error("pt systransfer error:not pt user");
			throw new FundPTNoUserException(rcvAccount);
		}
		FundGameVo fundvo = new FundGameVo(FundModel.TF.TAIX.ITEMS.TFTP,
				model.getUserId(), amount, sn, true,
				"转至 子系統 " + response.getBody().getResult().getPtAccount());

		fundvo.setSn(sn);
		fundChangeService.action(fundvo);

		return systemStatus;
	}

	private void insertSlotExchangeLog(Long activityId, String cellularPhone, String exchangeNumber, Long exchangeAmount, Long status, String memo){
		UserSlotExchangeLog logVo = new UserSlotExchangeLog();
		logVo.setCellularPhone(cellularPhone);
		logVo.setExchangeNumber(exchangeNumber);
		logVo.setStatus(status);
		logVo.setActivityId(activityId);

		String note = cellularPhone + memo + exchangeAmount + "元";
		logVo.setNote(note);
		logVo.setGmtCreate(new Date());
		userSlotExchangeLogDao.insertUserSlotExchangeLog(logVo);
	}
	
	private Integer randomSlot() {
		Random ran = new Random();
		Integer randomIndex = ran.nextInt(10);
		return randomIndex;
	}

	private UserSlotExchangeByUserResponse changeUserSlotExchangeByUserResponse(ViewUserSlotExchange vo) {
		UserSlotExchangeByUserResponse response = new UserSlotExchangeByUserResponse();
		response.setExchangeNumber(vo.getExchangeNumber());
		response.setUserId(vo.getUserId());
		response.setExchangeAmount(vo.getExchangeAmount());
		response.setIsBinding(vo.getIsBinding());
		response.setIsAward(vo.getIsAward());
		response.setSendStatus(vo.getSendStatus());
		response.setCellularPhone(vo.getCellularPhone());
		return response;
	}

	private Boolean isCellularPhone(String cellularPhone) {

		Boolean isCellularPhone = false;

		if (cellularPhone.matches("[0-9]{11}")) {
			isCellularPhone = true;
		}

		return isCellularPhone;
	}
	
	private Long changeActivityType(String type){
		
		Long value = null;
		
		if(type.equals("PT_NEW_ONE")){
			value = 1L;
		}
		
		return value;
	}
	
	
	private Long startDateToEndate(Date starDate, Date endDate){
		Long status = 0L;
		Date nowDate = new Date();
		
		if (starDate.before(nowDate) && endDate.after(nowDate)) {
			status = 1L;
		}
		
		return status;
	}
	
	private String dateChangeStr(Date temp, String style) {

		String str = "";

		if (temp == null || style == null) {
			return str;
		}
		
		String formatStr = "%02d";
		
		Integer year = temp.getYear()+1900;
		String month = String.format(formatStr, (temp.getMonth()+1));
		String date = String.format(formatStr, temp.getDate());
		String hours = String.format(formatStr, temp.getHours());
		String minutes = String.format(formatStr, temp.getMinutes());
		String seconds = String.format(formatStr, temp.getSeconds());
		
		str = year + style + month + style + date;
		return str;
	}
	
	private Date startDateByDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	private Date endDateByDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
}
