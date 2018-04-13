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
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.service.IFundMowPayService;
import com.winterframework.firefrog.fund.service.impl.th.ThPayQuery;
import com.winterframework.firefrog.fund.service.impl.th.ThPayQueryResponse;
import com.winterframework.firefrog.fund.service.impl.th.ThPayQueryResponseDetail;
import com.winterframework.modules.security.MD5Encrypt;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("thPayConfirmTask")
public class ThPayConfirmTask {
	
	private Logger logger = LoggerFactory.getLogger(ThPayConfirmTask.class);
	
	@Resource(name = "fundMowPayServiceImpl")
	private IFundMowPayService fundMowPayService;
	
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
	
	@PropertyConfig(value = "url.fund.transferToMow")
	private String transferToMow;
	
	@PropertyConfig(value = "thpay_withdraw_query_url")
	private String thpayWithdrawQueryUrl;
	
	private static long LIMIT_TIMES = 3L;
	
	public void execute() throws Exception {
		logger.info("-------------------ThPayConfirmTask start -------------------- ");
		Thread.sleep(10000);
		/*//查詢ThPay 訂單處理中的
		List<FundMowPay> thPayOrderList =  fundMowPayService.queryThPayOrder(7L);
		logger.info("thPayOrderList size : " + thPayOrderList.size());
		
		for(FundMowPay thPayOrder : thPayOrderList){
			if(thPayOrder.getConfirmCnt().longValue()>= LIMIT_TIMES){
				continue;
			}
			logger.info("thPayOrder.getConfirmCnt() : " + thPayOrder.getConfirmCnt());
			logger.info("thPayOrder.setMowStatus() : " + thPayOrder.getMowStatus());
			
			logger.info("thPayOrder.SN() : " + thPayOrder.getExSn());
			thPayOrder.setMowStatus(9l);
			int success = fundMowPayService.updateFundMowPay(thPayOrder);
			logger.info("success : " + success);
			
			if(success == 1 ){
				try{
					ThPayQueryResponse thPayQueryResponse = getThPayQueryResponse(thPayOrder.getExSn());
					//若查詢為失敗，且訂單不存在，則轉發通匯
					String isSuccess = thPayQueryResponse.getThPayQueryResponseDetail().getIsSuccess();
					//isSuccess = "SUCCESS";
					String errMsg = thPayQueryResponse.getThPayQueryResponseDetail().getErrorMsg();
					logger.error("isSuccess : " + isSuccess + ",errMsg : " + errMsg);
					//errMsg = "订单不存在";
					//if(thPayQueryResponse.getThPayQueryResponseDetail().getIsSuccess().equals("FALSE") &&
					if(("FALSE").equals(isSuccess) && "订单不存在".equals(errMsg)){
						logger.info("Thpay 订单不存在 transfer DP~~!" );
						transferMow(thPayOrder,thPayQueryResponse);
						
					}else{
						//查詢成功則改變fundMowPay狀態為8，ThPayQueryTask去做再次確認
						thPayOrder.setMowStatus(8l);
					}
					
				}catch(Exception e){
					//失敗則改回待查狀態
					logger.error("ThPayConfirmTask error:",e.fillInStackTrace());
					logger.error("ThPay Confirm or transfer Mow error sn = "
							+ thPayOrder.getExSn());
					thPayOrder.setMowStatus(7l);					
				}finally{
					long confirmCnt = thPayOrder.getConfirmCnt().longValue();
					thPayOrder.setConfirmCnt(confirmCnt+1);
					logger.info("update confirmCnt:"+confirmCnt+"+1");
					fundMowPayService.updateFundMowPay(thPayOrder);
				}

			}
		}*/
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
		String xml = mcwClient.invokeHttpXml(thpayUrl + thpayWithdrawQueryUrl, params);
		logger.info("ThPay Response  = " + xml);
		JAXBContext context = JAXBContext.newInstance(ThPayQueryResponse.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		return (ThPayQueryResponse) unmarshal.unmarshal(new StringReader(xml));
	}
	
	private void transferMow(FundMowPay thPayOrder,ThPayQueryResponse thPayQueryResponse) throws Exception{
		logger.info("==========================transferMow=====================");
		Map<String, Object> withdrawConfirm = getWithdrawConfirm(thPayOrder.getExId());
		logger.info("withdrawConfirm map : " + withdrawConfirm.toString());
		mcwClient.invokeHttpPost(baseUrl+transferToMow,withdrawConfirm);
	}
	
	private Map<String, Object> getWithdrawConfirm(Long id){
		logger.info("==========================getWithdrawConfirm=====================");
		Map<String, Object> withdrawConfirm = new HashMap<String, Object>();
		withdrawConfirm.put("ex_id", id);
		return withdrawConfirm;
	}
}
