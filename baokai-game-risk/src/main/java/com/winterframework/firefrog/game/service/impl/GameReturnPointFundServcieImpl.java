package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameTransactionFundDao;
import com.winterframework.firefrog.game.dao.vo.FundGameVos;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName GameReturnPointFundServcieImpl 
* @Description 返点资金操作类 
* @author  hugh
* @date 2014年4月22日 下午2:46:23 
*  
*/
@Service("gameReturnPointFundServcieImpl")
@Transactional
public class GameReturnPointFundServcieImpl implements IGameReturnPointFundService {

	private Logger log = LoggerFactory.getLogger(GameReturnPointFundServcieImpl.class);

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDaoImpl;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "fund.action")
	private String fundActionUrl;

	@PropertyConfig(value = "fund.actions")
	private String fundActionsUrl;

	@PropertyConfig(value = "url.connect.fund")
	private String baseFundUrl;

	@Resource(name = "gameTransactionFundDaoImpl")
	private IGameTransactionFundDao gameTransactionFundDao;

	/** 
	* @Title: returnPointFundUpdateRetsStatus 
	* @Description: 派发资金返点  并更新返点表 
	* @param orders
	* @return
	* @throws GameRiskException
	*/
	public boolean returnPointFundUpdateRetsStatus(Map<Long, GameOrder> orders) throws GameRiskException{
			
		return returnPointFundUpdateRetsStatus(orders, getReturnPoints(orders));
	}
	
	/** 
	* @Title: returnPointFreeze 
	* @Description: 冻结资金返点  并更新返点表 
	* @param orderList
	* @return
	*/
	public boolean returnPointFreeze(Map<Long, GameOrder> orders){
		for (Long orderId : orders.keySet()) {
			GameRetPoint ret = gameReturnPointDaoImpl.getGameRetPointByGameOrderId(orderId);
			ret.setStatus(3);
			gameReturnPointDaoImpl.update(ret);
		}	
		return true;
	}
	
	/** 
	* @Title: returnPointCancel 
	* @Description: 撤销资金返点  并更新返点表 
	* @param orderList
	* @return
	*/
	public boolean returnPointCancel(List<GameOrder> orderList){
		for (GameOrder gameOrder : orderList) {
			GameRetPoint ret = gameReturnPointDaoImpl.getGameRetPointByGameOrderId(gameOrder.getId());
			ret.setStatus(4);
			gameReturnPointDaoImpl.update(ret);
		}	
		return true;
	}
	/**
	* @Title: returnPointFund
	* @Description:资金返点
	* @param orderIds
	* @return
	* @throws GameRiskException 
	* @see com.winterframework.firefrog.game.service.IGameReturnPointFundService#returnPointFund(java.util.List) 
	*/
	@Override
	public boolean returnPointFund(List<Long> orderIds) throws GameRiskException {
		List<FundGameVos> list = new ArrayList<FundGameVos>();
		/* /*  無使用功能 先不insert db
		List<GameTransactionFund> trans = new ArrayList<GameTransactionFund>();
*/
		for (Long orderId : orderIds) {
			GameRetPoint ret = gameReturnPointDaoImpl.getGameRetPointByGameOrderId(orderId);

			String[] users = StringUtils.split(ret.getRetUserChain(), SEPARATOR);
			String[] amounts = StringUtils.split(ret.getRetPointChain(), SEPARATOR);

			for (int i = 0; i < users.length; i++) {

				list.add(packageFundGameVos(users[i], amounts[i], users[users.length - 1]));
/* /*  無使用功能 先不insert db
				trans.add(packageGameTransactionFund(gameOrderDaoImpl.getById(orderId), Long.parseLong(users[i]),
						Long.parseLong(amounts[i])));
*/
			}

			log.info("审核中心开始请求资金系统");
			try {
				httpClient.invokeHttpWithoutResultType(baseFundUrl + fundActionsUrl, list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/*  無使用功能 先不insert db
			for (GameTransactionFund gameTransactionFund : trans) {
				gameTransactionFund.setStatus(1L);//1发送成功2发送失败
				gameTransactionFundDao.insert(gameTransactionFund);
			}
			*/
		}

		return false;
	}

	/**
	* @Title: returnPointFund
	* @Description:资金返点
	* @param orders
	* @param rets
	* @return
	* @throws GameRiskException 
	* @see com.winterframework.firefrog.game.service.IGameReturnPointFundService#returnPointFund(java.util.Map, java.util.Map) 
	*/
	public boolean returnPointFund(Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException {
		List<FundGameVos> list = new ArrayList<FundGameVos>();
	//	List<GameTransactionFund> trans = new ArrayList<GameTransactionFund>();
		Long status = 1L;
		
		for (Long orderId : orders.keySet()) {
			GameRetPoint ret = rets.get(orderId);
			String[] users = StringUtils.split(ret.getRetUserChain(), SEPARATOR);
			String[] amounts = StringUtils.split(ret.getRetPointChain(), SEPARATOR);

			for (int i = 0; i < users.length; i++) {

				list.add(packageFundGameVos(users[i], amounts[i], users[users.length - 1]));
		//		trans.add(packageGameTransactionFund(orders.get(orderId), Long.parseLong(users[i]),
		//				Long.parseLong(amounts[i])));
			}

			log.info("审核中心开始请求资金系统");
			try {
				httpClient.invokeHttpWithoutResultType(baseFundUrl + fundActionsUrl, list);
			} catch (Exception e) {
				log.error("调用资金接口失败:" + baseFundUrl + fundActionsUrl,e);
				status = 2L;
			}

		}
/* /*  無使用功能 先不insert db
		for (GameTransactionFund gameTransactionFund : trans) {
			gameTransactionFund.setStatus(status);//1发送成功2发送失败
			gameTransactionFundDao.insert(gameTransactionFund);
		}
*/
		return true;
	}

	public boolean returnPointFundUpdateRetsStatus(Map<Long, GameOrder> orders ,Map<Long, GameRetPoint> rets) throws GameRiskException{
		if(returnPointFund( orders , rets)){
			for (Long orderId : rets.keySet()) {
				GameRetPoint ret = rets.get(orderId);
				ret.setStatus(2);
				gameReturnPointDaoImpl.update(ret);
			}			
		}
		return true;
	}
	

	
	/** 
	* @Title: getReturnPoints 
	* @Description: 获取返点记录
	* @param orders
	* @return
	* @throws GameRiskException
	*/
	public Map<Long, GameRetPoint> getReturnPoints(Map<Long, GameOrder> orders) throws GameRiskException{
		
		Map<Long, GameRetPoint> rets = new HashMap<Long, GameRetPoint>();
		for (Long orderId : orders.keySet()) {
			rets.put(orderId, gameReturnPointDaoImpl.getGameRetPointByGameOrderId(orderId));
		}
		return rets;
	}
	
	
	
//	@Override
//	public boolean returnPointFund(Long lotteryId, Long issueCode) throws GameRiskException {
//		List<GameRetPoint> rets = gameReturnPointDaoImpl.getGameRetPointByIssueCode(lotteryId, issueCode);
//		return false;
//	}

	/** 
	* @Title: packageFundGameVos 
	* @Description: 封装资金交易请求实体
	* @param user
	* @param amount
	* @param selfUser
	* @return
	*/
	private FundGameVos packageFundGameVos(String user, String amount, String selfUser) {
		FundGameVos fundGameVos = new FundGameVos();
		fundGameVos.setIsAclUser(1L);
		fundGameVos.setOperator(-1L);
		//GM-RTRX-1 自己， 2 为上级。
		//users 最后一个用户为订单的拥有者
		fundGameVos.setReason(selfUser.equals(user) ? "GM-RTRX-1" : "GM-RTRX-2");
		fundGameVos.setVals(user + "|" + amount);
		return fundGameVos;
	}

	/** 
	* @Title: packageGameTransactionFund 
	* @Description: 封装保存的交易记录
	* @param order
	* @param operatorId
	* @param amount
	* @return
	*/
	private GameTransactionFund packageGameTransactionFund(GameOrder order, Long operatorId, Long amount) {
		GameTransactionFund entity = new GameTransactionFund();
		entity.setAmount(amount);
		entity.setCheckType(2L);// 1 投注冻结 2 派发奖金 3 投注扣款 4 撤销扣款"
		entity.setCreateTime(new Date());
		entity.setCreator(1L);//系统默认1
		entity.setFundType("派发奖金");
		entity.setIssueCode(order.getIssueCode());
		entity.setLotteryId(order.getLotteryid());
		entity.setOperatorid(operatorId);//"资金变更用户id";
		entity.setOrderCode(order.getOrderCode());
		entity.setPlanCode("");
		entity.setSymbol("+");
		entity.setTid("");
		entity.setUpdateTime(new Date());
		entity.setUserid(1L); //"执行用户ID 系统默认1";
		entity.setWebIssueCode(order.getWebIssueCode());
		return entity;
	}

	private static final String SEPARATOR = ",";
}
