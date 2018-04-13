package com.winterframework.firefrog.game.service.gametrend;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;
import com.winterframework.firefrog.game.web.util.QueryType;

/** 
* @ClassName: IGameTrendChartService 
* @Description: 走势图数据操作Service接口 
* @author Denny 
* @date 2013-10-25 下午3:59:40 
*  
*/
public interface IGameTrendChartService {

	/** 
	* @Title: generateTrendChartData 
	* @Description: 生成走势图数据
	*/
	public void generateTrendChartData() throws Exception;
	
	/**
	 * 依照條件即時產出秒秒彩的走勢圖資料
	 * @param userid 
	 * @param lotteryId
	 * @param isGeneral xml定義條件
	 * @param groupCode xml定義條件
	 * @param num 有可能為 50筆, 30筆, 或 1天, 2天, 5天
	 * @param startDate 查詢開始時間
	 * @param endDate 查詢結束時間
	 * @return List<GameTrendJbyl>
	 * @throws Exception
	 */
	public List<GameTrendJbyl> generateMMCTrendChartData(Long userid, Long lotteryId, Integer isGeneral, Long groupCode, 
			Integer num, Long startDate, Long endDate) throws Exception;
	
	public GameTrendChartStruc queryOmissionValue(List<GameTrendJbyl> gameTrendJbyls, Long lotteryId, Integer isGeneral, Integer gameGroupCode) ;
	
	/**
	 * 判斷走勢圖查詢類型
	 * @param lotteryid
	 * @return
	 */
	public QueryType checkQueryTypeByLotteryid(Long lotteryid);

}
