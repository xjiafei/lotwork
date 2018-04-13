/**   
* @Title: GameSingleIssueIncomeAndExpenseReportDaoImpl.java 
* @Package com.winterframework.firefrog.game.dao 
* @Description: winter-game.SingleIssueIncomeAndExpenseReportDaoImpl.java 
* @author Denny  
* @date 2014-2-21 下午4:43:41 
* @version V1.0   
*/
package com.winterframework.firefrog.game.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.ISingleIssueIncomeAndExpenseReportDao;
import com.winterframework.firefrog.game.dao.vo.GameWinsReport;
import com.winterframework.firefrog.game.dao.vo.SingleIssueIncomeAndExpense;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.SingleIssueIncomeAndExpenseEntity;
import com.winterframework.firefrog.game.entity.WinsReport;
import com.winterframework.firefrog.game.web.dto.SingleIssueIncomeAndExpenseQueryRequest;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameSingleIssueIncomeAndExpenseReportDaoImpl 
* @Description: 单期收支报表Dao实现类 
* @author Denny 
* @date 2014-2-21 下午4:43:41 
*  
*/
@Repository("gameSingleIssueIncomeAndExpenseReportDaoImpl")
public class GameSingleIssueIncomeAndExpenseReportDaoImpl extends BaseIbatis3Dao<SingleIssueIncomeAndExpense> implements ISingleIssueIncomeAndExpenseReportDao {

	@Override
	public Page<SingleIssueIncomeAndExpenseEntity> getReport(PageRequest<SingleIssueIncomeAndExpenseQueryRequest> pr) {
		SingleIssueIncomeAndExpenseQueryRequest requestParam = pr.getSearchDo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", requestParam.getLotteryid());
		map.put("webIssueCode", requestParam.getWebIssueCode());
		if(requestParam.getStartCreateTime() != null){
			map.put("startTime", new Date(requestParam.getStartCreateTime()));
		}
		if(requestParam.getEndCreateTime() != null){
			map.put("endTime", new Date(requestParam.getEndCreateTime()));
		}
		
		Number totalCount = (Number) sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameWinsReport.getCountSingleIssueIncomeAndExpenseReport", map);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<SingleIssueIncomeAndExpenseEntity>(0);
		}
		
		Page<SingleIssueIncomeAndExpenseEntity> page = new Page<SingleIssueIncomeAndExpenseEntity>(pr.getPageNumber(), pr.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", 	page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pr.getSortColumns());
		filters.putAll(map);
		
		List<SingleIssueIncomeAndExpense> l = new ArrayList<SingleIssueIncomeAndExpense>();
		List<SingleIssueIncomeAndExpenseEntity> list = new ArrayList<SingleIssueIncomeAndExpenseEntity>();
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
			
		l = sqlSessionTemplate.selectList("com.winterframework.firefrog.game.dao.vo.GameWinsReport.getAllSingleIssueIncomeAndExpenseReport", filters, rowBounds);
		
		for (SingleIssueIncomeAndExpense vo : l) {
			SingleIssueIncomeAndExpenseEntity entity = VOConverter.gameSingleIssueIncomeAndExpense2Entity(vo);
			list.add(entity);
		}
		page.setResult(list);
		
		return page;
	}

}
