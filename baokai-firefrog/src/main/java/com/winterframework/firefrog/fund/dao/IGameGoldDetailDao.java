package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.GameGoldDetailVO;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName IGameGoldDetailDao 
* @Description 游戏币明细  
* @author  hugh
* @date 2014年9月22日 下午3:44:10 
*  
*/
public interface IGameGoldDetailDao  extends BaseDao<GameGoldDetailVO>{
	
	/** 
	* @Title: getGameGoldDetails 
	* @Description: 获取游戏币明细
	* @param vo
	* @return
	*/
	List<GameGoldDetailVO> getGameGoldDetails(GameGoldDetailVO vo);
}
