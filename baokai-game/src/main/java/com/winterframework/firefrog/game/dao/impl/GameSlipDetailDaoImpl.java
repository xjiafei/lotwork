package com.winterframework.firefrog.game.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameSlipDetailDao;
import com.winterframework.firefrog.game.dao.vo.GameSlipDetail;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 注单明细DAO实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月12日
 */
@Repository("gameSlipDetailDaoImpl")
public class GameSlipDetailDaoImpl extends BaseIbatis3Dao<GameSlipDetail> implements IGameSlipDetailDao { 
	@Override
	public List<GameSlipDetail> getByParentId(Long parentId) throws Exception {
		return this.sqlSessionTemplate.selectList("getByParentId", parentId);
	}
}
