package com.winterframework.firefrog.beginmession.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginAwardDao;
import com.winterframework.firefrog.beginmession.dao.BeginAwardLogDao;
import com.winterframework.firefrog.beginmession.dao.BeginAwardLotteryDao;
import com.winterframework.firefrog.beginmession.dao.BeginBankCardCheckDao;
import com.winterframework.firefrog.beginmession.dao.BeginLotterySetDao;
import com.winterframework.firefrog.beginmession.dao.BeginMissionDao;
import com.winterframework.firefrog.beginmession.dao.BeginMissionVerDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewChargeDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewDaybetDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewDayquesDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewMissionDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewQuestionDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewTolbetDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAward;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLog;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLottery;
import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheck;
import com.winterframework.firefrog.beginmession.dao.vo.BeginLotterySet;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMissionVer;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDaybet;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDayques;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewQuestion;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewTolbet;
import com.winterframework.firefrog.beginmession.entity.BeginMissionData;
import com.winterframework.firefrog.beginmession.entity.QuestionData;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.AwardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.BindCardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.BindCardType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.CancelReason;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.LotteryType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.Status;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.YesOrNo;
import com.winterframework.firefrog.beginmession.service.BeginMissionAwardService;
import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.beginmession.utils.BeginMissionServiceHelper;
import com.winterframework.firefrog.beginmession.utils.BeginMissionUtils;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.fund.dao.IBankCardDao;
import com.winterframework.firefrog.fund.dao.IUserBankLockedDao;
import com.winterframework.firefrog.fund.dao.vo.UserBank;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.modules.spring.exetend.PropertyConfig;


/**
 * 新手任務主流程API
 * @author Ami.Tsai
 *
 */
@Service
@Transactional
public class BeginMissionServiceImpl implements BeginMissionService {
	

	private static final Logger log = LoggerFactory.getLogger(BeginMissionServiceImpl.class);
	
	private static final Long UN_ORDER = 2l;//尚未開獎
	
	private static final Long ORDER = 1l;//有中獎
	
	private static final Long NO_ORDER = 0l;//未投注
	
	private static final int TOLBET_FINISH = 0;//累積投注已完成
	
	private static final int TOLBET_ING = 1;//累積投注進行中
	
	private static final int TOLBET_NO = 2;//累積投注未開始
	
	private static final int BET_ORDER_DAY = 21;
	
	@Resource(name = "HttpJsonClientImpl")
	protected IHttpJsonClient httpJsonClient;
	
	@Autowired
	private BeginNewMissionDao beginNewMissionDao;
	@Autowired
	private BeginNewChargeDao beginNewChargeDao;
	@Autowired
	private BeginNewDaybetDao beginNewDaybetDao;
	@Autowired
	private BeginNewDayquesDao beginNewDayquesDao;
	@Autowired
	private BeginNewTolbetDao beginNewTolbetDao;
	@Autowired
	private BeginLotterySetDao beginLotterySetDao;
	@Autowired
	private BeginMissionVerDao beginMissionVerDao;
	@Autowired
	private IUserCustomerDao userCustomerDao;
	@Autowired
	private BeginAwardDao beginAwardDao;
	@Autowired
	private BeginAwardLogDao beginAwardLogDao;
	@Autowired
	private BeginMissionDao beginMissionDao;
	@Autowired
	private BeginNewQuestionDao beginNewQuestionDao;
	@Autowired
	private BeginAwardLotteryDao beginAwardLotteryDao;
	@Autowired
	private BeginBankCardCheckDao beginBankCardCheckDao;
	
	@Resource(name = "bankCardDaoImpl")
	private IBankCardDao bankCardDao;
	
	@Resource(name = "userBankLockedDaoImpl")
	private IUserBankLockedDao userBankLockedDao;
	
	@Autowired
	private BeginMissionAwardService beginMissionAwardService;
	
	@PropertyConfig("game_get_queryBeginMissionOrder")
	private String queryBeginMissionOrder;
	
	
	
	@Override
	public void bindMissionVer(Long userId) {

		log.info("bindMissionVer userid="+userId);
		BeginMissionVer missionVer = new BeginMissionVer();
		missionVer.setUserId(userId);
		Optional<BeginMissionVer> optionalVer = beginMissionVerDao.findOneByCondition(missionVer);
		if(!optionalVer.isPresent()){
			BeginMission mission = new BeginMission();
			UserCustomer user = userCustomerDao.getById(userId);		
			mission.setUserId(userId);
			mission.setMissionStartTime(user.getRegisterDate());
			Date bindCardEndTime = DateUtils.addDays(user.getRegisterDate(), 7);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			try {
				bindCardEndTime = format.parse(format.format(bindCardEndTime));
			} catch (ParseException e) {
				log.error(" parse bindCardEndTime error");
			}
			//預設bindCardEndTime
			mission.setBindCardEndTime(bindCardEndTime);
			mission.setStatus(BeginMissionEnum.Status.BEVALID.getValue());
			beginMissionDao.insert(mission);
			missionVer = new BeginMissionVer();
			missionVer.setUserId(userId);
			missionVer.setUserAccount(user.getAccount());
			missionVer.setNewMissionVer(beginNewMissionDao.getMaxVersion());
			missionVer.setChargeVer(beginNewChargeDao.getMaxVersion());
			missionVer.setTolbetVer(beginNewTolbetDao.getMaxVersion());//累積投注版本
			missionVer.setDaybetVer(beginNewDaybetDao.getMaxVersion());//每日投注版本
			missionVer.setDayquesVer(beginNewDayquesDao.getMaxVersion());//累積答題版本
			missionVer.setEggVer(beginLotterySetDao.getMaxVersion());//砸蛋獎勵版本
			beginMissionVerDao.insert(missionVer);
		}
	}

	@Override
	public BeginMissionData checkNewMission(Long userId) {
		BeginMissionData data = new BeginMissionData();
		
		BeginMission mission = beginMissionDao.findByUserId(userId);
		data.setIsNewMission(checkMissionApprove(mission));
		
		if(data.getIsNewMission()){
			Long missionDay = BeginMissionServiceHelper.calMissionDay(mission);
			data.setMissionDay(missionDay);
			BeginMissionVer ver =beginMissionVerDao.findByUserId(userId);
			Optional<BeginNewMission> missionOpl= beginNewMissionDao.findOneByVersion(ver.getNewMissionVer());
			if(missionOpl.isPresent()){
				//取得mission設定
				data.setNewMission(missionOpl.get());
				//查詢是否已綁卡
				BeginAward beginAward = new BeginAward();
				beginAward.setUserId(userId);
				beginAward.setAwardType1(MissionType.BIND_CARD.getValue());
				beginAward.setStatus(AwardStatus.AwardSucess.getValue());
				List<BeginAward> awards = null;
				awards = beginAwardDao.findByCondition(beginAward);
				
				if(awards!=null && !awards.isEmpty()){
					//綁卡通過0天
					data.setBindCardDay(0l);					
				}else{
					//綁卡獎勵未通過,依然計算天數
					Long bindCardDay = BeginMissionServiceHelper.calBindCardDay(mission);				
					data.setBindCardDay(bindCardDay);					
				}
				
				//查詢是否已充值
				beginAward = new BeginAward();
				beginAward.setUserId(userId);
				beginAward.setAwardType1(MissionType.FIRST_CHARGE.getValue());
				beginAwardDao.findByCondition(beginAward);
				awards = beginAwardDao.findByCondition(beginAward);
				if(awards!=null && !awards.isEmpty()){
					data.setIsFirstChagre(true);
				}else{
					data.setIsFirstChagre(false);
				}
				
				//查詢是否已提現
				beginAward = new BeginAward();
				beginAward.setUserId(userId);
				beginAward.setAwardType1(MissionType.FIRST_WITHDRAW.getValue());
				beginAwardDao.findByCondition(beginAward);
				awards = beginAwardDao.findByCondition(beginAward);
				if(awards!=null && !awards.isEmpty()){
					data.setIsFirstWithdraw(true);
				}else{
					data.setIsFirstWithdraw(false);
				}
				log.info("userId : " + userId);
				data.setBindCardStatus(checkBindCardStatus(userId));
			}
		}
		
		return data;
	}
	
	private Boolean checkMissionApprove(BeginMission mission){
		Boolean isNewMission = false;
		if(mission!=null && !Status.INVALID.getValue().equals(mission.getStatus()) && !Status.CANCEL.getValue().equals(mission.getStatus())){
			isNewMission = true;
		}
		return isNewMission;
	}
	
	private Long checkBindCardStatus(Long userId){
		Long result = BindCardType.FAIL.getValue();
		BeginMission beginMission = beginMissionDao.findByUserId(userId);
		List<UserBank> userBankList = bankCardDao.queryBankCardsByUserId(userId);
		try{
			log.debug("---------------------checkBindCardStatus-------------------");
			BeginBankCardCheck beginBankCardCheck = beginBankCardCheckDao.queryByUserId(userId);
			Boolean isMission = checkMissionApprove(beginMission);
			log.debug("isMission : " + isMission);
			long overTime = userBankLockedDao.getOverTime(userId,1);
			log.debug("overTime : " + overTime);
			Boolean isLock = false;
			long currentTime = new Date().getTime();
			log.debug("currentTime : " + currentTime);
			if(overTime > 0 && currentTime > overTime){
				log.debug(" ------------------isLock = true -------------");
				isLock = true;
			}
			log.debug("isLock : " + isLock);
			
			if(isMission && beginBankCardCheck == null && (userBankList == null || userBankList.isEmpty())){
				result = BindCardType.UNBIND.getValue();
			}else if(isMission && beginBankCardCheck == null && ( userBankList != null && !userBankList.isEmpty()) && !isLock){
				result = BindCardType.BIND_UNLOCK.getValue();
			}else if(isMission && beginBankCardCheck != null && beginBankCardCheck.getCheckStatus() != BindCardStatus.PASS.getValue()){
				result = BindCardType.CHECKING.getValue();
			}else if(isMission && beginBankCardCheck != null && beginBankCardCheck.getCheckStatus() == BindCardStatus.PASS.getValue()){
				result = BindCardType.FINISH.getValue();
			}
			log.debug("result : " + result);
		}catch(Exception e){
			log.error("checkBindCardStatus error~~!");
			result = BindCardType.FAIL.getValue();
		}
		return result;
	}

	@Override
	public void bindCardAward(Long userId) {
		BeginMission misison = beginMissionDao.findByUserId(userId);
		if(misison!=null){
			if(misison.getBindCardLockTime()==null){
				//第一次綁卡
				Date bindCardTime = DateUtils.currentDate();
				Date missionDate = DateUtils.addDays(bindCardTime, 21);
				Date bindCardLockTime = DateUtils.addHours(bindCardTime, 1);
				//計算主檔綁卡時間
				beginMissionDao.updateMissionTime(userId, bindCardTime, missionDate,bindCardLockTime);			
			}else{
				Date newDate = DateUtils.currentDate();
				//超過綁卡期限
				if(newDate.getTime() >= misison.getBindCardLockTime().getTime()){
					checkBindCardMissionAndCancel(misison.getUserId());
				}else{
					//在綁卡期限內換卡,刪除重綁,DONOTHING
				}
			}			
		}
	}

	
	@Override
	public void firstChargeAward(Long userId, Long amount) {
		log.info("firstChargeAward userId="+userId);
		log.info("firstChargeAward amount="+amount);		
		BeginMission mission = beginMissionDao.findByUserId(userId);
		//判斷是否為首充成功
		if(mission!= null && Status.VALID.getValue().equals(mission.getStatus())){
			Long chargeCount = beginMissionDao.findFirstcharge(userId, FundChargeOrder.Status.SUCCESS.getValue());
			log.info("firstChargeAward chargeCount="+chargeCount);				
			if(chargeCount==1l){
				beginMissionDao.updateChargeAmt(userId, amount);
				BeginMissionVer ver =beginMissionVerDao.findByUserId(userId);
				BeginNewCharge entity = new BeginNewCharge();
				entity.setVersion(ver.getChargeVer());
				entity.setChargeAmt(amount);
				Optional<List<BeginNewCharge>> missionOptional= beginNewChargeDao.findByCondition2(entity);
				if(missionOptional.isPresent() && !missionOptional.get().isEmpty()){
					beginMissionAwardService.firstChargeAward(missionOptional.get(), userId, amount);			
				}				
			}

		}
	}
	
	
	@Override
	public void firstWthdrawAward(Long userId, Long amount) {
		List<Long> withdrawStatus = Lists.newArrayList();
		log.info("firstWthdrawAward userId="+userId);
		log.info("firstWthdrawAward amount="+amount);	
		
		BeginMission mission= beginMissionDao.findByUserId(userId);
		
		if(mission!=null && Status.VALID.getValue().equals(mission.getStatus())){
			withdrawStatus.add(WithdrawStauts.SUCCESS.getValue());
			withdrawStatus.add(WithdrawStauts.PART.getValue());
			//判斷是否為首提成功
			Long withdrawCount= beginMissionDao.findFirstWithdraw(userId, withdrawStatus);
			log.info("firstWthdrawAward chargeCount="+withdrawCount);				
			if(withdrawCount==1l){
				//確定沒壓上首提時間才派獎
				if(mission.getWithdrawTime()==null){
					beginMissionDao.updateWithdrawAmt(userId, amount);
					BeginMissionVer ver =beginMissionVerDao.findByUserId(userId);
					Optional<BeginNewMission> missionOpl= beginNewMissionDao.findOneByVersion(ver.getNewMissionVer());
					if(missionOpl.isPresent()){
						BeginNewMission newMission = missionOpl.get();
						beginMissionAwardService.firstWithdrawAward(userId, newMission, amount);
					}				
				}
			}
		}
		
		
	}
	
	@Override
	public BeginMissionData getDailyMissionData(Long userId) throws Exception {
		BeginMissionData data = new BeginMissionData();

		BeginMission beginMission = beginMissionDao.findByUserId(userId);
		data.setIsNewMission(checkMissionApprove(beginMission));
		
		if(data.getIsNewMission()){
			BeginMissionVer ver =beginMissionVerDao.findByUserId(userId);
			//查看每日投注進度
			Optional<List<BeginNewDaybet>> dayBestOpl = beginNewDaybetDao.findByVersion(ver.getDaybetVer());
			if(dayBestOpl.isPresent() && !dayBestOpl.get().isEmpty()){
				//每日投注資訊
				List<BeginNewDaybet> dayBests = Lists.newArrayList();
				for(BeginNewDaybet dayBest :dayBestOpl.get()){
					BeginAward award = BeginMissionServiceHelper.createQueryBeginAward(userId, MissionType.DAY_BET,dayBest.getId());
					Long successCount = beginAwardDao.getAwardNowByMissionId(award);
					if(successCount!=null && successCount>0){
						dayBest.setIsSuccess(true);
					}else{
						dayBest.setIsSuccess(false);
					}
					dayBests.add(BeginMissionUtils.convertSelectAmtField(dayBest));
				}
				Collections.sort(dayBests, new Comparator<BeginNewDaybet>() {
					@Override
					public int compare(BeginNewDaybet o1, BeginNewDaybet o2) {
						if(o1.getDaybetAmount()> o2.getDaybetAmount()){
							return 1;	
						}else{
							return -1;	
						}
					}
				});
				data.setDayBests(dayBests);
			}
			
			//撈取投注小日曆
			Optional<BeginNewMission> mission= beginNewMissionDao.findOneByVersion(ver.getNewMissionVer());
			if(beginMission.getMissionValidTime()!=null){
				Map<String, Object> map = Maps.newHashMap();
				map.put("userId", userId);
				map.put("betFactor", mission.get().getDayBetFactor());
				List<String> results = (List<String>) httpJsonClient.invokeHttp(queryBeginMissionOrder,
						map, new TypeReference<List<String>>(){});
				if(results!=null){
					data.setTolBetDay(new Long(results.size()));			
				}else{
					data.setTolBetDay(0l);
				}
				List<Long> betDayList = genDailyBetData(results,beginMission.getMissionValidTime());
				data.setBetDayList(betDayList);			
			}else{
				data.setTolBetDay(0l);
				List<Long> betDayList = genDailyBetData(null,beginMission.getMissionValidTime());
				data.setBetDayList(betDayList);			
			}
			
			
			//累計投注天數
			Optional<List<BeginNewTolbet>> tolBetOpl =beginNewTolbetDao.findByVersion(ver.getTolbetVer());
			if(tolBetOpl.isPresent() && !tolBetOpl.get().isEmpty()){
				List<BeginNewTolbet> tolBets = Lists.newArrayList();
				if(data.getTolBetDay() > 0){
					//累積投注資訊
					BeginNewTolbet beforeBet = null;
					for(BeginNewTolbet tolBet :tolBetOpl.get()){
						BeginAward award = BeginMissionServiceHelper.createQueryBeginAward(userId, MissionType.TOL_BET,tolBet.getId());
						award = beginAwardDao.findFirstByCondition(award);
						if(award!=null){
							tolBet.setIsSuccess(true);
							tolBet.setSuccessStatus(TOLBET_FINISH);
						}else{
							tolBet.setIsSuccess(false);
							//若無前一筆資料表示為第一筆,因此該狀態為進行中
							if(beforeBet==null){
								tolBet.setSuccessStatus(TOLBET_ING);
							}else if(beforeBet.getIsSuccess()){
							//若前一筆資已完成,表示為進行中
								tolBet.setSuccessStatus(TOLBET_ING);
							}else{
							//若前一筆資未完成,表示為未開始	
								tolBet.setSuccessStatus(TOLBET_NO);
							}
						}
						tolBets.add(BeginMissionUtils.convertSelectAmtField(tolBet));
						beforeBet = tolBet;
					}								
				}else{
					//尚未投注則都是未開始
					for(BeginNewTolbet tolBet :tolBetOpl.get()){
						tolBet.setIsSuccess(false);
						tolBet.setSuccessStatus(TOLBET_NO);
						tolBets.add(BeginMissionUtils.convertSelectAmtField(tolBet));
					}	
				}
				data.setTolBets(tolBets);
			}
			
		}
		return data;
	}
	
	
	private List<Long> genDailyBetData(List<String> results,
			Date missionValidTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date now = DateUtils.currentDate();
		List<Long> betDayList = Lists.newArrayList();
		
		if(missionValidTime==null){
			for(int i = 0;i<BET_ORDER_DAY;i++){
				betDayList.add(UN_ORDER);				
			}
		}else{
			if(results!=null && !results.isEmpty()){
				Boolean orderFlag = false;
				for(int i = 0;i<BET_ORDER_DAY;i++){
					Date date = DateUtils.addDays(missionValidTime, i);
					String dateStr=format.format(date);
					String nowStr = format.format(now);
					if(nowStr.equals(dateStr)){
						orderFlag = true;
						if(results.contains(dateStr)){
							betDayList.add(ORDER);
						}else{
							betDayList.add(NO_ORDER);
						}
					}else{
						if(orderFlag){
							betDayList.add(UN_ORDER);
						}else{
							if(results.contains(dateStr)){
								betDayList.add(ORDER);
							}else{
								betDayList.add(NO_ORDER);
							}
						}
					}
				}
			}else{
				for(int i = 0;i<BET_ORDER_DAY;i++){
					betDayList.add(NO_ORDER);				
				}
			}
		}
		
		return betDayList;
	}

	@Override
	public BeginMissionData getDayQuestion(Long userId) {
		
		BeginMissionData data =new  BeginMissionData();
		
		//是否答題完畢
		BeginAward award= beginAwardDao.getNowDailyAnsAward(userId);
		if(award!=null){
			data.setDayAnsFinish(true);			
			BeginAwardLog awardlog = new BeginAwardLog();
			awardlog.setUserId(userId);
			awardlog.setAwardId(award.getId());
			awardlog = beginAwardLogDao.findFirstByCondition(awardlog);
			//若有資料則給予答題獎金
			if(awardlog!=null){
				data.setAnsMoney(awardlog.getPrize());
			}
		}else{
			data.setDayAnsFinish(false);			
		}
		
		BeginMissionVer ver =beginMissionVerDao.findByUserId(userId);
		if(ver!=null){
			//連續答題設定
			Optional<List<BeginNewDayques>> dayQuesOpl = beginNewDayquesDao.findByVersion(ver.getDayquesVer());
			if(dayQuesOpl.isPresent() && !dayQuesOpl.get().isEmpty()){
				List<BeginNewDayques> dayQuesList = Lists.newArrayList();
				for(BeginNewDayques dayques:dayQuesOpl.get()){
					String cssConfig = LotteryType.mapping(dayques.getLotteryType()).getCssConfig();
					dayques.setLotteryCss(cssConfig);
					dayQuesList.add(dayques);
				}
				 data.setDayques(dayQuesList);
			}			
		}


		//連續答題天數
		Long answersDays = queryAnsDays(userId);
		data.setAnswersDays(answersDays);
		
		
		//每日題目
		List<BeginNewQuestion> questions = beginNewQuestionDao.getAll();
		List<QuestionData> questionData = BeginMissionServiceHelper.getQuestion(questions);
		data.setQuestionData(questionData);
		
		return data;
	}

	
	@Override
	public BeginMissionData dailyAnswerAward(Long userId) {
		
		BeginMissionData data = new BeginMissionData();
		
		Boolean isNewMission = false;
		//判斷新手權限
		BeginMission mission = beginMissionDao.findByUserId(userId);
		isNewMission=checkMissionApprove(mission);
		//每日答題獎勵
		data.setIsNewMission(isNewMission);
		Long bindCardStatus = checkBindCardStatus(userId);
		//先判斷是否已經綁卡, 沒綁卡不可以往下做
		if(BindCardType.FINISH.getValue().equals(bindCardStatus)){
			BeginMissionVer ver =beginMissionVerDao.findByUserId(userId);
			Optional<List<BeginNewMission>> newMissionOpl = beginNewMissionDao.findByVersion(ver.getNewMissionVer());
			Long awardAmt = 0l;
			
			if(isNewMission && newMissionOpl.isPresent() && !newMissionOpl.get().isEmpty()){
				//領獎前判斷是否答題過
				BeginAward award = beginAwardDao.getNowDailyAnsAward(userId);
				if(award==null){
					BeginNewMission newMission= newMissionOpl.get().get(0);
					Long low= newMission.getDayAnsLow();
					Long high= newMission.getDayAnsHigh();
					awardAmt = BeginMissionServiceHelper.createRandomValue(low,high);
					if(awardAmt>0){
						awardAmt = beginMissionAwardService.dailyAnswerAward(userId, awardAmt , newMission);									
					}
					
				}
			}
			data.setAnsMoney(awardAmt);
			//當為0元表示系統今日已答題過但卻進入該程序
			if(awardAmt==0l){
				data.setErrorAwardFlag(YesOrNo.YES.getValue());
			}else{
				data.setErrorAwardFlag(YesOrNo.NO.getValue());
			}
			//查詢連續答題天數
			Long answersDays = queryAnsDays(userId);
			data.setAnswersDays(answersDays);
			
			if(isNewMission){
				//根據連續答題天數,判斷是否有獎可派
				continueDailyAnsAward(userId , answersDays , ver.getDayquesVer());			
			}
			
			//是否答題完畢
			BeginAward award= beginAwardDao.getNowDailyAnsAward(userId);
			if(award!=null){
				data.setDayAnsFinish(true);			
			}else{
				data.setDayAnsFinish(false);			
			}
		} else {
			//尚未綁卡的區段
			data.setAnsMoney(0l);
			data.setErrorAwardFlag(YesOrNo.YES.getValue());
			data.setAnswersDays(0l);
			//沒有答題完畢
			data.setDayAnsFinish(false);
		}
		//設定綁卡狀態
		data.setBindCardStatus(bindCardStatus);
		return data;
	}
	
	/**
	 * 判斷是否有達成連續答題天數
	 * @param userId
	 * @param answersDays
	 * @param dayquesVer
	 */
	private void continueDailyAnsAward(Long userId, Long answersDays,
			Long dayquesVer) {
		BeginNewDayques dayque = new BeginNewDayques();
		dayque.setVersion(dayquesVer);
		dayque.setAnswerDate(answersDays);
		Optional<List<BeginNewDayques>> dayQues= beginNewDayquesDao.findByCondition(dayque);
		if(dayQues.isPresent() && !dayQues.get().isEmpty()){
			BeginNewDayques beginNewDayques= dayQues.get().get(0);
			BeginAward award = BeginMissionServiceHelper.createQueryBeginAward(userId, MissionType.TOL_ANS,beginNewDayques.getId());
			List<BeginAward> awards = beginAwardDao.findByCondition(award);
			
			BeginAwardLottery awardLottery = BeginMissionServiceHelper.createQueryBeginAwardLottery(userId, beginNewDayques.getId(), MissionType.TOL_ANS);
			List<BeginAwardLottery> awardLotterys = beginAwardLotteryDao.findByCondition(awardLottery);
			//派獎錢查詢是否已領取過累積答題
			if( (awards==null || awards.isEmpty()) && (awardLotterys==null || awardLotterys.isEmpty()) ){
				//發派獎金
				if(YesOrNo.YES.getValue().equals(beginNewDayques.getIsAmount())){
					if(beginNewDayques.getAmount()>0){
						if(beginNewDayques.getAmount()>0){
							beginMissionAwardService.continueDailyAnsAward(userId, beginNewDayques.getAmount() , beginNewDayques);				
						}
					}
				}
				
				//發派砸蛋
				if(YesOrNo.YES.getValue().equals(beginNewDayques.getIsLottery())){
					if(beginNewDayques.getLottery()>0){
						for(int i=0;i<beginNewDayques.getLottery();i++){
							LotteryType type= LotteryType.mapping(beginNewDayques.getLotteryType());
							BeginAwardLottery lottery= BeginMissionServiceHelper.createBeginAwardLottery(userId, beginNewDayques.getId(), type, MissionType.TOL_ANS);
							beginAwardLotteryDao.insert(lottery);
						}
					}
				}
			}
		}
	}

	/**
	 * 計算連續答題天數
	 * @param userId
	 * @return
	 */
	private Long queryAnsDays(Long userId){
		//連續答題天數
		BeginAward award = new BeginAward();
		award.setUserId(userId);
		award.setAwardType1(MissionType.DAY_ANS.getValue());
		List<BeginAward> awards = beginAwardDao.findByCondition(award);
		Long answersDays = BeginMissionServiceHelper.calcuteAnsDays(awards);
		return answersDays;
	}

	@Override
	public BeginMissionData gotoEggLottery(Long userId) {
		BeginAwardLottery beginAwardLottery = new BeginAwardLottery();
		beginAwardLottery.setUserId(userId);
		beginAwardLottery.setStatus(AwardStatus.UnAward.getValue());
		List<BeginAwardLottery> eggLotterys = beginAwardLotteryDao.findByCondition(beginAwardLottery);

		Map<String,Integer> lotteryMap = Maps.newHashMap();
		if(eggLotterys==null ||eggLotterys.isEmpty()){
			lotteryMap.put(LotteryType.GOLD.getPhpConfig(), 0);
			lotteryMap.put(LotteryType.SILVER.getPhpConfig(), 0);
			lotteryMap.put(LotteryType.COPPER.getPhpConfig(), 0);			
		}else{
			for(BeginAwardLottery eggLottery:eggLotterys){
				if(LotteryType.GOLD.equals(LotteryType.mapping(eggLottery.getLotteryType2()))){
					lotteryMap = genLotteryMap(lotteryMap ,eggLottery ,LotteryType.GOLD);
				}else if(LotteryType.SILVER.equals(LotteryType.mapping(eggLottery.getLotteryType2()))){
					lotteryMap = genLotteryMap(lotteryMap ,eggLottery ,LotteryType.SILVER);
				}else if(LotteryType.COPPER.equals(LotteryType.mapping(eggLottery.getLotteryType2()))){
					lotteryMap = genLotteryMap(lotteryMap ,eggLottery ,LotteryType.COPPER);
				}
			}
		}
		BeginMissionData data = new BeginMissionData();
		data.setLotteryMap(lotteryMap);
		return data;
	}
	
	private Map<String,Integer> genLotteryMap(Map<String,Integer> lotteryMap , BeginAwardLottery eggLottery , LotteryType lotteryType){
		if(lotteryMap.get(lotteryType.getPhpConfig())!=null){
			lotteryMap.put(lotteryType.getPhpConfig(), lotteryMap.get(lotteryType.getPhpConfig())+1);
		}else{
			lotteryMap.put(lotteryType.getPhpConfig(),1);
		}
		return lotteryMap;
	}

	@Override
	public BeginMissionData eggLotteryAward(Long userId, Long lotteryType) {
		
		Boolean isNewMission = false;
		//判斷新手權限
		BeginMission mission = beginMissionDao.findByUserId(userId);
		isNewMission=checkMissionApprove(mission);
		BeginMissionData data =new BeginMissionData();
		
		Long bindCardStatus = checkBindCardStatus(userId);
		//先判斷是否已經綁卡, 沒綁卡不可以往下做
		if(BindCardType.FINISH.getValue().equals(bindCardStatus)){
			Long awardAmt = 0l;
			BeginMissionVer ver = beginMissionVerDao.findByUserId(userId);
			BeginAwardLottery beginAwardLottery = new BeginAwardLottery();
			beginAwardLottery.setUserId(userId);
			beginAwardLottery.setLotteryType2(lotteryType);
			beginAwardLottery.setStatus(AwardStatus.UnAward.getValue());
			List<BeginAwardLottery> eggLotterys = beginAwardLotteryDao.findByCondition(beginAwardLottery);
			if(eggLotterys.size()>0){
				beginAwardLottery = eggLotterys.get(0);
				BeginLotterySet beginLotterySet = new BeginLotterySet();
				beginLotterySet.setVersion(ver.getEggVer());
				beginLotterySet.setLotteryType(lotteryType);
				Optional<List<BeginLotterySet>> lotterySet = beginLotterySetDao.findByCondition(beginLotterySet);
				if(lotterySet.isPresent() && !lotterySet.get().isEmpty() && isNewMission){
					BeginLotterySet setting= lotterySet.get().get(0);
					//計算本次砸蛋可得的獎勵
					awardAmt = BeginMissionServiceHelper.createRandomValue(setting.getLotteryLow(),setting.getLotteryHigh());
					awardAmt = beginMissionAwardService.eggLotteryAward(userId,awardAmt,beginAwardLottery,setting);
				}
			}
			data.setIsNewMission(isNewMission);
			data.setLotteryAwardAmt(awardAmt);
			//當為0元表示系統已無砸蛋機會但卻進入該程序
			if(awardAmt==0l){
				data.setErrorAwardFlag(YesOrNo.YES.getValue());
			}else{
				data.setErrorAwardFlag(YesOrNo.NO.getValue());
			}
		} else {
			//尚未綁卡區段
			data.setIsNewMission(isNewMission);
			data.setLotteryAwardAmt(0l);
			data.setErrorAwardFlag(YesOrNo.YES.getValue());
		}
		data.setBindCardStatus(bindCardStatus);
		return data;
	}

	@Override
	public BeginMissionData getUserBeginNewCharge(Long userId) {
		BeginMissionData data = new BeginMissionData();
		BeginMissionVer ver = beginMissionVerDao.findByUserId(userId);
		if(ver!=null){
			Optional<List<BeginNewCharge>> opl= beginNewChargeDao.findByVersion(ver.getChargeVer());
			List<BeginNewCharge> charges = Lists.newArrayList();
			if(opl.isPresent() && !opl.get().isEmpty()){
				for(BeginNewCharge charge:opl.get()){
					charges.add(BeginMissionUtils.convertSelectAmtField(charge));
				}
			}
			data.setCharges(charges);
		}
		return data;
	}

	@Override
	public void checkBindCardMissionAndCancel(Long userId) {
		BeginMission mission =beginMissionDao.findByUserId(userId);
		if(mission!=null && 
			(Status.BEVALID.getValue().equals(mission.getStatus()) || Status.VALID.getValue().equals(mission.getStatus()))){
			Date now = DateUtils.currentDate();
			//若儲存時間大於綁卡時間以後
			if(now.getTime() > mission.getBindCardLockTime().getTime()){
				beginMissionDao.cancelMission(userId, CancelReason.Bank_Card_Double_Lock.getValue(),null);
			}
		}
	}

	@Override
	public void actionBindCheck(Long userId, String checkAccount,
			BindCardStatus status) throws Exception {
		log.info("into actionBindCheck userId={},checkAccount={}", new Object[]{userId,checkAccount});
		if(BindCardStatus.PASS.equals(status)){
			BeginMission misison = beginMissionDao.findByUserId(userId);
			Boolean isMission=checkMissionApprove(misison);
			if(isMission){
				BeginMissionVer ver = beginMissionVerDao.findByUserId(userId);
				if(ver!=null){
					Optional<List<BeginNewMission>> newMissionopl =beginNewMissionDao.findByVersion(ver.getNewMissionVer());
					if(newMissionopl.isPresent() && !newMissionopl.get().isEmpty()){
						BeginNewMission newMission= newMissionopl.get().get(0);
						BeginAward condition = BeginMissionServiceHelper.createQueryBeginAward(userId, MissionType.BIND_CARD, newMission.getId());
						//綁卡獎勵若無派獎才給
						condition = beginAwardDao.findFirstByCondition(condition);
						if(condition==null){
							beginMissionAwardService.bindCardAward(userId, newMission);							
						}
						beginMissionDao.updateStatusValid(userId);
					}
				}
			}
		}else if(BindCardStatus.UNPASS.equals(status)){
			BeginMission misison = beginMissionDao.findByUserId(userId);
			if(misison!=null){
				beginMissionDao.cancelMission(userId, CancelReason.Bank_Card_check_Error.getValue(),checkAccount);
			}
		}else{
			throw new Exception("status error");
		}
	}

	@Override
	public void bankCardLock(Long userId) {
		BeginMission mission = beginMissionDao.findByUserId(userId);
		//當狀態為待綁卡才進行後續動作
		if(mission!=null && (new Date().getTime() <= mission.getBindCardEndTime().getTime()) && 
				Status.BEVALID.getValue().equals(mission.getStatus())){
			Boolean isSuccess = false;
			beginMissionDao.updateBindCardLockTime(userId);			
			List<UserBank> banks = bankCardDao.getFormUserBankByUserId(mission.getUserId());
			//判斷是否只有一張卡
			if(banks!=null &&  banks.size()==1){
				UserBank bank= banks.get(0);
				Long bankNum = bankCardDao.getCountFromUserBankByBankNum(bank.getBankNumber());
				//判斷該卡是否不曾註冊
				if(bankNum==1l){
					Long actionBankNum = bankCardDao.getCountFromUserBankHistoryByDate(mission.getUserId(), mission.getBindCardLockTime());
					//判斷是否綁定後有異動銀行卡
					if(actionBankNum==0){
						BeginMissionVer ver =beginMissionVerDao.findByUserId(userId);
						Optional<List<BeginNewMission>> newMissionOpl= beginNewMissionDao.findByVersion(ver.getNewMissionVer());
						if(newMissionOpl.isPresent() && !newMissionOpl.get().isEmpty()){
							BeginNewMission newMission= newMissionOpl.get().get(0);
							if(YesOrNo.NO.getValue().equals(newMission.getBindCardCheck())){
								BeginAward awardCondition = BeginMissionServiceHelper.createQueryBeginAward(mission.getUserId(), MissionType.BIND_CARD, newMission.getId());
								awardCondition = beginAwardDao.findFirstByCondition(awardCondition);
								//確定無派獎才發獎勵
								if(awardCondition==null){
									beginMissionAwardService.bindCardAward(mission.getUserId(), newMission);								
								}
								BeginBankCardCheck bindCardCheck = BeginMissionServiceHelper.createBeginBankCardCheck(mission.getUserId(), BindCardStatus.PASS);
								bindCardCheck.setBankNum(bank.getBankNumber());
								bindCardCheck.setAccountName(bank.getBankAccount());							
								bindCardCheck.setCity(bank.getCity());
								
								try {
									if(beginBankCardCheckDao.queryByUserId(bindCardCheck.getUserId())==null){
										beginBankCardCheckDao.insert(bindCardCheck);																	
										beginMissionDao.updateStatusValid(mission.getUserId());									
										isSuccess = true;
									}
								} catch (Exception e) {
									log.error("綁卡帳號查詢錯誤",e);
								}

							}else{
								BeginBankCardCheck bindCardCheck = BeginMissionServiceHelper.createBeginBankCardCheck(mission.getUserId(), BindCardStatus.BEHCECK);
								bindCardCheck.setBankNum(bank.getBankNumber());
								bindCardCheck.setAccountName(bank.getBankAccount());							
								bindCardCheck.setCity(bank.getCity());
								try {
									if(beginBankCardCheckDao.queryByUserId(bindCardCheck.getUserId())==null){
										beginBankCardCheckDao.insert(bindCardCheck);
										isSuccess = true;
									}
								} catch (Exception e) {
									log.error("綁卡帳號查詢錯誤",e);								
								}
							}
						}
					}
				}
			}
			
			//銀行卡資料不合,取消活動資格
			if(!isSuccess){
				beginMissionDao.cancelMission(mission.getUserId(), CancelReason.Bank_Card_Error.getValue(),null);
				BeginBankCardCheck bindCardCheck = BeginMissionServiceHelper.createBeginBankCardCheck(mission.getUserId(), BindCardStatus.UNPASS);
				try {
					if(beginBankCardCheckDao.queryByUserId(mission.getUserId())==null){
						beginBankCardCheckDao.insert(bindCardCheck);
					}
				} catch (Exception e) {
					log.error("綁卡帳號查詢錯誤",e);	
				}
			}
			
		}
	}
}