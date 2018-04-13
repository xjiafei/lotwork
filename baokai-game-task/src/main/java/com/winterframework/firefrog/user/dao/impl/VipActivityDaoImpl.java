package com.winterframework.firefrog.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IVipActivityDao;
import com.winterframework.firefrog.user.dao.vo.VipActivityVo;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/**
 * @Description: UserMessage的DAO层
 * @author Denny
 * @date 2013-4-22 下午3:49:36
 * 
 */
@Repository("vipactivtyDao")
public class VipActivityDaoImpl extends BaseIbatis3Dao<VipActivityVo> implements IVipActivityDao {

	/**
	 * 新增一条回复消息
	 * 
	 * @param messageReply
	 * 
	 * @return
	 */
	public void insertMessageReply(VipActivityVo activityVo){
		insert(activityVo);

	}

	@Override
	public VipActivityVo queryByID(Long id) {
		// TODO Auto-generated method stub
		VipActivityVo vo = this.getById(id);
		return vo;
	}

	public List<VipActivityVo> queryByActivityVo(VipActivityVo vo)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", vo.getAccount());
		map.put("month", vo.getMonth());
		map.put("startTime", vo.getStartTime());
		map.put("endTime", vo.getEndTime());
		return sqlSessionTemplate.selectList(this.getQueryPath("queryByActivityVo"), map);
	}
	

	

}
