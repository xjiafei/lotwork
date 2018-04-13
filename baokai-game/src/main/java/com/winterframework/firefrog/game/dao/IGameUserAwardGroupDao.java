package com.winterframework.firefrog.game.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameBetAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameUserAwardGroupDao extends BaseDao<GameUserAwardGroup> {

	/**
	 * 根据用户ID获取奖金组列表
	 * @param userid
	 * @return
	 */
	public List<GameUserAwardGroup> getGameUserAwardGroupByUserId(Long userid);

	/**
	 * 根据用户ID获取投注奖金组列表
	 * @param userid
	 * @return
	 */
	public List<GameBetAwardGroup> getGameBetAwardGroupByUserId(Long userid);

	/**
	 * 根据用户ID,彩种ID获取投注奖金组列表
	 * @param userid
	 * @param lotteryid
	 * @return
	 */
	public List<GameBetAwardGroup> getGameBetAwardGroupByUserId(Long userid, Long lotteryid);

	/**
	 * 更新用户奖金组 
	 * @param guag
	 */
	public void updateRet(GameUserAwardGroup guag);

	/**
	 * 获取用户返点信息
	 * @param userId
	 * @param lotteryId
	 * @return
	 */
	public UserAwardGroupEntity selectUserAwardGroup(Long userId, Long lotteryId);

	/**
	 * 根据用户ID和投注方式查询用户奖金组列表
	 * @param userid
	 * @param betType
	 * @return
	 */
	public List<GameUserAwardGroup> getGameUserAwardGroupByUserIdAndBetType(long userid, int betType);

	/**
	 * 按照彩系编码、USERid、奖金组名称查询奖金组 
	 * @param lotterySeriesCode
	 * @param userid
	 * @param awardName
	 * @return
	 */
	public List<GameUserAwardGroup> getAllByLotterySeriesCodeAndUserIdAndAwardName(Integer lotterySeriesCode,
			Long userid, String awardName);

	/** 
	 * 保存奖金组时的更新
	 * @param userAwardGroup 设定文件 
	 * @return  
	 */
	public void updateUserAwardGroup(GameUserAwardGroup userAwardGroup);

	/**
	 * 返回用户默认彩系的奖金组
	 * @param userid
	 * @param lotteryid
	 * @return
	 */
	public List<GameUserAwardGroup> getGameUserAwardGroupByUserIdAndLotteryId(long userid, String lotteryid);

	/** 
	 * 根据用户id，彩种id和系统奖金组获取用户奖金组
	 * @param userId
	 * @param lotteryId
	 * @param sysAwardGroupId
	 * @return
	 */
	public List<GameUserAwardGroup> getAwardByUserIdAndLryIdAndSysAwardId(List<Long> userId, Long lotteryId,
			Long sysAwardGroupId);

	/** 
	 * 获取指定玩法，彩种下的用户返点值的Map(直选)
	 * @param betTypeCode
	 * @param userAwardGroupId
	 * @param lotteryid
	 * @return
	 */
	public  Map<String, BigDecimal> getUserAwardGroupDirPoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/** 
	 * 获取指定玩法，彩种下的用户返点值的Map(三星不定位)
	 * @param betTypeCode
	 * @param userAwardGroupId
	 * @param lotteryid
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupThreeOnePoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(超級2000)
	 * @param sysAwardGroupId  
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupSuperPoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-特肖)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupYearPoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-兩面色波)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupColorPoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	public Map<String, BigDecimal> getUserAwardGroupSbThreeOnePoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-平碼)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupFlatcodePoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-半波)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupHalfwavePoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-一肖)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupOneyearPoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-不中)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupNotinPoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-連肖(中)二、三連肖返點)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupContinuein23Point(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-連肖(中)四連肖返點)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupContinuein4Point(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-連肖(中)五連肖返點)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupContinuein5Point(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-連肖(不中)二、三連肖返點)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupContinuenotin23Point(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-連肖(不中)四連肖返點)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupContinuenotin4Point(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-連肖(不中)五連肖返點)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupContinuenotin5Point(Long sysAwardGroupId, Long userId,
			Long lotteryid);
	/**
	 * 获取指定玩法，彩种下的用户返点值的Map(六合彩-連碼返點)
	 * @param sysAwardGroupId
	 * @param userId 用戶ID
	 * @param lotteryid 彩種ID
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardGroupContinuecodePoint(Long sysAwardGroupId, Long userId,
			Long lotteryid);

	/** 
	 * 获取当前彩种的用户奖金组信息
	 * @param userid
	 * @param lotteryid
	 * @return
	 */
	List<GameUserAwardGroup> getLotteryGameUserAwardGroupByUserIdAndLotteryId(Long userid, Long lotteryid);
	
	/** 
	 * 查詢用戶獎金組、總代返點
	 * @param userid
	 * @param lotteryid
	 * @return
	 */
	List<GameAwardGroup> queryUserAwardRet(Long userid, Long lotteryid);


	public GameUserAwardGroup getGameUserAwardGroupByGroupId(
			Long userAwardGroupId);

	public GameUserAwardGroup getUserAwardGroupByUserAndSysAwardId(Long id,
			Long userId);
	
	public GameUserAwardGroup getDirectRetValueRange(Long awardGroupId,Long userId) throws Exception;
	
	public GameUserAwardGroup getThreeoneRetValueRange(Long awardGroupId,Long userId) throws Exception;

	/**
	 * 获取老数据返点信息
	 * @param gameBetType
	 * @param userId
	 * @param lotteryid
	 * @return
	 */
	public Map<String, BigDecimal> getUserAwardPoint(String gameBetType,
			Long userId, Long lotteryid);

}
