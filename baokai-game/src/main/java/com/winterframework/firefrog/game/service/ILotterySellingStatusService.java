package com.winterframework.firefrog.game.service;

import java.util.Date;

/** 
* @ClassName: ILotterySellingStatusService 
* @Description: 彩种销售状态Service接口 
* @author Denny 
* @date 2013-8-29 上午12:50:32 
*  
*/
public interface ILotterySellingStatusService {

	/**
	 * 
	* @Title: queryLotterySellingStatus 
	* @Description: 按照彩种ID查询彩种销售状态
	* @param lotteryid
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer queryLotterySellingStatus(long lotteryid) throws Exception;
	
	/**
	 * 
	* @Title: modifyLotterySellingStatus 
	* @Description: 修改彩种销售状态 
	* @param checkStatus
	* @param status
	* @param lotteryid
	* @throws
	 */
	public void modifyLotterySellingStatus(Integer checkStatus, Integer status, Long lotteryid,Date takeOffTime) throws Exception;
	
	/**
	 * 
	* @Title: checkLotterySellingStatus 
	* @Description: 审核彩种销售状态 
	* @param lotteryid    设定文件 
	* @throws
	 */
	public void checkLotterySellingStatus(Long lotteryid, Long auditType) throws Exception;
	
	/**
	 * 
	* @Title: publishLotterySellingStatus 
	* @Description: 发布彩种销售状态
	* @param lotteryid    设定文件 
	* @throws
	 */
	public void publishLotterySellingStatus(Long lotteryid, Long publishType) throws Exception;

	/**
	 * 
	* @Title: queryLotteryCheckStatus 
	* @Description: 查询彩种审核状态
	* @param lotteryid
	* @return int    返回类型 
	* @throws
	 */
	public int queryLotteryCheckStatus(long lotteryid);
	
	/**
	 * 
	 * @Title: queryLotterySellingTakeOffTime 
	* @Description: 查询彩种下架時間
	* @param lotteryid
	* @return Date    返回类型 
	* @throws
	 */
	public Date queryLotterySellingTakeOffTime(long lotteryid) throws Exception;
}
