/**   
* @Title: FundChargeController.java 
* @Package com.winterframework.firefrog.fund.web.controller 
* @Description: 资金充值类
* @author Tod
* @date 2013-7-2 上午10:52:49 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.controller;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestHeader;
import com.winterframework.firefrog.common.config.dao.impl.ConfigDaoImpl;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.common.util.UserTools;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder.Status;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.enums.DDBFundBank.DDBBank;
import com.winterframework.firefrog.fund.enums.FundChargeEnum;
import com.winterframework.firefrog.fund.enums.HBFundBank.HBBank;
import com.winterframework.firefrog.fund.enums.JYFundBank.JYBank;
import com.winterframework.firefrog.fund.enums.SPFundBank.SPBank;
import com.winterframework.firefrog.fund.enums.THFundBank.THBank;
import com.winterframework.firefrog.fund.exception.FundChargeHighException;
import com.winterframework.firefrog.fund.exception.FundChargeLowException;
import com.winterframework.firefrog.fund.service.IBankCardService;
import com.winterframework.firefrog.fund.service.IBypassConfigService;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.fund.service.impl.ddb.DDBDeposit;
import com.winterframework.firefrog.fund.service.impl.din.DINDeposit;
import com.winterframework.firefrog.fund.service.impl.ecpss.ECPSSDeposit;
import com.winterframework.firefrog.fund.service.impl.hb.HBDeposit;
import com.winterframework.firefrog.fund.service.impl.huayin.HUAYINDeposit;
import com.winterframework.firefrog.fund.service.impl.jinyang.JINYANGDeposit;
import com.winterframework.firefrog.fund.service.impl.mow.MownecumWithdrawResponseData;
import com.winterframework.firefrog.fund.service.impl.sp.SPDeposit;
import com.winterframework.firefrog.fund.service.impl.th.THDeposit;
import com.winterframework.firefrog.fund.service.impl.wft.WFTDeposit;
import com.winterframework.firefrog.fund.service.impl.worth.UtilSign;
import com.winterframework.firefrog.fund.service.impl.worth.WORTHDeposit;
import com.winterframework.firefrog.fund.service.impl.yinbang.YINBANGDeposit;
import com.winterframework.firefrog.fund.util.NumUtil;
import com.winterframework.firefrog.fund.util.hbpay.Base64;
import com.winterframework.firefrog.fund.util.hbpay.Base64Utils;
import com.winterframework.firefrog.fund.util.hbpay.LocalUtil;
import com.winterframework.firefrog.fund.util.hbpay.RSAUtils;
import com.winterframework.firefrog.fund.util.sppay.SignUtils;
import com.winterframework.firefrog.fund.util.yinbangpay.Base64Local;
import com.winterframework.firefrog.fund.util.yinbangpay.SecurityRSAPay;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.CountResultPager;
import com.winterframework.firefrog.fund.web.dto.ChargeApplyRequest;
import com.winterframework.firefrog.fund.web.dto.ChargeApplyResponse;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.firefrog.fund.web.dto.ChargeStruc;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.fund.web.dto.FundUserChargeStruc;
import com.winterframework.firefrog.user.service.ILevelRecycleService;
import com.winterframework.firefrog.user.web.dto.QueryLevelRecycleHistoryResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.security.MD5Encrypt;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName: FundChargeController 
* @Description: 资金充值类
* @author Tod
* @date 2013-7-2 上午10:52:49 
*  
*/
@Controller("fundChargeController")
@RequestMapping(value = "/fund")
public class FundChargeController {

	private static Logger logger = LoggerFactory.getLogger(FundChargeController.class);

	@Resource(name = "fundChargeServiceImpl")
	private IFundChargeService fundChargeService;
	@Resource(name = "levelRecycleServiceImpl")
	private ILevelRecycleService levelRecycleService;
	@Resource(name = "bankCardServiceImpl")
	private IBankCardService bankCardService;
	@Resource
	private ConfigDaoImpl configDaoImpl;
	@PropertyConfig(value = "deposit_cancel_url")
	private String mcUrl;
	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient mcwClient;
	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	@PropertyConfig(value = "platVersion")
	private String platVersion;
	@Resource(name = "bypassConfigServiceImpl")  
	private IBypassConfigService bypassConfigService;
	
	@PropertyConfig(value = "thpay_company_id")
	private String merchantCode;
	
	@PropertyConfig(value = "thpay_key")
	private String thpayKey;
	
	@PropertyConfig(value = "ecpss_company_id")
	private String merNo;
	
	
	@PropertyConfig(value = "hbpay_channel_code")
	private String hbpayChannelCode;
	
	@PropertyConfig(value = "hbpay_charge_order_code")
	private String hbpayChargeOrderCode;
	
	@PropertyConfig(value = "hbpay_public_key")
	private String hbpayPublicKey;
	
	@PropertyConfig(value = "hbpay_private_key")
	private String hbpayPrivateKey;
	
	@PropertyConfig(value = "hbpay_account")
	private String hbpay_account;
	
	
	@PropertyConfig(value = "worth_merchant_ID")
	private String worthMerchantID;
	
	@PropertyConfig(value = "worth_seller_email")
	private String worthSellerEmail;
	
	@PropertyConfig(value = "worth_md5key")
	private String worthMd5key;
	
	@PropertyConfig(value = "sppay_private_key")
	private String sppayPrivateKey;
	@PropertyConfig(value = "sppay_account")
	private String sppayAccount;
	@PropertyConfig(value = "ddbpay_account")
	private String ddbpayAccount;
	@PropertyConfig(value = "ddbpay_private_key")
	private String ddbpayPrivateKey;
	@PropertyConfig(value = "ddbpay_qrurl")
	private String ddbpayQrUrl;
	@PropertyConfig(value = "ddbpay_bankurl")
	private String ddbpayBankUrl;
	@PropertyConfig(value = "wftpay_account")
	private String wftpayAccount;
	@PropertyConfig(value = "wftpay_private_key")
	private String wftpayPrivateKey;
	@PropertyConfig(value = "wftpay_qrurl")
	private String wftpayQrUrl;
	
	@PropertyConfig(value = "dinpay_account")
	private String dinpayAccount;
	@PropertyConfig(value = "dinpay_private_key")
	private String dinpayPrivateKey;
	@PropertyConfig(value = "dinpay_qrurl")
	private String dinpayQrUrl;
	@PropertyConfig(value = "dinpay_bankurl")
	private String dinpayBankUrl;
	
	@PropertyConfig(value = "huayinpay_account")
	private String huayinpayAccount;
	@PropertyConfig(value = "huayinpay_private_key")
	private String huayinpayPrivateKey;
	@PropertyConfig(value = "huayinpay_qrurl")
	private String huayinpayQrUrl;
	@PropertyConfig(value = "huayinpay_bankurl")
	private String huayinpayBankUrl;
	
	@PropertyConfig(value = "yinbangpay_account")
	private String yinbangpayAccount;
	@PropertyConfig(value = "yinbangpay_terminal")
	private String yinbangpayTerminal;
	@PropertyConfig(value = "yinbangpay_private_key")
	private String yinbangpayPrivateKey;
	@PropertyConfig(value = "yinbangplt_public_key")
	private String yinbangpayPlatformKey;
	@PropertyConfig(value = "yinbangpay_qrurl")
	private String yinbangpayQrUrl;
	@PropertyConfig(value = "yinbangpay_bankurl")
	private String yinbangpayBankUrl;
	
	
	@PropertyConfig(value = "jinyangpay_account")
	private String jinyangpayAccount;
	@PropertyConfig(value = "jinyangpay_key")
	private String jinyangpayKey;
	@PropertyConfig(value = "jinyangpay_qrurl")
	private String jinyangpayQrUrl;
	@PropertyConfig(value = "jinyangpay_bankurl")
	private String jinyangpayBankUrl;
	/**
	 * 
	* @Title: cancelOrder 
	* @Description: 取消订单
	* @param request
	* @return
	* @throws Exception
	 */

	/**
	 * 
	* @Title: chargeApply 
	* @Description: 充值申请
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/chargeApply")
	@ResponseBody
	public Response<ChargeApplyResponse> chargeApply(
			@RequestBody @ValidRequestHeader Request<ChargeApplyRequest> request) throws Exception {
		Response<ChargeApplyResponse> response = new Response<ChargeApplyResponse>(request);

		ChargeApplyRequest chargeApplyRequest = request.getBody().getParam();

		
		FundChargeOrder order = DTOConverter.tranChargeApplyReq2Order(chargeApplyRequest);
		order.setApplyUser(UserTools.getUserFromHead(request));
		ChargeApplyResponse resp = new ChargeApplyResponse();
		try {
			int bypass = 0;
			logger.info("depositMode : " + chargeApplyRequest.getDepositMode());
			logger.info("platfom : " + chargeApplyRequest.getPlatfom());
			bypass = bypassConfigService.checkChargeBypass(order.getPreChargeAmt(),order.getApplyUser().getId(), 0L,chargeApplyRequest.getDepositMode(),order.getPlatfom());
			logger.info("final bypass : " + bypass);
			
			FundChargeOrder resultOrder = null;
			THDeposit thpay = null;
			HBDeposit hbpay = null;
			ECPSSDeposit pay = null;
			WORTHDeposit worthpay = null;
			SPDeposit sppay = null;
			DDBDeposit ddbpay = null;
			WFTDeposit wftpay = null;
			DINDeposit dinpay = null;
			HUAYINDeposit huayinpay = null;
			YINBANGDeposit yinbangpay = null;
			JINYANGDeposit jinyangpay = null;
			switch(bypass){
			case 1://通匯
				order.setChargeMode(FundChargeEnum.ChargeMode.THPAY.getValue());
				order.setCustomerIp(chargeApplyRequest.getCustomerIp());
				resultOrder = fundChargeService.thpayApply(order);
				thpay = createTHParams(resultOrder);
				break;
			case 3://匯潮
				logger.info("bypass == 3");
				order.setChargeMode(FundChargeEnum.ChargeMode.ECPSS.getValue());
				resultOrder = fundChargeService.ecpssApply(order);
				pay = createECPSS(resultOrder);
				break;
			case 4://匯博
				logger.info("bypass == 4");
				order.setChargeMode(FundChargeEnum.ChargeMode.HBPAY.getValue());
				resultOrder = fundChargeService.hbpayApply(order);
				hbpay = createHBParams(resultOrder);
				break;
			case 7://華勢
				logger.info("bypass == 7");
				order.setChargeMode(FundChargeEnum.ChargeMode.WORTH.getValue());
				resultOrder = fundChargeService.worthApply(order);
				worthpay = createWORTH(resultOrder);
				break;
			case 8://秒付
				logger.info("bypass == 8");
				order.setChargeMode(FundChargeEnum.ChargeMode.SP.getValue());
				resultOrder = fundChargeService.spApply(order);
				if(!isQr(order.getDepositMode())){
					sppay = createSp(resultOrder);
				}
				break;
			case 9://多得宝
				logger.info("bypass == 9");
				order.setChargeMode(FundChargeEnum.ChargeMode.DDB.getValue());
				order.setCustomerIp(chargeApplyRequest.getCustomerIp());
				resultOrder = fundChargeService.ddbApply(order);
				if(!isQr(order.getDepositMode())){
					ddbpay = createDdb(resultOrder);
				}
				break;
			case 10://网富通
				logger.info("bypass == 10");
				order.setChargeMode(FundChargeEnum.ChargeMode.WFT.getValue());
				order.setCustomerIp(chargeApplyRequest.getCustomerIp());
				resultOrder = fundChargeService.wftApply(order);
				if(!isQr(order.getDepositMode())){
					wftpay = createWft(resultOrder);
				}
				break;
			case 11://智付
				logger.info("bypass == 11");
				order.setChargeMode(FundChargeEnum.ChargeMode.DIN.getValue());
				order.setCustomerIp(chargeApplyRequest.getCustomerIp());
				resultOrder = fundChargeService.dinApply(order);
				if(!isQr(order.getDepositMode())){
					dinpay = createDin(resultOrder);
				}
				break;
			case 12://华银
				logger.info("bypass == 12");
				order.setChargeMode(FundChargeEnum.ChargeMode.HUAYIN.getValue());
				order.setCustomerIp(chargeApplyRequest.getCustomerIp());
				resultOrder = fundChargeService.huayinApply(order);
				if(!isQr(order.getDepositMode())){
					huayinpay = createHuaYin(resultOrder);
				}
				break;
			case 13://银邦
				logger.info("bypass == 13");
				order.setChargeMode(FundChargeEnum.ChargeMode.YINBANG.getValue());
				order.setCustomerIp(chargeApplyRequest.getCustomerIp());
				resultOrder = fundChargeService.yinbangApply(order);
				if(!isQr(order.getDepositMode())){
					yinbangpay = createYinBang(resultOrder);
				}
				break;
			case 14://金阳
				logger.info("bypass == 14");
				order.setChargeMode(FundChargeEnum.ChargeMode.JINYANG.getValue());
				order.setCustomerIp(chargeApplyRequest.getCustomerIp());
				resultOrder = fundChargeService.jinyangApply(order);
				if(!isQr(order.getDepositMode())){
					jinyangpay = createJinYang(resultOrder);
				}
				break;
			default://DP
				order.setChargeMode(FundChargeEnum.ChargeMode.DP.getValue());
				resultOrder = fundChargeService.apply(order);
				break;
			}
			
			if(order.getDepositMode() != null){
				String expreTime = "";
				expreTime = getConfigExpireTime(order.getDepositMode());
				try {
					resp.setExpireTime(Long.parseLong(expreTime));
				} catch (Exception e) {
					resp.setExpireTime(DataConverterUtil.convertDate2Long(resultOrder.getMcOrder().getExpireTime()));
				}
				logger.info("expreTime : " + expreTime);
			}
			
			resp.setOrderId(resultOrder.getId());
			resp.setBreakUrl(resultOrder.getBreakUrl());
			resp.setRcvEmail(resultOrder.getRevEmail());
			resp.setChargeMemo(resultOrder.getMemo());
			resp.setMode(resultOrder.getMode());
			resp.setPayOrderNo(resultOrder.getSn());
			resp.setThPay(thpay);
			resp.setPay(pay);
			resp.setHbPay(hbpay);
			resp.setWorthPay(worthpay);
			resp.setSpPay(sppay);
			resp.setDdbPay(ddbpay);
			resp.setDinPay(dinpay);
			resp.setHuayinPay(huayinpay);
			resp.setYinbangPay(yinbangpay);
			resp.setJinyangPay(jinyangpay);
			//resp.setWftPay(wftpay);
			if (resultOrder.getRevCard() != null && resultOrder.getRevCard().getSubBranch() != null) {
				resp.setRcvBankName(resultOrder.getRevCard().getSubBranch());
			}
			if (resultOrder.getRevCard() != null && resultOrder.getRevCard().getBank() != null) {
				resp.setBankId(resultOrder.getRevCard().getBank().getId());
			}
			if (resultOrder.getRevCard() != null) {
				resp.setRevAccName(resultOrder.getRevCard().getAccountHolder());
				resp.setRcvAccNum(resultOrder.getRevCard().getBankCardNo());
			}
			
			response.setResult(resp);

		} catch (FundChargeLowException e) {
			logger.error(FundChargeLowException.MSG, e);
			response.getHead().setStatus(e.getStatus());
		} catch (FundChargeHighException e) {
			logger.error(FundChargeHighException.MSG, e);
			response.getHead().setStatus(e.getStatus());

		} catch (MownecumWithdrawResponseData e) {
			logger.error("Fund Withdraw Audit error:", e);
			response.getHead().setStatus(e.getErrorStatus());
		} catch (Exception e) {
			logger.error("chargeApply error.", e);
			throw e;
		}
		return response;
	}
	private boolean isQr(Long depositMode){
		return depositMode==FundChargeEnum.DepositeMode.ALIPAY.getValue() || depositMode==FundChargeEnum.DepositeMode.WECHAT.getValue()
				 || depositMode==FundChargeEnum.DepositeMode.QQ.getValue() || depositMode==FundChargeEnum.DepositeMode.UNIPAY.getValue();
	}
	/**
	 * 
	* @Title: chargeQuery 
	* @Description: 充值记录查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/chargeQuery")
	@ResponseBody
	public Response<List<ChargeStruc>> chargeQuery(@RequestBody @ValidRequestHeader Request<ChargeQueryRequest> request)
			throws Exception {
		Response<List<ChargeStruc>> response = new Response<List<ChargeStruc>>(request);

		PageRequest<ChargeQueryRequest> pageReq = getPageRequest(request);
		ChargeQueryRequest req = request.getBody().getParam();
		if(req.getUserId()!=null){
			QueryLevelRecycleHistoryResponse recycleHist = levelRecycleService.queryRecycleLastHistory(req.getUserId());
			req.setRecycleDate(recycleHist.getActivityDate());
		}
		if(req.getFromDate() == null && req.getToDate() == null && req.getFromDealDate() == null && req.getToDealDate() == null && req.getFromAddCoinDate() == null && req.getToAddCoinDate() == null && req.getFromOperatingDate() == null && req.getToOperatingDate() == null ){
			req.setFromDate(DateUtils.getStartTimeOfDate(DateUtils.addDays(DateUtils.currentDate(), -2)));
			req.setToDate(DateUtils.getEndTimeOfCurrentDate());
			
		}
		pageReq.setSearchDo(req);
		pageReq.setSortColumns("APPLY_TIME DESC");
		try {
			CountPage<FundChargeOrder> page = fundChargeService.query(pageReq);
			response = fillResponse(response, page, request);
		} catch (Exception e) {
			logger.error("chargeQuery error.", e);
			throw e;
		}

		return response;
	}
	
	/**
	 * 
	* @Title: chargeRecordQuery 
	* @Description: 充值记录查询
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/chargeRecordQuery")
	@ResponseBody
	public Response<List<ChargeStruc>> chargeRecordQuery(@RequestBody @ValidRequestHeader Request<ChargeQueryRequest> request)
			throws Exception {
		Response<List<ChargeStruc>> response = new Response<List<ChargeStruc>>(request);
		
		PageRequest<ChargeQueryRequest> pageReq = getPageRequest(request);
		ChargeQueryRequest req = request.getBody().getParam();
		pageReq.setSearchDo(req);
		pageReq.setSortColumns("APPLY_TIME DESC");
		try {
			CountPage<FundChargeOrder> page = fundChargeService.query(pageReq);
			response = fillResponse(response, page, request);
		} catch (Exception e) {
			logger.error("chargeQuery error.", e);
			throw e;
		}
		
		
		return response;
		
		
	}

	@RequestMapping(value = "/chargeCancel")
	@ResponseBody
	public Response<List<ChargeStruc>> chargeCancel(@RequestBody @ValidRequestHeader Request<ChargeQueryRequest> request)
			throws Exception {
		Response<List<ChargeStruc>> response = new Response<List<ChargeStruc>>(request);

		fundChargeService.cancelMowOrder(request.getBody().getParam().getSn(), Status.USER_CANCEL);
		return response;
	}

	@RequestMapping(value = "/adChargeCancel")
	@ResponseBody
	public Response<List<ChargeStruc>> adChargeCancel(
			@RequestBody @ValidRequestHeader Request<ChargeQueryRequest> request) throws Exception {
		Response<List<ChargeStruc>> response = new Response<List<ChargeStruc>>(request);

		fundChargeService.cancelMowOrder(request.getBody().getParam().getSn(), Status.ADMIN_CANCEL);

		return response;
	}

	@RequestMapping(value = "/chargeSuccess")
	@ResponseBody
	public Response<List<ChargeStruc>> chargeSuccess(
			@RequestBody Request<ChargeQueryRequest> request) throws Exception {
		Response<List<ChargeStruc>> response = new Response<List<ChargeStruc>>(request);
		
		fundChargeService.changeOrderStatus(request.getBody().getParam().getSn(),FundManualOrder.Status.ADDCOIN);

		return response;
	}

	/*@RequestMapping(value = "/cancelOrder")
	@ResponseBody
	public Response<Object> cancelOrder(@RequestBody @ValidRequestHeader Request<Map<String, Long>> request)
			throws Exception {
		Response<Object> response = new Response<Object>(request);

		try {
			Long chargeId = request.getBody().getParam().get("chargeId");
			//fundChargeService.cancelOrder(chargeId);
			fundChargeService.cancelMowOrder(""+chargeId,Status.USER_CANCEL);
	 	} catch (Exception e) {
			logger.error("cancelOrder error", e);
			throw e;
		}
		return response;
	}*/

	private static class TT {
		private Long depositMode;
		private Long userId;

		public Long getDepositMode() {
			return depositMode;
		}

		public void setDepositMode(Long depositMode) {
			this.depositMode = depositMode;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

	}

	@RequestMapping(value = "/haveChargeItem")
	@ResponseBody
	public Response<FundUserChargeStruc> haveChargeItem(@RequestBody Request<TT> request) throws Exception {
		Response<FundUserChargeStruc> response = new Response<FundUserChargeStruc>(request);
		Long userId = request.getHead().getUserId();
		try {
			FundChargeOrder order = fundChargeService.haveChargeItem(userId, request.getBody().getParam()
					.getDepositMode());
			//order.setDepositMode(request.getBody().getParam().getDepositMode());
			response.setResult(DTOConverter.transforFundChargeOrder2Struc(order));
		} catch (Exception e) {
			logger.error("haveChargeItem error", e);
			throw e;
		}
		return response;
	}

	private PageRequest<ChargeQueryRequest> getPageRequest(Request<?> request) {

		PageRequest<ChargeQueryRequest> pageReqeust = PageConverterUtils.getRestPageRequest(request.getBody()
				.getPager().getStartNo(), request.getBody().getPager().getEndNo());
		return pageReqeust;
	}

	private Response<List<ChargeStruc>> fillResponse(Response<List<ChargeStruc>> response, Page<FundChargeOrder> list,
			Request<?> request) throws Exception {

		if (null == response) {
			response = new Response<List<ChargeStruc>>(request);
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

	private List<ChargeStruc> convertList(List<FundChargeOrder> list) throws Exception {

		List<ChargeStruc> chargeList = new ArrayList<ChargeStruc>();
		
		String appealTime = configService.getConfigValueByKey("fund", "move_recharge_appeal");
		String[] str = appealTime.split(",");
		String[] waitTime = str[0].split(":");
		String[] time = waitTime[1].split("\"");
		
		for (FundChargeOrder order : list) {
			ChargeStruc struc = new ChargeStruc();
			struc.setPlatVersion(order.getPlatfom());
			struc.setApplyAmt(order.getPreChargeAmt());
			struc.setApplyTime(order.getApplyTime());

			struc.setChargeAmt(order.getRealChargeAmt());
			struc.setChargeTime(order.getChargeTime());
			
			struc.setSn(order.getSn());
			struc.setPreChargeAmt(order.getPreChargeAmt());
			struc.setDepositMode(order.getDepositMode());
			struc.setMemo(order.getMemo());
			struc.setMcSn(order.getMcSn());
			struc.setApplyTime(order.getApplyTime());
			struc.setTopVip(order.getTopVip());
			struc.setRealMemo(order.getMemo());
			if (order.getMcOrder() != null) {
				struc.setShouxufei(order.getMcOrder().getBankFee());
				struc.setMcChannel(order.getMcOrder().getChannel());
				struc.setAddCoinTime(order.getMcOrder().getNoticeTime());
				struc.setMowDate(order.getMcOrder().getNoticeTime());
			}

			if (order.getRevCard() != null) {
				struc.setRcvAcct(order.getRevCard().getAccountHolder());
				struc.setRcvEmail(order.getRevCard().getRcvEmail());
				struc.setRcvBankNumber(order.getRevCard().getBankCardNo());
				if (order.getRevCard().getBank() != null) {
					struc.setRcvBankId(order.getRevCard().getBank().getId());
					struc.setBankId(order.getRevCard().getBank().getId());
					struc.setRcvBankName(order.getRevCard().getBank().getName());
				}
				

			}
			if (order.getPayCard() != null) {
				struc.setCardAccount(order.getPayCard().getAccountHolder());
				struc.setCardNumber(order.getPayCard().getBankCardNo());
				if (order.getPayCard().getBank() != null)
					struc.setPayBankId(order.getPayCard().getBank().getId());
			}


			if (order.getApplyUser() != null) {
				struc.setAccount(order.getApplyUser().getAccount());
			}
			if (order.getStatus() != null) {
				struc.setStatus(order.getStatus().getValue());
			}
			struc.setUserAct(order.getUserAct());
			struc.setRealBankId(order.getRealBankId());
			if (order.getOperatingTime() != null) {
				struc.setOperatingTime(order.getOperatingTime());
			}
			struc.setChargeCardNum(order.getChargeCardNum());
			struc.setWaitTime(Long.valueOf(time[1]));
			struc.setChargeMode(order.getChargeMode());
			chargeList.add(struc);
		}

		return chargeList;
	}
	
	private ECPSSDeposit createECPSS(FundChargeOrder order) {
		ECPSSDeposit params = new ECPSSDeposit();
				
		String transferDomain = configService.getConfigValueByKey("fund", "thpay_charge_domain");
		logger.info("transferDomain : " +transferDomain);
		String ecpssDomain = configService.getConfigValueByKey("fund", "ecpss_trans_domain");
		params.setEcpssDomain(ecpssDomain);
		logger.info("ecpssDomain : " +params.getEcpssDomain());
		params.setMerNo(merNo);
		logger.info("merNo : " +merNo);
		params.setBillNo(order.getSn());
		logger.info("BillNo : " +params.getBillNo());
		params.setAmount(NumUtil.toMow(order.getPreChargeAmt()).toString());
		logger.info("amount : " +params.getAmount());
//		params.setReturnURL("returnbusiness://go_business");
//		logger.info("ReturnURL : " +params.getReturnURL());
		params.setAdviceURL("http://" + transferDomain + "/fund/ecpss/chargeConfirm");
		logger.info("AdviceURL : " +params.getAdviceURL());
		params.setOrderTime(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		logger.info("OrderTime : " +params.getOrderTime());
		params.setPayType("noCard");
		logger.info("PayType : " +params.getPayType());
		String signInfo = params.createSign();
		params.setSignInfo(signInfo);
		logger.info("signInfo : " +params.getSignInfo());
		
		return params;
	}
	
	private THDeposit createTHParams(FundChargeOrder order) {
		THDeposit params = new THDeposit();
		String transferDomain = configService.getConfigValueByKey("fund", "thpay_charge_domain");
		params.setNotifyUrl("http://" + transferDomain + "/fund/thpay/chargeConfirm");
		params.setMerchantCode(merchantCode);
		params.setPayType("1");
		params.setBankCode(THBank.mapping(order.getPayBank().getId()));
		params.setOrderNo(order.getSn());
		params.setOrderAmount(NumUtil.toMow(order.getPreChargeAmt()).toString());
		params.setOrderTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		params.setReqReferer(transferDomain);
		params.setCustomerIp(order.getCustomerIp());
		String md5Params = params.createSign(thpayKey);
		logger.info("md5Params : " + md5Params);
		logger.info("MD5 : " + MD5Encrypt.encrypt(md5Params));
		params.setSign(MD5Encrypt.encrypt(md5Params));
		return params;	}
	
	private HBDeposit createHBParams(FundChargeOrder order) {
		try {
			
			HBDeposit params = new HBDeposit();
			String transferDomain = configService.getConfigValueByKey("fund", "hbpay_trans_domain");
			String retunUrl = "http://"+configService.getConfigValueByKey("fund", "hbpay_trans_domain")+"/fund";
			logger.info("transferDomain : " +transferDomain);
			
			String transBank = HBBank.mapping(order.getPayBank().getId());
			Long preTransAmount = order.getPreChargeAmt()/100;
			String transAmount = (preTransAmount).toString();
			
			Map<String, Object> signMap = new HashMap<String, Object>();
			signMap.put("amount", transAmount);
			signMap.put("bankAbbr", transBank);
			signMap.put("cardType", "1");
			signMap.put("orderId", order.getSn());
			signMap.put("returnURL", retunUrl);
			String signData = JsonMapper.nonAlwaysMapper().toJson(signMap);
			
			//签名
			logger.info("signData: "+signData);
			byte[] sign = LocalUtil.sign(Base64.decode(hbpayPrivateKey.getBytes()), Base64.encodeToString(signData));
			
            
            // 加密			
			String msgValue=Base64.encodeToString(signData);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("account", hbpay_account);
			dataMap.put("channelCode", hbpayChannelCode);
			dataMap.put("msg", msgValue);
			dataMap.put("orderCode", hbpayChargeOrderCode);
			String data = JsonMapper.nonAlwaysMapper().toJson(dataMap);
			
			
			logger.info("data: "+data);
			
			String param=Base64.encodeToString(data);
			PublicKey publicKey = RSAUtils.loadPublicKey(hbpayPublicKey);
			byte[] decryptByte1 = RSAUtils.encryptData(param.getBytes(),publicKey);
			
			
			String sendSign = new String(sign);
			String sendData = Base64Utils.encode(decryptByte1);
			logger.info("sendSign:"+sendSign);
			logger.info("sendData:"+sendData);
			
			params.setTransDomain(transferDomain);
			params.setSign(sendSign);
			params.setData(sendData);
			return params;
		} catch (Exception e) {
			logger.error("hbpay TransParam error:",e);
			return null;
		}
	}
	private SPDeposit createSp(FundChargeOrder order) {
		try {
			
			SPDeposit params = new SPDeposit();
			String transferDomain = configService.getConfigValueByKey("fund", "hbpay_trans_domain");
			String retunUrl = "http://"+configService.getConfigValueByKey("fund", "hbpay_trans_domain")+"/fund";
			String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "hbpay_trans_domain")+"/fund/sppay/notify";
			logger.info("transferDomain : " +transferDomain);
			
			String transBank = SPBank.mapping(order.getPayBank().getId());
			Long preTransAmount = order.getPreChargeAmt()/100;
			String transAmount = (preTransAmount).toString();
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("ORDERNO", order.getSn());
			dataMap.put("TXNAMT", transAmount);
			dataMap.put("PRO_ID", "GC_GATEWAY");	// GC_GATEWAY、YYPay
			dataMap.put("RETURNURL", retunUrl);
			dataMap.put("NOTIFYURL", notifyUrl);
			dataMap.put("BANKID", transBank);
			dataMap.put("CARD_TYPE", "01");	//01：借记卡	02：贷记卡
			dataMap.put("USER_TYPE", "1");	//1：个人	2：企业
			dataMap.put("REMARK", "");
			String data = JsonMapper.nonAlwaysMapper().toJson(dataMap);
			
			logger.info("data: "+data);
			String sendSign =  SignUtils.Signaturer(data, sppayPrivateKey);
			logger.info("sendData:"+data);
			
			params.setMERCNUM(sppayAccount);
			params.setTRANDATA(data);
			params.setSIGN(sendSign);
			params.setTransDomain(transferDomain);
			return params;
		} catch (Exception e) {
			logger.error("hbpay TransParam error:",e);
			return null;
		}
	}
	private DDBDeposit createDdb(FundChargeOrder order) {
		try {
			
			DDBDeposit params = new DDBDeposit();
			String transferDomain = configService.getConfigValueByKey("fund", "ddbpay_trans_domain");
			String returnUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund";
			String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund/ddbpay/notify";
			logger.info("transferDomain : " +transferDomain);
			
			String merchantCode=ddbpayAccount;
			String serviceType="direct_pay";
			String interfaceVersion="V3.0";
			String inputCharset="UTF-8";
			String signType="RSA-S";
			String orderNo=order.getSn();
			String orderTime=DateUtils.format(order.getApplyTime(),"yyyy-MM-dd HH:mm:ss");
			String orderAmount=NumUtil.toMow(order.getPreChargeAmt())+"";
			String bankCode=DDBBank.mapping(order.getPayBank().getId());
			String productName="money";
			
			
			StringBuffer signSrc= new StringBuffer();
			signSrc.append("bank_code=").append(bankCode).append("&");	
			signSrc.append("input_charset=").append(inputCharset).append("&");
			signSrc.append("interface_version=").append(interfaceVersion).append("&");
			signSrc.append("merchant_code=").append(merchantCode).append("&");				
			signSrc.append("notify_url=").append(notifyUrl).append("&");	
			signSrc.append("order_amount=").append(orderAmount).append("&");
			signSrc.append("order_no=").append(orderNo).append("&");
			signSrc.append("order_time=").append(orderTime).append("&");
			signSrc.append("product_name=").append(productName).append("&");
			signSrc.append("return_url=").append(returnUrl).append("&");
			signSrc.append("service_type=").append(serviceType);		
			
			String signInfo = signSrc.toString();
			
			logger.info("signInfo:"+signInfo);
			//签名
			String sign = new String(LocalUtil.sign(Base64.decode(ddbpayPrivateKey.getBytes()),signInfo));
			
			
			params.setMerchantCode(merchantCode);
			params.setServiceType(serviceType);
			params.setReturnUrl(returnUrl);
			params.setNotifyUrl(notifyUrl);
			params.setInterfaceVersion(interfaceVersion);
			params.setInputCharset(inputCharset);
			params.setSignType(signType);
			params.setOrderNo(orderNo);
			params.setOrderTime(orderTime);
			params.setOrderAmount(orderAmount);
			params.setBankCode(bankCode);
			params.setProductName(productName);
			params.setSign(sign);
			params.setTransDomain(transferDomain);
			params.setChargeUrl(transferDomain);
			return params;
		} catch (Exception e) {
			logger.error("hbpay TransParam error:",e);
			return null;
		}
	}
	private DINDeposit createDin(FundChargeOrder order) {
		try {
			
			DINDeposit params = new DINDeposit();
			String transferDomain = configService.getConfigValueByKey("fund", "dinpay_trans_domain");
			String returnUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund";
			String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund/dinpay/notify";
			logger.info("transferDomain : " +transferDomain);
			
			String merchantCode=dinpayAccount;
			String serviceType="direct_pay";
			String interfaceVersion="V3.0";
			String inputCharset="UTF-8";
			String signType="RSA-S";
			String orderNo=order.getSn();
			String orderTime=DateUtils.format(order.getApplyTime(),"yyyy-MM-dd HH:mm:ss");
			String orderAmount=NumUtil.toMow(order.getPreChargeAmt())+"";
			String bankCode=DDBBank.mapping(order.getPayBank().getId());
			String productName="money";
			
			
			StringBuffer signSrc= new StringBuffer();
			signSrc.append("bank_code=").append(bankCode).append("&");	
			signSrc.append("input_charset=").append(inputCharset).append("&");
			signSrc.append("interface_version=").append(interfaceVersion).append("&");
			signSrc.append("merchant_code=").append(merchantCode).append("&");				
			signSrc.append("notify_url=").append(notifyUrl).append("&");	
			signSrc.append("order_amount=").append(orderAmount).append("&");
			signSrc.append("order_no=").append(orderNo).append("&");
			signSrc.append("order_time=").append(orderTime).append("&");
			signSrc.append("product_name=").append(productName).append("&");
			signSrc.append("return_url=").append(returnUrl).append("&");
			signSrc.append("service_type=").append(serviceType);		
			
			String signInfo = signSrc.toString();
			
			logger.info("signInfo:"+signInfo);
			//签名
			String sign = new String(LocalUtil.sign(Base64.decode(dinpayPrivateKey.getBytes()),signInfo));
			
			
			params.setMerchantCode(merchantCode);
			params.setServiceType(serviceType);
			params.setReturnUrl(returnUrl);
			params.setNotifyUrl(notifyUrl);
			params.setInterfaceVersion(interfaceVersion);
			params.setInputCharset(inputCharset);
			params.setSignType(signType);
			params.setOrderNo(orderNo);
			params.setOrderTime(orderTime);
			params.setOrderAmount(orderAmount);
			params.setBankCode(bankCode);
			params.setProductName(productName);
			params.setSign(sign);
			params.setTransDomain(transferDomain);
			params.setChargeUrl(transferDomain);
			return params;
		} catch (Exception e) {
			logger.error("dinpay TransParam error:",e);
			return null;
		}
	}
	private HUAYINDeposit createHuaYin(FundChargeOrder order) {
		try {
			
			HUAYINDeposit params = new HUAYINDeposit();
			String transferDomain = configService.getConfigValueByKey("fund", "hyinpay_trans_domain");
			String returnUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund";
			String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund/huayinpay/notify";
			logger.info("transferDomain : " +transferDomain);
			
			String merchantCode=huayinpayAccount;
			String serviceType="direct_pay";
			String interfaceVersion="V3.0";
			String inputCharset="UTF-8";
			String signType="RSA-S";
			String orderNo=order.getSn();
			String orderTime=DateUtils.format(order.getApplyTime(),"yyyy-MM-dd HH:mm:ss");
			String orderAmount=NumUtil.toMow(order.getPreChargeAmt())+"";
			String bankCode=DDBBank.mapping(order.getPayBank().getId());
			String productName="money";
			
			
			StringBuffer signSrc= new StringBuffer();
			signSrc.append("bank_code=").append(bankCode).append("&");	
			signSrc.append("input_charset=").append(inputCharset).append("&");
			signSrc.append("interface_version=").append(interfaceVersion).append("&");
			signSrc.append("merchant_code=").append(merchantCode).append("&");				
			signSrc.append("notify_url=").append(notifyUrl).append("&");	
			signSrc.append("order_amount=").append(orderAmount).append("&");
			signSrc.append("order_no=").append(orderNo).append("&");
			signSrc.append("order_time=").append(orderTime).append("&");
			signSrc.append("product_name=").append(productName).append("&");
			signSrc.append("return_url=").append(returnUrl).append("&");
			signSrc.append("service_type=").append(serviceType);		
			
			String signInfo = signSrc.toString();
			
			logger.info("signInfo:"+signInfo);
			//签名
			String sign = new String(LocalUtil.sign(Base64.decode(huayinpayPrivateKey.getBytes()),signInfo));
			
			
			params.setMerchantCode(merchantCode);
			params.setServiceType(serviceType);
			params.setReturnUrl(returnUrl);
			params.setNotifyUrl(notifyUrl);
			params.setInterfaceVersion(interfaceVersion);
			params.setInputCharset(inputCharset);
			params.setSignType(signType);
			params.setOrderNo(orderNo);
			params.setOrderTime(orderTime);
			params.setOrderAmount(orderAmount);
			params.setBankCode(bankCode);
			params.setProductName(productName);
			params.setSign(sign);
			params.setTransDomain(transferDomain);
			params.setChargeUrl(transferDomain);
			return params;
		} catch (Exception e) {
			logger.error("dinpay TransParam error:",e);
			return null;
		}
	}
	private YINBANGDeposit createYinBang(FundChargeOrder order) {
		try {
			
			YINBANGDeposit params = new YINBANGDeposit();
			String transferDomain = configService.getConfigValueByKey("fund", "yinbpay_trans_domain");
			String returnUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund";
			String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund/yinbangpay/notify";
			logger.info("transferDomain : " +transferDomain);
			
			String merId=yinbangpayAccount;
			String version="1.0.9";
			String terId=yinbangpayTerminal;
			String orderName="money";
			String businessOrdid=order.getSn();
			String tradeMoney=order.getPreChargeAmt()/100+"";
			String payType="1003";
			String syncURL=returnUrl;
			String asynURL=notifyUrl;
			String chargeUrl=transferDomain;
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessOrdid", businessOrdid);
			map.put("orderName", orderName);
			map.put("merId", merId);
			map.put("terId", terId);
			map.put("tradeMoney", tradeMoney);
			map.put("payType", payType); // 1000默认支持所有支付方式
			map.put("syncURL", syncURL);
			map.put("asynURL", asynURL);

			String jsonParam = JsonUtil.toJson(map);
			logger.info("signInfo:"+jsonParam);
			//服务器公钥加密
			byte by[] = SecurityRSAPay.encryptByPublicKey(jsonParam.getBytes("UTF-8"), Base64Local.decode(yinbangpayPlatformKey));
			String encParam = Base64Local.encodeToString(by, true);
			
			//商户自己的私钥签名
			byte signBy[] = SecurityRSAPay.sign(by, Base64Local.decode(yinbangpayPrivateKey));
			String sign = Base64Local.encodeToString(signBy, true);
			
			params.setEncParam(encParam);
			params.setMerId(yinbangpayAccount);
			params.setSign(sign);
			params.setVersion(version);
			params.setTransDomain(transferDomain);
			params.setChargeUrl(transferDomain);
			return params;
		} catch (Exception e) {
			logger.error("yinbangpay TransParam error:",e);
			return null;
		}
	}
	private JINYANGDeposit createJinYang(FundChargeOrder order) {
		try {
			
			JINYANGDeposit params = new JINYANGDeposit();
			String transferDomain = configService.getConfigValueByKey("fund", "jinypay_trans_domain");
			String returnUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund";
			String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund/jinyangpay/notify";
			logger.info("jinyang transferDomain : " +transferDomain);
			
			
			String merId=jinyangpayAccount;
			
			String payType=JYBank.mapping(order.getPayBank().getId());
			String paymoney=BigDecimal.valueOf(order.getPreChargeAmt()*1.0/10000).stripTrailingZeros().toPlainString();
			String orderno=order.getSn();
			String asynURL=notifyUrl;
			String version="v2.8";
			String signType="1";	//MD5
			String isShow="0";//是否显示收银台
			
			StringBuffer sb = new StringBuffer();
			sb.append("p1_mchtid").append("=").append(merId).append("&")
			.append("p2_paytype").append("=").append(payType).append("&")
			.append("p3_paymoney").append("=").append(paymoney).append("&")
			.append("p4_orderno").append("=").append(orderno).append("&")
			.append("p5_callbackurl").append("=").append(asynURL).append("&")
			.append("p6_notifyurl").append("=").append("").append("&")
			.append("p7_version").append("=").append(version).append("&")
			.append("p8_signtype").append("=").append(signType).append("&")
			.append("p9_attach").append("=").append("").append("&")
			.append("p10_appname").append("=").append("").append("&")
			.append("p11_isshow").append("=").append(isShow).append("&")
			.append("p12_orderip").append("=").append("")
	        .append(jinyangpayKey);


			String sign=MD5Encrypt.encrypt(sb.toString());

			logger.info("jinyang signInfo:"+sb.toString());
 
			params.setMerId(merId+"");
			params.setPayType(payType);
			params.setPaymoney(paymoney+"");
			params.setOrderno(orderno);
			params.setAsynURL(asynURL);
			params.setVersion(version);
			params.setSignType(signType+"");
			params.setIsShow(isShow+"");
			params.setSign(sign);
			params.setTransDomain(transferDomain);
			params.setChargeUrl(transferDomain);
			return params;
		} catch (Exception e) {
			logger.error("jinyangpay TransParam error:",e);
			return null;
		}
	}
	
	private String getWftChannelCode(Long depositMode){
		if(depositMode==FundChargeEnum.DepositeMode.ALIPAY.getValue()){
			return "ALIPAYSCAN";
		}else if(depositMode==FundChargeEnum.DepositeMode.WECHAT.getValue()){
			return "WEIXIN";
		}else if(depositMode==FundChargeEnum.DepositeMode.QQ.getValue()){
			return "QQ";
		}
		return "";
	}
	private WFTDeposit createWft(FundChargeOrder order) {
		try {
			
			WFTDeposit params = new WFTDeposit();
			String transferDomain = configService.getConfigValueByKey("fund", "wftpay_trans_domain");
			String returnUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund";
			String notifyUrl = "http://"+configService.getConfigValueByKey("fund", "host_domain")+"/fund/wftpay/notify";
			logger.info("transferDomain : " +transferDomain);
			
			String version="3.0";
			String method="Rx.online.pay";
			String partner=wftpayAccount;
			String banktype=getWftChannelCode(order.getDepositMode());
			String paymoney=NumUtil.toMow(order.getPreChargeAmt())+"";
			String ordernumber=order.getSn();
			String callbackurl = notifyUrl;
			String hrefbackurl=returnUrl;
			String isshow="N";
			
			
			StringBuffer mainStr= new StringBuffer();
			mainStr.append("partner=").append(partner).append("&");	
			mainStr.append("banktype=").append(banktype).append("&");
			mainStr.append("paymoney=").append(paymoney).append("&");				
			mainStr.append("ordernumber=").append(ordernumber).append("&");	
			mainStr.append("callbackurl=").append(callbackurl);
			
			
			StringBuffer signStr= new StringBuffer();
			signStr.append(mainStr).append(wftpayPrivateKey);
			String sign=MD5Encrypt.encrypt(signStr.toString());
			
			StringBuffer paramStr= new StringBuffer();
			paramStr.append("version=").append(version).append("&");
			paramStr.append("method=").append(method).append("&");
			paramStr.append(mainStr).append("&");
			paramStr.append("isshow=").append(isshow).append("&");
			paramStr.append("hrefbackurl=").append(hrefbackurl).append("&");
			paramStr.append("sign=").append(sign);
				
			params.setVersion(version);
			params.setMethod(method);
			params.setPartner(partner);
			params.setBanktype(banktype);
			params.setPaymoney(paymoney);
			params.setOrdernumber(ordernumber);
			params.setCallbackurl(callbackurl);
			params.setIsshow(isshow);
			params.setHrefbackurl(hrefbackurl);
			params.setTransDomain(transferDomain);
			params.setChargeUrl(wftpayQrUrl);
			params.setSign(sign);
			return params;
		} catch (Exception e) {
			logger.error("wftpay TransParam error:",e);
			return null;
		}
	}
	private String getChannelCode(Long depositMode){
		if(depositMode==7L){
			return "WEIXIN";
		}else if(depositMode==6L){
			return "ALIPAY";
		}else if(depositMode==5L){
			return "UNIPAY";
		}
		return "";

	}
	private WORTHDeposit createWORTH(FundChargeOrder order) {
		try {
		
			WORTHDeposit params = new WORTHDeposit();
			
			String transferDomain = configService.getConfigValueByKey("fund", "worth_trans_domain");
	
			Map<String,String> DateContentParms = new HashMap<String,String>();
			DateContentParms.put("service", "online_pay");//接口名稱
			DateContentParms.put("merchant_ID",worthMerchantID);		
			DateContentParms.put("notify_url","http://" + transferDomain + "/fund/worthpay/chargeConfirm");//通知URL
			DateContentParms.put("return_url","http://" + transferDomain + "/fund");//返回URL
			DateContentParms.put("charset", "UTF-8");//編碼字符
			DateContentParms.put("title", "充值点卡");//商品的名称		
			DateContentParms.put("body", "充值点卡");//商品的具体描述		
			DateContentParms.put("order_no",order.getSn());//商户订单号（确保在合作伙伴系统中唯一）		
			DateContentParms.put("total_fee",NumUtil.toMow(order.getPreChargeAmt()).toString());//商戶訂單號
			DateContentParms.put("payment_type", "1");//当前默认值为1
			DateContentParms.put("paymethod","directPay");//固定值directPay，直连模式		
			DateContentParms.put("defaultbank","WXPAY");//網銀代碼 -wxpay代表微信
			DateContentParms.put("seller_email",worthSellerEmail);//卖家在华势的注册Email	
			DateContentParms.put("isApp", "");//接入方式
			
			//MD5加密
			String md5Params = UtilSign.GetMd5str(DateContentParms);
			logger.info("MD5拼接字串（不包含密码）："+ md5Params);
			logger.info("MD5拼接："+ md5Params +worthMd5key);
			String MD5str = MD5Encrypt.encrypt(md5Params +worthMd5key);
			DateContentParms.put("sign_type","MD5");//簽名方式
			DateContentParms.put("sign", MD5str);//簽名
					
			params.setService(DateContentParms.get("service"));
			params.setMerchant_ID(DateContentParms.get("merchant_ID"));
			params.setNotify_url(DateContentParms.get("notify_url"));
			params.setReturn_url(DateContentParms.get("return_url"));
			params.setCharset(DateContentParms.get("charset"));
			params.setTitle(DateContentParms.get("title"));
			params.setBody(DateContentParms.get("body"));
			params.setOrder_no(DateContentParms.get("order_no"));
			params.setTotal_fee(DateContentParms.get("total_fee"));
			params.setPayment_type(DateContentParms.get("payment_type"));
			params.setPaymethod(DateContentParms.get("paymethod"));
			params.setDefaultbank(DateContentParms.get("defaultbank"));
			params.setSeller_email(DateContentParms.get("seller_email"));
			params.setIsApp(DateContentParms.get("isApp"));
			params.setSign_type(DateContentParms.get("sign_type"));
			params.setSign(DateContentParms.get("sign"));
			params.setTransDomain(transferDomain);
	
			return params;
			
		}catch (Exception e) {
			logger.error("worthpay TransParam error:",e);
			return null;
		}
	}
	private String getConfigExpireTime(Long depositMode){
		String expireTime = "";
		switch(depositMode.intValue()){
		case 1:
			expireTime = configService.getConfigValueByKey("fund", "chargeCountDown");
			break;
		case 3:
			expireTime = configService.getConfigValueByKey("fund", "chargeCouteCaifu");
			break;
		case 5:
			expireTime = configService.getConfigValueByKey("fund", "chargeCouteUnionpay");
			break;
		case 6:
			expireTime = configService.getConfigValueByKey("fund", "chargeCouteAlipay");
			break;
		case 7:
			expireTime = configService.getConfigValueByKey("fund", "chargeCouteWechat");
			break;
		default:
			expireTime = configService.getConfigValueByKey("fund", "chargeCouteThired");
			break;
		}		
		return expireTime;
	}
	
	
	@RequestMapping(value = "/checkdaylimit")
	@ResponseBody
	public Response<Long> checkChargeLimit(@RequestBody @ValidRequestHeader Request<ChargeApplyRequest> request)throws Exception{
		ChargeApplyRequest chargeApplyRequest = request.getBody().getParam();
		Long chargelimt = fundChargeService.checkChargeLimit(chargeApplyRequest);
		Response<Long> res = new Response<Long>();
		res.setResult(chargelimt);
		return res;
	}
	/**
	 * 
	* @Title: checkBankDayLimit 
	* @Description: 支付寶當日充值限制檢查
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/checkBankDayLimit")
	@ResponseBody
	public Response<Long> checkBankDayLimit(@RequestBody @ValidRequestHeader Request<ChargeApplyRequest> request)throws Exception{

		Response<Long> response = new Response<Long>();
		//預設檢查結果為 不通過為0    通過為1
		response.setResult(0L);
		//檢查今日充值額度是否超過限額
		logger.debug("單日限額檢查開始，start.........");
		if(fundChargeService.checkBankDayLimit(request.getBody().getParam().getDepositMode())){
			response.setResult(1L);
			logger.debug("單日限額檢查結果.........通過");
			return response;
		}
		logger.debug("單日限額檢查結果.........不通過");
		return response;
	}
	
	
	/**
	 * 
	* @Title: chargeThirdPartyLimit 
	* @Description: 第三方支付充值限制檢查
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/chargeThirdPartyLimit")
	@ResponseBody
	public Response<Long> chargeThirdPartyLimit(
			@RequestBody @ValidRequestHeader Request<ChargeApplyRequest> request)
			throws Exception {
		

		
	    logger.info("第三方支付充值限制檢查，start.........");  
		Response<Long> response = new Response<Long>();		
		//預設response=0  審核不通過
		response.setResult(0L);
		
		//檢查是否審核通過第三方支付充值限制，true為通過，false為不通過
		try{
			boolean isApprovalChargeThirdPartyLimit = fundChargeService.checkChargeThirdPartyLimit(
					request.getBody().getParam().getUserId(),request.getBody().getParam().getPreChargeAmt());
	
			logger.info("第三方支付充值限制檢查結果為:"+isApprovalChargeThirdPartyLimit+".....");
	
			if(isApprovalChargeThirdPartyLimit){//審核結果如為true，將response內result設定為1代表通過
				response.setResult(1L);
				return response;
			}
		
		}catch(Exception e){
			logger.error("第三方支付充值限制檢查發生異常:"+e);
			response.setResult(-1L);
		}
		
		
		return response;		
	}
	@RequestMapping(value = "/queryUnhandleCharge")
	@ResponseBody
	public Response<Object> queryUnhandleCharge(@RequestBody Request<Object> request) throws Exception {
		Response<Object> resp = new Response<Object>(request);
		logger.debug("--------------------------------------------");
		try {
			Integer count = fundChargeService.queryUnhandleCharge();
			logger.debug("------------count : " + count);
			resp.getBody().setResult(count);
		} catch (Exception e) {
			logger.error("queryUnhandleCharge error", e);
			throw e;
		}
		return resp;
	}
}
