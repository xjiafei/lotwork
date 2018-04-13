/**   
* @Title: IGameLockAppraiseDao.java 
* @Package com.winterframework.firefrog.game.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-5 下午5:04:41 
* @version V1.0   
*/
package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameLockAppraise;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IGameLockAppraiseDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-5-5 下午5:04:41 
*  
*/
public interface IGameLockAppraiseDao extends BaseDao<GameLockAppraise> {

	public List<GameLockAppraise> queryAllGameLockAppraise(Long lotteryId) throws Exception;

	public int deleteGameLockAppraise(List<Long> ids) throws Exception;

	public int updateStatus(GameLockAppraise gameLockAppraise) throws Exception;

	public GameLockAppraise queryCurrUseGameLockAppraise(Long lotteryId) throws Exception;
	
	public void updateCurUser(Long lotterId) throws Exception;
}
