/**   
* @Title: IFundBankDao.java 
* @Package com.winterframework.firefrog.fund.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午10:49:47 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundBankWithSub;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IFundBankWithSubDao 
* @Description: 银行管理(加上個人版與企業版)DAO 
* @author 你的名字 
* @date 2016-12-05 上午10:49:47 
*  
*/
public interface IFundBankWithSubDao extends BaseDao<FundBankWithSub> {
	/**
	 * 
	* @Title: bankParamsSetWithSub  
	* @Description:查詢fund_bank join fund_bank_sub的資料
	* @return List<FundBank>
	* @throws Exception
	 */
	public List<FundBankWithSub> queryAllBank() throws Exception;
	/**
	 * 
	* @Title: bankParamsSetWithSub  
	* @Description:更新fund_bank_sub的資料
	* @param FundBankWithSub
	* @throws Exception
	 */
	public void updateBank(FundBankWithSub bank) throws Exception;

	public void saveBank(FundBankWithSub bank) throws Exception;

	public FundBankWithSub queryById(Long bankId) throws Exception;
	
	List<FundBankWithSub> getCanAppealBanks(Long canRechargeAppeal) throws Exception;
	
	public FundBankWithSub queryByMappingVersion(Long mappingCode, Long version) throws Exception;
	/**
	 * 
	* @Title: query FUND_BANK_SUB的資料
	* @Description:查詢fund_bank join fund_bank_sub的資料
	* @return FundBankWithSub
	* @throws Exception
	 */
	public FundBankWithSub queryBankByCondition(Long code, Long version) throws Exception;
		
}
