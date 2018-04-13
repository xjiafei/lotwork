package com.winterframework.firefrog.fund.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundDao;
import com.winterframework.firefrog.fund.dao.vo.Fund;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("fundDaoImpl")
public class FundDaoImpl extends BaseIbatis3Dao<Fund> implements IFundDao {

	@Override
	public UserFund getUserFund(Long userId) {
		Fund fund = sqlSessionTemplate.selectOne("getByUserId", userId);
		if(fund==null){
			fund=Fund.getZeroFund(userId);
		}
		UserFund userFund = VOConverter.fund2UserFund(fund);
		return userFund;
	}
	@Override
	public void addWhite(String account){
		System.out.print("adsfsdfsad:"+account);
		sqlSessionTemplate.update("updateWhite", account);
	}
	@Override
	public int updateFund(Map<String, Object> map) {
		return sqlSessionTemplate.update("updateFund", map);

	}
	public Long getTeamFund(Long userId){
		return sqlSessionTemplate.selectOne("getTeamSumByUserId", userId);
	};
	@Override
	public int updateFundById(Long id, Long frozenAmt) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("frozenAmt", frozenAmt);
		return sqlSessionTemplate.update("updateFundById", map);
	}

}
