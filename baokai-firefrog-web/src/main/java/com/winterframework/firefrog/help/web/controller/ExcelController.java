package com.winterframework.firefrog.help.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.http.IHttpJsonClient;
import com.winterframework.firefrog.help.excel.entity.Oper;
import com.winterframework.firefrog.help.excel.entity.OperSum;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;



@Controller("excelController")
@RequestMapping("/operation")
public class ExcelController {
	private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.help.operation.statistics")
	private String operationStatistics;
	
	@PropertyConfig(value="url.help.operation.queryCount")
	private String queryCount;
	
	@PropertyConfig(value = "url.connect")
	private String serverPath;
	
	@PropertyConfig(value = "page.pagesize")
	private Integer pageSize;
	
	private String getUrl(String path) {
		return serverPath + path;
	}
/*	@Autowired
	private ExcelReportService excelReport;*/
	
	
	/**
	 * 报表展示Controller层
	 * @param request
	 * @param response
	 * @param year
	 * @param month
	 * @param day
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/statistics")
	public String showExcelAtDate(HttpServletRequest request,
			 @ModelAttribute("page") PageRequest<Oper> page, Model model){
	
		logger.info("statistics start");
		
		String date=request.getParameter("excelTimeStr");
		
		
//		分页
		page.setPageSize(pageSize);
		
		int startNo = page.getPageNo() == 0 ? 1 : (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endNo = page.getPageSize() - 1 + startNo;
		
		Pager pager=new Pager();
		pager.setStartNo(startNo);
		pager.setEndNo(endNo);	
		
		Response<List<Oper>> operResponse=null;
		Response<OperSum> operCountResponse=null;
		
		try {
			operResponse = httpClient.invokeHttp(getUrl(operationStatistics),date,pager, 
					new TypeReference<Response<List<Oper>>>() {});
			if(operResponse!=null) {
				operCountResponse=httpClient.invokeHttp(getUrl(queryCount),date,
						new TypeReference<Response<OperSum>>() {});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		if(operResponse==null)return "/help/userReport";
		
		ResultPager resultPage = operResponse.getBody().getPager();
		int pageNo = resultPage.getStartNo() / pageSize + (resultPage.getStartNo() % pageSize == 0 ? 0 : 1);
		
		Page<Object> newPage = new Page<Object>(pageNo, pageSize.intValue(), resultPage.getTotal());
		
		List<Oper> oper2 =operResponse.getBody().getResult();
		
		
		
		model.addAttribute("opers", operResponse.getBody().getResult());
		model.addAttribute("page",newPage);
		
		if(operCountResponse.getBody().getResult()==null)return "/help/userReport";
		
		model.addAttribute("operCount",operCountResponse.getBody().getResult());
		return "/help/userReport";
	}

	

}
