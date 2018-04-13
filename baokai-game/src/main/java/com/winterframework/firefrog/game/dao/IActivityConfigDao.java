package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.ActivityConfig;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IActivityConfigDao 
* @Description TODO 
* @author  Ray
* @date 2015年8月19日 下午3:28:29 
*  
*/
public interface IActivityConfigDao extends BaseDao<ActivityConfig>{
	/** 
	* @Title: getActCfgByid 
	* @Description: 根据活動id获取对应的MODEL
	* @param act_id
	* @return 
	*/
	public ActivityConfig getActCfgById(Long act_id);
}

