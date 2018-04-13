package com.winterframework.firefrog.beginmession.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheck;
import com.winterframework.firefrog.beginmession.entity.BeginCheckData;
import com.winterframework.firefrog.beginmession.entity.BeginSearchCheckData;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginBankCardCheckDao extends BaseDao<BeginBankCardCheck>{

	public CountPage<BeginSearchCheckData> queryBankCardChecksByCondition(
			PageRequest<BeginCheckData> pageRequest) throws Exception;
	
	public Integer updateCheckStatus(Long userId, String checkUser, Long checkStatus);
	
	public List<BeginSearchCheckData> queryCheckDataAllByCondition(
			BeginCheckData req) throws Exception;
	
	public BeginBankCardCheck queryByUserId(Long userId) throws Exception;
	
	public Long queryBetsTotalByCconditions(Long userId,Date startTime, Date endTime);
}
