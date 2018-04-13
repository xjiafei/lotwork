package com.winterframework.firefrog.game.service;

import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameBettypeAssist;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.entity.GameAwardEntity;
import com.winterframework.firefrog.game.entity.GameAwardEntity.GameAwardStatus;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.firefrog.game.web.dto.CreateGameAwardGroupDTO;
import com.winterframework.firefrog.game.web.dto.EditGameAwardGroupDTO;

/**
 * 奖金组服务接口
 * @author Richard
 * @date 2013-8-15 下午3:34:55 
 */
public interface IGameAwardGroupService {

	/**
	 * 获取所有奖金组信息
	 * @param LotteryId
	 * @return
	 * @throws Exception
	 */
	public List<GameAwardGroupEntity> queryGameAwardGroupByLotteryId(Long LotteryId) throws Exception;

	GameAwardGroup queryById(Long id) throws Exception;
	/**
	 * 获取奖金组明细信息
	 * @param LotteryId
	 * @param groupId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<GameAwardEntity> queryGameAward(Long LotteryId, Long groupId, Integer status) throws Exception;

	/**
	 * 新增奖金组信息 
	 * @param awardGroupDTO
	 * @throws Exception
	 */
	public void createAwardGroup(CreateGameAwardGroupDTO awardGroupDTO) throws Exception;

	/**
	 * 奖金组审核
	 * @param lotteryId
	 * @param awardId
	 * @param checkType
	 * @param checkResult
	 * @throws Exception
	 */
	public void auditAwardGroup(Long lotteryId, String awardId, Integer checkType, Integer checkResult)
			throws Exception;

	/**
	 * 修改奖金组
	 * @param awardGroupDTO
	 * @throws Exception
	 */
	public void editAwardGroup(EditGameAwardGroupDTO awardGroupDTO) throws Exception;

	/**
	 * 发布奖金组
	 * @param lotteryId
	 * @param awardId
	 * @param publishResult
	 * @throws Exception
	 */
	public void publishAwardGroup(Long lotteryId, String awardId, Integer publishResult) throws Exception;

	/**
	 * 奖金组查询 
	 * @param lotteryId
	 * @param status
	 * @param awardId
	 * @return
	 * @throws Exception
	 */
	public List<GameAwardGroupEntity> queryGameAwardGroup(Long lotteryId, Integer status, Long awardId)
			throws Exception;

	/**
	 * 估计LotteryId获取理论奖金
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	public List<GameBettypeStatus> queryTheoryByLotteryId(Long lotteryId) throws Exception;

	public void delAwardGroup(Long lotteryId, Long awardId) throws Exception;

	/**
	 * 彩种的公司最小留水 
	 * @param lotteryId
	 * @return
	 * @throws Exception
	 */
	public Long getMiniLotteryProfit(Long lotteryId) throws Exception;

	/**
	 * 获取已修改的奖金组信息 
	 * @param lotteryId
	 * @param gameAwardName
	 * @param awardStatus
	 * @return
	 * @throws Exception
	 */
	public List<GameAwardEntity> queryGameAwardBak(Long lotteryId, String gameAwardName, GameAwardStatus awardStatus)
			throws Exception;

	/**
	 * 根据用户ID查询奖金组
	 * @param userid
	 * @return
	 */
	public List<GameAwardGroupEntity> queryGameAwardGroupByUserId(long userid);

	/**
	 * 根据投注类型id获取对应的辅助玩法列表
	 * @param betTypeStatusIds
	 * @return
	 */
	public Map<Long, List<GameBettypeAssist>> getBetyTypeAssistByTypeIds(List<Long> betTypeStatusIds);

	/**
	 * 
	 * @param lotteryId
	 * @param awardGroupId
	 * @param type
	 * @param awardType 1:直選返點、2:不定位返點、3:所有返點、4:任選型返點、5:趣味型返點、6:混選返點、7:骰寶直選返點、8:超級2000返點、9:特肖返點、10:色波兩面返點、11:平碼返點、12:半波返點、13:一肖返點、14:不中返點、15:連肖(中)二三連肖返點、16:連肖(中)四連肖返點、17:連肖(中)五連肖返點、18:連肖(不中)二三連肖返點、19:連肖(不中)四連肖返點、20:連肖(不中)五連肖返點、21:連碼返點
	 * @param sysAwardGroupId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<GameAwardEntity> queryUserGameAward(Long lotteryId, Long awardGroupId, Long type, Long awardType,
			Long sysAwardGroupId, Long userId) throws Exception;
}
