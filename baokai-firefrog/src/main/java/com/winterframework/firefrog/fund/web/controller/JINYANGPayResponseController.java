package com.winterframework.firefrog.fund.web.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.fund.dao.IFundChargeQueueDao;
import com.winterframework.firefrog.fund.dao.vo.FundChargeQueue;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.enums.FundChargeQueueEnum.QueueStatus;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher;
import com.winterframework.firefrog.fund.service.impl.jinyang.JINYANGPayConfirmRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.modules.security.MD5Encrypt;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("JINYANGPayResponseController")
@RequestMapping(value = "/fund/jinyangpay/")
public class JINYANGPayResponseController {
	private static Logger logger = LoggerFactory
			.getLogger(JINYANGPayResponseController.class);

	@Resource(name = "fundChargeServiceImpl")
	private IFundChargeService fundChargeService;
	
	@Resource(name = "SNUtil")
	private ISNGenerator snUtil;
	
	
	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient mcwClient;

	@Resource(name = "paymentCallbackDispatcher")
	private IPaymentCallbackDispatcher paymentCallbackDispatcher;
	
	@PropertyConfig(value = "unitConvert")
	private Long unitConvert;
	
	@Resource(name = "fundChargeQueueDaoImpl")
	private IFundChargeQueueDao fundChargeQueueDaoImpl;
	
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
	 * @Title: chargeConfirm
	 * @Description: 充值确认
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/chargeConfirm")
	@ResponseBody
	public String chargeConfirm(JINYANGPayConfirmRequest chargeConfirmRequest) {
		chargeConfirmRequest.setOrdernumber(chargeConfirmRequest.getOrdernumber().substring(0,chargeConfirmRequest.getOrdernumber().indexOf(",")));
		chargeConfirmRequest.setOrderstatus(chargeConfirmRequest.getOrderstatus().substring(0,chargeConfirmRequest.getOrderstatus().indexOf(",")));
		chargeConfirmRequest.setPartner(chargeConfirmRequest.getPartner().substring(0,chargeConfirmRequest.getPartner().indexOf(",")));
		chargeConfirmRequest.setPaymoney(chargeConfirmRequest.getPaymoney().substring(0,chargeConfirmRequest.getPaymoney().indexOf(",")));
		chargeConfirmRequest.setSysnumber(chargeConfirmRequest.getSysnumber().substring(0,chargeConfirmRequest.getSysnumber().indexOf(",")));
		chargeConfirmRequest.setAttach(chargeConfirmRequest.getAttach().substring(0,chargeConfirmRequest.getAttach().indexOf(",")));
		chargeConfirmRequest.setSign(chargeConfirmRequest.getSign().substring(0,chargeConfirmRequest.getSign().indexOf(",")));
		
		
		logger.info("=/fund/jinyangpay/chargeConfirm Start:");
		logger.info("chargeConfirm:"
				+ JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
		
		String status = "failed";
		try {
			FundChargeQueue queueReq = new FundChargeQueue();
			
			long updateNum = 0L;
			FundChargeOrder chargeOrder = fundChargeService.queryById(chargeConfirmRequest.getOrdernumber());
			FundChargeQueue queueDbOrder = fundChargeQueueDaoImpl.queryByOrderNum(chargeConfirmRequest.getOrdernumber());
			if(queueDbOrder == null){
				//查無訂單，則新增一筆
				if (!StringUtils.isEmpty(chargeConfirmRequest.getOrderstatus()) && !StringUtils.isEmpty(chargeConfirmRequest.getSign())) {
					if(chargeConfirmRequest.getOrderstatus().equals("1")){
						String sign=chargeConfirmRequest.getSign();
						
						String partner=chargeConfirmRequest.getPartner();
						String ordernumber=chargeConfirmRequest.getOrdernumber();
						String orderstatus=chargeConfirmRequest.getOrderstatus();
						String paymoney=chargeConfirmRequest.getPaymoney();
						
						StringBuffer sb=new StringBuffer();
						sb.append("partner=").append(partner)
						.append("&ordernumber=").append(ordernumber)
						.append("&orderstatus=").append(orderstatus)
						.append("&paymoney=").append(paymoney)
						.append(jinyangpayKey);
						
						String sign2=MD5Encrypt.encrypt(sb.toString());
						
						if(sign.equals(sign2)){
							logger.info("jinyang pay comfirm success.order="+chargeConfirmRequest.getOrdernumber());
							queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
						}else{
							logger.error("jinyang pay comfirm verify failed.order="+chargeConfirmRequest.getOrdernumber()+" sign="+sign+" sign2="+sign2+" params="+sb.toString());
							queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
						}
					}else{
						logger.error("jinyang pay comfirm failed.order="+chargeConfirmRequest.getOrdernumber()+" orderstatus="+chargeConfirmRequest.getOrderstatus()+" params="+JsonUtil.toJson(chargeConfirmRequest));
						queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
					}
				}else{
					logger.error("jinyang pay comfirm params invalid.order="+chargeConfirmRequest.getOrdernumber()+" params="+JsonUtil.toJson(chargeConfirmRequest));
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				queueReq.setSn(chargeConfirmRequest.getOrdernumber());
				queueReq.setTime(1L);
				queueReq.setCreatedTime(new Date());
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				queueReq.setUserSn(chargeOrder.getUserAct()+chargeConfirmRequest.getOrdernumber());
				queueReq.setChargeConfirmTime(0L);
				
				updateNum += fundChargeQueueDaoImpl.addOrderQueue(queueReq);
				
			}else if(queueDbOrder.getStatus().equals(QueueStatus.ORDABNORMAL.getValue())
	                || queueDbOrder.getStatus().equals( QueueStatus.UNTREAT.getValue()))
			{
				fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getOrdernumber(), QueueStatus.PROCESS.getValue());
				
				
				//開始更新訂單資訊
				if (!StringUtils.isEmpty(chargeConfirmRequest.getOrderstatus()) && !StringUtils.isEmpty(chargeConfirmRequest.getSign())) {
					if(chargeConfirmRequest.getOrderstatus().equals("1")){
						String sign=chargeConfirmRequest.getSign();
						
						String partner=chargeConfirmRequest.getPartner();
						String ordernumber=chargeConfirmRequest.getOrdernumber();
						String orderstatus=chargeConfirmRequest.getOrderstatus();
						String paymoney=chargeConfirmRequest.getPaymoney();
						
						StringBuffer sb=new StringBuffer();
						sb.append("partner=").append(partner)
						.append("&ordernumber=").append(ordernumber)
						.append("&orderstatus=").append(orderstatus)
						.append("&paymoney=").append(paymoney)
						.append(jinyangpayKey);
						
						String sign2=MD5Encrypt.encrypt(sb.toString());
						
						if(sign.equals(sign2)){
							logger.info("jinyang pay comfirm success.order="+chargeConfirmRequest.getOrdernumber());
							queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
						}else{
							logger.error("jinyang pay comfirm verify failed.order="+chargeConfirmRequest.getOrdernumber()+" sign="+sign+" sign2="+sign2+" params="+sb.toString());
							queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
						}
					}else{
						logger.error("jinyang pay comfirm failed.order="+chargeConfirmRequest.getOrdernumber()+" orderstatus="+chargeConfirmRequest.getOrderstatus()+" params="+JsonUtil.toJson(chargeConfirmRequest));
						queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
					}
				}else{
					logger.error("jinyang pay comfirm params invalid.order="+chargeConfirmRequest.getOrdernumber()+" params="+JsonUtil.toJson(chargeConfirmRequest));
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				
				queueReq.setSn(chargeConfirmRequest.getOrdernumber());
				queueReq.setTime(queueDbOrder.getTime()+1);
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				
				updateNum += fundChargeQueueDaoImpl.updateOrderQueue(queueReq);
					
			}else {
				logger.info("OrderSn:"+chargeConfirmRequest.getOrdernumber()+", was already Operated");
			}
			logger.info("Operate OrderSn = "+chargeConfirmRequest.getOrdernumber()+",updateNum = "+updateNum);
			status="ok";
			
		}catch(SQLException e){
			logger.error("OrderSn:"+chargeConfirmRequest.getOrdernumber()+e);
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			long num = 0;
			try {
				num = fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getOrdernumber(), QueueStatus.UNTREAT.getValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			logger.error("OrderSn:"+chargeConfirmRequest.getOrdernumber()+", update Num="+num+": "+e);
		}
		
		return status;
	}
}
