package com.winterframework.firefrog.game.service.revocation.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameReturnPointService;
import com.winterframework.firefrog.game.service.IGameSlipService;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderNewService;
import com.winterframework.firefrog.game.web.dto.LockServiceReqeust;
import com.winterframework.modules.spring.exetend.PropertyConfig;

 
/**
 * 订单撤销基类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月7日
 */
public abstract class AbstractGameRevocationOrderNewService implements IGameRevocationOrderNewService{
	private Logger log = LoggerFactory.getLogger(AbstractGameRevocationOrderNewService.class);

	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	@Resource(name = "gameSlipServiceImpl")
	private IGameSlipService gameSlipService; 
	@Resource(name = "gameReturnPointServiceImpl")
	private IGameReturnPointService gameReturnPointService;  
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;	
	@PropertyConfig(value = "url.business.connect")
	private String serverPath;	
	@PropertyConfig(value = "url.game.nudoLock")
	private String nudoLock;
	
	protected static final int ZERO=0;
	protected static final int ONE=1; 
	
	@Override
	public int doRevocation(GameContext ctx, GameOrder order)
			throws Exception {
		/**
		 * 1.资金处理	
		 * 2.解除封锁变价 
		 * 3.撤销订单辅助信息
		 * 4.反写追号信息：中奖金额---采用紧密业务参数？？？
		 * 5.处理订单
		 */ 
		log.info("开始：撤销订单");
		
		if(order==null) return ZERO; 
		
		log.info("资金处理"); 
		doFund(ctx, order);
		
		log.info("封锁变价"); 
		undoLock(ctx,order);  
		
		log.info("撤销订单辅助信息");
		doOrderAssist(ctx, order);
		
		log.info("反写追号");
		if(order!=null && order.getPlanId()!=null){
			doWriteback(ctx, order);
		}
		
		log.error("处理订单");
		doOrder(ctx, order);
		
		log.info("结束：撤销订单");
		return ONE; 
	} 
	/**
	 * 资金处理（封装资金结构体，不是真的请求资金系统）
	 * @param ctx
	 * @param order
	 * @throws Exception
	 */
	protected void doFund(GameContext ctx,GameOrder order) throws Exception{
		
	};
	/**
	 * 解除封锁变价
	 * @param ctx
	 * @param order
	 * @throws Exception
	 */
	protected void undoLock(GameContext ctx,GameOrder order) throws Exception{ 
		LockServiceReqeust lockServiceReqeust = new LockServiceReqeust();
		lockServiceReqeust.setIssueCode(order.getIssueCode());
		lockServiceReqeust.setLotteryId(order.getLotteryid());
		lockServiceReqeust.setOrderId(order.getId());
		lockServiceReqeust.setUserId(order.getUserid());
		try {
			httpClient.invokeHttpWithoutResultType(serverPath + nudoLock, lockServiceReqeust);
		} catch (Exception e) {
			log.error("请求 nudoLock失败");
		}
	}
	/**
	 * 订单相关处理
	 * @param ctx
	 * @param order
	 * @throws Exception
	 */
	protected void doOrderAssist(GameContext ctx,GameOrder order) throws Exception{
		log.info("撤销注单");
		this.gameSlipService.cancelByOrder(ctx, order);  
		
		log.info("撤销返点"); 
		this.gameReturnPointService.cancel(ctx, order);
	}
	/**
	 * 反写处理
	 * @param ctx
	 * @param order
	 * @throws Exception
	 */
	protected void doWriteback(GameContext ctx,GameOrder order) throws Exception{
		
	}
	/**
	 * 处理订单
	 * @param ctx
	 * @param order
	 * @throws Exception
	 */
	protected void doOrder(GameContext ctx,GameOrder order) throws Exception{
		order.setStatus(GameOrder.Status.CANCEL.getValue()); 
		order.setCancelModes(GameOrder.CancelMode.SYSTEM.getValue());
		order.setCancelTime(DateUtils.currentDate()); 
		this.gameOrderService.save(ctx, order); 	
	}
}
