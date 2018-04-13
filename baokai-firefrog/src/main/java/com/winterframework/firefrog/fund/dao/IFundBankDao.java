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
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IFundBankDao 
* @Description: 银行管理DAO 
* @author 你的名字 
* @date 2013-7-1 上午10:49:47 
*  
*/
public interface IFundBankDao extends BaseDao<FundBank> {

	public List<FundBank> queryAllBank() throws Exception;

	public void updateBank(FundBank bank) throws Exception;

	public void saveBank(FundBank bank) throws Exception;

	public FundBank queryById(Long bankId) throws Exception;
	
	List<FundBank> getCanAppealBanks(Long canRechargeAppeal) throws Exception;
	/**
	 * Title: queryByCode
	 * param code
	 * return List<FundBank>
	 * throws Exception
	 */
	List<FundBank> queryByCode(Long code) throws Exception;
}
