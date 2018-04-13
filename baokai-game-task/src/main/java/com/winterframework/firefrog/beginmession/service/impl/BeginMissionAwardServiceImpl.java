package com.winterframework.firefrog.beginmession.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.dao.BeginAwardDao;
import com.winterframework.firefrog.beginmession.dao.BeginAwardLogDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAward;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLog;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.AwardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.MissionType;
import com.winterframework.firefrog.beginmession.service.BeginMissionAwardService;
import com.winterframework.firefrog.beginmession.utils.BeginMissionServiceHelper;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.fund.ff.bean.FundGameVo;

@Service("beginMissionAwardServiceImpl")
public class BeginMissionAwardServiceImpl implements BeginMissionAwardService{

	private static final Logger log = LoggerFactory.getLogger(BeginMissionAwardServiceImpl.class);
	
	@Resource(name="gameOrderDaoImpl")
	private IGameOrderDao gameOrderDaoImpl;
	
	@Resource(name = "fundChangeServiceImpl")
	private IFundAtomicOperationService fundChangeService;
	
	@Autowired
	private BeginAwardDao beginAwardDao;
	
	@Autowired
	private BeginAwardLogDao beginAwardLogDao;

	
	@Override
	public Boolean charegAward(BeginMission mission , BeginNewCharge charge,BeginAward award) {
		Boolean isSucess = false;
		Date dateLimit = BeginMissionServiceHelper.addDaysToBeforeDawn(mission.getChargeTime(), charge.getBettingDate());
		Long sumOrderAmt = gameOrderDaoImpl.getSumAmtByUserIdAndStatus(mission.getUserId(), ORDER_STATUSES, dateLimit,mission.getChargeTime());
		if((sumOrderAmt!=null && sumOrderAmt >= mission.getChargeAmt()*charge.getChargeFactor()) ||
		    (charge.getChargeFactor()==0l || charge.getChargeFactor()==null)){
			Long awardAmount = 0l;
			if(charge.getChargePreium()>0){
				awardAmount+=charge.getChargePreium();
			}
			if(charge.getChargePer()>0){
				awardAmount+=mission.getChargeAmt()*charge.getChargePer()/100;
			}
			if(awardAmount>0){
				//派獎前確定該獎項是否已派過,充值派獎只允許一次派獎
				if(!checkDuplicateAward(mission.getUserId(),MissionType.FIRST_CHARGE,charge.getId())){
					actionAward(award,awardAmount,MissionType.FIRST_CHARGE);					
					isSucess = true;					
				}
			}
		}
		return isSucess;
	}
	
	private void saveFundChaneLog(Long userId, Long amount,MissionType type) throws FundChangedException, Exception{
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

	@Override
	public void toAward(Long userId ,MissionType type,Long missionId,Long amount) {
		BeginAward award = BeginMissionServiceHelper.createBeginAward(userId, type);
		award.setMissionId(missionId);
		beginAwardDao.insert(award);
		actionAward(award,amount,type);
	}

	@Override
	public void bindCardAward(Long userId, BeginNewMission newMission) {
		toAward(userId,MissionType.BIND_CARD,newMission.getId(),newMission.getBindCardPremium());
	}
	
	private Boolean checkDuplicateAward(Long userId,MissionType type,Long missionId){
		BeginAward queryAward = BeginMissionServiceHelper.createQueryBeginAward(userId, type,missionId);
		queryAward.setStatus(AwardStatus.AwardSucess.getValue());
		queryAward=beginAwardDao.findFirstByCondition(queryAward);
		if(queryAward==null){
			return false;
		}else{
			return true;
		}
	}
	
}
