package com.winterframework.firefrog.beginmession.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.beginmession.entity.BeginCheckData;
import com.winterframework.firefrog.beginmession.entity.BeginSearchCheckData;
import com.winterframework.firefrog.beginmession.entity.CancelListData;
import com.winterframework.firefrog.beginmession.web.dto.BeginCancelListDto;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.modules.page.PageRequest;

public interface BeginBankCardCheckService {

	/**
	 * search begine bindcard data
	 * @param vo
	 * @throws Exception
	 */
//	public Page<BeginBankCardCheck> getAllByPage(PageRequest<BeginSearchCheckDataRequest> pageRequest) throws Exception;
	public CountPage<BeginSearchCheckData> queryBankCardChecksByCondition(
			PageRequest<BeginCheckData> pageRequest) throws Exception;
	public List<BeginSearchCheckData> queryCheckDataAllByCondition(BeginCheckData req) throws Exception;
	/**
	 * 查詢取消名單
	 * @param status
	 * @param missionStartTime
	 * @param missionEndTime
	 * @return
	 */
	public CountPage<CancelListData> searchCancelList(PageRequest<BeginCancelListDto> pageRequest) throws Exception;
	
	/**
	 * 查詢取消名單(無分頁，excel用)
	 * @param status
	 * @param missionStartTime
	 * @param missionEndTime
	 * @return
	 */
	public List<CancelListData> searchCancelListAll(BeginCancelListDto req) throws Exception;
	
	public void updateCheckStatus(Map<Long,String> userids , String checkUser) throws Exception;
	//批次通過/拒絕
	public List<String> batchUpdateCheckStatus(List<String> accounts, String checkUser,String status) throws Exception;
	//取消資格
	public Integer backendCancelMission(String cancelUser,String account,String reason) throws Exception;
	
}
