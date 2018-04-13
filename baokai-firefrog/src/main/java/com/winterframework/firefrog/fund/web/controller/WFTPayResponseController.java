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
import com.winterframework.firefrog.fund.service.impl.wft.WFTPayConfirmRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("WFTPayResponseController")
@RequestMapping(value = "/fund/wftpay/")
public class WFTPayResponseController {
	private static Logger logger = LoggerFactory
			.getLogger(WFTPayResponseController.class);

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
	
	
	@PropertyConfig(value = "wftpay_private_key")
	private String wftpayPrivateKey;

	/**
	 * 
	 * @Title: chargeConfirm
	 * @Description: 充值确认
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/chargeConfirm")
	@ResponseBody
	public String chargeConfirm(WFTPayConfirmRequest chargeConfirmRequest) {
		logger.info("=/fund/wftpay/chargeConfirm Start:");
		logger.info("chargeConfirm:"
				+ JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
		
		String status = "failed";
		try {
			FundChargeQueue queueReq = new FundChargeQueue();
			chargeConfirmRequest.setOrdernumber(chargeConfirmRequest.getOrdernumber().substring(0,chargeConfirmRequest.getOrdernumber().indexOf(",")));
			chargeConfirmRequest.setOrderstatus(chargeConfirmRequest.getOrderstatus().substring(0,chargeConfirmRequest.getOrderstatus().indexOf(",")));
			chargeConfirmRequest.setVersion(chargeConfirmRequest.getVersion().substring(0,chargeConfirmRequest.getVersion().indexOf(",")));
			chargeConfirmRequest.setPartner(chargeConfirmRequest.getPartner().substring(0,chargeConfirmRequest.getPartner().indexOf(",")));
			chargeConfirmRequest.setPaymoney(chargeConfirmRequest.getPaymoney().substring(0,chargeConfirmRequest.getPaymoney().indexOf(",")));
			chargeConfirmRequest.setSysnumber(chargeConfirmRequest.getSysnumber().substring(0,chargeConfirmRequest.getSysnumber().indexOf(",")));
			chargeConfirmRequest.setAttach(chargeConfirmRequest.getAttach().substring(0,chargeConfirmRequest.getAttach().indexOf(",")));
			chargeConfirmRequest.setSign(chargeConfirmRequest.getSign().substring(0,chargeConfirmRequest.getSign().indexOf(",")));
			
			long updateNum = 0L;
			FundChargeOrder chargeOrder = fundChargeService.queryById(chargeConfirmRequest.getOrdernumber());
			FundChargeQueue queueDbOrder = fundChargeQueueDaoImpl.queryByOrderNum(chargeConfirmRequest.getOrdernumber());
			if(queueDbOrder == null){
				//查無訂單，則新增一筆
				if("1".equals(chargeConfirmRequest.getOrderstatus())){
					queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
				}else{
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
				if("1".equals(chargeConfirmRequest.getOrderstatus())){
					queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
				}else{
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
