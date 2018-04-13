package com.winterframework.firefrog.beginmession.dao;

import java.util.List;

import com.winterframework.firefrog.beginmession.dao.vo.BeginAwardLottery;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface BeginAwardLotteryDao extends BaseDao<BeginAwardLottery>{

	public List<BeginAwardLottery> findByUserId(Long userId);
	
	public List<BeginAwardLottery> findByCondition(BeginAwardLottery lottery);
	
	public void updateLotteryAwardStatus(BeginAwardLottery lottery);
}
