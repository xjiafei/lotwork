package com.winterframework.firefrog.user.web.dto;

import java.util.List;

public class RetElectronicRetResult {
	private String retCode;
	private List<PtRetItem> ptRetList;
	private List<PtBonusItem> ptBonusList;
	private List<WgRetItem> wgRetList;
	private List<FhxRetItem> fhxRetList;
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public List<PtRetItem> getPtRetList() {
		return ptRetList;
	}
	public void setPtRetList(List<PtRetItem> ptRetList) {
		this.ptRetList = ptRetList;
	}
	public List<PtBonusItem> getPtBonusList() {
		return ptBonusList;
	}
	public void setPtBonusList(List<PtBonusItem> ptBonusList) {
		this.ptBonusList = ptBonusList;
	}
	public List<WgRetItem> getWgRetList() {
		return wgRetList;
	}
	public void setWgRetList(List<WgRetItem> wgRetList) {
		this.wgRetList = wgRetList;
	}
	public List<FhxRetItem> getFhxRetList() {
		return fhxRetList;
	}
	public void setFhxRetList(List<FhxRetItem> fhxRetList) {
		this.fhxRetList = fhxRetList;
	}

	

}
