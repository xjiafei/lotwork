package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameAwardCheck;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 
* @ClassName: IGameAwardCheckDao 
* @Description: 奖金组辅助表Dao接口方法。
* @author Richard
* @date 2013-12-27 下午3:44:05 
*
 */
public interface IGameAwardCheckDao  extends BaseDao<GameAwardCheck> {

	/**
	 * 
	* @Title: queryGameAwardCheckByGroupId 
	* @Description: 通过GroupId获取GameAwardCheck
	* @param awardGroupId
	* @return
	* @throws Exception
	 */
	public List<GameAwardCheck> queryGameAwardCheckByGroupId(Long awardGroupId) throws Exception;
	
	/**
	 * 
	* @Title: updataStatusByGroupId 
	* @Description: 更新check表的状态
	* @param awardGroupId
	* @throws Exception
	 */
	public void updataStatusByGroupId(Long awardGroupId, Long lotteryId, Long status) throws Exception;

	/**
	 * 
	* @Title: queryGameAwardCheckByGroupId 
	* @Description: 查询GameAwardCheck信息
	* @param groupId
	* @param lotteryId
	* @param status(状态 3:待审核 4:待发布状态 )
	* @return
	 */
	public List<GameAwardCheck> queryGameAwardCheckByGroupId(Long groupId, Long lotteryId, Integer status) throws Exception;

	/**
	 * 
	* @Title: delByAwardGroupId 
	* @Description: 根据奖金组Id删除
	* @param awardId
	* @throws Exception
	 */
	public void delByAwardGroupId(Long awardId) throws Exception;

	/**
	 * 
	* @Title: saveOrUpdate 
	* @Description: 针对已存在的奖金组信息，如果存在则更新，否则新增
	* @param check
	* @throws Exception
	 */
	public void saveOrUpdate(GameAwardCheck check) throws Exception;
}
