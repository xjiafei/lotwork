package com.winterframework.firefrog.fund.service;

import com.winterframework.firefrog.fund.enums.EnumItem;

public interface IDisableAmtChangeService {

	/** 
	 * 不可用余额加
	*/
	Long getAddAmt(Long amount, EnumItem item);

	/** 
	* 不可用余额减
	*/
	Long getReduceAmt(Long amount, EnumItem item);
}
