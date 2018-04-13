package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.ActivityUserAwardLog;
import com.winterframework.firefrog.game.web.bet.util.BetHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.ActivityUserAwardRequest;
import com.winterframework.firefrog.game.web.dto.ActivityUserAwardResponse;
import com.winterframework.firefrog.game.web.dto.DailyActivityRequest;
import com.winterframework.firefrog.game.web.dto.DailyActivityResponse;
import com.winterframework.firefrog.game.web.dto.DailyActivityRewardStruc;
import com.winterframework.firefrog.game.web.dto.DailyBetPrizeRequest;
import com.winterframework.firefrog.game.web.dto.DailyBetPrizeResponse;
import com.winterframework.firefrog.game.web.dto.GetLuckyNumberRequest;
import com.winterframework.firefrog.game.web.dto.GetLuckyNumberResponse;
import com.winterframework.firefrog.game.web.dto.GetLuckyRequest;
import com.winterframework.firefrog.game.web.dto.GetLuckyResponse;
import com.winterframework.firefrog.game.web.dto.MyLuckStruc;
import com.winterframework.firefrog.game.web.dto.PrizeDaliyStruc;
import com.winterframework.firefrog.game.web.dto.PrizeList;
import com.winterframework.firefrog.game.web.dto.PrizeListStruc;
import com.winterframework.firefrog.game.web.dto.PrizeLuckyStruc;
import com.winterframework.firefrog.game.web.dto.PrizeStruc;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.RequestContext;

/**
 * @Title 抽奖系统
 * @Description
 * 
 * @author david
 * 
 */
@Controller("GamePrizeController")
@RequestMapping(value = "/gamePrize")
public class PrizeController {
	private Logger logger = LoggerFactory.getLogger(PrizeController.class);

	@Resource(name = "betHttpClient")
	protected BetHttpJsonClient betHttpClient;

	@PropertyConfig("dailyActivityStartTime")
	private String dailyActivityStartTime;

	@PropertyConfig("daily.activity.end.time")
	private String dailyActivityEndTime;

	@PropertyConfig("daily.activity.end.reward.time")
	private String dailyActivityEndRewardTime;

	@PropertyConfig("sheepYear.activity.hongbao.startTime")
	private String sheepYearActivityHongbaoStartTime;

	@PropertyConfig("sheepYear.activity.hongbao.endTime")
	private String sheepYearActivityHongbaoEndTime;

	@PropertyConfig("sheepyear.activity.dice.startTime")
	private String diceStartTime;

	@PropertyConfig("sheepyear.activity.dice.endTime")
	private String diceEndTime;

	@PropertyConfig("sheepyear.activity.rotary.startTime")
	private String rotaryStartTime;

	@PropertyConfig("sheepyear.activity.rotary.endTime")
	private String rotaryEndTime;
	
	@PropertyConfig("aes_key")
	private String aesKey;
	
	@PropertyConfig("aes_iv")
	private String aesIv;

	/**
	 * @param 网页端活动首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prizeIndex")
	public String prizeEnter(Model model) throws Exception {
		DailyActivityRequest request = new DailyActivityRequest();
		request.setStartTime(dailyActivityStartTime);
		request.setEndTime(dailyActivityEndTime);
		request.setUserId(RequestContext.getCurrUser().getId());
		Response<DailyActivityResponse> dar = betHttpClient
				.getUserDailyActivityStruc(request);
		List<DailyActivityRewardStruc> strucs = dar.getBody().getResult()
				.getStrucs();// 获取当前用户的投注领奖数据结构
		Date now = new Date();
		Date startTime = DateUtils.getStartTimeOfDate(DateUtils
				.parse(dailyActivityStartTime));
		Date endTime = DateUtils.getEndTimeOfDate(DateUtils
				.parse(dailyActivityEndTime));

		PrizeStruc ps = new PrizeStruc();
		ps.setThisDay(DateUtils.getDay(new Date()));
		List<PrizeDaliyStruc> plss = new ArrayList<PrizeDaliyStruc>();
		if (now.before(startTime)) {// 活动未开始
			while (startTime.before(endTime)) {
				PrizeDaliyStruc pls = new PrizeDaliyStruc();
				pls.setDay(DateUtils.getDay(startTime));
				pls.setYear(DateUtils.getYear(startTime));
				pls.setMonth(DateUtils.getMonth(startTime));
				pls.setMoney(2);
				pls.setStatus("comming");
				plss.add(pls);
				startTime = DateUtils.addDays(startTime, 1);
			}
		} else if (now.after(endTime)) {// 活动已结束
			while (startTime.before(endTime)) {
				PrizeDaliyStruc pls = new PrizeDaliyStruc();
				pls.setDay(DateUtils.getDay(startTime));
				pls.setYear(DateUtils.getYear(startTime));
				pls.setMonth(DateUtils.getMonth(startTime));
				pls.setMoney(2);
				boolean betFlag = false;// 当天投注标示
				boolean rewardFlag = false;// 当天领奖标示
				if (strucs != null && !strucs.isEmpty()) {
					for (DailyActivityRewardStruc ds : strucs) {
						if (DateUtils.format(startTime).equals(ds.getDate())) {
							betFlag = true;// 有日期匹配表明有有效投注记录
							if (ds.getHadGet()) {
								rewardFlag = true;// 标示此投注已领奖
							}
						}
					}
				}
				if (betFlag) {
					if (rewardFlag) {
						pls.setStatus("accepted");
					} else {
						pls.setStatus("enabled");
					}
				} else {
					pls.setStatus("expired");
				}
				plss.add(pls);
				startTime = DateUtils.addDays(startTime, 1);
			}
		} else {// 活动进行中
			while (startTime.before(endTime)) {
				PrizeDaliyStruc pls = new PrizeDaliyStruc();
				pls.setDay(DateUtils.getDay(startTime));
				pls.setYear(DateUtils.getYear(startTime));
				pls.setMonth(DateUtils.getMonth(startTime));
				pls.setMoney(2);
				boolean betFlag = false;// 当天投注标示
				boolean rewardFlag = false;// 当天领奖标示
				if (strucs != null && !strucs.isEmpty()) {
					for (DailyActivityRewardStruc ds : strucs) {
						if (DateUtils.format(startTime).equals(ds.getDate())) {
							betFlag = true;// 有日期匹配表明有有效投注记录
							if (ds.getHadGet()) {
								rewardFlag = true;// 标示此投注已领奖
							}
						}
					}
				}
				if (betFlag) {
					if (rewardFlag) {
						pls.setStatus("accepted");
					} else {
						pls.setStatus("enabled");
					}
				} else {
					if (startTime.after(DateUtils.getStartTimeOfDate(now))) {
						pls.setStatus("comming");
					} else if (startTime.before(DateUtils
							.getStartTimeOfDate(now))) {
						pls.setStatus("expired");
					} else if (startTime.equals(DateUtils
							.getStartTimeOfDate(now))) {
						pls.setStatus("lottery");
					}
				}
				plss.add(pls);
				startTime = DateUtils.addDays(startTime, 1);
			}

		}

		//
		// //构建日活动投注领奖结构
		//
		// ps.setYear(DateUtils.getYear(new Date()));
		// ps.setMonth(DateUtils.getMonth(new Date()));
		// ps.setDays(DateUtils.getDaysOfMonth(ps.getYear(), ps.getMonth()-1));
		// ps.setThisDay(DateUtils.getDay(new Date()));
		//
		// for(int i=1;i<=ps.getDays();i++){
		// PrizeDaliyStruc pls=new PrizeDaliyStruc();
		// String date=ps.getYear()+"-"+ps.getMonth()+"-"+(i<10?"0"+i:i);
		// pls.setDay(i);
		// pls.setMoney(2);
		// boolean betFlag=false;//当天投注标示
		// boolean rewardFlag=false;//当天领奖标示
		// if(strucs!=null&&!strucs.isEmpty()){
		// for(DailyActivityRewardStruc ds:strucs){
		// if(date.equals(ds.getDate())){
		// betFlag=true;//有日期匹配表明有有效投注记录
		// if(ds.getHadGet()){
		// rewardFlag=true;//标示此投注已领奖
		// }
		// }
		// }
		// }
		// if(betFlag){
		// if(rewardFlag){
		// pls.setStatus("accepted");
		// }else{
		// pls.setStatus("enabled");
		// }
		// }else{
		// if(i>ps.getThisDay()){
		// pls.setStatus("comming");
		// }else if(i<ps.getThisDay()){
		// pls.setStatus("expired");
		// }else{
		// pls.setStatus("lottery");
		// }
		// }
		// plss.add(pls);
		// }
		ps.setPrizes(plss);
		model.addAttribute("ps", ps);
		model.addAttribute("userName", RequestContext.getCurrUser()
				.getUserName());
		model.addAttribute("luckyList", getLuckList());
		model.addAttribute("myLuckyList", getMyLuckList());
		GetLuckyNumberRequest getLuckyNumberRequest = new GetLuckyNumberRequest();
		getLuckyNumberRequest.setActivityStartTime(DateUtils
				.getStartTimeOfDate(DateUtils.parse(dailyActivityStartTime)));
		getLuckyNumberRequest.setActivityEndTime(DateUtils
				.getEndTimeOfDate(DateUtils.parse(dailyActivityEndTime)));
		getLuckyNumberRequest.setUserId(RequestContext.getCurrUser().getId());
		Response<GetLuckyNumberResponse> getLuckyNumberResponse = betHttpClient
				.getLuckyNumber(getLuckyNumberRequest);
		model.addAttribute("LuckyNumber", getLuckyNumberResponse.getBody()
				.getResult().getNumber() < 0 ? 0 : getLuckyNumberResponse
				.getBody().getResult().getNumber());
		model.addAttribute("activityStartTime", DateUtils
				.getStartTimeOfDate(DateUtils.parse(dailyActivityStartTime)));
		model.addAttribute("activityEndTime", DateUtils
				.getEndTimeOfDate(DateUtils.parse(dailyActivityEndTime)));
		Long userId = RequestContext.getCurrUser().getId();
		Response<Long> response = betHttpClient.getUserBal(userId);
		model.addAttribute("userbal", response.getBody().getResult()
				.longValue());
		return "/prize/prizeIndex";
	}

	/**
	 * @param 手机端活动首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prizeMobileIndex")
	public String prizeMobileEnter(Model model) throws Exception {
		DailyActivityRequest request = new DailyActivityRequest();
		request.setStartTime(dailyActivityStartTime);
		request.setEndTime(dailyActivityEndTime);
		request.setUserId(RequestContext.getCurrUser().getId());
		Response<DailyActivityResponse> dar = betHttpClient
				.getUserDailyActivityStruc(request);
		List<DailyActivityRewardStruc> strucs = dar.getBody().getResult()
				.getStrucs();// 获取当前用户的投注领奖数据结构
		Date now = new Date();
		Date startTime = DateUtils.getStartTimeOfDate(DateUtils
				.parse(dailyActivityStartTime));
		Date endTime = DateUtils.getEndTimeOfDate(DateUtils
				.parse(dailyActivityEndTime));

		PrizeStruc ps = new PrizeStruc();
		ps.setThisDay(DateUtils.getDay(new Date()));
		List<PrizeDaliyStruc> plss = new ArrayList<PrizeDaliyStruc>();
		if (now.before(startTime)) {// 活动未开始
			while (startTime.before(endTime)) {
				PrizeDaliyStruc pls = new PrizeDaliyStruc();
				pls.setDay(DateUtils.getDay(startTime));
				pls.setYear(DateUtils.getYear(startTime));
				pls.setMonth(DateUtils.getMonth(startTime));
				pls.setMoney(2);
				pls.setStatus("comming");
				plss.add(pls);
				startTime = DateUtils.addDays(startTime, 1);
			}
		} else if (now.after(endTime)) {// 活动已结束
			while (startTime.before(endTime)) {
				PrizeDaliyStruc pls = new PrizeDaliyStruc();
				pls.setDay(DateUtils.getDay(startTime));
				pls.setYear(DateUtils.getYear(startTime));
				pls.setMonth(DateUtils.getMonth(startTime));
				pls.setMoney(2);
				boolean betFlag = false;// 当天投注标示
				boolean rewardFlag = false;// 当天领奖标示
				if (strucs != null && !strucs.isEmpty()) {
					for (DailyActivityRewardStruc ds : strucs) {
						if (DateUtils.format(startTime).equals(ds.getDate())) {
							betFlag = true;// 有日期匹配表明有有效投注记录
							if (ds.getHadGet()) {
								rewardFlag = true;// 标示此投注已领奖
							}
						}
					}
				}
				if (betFlag) {
					if (rewardFlag) {
						pls.setStatus("accepted");
					} else {
						pls.setStatus("enabled");
					}
				} else {
					pls.setStatus("expired");
				}
				plss.add(pls);
				startTime = DateUtils.addDays(startTime, 1);
			}
		} else {// 活动进行中
			while (startTime.before(endTime)) {
				PrizeDaliyStruc pls = new PrizeDaliyStruc();
				pls.setDay(DateUtils.getDay(startTime));
				pls.setYear(DateUtils.getYear(startTime));
				pls.setMonth(DateUtils.getMonth(startTime));
				pls.setMoney(2);
				boolean betFlag = false;// 当天投注标示
				boolean rewardFlag = false;// 当天领奖标示
				if (strucs != null && !strucs.isEmpty()) {
					for (DailyActivityRewardStruc ds : strucs) {
						if (DateUtils.format(startTime).equals(ds.getDate())) {
							betFlag = true;// 有日期匹配表明有有效投注记录
							if (ds.getHadGet()) {
								rewardFlag = true;// 标示此投注已领奖
							}
						}
					}
				}
				if (betFlag) {
					if (rewardFlag) {
						pls.setStatus("accepted");
					} else {
						pls.setStatus("enabled");
					}
				} else {
					if (startTime.after(DateUtils.getStartTimeOfDate(now))) {
						pls.setStatus("comming");
					} else if (startTime.before(DateUtils
							.getStartTimeOfDate(now))) {
						pls.setStatus("expired");
					} else if (startTime.equals(DateUtils
							.getStartTimeOfDate(now))) {
						pls.setStatus("lottery");
					}
				}
				plss.add(pls);
				startTime = DateUtils.addDays(startTime, 1);
			}

		}
		ps.setPrizes(plss);
		model.addAttribute("ps", ps);
		model.addAttribute("userName", RequestContext.getCurrUser()
				.getUserName());
		model.addAttribute("luckyList", getLuckList());
		model.addAttribute("myLuckyList", getMyLuckList());
		GetLuckyNumberRequest getLuckyNumberRequest = new GetLuckyNumberRequest();
		getLuckyNumberRequest.setActivityStartTime(DateUtils
				.getStartTimeOfDate(DateUtils.parse(dailyActivityStartTime)));
		getLuckyNumberRequest.setActivityEndTime(DateUtils
				.getEndTimeOfDate(DateUtils.parse(dailyActivityEndTime)));
		getLuckyNumberRequest.setUserId(RequestContext.getCurrUser().getId());
		Response<GetLuckyNumberResponse> getLuckyNumberResponse = betHttpClient
				.getLuckyNumber(getLuckyNumberRequest);
		model.addAttribute("LuckyNumber", getLuckyNumberResponse.getBody()
				.getResult().getNumber() < 0 ? 0 : getLuckyNumberResponse
				.getBody().getResult().getNumber());
		return "/prize/prizeMobileIndex";
	}

	/**
	 * web端领取日常投注奖励
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDailyBetPrize")
	@ResponseBody
	public Object getDailyBetPrize(@RequestParam("date") String date,
			@RequestParam("desc") String money) throws Exception {
		return getDailyPrize(date, true);

	}

	private Object getDailyPrize(String date, Boolean isWeb) {
		String temp = date.replace("年", "-").replace("月", "-").replace("日", "");
		String da = DateUtils.format(DateUtils.parse(temp));
		DailyBetPrizeRequest request = new DailyBetPrizeRequest();
		request.setBetDate(da);
		request.setMoney(2L);
		request.setUserId(RequestContext.getCurrUser().getId());
		request.setChannel(isWeb ? 0 : 1);
		PrizeLuckyStruc pls = new PrizeLuckyStruc();
		try {
			Response<DailyBetPrizeResponse> response = betHttpClient
					.getDailyBetPrize(request);
			if (response.getHead().getStatus() != 0) {
				pls.setStatus("fail");
				return pls;
			}
			pls.setStatus("ok");
			return pls;
		} catch (Exception e) {
			logger.error("领取日常投注奖励失败。。", e);
			pls.setStatus("fail");
			return pls;
		}
	}

	/**
	 * 手机端领取日常投注奖励
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMobileDailyBetPrize")
	@ResponseBody
	public Object getMobileDailyBetPrize(@RequestParam("date") String date,
			@RequestParam("desc") String money) throws Exception {
		return getDailyPrize(date, false);
	}

	/**
	 * web抽奖
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLucky")
	@ResponseBody
	public Object getLucky() throws Exception {
		return getLuck(true);
	}

	private Object getLuck(Boolean isWeb) {
		GetLuckyRequest request = new GetLuckyRequest();
		request.setUserId(RequestContext.getCurrUser().getId());
		request.setActivityEndTime(DateUtils.getEndTimeOfDate(DateUtils
				.parse(dailyActivityEndTime)));
		request.setActivityStartTime(DateUtils.getStartTimeOfDate(DateUtils
				.parse(dailyActivityStartTime)));
		request.setChannel(isWeb ? 0 : 1);
		PrizeLuckyStruc pls = new PrizeLuckyStruc();
		Response<GetLuckyResponse> response;
		try {
			response = betHttpClient.getLucky(request);
		} catch (Exception e) {
			logger.error("抽奖异常", e);
			pls.setStatus("fail");
			return pls;
		}
		if (response.getHead().getStatus() != 0) {
			pls.setStatus("fail");
			return pls;
		}
		pls.setStatus("ok");
		pls.setLuckyidx(response.getBody().getResult().getLuckyId().intValue());
		pls.setDate(DateUtils.format(response.getBody().getResult().getDate(),
				DateUtils.DATETIME_FORMAT_PATTERN));
		pls.setDesc(response.getBody().getResult().getDesc());
		return pls;
	}

	/**
	 * 手机端抽奖
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMobileLucky")
	@ResponseBody
	public Object getMobileLucky() throws Exception {
		return getLuck(false);
	}

	/**
	 * 全部中奖名单
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLuckyList")
	@ResponseBody
	public Object getLuckyList() {
		List<PrizeListStruc> plss = getLuckList();

		PrizeList pl = new PrizeList();
		pl.setStatus("ok");
		pl.setList(plss);
		return pl;
	}

	private List<PrizeListStruc> getLuckList() {
		ActivityUserAwardRequest request = new ActivityUserAwardRequest();
		request.setUserId(RequestContext.getCurrUser().getId());
		Response<ActivityUserAwardResponse> reponse = new Response<ActivityUserAwardResponse>();
		try {
			reponse = betHttpClient.queryTodayUserAwardLog(request);
		} catch (Exception e) {
			logger.error("获取今天抽奖列表异常。。", e);
		}
		List<PrizeListStruc> plss = new ArrayList<PrizeListStruc>();
		if (reponse.getBody().getResult() != null
				&& !reponse.getBody().getResult().getLogs().isEmpty()) {
			for (ActivityUserAwardLog log : reponse.getBody().getResult()
					.getLogs()) {
				PrizeListStruc pls = new PrizeListStruc();
				pls.setUsername(format(log.getUserName()));
				pls.setDesc("抽中了" + log.getAward() + "元");
				plss.add(pls);
			}
		}
		return plss;
	}

	/**
	 * 格式化抽奖用户名 当length>4时 取 前两位+***+后两位 当length<=4时 取第一位+***+最后一位
	 * 
	 * @param userName
	 * @return
	 */
	private String format(String userName) {
		if (userName != null) {
			if (userName.length() > 4) {
				return userName.substring(0, 2)
						+ "***"
						+ userName.substring(userName.length() - 2,
								userName.length());
			} else {
				return userName.substring(0, 1)
						+ "***"
						+ userName.substring(userName.length() - 1,
								userName.length());
			}

		}

		return null;
	}

	/**
	 * 我的抽奖记录
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyLuckyList")
	@ResponseBody
	public List<MyLuckStruc> getMyLuckyList() {
		List<MyLuckStruc> plss = getMyLuckList();

		return plss;
	}

	private List<MyLuckStruc> getMyLuckList() {
		ActivityUserAwardRequest request = new ActivityUserAwardRequest();
		request.setUserId(RequestContext.getCurrUser().getId());
		Response<ActivityUserAwardResponse> reponse = new Response<ActivityUserAwardResponse>();
		try {
			reponse = betHttpClient.queryMyUserAwardLog(request);
		} catch (Exception e) {
			logger.error("获取今天抽奖列表异常。。", e);
		}
		List<MyLuckStruc> plss = new ArrayList<MyLuckStruc>();
		if (reponse.getBody().getResult() != null
				&& !reponse.getBody().getResult().getLogs().isEmpty()) {
			for (ActivityUserAwardLog log : reponse.getBody().getResult()
					.getLogs()) {
				MyLuckStruc pls = new MyLuckStruc();
				pls.setAwardName(log.getAwardName());
				pls.setDate(DateUtils.format(log.getGmtCreated(),
						DateUtils.DATETIME_FORMAT_PATTERN));
				plss.add(pls);
			}
		}
		return plss;
	}
	//羊年相关移动到sheepYearController

//	/**
//	 * @param 网页端活动首页
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/sheepYearActivity")
//	public String sheepYearEnter(Model model,Long device) throws Exception {
//		Long userId=RequestContext.getCurrUser().getId();
//		if(device!=null){
//			initSheepYear(model,userId,device);
//		}else{
//			initSheepYear(model,userId,4L);
//		}
//
//		return "/prize/sheepYearActivity";
//	}
//
//	private void initSheepYear(Model model,Long userId,Long channel) throws Exception {
//		// 获取红包列表 假如没有则初始化红包列表
//		ActivitySheepUserHongBaoRequest request = new ActivitySheepUserHongBaoRequest();
//		request.setUserId(userId);
////		request.setUserName(RequestContext.getCurrUser().getUserName());
//		request.setChannel(channel);
//		Response<ActivitySheepUserHongBaoResponse> reponse = betHttpClient
//				.getUserHongBaoList(request);
//		// 获取大小押大小数据 假如没有初始化押大小基础数据
//		ActivitySheepUserDiceRequest diceRequest = new ActivitySheepUserDiceRequest();
//		diceRequest.setUserId(userId);
////		diceRequest.setUserName(userName);
//		diceRequest.setChannel(channel);
//		Response<ActivitySheepUserDiceResponse> dicereponse = betHttpClient
//				.getUserDiceData(diceRequest);
//		model.addAttribute("diceLastNum", dicereponse.getBody().getResult()
//				.getLastDiceTime());
//		model.addAttribute("diceContinueNum", dicereponse.getBody().getResult()
//				.getDiceContinus()%8);
//		List<ActivitySheepYearDiceDetailStruc> list = new ArrayList<ActivitySheepYearDiceDetailStruc>();
//		if (dicereponse.getBody().getResult().getList() != null
//				&& !dicereponse.getBody().getResult().getList().isEmpty()) {
//			for (ActivitySheepDetail ad : dicereponse.getBody().getResult()
//					.getList()) {
//				ActivitySheepYearDiceDetailStruc add = new ActivitySheepYearDiceDetailStruc();
//				if (ad.getDrawResult() != null) {
//					add.setResult(ad.getDrawResult().split(","));
//				}
//				add.setTime(DateUtils.format(ad.getActivityTime(),
//						"MM-dd HH:mm:ss"));
//				add.setType(ad.getDrawType());
//				add.setWinMoney(ad.getAward() / 10000);
//				list.add(add);
//			}
//		}
//		model.addAttribute("diceDetail", list);
//		// 转盘活动数据 假如没有舒适化转盘活动基础数据
//		ActivitySheepUserRotaryRequest rotaryRequest = new ActivitySheepUserRotaryRequest();
//		rotaryRequest.setUserId(userId);
////		rotaryRequest.setUserName(userName);
//		rotaryRequest.setChannel(channel);
//		Response<ActivitySheepUserRotaryResponse> rotaryreponse = betHttpClient
//				.getUserRotaryData(rotaryRequest);
//		model.addAttribute("rotaryLastNum", rotaryreponse.getBody().getResult()
//				.getLastDiceTime());
//		List<ActivitySheepYearRotaryDetailStruc> rotary = new ArrayList<ActivitySheepYearRotaryDetailStruc>();
//		List<ActivitySheepYearRotaryDetailStruc> allRotary = new ArrayList<ActivitySheepYearRotaryDetailStruc>();
//		if (rotaryreponse.getBody().getResult().getMyList() != null
//				&& !rotaryreponse.getBody().getResult().getMyList().isEmpty()) {
//			for (ActivitySheepDetail ad : rotaryreponse.getBody().getResult()
//					.getMyList()) {
//				ActivitySheepYearRotaryDetailStruc add = new ActivitySheepYearRotaryDetailStruc();
//				Long time = DateUtils.calcMinutesBetween(ad.getActivityTime(),
//						new Date());
//				Long mtime = time / 60;
//				Long dTime = time / 1440;
//				if (time.intValue() < 60) {
//					add.setDate(time + "分钟前");
//				} else if (mtime > 0 && mtime < 24) {
//					add.setDate(mtime + "小时前");
//				} else if (dTime > 0) {
//					add.setDate(dTime + "天前");
//				}
//
//				add.setDesc(ad.getAward() / 10000 + "元奖金");
//				add.setUserName(ad.getUserName());
//				rotary.add(add);
//			}
//		}
//
//		if (rotaryreponse.getBody().getResult().getAllList() != null
//				&& !rotaryreponse.getBody().getResult().getAllList().isEmpty()) {
//			for (ActivitySheepDetail ad : rotaryreponse.getBody().getResult()
//					.getAllList()) {
//				ActivitySheepYearRotaryDetailStruc add = new ActivitySheepYearRotaryDetailStruc();
//				Long time = DateUtils.calcMinutesBetween(ad.getActivityTime(),
//						new Date());
//				Long mtime = time / 60;
//				Long dTime = time / 1440;
//				if (time.intValue() < 60) {
//					add.setDate(time + "分钟前");
//				} else if (mtime > 0 && mtime < 24) {
//					add.setDate(mtime + "小时前");
//				} else if (dTime > 0) {
//					add.setDate(dTime + "天前");
//				}
//
//				add.setDesc(ad.getAward() / 10000 + "元奖金");
//				add.setUserName(format(ad.getUserName()));
//				allRotary.add(add);
//			}
//		}
//		model.addAttribute("myRotary", rotary);
//
//		model.addAttribute("allRotary", allRotary);
//		model.addAttribute("userName",RequestContext.getCurrUser().getUserName());
//	}
//
//	/**
//	 * 全部中奖名单
//	 * 
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/getMyRotaryList")
//	@ResponseBody
//	public Object getMyRotaryList() throws Exception {
//		ActivitySheepUserRotaryRequest rotaryRequest = new ActivitySheepUserRotaryRequest();
//		rotaryRequest.setUserId(RequestContext.getCurrUser().getId());
////		rotaryRequest.setUserName(RequestContext.getCurrUser().getUserName());
//		Response<ActivitySheepUserRotaryResponse> rotaryreponse = betHttpClient
//				.getUserRotaryData(rotaryRequest);
//		List<ActivitySheepYearRotaryDetailStruc> rotary = new ArrayList<ActivitySheepYearRotaryDetailStruc>();
//		if (rotaryreponse.getBody().getResult().getMyList() != null
//				&& !rotaryreponse.getBody().getResult().getMyList().isEmpty()) {
//			for (ActivitySheepDetail ad : rotaryreponse.getBody().getResult()
//					.getMyList()) {
//				ActivitySheepYearRotaryDetailStruc add = new ActivitySheepYearRotaryDetailStruc();
//				Long time = DateUtils.calcMinutesBetween(ad.getActivityTime(),
//						new Date());
//				Long mtime = time / 60;
//				Long dTime = time / 1440;
//				if (time.intValue() < 60) {
//					add.setDate(time + "分钟前");
//				} else if (mtime > 0 && mtime < 24) {
//					add.setDate(mtime + "小时前");
//				} else if (dTime > 0) {
//					add.setDate(dTime + "天前");
//				}
//
//				add.setDesc(ad.getAward() / 10000 + "元奖金");
//				add.setUserName(ad.getUserName());
//				rotary.add(add);
//			}
//		}
//		return rotary;
//	}
//	
//	
//	/**
//	 * 全部中奖名单
//	 * 
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/getMyDiceList")
//	@ResponseBody
//	public Object getMyDiceList() throws Exception {
//		ActivitySheepUserDiceRequest diceRequest = new ActivitySheepUserDiceRequest();
//		diceRequest.setUserId(RequestContext.getCurrUser().getId());
////		diceRequest.setUserName(RequestContext.getCurrUser().getUserName());
//		Response<ActivitySheepUserDiceResponse> dicereponse = betHttpClient
//				.getUserDiceData(diceRequest);
//		List<ActivitySheepYearDiceDetailStruc> list = new ArrayList<ActivitySheepYearDiceDetailStruc>();
//		if (dicereponse.getBody().getResult().getList() != null
//				&& !dicereponse.getBody().getResult().getList().isEmpty()) {
//			for (ActivitySheepDetail ad : dicereponse.getBody().getResult()
//					.getList()) {
//				ActivitySheepYearDiceDetailStruc add = new ActivitySheepYearDiceDetailStruc();
//				if (ad.getDrawResult() != null) {
//					add.setResult(ad.getDrawResult().split(","));
//				}
//				add.setTime(DateUtils.format(ad.getActivityTime(),
//						"MM-dd HH:mm:ss"));
//				add.setType(ad.getDrawType());
//				add.setWinMoney(ad.getAward() / 10000);
//				list.add(add);
//			}
//		}
//		return list;
//	}
//	
//	
//
//	/**
//	 * @param 网页端活动首页
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/sheepYearMobileActivity")
//	public String sheepYearMobileEnter(Model model,@RequestParam Long device,@RequestParam String userId) throws Exception {
////		String temp1=StringMgr.Aes128Encode("312", aesKey, aesIv);
//		String temp=StringMgr.Aes128Decode(userId, aesKey, aesIv);
//		Long uId=Long.valueOf(temp);
//		
//		
//		
//		initSheepYear(model,uId,device);
//
//
//		return "/prize/sheepYearMobileActivity";
//	}
//
//	@RequestMapping(value = "/rewards")
//	@ResponseBody
//	public Object getRewards(Integer model, Long index, Long amount)
//			throws Exception {
//		ActivitySheepYearRewardStruc struc = new ActivitySheepYearRewardStruc();
//		Date startTime = DateUtils.getStartTimeOfDate(DateUtils
//				.parse(sheepYearActivityHongbaoStartTime));
//		Date endTime = DateUtils.getEndTimeOfDate(DateUtils
//				.parse(sheepYearActivityHongbaoEndTime));
//		Date nowDate = new Date();
//		if (nowDate.before(startTime) || nowDate.after(endTime)) {
//			struc.setStatus(1);
//			struc.setMessage("当前日期不在活动时间范围");
//			return struc;
//		}
//		Integer isvip = 1;
//		try {
//			UserStrucResponse usr = (UserStrucResponse) RequestContext
//					.getCurrUser();
//			isvip = usr.getVipLvl();
//		} catch (Exception e) {
//			logger.error("获取用户信息异常", e);
//		}
//		ActivitySheepUserHongBaoRequest request = new ActivitySheepUserHongBaoRequest();
//		request.setUserId(RequestContext.getCurrUser().getId());
////		request.setUserName(RequestContext.getCurrUser().getUserName());
//		Response<ActivitySheepUserHongBaoResponse> reponse = betHttpClient
//				.getUserHongBaoList(request);// 获取红包列表 并初始化数据
//		if (model == null) {
//			model = 1;
//		}
//		if (model == 1) {
//			List<ActivitySheepHongBao> list = reponse.getBody().getResult()
//					.getList();
//			List<ActivitySheepYearHongBaoStep> steps = new ArrayList<ActivitySheepYearHongBaoStep>();
//			struc.setStatus(0);
//			struc.setMessage("红包信息更新成功");
//			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
//			for (ActivitySheepHongBao ashb : list) {
//				ashb.setStatus(ashb.getStatus());
//				steps.add(new ActivitySheepYearHongBaoStep(ashb.getStatus()
//						.intValue()));
//				if (ashb.getStatus().intValue() == 6) {
//					data.setIndex(ashb.getIndexHb().intValue());
//					data.setTotal(3L);
//					data.setRewardsNum(ashb.getAward() / 10000);
//				}
//				if (ashb.getStatus().intValue() == 5) {
//					data.setExpected(ashb.getTargetAward() / 10000);
//					data.setDeadline(ashb.getDeadTime().getTime());
//					data.setNowTime(nowDate.getTime());
//					data.setLastBet(ashb.getAllAward() == null ? 0 : ashb
//							.getAllAward() / 10000);
//					data.setRewardsNum(ashb.getAward() / 10000);
//				}
//			}
//			data.setRewards(steps);
//			struc.setData(data);
//			return struc;
//		} else if (model == 2) {// 提取红包金额 vip的上限多1w
//			struc.setStatus(0);
//			struc.setMessage("红包信息更新成功");
//			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
//			if (isvip == 1) {
//				data.setIsVip(true);
//				data.setMinNum(100L);
//				data.setMaxNum(38888L);
//			} else {
//				data.setIsVip(false);
//				data.setMinNum(100L);
//				data.setMaxNum(28888L);
//			}
//			struc.setData(data);
//			return struc;
//		} else if (model == 3) {// 申请红包金额，保存红包申请信息
//			ActivitySheepUserApplyHongBaoRequest applyRequest = new ActivitySheepUserApplyHongBaoRequest();
//			if (DateUtils.addDays(nowDate, 2).before(endTime)) {
//				applyRequest.setDeadDate(DateUtils.addDays(nowDate, 2));
//			} else {
//				applyRequest.setDeadDate(endTime);
//			}
//			if (isvip == 1) {
//				if (amount < 100 || amount > 38888) {
//					struc.setStatus(1);
//					struc.setMessage("红包金额不在范围之内");
//					return struc;
//				}
//			} else {
//				if (amount < 100 || amount > 28888) {
//					struc.setStatus(1);
//					struc.setMessage("红包金额不在范围之内");
//					return struc;
//				}
//			}
//			applyRequest.setApplyDate(nowDate);
//			applyRequest.setAward(amount * 10000);
//			applyRequest.setIndex(index);
//			applyRequest.setTargetAmount(amount * 30 * 10000);
//			applyRequest.setUserId(RequestContext.getCurrUser().getId());
//			Response<ActivitySheepUserApplyHongBaoResponse> applyReponse = betHttpClient
//					.applyHongbaoAmount(applyRequest);// 申请红包
//
//			struc.setStatus(0);
//			struc.setMessage("提交余额成功");
//			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
//			data.setExpected(amount * 30);
//			data.setNowTime(nowDate.getTime());
//			data.setDeadline(applyRequest.getDeadDate().getTime());
//			data.setRewardsNum(amount);
//			data.setLastBet(applyReponse.getBody().getResult()
//					.getValidBetAmount() / 10000);
//			struc.setData(data);
//			return struc;
//		} else if (model == 4) {// 红包作废
//			ActivitySheepUserHongBaoAbortRequest abortRequest = new ActivitySheepUserHongBaoAbortRequest();
//			abortRequest.setUserId(RequestContext.getCurrUser().getId());
//			abortRequest.setIndex(index);
//			betHttpClient.aborthongbao(abortRequest);
//			struc.setStatus(0);
//			struc.setMessage("红包已经作废");
//			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
//			data.setIndex(index.intValue());
//			data.setTotal(3L);
//			struc.setData(data);
//			return struc;
//		} else if (model == 5) {// 领取红包
//			ActivitySheepUserHongBaoDrawRequest drawRequest = new ActivitySheepUserHongBaoDrawRequest();
//			drawRequest.setUserId(RequestContext.getCurrUser().getId());
//			drawRequest.setIndex(index);
//			Response<ActivitySheepUserHongBaoDrawResponse> drawResponse = betHttpClient
//					.drawHongbao(drawRequest);
//			struc.setStatus(0);
//			struc.setMessage("领取成功");
//			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
//			data.setIndex(index.intValue());
//			data.setTotal(3L);
//			data.setRewardsNum(drawResponse.getBody().getResult().getAmount() / 10000);
//			struc.setData(data);
//			return struc;
//
//		}
//
//		return struc;
//	}
//
//	// 押大小返回数据
//	@RequestMapping(value = "/dice")
//	@ResponseBody
//	public Object dice(String diceType, String channel) throws Exception {
//		ActivitySheepYearDiceStruc asyd = new ActivitySheepYearDiceStruc();
//		Date startTime = DateUtils.getStartTimeOfDate(DateUtils
//				.parse(diceStartTime));
//		Date endTime = DateUtils.getEndTimeOfDate(DateUtils.parse(diceEndTime));
//		Date nowDate = new Date();
//		if (nowDate.before(startTime) || nowDate.after(endTime)) {
//			asyd.setStatus("fail");
//			asyd.setMsg("当前日期不在活动时间范围");
//			return asyd;
//		}
//		ActivitySheepUserDiceAwardRequest request = new ActivitySheepUserDiceAwardRequest();
//		// 0Android| 1webOS| 2i hone| 3iPad| 4iPod| 5BlackBerry| 6IEMobile|
//		// 7Opera Mini
//		channel = "web3.0";
//		if (channel.contains("web")) {
//			request.setChannel(1L);
//		} else {
//			request.setChannel(0L);
//		}
//		try {
//			request.setIsGuessLittle(diceType.equals("small") ? true : false);
//			request.setUserId(RequestContext.getCurrUser().getId());
//			Response<ActivitySheepUserDiceAwardResponse> diceAwardResponse = betHttpClient
//					.diceAward(request);
//			ActivitySheepUserDiceAwardResponse result = diceAwardResponse
//					.getBody().getResult();
//
//			if (result != null) {
//				if(!result.isHaveAward()){
//					asyd.setStatus("noAward");
//					asyd.setMsg("已无奖品");
//					return asyd;
//				}
//				
//				asyd.setDate(DateUtils.format(new Date(), "MM-dd HH:mm:ss"));
//				asyd.setStatus("ok");
//				asyd.setDiceAmount(result.getLastGuessNum());
//				asyd.setDiceContinus(result.getContinuousWinNum());
//				asyd.setDiceStatus(result.isWin() ? "win" : "lose");
//				// asyd.setMsg("aaa");
//				asyd.setResult(result.getResultNum());
//				asyd.setType(diceType);
//				asyd.setWinMoney(result.getAward() / 10000);
//			}
//		} catch (Exception e) {
//			logger.error("押大小错误", e);
//			asyd.setStatus("fail");
//		}
//		return asyd;
//	}
//
//	// 转盘
//	@RequestMapping(value = "/rotary")
//	@ResponseBody
//	public Object rotary(String diceType, String channel) throws Exception {
//		ActivitySheepYearRotaryStruc asyd = new ActivitySheepYearRotaryStruc();
//		Date startTime = DateUtils.getStartTimeOfDate(DateUtils
//				.parse(rotaryStartTime));
//		Date endTime = DateUtils.getEndTimeOfDate(DateUtils
//				.parse(rotaryEndTime));
//		Date nowDate = new Date();
//		if (nowDate.before(startTime) || nowDate.after(endTime)) {
//			asyd.setStatus("fail");
//			asyd.setDesc("当前日期不在活动时间范围");
//			return asyd;
//		}
//		Integer isvip = 1;
//		try {
//			UserStrucResponse usr = (UserStrucResponse) RequestContext
//					.getCurrUser();
//			isvip = usr.getVipLvl();
//		} catch (Exception e) {
//			logger.error("获取用户信息异常", e);
//		}
//		if (isvip != 1) {
//			asyd.setStatus("fail");
//			asyd.setDesc("需要vip权限");
//			return asyd;
//		}
//
//		ActivitySheepUserRotaryRewardRequest request = new ActivitySheepUserRotaryRewardRequest();
//		// 0Android| 1webOS| 2i hone| 3iPad| 4iPod| 5BlackBerry| 6IEMobile|
//		// 7Opera Mini
//		channel = "web3.0";
//		if (channel.contains("web")) {
//			request.setChannel(1L);
//		} else {
//			request.setChannel(0L);
//		}
//		try {
//			request.setUserId(RequestContext.getCurrUser().getId());
//			Response<ActivitySheepUserRotaryRewardResponse> diceAwardResponse = betHttpClient
//					.rotaryAward(request);
//			ActivitySheepUserRotaryRewardResponse result = diceAwardResponse
//					.getBody().getResult();
//			if (result != null) {
//				if(!result.isHaveAward()){
//					asyd.setStatus("noAward");
//					asyd.setDesc("已无奖品");
//					return asyd;
//				}
//				
//				Long time = DateUtils.calcMinutesBetween(new Date(),
//						result.getDate());
//				if (time.intValue() > 50) {
//					time = 50L;
//				}
//				asyd.setDate(time + "分钟前");
//				asyd.setStatus("ok");
//				if (result.getDesc().equals("一等奖")) {
//					asyd.setLuckyidx(5L);
//				} else if (result.getDesc().equals("二等奖")) {
//					asyd.setLuckyidx(4L);
//				} else if (result.getDesc().equals("三等奖")) {
//					asyd.setLuckyidx(3L);
//				} else if (result.getDesc().equals("四等奖")) {
//					asyd.setLuckyidx(2L);
//				} else if (result.getDesc().equals("五等奖")) {
//					asyd.setLuckyidx(1L);
//				} else {
//					asyd.setLuckyidx(0L);
//				}
//				asyd.setTimes(result.getLastTime());
//				asyd.setUsername(RequestContext.getCurrUser().getUserName());
//				asyd.setDesc(result.getAward() / 10000 + "元奖金");
//			}
//		} catch (Exception e) {
//			logger.error("转盘错误", e);
//			asyd.setStatus("fail");
//		}
//		return asyd;
//	}

}
