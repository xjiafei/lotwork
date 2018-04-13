package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BaseTrendChartStruc;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;
import com.winterframework.firefrog.game.web.dto.QueryAssistActionResponse;


/** 
* @ClassName: IConverterService 
* @Description:  走势图遗漏数据转换接口
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/
public interface IConverterService {
	
	/** 
	* @Title: 
	* @Description: 
	* @param   设定文件 
	* @return   
	* @throws 
	*/
	public abstract GameTrendChartStruc converter(BaseTrendChartStruc struc, long lotteryId, long gameGroupCode, Integer isGeneral);
	
	public abstract QueryAssistActionResponse converter(List<BaseChartStruc> baseChartStrucs, long lotteryId,
			long gameGroupCode, Integer isGeneral);

	
}