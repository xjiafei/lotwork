/**   
* @Title: IGameLockParamDao.java 
* @Package com.winterframework.firefrog.game.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-5 下午5:03:46 
* @version V1.0   
*/
package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.GameLockParam;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGameLockParamDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-5-5 下午5:03:46 
*  
*/
public interface IGameLockParamDao extends BaseDao<GameLockParam>{

	public GameLockParam queryGameLockParam(Long gameId) throws Exception;
}
