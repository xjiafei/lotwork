package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameSlipAssistDao;
import com.winterframework.firefrog.game.dao.vo.GameSlipAssist;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 注单辅助DAO实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
@Repository("gameSlipAssistDaoImpl")
public class GameSlipAssistDaoImpl extends BaseIbatis3Dao<GameSlipAssist> implements IGameSlipAssistDao { 
	@Override
	public List<GameSlipAssist> getBySlipId(Long slipId) throws Exception {
		return this.sqlSessionTemplate.selectList("getBySlipId", slipId);
	}
}
