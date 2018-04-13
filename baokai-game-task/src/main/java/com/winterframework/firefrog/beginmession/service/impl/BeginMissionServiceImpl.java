package com.winterframework.firefrog.beginmession.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginAwardDao;
import com.winterframework.firefrog.beginmession.dao.BeginAwardLotteryDao;
import com.winterframework.firefrog.beginmession.dao.BeginBankCardCheckDao;
import com.winterframework.firefrog.beginmession.dao.BeginMissionDao;
import com.winterframework.firefrog.beginmession.dao.BeginMissionTaskLogDao;
import com.winterframework.firefrog.beginmession.dao.BeginMissionVerDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewChargeDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewDaybetDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewDayquesDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewMissionDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewTolbetDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAward;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLottery;
import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheck;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMissionTaskLog;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMissionVer;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDaybet;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewTolbet;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.AwardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.BindCardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.CancelReason;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.LotteryType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionTask;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.Status;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.TaskStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.YesOrNo;
import com.winterframework.firefrog.beginmession.service.BeginMissionAwardService;
import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.beginmession.utils.BeginMissionServiceHelper;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.fund.dao.IBankCardDao;
import com.winterframework.firefrog.fund.dao.vo.UserBank;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Transactional
@Service("beginMissionServiceImpl")
public class BeginMissionServiceImpl implements BeginMissionService{

	private static final Logger log = LoggerFactory.getLogger(BeginMissionServiceImpl.class);
	
	
	@Resource(name = "httpJsonClientImpl")
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
	private BeginMissionVerDao beginMissionVerDao;
	@Autowired
	private IUserCustomerDao userCustomerDao;
	@Autowired
	private BeginAwardDao beginAwardDao;
	@Autowired
	private BeginMissionDao beginMissionDao;
	@Autowired
	private BeginAwardLotteryDao beginAwardLotteryDao;
	@Autowired
	private BeginMissionAwardService beginMissionAwardService;
	@Autowired
	private BeginMissionTaskLogDao beginMissionTaskLogDao;
	@Autowired
	private BeginBankCardCheckDao beginBankCardCheckDao;
	
	@Resource(name="bankCardDaoImpl")
	private IBankCardDao bankCardDaoImpl;
	
	@Resource(name="gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;
	
	@PropertyConfig(value = "url.business.connect")
	private String serverPath;	
	
	/**
	 * task任務只處理具有流水倍數
	 */
	@Override
	public void chargeAwardJob(BeginMission mission) {
			BeginMissionVer ver = beginMissionVerDao.findByUserId(mission.getUserId());
			Optional<List<BeginNewCharge>> opt1 = beginNewChargeDao.getByMultipleGet(ver.getUserId(), ver.getChargeVer(),YesOrNo.YES.getValue());
			//先做併列領取的
			if(opt1.isPresent() && !opt1.get().isEmpty()){
				for(BeginNewCharge charge:opt1.get()){
					BeginAward award = BeginMissionServiceHelper.createQueryBeginAward(mission.getUserId(), MissionType.FIRST_CHARGE,charge.getId());
					award.setStatus(AwardStatus.UnAward.getValue());
					award=beginAwardDao.findFirstByCondition(award);
					if(award!=null){
						Date dateLimit = BeginMissionServiceHelper.addDaysToBeforeDawn(mission.getChargeTime(), charge.getBettingDate());
						if(new Date().getTime() > dateLimit.getTime()){
							award.setStatus(AwardStatus.AwardCancel.getValue());
							award.setCancelReason(CancelReason.Award_Expired.getValue());
							beginAwardDao.updateAwardStatus(award);
						}else{
							beginMissionAwardService.charegAward(mission, charge, award);							
						}
					}
				}
			}
			
			//開始執行非並列領取
			Optional<List<BeginNewCharge>> opt2 = beginNewChargeDao.getByMultipleGet(ver.getUserId(), ver.getChargeVer(),YesOrNo.NO.getValue());
			if(opt2.isPresent() && !opt2.get().isEmpty()){
				Long maxDate = 0l;
				maxDate = beginNewChargeDao.getMaxChargeDate(mission.getUserId());
				Date chargeTime = mission.getChargeTime();
				//去得最大日期期限
				Date lilmitTime = BeginMissionServiceHelper.addDaysToBeforeDawn(chargeTime, maxDate);
				//若日期已截止,則派發獎勵
				if(new Date().getTime() > lilmitTime.getTime()){
					Boolean isSuccess  = false;
						for(BeginNewCharge charge:opt2.get()){
							BeginAward award = BeginMissionServiceHelper.createQueryBeginAward(mission.getUserId(), MissionType.FIRST_CHARGE,charge.getId());
							award.setStatus(AwardStatus.UnAward.getValue());
							award=beginAwardDao.findFirstByCondition(award);
							if(award!=null){
								//若目前時間超過充值時間+投注期限,則該獎項失效
								//或活動已截止
								if(new Date().getTime()>=mission.getMissionEndTime().getTime()){
									//該獎項超過天數尚未完成
									award.setStatus(AwardStatus.AwardCancel.getValue());
									award.setCancelReason(CancelReason.Award_Expired.getValue());
									beginAwardDao.updateAwardStatus(award);
								}else{
									isSuccess = beginMissionAwardService.charegAward(mission, charge, award);
									if(isSuccess){
										break;
									}
								}
							}
						} 
					//時間截止,一律都壓成失效
					beginAwardDao.updateUnAward(mission.getUserId(), CancelReason.Award_charge_multiget.getValue());					
				}

			}
	}
	

	@Override
	//累積投注天數
	public void tolbetAwardJob(BeginMission mission) {
		BeginMissionVer ver =beginMissionVerDao.findByUserId(mission.getUserId());
		Optional<BeginNewMission> newMission= beginNewMissionDao.findOneByVersion(ver.getNewMissionVer());
		Map<String, Object> map = Maps.newHashMap();
		map.put("userId", mission.getUserId());
		map.put("betFactor", newMission.get().getDayBetFactor());
		List<String> results = Lists.newArrayList();
		results = gameOrderDaoImpl.queryBeginMissionOrder(map);		
		//有投注才會有派獎機會
		if(results!=null && !results.isEmpty()){
			int betDays = results.size();
			List<BeginNewTolbet> tolBets= beginNewTolbetDao.getTolBetAward(ver.getTolbetVer(), betDays, ver.getUserId());
			for(BeginNewTolbet bet:tolBets){
				//發派獎金
				if(YesOrNo.YES.getValue().equals(bet.getIsAmount())){
					if(bet.getAmount()>0){
						
						if(bet.getAmount()>0){
							beginMissionAwardService.toAward(mission.getUserId(), MissionType.TOL_BET,bet.getId() , bet.getAmount());				
						}
					}
				}
				
				//發派砸蛋
				if(YesOrNo.YES.getValue().equals(bet.getIsLottery())){
					if(bet.getLottery()>0){
						for(int i=0;i<bet.getLottery();i++){
							LotteryType type= LotteryType.mapping(bet.getLotteryType());
							BeginAwardLottery lottery= BeginMissionServiceHelper.createBeginAwardLottery(mission.getUserId(), bet.getId(), type, MissionType.TOL_BET);
							beginAwardLotteryDao.insert(lottery);							
						}
					}
				}
			}
		}
	}

	@Override
	public void dailyBetAwardJob(BeginMission mission) {
		BeginMissionVer ver =beginMissionVerDao.findByUserId(mission.getUserId());
		Long todayAmt= gameOrderDaoImpl.getSumAmtByUserThisDay(mission.getUserId(), BeginMissionAwardService.ORDER_STATUSES,mission.getMissionValidTime());
		List<BeginNewDaybet> dayBets = beginNewDaybetDao.getDayBetAward(mission.getUserId(), todayAmt, ver.getDaybetVer());
		for(BeginNewDaybet dayBet:dayBets){
			if(todayAmt>=dayBet.getDaybetAmount()){
				BeginAward queryAward = BeginMissionServiceHelper.createQueryBeginAward(mission.getUserId(), MissionType.DAY_BET, dayBet.getId());
				Long awardCount = beginAwardDao.getAwardNowByMissionId(queryAward);
				//檢查是否派過獎
				if(awardCount==0){
					//發派獎金
					if(YesOrNo.YES.getValue().equals(dayBet.getIsAmount())){
						if(dayBet.getAmount()>0){
							if(dayBet.getAmount()>0){
								beginMissionAwardService.toAward(mission.getUserId(), MissionType.DAY_BET,dayBet.getId() , dayBet.getAmount());				
							}
						}
					}
					//發派砸蛋
					if(YesOrNo.YES.getValue().equals(dayBet.getIsLottery())){
						if(dayBet.getLottery()>0){
							for(int i=0;i<dayBet.getLottery();i++){
								LotteryType type= LotteryType.mapping(dayBet.getLotteryType());
								BeginAwardLottery lottery= BeginMissionServiceHelper.createBeginAwardLottery(mission.getUserId(), dayBet.getId(), type, MissionType.TOL_BET);
								beginAwardLotteryDao.insert(lottery);							
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void bindCardAwardJob(BeginMission mission) {
		Boolean isSuccess = false;
		BeginMissionVer ver =beginMissionVerDao.findByUserId(mission.getUserId());
		List<UserBank> banks = bankCardDaoImpl.getFormUserBankByUserId(mission.getUserId());
		Long count = beginBankCardCheckDao.getByUserIdCount(mission.getUserId());
		//於綁卡審核無資料才進入,因為前台增加立即綁定,會於前台馬上寫資料進審核系統
		if(count==0){
			//判斷是否只有一張卡
			if(banks!=null &&  banks.size()==1){
				UserBank bank= banks.get(0);
				Long bankNum = bankCardDaoImpl.getCountFromUserBankByBankNum(bank.getBankNumber());
				//判斷該卡是否不曾註冊
				if(bankNum==1l){
					Long actionBankNum = bankCardDaoImpl.getCountFromUserBankHistoryByDate(mission.getUserId(), mission.getBindCardLockTime());
					//判斷是否綁定後有異動銀行卡
					if(actionBankNum==0){
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
								beginBankCardCheckDao.insert(bindCardCheck);																	
								beginMissionDao.updateStatusValid(mission.getUserId(), Status.VALID);
								isSuccess = true;
							}else{
								BeginBankCardCheck bindCardCheck = BeginMissionServiceHelper.createBeginBankCardCheck(mission.getUserId(), BindCardStatus.BEHCECK);
								bindCardCheck.setBankNum(bank.getBankNumber());
								bindCardCheck.setAccountName(bank.getBankAccount());							
								bindCardCheck.setCity(bank.getCity());								
								beginBankCardCheckDao.insert(bindCardCheck);
								isSuccess = true;
							}
						}
					}
				}
			}
			//銀行卡資料不合,取消活動資格
			if(!isSuccess){
				beginMissionDao.cancelMission(mission.getUserId(), CancelReason.Bank_Card_Error.getValue());
				BeginBankCardCheck bindCardCheck = BeginMissionServiceHelper.createBeginBankCardCheck(mission.getUserId(), BindCardStatus.UNPASS);
				beginBankCardCheckDao.insert(bindCardCheck);
			}
		}
		
	}

	@Override
	public void cancelMissionJob(BeginMission beginMission) {
		if(new Date().getTime()>=beginMission.getBindCardEndTime().getTime()){
			//超過期限未綁卡
			//及在綁卡待審核超過綁卡時間者
			if(beginMission.getBindCardTime()==null || (new Date().getTime()>=beginMission.getBindCardEndTime().getTime() 
					&& BeginMissionEnum.Status.BEVALID.getValue().equals(beginMission.getStatus()))){
				beginMissionDao.cancelMission(beginMission.getUserId(), CancelReason.Bank_Card_End_Time_Error.getValue());
				beginBankCardCheckDao.updateCheckStatus(beginMission.getUserId(),BeginMissionServiceHelper.AUTO_USER,BindCardStatus.UNPASS.getValue());
			}
		}
		if(beginMission.getMissionEndTime()!=null && new Date().getTime()>=beginMission.getMissionEndTime().getTime()){
			beginMissionDao.cancelMission(beginMission.getUserId(), CancelReason.Mission_Over_Time.getValue());
		}
		
	}

	@Override
	public Long creatMissionTaskLog(MissionTask missionTask) {
		BeginMissionTaskLog taskLog = new BeginMissionTaskLog();
		taskLog.setCreateTime(new Date());
		taskLog.setStatus(TaskStatus.START.getValue());
		taskLog.setTaskType(missionTask.getValue());
		beginMissionTaskLogDao.insert(taskLog);
		return taskLog.getId();
	}

	@Override
	public void updateMissionTaskLog(Long taskId,TaskStatus status) {
		beginMissionTaskLogDao.updateStatus(taskId,status);
	}
	
	
}
