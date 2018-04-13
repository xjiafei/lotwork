package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.entity.SingleIssueIncomeAndExpenseEntity;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IGameSingleIssueIncomeAndExpenseReportService 
* @Description: 单期收支报表Service
* @author Denny 
* @date 2014-2-21 下午2:56:36 
*  
*/
public interface IGameSingleIssueIncomeAndExpenseReportService {

	/** 
	* @Title: queryReport 
	* @Description: 单期收支报表查询 
	* @return Page<SingleIssueIncomeAndExpenseEntity>    返回类型 
	* @throws 
	*/
	public Page<SingleIssueIncomeAndExpenseEntity> queryReport(PageRequest<SingleIssueIncomeAndExpenseQueryRequest> pr);

}
