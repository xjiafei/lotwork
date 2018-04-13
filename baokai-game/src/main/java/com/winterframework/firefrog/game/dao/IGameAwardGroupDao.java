package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.entity.GameAwardEntity;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameAwardGroupDao 
* @Description: 奖金组DAO接口信息 
* @author Richard
* @date 2013-12-27 下午4:00:12 
*
 */
public interface IGameAwardGroupDao extends BaseDao<GameAwardGroup> {


	/**
	 * 
	* @Title: getMaxAwardMoney 
	* @Description: 根据LotteryId获取该彩种最高奖金组ID
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public Long getMaxAwardMoney(Long lotteryId) throws Exception;
	
	/**
	 * 查询奖金组 
	 * @param lotteryId 彩種ID(必填)
	 * @param status 狀態(選填)；1:進行中、2:已刪除
	 * @param awardId 奬金組ID(選填)
	 * @return
	 * @throws Exception
	 */
	public List<GameAwardGroupEntity> queryGameAwardGroup(Long lotteryId, Integer status, Long awardId) throws Exception;
	
	/**
	 * 
	* @Title: queryGameAwardGroupByLotteryId 
	* @Description: 根据LotteryID获取所有奖金组信息 
	* @param LotteryId
	* @return
	* @throws Exception
	 */
	public List<GameAwardGroupEntity> queryGameAwardGroupByLotteryId(Long LotteryId) throws Exception;
	
	/**
	 * 
	* @Title: saveOrUpdate 
	* @Description: 保存更新奖金组信息 
	* @param group
	* @return
	* @throws Exception
	 */
	public Long saveOrUpdate(GameAwardGroup group) throws Exception;
	
	/**
	 * 
	* @Title: queryGameAwardEntityByLotteryId 
	* @Description: 根据LotteryID获取最大奖金组信息 
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public List<GameAwardEntity> queryGameAwardEntityByLotteryId(Long lotteryId) throws Exception;
	
	/**
	 * 
	* @Title: queryAwardGroupIdByAwardGroupName 
	* @Description: 根据奖金组名称查询奖金组ID
	* @param awardName
	* @return
	* @throws Exception
	 */
	public Long queryAwardGroupIdByAwardGroupName(String awardName) throws Exception;

	/**
	 * 
	* @Title: queryUsedAwardGroupUserSum 
	* @Description: 查询该奖金组已分配的所有用户数 
	* @param lotterySeriesCode 彩系编码
	* @param awardName 奖金组名称
	* @param userid 代理用户ID
	* @return Integer  
	 */
	public Integer queryUsedAwardGroupUserSum(Long lotteryId,Long userid);

	/**
	 * 
	* @Title: queryGameGroupByLotteryIdAndName 
	* @Description: 根据彩种ID和奖金组名称查询奖金组 
	* @param lotteryId 
	* @param gameAwardName 
	* @throws Exception    设定文件 
	* @return GameAwardGroup    
	 */
	/**
	 * 
	* @Title: queryGameGroupByLotteryIdAndName 
	* @Description: 根据LotteryId和奖金组名称获取奖金组信息。 
	* @param lotteryId
	* @param gameAwardName
	* @return
	* @throws Exception
	 */
	public GameAwardGroup queryGameGroupByLotteryIdAndName(Long lotteryId, String gameAwardName) throws Exception;

	/** 
	* @Title: queryGameAwardGroupByUserId 
	* @Description: 根据用户ID查询用户奖金组列表 
	* @param userid
	* @return List<GameAwardGroupEntity>    
	*/
	public List<GameAwardGroupEntity> queryGameAwardGroupByUserId(long userid);
	
	/**
	 * 
	 * @param groupid
	 * @return
	 */
	public List<GameAwardGroupEntity> queryGameAwardGroupByGroupId(long groupid, long lotterySeriesCodes);

	public Long queryAwardCountByLotteryId(Long lotteryid) throws Exception;

}
