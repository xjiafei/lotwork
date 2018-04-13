/**   
* @Title: BankCardBindHistoryRecordResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-24 上午10:21:39 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.List;

/** 
* @ClassName: BankCardBindHistoryRecordResponse 
* @Description: 绑卡历史记录response 
* @author Alan
* @date 2013-7-24 上午10:21:39 
*  
*/
public class BankCardBindHistoryRecordResponse {

	private List<BankCardHistoryStruc> bankCardHistorys;

	public List<BankCardHistoryStruc> getBankCardHistorys() {
		return bankCardHistorys;
	}

	public void setBankCardHistorys(List<BankCardHistoryStruc> bankCardHistorys) {
		this.bankCardHistorys = bankCardHistorys;
	}

}
