package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;


/** 
* @ClassName GameRiskIssueListQueryRequest 
* @Description 审核系统 审核订单操作 
* @author  hugh
* @date 2014年4月9日 下午3:16:29 
*  
*/
public class GameRiskOperateOrdersRequest implements Serializable {

	private static final long serialVersionUID = 1429388665080304524L;

	/** 彩种 */
	private List<Long> orderIds;

	//"处理类型 1 审核通过 2 审核不通过
	private Long status;
	
	private String disposeInfo;
	
	private String disposeMemo;
	
	private String disposeUser;

	public List<Long> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<Long> orderIds) {
		this.orderIds = orderIds;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getDisposeInfo() {
		return disposeInfo;
	}

	public void setDisposeInfo(String disposeInfo) {
		this.disposeInfo = disposeInfo;
	}

	public String getDisposeMemo() {
		return disposeMemo;
	}

	public void setDisposeMemo(String disposeMemo) {
		this.disposeMemo = disposeMemo;
	}

	public String getDisposeUser() {
		return disposeUser;
	}

	public void setDisposeUser(String disposeUser) {
		this.disposeUser = disposeUser;
	}
	


	
}
