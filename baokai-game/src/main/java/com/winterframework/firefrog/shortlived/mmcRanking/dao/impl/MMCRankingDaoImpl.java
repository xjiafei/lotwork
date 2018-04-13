package com.winterframework.firefrog.shortlived.mmcRanking.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.vo.MMCRanking;
import com.winterframework.firefrog.shortlived.mmcRanking.dao.IMMCRankingDao;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName ActivitySheepWheelSurfDaoImpl 
* @Description 羊年活动转盘 
* @author  hugh
* @date 2015年1月14日 下午3:30:56 
*  
*/
@Repository("mmcRankingDaoImpl")
public class MMCRankingDaoImpl  extends BaseIbatis3Dao<MMCRanking> implements IMMCRankingDao{
	
	public List<MMCRanking> queryTop(String beginDate , String endDate,Long rank){
		Map<String, Object> param  = new HashMap<String, Object>();
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		
		if(rank != null){
			List<Long> rankList = new ArrayList<Long>();
			rankList.add(rank-1);
			rankList.add(rank);
			rankList.add(rank+1);
			param.put("rank", rankList);
		}
		
		return sqlSessionTemplate.selectList(this.getQueryPath("queryTop"),param);
	}
	
	
	public MMCRanking queryByAccount(String account,String beginDate , String endDate){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("account", account);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		return sqlSessionTemplate.selectOne("queryByAccount",param);
		
	}
	
	public List<MMCRanking> queryHistory(){
		return sqlSessionTemplate.selectList(this.getQueryPath("queryHistory"));
	}
	
	
	
}
