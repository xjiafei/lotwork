package com.winterframework.firefrog.game.dao.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameIssueGenerateUtil;
import com.winterframework.firefrog.common.util.PageListUtil;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameIssueRuleCheckDao;
import com.winterframework.firefrog.game.dao.IGameIssueRuleDao;
import com.winterframework.firefrog.game.dao.IGameIssueTemplateCheckDao;
import com.winterframework.firefrog.game.dao.IGameIssueTemplateDao;
import com.winterframework.firefrog.game.dao.vo.GameExceptionAuditOrder;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.firefrog.game.exception.GameIssueNotExistErrorException;
import com.winterframework.firefrog.game.exception.QueryBeginTimeAfterEndTimeException;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameIssueListQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateRequest;
import com.winterframework.firefrog.game.web.dto.GameIssueManualGenerateResponse;
import com.winterframework.firefrog.game.web.dto.HistoryLotteryAwardRequest;
import com.winterframework.firefrog.game.web.util.IssueCodeUtil;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameIssueDaoImpl")
public class GameIssueDaoImpl extends BaseIbatis3Dao<GameIssue> implements IGameIssueDao {

	@Resource(name = "gameIssueRuleDaoImpl")
	private IGameIssueRuleDao gameIssueRuleDao;

	@Resource(name = "gameIssueTemplateDaoImpl")
	private IGameIssueTemplateDao gameIssueTemplateDao;

	@Resource(name = "gameIssueTemplateCheckDaoImpl")
	private IGameIssueTemplateCheckDao gameIssueTemplateCheckDao;

	@Resource(name = "gameIssueRuleCheckDaoImpl")
	private IGameIssueRuleCheckDao gameIssueRuleCheckDao;

	@PropertyConfig(value = "HLJGenerateStartIssueCode")
	private String HLJStartGenerateIssueCode;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@PropertyConfig(value = "BJKL8GenerateStartIssueCode")
	private String BJKL8GenerateStartIssueCode;

	@Override
	public GameIssue getGameIssueByIssueCode(Long issueCode) throws Exception {

		return this.sqlSessionTemplate.selectOne("getGameIssueByGameIssueCode", issueCode);
	}

	@Override
	public GameIssue getMaxGameIssuesByLotteryId(Long lotteryId) throws Exception {
		return this.sqlSessionTemplate.selectOne("getMaxGameIssuesByLotteryId", lotteryId);
	}

	@Override
	public GameIssue queryCurrentGameIssue(Long lotteryId) throws Exception {

		return this.sqlSessionTemplate.selectOne("queryCurrentGameIssue", lotteryId);
	}

	@Override
	public GameIssueEntity queryGameIssue(Long lotteryId, Long issueCode) throws Exception {

		//假如issuecode为null，则根据当前时间获取当前奖期，假如当前时间段没没有任何奖期则获取下一条即将开奖奖期,
		GameIssue gameIssue = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
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
	public GameIssueEntity queryGameIssue(Long lotteryid, String webIssueCode) throws Exception {
		//假如issuecode为null，则根据当前时间获取当前奖期，假如当前时间段没没有任何奖期则获取下一条即将开奖奖期,
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
	public List<GameIssueEntity> queryGameIssues(GameIssueListQueryRequest gameIssueListQueryRequest,Date takeOffTime) {
		Long lotteryId = gameIssueListQueryRequest.getLotteryId();
		Integer queryType = gameIssueListQueryRequest.getQueryType();
		Date fromDate = DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowStartTime());
		Date startOfFromDate = DateUtils.getStartTimeOfDate(fromDate);
		Date toDate = DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowEndTime());
		Date endOfToDate = DateUtils.getEndTimeOfDate(toDate);
		//计算查询参数的间隔天数
		//Date startOfFromDate = fromDate;
		//Date endOfToDate = toDate;
		int intervalDay = (int) DateUtils.calcDateBetween(startOfFromDate, endOfToDate);

		Map<String, Object> queryParamMap = makeQueryParamMap(gameIssueListQueryRequest);

		//分三种情形过去奖期查询都是查询数据库，未来奖期查询则是代码预生成issuelist,生成原则：分周一到周日，同一天只能存在一个特例奖期或者一个普通奖期
		//1:查询过去奖期
		if (toDate.before(fromDate)) {
			throw new QueryBeginTimeAfterEndTimeException();
		}

		//仅显示已生成将期
		if (queryType == 1) {
			return queryPastIssue(queryParamMap);
		} else {//查询未来奖期 注意奖期规则中如果没有结束时间，则为无限期
			/*if(fromDate.after(DateUtils.currentDate())){
				throw new FutureGameIssueQueryBeginTimeBeforeStartTimeOfCurrentDayException();
			}*/
			//return generateGameIssueListForQuery(pr, lotteryId, queryType, startOfFromDate, endOfToDate, intervalDay);
			List<GameIssueEntity> issueEntityList = getGenerateGameIssueListForQuery(lotteryId, queryType,
					startOfFromDate, intervalDay,takeOffTime);
			List<GameIssueEntity> removeIssueEntityList = new ArrayList<GameIssueEntity>();
			if (issueEntityList != null) {
				for (GameIssueEntity g : issueEntityList) {
					if (g.getSaleEndTime().before(fromDate) || g.getSaleEndTime().after(toDate)) {
						removeIssueEntityList.add(g);
					}
				}
				issueEntityList.removeAll(removeIssueEntityList);
			}
			return issueEntityList;
		}
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
	public GameIssueManualGenerateResponse manualGenerateIssues(GameIssueManualGenerateRequest res,Date takeOffTime) throws Exception {
		Date fromDate = DataConverterUtil.convertLong2Date(res.getShowStartTime());
		Date startOfFromDate = DateUtils.getStartTimeOfDate(fromDate);
		Date toDate = DataConverterUtil.convertLong2Date(res.getShowEndTime());
		Date endOfToDate = DateUtils.getEndTimeOfDate(toDate);
		Long lotteryId = res.getLotteryId();
		GameIssueManualGenerateResponse resp = new GameIssueManualGenerateResponse();
		GameIssue saleGameIssue = queryCurrentSaleIssue(lotteryId, fromDate, toDate);
		if (saleGameIssue != null) {
			resp.setMessage("销售结束时间为"
					+ DateUtils.format(saleGameIssue.getSaleEndTime(), DateUtils.DATETIME_FORMAT_PATTERN) + "的奖期"
					+ saleGameIssue.getWebIssueCode() + "已经在售,不能重新生成,请选择在售奖期以后的时间");
			return resp;
		}
		/*GameIssue planGameIssue = this.queryMaxPlanIssue(lotteryId, fromDate, toDate);
		if(planGameIssue!= null){
			resp.setMessage("销售结束时间为"
					+ DateUtils.format(planGameIssue.getSaleEndTime(), DateUtils.DATETIME_FORMAT_PATTERN) + "的奖期"
					+ planGameIssue.getWebIssueCode() + "已经追号,请选择追号奖期以后的删除时间");
			return resp;
		}*/
		int intervalDay = (int) DateUtils.calcDateBetween(startOfFromDate, endOfToDate);
		List<GameIssueEntity> lastGameIssueEntity = new ArrayList<GameIssueEntity>();
		List<GameIssue> lastIssue = new ArrayList<GameIssue>();
		Long total = 0l;
		int j = 0;
		for (int i = 0; i < intervalDay; i++) {
			try {
				List<GameIssueTemplateEntity> getCommRuleTemplate = this.getCommRuleTemplate(lotteryId, i,fromDate);
				Date generateStartTime = GameIssueGenerateUtil.getGenerateStartTime(getCommRuleTemplate, i, fromDate);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lotteryId", lotteryId);
				map.put("saleStartTime", generateStartTime);
				this.sqlSessionTemplate.delete("deleteLastDate", map);
				Date tempToDate = DateUtils.getEndTimeOfDate(generateStartTime);
				List<GameIssueRuleEntity> stopRules = new ArrayList<GameIssueRuleEntity>();
				List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();
				if(lotteryId!=99701){
					issueEntityList = this.generateGameIssueListByRule(lotteryId, 2,
							generateStartTime, tempToDate, stopRules,takeOffTime);
				}else{
					//六合彩走此路徑,但理論低頻應該統一歷程
					issueEntityList = this.generateLowFreqGameIssueListByRule(lotteryId, 2,
							generateStartTime, tempToDate, stopRules);
				}
				//第一期有直接给开始期号，以后每天不需按开始期号来
				if (!issueEntityList.isEmpty()) {
					this.generalIssueCodeForManual(issueEntityList, lotteryId, generateStartTime,
							lastGameIssueEntity.isEmpty() ? null : lastGameIssueEntity.get(j - 1),
							!lastGameIssueEntity.isEmpty() ? null : res.getStartIsssueCode(),
							gameIssueRuleDao.getStopGameIssueRuleByRuleTypeAndDay(lotteryId, null, null, null));
					issueEntityList = GameIssueGenerateUtil.removeStopIssue(issueEntityList, stopRules, false);
					issueEntityList = GameIssueGenerateUtil.removeStopSeriesIssue(issueEntityList, stopRules, false,takeOffTime);
					lastGameIssueEntity.add(issueEntityList.get(issueEntityList.size() - 1));

				}
				List<GameIssue> insertGis = new ArrayList<GameIssue>();
				if (!issueEntityList.isEmpty()) {
					GameIssueGenerateUtil.insertEntityListToVoList(issueEntityList, lastIssue.isEmpty() ? null
							: lastIssue.get(j - 1), insertGis, lotteryId, generateStartTime);
					//六合彩需要初始化第一期lastIssueCode
					if(lotteryId==99701 && lastIssue.isEmpty()){
						GameIssue preIssue = getGameIssueLastByDate(lotteryId, issueEntityList.get(0).getSaleStartTime());
						insertGis.get(0).setLastIssue(preIssue.getIssueCode());
					}
				}
				//第一天的奖期需要判断开始期号作为第一期还是作为实际期号
				if (i == 0 && res.getStartIsssueNumber() != 0 && res.getStartIsssueNumber() == 2) {
					if (res.getStartIsssueCode().contains("-")) {
						List<GameIssue> startGameIssues = new ArrayList<GameIssue>();
						String[] codeArray = res.getStartIsssueCode().split("-");
						String numberStr = codeArray[1];
						Long number = Long.valueOf(numberStr);
						for (int k = 0; k < insertGis.size(); k++) {
							GameIssue formateGameIssue = insertGis.get(k);
							formateGameIssue.setIssueCode(formateGameIssue.getIssueCode() - number + 1);
							String[] webIssueCode = formateGameIssue.getWebIssueCode().split("-");
							Long webNumber = Long.valueOf(webIssueCode[1]) - number + 1;
							String webcodeStr = String.valueOf(webNumber);
							while (webcodeStr.length() < numberStr.length()) {
								webcodeStr = "0" + webcodeStr;
							}
							formateGameIssue.setWebIssueCode(webIssueCode[0] + webcodeStr);
							if (webNumber < number) {
								continue;
							}
							startGameIssues.add(formateGameIssue);
						}
						insertGis = startGameIssues;
					}

				}
				if (!insertGis.isEmpty()) {
					insert(insertGis);
					lastIssue.add(insertGis.get(insertGis.size() - 1));
					total += insertGis.size();
					j++;
				}
			} catch (Exception e) {
				throw e;
			}

		}

		resp.setStartDate(DateUtils.format(fromDate, DateUtils.DATE_FORMAT_PATTERN));
		resp.setEndDate(DateUtils.format(toDate, DateUtils.DATE_FORMAT_PATTERN));
		resp.setGenerateDays(intervalDay);
		resp.setGenerateIssues(total);
		return resp;
	}

	private void generalIssueCodeForManual(List<GameIssueEntity> list, Long lotteryId, Date tempFromDate,
			GameIssueEntity gis, String startIssueCode, List<GameIssueRuleEntity> stopRules) {
		if (!list.isEmpty()) {

			for (int j = 1; j <= list.size(); j++) {
				GameIssueEntity gameIssueEntity = list.get(j - 1);
				//第一期的销售开始时间为最大奖期的结束时间
				if (j == 1) {
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
					String tempIssueCode;
					if (gis == null) {
						tempIssueCode = String.valueOf(Long.valueOf(startIssueCode) - 1);
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
						tempIssueCode = String.valueOf(Long.valueOf(startIssueCode) - 1);
					} else {
						tempIssueCode = gis.getWebIssueCode();
					}
					gameIssueEntity.setWebIssueCode(String.valueOf(Long.valueOf(tempIssueCode) + j));
				} else if (lotteryId == 99108 || lotteryId == 99109 || lotteryId == 99401) {//福彩3D年+开奖次数累计
					/*//获取最新一条黑龙江时时彩奖期数据
					String tempIssueCode;
					if (gis == null) {
						tempIssueCode = String.valueOf(Long.valueOf(startIssueCode) - 1);
					} else {
						tempIssueCode = gis.getWebIssueCode();
					}
					String issueCode = (Long.valueOf(tempIssueCode) + j) + "";*/
					//生成黑龙江时时彩的web显示期号
					GameIssueGenerateUtil.generateWebIssueCode(gameIssueEntity, stopRules, lotteryId == 99401);
				}  else if (lotteryId == 99701) {//六合彩
					String lastWebIssueCode =  "";
					try {
						lastWebIssueCode = this.getGameIssueLastWebIssueCode(lotteryId,tempFromDate);
					} catch (Exception e) {
						log.error("query last web isscode error" , e);
					}
					GameIssueGenerateUtil.generateLhcWebIssueCode(gameIssueEntity, lastWebIssueCode);
				} /*else if (lotteryId == 99401l) {
					if (gis == null) {
						String year = startIssueCode.substring(0, 4);
						Long index = Long.valueOf(startIssueCode.substring(4, startIssueCode.length())) + j - 1;
						gameIssueEntity.setWebIssueCode(year + index);

					} else {
						String tempIssueCode = gis.getWebIssueCode();
						String year = "";//DateUtils.parse(gameIssueEntity.getSaleEndTime(), DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR);
						Long index = Long.valueOf(startIssueCode.substring(4, tempIssueCode.length())) + j;
						gameIssueEntity.setWebIssueCode(year + index);
					}
					}*/else {
					if (gis == null) {
						String tempIssueCode = startIssueCode;
						if (tempIssueCode.contains("-")) {
							String[] codeArray = tempIssueCode.split("-");
							String number = String.valueOf(Long.valueOf(codeArray[1]) + j - 1);
							int tag = codeArray[1].length() - number.length();
							for (int i = 0; i < tag; i++) {
								number = "0" + number;
							}
							String tempFromDateStr = DateUtils.format(tempFromDate,
									DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR);
							if (tempFromDateStr.equals(codeArray[0])) {
								gameIssueEntity.setWebIssueCode(codeArray[0] + "-" + number);
							} else {
								gameIssueEntity.setWebIssueCode(IssueCodeUtil.createWebIssueCode(lotteryId,
										tempFromDate, j));
							}

						} else {
							gameIssueEntity.setWebIssueCode(String.valueOf(Long.valueOf(tempIssueCode) + j - 1));
						}

					} else {
						gameIssueEntity.setWebIssueCode(IssueCodeUtil.createWebIssueCode(lotteryId, tempFromDate, j));
					}
				}
			}
		}
	}

	@Override
	public Page<GameIssueEntity> queryGameIssues(PageRequest<GameIssueListQueryRequest> pr,Date takeOffTime) {
		GameIssueListQueryRequest gameIssueListQueryRequest = pr.getSearchDo();
		Long lotteryId = gameIssueListQueryRequest.getLotteryId();
		Integer queryType = gameIssueListQueryRequest.getQueryType();
		Date fromDate = DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowStartTime());

		Date toDate = DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowEndTime());

		//计算查询参数的间隔天数
		//Date startOfFromDate = fromDate;
		//Date endOfToDate = toDate;

		Map<String, Object> queryParamMap = makeQueryParamMap(gameIssueListQueryRequest);

		//分三种情形过去奖期查询都是查询数据库，未来奖期查询则是代码预生成issuelist,生成原则：分周一到周日，同一天只能存在一个特例奖期或者一个普通奖期
		//1:查询过去奖期
		if (toDate.before(fromDate)) {
			throw new QueryBeginTimeAfterEndTimeException();
		}
		//仅显示已生成将期
		if (queryType == 1) {
			return queryPastIssue(pr, queryParamMap);
		} else {//查询未来奖期 注意奖期规则中如果没有结束时间，则为无限期
			/*if(fromDate.after(DateUtils.currentDate())){
				throw new FutureGameIssueQueryBeginTimeBeforeStartTimeOfCurrentDayException();
			}*/
			return generateGameIssueListForQuery(pr, lotteryId, queryType,takeOffTime);
		}
	}

	private Page<GameIssueEntity> generateGameIssueListForQuery(PageRequest<GameIssueListQueryRequest> pr,
			Long lotteryId, Integer queryType,Date takeOffTime) {
		GameIssueListQueryRequest gameIssueListQueryRequest = pr.getSearchDo();
		Date fromDate = DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowStartTime());
		Date toDate = DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowEndTime());
		Date startOfFromDate = DateUtils.getStartTimeOfDate(fromDate);
		Date endOfToDate = DateUtils.getEndTimeOfDate(toDate);
		int intervalDay = (int) DateUtils.calcDateBetween(startOfFromDate, endOfToDate);
		List<GameIssueEntity> issueEntityList = getGenerateGameIssueListForQuery(lotteryId, queryType, startOfFromDate,
				intervalDay,takeOffTime);//获取生成的未来奖期列表，用于分页或者批处理中的奖期记录列表
		//		System.err.println(issueEntityList.size());
		//		for (GameIssueEntity g : issueEntityList) {
		//			System.err.println(g.getWebIssueCode() + "-"
		//					+ DateUtils.format(g.getSaleStartTime(), DateUtils.DATETIME_FORMAT_PATTERN) + "-"
		//					+ DateUtils.format(g.getSaleEndTime(), DateUtils.DATETIME_FORMAT_PATTERN));
		//		}

		List<GameIssueEntity> removeIssueEntityList = new ArrayList<GameIssueEntity>();
		if (issueEntityList != null) {
			for (GameIssueEntity g : issueEntityList) {
				if (g.getSaleEndTime().before(fromDate) || g.getSaleEndTime().after(toDate)) {
					removeIssueEntityList.add(g);
				}
			}
			issueEntityList.removeAll(removeIssueEntityList);
		}

		Page<GameIssueEntity> page = new Page<GameIssueEntity>(pr.getPageNumber(), pr.getPageSize(),
				issueEntityList.size());//系统page
		PageListUtil<GameIssueEntity> pageList = new PageListUtil<GameIssueEntity>(issueEntityList, pr.getPageNumber(),
				pr.getPageSize());//分页工具获取list
		page.setResult(pageList.getPage(pr.getPageNumber()));
		return page;
	}

	private List<GameIssueEntity> getGameIssues(Long lotteryId, Date startTime, Date endTime) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("startTime", DateUtils.format(startTime, DateUtils.DATETIME_FORMAT_PATTERN));
		map.put("endTime", DateUtils.format(endTime, DateUtils.DATETIME_FORMAT_PATTERN));
		List<GameIssue> gis = sqlSessionTemplate.selectList("getGameIssuesBylotteryId", map);
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();
		for (GameIssue gi : gis) {
			GameIssueEntity gameIssueEntity = VOConverter.gameIssue2GameIssueEntity(gi);
			issueEntityList.add(gameIssueEntity);
		}
		return issueEntityList;
	}

	private List<GameIssueEntity> generateGameIssueListByRule(Long lotteryId, Integer queryType, Date tempFromDate,
			Date tempToDate, List<GameIssueRuleEntity> stopRules,Date takeOffTime) {
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();
		GameIssueRuleEntity commenRules = new GameIssueRuleEntity();
		GameIssueRuleEntity specialRules = new GameIssueRuleEntity();

		List<GameIssueTemplateEntity> gameIssueComTemplateEntities = new ArrayList<GameIssueTemplateEntity>();
		List<GameIssueTemplateEntity> gameIssueSpecialTemplateEntities = new ArrayList<GameIssueTemplateEntity>();
		//分周一到周日分别生成每天的gameissueList
		//获取不包含待审核的规则
		commenRules = gameIssueRuleDao.getCommenGameIssueRuleByLottery(lotteryId, getDayValue(tempFromDate),tempFromDate);
		specialRules = gameIssueRuleDao.getSpecialGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate, tempToDate,
				getDayValue(tempFromDate));//获取当天的特殊
		// add 一天 for  才能找出新疆特殊規則 但不影響現有ㄍㄨ
		Date currenttodate = new Date(tempToDate.getTime() + (1000 * 60 * 60 * 24));
		stopRules.addAll(gameIssueRuleDao.getStopGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate, currenttodate,
				getDayValue(tempFromDate)));//获取当天的休市规则
		if (commenRules != null && commenRules.getId() != null) {
			gameIssueComTemplateEntities = gameIssueTemplateDao.getGameIssueTemplateByRuleId(commenRules.getId());
		}
		if (specialRules != null && specialRules.getId() != null) {
			gameIssueSpecialTemplateEntities = gameIssueTemplateDao.getGameIssueTemplateByRuleId(specialRules.getId());
		}

		if (queryType == 3) {//获取包含待审核的规则,按照约定,未来显示待审核的,未来没有待审核则显示已审核的

			commenRules = gameIssueRuleCheckDao.getCommenGameIssueRuleByLottery(lotteryId, getDayValue(tempFromDate));//获取当天的普通规则
			specialRules = gameIssueRuleCheckDao.getSpecialGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate,
					tempToDate, getDayValue(tempFromDate));//获取当天的特殊或者休市规则
			List<GameIssueRuleEntity> stopRulesAudit = gameIssueRuleCheckDao.getStopGameIssueRuleByRuleTypeAndDay(
					lotteryId, tempFromDate, tempToDate, getDayValue(tempFromDate));//获取当天的特殊或者休市规则

			if (commenRules != null && commenRules.getId() != null) {
				gameIssueComTemplateEntities = gameIssueTemplateCheckDao.getGameIssueTemplateCheckByRuleId(commenRules
						.getId());
			}
			if (specialRules != null && specialRules.getId() != null) {
				gameIssueSpecialTemplateEntities = gameIssueTemplateCheckDao
						.getGameIssueTemplateCheckByRuleId(specialRules.getId());
			}
			if (!stopRulesAudit.isEmpty()) {
				stopRules.clear();
				stopRules.addAll(stopRulesAudit);
			}

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
		//假如当天存在休市时间段时将奖期列表中的休市规则移除掉，生成不连续的奖期规则
		issueEntityList = GameIssueGenerateUtil.removeStopIssue(issueEntityList, stopRules, true);
		issueEntityList = GameIssueGenerateUtil.removeStopSeriesIssue(issueEntityList, stopRules, true,takeOffTime);
		
		return issueEntityList;
	}
	
	/**
	 * 目前先暫列為六合彩使用,但理論上應該低頻也須納入
	 * @param lotteryId
	 * @param queryType
	 * @param tempFromDate
	 * @param tempToDate
	 * @param stopRules
	 * @return
	 */
	private List<GameIssueEntity> generateLowFreqGameIssueListByRule(Long lotteryId, Integer queryType, Date tempFromDate,
			Date tempToDate, List<GameIssueRuleEntity> stopRules) {
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();
		GameIssueRuleEntity commenRule = new GameIssueRuleEntity();
		GameIssueRuleEntity specialRule = new GameIssueRuleEntity();
		//先撈看是否有
		List<GameIssueRuleEntity> specialRules = gameIssueRuleDao.getSpecialGameIssueRuleByLotteryAndDate(lotteryId, tempFromDate, tempToDate);
		
		if(specialRules.isEmpty()){
			List<GameIssueTemplateEntity> gameIssueComTemplateEntities = new ArrayList<GameIssueTemplateEntity>();			
			//分周一到周日分别生成每天的gameissueList
			//获取不包含待审核的规则
			commenRule = gameIssueRuleDao.getCommenGameIssueRuleByLottery(lotteryId, getDayValue(tempFromDate),null);
			if (commenRule != null && commenRule.getId() != null) {
				gameIssueComTemplateEntities = gameIssueTemplateDao.getGameIssueTemplateByRuleId(commenRule.getId());
			}
			//产生常规规则
			if (!gameIssueComTemplateEntities.isEmpty()) {
				issueEntityList.addAll(generaterGameIssueList(lotteryId, tempFromDate, gameIssueComTemplateEntities));//根据得到的分段期间生成issueEntityList
			}
		}else{
			List<GameIssueTemplateEntity> gameIssueSpecialTemplateEntities = new ArrayList<GameIssueTemplateEntity>();	
			specialRule = gameIssueRuleDao.getSpecialGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate, tempToDate,
					getDayValue(tempFromDate));//获取当天的特殊
			if (specialRule != null && specialRule.getId() != null) {
				gameIssueSpecialTemplateEntities = gameIssueTemplateDao.getGameIssueTemplateByRuleId(specialRule.getId());
			}
			//产生特殊规则
			List<GameIssueEntity> specialRuleList = new ArrayList<GameIssueEntity>();
			if (!gameIssueSpecialTemplateEntities.isEmpty()) {
				specialRuleList = generaterGameIssueList(lotteryId, tempFromDate, gameIssueSpecialTemplateEntities);
				issueEntityList.addAll(specialRuleList);
			}

		}
		stopRules.addAll(gameIssueRuleDao.getStopGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate, tempToDate,
				getDayValue(tempFromDate)));//获取当天的休市规则

		//假如当天存在休市时间段时将奖期列表中的休市规则移除掉，生成不连续的奖期规则
		issueEntityList = GameIssueGenerateUtil.removeStopIssue(issueEntityList, stopRules, true);
		return issueEntityList;
	}

	/** 
	* @Title: getGenerateGameIssueList 
	* @Description: 根据规则模版返回时间段内的奖期集合 ,注意获取常规奖期规则时忽略常规奖期的结束时间，即常规奖期永久有效
	* @param lotteryId 彩种id
	* @param queryType，1获取已生成的奖期  2：获取不包含待审核的规则 ，3 获取包含待审核的规则
	* @param startOfFromDate
	* @param intervalDay
	* @return
	*/
	private List<GameIssueEntity> getGenerateGameIssueListForQuery(Long lotteryId, Integer queryType,
			Date startOfFromDate, int intervalDay,Date takeOffTime) {
		List<GameIssueEntity> totalIssueEntityList = new ArrayList<GameIssueEntity>();
		for (int i = 1; i <= intervalDay; i++) {
			//获取查询时间间隔内的每一天的起始时间和结束时间
			Date tempFromDate = DateUtils.addDays(startOfFromDate, i - 1);
			Date tempToDate = DateUtils.getEndTimeOfDate(tempFromDate);
			//过去时间的数据属于已经生成奖期，直接从数据库查询
			if (tempFromDate.before(DateUtils.currentDate())) {//小於今天的日期直接拿DB
				List<GameIssueEntity> existGameIssues = this.getGameIssues(lotteryId, tempFromDate, tempToDate);
				if (!existGameIssues.isEmpty()) {
					totalIssueEntityList.addAll(existGameIssues);
				}
				continue;
			}
			if(lotteryId == 99701){
				List<GameIssueEntity> existGameIssues = this.getGameIssues(lotteryId, tempFromDate, tempToDate);
				if (!existGameIssues.isEmpty()) {
					totalIssueEntityList.addAll(existGameIssues);
					continue;
				}
			}
			List<GameIssueRuleEntity> stopRules = new ArrayList<GameIssueRuleEntity>();
			List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();
			
			if(lotteryId!=99701){
				issueEntityList = this.generateGameIssueListByRule(lotteryId, queryType,
						tempFromDate, tempToDate, stopRules,takeOffTime);
			}else{
				//六合彩走此路徑,但理論低頻應該統一歷程
				issueEntityList = this.generateLowFreqGameIssueListByRule(lotteryId, queryType, tempFromDate, tempToDate, stopRules);
			}
			//链接最终有效规则的销售时间，webIssueCode
			this.generalIssueCodeForQuery(issueEntityList, lotteryId, tempFromDate, totalIssueEntityList,
					gameIssueRuleDao.getStopGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate, tempToDate,
							getDayValue(tempFromDate)));
			issueEntityList = GameIssueGenerateUtil.removeStopIssue(issueEntityList, stopRules, false);
			issueEntityList = GameIssueGenerateUtil.removeStopSeriesIssue(issueEntityList, stopRules, false,takeOffTime);
			

			totalIssueEntityList.addAll(issueEntityList);
			
		}
		List<GameIssueRuleEntity> stopRules = new ArrayList<GameIssueRuleEntity>();
		if (totalIssueEntityList.size() > 0){
			GameIssueEntity firstTotalIssueEntity = totalIssueEntityList.get(0);
			GameIssueEntity lastTotalIssueEntity = totalIssueEntityList.get(totalIssueEntityList.size() -1);
			//取得第一期的銷售時間和最後一期開獎時間
			Date tempFromDate = DateUtils.getStartTimeOfDate(firstTotalIssueEntity.getSaleStartTime());
			Date tempToDate = DateUtils.getEndTimeOfDate(lastTotalIssueEntity.getOpenDrawTime());
			//產生獎期時間內是否有休市規則
			stopRules.addAll(gameIssueRuleDao.getStopGameIssueRuleByRuleTypeAndDay(lotteryId, tempFromDate, tempToDate,
					getDayValue(tempFromDate)));//获取当天的休市规则
			//移除休市時間內獎期
			totalIssueEntityList = GameIssueGenerateUtil.removeStopIssue(totalIssueEntityList, stopRules, true);
			totalIssueEntityList = GameIssueGenerateUtil.removeStopIssue(totalIssueEntityList, stopRules, false);
		}
		return totalIssueEntityList;
	}

	private void generalIssueCodeForQuery(List<GameIssueEntity> list, Long lotteryId, Date tempFromDate,
			List<GameIssueEntity> totalList, List<GameIssueRuleEntity> stopRules) {
		if (!list.isEmpty()) {
			for (int j = 1; j <= list.size(); j++) {
				GameIssueEntity gameIssueEntity = list.get(j - 1);
				//第一期的销售开始时间为最大奖期的结束时间
				if (j == 1) {

					gameIssueEntity.setSaleStartTime(totalList.isEmpty() ? gameIssueEntity.getSaleStartTime()
							: totalList.get(totalList.size() - 1).getSaleEndTime());

				}//否则为上一期的结束时间
				else {
					gameIssueEntity.setSaleStartTime(list.get(j - 2).getSaleEndTime());
				}

				if (lotteryId == 99105)//当为黑龙江时时彩时，web显示奖期比较特殊，这里做特殊处理，当数据库中存在黑龙江时时彩的奖期记录时，以大期数记录作为生成的起始记录，
				//当数据库中不存在记录时，以配置文件中配置的记录作为起始记录
				{
					//获取最新一条黑龙江时时彩奖期数据

					String tempIssueCode = HLJStartGenerateIssueCode;

					try {
						tempIssueCode = totalList.isEmpty() ? this.getStartWebIssueCode(lotteryId,
								DataConverterUtil.convertDate2Long(tempFromDate)) : totalList.get(totalList.size() - 1)
								.getWebIssueCode();
					} catch (Exception e) {
						log.error("取得黑龙江时时彩web显示奖期 error.{}", e.getMessage(), e);
					}

					String issueCode = (Long.valueOf(tempIssueCode) + j) + "";
					//生成黑龙江时时彩的web显示期号
					gameIssueEntity.setWebIssueCode(IssueCodeUtil.paddingChar(issueCode, 7, '0', false));
				} else if (lotteryId == 99201) {//北京快乐8
					//获取最新一条北京快乐8奖期数据

					String tempIssueCode = BJKL8GenerateStartIssueCode;

					try {
						tempIssueCode = totalList.isEmpty() ? this.getStartWebIssueCode(lotteryId,
								DataConverterUtil.convertDate2Long(tempFromDate)) : totalList.get(totalList.size() - 1)
								.getWebIssueCode();
					} catch (Exception e) {
						log.error("取得北京快乐8web显示奖期 error.{}", e.getMessage(), e);
					}

					gameIssueEntity.setWebIssueCode(String.valueOf(Long.valueOf(tempIssueCode) + j));
				} else if (lotteryId == 99108 || lotteryId == 99109 || lotteryId == 99401) {//福彩3D年+开奖次数累计
					GameIssueGenerateUtil.generateWebIssueCode(gameIssueEntity, stopRules, lotteryId==99401);						
				} else if(lotteryId == 99701){
					String tempIssueCode = "16999";
					try {
						 tempIssueCode = totalList.isEmpty() ? this.getStartWebIssueCode(lotteryId,
								DataConverterUtil.convertDate2Long(tempFromDate)) : totalList.get(totalList.size() - 1)
								.getWebIssueCode();
					} catch (Exception e) {
						log.error("取得六合彩web显示奖期 error.{}", e.getMessage(), e);
					}

					gameIssueEntity.setWebIssueCode(String.valueOf(Long.valueOf(tempIssueCode) + 1));
					
				}else {
					gameIssueEntity.setWebIssueCode(IssueCodeUtil.createWebIssueCode(lotteryId, tempFromDate, j));
				}
			}
		}
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
			GameIssueEntity kuatian = new GameIssueEntity();
			if (lotteryId != 99108l && lotteryId != 99109l && lotteryId != 99401l && lotteryId != 99701l) {
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
							kuatian = gameIssueEntity;
							continue;
						}
						if (lotteryId != 99108l && lotteryId != 99109l && lotteryId != 99401l && lotteryId != 99701l) {
							gameIssueEntity.setSaleStartTime(DateUtils.addSeconds(gameIssueEntity.getSaleEndTime(),
									-gameIssueTemplateEntities.get(m).getSalePeriodTime().intValue()));
						} else {
							if(lotteryId == 99701l){
								GameIssue issue = null;
								try {
									issue = getGameIssueLastByDate(lotteryId, tempFromDate);
								} catch (Exception e) {
									log.error("get last issue_code error",e);
								}
								if(issue==null){
									//第一期預設銷售截止日減一天
									gameIssueEntity.setSaleStartTime(DateUtils.addDays(gameIssueEntity.getSaleEndTime(), -1));
								}else{
									gameIssueEntity.setSaleStartTime(issue.getSaleEndTime());
								}

							}else{
								//没有周期，第一期的开始时间随便给，生成的时候会换成上一期的结束时间
								int day = lotteryId == 99401l ? 2 : 1;
								gameIssueEntity.setSaleStartTime(DateUtils.addDays(gameIssueEntity.getSaleEndTime(), -day));
							}
						}
					} else {
						//为上一个奖期的销售开始时间
						gameIssueEntity.setSaleStartTime(issueEntityList.get(issueEntityList.size() - 1)
								.getSaleEndTime());
					}

				} else {//非分段第一期的开奖周期的开始时间和结束时间设置
					gameIssueEntity.setSaleStartTime(!issueEntityList.isEmpty() ? issueEntityList.get(
							issueEntityList.size() - 1).getSaleEndTime() : kuatian.getSaleEndTime());
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

	private Page<GameIssueEntity> queryPastIssue(PageRequest<GameIssueListQueryRequest> pr,
			Map<String, Object> queryParamMap) {
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getGameIssuesNumber", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameIssueEntity>(0);
		}

		Page<GameIssueEntity> page = new Page<GameIssueEntity>(pr.getPageNumber(), pr.getPageSize(),
				totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(queryParamMap);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GameIssue> gis = sqlSessionTemplate.selectList("getGameIssues", filters, rowBounds);
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();

		for (GameIssue gi : gis) {
			GameIssueEntity gameIssueEntity = VOConverter.gameIssue2GameIssueEntity(gi);
			issueEntityList.add(gameIssueEntity);
		}
		page.setResult(issueEntityList);
		return page;
	}

	private List<GameIssueEntity> queryPastIssue(Map<String, Object> queryParamMap) {

		queryParamMap.put("sortColumns", "SALE_START_TIME asc");
		List<GameIssue> gis = sqlSessionTemplate.selectList("getGameIssues", queryParamMap);
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();

		for (GameIssue gi : gis) {
			GameIssueEntity gameIssueEntity = VOConverter.gameIssue2GameIssueEntity(gi);
			issueEntityList.add(gameIssueEntity);
		}
		return issueEntityList;
	}

	private Map<String, Object> makeQueryParamMap(GameIssueListQueryRequest gameIssueListQueryRequest) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (null != gameIssueListQueryRequest.getLotteryId()) {
			map.put("lotteryId", gameIssueListQueryRequest.getLotteryId());
		}
		if (null != gameIssueListQueryRequest.getQueryType()) {
			map.put("queryType", gameIssueListQueryRequest.getQueryType());
		}
		//当传过来起始时间段为null 则根据规则设置相应时间
		if (null != gameIssueListQueryRequest.getShowStartTime()) {
			//			map.put("startTime", DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowStartTime()));
			map.put("startTime", DateUtils.format(
					DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowStartTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
		} else {
			map.put("startTime", DateUtils.format(DateUtils.addDays(DateUtils.getStartTimeOfCurrentDate(), -7),
					DateUtils.DATETIME_FORMAT_PATTERN));
		}
		if (null != gameIssueListQueryRequest.getShowEndTime()) {
			//			map.put("endTime", DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowEndTime()));
			map.put("endTime", DateUtils.format(
					DataConverterUtil.convertLong2Date(gameIssueListQueryRequest.getShowEndTime()),
					DateUtils.DATETIME_FORMAT_PATTERN));
		} else {
			map.put("endTime",
					DateUtils.format(DateUtils.getStartTimeOfCurrentDate(), DateUtils.DATETIME_FORMAT_PATTERN));
		}
		return map;
	}

	@Override
	public List<GameIssueEntity> getGameIssuesByLotteryId(Long lotteryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("startTime", DateUtils.format(DateUtils.addDays(DateUtils.getStartTimeOfCurrentDate(), -2),
				DateUtils.DATETIME_FORMAT_PATTERN));
		map.put("endTime", DateUtils.format(DateUtils.addDays(DateUtils.getStartTimeOfCurrentDate(), 1),
				DateUtils.DATETIME_FORMAT_PATTERN));
		List<GameIssue> gis = sqlSessionTemplate.selectList("getGameIssuesBylotteryId", map);
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();

		for (GameIssue gi : gis) {
			GameIssueEntity gameIssueEntity = VOConverter.gameIssue2GameIssueEntity(gi);
			issueEntityList.add(gameIssueEntity);
		}
		return issueEntityList;
	}

	@Override
	public List<GameIssueEntity> getBackGameIssuesByLotteryId(Long lotteryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		if (lotteryId == 99118 || lotteryId == 99119 || lotteryId == 99401) {
			map.put("startTime", DateUtils.format(DateUtils.addDays(DateUtils.getStartTimeOfCurrentDate(), -10),
					DateUtils.DATETIME_FORMAT_PATTERN));
			map.put("endTime", DateUtils.format(new Date(), DateUtils.DATETIME_FORMAT_PATTERN));
		} else {
			map.put("startTime", DateUtils.format(DateUtils.addDays(DateUtils.getStartTimeOfCurrentDate(), -1),
					DateUtils.DATETIME_FORMAT_PATTERN));
			//吉利骰寶 查當期銷售中獎期
			if(lotteryId == 99602 || lotteryId == 99603){
				map.put("endTime", DateUtils.format(DateUtils.addSeconds(new Date(), 75), DateUtils.DATETIME_FORMAT_PATTERN));
			}else{
				map.put("endTime", DateUtils.format(new Date(), DateUtils.DATETIME_FORMAT_PATTERN));
			}		
		}
		List<GameIssue> gis = sqlSessionTemplate.selectList("getBackGameIssuesBylotteryId", map);
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();

		for (GameIssue gi : gis) {
			GameIssueEntity gameIssueEntity = VOConverter.gameIssue2GameIssueEntity(gi);
			issueEntityList.add(gameIssueEntity);
		}
		return issueEntityList;
	}

	@Override
	public List<GameIssueEntity> queryTraceGameIssues(Long lotteryId, Integer maxCountIssue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("maxCountIssue", maxCountIssue);
		map.put("currentTime", new Date());
		List<GameIssue> gis = sqlSessionTemplate.selectList("getTraceGameIssues", map);
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();

		for (GameIssue gi : gis) {
			GameIssueEntity gameIssueEntity = VOConverter.gameIssue2GameIssueEntity(gi);
			issueEntityList.add(gameIssueEntity);
		}
		return issueEntityList;
	}

	@Override
	public List<GameIssueEntity> getGameIssueByLotteryIdAndStatus(Long lotteryId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);

		List<GameIssue> gis = sqlSessionTemplate.selectList("getGameIssueByLotteryIdAndStatus", map);
		List<GameIssueEntity> issueEntityList = new ArrayList<GameIssueEntity>();

		for (GameIssue gi : gis) {
			GameIssueEntity gameIssueEntity = VOConverter.gameIssue2GameIssueEntity(gi);
			issueEntityList.add(gameIssueEntity);
		}
		return issueEntityList;
	}

	@Override
	public GameIssue getNextGameIssueByIssueAndLotteryId(Long lotteryId, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectOne("getNextGameIssueByIssueAndLotteryId", map);
	}

	@Override
	public List<GameIssue> getEarlierSuspendedGameIssue(Long lotteryid, Long issueCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryid);
		map.put("issueCode", issueCode);
		return this.sqlSessionTemplate.selectList("getEarlierSuspendedGameIssue", map);
	}

	@Override
	public Page<GameExceptionAuditOrder> queryGameWarnIssueAuditList(
			PageRequest<GameExceptionAuditRequestDTO> pageRequest) {
		Map<String, Object> map = makeQueryMap(pageRequest);

		Number totalCount = (Number) sqlSessionTemplate.selectOne("queryCountAllGameWarnOrderList", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameExceptionAuditOrder>(0);
		}

		Page<GameExceptionAuditOrder> page = new Page<GameExceptionAuditOrder>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		List<GameExceptionAuditOrder> list = new ArrayList<GameExceptionAuditOrder>();

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());

		list = sqlSessionTemplate.selectList("queryAllGameWarnOrderAuditList", filters, rowBounds);
		page.setResult(list);

		return page;
	}

	private Map<String, Object> makeQueryMap(PageRequest<GameExceptionAuditRequestDTO> pageRequest) {
		GameExceptionAuditRequestDTO dto = pageRequest.getSearchDo();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("startIssueTime", dto.getStartIssueTime() == null ? null : dateFormat.format(dto.getStartIssueTime()));
		map.put("endIssueTime", dto.getEndIssueTime() == null ? null : dateFormat.format(dto.getEndIssueTime()));
		map.put("lotteryId", dto.getLotteryId() > 0 ? dto.getLotteryId() : null);
		map.put("status", dto.getStatus());
		return map;
	}

	@Override
	public Page<GameExceptionAuditOrder> queryGameWarnIssueToAuditList(
			PageRequest<GameExceptionAuditRequestDTO> pageRequest) {
		Map<String, Object> map = makeQueryMap(pageRequest);

		Number totalCount = (Number) sqlSessionTemplate.selectOne("queryCountGameWarnOrderToAuditList", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameExceptionAuditOrder>(0);
		}

		Page<GameExceptionAuditOrder> page = new Page<GameExceptionAuditOrder>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		List<GameExceptionAuditOrder> list = new ArrayList<GameExceptionAuditOrder>();

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());

		list = sqlSessionTemplate.selectList("queryGameWarnOrderToAuditList", filters, rowBounds);
		page.setResult(list);

		return page;
	}

	@Override
	public Page<GameExceptionAuditOrder> queryGameWarnIssueAuditedList(
			PageRequest<GameExceptionAuditRequestDTO> pageRequest) {
		Map<String, Object> map = makeQueryMap(pageRequest);

		Number totalCount = (Number) sqlSessionTemplate.selectOne("queryCountGameWarnOrderAuditedList", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GameExceptionAuditOrder>(0);
		}

		Page<GameExceptionAuditOrder> page = new Page<GameExceptionAuditOrder>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		List<GameExceptionAuditOrder> list = new ArrayList<GameExceptionAuditOrder>();

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());

		list = sqlSessionTemplate.selectList("queryGameWarnOrderAuditedList", filters, rowBounds);
		page.setResult(list);

		return page;
	}

	@Override
	public List<GameIssueEntity> queryPreGameIssue(Long lotteryid, Long issueCode, Long count) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryid);
		map.put("issueCode", issueCode);
		map.put("count", count);
		List<GameIssue> list = this.sqlSessionTemplate.selectList("queryPreGameIssue", map);
		List<GameIssueEntity> result = new ArrayList<GameIssueEntity>();
		for (GameIssue gameIssue : list) {
			result.add(VOConverter.gameIssue2GameIssueEntity(gameIssue));
		}
		return result;
	}

	@Override
	public List<GameIssueEntity> queryNextGameIssue(Long lotteryid, Long issueCode, Long count) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryid);
		map.put("issueCode", issueCode);
		map.put("count", count);
		List<GameIssue> list = this.sqlSessionTemplate.selectList("queryNextGameIssue", map);
		List<GameIssueEntity> result = new ArrayList<GameIssueEntity>();
		for (GameIssue gameIssue : list) {
			result.add(VOConverter.gameIssue2GameIssueEntity(gameIssue));
		}
		return result;
	}

	@Override
	public GameIssue getGameIssueByIssueCodeAndLottery(Long lotteryId, Long issueCode) {

		GameIssue gameIssue = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);

		map.put("issueCode", issueCode);
		gameIssue = this.sqlSessionTemplate.selectOne("getGameIssueByIssueCodeAndlotteryId", map);

		return gameIssue;
	}

	@Override
	public List<GameIssueEntity> getGameIssues(Long lotteryId, String issueStartTime, String issueEndTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("startTime", issueStartTime);
		map.put("endTime", issueEndTime);
		List<GameIssue> list = this.sqlSessionTemplate.selectList("queryGameIssuesByLotteryIdAndTime", map);
		List<GameIssueEntity> result = new ArrayList<GameIssueEntity>();
		for (GameIssue gameIssue : list) {
			result.add(VOConverter.gameIssue2GameIssueEntity(gameIssue));
		}
		return result;
	}

	@Override
	public GameIssueEntity getLastDrawGameIssue(Long lotteryId) {
		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("getLastDrawGameIssue", lotteryId);
		return VOConverter.gameIssue2GameIssueEntity(gameIssue);
	}

	public GameIssue getMinIssueNotReciveResultCode(Long lotteryId, Long userId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectOne("getMinIssueNotReciveResultCode", map);
	}

	public GameIssue getMaxIssueNotReciveResultCode(Long lotteryId, Long userId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectOne("getMaxIssueNotReciveResultCode", map);
	}

	public static void main(String args[]) {
		Calendar c = Calendar.getInstance();

		c.setTime(new Date());
		System.out.print(c.get(Calendar.DAY_OF_YEAR));
	}

	public void updateTry(Long count, Long id) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("id", id);
		this.sqlSessionTemplate.update(getQueryPath("updateTry"), map);

	}

	/**
	* Title: manualDeleteIssues
	* Description:
	* @param type
	* @param start
	* @param end
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameIssueDao#manualDeleteIssues(int, java.lang.String, java.lang.String) 
	*/
	@Override
	public GameIssueManualGenerateResponse manualDeleteIssues(int type, String start, String end, Long lotteryId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		GameIssueManualGenerateResponse response = new GameIssueManualGenerateResponse();
		response.setStartDate(start);
		response.setEndDate(end);
		if (type == 1) {
			Date startDate = DateUtils.parse(start, DateUtils.DATE_FORMAT_PATTERN);
			Date endDate = DateUtils.parse(end, DateUtils.DATE_FORMAT_PATTERN);
			GameIssue saleGameIssue = queryCurrentSaleIssue(lotteryId, startDate, endDate);
			if (saleGameIssue != null) {
				response.setMessage("销售结束时间为"
						+ DateUtils.format(saleGameIssue.getSaleEndTime(), DateUtils.DATETIME_FORMAT_PATTERN) + "的奖期"
						+ saleGameIssue.getWebIssueCode() + "已经在售,请选择在售奖期以后的删除时间");
				return response;
			}
			GameIssue planGameIssue = this.queryMaxPlanIssue(lotteryId, startDate, endDate);
			if (planGameIssue != null) {
				response.setMessage("销售结束时间为"
						+ DateUtils.format(planGameIssue.getSaleEndTime(), DateUtils.DATETIME_FORMAT_PATTERN) + "的奖期"
						+ planGameIssue.getWebIssueCode() + "已经追号,请选择追号奖期以后的删除时间");
				return response;
			}
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("lotteryId", lotteryId);
			int count = this.sqlSessionTemplate.delete(getQueryPath("deleteByDate"), map);
			response.setGenerateIssues(Long.valueOf(count));
			int intervalDay = (int) DateUtils.calcDateBetween(startDate, endDate);
			response.setGenerateDays(startDate.compareTo(endDate) == 0 ? 1 : intervalDay + 1);
		} else {
			map.put("startWebIssue", start);
			map.put("endWebIssue", end);
			map.put("lotteryId", lotteryId);
			int count = this.sqlSessionTemplate.delete(getQueryPath("deleteByWebIssue"), map);
			response.setGenerateIssues(Long.valueOf(count));
		}
		return response;
	}

	public GameIssue queryCurrentSaleIssue(Long lotteryId, Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("lotteryId", lotteryId);
		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("queryCurrentSaleIssue", map);
		return gameIssue;
	}

	private GameIssue queryMaxPlanIssue(Long lotteryId, Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("lotteryId", lotteryId);
		GameIssue gameIssue = this.sqlSessionTemplate.selectOne("queryMaxPlanIssue", map);
		return gameIssue;
	}

	/**
	* Title: updateGameIssueScheduleStopTime
	* Description:
	* @param lotteryId
	* @param scheduleStopTime
	* @throws Exception 
	* @see com.winterframework.firefrog.game.dao.IGameIssueDao#updateGameIssueScheduleStopTime(java.lang.Long, java.lang.Integer) 
	*/
	@Override
	public void updateGameIssueScheduleStopTime(Long lotteryId, Integer scheduleStopTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("scheduleStopTime",
				new BigDecimal(scheduleStopTime).divide(new BigDecimal(86400), MathContext.DECIMAL128));
		this.sqlSessionTemplate.update("updateGameIssueScheduleStopTime", map);
	}

	@Override
	public String getStartWebIssueCode(Long lotteryId, Long startTime) throws Exception {
		int j = 1;
		SimpleDateFormat dt = new SimpleDateFormat("YY");
		Date startDate = DataConverterUtil.convertLong2Date(startTime);
		List<GameIssueTemplateEntity> getCommRuleTemplate = this.getCommRuleTemplate(lotteryId, 0,startDate);
		Date generateStartTime = GameIssueGenerateUtil.getGenerateStartTime(getCommRuleTemplate, 0, startDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("saleStartTime", generateStartTime);
		GameIssue gis = null;
		List<GameIssue> gisList = this.sqlSessionTemplate.selectList("getMaxGameIssuesByLotteryIdAndDate", map);
		if (!gisList.isEmpty()) {
			gis = gisList.get(0);
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
			return IssueCodeUtil.paddingChar(issueCode, 7, '0', false);
		} else if (lotteryId == 99201) {//北京快乐8
			//获取最新一条北京快乐8奖期数据
			//GameIssue gis = this.sqlSessionTemplate.selectOne("getMaxGameIssuesByLotteryId", lotteryId);
			String tempIssueCode;
			if (gis == null) {
				tempIssueCode = BJKL8GenerateStartIssueCode;
			} else {
				tempIssueCode = gis.getWebIssueCode();
			}
			return String.valueOf(Long.valueOf(tempIssueCode) + j);
		} else if (lotteryId == 99108 || lotteryId == 99109 || lotteryId == 99401) {//福彩3D年+开奖次数累计
			String tempIssueCode = gis.getWebIssueCode();
			return String.valueOf(Long.valueOf(tempIssueCode) + j);
		} else if(lotteryId == 99701){
			if(gis!=null){
				String tempIssueCode = gis.getWebIssueCode();
				return String.valueOf(Long.valueOf(tempIssueCode) + j);
			}else{
				return dt.format(DataConverterUtil.convertLong2Date(startTime))+"000";
			}
		}else {
			return IssueCodeUtil.createWebIssueCode(lotteryId, generateStartTime, j);
		}
	}
	
	@Override
	public List<GameIssue> getGameIssueNumberRecord(Long lotteryId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		return this.sqlSessionTemplate.selectList("getGameIssueNumberRecord", map);
	}

	@Override
	public boolean getGameIssueIsOpenAward(Long lotteryId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		List<GameIssue> list = this.sqlSessionTemplate.selectList("getGameIssueIsOpenAward", map);
		if(list.size() >0){
			return true;
		}
		return false;
	}

	@Override
	public String getGameIssueLastWebIssueCode(Long lotteryId, Date date)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("date", date);		
		return this.sqlSessionTemplate.selectOne("getGameIssueLastWebIssueCode", map);
	}

	@Override
	public GameIssue getGameIssueLastByDate(Long lotteryId, Date date)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("date", date);		
		return this.sqlSessionTemplate.selectOne("getGameIssueLastByDate", map);
	}
	
	@Override
	public GameIssue queryGameIssue(Map<String, Object> map) throws Exception {
	
		GameIssue gameIssue = null;

		gameIssue = this.sqlSessionTemplate.selectOne("getGameIssueByIssueCodeAndlotteryId", map);
		if (null == gameIssue) {
			throw new GameIssueNotExistErrorException();
		}
		
		
		return gameIssue;
	}
	
	@Override
	public GameIssue queryNextGameIssue(Map<String, Object> map)throws Exception  {
		
		List<GameIssue> list = this.sqlSessionTemplate.selectList("queryNextGameIssue", map);
	
		GameIssue gameIssue = new GameIssue();
		if(list != null && list.size()>0){
			gameIssue = (GameIssue)list.get(0);
		}else{
			throw new GameIssueNotExistErrorException();
		}
		
		return gameIssue;
	}
	
	@Override
	public GameIssue queryPreviousGameIssue(Map<String, Object> map)throws Exception  {
		
		List<GameIssue> list = this.sqlSessionTemplate.selectList("queryPreviousGameIssue", map);
	
		GameIssue gameIssue = new GameIssue();
		if(list != null && list.size()>0){
			gameIssue = (GameIssue)list.get(0);
		}else{
			throw new GameIssueNotExistErrorException();
		}
		
		return gameIssue;
	}
	
	@Override
	public void updateIssuseCodeAfterExtendGameIssue(Map<String, Object> map)throws Exception {
		this.sqlSessionTemplate.update("updateIssuseCodeAfterExtendGameIssue", map);

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
	public void updateGameIssue(GameIssue gameIssue) throws Exception {
		try {
			this.sqlSessionTemplate.update(getQueryPath("updateGameIssue"), gameIssue);
		} catch (Exception e) {
			log.error("updateGameIssue", e);
			throw e;
		}
	}

	@Override
	public List<GameIssue> getHistoryLotteryAward(
			HistoryLotteryAwardRequest request) throws Exception {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lotteryId", request.getLotteryId());
			map.put("count", request.getHistorynum());
			
			return this.sqlSessionTemplate.selectList("getHistoryLotteryAward", map);
		} catch(Exception e) {
			log.error("getHistoryLotteryAward error. {}. {}", request.toString(), e.getMessage(), e);
			throw e;
		}
	}
}
