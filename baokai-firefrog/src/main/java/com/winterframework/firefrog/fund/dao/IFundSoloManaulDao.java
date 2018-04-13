package com.winterframework.firefrog.fund.dao;

import com.winterframework.firefrog.fund.entity.FundSoloRemarkManual;

/** 
* @ClassName: IFundSoloManaulDao 
* @Description: 手工附言
* @author 你的名字 
* @date 2014-3-11 下午1:21:38 
*  
*/
/** 
* @ClassName: IFundSoloManaulDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-11 下午2:38:26 
*  
*/
public interface IFundSoloManaulDao {

	/** 
	 * 根据附言获取手工附言信息
	*/
	public FundSoloRemarkManual getFundSoloManaulByRemark(String remark) throws Exception;

	/** 
	* @Title: 保存附言
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param manual
	*/
	public void saveSoloManual(FundSoloRemarkManual manual);

	/** 
	* @Title: 更新附言
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param manual
	*/
	public void updateSoleManual(FundSoloRemarkManual manual);
	public void deleteSoleManual(String manual);
}
