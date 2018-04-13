package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: QueryTransactionRecordResponse 
* @Description: 5.6.1	交易记录查询(REI001) response 
* @author Richard
* @date 2014-2-17 上午10:15:07 
*
 */
public class QueryTransactionRecordResponse implements Serializable{

	private static final long serialVersionUID = -1655795398696896360L;

	private List<FundTransactionStruc> fundTransactionStrucs;
	
	public QueryTransactionRecordResponse(){
		
	}

	public List<FundTransactionStruc> getFundTransactionStrucs() {
		return fundTransactionStrucs;
	}

	public void setFundTransactionStrucs(List<FundTransactionStruc> fundTransactionStrucs) {
		this.fundTransactionStrucs = fundTransactionStrucs;
	}
	
	
}
