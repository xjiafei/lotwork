/**   
* @Title: GameSingleIssueIncomeAndExpenseReportServiceImpl.java 
* @Package com.winterframework.firefrog.game.service.impl 
* @Description: winter-game.GameSingleIssueIncomeAndExpenseReportServiceImpl.java 
* @author Denny  
* @date 2014-2-21 下午4:12:58 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.ISingleIssueIncomeAndExpenseReportDao;
import com.winterframework.firefrog.game.entity.SingleIssueIncomeAndExpenseEntity;
import com.winterframework.firefrog.game.service.IGameSingleIssueIncomeAndExpenseReportService;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: GameSingleIssueIncomeAndExpenseReportServiceImpl 
* @Description: 单期收支报表Service实现类 
* @author Denny 
* @date 2014-2-21 下午4:12:58 
*  
*/
@Service("gameSingleIssueIncomeAndExpenseReportServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameSingleIssueIncomeAndExpenseReportServiceImpl implements IGameSingleIssueIncomeAndExpenseReportService {

	@Resource(name = "gameSingleIssueIncomeAndExpenseReportDaoImpl")
	private ISingleIssueIncomeAndExpenseReportDao singleIssueIncomeAndExpenseReportDao;
	
	@Override
	public Page<SingleIssueIncomeAndExpenseEntity> queryReport(PageRequest<SingleIssueIncomeAndExpenseQueryRequest> pr) {
		return singleIssueIncomeAndExpenseReportDao.getReport(pr);
	}

}
