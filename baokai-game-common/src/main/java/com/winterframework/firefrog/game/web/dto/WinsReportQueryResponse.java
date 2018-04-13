package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

/** 
* @ClassName: WinsReportQueryResponse 
* @Description: 盈亏报表查询响应参数DTO 
* @author Denny 
* @date 2013-10-16 上午9:48:17 
*  
*/
public class WinsReportQueryResponse implements Serializable{

	private static final long serialVersionUID = -3604583954221848731L;

	private List<OperationReportStruc> operationReportStruc;

	public List<OperationReportStruc> getOperationReportStruc() {
		return operationReportStruc;
	}

	public void setOperationReportStruc(List<OperationReportStruc> operationReportStruc) {
		this.operationReportStruc = operationReportStruc;
	}
	
	
}
