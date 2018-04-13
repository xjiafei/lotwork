package com.winterframework.firefrog.game.dao;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameUserAwardDao 
* @Description: 用户奖金组DAO 
* @author Alan
* @date 2013-10-10 上午10:23:05 
*
 */
public interface IGameUserAwardDao extends BaseDao<GameUserAward> {
	/**
	 * 查询用户奖金
	 * @param lotteryId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<GameUserAward> getByLotteryIdAndUserId(Long lotteryId,long userId) throws Exception;
	List<GameUserAward> getByLotteryIdAndUserIdAndAwardGroupId(Long lotteryId,Long userId,Long userAwardGroupId) throws Exception;
	
	/**
	 * 查询（根据用户彩种玩法）
	 * @param lotteryId
	 * @param userId
	 * @param betTypeCode
	 * @return
	 * @throws Exception
	 */
	GameUserAward getByLotteryIdAndUserIdAndBetTypeCode(Long lotteryId,long userId,String betTypeCode) throws Exception;
	/**
	 * 
	* @Title: selectActualBoundsByCondition 
	* @Description: 根据玩法群、组、玩法查询用户奖金组对应的金额
	* @param map
	* @return
	* @throws Exception
	 */
	public Long selectActualBoundsByCondition(Map<String, Object> map) throws Exception;

	Long getUserAward(Long lotteryid, Integer gameGroupCode, Integer gameSetCode, Long betMethodCode, Long userid); 	
	
	/** 
	* @Title: getByGroupIdAndUserId 
	* @Description: 根据奖金组ID和用户ID查询用户奖金组列表 
	* @param userAwardGroupId
	* @param userid
	* @return List<GameUserAward>    返回类型 
	* @throws 
	*/
	public List<GameUserAward> getByGroupIdAndUserId(Long userAwardGroupId, Long userid);

	/** 
	* @Title: isUserAwardExist 
	* @Description: 查询奖金组是否存在 
	* @param longValue
	* @param intValue
	* @param intValue2
	* @param betMethodCode
	*@param userAwardGroupId
	* @param userid
	* @return    设定文件 
	* @throws 
	*/
	public boolean isUserAwardExist(Long longValue, Integer intValue, Integer intValue2, Long betMethodCode,
			Long userAwardGroupId, Long userid);

	/** 
	* @Title: updateUserAward 
	* @Description: 保存奖金组时更新
	* @param gua    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void updateUserAward(GameUserAward gua); 
	
	
	/**
	 * 根据奖金组id和用户id更新奖金状态
	 * @param userAwardGroupId
	 * @param userid
	 */
	public void updateUserAwardByAwardGroupID(Long userAwardGroupId, Long userid,int status);
	
	/**
	 * 更新--按用户彩种玩法
	 * @param userAward
	 * @return
	 * @throws Exception
	 */
	int updateByUserIdLotteryIdBetTypeCode(GameUserAward userAward) throws Exception;
}
