package com.winterframework.firefrog.subsys.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.subsys.dao.ISubSystemDao;
import com.winterframework.firefrog.subsys.vo.SubSystem;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("SubSystemDaoImpl")
public class SubSystemDaoImpl extends BaseIbatis3Dao<SubSystem> implements ISubSystemDao {


	@Override
	public void updateSubSystem (SubSystem system) {

		sqlSessionTemplate.update("updateJudgement", system);

	}

	@Override
	public SubSystem getName (SubSystem system) {
		return sqlSessionTemplate.selectOne("getBytoken", system);

	}

}
