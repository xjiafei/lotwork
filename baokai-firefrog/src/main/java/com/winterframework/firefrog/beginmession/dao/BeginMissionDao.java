package com.winterframework.firefrog.beginmession.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.entity.CancelListData;
import com.winterframework.firefrog.beginmession.web.dto.BeginCancelListDto;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginMissionDao extends BaseDao<BeginMission>{
	
	public BeginMission findByUserId(Long userId);
	
	public Long findFirstWithdraw(Long userId , List<Long> status);

	public Long findFirstcharge(Long userId , Long status);
	
	public Integer updateMissionTime(Long userId , Date bindCardTime,Date misisonEndTime, Date bindCardLockTime);
	
	public Integer updateChargeAmt(Long userId , Long chargeAmt);
	
	public Integer updateWithdrawAmt(Long userId , Long withdrawAmt);
	
	public void cancelMission(Long userId , String cancelReason,String cancelUser );
	
	public CountPage<CancelListData> searchCancelList(PageRequest<BeginCancelListDto> pageRequest) throws Exception;
	
	public List<CancelListData> searchCancelListAll(BeginCancelListDto req) throws Exception ;
	
	public void updateStatusValid(Long userId);
	
	public void updateBindCardLockTime(Long userId);
	
}
