package com.winterframework.firefrog.phone.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.phone.utils.ECPSSUtils;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardApplyBindRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardQueryRecordResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardQueryRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.BankCardQueryResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.BindDate;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeApplyRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeApplyResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeQueryRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeRecordDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeRecordRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeRecordResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.ChargeStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.ConfigValueRequestDTO;
import com.winterframework.firefrog.phone.web.cotroller.dto.ConfigValueResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.FundBank;
import com.winterframework.firefrog.phone.web.cotroller.dto.FundChargeAppealRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.QuickConfirmRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.QuickConfirmResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.QuickInitDto;
import com.winterframework.firefrog.phone.web.cotroller.dto.QuickInitRequest;
import com.winterframework.firefrog.phone.web.cotroller.dto.QuickInitResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStrucResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

@Controller("quickController")
@RequestMapping("/recharge")
public class QuickController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(QuickController.class);
	
	@PropertyConfig(value="url.fund.cardBindingInit")
	private String cardBindingInitUrl;
	@PropertyConfig(value="url.recharge.confirm")
	private String confirmUrl;
	@PropertyConfig(value="url.front.teamUserBalance")
	private String teamUserBalanceUrl;
	@PropertyConfig(value="url.fund.chargeQuery")
	private String chargeRecordQueryUrl;
	@PropertyConfig(value="url.front.getConfig")
	private String configQueryUrl;
	@PropertyConfig(value="url.fund.cardList")
	private String cardListUrl;
	@PropertyConfig(value="url.fund.cardBindingConfirm")
	private String cardBindingConfirmUrl;
	@PropertyConfig(value="url.fund.cardBindingCommit")
	private String cardBindingCommitUrl;
	@PropertyConfig(value="url.front.checkAliOpen")
	private String checkAliOpenUrl;
	@PropertyConfig(value="url.front.appealRecharge")
	private String appealRechargeUrl;
	@PropertyConfig(value="url.fund.queryAllBank")
	private String queryAllBank;
	@PropertyConfig(value="url.fund.chargeThirdPartyLimit")
	private String chargeThirdPartyLimitUrl;
	
	@ResponseBody
	@RequestMapping("/quickInit")
	public Response<QuickInitResponse> init(@RequestBody Request<QuickInitRequest> request) throws Exception{
		
		Response<QuickInitResponse> response = new Response<QuickInitResponse>(request);
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			
			UserToken ut = getUserToken(account);
			
			//test code
			//UserToken ut = new UserToken();
			//ut.setUserName("lingss");
			//ut.setUserId(1371761L);
			
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, null,new Pager(),ut.getUserId(), null, new TypeReference<Response<UserStrucResponse>>() {
			});
	
			UserStrucResponse user = firforgResponse.getBody().getResult();
			Map<String,String> map = new HashMap<String,String>();
			for(String key : RECHARGE_COUNTDOWN){
				ConfigValueRequestDTO cfgReq = new ConfigValueRequestDTO();
				cfgReq.setModule("fund");
				cfgReq.setFunction(key);
				log.info("key : " + key);
				Response<ConfigValueResponse> cfgRsp = httpClient.invokeHttp(firefrogUrl+ configQueryUrl, cfgReq,new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<ConfigValueResponse>>() {
				});
				if(key.equals("thirdChargeLimit")){
					//取出config thirdChargeLimit 的value內limitMoney的值
					Map<String, Object> thirdChargeLimitMap = DataConverterUtil.convertJson2Map(cfgRsp.getBody().getResult().getVal());
					map.put(key, thirdChargeLimitMap.get("limitMoney").toString());
					
				}else{
				
					map.put(key, cfgRsp.getBody().getResult().getVal());
				}
			}
			
			BindDate requestData = new BindDate();
			requestData.setIsBind(0);
			
			QuickInitResponse result = new QuickInitResponse();
			Response<BankCardQueryRecordResponse> gameResponse = httpClient.invokeHttp(firefrogUrl+ cardBindingInitUrl, requestData,new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<BankCardQueryRecordResponse>>() {
			});
			
			List<QuickInitDto> list =new ArrayList<QuickInitDto>();
			if (null != gameResponse.getBody()
					&& null != gameResponse.getBody().getResult()) {

				BankCardQueryRecordResponse cards = gameResponse.getBody().getResult();
				
				for (FundBank bank : cards.getBankStruc()) {
					//銀聯新增後台　一般用戶與ＶＩＰ用戶開關
					if(bank.getId().intValue() == 30){
						FundBank aliOpenReq = new FundBank();
						aliOpenReq.setId(bank.getId());
						aliOpenReq.setUserId(ut.getUserId());
						Response<String> isAliOpen = httpClient.invokeHttp(firefrogUrl+ checkAliOpenUrl, aliOpenReq,new Pager(), ut.getUserId(), ut.getUserName(),new TypeReference<Response<String>>() {
						});
						if(!"Y".equals(isAliOpen.getBody().getResult()))
							continue;
					}
					//銀聯充值ID=51，支付寶ID=30
					
					if(bank.getId() < 30L && bank.getDeposit().longValue() == 1L){
						QuickInitDto dto = new QuickInitDto();
						dto.setBankId(bank.getId());
						dto.setBankName(bank.getName());
						//快捷的限額 跟 手機銀聯的限額同步 然後 不分VIP(之後在做調整)
						if(bank.getOther().intValue() == 1){
							QuickInitDto dto2= copy(dto);
//							dto.setMax(new BigDecimal(bank.getOtheruplimit() == null ? 0 : bank.getOtheruplimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
//							dto.setMin(new BigDecimal(bank.getOtherdownlimit() == null ? 0 : bank.getOtherdownlimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
//							dto.setVipMax(new BigDecimal(bank.getOthervipuplimit() == null ? 0	: bank.getOthervipuplimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
//							dto.setVipMin(new BigDecimal(bank.getOthervipdownlimit() == null ? 0 : bank.getOthervipdownlimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
							dto2.setMax(new BigDecimal(5000).doubleValue());
							dto2.setMin(new BigDecimal(10).doubleValue());
							dto2.setVipMax(new BigDecimal(5000).doubleValue());
							dto2.setVipMin(new BigDecimal(10).doubleValue());
							dto2.setOther(true);
							list.add(dto2);
						}
						dto.setMax(new BigDecimal(bank.getUplimit() == null ? 0	: bank.getUplimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
						dto.setMin(new BigDecimal(bank.getLowlimit() == null ? 0 : bank.getLowlimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
						dto.setVipMax(new BigDecimal(bank.getVipuplimit() == null ? 0	: bank.getVipuplimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
						dto.setVipMin(new BigDecimal(bank.getViplowlimit() == null ? 0 : bank.getViplowlimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
						if(bank.getId().longValue()==2L){	//网银转账（其它银行）默认用招行ID  附言对上即可转账成功
							QuickInitDto dto3=copy(dto);
							dto3.setBankName("其它银行");
							list.add(dto3);
						}
						
						log.info(" " + user.getRegisterDate());
						log.info(" " + (System.currentTimeMillis() - 90 * 60 * 60 * 24 * 1000L));
						// 老舊用戶 和VIP用戶區別 [新用戶看不到銀聯充值選項](目前無使用)
						//if (user.getRegisterDate() < (System.currentTimeMillis() - 90 * 60 * 60 * 24 * 1000L) || user.getVipLvl() > 0  || bank.getOther().intValue() == 1 ){
							list.add(dto);
						//}
					}
					//移动端充值
					if(bank.getMoveDeposit() != null && bank.getMoveDeposit().longValue() == 1L){
						QuickInitDto dto = new QuickInitDto();
						dto.setBankId(bank.getId());
						dto.setBankName(bank.getName()); 
						dto.setMax(new BigDecimal(bank.getUplimit() == null ? 0	: bank.getUplimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
						dto.setMin(new BigDecimal(bank.getLowlimit() == null ? 0 : bank.getLowlimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
						dto.setVipMax(new BigDecimal(bank.getVipuplimit() == null ? 0	: bank.getVipuplimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
						dto.setVipMin(new BigDecimal(bank.getViplowlimit() == null ? 0 : bank.getViplowlimit()).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP).doubleValue());
						list.add(dto);
					}
					
				}
			}
			result.setList(list);
			result.setMap(map);
			result.setIsvip(user.getVipLvl().longValue());
			result.setIsvip(0L);
			response.setResult(result);
			
		} catch (Exception e) {
			log.error("RechargeQuickInit error:", e);
			response.getHead().setStatus(901000L);
		}
		return response;
	}
	private QuickInitDto copy(QuickInitDto dto){
		QuickInitDto dtonew=new QuickInitDto();
		dtonew.setBankId(dto.getBankId());
		dtonew.setBankName(dto.getBankName()); 
		dtonew.setMax(dto.getMax());
		dtonew.setMin(dto.getMin());
		dtonew.setVipMax(dto.getVipMax());
		dtonew.setVipMin(dto.getVipMin());
		return dtonew;
		
	}
	
	@ResponseBody
	@RequestMapping("/quickCommit")
	public Response<QuickConfirmResponse> commit(@RequestBody Request<QuickConfirmRequest> request) throws Exception{
		
		Response<QuickConfirmResponse> response = new Response<QuickConfirmResponse>(request);
		
		String token = request.getHead().getSessionId();
		try {
			String account = getUserNameByToken(token);
			if(null == account){
				response.getHead().setStatus(7L);
				return response;
			}
			UserToken ut = getUserToken(account);
			
			//add 冻结判断 	
			Response<UserStrucResponse> firforgResponse = httpClient.invokeHttp(firefrogUrl+teamUserBalanceUrl, null,new Pager(),ut.getUserId(), null, new TypeReference<Response<UserStrucResponse>>() {
			});
			
			UserStrucResponse user = firforgResponse.getBody().getResult();
			if(user.getFreezeMethod() !=null && user.getFreezeMethod()>0){
				
				//完全冻结
				//1.完全冻结，不能登陆，
				//2.可登陆，不可投注，不可充提
				//3.可登陆，不可投注，可充提
				//4.不可转帐，不可提现。
				if(user.getFreezeMethod().intValue() < 3){
					response.getHead().setStatus(109992L);//109992 此功能已经冻结
					return response;
				}
			}
			
			QuickConfirmRequest commitRequest = request.getBody().getParam();
			log.info("bankId : " + commitRequest.getBankId());
			log.info("system : " + commitRequest.getSystem());
			log.info("version : " + commitRequest.getVersion());
			
			ChargeApplyRequest requestData = new ChargeApplyRequest();
			requestData.setApplyTime(new Date());
			requestData.setBankId(commitRequest.getBankId());
			//兼容旧版本APP
			if(commitRequest.getDepositMode()==null){
				requestData.setDepositMode(2L);
			}else{
				requestData.setDepositMode(commitRequest.getDepositMode().longValue());
			}
			if(51 == commitRequest.getBankId().intValue()){
				requestData.setDepositMode(5L);
			}else if (30 == commitRequest.getBankId().intValue()){
				requestData.setDepositMode(6L);
			}
			requestData.setMcBankId(commitRequest.getBankId());
			requestData.setPreChargeAmt(new BigDecimal(commitRequest.getMoney()).multiply(new BigDecimal(10000)).setScale(2, RoundingMode.HALF_UP).longValue());
			requestData.setUserAct(ut.getUserName());
			requestData.setUserId(ut.getUserId());
			requestData.setBankNumber(commitRequest.getBankNumber());
			requestData.setBankAccount(commitRequest.getBankAccount());
			requestData.setPlatfom(commitRequest.getSystem());
			requestData.setVer(commitRequest.getVersion());
			requestData.setCustomerIp(IPConverter.longToIp(Long.valueOf(commitRequest.getCustomerIp())));
			
			
			//判斷第三方支付充值金額限制
			log.debug("開始判斷是否通過第三方支付充值金額限制.....");
			Response<Long> thirdPartyResponse = httpClient.invokeHttp(firefrogUrl+chargeThirdPartyLimitUrl, requestData,new Pager(),ut.getUserId(), null, Long.class);
			log.info("thirdPartyResponse.getBody().getResult():"+thirdPartyResponse.getBody().getResult());
			
			if (thirdPartyResponse.getBody().getResult()==0){
				log.debug("沒有通過第三方支付充值金額限制......");
				response.getHead().setStatus(110000L);//未通過第三方支付充值金額限制
				return response;
			}else if(thirdPartyResponse.getBody().getResult()== -1){
				log.error("第三方支付充值限制檢查發生異常");
				response.getHead().setStatus(901000L);//系統發生錯誤回傳 "服务器内部错误"
				return response;
			}
			
			//如果是支付寶要記錄用戶帳號/昵称
			if(30 == commitRequest.getBankId().intValue()){
				addAliPayBankCard(commitRequest,ut);
			}
			
			
			Response<ChargeApplyResponse> firefrogResponse = httpClient.invokeHttp(firefrogUrl+confirmUrl, requestData, new Pager(), ut.getUserId(), ut.getUserName(), new TypeReference<Response<ChargeApplyResponse>>() {
			});
			
			QuickConfirmResponse result = new QuickConfirmResponse();
			if(null != firefrogResponse.getBody() && null != firefrogResponse.getBody().getResult()){
				ChargeApplyResponse res=firefrogResponse.getBody().getResult();
				result.setBankId(res.getBankId());
				result.setRcvAccNum(res.getRcvAccNum());
				result.setRevAccName(res.getRevAccName());
				result.setRcvBankName(res.getRcvBankName());
				result.setRcvEmail(res.getRcvEmail());
				result.setChargeMemo(res.getChargeMemo());
				result.setMode(res.getMode());
				result.setExpireTime(res.getExpireTime());
				result.setUrl(res.getBreakUrl());
				result.setOrderNo(res.getPayOrderNo());
				if(res.getDdbPay()!=null){
					result.setMerchantCode(res.getDdbPay().getMerchantCode());
					result.setServiceType(res.getDdbPay().getServiceType());
					result.setReturnUrl(res.getDdbPay().getReturnUrl());
					result.setNotifyUrl(res.getDdbPay().getNotifyUrl());
					result.setInterfaceVersion(res.getDdbPay().getInterfaceVersion());
					result.setInputCharset(res.getDdbPay().getInputCharset());
					result.setSignType(res.getDdbPay().getSignType());
					result.setOrderTime(res.getDdbPay().getOrderTime());
					result.setOrderAmount(res.getDdbPay().getOrderAmount());
					result.setBankCode(res.getDdbPay().getBankCode());
					result.setProductName(res.getDdbPay().getProductName());
					result.setSign(res.getDdbPay().getSign());
					result.setUrl(res.getDdbPay().getChargeUrl());
				}				
			}
			
			response.setResult(result);
		} catch (Exception e) {
			log.error("RechargeQuickCommit error:", e);
			response.getHead().setStatus(901000L);
		}
		
		return response;
	}
	
	@ResponseBody
	@RequestMapping("/chargeRecord")
	public Response<ChargeRecordResponse> chargeRecord(@RequestBody Request<ChargeRecordRequest> request) throws Exception{
		
		 Response<ChargeRecordResponse> response = new  Response<ChargeRecordResponse>(request);
		 
		 String token = request.getHead().getSessionId();
			try {
				String account = getUserNameByToken(token);
				if(null == account){
					response.getHead().setStatus(7L);
					return response;
				}
				UserToken ut = getUserToken(account);
				//test code
//				UserToken ut = new UserToken();
//				ut.setUserName("katy009");
//				ut.setUserId(1338522L);
				ChargeRecordRequest record = request.getBody().getParam();
				Long[] depositeModes = null;
				if(record.getChargeType() >= 0){
					
					if(record.getChargeType()==0){
						depositeModes = new Long[9];
						depositeModes[0]=1L;
						depositeModes[1]=2L;
						depositeModes[2]=3L;
						depositeModes[3]=5L;
						depositeModes[4]=6L;
						depositeModes[5]=7L;
						depositeModes[6]=8L;
						depositeModes[7]=9L;
						depositeModes[8]=10L;
					}else{
						depositeModes = new Long[1];
						depositeModes[0]=record.getChargeType().longValue();
					}
				}
				
				ChargeQueryRequest requestData = new ChargeQueryRequest();
//				requestData.setAccount(ut.getUserName());
				requestData.setUserId(ut.getUserId());
				requestData.setDepositeMode(depositeModes);
//				requestData.setIsReport("rpt");
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(java.util.Calendar.DATE, -7);
				//test code
				//cal.add(java.util.Calendar.DATE, -20);
				requestData.setFromDate(DateUtils.parse(DateUtils.format( cal.getTime(), DateUtils.DATE_FORMAT_PATTERN)));
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(new Date());
				cal2.add(java.util.Calendar.DATE, 1);
				requestData.setToDate(DateUtils.parse(DateUtils.format(cal2.getTime(), DateUtils.DATE_FORMAT_PATTERN)));
				
				BindDate bind = new BindDate();
				bind.setIsBind(1);
				BankCardQueryRecordResponse bankResponse = (BankCardQueryRecordResponse) httpClient.invokeHttp(firefrogUrl+queryAllBank, bind, new Pager(0, 10000),ut.getUserId(),ut.getUserName(), new  TypeReference<Response<BankCardQueryRecordResponse>>(){}).getBody().getResult();
				Response<List<ChargeStruc>> queryResponse = httpClient.invokeHttp(firefrogUrl+chargeRecordQueryUrl, requestData, new Pager(0, 10000),ut.getUserId(),ut.getUserName(), new  TypeReference<Response<List<ChargeStruc>>>(){});
				Boolean canAppeal = false;
				for(FundBank bank : bankResponse.getBankStruc()){
					if(bank.getId() == 30l && bank.getMoveCanRechargeAppeal() == 1l){
						canAppeal = true;
					}
				}
				ChargeRecordResponse result = new ChargeRecordResponse();				
				List<ChargeRecordDto> list = new ArrayList<ChargeRecordDto>();
				
				if(null != queryResponse.getBody() && null != queryResponse.getBody().getResult()){
					
					List<ChargeStruc> chargeList = queryResponse.getBody().getResult();
					
					for(ChargeStruc struc : chargeList){
						ChargeRecordDto dto = new ChargeRecordDto();
						dto.setPayBankId(struc.getPayBankId().intValue());
						dto.setApplyMoney(new BigDecimal(struc.getApplyAmt() == null ? 0 : struc.getApplyAmt()).divide(new BigDecimal(10000), 3, RoundingMode.HALF_UP).doubleValue() );
						dto.setOrderId(struc.getSn());
						dto.setRealMoney(new BigDecimal(struc.getRealChargeAmount()==null ? 0 : struc.getRealChargeAmount()).divide(new BigDecimal(10000), 3, RoundingMode.HALF_UP).doubleValue() );
						dto.setStatus(struc.getStatus()==null ? 0 :struc.getStatus().intValue());						
						dto.setTime(DateUtils.format(DataConverterUtil.convertLong2Date(struc.getApplyTime()==null? System.currentTimeMillis():struc.getApplyTime()), DateUtils.DATETIME_FORMAT_PATTERN));
						String temp =getDepositMode(struc.getDepositMode());
						if(null != struc.getPayBankId() && struc.getPayBankId() > 0){
								temp +=  "["+ Bank_Name_Map.get(struc.getPayBankId()+"")+"]";
						}
						dto.setType(temp);
						dto.setWaitTime(struc.getWaitTime());
						dto.setAppealStatus(false);
						
						if(canAppeal){
							if(dto.getPayBankId() == 30){
								if(DateUtils.currentDate().getTime()-struc.getApplyTime() >= struc.getWaitTime() * 60){
									if(dto.getStatus() == 1 || dto.getStatus() == 3 || dto.getStatus() == 4){
										dto.setAppealStatus(true);
									}
								}
							}
						}
						list.add(dto);
					}
					result.setList(list);
				}
				
				response.setResult(result);
			}catch(Exception e){
				log.error("Recharge chargeRecord error:", e);
				response.getHead().setStatus(901000L);
			}
		 
		 return response;
	}
	
	private String getDepositMode(Long mode){
		
		if(mode == 1L){
			return "网银汇款";
		}else if(mode == 2L){
			return "快捷充值";
		}else if(mode == 3L){
			return "财付通充值";
		}else if(mode == 5L){
			return "银联充值";
		}else if(mode == 6L){
			return "支付宝充值";
		}
		return "";
	}
	
	private void addAliPayBankCard(QuickConfirmRequest req, UserToken token){
		try{
			//可綁定數量
			ConfigValueRequestDTO cfgReq = new ConfigValueRequestDTO();
			cfgReq.setModule("fund");
			cfgReq.setFunction("aliPayChargeCoute");
			Response<ConfigValueResponse> cfgRsp = httpClient.invokeHttp(firefrogUrl+ configQueryUrl, cfgReq,new Pager(),new TypeReference<Response<ConfigValueResponse>>() {
			});
			int icardCount = Integer.valueOf(cfgRsp.getBody().getResult().getVal());
			log.info("icardCount : " + icardCount);
			//user 已綁定數量
			//判断 锁定时间
			BankCardQueryRequest requestData = new BankCardQueryRequest();
			requestData.setUserId(token.getUserId());
			requestData.setBindCardType(1);
			Response<BankCardQueryResponse> gameResponse = httpClient.invokeHttp(firefrogUrl+ cardListUrl, requestData, new Pager(), new TypeReference<Response<BankCardQueryResponse>>() {
			});
			
			if(gameResponse.getBody().getResult().getUserBankStruc().size() < icardCount){
				BankCardApplyBindRequest bcReqData = new BankCardApplyBindRequest();
				bcReqData.setUserId(token.getUserId());
				bcReqData.setBankNumber(req.getBankNumber());
				bcReqData.setBankAccount(req.getBankAccount());
				
				Response<Object> cbConfirmResponse = httpClient.invokeHttp(firefrogUrl+cardBindingConfirmUrl, bcReqData, new TypeReference<Response<Object>>(){});
				if(cbConfirmResponse.getHead().getStatus() == 0l){
					BankCardApplyBindRequest bcApplyReqData = new BankCardApplyBindRequest();
					bcApplyReqData.setUserId(token.getUserId());
					bcApplyReqData.setBankAccount(req.getBankAccount());
					bcApplyReqData.setBankId(req.getBankId().longValue());
					bcApplyReqData.setBankNumber(req.getBankNumber());
					//bcApplyReqData.setBranchName(req.getBank());
					//ApplyReqData.setCity(commit.getCity());
					bcApplyReqData.setMcBankId(req.getBankId().longValue());
					//bcApplyReqData.setProvince(commit.getProvince());
					bcApplyReqData.setBindcardType(1L);
					Response<Object> bindCardRsp = httpClient.invokeHttp(firefrogUrl+cardBindingCommitUrl, bcApplyReqData, token.getUserId(), token.getUserName(),new TypeReference<Response<Object>>(){});
					log.info("status : " + bindCardRsp.getHead().getStatus());
				}
			}
			
		}catch(Exception e){
			
		}
	}
	
	@ResponseBody
	@RequestMapping("/appealRecharge")
	public Response<Boolean> appealRecharge(@RequestBody Request<FundChargeAppealRequest> request) throws Exception{
		 Response<Boolean> response = new  Response<Boolean>(request);
		 log.info("appealRecharge getChargeSn =  " + request.getBody().getParam().getChargeSn());
		 log.info("appealRecharge getChargeAmt =  " + request.getBody().getParam().getChargeAmt());
		 log.info("appealRecharge getTransactionNum =  " + request.getBody().getParam().getTransactionNum());
		 String token = request.getHead().getSessionId();
			try {
				String account = getUserNameByToken(token);
				if(null == account){
					response.getHead().setStatus(7L);
					return response;
				}
				UserToken ut = getUserToken(account);
				//test code
//				UserToken ut = new UserToken();
//				ut.setUserName("katy009");
//				ut.setUserId(1338522L);
				FundChargeAppealRequest requestData = request.getBody().getParam();
				requestData.setUserId(ut.getUserId());
				requestData.setUserAccount(ut.getUserName());
				requestData.setChargeAmt(requestData.getChargeAmt() * 10000);
				requestData.setDepositeMode(6);
				requestData.setBankId(30);
				log.info("requestData userId =  " + requestData.getUserId());
				log.info("requestData Account =  " + requestData.getUserAccount());
				Response<List<FundChargeAppealRequest>> queryResponse = httpClient.invokeHttp(firefrogUrl+appealRechargeUrl, requestData, new Pager(0, 10000),ut.getUserId(),ut.getUserName(), new  TypeReference<Response<List<FundChargeAppealRequest>>>(){});
				if(queryResponse.getHead().getStatus() == 1l ){
					response.setResult(true);
				}else{
					response.setResult(false);
				}
				log.info("appealRecharge queryResponse =  " + queryResponse.getHead().getStatus());
			}catch(Exception e){
				log.error("Recharge chargeRecord error:", e);
				response.getHead().setStatus(901000L);
			}
		 
		 return response;
	}
}
