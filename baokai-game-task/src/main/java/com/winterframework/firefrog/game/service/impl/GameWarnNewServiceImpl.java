package com.winterframework.firefrog.game.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameOrderWinService;
import com.winterframework.firefrog.game.service.IGameReturnPointService;
import com.winterframework.firefrog.game.service.IGameRiskConfigService;
import com.winterframework.firefrog.game.service.IGameWarnNewService;
import com.winterframework.firefrog.game.service.IGameWarnOrderService;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.IndexLottery;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.web.util.JsonMapper;

 
/**
 * 风险审核服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月13日
 */
@Service("gameWarnNewServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameWarnNewServiceImpl implements IGameWarnNewService {

	private static final Logger log = LoggerFactory.getLogger(GameWarnNewServiceImpl.class);
	@Resource(name = "gameOrderWinServiceImpl")
	private IGameOrderWinService gameOrderWinService;
	@Resource(name = "gameRiskConfigServiceImpl")
	private IGameRiskConfigService gameRiskConfigService;
	@Resource(name = "gameWarnOrderServiceImpl")
	private IGameWarnOrderService gameWarnOrderService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService;
	@Resource(name = "gameReturnPointServiceImpl")
	private IGameReturnPointService gameReturnPointService;
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundServcie; 
	@Resource(name = "RedisService")
	private RedisService redisService;
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;
	 

	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();

	public int doBusiness(GameContext ctx,GameOrder order) throws Exception {
		if(order==null) return 0; 
		GameRiskConfig riskConfig=this.getGameRiskConfig(order.getLotteryid());
		if(riskConfig!=null){			
			Long configMaxSlipWinAmount=riskConfig.getOrderwarnMaxslipWins();
			Long configMaxSlipWinRatio=riskConfig.getOrderwarnSlipWinsratio(); 
			GameOrderWin orderWin=this.gameOrderWinService.getByOrderId(ctx, order.getId()); 
			//单注最大中奖金额、单注最大中投比
			Long maxSlipWinAmount=orderWin.getMaxslipWins();
			Long maxSlipWinRatio=orderWin.getSlipWinsratio(); 
			if(this.isWarn(configMaxSlipWinAmount, maxSlipWinAmount) || this.isWarn(configMaxSlipWinRatio, maxSlipWinRatio)){
				doWarnOrder(ctx,order,orderWin);				
			}else{
				doNormalOrder(ctx,order,orderWin);
			}
			
		}  
		return 1;
	}

	private boolean isWarn(Long configValue,Long value){
		return value>configValue;
	}
	/**
	 * 处理中奖风险订单
	 * @param ctx
	 * @param order
	 * @param orderWin
	 * @throws Exception
	 */
	private void doWarnOrder(GameContext ctx,GameOrder order,GameOrderWin orderWin) throws Exception{
		/**
		 * 风险订单
		 * 1.生成风险订单 
		 * 2.异常订单
		 * 3.暂停后续追号??????????????
		 */ 
		GameWarnOrder warnOrder=convertGameOrder2WarnOrder(order, orderWin); 
		this.gameWarnOrderService.save(ctx, warnOrder); 
		this.gameOrderService.excep(ctx, order);
		
		log.info("暂停后续追号");   
		this.gamePlanService.pause(ctx, order.getPlanId()); 
	}
	/**
	 * 处理中奖正常订单
	 * @param ctx
	 * @param order
	 * @param orderWin
	 * @throws Exception
	 */
	private void doNormalOrder(GameContext ctx,GameOrder order,GameOrderWin orderWin) throws Exception{
		/**
		 * 通过审核的订单
		 * 1.资金中奖订单派奖
		 * 2.资金返点派奖
		 * 3.资金投注解冻并扣款
		 * 4.更新订单中奖？？？？ 
		 * 5.更新首页缓存？？
		 */
		this.gameOrderWinService.distribute(ctx, order);
		this.gameReturnPointService.distribute(ctx, order);
		this.gameOrderFundServcie.pay(ctx, order); 
		//更新订单中奖？？？？ 
		//????????
		
		//更新首页缓存？？
		updateHomePageCache(order,orderWin); 
	}

	private GameWarnOrder convertGameOrder2WarnOrder(GameOrder order,
			GameOrderWin orderWin) {
		GameWarnOrder warnOrder = new GameWarnOrder();
		warnOrder.setOrderId(order.getId()); 
		warnOrder.setIssueCode(order.getIssueCode());
		warnOrder.setLotteryid(order.getLotteryid());
		warnOrder.setOrderCode(order.getOrderCode());
		warnOrder.setParentType(order.getPlanId()==null?1L:2L); 
		warnOrder.setSaleTime(order.getSaleTime()); 
		warnOrder.setType(1L); //风险订单 
		warnOrder.setStatus(0L);//待审核
		warnOrder.setChannelId(1L);	//终端类型： 1 WEB 2 IOS 3 IPAD 4 android 5 WAC
		warnOrder.setWebIssueCode(order.getWebIssueCode());
		warnOrder.setTotamount(order.getTotamount());  
		warnOrder.setCountWin(orderWin.getCountWin());
		warnOrder.setWinsRatio(orderWin.getWinsRatio());
		warnOrder.setSlipWinsratio(orderWin.getSlipWinsratio());
		warnOrder.setMaxslipWins(orderWin.getMaxslipWins());
		warnOrder.setUserid(order.getUserid());
		warnOrder.setCreateTime(new Date());
		return warnOrder;
	} 
 
	/**
	 * 获取审核配置
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	private GameRiskConfig getGameRiskConfig(Long lotteryId) throws Exception {
		GameRiskConfig config = this.gameRiskConfigService.getGameRiskConfig(lotteryId);
		if(config==null){
			log.warn("获取审核配置为空");
		}
		return config;
	} 
	  
	@SuppressWarnings("unchecked")
	public void updateHomePageCache(GameOrder order,GameOrderWin orderWin) {
		log.info("风控模块：update firefrog_index_lastdata_");
		try {
			if (order.getStatus().intValue() == 5 || order.getTotalWin() == null || orderWin.getCountWin() < 10000000L) {
				return;
			}

			Long lotteryId=order.getLotteryid();
			IndexLottery indexLottery = redisService.getHome(lotteryId);
			 
			Map<String, String> wins = indexLottery.getWins();
			LRUMap winsLRUMap = new LRUMap(120);
			if (wins != null) {
				winsLRUMap.putAll(wins);
			}

			log.debug("风控模块： new User()");
			User user = new User();
			try {
				user = userCustomerDao.queryUserById(order.getUserid());
			} catch (Exception e) {
				log.error(" queryUserById error: userId=" + order.getUserid(), e);
			}
			if (user != null && user.getUserProfile() != null && user.getUserProfile().getAccount() != null
					&& !user.getUserProfile().getAccount().equals("")) {
				log.debug("风控模块：wins.put");
				winsLRUMap.put(user.getUserProfile().getAccount(), order.getTotalWin() + "");
			}

			indexLottery.setWins(winsLRUMap);
			redisService.setHome(lotteryId,indexLottery); 
		} catch (Exception e) {
			log.error("updateIssuseRedisCache fail", e);
		}
	}
}
