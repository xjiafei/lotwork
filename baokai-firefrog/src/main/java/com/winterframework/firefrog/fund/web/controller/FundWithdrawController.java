package com.winterframework.firefrog.fund.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.AccountTool;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.config.web.controller.dto.WithdralDtoUser;
import com.winterframework.firefrog.fund.dao.IFundMowPayDao;
import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawLog;
import com.winterframework.firefrog.fund.dao.vo.WithdrawWhiteList;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.entity.WithdrawAppeal;
import com.winterframework.firefrog.fund.enums.FundLogEnum;
import com.winterframework.firefrog.fund.enums.FundLogEnum.LogModel;
import com.winterframework.firefrog.fund.enums.FundLogEnum.WITHDRAW_STATUS;
import com.winterframework.firefrog.fund.exception.FundBalanceShortageException;
import com.winterframework.firefrog.fund.exception.FundChangeProcessException;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.exception.FundWithdrawHighException;
import com.winterframework.firefrog.fund.exception.FundWithdrawLowException;
import com.winterframework.firefrog.fund.exception.FundWithdrawTooMuchException;
import com.winterframework.firefrog.fund.service.IBankCardService;
import com.winterframework.firefrog.fund.service.IFundMowService;
import com.winterframework.firefrog.fund.service.IFundService;
import com.winterframework.firefrog.fund.service.IFundWithdrawLogService;
import com.winterframework.firefrog.fund.service.IFundWithdrawService;
import com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher;
import com.winterframework.firefrog.fund.service.IWithdrawAppealService;
import com.winterframework.firefrog.fund.service.IWithdrawWhiteListService;
import com.winterframework.firefrog.fund.service.impl.mow.MowQuerywithDrawResponse;
import com.winterframework.firefrog.fund.service.impl.mow.MownecumWithdrawResponseData;
import com.winterframework.firefrog.fund.service.impl.mow.WithdrawConfirmRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.util.MowNumTool;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.CountResultPager;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRequest;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.fund.web.dto.ExceptionRefundRequest;
import com.winterframework.firefrog.fund.web.dto.QueryFundWithdrawOrderRequest;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.firefrog.fund.web.dto.WithdrawAppealStruc;
import com.winterframework.firefrog.fund.web.dto.WithdrawApplyRequest;
import com.winterframework.firefrog.fund.web.dto.WithdrawApplyResponse;
import com.winterframework.firefrog.fund.web.dto.WithdrawAuditRequest;
import com.winterframework.firefrog.fund.web.dto.WithdrawAuditResponse;
import com.winterframework.firefrog.fund.web.dto.WithdrawInitRequest;
import com.winterframework.firefrog.fund.web.dto.WithdrawInitResponse;
import com.winterframework.firefrog.fund.web.dto.WithdrawRemarkRequest;
import com.winterframework.firefrog.fund.web.dto.WithdrawStruc;
import com.winterframework.firefrog.fund.web.dto.WithdrawWhiteListRequest;
import com.winterframework.firefrog.global.dao.vo.GlobalGrayListVO;
import com.winterframework.firefrog.global.service.IGlobalGrayListService;
import com.winterframework.firefrog.user.dao.impl.UserCustomerDaoImpl;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.ILevelRecycleService;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

/**
 * 
* @ClassName: WithdrawController 
* @Description: 提现申请控制类 
* @author Richard 
* @date 2013-6-28 下午6:00:45 
*
 */
@Controller("withdrawController")
@RequestMapping("/fund")
public class FundWithdrawController extends BaseUserController {

	private static final Logger log = LoggerFactory.getLogger(FundWithdrawController.class);
	private final long SUCCESS = 1L;
	private final long FAIL = 0L;
	
	@Autowired
	private ConfigUtils configUtils;
	
	@Resource(name = "SNUtil")
	private ISNGenerator snUtil;
	
	@PropertyConfig(value = "querywithdral")
	private String querywithdral;
	
	@PropertyConfig(value = "mownum_url")
	private String mow_rul;
	
	@PropertyConfig(value = "company_id")
	private Long company_id;
	
	@Resource(name = "RedisClient")
	private RedisClient redis;
	
	@Resource(name = "fundWithdrawService")
	private IFundWithdrawService fundWithdrawService;

	@Resource(name = "bankCardServiceImpl")
	private IBankCardService bankCardService;
	
	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient mcwClient;

	@Resource(name = "fundChargeServiceImpl")
	private IFundService<FundOrder> fundService;

	@Resource(name = "configServiceImpl")
	private IConfigService configService;

	@Resource(name = "fundMowPayDao")
	private IFundMowPayDao fundMowPayDao;
	
	@Resource(name = "withdrawWhiteListServiceImpl")
	private IWithdrawWhiteListService withdrawWhiteListService;
	
	
	@Resource(name = "levelRecycleServiceImpl")
	private ILevelRecycleService levelRecycleService;

	@Resource(name = "withdrawAppealServiceImpl")
	private IWithdrawAppealService withdrawAppealServiceImpl;
	
	@PropertyConfig(value = "platVersion")
	private String platVersion;
	
	@Resource(name="fundWithdrawLogServiceImpl")
	private IFundWithdrawLogService fundWithdrawLogServiceImpl;
	
	@Resource(name = "globalGrayListServiceImpl")
	private IGlobalGrayListService globalGrayListService;
	
	@Resource(name = "fundWithdrawDaoImpl")
	private IFundWithdrawDao fundWithdrawDao;
	@Resource(name = "paymentCallbackDispatcher")
	private IPaymentCallbackDispatcher paymentCallbackDispatcher;
	@Autowired
	private BeginMissionService beginMissionService;
	/**
	 * 
	* @Title: initFundWithdraw 
	* @Description: 用户提现初始化方法
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/initFundWithdraw")
	@ResponseBody
	public Response<WithdrawInitResponse> init(@RequestBody @ValidRequestBody Request<WithdrawInitRequest> request)
			throws Exception {

		Response<WithdrawInitResponse> response = new Response<WithdrawInitResponse>(request);

		try {

			if (null != request.getBody()) {
				WithdrawInitRequest initRequest = request.getBody().getParam();
				WithdrawInitResponse initWithdraw = new WithdrawInitResponse();
				
				BankCardQueryRequest br = new BankCardQueryRequest();
				br.setUserId(initRequest.getUserId());
				br.setBindCardType(initRequest.getBindcardType());
				//set USER_BANK_STRUC
				//List<BankCard> list = bankCardService.queryBoundBankCard(initRequest.getUserId(), null);
				List<BankCard> list = bankCardService.queryBoundBankCardByRequest(br);
				List<UserBankStruc> userBankList = new ArrayList<UserBankStruc>();

				for (BankCard bc : list) {

					UserBankStruc userBankStruc = new UserBankStruc();
					userBankStruc.setBankAccount(bc.getAccountHolder());
					userBankStruc.setBranchName(bc.getSubBranch());
					userBankStruc.setBindDate(bc.getGmtCreated());
					userBankStruc.setBankId(bc.getBank().getId());
					userBankStruc.setBankNumber((bc.getBankCardNo()));
					userBankStruc.setMcBankId(bc.getMownecumId());
					userBankStruc.setId(bc.getId());
					userBankStruc.setBindcardType(bc.getBindcardType());
					userBankList.add(userBankStruc);
				}
				initWithdraw.setUserBankStruc(userBankList);

				//set bal
				UserFund userFund = fundService.getUserFund(initRequest.getUserId());
				Long availBal = userFund.getDisableAmt();
				initWithdraw.setAvailBal(availBal > userFund.getBal() ?userFund.getBal():availBal);
				initWithdraw.setBal(userFund.getBal());

				//set Wi
				WithdralDtoUser withDraw = configUtils.getWITHDRAW();

				//set withdrawStruc
				initWithdraw.setWithdrawStruc(JsonMapper.nonAlwaysMapper().toJson(withDraw));
				Long isVip = request.getBody().getParam().getVipLvl();
				Long wt = withDraw.getUser().getTime();
				if (isVip != null && isVip > 0) {
					wt = withDraw.getVip().getTime();
				}
				initWithdraw.setAvailWithdrawTime(wt
						- fundWithdrawService.getAvailWithdrawTime(initRequest.getUserId()));

				response.setResult(initWithdraw);

			}
		} catch (Exception e) {
			log.error("Fund Withdraw Initialization error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: fundWithdrawApply 
	* @Description: 用户提现申请
	* @param reqeust
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/applyFundWithdraw")
	@ResponseBody
	public Object apply(@RequestBody @ValidRequestBody Request<WithdrawApplyRequest> reqeust) throws Exception {

		Response<WithdrawApplyResponse> response = new Response<WithdrawApplyResponse>(reqeust);

		try {
			if (null != reqeust.getBody()) {
				if (redis.acquireLock("APPLYFUND" + reqeust.getHead().getUserId(), 3000)){
					User user = UserTools.getUserFromHead(reqeust);
					WithdrawApplyRequest applyRequest = reqeust.getBody().getParam();
					
					//提現分拆流程
					List<FundWithdrawOrder> orders = fundWithdrawService.seperateDrawApply(user, applyRequest);
					WithdrawApplyResponse result = new WithdrawApplyResponse();
					result.setOrders(orders);
					redis.releaseLock("APPLYFUND" + reqeust.getHead().getUserId());
					response.setResult(result);
				}
			}

		} catch (FundWithdrawLowException e) {
			log.error(FundWithdrawLowException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundWithdrawHighException e) {
			log.error(FundWithdrawHighException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundBalanceShortageException e) {
			log.error(FundBalanceShortageException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundWithdrawTooMuchException e) {
			log.error(FundWithdrawTooMuchException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundChangedException e) {
			log.error(FundChangedException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (Exception e) {
			log.error("Fund Withdraw Apply error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: queryFundWithdrawOrder 
	* @Description: 查询提现申请订单
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryFundWithdrawOrder")
	@ResponseBody
	public Response<List<WithdrawStruc>> queryFundWithdrawOrder(
			@RequestBody Request<QueryFundWithdrawOrderRequest> request) throws Exception {

		Response<List<WithdrawStruc>> response = new Response<List<WithdrawStruc>>(request);
		try {
			
			if (null != request.getBody()) {

				PageRequest<QueryFundWithdrawOrderRequest> pageRequest = PageConverterUtils.getRestPageRequest(request
						.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());
				QueryFundWithdrawOrderRequest req = request.getBody().getParam();
				boolean isNoUserApplyDate = req.getFromDate() == null && req.getToDate() == null;
				boolean isNoNoticeMowDate = req.getFromNoticeMowDate() == null && req.getToNoticeMowDate() == null;
				boolean isNoMowDate = req.getFromMowDate() == null && req.getToMowDate() == null;
				boolean isNoYiShenStartDate = req.getYishenStartBegin() == null && req.getYishenStartEnd()==null;
				boolean isNoYiShenEndDate = req.getYishenEndBegin() == null && req.getYishenEndEnd()==null;
				boolean isNoOperatingTime = req.getFromOperatingDate() == null && req.getToOperatingDate() == null;
				//若是都无输入日期类别条件，自动带入预设天数
				if (isNoUserApplyDate && isNoNoticeMowDate && isNoMowDate
						&& isNoYiShenStartDate && isNoYiShenEndDate && isNoOperatingTime) {
					req.setFromDate(DateUtils.getStartTimeOfDate(DateUtils
							.addDays(DateUtils.currentDate(), -2)));
					req.setToDate(DateUtils.getEndTimeOfCurrentDate());
				}
				pageRequest.setSearchDo(req);
				pageRequest.setSortColumns("APPLY_TIME DESC,ID DESC");

				request.getBody().getParam().setCurrApprer(null);
				Page<FundWithdrawOrder> withdrawList = fundWithdrawService.queryFundWithdrawList(pageRequest);
				final String userAccount = request.getHead().getUserAccount();
				log.debug(" list totalCount : " +withdrawList.getTotalCount());
				//增加查詢時,判斷目前該資料是否有人審核
				if(withdrawList.getTotalCount()>0){
					List<FundWithdrawOrder> results= Lists.transform(withdrawList.getResult(), new Function<FundWithdrawOrder , FundWithdrawOrder>(){
						@Override
						public FundWithdrawOrder apply(FundWithdrawOrder order) {
							//有拆單才做isShowReview的判斷
							if(order.getSeperateCount() > 1){
								order.setIsShowReview(checkIsShowReview(order.getRootSn()));
							}else{
								order.setIsShowReview(true);
							}
							
							log.debug("SN : "+ order.getSn() + ",isShowReview : " + order.getIsShowReview());
							String nowCheckUser = redis.get("under_"+order.getSn());
							
							if(StringUtils.isEmpty(nowCheckUser) || userAccount.equals(nowCheckUser)){
								order.setIsCheck(false);								
							}else{
								order.setIsCheck(true);
								order.setNowCheckUser(nowCheckUser);
							}
							return order;
						}
					});
					withdrawList.setResult(results);
					
				}
				response = fillResponse(response, withdrawList, request);

			}

		} catch (Exception e) {
			log.error("Query Fund Withdraw Order error:", e);
			throw e;
		}
		return response;
	}
	
	
	/**
	 * 
	* @Title: queryReFundWithdrawOrder
	* @Description: 查询欲退款订单(Redis)
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryReFundWithdrawOrder")
	@ResponseBody
	public Response<List<WithdrawStruc>> queryReFundWithdrawOrder(
			@RequestBody Request<QueryFundWithdrawOrderRequest> request) throws Exception {

		Response<List<WithdrawStruc>> response = new Response<List<WithdrawStruc>>(request);
		
		boolean redResault=redis.acquireLock("/fund/queryReFundWithdrawOrder"+request.getBody().getParam().getSn(),90000);	
		
		if(redResault==true){			
			redis.set("/fund/queryReFundWithdrawOrder"+request.getBody().getParam().getSn()+"nowOperater",request.getBody().getParam().getCurrApprer(),90000);
		}
		else{			
			if(redis.get("/fund/queryReFundWithdrawOrder"+request.getBody().getParam().getSn()+"nowOperater")==null){
				redResault=false;
			} 
			else{
				String tempValue=redis.get("/fund/queryReFundWithdrawOrder"+request.getBody().getParam().getSn()+"nowOperater");
				redResault=tempValue.equals(request.getBody().getParam().getCurrApprer());
			}
		}
		try {
			
			if (null != request.getBody()) {				
				PageRequest<QueryFundWithdrawOrderRequest> pageRequest = PageConverterUtils.getRestPageRequest(request
						.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());
				QueryFundWithdrawOrderRequest req = request.getBody().getParam();
				if(req.getFromDate() == null && req.getToDate() == null && req.getFromNoticeMowDate() == null && req.getToNoticeMowDate() == null && req.getFromMowDate() == null && req.getToMowDate() == null){
					req.setFromDate(DateUtils.getStartTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -2)));
					req.setToDate(DateUtils.getEndTimeOfCurrentDate());
				}
				pageRequest.setSearchDo(req);
				pageRequest.setSortColumns("APPLY_TIME DESC");

				request.getBody().getParam().setCurrApprer(null);
				Page<FundWithdrawOrder> withdrawList = fundWithdrawService.queryReFundWithdrawList(pageRequest);
				
				response = fillResponse(response, withdrawList, request);

			}

		} catch (Exception e) {
			log.error("Query Fund Withdraw Order error:", e);
			throw e;
		}
		response.getBody().getResult().get(0).setRedisResault(redResault);
		return response;
	}
	
	/**
	 * 
	* @Title: checkAndUpdateWithDrawMowStatus 
	* @Description: 查询提现申请订单目前狀態
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/checkAndUpdateWithDrawMowStatus")
	@ResponseBody
	public Response<WithdrawStruc> checkAndUpdateWithDrawMowStatus(
			@RequestBody Request<QueryFundWithdrawOrderRequest> request) throws Exception {

		Response<WithdrawStruc> response = new Response<WithdrawStruc>(request);
		try {
			
			if (null != request.getBody()) {
				QueryFundWithdrawOrderRequest param = request.getBody().getParam();
				String mcSn = param.getMownecumOrderNum();
				String sn = param.getCompanyOrderNum();
				//獲取狀態分流:查詢提現渠道為DP或通匯
				FundWithdraw wdOrder = fundWithdrawLogServiceImpl.getFundWithdrawByMowNum(sn);
				WithdrawStruc withdraw = new WithdrawStruc();
				if(wdOrder.getWithdrawMode().intValue() == 1){
					MowQuerywithDrawResponse mowOrder = fundWithdrawService.queryMowWithdrawOrderStatus(mcSn, sn);
					if(mowOrder!=null){
						fundWithdrawService.updateWithdrawalResult(mowOrder);
						PageRequest<QueryFundWithdrawOrderRequest> pageRequest = PageConverterUtils.getRestPageRequest(0, 1);
						pageRequest.setSearchDo(param);
						Page<FundWithdrawOrder> withdrawPage = fundWithdrawService.queryReFundWithdrawList(pageRequest);
						if(withdrawPage!=null&&withdrawPage.getTotalCount()>0){
							List<WithdrawStruc> list = convertList(withdrawPage.getResult());
							withdraw = list.get(0);
							withdraw.setMowApiStatus(String.valueOf(mowOrder.getStatus()));
							response.setResult(withdraw);
						}
						response.getHead().setStatus(SUCCESS);
					}else{
						response.getHead().setStatus(FAIL);
					}
				} else if(wdOrder.getWithdrawMode().intValue() == 2){
					int remitStatus = fundWithdrawService.queryThWithdrawOrderStatus(sn);
					withdraw.setWithdrawMode(2L);
					withdraw.setCurrApprer(wdOrder.getCurrApprer());
					withdraw.setCompanyOrderNum(sn);
					withdraw.setMownecumOrderNum(sn);
					withdraw.setMowApiStatus(String.valueOf(remitStatus));
					response.setResult(withdraw);
					response.getHead().setStatus(SUCCESS);
				} else if(wdOrder.getWithdrawMode().intValue() == 8){
					int status = fundWithdrawService.queryWithdrawOrderStatus(wdOrder.getWithdrawMode().intValue(),sn);
					withdraw.setWithdrawMode(8L);
					withdraw.setCurrApprer(wdOrder.getCurrApprer());
					withdraw.setCompanyOrderNum(sn);
					withdraw.setMownecumOrderNum(sn);
					int wStatus=status==1?3:(status==2?4:2);
					withdraw.setMowApiStatus(String.valueOf(wStatus));
					response.setResult(withdraw);
					response.getHead().setStatus(SUCCESS);
				} else {
					throw new Exception("withdraw status wrong!");
				}
			}

		} catch (Exception e) {
			log.error("checkAndUpdateWithDrawMowStatus error:", e);
			response.getHead().setStatus(FAIL);
		}
		return response;
	}
	
	/**
	 * 
	* @Title: queryWithDrawNowDetail
	* @Description: 查询提现申请订单詳情
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryWithDrawNowDetail")
	@ResponseBody
	public Response<List<WithdrawStruc>> queryWithDrawNowDetail(
			@RequestBody Request<QueryFundWithdrawOrderRequest> request) throws Exception {

		Response<List<WithdrawStruc>> response = new Response<List<WithdrawStruc>>(request);
		try {

			if (null != request.getBody()) {
				log.info("start with now detail");
				PageRequest<QueryFundWithdrawOrderRequest> pageRequest = PageConverterUtils.getRestPageRequest(request
						.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());
				QueryFundWithdrawOrderRequest req = request.getBody().getParam();
				if(req.getFromDate() == null && req.getToDate() == null && req.getFromNoticeMowDate() == null && req.getToNoticeMowDate() == null && req.getFromMowDate() == null && req.getToMowDate() == null){
					req.setFromDate(DateUtils.getStartTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -2)));
					req.setToDate(DateUtils.getEndTimeOfCurrentDate());
				}
				pageRequest.setSearchDo(req);
				pageRequest.setSortColumns("APPLY_TIME DESC");

				request.getBody().getParam().setCurrApprer(null);
				Page<FundWithdrawOrder> withdrawList = fundWithdrawService.queryDetailWithdrawList(pageRequest);

				response = fillResponse(response, withdrawList, request);

			}

		} catch (Exception e) {
			log.error("Query Fund Withdraw Order error:", e);
			throw e;
		}
		return response;
	}
	
	
	

	/**
	 * 
	* @Title: queryFundWithdrawOrder 
	* @Description: 查询提现申请订单
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryFundWithdrawOrderByUserId")
	@ResponseBody
	public Response<List<WithdrawStruc>> queryFundWithdrawOrderByUserId(
			@RequestBody Request<QueryFundWithdrawOrderRequest> request) throws Exception {

		Response<List<WithdrawStruc>> response = new Response<List<WithdrawStruc>>(request);
		try {

			if (null != request.getBody()) {

				PageRequest<QueryFundWithdrawOrderRequest> pageRequest = PageConverterUtils.getRestPageRequest(request
						.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());
//				request.getBody().getParam().setApplyAccount(request.getHead().getUserAccount());
				Long userId = request.getBody().getParam().getUserId();
				if(userId!=null){					
					QueryLevelRecycleHistoryResponse recycleHist = levelRecycleService
							.queryRecycleLastHistory(userId);
					request.getBody().getParam().setRecycleDate(recycleHist.getActivityDate());
				}
				pageRequest.setSearchDo(request.getBody().getParam());
				pageRequest.setSortColumns("APPLY_TIME DESC,ID DESC");
				Page<FundWithdrawOrder> withdrawList = fundWithdrawService.queryFundWithdrawList(pageRequest);

				response = fillResponse(response, withdrawList, request);
			}

		} catch (Exception e) {
			log.error("Query Fund Withdraw Order error:", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * 
	* @Title: queryFundWithdrawOrderBySn 
	* @Description: 查询提现申请订单詳情
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryFundWithdrawOrderBySn")
	@ResponseBody
	public Response<List<WithdrawStruc>> queryFundWithdrawOrderBySn(
			@RequestBody Request<QueryFundWithdrawOrderRequest> request) throws Exception {

		Response<List<WithdrawStruc>> response = new Response<List<WithdrawStruc>>(request);
		try {

			if (null != request.getBody()) {

				PageRequest<QueryFundWithdrawOrderRequest> pageRequest = PageConverterUtils.getRestPageRequest(request
						.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());
				//request.getBody().getParam().setApplyAccount(request.getHead().getUserAccount());
				pageRequest.setSearchDo(request.getBody().getParam());
				pageRequest.setSortColumns("APPLY_TIME DESC");
				Page<FundWithdrawOrder> withdrawList = fundWithdrawService.queryFundWithdrawDetail(pageRequest);

				if("queryprocess".equals(request.getBody().getParam().getFromAction())){
					if(withdrawList.getResult()!=null && withdrawList.getResult().size()>0){
						String withdraw1 = "";
						String withdraw2 = "";
						String withdraw3 = "";
						String withdraw4 = "";
						String withdraw5 = "";						
						
						FundWithdrawOrder order = withdrawList.getResult().get(0);
						final SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy/MM/dd");
						final SimpleDateFormat formatHMS = new SimpleDateFormat("HH:mm:ss");
						
						
						Date applyTime = order.getApplyTime();
						Date beginTime =DateUtils.addSeconds(applyTime, 5);
						withdraw1 = getComposeString(applyTime,formatYMD,formatHMS);
						withdraw2 = getComposeString(beginTime,formatYMD,formatHMS);					

						//撖拇銝?
						if(WithdrawStauts.APPROVING.equals(order.getStauts())){
							Date begin2Time = order.getApprTime();
							withdraw3 = getComposeString(begin2Time,formatYMD,formatHMS);													
						//??
						}else if(WithdrawStauts.REJECT.equals(order.getStauts())){
							Date apprTime = order.getApprTime();
							if(order.getAppr2Time()!=null){
								apprTime = order.getAppr2Time();
							}
							Date prepareReturnTime =DateUtils.addSeconds(apprTime, 5);
							Date returnTime =DateUtils.addSeconds(apprTime, 10);	
							 withdraw3 = getComposeString(apprTime,formatYMD,formatHMS);
							 withdraw4 = getComposeString(prepareReturnTime,formatYMD,formatHMS);
							 withdraw5 = getComposeString(returnTime,formatYMD,formatHMS);
							 
						}else if(WithdrawStauts.APPROVED.equals(order.getStauts())){
							Date apprTime = order.getApprTime();
							if(order.getAppr2Time()!=null){
								apprTime = order.getAppr2Time();
							}
							Date preparePayTime = DateUtils.addSeconds(apprTime, 5);
							
							withdraw3 = getComposeString(apprTime,formatYMD,formatHMS);
							withdraw4 = getComposeString(preparePayTime,formatYMD,formatHMS);
						}else if(WithdrawStauts.SUCCESS.equals(order.getStauts()) || WithdrawStauts.PART.equals(order.getStauts())){
							Date apprTime = order.getApprTime();
							if(order.getAppr2Time()!=null){
								apprTime = order.getAppr2Time();
							}
							Date preparePayTime = DateUtils.addSeconds(apprTime, 5);
							Date successTime = order.getMcNoticeTime();
							withdraw3 = getComposeString(apprTime,formatYMD,formatHMS);
							withdraw4 = getComposeString(preparePayTime,formatYMD,formatHMS);
							withdraw5 = getComposeString(successTime,formatYMD,formatHMS);				
						}else if(WithdrawStauts.FAIL.equals(order.getStauts())){
							Date apprTime = order.getApprTime();
							if(order.getAppr2Time()!=null){
								apprTime = order.getAppr2Time();
							}
							Date cancelTime= order.getMcNoticeTime();
							if(order.getCancelTime()!=null){
								cancelTime= order.getCancelTime();
							}
							Date successTime = DateUtils.addSeconds(cancelTime, 5);
							withdraw3 = getComposeString(apprTime,formatYMD,formatHMS);
							withdraw4 = getComposeString(cancelTime,formatYMD,formatHMS);
							withdraw5 = getComposeString(successTime,formatYMD,formatHMS);						
						}else if(WithdrawStauts.APPROVEDREFUND.equals(order.getStauts())){
							Date apprTime = order.getApprTime();
							if(order.getAppr2Time()!=null){
								apprTime = order.getAppr2Time();
							}
							Date cancelTime= order.getMcNoticeTime(); 
							if(order.getCancelTime()!=null){
								cancelTime= order.getCancelTime();
							}
							Date successRetimeTime= DateUtils.addSeconds(cancelTime, 5);
							withdraw3 = getComposeString(apprTime,formatYMD,formatHMS);
							withdraw4 = getComposeString(cancelTime,formatYMD,formatHMS);
							withdraw5 = getComposeString(successRetimeTime,formatYMD,formatHMS);							
						}
						order.setWithdrawTimeStr1(withdraw1);
						order.setWithdrawTimeStr2(withdraw2);
						order.setWithdrawTimeStr3(withdraw3);
						order.setWithdrawTimeStr4(withdraw4);
						order.setWithdrawTimeStr5(withdraw5);
					}
				}
				
				response = fillResponse(response, withdrawList, request);
			}

		} catch (Exception e) {
			log.error("Query Fund Withdraw Order error:", e);
			throw e;
		}
		return response;
	}
	/**
	 * 
	* @Title: manualWithdraw 
	* @Description: 人工处理 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/manualWithdraw")
	@ResponseBody
	public Response<WithdrawStruc> manualWithdraw(
			@RequestBody Request<WithdrawAuditRequest> request) throws Exception {

		Response<WithdrawStruc> response = new Response<WithdrawStruc>(request);
		if (null != request.getBody()) {
			WithdrawAuditRequest param = request.getBody().getParam();
			String sn = param.getSn();
			FundWithdraw wdOrder = fundWithdrawLogServiceImpl.getFundWithdrawByMowNum(sn);
			if(wdOrder!=null){ 
				fundWithdrawDao.updateFundWithdrawAudingInfo(wdOrder.getId(), param.getApproveAct(), WithdrawStauts.APPROVED.getValue(), null,"人工处理");
				FundWithdrawOrder order= fundWithdrawService.queryFundWithdrawById(wdOrder.getId());
				addAuditWithdrawLog(wdOrder.getId(), WithdrawStauts.APPROVED.getValue(),FundLogEnum.WITHDRAW_STATUS.CHECK_PASS.getText(),order.getApprTime());
			}else{
				response.getHead().setStatus(-1);
			}
		}

		return response;
	}
	/**
	 * 
	* @Title: manualWithdraw 
	* @Description: 人工完成
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/manualFinish")
	@ResponseBody
	public Response<WithdrawStruc> manualFinish(
			@RequestBody Request<QueryFundWithdrawOrderRequest> request) throws Exception {

		Response<WithdrawStruc> response = new Response<WithdrawStruc>(request);
		if (null != request.getBody()) {
			QueryFundWithdrawOrderRequest param = request.getBody().getParam();
			String sn = param.getSn();
			FundWithdraw wdOrder = fundWithdrawLogServiceImpl.getFundWithdrawByMowNum(sn);
			if(wdOrder!=null){
				String str = snUtil.parseBusinessSnDesc(sn);
				WithdrawConfirmRequest confirmRequest=new WithdrawConfirmRequest();
				confirmRequest.setAmount(BigDecimal.valueOf(wdOrder.getWithdrawAmt()).divide(new BigDecimal(10000L)));
				confirmRequest.setCompany_order_num(wdOrder.getSn());
				confirmRequest.setStatus(1L);
				confirmRequest.setOperating_time(DateUtils.format(new Date(), "yyyyMMddHHmmss"));
				paymentCallbackDispatcher.doDispatch(str, confirmRequest);
				
				fundWithdrawLogServiceImpl.saveFundWithLog(sn, LogModel.WITHDRAW, new Date(), FundLogEnum.WITHDRAW_STATUS.DRAW_SUCCESS);
				 //判斷是否為新手進行首提獎勵
				beginMissionService.firstWthdrawAward(wdOrder.getUserId(), wdOrder.getWithdrawAmt());
				//提現成功若為灰名單則移除灰名單
				GlobalGrayListVO isGrayUser = globalGrayListService.queryGlobalGrayListByUserId(wdOrder.getUserId());
				if(isGrayUser != null){
					//用戶為灰名單且審核成功，移除灰名單
					globalGrayListService.deleteGlobalGrayList(wdOrder.getUserId());
				}
			}else{
				response.getHead().setStatus(-1);
			}
		}

		return response;
	}
	private String getComposeString(Date date , SimpleDateFormat formatYMD ,SimpleDateFormat formatHMS){
		date = date==null?new Date():date;
		return formatYMD.format(date)+"<br>"+formatHMS.format(date);
	}
	
	public Response<List<WithdrawStruc>> fillResponse(Response<List<WithdrawStruc>> response, Page list,
			Request<?> request) throws Exception {

		if (null == response) {
			response = new Response<List<WithdrawStruc>>(request);
		}
		
		response.setResult(convertList(list.getResult()));

		CountResultPager pager = new CountResultPager();
		pager.setEndNo(list.getThisPageLastElementNumber());
		pager.setStartNo(list.getThisPageFirstElementNumber());
		pager.setTotal(list.getTotalCount());
		if (list instanceof CountPage) {
			pager.setSum(((CountPage) list).getSum());
		}

		response.setResultPage(pager);

		return response;
	}

	private List<WithdrawStruc> convertList(List<FundWithdrawOrder> list) throws Exception {
		List<WithdrawStruc> withdrawList = new ArrayList<WithdrawStruc>();
		if (list == null || list.isEmpty()) {
			return withdrawList;
		}
		
		for (FundWithdrawOrder order : list) {
			WithdrawStruc struc = new WithdrawStruc();
			struc.setApplyTime(DataConverterUtil.convertDate2Long(order.getApplyTime()));
			struc.setIpAddr(order.getApplyIpAddr());
			struc.setMemo(order.getMemo());
			struc.setStatus(DTOConverter.getWithdrawStatusValue(order.getStauts()));
			struc.setSn(order.getSn());
			struc.setId(order.getId());
			struc.setWithdrawMode(order.getWithdrawMode());
			struc.setWithdrawAmt(order.getWithdrawAmt());
			struc.setRealWithDrawAmt(order.getRealWithdrawAmt());
			//set Bank_struc
			UserBankStruc userBankStruc = (UserBankStruc) DataConverterUtil.convertJson2Object(order.getCardStr(),
					new UserBankStruc().getClass());
			struc.setUserBankStruc(userBankStruc);
			struc.setCurrApprer(order.getCurrApprer());
			struc.setApplyAccount(order.getApplyUser().getAccount());
			if (order.getApprover() != null) {
				struc.setApprAccount(order.getApprover().getUserProfile().getAccount());
				struc.setApprTime(order.getAppr2Time()==null?order.getApprTime():order.getAppr2Time());
			}
			struc.setAttach(order.getAttach());
			struc.setApprBeginStatus(order.getApprBeginStatus());
			struc.setCurrDate(order.getCurrDate());
			struc.setIsVip(order.getIsVip());
			struc.setRiskType(Long.valueOf(order.getRiskType().getIndex()));
			struc.setApprBeginTime(order.getApprBeginTime());
			struc.setAppr2BeginTime(order.getAppr2BeginTime());
			struc.setAppr2Acct(order.getAppr2Acct());
			struc.setAppr2Time(order.getAppr2Time());
			struc.setPassDate(order.getAppr2Time());
			struc.setMowRcvDate(order.getAppr2Time());
			struc.setTopAcc(order.getTopAcc());
			struc.setApChannel(order.getApChannel());
			struc.setApProject(platVersion);
			struc.setCancelAcct(order.getCancelAcct());//99
			struc.setCancelTime(order.getCancelTime());//99
			struc.setRootSn(order.getRootSn());
			struc.setSeperateCount(order.getSeperateCount());
			struc.setTotalDrawAmount(order.getTotalDrawAmount());
			if (order.getMc() != null) {
				struc.setMcNoticeTime((order.getMc().getMcNoticeTime()));
				struc.setMcMemo(order.getMc().getMcMemo());
				struc.setMcSn(order.getMc().getMcSN());
			}
			
			//mow API  Response 狀態專用 
			struc.setCompanyOrderNum(order.getCompanyOrderNum());
			struc.setMownecumOrderNum(order.getMownecumOrderNum());
			struc.setMowApiStatus(order.getMowApiStatus());
			struc.setAmount(order.getAmount());
			struc.setMowDetail(order.getMowDetail());
			struc.setExactTransactionCharge(order.getExactTransactionCharge());
			struc.setMd5Key(order.getMd5Key());
			struc.setMowApiErrorMsg(order.getMowApiErrorMsg());
			struc.setIsCheck(order.getIsCheck());
			struc.setNowCheckUser(order.getNowCheckUser());
			struc.setIsShowReview(order.getIsShowReview());
			struc.setWithdrawTimeStr1(order.getWithdrawTimeStr1());
			struc.setWithdrawTimeStr2(order.getWithdrawTimeStr2());
			struc.setWithdrawTimeStr3(order.getWithdrawTimeStr3());
			struc.setWithdrawTimeStr4(order.getWithdrawTimeStr4());
			struc.setWithdrawTimeStr5(order.getWithdrawTimeStr5());
			struc.setOperatingTime(order.getOperatingTime());
			withdrawList.add(struc);
		}
		return withdrawList;
	}

	/**
	 * 
	* @Title: auditFundWithdraw 
	* @Description: 提现申请审核
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/auditFundWithdraw")
	@ResponseBody
	public Object audit(@RequestBody @ValidRequestBody Request<WithdrawAuditRequest> request) throws Exception {

		Response<WithdrawAuditResponse> response = new Response<WithdrawAuditResponse>(request);

		try {
			log.info("=提现申请审核=/fund/auditFundWithdraw Start:");
			
			Long id = request.getBody().getParam().getId();
			String approveAct = request.getBody().getParam().getApproveAct();
			Long status = request.getBody().getParam().getStatus();
			String memo = request.getBody().getParam().getMemo();
			String attach = request.getBody().getParam().getAttach();
			String isAppeal = request.getBody().getParam().getIsAppeal();
			String appealTips = request.getBody().getParam().getAppealTips();
			
			log.info("id:"+id);
			log.info("approveAct:"+approveAct);
			log.info("status:"+status);
			log.info("memo:"+memo);
			log.info("attach:"+attach);
			log.info("isAppeal:"+isAppeal);
			log.info("appealTips:"+appealTips);
			
			if (null != request.getBody()) {

				WithdrawAuditRequest auditRequest = request.getBody().getParam();
				boolean needmsg = fundWithdrawService.audit(auditRequest.getId(), auditRequest.getApproveAct(), auditRequest.getStatus(),
						auditRequest.getAttach(), auditRequest.getMemo());
				
				FundWithdrawOrder order= fundWithdrawService.queryFundWithdrawById(auditRequest.getId());
				if("Y".equals(order.getIsSeperate()) && auditRequest.getStatus().longValue() == 1){
					List<FundWithdrawOrder> subDraws = fundWithdrawService.queryByRootSn(order.getRootSn());
					for(FundWithdrawOrder subDraw:subDraws){
						addAuditWithdrawLog(subDraw.getId(), auditRequest.getStatus(), auditRequest.getAppealTips(),order.getApprTime());
					}
				}else{
					addAuditWithdrawLog(auditRequest.getId(), auditRequest.getStatus(), auditRequest.getAppealTips(),order.getApprTime());
				}
				//決定要不要產生 申訴單號
				if(request.getBody().getParam().getIsAppeal().equals("1")){													
					List<WithdrawAppeal> withdrawAppealList = withdrawAppealServiceImpl.createAppealSn(request);
				}
				if (WithdrawStauts.REJECT.getValue() == auditRequest.getStatus() && needmsg == true) {
					
					fundWithdrawService.sendMsgaudit(auditRequest.getId());
				}
			}

		} catch(FundChangeProcessException e){
			log.error(FundChangeProcessException.MSG, e);
			response.getHead().setStatus(e.getStatus());			
		} catch (FundChangedException e) {
			log.error(FundChangedException.MSG, e);
			response.getHead().setStatus(e.getStatus());

		} catch (MownecumWithdrawResponseData e) {
			log.error("Fund Withdraw Audit error:", e);
			response.getHead().setStatus(e.getErrorStatus());
		}
		
		log.info("=提现申请审核=/fund/auditFundWithdraw End:");
		
		return response;
	}

	/**
	 * 
	* @Title: auditFundWithdraw 
	* @Description: 提现申请审核（第二步）
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/auditFundWithdraw2")
	@ResponseBody
	public Object audit2(@RequestBody @ValidRequestBody Request<WithdrawAuditRequest> request) throws Exception {

		Response<WithdrawAuditResponse> response = new Response<WithdrawAuditResponse>(request);

		try {

			if (null != request.getBody()) {

				WithdrawAuditRequest auditRequest = request.getBody().getParam();

				boolean needmsg = fundWithdrawService.audit2(auditRequest.getId(), auditRequest.getApproveAct(),
						auditRequest.getStatus(), auditRequest.getMemo());
				
				FundWithdrawOrder order= fundWithdrawService.queryFundWithdrawById(auditRequest.getId());
				
				addAuditWithdrawLog(auditRequest.getId(), auditRequest.getStatus(), auditRequest.getAppealTips(),order.getAppr2Time());
				
				if (WithdrawStauts.REJECT.getValue() == auditRequest.getStatus() && needmsg == true) {
					fundWithdrawService.sendMsgaudit2 (auditRequest.getId());
				}
			
			}

		} catch(FundChangeProcessException e){
			log.error(FundChangeProcessException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundChangedException e) {
			log.error(FundChangedException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (MownecumWithdrawResponseData e) {
			log.error("Fund Withdraw Audit error:", e);
			response.getHead().setStatus(e.getErrorStatus());
		} catch (Exception e) {
			log.error("Fund Withdraw Audit error:", e);
			throw e;
		}
		return response;
	}
	
	private void addAuditWithdrawLog(Long id,Long auditStatus,String appealTips,Date date) throws Exception{

		FundWithdrawOrder order = fundWithdrawService.queryFundWithdrawById(id);

		Date now = date==null?new Date():date;
		String sn = order.getSn();
		
		addWithdrawLog(order.getSn(), appealTips,now);
		WithdrawStauts status = WithdrawStauts.creatStatus(auditStatus);
		switch(status){
			case APPROVED:
				fundWithdrawLogServiceImpl.saveFundWithLog(sn, LogModel.WITHDRAW, DateUtils.addSeconds(now, 5), WITHDRAW_STATUS.DRAW_CHECK);
				break;
			case REJECT:
				fundWithdrawLogServiceImpl.saveFundWithLog(sn, LogModel.WITHDRAW, DateUtils.addSeconds(now, 5), WITHDRAW_STATUS.DRAW_RETURN_START);
				fundWithdrawLogServiceImpl.saveFundWithLog(sn, LogModel.WITHDRAW, DateUtils.addSeconds(now, 10), WITHDRAW_STATUS.DRAW_RETURN);
				break;
		}
		
	}
	
	/**99
	 * 
	* @Title: auditFundWithdraw 
	* @Description: 提现處裡退款
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/auditRefund")
	@ResponseBody
	public Object auditRefund(@RequestBody @ValidRequestBody Request<WithdrawAuditRequest> request) throws Exception {

		Response<WithdrawAuditResponse> response = new Response<WithdrawAuditResponse>(request);
		try {

			if (null != request.getBody()) {

				WithdrawAuditRequest auditRequest = request.getBody().getParam();

				boolean needmsg = fundWithdrawService.refund(auditRequest.getId(), auditRequest.getApproveAct(),auditRequest.getStatus(), auditRequest.getMemo(),
						                                     auditRequest.getAttach());
				
				FundWithdrawOrder order = fundWithdrawService.queryFundWithdrawById(auditRequest.getId());
				fundWithdrawService.refundRestProcess(order);
				log.info("訂單:"+order.getSn()+",退款狀態   :"+ needmsg);

			if (WithdrawStauts.APPROVEDREFUND.getValue() == auditRequest.getStatus() && needmsg == true) {
					fundWithdrawService.sendMsgauditRefund (auditRequest.getId());
				}
			
			}

		} catch(FundChangeProcessException e){
			log.error(FundChangeProcessException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundChangedException e) {
			log.error(FundChangedException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (MownecumWithdrawResponseData e) {
			log.error("Fund Withdraw Audit error:", e);
			response.getHead().setStatus(e.getErrorStatus());
		} catch (Exception e) {
			log.error("Fund Withdraw Audit error:", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	* @Title: withdrawRemark 
	* @Description: 提现记录新增/修改备注
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/withdrawRemark")
	@ResponseBody
	public Response<Object> withdrawRemark(@RequestBody @ValidRequestBody Request<WithdrawRemarkRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		WithdrawRemarkRequest req = request.getBody().getParam();
		try {
			fundWithdrawService.remark(req.getTypeId(), req.getMemo());
		} catch (Exception e) {
			log.error("withdrawRemark error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * 
	 * @param 预处理
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yuchuliWithDraw")
	@ResponseBody
	public Response<Object> yuchuli(@RequestBody @ValidRequestHeader Request<ExceptionRefundRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			FundWithdrawOrder order = fundWithdrawService.queryFundWithdrawById(request.getBody().getParam().getExceptId());
			log.info("rootSn : " + order.getRootSn());
			List<FundWithdrawOrder> subs = fundWithdrawService.queryByRootSn(order.getRootSn());
			for(FundWithdrawOrder sub:subs){
				fundWithdrawService.yuchuli(sub.getId(),AccountTool.getRealAccount(request.getHead().getUserAccount()), 
						request.getBody().getParam().getCurrStatus());
			}
		} catch (Exception e) {
			log.error("exceptionRefund error.", e);
			throw e;
		}
		return response;
	}

	/**
	 * @param 预处理结束
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yuchuliWithDrawEnd")
	@ResponseBody
	public Response<Object> yuchuliEnd(@RequestBody @ValidRequestHeader Request<ExceptionRefundRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			log.info("----------------------------yuchuliEnd----------------------------");
			log.info("excpetId : " + request.getBody().getParam().getExceptId());
			log.info("currStatus : " + request.getBody().getParam().getCurrStatus());
			
			FundWithdrawOrder order = fundWithdrawService.queryFundWithdrawById(request.getBody().getParam().getExceptId());
			log.info("rootSn : " + order.getRootSn());
			List<FundWithdrawOrder> subs = fundWithdrawService.queryByRootSn(order.getRootSn());
			for(FundWithdrawOrder sub:subs){
				fundWithdrawService.yuchuliEnd(sub.getId(), request.getBody().getParam()
					.getCurrStatus());
			}
		} catch (Exception e) {
			log.error("exceptionRefund error.", e);
			throw e;
		}
		return response;
	}
	
	/**
	 * @param 風險提現處理退款中的預處理
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yuchuliWithDrawEndRefund")
	@ResponseBody
	public Response<Object> yuchuliEndRefund(@RequestBody @ValidRequestHeader Request<ExceptionRefundRequest> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);
		try {
			fundWithdrawService.yuchuliEndRefund(request.getBody().getParam().getExceptId(), request.getBody().getParam()
					.getCurrStatus());
		} catch (Exception e) {
			log.error("exceptionRefund error.", e);
			throw e;
		}
		return response;
	}
	
	
	@RequestMapping(value = "/whiteList")
	@ResponseBody
	public Response<List<WithdrawWhiteList>> whiteList(@RequestBody Request<WithdrawWhiteListRequest> request)
			throws Exception {
		Response<List<WithdrawWhiteList>> response = new Response<List<WithdrawWhiteList>>(request);
		try {
			WithdrawWhiteListRequest param = request.getBody().getParam();
			if ("1".equals(param.getAction())) {
				List list = userCustomerDao.queryUserByName(param.getAccountList());
				if (list.size() == param.getAccountList().size()) {
					withdrawWhiteListService.save(param.getAccountList(), param.getOperator(), param.getNote());
			
				} else {
					response.getHead().setStatus(2014l);
				}
			} else if ("0".equals(param.getAction())) {
				withdrawWhiteListService.deleteByAccount(param.getAccountList());
			} else if ("2".equals(param.getAction())) {
				return withdrawWhiteListService.queryPage(request);
			}
		} catch (Exception e) {
			log.error("whiteList error.", e);
			response.getHead().setStatus(2013l);
		}
		return response;

	}
	
	/**
	 * 
	* @Title: queryAppealWithdrawList
	* @Description: 提現申诉列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAppealWithdrawList")
	@ResponseBody
	public Response<List<WithdrawAppealStruc>> queryAppealWithdrawList(@RequestBody Request<WithdrawAppealStruc> request) throws Exception {
		log.info("queryAppealWithdrawList begin");
		log.info("UserId       ="+request.getBody().getParam().getUserId());
		log.info("Account      ="+request.getBody().getParam().getAccount());

		Response<List<WithdrawAppealStruc>> resp = new Response<List<WithdrawAppealStruc>>(request);
		try {
			List<WithdrawAppealStruc> withdrawAppealStrucList = new ArrayList<WithdrawAppealStruc>();
			List<WithdrawAppeal> withdrawAppealList = withdrawAppealServiceImpl.queryWithdrawAppeal(request);
			for (WithdrawAppeal withdrawAppeal : withdrawAppealList) {
				WithdrawAppealStruc appeal = DTOConverter.transWithdrawAppeal2Struc(withdrawAppeal);
				if("Y".equals(appeal.getIsSeperate())){
					List<FundWithdrawOrder> subDraws = fundWithdrawService.queryByRootSn(appeal.getSn());
					appeal.setSubDraws(subDraws);
				}
				withdrawAppealStrucList.add(appeal);
			}
			resp.setResult(withdrawAppealStrucList);
			if(withdrawAppealList.size()>0){
				resp.getBody().getPager().setStartNo(1);				
				resp.getBody().getPager().setEndNo(withdrawAppealList.size());
			}
			resp.getBody().getPager().setTotal(withdrawAppealList.size());
		} catch (Exception e) {
			log.error("queryAppealWithdrawList error", e);
			throw e;
		}
		return resp;
	}
	
	
	/**
	 * 
	* @Title: updateWithdrawAppealbySn
	* @Description: 更新提現申請單對應的提現申訴單
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateWithdrawAppealbySn")
	@ResponseBody
	public Response<List<WithdrawAppealStruc>> updateWithdrawAppealbySn(@RequestBody Request<WithdrawAppealStruc> request) throws Exception {
		log.info("updateWithdrawAppealbySn begin");		
		Response<List<WithdrawAppealStruc>> resp = new Response<List<WithdrawAppealStruc>>(
				request);
		WithdrawAppealStruc param = request.getBody().getParam();
		log.info("SN       ="+param.getSn());
		log.info("UploadImages ="+param.getUploadImages());
		
		try {
			List<WithdrawAppealStruc> withdrawAppealStrucList = new ArrayList<WithdrawAppealStruc>();
			if(!"".equals(param.getSn()) && param.getSn() != null){
				List<WithdrawAppeal> withdrawAppealList = withdrawAppealServiceImpl
						.updateAppealByWithdrawSn(request);
				
				List<FundWithdrawOrder> subDraws = fundWithdrawService.queryByRootSn(param.getSn());
				for(FundWithdrawOrder subDraw:subDraws){
					log.info("subDraw.getSn() ="+subDraw.getSn());
					fundWithdrawLogServiceImpl.saveFundWithLog(subDraw.getSn(), LogModel.APEAL, new Date(), FundLogEnum.APPEAL_STATUS.START);
				}
				
				for (WithdrawAppeal withdrawAppeal : withdrawAppealList) {
					withdrawAppealStrucList.add(DTOConverter
							.transWithdrawAppeal2Struc(withdrawAppeal));
				}
				resp.setResult(withdrawAppealStrucList);
	
				if (withdrawAppealList.size() > 0) {
					resp.getBody().getResult().get(0).setIsUpdateState("ok"); // 更新成功
					log.info("更新成功 資料筆數為" + withdrawAppealList.size());
				} else {
					resp.getBody().getResult().get(0).setIsUpdateState("error"); // 更新失敗
					log.info("更新失敗 資料筆數為0查不到訂單");
				}
			}else{
				withdrawAppealStrucList.add(new WithdrawAppealStruc());
				resp.setResult(withdrawAppealStrucList);
				resp.getBody().getResult().get(0).setIsUpdateState("error");// 更新失敗
				log.error("SN value is empty or null");
			}
		} catch (Exception e) {
			log.error("updateWithdrawAppealbySn error", e);
			throw e;
		}
		return resp;
	}
	
	
	
	/**
	 * 
	* @Title: queryAppeaList
	* @Description: 後台--資金中心--提款申诉列表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAppealList")
	@ResponseBody
	public Response<List<WithdrawAppealStruc>> queryAppealList(@RequestBody Request<WithdrawAppealStruc> request) throws Exception {
		log.info("queryAppeaList begin");		
		log.info("UserId       ="+request.getBody().getParam().getUserId());
		log.info("Account      ="+request.getBody().getParam().getAccount());
		log.info("Statuses       ="+request.getBody().getParam().getStatuses());
		log.info("UserName      ="+request.getBody().getParam().getUserName());
		log.info("AppealSN       ="+request.getBody().getParam().getAppealSn());
		

		Response<List<WithdrawAppealStruc>> resp = new Response<List<WithdrawAppealStruc>>(request);
		try {
			List<WithdrawAppealStruc> withdrawAppealStrucList = new ArrayList<WithdrawAppealStruc>();
			List<WithdrawAppeal> withdrawAppealList = withdrawAppealServiceImpl.queryAppeal(request);
			final String userAccount = request.getHead().getUserAccount();
			//增加查詢時,判斷目前該資料是否有人審核
			if(withdrawAppealList!=null && withdrawAppealList.size()>0){
				withdrawAppealList= Lists.transform(withdrawAppealList, new Function<WithdrawAppeal , WithdrawAppeal>(){
					@Override
					public WithdrawAppeal apply(WithdrawAppeal appeal) {
						String nowCheckUser = redis.get("under_"+appeal.getAppealSn());
						if(StringUtils.isEmpty(nowCheckUser) || userAccount.equals(nowCheckUser)){
							appeal.setIsCheck(false);								
						}else{
							appeal.setIsCheck(true);
							appeal.setNowCheckUser(nowCheckUser);
						}
						return appeal;
					}
				});
			}
			
			for (WithdrawAppeal withdrawAppeal : withdrawAppealList) {
				withdrawAppealStrucList.add(DTOConverter.transAppeal2Struc(withdrawAppeal));
				}
			resp.setResult(withdrawAppealStrucList);
			if(withdrawAppealList.size()>0){
				resp.getBody().getPager().setStartNo(1);				
				resp.getBody().getPager().setEndNo(withdrawAppealList.size());
			}
			resp.getBody().getPager().setTotal(withdrawAppealList.size());
			log.info("queryAppeaList count  ="+withdrawAppealList.size());
		} catch (Exception e) {
			log.error("queryAppeaList error", e);
			throw e;
		}
		return resp;
	}
	
	/**
	 * 
	* @Title: updateAppealbySn
	* @Description: 更新提現申請單對應的提現申訴單
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/updateAppealbySn")
	@ResponseBody
	public Response<List<WithdrawAppealStruc>> updateAppealbySn(@RequestBody Request<WithdrawAppealStruc> request) throws Exception {
		log.info("updateAppealbySn begin");		
		
		WithdrawAppealStruc param = request.getBody().getParam();
		log.info("appealSN       ="+param.getAppealSn());
		log.info("appealStatus   ="+param.getAppealStatus());
		

		Response<List<WithdrawAppealStruc>> resp = new Response<List<WithdrawAppealStruc>>(request);
		try {
			List<WithdrawAppealStruc> withdrawAppealStrucList = new ArrayList<WithdrawAppealStruc>();
			List<WithdrawAppeal> withdrawAppealList = withdrawAppealServiceImpl.updateAppealStatus(request);
			log.info("withdrawAppealList size ="+withdrawAppealList.size());
			if(withdrawAppealList.size()>0){
				String rootSn = withdrawAppealList.get(0).getWithdrawSn();
				log.info("rootSn =" + rootSn);
				List<FundWithdrawOrder> subDraws = fundWithdrawService.queryByRootSn(rootSn);
				log.info("subDraws size ="+subDraws.size());
				for(FundWithdrawOrder subDraw:subDraws){
					addWithdrawAppealLog(subDraw.getSn(),param.getAppealTipsResult());
				}
				withdrawAppealStrucList.add(DTOConverter.transWithdrawAppeal2Struc(withdrawAppealList.get(0)));
				resp.setResult(withdrawAppealStrucList);
				resp.getBody().getResult().get(0).setIsUpdateState("ok");  //更新成功
				log.info("更新成功 資料筆數為"+withdrawAppealList.size());				
			}else{
				resp.getBody().getResult().get(0).setIsUpdateState("error");  //更新失敗
				log.info("更新失敗 資料筆數為0查不到訂單");		
			}
		} catch (Exception e) {
			log.error("updateAppealbySn error", e);
			throw e;
		}
		return resp;
	}

	private void addWithdrawAppealLog(String sn,String appealTips) {
		FundWithdrawLog fundLog = new FundWithdrawLog();
		fundLog.setWithdrawSn(sn);
		fundLog.setLogModel(LogModel.APEAL.getCode());
		fundLog.setCreateTime(new Date());
		fundLog.setLogContent(appealTips);
		fundWithdrawLogServiceImpl.addLog(fundLog);
	}

	private void addWithdrawLog(String sn,String appealTips,Date now) {
		FundWithdrawLog fundLog = new FundWithdrawLog();
		fundLog.setWithdrawSn(sn);
		fundLog.setLogModel(LogModel.WITHDRAW.getCode());
		fundLog.setCreateTime(now);
		fundLog.setLogContent(appealTips);
		fundWithdrawLogServiceImpl.addLog(fundLog);
	}
	
	
	
	/**
	 * 
	* @Title: queryAppealbySn
	* @Description: 利用提現單號確認有無對應的申訴單
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryAppealbySn")
	@ResponseBody
	public Response<List<WithdrawAppealStruc>> queryAppealbySn(@RequestBody Request<WithdrawAppealStruc> request) throws Exception {
		log.info("withdrawSN       ="+request.getBody().getParam().getWithdrawSn());
		Response<List<WithdrawAppealStruc>> resp = new Response<List<WithdrawAppealStruc>>(request);
		try {
					
			List<WithdrawAppealStruc> withdrawAppealStrucList = new ArrayList<WithdrawAppealStruc>();
			List<WithdrawAppeal> withdrawAppealList = withdrawAppealServiceImpl.queryAppealbySn(request);
			for (WithdrawAppeal withdrawAppeal : withdrawAppealList) {
				withdrawAppealStrucList.add(DTOConverter.transWithdrawAppeal2Struc(withdrawAppeal));
				}
			resp.setResult(withdrawAppealStrucList);
			
			if(withdrawAppealList.size()>0){
				resp.getBody().getResult().get(0).setIsHaveAppeal("Y");  
				log.info("查詢結果  已經有該筆申訴單");				
			}else{
				log.info("查詢結果  未有該筆申訴單");		
			}
		} catch (Exception e) {
			log.error("queryAppealbySn error", e);
			throw e;
		}
		return resp;
	}
	
	@RequestMapping(value = "/queryUncheckAppeal")
	@ResponseBody
	public Response<Object> queryUncheckAppeal(@RequestBody Request<Object> request) throws Exception {
		Response<Object> resp = new Response<Object>(request);
		log.debug("--------------------------------------------");
		try {
			Long count = withdrawAppealServiceImpl.queryUncheckAppeal();
			log.debug("------------count : " + count);
			resp.getBody().setResult(count);
		} catch (Exception e) {
			log.error("queryAppealbySn error", e);
			throw e;
		}
		return resp;
	}
	@RequestMapping(value = "/queryUnhandleWithdraw")
	@ResponseBody
	public Response<Object> queryUnhandleWithdraw(@RequestBody Request<Object> request) throws Exception {
		Response<Object> resp = new Response<Object>(request);
		log.debug("--------------------------------------------");
		try {
			Integer count = fundWithdrawService.queryUnhandleWithdraw();
			log.debug("------------count : " + count);
			resp.getBody().setResult(count);
		} catch (Exception e) {
			log.error("queryUnhandleWithdraw error", e);
			throw e;
		}
		return resp;
	}
	
	@RequestMapping(value = "/queryWithdrawById")
	@ResponseBody
	public Response<WithdrawStruc> queryWithdrawById(@RequestBody Request<WithdrawInitRequest> request) throws Exception {
		FundWithdrawOrder order = null;
		Long id= request.getBody().getParam().getUserId();
		WithdrawStruc struc = new WithdrawStruc();
		Response<WithdrawStruc> resp = new Response<WithdrawStruc>(request);
		log.debug(" id = "+id);
		try {
			order = fundWithdrawService.queryFundWithdrawById(id);
			struc.setRootSn(order.getRootSn());
			resp.setResult(struc);
		} catch (Exception e) {
			log.error("queryAppealbySn error", e);
			throw e;
		}
		return resp;
	}
	
	@RequestMapping(value = "/queryWithdrawAppealByRootSn")
	@ResponseBody
	public Response<WithdrawStruc> queryWithdrawByRootSn(@RequestBody Request<WithdrawAppealStruc> request)throws Exception {
		String rootSn= request.getBody().getParam().getSn();
		WithdrawStruc struc = new WithdrawStruc();
		Response<WithdrawStruc> resp = new Response<WithdrawStruc>(request);
		log.info(" rootSn = "+rootSn);
		try {
			List<FundWithdrawOrder> orders = fundWithdrawService.queryByRootSn(rootSn);
			UserBankStruc userBankStruc = (UserBankStruc) DataConverterUtil.convertJson2Object(orders.get(0).getCardStr(),
					new UserBankStruc().getClass());
			struc.setRootSn(orders.get(0).getRootSn());
			struc.setApplyAccount(orders.get(0).getApplyAccount());
			struc.setApplyTime(DataConverterUtil.convertDate2Long(orders.get(0).getApplyTime()));
			struc.setWithdrawAmt(orders.get(0).getTotalDrawAmount());
			struc.setUserBankStruc(orders.get(0).getUserBankStruc());
			resp.setResult(struc);
		} catch (Exception e) {
			log.error("queryAppealbySn error", e);
			throw e;
		}
		return resp;
	}
	
	@RequestMapping(value = "/transferToMow")
	@ResponseBody
	public void transferToMow(WithdrawConfirmRequest confirmRequest) throws Exception {
		try{
			log.info("ex_id : " + confirmRequest.getEx_id());
			FundWithdrawOrder drawOrder = fundWithdrawService.queryFundWithdrawById(Long.parseLong(confirmRequest.getEx_id()));
			fundWithdrawService.callMowWithDraw(drawOrder,true);
		}catch(Exception e){
			log.error("transferToMow error:",e);
		}
	}
	
	private boolean checkIsShowReview(String rootSn){
		List<FundWithdrawOrder> orders = fundWithdrawService.queryByRootSn(rootSn);
		for(FundWithdrawOrder draw:orders){
			if(draw.getStauts().getValue().longValue() != 0){
				return false;
			}
		}
		return true;
	}
	
	@Resource(name = "userCustomerDaoImpl")
	private UserCustomerDaoImpl userCustomerDao;
}
