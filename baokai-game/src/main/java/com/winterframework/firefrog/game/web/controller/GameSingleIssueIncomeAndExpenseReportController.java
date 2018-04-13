/**   
* @Title: GameSingleIssueIncomeAndExpenseReportController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Denny  
* @date 2014-2-21 下午1:59:47 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winterframework.firefrog.common.annotation.ValidRequestBody;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.game.entity.SingleIssueIncomeAndExpenseEntity;
import com.winterframework.firefrog.game.service.IGameSingleIssueIncomeAndExpenseReportService;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseQueryRequest;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseQueryResponse;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseStruc;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: GameSingleIssueIncomeAndExpenseReportController 
* @Description: 单期收支报表Controller 
* @author Denny 
* @date 2014-2-21 下午1:59:47 
*  
*/
@Controller("gameSingleIssueIncomeAndExpenseReportController")
@RequestMapping("/game")
public class GameSingleIssueIncomeAndExpenseReportController {
	private Logger log = LoggerFactory.getLogger(GameWinsReportController.class);

	@Resource(name = "gameSingleIssueIncomeAndExpenseReportServiceImpl")
	private IGameSingleIssueIncomeAndExpenseReportService gameSingleIssueIncomeAndExpenseReportService;
	
	/** 
	* @Title: querySingleIssueIncomeAndExpense
	* @Description: 单期收支报表查询
	* @param request
	* @return
	* @throws Exception
	*/
	@RequestMapping(value = "/querySingleIssueIncomeAndExpense")
	@ResponseBody
	public Response<SingleIssueIncomeAndExpenseQueryResponse> querySingleIssueIncomeAndExpense(
			@RequestBody @ValidRequestBody Request<SingleIssueIncomeAndExpenseQueryRequest> request) throws Exception {

		log.info("开始查询单期收支报表......");
		Response<SingleIssueIncomeAndExpenseQueryResponse> response = new Response<SingleIssueIncomeAndExpenseQueryResponse>(request);

		int sNo = request.getBody().getPager().getStartNo();
		int eNo = request.getBody().getPager().getEndNo();

		PageRequest<SingleIssueIncomeAndExpenseQueryRequest> pr = PageConverterUtils.getRestPageRequest(sNo, eNo);
		pr.setSearchDo(request.getBody().getParam());

		Page<SingleIssueIncomeAndExpenseEntity> page = null;
		List<SingleIssueIncomeAndExpenseEntity> reports = null;
		List<SingleIssueIncomeAndExpenseStruc> strucs = new ArrayList<SingleIssueIncomeAndExpenseStruc>();
		SingleIssueIncomeAndExpenseQueryResponse result = new SingleIssueIncomeAndExpenseQueryResponse();
		SingleIssueIncomeAndExpenseStruc struc = null;
		try {
			page = gameSingleIssueIncomeAndExpenseReportService.queryReport(pr);
			reports = page.getResult();
			if (reports != null && reports.size() > 0) {
				for (SingleIssueIncomeAndExpenseEntity s : reports) {
					struc = DTOConvert.SingleIssueIncomeAndExpenseEntity2SingleIssueIncomeAndExpenseStruc(s);
					strucs.add(struc);
				}
			}
			
			result.setSingleIssueIncomeAndExpenseStrucList(strucs);
			response.setResult(result);
			ResultPager pager = new ResultPager();
			pager.setEndNo(page.getThisPageFirstElementNumber());
			pager.setStartNo(page.getThisPageFirstElementNumber());
			pager.setTotal(page.getTotalCount());
			response.setResultPage(pager);

		} catch (Exception e) {
			log.error("查询单期收支报表异常 ", e);
			throw e;
		}

		log.info("查询单期收支报表完成。");
		return response;
	}
}
