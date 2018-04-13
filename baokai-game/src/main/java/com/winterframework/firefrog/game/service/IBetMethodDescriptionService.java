package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.web.dto.BetMethodDescriptionJoinBonus;

/** 
* @ClassName: IBetMethodDescriptionService 
* @Description: 玩法描述Service接口 
* @author Denny 
* @date 2013-8-20 下午2:11:03 
*  
*/
public interface IBetMethodDescriptionService {
	
	/**
	* @Title: queryBetMethodDescription 
	* @Description: 查询投注方式描述
	 */
	public List<BetMethodDescription> queryBetMethodDescription(long lotteryid) throws Exception;
	
	/**
	* @Title: queryLotteryHelpDes 
	* @Description: 查询彩种开奖周期描述
	 */
	public String queryLotteryHelpDes(Long lotteryid);

    /**
     * 玩法描述审核状态
     * @param lotteryid
     * @return
     */
    public Integer queryLotteryHelpCheckStatus(Long lotteryid);
	/**
	* @Title: queryLotteryHelpDes 
	* @Description: 查询彩种开奖周期描述对比数据
	 */
	public String queryLotteryHeloDesBak(Long lotteryid);
	
	/**
	* @Title: modifyBetMethodDescription 
	* @Description: 修改玩法描述
	 */
	public void modifyBetMethodDescription(List<BetMethodDescription> betMethodDescriptionList, String lotteryHelpDes, long lotteryid) throws Exception;
	
	/**
	* @Title: checkBetMethodDescription 
	* @Description: 审核玩法描述
	 */
	public void checkBetMethodDescription(Long lotteryid, Long auditType) throws Exception;

    /**
     * @Title 发布玩法描述
     * @param lotteryid
     * @param auditType
     * @throws Exception
     */
    public void publishBetMethodDescription(Long lotteryid, Long auditType) throws Exception;
	/** 
	* @Title: queryDescByBetMethod 
	* @Description: 根据投注方式查询玩法描述 
	*/
	public BetMethodDescription queryDescByBetMethod(long lotteryid, int gameGroupCode, int gameSetCode, int betMethodCode);
	
	/** 
	* @Title: queryDescByBetMethod
	* @Description: 根据投注方式查询玩法描述 
	*/
	public BetMethodDescriptionJoinBonus queryDescByBetMethod(long lotteryid, long userid,int gameGroupCode, int gameSetCode, int betMethodCode) throws Exception;

	/** 
	* @Title: getAllBettypeStatus 
	* @Description: 获取所有玩法信息
	* @return
	*/
	public List<GameBettypeStatus> getAllBettypeStatus();
}
