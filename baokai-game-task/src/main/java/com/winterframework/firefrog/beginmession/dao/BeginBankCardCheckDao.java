package com.winterframework.firefrog.beginmession.dao;

import com.winterframework.firefrog.beginmession.dao.vo.BeginBankCardCheck;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginBankCardCheckDao extends BaseDao<BeginBankCardCheck>{
	public Integer updateCheckStatus(Long userId, String checkUser, Long checkStatus);
	
	Long getByUserIdCount(Long userId);
}
