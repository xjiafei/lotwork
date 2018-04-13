package com.winterframework.firefrog.game.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.winterframework.firefrog.common.redis.RedisClient2;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameOrderLogDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGameRiskConfigDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.IGameWarnUserDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameOrderWin;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.GameWarnUser;
import com.winterframework.firefrog.game.entity.GameRiskConfig;
import com.winterframework.firefrog.game.exception.GameRiskException;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameFundService;
import com.winterframework.firefrog.game.service.IGameOrderFundService;
import com.winterframework.firefrog.game.service.IGameOrderLogService;
import com.winterframework.firefrog.game.service.IGameOrderWinFundService;
import com.winterframework.firefrog.game.service.IGamePlanService;
import com.winterframework.firefrog.game.service.IGameReturnPointFundService;
import com.winterframework.firefrog.game.service.IGameSlipService;
import com.winterframework.firefrog.game.service.IGameWarnService;
import com.winterframework.firefrog.game.service.IGameWarnUserService;
import com.winterframework.firefrog.game.service.IUserSystemUpdateLogService;
import com.winterframework.firefrog.game.service.bean.GameWarnServiceBean;
import com.winterframework.firefrog.game.service.bean.GameWarnServiceCacheBean;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.IndexLottery;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName GameWarnServiceimpl 
* @Description 审核风控订单入库 
* @author  hugh
* @date 2014年4月11日 下午4:17:37 
*  
*/
@Service("gameWarnServiceImpl")

public class GameWarnServiceImpl implements IGameWarnService {

	private static final Logger log = LoggerFactory.getLogger(GameWarnServiceImpl.class);

	@Resource(name = "gameOrderLogDaoImpl")
	private IGameOrderLogDao gameOrderLogDao;
	@Resource(name = "gameOrderLogServiceImpl")
	private IGameOrderLogService gameOrderLogService;
	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;
	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDaoImpl;
	@Resource(name = "gameWarnOrderDaoImpl")
	private IGameWarnOrderDao warnOrderDao;
	@Resource(name = "gamePlanDaoImpl")
	private IGamePlanDao gamePlanDao;
	@Resource(name = "gameWarnUserDaoImpl")
	private IGameWarnUserDao gameWarnUserDao;
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;
	@Resource(name = "gameRiskConfigDaoImpl")
	private IGameRiskConfigDao gameRiskConfigDao;
	@Resource(name = "gameReturnPointFundServcieImpl")
	private IGameReturnPointFundService gameReturnPointFundServcie;
	@Resource(name = "gameOrderWinFundServcieImpl")
	private IGameOrderWinFundService gameOrderWinFundServcie;
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameOrderFundService gameOrderFundServcie;
	@Resource(name = "gameOrderFundServcieImpl")
	private IGameFundService gameFundServcie; 
//	@Resource(name = "generateGamePlanServiceImpl")
//	private IGamePlanService generateGamePlanServiceImpl;
//	@Resource(name = "gamePlanService")
//	protected ICommonGamePlanService gamePlanService; 
	@Resource(name = "gameWarnUserServiceImpl")
	protected IGameWarnUserService gameWarnUserService;
	
	@Resource(name = "gameFundRiskServiceImpl")
	protected IGameFundRiskService gameFundRiskService;
	
	
	//@Resource(name = "RedisClient")
//	private RedisClient redisClient;
	
	@Resource(name = "RedisClient2")
	private RedisClient2 redisClient2;
	
	@Resource(name = "RedisService")
	private RedisService redisService;
	
	@Resource(name = "userSystemUpdateLogTaskServiceImpl")
	private IUserSystemUpdateLogService userSystemUpdateLogServiceImpl;
	@Resource(name = "gameSlipServiceImpl")
	private IGameSlipService gameSlipService;
	
	private final String WARN_SERVICE_RPRE = "WARN_SERVICE_";
	private final String WARN_SEPARATE = "_";
	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();


	/** 
	* @Title: updateIssuseRedisCache 
	* @Description: 更新奖期相关  首页的缓存
	* @param order
	*/
	@SuppressWarnings("unchecked")
	public void updateIssuseRedisCache(GameOrder order) {
		log.debug("风控模块：update firefrog_index_lastdata_");
		try {
			if (order.getStatus().intValue() == 5 || order.getTotalWin() == null || order.getTotalWin() < 10000000L) {
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
				user = customerDao.queryUserById(order.getUserid());
			} catch (Exception e) {
				log.error(" queryUserById error: userId=" + order.getUserid(), e);
			}
			if (user != null && user.getUserProfile() != null && user.getUserProfile().getAccount() != null
					&& !user.getUserProfile().getAccount().equals("")) {
				log.debug("风控模块：wins.put");
				if(lotteryId==99701){
					String wbeIssueCode= order.getWebIssueCode();
					if(StringUtils.isNotEmpty(wbeIssueCode)){
						wbeIssueCode = wbeIssueCode.substring(0,2)+"/"+wbeIssueCode.substring(2);
						winsLRUMap.put(user.getUserProfile().getAccount()+"-"+wbeIssueCode, order.getTotalWin() + "");						
					}else{
						winsLRUMap.put(user.getUserProfile().getAccount(), order.getTotalWin() + "");					
					}
				}else{
					winsLRUMap.put(user.getUserProfile().getAccount(), order.getTotalWin() + "");					
				}
			}

			indexLottery.setWins(winsLRUMap);
			redisService.setHome(lotteryId,indexLottery); 
		} catch (Exception e) {
			log.error("updateIssuseRedisCache fail", e);
		}
	}
 
}
