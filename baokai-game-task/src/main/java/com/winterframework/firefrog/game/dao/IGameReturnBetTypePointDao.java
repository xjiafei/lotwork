package com.winterframework.firefrog.game.dao;

import java.util.List;
import com.winterframework.firefrog.game.dao.vo.GameRetBetTypePoint;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
 * @ClassName: IGameReturnBetTypePointDao 
 * @Description: 每一注單的返點資料
 * @author David.Wu
 * @date 2016-4-26 下午04:44:48 
 */
public interface IGameReturnBetTypePointDao extends	BaseDao<GameRetBetTypePoint> {
	

	/**
	 * 紀錄注單返點
	 * @param gameRetBetTypePoint
	 * @return 
	 * @throws Exception
	 */
	void saveGameRetBetTypePoint(GameRetBetTypePoint gameRetBetTypePoint) throws Exception;

}
