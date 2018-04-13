package com.winterframework.firefrog.active.web.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.winterframework.firefrog.active.entity.ActivityAuguest;


public class DtoConverter {
	
	public static ActAuguestResponse activity2response(ActivityAuguest actEntity){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		ActAuguestResponse actRsp = new ActAuguestResponse();
		actRsp.setStatus(actEntity.getStatus());
		actRsp.setQualification((actEntity.isQualification())?1L : 0L);
		
		actRsp.setRest(actEntity.getSurplusPrize());
		actRsp.setRedEnvelope(actEntity.getPrize());
		actRsp.setBetsTotal(actEntity.getPrizeAmount());
		actRsp.setBetAmount(actEntity.getUserAmount());
		actRsp.setLeftAmount(actEntity.getLeftAmount());
		actRsp.setIsFinished((actEntity.isFinished() )? 1L : 0L);
		actRsp.setTodayFinished(actEntity.isTodayFinished() ? 1L : 0L);
		if(actEntity.getTodayStartTime() != null){
			Date todaStart = actEntity.getTodayStartTime();
			actRsp.setTodayStartTime(sdf.format(todaStart));
		}
		
		if(actEntity.getTodayEndTime() != null){
			Date todaEnd = actEntity.getTodayEndTime();
			actRsp.setTodayEndTime(sdf.format(todaEnd));
		}
		if(actEntity.getNowTime() != null){
			Date nowTime = actEntity.getNowTime();
			actRsp.setNowTime(sdf.format(nowTime));
		}
		return actRsp;
	}

}
