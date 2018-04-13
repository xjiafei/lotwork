package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSeriesCheck;

/** 
* @ClassName: IGameSeriesCheckDao 
* @Description: 销售状态审核DAO接口 
* @author Denny 
* @date 2013-8-25 下午10:13:18 
*  
*/
public interface IGameSeriesCheckDao {
	/**
	* @Title: getGameSeriesCheckByLotteryId 
	* @Description: 查询销售状态审核
	 */
	public GameSeriesCheck getGameSeriesCheckByLotteryId(long lotteryid, int checkType);

	/**
	* @Title: addGameSeriesCheck 
	* @Description: 添加销售状态审核
	 */
	public void addGameSeriesCheck(long lotteryid);

	/**
	* @Title: removeGameSeriesCheck 
	* @Description: 删除销售状态审核
	 */
	public void removeGameSeriesCheck(long id);

	/**
	* @Title: updateGameSeriesCheck 
	* @Description: 修改销售状态审核
	 */
	public void updateGameSeriesCheck(GameSeriesCheck GameSeriesCheck);
	
	public void insert(List<GameSeriesCheck> entitys);
	
	/**
	* @Title: getLotteryHelpDes 
	* @Description: 查询彩种开奖周期描述
	 */
	public GameSeriesCheck getLotteryHelpDes(long lotteryid);

	/**
	* @Title: getLotterySellingStatus 
	* @Description: 查询彩种销售状态
	 */
	public GameSeriesCheck getLotterySellingStatus(long lotteryid);

	/**
	* @Title: updateToPublish 
	* @Description: 更新状态
	 */
	public void updateToPublish(Long lotteryid, Long auditType);

	/**
	* @Title: getLotterySellingStatus 
	* @Description: 查询彩种审核状态
	 */
	public Integer getLotteryCheckStatus(long lotteryid);
	
	public void insertOne(GameSeriesCheck gsk);

	/** 
	* @Title: removeByLotteryId 
	* @Description: 按照彩种ID删除销售状态数据
	*/
	public void removeByLotteryId(Long lotteryid, Integer checkType);

	/** 
	* @Title: updateToCheck 
	* @Description: 更新状态为“待审核” 
	*/
	public void updateToCheck(GameSeriesCheck gsc);

	/** 
	* @Title: updateSeriesCheckToNotPublished 
	* @Description: 发布不通过SeriesCheck
	* @param lotteryid
	*/
	public void updateSeriesCheckToNotPublished(Long lotteryid);

    /**
     * 获取审核状态
     * @param lotteryId 彩种ID
     * @param type 审核类型1：玩法描述 2：销售状态 3：奖金组
     * @return
     */
    public Integer getStatus(Long lotteryId, Integer type) throws Exception;

    /**
     * 更新状态
     * @param lotteryId 彩种ID
     * @param checkStatus 状态
     * @param checkType 类型1：玩法描述 2：销售状态 3：奖金组
     * @throws Exception
     */
    public void updateStatus(Long lotteryId, Integer checkStatus, Integer checkType) throws Exception;
}
