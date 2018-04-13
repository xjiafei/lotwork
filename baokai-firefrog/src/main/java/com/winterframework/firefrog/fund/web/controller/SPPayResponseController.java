package com.winterframework.firefrog.fund.web.controller;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.fund.dao.IFundChargeQueueDao;
import com.winterframework.firefrog.fund.dao.vo.FundChargeQueue;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.enums.FundChargeQueueEnum.QueueStatus;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher;
import com.winterframework.firefrog.fund.service.impl.sp.SPPayConfirmRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("SPPayResponseController")
@RequestMapping(value = "/fund/sppay/")
public class SPPayResponseController {
	private static Logger logger = LoggerFactory
			.getLogger(SPPayResponseController.class);

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
	
	@PropertyConfig(value = "hbpay_public_key")
	private String hbpayPublicKey;
	
	@PropertyConfig(value = "hbpay_private_key")
	private String hbpayPrivateKey;

	private static final Logger log = LoggerFactory.getLogger(SPPayResponseController.class);

	/**
	 * 
	 * @Title: chargeConfirm
	 * @Description: 充值确认
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/chargeConfirm")
	@ResponseBody
	public String chargeConfirm(SPPayConfirmRequest chargeConfirmRequest) {
		logger.info("=/fund/sppay/chargeConfirm Start:");
		logger.info("chargeConfirm:"
				+ JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
		
		String status = "failed";
		try {
			FundChargeQueue queueReq = new FundChargeQueue();
			
			long updateNum = 0L;
			FundChargeOrder chargeOrder = fundChargeService.queryById(chargeConfirmRequest.getORDERNO());
			FundChargeQueue queueDbOrder = fundChargeQueueDaoImpl.queryByOrderNum(chargeConfirmRequest.getORDERNO());
			if(queueDbOrder == null){
				//查無訂單，則新增一筆
				if("000000".equals(chargeConfirmRequest.getRECODE())){
					queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
				}else{
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				
				queueReq.setSn(chargeConfirmRequest.getORDERNO());
				queueReq.setTime(1L);
				queueReq.setCreatedTime(new Date());
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				queueReq.setUserSn(chargeOrder.getUserAct()+chargeConfirmRequest.getORDERNO());
				queueReq.setChargeConfirmTime(0L);
				
				updateNum += fundChargeQueueDaoImpl.addOrderQueue(queueReq);
			}else if(queueDbOrder.getStatus().equals(QueueStatus.ORDABNORMAL.getValue())
	                || queueDbOrder.getStatus().equals( QueueStatus.UNTREAT.getValue()))
			{
				fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getORDERNO(), QueueStatus.PROCESS.getValue());
				
				
				//開始更新訂單資訊
				if("000000".equals(chargeConfirmRequest.getRECODE())){
					queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
				}else{
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				
				queueReq.setSn(chargeConfirmRequest.getORDERNO());
				queueReq.setTime(queueDbOrder.getTime()+1);
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				
				updateNum += fundChargeQueueDaoImpl.updateOrderQueue(queueReq);
					
			}else {
				log.info("OrderSn:"+chargeConfirmRequest.getORDERNO()+", was already Operated");
			}
			log.info("Operate OrderSn = "+chargeConfirmRequest.getORDERNO()+",updateNum = "+updateNum);
			status="success";
			
			
			
		}catch(SQLException e){
			log.error("OrderSn:"+chargeConfirmRequest.getORDERNO()+e);
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			long num = 0;
			try {
				num = fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getORDERNO(), QueueStatus.UNTREAT.getValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			log.error("OrderSn:"+chargeConfirmRequest.getORDERNO()+", update Num="+num+": "+e);
		}
		
		return status;
	}
}
