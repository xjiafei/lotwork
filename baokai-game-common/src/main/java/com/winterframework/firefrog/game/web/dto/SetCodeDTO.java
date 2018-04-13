package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class SetCodeDTO implements Serializable {
	
	private static final long serialVersionUID = -4599833855128985580L;

	private Integer setCode;
	private String setCodeName;
	private Integer setCount = 0;
	
	//投注方式
	private List<MethodCodeDTO> methodCodeList;
	
	public Integer getSetCode() {
		return setCode;
	}
	public void setSetCode(Integer setCode) {
		this.setCode = setCode;
	}
	public String getSetCodeName() {
//		setCodeName = GameAwardNameUtil.setCodeName(setCode);
		return setCodeName;
	}
	public void setSetCodeName(String setCodeName) {
		this.setCodeName = setCodeName;
	}
	public List<MethodCodeDTO> getMethodCodeList() {
		return methodCodeList;
	}
	public void setMethodCodeList(List<MethodCodeDTO> methodCodeList) {
		this.methodCodeList = methodCodeList;
	}
	public Integer getSetCount() {
		setCount = methodCodeList.size();
		return setCount;
	}
	public void setSetCount(Integer setCount) {
		this.setCount = setCount;
	}
	
	
}
