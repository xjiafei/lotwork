package com.winterframework.firefrog.fund.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.web.controller.vo.DatePeriod;
import com.winterframework.firefrog.fund.web.controller.vo.WithCharge;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.RequestBody;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;
@Repository
public class FundReportDepositDao extends BaseIbatis3Dao<WithCharge>{
	private static final String basesqlPath="com.winterframework.firefrog.fund.dao.vo.FundReport";

	public Page<WithCharge> getSumByPeriod(RequestBody<DatePeriod> fpd){
		PageRequest<DatePeriod> pr=new PageRequest<DatePeriod>(fpd.getPager().getStartNo(),fpd.getPager().getEndNo());
		pr.setSearchDo(fpd.getParam());		
		Page<WithCharge> pg= this.pageQuery(pr, "chargeWithdraw");
		return pg;
	}
	protected String getQueryPath(String type) {
		return basesqlPath + "." + type;
	}

}
