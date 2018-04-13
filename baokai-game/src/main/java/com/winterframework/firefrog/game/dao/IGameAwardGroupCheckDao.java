package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameAwardGroupCheck;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.orm.dal.ibatis3.BaseDao;
/**
 * 
* @ClassName: IGameAwardGroupCheckDao 
* @Description: 奖金组check表接口信息 
* @author Richard
* @date 2013-12-27 下午3:51:48 
*
 */
public interface IGameAwardGroupCheckDao extends BaseDao<GameAwardGroupCheck> {

	/**
	 * 
	* @Title: saveGameAwardGroup 
	* @Description:保存GameAwardGroup并返回Id
	* @param awardGroupCheck
	* @return
	* @throws Exception
	 */
	public Long saveGameAwardGroupCheck(GameAwardGroupCheck awardGroupCheck) throws Exception;
	
	/**
	 * 
	* @Title: queryGameAwardGroupCheckById 
	* @Description: 根据LottertyId和GroupId获取GameAwardGroupCheck信息 
	* @param lotteryId
	* @param GroupId
	* @return
	* @throws Exception
	 */
	public GameAwardGroupCheck queryGameAwardGroupCheckById(Long lotteryId, Long groupId) throws Exception;

	/**
	 * 
	* @Title: queryGameAwardGroupChecks 
	* @Description:查询check表中的奖金组
	* @param lotteryId
	* @param status
	* @param awardId
	* @return
	* @throws Exception
	 */
	public List<GameAwardGroupEntity> queryGameAwardGroupChecks(Long lotteryId, Integer status, Long awardId) throws Exception;

	/**
	 * 
	* @Title: checkIsExistAwardName 
	* @Description: 判断是否存在相同的奖金组名称 
	* @param lotteryId
	* @param awardName
	* @return
	* @throws Exception
	 */
	public Boolean checkIsExistAwardName(Long lotteryId, String awardName) throws Exception;

	/**
	 * 
	* @Title: saveOrUpdate 
	* @Description:保存或更新
	* @param awardGroupCheck
	* @throws Exception
	 */
	public void saveOrUpdate(GameAwardGroupCheck awardGroupCheck) throws Exception;
	
	/**
	 * 
	* @Title: queryGameGroupByLotteryIdAndName 
	* @Description: 根据LotteryId和奖金组名称获取奖金组信息
	* @param lotteryId
	* @param gameAwardName
	* @return
	* @throws Exception
	 */
	public GameAwardGroupCheck queryGameGroupByLotteryIdAndName(Long lotteryId, String gameAwardName) throws Exception;
}
