/**   
* @Title: ILotteryWinSlipNumCaculator.java 
* @Package com.winterframework.firefrog.common.validate.business 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-18 上午11:18:58 
* @version V1.0   
*/
package com.winterframework.firefrog.common.wincaculate;


/** 
* @ClassName: ILotteryWinSlipNumCaculator 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-18 上午11:18:58 
*  
*/
public interface ILotteryWinSlipNumCaculator {

	IWinResultBean getWinSlipNum(String betDetail, String resultCode, ILotterySlipNumCaculatorContext context)
			throws Exception;
}
