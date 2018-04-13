package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.entity.GameLryl;

/** 
* @ClassName: IGameDrawLrylService 
* @Description: 冷热遗漏查询service接口 
* @author Denny 
* @date 2013-12-20 上午10:35:02 
*  
*/
public interface IGameDrawLrylService {

	/**
	 * 
	 * @param countIssue 
	 * @Title: queryColdAndHotNumbers 
	* @Description: 冷热号查询
	 */
	List<GameLryl> queryColdAndHotNumbers(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode, int countIssue) throws Exception;

	/**
	* @Title: queryMissingValue 
	* @Description: 遗漏值查询
	*/
	public List<GameLryl> queryMissingValue(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode, int queryType) throws Exception;
}
