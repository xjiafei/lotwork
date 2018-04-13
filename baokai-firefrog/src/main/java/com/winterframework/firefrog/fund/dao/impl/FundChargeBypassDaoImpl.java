package com.winterframework.firefrog.fund.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.fund.dao.IFundChargeBypassDao;
import com.winterframework.firefrog.fund.dao.vo.FundChargeBypassVO;
import com.winterframework.firefrog.fund.web.dto.FundChargeBypassRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository
public class FundChargeBypassDaoImpl extends BaseIbatis3Dao<FundChargeBypassVO>
		implements IFundChargeBypassDao {
	@Override
	public Page<FundChargeBypassVO> selectByPage(PageRequest<FundChargeBypassRequest> pageRequest) throws Exception {
		pageRequest.setSortColumns("CREATE_TIME DESC");
		return this.getAllByPage(pageRequest);
	}
	
	@Override
	public void updateDeleteFlag(Long deleteId,String type) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("deleteId", deleteId);
		map.put("deleteFlag", type);
		this.sqlSessionTemplate.update(this.getQueryPath("updateDeleteFlag"),map);
	}
	
	@Override
	public void deleteById(Long id){
		Map<String,Object> map = Maps.newHashMap();
		map.put("id", id);
		this.sqlSessionTemplate.delete(this.getQueryPath("delete"),map);
	}
	
	@Override
	public int saveWhiteList(FundChargeBypassVO vo) throws Exception {
		int count = sqlSessionTemplate.insert(this.getQueryPath("insert"),vo);
		return count;
	}
	
	@Override
	public FundChargeBypassVO searchByAccount(String userAccount,Long chargeWaySet) throws Exception {
		Map<String,Object> map = Maps.newHashMap();
		map.put("userAccount", userAccount);
		map.put("chargeWaySet", chargeWaySet);
		return sqlSessionTemplate.selectOne(this.getQueryPath("searchByAccount"),map);
	}
}
