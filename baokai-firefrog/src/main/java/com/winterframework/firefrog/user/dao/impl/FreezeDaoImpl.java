package com.winterframework.firefrog.user.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IFreezeDao;
import com.winterframework.firefrog.user.dao.vo.UserFreezeLog;
import com.winterframework.firefrog.user.entity.FreezeLog;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("freezeDaoImpl")
public class FreezeDaoImpl extends BaseIbatis3Dao<UserFreezeLog> implements IFreezeDao {

	@Override
	public void saveFreezeLog(FreezeLog freezeLog) throws Exception {
		UserFreezeLog ufl = freezeLogConverterUserFreeLog(freezeLog);
		insert(ufl);
		freezeLog.setUserFreeLog(ufl);
	}

	@Override
	public void saveUnFreezeLog(FreezeLog freezeLog) throws Exception {
		UserFreezeLog ufl = freezeLogConverterUserUnFreeLog(freezeLog);
		update(ufl);
	}

	@Override
	public void deleteByFreezeAccount(String freezeAccount) {
		sqlSessionTemplate.delete(this.getQueryPath("deleteByFreezeAccount"), freezeAccount);
	}
	
	public UserFreezeLog freezeLogConverterUserFreeLog(FreezeLog freezeLog) {

		UserFreezeLog userFreezeLog = new UserFreezeLog();
		userFreezeLog.setFrozen(freezeLog.getFrozenUser().getId());
		userFreezeLog.setActor(freezeLog.getActor().getId());
		userFreezeLog.setRange(freezeLog.getRange().getValue());
		userFreezeLog.setMemo(freezeLog.getReason());
		userFreezeLog.setMethod(freezeLog.getMethod());
		userFreezeLog.setAction(freezeLog.getAction().getValue());
		userFreezeLog.setFreezeDate(freezeLog.getTime());
		userFreezeLog.setUnfreezeDate(freezeLog.getUnfreezeDate());
		userFreezeLog.setFreezeAccount(freezeLog.getFreezeAccount());
		return userFreezeLog;
	}

	public UserFreezeLog freezeLogConverterUserUnFreeLog(FreezeLog freezeLog) {

		UserFreezeLog userFreezeLog = new UserFreezeLog();
		userFreezeLog.setId(freezeLog.getDbId());
		userFreezeLog.setUnfreezeDate(freezeLog.getUnfreezeDate());
		userFreezeLog.setUnfreezeActor(freezeLog.getUnfreezeActor().getId());
		userFreezeLog.setUnfreezeRange(freezeLog.getUnfreezeRange().getValue());
		userFreezeLog.setUnfreezeMemo(freezeLog.getUnfreezeMemo());
		userFreezeLog.setUnfreezeMethod(freezeLog.getUnfreezeMethod());
		userFreezeLog.setAction(freezeLog.getAction().getValue());
		userFreezeLog.setUnfreezeAccount(freezeLog.getUnfreezeAccount());

		return userFreezeLog;
	}

	@Override
	public void saveFreezeLogBatch(List<FreezeLog> freezeLogList) throws Exception {

		List<UserFreezeLog> list = new ArrayList<UserFreezeLog>();

		for (FreezeLog freezeLog : freezeLogList) {

			UserFreezeLog _userFreezeLog = freezeLogConverterUserFreeLog(freezeLog);
			list.add(_userFreezeLog);
			freezeLog.setUserFreeLog(_userFreezeLog);

		}

		insert(list);
	}

}
