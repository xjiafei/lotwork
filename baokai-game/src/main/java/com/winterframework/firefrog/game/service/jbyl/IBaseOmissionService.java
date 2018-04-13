package com.winterframework.firefrog.game.service.jbyl;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BaseTrendChartStruc;

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
	* @param   设定文件 
	* @return BaseTrendChartStruc    返回类型 
	* @throws 
	*/
	public abstract BaseTrendChartStruc queryTrendCharts(List<GameDrawResult> list, Long lotteryId, Integer gameGroupCode,
			int isGeneral, int num);

}