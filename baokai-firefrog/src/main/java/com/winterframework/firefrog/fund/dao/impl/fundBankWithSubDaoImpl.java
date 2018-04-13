/**   
* @Title: FundBankDaoWithSubImpl.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午10:50:23 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundBankDao;
import com.winterframework.firefrog.fund.dao.IFundBankWithSubDao;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundBankWithSub;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: FundBankDaoWithSubImpl
* @Description: 银行管理 DaoImpl 
* @author Alan
* @date 2013-7-1 上午10:50:23 
*  
*/
@Repository("fundBankWithSubDaoImpl")
public class fundBankWithSubDaoImpl extends BaseIbatis3Dao<FundBankWithSub> implements IFundBankWithSubDao {
	/**
	* Title: queryAllBank
	* @return 
	* @see com.winterframework.firefrog.fund.dao.IFundBankDao#queryAllBank() 
	*/
	@Override
	public List<FundBankWithSub> queryAllBank() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sortColumns", "ID");
		
		List<FundBankWithSub> fundBankList = sqlSessionTemplate.selectList("com.winterframework.firefrog.fund.dao.vo.FundBankWithSub.getFundBankAllWithSub", map);
		return fundBankList;
	}
	
	@Override
	public FundBankWithSub queryBankByCondition(Long code, Long version) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("version", version);
		
		FundBankWithSub fundBankList = sqlSessionTemplate.selectOne("com.winterframework.firefrog.fund.dao.vo.FundBankWithSub.getFundBankAllWithSub", map);
		return fundBankList;
	}
	
	public List<FundBankWithSub> getCanAppealBanks(Long canRechargeAppeal) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("canRechargeAppeal", canRechargeAppeal);
		map.put("sortColumns", "ID");
		List<FundBankWithSub> fundBankList = sqlSessionTemplate.selectList("getCanAppealBanks", map);
		return fundBankList;
	}

	@Override
	public void updateBank(FundBankWithSub bank) throws Exception {
		this.update((bank));
	}

	@Override
	public void saveBank(FundBankWithSub bank) throws Exception {
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
	public FundBankWithSub queryById(Long bankId) throws Exception {
		return this.getById(bankId);
	}

	@Override
	public FundBankWithSub queryByMappingVersion(Long mappingCode, Long version) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mapping_code", mappingCode);
		map.put("version", version);
		FundBankWithSub fundBankWithSub = sqlSessionTemplate.selectOne("com.winterframework.firefrog.fund.dao.vo.FundBankWithSub.getFundBankWithSubByMappingCodeVersion", map);
		return fundBankWithSub;
	}
}
