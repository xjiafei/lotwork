package com.winterframework.firefrog.shortlived.mmcRanking.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.MMCRanking;
import com.winterframework.firefrog.game.dao.vo.MMCRankingLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IActivitySheepWheelSurfDao 
* @Description 羊年活动转运盘 
* @author  hugh
* @date 2015年1月14日 下午3:29:21 
*  
*/
public interface IMMCRankingLogDao extends BaseDao<MMCRankingLog>{
	
	public List<MMCRanking> queryTop(String beginDate , String endDate,Long rank);
	
	
	public MMCRanking queryByAccount(String account,String beginDate , String endDate);
	
	public List<MMCRanking> queryHistory(); 
	
}
