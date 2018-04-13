package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IActivityUserChargeDao;
import com.winterframework.firefrog.game.dao.vo.ActivityUserCharge;
import com.winterframework.firefrog.game.service.IActivityUserChargeService;
import com.winterframework.firefrog.game.web.dto.ActivityUserBetOrChargeAmountStruc;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepDetailDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepHongBaoDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepOperateLogDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepDetail;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepOperateLog;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepWheelSurf;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepBigLittleService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepHongBaoService;
import com.winterframework.firefrog.shortlived.sheepactivity.service.IActivitySheepWheelSurfService;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("activityUserChargeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ActivityUserChargeServiceImpl implements
		IActivityUserChargeService {

	@Resource(name = "activityUserChargeDaoImpl")
	private IActivityUserChargeDao activityUserChargeDaoImpl;

	private Logger log = LoggerFactory
			.getLogger(ActivityUserChargeServiceImpl.class);

	@PropertyConfig("sheepyear.activity.dice.startTime")
	private String diceStartTime;

	@PropertyConfig("sheepyear.activity.dice.endTime")
	private String diceEndTime;

	@PropertyConfig("sheepyear.activity.rotary.startTime")
	private String rotaryStartTime;

	@PropertyConfig("sheepyear.activity.rotary.endTime")
	private String rotaryEndTime;

	@Resource(name = "activitySheepBigLittleServiceImpl")
	private IActivitySheepBigLittleService activitySheepBigLittleServiceImpl;

	@Resource(name = "activitySheepWheelSurfServiceImpl")
	private IActivitySheepWheelSurfService activitySheepWheelSurfServiceImpl;

	@Resource(name = "activitySheepHongBaoServiceImpl")
	private IActivitySheepHongBaoService activitySheepHongBaoServiceImpl;

	@Resource(name = "activitySheepHongBaoDaoImpl")
	private IActivitySheepHongBaoDao activitySheepHongBaoDaoImpl;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;
	
	@Resource(name = "activitySheepDetailDaoImpl")
	private IActivitySheepDetailDao activitySheepDetailDaoImpl; 
	
	@Resource(name = "activitySheepOperateLogDaoImpl")
	private IActivitySheepOperateLogDao activitySheepOperateLogDaoImpl; 

	@Override
	public void saveUserCharge(ActivityUserBetOrChargeAmountStruc[] arr)
			throws Exception {
		List<ActivityUserCharge> aucs = new ArrayList<ActivityUserCharge>();
		for (ActivityUserBetOrChargeAmountStruc au : arr) {
			log.info("插入用户投注金额或者充值金额数据：userid=" + au.getUserId() + "--type="
					+ au.getType() + "--amount=" + au.getAmount() + "--date="
					+ au.getDate() + "--source=" + au.getSource() + "--demo="
					+ au.getDemo());
			User user = customerDao.queryUserById(au.getUserId());
			ActivityUserCharge auc = new ActivityUserCharge();
			auc.setAmount(au.getAmount());
			auc.setGmtCreated(new Date());
			auc.setMoneyDate(DateUtils.format(DateUtils.parse(au.getDate(),
					DateUtils.DATETIME_FORMAT_PATTERN),
					DateUtils.DATE_FORMAT_PATTERN));
			auc.setSource(au.getSource());
			auc.setType(Long.valueOf(au.getType()));
			auc.setUserId(au.getUserId());
			aucs.add(auc);
			// 单次充值金额超过五百累加一次押宝 vip用户单次充值一次超过2w 累加一次转盘 在活动期间
			Date diceStart = DateUtils.getStartTimeOfDate(DateUtils
					.parse(diceStartTime));
			Date diceEnd = DateUtils.getEndTimeOfDate(DateUtils
					.parse(diceEndTime));
			Date rotaryStart = DateUtils.getStartTimeOfDate(DateUtils
					.parse(rotaryStartTime));
			Date rotaryEnd = DateUtils.getEndTimeOfDate(DateUtils
					.parse(rotaryEndTime));
			Date now = DateUtils.parse(au.getDate(),
					DateUtils.DATETIME_FORMAT_PATTERN);

			if (au.getType() == 0) {// 用户消费金额，将用户消费金额传给用户正在进行中的红包
				ActivitySheepHongBao info = activitySheepHongBaoServiceImpl
						.getUserValidHongBaoInfo(au.getUserId());
				info.setAllAward(info.getAllAward().longValue()
						+ au.getAmount());
				if (info.getStatus() == 5L
						&& (info.getTargetAward() != null ? info
								.getTargetAward() : 0L) <= info.getAllAward()) {
					info.setStatus(6L);
					info.setReachTime(new Date());
				}
				activitySheepHongBaoDaoImpl.update(info);
				
				//保存红包金额详情
				Long channel=4L;
				if(au.getSource().equals("3.0")){
					channel=3L;
				}
				ActivitySheepDetail detail = new ActivitySheepDetail();
				detail.setActivityId(3L);
				detail.setActivityTime(DateUtils.addDays(new Date(), -1));//所有时间减一天 表明是昨天的投注数据
				detail.setActivityType(3L);//充值添加
				detail.setAward(0L);//管理员添加
				detail.setChannel(channel);
				detail.setUseNum(0L);
				detail.setResult("");//管理员添加
				detail.setRecharge(au.getAmount());
				detail.setStatus(1L);//充值状态直接是发布
				detail.setUserName(user.getUserProfile().getAccount());
				detail.setUserId(au.getUserId());
				detail.setGetNum(0L);
				detail.setActivityConfigId(info.getId());
				activitySheepDetailDaoImpl.insert(detail);
			}

			if (now.before(diceEnd) && now.after(diceStart)) {
				if (au.getType() == 1 && au.getAmount() >= 5000000) {// 给用户押宝活动加次数，可能需要初始化活动数据
					Long times = au.getAmount() / 5000000;
					log.error("用户充值增加押宝次数：userid=" + au.getUserId() + "--time="
							+ times);
					ActivitySheepBigLittle bl = activitySheepBigLittleServiceImpl
							.getUserDice(au.getUserId());
					Long channel=4L;
					if(au.getSource().equals("3.0")){
						channel=3L;
					}
					if (bl == null) {// 不存在用户押大小记录
						activitySheepBigLittleServiceImpl.initUserDice(
								au.getUserId(), times, au.getAmount(),channel);
					} else {// 存在加次数
						activitySheepBigLittleServiceImpl.addUserDiceLastNum(
								au.getUserId(), times,au.getAmount());
						
					}
					//详情中加记录
					ActivitySheepDetail detail = new ActivitySheepDetail();
					detail.setActivityId(4L);
					detail.setActivityTime(new Date());
					detail.setActivityType(2L);//充值添加
					detail.setAward(0L);//管理员添加
					detail.setChannel(channel);
					detail.setUseNum(0L);
					detail.setResult("");//管理员添加
					detail.setRecharge(au.getAmount());
					detail.setStatus(1L);//充值状态直接是发布
					detail.setUserName(user.getUserProfile().getAccount());
					detail.setUserId(au.getUserId());
					detail.setGetNum(times);
					activitySheepDetailDaoImpl.insert(detail);
				}
			}

			if (now.before(rotaryEnd) && now.after(rotaryStart)) {
				if (user.getVipLvl() == 1) {//vip权限
					if (au.getType() == 1 && au.getAmount() >= 200000000) {// 转盘押宝加次数
						Long times = au.getAmount() / 200000000;
						log.error("用户充值增加转盘次数：userid=" + au.getUserId()
								+ "--time=" + times);
						ActivitySheepWheelSurf bl = activitySheepWheelSurfServiceImpl
								.getUserRotary(au.getUserId());
						Long channel=4L;
						if(au.getSource().equals("3.0")){
							channel=3L;
						}
						if (bl == null) {
							
							activitySheepWheelSurfServiceImpl.initUserRotary(
									au.getUserId(), times,au.getAmount(),channel);
						} else {
							activitySheepWheelSurfServiceImpl
									.addUserRotaryLastNum(au.getUserId(), times,au.getAmount());
						}
						ActivitySheepDetail detail = new ActivitySheepDetail();
						detail.setActivityId(5L);
						detail.setActivityTime(new Date());
						detail.setActivityType(2L);//充值添加
						detail.setAward(0L);//管理员添加
						detail.setChannel(channel);
						detail.setUseNum(0L);
						detail.setResult("");//管理员添加
						detail.setRecharge(au.getAmount());
						detail.setStatus(1L);//充值状态直接是发布
						detail.setUserName(user.getUserProfile().getAccount());
						detail.setUserId(au.getUserId());
						detail.setGetNum(times);
						activitySheepDetailDaoImpl.insert(detail);
					}
				}
			}

		}
		activityUserChargeDaoImpl.insert(aucs);
	}

}
