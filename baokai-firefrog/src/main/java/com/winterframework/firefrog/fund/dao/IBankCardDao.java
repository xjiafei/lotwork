/**   
* @Title: IBankCardDao.java 
* @Package com.winterframework.firefrog.fund.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Denny  
* @date 2013-7-1 下午4:22:08 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.UserBank;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.UserCardBind;
import com.winterframework.firefrog.fund.entity.UserCardBindHistory;
import com.winterframework.firefrog.fund.web.dto.BankCardBindHistoryRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardBindRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IBankCardDao 
* @Description: 银行卡管理Dao接口 
* @author Denny 
* @date 2013-7-1 下午4:22:08 
*  
*/
public interface IBankCardDao extends BaseDao<UserBank> {

	/**
	 * 
	* @Title: queryBankCardByUserId 
	* @Description: 根据UserID查询银行卡
	* @param userId
	* @return
	 */
	public UserBank queryBankCardByUserId(long userId);

	/**
	 * 
	* @Title: addBankCard 
	* @Description: 添加银行卡
	 */
	public void addBankCard(BankCard bc);
	
	/**
	 * 
	* @Title: updateBankCard 
	* @Description: 修改银行卡
	 */
	public void updateBankCard(BankCard bc);

	/**
	 * 
	* @Title: addBankCard 
	* @Description: 移除银行卡
	 */
	public void removeBankCard(long bindId);

	/**
	 * 
	* @Title: addBankCardHistoryRecord 
	* @Description: 添加银行卡绑定历史记录
	 */
	public void addBankCardHistoryRecord(UserCardBindHistory userCardBindHistoryRecord);

	/**
	 * 
	* @Title: addBankCard 
	* @Description: 查询绑定银行卡
	 */
	public List<BankCard> getBoundBankCard(long userId,String cardNumber);
	
	
	
	/**
	 * 
	* @Title: getBoundBankCardByRequest 
	* @Description: 查询绑定银行卡
	 */
	public List<BankCard> getBoundBankCardByRequest(BankCardQueryRequest request);
	
	/**
	 * 
	* @Title: searchUserCardBindRecords 
	* @Description: 查询用户绑卡记录
	* @param pageRequest
	* @return
	 */
	public Page<UserCardBind> searchUserCardBindRecords(PageRequest<BankCardBindRecordQueryDTO> pageRequest)
			throws Exception;

	/**
	 * 
	* @Title: searchUserCardBindHistoryRecords 
	* @Description: 查询单用户绑卡记录
	* @param pageRequest
	* @return
	* @throws Exception
	 */
	public Page<UserCardBindHistory> searchUserCardBindHistoryRecords(
			PageRequest<BankCardBindHistoryRecordQueryDTO> pageRequest) throws Exception;

	public List<UserBank> queryBankCardsByUserId(long userId); 
	
	public List<UserBank> getFormUserBankByUserId(Long userId);

	public Long getCountFromUserBankByBankNum(String bankNum);

	public Long getCountFromUserBankHistoryByDate(Long userId, Date lockTime);
	
	public List<String> getBankCardNotSuspicious(long userId);
}
