package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;
import com.winterframework.firefrog.game.web.dto.GameTrendQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameTrendReport;
import com.winterframework.firefrog.game.web.util.WapChart;

import java.util.List;

/** 
* @ClassName: IBaseOmissionService 
* @Description:  走势图遗漏数据合成接口
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/
public interface IBaseOmissionService {

	/**
	 * 
	* @Title: queryBaseChartStruc 
	* @Description: 生成走势图遗漏数据Struc
	* @param lotteryId
	* @param gameGroupCode
	* @param gameSetCode
	* @param betMethodCode
	* @param  num
	* @param @throws Exception    设定文件 
	* @return List<BaseChartStruc>    返回类型 
	* @throws
	 */
	public abstract List<BaseChartStruc> queryBaseChartStruc(Long lotteryId, Integer gameGroupCode, Integer gameSetCode,
			Integer betMethodCode, int num) throws Exception;
	/**
	* @Title: queryTrendCharts
	* @Description: 走势图数据查询 
	* @param  lotteryId 
	* @param  gameGroupCode
	* @param  type                     查询类型：1，期数查询；2，日期查询    
	* @param  isGeneral				    在五星查询时为必填，1为基本走势，0为五星综合走势
	* @param  num						期数
	* @param  startDate
	* @param  endDate
	* @return BaseTrendChartStruc    返回类型 
	* @throws 
	*/
	public abstract GameTrendChartStruc queryTrendCharts(Long lotteryId, Integer gameGroupCode,Integer type, Integer isGeneral,
			Integer num, Long startDate,Long endDate)  throws Exception ;


	public List<GameTrendReport> queryTrendReport(GameTrendQueryRequest request) throws Exception ;
	public List<WapChart> getWapChart(Long lotteryId);
}