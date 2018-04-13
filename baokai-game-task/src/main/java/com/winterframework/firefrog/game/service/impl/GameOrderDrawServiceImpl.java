package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.exception.GameException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderDrawService;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.service.IGameReturnPointService;
import com.winterframework.firefrog.game.service.IGameSlipService;


/**
 * 开奖服务类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
@Service("gameOrderDrawServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public  class GameOrderDrawServiceImpl implements IGameOrderDrawService {

	private static final Logger log = LoggerFactory.getLogger(GameOrderDrawServiceImpl.class);
 
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService; 
	@Resource(name = "gameSlipServiceImpl")
	private IGameSlipService gameSlipService; 
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService; 
	@Resource(name = "gameOrderWinServiceImpl")
	private IGameOrderWinService gameOrderWinService; 
	@Resource(name = "gameDrawResultServiceImpl")
	protected IGameDrawResultService drawResultService;
	@Resource(name = "gameReturnPointServiceImpl")
	protected IGameReturnPointService gameReturnPointService;
	@Resource(name = "gameOrderFundServcieImpl")
	protected IGameOrderFundService gameOrderFundServcie; 
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService;
	
	@Override
	public int doBusiness(GameContext ctx, GameOrder order) throws Exception {
		/**
		 * 1.订单处理（中奖、不中奖） 
		 * 	1.1循环处理注单--判断是否中奖
		 * 		1.1.1循环处理注单明细--判断是否中奖
		 * 2.返点调用
		 * 3.订单解冻并扣款调用
		 */
		doBefore(ctx,order);
		log.info("订单处理");
		doOrder(ctx,order);
		
		doAfter(ctx,order);
		
		return 1;
	}
	/**
	 * 前处理
	 * @param ctx
	 * @param order
	 * @return
	 * @throws Exception
	 */
	protected int doBefore(GameContext ctx,GameOrder order) throws Exception{
		return 0;
	}
	protected int doOrder(GameContext ctx,GameOrder order) throws Exception{
		/**
		 * 1.调用注单开奖行为
		 * 2.判断订单是否中奖，调用订单中奖不中奖业务
		 */
		//用户主动操作的不参与开奖：撤销等	
		if(order.getStatus().intValue()==GameOrder.Status.CANCEL.getValue() && order.getCancelModes()==1L) return 0;
		 
		List<GameSlip> slipList=this.gameSlipService.getByOrderId(ctx, order.getId());
		if(slipList!=null && slipList.size()>0){
			boolean isWin=false;
			Long winAmount=0L;	//中奖总金额
			int continueWinCount=0;	//连续中奖次数
			for(GameSlip slip:slipList){
				if(this.gameSlipService.draw(ctx, order, slip)){
					isWin=true;
					winAmount+=slip.getEvaluateWin();
					continueWinCount++;
				}
			} 
			if(isWin){
				//先设置上下文变量
				ctx.set("winAmount",winAmount); 
				//中奖逻辑调用
				this.win(ctx, order);
				//设置上下文中奖订单集合
				List<GameOrder> winOrderList=(List<GameOrder>)ctx.get("winOrderList");
				Set<Long> winUserIdSet=(Set<Long>)ctx.get("winUserIdSet");
				if(winOrderList==null){
					winOrderList=new ArrayList<GameOrder>();
				}
				if(winUserIdSet==null){
					winUserIdSet=new HashSet<Long>();
				}
				winOrderList.add(order); 
				winUserIdSet.add(order.getUserid()); 
			}else{
				this.unwin(ctx,order);
			}
		}else{
			throw new GameException("Game slip not found by order id.");
		}
		return 1;
	}
	@Override
	public boolean isWin(GameContext ctx, GameOrder order) throws Exception {
		log.info("开始：判断订单是否中奖");
		Long lotteryId=order.getLotteryid();
		Long issueCode=order.getIssueCode();
		Long orderId=order.getId();
		
		log.info("开奖号码没有则获取开奖号码（开奖调用时肯定有值）");
		String winNumber=(String)ctx.get("winNumber");
		if(winNumber==null){
			winNumber=drawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
			ctx.set("winNumber",winNumber);
		}
		log.info("获取注单内容");
		List<GameSlip> slipList = gameSlipService.getByOrderId(ctx, orderId); 
		if(slipList!=null && slipList.size()>0){ 
			boolean isWin=false;
			for(GameSlip slip:slipList){ 
				if(this.gameSlipService.isWin(ctx,order,slip)){
					isWin=true;
					break;
				}
			}   
			return isWin;
		}else{
			String msg="订单注单内容不存在（orderId："+orderId+")";
			log.error(msg);
			throw new GameException(1001L,msg);
		}  
	}
	/**
	 * 后处理
	 * @param ctx
	 * @param order
	 * @return
	 * @throws Exception
	 */
	protected int doAfter(GameContext ctx,GameOrder order) throws Exception{
		if(order==null) return 0;
		log.info("返点调用");
		GameRetPoint retPoint=this.gameReturnPointService.getByOrderId(ctx, order.getId());
		this.gameReturnPointService.distribute(ctx, order, retPoint);
		
		log.info("订单解冻并扣款调用"); 
		this.gameOrderFundServcie.pay(ctx, order);
		return 1;
	}
	private int win(GameContext ctx, GameOrder order) throws Exception {
		/**无需更新订单、无需资金调用？？？
		 * 1.中奖订单中奖行为调用 
		 * 2.如果追号，反写追号中奖信息
		 * 3.更新订单信息
		 */ 
		
		log.info("中奖订单中奖行为调用");
		GameOrderWin orderWin=this.gameOrderWinService.getByOrderId(ctx, order.getId());
		//首次生成中奖订单，orderWin为空 
		this.gameOrderWinService.win(ctx, order, orderWin);  
		
		log.info("如果追号，反写追号中奖信息");
		if(order.getPlanId()!=null){
			GamePlan plan=this.gamePlanService.getById(order.getPlanId());
			log.error("planId="+plan.getId() + plan.getWinAmount());
			plan.setWinAmount(plan.getWinAmount()+orderWin.getCountWin());
			log.error("planId="+plan.getId() + plan.getWinAmount());
			this.gamePlanService.save(ctx, plan);
		} 
		
		log.info("更新订单中奖信息");
		order.setStatus(GameOrder.Status.WIN.getValue());
		order.setCalculateWinTime(DateUtils.currentDate());
		order.setCancelTime(null);
		order.setCancelModes(0); 
		this.gameOrderService.save(ctx,order); 
		return 1;
	} 
	private int unwin(GameContext ctx, GameOrder order) throws Exception {
		/**
		 * 需要更新订单、需要资金调用
		 * 1.不中奖订单 
		 * 2.更新订单信息 
		 */ 		
		log.info("中奖订单不中奖行为调用");
		GameOrderWin orderWin=this.gameOrderWinService.getByOrderId(ctx, order.getId());
		if(orderWin!=null){
			this.gameOrderWinService.unwin(ctx, order, orderWin);
		} 
		
		log.info("更新订单不中奖信息");
		order.setStatus(GameOrder.Status.UN_WIN.getValue());
		order.setCalculateWinTime(null);
		order.setCancelTime(null);
		order.setCancelModes(0); 
		this.gameOrderService.save(ctx,order); 
		return 1;
	}  
	
}
