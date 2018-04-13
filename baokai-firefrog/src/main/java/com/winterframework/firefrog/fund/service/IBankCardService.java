/**   
* @Title: IBankCardService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Denny  
* @date 2013-6-28 下午5:51:02 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.UserCardBind;
import com.winterframework.firefrog.fund.entity.UserCardBindHistory;
import com.winterframework.firefrog.fund.web.dto.BankCardBindHistoryRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardBindRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IBankCardService 
* @Description: 银行卡管理Service接口 
* @author Denny 
* @date 2013-6-28 下午5:51:02 
*  
*/
public interface IBankCardService {

	/**
	 * 
	* @Title: applyBind 
	* @Description: 申请绑卡
	 */
	public void applyBind(BankCard bankCard);
	
	/**
	 * 
	* @Title: updateBind 
	* @Description: 修改绑卡
	 */
	public void updateBind(BankCard bankCard);
	/**
	 * 
	* @Title: applyBind 
	* @Description: 申请绑卡
	 */
	public boolean checkBind(String bankCard,Long userId,String bankAccount);

	/**
	 * 
	* @Title: unbind 
	* @Description: 删除绑卡
	 */
	public void unbind(long bindId,long userId, long bankId, long mcBankId, long bindCardType,String nickName);

	/**
	 * 
	* @Title: queryBoundBankCard 
	* @Description: 查询绑卡
	 */
	public List<BankCard> queryBoundBankCard(long userId,String cardNumber);
	
	/**
	 * 
	* @Title: queryBoundBankCardByRequest 
	* @Description: 查询绑卡
	 */
	public List<BankCard> queryBoundBankCardByRequest(BankCardQueryRequest request);

	/**
	 * 
	* @Title: isFirstBind 
	* @Description: 是否首次绑卡
	 */
	public boolean isFirstBind(long userId);

	/**
	 * 
	* @Title: isLessThanConfiguredBindQuantity 
	* @Description: 已绑卡数量是否少于配置的可绑定数
	 */
	public boolean isLessThanConfiguredBindQuantity(long userId,long bindcardType);

	/**
	 * 
	* @Title: searchUserCardBindRecords 
	* @Description: 查询用户绑卡记录分页列表
	* @param pageRequest
	* @return
	 */
	public Page<UserCardBind> searchUserCardBindRecords(PageRequest<BankCardBindRecordQueryDTO> pageRequest)
			throws Exception;

	/**
	 * 
	* @Title: searchUserCardBindHistoryRecords 
	* @Description: 查询单用户绑定历史记录
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<UserCardBindHistory> searchUserCardBindHistoryRecords(
			PageRequest<BankCardBindHistoryRecordQueryDTO> pageRequest) throws Exception;
	
	/**
	 * 
	* @Title: checkAlipayStatus 
	* @Description: 確認目前支付寶是何種版本
	* @param BankCard
	* @return
	 */
	public boolean checkAlipayStatus(BankCard bankCard) throws Exception;

}
