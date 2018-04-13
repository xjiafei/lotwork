package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameIssueGenerateUtil;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameIssueRuleCheckDao;
import com.winterframework.firefrog.game.dao.IGameIssueRuleDao;
import com.winterframework.firefrog.game.dao.IGameIssueTemplateDao;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.dao.vo.VOConverter4Task;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.firefrog.game.exception.GameIssueNotExistErrorException;
import com.winterframework.firefrog.game.web.util.IssueCodeUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 奖期DAO
 * @author Richard
 * @date 2013-11-18 上午11:00:34 
 */
@Repository("gameIssueDaoImpl")
public class GameIssueDaoImpl extends BaseIbatis3Dao<GameIssue> implements IGameIssueDao {
	@Resource(name = "gameIssueRuleDaoImpl")
	private IGameIssueRuleDao gameIssueRuleDao;

	@Resource(name = "gameIssueTemplateDaoImpl")
	private IGameIssueTemplateDao gameIssueTemplateDao;

	@Resource(name = "gameIssueRuleCheckDaoImpl")
	private IGameIssueRuleCheckDao gameIssueRuleCheckDao;

	@PropertyConfig(value = "HLJGenerateStartIssueCode")
	private String HLJStartGenerateIssueCode;
	@PropertyConfig(value = "BJKL8GenerateStartIssueCode")
	private String BJKL8GenerateStartIssueCode;

	@Override
	public GameIssueEntity getGameIssueByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("getGameIssueByLotteryIdAndIssueCode", map);
		if (null == gameIssue) {
			return null;
		}
		GameIssueEntity gie = VOConverter4Task.gameIssue2GameIssueEntity(gameIssue);
		return gie;
	}

	@Override
	public GameIssue getByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne(getQueryPath("getByLotteryIdAndIssueCode"), map);
	}

	@Override
	public GameIssue getGameIssueByLotteryIssue(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("getGameIssueByLotteryIdAndIssueCode", map);

		return gameIssue;
	}

	@Override
	public GameIssueEntity queryGameIssueByWithoutLock(Long lotteryId, Long issueCode, int status) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		map.put("status", status);

		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("getGameIssueByWithoutLock", map);
		if (null == gameIssue) {
			return null;
		}
		GameIssueEntity gie = VOConverter4Task.gameIssue2GameIssueEntity(gameIssue);
		return gie;
	}

	@Override
	public List<GameIssueEntity> getGameIssueByStatusAndOpenDrawTime(Long status, Long periodStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("periodStatus", periodStatus);
		map.put("date", new Date());
		List<GameIssue> gameIssueList = this.sqlSessionTemplate.selectList("getGameIssueByStatusAndSalesEndTime", map);
		List<GameIssueEntity> gameIssueEntityList = new ArrayList<GameIssueEntity>();
		for (GameIssue gi : gameIssueList) {
			GameIssueEntity e = VOConverter4Task.gameIssue2GameIssueEntity(gi);
			gameIssueEntityList.add(e);
		}
		return gameIssueEntityList;
	}
	@Override
	public List<GameIssueEntity> queryGameIssueByStatusAndSalesStartTime(
			Long status, Long periodStatus, Date time) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("periodStatus", periodStatus);
		map.put("date", time);
		List<GameIssue> gameIssueList = this.sqlSessionTemplate.selectList("queryGameIssueByStatusAndSalesStartTime", map);
		List<GameIssueEntity> gameIssueEntityList = new ArrayList<GameIssueEntity>();
		for (GameIssue gi : gameIssueList) {
			GameIssueEntity e = VOConverter4Task.gameIssue2GameIssueEntity(gi);
			gameIssueEntityList.add(e);
		}
		return gameIssueEntityList;
	}

	@Override
	public GameIssueEntity getNextGameIssueByTimeAndLotteryId(Long lotteryId, Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("date", date);

		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("getNextGameIssueByTimeAndLotteryId", map);
		if (null == gameIssue) {
			return null;
		}
		GameIssueEntity gie = VOConverter4Task.gameIssue2GameIssueEntity(gameIssue);
		return gie;
	}

	@Override
	public GameIssueEntity getNextGameIssueByIssueAndLotteryId(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("getNextGameIssueByIssueAndLotteryId", map);
		if (null == gameIssue) {
			return null;
		}
		GameIssueEntity gie = VOConverter4Task.gameIssue2GameIssueEntity(gameIssue);
		return gie;
	}

	@Override
	public GameIssue getNextByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne("getNextGameIssueByIssueAndLotteryId", map);
	}

	@Override
	public List<GameIssueEntity> getGameIssueByLotteryIdAndStatus(Long lotteryId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);

		List<GameIssue> gis = sqlSessionTemplate.selectList("getGameIssueByLotteryIdAndStatus", map);
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();

		for (GameIssue gi : gis) {
			GameIssueEntity gameIssueEntity = VOConverter4Task.gameIssue2GameIssueEntity(gi);
			issueEntityList.add(gameIssueEntity);
		}
		return issueEntityList;
	}

	@Override
	public GameIssueEntity getPreGameIssueByLotteryIdAndIssueCode(Long lotteryid, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryid);
		map.put("issueCode", issueCode);
		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("getPreGameIssueByLotteryIdAndIssueCode", map);

		if (null == gameIssue) {
			return null;
		}
		GameIssueEntity gie = VOConverter4Task.gameIssue2GameIssueEntity(gameIssue);
		return gie;
	}

	@Override
	public List<GameIssueEntity> queryGameIssuesByIssueCodes(Long lotteryid, Long startIssueCode, Long endIssueCode)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryid", lotteryid);
		map.put("startIssueCode", startIssueCode);
		map.put("endIssueCode", endIssueCode);

		List<GameIssue> gis = sqlSessionTemplate.selectList("queryGameIssuesByIssueCodes", map);
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();

		for (GameIssue gi : gis) {
			GameIssueEntity gameIssueEntity = VOConverter4Task.gameIssue2GameIssueEntity(gi);
			issueEntityList.add(gameIssueEntity);
		}
		return issueEntityList;
	}

	@Override
	public void updateGameIssueLastIssueStop(Long lotteryid, Long issueCode, Integer lastIssueStop) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryid", lotteryid);
		map.put("issueCode", issueCode);
		map.put("lastIssueStop", lastIssueStop);

		this.sqlSessionTemplate.update("updateGameIssueLastIssueStop", map);
	}

	@Override
	public List<GameIssueEntity> getGameIssueByStatus(Long status, Long periodStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("periodStatus", periodStatus);
		List<GameIssue> gameIssueList = this.sqlSessionTemplate.selectList("getGameIssueByStatus", map);
		List<GameIssueEntity> gameIssueEntityList = new ArrayList<GameIssueEntity>();
		for (GameIssue gi : gameIssueList) {
			GameIssueEntity e = VOConverter4Task.gameIssue2GameIssueEntity(gi);
			gameIssueEntityList.add(e);
		}
		return gameIssueEntityList;
	}

	//获取规则生成日期的常规奖期
	private List<GameIssueTemplateEntity> getCommRuleTemplate(Long lotteryId, int i,Date fromDate) {
		GameIssueRuleEntity commRule = gameIssueRuleDao.getCommenGameIssueRuleByLottery(lotteryId,
				getDayValue(DateUtils.getStartTimeOfDate(DateUtils.addDays(DateUtils.getStartTimeOfCurrentDate(), i))),fromDate);
		if (commRule != null) {
			return gameIssueTemplateDao.getGameIssueTemplateByRuleId(commRule.getId());
		} else {
			return null;
		}
	}

	@Override
	public void generateGameIssue(GameSeries gs) {
		Long lotteryId = gs.getLotteryid();

		//目前只做时时彩
		//按天来插入，存在某天奖期数据则不生成当天的奖期记录
		// 是否存在需要修改已生成奖期的情况，这个逻辑需要确认，由于涉及到追号，需求应该避免
		//		if (gs.getLotterySeriesCode() == 1) {//时时彩系，时时彩系只有web奖期规则不一样，其他规则都是一样的
		//int intervalDay = 4;//生成奖期天数， 当要生成多个彩种奖期时，天数和显示编码等通过彩种不同进行区分即可
		int total = 0;
		int i = 0;
		//由于奖期编码规则由8位日期码+4位顺序码组成，这里按天循环插入数据，由于奖期编码是整个彩种每个奖期的唯一编码，是一个全局变量
		while (total < gs.getMaxCountIssue()) {
			//查询数据生成日期的常规奖期
			//查詢GAME_ISSUE_RULE取得ruleid，在拿ruleid去查GAME_ISSUE_TEMPLATE
			List<GameIssueTemplateEntity> getCommRuleTemplate = this.getCommRuleTemplate(lotteryId, i,Calendar.getInstance().getTime());
			
			//取得現在日期加上i天+官方第一期开奖时间時分秒
			Date generateStartTime = GameIssueGenerateUtil.getGenerateStartTime(getCommRuleTemplate, i, null);
			
			//取得現在日期加上i天+官方最后一期结束时间時分秒
			Date generateEndTime = GameIssueGenerateUtil.getGenerateEndTime(getCommRuleTemplate, i);
			
			//以第一期開獎時間與最後一期開獎時間 取得獎期
			List<GameIssue> gis = getGameIssues(lotteryId, generateStartTime, generateEndTime);
			if (gis.isEmpty()) {
				List<GameIssueEntity> issueEntityList = getGenerateGameIssueList(lotteryId, 2, generateStartTime, 1,gs.getTakeOffTime());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lotteryId", lotteryId);
				map.put("saleStartTime", generateStartTime);
				List<GameIssue> lastGissue = this.sqlSessionTemplate.selectList("getMaxGameIssuesByLotteryIdAndDate",
						map);
				List<GameIssue> insertGis = new ArrayList<GameIssue>();
				if (!issueEntityList.isEmpty()) {
					GameIssueGenerateUtil.insertEntityListToVoList(issueEntityList, lastGissue.isEmpty() ? null
							: lastGissue.get(0), insertGis, lotteryId, generateStartTime);
				}
				if (!insertGis.isEmpty()) {
					insert(insertGis);
					if (i != 0) {
						total += insertGis.size();
					}
				}

			} else {
				//除了当天，i=0不算到限制的最大期数里面，还要加一天不算进去，多生成一天的数据
				if (i > 1) {
					total += gis.size();
				}
				GameIssue first = gis.get(gis.size() - 1);//为开始时间的降序排列，取的最开始一条
				//某一天有数据的时候，如果第一期的lastIssue为空，则为手工生成的数据，需要补上上一期
				if (first.getLastIssue() == null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("lotteryId", lotteryId);
					map.put("saleStartTime", first.getSaleStartTime());
					GameIssue lastGissue = this.sqlSessionTemplate.selectOne("getMaxGameIssuesByLotteryIdAndDate", map);
					if (lastGissue != null) {
						map.put("id", first.getId());
						map.put("lastIssue", lastGissue.getIssueCode());
						map.put("saleStartTime", lastGissue.getSaleEndTime());
						this.sqlSessionTemplate.update("updateManualLastIssue", map);
					}
				}
			}
			i++;
		}
	}

	private List<GameIssue> getGameIssues(Long lotteryId, Date startTime, Date endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("startTime", DateUtils.format(startTime, DateUtils.DATETIME_FORMAT_PATTERN));
		map.put("endTime", DateUtils.format(endTime, DateUtils.DATETIME_FORMAT_PATTERN));
		List<GameIssue> gis = sqlSessionTemplate.selectList("getGameIssuesBylotteryId", map);
		return gis;
	}

	/** 
	* @Title: getGenerateGameIssueList 
	* @Description: 根据规则模版返回时间段内的奖期集合 ,注意获取常规奖期规则时忽略常规奖期的结束时间，即常规奖期永久有效
	* @param lotteryId 彩种id
	* @param queryType，1获取已生成的奖期  2：获取不包含待审核的规则 ，3 获取包含待审核的规则
	* @param startOfFromDate 第一期時間
	* @param intervalDay 1
	* @param takeOffTime 彩種下架時間
	* @return
	*/
	private List<GameIssueEntity> getGenerateGameIssueList(Long lotteryId, Integer queryType, Date startOfFromDate,
			int intervalDay,Date takeOffTime) {
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();
		for (int i = 1; i <= intervalDay; i++) {
			//获取查询时间间隔内的每一天的起始时间和结束时间
			Date tempFromDate = DateUtils.addDays(startOfFromDate, i - 1);
			Date tempToDate = DateUtils.getEndTimeOfDate(tempFromDate);
			GameIssueRuleEntity commenRules = new GameIssueRuleEntity();
			GameIssueRuleEntity specialRules = new GameIssueRuleEntity();
			List<GameIssueRuleEntity> stopRules = new ArrayList<GameIssueRuleEntity>();
			//分周一到周日分别生成每天的gameissueList
			if (queryType == 2) {//获取不包含待审核的规则
				commenRules = gameIssueRuleDao.getCommenGameIssueRuleByLottery(lotteryId, getDayValue(tempFromDate),tempFromDate);
				specialRules = gameIssueRuleDao.getSpecialGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate,
						tempToDate, getDayValue(tempFromDate));//获取当天的特殊
				stopRules = gameIssueRuleDao.getStopGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate, tempToDate,
						getDayValue(tempFromDate));//获取当天的休市规则
			}
			//			else if (queryType == 3) {//获取包含待审核的规则   2014-2-20 modify 生成奖期时不需要考虑待审核的情况
			//				commenRules = gameIssueRuleCheckDao.getCommenGameIssueRuleByRuleTypeAndDayWithUnAuditRule(lotteryId,
			//						tempFromDate, tempToDate, getDayValue(tempFromDate));//获取当天的普通规则
			//				specialRules = gameIssueRuleCheckDao.getSpecialOrStopGameIssueRuleByRuleTypeAndDayWithUnAuditRule(
			//						lotteryId, tempFromDate, tempToDate, getDayValue(tempFromDate));//获取当天的特殊或者休市规则
			//			}
			List<GameIssueTemplateEntity> gameIssueComTemplateEntities = new ArrayList<GameIssueTemplateEntity>();

			List<GameIssueTemplateEntity> gameIssueSpecialTemplateEntities = new ArrayList<GameIssueTemplateEntity>();
			if (commenRules != null && commenRules.getId() != null) {
				gameIssueComTemplateEntities = gameIssueTemplateDao.getGameIssueTemplateByRuleId(commenRules.getId());
			}
			if (specialRules != null && specialRules.getId() != null) {
				gameIssueSpecialTemplateEntities = gameIssueTemplateDao.getGameIssueTemplateByRuleId(specialRules
						.getId());
			}
			//产生常规规则
			if (!gameIssueComTemplateEntities.isEmpty()) {
				issueEntityList.addAll(generaterGameIssueList(lotteryId, tempFromDate, gameIssueComTemplateEntities));//根据得到的分段期间生成issueEntityList
			}
			//产生特殊规则
			List<GameIssueEntity> specialRuleList = new ArrayList<GameIssueEntity>();
			if (!gameIssueSpecialTemplateEntities.isEmpty()) {
				specialRuleList = generaterGameIssueList(lotteryId, tempFromDate, gameIssueSpecialTemplateEntities);
			}

			//组合常规与特殊规则
			issueEntityList = GameIssueGenerateUtil.handerCommRuleAndSpecialRule(issueEntityList, specialRuleList);
			//假如当天存在休市时间段时将奖期列表中的休市规则移除掉，生成连续的奖期规则
			issueEntityList = GameIssueGenerateUtil.removeStopIssue(issueEntityList, stopRules, true);
			issueEntityList = GameIssueGenerateUtil.removeStopSeriesIssue(issueEntityList, stopRules, true,takeOffTime);

			//链接最终有效规则的销售时间，webIssueCode
			this.generalIssueCode(issueEntityList, lotteryId, tempFromDate,
					gameIssueRuleDao.getStopGameIssueRuleByRuleTypeAndDay(lotteryId, null, null, null));
			//假如当天存在休市时间段时将奖期列表中的休市规则移除掉，生成不连续的奖期规则
			issueEntityList = GameIssueGenerateUtil.removeStopIssue(issueEntityList, stopRules, false);
			issueEntityList = GameIssueGenerateUtil.removeStopSeriesIssue(issueEntityList, stopRules, true,takeOffTime);

		}
		List<GameIssueRuleEntity> stopRules = new ArrayList<GameIssueRuleEntity>();
		if(issueEntityList.size() ==0){
			return issueEntityList;
		}
		GameIssueEntity firstTotalIssueEntity = issueEntityList.get(0);
		GameIssueEntity lastTotalIssueEntity = issueEntityList.get(issueEntityList.size() -1);
		//取得第一期的銷售時間和最後一期開獎時間
		Date tempFromDate = DateUtils.getStartTimeOfDate(firstTotalIssueEntity.getSaleStartTime());
		Date tempToDate = DateUtils.getEndTimeOfDate(lastTotalIssueEntity.getOpenDrawTime());
		//產生獎期時間內是否有休市規則
		stopRules = gameIssueRuleDao.getStopGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate, tempToDate,
				getDayValue(tempFromDate));//获取当天的休市规则
		//移除休市時間內獎期
		issueEntityList = GameIssueGenerateUtil.removeStopIssue(issueEntityList, stopRules, true);
		issueEntityList = GameIssueGenerateUtil.removeStopIssue(issueEntityList, stopRules, false);
		return issueEntityList;
	}

	/** 
	* @Title: generaterGameIssueList 
	* @Description: 根据模版生成奖期 
	* @param tempFromDate 奖期生成规则日期
	* @param gameIssueTemplateEntities 奖期模版
	* @return
	*/
	private List<GameIssueEntity> generaterGameIssueList(Long lotteryId, Date tempFromDate,
			List<GameIssueTemplateEntity> gameIssueTemplateEntities) {
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();
		int[] temp = new int[gameIssueTemplateEntities.size()];
		Date startDate = gameIssueTemplateEntities.get(0).getFirstAwardTime();
		Date endDate = gameIssueTemplateEntities.get(gameIssueTemplateEntities.size() - 1).getLastAwardTime();
		boolean isKuaTian = false;
		if (startDate.compareTo(endDate) == 0) {
			if (gameIssueTemplateEntities.get(gameIssueTemplateEntities.size() - 1).getFirstAwardTime()
					.after(gameIssueTemplateEntities.get(gameIssueTemplateEntities.size() - 1).getLastAwardTime())) {
				isKuaTian = true;
			}
		}
		for (int m = 0; m < gameIssueTemplateEntities.size(); m++) {
			long issues = 0;

			GameIssueEntity kuantian = null;
			if (lotteryId != 99108l && lotteryId != 99109l && lotteryId != 99401l) {
				if (gameIssueTemplateEntities.get(m).getFirstAwardTime()
						.after(gameIssueTemplateEntities.get(m).getLastAwardTime())) {//当官方最后一起结束时间<第一期开奖时间时，将最后一起结束时间加上一天处理
					issues = DateUtils.getTimeDiff(gameIssueTemplateEntities.get(m).getFirstAwardTime(),
							DateUtils.addDays(gameIssueTemplateEntities.get(m).getLastAwardTime(), 1))
							/ gameIssueTemplateEntities.get(m).getSalePeriodTime() + 1;//获取一个分段期间内总共有多少奖期

				} else {
					issues = DateUtils.getTimeDiff(gameIssueTemplateEntities.get(m).getFirstAwardTime(),
							gameIssueTemplateEntities.get(m).getLastAwardTime())
							/ gameIssueTemplateEntities.get(m).getSalePeriodTime() + 1;//获取一个分段期间内总共有多少奖期
				}
			} else {
				//每个分段规则就只有一期
				issues = 1;
			}
			temp[m] = (int) issues;
			for (int j = getValue(temp, m - 1) + 1; j <= getValue(temp, m); j++) {
				GameIssueEntity gameIssueEntity = new GameIssueEntity();//设置gameissue
				Date currentIssueEndTime = getCurrentDayIssueTime(tempFromDate, gameIssueTemplateEntities.get(m)
						.getFirstAwardTime());//2013年10月24日，此处在生成奖期结束时间时就减去提前销售时间

				if (j == (getValue(temp, m - 1) + 1)) {//当奖期为分段开始的第一期时，销售开始时间分段销售开始时间，结束时间为第一次开奖时间
					gameIssueEntity.setSaleEndTime(DateUtils.addSeconds(currentIssueEndTime, -gameIssueTemplateEntities
							.get(m).getScheduleStopTime().intValue()));
					gameIssueEntity.setOpenDrawTime(currentIssueEndTime);
					//整个规则生成的第一期，开始时间没法取上期结束时间
					if (m == 0) {
						if (isKuaTian) {
							kuantian = gameIssueEntity;
							continue;
						}
						if (lotteryId != 99108l && lotteryId != 99109l && lotteryId != 99401l) {
							gameIssueEntity.setSaleStartTime(DateUtils.addSeconds(gameIssueEntity.getSaleEndTime(),
									-gameIssueTemplateEntities.get(m).getSalePeriodTime().intValue()));
						} else {
							//没有周期，第一期的开始时间随便给，生成的时候会换成上一期的结束时间
							gameIssueEntity.setSaleStartTime(DateUtils.addDays(gameIssueEntity.getSaleEndTime(),
									lotteryId != 99401l ? -1 : -2));
						}
					} else {
						//为上一个奖期的销售开始时间
						gameIssueEntity.setSaleStartTime(issueEntityList.get(issueEntityList.size() - 1)
								.getSaleEndTime());
					}

				} else {//非分段第一期的开奖周期的开始时间和结束时间设置
					gameIssueEntity.setSaleStartTime(!issueEntityList.isEmpty() ? issueEntityList.get(
							issueEntityList.size() - 1).getSaleEndTime() : kuantian.getSaleEndTime());
					gameIssueEntity.setSaleEndTime(DateUtils.addSeconds(gameIssueEntity.getSaleStartTime(),
							gameIssueTemplateEntities.get(m).getSalePeriodTime().intValue()));
					gameIssueEntity.setOpenDrawTime(DateUtils.addSeconds(gameIssueEntity.getSaleEndTime(),
							gameIssueTemplateEntities.get(m).getScheduleStopTime().intValue()));
				}
				issueEntityList.add(gameIssueEntity);
			}
		}
		return issueEntityList;
	}

	private void generalIssueCode(List<GameIssueEntity> list, Long lotteryId, Date tempFromDate,
			List<GameIssueRuleEntity> stopRules) {
		if (!list.isEmpty()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lotteryId", lotteryId);
			map.put("saleStartTime", tempFromDate);
			GameIssue gis = null;
			List<GameIssue> gisList = this.sqlSessionTemplate.selectList("getMaxGameIssuesByLotteryIdAndDate", map);
			if (!gisList.isEmpty()) {
				gis = gisList.get(0);
			}
			for (int j = 1; j <= list.size(); j++) {
				GameIssueEntity gameIssueEntity = list.get(j - 1);
				//第一期的销售开始时间为最大奖期的结束时间
				if (j == 1) {
					//GameIssue gis = this.sqlSessionTemplate.selectOne("getMaxGameIssuesByLotteryId", lotteryId);
					gameIssueEntity.setSaleStartTime(gis == null ? gameIssueEntity.getSaleStartTime() : gis
							.getSaleEndTime());
				}//否则为上一期的结束时间
				else {
					gameIssueEntity.setSaleStartTime(list.get(j - 2).getSaleEndTime());
				}
				if (lotteryId == 99105)//当为黑龙江时时彩时，web显示奖期比较特殊，这里做特殊处理，当数据库中存在黑龙江时时彩的奖期记录时，以大期数记录作为生成的起始记录，
				//当数据库中不存在记录时，以配置文件中配置的记录作为起始记录
				{
					//获取最新一条黑龙江时时彩奖期数据
					//GameIssue gis = this.sqlSessionTemplate.selectOne("getMaxGameIssuesByLotteryId", lotteryId);
					String tempIssueCode;
					if (gis == null) {
						tempIssueCode = HLJStartGenerateIssueCode;
					} else {
						tempIssueCode = gis.getWebIssueCode();
					}
					String issueCode = (Long.valueOf(tempIssueCode) + j) + "";
					//生成黑龙江时时彩的web显示期号
					gameIssueEntity.setWebIssueCode(IssueCodeUtil.paddingChar(issueCode, 7, '0', false));
				} else if (lotteryId == 99201) {//北京快乐8
					//获取最新一条北京快乐8奖期数据
					//GameIssue gis = this.sqlSessionTemplate.selectOne("getMaxGameIssuesByLotteryId", lotteryId);
					String tempIssueCode;
					if (gis == null) {
						tempIssueCode = BJKL8GenerateStartIssueCode;
					} else {
						tempIssueCode = gis.getWebIssueCode();
					}
					gameIssueEntity.setWebIssueCode(String.valueOf(Long.valueOf(tempIssueCode) + j));
				} else if (lotteryId == 99108 || lotteryId == 99109 || lotteryId == 99401) {//福彩3D年+开奖次数累计
					/*//获取最新一条黑龙江时时彩奖期数据
					String yearCode = DateUtils.getYear(new Date()) + "";
					map.put("yearCode", yearCode);
					GameIssue gisYear = this.sqlSessionTemplate.selectOne("getMaxGameIssuesByLotteryIdAndYearAndDate",
							map);
					String tempIssueCode;
					if (gisYear == null) {
						tempIssueCode = yearCode + "000";
					} else {
						tempIssueCode = gisYear.getWebIssueCode();
					}
					String issueCode = (Long.valueOf(tempIssueCode) + j) + "";
					//生成黑龙江时时彩的web显示期号
					gameIssueEntity.setWebIssueCode(issueCode);*/
					GameIssueGenerateUtil.generateWebIssueCode(gameIssueEntity, stopRules, lotteryId == 99401);
				}else if (lotteryId == 99701) {//六合彩
					String lastWebIssueCode =  "";
					try {
						lastWebIssueCode = this.getGameIssueLastWebIssueCode(lotteryId,tempFromDate);
					} catch (Exception e) {
						log.error("query last web isscode error" , e);
					}
					GameIssueGenerateUtil.generateLhcWebIssueCode(gameIssueEntity, lastWebIssueCode);
				} else {
					gameIssueEntity.setWebIssueCode(IssueCodeUtil.createWebIssueCode(lotteryId, tempFromDate, j));
				}
			}
		}
	}

	/** 
	* @Title: getValue 
	* @Description:计算数组前M位的和值
	* @param temp
	* @param m
	* @return
	*/
	private int getValue(int[] temp, int m) {
		if (m < 0) {
			return 0;
		}
		int n = temp[0];
		if (m == 0) {
			return n;
		} else {
			for (int i = 1; i <= m; i++) {
				n += temp[i];
			}
		}
		return n;

	}

	/** 
	* @Title: getDayValue 
	* @Description: 获取星期几对应的数字 
	* @param date
	* @return
	*/
	private int getDayValue(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.DAY_OF_WEEK);
		if (i == 1) {
			return 7;
		} else {
			return i - 1;
		}
	}

	/** 
	* @Title: getCurrentDayIssueTime 
	* @Description: 由于模版中的时间只有时分秒才是我们需要的，我们需要和当天的年月日组成我们需要的时间
	* @param yearDate,saleDate
	* @return
	*/
	private Date getCurrentDayIssueTime(Date yearDate, Date saleDate) {
		int hour = DateUtils.getHours(saleDate);
		int minutes = DateUtils.getMinutes(saleDate);
		int second = DateUtils.getSeconds(saleDate);
		int year = DateUtils.getYear(yearDate);
		int month = DateUtils.getMonth(yearDate);
		int day = DateUtils.getDay(yearDate);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, second);

		return DateUtils.parse(DateUtils.format(cal.getTime(), DateUtils.DATETIME_FORMAT_PATTERN),
				DateUtils.DATETIME_FORMAT_PATTERN);
	}

	@Override
	public List<GameIssue> getEarlierSuspendedGameIssue(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getEarlierSuspendedGameIssue", map);
	}

	@Override
	public void updateGameIssue(GameIssue gameIssue) throws Exception {
		try {
			this.sqlSessionTemplate.update(getQueryPath("updateGameIssue"), gameIssue);
		} catch (Exception e) {
			log.error("updateGameIssue", e);
			throw e;
		}
	}

	@Override
	public void updataGameIssueSaleEnd(GameIssue gameIssue) throws Exception {
		try {
			this.sqlSessionTemplate.update(getQueryPath("updataGameIssueSaleEnd"), gameIssue);
		} catch (Exception e) {
			log.error("updataGameIssueSaleEnd", e);
			throw e;
		}
	}
	@Override
	public int updataGameIssueSaleStart(GameIssue issue, Integer preStatus) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("issue", issue); 
		map.put("preStatus", preStatus); 
		return this.sqlSessionTemplate.update(getQueryPath("updataGameIssueSaleStart"), map);
	}

	public void updateTry(Long count, Long id) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("id", id);
		this.sqlSessionTemplate.update(getQueryPath("updateTry"), map);

	}

	@Override
	public GameIssueEntity queryGameIssue(Long lotteryid, Long issueCode) {
		//假如issuecode为null，则根据当前时间获取当前奖期，假如当前时间段没没有任何奖期则获取下一条即将开奖奖期,
		GameIssue gameIssue = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryid);
		if (null == issueCode) {
			map.put("time", new Date());
			gameIssue = this.sqlSessionTemplate.selectOne("getGameIssueByTimeAndlotteryId", map);
			if (null == gameIssue) {
				gameIssue = this.sqlSessionTemplate.selectOne("getNextGameIssueByTimeAndlotteryId", map);
			}
		} else {
			map.put("issueCode", issueCode);
			gameIssue = this.sqlSessionTemplate.selectOne("getGameIssueByIssueCodeAndlotteryId", map);
			if (null == gameIssue) {
				throw new GameIssueNotExistErrorException();
			}
		}
		GameIssueEntity gie = VOConverter.gameIssue2GameIssueEntity(gameIssue);
		return gie;
	}

	@Override
	public GameIssueEntity queryGameIssue(Long lotteryid, String webIssueCode) {
		//假如webIssueCode为null，则根据当前时间获取当前奖期，假如当前时间段没没有任何奖期则获取下一条即将开奖奖期,
		GameIssue gameIssue = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryid);
		if (null == webIssueCode) {
			map.put("time", new Date());
			gameIssue = this.sqlSessionTemplate.selectOne("getGameIssueByTimeAndlotteryId", map);
			if (null == gameIssue) {
				gameIssue = this.sqlSessionTemplate.selectOne("getNextGameIssueByTimeAndlotteryId", map);
			}
		} else {
			map.put("webIssueCode", webIssueCode);
			gameIssue = this.sqlSessionTemplate.selectOne("getGameIssueByWebIssueCodeAndlotteryId", map);
			if (null == gameIssue) {
				throw new GameIssueNotExistErrorException();
			}
		}
		GameIssueEntity gie = VOConverter.gameIssue2GameIssueEntity(gameIssue);
		return gie;
	}
	
	@Override
	public GameIssueEntity getNextGameIssueByPlanIdAndIssueCode(Long currentIssuCode, Long planId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planId", planId);
		map.put("issueCode", currentIssuCode);

		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("getNextGameIssueByPlanIdAndIssueCode", map);
		if (null != gameIssue) {
			return VOConverter.gameIssue2GameIssueEntity(gameIssue);
		}
		return null;
	}

	@Override
	public List<GameIssueEntity> queryGameIssueByStatusAndSalesEndTimeForBeginSaleStart(Long status, Long periodStatus) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("periodStatus", periodStatus);
		map.put("date", new Date());
		List<GameIssue> gameIssueList = this.sqlSessionTemplate.selectList(
				"getGameIssueByStatusAndSalesEndTimeForStartSale", map);
		List<GameIssueEntity> gameIssueEntityList = new ArrayList<GameIssueEntity>();
		for (GameIssue gi : gameIssueList) {
			GameIssueEntity e = VOConverter4Task.gameIssue2GameIssueEntity(gi);
			gameIssueEntityList.add(e);
		}
		return gameIssueEntityList;

	}

	public List<GameIssue> getNoAwardNumberSaleEndIssue(Date time) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("time", time);
		return this.sqlSessionTemplate.selectList("getNoAwardNumberSaleEndIssue", map);
	}

	@Override
	public List<GameIssueEntity> getGameIssueByLotteryIdAndDay(Long lotteryId, String startDay, String endDay)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("startDay", startDay);
		map.put("endDay", endDay);
		List<GameIssue> gameIssueList = this.sqlSessionTemplate.selectList("getGameIssueByLotteryIdAndDay", map);
		if (gameIssueList != null && gameIssueList.size() > 0) {
			List<GameIssueEntity> gameIssueEntityList = new ArrayList<GameIssueEntity>();
			for (GameIssue gi : gameIssueList) {
				GameIssueEntity e = VOConverter4Task.gameIssue2GameIssueEntity(gi);
				gameIssueEntityList.add(e);
			}
			return gameIssueEntityList;
		}
		return null;
	}

	@Override
	public GameIssueEntity getSsqGameIssueForMail(Date saleEndDate) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", 99401);
		map.put("startDay", DateUtils.addMinutes(saleEndDate, -7));
		map.put("endDay", saleEndDate);
		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("getSsqGameIssueForMail", map);
		if (gameIssue != null) {
			return VOConverter4Task.gameIssue2GameIssueEntity(gameIssue);
		}
		return null;
	}

	@Override
	public String getUserAccount(Long userId) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUserAccount"), userId);
	}
	@Override
	public List<GameIssue> getNoNumberLatestByLotteryIdAndTime(Long lotteryId,Date time)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("nowTime", time);
		return this.sqlSessionTemplate.selectList("getNoNumberLatestByLotteryIdAndTime", map);
	}
	@Override
	public String getGameIssueLastWebIssueCode(Long lotteryId, Date date)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("date", date);		
		return this.sqlSessionTemplate.selectOne("getGameIssueLastWebIssueCode", map);
	}
}
