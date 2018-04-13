package com.winterframework.firefrog.fund.enums;


public enum SysFundStatusEnum {

	FAIL(0),
	SUCCESS(1),
	FAIL_NO_USER(2);
	
	private int code;
	
	SysFundStatusEnum(int code){
		this.code = code ;
	}
	public int getValue(){
		return code;
	}
}
