package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawLog;

public class FundWithdrawLogRequest implements Serializable{

	private static final long serialVersionUID = 8594471619209571856L;
	
	private FundWithdrawLog drawLog;
	
	private String withdrawSn;
	
	private Date applyTime;
	
	private String withdrawTimeStr5;
	
	public String getWithdrawTimeStr5() {
		return withdrawTimeStr5;
	}

	public void setWithdrawTimeStr5(String withdrawTimeStr5) {
		this.withdrawTimeStr5 = withdrawTimeStr5;
	}

	public FundWithdrawLog getDrawLog() {
		return drawLog;
	}

	public void setDrawLog(FundWithdrawLog drawLog) {
		this.drawLog = drawLog;
	}

	public String getWithdrawSn() {
		return withdrawSn;
	}

	public void setWithdrawSn(String withdrawSn) {
		this.withdrawSn = withdrawSn;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}


}
