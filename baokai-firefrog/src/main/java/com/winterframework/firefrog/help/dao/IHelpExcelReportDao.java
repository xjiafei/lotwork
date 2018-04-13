package com.winterframework.firefrog.help.dao;

import com.winterframework.firefrog.help.entity.Oper;
import com.winterframework.firefrog.help.entity.OperReportSum;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;


public interface IHelpExcelReportDao {
	/**
	 * 根据日期分页查询报表	
	 * @param operPageRequest
	 * @return
	 */
	public Page<Oper> selectExcelByDate(PageRequest<String> operPageRequest);
	
	/**
	 * 根据日期计算报表总和
	 * @param reportDate
	 * @return
	 */
	public OperReportSum selectExcelReportSum(String reportDate);
}
