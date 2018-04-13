package com.winterframework.firefrog.beginmession.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.dao.BeginAwardDao;
import com.winterframework.firefrog.beginmession.dao.BeginAwardLogDao;
import com.winterframework.firefrog.beginmession.dao.BeginAwardLotteryDao;
import com.winterframework.firefrog.beginmession.dao.BeginMissionDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAward;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLog;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLottery;
import com.winterframework.firefrog.beginmession.dao.vo.BeginLotterySet;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDayques;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.AwardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.LotteryUnit;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.beginmession.service.BeginMissionAwardService;
import com.winterframework.firefrog.beginmession.utils.BeginMissionServiceHelper;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;

/**
 * 活動各種派獎邏輯
 * @author Ami.Tsai
 *
 */
@Service
@Transactional
public class BeginMissionAwardServiceImpl implements BeginMissionAwardService{

	
	private static final Logger log = LoggerFactory.getLogger(BeginMissionAwardServiceImpl.class);
	
	private static final String ACTIVITY_REASON_KEY = "PM-PGXX-3";
	
	@Autowired
	private BeginAwardDao beginAwardDao;
	@Autowired
	private BeginAwardLogDao beginAwardLogDao;

	@Autowired
	private BeginMissionDao beginMissionDao;
	
	@Autowired
	private BeginAwardLotteryDao beginAwardLotteryDao;
	
	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;
	
	
	@Override
	public void firstChargeAward(List<BeginNewCharge> missionChagres,
			Long userId, Long amount) {
		log.info("into firstChargeAward userId="+userId+" amount="+amount);
		
		BeginAward queryAward = new BeginAward();
		queryAward.setUserId(userId);
		queryAward.setAwardType1(MissionType.FIRST_CHARGE.getValue());
		List<BeginAward> awards = beginAwardDao.findByCondition(queryAward);
		if(awards==null || awards.isEmpty()){
			for(BeginNewCharge charge:missionChagres){
				BeginAward award = BeginMissionServiceHelper.createBeginAward(userId, MissionType.FIRST_CHARGE);
				award.setMissionId(charge.getId());			
				if("Y".equals(charge.getMultipleGet())){
					//併列領取
					award.setAwardType2(1l);
				}else{
					//非併列領取
					award.setAwardType2(0l);
				}
				beginAwardDao.insert(award);
				//流水倍數=0,直接派獎,非直接派獎待批次派獎
				if(charge.getChargeFactor()==0l){
					Long awardAmt = 0l;
					if(charge.getChargePreium()!=0l){
						//若直接返獎有錢則用直接
						awardAmt+= charge.getChargePreium();					
					}
					if(charge.getChargePer()!=0l){
						//若百分比有錢則使用百分比
						awardAmt+= amount*(charge.getChargePreium()/100l);					
					}
					
					if(awardAmt>0){
						actionAward(award,awardAmt,MissionType.FIRST_CHARGE);											
					}
				}
			}
		}
	}

	@Override
	public void firstWithdrawAward(Long userId,BeginNewMission newMission,Long amount) {
		log.info("into firstWithdrawAward userId="+userId+" amount="+amount);
		BeginAward queryAward = new BeginAward();
		queryAward.setUserId(userId);
		queryAward.setAwardType1(MissionType.FIRST_WITHDRAW.getValue());
		List<BeginAward> awards = beginAwardDao.findByCondition(queryAward);
		if(awards==null || awards.isEmpty()){
			BeginAward award = BeginMissionServiceHelper.createBeginAward(userId, MissionType.FIRST_WITHDRAW);
			award.setMissionId(newMission.getId());
			beginAwardDao.insert(award);
			if(newMission.getWithdrawFactor()>0 && amount >= newMission.getWithdrawFactor()){
				Long awardAmount = newMission.getWithdrawPremium();
				actionAward(award,awardAmount,MissionType.FIRST_WITHDRAW);				
			}
		}
	}
	
	private void actionAward(BeginAward award , Long amount,MissionType type){
		BeginAwardLog awardLog= BeginMissionServiceHelper.createBeginAwardLog(award, amount);
		award.setStatus(AwardStatus.toBeAward.getValue());
		beginAwardDao.updateAwardStatus(award);
		try {
			saveFundChaneLog(award.getUserId(),awardLog.getPrize(),type);
			beginAwardLogDao.insert(awardLog);
			award.setStatus(AwardStatus.AwardSucess.getValue());
			award.setAwardTime(awardLog.getAwardTime());
			beginAwardDao.updateAwardStatus(award);
		} catch (Exception e) {
			log.error(" actionAward error beginAward Id = "+award.getId());
			award.setStatus(AwardStatus.AwardFail.getValue());
			beginAwardDao.updateAwardStatus(award);
		}
	}
	
	
	private void saveFundChaneLog(Long userId, Long amount,MissionType type){
		FundGameVo vo = new FundGameVo();
		vo.setUserId(userId);
		vo.setAmount(amount);
		vo.setIsAclUser(0L);
		vo.setOperator(0L);
		vo.setReason(ACTIVITY_REASON_KEY);
		vo.setNote(type.getText());
		List<FundGameVo> vos = Lists.newArrayList();
		vos.add(vo);
		List<FundChangeDetail> maps = new ArrayList<FundChangeDetail>();
		fundChangeService.action(vos, maps);
	}

	@Override
	public Long dailyAnswerAward(Long userId, Long amount , BeginNewMission newMission) {
		Long unit = newMission.getDayAnsUnit();		
		BeginAward award = BeginMissionServiceHelper.createBeginAward(userId, MissionType.DAY_ANS);
		award.setMissionId(newMission.getId());
		beginAwardDao.insert(award);
		LotteryUnit lotteryUnit = LotteryUnit.mapping(unit);
		if(lotteryUnit.equals(LotteryUnit.ANGLE)){
			amount = amount/10L;
		}else if(lotteryUnit.equals(LotteryUnit.CENT)){
			amount = amount/100L;
		}
		actionAward(award,amount,MissionType.DAY_ANS);
		
		return amount;
	}

	@Override
	public void continueDailyAnsAward(Long userId, Long amount,BeginNewDayques dayQues) {
		BeginAward award = BeginMissionServiceHelper.createBeginAward(userId, MissionType.TOL_ANS);
		award.setMissionId(dayQues.getId());
		beginAwardDao.insert(award);
		actionAward(award,amount,MissionType.TOL_ANS);			
	}

	@Override
	public Long eggLotteryAward(Long userId, Long awardAmt,
			BeginAwardLottery beginAwardLottery, BeginLotterySet setting) {
		if(LotteryUnit.ANGLE.equals(LotteryUnit.mapping(setting.getLotteryUnit()))){
			awardAmt = awardAmt/10l;
		}else if(LotteryUnit.CENT.equals(LotteryUnit.mapping(setting.getLotteryUnit()))){
			awardAmt = awardAmt/100l;
		}
		BeginAwardLottery updateAwardBean = new BeginAwardLottery();
		updateAwardBean.setId(beginAwardLottery.getId());
		updateAwardBean.setStatus(AwardStatus.toBeAward.getValue());
		beginAwardLotteryDao.updateLotteryAwardStatus(updateAwardBean);

		try {
			saveFundChaneLog(userId,awardAmt,MissionType.EGG);
			BeginAwardLog awardLog = BeginMissionServiceHelper.createBeginAwardLog(beginAwardLottery, awardAmt);
			beginAwardLogDao.insert(awardLog);
			updateAwardBean = new BeginAwardLottery();
			updateAwardBean.setId(beginAwardLottery.getId());
			updateAwardBean.setStatus(AwardStatus.AwardSucess.getValue());
			updateAwardBean.setAwardTime(awardLog.getAwardTime());
			beginAwardLotteryDao.updateLotteryAwardStatus(updateAwardBean);
		} catch (Exception e){
			log.error(" eggLotteryAward error beginAwardLottery Id = "+beginAwardLottery.getId());
			updateAwardBean = new BeginAwardLottery();
			updateAwardBean.setId(beginAwardLottery.getId());
			updateAwardBean.setStatus(AwardStatus.AwardFail.getValue());
			beginAwardLotteryDao.updateLotteryAwardStatus(updateAwardBean);
		}
		return awardAmt;
	}

	@Override
	public void bindCardAward(Long userId,BeginNewMission mission){
		BeginAward award = BeginMissionServiceHelper.createBeginAward(userId, MissionType.BIND_CARD);
		award.setMissionId(mission.getId());
		beginAwardDao.insert(award);
		actionAward(award,mission.getBindCardPremium(),MissionType.BIND_CARD);
	}

}
