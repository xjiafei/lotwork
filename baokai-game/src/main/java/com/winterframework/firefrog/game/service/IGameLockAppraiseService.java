/**   
* @Title: IGameLockAppraiseService.java 
* @Package com.winterframework.firefrog.game.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-5 下午5:17:44 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameLockAppraise;

/** 
* @ClassName: IGameLockAppraiseService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-5-5 下午5:17:44 
*  
*/
public interface IGameLockAppraiseService {

	public List<GameLockAppraise> queryAllGameLockAppraise(Long lotteryId) throws Exception;

	public int updateGameLockAppraise(GameLockAppraise gameLockAppraise) throws Exception;

	public int updateStatus(GameLockAppraise gameLockAppraise) throws Exception;

	public int deleteGameLockAppraise(String ids) throws Exception;

	public int addGameLockAppraise(GameLockAppraise gameLockAppraise) throws Exception;

	public GameLockAppraise queryGameLockAppraise(Long id) throws Exception;
	
	public void updateCurUser(Long lotterId) throws Exception;
}
