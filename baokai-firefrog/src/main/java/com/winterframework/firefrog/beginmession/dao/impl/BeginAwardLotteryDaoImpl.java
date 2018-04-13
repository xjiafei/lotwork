package com.winterframework.firefrog.beginmession.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.beginmession.dao.BeginAwardLotteryDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLottery;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * 
 * @author Ami.Tsai
 *
 */
@Repository
public class BeginAwardLotteryDaoImpl extends BaseIbatis3Dao<BeginAwardLottery> implements
		BeginAwardLotteryDao {

	@Override
	public List<BeginAwardLottery> findByUserId(Long userId) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("findByUserId"), userId);
	}

	@Override
	public List<BeginAwardLottery>
	findByCondition(BeginAwardLottery lottery) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("findByCondition"), lottery);
	}

	@Override
	public void updateLotteryAwardStatus(BeginAwardLottery lottery) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("id", lottery.getId());
		map.put("status", lottery.getStatus());
		map.put("awardTime", lottery.getAwardTime());		
		this.sqlSessionTemplate.update(this.getQueryPath("updateLotteryAwardStatus"), map);
	}

}
