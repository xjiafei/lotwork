package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;

/**
 * 
 * @ClassName FundWithdrawUrgencyRequest.java
 * @Description 
 * @author Ami.Tsai
 * @date 2015年12月24日
 *
 */
public class FundWithdrawUrgencyRequest implements Serializable{

	private FundWithdrawUrgency urgencys ;
	
	private Date applyTime;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5054346187604252284L;

	public FundWithdrawUrgency getUrgencys() {
		return urgencys;
	}

	public void setUrgencys(FundWithdrawUrgency urgencys) {
		this.urgencys = urgencys;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}


}
