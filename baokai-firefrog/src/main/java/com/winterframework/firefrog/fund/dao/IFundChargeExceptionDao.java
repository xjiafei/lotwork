/**   
* @Title: IFundChargeExceptionDao.java 
* @Package com.winterframework.firefrog.fund.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-8 下午8:06:06 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao;

import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.web.dto.ExceptionQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFundChargeExceptionDao 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-8 下午8:06:06 
*  
*/
public interface IFundChargeExceptionDao {

	public void save(FundChargeException fundChargeException) throws Exception;

	public Page<FundChargeException> query(PageRequest<ExceptionQueryRequest> pageRequest) throws Exception;

	public int update(FundChargeException fundChargeException) throws Exception;

	public FundChargeException queryById(Long id) throws Exception;
	
	public FundChargeException queryBySn(String sn) throws Exception;
	public void yuchuli(Long id,String yuchuliId,Long currStatus) ;
	public void yuchuliEnd(Long id,Long currStatus);
	public int zero(Long id,Long... checkStatuses);
}
