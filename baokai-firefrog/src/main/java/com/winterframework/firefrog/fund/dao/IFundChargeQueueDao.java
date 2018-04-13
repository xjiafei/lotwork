/**   
* @Title: IFunChargeDao.java 
* @Package com.winterframework.firefrog.fund.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午9:54:17 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundChargeQueue;
import com.winterframework.firefrog.fund.enums.FundChargeQueueEnum.QueueStatus;

/** 
* @ClassName: IFunChargeQueueDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-1 上午9:54:17 
*  
*/
public interface IFundChargeQueueDao {

	public long addOrderQueue(FundChargeQueue fundChargeQueue) throws Exception;

	public long updateOrderQueue(FundChargeQueue fundChargeQueue) throws Exception;

	public FundChargeQueue queryByOrderNum(String orderNum) throws Exception;
	
	public long updateOrderQueueStatus(String sn , Long status) throws Exception;
	
	public List<FundChargeQueue> queryUntreatOrder() throws Exception;

}
