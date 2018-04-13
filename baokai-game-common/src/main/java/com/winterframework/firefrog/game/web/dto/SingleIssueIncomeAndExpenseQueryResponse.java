package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: SingleIssueIncomeAndExpenseQueryResponse 
* @Description: 单期收支报表查询响应参数DTO 
* @author Denny 
* @date 2014-2-21 下午3:02:34 
*  
*/
public class SingleIssueIncomeAndExpenseQueryResponse implements Serializable {

	private static final long serialVersionUID = -5948283432463646818L;

	private List<SingleIssueIncomeAndExpenseStruc> singleIssueIncomeAndExpenseStrucList;

	public List<SingleIssueIncomeAndExpenseStruc> getSingleIssueIncomeAndExpenseStrucList() {
		return singleIssueIncomeAndExpenseStrucList;
	}

	public void setSingleIssueIncomeAndExpenseStrucList(
			List<SingleIssueIncomeAndExpenseStruc> singleIssueIncomeAndExpenseStrucList) {
		this.singleIssueIncomeAndExpenseStrucList = singleIssueIncomeAndExpenseStrucList;
	}
}
