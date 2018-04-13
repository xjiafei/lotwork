package com.winterframework.firefrog.game.dao;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameMultipleCheck;

/** 
* @ClassName: IGameMultipleCheckDao 
* @Description: 投注限制审核DAO接口 
* @author Denny 
* @date 2013-8-25 下午10:13:18 
*  
*/
public interface IGameMultipleCheckDao {
	
	/**
	* @Title: getGameMultipleCheck 
	* @Description: 根据彩种查询投注限制审核
	 */
	public List<GameMultipleCheck> getGameMultipleCheckByLotteryId(long lotteryid);
	
	/**
	 * 
	* @Title: getGameMultipleCheckByCondition 
	* @Description: 根据条件查询投注限制审核
	* @param map 查询条件map
	* @return GameMultipleCheck
	 */
	public GameMultipleCheck getGameMultipleCheckByCondition(Map<String, Object> map);

	/**
	* @Title: addGameMultipleCheck 
	* @Description: 添加投注限制审核
	 */
	public void addGameMultipleCheck(GameMultipleCheck gameMultipleCheck);

	/**
	* @Title: removeGameMultipleCheck 
	* @Description: 根据彩种删除投注限制审核
	 */
	public void removeGameMultipleCheckByLotteryId(long lotteryid);

	/**
	 * 
	* @Title: updateGameMultipleCheck 
	* @Description: 更新GameMultipleCheck 
	* @param GameMultipleCheck    设定文件 
	* @throws
	 */
	public void updateGameMultipleCheck(GameMultipleCheck GameMultipleCheck);
	
	/**
	* @Title: updateGameMultipleCheckToPublish 
	* @Description: 审核投注限制修改记录
	 */
	public void checkGameMultipleCheck(Long lotteryid, Long auditType);
	
	public void publishGameMultipleCheck(Long lotteryid, Long publishType);
	
	public void insert(List<GameMultipleCheck> entitys);
}
