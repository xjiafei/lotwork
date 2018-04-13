package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/** 
* @ClassName ActivityUserAwardRequest 
* @Description 用户获奖记录
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityDoubleboxResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -542301540358622695L;
	/**
	 * 
	 */
	private Long result;
	
	private Long isSuccess;
	
	private Boolean isPrize;
	
    private Long hisBonus; 
	
	private Long betAmount;
	
	private String betRatio;
	
	private List<Map<String,String>> data;
	
	private Long endTime;
	
	//發獎用參數
	private String multiple;
	
	private String bonus;

	
	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public Long getHisBonus() {
		return hisBonus;
	}

	public void setHisBonus(Long hisBonus) {
		this.hisBonus = hisBonus;
	}

	public Long getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Long isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Boolean getIsPrize() {
		return isPrize;
	}

	public void setIsPrize(Boolean isPrize) {
		this.isPrize = isPrize;
	}

	public Long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Long betAmount) {
		this.betAmount = betAmount;
	}

	public String getBetRatio() {
		return betRatio;
	}

	public void setBetRatio(String betRatio) {
		this.betRatio = betRatio;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}
}
