package com.winterframework.firefrog.game.enums;

public enum GameBizOperType {
	//code:oper_target
	//oper:(撤销：4)
	//target:(订单:1,追号:2)
	CANCEL_ORDER("4_1","撤销订单");
	private String _code;
	private String _alias;
	private GameBizOperType(String code,String alias){
		this._code=code;
		this._alias=alias;
	}
	
	public String getCode() {
		return _code;
	} 
	public String getAlias(){
		return _alias;
	}
}
