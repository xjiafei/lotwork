package com.winterframework.firefrog.shortlived.sheepactivity.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.IActivitySheepBigLittleDao;
import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName ActivitySheepBigLittleDaoImpl 
* @Description 羊年活动猜大小 
* @author  hugh
* @date 2015年1月14日 下午3:30:56 
*  
*/
@Repository("activitySheepBigLittleDaoImpl")
public class ActivitySheepBigLittleDaoImpl  extends BaseIbatis3Dao<ActivitySheepBigLittle> implements IActivitySheepBigLittleDao{
	public Long getUncheckNum(){
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUncheckNum"));
	}

	@Override
	public ActivitySheepBigLittle getUserDice(Long userId) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getUserDice"),userId);
	}
	public void  reduceTime(ActivitySheepBigLittle userId) throws Exception{
		if(this.sqlSessionTemplate.update(this.getQueryPath("reduceTime"),userId)<1){
			throw new Exception("");
		}
	}

	@Override
	public void addUserDiceLastNum(Long userId, Long times,Long amount) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("times", times);
		map.put("amount", amount);
		this.sqlSessionTemplate.update(this.getQueryPath("addUserDiceLastNum"),map);
	}
}
