package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderWinDao;
import com.winterframework.firefrog.game.dao.IGameTransactionFundDao;
import com.winterframework.firefrog.game.dao.vo.FundGameVos;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName GameOrderWinFundServcieImpl 
* @Description 派奖操作 
* @author  hugh
* @date 2014年4月22日 下午2:27:12 
*  
*/
@Service("gameOrderWinFundServcieImpl")
@Transactional
public class GameOrderWinFundServcieImpl implements IGameOrderWinFundService{

	private Logger log = LoggerFactory.getLogger(GameOrderWinFundServcieImpl.class);
	
	@Resource(name = "gameOrderWinDaoImpl")
	private IGameOrderWinDao gameOrderWinDaoImpl;
	
	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value="fund.action")
	private String fundActionUrl;
	
	@PropertyConfig(value="fund.actions")
	private String fundActionsUrl;
	
	@PropertyConfig(value = "url.connect.fund")
	private String baseFundUrl;
	

	@Resource(name = "gameTransactionFundDaoImpl")
	private IGameTransactionFundDao gameTransactionFundDao;
	
	/**
	* @Title: distributeFund
	* @Description:派奖 (1个单)
	* @param orderWin
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameOrderWinFundService#distributeFund(com.winterframework.firefrog.game.dao.vo.GameOrder, com.winterframework.firefrog.game.dao.vo.GameOrderWin) 
	*/
	@Override
	public boolean orderWinFund(GameOrderWin orderWin)  throws GameRiskException{		
		return orderWinFund(gameOrderDaoImpl.getById(orderWin.getOrderid()), orderWin);
	}

	/**
	* @Title: distributeFund
	* @Description:派奖 (1个单)
	* @param order
	* @param orderWin
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameOrderWinFundService#distributeFund(com.winterframework.firefrog.game.dao.vo.GameOrder, com.winterframework.firefrog.game.dao.vo.GameOrderWin) 
	*/
	@Override
	public boolean orderWinFund(GameOrder order, GameOrderWin orderWin) throws GameRiskException{
		
		List<FundGameVos> list = new ArrayList<FundGameVos>();
		log.info("派奖, 用户  ="+ orderWin.getUserid() + ", 资金="+orderWin.getCountWin());
		list.add(packageFundGameVos(orderWin));
		
		log.info("审核中心开始请求资金系统");		
		try {
			httpClient.invokeHttpWithoutResultType(baseFundUrl + fundActionsUrl, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/*	 未使用功能暫時不insert 
		GameTransactionFund entity = packageGameTransactionFund(order, orderWin);		
		entity.setStatus(1L);//1发送成功2发送失败
		gameTransactionFundDao.insert(entity );
		*/
		return true;
	}

	
	/**
	* @Title: distributeFund
	* @Description:派奖 (多单)
	* @param order
	* @param orderWin
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameOrderWinFundService#distributeFund(com.winterframework.firefrog.game.dao.vo.GameOrder, com.winterframework.firefrog.game.dao.vo.GameOrderWin) 
	*/
	@Override
	public boolean orderWinFund(Map<Long,GameOrder> orders, List<GameOrderWin> orderWins) throws GameRiskException{
		
		List<FundGameVos> list = new ArrayList<FundGameVos>();
		
		// 未使用功能暫時不寫入
		//List<GameTransactionFund> trans = new ArrayList<GameTransactionFund>();
		
		// 未使用功能暫時不寫入 Long status = 1L;
		for (GameOrderWin orderWin : orderWins) {
			GameOrder order = orders.get(orderWin.getOrderid());
	
			list.add(packageFundGameVos(orderWin));			
		//	trans.add(packageGameTransactionFund(order, orderWin));
		}
		
		log.info("审核中心开始请求资金系统");		
		try {
			httpClient.invokeHttpWithoutResultType(baseFundUrl + fundActionsUrl, list);
		} catch (Exception e) {
			log.error("调用资金接口失败:" + baseFundUrl + fundActionsUrl,e);
		//	 未使用功能暫時不寫入 status = 2L;
		}
		/* 未使用功能暫時不寫入
		for (GameTransactionFund gameTransactionFund : trans) {
			gameTransactionFund.setStatus(status);//1发送成功2发送失败
			gameTransactionFundDao.insert(gameTransactionFund );
		}
		*/
		
		return true;
	}
	
	/** 
	* @Title: orderWinFundUpdateWinsStatus 
	* @Description: 中奖派奖 并更新中奖表
	* @param orders
	* @param orderWins
	* @return
	* @throws GameRiskException
	*/
	public boolean orderWinFundUpdateWinsStatus(Map<Long,GameOrder> orders, List<GameOrderWin> orderWins)throws GameRiskException{
		if(orderWinFund(orders, orderWins)){
			for (GameOrderWin gameOrderWin : orderWins) {
				gameOrderWin.setStatus(1L);
				gameOrderWinDaoImpl.update(gameOrderWin);
			}
		}
		return true;
	}
	
	/**
	* @Title: orderWinFreeze
	* @Description:冻结中奖订单
	* @param orderWins
	* @return
	* @throws GameRiskException 
	* @see com.winterframework.firefrog.game.service.IGameOrderWinFundService#orderWinFreeze(java.util.List) 
	*/
	public boolean orderWinFreeze (List<GameOrderWin> orderWins)throws GameRiskException{
		for (GameOrderWin gameOrderWin : orderWins) {
			gameOrderWin.setStatus(2L);
			gameOrderWinDaoImpl.update(gameOrderWin);
		}
		return true;		
	}
	
	private FundGameVos packageFundGameVos(GameOrderWin orderWin){
		FundGameVos fundGameVos = new FundGameVos();
		fundGameVos.setIsAclUser(1L);
		fundGameVos.setOperator(-1L);
		fundGameVos.setReason(GameFundTypesUtils.getFundDigest(GameFundTypesUtils.GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE));
		fundGameVos.setVals(orderWin.getUserid() + "|"+orderWin.getCountWin()); //派发奖金时， 用户和资金只有一条。
		return fundGameVos;
	}
	
	private GameTransactionFund packageGameTransactionFund(GameOrder order, GameOrderWin orderWin){
		GameTransactionFund entity = new GameTransactionFund();
		entity.setAmount(orderWin.getCountWin());
		entity.setCheckType(2L);// 1 投注冻结 2 派发奖金 3 投注扣款 4 撤销扣款"
		entity.setCreateTime(new Date());
		entity.setCreator(1L);//系统默认1
		entity.setFundType("派发奖金");
		entity.setIssueCode(orderWin.getIssueCode());
		entity.setLotteryId(orderWin.getLotteryid().longValue());
		entity.setOperatorid(orderWin.getUserid());//"资金变更用户id";
		entity.setOrderCode(order.getOrderCode());
		entity.setPlanCode("");			
		entity.setSymbol("+");
		entity.setTid("");
		entity.setUpdateTime(new Date());
		entity.setUserid(1L); //"执行用户ID 系统默认1";
		entity.setWebIssueCode(order.getWebIssueCode());
		return entity;
	}
}
