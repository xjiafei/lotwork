/**   
* @Title: GameIssueGenerateUtil.java 
* @Package com.winterframework.firefrog.common.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-8-21 下午1:58:51 
* @version V1.0   
*/
package com.winterframework.firefrog.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameIssueTemplateEntity;
import com.winterframework.firefrog.game.web.util.IssueCodeUtil;

/** 
* @ClassName: GameIssueGenerateUtil 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-8-21 下午1:58:51 
*  
*/
public class GameIssueGenerateUtil {

	public static Date getTimeOfDate(Date date, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return DateUtils.parse(DateUtils.format(cal.getTime(), DateUtils.DATETIME_FORMAT_PATTERN),
				DateUtils.DATETIME_FORMAT_PATTERN);
	}

	//获取开始奖期生成时间
	public static Date getGenerateStartTime(List<GameIssueTemplateEntity> list, int i, Date fromDate) {
		//fromDate為null則為現在時間加上i天
		//format為yyyy-mm-dd
		Date generateStartTime = DateUtils.getStartTimeOfDate(DateUtils.addDays(
				fromDate == null ? DateUtils.getStartTimeOfCurrentDate() : DateUtils.getStartTimeOfDate(fromDate), i));
		if (list == null || list.isEmpty()) {
			return generateStartTime;
		}

		//GameIssueTemplateEntity endTemplate = list.get(list.size() - 1);
		GameIssueTemplateEntity firstTemplate = list.get(0);
		//最后一期的结束时间小于开始时间或结束时间>24，表示跨天
		/*if (endTemplate.getFirstAwardTime().after(endTemplate.getLastAwardTime())) {
			generateStartTime = DateUtils.addHours(generateStartTime,
					DateUtils.getHours(endTemplate.getLastAwardTime()));
			generateStartTime = DateUtils.addMinutes(generateStartTime,
					DateUtils.getMinutes(endTemplate.getLastAwardTime()));
			generateStartTime = DateUtils.addSeconds(generateStartTime,
					DateUtils.getSeconds(endTemplate.getLastAwardTime()));
		}else{*/
		
		//firstAwardTime=官方第一期开奖时间
			generateStartTime = DateUtils.addHours(generateStartTime,
					DateUtils.getHours(firstTemplate.getFirstAwardTime()));
			generateStartTime = DateUtils.addMinutes(generateStartTime,
					DateUtils.getMinutes(firstTemplate.getFirstAwardTime()));
			generateStartTime = DateUtils.addSeconds(generateStartTime,
					DateUtils.getSeconds(firstTemplate.getFirstAwardTime()));
		/*}*/
		return generateStartTime;
	}

	//获取开始奖期生成时间
	public static Date getGenerateEndTime(List<GameIssueTemplateEntity> list, int i) {

		//取得現在時間加上i天，時間為23:59:59
		Date generateEndTime = DateUtils.getEndTimeOfDate(DateUtils.addDays(DateUtils.getStartTimeOfCurrentDate(), i));
		if (list == null || list.isEmpty()) {
			return generateEndTime;
		}
		//取得最後一筆GAME_ISSUE_TEMPLATE
		GameIssueTemplateEntity endTemplate = list.get(list.size() - 1);
		//最后一期的结束时间小于开始时间或结束时间>24，表示跨天
		if (endTemplate.getFirstAwardTime().after(endTemplate.getLastAwardTime())) {
			generateEndTime = DateUtils.addHours(generateEndTime, DateUtils.getHours(endTemplate.getLastAwardTime()));
			generateEndTime = DateUtils.addMinutes(generateEndTime,
					DateUtils.getMinutes(endTemplate.getLastAwardTime()));
			generateEndTime = DateUtils.addSeconds(generateEndTime,
					DateUtils.getSeconds(endTemplate.getLastAwardTime()));
		}
		return generateEndTime;
	}

	public static void insertEntityListToVoList(List<GameIssueEntity> issueEntityList, GameIssue lastGissue,
			List<GameIssue> insertGis, Long lotteryId, Date startGeneralDate) {
		Long sequence = 0l;
		for (int j = 0; j < issueEntityList.size(); j++) {
			GameIssueEntity gie = issueEntityList.get(j);
			GameIssue gi = new GameIssue();
			gi.setSaleStartTime(gie.getSaleStartTime());
			gi.setSaleEndTime(gie.getSaleEndTime());
			gi.setOpenDrawTime(gie.getOpenDrawTime());
			gi.setSequence(++sequence);
			int bit = (lotteryId == 99111l || lotteryId == 99114l || lotteryId == 99601l || lotteryId == 99602l || lotteryId == 99603l) ? 4 : 3;
			if (gie.getWebIssueCode() != null && gie.getWebIssueCode() != "") {
				String[] webIssueCodeArray = gie.getWebIssueCode().split("-");
				if (webIssueCodeArray.length > 1) {
					sequence = Long.valueOf(webIssueCodeArray[1]);
				}
			}
			gi.setIssueCode(Long.valueOf(IssueCodeUtil.createCommenIssueCode(startGeneralDate, "", gi.getSequence(),
					bit, lotteryId)));
			gi.setWebIssueCode((gie.getWebIssueCode() == null || gie.getWebIssueCode().equals("")) ? gi.getIssueCode()
					.toString() : gie.getWebIssueCode());
			gi.setLotteryid(lotteryId);
			gi.setCreateTime(new Date());
			gi.setUpdateTime(gi.getCreateTime());
			gi.setStatus(0L);
			gi.setPeriodStatus(0L);
			gi.setPauseStatus(1);
			gi.setEventStatus(1);
			gi.setPlanFinishStatus(0);
			gi.setLastIssueStop(0);
			//给每个奖期设置上一期的奖期规则
			gi.setLastIssue(j == 0 ? lastGissue == null ? null : lastGissue.getIssueCode() : insertGis.get(j - 1)
					.getIssueCode());
			insertGis.add(gi);
		}
	}

	public static List<GameIssueEntity> handerCommRuleAndSpecialRule(List<GameIssueEntity> issueEntityList,
			List<GameIssueEntity> specialRuleList) {
		List<GameIssueEntity> list = new ArrayList<GameIssueEntity>();
		if (!specialRuleList.isEmpty()) {
			Date startSpecialTime = specialRuleList.get(0).getSaleStartTime();
			Date endSpecialTime = specialRuleList.get(specialRuleList.size() - 1).getSaleEndTime();
			//加入特殊规则时间之前的
			for (GameIssueEntity gameIssueEntity : issueEntityList) {
				if (gameIssueEntity.getSaleStartTime().before(startSpecialTime)) {
					list.add(gameIssueEntity);
				}
			}
			//加入特殊规则数据
			list.addAll(specialRuleList);
			Date endComTime = issueEntityList.get(issueEntityList.size() - 1).getSaleEndTime();
			//加入特殊规则之后的
			if (endSpecialTime.before(endComTime)) {
				for (GameIssueEntity gameIssueEntity : issueEntityList) {
					if (gameIssueEntity.getSaleEndTime().after(endSpecialTime)) {
						list.add(gameIssueEntity);
					}
				}
			}

		} else {
			list = issueEntityList;
		}
		return list;
	}

	public static List<GameIssueEntity> removeStopIssue(List<GameIssueEntity> issueEntityList,
			List<GameIssueRuleEntity> stopRules, Boolean isPre) {
		if (!stopRules.isEmpty() && !issueEntityList.isEmpty()) {
			List<GameIssueEntity> tempIssueEntityList = new ArrayList<GameIssueEntity>();
			tempIssueEntityList.addAll(issueEntityList);
			for (GameIssueEntity gie : tempIssueEntityList) {
				for (GameIssueRuleEntity ge : stopRules) {//当奖期的开始结束时间段在休市之间时 将该奖期移除
					if (ge.getSeriesIssueCode() == null || (isPre && ge.getSeriesIssueCode() == 1l)
							|| (!isPre && ge.getSeriesIssueCode() == 0)) {//之前如果奖期
						//比對開獎時間、販賣時間是否在休市時間內
						if (/*(gie.getSaleStartTime().after(ge.getRuleStartTime()) && gie.getSaleStartTime().before(
								ge.getRuleEndTime()))
								|| */((gie.getOpenDrawTime().after(ge.getRuleStartTime()) && gie.getOpenDrawTime().before(
								ge.getRuleEndTime())))
								|| (gie.getSaleEndTime().after(ge.getRuleStartTime()) && gie.getSaleEndTime().before(
										ge.getRuleEndTime()))) {
							//休市周期
							List<String> openAwardPeriods = Arrays.asList(ge.getOpenAwardPeriod().split(","));
							if (openAwardPeriods.contains(getDayValue(gie.getOpenDrawTime()))) {
								Date stopStartTimeForOpenAward = getTimeOfDate(gie.getOpenDrawTime(),
										DateUtils.getHours(ge.getStopStartTime()),
										DateUtils.getMinutes(ge.getStopStartTime()),
										DateUtils.getSeconds(ge.getStopStartTime()));
								Date stopEndTimeForOpenAward = getTimeOfDate(gie.getOpenDrawTime(),
										DateUtils.getHours(ge.getStopEndTime()),
										DateUtils.getMinutes(ge.getStopEndTime()),
										DateUtils.getSeconds(ge.getStopEndTime()));

								Date stopStartTimeForSaleEnd = getTimeOfDate(gie.getSaleEndTime(),
										DateUtils.getHours(ge.getStopStartTime()),
										DateUtils.getMinutes(ge.getStopStartTime()),
										DateUtils.getSeconds(ge.getStopStartTime()));
								Date stopEndTimeForSaleEnd = getTimeOfDate(gie.getSaleEndTime(),
										DateUtils.getHours(ge.getStopEndTime()),
										DateUtils.getMinutes(ge.getStopEndTime()),
										DateUtils.getSeconds(ge.getStopEndTime()));
								if ((gie.getOpenDrawTime().after(stopStartTimeForOpenAward) && gie.getOpenDrawTime()
										.before(stopEndTimeForOpenAward))
										|| (gie.getSaleEndTime().after(stopStartTimeForSaleEnd) && gie.getSaleEndTime()
												.before(stopEndTimeForSaleEnd))) {
									issueEntityList.remove(gie);
								}
							}
						}
					}
				}
			}
		}
		return issueEntityList;
	}
	
	/***
	 * removeStopSeriesIssue
	 * 移除彩種下架時間以後的獎期
	 * @param issueEntityList
	 * @param stopRules
	 * @param isPre
	 * @param takeOffTime
	 * @return
	 */
	public static List<GameIssueEntity> removeStopSeriesIssue(List<GameIssueEntity> issueEntityList,
			List<GameIssueRuleEntity> stopRules, Boolean isPre,Date takeOffTime) {
		if (!issueEntityList.isEmpty()) {
			List<GameIssueEntity> tempIssueEntityList = new ArrayList<GameIssueEntity>();
			tempIssueEntityList.addAll(issueEntityList);
			for (GameIssueEntity gie : tempIssueEntityList) {
				//移除休市獎期後，驗證開獎時間、販賣截止時間是否大於彩種下架時間
				if(takeOffTime != null ){
					if((gie.getOpenDrawTime().after(takeOffTime) || gie.getSaleEndTime().after(takeOffTime))){
						issueEntityList.remove(gie);
					}
				}
				
			}
			
		}
		return issueEntityList;
	}

	public static int getSscIssuesBetweendays(Date fromDay, Date endDay, List<GameIssueRuleEntity> stopRules,
			boolean isSsc) {
		int numIssue = 0;
		endDay = DateUtils.getStartTimeOfDate(endDay);
		for (Date begin = fromDay; begin.before(DateUtils.addDays(endDay, 1));begin = DateUtils.addDays(begin, 1)) {
			if (!stopRules.isEmpty()) {
				//GameIssueRuleEntity stopRule = stopRules.get(0);
				boolean isStop = false;
				Long seriesIssueCode = 1l;
				for (GameIssueRuleEntity stopRule : stopRules) {
					if (begin.after(DateUtils.addDays(DateUtils.getStartTimeOfDate(stopRule.getRuleStartTime()), -1))
							&& begin.before(DateUtils.addDays(DateUtils.getStartTimeOfDate(stopRule.getRuleEndTime()), 1))) {
						isStop = true;
						seriesIssueCode = stopRule.getSeriesIssueCode();
						break;
					}
				}
				if (isStop && (seriesIssueCode == null || seriesIssueCode == 1l)) {//连续的奖期直接加
					continue;
				} else {
					if (isSsc) {
						if (DateUtils.getDate(begin) == 1 || DateUtils.getDate(begin) == 3
								|| DateUtils.getDate(begin) == 5) {
							//System.out.println(DateUtils.format(begin, DateUtils.DATE_FORMAT_PATTERN));
							numIssue++;
						}
					} else {
						numIssue++;
					}
				}
			} else {
				if (isSsc) {
					if (DateUtils.getDate(begin) == 1 || DateUtils.getDate(begin) == 3 || DateUtils.getDate(begin) == 5) {
						numIssue++;
					}
				} else {
					numIssue++;
				}
			}
		}
		return numIssue;
	} 

	public static void generateWebIssueCode(GameIssueEntity issueEntityList, List<GameIssueRuleEntity> stopRules,
			Boolean isSsc) {
		int webCodeNum = 1;
		webCodeNum = getSscIssuesBetweendays(
				DateUtils.getStartDateTimeOfYear(DateUtils.getYear(issueEntityList.getSaleEndTime())),
				issueEntityList.getSaleEndTime(), stopRules, isSsc);

		String yearCode = DateUtils
				.format(issueEntityList.getSaleEndTime(), DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR).substring(0, 4);

		issueEntityList.setWebIssueCode(String.valueOf(webCodeNum).length() == 1 ? yearCode + "00" + webCodeNum
				: String.valueOf(webCodeNum).length() == 2 ? yearCode + "0" + webCodeNum : yearCode + webCodeNum);

	}

	public static void generateLhcWebIssueCode(GameIssueEntity issueEntityList ,String latsWebIssueCode) {
		if(StringUtils.isNotEmpty(latsWebIssueCode)){
			String webIssueCode = new Integer(Integer.valueOf(latsWebIssueCode)+1).toString();
			issueEntityList.setWebIssueCode(webIssueCode);			
		}else{
			//開獎日為新的年度
			String yearCode = DateUtils
					.format(issueEntityList.getOpenDrawTime(), DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR).substring(2,4);
			String webIssueCode =yearCode+"001";
			issueEntityList.setWebIssueCode(webIssueCode);
		}
	}
	
	/** 
	* @Title: getDayValue 
	* @Description: 获取星期几对应的数字 
	* @param date
	* @return
	*/
	private static String getDayValue(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.DAY_OF_WEEK);
		if (i == 1) {
			return "7";
		} else {
			return i - 1 + "";
		}
	}

	public static void main(String[] args) {
		GameIssueEntity issueEntityList = new GameIssueEntity();
		Date dt = DateUtils.parse("2014-12-31", DateUtils.DATE_FORMAT_PATTERN);
		issueEntityList.setSaleEndTime(dt);
		List<GameIssueRuleEntity> stopRules = new ArrayList<GameIssueRuleEntity>();
		GameIssueRuleEntity rule = new GameIssueRuleEntity();
		rule.setRuleStartTime(DateUtils.parse("2014-01-30", DateUtils.DATE_FORMAT_PATTERN));
		rule.setRuleEndTime(DateUtils.parse("2014-02-06", DateUtils.DATE_FORMAT_PATTERN));
		stopRules.add(rule);
		generateWebIssueCode(issueEntityList, stopRules, false);
		System.out.println(issueEntityList.getWebIssueCode());
	}
}
