/**   
* @Title: IBusinessSNGenerator.java 
* @Package com.winterframework.firefrog.fund.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-16 下午3:22:34 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.util;

import com.winterframework.firefrog.fund.enums.EnumItem;

/** 
* @ClassName: ISNGenerator 
* @Description: 创建资金业务的流水号和资金变动的流水号. 
* @author Page
* @date 2013-12-16 下午3:22:34 
*  
*/
public interface ISNGenerator {
	/**
	 * 
	* @Title: createBusinessSn 
	* @Description: 创建资金业务的流水号
	* @param item
	* @param userId
	* @return
	 */
	public abstract String createBusinessSn(EnumItem item, long userId);
	public abstract String createBusinessSnForNull(EnumItem item, long userId);
	public abstract String createAPlSn(EnumItem item, long userId);

	public abstract String parseBusinessSnDesc(String sn);
	
	/**
	 * 
	* @Title: createFundSn 
	* @Description: 创建资金变动的流水号
	* @param item
	* @param userId
	* @return
	 */
	public abstract String createFundSn(EnumItem item, long userId);

}