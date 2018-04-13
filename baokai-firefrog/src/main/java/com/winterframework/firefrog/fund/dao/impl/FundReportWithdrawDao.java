package com.winterframework.firefrog.fund.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis3.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.web.dto.FundReportDeposit;
import com.winterframework.firefrog.fund.web.dto.FundReportDepositRes;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.RequestBody;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;
@Repository
public class FundReportWithdrawDao extends BaseIbatis3Dao<FundReportDepositRes>{
	private static final String basesqlPath="com.winterframework.firefrog.fund.dao.vo.FundReport";
	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;
	public Page<FundReportDepositRes> getSumByPeriod(RequestBody<FundReportDeposit> fpd){
		PageRequest<FundReportDepositRes> pr=new PageRequest<FundReportDepositRes>(fpd.getPager().getStartNo(),fpd.getPager().getEndNo());
		pr.setSearchDo(fpd.getParam());		
		Page<FundReportDepositRes> pg= this.pageQuery(pr, "getDeport");
		return pg;
	}	
	protected String getQueryPath(String type) {
		return basesqlPath + "." + type;
	}

}
