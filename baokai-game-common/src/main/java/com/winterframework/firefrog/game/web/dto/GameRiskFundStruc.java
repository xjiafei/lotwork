package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: GameReportStruc 
* @Description:审核系统请求资金返回
* @author hugh
* @date 2014-4-23 上午10:46:10 
*
 */
public class GameRiskFundStruc implements Serializable{

	private static final long serialVersionUID = 9046049608557427582L;

	private String tid;//交易流水
	
	private String fundType;//摘要
	
	private String operatorId;//资金变更用户id
	
	private String amount;//资金
	
	private String symbol;//交易符号(-+)

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	
}
