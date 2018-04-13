package com.winterframework.firefrog.game.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameWarnIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent.EventType;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssue;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssuePeriodStatus;
import com.winterframework.firefrog.game.entity.GameIssueStatus;
import com.winterframework.firefrog.game.entity.PauseStatus;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.service.IGameWarnIssueLogService;

/**
 * 
 * @ClassName: GameDrawServiceImpl
 * @Description: 彩票开奖结果处理服务。
 * @author Richard
 * @date 2013-11-18 下午2:33:58
 *
 */
@Service("gameDrawResultServiceImpl")
public class GameDrawResultServiceImpl implements IGameDrawResultService {

	private static final Logger log = LoggerFactory
			.getLogger(GameDrawResultServiceImpl.class);

	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;

	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueServiceImpl;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameWarnIssueLogServiceImpl")
	private IGameWarnIssueLogService gameWarnIssueLogServiceImpl;

	@Resource(name = "gameWarnIssueDaoImpl")
	private IGameWarnIssueDao gameWarnIssueDao;

	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService configService;

	@Override
	public String getnumberRecordByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception {
		String result = gameDrawResultDao
				.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		if (null == result) {
			log.error("获取开奖号码失败，彩种【" + lotteryId + "】，期号【" + issueCode + "】");
			return null;
		}
		log.debug("获取时时彩开奖号码成功。开奖号码" + result);
		return result;
	}

	@Override
	public void addOrUpdateDrawResult(GameControlEvent event) throws Exception {
		log.info("addOrUpdateDrawResult start");
		Map<String, String> params = new ObjectMapper().readValue(
				event.getParams(),
				new TypeReference<HashMap<String, String>>() {
				});
		String numberRecord = params.get("number").replaceAll("%2C", ",");
		Date verifiedTime = DateUtils.parse(params.get("verifiedTime"),
				"yyyyMMddHHmmss");
		Long issueCode = event.getStartIssueCode();
		Long lotteryId = event.getLotteryid();
		GameDrawResult gameDrawResult = gameDrawResultDao
				.getDrawResuleByLotteryIdAndIssueCode(lotteryId, lotteryId);

		log.info("addOrUpdateDrawResult lotteryId:"+lotteryId+",issueCode:"+issueCode+",numberRecord:"+numberRecord);
		
		if(checkIsAfterTargetIssueStatus(lotteryId, issueCode,GameIssueStatus.SALE_END)){
			if (gameDrawResult != null) {
				modifyDrawResult(lotteryId, issueCode, numberRecord, verifiedTime,
						gameDrawResult);
			} else {
				addDrawResult(lotteryId, issueCode, numberRecord, verifiedTime);
			}
		}else{
			throw new Exception("Issue status not after SaleEnd.");
		}
		log.info("addOrUpdateDrawResult end");
	}
	
	private boolean checkIsAfterTargetIssueStatus(Long lotteryId,Long issueCode,GameIssueStatus status){
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryId, issueCode);
		return issue.getStatus().getValue()>=status.getValue();
	}

	private void modifyDrawResult(Long lotteryId, Long issueCode,
			String numberRecord, Date ecVerifiedTime,
			GameDrawResult gameDrawResult) throws Exception {
		log.info("modifyDrawResult start");
		// 有号码 且 非手动输入
		log.info(lotteryId + "," + gameDrawResult.getWebIssueCode()
				+ "已经存在开奖号码 ");
		if (!numberRecord.equals(gameDrawResult.getNumberRecord())
				&& gameDrawResult.getType().longValue() == 0L) {

			// 1.更新獎期開獎號碼
			GameIssueEntity issue = updateGameIssueNumRecord(lotteryId,
					issueCode, numberRecord);

			// 2、更新開獎結果表
			updateGameDrawResult(issue);

			if(PauseStatus.NORMAL.equals(issue.getPauseStatus())){
				// 3.添加奖期警告表
				GameWarnEvent issueWarnId = GameWarnEvent.SYSTEM_R_AWARD;
				GameWarnIssue warnIssue = addGameWarnIssue(issue, issueWarnId);
	
				// 4.添加更新獎號奖期警告表Log
				GameWarnIssueLog warnIssueLog = addModifyNumberRecordWarnIssueLog(
						issue, issueWarnId, gameDrawResult.getNumberRecord());
	
				// 5.添加"重新开奖计奖"事件
				addGameControlEvent(issue, new Date(), EventType.REDRAW, warnIssue,
						warnIssueLog);
			}else{
				log.info(issueCode+"奖期已暫停");
			}
			
		}else if(gameDrawResult.getType().longValue() != 0L){
			log.info(issueCode+"已经手动输入开奖号码！！！");
		}
		log.info("modifyDrawResult end");
	}

	private void addDrawResult(Long lotteryId, Long issueCode,
			String numberRecord, Date verifiedTime) throws Exception {
		log.info("addDrawResult start");

		Date now = new Date();

		// 1.更新奖期状态"开奖号码已确认"
		GameIssueEntity issue = updateGameIssueNumRecordAndStatus(lotteryId,
				issueCode, numberRecord, now, verifiedTime);

		// 2、添加奖期结果记录
		addGameDrawResult(issue, now);
		
		if(PauseStatus.NORMAL.equals(issue.getPauseStatus())){
			// 3、添加"待计算奖金"事件
			addGameControlEvent(issue, new Date(), EventType.DRAW, null, null);
		}else{
			log.info(issueCode+"奖期已暫停");
		}

		log.info("addDrawResult end");
	}

	private GameIssueEntity updateGameIssueNumRecord(Long lotteryId,
			Long issueCode, String numberRecord) throws Exception {
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryId,
				issueCode);
		issue.setNumberRecord(numberRecord);
		issue.setNumberUpdateTime(DateUtils.currentDate());
		issue.setNumberUpdateCount((issue.getNumberUpdateCount() == null ? 0
				: issue.getNumberUpdateCount()) + 1);
		issue.setRecivceDrawTime(new Date());
		issue.setUpdateTime(new Date());
		gameIssueDao
				.updateGameIssue(VOConvert.gameIssueEntity2GameIssue(issue));
		return issue;
	}

	private GameIssueEntity updateGameIssueNumRecordAndStatus(Long lotteryId,
			Long issueCode, String numberRecord, Date time, Date verifiedTime)
			throws Exception {
		// 更新奖期状态"开奖号码已确认"
		GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryId,
				issueCode);
		issue.setNumberRecord(numberRecord);
		issue.setNumberUpdateTime(DateUtils.currentDate());
		issue.setNumberUpdateCount((issue.getNumberUpdateCount() == null ? 0
				: issue.getNumberUpdateCount()) + 1);
		issue.setStatus(GameIssueStatus.ACK_DRAW_RESULT);
		issue.setPeriodStatus(GameIssuePeriodStatus.WAIT_DRAW_RESULT);
		issue.setRecivceDrawTime(time);
		issue.setEcVerifiedTime(verifiedTime);
		issue.setUpdateTime(time);
		gameIssueDao
				.updateGameIssue(VOConvert.gameIssueEntity2GameIssue(issue));
		return issue;
	}

	private GameDrawResult updateGameDrawResult(GameIssueEntity issue) {
		GameDrawResult drawResult = gameDrawResultDao
				.getDrawResuleByLotteryIdAndIssueCode(issue.getLottery()
						.getLotteryId(), issue.getIssueCode());
		drawResult.setNumberRecord(issue.getNumberRecord());
		drawResult.setUpdateTime(new Date());
		drawResult.setType(0L);
		drawResult.setOpenDrawTime(issue.getOpenDrawTime());
		gameDrawResultDao.update(drawResult);
		return drawResult;
	}

	/**
	 * 新增一筆 type = 0(事件驅動) 的 GameDrawResul(獎期開獎結果表)。
	 * @param issue 每期獎期資料
	 * @param time 建立時間、開獎時間
	 * @return
	 * @throws Exception 非 ORA-00001(PK 重複)時拋出
	 */
	private GameDrawResult addGameDrawResult(GameIssueEntity issue, Date time) throws Exception {
		GameDrawResult drawResult = new GameDrawResult();
		drawResult.setLotteryid(issue.getLottery().getLotteryId());
		drawResult.setIssueCode(issue.getIssueCode());
		drawResult.setWebIssueCode(issue.getWebIssueCode());
		drawResult.setNumberRecord(issue.getNumberRecord());
		drawResult.setCreateTime(time);
		drawResult.setOpenDrawTime(time);
		drawResult.setMemo("");
		drawResult.setType(0L);
		try {
			gameDrawResultDao.insert(drawResult);
		} catch(Exception e) {
			if(!e.getMessage().contains("ORA-00001")) {
				throw e;
			}
		}
		return drawResult;
	}

	private GameWarnIssue addGameWarnIssue(GameIssueEntity issue,
			GameWarnEvent issueWarnId) throws Exception {
		GameWarnIssue warn = new GameWarnIssue();
		warn.setIssueCode(issue.getIssueCode());
		warn.setLotteryid(issue.getLottery().getLotteryId());
		warn.setWebIssueCode(issue.getWebIssueCode());
		warn.setIssueWarnId(issueWarnId.getCode());
		warn.setCreateTime(new Date());
		warn.setUpdateTime(warn.getCreateTime());
		warn.setReadFlag(0L);
		warn.setStatus(2L);
		warn.setStatusRout(warn.getIssueWarnId());
		GameWarnIssue warnIssue = gameWarnIssueDao
				.queryGameWarnIssueByLotteryIdAndIssueCode(warn.getLotteryid(),
						warn.getIssueCode());
		if (warnIssue != null) {
			warn.setId(warnIssue.getId());
			if (warnIssue.getStatusRout() != null) {
				warn.setStatusRout(warnIssue.getStatusRout() + ","
						+ warn.getStatusRout());
			}
			gameWarnIssueDao.update(warnIssue);
		}
		return warn;
	}

	private GameWarnIssueLog addModifyNumberRecordWarnIssueLog(
			GameIssueEntity issue, GameWarnEvent issueWarnId,
			String oldRecordNum) throws Exception {
		GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();
		warnIssueLog.setDisposeInfo("接受到EC开奖号码：" + issue.getNumberRecord());
		warnIssueLog.setCreateTime(new Date());
		warnIssueLog.setDisposeMemo("EC开奖通知");
		warnIssueLog.setDisposeUser("ECNF");
		warnIssueLog.setIssueCode(issue.getIssueCode());
		warnIssueLog.setLotteryid(issue.getLottery().getLotteryId());
		warnIssueLog.setWebIssueCode(issue.getWebIssueCode());
		warnIssueLog.setEvent(issueWarnId.getCode());// 开奖号码错误（已处理）
		warnIssueLog.setDisposeInfo("EC修改开奖号码:" + oldRecordNum + ">>"
				+ issue.getNumberRecord());
		gameWarnIssueLogServiceImpl.addWarnIssueLog(warnIssueLog);
		return warnIssueLog;
	}

	private GameControlEvent addGameControlEvent(GameIssueEntity issue,
			Date time, EventType eventType, GameWarnIssue warnIssue,
			GameWarnIssueLog warnIssueLog) throws Exception {
		GameControlEvent event = new GameControlEvent();
		event.setLotteryid(issue.getLottery().getLotteryId());
		event.setStartIssueCode(issue.getIssueCode());
		event.setEndIssueCode(issue.getIssueCode());
		event.setSaleStartTime(issue.getSaleStartTime());
		event.setSaleEndTime(issue.getSaleEndTime());
		event.setStatus(0L);
		event.setCreateTime(time);
		event.setEnentType((long) eventType.getValue());
		if (warnIssueLog != null) {
			event.setParams(warnIssueLog.getDisposeUser());
			event.setMessage(warnIssueLog.getDisposeInfo());
		}
		if (warnIssue != null) {
			if (warnIssue.getId() == null) {
				event.setWarnIssueId(-1L * warnIssueLog.getId());
			} else {
				event.setWarnIssueId(warnIssue.getId());
			}
		}
		gameControlEventDao.addControlEvent(event);
		return event;
	}
}
