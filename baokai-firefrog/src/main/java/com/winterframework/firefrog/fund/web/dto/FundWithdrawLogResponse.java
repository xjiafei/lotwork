package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawLog;

/**
 * 
 * @ClassName FundWithdrawLogResponse.java
 * @Description 
 * @author Ami.Tsai
 * @date 2015年12月22日
 *
 */
public class FundWithdrawLogResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5406315600774168331L;

	private Boolean isSuccess;
	
	private FundWithdrawLog withDrawLog;
	
	private List<FundWithdrawLog> withDrawLogs;
	
	private List<String> logData;

	public FundWithdrawLog getWithDrawLog() {
		return withDrawLog;
	}

	public void setWithDrawLog(FundWithdrawLog withDrawLog) {
		this.withDrawLog = withDrawLog;
	}

	public List<FundWithdrawLog> getWithDrawLogs() {
		return withDrawLogs;
	}

	public void setWithDrawLogs(List<FundWithdrawLog> withDrawLogs) {
		this.withDrawLogs = withDrawLogs;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public List<String> getLogData() {
		return logData;
	}

	public void setLogData(List<String> logData) {
		this.logData = logData;
	}
}
