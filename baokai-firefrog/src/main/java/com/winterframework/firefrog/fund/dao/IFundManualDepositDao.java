/**   
* @Title: IFundManualDepositDao.java 
* @Package com.winterframework.firefrog.fund.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-29 下午3:25:10 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao;

import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.web.dto.DepositQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFundManualDepositDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Tod
* @date 2013-7-29 下午3:25:10 
*  
*/
public interface IFundManualDepositDao {

	public void saveManualDeposit(FundManualOrder deposit) throws Exception;

	public void updateManualDeposit(FundManualOrder deposit) throws Exception;
	
	public int updateManualDepositCheckStatus(FundManualOrder deposit) throws Exception;

	public FundManualOrder queryManualDepositBySn(String sn) throws Exception;

	public FundManualOrder queryManualDepositById(Long id) throws Exception;

	public Page<FundManualOrder> queryFundManualDeposit(PageRequest<DepositQueryRequest> pageReqeust)
			throws Exception;

	public int updateRemark(Long id, String remark) throws Exception;

}
