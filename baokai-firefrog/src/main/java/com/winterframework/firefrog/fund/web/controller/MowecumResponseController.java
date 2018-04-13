package com.winterframework.firefrog.fund.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.fund.dao.IFundChargeExceptionDao;
import com.winterframework.firefrog.fund.dao.IFundChargeQueueDao;
import com.winterframework.firefrog.fund.dao.IFundManualDepositDao;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundChargeQueue;
import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.entity.FundChargeMCOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder.Status;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.enums.FundChargeQueueEnum.QueueStatus;
import com.winterframework.firefrog.fund.enums.FundLogEnum;
import com.winterframework.firefrog.fund.enums.FundLogEnum.LogModel;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.fund.service.IFundWithdrawLogService;
import com.winterframework.firefrog.fund.service.IFundWithdrawService;
import com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher;
import com.winterframework.firefrog.fund.service.impl.FundMowBaseService;
import com.winterframework.firefrog.fund.service.impl.ddb.DDBPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.ddb.DdbQueryResponse;
import com.winterframework.firefrog.fund.service.impl.din.DINPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.din.DinQueryResponse;
import com.winterframework.firefrog.fund.service.impl.ecpss.ECPSSQueryResponse;
import com.winterframework.firefrog.fund.service.impl.ecpss.ECPSSQueryResponseDetail;
import com.winterframework.firefrog.fund.service.impl.ecpss.Root;
import com.winterframework.firefrog.fund.service.impl.hb.HBPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.huayin.HUAYINPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.huayin.HuaYinQueryResponse;
import com.winterframework.firefrog.fund.service.impl.jinyang.JINYANGPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.jinyang.JinYangPayQueryDetailResponse;
import com.winterframework.firefrog.fund.service.impl.jinyang.JinYangPayQueryResponse;
import com.winterframework.firefrog.fund.service.impl.mow.ChargeConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.mow.ECPSSConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.mow.McQuery;
import com.winterframework.firefrog.fund.service.impl.mow.MowBigCallback;
import com.winterframework.firefrog.fund.service.impl.mow.THConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.mow.WORTHConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.mow.WithdrawConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.sp.SPPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.th.ThPayOrderCheckQuery;
import com.winterframework.firefrog.fund.service.impl.th.ThPayQueryOrderCheckResponse;
import com.winterframework.firefrog.fund.service.impl.wft.WFTPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.worth.WorthPayOrderCheckQuery;
import com.winterframework.firefrog.fund.service.impl.worth.WorthPayQueryResponse;
import com.winterframework.firefrog.fund.service.impl.yinbang.YINBANGPayConfirmRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.util.hbpay.Base64;
import com.winterframework.firefrog.fund.util.hbpay.Base64Utils;
import com.winterframework.firefrog.fund.util.hbpay.LocalUtil;
import com.winterframework.firefrog.fund.util.hbpay.RSAUtils;
import com.winterframework.firefrog.fund.util.sppay.SignUtils;
import com.winterframework.firefrog.fund.util.yinbangpay.Base64Local;
import com.winterframework.firefrog.fund.util.yinbangpay.SecurityRSAPay;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.dto.ChargeExceptionRequest;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.fund.web.dto.QueryFundWithdrawOrderRequest;
import com.winterframework.firefrog.fund.web.dto.WithdrawConfirmResponse;
import com.winterframework.firefrog.global.dao.vo.GlobalGrayListVO;
import com.winterframework.firefrog.global.service.IGlobalGrayListService;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.security.MD5Encrypt;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

/**
 * http://api.joy188.com:888/fund/mownecum/exceptionWithdrawApply
http://api.joy188.com:888/fund/mownecum/addTransfer
http://api.joy188.com:888/fund/mownecum/withdrawalResult
http://api.joy188.com:888/fund/mownecum/requestWithdrawApproveInformation
james(James) 07-30 14:36:31
 * @author heny
 *
 */
@Controller("mowecumResponseController")
@RequestMapping(value = "/fund/mownecum/")
public class MowecumResponseController {
	private static Logger logger = LoggerFactory.getLogger(MowecumResponseController.class);

	@Resource(name = "fundChargeServiceImpl")
	private IFundChargeService fundChargeService;
	@Resource(name = "SNUtil")
	private ISNGenerator snUtil;

	@Resource(name = "paymentCallbackDispatcher")
	private IPaymentCallbackDispatcher paymentCallbackDispatcher;

	private static final Logger log = LoggerFactory.getLogger(MowecumResponseController.class);
	@Resource(name = "fundWithdrawService")
	private IFundWithdrawService fundWithdrawService;
	@Resource(name = "fundChargeExceptionDao")
	private IFundChargeExceptionDao exceptionService;

	@Resource(name="fundWithdrawLogServiceImpl")
	private IFundWithdrawLogService fundWithdrawLogServiceImpl;
	
	@Resource(name = "globalGrayListServiceImpl")
	private IGlobalGrayListService globalGrayListService;
	
	@Resource(name = "FundManualChargeService")
	private FundMowBaseService fundManualChargeService;
	
	@Resource(name = "fundManualDepositDao")
	protected IFundManualDepositDao fundManualDepositDao;
	
	@PropertyConfig(value = "thpay_charset")
	private String inputCharset;
	
	@PropertyConfig(value = "thpay_company_id")
	private String merchantCode;
	
	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient mcwClient;
	
	@PropertyConfig(value = "thpay_url")
	private String thpayUrl;
	
	@PropertyConfig(value = "thpay_orderCheck_query_url")
	private String thpayOrderCheckQueryUrl;
	
	@PropertyConfig(value = "thpay_key")
	private String thpayKey;
	
	@PropertyConfig(value = "ecpss_company_id")
	private String merNo;
	
	@PropertyConfig(value = "ecpss_key")
	private String ecpssKey;
	
	@PropertyConfig(value = "ecpss_query_url")
	private String ecpssQueryUrl;
	
	@PropertyConfig(value = "ecpss_url")
	private String ecpssUrl;
	
	private static String txCode = "1001";

	@Resource(name = "fundChargeQueueDaoImpl")
	private IFundChargeQueueDao fundChargeQueueDaoImpl;
	
	@PropertyConfig(value = "unitConvert")
	private Long unitConvert;
	
	@PropertyConfig(value = "hbpay_public_key")
	private String hbpayPublicKey;
	
	@PropertyConfig(value = "hbpay_private_key")
	private String hbpayPrivateKey;
	
	@PropertyConfig(value = "hbpay_account")
	private String hbpay_account;
	
	@PropertyConfig(value = "hbpay_chargeConfirm_order_code")
	private String hbpayChargeConfirmOrderCode;
	
	@PropertyConfig(value = "worth_md5key")
	private String worthMd5key;
	
	@PropertyConfig(value = "worth_merchant_ID")
	private String worthMerchantID;
	
	@PropertyConfig(value = "worth_url")
	private String worthUrl;
	
	@PropertyConfig(value = "worth_orderCheck_query_url")
	private String worthOrderCheckQueryUrl;
	
	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	
	@PropertyConfig(value = "sppay_query_order")
	private String sppayQueryOrderUrl;
	@PropertyConfig(value = "sppay_private_key")
	private String sppayPrivateKey;
	@PropertyConfig(value = "sppay_account")
	private String sppayAccount;
	@Autowired
	private BeginMissionService beginMissionService;
	@PropertyConfig(value = "ddbpay_query_order")
	private String ddbpayQueryOrderUrl;
	@PropertyConfig(value = "ddbpay_account")
	private String ddbpayAccount;
	@PropertyConfig(value = "ddbpay_private_key")
	private String ddbpayPrivateKey;
	
	@PropertyConfig(value = "wftpay_query_order")
	private String wftpayQueryOrderUrl;
	@PropertyConfig(value = "wftpay_account")
	private String wftpayAccount;
	@PropertyConfig(value = "wftpay_private_key")
	private String wftpayPrivateKey;
	
	@PropertyConfig(value = "dinpay_query_order")
	private String dinpayQueryOrderUrl;
	@PropertyConfig(value = "dinpay_account")
	private String dinpayAccount;
	@PropertyConfig(value = "dinpay_private_key")
	private String dinpayPrivateKey;
	
	@PropertyConfig(value = "huayinpay_query_order")
	private String huayinpayQueryOrderUrl;
	@PropertyConfig(value = "huayinpay_account")
	private String huayinpayAccount;
	@PropertyConfig(value = "huayinpay_private_key")
	private String huayinpayPrivateKey;
	
	@PropertyConfig(value = "yinbangpay_query_order")
	private String yinbangpayQueryOrderUrl;
	@PropertyConfig(value = "yinbangpay_account")
	private String yinbangpayAccount;
	@PropertyConfig(value = "yinbangpay_private_key")
	private String yinbangpayPrivateKey;
	@PropertyConfig(value = "yinbangplt_public_key")
	private String yinbangpayPlatformKey;
		
	@PropertyConfig(value = "jinyangpay_account")
	private String jinyangpayAccount;
	@PropertyConfig(value = "jinyangpay_key")
	private String jinyangpayKey;
	@PropertyConfig(value = "jinyangpay_qrurl")
	private String jinyangpayQrUrl;
	@PropertyConfig(value = "jinyangpay_bankurl")
	private String jinyangpayBankUrl;
	@PropertyConfig(value = "jinyangpay_query_order")
	private String jinyangpayQueryOrderUrl;
	
	/**
	 * 
	* @Title: chargeException 
	* @Description: 充值异常记录（MOW调用） 
	* @param request
	* @return
	 */
	@RequestMapping(value = "/exceptionWithdrawApply")
	@ResponseBody
	public Map<String, Object> chargeException(ChargeExceptionRequest request) throws Exception {
		logger.error("exceptionNotice:" + JsonMapper.nonAlwaysMapper().toJson(request));
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("exception_order_num", request.getException_order_num());
		maps.put("status", 1);
		///处理参数问题
		try {
			fundChargeService.reportException(DTOConverter.transChargeExceptionRequest2FundChargeException(request));
		} catch (Exception e) {
			logger.error("chargeException error.", e);
			maps.put("error_msg", e.getLocalizedMessage());
			maps.put("status", 0);
		}

		return maps;
	}

	@RequestMapping(value = "/requestWithdrawApproveInformation")
	@ResponseBody
	public Map<String, Object> withdrawQuery(McQuery request) throws Exception {
		logger.info("requestWithdrawApproveInformation:" + JsonMapper.nonAlwaysMapper().toJson(request));
		PageRequest<QueryFundWithdrawOrderRequest> pr = new PageRequest<QueryFundWithdrawOrderRequest>();
		if (request.getCompany_order_num().startsWith("FDADML")) {
			FundChargeException chargeException = exceptionService.queryBySn(request.getCompany_order_num());

			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("mownecum_order_num", request.getMownecum_order_num());
			maps.put("company_order_num", request.getCompany_order_num());
			maps.put("status", 5L);
			maps.put("error_msg", "");
			if (chargeException != null) {
				Long status = chargeException.getStatus();
				//APPLY(0L), APPROVING(1L), APPROVED(2L), REJECT(3L), SUCCESS(4L), PART(6L), FAIL(5L);
				if (status != null) {
					if (status == 4L) {
						status = 4L;
					}
					if (status == 6L) {
						status = 0L;
					}
					if (status == 7L) {
						status = 2L;
					}
				}

				maps.put("status", status);
			}

			return maps;
		} else if (request.getCompany_order_num().startsWith("OTRGDK")) {

			FundManualOrder manu = fundManualDepositDao.queryManualDepositBySn(request.getCompany_order_num());

			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("mownecum_order_num", request.getMownecum_order_num());
			maps.put("company_order_num", request.getCompany_order_num());
			maps.put("status", 5L);
			maps.put("error_msg", "");
			if (manu != null) {
				Long status = manu.getStatus();
				//APPLY(0L), APPROVING(1L), APPROVED(2L), REJECT(3L), SUCCESS(4L), PART(6L), FAIL(5L);
				if (status != null) {
					if (status == 1L) {
						//如果是人工打款。1为审核通过
						status = 4L;
					}
					if (status == 6L) {
						status = 0L;
					}
					if (status == 7L) {
						status = 2L;
					}
				}

				maps.put("status", status);
			}

			return maps;
		} else {

			QueryFundWithdrawOrderRequest qf = new QueryFundWithdrawOrderRequest();
			qf.setSn(request.getCompany_order_num());
			pr.setSearchDo(qf);
			CountPage<FundWithdrawOrder> cp = fundWithdrawService.queryFundWithdrawList(pr);
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("mownecum_order_num", request.getMownecum_order_num());
			maps.put("company_order_num", request.getCompany_order_num());
			maps.put("status", 5L);
			maps.put("error_msg", "");
			if (cp.getResult() != null && cp.getResult().size() > 0) {
				WithdrawStauts ws = cp.getResult().get(0).getStauts();
				Long status = 5L;
				//APPLY(0L), APPROVING(1L), APPROVED(2L), REJECT(3L), SUCCESS(4L), PART(6L), FAIL(5L);
				switch (ws) {
				case APPROVED: {
					status = 4L;
					break;
				}
				case APPROVING: {
					status = 2L;
					break;
				}
				case SUCCESS: {
					status = 1L;
					fundWithdrawLogServiceImpl.saveFundWithLog(qf.getSn(), LogModel.WITHDRAW, new Date(), FundLogEnum.WITHDRAW_STATUS.DRAW_SUCCESS);
					break;
				}
				case FAIL: {
					status = 0L;
					break;
				}
				case PART: {
					status = 2L;
					break;
				}
				default:
					status = 5L;
				}

				maps.put("status", status);
			}

			return maps;
		}
	}

	/**
	 * 
	* @Title: chargeConfirm 
	* @Description: 充值确认（MOW调用）
	* @param request
	* @return
	 */
	
	@RequestMapping(value = "/addTransfer")
	@ResponseBody
	public Map<String, Object> chargeConfirm(ChargeConfirmRequest chargeConfirmRequest) {
		logger.info("=/fund/mownecum/addTransfer Start:");
		logger.info("chargeConfirm:" + JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
		FundChargeMCOrder mcOrder = DTOConverter.tranChargeConfirmReq2MCOrder(chargeConfirmRequest);

		Map<String, Object> confirmRes = new HashMap<String, Object>();
		String errorMsg = null;
		long status = 1l;
		FundChargeOrder order = null;
		try {
			
			order = fundChargeService.queryById(chargeConfirmRequest.getCompany_order_num());
		
			if (order.getStatus()==null || order.getStatus().equals(Status.APPLY)
										|| order.getStatus().equals(Status.FAILED)  // time out call cancel 
										|| order.getStatus().equals(Status.ADMIN_CANCEL) // admin call cancel
										|| order.getStatus().equals(Status.USER_CANCEL)) // user call cancel
				{
				long num = fundChargeService.updateOrder(order.getId(), Status.PROCESS);
	
				if(num != 1l){
					confirmRes.put("company_order_num", chargeConfirmRequest.getCompany_order_num());
					confirmRes.put("mownecum_order_num", chargeConfirmRequest.getMownecum_order_num());
					confirmRes.put("status", 0);
					logger.info("addTransfer return fail");
					return confirmRes;
				}
				//如果不为空，而且不是申请状态的话，直接返回了
				//FundChargeOrder order = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.SUCCESS);
				order.setSn(chargeConfirmRequest.getCompany_order_num());
				BankCard payCard = new BankCard();
				FundBank payBank = new FundBank();
				if (chargeConfirmRequest.getExact_payment_bank_id() == null) {
					payBank.setId(chargeConfirmRequest.getExact_payment_bank_id());
				} else {
					payBank.setId(order.getPayCard().getBank().getId());
				}
				payCard.setBank(payBank);
				payCard.setBankCardNo(chargeConfirmRequest.getPay_card_num());
				payCard.setAccountHolder(chargeConfirmRequest.getPay_card_name());
				order.setPayCard(payCard);
				order.setRealBankId(chargeConfirmRequest.getBank_id());
				try {
					order.setChargeTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(chargeConfirmRequest.getPay_time()));
				} catch (Exception e) {
				}
				
				order.setOperatingTime(DataConverterUtil.convertLong3Date(chargeConfirmRequest.getOperating_time()));
				mcOrder.setOrder(order);
				
				 fundChargeService.confirm(mcOrder);
			
				//判斷是否為新手進行首充獎勵
				 beginMissionService.firstChargeAward(mcOrder.getOrder().getApplyUser().getId(), mcOrder.getAmount());
				 
				 //結束才寄信
				 fundChargeService.sendMsg (mcOrder.getAmount(), mcOrder.getOrder().getApplyUser());
				 logger.info("addTransfer done");
			}else if (order.getStatus()!= null && order.getStatus().equals(Status.SUCCESS))
			{
				status = 1l;
			}else {
				status = 0;
			}
		} catch (Exception e) {
			logger.error("chargeConfirm error.", e);
			if (order != null){
				try {
					fundChargeService.updateOrder(order.getId(), Status.APPLY);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			errorMsg = e.getMessage();
			status = 0;
		}
		confirmRes.put("company_order_num", chargeConfirmRequest.getCompany_order_num());
		confirmRes.put("mownecum_order_num", chargeConfirmRequest.getMownecum_order_num());
		confirmRes.put("status", status);
		confirmRes.put("error_msg", errorMsg);
		
		logger.info("=/fund/mownecum/addTransfer End:");
		
		return confirmRes;
	}

	/**
	 * 
	* @Title: confirmCallback 
	* @Description: 提现申请Mownecum 回调 
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/withdrawalResult")
	@ResponseBody
	public WithdrawConfirmResponse confirmCallback(WithdrawConfirmRequest confirmRequest) {
		logger.info("withdrawConfirm:" + JsonMapper.nonAlwaysMapper().toJson(confirmRequest));
		WithdrawConfirmResponse confirmResponse = new WithdrawConfirmResponse();
		confirmResponse.setCompanyOrderNum(confirmRequest.getCompany_order_num());
		confirmResponse.setMownecumOrderNum(confirmRequest.getMownecum_order_num());
		confirmResponse.setStatus("1");
		try {
			if (null != confirmResponse) {
				// add处理逻辑，根据平台订单号处理
				String str = snUtil.parseBusinessSnDesc(confirmRequest.getCompany_order_num());
				paymentCallbackDispatcher.doDispatch(str, confirmRequest);
			}
			
			//於mow回傳結果時,進行確認動作
			FundWithdraw funddraw= fundWithdrawLogServiceImpl.getFundWithdrawByMowNum(confirmRequest.getCompany_order_num());
			if(funddraw!=null){
				//提款成功或部份成功
				if((1l==confirmRequest.getStatus()||2l==confirmRequest.getStatus()) && funddraw.getMcNoticeTime()!=null){
					fundWithdrawLogServiceImpl.saveFundWithLog(funddraw.getSn(), LogModel.WITHDRAW, funddraw.getMcNoticeTime(), FundLogEnum.WITHDRAW_STATUS.DRAW_SUCCESS);
					 //判斷是否為新手進行首提獎勵
					beginMissionService.firstWthdrawAward(funddraw.getUserId(), funddraw.getWithdrawAmt());
					//提現成功若為灰名單則移除灰名單
					GlobalGrayListVO isGrayUser = globalGrayListService.queryGlobalGrayListByUserId(funddraw.getUserId());
					if(isGrayUser != null){
						//用戶為灰名單且審核成功，移除灰名單
						globalGrayListService.deleteGlobalGrayList(funddraw.getUserId());
					}
				}else{
					//默認退款失敗
					if(funddraw.getMcNoticeTime()!=null){
						//當有mc_notice_time才為mow正確通知
						fundWithdrawLogServiceImpl.saveFundWithLog(funddraw.getSn(), LogModel.WITHDRAW, funddraw.getMcNoticeTime(), FundLogEnum.WITHDRAW_STATUS.DRAW_RETURN_FAIL);				
						fundWithdrawLogServiceImpl.saveFundWithLog(funddraw.getSn(), LogModel.WITHDRAW, DateUtils.addSeconds(funddraw.getMcNoticeTime(), 5),
								FundLogEnum.WITHDRAW_STATUS.DRAW_RETURN);
						fundWithdrawLogServiceImpl.saveFundWithLogWithDetail(funddraw.getSn(), LogModel.WITHDRAW, DateUtils.addSeconds(funddraw.getMcNoticeTime(), 6),
								confirmRequest.getDetail());
						
					}
				}
			}else{
				log.error("funddraw is null:"+funddraw.getSn());
				throw new Exception();
			}
			
			
		} catch (Exception e) {
			log.error("Mownecum Confirm Fund Withdraw Callback error: ", e);
			confirmResponse.setStatus("0");
		}
		return confirmResponse;
	}

	@RequestMapping(value = "/DepositInquiryConfirm")
	@ResponseBody
	public WithdrawConfirmResponse chargeBig(MowBigCallback confirmRequest) throws Exception {
		logger.error("withdrawConfirm:" + JsonMapper.nonAlwaysMapper().toJson(confirmRequest));
		WithdrawConfirmResponse confirmResponse = new WithdrawConfirmResponse();
		confirmResponse.setCompanyOrderNum(confirmRequest.getCompany_order_num());
		confirmResponse.setMownecumOrderNum(confirmRequest.getMownecum_order_num());
		confirmResponse.setStatus("1");
		fundManualChargeService.callBack(confirmRequest);
		return confirmResponse;
	}
	
	/**
	 * 
	* @Title: chargeDispatch 
	* @Description: 第三方充值加幣,由task觸發
	* @param request
	* @return
	 */
	
	@RequestMapping(value = "/chargeDispatch")
	@ResponseBody
	public Long chargeDispatch() {
		log.info("=/fund/mownecum/chargeDispatch Start:");
		ObjectMapper mapper = new ObjectMapper();
		Long successNum=0L;
		try {
			//取出FUND_CHARGE_QUEUE 中status=3,7的訂單
			List<FundChargeQueue> queueOrders = fundChargeQueueDaoImpl.queryUntreatOrder();
			
			
			List<FundChargeQueue> queueOrdersNeedTreat = new ArrayList<FundChargeQueue>();
			//將訂單都改為加幣處理中狀態
			for(FundChargeQueue queueOrder : queueOrders){
				long nd = 1000 * 24 * 60 * 60;
			    long nh = 1000 * 60 * 60;
			    long nm = 1000 * 60;
				Date sysdate = new Date();
				//計算訂單產生時間後過幾分鐘
				long min =  (sysdate.getTime() - queueOrder.getCreatedTime().getTime()) % nd % nh / nm;
				//判斷查詢次數
				if(queueOrder.getChargeConfirmTime()<3){
					//判斷5分鐘查第二次，10分鐘查第三次，總共查詢三次
					if(queueOrder.getChargeConfirmTime()<1){
						
						//還未執行第一次查詢，更新資料並加入查詢列表
						FundChargeQueue fundChargeQueue = new FundChargeQueue();
						fundChargeQueue.setCreatedTime(new Date());
						fundChargeQueue.setSn(queueOrder.getSn());
						fundChargeQueue.setChargeConfirmTime(0L);
						fundChargeQueue.setStatus(QueueStatus.DISPATCHPROCESS.getValue());
						fundChargeQueueDaoImpl.updateOrderQueue(fundChargeQueue);
						queueOrdersNeedTreat.add(queueOrder);	
						
					}else if(min >=5  && queueOrder.getChargeConfirmTime()<2){
						//相差時間大於等於5分鐘，並且查詢次數低於2
						fundChargeQueueDaoImpl.updateOrderQueueStatus(queueOrder.getSn(),
								QueueStatus.DISPATCHPROCESS.getValue());
						queueOrdersNeedTreat.add(queueOrder);
					}else if(min >=10 && queueOrder.getChargeConfirmTime()<3){
						//相差時間大於等於10分鐘，並且查詢次數低於3
						fundChargeQueueDaoImpl.updateOrderQueueStatus(queueOrder.getSn(),
								QueueStatus.DISPATCHPROCESS.getValue());
						queueOrdersNeedTreat.add(queueOrder);
					}
				}else{
					//超過三次，排除於查詢名單外
				}
			}
			log.info("process order num:"+queueOrdersNeedTreat.size());
			
			for (FundChargeQueue queueOrder : queueOrdersNeedTreat) {
				try {
					FundChargeOrder order = fundChargeService.queryById(queueOrder.getSn());
					if(order == null){
						throw new Exception("查無此訂單");
					}

					THConfirmRequest chargeConfirmRequestThpay = null;
					ECPSSConfirmRequest chargeConfirmRequestECPSS = null;
					HBPayConfirmRequest chargeConfirmRequestHbpay = null;
					WORTHConfirmRequest chargeConfirmRequestWorthpay =null;
					SPPayConfirmRequest chargeConfirmRequestSppay = null;
					DDBPayConfirmRequest chargeConfirmRequestDdbpay = null;
					WFTPayConfirmRequest chargeConfirmRequestWftpay = null;
					DINPayConfirmRequest chargeConfirmRequestDinpay = null;
					HUAYINPayConfirmRequest chargeConfirmRequestHuayinpay = null;
					YINBANGPayConfirmRequest chargeConfirmRequestYinbangpay = null;
					JINYANGPayConfirmRequest chargeConfirmRequestJinyangpay = null;
					
					FundChargeMCOrder mcOrder = new FundChargeMCOrder();
					boolean isCheckSuccess = false;
					String orderId = null;				
												
					if (order.getChargeMode() == 2) { //通匯
						chargeConfirmRequestThpay = mapper.readValue(queueOrder.getNote(), THConfirmRequest.class);
						mcOrder = DTOConverter.tranTHChargeConfirmReq2MCOrder(chargeConfirmRequestThpay);
						
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做THPay訂單驗證
							log.info("------------------ThPay post for order check start -------------------");
							try {
								isCheckSuccess = checkOrderFromThPay(order);
							} catch (Exception e) {
								log.error("ThPay checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.error("ThPay from ThPay has some problem....");
								throw new Exception("通匯訂單驗證不通過");
							}else{
								orderId = chargeConfirmRequestThpay.getOrder_no();
							}
							log.info("------------------ThPay post check result is success -------------------");
						}
						
					} else if (order.getChargeMode() == 3) { //匯潮
						chargeConfirmRequestECPSS = mapper.readValue(queueOrder.getNote(), ECPSSConfirmRequest.class);
						mcOrder = DTOConverter.tranECPSSConfirmReq2MCOrder(chargeConfirmRequestECPSS);
									
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做ECPSS查詢驗證
							log.info("------------------ECPSS post for order check start -------------------");
							try {
								isCheckSuccess = checkECPSSPayStatus(order);
							} catch (Exception e) {
								log.error("ecpss checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("ecpss checks result is failed");
								throw new Exception("匯潮訂單驗證不通過");
							} else{
								orderId = chargeConfirmRequestECPSS.getBillNo();
							}
							log.info("------------------ECPSS post check result is success -------------------");
						}
					} else if(order.getChargeMode() == 4){ //匯博
						chargeConfirmRequestHbpay = mapper.readValue(queueOrder.getNote(), HBPayConfirmRequest.class);
						mcOrder = DTOConverter.tranHBConfirmReq2MCOrder(chargeConfirmRequestHbpay);
						
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做HBpay查詢驗證
							log.info("------------------HBpay post for order check start -------------------");
							try {
								isCheckSuccess = checkHBPayStatus(order);
							} catch (Exception e) {
								log.error("HBpay checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("HBpay checks result is failed");
								throw new Exception("匯博訂單驗證不通過");
							} else{
								orderId = chargeConfirmRequestHbpay.getOrderId();
							}
							log.info("------------------HBpay post check result is success -------------------");
						}
					} else if (order.getChargeMode() == 7) { //華勢
						chargeConfirmRequestWorthpay = mapper.readValue(queueOrder.getNote(), WORTHConfirmRequest.class);
						mcOrder = DTOConverter.tranWORTHConfirmReq2MCOrder(chargeConfirmRequestWorthpay);
									
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做WORTH查詢驗證
							log.info("------------------WORTH post for order check start -------------------");
							try {
								isCheckSuccess = checkWORTHPayStatus(order);
							} catch (Exception e) {
								log.error("worth checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("worth checks result is failed");
								throw new Exception("華勢訂單驗證不通過");
							} else{
								orderId = chargeConfirmRequestWorthpay.getOrder_no();
							}
							log.info("------------------WORTH post check result is success -------------------");
						}
					} else if(order.getChargeMode() == 8){ //秒付
						chargeConfirmRequestSppay = mapper.readValue(queueOrder.getNote(), SPPayConfirmRequest.class);
						mcOrder = DTOConverter.tranSPConfirmReq2MCOrder(chargeConfirmRequestSppay);
						
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做HBpay查詢驗證
							log.info("-----------------SPpay post for order check start -------------------");
							try {
								isCheckSuccess = checkSPPayStatus(order);
							} catch (Exception e) {
								log.error("HBpay checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("HBpay checks result is failed");
								throw new Exception("秒付订单验证不通过");
							} else{
								orderId = chargeConfirmRequestSppay.getORDERNO();
							}
							log.info("------------------SPpay post check result is success -------------------");
						}
					} else if(order.getChargeMode() == 9){ //多得宝
						chargeConfirmRequestDdbpay = mapper.readValue(queueOrder.getNote(), DDBPayConfirmRequest.class);
						mcOrder = DTOConverter.tranDDBConfirmReq2MCOrder(chargeConfirmRequestDdbpay);
						
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做DDBpay查詢驗證
							log.info("-----------------DDBpay post for order check start -------------------");
							try {
								isCheckSuccess = checkDDBPayStatus(order);
							} catch (Exception e) {
								log.error("DDBpay checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("DDBpay checks result is failed");
								throw new Exception("多得宝订单验证不通过");
							} else{
								orderId = chargeConfirmRequestDdbpay.getOrder_no();
							}
							log.info("------------------DDBpay post check result is success -------------------");
						}
					} else if(order.getChargeMode() == 10){ //网富通
						chargeConfirmRequestWftpay = mapper.readValue(queueOrder.getNote(), WFTPayConfirmRequest.class);
						mcOrder = DTOConverter.tranWFTConfirmReq2MCOrder(chargeConfirmRequestWftpay);
						
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做WFTpay查詢驗證
							log.info("-----------------WFTpay post for order check start -------------------");
							try {
								isCheckSuccess =true; //无查询订单接口 直接true checkWFTPayStatus(order);	
							} catch (Exception e) {
								log.error("WFTpay checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("WFTpay checks result is failed");
								throw new Exception("网富通订单验证不通过");
							} else{
								orderId = chargeConfirmRequestWftpay.getOrdernumber();
							}
							log.info("------------------WFTpay post check result is success -------------------");
						}
					} else if(order.getChargeMode() == 11){ //智付
						chargeConfirmRequestDinpay = mapper.readValue(queueOrder.getNote(), DINPayConfirmRequest.class);
						mcOrder = DTOConverter.tranDINConfirmReq2MCOrder(chargeConfirmRequestDinpay);
						
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做DINpay查詢驗證
							log.info("-----------------DINpay post for order check start -------------------");
							try {
								isCheckSuccess = checkDINPayStatus(order);
							} catch (Exception e) {
								log.error("DINpay checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("DINpay checks result is failed");
								throw new Exception("智付订单验证不通过");
							} else{
								orderId = chargeConfirmRequestDinpay.getOrder_no();
							}
							log.info("------------------DINpay post check result is success -------------------");
						}
					} else if(order.getChargeMode() == 12){ //华银
						chargeConfirmRequestHuayinpay = mapper.readValue(queueOrder.getNote(), HUAYINPayConfirmRequest.class);
						mcOrder = DTOConverter.tranHUAYINConfirmReq2MCOrder(chargeConfirmRequestHuayinpay);
						
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做DINpay查詢驗證
							log.info("-----------------HUAYINpay post for order check start -------------------");
							try {
								isCheckSuccess = checkHUAYINPayStatus(order);
							} catch (Exception e) {
								log.error("HUAYINpay checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("HUAYINpay checks result is failed");
								throw new Exception("华银订单验证不通过");
							} else{
								orderId = chargeConfirmRequestHuayinpay.getOrder_no();
							}
							log.info("------------------HUAYINpay post check result is success -------------------");
						}
					} else if(order.getChargeMode() == 13){ //银邦
						chargeConfirmRequestYinbangpay = mapper.readValue(queueOrder.getNote(), YINBANGPayConfirmRequest.class);
						
						Long amountConfirm=0L;
						if (!StringUtils.isEmpty(chargeConfirmRequestYinbangpay.getEncParam()) && !StringUtils.isEmpty(chargeConfirmRequestYinbangpay.getSign())) {
							if(SecurityRSAPay.verify(Base64Local.decode(chargeConfirmRequestYinbangpay.getEncParam()),Base64Local.decode(yinbangpayPlatformKey),Base64Local.decode(chargeConfirmRequestYinbangpay.getSign()))){
								String data = new String(SecurityRSAPay.decryptByPrivateKey(Base64Local.decode(chargeConfirmRequestYinbangpay.getEncParam()),Base64Local.decode(yinbangpayPrivateKey)), "utf-8");
								Map<String,String> resultMap = JsonUtil.fromJson2Map(data, String.class,String.class);
								if(resultMap.get("order_state")!=null && resultMap.get("order_state").equals("1003")){
									logger.info("yinbang check status decrypt success.order="+chargeConfirmRequestYinbangpay.getOrderId());
										amountConfirm=Long.valueOf(resultMap.get("money"));
								}else{
									logger.error("yinbang check status decrypt failed.order="+chargeConfirmRequestYinbangpay.getOrderId()+" respCode="+resultMap.get("respCode")+" respDesc="+resultMap.get("respDesc"));
								}
							}else{
								logger.error("yinbang check status decrypt verify failed.order="+chargeConfirmRequestYinbangpay.getOrderId()+" encParam="+chargeConfirmRequestYinbangpay.getEncParam()+" sign="+chargeConfirmRequestYinbangpay.getSign());
							}
						}else{
							logger.error("yinbang check status decrypt params invalid.order="+chargeConfirmRequestYinbangpay.getOrderId()+" encParam="+chargeConfirmRequestYinbangpay.getEncParam()+" sign="+chargeConfirmRequestYinbangpay.getSign());
						}
						
						mcOrder = new FundChargeMCOrder();
						mcOrder.setFee(0L);
						mcOrder.setNoticeTime(new Date());
						mcOrder.setSn(chargeConfirmRequestYinbangpay.getOrderId());
						mcOrder.setChannel("");
						mcOrder.setArea("");
						mcOrder.setBankFee(0L);
						mcOrder.setAmount(amountConfirm*100);
						mcOrder.setMcBankFee(0L);
						
						
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做YINBANGpay查詢驗證
							log.info("-----------------YINBANGpay post for order check start -------------------");
							try {
								isCheckSuccess = checkYINBANGPayStatus(order);
							} catch (Exception e) {
								log.error("YINBANGpay checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("YINBANGpay checks result is failed");
								throw new Exception("银邦订单验证不通过");
							} else{
								orderId = chargeConfirmRequestYinbangpay.getOrderId();
							}
							log.info("------------------YINBANGpay post check result is success -------------------");
						}
					} else if(order.getChargeMode() == 14){ //金阳
						chargeConfirmRequestJinyangpay = mapper.readValue(queueOrder.getNote(), JINYANGPayConfirmRequest.class);
						
						Long amountConfirm=0L;
						if (!StringUtils.isEmpty(chargeConfirmRequestJinyangpay.getOrderstatus()) && !StringUtils.isEmpty(chargeConfirmRequestJinyangpay.getSign())) {
							if(chargeConfirmRequestJinyangpay.getOrderstatus().equals("1")){
								String sign=chargeConfirmRequestJinyangpay.getSign();
								
								String partner=chargeConfirmRequestJinyangpay.getPartner();
								String ordernumber=chargeConfirmRequestJinyangpay.getOrdernumber();
								String orderstatus=chargeConfirmRequestJinyangpay.getOrderstatus();
								String paymoney=chargeConfirmRequestJinyangpay.getPaymoney();
								
								StringBuffer sb=new StringBuffer();
								sb.append("partner=").append(partner)
								.append("&ordernumber=").append(ordernumber)
								.append("&orderstatus=").append(orderstatus)
								.append("&paymoney=").append(paymoney)
								.append(jinyangpayKey);
								
								String sign2=MD5Encrypt.encrypt(sb.toString());
								
								if(sign.equals(sign2)){
									logger.info("jinyang check status success.order="+chargeConfirmRequestJinyangpay.getOrdernumber()+" params="+sb.toString());
									amountConfirm=new Double(Double.valueOf(paymoney)*10000).longValue();
								}else{
									logger.error("jinyang check status verify failed.order="+chargeConfirmRequestJinyangpay.getOrdernumber()+" sign="+sign+" sign2="+sign2+" params="+sb.toString());
								}
							}else{
								logger.error("jinyang check status failed.order="+chargeConfirmRequestJinyangpay.getOrdernumber()+" orderstatus="+chargeConfirmRequestJinyangpay.getOrderstatus()+" params="+JsonUtil.toJson(chargeConfirmRequestJinyangpay));
							}
						}else{
							logger.error("jinyang check status params invalid.order="+chargeConfirmRequestJinyangpay.getOrdernumber()+" params="+JsonUtil.toJson(chargeConfirmRequestJinyangpay));
						}
						
						mcOrder = new FundChargeMCOrder();
						mcOrder.setFee(0L);
						mcOrder.setNoticeTime(new Date());
						mcOrder.setSn(chargeConfirmRequestJinyangpay.getOrdernumber());
						mcOrder.setChannel("");
						mcOrder.setArea("");
						mcOrder.setBankFee(0L);
						mcOrder.setAmount(amountConfirm);
						mcOrder.setMcBankFee(0L);
						
						
						if(queueOrder.getStatus() == 7){
							//人工驗證通過，則不需做訂單驗證
						}else{
							//做JINYANGpay查詢驗證
							log.info("-----------------JINYANGpay post for order check start -------------------");
							try {
								isCheckSuccess = checkJINYANGPayStatus(order);
							} catch (Exception e) {
								log.error("JINYANGpay checks status error.", e);
							}
							if (!isCheckSuccess) { //驗證不通過
								log.info("JINYANGpay checks result is failed");
								throw new Exception("金阳订单验证不通过");
							} else{
								orderId = chargeConfirmRequestJinyangpay.getOrdernumber();
							}
							log.info("------------------JINYANGpay post check result is success -------------------");
						}
					}else{	
						throw new Exception("渠道異常");
					}

					//執行加幣流程
					fundChargeService.executeChargeDispatch(mcOrder,order,orderId);
					fundChargeQueueDaoImpl.updateOrderQueueStatus(queueOrder.getSn(),
							QueueStatus.COMPLETE.getValue());
					successNum++;

				} catch (Exception e) {
					log.error("SN:" + queueOrder.getSn() + ",訂單異常:" + e);
					try {
						FundChargeQueue queueReq = new FundChargeQueue();
						if(queueOrder.getChargeConfirmTime() >=2 ){ //總共查三次，如果驗證不過，改成異常單
							//將訂單狀態改成加幣異常
							queueReq.setStatus(QueueStatus.DISPATCHERROR.getValue());
						}else{
							queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
						}
						queueReq.setSn(queueOrder.getSn());
						queueReq.setChargeConfirmTime(queueOrder.getChargeConfirmTime()+1);
						fundChargeQueueDaoImpl.updateOrderQueue(queueReq);
					} catch (Exception e1) {
						log.error("change status error:"+e1);
					}
				}
			}
		} catch (Exception e) {
			log.error("/fund/mownecum/chargeDispatch ERROR:" + e);
		}
		return successNum;
	}
	
	/**
	 * 
	 * @param chargeConfirmRequest
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */

	private boolean checkOrderFromThPay (FundChargeOrder order) throws Exception{
		boolean checkResult = false;
		
		//驗證訂單狀態是否正確，送出請求給通匯做比對-------------------------------------
		ThPayOrderCheckQuery thPayOrderCheckQuery = new ThPayOrderCheckQuery();
		//簽名用參數
		thPayOrderCheckQuery.setInputCharset(inputCharset);
		thPayOrderCheckQuery.setMerchantCode(merchantCode);
		thPayOrderCheckQuery.setOrderNo(order.getSn());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("input_charset", inputCharset);
		params.put("merchant_code", merchantCode);
		params.put("sign", MD5Encrypt.encrypt(thPayOrderCheckQuery.createParam(thpayKey)));
		params.put("order_no", order.getSn());

		
		String xml = mcwClient.invokeHttpXml(thpayUrl + thpayOrderCheckQueryUrl, params);
		logger.info("ThPay Response  = " + xml);
		//將接收的xml轉成java物件
		JAXBContext context = JAXBContext.newInstance(ThPayQueryOrderCheckResponse.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		ThPayQueryOrderCheckResponse thPayQueryOrderCheckResponse = (ThPayQueryOrderCheckResponse) unmarshal.unmarshal(new StringReader(xml));
		
		//驗證結果是否符合用戶申請		
		if(thPayQueryOrderCheckResponse.getThPayQueryResponseOrderCheckDetail().getTrade_status()!=null && 
			thPayQueryOrderCheckResponse.getThPayQueryResponseOrderCheckDetail().getOrder_amount()!=null){
			
			String order_status = thPayQueryOrderCheckResponse.getThPayQueryResponseOrderCheckDetail().getTrade_status();
			String amount_request = String.valueOf((double)order.getPreChargeAmt()/unitConvert);
			String amount_response = thPayQueryOrderCheckResponse.getThPayQueryResponseOrderCheckDetail().getOrder_amount();
			//驗證訂單狀態是否為成功  and  訂單金額與充值金額是否符合
			if(order_status.equals("success") && amount_response.equals(amount_request)){				
				checkResult=true;			
			}else{
				logger.info("order_status="+order_status);
				logger.info("amount_request="+(double)order.getPreChargeAmt()/unitConvert);
				logger.info("amount_response="+amount_response);				
			}
		}
		return checkResult;
	}
	
	/**
	 * @Title 驗證接收到的訂單資料 
	 * @param ECPSSConfirmRequest
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */
	
	private boolean checkECPSSPayStatus(FundChargeOrder order ) throws Exception{
		boolean checkResult = false;
		//準備送出的參數用xml
		Root root = new Root();
		root.setTx(txCode);
		root.setMerCode(merNo);
		root.setOrderNumber(order.getSn());
	    String sign= MD5Encrypt.encrypt( merNo + ecpssKey).toUpperCase();
	    root.setSign(sign);
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    String generateXml = "";
	    String xml="";
	    String base64Xml="";
	    try {
	    	//轉換成xml格式
			JAXBContext jc = JAXBContext.newInstance(root.getClass());
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(root, os);
			generateXml = new String(os.toByteArray(), "UTF-8");
			logger.info("generateXml  = " + generateXml);
			//將xml轉換成base64格式
			byte[] message = generateXml.getBytes("UTF-8");
		    base64Xml = DatatypeConverter.printBase64Binary(message);
		    logger.info("generateBase64 = "+base64Xml);
		} finally {
			os.close();
		}
	    //包裝送出的請求參數
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("requestDomain", base64Xml);
	    xml = mcwClient.invokeHttpXml(ecpssUrl + ecpssQueryUrl, params);
	    
	    logger.info("ECPSS Response  = " + xml);
		JAXBContext context = JAXBContext.newInstance(ECPSSQueryResponse.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		ECPSSQueryResponse response = (ECPSSQueryResponse)unmarshal.unmarshal(new StringReader(xml));

		List<ECPSSQueryResponseDetail> list = response.getList();

		if(list != null && list.size() > 0){
			ECPSSQueryResponseDetail detail = list.get(0);
			//驗證訂單狀態是否為成功  and  訂單金額與充值金額是否符合
			int result = new BigDecimal(detail.getOrderAmount()).compareTo(new BigDecimal(order.getPreChargeAmt()).divide(new BigDecimal(unitConvert)));			
			if("1".equals(detail.getOrderStatus()) && result==0){
				checkResult = true;
			}else{
				logger.info("orderStatus = "+detail.getOrderStatus());
				logger.info("amount_request = "+new BigDecimal(order.getPreChargeAmt()).divide(new BigDecimal(unitConvert)));  //FF4.0平台   request
				logger.info("amount_response = "+new BigDecimal(detail.getOrderAmount())); //callback response
			}
		}
		
		return checkResult;
		
	}
	/**
	 * @Title 驗證接收到的訂單資料 
	 * @param ECPSSConfirmRequest
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */

	private boolean checkWORTHPayStatus (FundChargeOrder order) throws Exception{
		boolean checkResult = false;
		
		//驗證訂單狀態是否正確，送出請求給華勢做比對-------------------------------------
		WorthPayOrderCheckQuery worthPayOrderCheckQuery =new WorthPayOrderCheckQuery();
		worthPayOrderCheckQuery.setCharset("utf8");
		worthPayOrderCheckQuery.setMerchant_ID(worthMerchantID);
		worthPayOrderCheckQuery.setOrder_no(order.getSn());
		String md5Params = worthPayOrderCheckQuery.createParam(worthMd5key);		
		logger.info("華勢查詢訂單MD5拼接："+ md5Params);
		
		String MD5str = MD5Encrypt.encrypt(md5Params);
		worthPayOrderCheckQuery.setSign(MD5str);
		worthPayOrderCheckQuery.setSign_type("MD5");
		logger.info("華勢查詢訂單Params： charset="+ worthPayOrderCheckQuery.getCharset()+"merchant_id="+worthPayOrderCheckQuery.getMerchant_ID()
				+"order_no="+worthPayOrderCheckQuery.getOrder_no()+"sign_type="+worthPayOrderCheckQuery.getSign_type()+"sign="+worthPayOrderCheckQuery.getSign());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("charset",worthPayOrderCheckQuery.getCharset());
		params.put("merchant_ID",worthPayOrderCheckQuery.getMerchant_ID());
		params.put("order_no",worthPayOrderCheckQuery.getOrder_no());
		params.put("sign_type",worthPayOrderCheckQuery.getSign_type());
		params.put("sign",worthPayOrderCheckQuery.getSign());
		
		String xml = mcwClient.invokeHttpXml(worthUrl + worthOrderCheckQueryUrl, params);
		logger.info("WorthPay Response  = " + xml);
		//將接收的xml轉成java物件
		JAXBContext context = JAXBContext.newInstance(WorthPayQueryResponse.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		WorthPayQueryResponse worthPayQueryResponse = (WorthPayQueryResponse) unmarshal.unmarshal(new StringReader(xml));
		
		//查詢訂單是否成功
		if(worthPayQueryResponse.getIs_success()!=null && worthPayQueryResponse.getIs_success().equals("T")){
			//驗證結果是否符合用戶申請		
			if(worthPayQueryResponse.getWorthPayQueryResponseDetail().getStatus()!=null && 
					worthPayQueryResponse.getWorthPayQueryResponseDetail().getAmount()!=null){
				String order_status = worthPayQueryResponse.getWorthPayQueryResponseDetail().getStatus();
				String amount_request = String.valueOf((double)order.getPreChargeAmt()/unitConvert);
				String amount_response = worthPayQueryResponse.getWorthPayQueryResponseDetail().getAmount();
				String result_code = worthPayQueryResponse.getResult_code();
				//驗證訂單狀態是否為成功  and  訂單金額與充值金額是否符合
				int result = new BigDecimal(amount_response).compareTo(new BigDecimal(amount_request));
				if(order_status.equals("completed") && result==0){				
					checkResult=true;			
				}else{
					logger.info("order_status="+order_status);
					logger.info("amount_request="+(double)order.getPreChargeAmt()/unitConvert);
					logger.info("amount_response="+amount_response);
					logger.info("result_code(錯誤碼)="+result_code);	
				}
			}
		}else{
			logger.info("查詢訂單失敗is_success:"+worthPayQueryResponse.getIs_success());
		}
		return checkResult;
	}
	/**
	 * @Title 驗證接收到的訂單資料 
	 * @param FundChargeOrder
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */
	
	private boolean checkHBPayStatus(FundChargeOrder order ) throws Exception{
		boolean checkResult = false;
		
		String hbpayDomain = configService.getConfigValueByKey("fund", "hbpay_confirm_url");
		
		//簽名
		Map<String, Object> signMap = new HashMap<String, Object>();
		signMap.put("orderId", order.getSn());
		String signData = JsonMapper.nonAlwaysMapper().toJson(signMap);
		log.info("sign:"+signData );
		byte[] signParam1=Base64.decode(hbpayPrivateKey.getBytes());
		byte[] sign=LocalUtil.sign(signParam1, signData);
		//加密
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("account", hbpay_account);
		dataMap.put("orderCode", hbpayChargeConfirmOrderCode);
		dataMap.put("msg", signData);
		String data = JsonMapper.nonAlwaysMapper().toJson(dataMap);
		log.info("data:"+data );
		
		String param=Base64.encodeToString(data);
		PublicKey param2 = RSAUtils.loadPublicKey(hbpayPublicKey);
		
		byte[] decrypt = RSAUtils.encryptData(param.getBytes(),param2);
		
		//待送出參數
		String dataValue= Base64Utils.encode(decrypt);
		String signatureValue=new String(sign);
		
		
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("data",dataValue);
		requestMap.put("signature",signatureValue);
		requestMap.put("pass", "1");
		String request = JsonMapper.nonAlwaysMapper().toJson(requestMap);
		log.debug("resquest:"+request);
		String response = mcwClient.postHttpJson("http://"+hbpayDomain, request);
		log.info("hbResponse:"+response.toString());
		
		//解密
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = DataConverterUtil.convertJson2Map(response.toString());
		String msg = responseMap.get("data").toString();
		String signature = responseMap.get("signature").toString();
		PrivateKey privateKey = RSAUtils.loadPrivateKey(hbpayPrivateKey);
		byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(msg), privateKey);
		
		String decryptStr = new String(decryptByte);
		String datas = Base64.decodeToString(decryptStr);
		Map<String, Object> datasMap = new HashMap<String, Object>();
		datasMap = DataConverterUtil.convertJson2Map(datas);
		String msgData = datasMap.get("msg").toString();
		log.debug("解密:"+datas);
		log.info("解密msg:"+msgData);
		
		Map<String, Object> msgMap = new HashMap<String, Object>();
		msgMap = DataConverterUtil.convertJson2Map(msgData.toString());
		
		boolean vfy = LocalUtil.verifySignature(Base64.decode(hbpayPublicKey.getBytes()), signature, msgData.getBytes("UTF-8"));
        log.debug("vfy="+vfy);
        
        if("SUCCESS".equals(msgMap.get("respInfo")) && vfy){
        	checkResult=true;
        }else{
        	checkResult=false;
        }
		
		return checkResult;
		
	}
	/**
	 * @Title 驗證接收到的訂單資料 
	 * @param FundChargeOrder
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */
	
	private boolean checkSPPayStatus(FundChargeOrder order ) throws Exception{
		boolean checkResult = false;
		
		
		String signDataSrc = "ORDERNO="+order.getSn();
		
		String SIGN = SignUtils.Signaturer(signDataSrc, sppayPrivateKey);
		String param="MERCNUM="+java.net.URLEncoder.encode(sppayAccount,"UTF-8")+"&TRANDATA="+java.net.URLEncoder.encode(signDataSrc,"UTF-8")+"&SIGN="+java.net.URLEncoder.encode(SIGN,"UTF-8");
		
		logger.info(" params : " + param);
			
		String response = mcwClient.postHttpJson(sppayQueryOrderUrl, param);
		logger.info("charge result:" + response);

		Map<String,String> resMap = JsonUtil.fromJson2Map(response, String.class,String.class);
        String recode = resMap.get("RECODE");
        if(recode.equals("000000")){ 
        	checkResult=true;
        }else{
        	checkResult=false;
        }
		
		return checkResult;
		
	}
	/**
	 * @Title 驗證接收到的訂單資料 
	 * @param FundChargeOrder
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */
	
	private boolean checkDDBPayStatus(FundChargeOrder order ) throws Exception{
		String merchantCode=ddbpayAccount;
		String serviceType="single_trade_query";
		String interfaceVersion="V3.3";
		String signType="RSA-S";
		String orderNo=order.getSn();
		
		StringBuffer signSrc= new StringBuffer();
		signSrc.append("interface_version=").append(interfaceVersion).append("&");
		signSrc.append("merchant_code=").append(merchantCode).append("&");				
		signSrc.append("order_no=").append(orderNo).append("&");
		signSrc.append("service_type=").append(serviceType);		
		
		String signInfo = signSrc.toString();
		
		logger.info("signInfo:"+signInfo);
		//签名
		String sign = new String(LocalUtil.sign(Base64.decode(ddbpayPrivateKey.getBytes()),signInfo));
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("merchant_code", merchantCode);
		reqMap.put("service_type", serviceType);
		reqMap.put("interface_version", interfaceVersion);
		reqMap.put("sign_type", signType);
		reqMap.put("order_no", orderNo);
		reqMap.put("sign", sign);
			
		String response = mcwClient.postHttpForm(ddbpayQueryOrderUrl, reqMap,"UTF-8");
		logger.info("charge result:" + response);
		JAXBContext context = JAXBContext.newInstance(DdbQueryResponse.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		DdbQueryResponse queryRes= (DdbQueryResponse)unmarshal.unmarshal(new StringReader(response));
		boolean checkResult=false;
		if(queryRes!=null && queryRes.getDdbQueryResponseDetail()!=null && queryRes.getDdbQueryResponseDetail().getIsSuccess()!=null
				&& queryRes.getDdbQueryResponseDetail().getIsSuccess().equals("T")){
			if(queryRes.getDdbQueryResponseDetail().getDdbQueryTradeResponseDetail()!=null
					&& queryRes.getDdbQueryResponseDetail().getDdbQueryTradeResponseDetail().getTradeStatus()!=null
					&& queryRes.getDdbQueryResponseDetail().getDdbQueryTradeResponseDetail().getTradeStatus().equals("SUCCESS")){
				checkResult=true;
			}else{
				logger.error("ddb query request failed.order="+order.getSn()+ " result desc:"+queryRes.getDdbQueryResponseDetail().getDdbQueryTradeResponseDetail().getTradeStatus());
				checkResult=false;
			}
		}
		return checkResult;
		
	}
	/**
	 * @Title 驗證接收到的訂單資料 
	 * @param FundChargeOrder
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */
	
	private boolean checkDINPayStatus(FundChargeOrder order ) throws Exception{
		String merchantCode=dinpayAccount;
		String serviceType="single_trade_query";
		String interfaceVersion="V3.0";
		String signType="RSA-S";
		String orderNo=order.getSn();
		
		StringBuffer signSrc= new StringBuffer();
		signSrc.append("interface_version=").append(interfaceVersion).append("&");
		signSrc.append("merchant_code=").append(merchantCode).append("&");				
		signSrc.append("order_no=").append(orderNo).append("&");
		signSrc.append("service_type=").append(serviceType);		
		
		String signInfo = signSrc.toString();
		
		logger.info("signInfo:"+signInfo);
		//签名
		String sign = new String(LocalUtil.sign(Base64.decode(dinpayPrivateKey.getBytes()),signInfo));
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("merchant_code", merchantCode);
		reqMap.put("service_type", serviceType);
		reqMap.put("interface_version", interfaceVersion);
		reqMap.put("sign_type", signType);
		reqMap.put("order_no", orderNo);
		reqMap.put("sign", sign);
			
		String response = mcwClient.postHttpForm(dinpayQueryOrderUrl, reqMap,"UTF-8");
		logger.info("charge result:" + response);
		JAXBContext context = JAXBContext.newInstance(DinQueryResponse.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		DinQueryResponse queryRes= (DinQueryResponse)unmarshal.unmarshal(new StringReader(response));
		boolean checkResult=false;
		if(queryRes!=null && queryRes.getDinQueryResponseDetail()!=null && queryRes.getDinQueryResponseDetail().getIsSuccess()!=null
				&& queryRes.getDinQueryResponseDetail().getIsSuccess().equals("T")){
			if(queryRes.getDinQueryResponseDetail().getDinQueryTradeResponseDetail()!=null
					&& queryRes.getDinQueryResponseDetail().getDinQueryTradeResponseDetail().getTradeStatus()!=null
					&& queryRes.getDinQueryResponseDetail().getDinQueryTradeResponseDetail().getTradeStatus().equals("SUCCESS")){
				checkResult=true;
			}else{
				logger.error("din query request failed.order="+order.getSn()+ " result desc:"+queryRes.getDinQueryResponseDetail().getDinQueryTradeResponseDetail().getTradeStatus());
				checkResult=false;
			}
		}
		return checkResult;
		
	}
	/**
	 * @Title 驗證接收到的訂單資料 
	 * @param FundChargeOrder
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */
	
	private boolean checkYINBANGPayStatus(FundChargeOrder order ) throws Exception{
		String merId=yinbangpayAccount;
		String version="1.0.9";
		String businessOrdid=order.getSn();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessOrdid", businessOrdid);

		String jsonParam = JsonUtil.toJson(map);
		logger.info("signInfo:"+jsonParam);
		//服务器公钥加密
		byte by[] = SecurityRSAPay.encryptByPublicKey(jsonParam.getBytes("UTF-8"), Base64Local.decode(yinbangpayPlatformKey));
		String encParam = Base64Local.encodeToString(by, true);
		
		//商户自己的私钥签名
		byte signBy[] = SecurityRSAPay.sign(by, Base64Local.decode(yinbangpayPrivateKey));
		String sign = Base64Local.encodeToString(signBy, true);
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("encParam", encParam);
		reqMap.put("merId", merId);
		reqMap.put("version", version);
		reqMap.put("sign", sign);
		
		String response = mcwClient.postHttpForm(yinbangpayQueryOrderUrl, reqMap,"UTF-8");
		logger.info("charge result:" + response);
		
		
		
		Map<String,String> resMap = JsonUtil.fromJson2Map(response, String.class,String.class);
		boolean checkResult=false;
		if (!StringUtils.isEmpty(resMap.get("encParam")) && !StringUtils.isEmpty(resMap.get("sign"))) {
			if(SecurityRSAPay.verify(Base64Local.decode(resMap.get("encParam")),Base64Local.decode(yinbangpayPlatformKey),Base64Local.decode(resMap.get("sign")))){
				// 商户自己私钥解密
				String data = new String(SecurityRSAPay.decryptByPrivateKey(Base64Local.decode(resMap.get("encParam")),Base64Local.decode(yinbangpayPrivateKey)), "utf-8");
				Map<String,String> resultMap = JsonUtil.fromJson2Map(data, String.class,String.class);
				if(resultMap.get("respCode")!=null && resultMap.get("respCode").equals("1000")){
					logger.info("yinbang check status  success.order="+order.getSn());
					checkResult=true;
				}else{
					logger.error("yinbang check status   failed.order="+order.getSn()+" respCode="+resultMap.get("respCode")+" respDesc="+resultMap.get("respDesc"));
				}
				
			}else{
				logger.error("yinbang check status verify failed.order="+order.getSn()+" encParam="+resMap.get("encParam")+" sign="+resMap.get("sign"));
			}
			
		}else{
			logger.error("yinbang check status params invalid.order="+order.getSn()+" encParam="+resMap.get("encParam")+" sign="+resMap.get("sign"));
		}
		return checkResult;
		
	}
	private boolean checkJINYANGPayStatus(FundChargeOrder order ) throws Exception{
		String merId=jinyangpayAccount;
		String orderno=order.getSn();
		String version="v2.8";
		String signType="1";	//MD5
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("p1_mchtid", merId);
		map.put("p2_signtype", signType);
		map.put("p3_orderno", orderno);
		map.put("p4_version", version); 
		
		StringBuffer sb = new StringBuffer();
		sb.append("p1_mchtid").append("=").append(merId).append("&")
		.append("p2_signtype").append("=").append(signType).append("&")
		.append("p3_orderno").append("=").append(orderno).append("&")
		.append("p4_version").append("=").append(version)
        .append(jinyangpayKey);
		
		map.put("sign", MD5Encrypt.encrypt(sb.toString()));

		String jsonParam = JsonUtil.toJson(map);
		logger.info("signInfo:"+jsonParam);
		String response = mcwClient.postHttpForm(jinyangpayQueryOrderUrl, map,"UTF-8");
		logger.info("jinyang query order result:" + response);
		JinYangPayQueryResponse res = JsonUtil.fromJson(response, JinYangPayQueryResponse.class);
		boolean checkResult=false;
		if (!StringUtils.isEmpty(res.getRspCode())) {
			String rspCode=res.getRspCode();
			String rspMsg=res.getRspMsg();
			if(rspCode.equals("1")){
				JinYangPayQueryDetailResponse data=res.getData();
				if(data!=null){
					
					String r1_mchtid=data.getR1_mchtid();
					String r2_systemorderno=data.getR2_systemorderno();
					String r3_orderno=data.getR3_orderno();
					String r4_amount=BigDecimal.valueOf(Double.valueOf(data.getR4_amount())).stripTrailingZeros().toPlainString();
					String r5_orderstate=data.getR5_orderstate();
					String r6_version=data.getR6_version();
					String sign=data.getSign();
					StringBuffer sbRs=new StringBuffer();
					sbRs.append("r1_mchtid=").append(r1_mchtid)
					.append("&").append("r2_systemorderno=").append(r2_systemorderno)
					.append("&").append("r3_orderno=").append(r3_orderno)
					.append("&").append("r4_amount=").append(r4_amount)
					.append("&").append("r5_orderstate=").append(r5_orderstate)
					.append("&").append("r6_version=").append(r6_version)
					.append(jinyangpayKey);
					
					String sign2=MD5Encrypt.encrypt(sbRs.toString());
					if(!StringUtils.isEmpty(sign) && sign.equals(sign2)){
						logger.info("jinyang query order  success.order="+order.getSn());
						checkResult=true;
					}else{
						logger.error("jinyang query order failed.order="+order.getSn()+" sign="+sign+" sign2="+sign2+" params="+sbRs.toString());
					}
				}else{
					logger.error("jinyang query order failed.order="+order.getSn()+" data="+data);
				}
			}else{
				logger.error("jinyang query order failed.order="+order.getSn()+" rspCode="+rspCode+" rspMsg="+rspMsg);
			}
		}else{
			logger.error("jinyang pay request invalid.order="+order.getSn()+" response="+response);
		}
		return checkResult;
		
	}
	/**
	 * @Title 驗證接收到的訂單資料 
	 * @param FundChargeOrder
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */
	
	private boolean checkHUAYINPayStatus(FundChargeOrder order ) throws Exception{
		String merchantCode=huayinpayAccount;
		String serviceType="single_trade_query";
		String interfaceVersion="V3.0";
		String signType="RSA-S";
		String orderNo=order.getSn();
		
		StringBuffer signSrc= new StringBuffer();
		signSrc.append("interface_version=").append(interfaceVersion).append("&");
		signSrc.append("merchant_code=").append(merchantCode).append("&");				
		signSrc.append("order_no=").append(orderNo).append("&");
		signSrc.append("service_type=").append(serviceType);		
		
		String signInfo = signSrc.toString();
		
		logger.info("signInfo:"+signInfo);
		//签名
		String sign = new String(LocalUtil.sign(Base64.decode(huayinpayPrivateKey.getBytes()),signInfo));
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("merchant_code", merchantCode);
		reqMap.put("service_type", serviceType);
		reqMap.put("interface_version", interfaceVersion);
		reqMap.put("sign_type", signType);
		reqMap.put("order_no", orderNo);
		reqMap.put("sign", sign);
			
		String response = mcwClient.postHttpForm(huayinpayQueryOrderUrl, reqMap,"UTF-8");
		logger.info("charge result:" + response);
		JAXBContext context = JAXBContext.newInstance(HuaYinQueryResponse.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		HuaYinQueryResponse queryRes= (HuaYinQueryResponse)unmarshal.unmarshal(new StringReader(response));
		boolean checkResult=false;
		if(queryRes!=null && queryRes.getHuaYinQueryResponseDetail()!=null && queryRes.getHuaYinQueryResponseDetail().getIsSuccess()!=null
				&& queryRes.getHuaYinQueryResponseDetail().getIsSuccess().equals("T")){
			if(queryRes.getHuaYinQueryResponseDetail().getHuaYinQueryTradeResponseDetail()!=null
					&& queryRes.getHuaYinQueryResponseDetail().getHuaYinQueryTradeResponseDetail().getTradeStatus()!=null
					&& queryRes.getHuaYinQueryResponseDetail().getHuaYinQueryTradeResponseDetail().getTradeStatus().equals("SUCCESS")){
				checkResult=true;
			}else{
				logger.error("din query request failed.order="+order.getSn()+ " result desc:"+queryRes.getHuaYinQueryResponseDetail().getHuaYinQueryTradeResponseDetail().getTradeStatus());
				checkResult=false;
			}
		}
		return checkResult;
		
	}
	/**
	 * @Title 驗證接收到的訂單資料 
	 * @param FundChargeOrder
	 * @return boolean false不通過, true通過
	 * @throws Exception
	 */
	
	private boolean checkWFTPayStatus(FundChargeOrder order ) throws Exception{
		String version="1.0";
		String partner=wftpayAccount;
		String ordernumber=order.getSn();
		
		StringBuffer mainStr= new StringBuffer();
		mainStr.append("version=").append(version).append("&");
		mainStr.append("partner=").append(partner).append("&");	
		mainStr.append("ordernumber=").append(ordernumber).append("&");	
		mainStr.append("sysnumber=").append("");
		
		
		StringBuffer signStr= new StringBuffer();
		signStr.append(mainStr).append("&");
		signStr.append("key=").append(wftpayPrivateKey);
		String sign=MD5Encrypt.encrypt(signStr.toString());
		
		StringBuffer paramStr= new StringBuffer();
		paramStr.append(mainStr).append("&");
		paramStr.append("sign=").append(sign);
			
		logger.info("query order paramStr:" + paramStr);
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("version", version);
		reqMap.put("partner", partner);
		reqMap.put("ordernumber", ordernumber);
		reqMap.put("sysnumber", "");
		reqMap.put("sign", sign);
			
		String response = mcwClient.postHttpForm(wftpayQueryOrderUrl, reqMap,"UTF-8");
		logger.info("charge result:" + response);

		Map<String,String> resMap = JsonUtil.fromJson2Map(response, String.class,String.class);
		boolean checkResult=false;
		if(resMap!=null && resMap.get("status")!=null && resMap.get("status").equals("1")){
			if(resMap.get("tradestate")!=null && resMap.get("tradestate").equals("1")){
				checkResult=true;
			}else{
				logger.error("ddb query request failed.order="+order.getSn()+ " result desc:"+resMap.get("tradestate"));
				checkResult=false;
			}
		}
		return checkResult;
		
	}

	
}
