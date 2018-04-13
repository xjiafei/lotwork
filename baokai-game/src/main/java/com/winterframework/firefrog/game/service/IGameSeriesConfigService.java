package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigDTO;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigVedioSourceRequest;
import com.winterframework.modules.web.jsonresult.Request;

/**
 * 
* @ClassName: IGameSerierConfigService 
* @Description: 运营参数服务
* @author Richard & Alan
* @date 2013-9-17 下午5:36:31 
*
 */
public interface IGameSeriesConfigService {

	/**
	 * 
	* @Title: queryGameSeriesConfigByLotteryId 
	* @Description: 查询运营参数信息 
	* @param lotteryId, status
	* @return GameSeriesConfigEntity
	* @throws Exception
	 */
	public GameSeriesConfigEntity queryGameSeriesConfigByLotteryId(Long lotteryId) throws Exception;
	
	/**
	 * 
	* @Title: getGameSeriesConfigByLotteryId 
	* @Description: 获取正式表的运营参数信息。 
	* @param lotteryId
	* @return
	* @throws Exception
	 */
	public GameSeriesConfigEntity getSeriesConfigByLotteryId(Long lotteryId) throws Exception;
	
	/**
	 * 
	* @Title: editGameSeriesConfig 
	* @Description: 修改运营参数信息 
	* @param dto
	* @throws Exception
	 */
	public void editGameSeriesConfig(GameSeriesConfigDTO dto) throws Exception;
	
	/**
	 * 
	* @Title: auditGameSeriesConfig 
	* @Description: 审核运营参数信息 
	* @param lotteryId
	* @throws Exception
	 */
	public void auditGameSeriesConfig(Long lotteryId, Long auditType) throws Exception;
	
	/**
	 * 
	* @Title: releaseGameSeriesConfig 
	* @Description: 发布运营参数信息
	* @param lotteryId
	* @throws Exception
	 */
	public void releaseGameSeriesConfig(Long lotteryId, Long publishType) throws Exception;
	
	/**
	 * 
	* @Title: getAllGameSeries 
	* @Description:根据条件获取彩种信息
	* @param lotteryId
	* @param status
	* @return List<GameSeries>
	* @throws Exception
	 */
	public List<GameSeries> getAllGameSeries(Long lotteryId, Integer status) throws Exception;
	
	/**
	 * 
	* @Title: vedioSourceConfig 
	* @Description:视频源增删改
	* @param request
	* @throws Exception
	 */
	public 	void vedioSourceConfig(GameSeriesConfigVedioSourceRequest request)
			throws Exception;
	
}
