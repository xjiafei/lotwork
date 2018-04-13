package com.winterframework.firefrog.shortlived.gamemission.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMission;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMissionComplete;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.GameMissionUserData;
import com.winterframework.firefrog.shortlived.gamemission.dao.entity.convertor.GameMissionEntityVoConvertor;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionCompleteRequest;
import com.winterframework.firefrog.shortlived.gamemission.dto.GameMissionUserDataRequest;
import com.winterframework.firefrog.shortlived.gamemission.param.DiamondCollectParam;
import com.winterframework.firefrog.shortlived.gamemission.param.DiamondCollectTargetParam;
import com.winterframework.firefrog.shortlived.gamemission.vo.FundGameVo;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionUserDataVo;
import com.winterframework.firefrog.shortlived.gamemission.vo.GameMissionVo;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("diamondCollectMissionServiceImpl")
public class DiamondCollectMissionServiceImpl extends GameMissionAbstractService {
	
	private final String ACTIVITY_BONUS_REASON_KEY = "PM-PGXX-3";
	
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.baseFundUrl")
	private String baseFundUrl;
	
	@PropertyConfig(value = "url.fund.action")
	private String fundActionUrl;

	@Override
	public List<GameMissionUserDataVo> queryUserMissionData(
			GameMissionUserDataRequest request) {
		GameMission req = new GameMission();
		req.setTargetCode(request.getMissionCode());
		req.setTargetIsActive(1L);
		List<GameMission> missions = gameMissionDao.queryMissions(req);
		List<GameMissionUserDataVo> results = new ArrayList<GameMissionUserDataVo>();
		for (GameMission mission : missions) {
			GameMissionUserDataVo data = new GameMissionUserDataVo();
			String paramJson = mission.getParams();
			DiamondCollectParam param = jsonMapper.fromJson(paramJson,
					DiamondCollectParam.class);
			List<DiamondCollectTargetParam> targets = param.getDiamondTargets();
			for (DiamondCollectTargetParam target : targets) {
				Long collectTimes = queryUserDiamondCount(mission, target, request.getUserId());
				target.setCollectTimes(collectTimes);
			}
			List<GameMissionUserData> completes = queryUserCompletes(mission, request.getUserId());
			data.setMissionCode(mission.getCode());
			data.setItemSeq(mission.getItemSeq());
			data.setCompleteTimes(completes.size());
			data.setTargets(jsonMapper.toJson(targets));
			results.add(data);
		}
		return results;
	}

	@Override
	public boolean validateUserMssion(GameMissionVo mission,
			GameMissionCompleteRequest request) {
		DiamondCollectParam param = jsonMapper.fromJson(mission.getParams(), DiamondCollectParam.class);
		List<DiamondCollectTargetParam> targets = param.getDiamondTargets();
		GameMission missionEntity = GameMissionEntityVoConvertor.convertGameMissionToEntity(mission);
		boolean isCompleteAllTarget = true;
		for (DiamondCollectTargetParam target : targets) {
			Long userCollectDiamondCount = queryUserDiamondCount(missionEntity, target, request.getUserId());
			boolean isCompleteTarget = userCollectDiamondCount >= target
					.getNeedTime();
			isCompleteAllTarget = isCompleteAllTarget && isCompleteTarget;
		}
		List<GameMissionUserData> completes = queryUserCompletes(missionEntity, request.getUserId());
		boolean missionTimeNotAllFinished = completes.size()< param.getMissionTime();
		return isCompleteAllTarget && missionTimeNotAllFinished;
	}
	
	private Long queryUserDiamondCount(GameMission mission,DiamondCollectTargetParam target,Long userId){
		GameMissionUserData dataReq = new GameMissionUserData();
		dataReq.setTargetMissionId(mission.getId());
		dataReq.setTargetDiamonds(target.getDiamond());
		dataReq.setTargetStartTime(mission.getStartTime());
		dataReq.setTargetEndTime(mission.getEndTime());
		dataReq.setTargetUserId(userId);
		dataReq.setTargetNeedPay(target.getNeedPay());
		Long userCollectDiamondCount = gameMissionCompleteDao
				.queryUserDiamondCount(dataReq);
		return userCollectDiamondCount;
	}
	
	private List<GameMissionUserData> queryUserCompletes(GameMission mission,Long userId){

		GameMissionComplete completeReq = new GameMissionComplete();
		completeReq.setTargetMissionId(mission.getId());
		completeReq.setTargetCreateDateStart(mission.getStartTime());
		completeReq.setTargetCreateDateEnd(mission.getEndTime());
		completeReq.setTargetUserId(userId);
		List<GameMissionUserData> completes = gameMissionCompleteDao
				.queryMissionCompletes(completeReq);
		return completes;
	}

	@Override
	protected void doAfterInsertCompleteMission(GameMissionVo mission,
			GameMissionCompleteRequest request) {
		DiamondCollectParam param = jsonMapper.fromJson(mission.getParams(), DiamondCollectParam.class);
		bonusFundAction(request.getUserId(), param.getBonus()*10000, ACTIVITY_BONUS_REASON_KEY, "钻石加奖任务奖金");
	}
	
	private void bonusFundAction(Long userId,Long amount,String reason,String note){
		FundGameVo vo = new FundGameVo();
	    vo.setUserId(userId);
	    vo.setAmount(amount);
	    vo.setIsAclUser(0L);
	    vo.setOperator(0L);
	    vo.setReason(reason);
	    vo.setNote(note);
	    List<FundGameVo> vos = new ArrayList<FundGameVo>();
	    vos.add(vo);
		try {
			httpClient.invokeHttpWithoutResultType(baseFundUrl + fundActionUrl, vos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
