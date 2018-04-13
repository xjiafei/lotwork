package com.winterframework.firefrog.beginmession.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.winterframework.firefrog.beginmession.dao.BeginNewChargeDao;
import com.winterframework.firefrog.beginmession.dao.BeginNewMissionDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewCharge;
import com.winterframework.firefrog.beginmession.dao.vo.BeginNewMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.YesOrNo;
import com.winterframework.firefrog.beginmession.service.BeginNewMissionService;
import com.winterframework.firefrog.beginmession.utils.BeginMissionUtils;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewMissionRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginNewMissionResponse;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Service
@Transactional
public class BeginNewMissionServiceImpl implements BeginNewMissionService {

	private static Logger log = LoggerFactory.getLogger(BeginNewMissionServiceImpl.class);
	
	@Autowired
	private BeginNewMissionDao missionDao;
	
	@Autowired
	private BeginNewChargeDao chargeDao;
	
	
	private final Integer chargeSize=5;
	
	@Override
	public void insertNewMission(BeginNewMissionRequest dto) throws Exception {
		log.info("into insertNewMission");
		List<BeginNewCharge> charges= dto.getCharges();
		BeginNewMission mission = dto.getMission();
		final Long missionMaxVersion = missionDao.getMaxVersion();
		final Long chargeMaxVersion = chargeDao.getMaxVersion();		
		mission.setVersion(missionMaxVersion+1);
		
		List<BeginNewCharge> charges2 = new ArrayList<BeginNewCharge>();
		
		for(BeginNewCharge charge:charges){
			charge.setVersion(chargeMaxVersion+1);
			if(StringUtils.isEmpty(charge.getMultipleGet())){
				charge.setMultipleGet(YesOrNo.NO.getValue());					
			}
			charges2.add(BeginMissionUtils.convertInsertAmtField(charge));
		}
		
		Optional<List<BeginNewMission>> missionOptional= missionDao.findMaxVersion();
		mission = BeginMissionUtils.convertInsertAmtField(mission);
		if(missionOptional.isPresent() && !missionOptional.get().isEmpty()){
			//因為Begin_new_mission在日常任務頁面有使用,需要同步兩邊資料
			BeginNewMission beforeMission = missionOptional.get().get(0);
			mission.setDayBetFactor(beforeMission.getDayBetFactor());
			mission.setDayAnsHigh(beforeMission.getDayAnsHigh());
			mission.setDayAnsLow(beforeMission.getDayAnsLow());
			mission.setDayAnsUnit(beforeMission.getDayAnsUnit());
		}
		missionDao.insert(mission);		
		BeginMissionUtils.logBeginMissionPageOneRow(missionOptional, mission, BeginMissionEnum.LogType.NEW_MISSION, mission.getModifyUser(), BeginNewMission.class);
		Optional<List<BeginNewCharge>> chargeOptional = chargeDao.findMaxVersion();
		chargeDao.insert(charges);
		BeginMissionUtils.logBeginMissionPageManyRow(chargeOptional, charges2, BeginMissionEnum.LogType.NEW_MISSION, mission.getModifyUser(), BeginNewCharge.class);
	}

	@Override
	public BeginNewMissionResponse selectNewMissionMaxVersion() throws Exception {
		log.info("into selectNewMissionMaxVersion");
		BeginNewMissionResponse reponse = new BeginNewMissionResponse();
		 Optional<List<BeginNewMission>> missionOptional= missionDao.findMaxVersion();
		 Optional<List<BeginNewCharge>> chargeOptional = chargeDao.findMaxVersion();
		 if(missionOptional.isPresent() && missionOptional.get().size()>0){
			 reponse.setMission(BeginMissionUtils.convertSelectAmtField(missionOptional.get().get(0)));
		 }else{
			 reponse.setMission(new BeginNewMission());
		 }
		 reponse.setCharges(BeginMissionUtils.createPageList(chargeOptional,chargeSize,BeginNewCharge.class));
		return reponse;
	}


}
