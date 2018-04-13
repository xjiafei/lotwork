/**   
* @Title: IFundSuspiciousCardService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-25 上午10:17:57 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.entity.FundSuspiciousCard;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFundSuspiciousCardService 
* @Description: 可疑银行卡操作Service
* @author Alan
* @date 2013-7-25 上午10:17:57 
*  
*/
public interface IFundSuspiciousCardService {

	/**
	 * 
	* @Title: queryFundSuspiciousCardByPage 
	* @Description: 查询可疑银行卡分页列表
	* @param pageRequest
	* @return
	 */
	public Page<FundSuspiciousCard> queryFundSuspiciousCardByPage(PageRequest<?> pageRequest);

	/**
	 * 
	* @Title: addFundSuspiciousCard 
	* @Description: 添加可疑银行卡 
	* @param fundSuspiciousCard
	 */
	public void addFundSuspiciousCard(FundSuspiciousCard fundSuspiciousCard);

	/**
	 * 
	* @Title: deleteFundSuspiciousCard 
	* @Description: 删除可疑银行卡
	* @param id
	 */
	public void deleteFundSuspiciousCard(Long id);
	
	/**
	 * 
	* @Title: isFundSuspiciousCard 
	* @Description: 判断是否是可疑卡
	* @param cardNumber
	 */
	public Boolean isFundSuspiciousCard(String cardNumber) throws Exception;
	
	/**
	 * 
	* @Title: isFundSuspiciousCard 
	* @Description: 判断是否是可疑卡
	* @param cardNumber
	 */
	public Boolean isFundSuspiciousCardAccount(Long userId) throws Exception;
}
