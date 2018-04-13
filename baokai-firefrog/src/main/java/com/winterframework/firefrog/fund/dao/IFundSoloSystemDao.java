package com.winterframework.firefrog.fund.dao;

/** 
* @ClassName: IFundSoloSystemDao 
* @Description: 系统附言
* @author 你的名字 
* @date 2014-3-11 下午1:21:43 
*  
*/
public interface IFundSoloSystemDao {

	/** 
	 * 获取下一个系统附言
	*/
	String getNextSystemRemark() throws Exception;
}
