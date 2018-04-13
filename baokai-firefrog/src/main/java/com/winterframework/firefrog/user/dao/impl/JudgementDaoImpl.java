package com.winterframework.firefrog.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IJudgementDao;
import com.winterframework.firefrog.user.dao.vo.Judgement;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("judgementDaoImpl")
public class JudgementDaoImpl extends BaseIbatis3Dao<Judgement> implements IJudgementDao {

	@Override
	public void initAction(Judgement judgement) {
		insert(judgement);

	}

	@Override
	public void updateAction(Judgement judgement) {

		sqlSessionTemplate.update("updateJudgement", judgement);

	}

	@Override
	public Judgement getAction(Judgement judgement) {
		return sqlSessionTemplate.selectOne("getJudgement", judgement);

	}

}
