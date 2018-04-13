package com.winterframework.firefrog.beginmession.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.Status;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginMissionDao extends BaseDao<BeginMission>{
	
	public BeginMission findByUserId(Long userId);
	
	public Long findFirstWithdraw(Long userId , List<Long> status);

	public Long findFirstcharge(Long userId , Long status);
	
	public Integer updatebindCardDate(Long userId , Date bindCardDate);
	
	public Integer updateChargeAmt(Long userId , Long chargeAmt);
	
	public Integer updateWithdrawAmt(Long userId , Long withdrawAmt);
	
	public List<BeginMission> getChargeMission();
	
	public List<BeginMission> findByCondition(BeginMission entity);

	public List<BeginMission> getDailyBetMission();
	
	public List<BeginMission> getBindCardMission();
	
	public void cancelMission(Long userId , String cancelReason );
	
	public void cancelOverTimeMission();
	
	public void updateStatusValid(Long userId,Status status);
	
	public List<BeginMission> getInValidMission();
}
