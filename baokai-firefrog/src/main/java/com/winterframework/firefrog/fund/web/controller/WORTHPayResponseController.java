package com.winterframework.firefrog.fund.web.controller;


import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.winterframework.firefrog.fund.service.impl.mow.WORTHConfirmRequest;
import com.winterframework.modules.security.MD5Encrypt;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("worthResponseController")
@RequestMapping(value = "/fund/worthpay/")
public class WORTHPayResponseController {
	private static Logger logger = LoggerFactory.getLogger(WORTHPayResponseController.class);
	
	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient mcwClient;
	
	@Resource(name = "fundChargeServiceImpl")
	private IFundChargeService fundChargeService;

	@Resource(name = "fundChargeQueueDaoImpl")
	private IFundChargeQueueDao fundChargeQueueDaoImpl;
	
	@PropertyConfig(value = "worth_merchant_ID")
	private String worthMerchantID;
	
	@PropertyConfig(value = "worth_url")
	private String worthUrl;
	
	@PropertyConfig(value = "worth_verifyCheck_url")
	private String worthVerifyCheckUrl;
	
	@PropertyConfig(value = "worth_md5key")
	private String worthMd5key;
	/**
	 * 
	 * @Title: chargeConfirm
	 * @Description: 充值确认（TH调用）
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/chargeConfirm")
	@ResponseBody
	public String chargeConfirm(WORTHConfirmRequest chargeConfirmRequest) {
		logger.info("=/fund/worthpay/chargeConfirm Start:");
		logger.info("chargeConfirm:"
				+ JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));

		String status = "fail";

		try {
			FundChargeQueue queueReq = new FundChargeQueue();
				
			long updateNum = 0L;
			FundChargeOrder chargeOrder = fundChargeService.queryById(chargeConfirmRequest.getOrder_no());
			FundChargeQueue queueDbOrder = fundChargeQueueDaoImpl.queryByOrderNum(chargeConfirmRequest.getOrder_no());
			if(queueDbOrder == null){
				//查無訂單，則新增一筆
				if("trade_finished".equals(chargeConfirmRequest.getTrade_status().toLowerCase())){
					queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
				}else{
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				
				queueReq.setSn(chargeConfirmRequest.getOrder_no());
				queueReq.setTime(1L);
				queueReq.setCreatedTime(new Date());
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				queueReq.setUserSn(chargeOrder.getUserAct()+chargeConfirmRequest.getOrder_no());
				queueReq.setChargeConfirmTime(0L);
				updateNum += fundChargeQueueDaoImpl.addOrderQueue(queueReq);
			}else if(queueDbOrder.getStatus().equals(QueueStatus.ORDABNORMAL.getValue())
	                || queueDbOrder.getStatus().equals( QueueStatus.UNTREAT.getValue()))
			{
				fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getOrder_no(), QueueStatus.PROCESS.getValue());
				
				//開始更新訂單資訊
				if("trade_finished".equals(chargeConfirmRequest.getTrade_status().toLowerCase())){
					queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
				}else{
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				queueReq.setSn(chargeConfirmRequest.getOrder_no());
				queueReq.setTime(queueDbOrder.getTime()+1);
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				
				updateNum += fundChargeQueueDaoImpl.updateOrderQueue(queueReq);
					
			}else{
				logger.info("OrderSn:"+chargeConfirmRequest.getOrder_no()+", was already Operated");
				}
				logger.info("Operate OrderSn = "+chargeConfirmRequest.getOrder_no()+",updateNum = "+updateNum);
				status="success";
		} catch(SQLException e){
			logger.error("OrderSn:"+chargeConfirmRequest.getOrder_no()+e);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			long num = 0;
			try {
				num = fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getOrder_no(), QueueStatus.UNTREAT.getValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			logger.error("OrderSn:"+chargeConfirmRequest.getOrder_no()+", update Num="+num+": "+e);
		}
		
		return status;
	}
	
}


