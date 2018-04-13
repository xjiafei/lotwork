package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;


/** 
* @ClassName: IConverterService 
* @Description:  走势图遗漏数据转换接口
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/
public interface IDataMakerService {
	
	/** 
	* @Title: 
	* @Description: 
	* @param   设定文件 
	* @return   
	* @throws 
	*/
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteyrId, Integer isGeneral);

	
}