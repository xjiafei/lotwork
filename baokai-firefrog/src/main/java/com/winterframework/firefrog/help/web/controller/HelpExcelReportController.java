package com.winterframework.firefrog.help.web.controller;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.help.entity.Oper;
import com.winterframework.firefrog.help.entity.OperReportSum;
import com.winterframework.firefrog.help.service.IHelpExcelReportService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

@Controller("helpExcelReportController")
@RequestMapping("/operation")
public class HelpExcelReportController<T> {
	private static final Logger logger = LoggerFactory.getLogger(HelpExcelReportController.class);
	
	@Autowired
	IHelpExcelReportService iHelpExcelReportService;
	
	
	/**
	 * 报表查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/statistics")
	public @ResponseBody
	Response<List<Oper>> showExcelReportForMonth(@RequestBody  Request<String> request) throws Exception{
		logger.info("qury excelReport start");
				
		
		PageRequest<String> operPageRequest=PageConverterUtils.getRestPageRequest(request.getBody().getPager().getStartNo(),
				request.getBody().getPager().getEndNo());
		
		
		operPageRequest.setSearchDo(request.getBody().getParam());
		operPageRequest.setSortColumns("BIZ_DATE DESC");
		
		Response<List<Oper>> response=new Response<List<Oper>>();
		

		Page<Oper> page;
		
		try {
			page=iHelpExcelReportService.getExcelByDate(operPageRequest);
		} catch (Exception e) {
			logger.info("query allExcel error");
			throw e;
		}
			
		ResultPager resultPager = new ResultPager();
		resultPager.setStartNo(page.getThisPageFirstElementNumber());
		resultPager.setEndNo(page.getThisPageLastElementNumber());
		resultPager.setTotal(page.getTotalCount());
		
		response.setResult(page.getResult());
		response.setResultPage(resultPager);
		

		return response;
	}
	
	@RequestMapping(value="/queryExcelReportSum")
	public @ResponseBody
	Response<OperReportSum> queryExcelReportSum(@RequestBody  Request<String> request) {
		logger.info("qury excelReportSum start");
		Response<OperReportSum> response=new Response<OperReportSum>();
		
		response.setResult(iHelpExcelReportService.getExcelReportSum(request.getBody().getParam()));
		
		return response;
		
	}
}
