package com.winterframework.firefrog.game.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.FileUtil;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameSeriesConfigDao;
import com.winterframework.firefrog.game.dao.vo.GameBonusJsonBean;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.entity.GameOrderOperationsEntity;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.web.dto.GetIssueDrawResult;
import com.winterframework.firefrog.user.entity.IndexLottery;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

/**
 * 
* @ClassName: GameIssueServiceImpl 
* @Description: 奖期操作服务类
* @author Richard，David,Denny
* @date 2013-11-18 上午10:55:32 
*
 */
@Service("gameIssueServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameIssueServiceImpl implements IGameIssueService {

	private static final Logger log = LoggerFactory.getLogger(GameIssueServiceImpl.class);

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;

	@Resource(name = "gameSeriesConfigDaoImpl")
	private IGameSeriesConfigDao gameSeriesConfigDao;

	@Resource(name = "fileUtil")
	private FileUtil fileUtil;

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.business.connect")
	private String businessConnect;

	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDaoImpl;
	@Resource(name = "RedisService")
	private RedisService redisService;

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;
	
	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;
	@Resource(name = "gameDrawResultServiceImpl")
	private IGameDrawResultService gameDrawResultService;
	
	
	/**
	 * 根据彩种Id和期号获取奖期信息。
	* @Title: queryGameIssueByLotteryIdAndIssueCode 
	* @Description:获取奖期信息
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	@Override
	public GameIssueEntity queryGameIssueByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		return gameIssueDao.getGameIssueByLotteryIdAndIssueCode(lotteryId, issueCode);
	}

	@Override
	public GameIssue getByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {
		return gameIssueDao.getByLotteryIdAndIssueCode(lotteryId, issueCode);
	}

	/**
	 * 根据锁定状态的奖期信息。
	* @Title: queryGameIssueByLotteryIdAndIssueCode 
	* @Description:获取奖期信息
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception
	 */
	@Override
	public GameIssueEntity queryGameIssueByWithoutLock(Long lotteryId, Long issueCode, int status) throws Exception {

		return gameIssueDao.queryGameIssueByWithoutLock(lotteryId, issueCode, status);
	}

	/**
	 * 检查奖期状态
	* Title: checkGameIssueStatus
	* Description:
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#checkGameIssueStatus(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean checkGameIssueStatus(Long lotteryId, Long issueCode) throws Exception {

		GameIssueEntity entity = gameIssueDao.getGameIssueByLotteryIdAndIssueCode(lotteryId, issueCode);

		if (null == entity) {
			log.error("获取奖期失败。");
			return false;
		}

		if (isPause(entity)) {
			log.info("奖期已暂停");
			return false;
		}

		//判断上期计划是否完成， 还有上期是否为空，如果上期为空，则表示为第一期。
		//上期如果没有奖期返回true
		//上期有奖期，但不存在追号计划，返回true
		//上期有奖期，存在追号计划，返回false
		//				if(!planFinishStatus(entity)){
		//					log.info("本期计划未完成。");
		//					return false;
		//				}

		//		if (hasEvent(entity)) { //主控已锁，这里不需要锁
		//			log.info("奖期已处于任务执行中，");
		//			return false;
		//		}

		//上期追号是否完成，不影响正常流程的计奖、派奖。
		//		if (planFinishStatus(entity)) {
		//			log.info("上期奖期追号未完成追号计划。");
		//			return false;
		//		}

		return true;
	}

	/**
	 * 检查奖期状态
	* Title: checkGameIssueStatus
	* Description:
	* @param lotteryId
	* @param issueCode
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#checkGameIssueStatus(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean checkGameIssueStatus(GameIssueEntity entity) throws Exception {

		if (null == entity) {
			log.error("获取奖期失败。");
			return false;
		}

		if (isPause(entity)) {
			log.info("奖期已暂停");
			return false;
		}

		int planStatus = entity.getPlanFinishStatus().getValue();

		if (planStatus == 0) {
			log.info("奖期计划未执行");
			return false;
		}

		return true;
	}

	/**
	 * 奖期是否暂停(pauseStatus = 0)
	 * @param entity
	 * @return
	 */
	private boolean isPause(GameIssueEntity entity) {

		//0 为暂停。
		if (entity.getPauseStatus().getValue() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 奖期是否有任務锁定(eventStatus = 2)
	 * @param entity
	 * @return
	 */
	private boolean hasEvent(GameIssueEntity entity) {

		if (entity.getEventStatus().getValue() == 2) {
			return true;
		}

		return false;
	}

	/**
	 * 锁定奖期
	* Title: lockGameIssue
	* Description:
	* @param entity
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#lockGameIssue(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Override
	public void lockGameIssue(GameIssueEntity entity) throws Exception {

		log.info("开始进行奖期锁定。");
		GameIssue issue = gameIssueDao.getById(entity.getId());
		issue.setEventStatus(2);
		gameIssueDao.updateGameIssue(issue);

		log.info("奖期锁定成功");
	}

	/**
	 * 解锁奖期
	* Title: unLockGameIssue
	* Description:
	* @param entity
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#unLockGameIssue(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Override
	public void unLockGameIssue(GameIssueEntity entity) throws Exception {
		log.info("开始进行奖期解锁。");
		GameIssue issue = gameIssueDao.getById(entity.getId());
		issue.setEventStatus(1);
		issue.setUpdateTime(new Date());
		gameIssueDao.updateGameIssue(issue);
		log.info("奖期解锁成功");
	}

	@Override
	public List<GameIssueEntity> queryGameIssueByStatusAndOpenDrawTime(Long status, Long periodStatus) throws Exception {
		return gameIssueDao.getGameIssueByStatusAndOpenDrawTime(status, periodStatus);
	}
	
	@Override
	public List<GameIssueEntity> queryGameIssueByStatusAndSalesStartTime(
			Long status, Long periodStatus,Date time) throws Exception {
		return gameIssueDao.queryGameIssueByStatusAndSalesStartTime(status, periodStatus,time);
	}

	@Override
	public boolean isGameIssuePause(GameIssueEntity entity) throws Exception {
		return isPause(entity);
	}
	
	@Override
	public void updataGameIssue(GameIssueEntity entity, Long status, Long preiodStatus) throws Exception {

		GameIssue gameIssue = gameIssueDao.getById(entity.getId());

		gameIssue.setAwardStruct(entity.getAwardStruct());
		gameIssue.setAdminEndCancelTime(entity.getAdminCanCancelEndTime());
		gameIssue.setStatus(status);
		gameIssue.setPeriodStatus(preiodStatus);
		gameIssue.setUpdateTime(new Date());
		if (entity.getIssuewarnExceptionTime() != null) {
			gameIssue.setIssuewarnExceptionTime(entity.getIssuewarnExceptionTime());
		}
		gameIssueDao.updateGameIssue(gameIssue);
	}

	public void updataGameIssueSaleEnd(GameIssueEntity entity, Long status, Long preiodStatus) throws Exception {

		GameIssue gameIssue = gameIssueDao.getById(entity.getId());
		gameIssue.setAwardStruct(entity.getAwardStruct());
		gameIssue.setStatus(status);
		gameIssue.setPeriodStatus(preiodStatus);
		gameIssue.setUpdateTime(new Date());
		gameIssueDao.updataGameIssueSaleEnd(gameIssue);

	}
	@Override
	public int updataGameIssueSaleStart(GameIssue issue) throws Exception { 
		gameIssueDao.updateGameIssue(issue);
		return 1;
	} 

	/**
	 * 判断奖期是否锁定
	* Title: isGameIssueLocked
	* Description:
	* @param entity
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#isGameIssueLocked(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Override
	public boolean isGameIssueLocked(GameIssueEntity entity) throws Exception {

		return hasEvent(entity);
	}

	/**
	 * 根据当前奖期获取下一奖期
	* Title: queryNextGameIssue
	* Description:
	* @param e
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#queryNextGameIssue(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Override
	public GameIssueEntity queryNextGameIssue(GameIssueEntity e) {
		Long lotteryid = e.getLottery().getLotteryId();
		Long issueCode = e.getIssueCode();

		return gameIssueDao.getNextGameIssueByIssueAndLotteryId(lotteryid, issueCode);
	}

	@Override
	public GameIssue getNextByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {
		return gameIssueDao.getNextByLotteryIdAndIssueCode(lotteryId, issueCode);
	}

	/**
	 * 导出订单数据
	* Title: exportOrdersData
	* Description:
	* @param issueCode 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#exportOrdersData(java.lang.Long)
	 */
	@Override
	public void exportOrdersData(Long issueCode) {
		// 导出本奖期的订单数据
		List<GameOrderOperationsEntity> orders = gameOrderDao.getOrdersByIssueCode(issueCode);

		String fileName = "奖期_" + issueCode + "_订单数据";

		final String separator = "|";

		for (GameOrderOperationsEntity e : orders) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(e.getOrderCode());
			buffer.append(separator);
			buffer.append(e.getLotteryid());
			buffer.append(separator);
			buffer.append(e.getLotteryName());
			buffer.append(separator);
			buffer.append(e.getWebIssueCode());
			buffer.append(separator);
			buffer.append(e.getIssueCode());
			buffer.append(separator);
			buffer.append(e.getTotamount());
			buffer.append(separator);
			buffer.append(e.getTotwin());
			buffer.append(separator);
			buffer.append(e.getStatus());
			buffer.append(separator);
			buffer.append(e.getParentType());
			buffer.append(separator);
			buffer.append(e.getParentid());
			buffer.append(separator);
			buffer.append(e.getSaleTime());
			buffer.append(separator);
			buffer.append(e.getOrderId());
			buffer.append(separator);
			buffer.append(e.getAccount());
			buffer.append(separator);
			buffer.append(e.getUserid());

			fileUtil.createFilepath(buffer.toString(), fileName, new Date());
		}
	}

	/**
	 * 根据彩种Id获取奖期列表
	* Title: getGameIssueByLotteryIdAndStatus
	* Description:
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#getGameIssueByLotteryIdAndStatus(java.lang.Long)
	 */
	@Override
	public List<GameIssueEntity> getGameIssueByLotteryIdAndStatus(Long lotteryId) throws Exception {
		return gameIssueDao.getGameIssueByLotteryIdAndStatus(lotteryId);
	}

	/**
	 * 根据当前奖期获取上一奖期
	* Title: queryPreGameIssue
	* Description:
	* @param gameIssue
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#queryPreGameIssue(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Override
	public GameIssueEntity queryPreGameIssue(GameIssueEntity gameIssue) throws Exception {

		Long lotteryid = gameIssue.getLottery().getLotteryId();
		Long issueCode = gameIssue.getIssueCode();
		return gameIssueDao.getPreGameIssueByLotteryIdAndIssueCode(lotteryid, issueCode);
	}

	/**
	 * 更新下一奖期Last_ISSUE_STOP状态
	* Title: updateLastIssueStop
	* Description:
	* @param nextGameIssue
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#updateLastIssueStop(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Override
	public void updateLastIssueStop(GameIssueEntity nextGameIssue) throws Exception {

		GameIssue gameIssue = gameIssueDao.getById(nextGameIssue.getId());

		if (null != nextGameIssue.getLastIssueStop()) {
			gameIssue.setLastIssueStop(nextGameIssue.getLastIssueStop().getValue());
		}
		if (null != nextGameIssue.getPlanFinishStatus()) {
			gameIssue.setPlanFinishStatus(nextGameIssue.getPlanFinishStatus().getValue());
		}
		gameIssueDao.update(gameIssue);
	}

	/**
	 * @throws Exception  
	* @Title: calculateGameIssueFinishTime 
	* @Description: 根据彩种配置参数及奖期开奖时间计算奖期结束时间 
	*/
	@Override
	public Long calculateGameIssueFinishTime(Long lotteryId, Date date) throws Exception {
		//log.debug("Entered Method :: calculateGameIssueFinishTime");
		GameSeriesConfigEntity gameSeriesConfigEntity = null;
		Long time = 0l;
		try {
			gameSeriesConfigEntity = gameSeriesConfigDao.getGameSeriesConfigByLotteryId(lotteryId);
			// 取出彩种的“开奖后异常处理时间”，转换为毫秒数
			if (gameSeriesConfigEntity != null) {
				time = gameSeriesConfigEntity.getIssuewarnExceptionTime() * 60 * 1000;
			}
		} catch (Exception e) {
			log.error("Error while calculateGameIssueFinishTime", e);
			throw e;
		}

		//log.debug("Exited Method ::  calculateGameIssueFinishTime");
		return date.getTime() + time;
	}

	/** 
	* @Title: queryGameIssueByStatus 
	* @Description: 按奖期状态和奖期过程状态查询奖期列表 
	*/
	@Override
	public List<GameIssueEntity> queryGameIssueByStatus(Long status, Long periodStatus) {
		return gameIssueDao.getGameIssueByStatus(status, periodStatus);
	}

	/**
	 * 判断奖期Last_ISSUE_STOP状态
	* Title: isLastIssueStop
	* Description:
	* @param e
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#isLastIssueStop(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Override
	public boolean isLastIssueStop(GameIssueEntity e) {
		if (e.getLastIssueStop().getValue() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断奖期的Plan_FINISH_STATUS状态
	* Title: isLastIssuePlanNotFinished
	* Description:
	* @param e
	* @return 
	* @see com.winterframework.firefrog.game.service.IGameIssueService#isLastIssuePlanNotFinished(com.winterframework.firefrog.game.entity.GameIssueEntity)
	 */
	@Override
	public boolean isLastIssuePlanNotFinished(GameIssueEntity e) {

		//上期奖期
		GameIssueEntity preEntity = gameIssueDao.getPreGameIssueByLotteryIdAndIssueCode(e.getLottery().getLotteryId(),
				e.getIssueCode());
		//如果上期奖期为空，则不需要判断上期是否为暂停状态。
		if (e.getPlanFinishStatus().getValue() == 0 && preEntity != null) {
			return true;
		}
		return false;
	}

	@Override
	public void generateGameIssue() {
		// 遍历彩种，根据奖期规则生成未来奖期，第一次生成四天的奖期，ssc简单判断规则，今天在内的四天内没有奖期，则生成包含当天在内的四天内奖期，已生成的无须重新生成
		List<GameSeries> gameSeries=null;
		try {
			gameSeries = gameSeriesConfigDao.getAllGameSeries(1);//获取系统可用彩系
		} catch (Exception e) {
			log.error("获取系统可用彩系出错", e);
		}
		
		if(null!=gameSeries){
			for (GameSeries gs : gameSeries) {
				if (gs.getLotteryid() != 99112 && gs.getLotteryid()!=99304L && gs.getLotteryid()!=99306l && gs.getLotteryid() != 99113) {//顺利秒秒彩、顺利秒秒彩超級2000无奖期
					try{
						log.info(gs.getLotteryName() + "奖期生成开始执行");
						gameIssueDao.generateGameIssue(gs);
						log.info(gs.getLotteryName() + "奖期生成执行结束");
					}catch(Exception e){
						log.error("生成奖期错误", e);
					}
				}
			}
		}
	}

	@Override
	public boolean isEarlierSuspendedGameIssueExist(Long lotteryId, Long issueCode) {
		List<GameIssue> earlierSuspendedGameIssueList = gameIssueDao.getEarlierSuspendedGameIssue(lotteryId, issueCode);
		if (earlierSuspendedGameIssueList != null && earlierSuspendedGameIssueList.size() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void updataGameIssuePauseStatus(GameIssueEntity gameIssueEntity, int i) {
		GameIssue issue = gameIssueDao.getById(gameIssueEntity.getId());
		issue.setPauseStatus(i);
		gameIssueDao.update(issue);

	}

	@Override
	public List<GameIssueEntity> queryGameIssueByStatusAndSalesEndTimeForBeginSaleStart(Long status, Long periodStatus) {
		return gameIssueDao.queryGameIssueByStatusAndSalesEndTimeForBeginSaleStart(status, periodStatus);
	}

	/** 
	* @Title: dealNoAwardNumberSaleEndIssue 
	* @Description: 处理销售截止后，（加上10分钟），还没有开奖号码的，就主动去获取开奖号码
	* @throws Exception
	*/
	@Override
	public void dealNoAwardNumberSaleEndIssue() throws Exception {

		Date time = new Date();
		time = new Date(time.getTime() - 30 * 60 * 1000);
		List<GameIssue> issues = gameIssueDao.getNoAwardNumberSaleEndIssue(time);
		if (issues != null) {
			log.warn("-----本批次 " + issues.size() + "条奖期没有开奖号码--------");

			for (GameIssue gameIssue : issues) {
				GetIssueDrawResult request = new GetIssueDrawResult();
				request.setLotteryId(gameIssue.getLotteryid());
				request.setWebIssueCode(gameIssue.getWebIssueCode());
				request.setRequestTime(new Date());
				try {
					httpClient.invokeHttp(businessConnect + "/game/getIssueDrawResult", request);
				} catch (Exception e) {
					log.error("-----调用主动获取奖期开奖号码异常--------");
				}
				//可能出现锁表问题，该有http请求的game项目执行该方法
				//gameIssueDao.updateTry(1L, gameIssue.getId());

			}

		}
	}
	

	/** 
	* @Title: getAndUpdateAwardStruct 
	* @Description: 获取奖金结构并更新奖期
	* @param issue
	* @throws Exception
	*/
	public void getAndUpdateAwardStruct(GameIssueEntity issue) throws Exception {
		GameBonusJsonBean pool = gameAwardDaoImpl.getGameBonusJsonBean(issue.getLottery().getLotteryId());
		String awardStruct = JsonMapper.nonEmptyMapper().toJson(pool);
		issue.setAwardStruct(awardStruct);
		GameIssue gameIssue = gameIssueDao.getById(issue.getId());
		gameIssue.setAwardStruct(awardStruct);
		log.error(awardStruct);
		gameIssueDao.update(gameIssue);

		if (pool != null && pool.getGameBonusPoolJson() != null) {
			Long lotteryId = issue.getLottery().getLotteryId();
			IndexLottery indexLottery = redisService.getHome(lotteryId);
			indexLottery.setPondAmount(pool.getGameBonusPoolJson().getActualBonus());
			redisService.setHome(lotteryId, indexLottery);
		}
	}

	@Override
	public List<GameIssueEntity> getGameIssueByLotteryIdAndDay(Long lotteryId, String startDay, String endDay)
			throws Exception {
		return gameIssueDao.getGameIssueByLotteryIdAndDay(lotteryId, startDay, endDay);
	}

	@Override
	public GameIssue getGameIssueByLotteryAndIssue(Long lotteryId, Long issueCode) throws Exception {
		return this.gameIssueDao.getGameIssueByLotteryIssue(lotteryId, issueCode);
	}

	@Override
	public int save(GameContext ctx, GameIssue issue) throws Exception {
		if(issue==null) return 0;
		Date curDate=DateUtils.currentDate();
		if(issue.getId()!=null){
			issue.setUpdateTime(curDate);
			this.gameIssueDao.update(issue);
		}else{
			issue.setCreateTime(curDate);
			this.gameIssueDao.insert(issue);
		}
		return 1;
	}

	@Override
	public int saleBegin(GameContext ctx, GameIssue issue) throws Exception {
		return 0;
	}

	@Override
	public int saleEnd(GameContext ctx, GameIssue issue) throws Exception {
		return 0;
	}

	@Override
	public int draw(GameContext ctx, GameIssue issue) throws Exception {
		return 0;
	}

	@Override
	public int redraw(GameContext ctx, GameIssue issue) throws Exception {
		return 0;
	}

	@Override
	public int makeupDraw(GameContext ctx, GameIssue issue) throws Exception {
		return 0;
	}

	@Override
	public int cancel(GameContext ctx, GameIssue issue) throws Exception {
		if (issue == null)
			return 0;
		issue.setStatus(new Long(GameIssue.Status.FINISH.getValue()));
		issue.setPauseStatus(Integer.valueOf(GameIssue.PauseStatus.CANCEL.getValue()));
		issue.setUpdateTime(DateUtils.currentDate());
		gameIssueDao.update(issue);
		return 1;
	}

	@Override
	public GameIssueEntity getSsqGameIssueForMail(Date saleEndDate) throws Exception {
		return gameIssueDao.getSsqGameIssueForMail(saleEndDate);
	}
	
	@Override
	public GameIssueEntity queryGameIssueByWebIssueCode(Long lotteryId,String webIssueCode){
		return gameIssueDao.queryGameIssue(lotteryId, webIssueCode);
	}
	
	@Override
	public List<GameIssue> getNoNumberLatestByLotteryIdAndTime(Long lotteryId,
			Date curTime) throws Exception {
		return gameIssueDao.getNoNumberLatestByLotteryIdAndTime(lotteryId, curTime);
	}
	
	@Override
	public void updateGameIssue(GameIssueEntity issueEntity) throws Exception {
		gameIssueDao.update(VOConvert.gameIssueEntity2GameIssue(issueEntity));
	}
	/**
	* @Title: addDrawResult
	* @Description:正常开奖
	* @param lotteryid
	* @param issueCode
	* @param numberRecord
	* @param warnIssueLog
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameDrawService#addDrawResult(java.lang.Long, java.lang.Long, java.lang.String, com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog) 
	*/
	
	public void addDrawResult(Long lotteryid, Long issueCode, String numberRecord, GameWarnIssueLog warnIssueLog,Date ecVerifiedTime ,String memo)
			throws Exception {
		String drawNumber = gameDrawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryid,issueCode);
		if(drawNumber!=null) return;
		
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, issueCode);
		Date now = new Date();
		//1、添加奖期结果记录
		GameDrawResult drawResult = new GameDrawResult();
		drawResult.setLotteryid(lotteryid);
		drawResult.setIssueCode(issueCode);
		drawResult.setWebIssueCode(issue.getWebIssueCode());
		drawResult.setNumberRecord(numberRecord);
		drawResult.setCreateTime(now);
		drawResult.setOpenDrawTime(now);
		drawResult.setType(0L);
		drawResult.setMemo(memo);
		gameDrawResultDao.insert(drawResult);

		//2、添加"待计算奖金"事件
		GameControlEvent event = new GameControlEvent();
		event.setLotteryid(lotteryid);
		event.setStartIssueCode(issueCode);
		event.setEndIssueCode(issueCode);
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setSaleEndTime(issue.getSaleEndTime());
		event.setStatus(0L);
		event.setCreateTime(now);
		event.setEnentType(2L);
		if (warnIssueLog != null) {
			event.setParams(warnIssueLog.getDisposeUser());
		}
		gameControlEventDao.addControlEvent(event);

		//更新奖期状态"开奖号码已确认"
		issue.setNumberRecord(numberRecord);
		issue.setNumberUpdateTime(DateUtils.currentDate());
		issue.setNumberUpdateCount((issue.getNumberUpdateCount()==null?0:issue.getNumberUpdateCount())+1);
		issue.setStatus(GameIssueStatus.ACK_DRAW_RESULT);
		issue.setPeriodStatus(GameIssuePeriodStatus.WAIT_DRAW_RESULT);
		issue.setRecivceDrawTime(now);
		//issue.setFactionDrawTime(now);
		issue.setEcVerifiedTime(ecVerifiedTime);
		issue.setUpdateTime(now);
		updateGameIssue(issue);
		
		if(lotteryid==99501L){
			try{
				Long otherLottreyId=99601L;
				GameIssueEntity gameIssue = gameIssueDao.queryGameIssue(otherLottreyId, issue.getWebIssueCode());
				if(gameIssue!=null){
					if(null!=warnIssueLog){
						warnIssueLog.setLotteryid(otherLottreyId);
						warnIssueLog.setIssueCode(gameIssue.getIssueCode());
					}
					addDrawResult(otherLottreyId, gameIssue.getIssueCode(), numberRecord, warnIssueLog, ecVerifiedTime, memo);
				}
			}catch(Exception e){
				log.error("exception occurs when query dice issue.",e);
			}
		}
	}
}
