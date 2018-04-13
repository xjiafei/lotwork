/**   
* @Title: FundBankDaoImpl.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午10:50:23 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundBankDao;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: FundBankDaoImpl 
* @Description: 银行管理 DaoImpl 
* @author Alan
* @date 2013-7-1 上午10:50:23 
*  
*/
@Repository("fundBankDaoImpl")
public class FundBankDaoImpl extends BaseIbatis3Dao<FundBank> implements IFundBankDao {
	/**
	* Title: queryAllBank
	* @return 
	* @see com.winterframework.firefrog.fund.dao.IFundBankDao#queryAllBank() 
	*/
	@Override
	public List<FundBank> queryAllBank() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sortColumns", "id");

		List<FundBank> fundBankList = sqlSessionTemplate.selectList("getFundBankAll", map);
		return fundBankList;
	}
	
	public List<FundBank> getCanAppealBanks(Long canRechargeAppeal) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("canRechargeAppeal", canRechargeAppeal);
		map.put("sortColumns", "ID");
		List<FundBank> fundBankList = sqlSessionTemplate.selectList("getCanAppealBanks", map);
		return fundBankList;
	}

	@Override
	public void updateBank(FundBank bank) throws Exception {
		this.update((bank));
	}

	@Override
	public void saveBank(FundBank bank) throws Exception {
		this.insert((bank));
	}

	/**
	* Title: queryById
	* Description:
	* @param bankId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundBankDao#queryById(java.lang.Long) 
	*/
	@Override
	public FundBank queryById(Long bankId) throws Exception {
		return this.getById(bankId);
	}
	/**
	 * Title: queryByCode
	 * param code
	 * return List<FundBank>
	 * throws Exception
	 */
	@Override
	public List<FundBank> queryByCode(Long code) throws Exception {
		List<Long> list =new ArrayList<Long>();
		list.add(code);
		return sqlSessionTemplate.selectList("com.winterframework.firefrog.fund.dao.vo.FundBank.getByIds",list);
	}
}
