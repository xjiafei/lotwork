package com.winterframework.firefrog.schedule;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.service.IFundMowPayService;
import com.winterframework.firefrog.fund.service.IFundWithDrawService;
import com.winterframework.firefrog.fund.service.impl.sp.SignUtils;
import com.winterframework.firefrog.fund.service.impl.th.PayQueryResponse;
import com.winterframework.firefrog.fund.service.impl.th.ThPayQuery;
import com.winterframework.firefrog.fund.service.impl.th.ThPayQueryResponse;
import com.winterframework.firefrog.fund.service.impl.th.ThPayQueryResponseDetail;
import com.winterframework.firefrog.fund.util.MowNumTool;
import com.winterframework.modules.security.MD5Encrypt;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("thPayQueryTask")
public class ThPayQueryTask {
	
	private Logger logger = LoggerFactory.getLogger(ThPayQueryTask.class);
	
	@Resource(name = "fundMowPayServiceImpl")
	private IFundMowPayService fundMowPayService;
	@Resource(name = "fundWithDrawServiceImpl")
	private IFundWithDrawService fundWithDrawService;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient mcwClient;
	
	@PropertyConfig(value = "thpay_charset")
	private String inputCharset;
	
	@PropertyConfig(value = "thpay_company_id")
	private String merchantCode;
	
	@PropertyConfig(value = "thpay_key")
	private String thpayKey;
	
	@PropertyConfig(value = "thpay_url")
	private String thpayUrl;
	
	@PropertyConfig(value = "url.baseFundUrl")
	private String baseUrl;
	
	@PropertyConfig(value = "url.fund.withdraw")
	private String urlWithdraw;
	@PropertyConfig(value = "thpay_withdraw_query_url")
	private String thpayWithdrawQueryUrl;
	@PropertyConfig(value = "sppay_withdraw_query")
	private String sppayWithdrawQueryUrl;
	@PropertyConfig(value = "sppay_private_key")
	private String sppayPrivateKey;
	@PropertyConfig(value = "sppay_account")
	private String sppayAccount;
	
	public void execute() throws Exception {
		logger.info("----------ThPayQueryTask start --------- ");
		//查詢ThPay 訂單處理中的
		List<FundMowPay> thPayOrderList =  fundMowPayService.queryThPayOrder(8L);
		
		for(FundMowPay thPayOrder : thPayOrderList){
			thPayOrder.setMowStatus(9l);
			int success = fundMowPayService.updateFundMowPay(thPayOrder);
			if(success == 1 ){
				try{
					
					FundWithdraw wd=fundWithDrawService.getBySn(thPayOrder.getExSn());
					if(!handleQuery(thPayOrder,wd)){
						logger.error("PayOrder error is null = "+thPayOrder.getExSn() );
						thPayOrder.setMowStatus(8l);
						fundMowPayService.updateFundMowPay(thPayOrder);
					}
				}catch(Exception e){
					logger.error("ThPayQueryResponse or withdrawalResult error sn = " 
							+ thPayOrder.getExSn());
					thPayOrder.setMowStatus(8l);
					fundMowPayService.updateFundMowPay(thPayOrder);
					continue;
				}
			}
		}
	}
	
	private boolean  handleQuery(FundMowPay payOrder,FundWithdraw wd) throws Exception{
		if(wd.getWithdrawMode()==8L){
			Map<String, String> responseMap=getSpPayQueryResponse(wd.getSn());
			if("000000".equals(responseMap.get("RECODE"))){
				//0 待处理	1 代付成功	2 代付失败
				int status =2;
				if(responseMap.get("STLSTS")!=null){
					status=Integer.valueOf(responseMap.get("STLSTS"));
				}
				PayQueryResponse payQueryRes=new PayQueryResponse();
				payQueryRes.setOrderNo(responseMap.get("CP_NO"));
				payQueryRes.setOrderTime(DateUtils.format(payOrder.getApplyTime(),"yyyy-MM-dd HH:mm:ss"));
				payQueryRes.setOrderAmount(MowNumTool.fromFrifrog(payOrder.getFfAmount())+"");
				payQueryRes.setPlatOrderNo(responseMap.get("BANK_FLOWNO"));
				payQueryRes.setRetMsg(responseMap.get("REMSG"));
				payQueryRes.setStatus(status+"");
				
				//1:系统处理中  2:银行处理中 3:成功  4:失败
				int wStatus=status==1?3:(status==2?4:2);
				withdrawalResultNew(payOrder,payQueryRes,wStatus);
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	public Map<String, String> getSpPayQueryResponse(String sn) throws Exception{
		String signDataSrc = "CP_NO="+sn;
		String SIGN = SignUtils.Signaturer(signDataSrc, sppayPrivateKey);
		String param="MERCNUM="+java.net.URLEncoder.encode(sppayAccount,"UTF-8")+"&TRANDATA="+java.net.URLEncoder.encode(signDataSrc,"UTF-8")+"&SIGN="+java.net.URLEncoder.encode(SIGN,"UTF-8");
		
		logger.info(" params : " + param);
		String resp = mcwClient.postHttpJson(sppayWithdrawQueryUrl, param);
		logger.info("SpPay withdraw sn ={},result={} :" , new Object[]{sn,resp});
		
		//解密
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap = JsonUtil.fromJson2Map(resp, String.class, String.class);
		return responseMap;
	}
	public ThPayQueryResponse getThPayQueryResponse(String sn) throws JAXBException{
		ThPayQuery thPayQuery = new ThPayQuery();
		thPayQuery.setInputCharset(inputCharset);
		thPayQuery.setMerchantCode(merchantCode);
		thPayQuery.setMerchantOrder(sn);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("input_charset", inputCharset);
		params.put("merchant_code", merchantCode);
		params.put("merchant_order", sn);
		params.put("sign", MD5Encrypt.encrypt(thPayQuery.createParam(thpayKey)));
		logger.info("------------------ThPay post start -------------------");
		String xml = mcwClient.invokeHttpXml(thpayUrl + thpayWithdrawQueryUrl, params);
		logger.info("ThPay Response  = " + xml);
		JAXBContext context = JAXBContext.newInstance(ThPayQueryResponse.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		return (ThPayQueryResponse) unmarshal.unmarshal(new StringReader(xml));
	}
	
	private void withdrawalResult(FundMowPay thPayOrder,ThPayQueryResponse thPayQueryResponse, int remitStatus) throws Exception {
		
		if(remitStatus == 1 || remitStatus == 2){
			thPayOrder.setMowStatus(8l);
			fundMowPayService.updateFundMowPay(thPayOrder);
		}
		if(remitStatus == 3 || remitStatus == 4){
			Map<String, Object> withdrawConfirm = getWithdrawConfirm(thPayQueryResponse);
			mcwClient.invokeHttpPost(baseUrl+urlWithdraw,withdrawConfirm);
		}
	}
	private void withdrawalResultNew(FundMowPay thPayOrder,PayQueryResponse payQueryResponse, int status) throws Exception {
		if(status == 1 || status == 2){
			thPayOrder.setMowStatus(8l);
			fundMowPayService.updateFundMowPay(thPayOrder);
		}
		if(status == 3 || status == 4){
			Map<String, Object> withdrawConfirm = getWithdrawConfirmNew(payQueryResponse);
			mcwClient.invokeHttpPost(baseUrl+urlWithdraw,withdrawConfirm);
		}
	}
	private Map<String, Object> getWithdrawConfirmNew(PayQueryResponse payQueryResponse){
		Map<String, Object> withdrawConfirm = new HashMap<String, Object>();
		withdrawConfirm.put("mownecum_order_num", payQueryResponse.getPlatOrderNo());
		withdrawConfirm.put("company_order_num", payQueryResponse.getOrderNo());
		withdrawConfirm.put("status", Long.valueOf(payQueryResponse.getStatus()) == 3l ? 1l : 3l);
		withdrawConfirm.put("detail", payQueryResponse.getRetMsg());
		withdrawConfirm.put("amount", BigDecimal.valueOf(Double.valueOf(payQueryResponse.getOrderAmount())));
		withdrawConfirm.put("exact_transaction_charge", BigDecimal.valueOf(0));
		withdrawConfirm.put("key", "");
		//2015-04-27 18:47:23 轉 20150427184723
		String date = payQueryResponse.getOrderTime();
		date = date.replaceAll("-", "");
		date = date.replaceAll(" ", "");
		date = date.replaceAll(":", "");
		withdrawConfirm.put("operating_time", date);
		return withdrawConfirm;
	}
	private Map<String, Object> getWithdrawConfirm(ThPayQueryResponse thPayResponse){
		ThPayQueryResponseDetail thPayDetail = thPayResponse.getThPayQueryResponseDetail();
		Map<String, Object> withdrawConfirm = new HashMap<String, Object>();
		withdrawConfirm.put("mownecum_order_num", thPayDetail.getRemitOrder());
		withdrawConfirm.put("company_order_num", thPayDetail.getMerchantOrder());
		withdrawConfirm.put("status", Long.valueOf(thPayDetail.getRemitStatus()) == 3l ? 1l : 3l);
		withdrawConfirm.put("detail", thPayDetail.getRemitStatusDesc());
		withdrawConfirm.put("amount", BigDecimal.valueOf(Double.valueOf(thPayDetail.getRemitAmount())));
		withdrawConfirm.put("exact_transaction_charge", BigDecimal.valueOf(0));
		withdrawConfirm.put("key", thPayDetail.getSign());
		//2015-04-27 18:47:23 轉 20150427184723
		String date = thPayDetail.getRemitTime();
		date = date.replaceAll("-", "");
		date = date.replaceAll(" ", "");
		date = date.replaceAll(":", "");
		withdrawConfirm.put("operating_time", date);
		return withdrawConfirm;
	}

}
