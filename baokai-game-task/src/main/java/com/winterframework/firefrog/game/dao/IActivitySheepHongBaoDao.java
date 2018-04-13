package com.winterframework.firefrog.game.dao;

import java.util.Date;

import com.winterframework.firefrog.shortlived.sheepactivity.dao.vo.ActivitySheepHongBao;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IActivitySheepHongBaoDao 
* @Description 羊年活动红包 
* @author  hugh
* @date 2015年1月14日 下午3:28:47 
*  
*/
public interface IActivitySheepHongBaoDao extends BaseDao<ActivitySheepHongBao>{
	Long getUncheckNum();
	Long getUserLastDateBet(Long userId ,Date sighTime);
}
