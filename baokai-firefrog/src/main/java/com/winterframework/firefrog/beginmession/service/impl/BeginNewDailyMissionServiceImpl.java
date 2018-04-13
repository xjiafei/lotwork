package com.winterframework.firefrog.beginmession.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.winterframework.firefrog.beginmession.dao.BeginNewDaybetDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewDayquesDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewMissionDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewTolbetDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDaybet;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewDayques;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewTolbet;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.YesOrNo;
import com.winterframework.firefrog.beginmession.service.BeginNewDailyMissionService;
import com.winterframework.firefrog.beginmession.utils.BeginMissionUtils;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewDailyMissionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewDailyMissionResponse;
import com.winterframework.firefrog.common.util.DateUtils;

@Service
@Transactional
public class BeginNewDailyMissionServiceImpl implements BeginNewDailyMissionService{
	
	@Autowired
	private BeginNewMissionDao beginNewMissionDao;
	
	@Autowired
	private BeginNewDaybetDao beginNewDaybetDao;
	
	@Autowired
	private BeginNewDayquesDao beginNewDayquesDao;
	
	@Autowired
	private BeginNewTolbetDao beginNewTolbetDao;
	
	
	//每日投注
	private final int  daybetSize = 5;

	//每日答題
	private final int  dayquesSize = 3;

	//累計投注
	private final int  tolbetSize = 3;


	
	@Override
	public void insertDailyMission(BeginNewDailyMissionRequest dto) throws Exception {
		
		
		BeginNewMission beginNewMission  = dto.getBeginNewMission();
		List<BeginNewDaybet> beginNewDaybets =dto.getBeginNewDaybets();
		List<BeginNewDayques> beginNewDayqueses =dto.getBeginNewDayqueses();
		List<BeginNewTolbet> beginNewTolbets =  dto.getBeginNewTolbets();
		
		//日常任務Dao
		final Long dailyMaxVersion = beginNewDaybetDao.getMaxVersion();
		
		//新手TableDao
		final Long missionMaxVersion = beginNewMissionDao.getMaxVersion();
		
		final String userAccount = beginNewMission.getModifyUser();
		beginNewMission.setVersion(missionMaxVersion+1);
		
		List<BeginNewDaybet> dayBets = new ArrayList<BeginNewDaybet>();
		
		for(BeginNewDaybet bet:beginNewDaybets){
			bet.setVersion(dailyMaxVersion+1);
			bet.setCreateUser(userAccount);
			bet.setModifyUser(userAccount);
			bet.setCreateTime(DateUtils.currentDate());
			bet.setModifyTime(DateUtils.currentDate());
			if(StringUtils.isEmpty(bet.getIsAmount())){
				bet.setIsAmount(YesOrNo.NO.getValue());					
			}
			if(StringUtils.isEmpty(bet.getIsLottery())){
				bet.setIsLottery(YesOrNo.NO.getValue());					
			}
			dayBets.add(BeginMissionUtils.convertInsertAmtField(bet));
		}
		
		
		List<BeginNewDayques> dayqueses = new ArrayList<BeginNewDayques>();
		for(BeginNewDayques dayques:beginNewDayqueses){
			dayques.setVersion(dailyMaxVersion+1);
			dayques.setCreateUser(userAccount);
			dayques.setModifyUser(userAccount);
			dayques.setCreateTime(DateUtils.currentDate());
			dayques.setModifyTime(DateUtils.currentDate());
			if(StringUtils.isEmpty(dayques.getIsAmount())){
				dayques.setIsAmount(YesOrNo.NO.getValue());					
			}
			if(StringUtils.isEmpty(dayques.getIsLottery())){
				dayques.setIsLottery(YesOrNo.NO.getValue());					
			}
			dayqueses.add(BeginMissionUtils.convertInsertAmtField(dayques));
		}
		
		
		List<BeginNewTolbet> tolbets = new ArrayList<BeginNewTolbet>();
		for(BeginNewTolbet tolbet:beginNewTolbets){
			tolbet.setVersion(dailyMaxVersion+1);
			tolbet.setCreateUser(userAccount);
			tolbet.setModifyUser(userAccount);
			tolbet.setCreateTime(DateUtils.currentDate());
			tolbet.setModifyTime(DateUtils.currentDate());
			if(StringUtils.isEmpty(tolbet.getIsAmount())){
				tolbet.setIsAmount(YesOrNo.NO.getValue());					
			}
			if(StringUtils.isEmpty(tolbet.getIsLottery())){
				tolbet.setIsLottery(YesOrNo.NO.getValue());					
			}
			tolbets.add(BeginMissionUtils.convertInsertAmtField(tolbet));
		}
		beginNewMission = BeginMissionUtils.convertInsertAmtField(beginNewMission);
		Optional<List<BeginNewMission>> newMissionOptional= beginNewMissionDao.findMaxVersion();
		if(newMissionOptional.isPresent() && !newMissionOptional.get().isEmpty()){
			BeginNewMission mission = newMissionOptional.get().get(0);
			//因為Begin_new_mission在新手任務頁面有使用,需要同步兩邊資料
			beginNewMission.setBindCardText(mission.getBindCardText());
			beginNewMission.setBindCardCheck(mission.getBindCardCheck());
			beginNewMission.setBindCardPremium(mission.getBindCardPremium());
			beginNewMission.setChargeText(mission.getChargeText());
			beginNewMission.setWithdrawFactor(mission.getWithdrawFactor());
			beginNewMission.setWithdrawText(mission.getWithdrawText());
			beginNewMission.setWithdrawPremium(mission.getWithdrawPremium());
			beginNewMissionDao.insert(beginNewMission);
		}else{
			beginNewMissionDao.insert(beginNewMission);
		}

		BeginMissionUtils.logBeginMissionPageOneRow(newMissionOptional, beginNewMission, BeginMissionEnum.LogType.DAILY_MISSION, beginNewMission.getModifyUser(), BeginNewMission.class);

		Optional<List<BeginNewDaybet>> dayBetOptional = beginNewDaybetDao.findMaxVersion();
		beginNewDaybetDao.insert(dayBets);
		BeginMissionUtils.logBeginMissionPageManyRow(dayBetOptional, dayBets, BeginMissionEnum.LogType.DAILY_MISSION, beginNewMission.getModifyUser(), BeginNewDaybet.class);
		
		Optional<List<BeginNewDayques>> newDayquesOptional = beginNewDayquesDao.findMaxVersion();
		beginNewDayquesDao.insert(dayqueses);
		BeginMissionUtils.logBeginMissionPageManyRow(newDayquesOptional, dayqueses, BeginMissionEnum.LogType.DAILY_MISSION, beginNewMission.getModifyUser(), BeginNewDayques.class);
		
		Optional<List<BeginNewTolbet>> tolBetOptional = beginNewTolbetDao.findMaxVersion();
		beginNewTolbetDao.insert(tolbets);
		BeginMissionUtils.logBeginMissionPageManyRow(tolBetOptional, tolbets, BeginMissionEnum.LogType.DAILY_MISSION, beginNewMission.getModifyUser(), BeginNewTolbet.class);
		
	}

	@Override
	public BeginNewDailyMissionResponse selectNewMissionMaxVersion() throws Exception {
		BeginNewDailyMissionResponse reponse = new BeginNewDailyMissionResponse();
		Optional<List<BeginNewDaybet>> beginNewDaybetOptional = beginNewDaybetDao.findMaxVersion();
		Optional<List<BeginNewDayques>> beginNewDayquesOptional =beginNewDayquesDao.findMaxVersion();
		Optional<List<BeginNewMission>> BeginNewMissionOptional =beginNewMissionDao.findMaxVersion();
		Optional<List<BeginNewTolbet>> beginNewTolbetOptional =beginNewTolbetDao.findMaxVersion();
		 
		 if(BeginNewMissionOptional.isPresent() && BeginNewMissionOptional.get().size()>0){
			 reponse.setBeginNewMission(BeginMissionUtils.convertSelectAmtField(BeginNewMissionOptional.get().get(0)));
		 }else{
			 reponse.setBeginNewMission(new BeginNewMission());
		 }
		 
		 //每日投注
		 reponse.setBeginNewDaybets(BeginMissionUtils.createPageList(beginNewDaybetOptional,daybetSize,BeginNewDaybet.class));
		 reponse.setBeginNewDayqueses(BeginMissionUtils.createPageList(beginNewDayquesOptional,dayquesSize,BeginNewDayques.class));
		 reponse.setBeginNewTolbets(BeginMissionUtils.createPageList(beginNewTolbetOptional,tolbetSize,BeginNewTolbet.class));
		return reponse;
	}

}
