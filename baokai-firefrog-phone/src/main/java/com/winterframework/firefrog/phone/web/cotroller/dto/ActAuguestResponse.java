package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;


/** 
* @ClassName ActRedEnvelopeResponse 
* @Description 
* @author  Ray
* @date 2016年07月15日 上午11:52:37 
*  
*/
public class ActAuguestResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2108726806220372577L;

	
	//0:失敗　1:成功
	private Long result;
	
	//紅包 -3:異常 -2:不再活動時間 -1:不再當天活動區間 0:已抽過紅包 1:成功
	//奧運 -1:不在活動區間 0:未報名 1:已報名或不需報
	private Long status;
	
	//剩餘紅包數
	private Long rest;
	
	//紅包金額
	private Long redEnvelope;
	
	//需投注總額
	private Long betsTotal;
	
	//已投注金額
	private Long betAmount;
	
	//充值金額
	private Long chargeAmount;
	
	//返利比例
	private Long RebateRatio;
	
	//加獎比例
	private Long plusRatio;
	
	//金牌數
	private Long medals;
	
	//獎金總額
	private String prize;
	
	//錯誤訊息
	private String errorMsg ;

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Long getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Long chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public Long getRebateRatio() {
		return RebateRatio;
	}

	public void setRebateRatio(Long rebateRatio) {
		RebateRatio = rebateRatio;
	}

	public Long getPlusRatio() {
		return plusRatio;
	}

	public void setPlusRatio(Long plusRatio) {
		this.plusRatio = plusRatio;
	}

	public Long getMedals() {
		return medals;
	}

	public void setMedals(Long medals) {
		this.medals = medals;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getRest() {
		return rest;
	}

	public void setRest(Long rest) {
		this.rest = rest;
	}

	public Long getRedEnvelope() {
		return redEnvelope;
	}

	public void setRedEnvelope(Long redEnvelope) {
		this.redEnvelope = redEnvelope;
	}

	public Long getBetsTotal() {
		return betsTotal;
	}

	public void setBetsTotal(Long betsTotal) {
		this.betsTotal = betsTotal;
	}

	public Long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Long betAmount) {
		this.betAmount = betAmount;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
    
}
