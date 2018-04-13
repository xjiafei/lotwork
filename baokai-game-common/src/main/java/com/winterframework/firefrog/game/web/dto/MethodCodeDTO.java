package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

public class MethodCodeDTO implements Serializable {

	private static final long serialVersionUID = 6903220564546969309L;

	private Integer methodCode;
	private String methodCodeName;
	/**理论奖金（分）*/
	private Long theoryBonus;
	/**总利润*/
	private Long totalProfit;
	/**总代返点*/
	private Long topAgentPoint;
	/**公司流水*/
	private Long companyProfit;
	
	private Long totalRetbate;

	private Long actualBonus;
	
	private Long actualBonusDown;

	private Long actualBonusBak;

	private List<AssistBonusDTO> assistBonusList;
	private List<MethodCodeDTO> methodCodeList = new ArrayList<MethodCodeDTO>();

	private Integer methodCount = 0;
	private Integer methodCodeCount = 0;
	private Long retVal;

	public Integer getMethodCode() {
		return methodCode;
	}

	public void setMethodCode(Integer methodCode) {
		this.methodCode = methodCode;
	}

	public String getMethodCodeName() {
//		methodCodeName = GameAwardNameUtil.methodCodeName(methodCode);
		return methodCodeName;
	}

	public void setMethodCodeName(String methodCodeName) {
		this.methodCodeName = methodCodeName;
	}

	public Long getTheoryBonus() {
		return theoryBonus;
	}

	public void setTheoryBonus(Long theoryBonus) {
		this.theoryBonus = theoryBonus;
	}

	public Long getTotalProfit() {
		return 10000l - totalRetbate;
	}

	public Long getTotalRetbate() {
		return totalRetbate;
	}

	public void setTotalRetbate(Long totalRetbate) {
		this.totalRetbate = totalRetbate;
	}

	public void setTotalProfit(Long totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Long getTopAgentPoint() {
		return topAgentPoint;
	}

	public void setTopAgentPoint(Long topAgentPoint) {
		this.topAgentPoint = topAgentPoint;
	}

	public Long getCompanyProfit() {
		return (10000 - retVal - totalRetbate);
	}

	public void setCompanyProfit(Long companyProfit) {
		this.companyProfit = companyProfit;
	}

	public Long getActualBonus() {
		return actualBonus;
	}

	public void setActualBonus(Long actualBonus) {
		this.actualBonus = actualBonus;
	}

	public Long getActualBonusBak() {
		return actualBonusBak;
	}

	public void setActualBonusBak(Long actualBonusBak) {
		this.actualBonusBak = actualBonusBak;
	}

	public List<AssistBonusDTO> getAssistBonusList() {
		return assistBonusList;
	}

	public void setAssistBonusList(List<AssistBonusDTO> assistBonusList) {
		this.assistBonusList = assistBonusList;
	}

	public Integer getMethodCount() {
		methodCount = assistBonusList != null ? assistBonusList.size() : 0;
		return methodCount;
	}

	public void setMethodCount(Integer methodCount) {
		this.methodCount = methodCount;
	}

	public List<MethodCodeDTO> getMethodCodeList() {
		return methodCodeList;
	}

	public void setMethodCodeList(List<MethodCodeDTO> methodCodeList) {
		this.methodCodeList = methodCodeList;
	}

	public Integer getMethodCodeCount() {
		return methodCodeList.size();
	}

	public void setMethodCodeCount(Integer methodCodeCount) {
		this.methodCodeCount = methodCodeCount;
	}

	public Long getRetVal() {
		return retVal;
	}

	public void setRetVal(Long retVal) {
		this.retVal = retVal;
	}

	public Long getActualBonusDown() {
		return actualBonusDown;
	}

	public void setActualBonusDown(Long actualBonusDown) {
		this.actualBonusDown = actualBonusDown;
	}

}
