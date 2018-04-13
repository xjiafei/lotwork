package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.firefrog.game.entity.UserBetAwardGroupEntity;
import com.winterframework.firefrog.game.enums.GameAwardBetType;

/** 
* @ClassName: IGameUserAwardGroupService 
* @Description: 用户奖金组Service 
* @author Denny 
* @date 2013-9-17 下午5:01:59 
*  
*/
public interface IGameUserAwardGroupService {

	/** 
	 * @throws Exception 
	 * @Title: queryGameUserAwardGroup 
	 * @Description:  查询用户奖金组
	 */
	public List<UserAwardGroupEntity> queryGameUserAwardGroup(long userid) throws Exception;
	
	/** 
	 * @throws Exception 
	 * @Title: queryGameUserAwardGroup 
	 * @Description:  查询用户奖金组
	 */
	public List<UserAwardGroupEntity> queryGameUserAwardGroupByUserIdAndBetType(long userid, int awardType) throws Exception;
	
	/** 
	 * @throws Exception 
	 * @Title: queryGameBetAwardGroup 
	 * @Description:  查询用户投注奖金组
	 */
	public List<UserBetAwardGroupEntity> queryGameBetAwardGroup(long userid) throws Exception;
	
	/**
	 * 当用户无投注奖金组,查询奖金组列表
	 * @param userid
	 * @param lotteryid
	 * @return
	 * @throws Exception
	 */
	public List<UserBetAwardGroupEntity> queryGameBetAwardGroup(long userid,long lotteryid) throws Exception ;

	/** 
	 * @Title: modifyGameUserAwardGroup 
	 * @Description: 修改用户奖金组 
	 */
	public void modifyGameUserAwardGroup(UserAwardGroupEntity e);

	/** 
	 * @Title: batchModifyGameUserAwardGroup 
	 * @Description: 批量修改用户奖金组及返点
	 */
	public void batchModifyGameUserAwardGroup(List<UserAwardGroupEntity> userAwardGroupList) throws Exception;
	
	/**
	 * @Title: selectActualBoundsByCodes 
	 * @Description: 根据彩种、玩法群、组、玩法查询对应的奖金组金额 
	 */
	public Long selectActualBoundsByCodes(Integer lotteryid,Long userId, Long gameGroupCode, 
			Long gameSetCode, Long betMethodCode) throws Exception;
	
	

	/**
	 * @param lotteryid 
	 * @param userid  
	 * @Title: modifyGameUserAwardGroupById 
	 * @Description: 根据奖金组ID修改投注属性 
	 * @param userAwardGroupId    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	public void modifyGameUserAwardGroupById(Long userAwardGroupId, Long userid, Long lotteryid) throws Exception;

	
	/**
	 * @throws Exception  
	 * @Title: openAccountAssignAwardGroup 
	 * @Description: 开户分配奖金组
	 * @param userAwardGroupList
	 * @param userid    设定文件 
	 * @throws 
	 */
	public void openAccountAssignAwardGroup(List<UserAwardGroupEntity> userAwardGroupList, Long currentUserId) throws Exception;
	
	
	/**
	 *  开户分配奖金组
	 * @param userAwardGroupList
	 * @param currentUserId	用户id 
	 * @param userLvl		用户级别
	 * @throws Exception
	 */ 
	public void openAccountAssignAwardGroup(List<UserAwardGroupEntity> userAwardGroupList, Long currentUserId ,Long userLvl) throws Exception ;
	
	/**
	 * @throws Exception  
	 * @Title: queryLotteryGameAwardsByUser 
	 * @Description: 查询指定用户的彩种奖金
	 * @param GameAwardList
	 * @param lotteryId    lotteryId
	 * @param userId   userId
	 * @throws 
	 */
	public List<GameAward> queryLotteryGameAwardsByUser(Long lotteryId,Long userId) throws Exception;

	/** 
	 * @Title: queryLotteryGameUserAwardGroupByLotteryIdAndUserId 
	 * @Description:获取彩种用户奖金组信息
	 * @param userId
	 * @param lotteryId
	 * @return
	 */
	List<GameUserAwardGroup> queryLotteryGameUserAwardGroupByLotteryIdAndUserId(Long userId, Long lotteryId);
	
	/** 
	 * @Title: queryUserAwardRet 
	 * @Description:查詢用戶獎金組、總代返點
	 * @param userId
	 * @param lotteryId
	 * @return
	 */
	List<GameAwardGroup> queryUserAwardRet(Long userId, Long lotteryId);

	/**
	 * 修改用户奖金组信息。
	 * @param userAwardGroupList
	 */
	public void chageGameUserAwardGroup(List<UserAwardGroupEntity> userAwardGroupList, Long userId) throws Exception;
	
	/**
	 * 获取奖金组玩法类型
	 * @param betType	玩法类型
	 * @return
	 * @throws Exception
	 */
	GameAwardBetType getGameAwardBetType(GameBetType betType) throws Exception;

	/**
	 * @param lotteryid 
	 * @param userid  
	 * @Title: updateGameUserAwardGroupById 
	 * @Description: 根据奖金组ID修改投注属性 
	 * @param userAwardGroupId    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 */
	public void updateGameUserAwardGroupById(Long userAwardGroupId, Long userid, Long lotteryid) throws Exception;
	
	Long getRetPointByUserIdAndLotteryIdAndBetTypeCode(Long userId,Long lotteryId,String betTypeCode) throws Exception;
	GameUserAwardGroup getBetedByUserIdAndLotteryId(Long userId,Long lotteryId) throws Exception;
	
	/**
	 * 取得六合彩用戶於不同玩法時的返點。
	 * @param userId 用戶ID
	 * @param lotteryId 彩種ID
	 * @param betTypeCode 投注方式代碼
	 * @param odds 賠率(必須先*10000)
	 * @return
	 * @throws Exception
	 */
	Long getRetPointByUserIdAndLotteryIdAndBetTypeCodeAndMultiple(Long userId,Long lotteryId,String betTypeCode,Long odds) throws Exception;

	/**
	 * 判斷獎金組最高是哪一個
	 * @param currentUserAwardGroupList
	 */
	public List<UserBetAwardGroupEntity> checkMaxAward(List<UserBetAwardGroupEntity> currentUserAwardGroupList);
}
