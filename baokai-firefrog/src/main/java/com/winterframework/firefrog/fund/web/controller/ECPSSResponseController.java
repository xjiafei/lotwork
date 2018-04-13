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
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.fund.dao.IFundChargeQueueDao;
import com.winterframework.firefrog.fund.dao.vo.FundChargeQueue;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.enums.FundChargeQueueEnum.QueueStatus;
import com.winterframework.firefrog.fund.service.IFundChargeService;
import com.winterframework.firefrog.fund.service.IPaymentCallbackDispatcher;
import com.winterframework.firefrog.fund.service.impl.mow.ECPSSConfirmRequest;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("ECPSSResponseController")
@RequestMapping(value = "/fund/ecpss/")
public class ECPSSResponseController {
	private static Logger logger = LoggerFactory
			.getLogger(ECPSSResponseController.class);

	@Resource(name = "fundChargeServiceImpl")
	private IFundChargeService fundChargeService;
	
	@Resource(name = "SNUtil")
	private ISNGenerator snUtil;
	
	@PropertyConfig(value = "ecpss_company_id")
	private String merNo;
	
	@PropertyConfig(value = "ecpss_key")
	private String ecpssKey;
	
	@PropertyConfig(value = "ecpss_query_url")
	private String ecpssQueryUrl;
	
	@PropertyConfig(value = "ecpss_url")
	private String ecpssUrl;
	
	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient mcwClient;

	@Resource(name = "paymentCallbackDispatcher")
	private IPaymentCallbackDispatcher paymentCallbackDispatcher;
	
	@PropertyConfig(value = "unitConvert")
	private Long unitConvert;
	
	@Resource(name = "fundChargeQueueDaoImpl")
	private IFundChargeQueueDao fundChargeQueueDaoImpl;

	private static final Logger log = LoggerFactory.getLogger(ECPSSResponseController.class);

	/**
	 * 
	 * @Title: chargeConfirm
	 * @Description: 充值确认（ECPSS调用）
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/chargeConfirm")
	@ResponseBody
	public String chargeConfirm(ECPSSConfirmRequest chargeConfirmRequest) {
		logger.info("=/fund/ecpss/chargeConfirm Start:");
		logger.info("chargeConfirm:"
				+ JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
		
		String status = "failed";	
		try {
			FundChargeQueue queueReq = new FundChargeQueue();
			
			long updateNum = 0L;
			FundChargeOrder chargeOrder = fundChargeService.queryById(chargeConfirmRequest.getBillNo());
			FundChargeQueue queueDbOrder = fundChargeQueueDaoImpl.queryByOrderNum(chargeConfirmRequest.getBillNo());
			if(queueDbOrder == null){
				//查無訂單，則新增一筆
				if("success".equals(chargeConfirmRequest.getResult().toLowerCase())){
					queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
				}else{
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				
				queueReq.setSn(chargeConfirmRequest.getBillNo());
				queueReq.setTime(1L);
				queueReq.setCreatedTime(new Date());
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				queueReq.setUserSn(chargeOrder.getUserAct()+chargeConfirmRequest.getBillNo());
				queueReq.setChargeConfirmTime(0L);
				updateNum += fundChargeQueueDaoImpl.addOrderQueue(queueReq);
			}else if(queueDbOrder.getStatus().equals(QueueStatus.ORDABNORMAL.getValue())
	                || queueDbOrder.getStatus().equals( QueueStatus.UNTREAT.getValue()))
			{
				fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getBillNo(), QueueStatus.PROCESS.getValue());
				
				
				//開始更新訂單資訊
				if("success".equals(chargeConfirmRequest.getResult().toLowerCase())){
					queueReq.setStatus(QueueStatus.ORDSUCCESS.getValue());
				}else{
					queueReq.setStatus(QueueStatus.ORDABNORMAL.getValue());
				}
				
				queueReq.setSn(chargeConfirmRequest.getBillNo());
				queueReq.setTime(queueDbOrder.getTime()+1);
				queueReq.setUpdateTime(new Date());
				queueReq.setNote(JsonMapper.nonAlwaysMapper().toJson(chargeConfirmRequest));
				
				updateNum += fundChargeQueueDaoImpl.updateOrderQueue(queueReq);
					
			}else {
				log.info("OrderSn:"+chargeConfirmRequest.getBillNo()+", was already Operated");
			}
			log.info("Operate OrderSn = "+chargeConfirmRequest.getBillNo()+",updateNum = "+updateNum);
			status="success";
			
			
			
		} catch(SQLException e){
			log.error("OrderSn:"+chargeConfirmRequest.getBillNo()+e);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			long num = 0;
			try {
				num = fundChargeQueueDaoImpl.updateOrderQueueStatus(chargeConfirmRequest.getBillNo(), QueueStatus.UNTREAT.getValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			log.error("OrderSn:"+chargeConfirmRequest.getBillNo()+", update Num="+num+": "+e);
		}
		return status;
	}
}
