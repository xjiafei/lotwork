package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: QueryTransactionRecordRequest 
* @Description:5.6.1	交易记录查询(REI001) request
* @author Richard
* @date 2014-2-17 上午10:14:04 
*
 */
public class QueryTransactionRecordRequest implements Serializable {

	private static final long serialVersionUID = 5539894410775879290L;

	private Long userid;
	private List<String> orderCodeList;
	private List<String> planCodeList;
	
	public QueryTransactionRecordRequest(){
		
	}
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public List<String> getOrderCodeList() {
		return orderCodeList;
	}
	public void setOrderCodeList(List<String> orderCodeList) {
		this.orderCodeList = orderCodeList;
	}
	public List<String> getPlanCodeList() {
		return planCodeList;
	}
	public void setPlanCodeList(List<String> planCodeList) {
		this.planCodeList = planCodeList;
	}
	
	
	
}
