package com.winterframework.firefrog.game.enums;

public enum YesNo {
	YES(1),NO(0);
	private int _value;
	private YesNo(int value){
		this._value=value;
	}
	
	public int getValue(){
		return _value;
	}
}
