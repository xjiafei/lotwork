package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.web.dto.GamePlanBetOriginDataStruc;
import com.winterframework.firefrog.game.entity.GamePackage;
/**
 * 
* @ClassName: IGamePackageService 
* @Description: 方案接口方法
* @author Richard
* @date 2013-7-22 下午1:42:07 
*
 */
public interface IGamePackageService {

	com.winterframework.firefrog.game.dao.vo.GamePackage getById(Long id) throws Exception;
	/**
	 * 
	* @Title: saveGamePackage 
	* @Description: 生成方案信息及方案明细信息 保存追号原始信息数据到packageitem
	* @param order
	 * @param gamePlanBetOriginDataStruc 
	* @throws Exception
	 */
	public GamePackage saveGamePackage(GameOrder order, List<GamePlanBetOriginDataStruc> gamePlanBetOriginDataStruc) throws Exception;
	
	/**
	 * 
	* @Title: cancelGamePackage 
	* @Description: 撤销本期方案
	* @param lotteryid
	* @param issueCode
	 * @param warnIssueLog 
	* @throws Exception
	 */
	public void cancelGamePackage(Long lotteryid, Long issueCode, GameWarnIssueLog warnIssueLog) throws Exception;
	

}
