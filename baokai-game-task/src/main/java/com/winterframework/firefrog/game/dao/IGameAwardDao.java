package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameAwardCheck;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameBonusJsonBean;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameAwardDao 
* @Description: 奖金组Dao接口类 
* @author Richard
* @date 2013-12-27 下午3:49:24 
*
 */
public interface IGameAwardDao extends BaseDao<GameAward> {

	/**
	 * 
	* @Title: queryGameAwardByGroupId 
	* @Description: 查询正式表的GameAward 
	* @param groupId
	* @param lotteryId
	 * @param status 
	* @return
	* @throws Exception
	 */
	List<GameAward> queryGameAwardByGroupId(Long groupId, Long lotteryId, Integer status) throws Exception;

	/**
	 * 
	* @Title: saveOrUpdate 
	* @Description: 保存更新奖金组明细
	* @param awardCheck
	* @param groupId
	* @throws Exception
	 */
	void saveOrUpdate(List<GameAwardCheck> awardCheck, GameAwardGroup awardGroup) throws Exception;

	/** 
	* @Title: getGameAwardByGroupId 
	* @Description: 根据奖金组ID获取奖金组 
	* @param awardGroupId
	* @return GameAward    返回类型 
	* @throws 
	*/
	List<GameAward> getGameAwardByGroupId(Long awardGroupId);

	/** 
	* @Title: getActualBonus 
	* @Description: 根据用户id，彩种id，以及玩法获取对应的期望奖金
	* @param lotteryId
	* @param betTypeCode
	* @param userId
	* @return
	*/
	Long getActualBonus(Long lotteryId, String betTypeCode, Long userId);
	Long getActualBonusDown(Long lotteryId, String betTypeCode, Long userId);
	/** 
	* @Title: getGameAwardByBetTypeAndLotteryId 
	* @Description: 根据彩种，玩法信息，和系统奖金组id获取系统奖金信息
	* @param lotteryId
	* @param betTypeCode
	* @param AwardGroupId
	* @return
	*/
	GameAward getGameAwardByBetTypeAndLotteryId(Long lotteryId, String betTypeCode, Long AwardGroupId);

	/** 
	* @Title: getGameAwardListByBetCodeParent 
	* @Description:获取多奖金组信息列表
	* @param lotteryid
	* @param string
	* @param userId
	* @return
	*/
	List<GameAward> getGameAwardListByBetCodeParent(Long lotteryId, String betTypeCode, Long userId);
	
	/** 
	* @Title: getGameBonusJsonBean 
	* @Description:获取多奖金组信息和奖金池
	* @param lotteryid
	* @return
	*/
	public GameBonusJsonBean getGameBonusJsonBean(Long lotteryId) throws Exception;
	
	/**
	 * 获取奖金明细（根据奖金组和投注编码上级）
	 * @param awardGroupId
	 * @param betTypeCode
	 * @return
	 * @throws Exception
	 */
	public List<GameAward> getGameAwardByGroupIdAndBetTypeCodeParent(Long awardGroupId,String betTypeCode)  throws Exception;
	
	/**
	 * 取得該彩種的各種獎金組明細(不包含四碼玩法)
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	public List<GameAward> getGameAwardByLotteryIdFilterFourCode(Long lotteryId) throws Exception ;
}
