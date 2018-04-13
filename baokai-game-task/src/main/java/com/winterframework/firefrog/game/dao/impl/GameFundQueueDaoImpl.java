package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameFundQueueDao;
import com.winterframework.firefrog.game.dao.vo.GameFundQueue;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;
 

/**
 * 资金请求队列DAO实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年12月15日
 */
@Repository("gameFundQueueDaoImpl")
public class GameFundQueueDaoImpl extends BaseIbatis3Dao<GameFundQueue> implements IGameFundQueueDao { 
	@Override
	public List<GameFundQueue> getByStatus(Integer status) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status); 
		return this.sqlSessionTemplate.selectList(getQueryPath("getByStatus"),map);
	}
	public java.util.List<String> getOrderCodeListByStatus(GameContext ctx,Integer status) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status); 
		return this.sqlSessionTemplate.selectList(getQueryPath("getOrderCodeListByStatus"),map);
	};
	@Override
	public List<GameFundQueue> getByOrderCodeAndStatus(GameContext ctx, String orderCode,Integer status)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderCode", orderCode);
		map.put("status", status);
		return this.sqlSessionTemplate.selectList(getQueryPath("getByOrderCodeAndStatus"),map);
	}
	@Override
	public List<GameFundQueue> getByOrderCodeListAndStatus(GameContext ctx, List<String> orderCodeList,Integer status)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderCodeList", orderCodeList);
		map.put("status", status);
		return this.sqlSessionTemplate.selectList(getQueryPath("getByOrderCodeListAndStatus"),map);
	}
	@Override
	public List<GameFundQueue> getByStatusAndRowCount(Integer status,
			Integer rowCount) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("rowCount", rowCount);
		return this.sqlSessionTemplate.selectList(getQueryPath("getByStatusAndRowCount"),map);
	}
}
