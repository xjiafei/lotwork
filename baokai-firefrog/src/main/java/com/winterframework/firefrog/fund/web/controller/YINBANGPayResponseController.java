package com.winterframework.firefrog.fund.web.controller;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.fund.dao.IFundChargeQueueDao;
import com.winterframework.firefrog.fund.dao.vo.FundChargeQueue;
import com.winterframework.firefrog.fund.entity.FundChargeMCOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder.Status;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.enums.FundChargeQueueEnum.QueueStatus;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher;
import com.winterframework.firefrog.fund.service.impl.yinbang.YINBANGPayConfirmRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.util.yinbangpay.Base64Local;
import com.winterframework.firefrog.fund.util.yinbangpay.SecurityRSAPay;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("YINBANGPayResponseController")
@RequestMapping(value = "/fund/yinbangpay/")
public class YINBANGPayResponseController {
	private static Logger logger = LoggerFactory
			.getLogger(YINBANGPayResponseController.class);

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
	
	@PropertyConfig(value = "yinbangpay_public_key")
	private String yinbangpayPublicKey;
	
	@PropertyConfig(value = "yinbangpay_private_key")
	private String yinbangpayPrivateKey;
	@PropertyConfig(value = "yinbangplt_public_key")
	private String yinbangpayPlatformKey;

	/**
	 * 
	 * @Title: chargeConfirm
	 * @Description: 充值确认
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/chargeConfirm")
	@ResponseBody
	public String chargeConfirm(YINBANGPayConfirmRequest chargeConfirmRequest) {
		logger.info("=/fund/yinbangpay/chargeConfirm Start:");
		logger.info("chargeConfirm:"
				+ JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
		
		String status = "failed";
		try {
			FundChargeQueue queueReq = new FundChargeQueue();
			
			long updateNum = 0L;
			FundChargeOrder chargeOrder = fundChargeService.queryById(chargeConfirmRequest.getOrderId());
			FundChargeQueue queueDbOrder = fundChargeQueueDaoImpl.queryByOrderNum(chargeConfirmRequest.getOrderId());
			if(queueDbOrder == null){
				//查無訂單，則新增一筆
				if (!StringUtils.isEmpty(chargeConfirmRequest.getEncParam()) && !StringUtils.isEmpty(chargeConfirmRequest.getSign())) {
					if(SecurityRSAPay.verify(Base64Local.decode(chargeConfirmRequest.getEncParam()),Base64Local.decode(yinbangpayPlatformKey),Base64Local.decode(chargeConfirmRequest.getSign()))){
						String data = new String(SecurityRSAPay.decryptByPrivateKey(Base64Local.decode(chargeConfirmRequest.getEncParam()),Base64Local.decode(yinbangpayPrivateKey)), "utf-8");
						Map<String,String> resultMap = JsonUtil.fromJson2Map(data, String.class,String.class);
						if(resultMap.get("order_state")!=null && resultMap.get("order_state").equals("1003")){
							logger.info("yinbang pay comfirm success.order="+chargeConfirmRequest.getOrderId());
							queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
						}else{
							logger.error("yinbang pay comfirm failed.order="+chargeConfirmRequest.getOrderId()+" orderStatus="+resultMap.get("order_state"));
							queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
						}
					}else{
						logger.error("yinbang pay comfirm verify failed.order="+chargeConfirmRequest.getOrderId()+" encParam="+chargeConfirmRequest.getEncParam()+" sign="+chargeConfirmRequest.getSign());
						queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
					}
				}else{
					logger.error("yinbang pay comfirm params invalid.order="+chargeConfirmRequest.getOrderId()+" encParam="+chargeConfirmRequest.getEncParam()+" sign="+chargeConfirmRequest.getSign());
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				queueReq.setSn(chargeConfirmRequest.getOrderId());
				queueReq.setTime(1L);
				queueReq.setCreatedTime(new Date());
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				queueReq.setUserSn(chargeOrder.getUserAct()+chargeConfirmRequest.getOrderId());
				queueReq.setChargeConfirmTime(0L);
				
				updateNum += fundChargeQueueDaoImpl.addOrderQueue(queueReq);
				
			}else if(queueDbOrder.getStatus().equals(QueueStatus.ORDABNORMAL.getValue())
	                || queueDbOrder.getStatus().equals( QueueStatus.UNTREAT.getValue()))
			{
				fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getOrderId(), QueueStatus.PROCESS.getValue());
				
				
				//開始更新訂單資訊
				if (!StringUtils.isEmpty(chargeConfirmRequest.getEncParam()) && !StringUtils.isEmpty(chargeConfirmRequest.getSign())) {
					if(SecurityRSAPay.verify(Base64Local.decode(chargeConfirmRequest.getEncParam()),Base64Local.decode(yinbangpayPlatformKey),Base64Local.decode(chargeConfirmRequest.getSign()))){
						String data = new String(SecurityRSAPay.decryptByPrivateKey(Base64Local.decode(chargeConfirmRequest.getEncParam()),Base64Local.decode(yinbangpayPrivateKey)), "utf-8");
						Map<String,String> resultMap = JsonUtil.fromJson2Map(data, String.class,String.class);
						if(resultMap.get("order_state")!=null && resultMap.get("order_state").equals("1003")){
							logger.info("yinbang pay comfirm success.order="+chargeConfirmRequest.getOrderId());
							queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
						}else{
							logger.error("yinbang pay comfirm failed.order="+chargeConfirmRequest.getOrderId()+" orderStatus="+resultMap.get("order_state"));
							queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
						}
					}else{
						logger.error("yinbang pay comfirm verify failed.order="+chargeConfirmRequest.getOrderId()+" encParam="+chargeConfirmRequest.getEncParam()+" sign="+chargeConfirmRequest.getSign());
						queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
					}
				}else{
					logger.error("yinbang pay comfirm params invalid.order="+chargeConfirmRequest.getOrderId()+" encParam="+chargeConfirmRequest.getEncParam()+" sign="+chargeConfirmRequest.getSign());
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				
				queueReq.setSn(chargeConfirmRequest.getOrderId());
				queueReq.setTime(queueDbOrder.getTime()+1);
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				
				updateNum += fundChargeQueueDaoImpl.updateOrderQueue(queueReq);
					
			}else {
				logger.info("OrderSn:"+chargeConfirmRequest.getOrderId()+", was already Operated");
			}
			logger.info("Operate OrderSn = "+chargeConfirmRequest.getOrderId()+",updateNum = "+updateNum);
			status="SUCCESS";
			
		}catch(SQLException e){
			logger.error("OrderSn:"+chargeConfirmRequest.getOrderId()+e);
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			long num = 0;
			try {
				num = fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getOrderId(), QueueStatus.UNTREAT.getValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			logger.error("OrderSn:"+chargeConfirmRequest.getOrderId()+", update Num="+num+": "+e);
		}
		
		return status;
	}
}
