package com.winterframework.firefrog.help.service;

import com.winterframework.firefrog.help.entity.Oper;
import com.winterframework.firefrog.help.entity.OperReportSum;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;



public interface IHelpExcelReportService {

	/**
	 * 根据时间查询报表
	 * @param operPageRequest
	 * @return
	 */
	public Page<Oper> getExcelByDate(PageRequest<String> operPageRequest);
	
	/**
	 * 根据时间计算报表总和
	 * @param reportDate
	 * @return
	 */
	public OperReportSum getExcelReportSum(String reportDate);

}
