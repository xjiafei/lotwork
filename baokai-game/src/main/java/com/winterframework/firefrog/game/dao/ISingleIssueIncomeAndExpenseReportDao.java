/**   
* @Title: ISingleIssueIncomeAndExpenseReportDao.java 
* @Package com.winterframework.firefrog.game.dao 
* @Description: winter-game.ISingleIssueIncomeAndExpenseReportDao.java 
* @author Denny  
* @date 2014-2-21 下午4:15:03 
* @version V1.0   
*/
package com.winterframework.firefrog.game.dao;

import com.winterframework.firefrog.game.dao.vo.SingleIssueIncomeAndExpense;
import com.winterframework.firefrog.game.entity.SingleIssueIncomeAndExpenseEntity;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: ISingleIssueIncomeAndExpenseReportDao 
* @Description: 单期收支报表Dao接口 
* @author Denny 
* @date 2014-2-21 下午4:15:03 
*  
*/
public interface ISingleIssueIncomeAndExpenseReportDao extends BaseDao<SingleIssueIncomeAndExpense>{

	/** 
	* @Title: getReport 
	* @Description: 获取单期收支报表数据 
	* @param @param pr
	* @param @return    设定文件 
	* @return Page<SingleIssueIncomeAndExpenseEntity>    返回类型 
	* @throws 
	*/
	public Page<SingleIssueIncomeAndExpenseEntity> getReport(PageRequest<SingleIssueIncomeAndExpenseQueryRequest> pr);

}
