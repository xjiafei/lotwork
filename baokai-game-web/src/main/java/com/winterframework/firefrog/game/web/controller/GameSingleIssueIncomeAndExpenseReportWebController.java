/**   
* @Title: GameSingleIssueIncomeAndExpenseReportWebController.java 
* @Package com.winterframework.firefrog.game.web.controller 
* @Description: winter-game-web.GameSingleIssueIncomeAndExpenseReportWebController.java 
* @author Denny  
* @date 2014-2-21 下午5:07:26 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.controller;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.game.web.dto.PageForView;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseQueryRequest;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseQueryResponse;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseStruc;
import com.winterframework.firefrog.game.web.util.PageUtils;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Pager;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: GameSingleIssueIncomeAndExpenseReportWebController 
* @Description: 审核系统单期收支报表查询Controller
* @author Denny 
* @date 2014-2-21 下午5:07:26 
*  
*/
@RequestMapping(value = "/gameRisk")
@Controller("gameSingleIssueIncomeAndExpenseReportWebController")
public class GameSingleIssueIncomeAndExpenseReportWebController {
	private Logger logger = LoggerFactory.getLogger(GameBetQueryController.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.connect")
	private String serverPath;

	@PropertyConfig(value = "url.game.querySingleIssueIncomeAndExpenseReport")
	private String querySingleIssueIncomeAndExpenseReportUrl;

	/**
	 * 
	* @Title: querySingleIssueIncomeAndExpenseReport
	* @Description: 查询单期收支报表
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/querySingleIssueIncomeAndExpenseReport")
	public String querySingleIssueIncomeAndExpenseReport(
			@ModelAttribute("page") PageRequest<SingleIssueIncomeAndExpenseQueryRequest> page,
			@ModelAttribute("req") SingleIssueIncomeAndExpenseQueryRequest request, Integer time, Model model,
			@ModelAttribute("pageCount") String pageCount) throws Exception {
		logger.info("querySingleIssueIncomeAndExpenseReport start");
		Response<SingleIssueIncomeAndExpenseQueryResponse> response = new Response<SingleIssueIncomeAndExpenseQueryResponse>();

		if ("".equals(pageCount)) {
			page.setPageSize(25);
		} else {
			page.setPageSize(Integer.parseInt(pageCount));
		}
		Pager pager = PageUtils.getPager(page);

		if (null == request.getStartCreateTime()) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			request.setStartCreateTime(cal.getTimeInMillis());
		}
		if (null == request.getEndCreateTime()) {
			Calendar cal = Calendar.getInstance();
			request.setEndCreateTime(cal.getTimeInMillis());
		}
		if (time == -1) {
			request.setStartCreateTime(null);
			request.setEndCreateTime(null);
		}

		try {
			response = httpClient.invokeHttp(serverPath + querySingleIssueIncomeAndExpenseReportUrl, request, pager,
					SingleIssueIncomeAndExpenseQueryResponse.class);

			logger.info("querySingleIssueIncomeAndExpenseReport end");
		} catch (Exception e) {
			logger.error("querySingleIssueIncomeAndExpenseReport is error.", e);
			throw e;
		}

		ResultPager rp = response.getBody().getPager();
		PageForView pv = PageUtils.getPageForView(page, rp);

		List<SingleIssueIncomeAndExpenseStruc> reports = response.getBody().getResult()
				.getSingleIssueIncomeAndExpenseStrucList();

		model.addAttribute("reports", reports);
		model.addAttribute("req", request);
		model.addAttribute("time", time);
		model.addAttribute("page", pv);

		return "/risk/singleIssueIncomeAndExpenseReport";
	}

}
