package com.winterframework.firefrog.beginmession.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.beginmession.dao.BeginBankCardCheckDao;
import com.winterframework.firefrog.beginmession.dao.BeginMissionDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheck;
import com.winterframework.firefrog.beginmession.dao.vo.BeginMission;
import com.winterframework.firefrog.beginmession.entity.BeginBankCardCheckData;
import com.winterframework.firefrog.beginmession.entity.BeginCheckData;
import com.winterframework.firefrog.beginmession.entity.BeginSearchCheckData;
import com.winterframework.firefrog.beginmession.entity.CancelListData;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.BindCardStatus;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum.CancelReason;
import com.winterframework.firefrog.beginmession.service.BeginBankCardCheckService;
import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.beginmession.web.BeginBankCardCheckController;
import com.winterframework.firefrog.beginmession.web.dto.BeginCancelListDto;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.user.dao.impl.UserCustomerDaoImpl;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.PageRequest;

@Service
@Transactional
public class BeginBankCardCheckServiceImpl implements BeginBankCardCheckService{
	private static final Logger log = LoggerFactory.getLogger(BeginBankCardCheckController.class);
	
	@Autowired
	private BeginBankCardCheckDao beginBindCardCheckDao;
	
	@Autowired
	private BeginMissionDao beginMissionDao;
	
	@Autowired
	private UserCustomerDaoImpl userCustomerDao;
	
	@Autowired 
	private BeginMissionService beginMissionService;
 
	@Override
	public CountPage<BeginSearchCheckData> queryBankCardChecksByCondition(
			PageRequest<BeginCheckData> pageRequest) throws Exception {
		CountPage<BeginSearchCheckData> results = beginBindCardCheckDao.queryBankCardChecksByCondition(pageRequest);
		if(results.getResult() != null){
			for (BeginSearchCheckData info : results.getResult()) {
				Long totalBet = beginBindCardCheckDao.queryBetsTotalByCconditions(info.getUserId(), 
						pageRequest.getSearchDo().getTimeStart(),pageRequest.getSearchDo().getTimeEnd());
				info.setTotAmount(totalBet);
			}
		}
		return results;
	}
	
	@Override
	public void updateCheckStatus(Map<Long,String> userids,String checkUser) throws Exception{
		if(userids!=null && userids.size()>0){
			for(Entry<Long,String> map:userids.entrySet()){
				BeginBankCardCheck vo = beginBindCardCheckDao.queryByUserId(map.getKey());
				if(null != vo &&  0 == vo.getCheckStatus().intValue()) {
					Integer updateStatus = beginBindCardCheckDao.updateCheckStatus(map.getKey(), checkUser, map.getValue().equals("Y")?1L:2L);
					if(updateStatus > 0){
						beginMissionService.actionBindCheck(map.getKey(), checkUser, map.getValue().equals("Y")?BindCardStatus.PASS:BindCardStatus.UNPASS);
					}
				}else{
					log.warn("userId : " + map.getKey() +",审核状态不符!");
				}
			}
		}
	}
	
	@Override
	public List<String> batchUpdateCheckStatus(List<String> accounts, String checkUser,String status) throws Exception{
		List<String> nonAccounts = new ArrayList<String>();
		Map<Long,String> userids = new HashMap<Long,String>();
		
		for(String account:accounts){
			User user = userCustomerDao.queryUserByName(account);
			if(user != null){
				BeginBankCardCheck vo = beginBindCardCheckDao.queryByUserId(user.getId());
				
				if(null != vo &&  0 == vo.getCheckStatus().intValue()){
					userids.put(user.getId(),status);
				}else{
					nonAccounts.add(account);
				}
			} else {
				nonAccounts.add(account);
			}
		}
		if(userids!=null && userids.size()>0){
			for(Entry<Long,String> map:userids.entrySet()){
				
				Integer updateStatus = beginBindCardCheckDao.updateCheckStatus(map.getKey(), checkUser, map.getValue().equals("Y")?1L:2L);
				if(updateStatus > 0){
					beginMissionService.actionBindCheck(map.getKey(), checkUser, map.getValue().equals("Y")?BindCardStatus.PASS:BindCardStatus.UNPASS);
				}
			}
		}
		
		return nonAccounts;
	}
	
	@Override
	public List<BeginSearchCheckData> queryCheckDataAllByCondition(BeginCheckData req) throws Exception{
		return beginBindCardCheckDao.queryCheckDataAllByCondition(req);
	}
	
	@Override
	public CountPage<CancelListData> searchCancelList(PageRequest<BeginCancelListDto> pageRequest) throws Exception{
		return beginMissionDao.searchCancelList(pageRequest);
	}
	
	@Override
	public List<CancelListData> searchCancelListAll(BeginCancelListDto req) throws Exception{
		return beginMissionDao.searchCancelListAll(req);
	}
	
	@Override
	public Integer backendCancelMission(String cancelUser,String account,String reason) throws Exception{
		int result = 0;
		log.info("-----------backendCancelMission---------------");
		log.debug("cancelUser : " + cancelUser);
		log.debug("account : " + account);
		log.debug("reason : " + reason);
		User user = userCustomerDao.queryUserByName(account);
		log.debug("userId : " + user.getId());
		if(user != null){
			//檢查狀態
			BeginBankCardCheck vo = beginBindCardCheckDao.queryByUserId(user.getId());
			//狀態不做修改
			if(2 == vo.getCheckStatus().intValue()){
				result = -1;
				return result;//
			}
			BeginMission mission = beginMissionDao.findByUserId(user.getId());
			
			if(mission!=null){
				log.info("mission : " + mission.getUserId());
				beginBindCardCheckDao.updateCheckStatus(user.getId(), cancelUser, 2L);
				beginMissionDao.cancelMission(user.getId(), reason , cancelUser);
				result = 1;
			}else{
				result = 0;
			}
		}
		return result;
	}	
}
