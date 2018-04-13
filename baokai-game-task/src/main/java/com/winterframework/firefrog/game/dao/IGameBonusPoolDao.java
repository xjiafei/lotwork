/**   
* @Title: IGameBonusPoolDao.java 
* @Package com.winterframework.firefrog.game.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-7-29 下午2:56:12 
* @version V1.0   
*/
package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameBonusPool;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGameBonusPoolDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-7-29 下午2:56:12 
*  
*/
public interface IGameBonusPoolDao extends BaseDao<GameBonusPool> {

	public GameBonusPool queryGameBonus(Long lotteryId) throws Exception;
}
