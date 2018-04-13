package com.winterframework.firefrog.active.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
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
	
	/**
	 * 取得活動設定，多條件查詢
	 * @param actCfgVO
	 * @return
	 */
	public ActivityConfig getActCfg(ActivityConfig actCfgVO);
	
	/**
	 * 取得活動設定，多條件查詢
	 * @param actCfgVO
	 * @return
	 */
	public List<ActivityConfig> getActCfgByCondition(ActivityConfig actCfgVO);

	public String getMaxDrawType(Long id, Long betAmount);
	
	public ActivityConfig getActivityConfigByTime(Date now);
}

