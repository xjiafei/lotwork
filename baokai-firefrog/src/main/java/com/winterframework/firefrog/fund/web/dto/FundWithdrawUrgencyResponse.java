package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundWithdrawUrgency;

/**
 * 
 * @ClassName FundWithdrawUrgencyResponse.java
 * @Description 
 * @author Ami.Tsai
 * @date 2015年12月24日
 *
 */
public class FundWithdrawUrgencyResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8125457126277531018L;

	
	private List<FundWithdrawUrgency> urgencys;


	public List<FundWithdrawUrgency> getUrgencys() {
		return urgencys;
	}


	public void setUrgencys(List<FundWithdrawUrgency> urgencys) {
		this.urgencys = urgencys;
	}
	
}
