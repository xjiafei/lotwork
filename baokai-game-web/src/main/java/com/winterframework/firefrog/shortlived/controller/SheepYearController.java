package com.winterframework.firefrog.shortlived.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.web.bet.util.BetHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.UserInfoRequest;
import com.winterframework.firefrog.game.web.dto.UserInfoResponse;
import com.winterframework.firefrog.game.web.util.StringMgr;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivityRewardDateStruc;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserApplyHongBaoRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserApplyHongBaoResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceAwardRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceAwardResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserDiceResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoAbortRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoDrawRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoDrawResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserHongBaoResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryRewardRequest;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepUserRotaryRewardResponse;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepYearDiceDetailStruc;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepYearDiceStruc;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepYearHongBaoStep;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepYearRewardStruc;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepYearRotaryDetailStruc;
import com.winterframework.firefrog.shortlived.sheepactivity.dto.ActivitySheepYearRotaryStruc;
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
@Controller("SheepYearController")
@RequestMapping(value = "/sheepYear")
public class SheepYearController {
	private Logger logger = LoggerFactory.getLogger(SheepYearController.class);

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
	 * @param 网页端活动首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sheepYearActivity")
	public String sheepYearEnter(Model model,Long device,HttpServletRequest request) throws Exception {
		if(RequestContext.getCurrUser()==null||RequestContext.getCurrUser().getId()==null||RequestContext.getCurrUser().getId()==0){
			String host=request.getHeader("Host");
			if(StringUtils.isNotEmpty(host)){
				request.setAttribute("currentContextPath", "http://"+host);
				request.setAttribute("gamecenterPath", "http://"+host.replace("www", "admin").replace("www2", "admin"));
				request.setAttribute("frontGame", "http://"+host.replace("www2", "em").replace("www", "em"));
				request.setAttribute("frontContextPath", "http://"+fromHosttoFrontHost(host));
				request.setAttribute("userContextPath", "http://"+fromHosttoFrontHost(host));
				request.setAttribute("adminContextPath",  "http://"+host.replace("www", "admin").replace("www2", "admin"));
			}
			
			return "redirect:"+request.getAttribute("userContextPath")+"/login/index";
		}
		Long userId=RequestContext.getCurrUser().getId();
		if(device!=null){
			initSheepYear(model,userId,device);
		}else{
			initSheepYear(model,userId,4L);
		}

		return "/prize/sheepYearActivity";
	}
	
	private String fromHosttoFrontHost(String host){
		if(host.contains("joy188")){
			return "www2.joy188.com:888";
		}
		return host.replace("admin","www").replace("em","www");
	}

	private void initSheepYear(Model model,Long userId,Long device) throws Exception {
		if(device==null){
			device=4L;
		}
		UserInfoRequest userInfoRequest=new UserInfoRequest();
		userInfoRequest.setUserId(userId);
		Response<UserInfoResponse> userInfoResponse=betHttpClient
				.getUserInfo(userInfoRequest);
		// 获取红包列表 假如没有则初始化红包列表
		ActivitySheepUserHongBaoRequest request = new ActivitySheepUserHongBaoRequest();
		request.setUserId(userId);
//		request.setUserName(RequestContext.getCurrUser().getUserName());
		request.setChannel(device);
		Response<ActivitySheepUserHongBaoResponse> reponse = betHttpClient
				.getUserHongBaoList(request);
		// 获取大小押大小数据 假如没有初始化押大小基础数据
		ActivitySheepUserDiceRequest diceRequest = new ActivitySheepUserDiceRequest();
		diceRequest.setUserId(userId);
//		diceRequest.setUserName(userName);
		diceRequest.setChannel(device);
		Response<ActivitySheepUserDiceResponse> dicereponse = betHttpClient
				.getUserDiceData(diceRequest);
		model.addAttribute("diceLastNum", dicereponse.getBody().getResult()
				.getLastDiceTime());
		model.addAttribute("diceContinueNum", dicereponse.getBody().getResult()
				.getDiceContinus());
		List<ActivitySheepYearDiceDetailStruc> list = new ArrayList<ActivitySheepYearDiceDetailStruc>();
		if (dicereponse.getBody().getResult().getList() != null
				&& !dicereponse.getBody().getResult().getList().isEmpty()) {
			for (ActivitySheepDetail ad : dicereponse.getBody().getResult()
					.getList()) {
				ActivitySheepYearDiceDetailStruc add = new ActivitySheepYearDiceDetailStruc();
				if (ad.getDrawResult() != null) {
					add.setResult(ad.getDrawResult().split(","));
				}
				add.setTime(DateUtils.format(ad.getActivityTime(),
						"MM-dd HH:mm:ss"));
				add.setType(ad.getDrawType());
				add.setWinMoney(ad.getAward() / 10000);
				list.add(add);
			}
		}
		model.addAttribute("diceDetail", list);
		// 转盘活动数据 假如没有舒适化转盘活动基础数据
		ActivitySheepUserRotaryRequest rotaryRequest = new ActivitySheepUserRotaryRequest();
		rotaryRequest.setUserId(userId);
//		rotaryRequest.setUserName(userName);
		rotaryRequest.setChannel(device);
		Response<ActivitySheepUserRotaryResponse> rotaryreponse = betHttpClient
				.getUserRotaryData(rotaryRequest);
		model.addAttribute("rotaryLastNum", rotaryreponse.getBody().getResult()
				.getLastDiceTime());
		List<ActivitySheepYearRotaryDetailStruc> rotary = new ArrayList<ActivitySheepYearRotaryDetailStruc>();
		List<ActivitySheepYearRotaryDetailStruc> allRotary = new ArrayList<ActivitySheepYearRotaryDetailStruc>();
		if (rotaryreponse.getBody().getResult().getMyList() != null
				&& !rotaryreponse.getBody().getResult().getMyList().isEmpty()) {
			for (ActivitySheepDetail ad : rotaryreponse.getBody().getResult()
					.getMyList()) {
				ActivitySheepYearRotaryDetailStruc add = new ActivitySheepYearRotaryDetailStruc();
				Long time = DateUtils.calcMinutesBetween(ad.getActivityTime(),
						new Date());
				Long mtime = time / 60;
				Long dTime = time / 1440;
				if (time.intValue() < 60) {
					add.setDate(time + "分钟前");
				} else if (mtime > 0 && mtime < 24) {
					add.setDate(mtime + "小时前");
				} else if (dTime > 0) {
					add.setDate(dTime + "天前");
				}

				add.setDesc(ad.getAward() / 10000 + "元奖金");
				add.setUserName(ad.getUserName());
				rotary.add(add);
			}
		}

		if (rotaryreponse.getBody().getResult().getAllList() != null
				&& !rotaryreponse.getBody().getResult().getAllList().isEmpty()) {
			for (ActivitySheepDetail ad : rotaryreponse.getBody().getResult()
					.getAllList()) {
				ActivitySheepYearRotaryDetailStruc add = new ActivitySheepYearRotaryDetailStruc();
				Long time = DateUtils.calcMinutesBetween(ad.getActivityTime(),
						new Date());
				Long mtime = time / 60;
				Long dTime = time / 1440;
				if (time.intValue() < 60) {
					add.setDate(time + "分钟前");
				} else if (mtime > 0 && mtime < 24) {
					add.setDate(mtime + "小时前");
				} else if (dTime > 0) {
					add.setDate(dTime + "天前");
				}

				add.setDesc(ad.getAward() / 10000 + "元奖金");
				add.setUserName(format(ad.getUserName()));
				allRotary.add(add);
			}
		}
		model.addAttribute("myRotary", rotary);

		model.addAttribute("allRotary", allRotary);
		model.addAttribute("userName",userInfoResponse.getBody().getResult().getUserName());
	}

	/**
	 * 全部中奖名单
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyRotaryList")
	@ResponseBody
	public Object getMyRotaryList(String userId) throws Exception {
		ActivitySheepUserRotaryRequest rotaryRequest = new ActivitySheepUserRotaryRequest();
		if(!StringUtils.isEmpty(userId)){
			String temp=StringMgr.Aes128Decode(userId, aesKey, aesIv);
			Long uId=Long.valueOf(temp);
			rotaryRequest.setUserId(uId);
		}else{
			rotaryRequest.setUserId(RequestContext.getCurrUser().getId());
		}
//		rotaryRequest.setUserName(RequestContext.getCurrUser().getUserName());
		Response<ActivitySheepUserRotaryResponse> rotaryreponse = betHttpClient
				.getUserRotaryData(rotaryRequest);
		List<ActivitySheepYearRotaryDetailStruc> rotary = new ArrayList<ActivitySheepYearRotaryDetailStruc>();
		if (rotaryreponse.getBody().getResult().getMyList() != null
				&& !rotaryreponse.getBody().getResult().getMyList().isEmpty()) {
			for (ActivitySheepDetail ad : rotaryreponse.getBody().getResult()
					.getMyList()) {
				ActivitySheepYearRotaryDetailStruc add = new ActivitySheepYearRotaryDetailStruc();
				Long time = DateUtils.calcMinutesBetween(ad.getActivityTime(),
						new Date());
				Long mtime = time / 60;
				Long dTime = time / 1440;
				if (time.intValue() < 60) {
					add.setDate(time + "分钟前");
				} else if (mtime > 0 && mtime < 24) {
					add.setDate(mtime + "小时前");
				} else if (dTime > 0) {
					add.setDate(dTime + "天前");
				}

				add.setDesc(ad.getAward() / 10000 + "元奖金");
				add.setUserName(ad.getUserName());
				rotary.add(add);
			}
		}
		return rotary;
	}
	
	
	/**
	 * 全部中奖名单
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMyDiceList")
	@ResponseBody
	public Object getMyDiceList(String userId) throws Exception {
		ActivitySheepUserDiceRequest diceRequest = new ActivitySheepUserDiceRequest();
		
		if(!StringUtils.isEmpty(userId)){
			String temp=StringMgr.Aes128Decode(userId, aesKey, aesIv);
			Long uId=Long.valueOf(temp);
			diceRequest.setUserId(uId);
		}else{
			diceRequest.setUserId(RequestContext.getCurrUser().getId());
		}
//		diceRequest.setUserName(RequestContext.getCurrUser().getUserName());
		Response<ActivitySheepUserDiceResponse> dicereponse = betHttpClient
				.getUserDiceData(diceRequest);
		List<ActivitySheepYearDiceDetailStruc> list = new ArrayList<ActivitySheepYearDiceDetailStruc>();
		if (dicereponse.getBody().getResult().getList() != null
				&& !dicereponse.getBody().getResult().getList().isEmpty()) {
			for (ActivitySheepDetail ad : dicereponse.getBody().getResult()
					.getList()) {
				ActivitySheepYearDiceDetailStruc add = new ActivitySheepYearDiceDetailStruc();
				if (ad.getDrawResult() != null) {
					add.setResult(ad.getDrawResult().split(","));
				}
				add.setTime(DateUtils.format(ad.getActivityTime(),
						"MM-dd HH:mm:ss"));
				add.setType(ad.getDrawType());
				add.setWinMoney(ad.getAward() / 10000);
				list.add(add);
			}
		}
		return list;
	}
	
	

	/**
	 * @param 网页端活动首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sheepYearMobileActivity")
	public String sheepYearMobileEnter(Model model,@RequestParam Long device,String userId) throws Exception {
//		String temp1=StringMgr.Aes128Encode("312", aesKey, aesIv);
		if(StringUtils.isEmpty(userId)){
			return "/prize/sheepYearMobileUpgrade";
		}
		
		String temp=StringMgr.Aes128Decode(userId, aesKey, aesIv);
		Long uId=Long.valueOf(temp);
		
		
		
		initSheepYear(model,uId,device);


		return "/prize/sheepYearMobileActivity";
	}

	@RequestMapping(value = "/rewards")
	@ResponseBody
	public Object getRewards(Integer model, Long index, Long amount,String userId)
			throws Exception {
		Long usrId=0L;
		if(!StringUtils.isEmpty(userId)){
			String temp=StringMgr.Aes128Decode(userId, aesKey, aesIv);
			usrId=Long.valueOf(temp);
		}else{
			usrId=RequestContext.getCurrUser().getId();
		}
		
		UserInfoRequest userInfoRequest=new UserInfoRequest();
		userInfoRequest.setUserId(usrId);
		Response<UserInfoResponse> userInfoResponse=betHttpClient
				.getUserInfo(userInfoRequest);
		
		ActivitySheepYearRewardStruc struc = new ActivitySheepYearRewardStruc();
		Date startTime = DateUtils.getStartTimeOfDate(DateUtils
				.parse(sheepYearActivityHongbaoStartTime));
		Date endTime = DateUtils.getEndTimeOfDate(DateUtils
				.parse(sheepYearActivityHongbaoEndTime));
		Date nowDate = new Date();
		
		Integer isvip = userInfoResponse.getBody().getResult().getVipLvl();
		ActivitySheepUserHongBaoRequest request = new ActivitySheepUserHongBaoRequest();
		request.setUserId(usrId);
//		request.setUserName(RequestContext.getCurrUser().getUserName());
		Response<ActivitySheepUserHongBaoResponse> reponse = betHttpClient
				.getUserHongBaoList(request);// 获取红包列表 并初始化数据
		
		if(model!=null&&model!=5){
		if (nowDate.before(startTime) || nowDate.after(endTime)) {//当时间超过截止一天后 所有红包显示为已过期，当有完成未领取的红包时 保留完成状态一天
			struc.setStatus("0");
			struc.setMessage("当前日期不在活动时间范围");
			List<ActivitySheepHongBao> list = reponse.getBody().getResult()
					.getList();
			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
			List<ActivitySheepYearHongBaoStep> steps = new ArrayList<ActivitySheepYearHongBaoStep>();
			for (ActivitySheepHongBao ashb : list) {
				if(ashb.getStatus().intValue() == 6&&nowDate.after(DateUtils.addDays(endTime, 4))){
					steps.add(new ActivitySheepYearHongBaoStep(10));
				}else if(ashb.getStatus().intValue() == 6&&nowDate.before(DateUtils.addDays(endTime, 4))){
					steps.add(new ActivitySheepYearHongBaoStep(6));
					data.setIndex(ashb.getIndexHb().intValue());
					data.setTotal(3L);
					data.setRewardsNum(ashb.getAward() / 10000);
				}else{
					steps.add(new ActivitySheepYearHongBaoStep(10));
				}
				data.setRewards(steps);
			}
			struc.setData(data);
			return struc;
		}}
		if (model == null) {
			model = 1;
		}
		if (model == 1) {
			List<ActivitySheepHongBao> list = reponse.getBody().getResult()
					.getList();
			List<ActivitySheepYearHongBaoStep> steps = new ArrayList<ActivitySheepYearHongBaoStep>();
			struc.setStatus("0");
			struc.setMessage("红包信息更新成功");
			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
			for (ActivitySheepHongBao ashb : list) {
				ashb.setStatus(ashb.getStatus());
				steps.add(new ActivitySheepYearHongBaoStep(ashb.getStatus()
						.intValue()));
				if (ashb.getStatus().intValue() == 6) {
					data.setIndex(ashb.getIndexHb().intValue());
					data.setTotal(3L);
					data.setRewardsNum(ashb.getAward() / 10000);
				}
				if (ashb.getStatus().intValue() == 5) {
					data.setExpected(ashb.getTargetAward() / 10000);
					data.setDeadline(ashb.getDeadTime().getTime());
					data.setNowTime(nowDate.getTime());
					data.setLastBet(ashb.getAllAward() == null ? 0 : ashb
							.getAllAward());
					data.setRewardsNum(ashb.getAward() / 10000);
				}
			}
			data.setRewards(steps);
			struc.setData(data);
			return struc;
		} else if (model == 2) {// 提取红包金额 vip的上限多1w
			struc.setStatus("0");
			struc.setMessage("红包信息更新成功");
			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
			if (isvip == 1) {
				data.setIsVip(true);
				data.setMinNum(100L);
				data.setMaxNum(38888L);
			} else {
				data.setIsVip(false);
				data.setMinNum(100L);
				data.setMaxNum(28888L);
			}
			struc.setData(data);
			return struc;
		} else if (model == 3) {// 申请红包金额，保存红包申请信息
			 if(reponse.getBody().getResult().getList()!=null)
					for (ActivitySheepHongBao ashb :  reponse.getBody().getResult().getList()) {
						if (ashb.getStatus().intValue() ==2 || ashb.getStatus().intValue() ==1 || ashb.getStatus().intValue() ==5) {
							//loading=ashb;
							if(index==ashb.getIndexHb()){
								break;
							}
							if(index!=ashb.getIndexHb()){
								List<ActivitySheepYearHongBaoStep> steps = new ArrayList<ActivitySheepYearHongBaoStep>();
								ActivityRewardDateStruc data = new ActivityRewardDateStruc();
								steps.add(new ActivitySheepYearHongBaoStep(10));
								data.setRewards(steps);
								return struc;
							}
							
						} 
						//data.setRewards(steps);
			}
			ActivitySheepUserApplyHongBaoRequest applyRequest = new ActivitySheepUserApplyHongBaoRequest();
			if (DateUtils.addDays(nowDate, 20).before(endTime)) {
				applyRequest.setDeadDate(DateUtils.addDays(nowDate, 20));
			} else {
				applyRequest.setDeadDate(endTime);
			}
			if (isvip == 1) {
				if (amount < 100 || amount > 38888) {
					struc.setStatus("moneyout");
					struc.setMessage("红包金额不在范围之内");
					return struc;
				}
			} else {
				if (amount < 100 || amount > 28888) {
					struc.setStatus("moneyout");
					struc.setMessage("红包金额不在范围之内");
					return struc;
				}
			}
			applyRequest.setApplyDate(nowDate);
			applyRequest.setAward(amount * 10000);
			applyRequest.setIndex(index);
			applyRequest.setTargetAmount(amount * 30 * 10000);
			applyRequest.setUserId(usrId);
			Response<ActivitySheepUserApplyHongBaoResponse> applyReponse = betHttpClient
					.applyHongbaoAmount(applyRequest);// 申请红包

			struc.setStatus("0");
			struc.setMessage("提交余额成功");
			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
			data.setExpected(amount * 30);
			data.setNowTime(nowDate.getTime());
			data.setDeadline(applyRequest.getDeadDate().getTime());
			data.setRewardsNum(amount);
			data.setLastBet(applyReponse.getBody().getResult()
					.getValidBetAmount() / 10000);
			struc.setData(data);
			return struc;
		} else if (model == 4) {// 红包作废
			if(reponse.getBody().getResult().getList()!=null){
				boolean canDo=false;
				
					for (ActivitySheepHongBao ashb :  reponse.getBody().getResult().getList()) {						
						if (ashb.getStatus().intValue() ==5){
							//loading=ashb;
							if(index==ashb.getIndexHb().intValue()){
								canDo=true;
								break;
							}
							
						}
					
					}		//data.setRewards(steps);
					if(!canDo) return null;
			}
			ActivitySheepUserHongBaoAbortRequest abortRequest = new ActivitySheepUserHongBaoAbortRequest();
			abortRequest.setUserId(usrId);
			abortRequest.setIndex(index);
			betHttpClient.aborthongbao(abortRequest);
			struc.setStatus("0");
			struc.setMessage("红包已经作废");
			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
			data.setIndex(index.intValue());
			data.setTotal(3L);
			struc.setData(data);
			return struc;
		} else if (model == 5) {// 领取红包
			if(reponse.getBody().getResult().getList()!=null){
				boolean canDo=false;
				
					for (ActivitySheepHongBao ashb :  reponse.getBody().getResult().getList()) {						
						if (ashb.getStatus().intValue() ==6){
							//loading=ashb;
							if(index==ashb.getIndexHb().intValue()){
								canDo=true;
								break;
							}
							
						}
					
					}		//data.setRewards(steps);
					if(!canDo) return null;
			}
			if(nowDate.after(DateUtils.addDays(endTime, 4))){
				struc.setStatus("timeout");
				struc.setMessage("红包领取时间已过期");
				return struc;
			}
			
			ActivitySheepUserHongBaoDrawRequest drawRequest = new ActivitySheepUserHongBaoDrawRequest();
			drawRequest.setUserId(usrId);
			drawRequest.setIndex(index);
			Response<ActivitySheepUserHongBaoDrawResponse> drawResponse = betHttpClient
					.drawHongbao(drawRequest);
			struc.setStatus("0");
			struc.setMessage("领取成功");
			ActivityRewardDateStruc data = new ActivityRewardDateStruc();
			data.setIndex(index.intValue());
			data.setTotal(3L);
			data.setRewardsNum(drawResponse.getBody().getResult().getAmount() / 10000);
			struc.setData(data);
			return struc;

		}

		return struc;
	}

	// 押大小返回数据
	@RequestMapping(value = "/dice")
	@ResponseBody
	public Object dice(String diceType, Long device,String userId) throws Exception {
		
		logger.error("dice1"+new Date().getTime());
		
		Long usrId=0L;
		if(!StringUtils.isEmpty(userId)){
			String temp=StringMgr.Aes128Decode(userId, aesKey, aesIv);
			usrId=Long.valueOf(temp);
		}else{
			usrId=RequestContext.getCurrUser().getId();
		}
		ActivitySheepYearDiceStruc asyd = new ActivitySheepYearDiceStruc();
		
//		ActivitySheepUserDiceRequest diceRequest = new ActivitySheepUserDiceRequest();
//		diceRequest.setUserId(usrId);
////		diceRequest.setUserName(userName);
//		Response<ActivitySheepUserDiceResponse> dicereponse = betHttpClient
//				.getUserDiceData(diceRequest);
//		
//		if(dicereponse.getBody().getResult().getLastDiceTime().intValue()<1){
//			asyd.setStatus("fail");
//			asyd.setMsg("没有可玩次数了，请充值获取可玩次数");
//			return asyd;
//		}
		
		
		Date startTime = DateUtils.getStartTimeOfDate(DateUtils
				.parse(diceStartTime));
		Date endTime = DateUtils.getEndTimeOfDate(DateUtils.parse(diceEndTime));
		Date nowDate = new Date();
		if (nowDate.before(startTime) || nowDate.after(DateUtils.addDays(endTime, 4))) {
			asyd.setStatus("timeout");
			asyd.setMsg("当前日期不在活动时间范围");
			return asyd;
		}
		ActivitySheepUserDiceAwardRequest request = new ActivitySheepUserDiceAwardRequest();
		// 0Android| 1webOS| 2i hone| 3iPad| 4iPod| 5BlackBerry| 6IEMobile|
		// 7Opera Mini
		if(device==null){
			device=4L;
		}
	    request.setChannel(device);
		try {
			request.setIsGuessLittle(diceType.equals("small") ? true : false);
			request.setUserId(usrId);
			logger.error("dice2"+new Date().getTime());
			Response<ActivitySheepUserDiceAwardResponse> diceAwardResponse = betHttpClient
					.diceAward(request);
			logger.error("dice3"+new Date().getTime());
			ActivitySheepUserDiceAwardResponse result = diceAwardResponse
					.getBody().getResult();

			if (result != null) {
				if(!result.isHaveAward()){
					asyd.setStatus("noAward");
					asyd.setMsg("已无奖品");
					return asyd;
				}
				
				asyd.setDate(DateUtils.format(new Date(), "MM-dd HH:mm:ss"));
				asyd.setStatus("ok");
				asyd.setDiceAmount(result.getLastGuessNum());
				asyd.setDiceContinus(result.getContinuousWinNum());
				asyd.setDiceStatus(result.isWin() ? "win" : "lose");
				// asyd.setMsg("aaa");
				asyd.setResult(result.getResultNum());
				asyd.setType(diceType);
				asyd.setWinMoney(result.getAward() / 10000);
			}
		} catch (Exception e) {
			logger.error("押大小错误", e);
			asyd.setStatus("fail");
		}
		logger.error("dice4"+new Date().getTime());
		return asyd;
	}

	// 转盘
	@RequestMapping(value = "/rotary")
	@ResponseBody
	public Object rotary(String diceType, Long device,String userId) throws Exception {
		
		Long usrId=0L;
		if(!StringUtils.isEmpty(userId)){
			String temp=StringMgr.Aes128Decode(userId, aesKey, aesIv);
			usrId=Long.valueOf(temp);
		}else{
			usrId=RequestContext.getCurrUser().getId();
		}
		ActivitySheepYearRotaryStruc asyd = new ActivitySheepYearRotaryStruc();
		
//		ActivitySheepUserRotaryRequest rotaryRequest = new ActivitySheepUserRotaryRequest();
//		rotaryRequest.setUserId(usrId);
////		rotaryRequest.setUserName(userName);
////		rotaryRequest.setChannel(channel);
//		Response<ActivitySheepUserRotaryResponse> rotaryreponse = betHttpClient
//				.getUserRotaryData(rotaryRequest);
//		if (rotaryreponse.getBody().getResult().getLastDiceTime()<1) {
//			asyd.setStatus("fail");
//			asyd.setDesc("没有可玩次数了，请充值获取可玩次数");
//			return asyd;
//		}
//		
		UserInfoRequest userInfoRequest=new UserInfoRequest();
		userInfoRequest.setUserId(usrId);
		Response<UserInfoResponse> userInfoResponse=betHttpClient
				.getUserInfo(userInfoRequest);
		
		Date startTime = DateUtils.getStartTimeOfDate(DateUtils
				.parse(rotaryStartTime));
		Date endTime = DateUtils.getEndTimeOfDate(DateUtils
				.parse(rotaryEndTime));
		Date nowDate = new Date();
		if (nowDate.before(startTime) || nowDate.after(endTime)) {
			asyd.setStatus("timeout");
			asyd.setDesc("当前日期不在活动时间范围");
			return asyd;
		}
		Integer isvip = userInfoResponse.getBody().getResult().getVipLvl();
		
//		if (isvip != 1) {
//			asyd.setStatus("fail");
//			asyd.setDesc("需要vip权限");
//			return asyd;
//		}

		ActivitySheepUserRotaryRewardRequest request = new ActivitySheepUserRotaryRewardRequest();
		// 0Android| 1webOS| 2i hone| 3iPad| 4iPod| 5BlackBerry| 6IEMobile|
		// 7Opera Mini
		if(device==null){
			device=4L;
		}
		request.setChannel(device);
		try {
			request.setUserId(usrId);
			Response<ActivitySheepUserRotaryRewardResponse> diceAwardResponse = betHttpClient
					.rotaryAward(request);
			ActivitySheepUserRotaryRewardResponse result = diceAwardResponse
					.getBody().getResult();
			if (result != null) {
				if(!result.isHaveAward()){
					asyd.setStatus("noAward");
					asyd.setDesc("已无奖品");
					return asyd;
				}
				
				Long time = DateUtils.calcMinutesBetween(new Date(),
						result.getDate());
				if (time.intValue() > 50) {
					time = 50L;
				}
				asyd.setDate(time + "分钟前");
				asyd.setStatus("ok");
				if (result.getDesc().equals("一等奖")) {
					asyd.setLuckyidx(5L);
				} else if (result.getDesc().equals("二等奖")) {
					asyd.setLuckyidx(4L);
				} else if (result.getDesc().equals("三等奖")) {
					asyd.setLuckyidx(3L);
				} else if (result.getDesc().equals("四等奖")) {
					asyd.setLuckyidx(2L);
				} else if (result.getDesc().equals("五等奖")) {
					asyd.setLuckyidx(1L);
				} else {
					asyd.setLuckyidx(0L);
				}
				asyd.setTimes(result.getLastTime());
				asyd.setUsername(format(userInfoResponse.getBody().getResult().getUserName()));
				asyd.setDesc(result.getAward() / 10000 + "元奖金");
			}
		} catch (Exception e) {
			logger.error("转盘错误", e);
			asyd.setStatus("fail");
		}
		return asyd;
	}

}
