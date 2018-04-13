package com.winterframework.firefrog.game.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameUserAwardGroupDao extends BaseDao<GameUserAwardGroup> {

	UserAwardGroupEntity selectUserAwardGroup(Long userid, Long lotteryid);

	List<GameUserAwardGroup> getAwardByUserIdAndLryIdAndSysAwardId(List<Long> userIds, Long lotteryId,
			Long sysAwardGroupId);

	/** 
	* @Title: getUserPoint 
	* @Description: 获取指定玩法，彩种下的用户返点值的Map(直选)
	* @param betTypeCode
	* @param userAwardGroupId
	* @param lotteryid
	* @return
	*/
	public List<Map<String, BigDecimal>> getUserAwardGroupDirPoint(Long sysAwardGroupId, List<Long> userAwardGroupIds,
			Long lotteryid);
	/** 
	* @Title: getUserPoint 
	* @Description: 获取指定玩法，彩种下的用户返点值的Map(三星不定位)
	* @param betTypeCode
	* @param userAwardGroupId
	* @param lotteryid
	* @return
	*/
	public List<Map<String, BigDecimal>> getUserAwardGroupThreeOnePoint(Long sysAwardGroupId, List<Long> userAwardGroupIds,
			Long lotteryid);
	
	/** 
	* @Title: getUserPoint 
	* @Description: 获取指定玩法，彩种下的用户返点值的Map(骰宝不定位)
	* @param betTypeCode
	* @param userAwardGroupId
	* @param lotteryid
	* @return
	*/
	public List<Map<String, BigDecimal>> getUserAwardGroupSbThreeOnePoint(Long sysAwardGroupId, List<Long> userAwardGroupIds,
			Long lotteryid);
	
	public Map<String, BigDecimal> getUserAwardGroupSuperPoint(Long sysAwardGroupId, Long userId,Long lotteryid); 
			
	/**
	 * 获取用户奖金组（根据彩种和用户）
	 * @param lottertId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<GameUserAwardGroup> getUserAwardGroupByLotteryIdAndUserId(Long lotteryId,Long userId) throws Exception;

	Map<String, BigDecimal> getUserAwardPoint(String gameBetType, Long userId,
			Long lotteryid);

	Map<String, BigDecimal> getUserAwardGroupThreeOnePoint(
			Long sysGroupAwardId, Long userId, Long lotteryid);

	Map<String, BigDecimal> getUserAwardGroupDirPoint(Long sysGroupAwardId,
			Long userId, Long lotteryid);
	
	Map<String, BigDecimal> getUserAwardGroupSbThreeOnePoint(Long sysGroupAwardId,
			Long userId, Long lotteryid);

}
