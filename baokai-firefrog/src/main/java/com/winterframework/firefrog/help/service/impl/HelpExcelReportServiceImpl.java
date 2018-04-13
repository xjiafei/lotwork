package com.winterframework.firefrog.help.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.winterframework.firefrog.help.dao.IHelpExcelReportDao;
import com.winterframework.firefrog.help.entity.Oper;
import com.winterframework.firefrog.help.entity.OperReportSum;
import com.winterframework.firefrog.help.service.IHelpExcelReportService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;




@Service("helpExcelReportServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class HelpExcelReportServiceImpl implements IHelpExcelReportService{

	@Resource(name="helpExcelReportDaoImpl")
	IHelpExcelReportDao iHelpExcelReportDao;
	
	
	@Override
	public Page<Oper> getExcelByDate(PageRequest<String> operPageRequest) {
	
		return iHelpExcelReportDao.selectExcelByDate(operPageRequest);
	}
	
	
	@Override
	public OperReportSum getExcelReportSum(String reportDate) {	
	
		return iHelpExcelReportDao.selectExcelReportSum(reportDate);
	}


}
