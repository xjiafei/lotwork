package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePoint;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGamePointDao 
* @Description: 变价point
* @author 你的名字 
* @date 2014-6-16 下午7:24:48 
*  
*/
public interface IGamePointDao  extends BaseDao<GamePoint> {
	/** 
	* @Title: getGamePointsBySlipId 
	* @Description: 根据slipid获取变价列表
	* @param slipId
	* @return
	*/
	List<GamePoint> getGamePointsBySlipId(Long slipId,List<String> points);

	/** 
	* @Title: saveGamePoints 
	* @Description: 保存
	* @param pointsList
	* @throws Exception
	*/
	void saveGamePoints(List<GamePoint> pointsList) throws Exception;
	
	/** 
	* @Title: insertGamePointSlipId 
	* @Description: 保存追号单的slipId
	* @param planDetailId,slipId
	* @throws Exception
	*/
	public void insertGamePointSlipId(Long planDetailId,Long pkgDetailId,Long slipId,String betDetail) throws Exception;
}
