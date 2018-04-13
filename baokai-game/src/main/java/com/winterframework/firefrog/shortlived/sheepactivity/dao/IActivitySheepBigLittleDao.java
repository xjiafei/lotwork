package com.winterframework.firefrog.shortlived.sheepactivity.dao;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepBigLittle;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IActivitySheepBigLittleDao 
* @Description 羊年活动猜大小 
* @author  hugh
* @date 2015年1月14日 下午3:28:13 
*  
*/
public interface IActivitySheepBigLittleDao extends BaseDao<ActivitySheepBigLittle>{
	Long getUncheckNum();

	ActivitySheepBigLittle getUserDice(Long userId);

	void addUserDiceLastNum(Long userId, Long times, Long amount);
	void  reduceTime(ActivitySheepBigLittle userId) throws Exception;
}
