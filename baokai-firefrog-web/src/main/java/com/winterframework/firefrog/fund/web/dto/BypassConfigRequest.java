package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.BypassConfigVO;

public class BypassConfigRequest implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2979175814224495164L;

	private Long type;
	
	private Long agency;
	
	private String isOpen;
	
	private List<BypassConfigVO> bypassList;

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getAgency() {
		return agency;
	}

	public void setAgency(Long agency) {
		this.agency = agency;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public List<BypassConfigVO> getBypassList() {
		return bypassList;
	}

	public void setBypassList(List<BypassConfigVO> bypassList) {
		this.bypassList = bypassList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
