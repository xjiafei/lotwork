package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundChargeBypassVO;



public class FundChargeBypassResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8486877186310077595L;
	private List<FundChargeBypassVO> bypassList;
	private String isExist;
	
	public List<FundChargeBypassVO> getBypassList() {
		return bypassList;
	}
	public void setBypassList(List<FundChargeBypassVO> bypassList) {
		this.bypassList = bypassList;
	}
	public String getIsExist() {
		return isExist;
	}
	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}
	
	
}
